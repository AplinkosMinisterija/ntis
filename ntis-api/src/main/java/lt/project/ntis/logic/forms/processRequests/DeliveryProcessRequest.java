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
import lt.project.ntis.job.request.delivery.DeliveryConfirmedEmailJobRequest;
import lt.project.ntis.job.request.delivery.DeliveryCancelledEmailJobRequest;
import lt.project.ntis.job.request.delivery.DeliveryRejectedEmailJobRequest;
import lt.project.ntis.job.request.delivery.DeliverySubmittedEmailJobRequest;

@Component
public class DeliveryProcessRequest {

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String EMAIL_LINK_EXPIRATION_MINUTES = "EMAIL_LINK_EXPIRATION_MINUTES";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    DeliverySubmittedEmailJobRequest deliverySubmittedEmailJobRequest;

    @Autowired
    DeliveryRejectedEmailJobRequest deliveryRejectedEmailJobRequest;

    @Autowired
    DeliveryConfirmedEmailJobRequest deliveryConfirmedEmailJobRequest;
    
    @Autowired
    DeliveryCancelledEmailJobRequest deliveryCancelledEmailJobRequest;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${app.host:#{null}}")
    private String appHost;

    public void createDeliverySubmittedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.DELIVERY_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(DeliverySubmittedEmailJobRequest.HOME_URL, appHost);
        params.put(DeliverySubmittedEmailJobRequest.DELIVERY_URL, generatedString);
        params.put(DeliverySubmittedEmailJobRequest.SERVICE_PROVIDER, context.get(DeliverySubmittedEmailJobRequest.SERVICE_PROVIDER).toString());
        params.put(DeliverySubmittedEmailJobRequest.DELIVERY_ID, context.get(DeliverySubmittedEmailJobRequest.DELIVERY_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = deliverySubmittedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createDeliveryRejectedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.DELIVERY_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(DeliveryRejectedEmailJobRequest.HOME_URL, appHost);
        params.put(DeliveryRejectedEmailJobRequest.DELIVERY_URL, generatedString);
        params.put(DeliveryRejectedEmailJobRequest.DELIVERY_ID, context.get(DeliveryRejectedEmailJobRequest.DELIVERY_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = deliveryRejectedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createDeliveryConfirmedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.DELIVERY_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(DeliveryConfirmedEmailJobRequest.HOME_URL, appHost);
        params.put(DeliveryConfirmedEmailJobRequest.DELIVERY_URL, generatedString);
        params.put(DeliveryConfirmedEmailJobRequest.DELIVERY_ID, context.get(DeliveryConfirmedEmailJobRequest.DELIVERY_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = deliveryConfirmedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
    
    public void createDeliveryCancelledRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.DELIVERY_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<>();
        params.put(DeliveryCancelledEmailJobRequest.HOME_URL, appHost);
        params.put(DeliveryCancelledEmailJobRequest.DELIVERY_URL, generatedString);
        params.put(DeliveryCancelledEmailJobRequest.DELIVERY_ID, context.get(DeliveryCancelledEmailJobRequest.DELIVERY_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = deliveryCancelledEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
