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
import eu.itreegroup.spark.modules.admin.service.SprProcessRequestsDBService;
import lt.project.ntis.app.job.request.contract.impl.ContractCancellationInitiatedEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractCommentedByOwnerEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractCommentedByProviderEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractExpirationEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractRejectedByOwnerEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractRejectedByProviderEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractRequestToCancelConfirmedEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractRequestToCancelEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractRequestedByOwnerEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractSignedByOwnerEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractSignedByProviderEmailJobRequest;
import lt.project.ntis.app.job.request.contract.impl.ContractUploadedByOwnerEmailJobRequest;
import lt.project.ntis.enums.NtisProcessRequestTypes;

@Component
public class ContractProcessRequest {

    public static String DEFAULT_EXPIRATION_MINUTES = "10";

    public static String EMAIL_LINK_EXPIRATION_MINUTES = "EMAIL_LINK_EXPIRATION_MINUTES";

    @Autowired
    ExecutorJob executerJob;

    @Autowired
    ContractCancellationInitiatedEmailJobRequest contractCancellationInitiatedEmailJobRequest;

    @Autowired
    ContractRequestToCancelConfirmedEmailJobRequest contractRequestToCancelConfirmedEmailJobRequest;

    @Autowired
    ContractRequestToCancelEmailJobRequest contractRequestToCancelEmailJobRequest;

    @Autowired
    ContractRequestedByOwnerEmailJobRequest contractRequestedByOwnerEmailJobRequest;

    @Autowired
    ContractRejectedByProviderEmailJobRequest contractRejectedByProviderEmailJobRequest;

    @Autowired
    ContractSignedByProviderEmailJobRequest contractSignedByProviderEmailJobRequest;

    @Autowired
    ContractSignedByOwnerEmailJobRequest contractSignedByOwnerEmailJobRequest;

    @Autowired
    ContractRejectedByOwnerEmailJobRequest contractRejectedByOwnerEmailJobRequest;

    @Autowired
    ContractCommentedByOwnerEmailJobRequest contractCommentedByOwnerEmailJobRequest;

    @Autowired
    ContractCommentedByProviderEmailJobRequest contractCommentedByProviderEmailJobRequest;

    @Autowired
    ContractExpirationEmailJobRequest contractExpirationEmailJobRequest;

    @Autowired
    ContractUploadedByOwnerEmailJobRequest contractUploadedByOwnerEmailJobRequest;

    @Autowired
    SprProcessRequest processRequest;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @Value("${app.host:#{null}}")
    private String appHost;

