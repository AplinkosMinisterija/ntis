package lt.project.ntis.logic.forms.isense;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.enums.NtisContractParties;

/**
 * Pagrindinė integracijos su iSense sistema klasė.
 */
@Component
public class Isense {

    private static final Logger log = LoggerFactory.getLogger(Isense.class);

    /**
     * Key used to store person ID into MDC context.
     */
    static final String MDC_PER_ID = "ntis:perId";

    /**
     * Key used to store user ID into MDC context.
     */
    static final String MDC_USR_ID = "ntis:usrId";

    /**
     * Key used to store organization ID into MDC context.
     */
    static final String MDC_ORG_ID = "ntis:orgId";

    /**
     * Key used to store contract ID into MDC context.
     */
    static final String MDC_COT_ID = "ntis:cotId";

    /**
     * Key used to store contract file (one of) ID into MDC context.
     */
    static final String MDC_FIL_ID = "ntis:filId";

    /**
     * Key used to store iSense issued OAuth token into MDC context.
     * OAuth tokens identify requests as coming from NTIS.
     */
    static final String MDC_OAUTH_TOKEN = "isense:OAuthToken";

    /**
     * Key used to store iSense issued signing session token into MDC context. 
     * Each session token identifies a single signing process of one contract by one person.
     */
    static final String MDC_SESSION_TOKEN = "isense:sessionToken";

    /**
     * Key used to store iSense issued data file ID into MDC context.
     */
    static final String MDC_DATA_FILE_ID = "isense:dataFileId";

    /**
     * Key used to store iSense issued edoc ID into MDC context.
     */
    static final String MDC_EDOC_ID = "isense:edocId";

    /**
     * Key used to store received callback request body content into MDC context.
     */
    static final String MDC_CALLBACK_BODY = "callbackBody";

    /**
     * Key used to store received session info body content into MDC context.
     */
    static final String MDC_SESSION_INFO = "sessionInfo";

    @Autowired
    IsenseDb isenseDb;

    @Autowired
    IsenseJson isenseJson;

    @Autowired
    IsenseHttpRestTemplate isenseHttp;

    @Value("${isense.url}")
    private String isenseUrl;

