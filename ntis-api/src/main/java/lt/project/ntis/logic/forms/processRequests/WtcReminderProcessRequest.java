package lt.project.ntis.logic.forms.processRequests;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.job.executor.impl.ExecuteEmailSendTask;
import eu.itreegroup.spark.app.scheduler.ExecutorJob;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.YesNo;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.job.request.delivery.DeliveryRejectedEmailJobRequest;
import lt.project.ntis.job.request.reminder.WtcReminderRequest;

@Component
public class WtcReminderProcessRequest {

    public static final String DEFAULT_EXPIRATION_MINUTES = "30";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    WtcReminderRequest wtcReminderRequest;

    public void createReminderRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.SRV_REQ_REQUEST, YesNo.Y, referenceId, lang, new Date(),
                Long.parseLong(DEFAULT_EXPIRATION_MINUTES));

        HashMap<String, String> params = new HashMap<>();
        params.put(DeliveryRejectedEmailJobRequest.DELIVERY_URL, generatedString);
        if (email != null && !email.isBlank()) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }

        Double requestId = wtcReminderRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
