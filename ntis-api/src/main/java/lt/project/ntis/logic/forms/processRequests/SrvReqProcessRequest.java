package lt.project.ntis.logic.forms.processRequests;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.job.request.serviceRequest.SrvReqConfirmedEmailJobRequest;
import lt.project.ntis.job.request.serviceRequest.SrvReqRejectedEmailJobRequest;
import lt.project.ntis.job.request.serviceRequest.SrvReqSubmittedEmailJobRequest;
import lt.project.ntis.job.request.serviceRequest.SrvReqSubmittedSysEmailJobRequest;

@Component
public class SrvReqProcessRequest {

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String EMAIL_LINK_EXPIRATION_MINUTES = "EMAIL_LINK_EXPIRATION_MINUTES";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    SrvReqSubmittedEmailJobRequest srvReqSubmittedEmailJobRequest;

    @Autowired
    SrvReqSubmittedSysEmailJobRequest srvReqSubmittedSysEmailJobRequest;

    @Autowired
    SrvReqConfirmedEmailJobRequest srvReqConfirmedEmailJobRequest;

    @Autowired
    SrvReqRejectedEmailJobRequest srvReqRejectedEmailJobRequest;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${app.host:#{null}}")
    private String appHost;

    public void createSrvReqSubmittedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.SRV_REQ_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(SrvReqSubmittedEmailJobRequest.HOME_URL, appHost);
        params.put(SrvReqSubmittedEmailJobRequest.REQUEST_URL, context.get(SrvReqSubmittedEmailJobRequest.REQUEST_URL).toString() + generatedString);
        params.put(SrvReqSubmittedEmailJobRequest.REQUEST_ID, context.get(SrvReqSubmittedEmailJobRequest.REQUEST_ID).toString());
        params.put(SrvReqSubmittedEmailJobRequest.SERVICE_TYPE, context.get(SrvReqSubmittedEmailJobRequest.SERVICE_TYPE).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = srvReqSubmittedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createSrvReqSubmittedSysRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.SRV_REQ_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(SrvReqSubmittedSysEmailJobRequest.HOME_URL, appHost);
        params.put(SrvReqSubmittedSysEmailJobRequest.REQUEST_URL, context.get(SrvReqSubmittedSysEmailJobRequest.REQUEST_URL).toString() + generatedString);
        params.put(SrvReqSubmittedSysEmailJobRequest.REQUEST_ID, context.get(SrvReqSubmittedSysEmailJobRequest.REQUEST_ID).toString());
        params.put(SrvReqSubmittedSysEmailJobRequest.SERVICE_TYPE, context.get(SrvReqSubmittedSysEmailJobRequest.SERVICE_TYPE).toString());
        params.put(SrvReqSubmittedSysEmailJobRequest.SERVICE_ORG, context.get(SrvReqSubmittedSysEmailJobRequest.SERVICE_ORG).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = srvReqSubmittedSysEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createSrvReqConfirmedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.SRV_REQ_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(SrvReqConfirmedEmailJobRequest.HOME_URL, appHost);
        params.put(SrvReqConfirmedEmailJobRequest.REQUEST_URL, context.get(SrvReqConfirmedEmailJobRequest.REQUEST_URL).toString() + generatedString);
        params.put(SrvReqConfirmedEmailJobRequest.REQUEST_ID, context.get(SrvReqConfirmedEmailJobRequest.REQUEST_ID).toString());
        params.put(SrvReqConfirmedEmailJobRequest.SERVICE_TYPE, context.get(SrvReqConfirmedEmailJobRequest.SERVICE_TYPE).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = srvReqConfirmedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createSrvReqRejectedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.SRV_REQ_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(SrvReqRejectedEmailJobRequest.HOME_URL, appHost);
        params.put(SrvReqRejectedEmailJobRequest.REQUEST_URL, context.get(SrvReqRejectedEmailJobRequest.REQUEST_URL).toString() + generatedString);
        params.put(SrvReqRejectedEmailJobRequest.REQUEST_ID, context.get(SrvReqRejectedEmailJobRequest.REQUEST_ID).toString());
        params.put(SrvReqRejectedEmailJobRequest.SERVICE_TYPE, context.get(SrvReqRejectedEmailJobRequest.SERVICE_TYPE).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = srvReqRejectedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