    /**
     * Pradeda sutarties dokumento pasirašymą ir suformuoja url į iSense sistemą kur galima tęsti pasirašymo procesą.
     * @param conn - prisijungimas prie DB.
     * @param cotId - sutarties ID.
     * @param perId - asmens ID.
     * @param usrId - sesijos naudotojo ID.
     * @param orgId - sesijos organizacijos ID.
     * @return url į iSense sistemą, kuriuo kreipiantis galima vykdyti pasirašymą.
     * @throws Exception
     */
    public String startSigning(Connection conn, Double cotId, Double perId, Double usrId, Double orgId, Boolean isClient) throws Exception {
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        try {
            MDC.put(MDC_COT_ID, String.valueOf(cotId));
            MDC.put(MDC_PER_ID, String.valueOf(perId));
            MDC.put(MDC_USR_ID, String.valueOf(usrId));
            MDC.put(MDC_ORG_ID, String.valueOf(orgId));
            log.info("START iSense signing for cotId:{}; perId:{}; usrId:{}; orgId:{}.", MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID),
                    MDC.get(MDC_ORG_ID));

            return doSigning(conn, cotId, perId, usrId, orgId, isClient);

        } catch (SparkBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Signing attempt failed.", e);

        } finally {
            log.info(
                    "FINISH iSense signing for perId:{}; usrId:{}; orgId:{}; cotId:{}; filId:{}; OAuthToken:'{}'; sessionToken:'{}'; edocId:'{}'; dataFileId:'{}'.",
                    MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_COT_ID), MDC.get(MDC_FIL_ID), MDC.get(MDC_OAUTH_TOKEN),
                    MDC.get(MDC_SESSION_TOKEN), MDC.get(MDC_EDOC_ID), MDC.get(MDC_DATA_FILE_ID));
            MDC.setContextMap(mdc);
        }
    }

    private String doSigning(Connection conn, Double cotId, Double perId, Double usrId, Double orgId, Boolean isClient) throws Exception {
        NtisContractParties personRelationToContract = isenseDb.getPersonRelationToContract(conn, orgId, perId, cotId, isClient);
        if (NtisContractParties.NONE == personRelationToContract) {
            throw new Exception("Conflicting ID parameters.");
        }

        NtisContractsDAO contract = isenseDb.lockContract(conn, cotId, perId, usrId, orgId);

        if (contract.getCot_fil_id() == null) {
            throw new SparkBusinessException(new S2Message("components.iSense.noDocument", SparkMessageType.ERROR, "Contract does not have document."));
        }
        MDC.put(MDC_FIL_ID, String.valueOf(contract.getCot_fil_id()));

        String sessionToken = null;
        if (NtisContractParties.SERVICE_PROVIDER == personRelationToContract) {
            if (contract.getCot_sign_1_fil_id() != null) {
                throw new SparkBusinessException(new S2Message("components.iSense.signedOnceAlready", SparkMessageType.ERROR,
                        "Contract already has document signed by service supplier."));
            }
            sessionToken = doFirstSigning(conn, contract, perId, orgId);

        } else if (NtisContractParties.CLIENT == personRelationToContract) {
            if (contract.getCot_sign_1_fil_id() == null) {
                throw new SparkBusinessException(new S2Message("components.iSense.noFirstSignature", SparkMessageType.ERROR,
                        "Contract document must first be signed by service supplier."));
            }
            if (contract.getCot_sign_2_fil_id() != null) {
                throw new SparkBusinessException(
                        new S2Message("components.iSense.signedTwiceAlready", SparkMessageType.ERROR, "Contract already has document signed by service user."));
            }
            sessionToken = doSecondSigning(conn, contract, perId);
        }

        return String.format("%s/?sessionToken=%s", isenseUrl, sessionToken);
    }

    private String doFirstSigning(Connection conn, NtisContractsDAO contract, Double perId, Double orgId) throws Exception {
        String ntisToken = isenseHttp.createNtisToken();
        SprFilesDAO file = isenseDb.loadContractFile(conn, contract);
        MDC.put(MDC_FIL_ID, String.valueOf(file.getFil_id()));
        String dataFileId = isenseHttp.uploadDataFile(ntisToken, file, isenseDb.getContractFileStream(conn, file));

        SprOrganizationsDAO supplierOrganization = isenseDb.loadOrganization(conn, orgId);
        SprPersonsDAO currentPerson = isenseDb.loadPerson(conn, perId);
        SprOrganizationsDAO clientOrganization = contract.getCot_org_id() == null ? null : isenseDb.loadOrganization(conn, contract.getCot_org_id());
        SprPersonsDAO clientPerson = contract.getCot_per_id() == null ? null : isenseDb.loadPerson(conn, contract.getCot_per_id());

        String edocId = isenseHttp.createEdoc(ntisToken, dataFileId,
                isenseJson.buildMinimalCreateEdocRequestBody(dataFileId, supplierOrganization, clientOrganization, clientPerson, file));
        String sessionToken = isenseHttp.doSigning(ntisToken, isenseJson.buildMinimalSigningRequestBody(edocId, currentPerson));
        isenseDb.createFirstSigningProcessRequest(conn, contract, sessionToken);
        return sessionToken;
    }

    private String doSecondSigning(Connection conn, NtisContractsDAO contract, Double perId) throws Exception {
        String ntisToken = isenseHttp.createNtisToken();
        String edocId = isenseHttp.uploadEdoc(ntisToken, isenseDb.loadContractSignedOnceFile(conn, contract));
        String sessionToken = isenseHttp.doSigning(ntisToken, isenseJson.buildMinimalSigningRequestBody(edocId, isenseDb.loadPerson(conn, perId)));
        isenseDb.createSecondSigningProcessRequest(conn, contract, sessionToken);
        return sessionToken;
    }

    /**
     * Apdoroja iSense sistemos pranešimą apie pasirašymo proceso rezultatus.
     * @param conn - prisijungimas prie DB.
     * @param callbackBody - iSense sistemos pranešimo turinys.
     * @throws Exception
     */
    public void signingCallback(Connection conn, String callbackBody) throws Exception {
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        try {
            MDC.put(MDC_CALLBACK_BODY, callbackBody);
            handleCallback(conn, callbackBody);

        } catch (Exception e) {
            throw new Exception(String.format("Failed handling of received signing callback. Callback body: %s.", callbackBody), e);

        } finally {
            MDC.setContextMap(mdc);
        }
    }

    private void handleCallback(Connection conn, String callbackBody) throws Exception {
        try {
            String sessionToken = IsenseJson.getJsonFieldValue(callbackBody, "sessionToken");
            MDC.put(MDC_SESSION_TOKEN, sessionToken);

            String ntisToken = isenseHttp.createNtisToken();
            String sessionInfo = isenseHttp.getSessionInfo(ntisToken, sessionToken);

            if (isenseJson.isFileSigned(sessionInfo)) {
                String edocId = isenseJson.retrieveEdocIdFromSessionJson(sessionInfo);
                MDC.put(MDC_EDOC_ID, edocId);

                log.info("START iSense callback handling for sessionToken:'{}'; edocId:'{}'.", MDC.get(MDC_SESSION_TOKEN), MDC.get(MDC_EDOC_ID));

                InputStream edocData = isenseHttp.downloadEdoc(ntisToken, edocId);
                isenseHttp.deleteSession(ntisToken, sessionToken);
                isenseDb.storeContractFile(conn, edocData, sessionToken);

            } else {
                log.info("IGNORING iSense callback for sessionToken '{}' with unfinished signing. Callback body:{}. SessionInfo:{}.", sessionToken,
                        callbackBody, sessionInfo);
                isenseDb.deleteProcessRequestsByToken(conn, sessionToken);
            }
        } finally {
            String sessionToken = MDC.get(MDC_SESSION_TOKEN);
            if (sessionToken == null) {
                log.error("FAILED iSense callback handling. Callback body:{}. SessionInfo:{}.", MDC.get(MDC_CALLBACK_BODY), MDC.get(MDC_SESSION_INFO));
            } else {
                log.info("FINISH iSense callback handling for OAuthToken:'{}'; sessionToken:'{}'; edocId:'{}'; cotId:{}; filId:{}.", MDC.get(MDC_OAUTH_TOKEN),
                        MDC.get(MDC_SESSION_TOKEN), MDC.get(MDC_EDOC_ID), MDC.get(MDC_COT_ID), MDC.get(MDC_FIL_ID));
            }
        }
    }

    /**
     * Pradeda pasirašyto sutarties dokumento peržiūrą ir suformuoja url į iSense sistemą kur galima tęsti peržiūrą.
     * @param conn - prisijungimas prie DB.
     * @param cotId - sutarties ID.
     * @param perId - asmens ID.
     * @param usrId - sesijos naudotojo ID.
     * @param orgId - sesijos organizacijos ID.
     * @return url į iSense sistemą, kuriuo kreipiantis galima peržiūrėti pasirašytą sutarties dokumentą.
     * @throws Exception
     */
    public String startPreview(Connection conn, Double cotId, Double perId, Double usrId, Double orgId, Boolean isClient) throws Exception {
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        try {
            MDC.put(MDC_COT_ID, String.valueOf(cotId));
            MDC.put(MDC_PER_ID, String.valueOf(perId));
            MDC.put(MDC_USR_ID, String.valueOf(usrId));
            MDC.put(MDC_ORG_ID, String.valueOf(orgId));
            log.info("START iSense previewing for cotId:{}; perId:{}; usrId:{}; orgId:{}.", MDC.get(MDC_COT_ID), MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID),
                    MDC.get(MDC_ORG_ID));

            return doPreviewing(conn, cotId, perId, usrId, orgId, isClient);

        } catch (Exception e) {
            throw new Exception("Previewing attempt failed.", e);

        } finally {
            log.info("FINISH iSense previewing for perId:{}; usrId:{}; orgId:{}; cotId:{}; filId:{}; OAuthToken:'{}'; sessionToken:'{}'; edocId:'{}'.",
                    MDC.get(MDC_PER_ID), MDC.get(MDC_USR_ID), MDC.get(MDC_ORG_ID), MDC.get(MDC_COT_ID), MDC.get(MDC_FIL_ID), MDC.get(MDC_OAUTH_TOKEN),
                    MDC.get(MDC_SESSION_TOKEN), MDC.get(MDC_EDOC_ID));
            MDC.setContextMap(mdc);
        }
    }

    private String doPreviewing(Connection conn, Double cotId, Double perId, Double usrId, Double orgId, Boolean isClient) throws Exception {
        NtisContractParties personRelationToContract = isenseDb.getPersonRelationToContract(conn, orgId, perId, cotId, isClient);
        if (NtisContractParties.CLIENT != personRelationToContract && NtisContractParties.SERVICE_PROVIDER != personRelationToContract) {
            throw new Exception("Preview is only intended for clients and service providers.");
        }

        NtisContractsDAO contract = isenseDb.loadContract(conn, cotId, perId, usrId, orgId);
        if (contract.getCot_sign_1_fil_id() == null) {
            throw new SparkBusinessException(new S2Message("components.iSense.zeroSignatures", SparkMessageType.ERROR,
                    "Contract does not have file version signed by service supplier."));
        }

        String ntisToken = isenseHttp.createNtisToken();
        String edocId = isenseHttp.uploadEdoc(ntisToken, isenseDb.loadContractSignedOnceFile(conn, contract));
        String sessionToken = isenseHttp.doViewing(ntisToken, isenseJson.buildMinimalPreviewRequestBody(edocId));
        isenseDb.createPreviewProcessRequest(conn, contract, sessionToken);
        return String.format("%s/?sessionToken=%s", isenseUrl, sessionToken);
    }

}
