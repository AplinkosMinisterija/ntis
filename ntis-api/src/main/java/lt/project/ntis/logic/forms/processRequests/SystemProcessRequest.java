package lt.project.ntis.logic.forms.processRequests;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.enums.Languages;
import lt.project.ntis.job.request.system.RestrictedAccessEmailJobRequest;

@Component
public class SystemProcessRequest {

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    RestrictedAccessEmailJobRequest restrictedAccessEmailJobRequest;

    public void createRestrictedAccessRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = restrictedAccessEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
