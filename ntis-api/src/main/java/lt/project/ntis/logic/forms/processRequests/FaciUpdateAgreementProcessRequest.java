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
import lt.project.ntis.job.request.faciUpdateAgreement.FuaStatusChangeEmailJobRequest;

@Component
public class FaciUpdateAgreementProcessRequest {

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String EMAIL_LINK_EXPIRATION_MINUTES = "EMAIL_LINK_EXPIRATION_MINUTES";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    FuaStatusChangeEmailJobRequest fuaStatusChangeEmailJobRequest;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${app.host:#{null}}")
    private String appHost;

    public void createFuaStatusChangeRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.FACI_UPDATE_AGREEMENT_REQUEST, YesNo.N, referenceId, lang,
                new Date(), Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(FuaStatusChangeEmailJobRequest.HOME_URL, appHost);
        params.put(FuaStatusChangeEmailJobRequest.FUA_URL, context.get(FuaStatusChangeEmailJobRequest.FUA_URL).toString() + "/email?token=" + generatedString);
        params.put(FuaStatusChangeEmailJobRequest.FUA_STATE, context.get(FuaStatusChangeEmailJobRequest.FUA_STATE).toString());
        params.put(FuaStatusChangeEmailJobRequest.FUA_DATE, context.get(FuaStatusChangeEmailJobRequest.FUA_DATE).toString());
        params.put(FuaStatusChangeEmailJobRequest.FUA_ID, context.get(FuaStatusChangeEmailJobRequest.FUA_ID).toString());
        params.put(FuaStatusChangeEmailJobRequest.REVIEWER, context.get(FuaStatusChangeEmailJobRequest.REVIEWER).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = fuaStatusChangeEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
