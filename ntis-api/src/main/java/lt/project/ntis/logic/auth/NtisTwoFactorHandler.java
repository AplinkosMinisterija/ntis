package lt.project.ntis.logic.auth;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.TwoFactorHandler;
import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;

import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.app.job.request.Login2faJobRequest;

// Iššūkis saugomas spark.spr_process_requests: prq_reference_id=usr_id, prq_token=kodas,
// c01=slaptas handle (įrodo, kad kviečiantysis praėjo 1 žingsnį), n01=bandymų skaitiklis (per iššūkį).
@Component
public class NtisTwoFactorHandler implements TwoFactorHandler {

    public static final String AUTH_TYPE_PASSWORD = "USER_PASSWORD_AUTH";

    // Serverio pusės resend throttle (klientinis 30s cooldown apeinamas kviečiant API tiesiogiai).
    private static final long RESEND_MIN_INTERVAL_SECONDS = 30;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private DBPropertyManager dbPropertyManager;

    @Autowired
    private SprProcessRequest sprProcessRequest;

    @Autowired
    private ExecutorJob executerJob;

    @Autowired
    private Login2faJobRequest login2faJobRequest;

    @Value("${app.host:}")
    private String appHost;

    @Override
    public boolean isRequired(Connection conn, SprUsersDAO userDAO, Map<String, Object> authExtData) throws Exception {
        boolean enabled = "Y".equalsIgnoreCase(dbPropertyManager.getPropertyByName("TWO_FA_EMAIL_ENABLED", "N"))
                || "true".equalsIgnoreCase(dbPropertyManager.getPropertyByName("TWO_FA_EMAIL_ENABLED", "N"));
        if (!enabled) {
            return false;
        }
        Object authType = authExtData == null ? null : authExtData.get("AUTH_TYPE");
        if (!AUTH_TYPE_PASSWORD.equals(authType)) {
            return false; // tik slaptažodžio kelias; VIISP/iSense/Google/API-key — neliečiam
        }
        if (!"Y".equalsIgnoreCase(userDAO.getUsr_2fa_used())) {
            return false;
        }
        String email = userDAO.getUsr_email();
        if (email == null || email.trim().isEmpty()) {
            // 2FA įjungtas naudotojui, bet nėra el. pašto — niekada neapeinam į normalų login
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.noEmail", SparkMessageType.ERROR,
                    "2FA is enabled for the user but no email is set"));
        }
        return true;
    }

    // Grąžina slaptą handle, kurį SprAuthorization įdeda į sesiją -> klientas jį pateikia verify/resend metu.
    @Override
    public String issueAndSend(Connection conn, SprUsersDAO userDAO) throws Exception {
        String handle = generateHandle();
        doIssue(conn, userDAO, handle);
        return handle;
    }

    // Resend saugumas: reikalaujam AKTYVAUS iššūkio su tuo pačiu handle (be jo — jokio siuntimo/spamo),
    // serverio 30s throttle. Bandymų skaitiklis lieka ant iššūkio eilutės (n01).
    public void resendCode(Connection conn, SprUsersDAO userDAO, String handle) throws Exception {
        Double userId = userDAO.getUsr_id();
        Timestamp issuedAt = getActiveChallengeIssuedAt(conn, userId, handle);
        if (issuedAt == null) {
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.noChallenge", SparkMessageType.ERROR,
                    "No active 2FA challenge for this user"));
        }
        if (System.currentTimeMillis() - issuedAt.getTime() < RESEND_MIN_INTERVAL_SECONDS * 1000L) {
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.resendTooSoon", SparkMessageType.ERROR,
                    "2FA code was just sent; wait before resending"));
        }
        doIssue(conn, userDAO, handle);
    }

    private void doIssue(Connection conn, SprUsersDAO userDAO, String handle) throws Exception {
        Double userId = userDAO.getUsr_id();
        String email = userDAO.getUsr_email();

        deleteExistingCodes(conn, userId);

        int codeLength = parseIntProp("TWO_FA_CODE_LENGTH", 6);
        long expiryMin = parseIntProp("TWO_FA_CODE_EXPIRATION_MINUTES", 5);
        String code = generateNumericCode(codeLength);

        // c01 = handle (slaptas iššūkio raktas); n01 (bandymai) startuoja null=0.
        sprProcessRequest.createRequest(conn, NtisProcessRequestType.LOGIN_2FA, YesNo.N, userId, Languages.LT,
                new Date(), expiryMin, code, handle);

        Map<String, String> params = new HashMap<String, String>();
        params.put(Login2faJobRequest.CODE, code);
        params.put(Login2faJobRequest.HOME_URL, appHost);
        params.put(ExecuteEmailSendTask.RECEIVER, email);
        Double requestId = login2faJobRequest.createJobRequest(conn, userId, Languages.LT, params);
        conn.commit();
        executerJob.execute(requestId);

        // executerJob klaidas praryja — patį siuntimo rezultatą tikrinam per job statusą (COMMITTED matomas).
        String status = null;
        String jobErr = null;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT jrq_status, jrq_error FROM spark.spr_job_requests WHERE jrq_id = ?")) {
            ps.setDouble(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString(1);
                    jobErr = rs.getString(2);
                }
            }
        }
        if (!"COMPLETED".equalsIgnoreCase(status)) {
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.sendFailed", SparkMessageType.ERROR,
                    "Failed to send 2FA email" + (jobErr != null ? ": " + jobErr : "")));
        }
    }

    public void verifyAndConsume(Connection conn, SprUsersDAO userDAO, String handle, String submittedCode) throws Exception {
        Double userId = userDAO.getUsr_id();
        int maxAttempts = parseIntProp("TWO_FA_MAX_ATTEMPTS", 5);

        long prqId = -1;
        String storedCode = null;
        Timestamp expiresAt = null;
        int attempts = 0;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT prq_id, prq_token, prq_date_to, coalesce(n01,0) FROM spark.spr_process_requests "
                        + "WHERE prq_type = ? AND prq_reference_id = ? AND c01 = ? ORDER BY prq_id DESC LIMIT 1")) {
            ps.setString(1, NtisProcessRequestType.LOGIN_2FA.getCode());
            ps.setDouble(2, userId);
            ps.setString(3, handle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prqId = rs.getLong(1);
                    storedCode = rs.getString(2);
                    expiresAt = rs.getTimestamp(3);
                    attempts = rs.getInt(4);
                }
            }
        }

        // Handle nesutampa arba iššūkio nėra -> nėra ką tikrinti; neatskleidžiam handle galiojimo.
        if (storedCode == null) {
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.expired", SparkMessageType.ERROR,
                    "The 2FA code has expired"));
        }

        if (expiresAt == null || expiresAt.before(new Timestamp(System.currentTimeMillis()))) {
            deleteById(conn, prqId);
            conn.commit();
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.expired", SparkMessageType.ERROR,
                    "The 2FA code has expired"));
        }

        if (attempts >= maxAttempts) {
            deleteById(conn, prqId);
            conn.commit();
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.tooManyAttempts", SparkMessageType.ERROR,
                    "Too many 2FA attempts"));
        }

        if (!storedCode.equals(submittedCode)) {
            incrementRowAttempts(conn, prqId);
            // KRITINIS: commit'inam skaitiklį prieš metant, kitaip @Transactional(rollbackFor) jį atsuktų
            conn.commit();
            throw new SparkBusinessException(new S2Message("pages.login.twoFactor.wrongCode", SparkMessageType.ERROR,
                    "Wrong 2FA code"));
        }

        deleteById(conn, prqId); // teisingas — sunaudojam (single-use)
    }

    @Override
    public String maskEmail(String email) {
        if (email == null || email.indexOf('@') < 1) {
            return email;
        }
        int at = email.indexOf('@');
        String local = email.substring(0, at);
        String domain = email.substring(at);
        String head = local.length() <= 1 ? local : local.substring(0, 1);
        return head + "***" + domain;
    }

    private void deleteExistingCodes(Connection conn, Double userId) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM spark.spr_process_requests WHERE prq_type = ? AND prq_reference_id = ?")) {
            ps.setString(1, NtisProcessRequestType.LOGIN_2FA.getCode());
            ps.setDouble(2, userId);
            ps.executeUpdate();
        }
    }

    // Grąžina aktyvaus (neišsibaigusio) iššūkio išdavimo laiką, susieto su konkrečiu handle; null jei tokio nėra.
    private Timestamp getActiveChallengeIssuedAt(Connection conn, Double userId, String handle) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT prq_date_from FROM spark.spr_process_requests WHERE prq_type = ? AND prq_reference_id = ? "
                        + "AND c01 = ? AND (prq_date_to IS NULL OR prq_date_to > now()) ORDER BY prq_id DESC LIMIT 1")) {
            ps.setString(1, NtisProcessRequestType.LOGIN_2FA.getCode());
            ps.setDouble(2, userId);
            ps.setString(3, handle);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getTimestamp(1) : null;
            }
        }
    }

    private void deleteById(Connection conn, long prqId) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM spark.spr_process_requests WHERE prq_id = ?")) {
            ps.setLong(1, prqId);
            ps.executeUpdate();
        }
    }

    private void incrementRowAttempts(Connection conn, long prqId) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE spark.spr_process_requests SET n01 = coalesce(n01,0)+1 WHERE prq_id = ?")) {
            ps.setLong(1, prqId);
            ps.executeUpdate();
        }
    }

    private int parseIntProp(String name, int def) {
        try {
            return Integer.parseInt(dbPropertyManager.getPropertyByName(name, String.valueOf(def)).trim());
        } catch (Exception e) {
            return def;
        }
    }

    private String generateNumericCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    private String generateHandle() {
        byte[] bytes = new byte[16];
        RANDOM.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }
}
