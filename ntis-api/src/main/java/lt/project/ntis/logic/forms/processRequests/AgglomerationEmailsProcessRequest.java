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
import lt.project.ntis.job.request.agglomerations.AggloConfirmedEmailJobRequest;
import lt.project.ntis.job.request.agglomerations.AggloRejectedEmailJobRequest;
import lt.project.ntis.job.request.agglomerations.AggloUploadedEmailJobRequest;

@Component
public class AgglomerationEmailsProcessRequest {

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String EMAIL_LINK_EXPIRATION_MINUTES = "EMAIL_LINK_EXPIRATION_MINUTES";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Autowired
    AggloConfirmedEmailJobRequest aggloConfirmedEmailJobRequest;

    @Autowired
    AggloUploadedEmailJobRequest aggloUploadedEmailJobRequest;

    @Autowired
    AggloRejectedEmailJobRequest aggloRejectedEmailJobRequest;

    @Value("${app.host:#{null}}")
    private String appHost;

    public void createAgglomerationUploadedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.AGGLOMERATION_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AggloUploadedEmailJobRequest.HOME_URL, appHost);
        params.put(AggloUploadedEmailJobRequest.AGGLO_URL, generatedString);
        params.put(AggloUploadedEmailJobRequest.AGG_ID, context.get(AggloUploadedEmailJobRequest.AGG_ID).toString());
        params.put(AggloUploadedEmailJobRequest.MUNICIPALITY, context.get(AggloUploadedEmailJobRequest.MUNICIPALITY).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = aggloUploadedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createAgglomerationConfirmedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.AGGLOMERATION_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AggloConfirmedEmailJobRequest.HOME_URL, appHost);
        params.put(AggloConfirmedEmailJobRequest.AGGLO_URL, generatedString);
        params.put(AggloConfirmedEmailJobRequest.AGG_ID, context.get(AggloUploadedEmailJobRequest.AGG_ID).toString());
        params.put(AggloConfirmedEmailJobRequest.MUNICIPALITY, context.get(AggloConfirmedEmailJobRequest.MUNICIPALITY).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = aggloConfirmedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createAgglomerationRejectedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.AGGLOMERATION_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(AggloRejectedEmailJobRequest.HOME_URL, appHost);
        params.put(AggloRejectedEmailJobRequest.AGGLO_URL, generatedString);
        params.put(AggloRejectedEmailJobRequest.AGG_ID, context.get(AggloUploadedEmailJobRequest.AGG_ID).toString());
        params.put(AggloRejectedEmailJobRequest.MUNICIPALITY, context.get(AggloRejectedEmailJobRequest.MUNICIPALITY).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = aggloRejectedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

}