    public void createContractCancellationInitiatedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractCancellationInitiatedEmailJobRequest.HOME_URL, appHost);
        params.put(ContractCancellationInitiatedEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractCancellationInitiatedEmailJobRequest.CONTRACT_ID, context.get(ContractCancellationInitiatedEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractCancellationInitiatedEmailJobRequest.SRV_PROVIDER,
                context.get(ContractCancellationInitiatedEmailJobRequest.SRV_PROVIDER).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractCancellationInitiatedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractRequestToCancelConfirmedRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractRequestToCancelConfirmedEmailJobRequest.HOME_URL, appHost);
        params.put(ContractRequestToCancelConfirmedEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractRequestToCancelConfirmedEmailJobRequest.CONTRACT_ID,
                context.get(ContractRequestToCancelConfirmedEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractRequestToCancelConfirmedEmailJobRequest.SRV_PROVIDER,
                context.get(ContractRequestToCancelConfirmedEmailJobRequest.SRV_PROVIDER).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractRequestToCancelConfirmedEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractRequestToCancelRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractRequestToCancelEmailJobRequest.HOME_URL, appHost);
        params.put(ContractRequestToCancelEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractRequestToCancelEmailJobRequest.CONTRACT_ID, context.get(ContractRequestToCancelEmailJobRequest.CONTRACT_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractRequestToCancelEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractRequestedByOwnerRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractRequestedByOwnerEmailJobRequest.HOME_URL, appHost);
        params.put(ContractRequestedByOwnerEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractRequestedByOwnerEmailJobRequest.CONTRACT_ID, context.get(ContractRequestToCancelEmailJobRequest.CONTRACT_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractRequestedByOwnerEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractRejectedByProviderRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractRejectedByProviderEmailJobRequest.HOME_URL, appHost);
        params.put(ContractRejectedByProviderEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractRejectedByProviderEmailJobRequest.CONTRACT_ID, context.get(ContractRejectedByProviderEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractRejectedByProviderEmailJobRequest.SRV_PROVIDER, context.get(ContractRejectedByProviderEmailJobRequest.SRV_PROVIDER).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractRejectedByProviderEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractSignedByProviderJobRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractRejectedByProviderEmailJobRequest.HOME_URL, appHost);
        params.put(ContractRejectedByProviderEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractRejectedByProviderEmailJobRequest.CONTRACT_ID, context.get(ContractRejectedByProviderEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractRejectedByProviderEmailJobRequest.SRV_PROVIDER, context.get(ContractRejectedByProviderEmailJobRequest.SRV_PROVIDER).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractSignedByProviderEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractSignedByOwnerRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractSignedByOwnerEmailJobRequest.HOME_URL, appHost);
        params.put(ContractSignedByOwnerEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractSignedByOwnerEmailJobRequest.CONTRACT_ID, context.get(ContractSignedByOwnerEmailJobRequest.CONTRACT_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractSignedByOwnerEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractRejectedByOwner(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractRejectedByOwnerEmailJobRequest.HOME_URL, appHost);
        params.put(ContractRejectedByOwnerEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractRejectedByOwnerEmailJobRequest.CONTRACT_ID, context.get(ContractRejectedByOwnerEmailJobRequest.CONTRACT_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractRejectedByOwnerEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractCommentedByOwner(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractCommentedByOwnerEmailJobRequest.HOME_URL, appHost);
        params.put(ContractCommentedByOwnerEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractCommentedByOwnerEmailJobRequest.CONTRACT_ID, context.get(ContractCommentedByOwnerEmailJobRequest.CONTRACT_ID).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractCommentedByOwnerEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractCommentedByProvider(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractCommentedByProviderEmailJobRequest.HOME_URL, appHost);
        params.put(ContractCommentedByProviderEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractCommentedByProviderEmailJobRequest.CONTRACT_ID, context.get(ContractCommentedByProviderEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractCommentedByProviderEmailJobRequest.SRV_PROVIDER, context.get(ContractCommentedByProviderEmailJobRequest.SRV_PROVIDER).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractCommentedByProviderEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractExpirationJobRequest(Connection conn, Double userId, Double referenceId, String email, Languages lang,
            Map<String, Object> context) throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractExpirationEmailJobRequest.HOME_URL, appHost);
        params.put(ContractExpirationEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractExpirationEmailJobRequest.CONTRACT_ID, context.get(ContractExpirationEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractExpirationEmailJobRequest.REMAINING_DAYS, context.get(ContractExpirationEmailJobRequest.REMAINING_DAYS).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractExpirationEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }

    public void createContractUploadedByOwner(Connection conn, Double userId, Double referenceId, String email, Languages lang, Map<String, Object> context)
            throws Exception {
        String generatedString = processRequest.createRequest(conn, NtisProcessRequestTypes.CONTRACT_REQUEST, YesNo.N, referenceId, lang, new Date(),
                Long.parseLong(dbPropertyManager.getPropertyByName(EMAIL_LINK_EXPIRATION_MINUTES, DEFAULT_EXPIRATION_MINUTES)));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ContractUploadedByOwnerEmailJobRequest.HOME_URL, appHost);
        params.put(ContractUploadedByOwnerEmailJobRequest.CONTRACT_URL, generatedString);
        params.put(ContractUploadedByOwnerEmailJobRequest.CONTRACT_ID, context.get(ContractUploadedByOwnerEmailJobRequest.CONTRACT_ID).toString());
        params.put(ContractUploadedByOwnerEmailJobRequest.CLIENT_NAME, context.get(ContractUploadedByOwnerEmailJobRequest.CLIENT_NAME).toString());
        if (email != null) {
            params.put(ExecuteEmailSendTask.RECEIVER, email);
        }
        Double requestId = contractUploadedByOwnerEmailJobRequest.createJobRequest(conn, userId, lang, params);
        conn.commit();
        executerJob.execute(requestId);
    }
}
