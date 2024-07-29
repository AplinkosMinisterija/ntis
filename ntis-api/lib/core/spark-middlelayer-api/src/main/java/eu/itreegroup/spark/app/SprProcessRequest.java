package eu.itreegroup.spark.app;

import java.sql.Connection;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.job.request.impl.EmailVerificationJobRequest;
import eu.itreegroup.spark.app.job.request.impl.NewUserInformJobRequest;
import eu.itreegroup.spark.app.job.request.impl.PasswordChangeJobRequest;
import eu.itreegroup.spark.app.job.request.impl.PasswordCreateJobRequest;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.ProcessRequestType;
import eu.itreegroup.spark.enums.SAPProcessRequestType;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprProcessRequestsDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@Component
public class SprProcessRequest {

    private static final int REQ_CODE_DEFAULT_LENGTH = 50;

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String CHANGE_PSW_REQ_EXPIRATION = "CHANGE_PSW_REQ_EXPIRATION";

    public static String NEW_USER_REQ_EXPIRATION = "NEW_USER_REQ_EXPIRATION";

    public static String CHECK_EMAIL_REQ_EXPIRATION = "CHECK_EMAIL_REQ_EXPIRATION";

    @Value("${req.code.length:#{null}}")
    private String reqCodeLength;

    @Autowired
    public SprProcessRequestsDBService sprProcessRequestsDBService;

    @Autowired
    PasswordChangeJobRequest passwordChangePrintrequest;

    @Autowired
    PasswordCreateJobRequest passwordCreateJobRequest;

    @Autowired
    NewUserInformJobRequest newUserInformJobRequest;

    @Autowired
    EmailVerificationJobRequest emailVerificationJobRequest;

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${app.host:#{null}}")
    private String appHost;

    private int getCodeLength() {
        if (reqCodeLength != null) {
            return Integer.parseInt(reqCodeLength);
        } else {
            return REQ_CODE_DEFAULT_LENGTH;
        }
    }

    public String createRequest(Connection conn, ProcessRequestType type, YesNo initiatedBySystem, Double requestReferenceId, Languages lang, Date startDate,
            long validPeriodInMinutes, String generatedString, String c01) throws Exception {
        SprProcessRequestsDAO request = sprProcessRequestsDBService.newRecord();
        request.setPrq_type(type.getCode());
        request.setPrq_reference_id(requestReferenceId);
        request.setPrq_lang(lang.getCode());
        request.setPrq_initiated_by_system(initiatedBySystem.getCode());
        request.setPrq_token(generatedString);
        request.setPrq_date_from(startDate);
        request.setC01(c01);
        LocalDateTime endDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                .plus(Duration.of(validPeriodInMinutes, ChronoUnit.MINUTES));
        request.setPrq_date_to(java.util.Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant()));
        sprProcessRequestsDBService.saveRecord(conn, request);
        return generatedString;

    }

    public String createRequest(Connection conn, ProcessRequestType type, YesNo initiatedBySystem, Double requestReferenceId, Languages lang, Date startDate,
            long validPeriodInMinutes, String c01) throws Exception {
        String generatedString = RandomStringUtils.random(getCodeLength(), true, false);
        return createRequest(conn, type, initiatedBySystem, requestReferenceId, lang, startDate, validPeriodInMinutes, generatedString, c01);

    }

    public String createRequest(Connection conn, ProcessRequestType type, YesNo initiatedBySystem, Double requestReferenceId, Languages lang, Date startDate,
            long validPeriodInMinutes) throws Exception {
        return this.createRequest(conn, type, initiatedBySystem, requestReferenceId, lang, startDate, validPeriodInMinutes, null);
    }

    public SprProcessRequestsDAO getIdetifierByToken(Connection conn, ProcessRequestType requestType, String token, Boolean deleteOnLoad) throws Exception {
        SprProcessRequestsDAO requestDAO = sprProcessRequestsDBService.loadByToken(conn, requestType, token);
        if (requestDAO != null) {
            if (deleteOnLoad) {
                sprProcessRequestsDBService.deleteRecord(conn, requestDAO.getPrq_id());
            }
        } else {
            throw new SparkBusinessException(new S2Message("common.error.linkExpired", SparkMessageType.ERROR, "The link has expired"));
        }
        return requestDAO;
    }

    public void createPasswordChangeRequest(Connection conn, Double userId, String email, Languages lang, Map<String, Object> context) throws Exception {
        String generatedString = createRequest(conn, SAPProcessRequestType.CHANGE_PASSWORD_REQUEST, YesNo.N, userId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(CHANGE_PSW_REQ_EXPIRATION, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(PasswordChangeJobRequest.HOME_URL, appHost);
        params.put(PasswordChangeJobRequest.PASSWORD_RESET_URL, generatedString);
        params.put(PasswordChangeJobRequest.USER_NAME, context.get(PasswordChangeJobRequest.USER_NAME).toString());
        params.put(ExecuteEmailSendTask.RECEIVER, email);
        Double requestId = passwordChangePrintrequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createPasswordCreateRequest(Connection conn, Double userId, String email, Languages lang, Map<String, Object> context) throws Exception {
        String generatedString = createRequest(conn, SAPProcessRequestType.CREATE_PASSWORD_REQUEST, YesNo.N, userId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(CHANGE_PSW_REQ_EXPIRATION, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(PasswordCreateJobRequest.HOME_URL, appHost);
        params.put(PasswordCreateJobRequest.PASSWORD_CREATE_URL, generatedString);
        params.put(PasswordCreateJobRequest.USER_NAME, context.get(PasswordCreateJobRequest.USER_NAME).toString());
        params.put(ExecuteEmailSendTask.RECEIVER, email);
        Double requestId = passwordCreateJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createNewUserInformRequest(Connection conn, Double userId, String email, Languages lang, Map<String, Object> context) throws Exception {
        String generatedString = createRequest(conn, SAPProcessRequestType.NEW_USER_REQUEST, YesNo.N, userId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(NEW_USER_REQ_EXPIRATION, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NewUserInformJobRequest.HOME_URL, appHost);
        params.put(NewUserInformJobRequest.PASSWORD_RESET_URL, generatedString);
        params.put(NewUserInformJobRequest.USER_NAME, context.get(NewUserInformJobRequest.USER_NAME).toString());
        params.put(ExecuteEmailSendTask.RECEIVER, email);
        Double requestId = newUserInformJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createCheckEmailRequest(Connection conn, Double userId, String email, Languages lang) throws Exception {
        String generatedString = createRequest(conn, SAPProcessRequestType.CHECK_EMAIL_REQUEST, YesNo.N, userId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(CHECK_EMAIL_REQ_EXPIRATION, DEFAULT_EXPIRATION_MINUTES)), email);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(EmailVerificationJobRequest.HOME_URL, appHost);
        params.put(EmailVerificationJobRequest.EMAIL_VERIFICATION_URL, generatedString);
        params.put(ExecuteEmailSendTask.RECEIVER, email);
        Double requestId = emailVerificationJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
