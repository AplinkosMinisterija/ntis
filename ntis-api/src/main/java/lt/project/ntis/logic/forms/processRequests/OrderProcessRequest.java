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
import lt.project.ntis.job.request.order.OrderCancelledEmailJobRequest;
import lt.project.ntis.job.request.order.OrderConfirmedEmailJobRequest;
import lt.project.ntis.job.request.order.OrderFinishedEmailJobRequest;
import lt.project.ntis.job.request.order.OrderRegisteredEmailJobRequest;
import lt.project.ntis.job.request.order.OrderRejectedEmailJobRequest;
import lt.project.ntis.job.request.order.OrderSubmittedEmailJobRequest;
import lt.project.ntis.job.request.order.OrderUpdatedEmailJobRequest;

@Component
public class OrderProcessRequest {

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String EMAIL_LINK_EXPIRATION_MINUTES = "EMAIL_LINK_EXPIRATION_MINUTES";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    OrderSubmittedEmailJobRequest orderSubmittedEmailJobRequest;

    @Autowired
    OrderCancelledEmailJobRequest orderCancelledEmailJobRequest;

    @Autowired
    OrderFinishedEmailJobRequest orderFinishedEmailJobRequest;

    @Autowired
    OrderUpdatedEmailJobRequest orderUpdatedEmailJobRequest;

    @Autowired
    OrderRejectedEmailJobRequest orderRejectedEmailJobRequest;

    @Autowired
    OrderConfirmedEmailJobRequest orderConfirmedEmailJobRequest;

    @Autowired
    OrderRegisteredEmailJobRequest orderRegisteredEmailJobRequest;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${app.host:#{null}}")
    private String appHost;

    public void createOrderSubmittedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderSubmittedEmailJobRequest.HOME_URL, appHost);
        params.put(OrderSubmittedEmailJobRequest.ORDER_URL, context.get(OrderSubmittedEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderSubmittedEmailJobRequest.SERVICE_TYPE, context.get(OrderSubmittedEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderSubmittedEmailJobRequest.ORDER_ID, context.get(OrderSubmittedEmailJobRequest.ORDER_ID).toString());
        params.put(OrderSubmittedEmailJobRequest.ORDER_DATE, context.get(OrderSubmittedEmailJobRequest.ORDER_DATE).toString());
        params.put(OrderSubmittedEmailJobRequest.CLIENT_NAME, context.get(OrderSubmittedEmailJobRequest.CLIENT_NAME).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderSubmittedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createOrderCancelledRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderCancelledEmailJobRequest.HOME_URL, appHost);
        params.put(OrderCancelledEmailJobRequest.ORDER_URL, context.get(OrderCancelledEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderCancelledEmailJobRequest.SERVICE_TYPE, context.get(OrderCancelledEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderCancelledEmailJobRequest.ORDER_ID, context.get(OrderCancelledEmailJobRequest.ORDER_ID).toString());
        params.put(OrderCancelledEmailJobRequest.ORDER_CANCEL_DATE, context.get(OrderCancelledEmailJobRequest.ORDER_CANCEL_DATE).toString());
        params.put(OrderCancelledEmailJobRequest.CLIENT_NAME, context.get(OrderCancelledEmailJobRequest.CLIENT_NAME).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderCancelledEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createOrderFinishedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderFinishedEmailJobRequest.HOME_URL, appHost);
        params.put(OrderFinishedEmailJobRequest.ORDER_URL, context.get(OrderFinishedEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderFinishedEmailJobRequest.SERVICE_TYPE, context.get(OrderFinishedEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderFinishedEmailJobRequest.ORDER_ID, context.get(OrderFinishedEmailJobRequest.ORDER_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderFinishedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createOrderUpdatedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderUpdatedEmailJobRequest.HOME_URL, appHost);
        params.put(OrderUpdatedEmailJobRequest.ORDER_URL, context.get(OrderUpdatedEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderUpdatedEmailJobRequest.SERVICE_TYPE, context.get(OrderUpdatedEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderUpdatedEmailJobRequest.ORDER_ID, context.get(OrderUpdatedEmailJobRequest.ORDER_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderUpdatedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createOrderRejectedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderRejectedEmailJobRequest.HOME_URL, appHost);
        params.put(OrderRejectedEmailJobRequest.ORDER_URL, context.get(OrderRejectedEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderRejectedEmailJobRequest.SERVICE_TYPE, context.get(OrderRejectedEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderRejectedEmailJobRequest.ORDER_ID, context.get(OrderRejectedEmailJobRequest.ORDER_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderRejectedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createOrderConfirmedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderConfirmedEmailJobRequest.HOME_URL, appHost);
        params.put(OrderConfirmedEmailJobRequest.ORDER_URL, context.get(OrderConfirmedEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderConfirmedEmailJobRequest.SERVICE_TYPE, context.get(OrderConfirmedEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderConfirmedEmailJobRequest.ORDER_ID, context.get(OrderConfirmedEmailJobRequest.ORDER_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderConfirmedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createOrderRegisteredRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.ORDER_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(OrderRegisteredEmailJobRequest.HOME_URL, appHost);
        params.put(OrderRegisteredEmailJobRequest.ORDER_URL,
                context.get(OrderRegisteredEmailJobRequest.ORDER_URL).toString() + "email?token=" + generatedString);
        params.put(OrderRegisteredEmailJobRequest.SERVICE_TYPE, context.get(OrderRegisteredEmailJobRequest.SERVICE_TYPE).toString());
        params.put(OrderRegisteredEmailJobRequest.ORDER_ID, context.get(OrderRegisteredEmailJobRequest.ORDER_ID).toString());
        params.put(OrderRegisteredEmailJobRequest.ORDER_DATE, context.get(OrderRegisteredEmailJobRequest.ORDER_DATE).toString());
        params.put(OrderRegisteredEmailJobRequest.SERVICE_PROVIDER, context.get(OrderRegisteredEmailJobRequest.SERVICE_PROVIDER).toString());
        params.put(OrderRegisteredEmailJobRequest.WTF_ADDRESS, context.get(OrderRegisteredEmailJobRequest.WTF_ADDRESS).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = orderRegisteredEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
