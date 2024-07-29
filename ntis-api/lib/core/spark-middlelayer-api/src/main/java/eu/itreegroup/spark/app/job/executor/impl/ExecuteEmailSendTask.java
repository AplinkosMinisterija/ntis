package eu.itreegroup.spark.app.job.executor.impl;

import java.io.StringWriter;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.app.model.MailTemplateStructure;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Service("EMAIL_EXECUTOR")
public class ExecuteEmailSendTask extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(ExecuteEmailSendTask.class);

    public static final String PROD_STATUS = "PROD";

    public static final String SUBJECT = "SUBJECT";

    public static final String BODY = "BODY";

    public static final String RECEIVER = "RECEIVER";

    @Value("${app.host}")
    private String appHostUrl;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private String mailPort;

    @Value("${mail.username}")
    private String mailUser;

    @Value("${mail.password}")
    private String mailUserPassword;

    @Value("${mail.sender}")
    private String emailSender;

    @Value("${mail.template}")
    private String mailTemplate;

    @Value("${mail.logo}")
    private String mailLogo;

    @Value("${mail.testRecipientList:#{null}}")
    private String testRecipientList;

    @Value("${mail.testRecipientDefault:#{null}}")
    private String testRecipientDefault;

    @Value("${mail.testIgnoreRecipientList:#{null}}")
    private String testIgnoreRecipientList;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    private MailTemplateStructure defaultMailStructure;

    private HashMap<String, MailTemplateStructure> templatesOfMailStructure;

    private ClassPathResource logoFile;

    private JavaMailSenderImpl javaMailSenderImpl;

    private MailTemplateStructure loadMailTemplate(Connection conn, String templateCode, String lang) {
        MailTemplateStructure structure = new MailTemplateStructure();
        try {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("select tt.tmt_code, " //
                    + "               tt.tmt_text " //
                    + "          from spr_templates t " //
                    + "    inner join spr_template_texts tt on t.tml_id = tt.tmt_tml_id ");
            stmt.addParam4WherePart(" t.tml_code = ? ", templateCode);
            stmt.addParam4WherePart(" t.tml_status = ? ", PROD_STATUS);
            stmt.addParam4WherePart(" tt.tmt_lang = ? ", lang);
            List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
            for (int i = 0; i < data.size(); i++) {
                HashMap<String, String> rec = data.get(i);
                if (SUBJECT.equals(rec.get("tmt_code"))) {
                    structure.setSubject(rec.get("tmt_text"));
                } else {
                    if (BODY.equals(rec.get("tmt_code"))) {
                        structure.setBody(rec.get("tmt_text"));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error while trying to extract template by name: " + mailTemplate, ex);
        }
        return structure;
    }

    @EventListener({ ContextRefreshedEvent.class })
    public void initMailServer() throws Exception {
        setJavaMailSenderImpl(setupService());
    }

    private MailTemplateStructure getMailStructure(Connection conn, String lang) {
        if (templatesOfMailStructure == null) {
            templatesOfMailStructure = new HashMap<String, MailTemplateStructure>();
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("select distinct tt.tmt_lang " //
                    + "          from spr_templates t " //
                    + "    inner join spr_template_texts tt on t.tml_id = tt.tmt_tml_id ");
            stmt.addParam4WherePart(" t.tml_code = ? ", mailTemplate);
            stmt.addParam4WherePart(" t.tml_status = ? ", PROD_STATUS);
            try {
                List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
                for (int i = 0; i < data.size(); i++) {
                    String langCode = data.get(i).get("tmt_lang");
                    defaultMailStructure = loadMailTemplate(conn, mailTemplate, langCode);
                    templatesOfMailStructure.put(langCode, defaultMailStructure);
                }
            } catch (Exception ex) {
                log.error("Error while trying to load mail templates, [" + mailTemplate + "]", ex);
            }
        }
        return templatesOfMailStructure.getOrDefault(lang, defaultMailStructure);
    }

    private JavaMailSenderImpl setupService() throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(Long.valueOf(mailPort).intValue());
        mailSender.setUsername(mailUser);
        mailSender.setPassword(mailUserPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", log.isDebugEnabled() ? "true" : "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        setlogoFile(mailLogo);
        return mailSender;

    }

    private void setlogoFile(String logoName) throws Exception {
        logoFile = new ClassPathResource("email/" + logoName, this.getClass().getClassLoader());
    }

    private JavaMailSenderImpl getJavaMailSenderImpl() throws Exception {
        if (javaMailSenderImpl == null) {
            initMailServer();
        }
        return javaMailSenderImpl;
    }

    private void setJavaMailSenderImpl(JavaMailSenderImpl javaMailSenderImpl) {
        this.javaMailSenderImpl = javaMailSenderImpl;
    }

    /**
     * Function will check if provided email is defined in property by name mail.testRecipientList. Ins case if not defined
     * then function will return email that is defined in mail.testRecipientDefault. This validation will be performed only 
     * when property mail.testRecipientList is defined.
     * @param recipientEmail - email address that should be validated.
     * @return - validated email address. 
     */
    private String checkMailRecipient(String recipientEmail) {
        if (testRecipientList != null) {
            List<String> availableRecipientList = Arrays.asList(testRecipientList.split("[\\s,;]+"));
            log.debug("availableRecipientList: {}", availableRecipientList);
            for (String rec : availableRecipientList) {
                log.debug("recipientEmail: {} rec: {}", recipientEmail, rec);
                if (recipientEmail.matches(rec)) {
                    return recipientEmail;
                }
            }
            log.debug("Email : {} do not match to the pattern: {} default email will be used {}", recipientEmail, availableRecipientList, testRecipientDefault);
            return testRecipientDefault;
        }
        return recipientEmail;
    }

    public void sendMail(Connection conn, Double requestId) throws Exception {
        List<HashMap<String, String>> data = retrieveEmailData(conn, requestId);
        if (data == null || data.isEmpty()) {
            log.warn("Retrieved no data for email request {} - nothing to send.", requestId);
            return;
        }

        String mailBody = null;
        String mailSubject = null;
        HashMap<String, String> firstRecord = data.get(0);
        MailTemplateStructure mailStructure = getMailStructure(conn, firstRecord.get("jrq_lang"));
        if (firstRecord.get("tml_code") != null) {
            MailTemplateStructure mailStructureNew = loadMailTemplate(conn, firstRecord.get("tml_code"), firstRecord.get("jrq_lang"));
            VelocityContext context = new VelocityContext();
            context.put("emailMessage", mailStructureNew.getBody());
            StringWriter w = new StringWriter();
            Velocity.evaluate(context, w, "emailTemplateReplacement", mailStructure.getBody());
            mailBody = w.toString();
            mailSubject = mailStructureNew.getSubject() != null ? mailStructureNew.getSubject() : mailStructure.getSubject();
        } else {
            mailBody = mailStructure.getBody();
            mailSubject = mailStructure.getSubject();
        }

        String receiversEmail = null;
        VelocityContext emailContext = new VelocityContext();
        for (HashMap<String, String> rec : data) {
            if (rec.get("jra_name") != null) {
                String paramName = rec.get("jra_name").replaceAll("\\s+", "");
                emailContext.put(paramName, rec.get("jra_value"));
                if (RECEIVER.equals(paramName)) {
                    receiversEmail = rec.get("jra_value");
                }
            }
        }

        if (emailContext.getKeys().length > 0) {
            StringWriter w = new StringWriter();
            Velocity.evaluate(emailContext, w, "emailTemplate", mailBody);
            mailBody = w.toString();
        }

        buildAndSendMessage(mailSubject, mailBody, receiversEmail);
    }

    private List<HashMap<String, String>> retrieveEmailData(Connection conn, Double requestId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" select r.jrq_lang, " //
                + "        t.tml_code, " //
                + "        ra.jra_name, " //
                + "        ra.jra_value " //
                + "   from spr_job_requests r" //
                + " inner join spr_job_definitions rt on r.jrq_jde_id = rt.jde_id " //
                + " left join spr_templates t on t.tml_id = rt.jde_tml_id " //
                + " left join spr_job_request_args ra on r.jrq_id = ra.jra_jrq_id ");
        stmt.addParam4WherePart(" r.jrq_id = ? ", requestId);
        return baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
    }

    private void buildAndSendMessage(String mailSubject, String mailBody, String receiversEmail) throws Exception {
        // testIgnoreRecipientList property was introduced to avoid mass sending of emails while doing performance tests.
        // To make performance tests as close to actual loads as conveniently possible, all steps of email creation are preserved
        // even if we know that created message will not be sent.
        boolean ignoreRecipient = isIgnoreRecipient(receiversEmail);

        MimeMessage mimeMessage = getJavaMailSenderImpl().createMimeMessage();
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(ignoreRecipient ? receiversEmail : checkMailRecipient(receiversEmail)));
        mimeMessage.setFrom(new InternetAddress(emailSender));
        mimeMessage.setSubject(mailSubject);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setText(mailBody, true);

        helper.addInline("webAppLogo", logoFile);

        if (ignoreRecipient) {
            log.info("Receiver email {} is in ntis-api.properties 'mail.testIgnoreRecipientList' list {} - we are not sending to these addresses.",
                    receiversEmail, testIgnoreRecipientList);
        } else {
            getJavaMailSenderImpl().send(mimeMessage);
        }
    }

    private boolean isIgnoreRecipient(String recipientEmail) {
        if (testIgnoreRecipientList != null) {
            List<String> jmeterRecipientList = Arrays.asList(testIgnoreRecipientList.split("[\\s,;]+"));
            for (String jmeterRecipient : jmeterRecipientList) {
                if (recipientEmail.matches(jmeterRecipient)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request)
            throws InterruptedException, SparkBusinessException, Exception {
        sendMail(conn, request.getJrq_id());
    }
}
