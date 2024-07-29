package lt.project.ntis.logic.forms.isense;

import static lt.project.ntis.logic.forms.isense.Isense.MDC_COT_ID;
import static lt.project.ntis.logic.forms.isense.Isense.MDC_FIL_ID;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.enums.ProcessRequestType;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprProcessRequestsDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.dao.NtisContractsDAO;
import lt.project.ntis.enums.NtisContractParties;
import lt.project.ntis.enums.NtisContractStatus;
import lt.project.ntis.enums.NtisProcessRequestTypes;
import lt.project.ntis.service.NtisContractsDBService;

/**
 * Pagalbinė integracijos su iSense klasė, naudojama darbui su DB.
 */
@Component
public class IsenseDb {

    private static final Logger log = LoggerFactory.getLogger(IsenseDb.class);

    @Autowired
    DBStatementManager dBStatementManager;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprOrganizationsDBService sprOrganizationsDBService;

    @Autowired
    SprOrgUsersDBService orgUsersDBService;

    @Autowired
    SprPersonsDBService personsDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Autowired
    SprProcessRequestsDBService sprProcessRequestsDBService;

    @Autowired
    NtisContractsDBService ntisContractsDBService;

    @Autowired
    SprAuthorization<?> sprAuthorization;

    @Autowired
    BaseControllerJDBC queryController;

    @Value("${isense.signingRequestValidInMinutes}")
    private int validSigningPeriodInMinutes;

    @Value("${isense.previewRequestValidInMinutes}")
    private int validPreviewPeriodInMinutes;

    NtisContractParties getPersonRelationToContract(Connection conn, Double orgId, Double perId, Double identifier, Boolean isClient) throws Exception {
        return ntisContractsDBService.isServiceProviderOrClient(conn, orgId, perId, identifier, isClient);
    }

    NtisContractsDAO loadContract(Connection conn, Double cotId, Double perId, Double usrId, Double orgId) throws Exception {
        return ntisContractsDBService.loadRecord(conn, cotId);
    }

    private NtisContractsDAO loadContractForUpdate(Connection conn, Double cotId) throws Exception {
        conn.setAutoCommit(false);
        return ntisContractsDBService.loadRecordByParams(conn, " WHERE COT_ID = ? FOR UPDATE  ", new SelectParamValue(Utils.getLong(cotId)));
    }

    private boolean isLockedByCurrentPerson(NtisContractsDAO contract, Double orgId, Double perId) {
        boolean result = false;
        Double cotPerId = contract.getSigningPerId();
        Double cotOrgId = contract.getSigningOrgId();
        if (cotPerId != null) {
            if (cotOrgId != null) {
                result = cotPerId.equals(perId) && cotOrgId.equals(orgId);
            } else {
                result = cotPerId.equals(perId) && orgId == null;
            }
        } else if (cotOrgId != null) {
            result = cotOrgId.equals(orgId) && perId == null;
        }
        return result;
    }

    private boolean isLockedByOtherPerson(NtisContractsDAO contract, Double orgId, Double perId) {
        boolean result = false;
        Double cotPerId = contract.getSigningPerId();
        Double cotOrgId = contract.getSigningOrgId();
        if (cotPerId != null) {
            if (cotOrgId != null) {
                result = !cotPerId.equals(perId) || !cotOrgId.equals(orgId);
            } else {
                result = !cotPerId.equals(perId) || orgId != null;
            }
        } else if (cotOrgId != null) {
            result = !cotOrgId.equals(orgId) || perId != null;
        }
        return result;
    }

    NtisContractsDAO lockContract(Connection conn, Double cotId, Double perId, Double usrId, Double orgId) throws Exception {
        NtisContractsDAO contract = loadContractForUpdate(conn, cotId);
        if (isLockedByOtherPerson(contract, orgId, perId)) {
            if (contractHasProcessRequests(conn, cotId)) {
                log.error("Contract locked by another user - orgId:{}; perId:{}.", contract.getSigningOrgId(), contract.getSigningPerId());
                throw new SparkBusinessException(new S2Message("components.iSense.locked", SparkMessageType.ERROR, "Contract locked by another user"));
            }
        } else if (isLockedByCurrentPerson(contract, orgId, perId)) {
            deleteProcessRequestsByContractId(conn, cotId);
        }

        contract.setSigningPerId(perId);
        contract.setSigningOrgId(orgId);
        ntisContractsDBService.saveRecord(conn, contract);
        return contract;
    }

    SprPersonsDAO loadPerson(Connection conn, Double perId) throws Exception {
        return personsDBService.loadRecord(conn, perId);
    }

    SprOrganizationsDAO loadOrganization(Connection conn, Double orgId) throws Exception {
        return sprOrganizationsDBService.loadRecord(conn, orgId);
    }

    void createFirstSigningProcessRequest(Connection conn, NtisContractsDAO contract, String signingSessionToken) throws Exception {
        createProcessRequest(conn, contract, signingSessionToken, NtisProcessRequestTypes.ISENSE_SIGNING_1_REQUEST, validSigningPeriodInMinutes);
    }

    void createSecondSigningProcessRequest(Connection conn, NtisContractsDAO contract, String signingSessionToken) throws Exception {
        createProcessRequest(conn, contract, signingSessionToken, NtisProcessRequestTypes.ISENSE_SIGNING_2_REQUEST, validSigningPeriodInMinutes);
    }

    void createPreviewProcessRequest(Connection conn, NtisContractsDAO contract, String previewSessionToken) throws Exception {
        createProcessRequest(conn, contract, previewSessionToken, NtisProcessRequestTypes.ISENSE_PREVIEW_REQUEST, validPreviewPeriodInMinutes);
    }

    private void createProcessRequest(Connection conn, NtisContractsDAO contract, String signingSessionToken, ProcessRequestType processRequestType,
            int validPeriodInMinutes) throws Exception {
        sprProcessRequest.createRequest(conn, processRequestType, YesNo.N, contract.getCot_id(), Languages.LT, new Date(), validPeriodInMinutes,
                signingSessionToken, null);
    }

    private void deleteProcessRequestsByContractId(Connection conn, Double cotId) throws Exception {
        String stmtStr = "DELETE FROM SPR_PROCESS_REQUESTS WHERE PRQ_REFERENCE_ID=?";
        log.debug("delete stmt: {}", stmtStr);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(stmtStr);
        stmt.addSelectParam(cotId);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    void deleteProcessRequestsByToken(Connection conn, String signingSessionToken) throws Exception {
        String stmtStr = "DELETE FROM SPR_PROCESS_REQUESTS WHERE PRQ_TOKEN=?";
        log.debug("delete stmt: {}", stmtStr);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(stmtStr);
        stmt.addSelectParam(signingSessionToken);
        queryController.adjustRecordsInDB(conn, stmt);
    }

    SprFilesDAO loadContractFile(Connection conn, NtisContractsDAO contract) throws Exception {
        return sprFilesDBService.loadRecord(conn, contract.getCot_fil_id());
    }

    InputStream getContractFileStream(Connection conn, SprFilesDAO file) throws Exception {
        return fileStorageService.getFileBySprFilesDAO(file);
    }

    InputStream loadContractSignedOnceFile(Connection conn, NtisContractsDAO contract) throws Exception {
        MDC.put(MDC_FIL_ID, String.valueOf(contract.getCot_sign_1_fil_id()));
        return fileStorageService.getFileBySprFilesDAO(sprFilesDBService.loadRecord(conn, contract.getCot_sign_1_fil_id()));
    }

    void storeContractFile(Connection conn, InputStream inputStream, String signingSessionToken) throws Exception {
        SprProcessRequestsDAO processRequest = loadProcessRequestByToken(conn, signingSessionToken);
        if (processRequest == null) {
            log.info("Could not find process request for sessionToken '{}' - signing completed too late?", signingSessionToken);
        }
        Double cotId = processRequest.getPrq_reference_id();
        NtisContractsDAO contract = loadContractForUpdate(conn, cotId);
        MDC.put(MDC_COT_ID, String.valueOf(contract.getCot_id()));

        deleteProcessRequestsByContractId(conn, cotId);

        SprFilesDAO file = loadContractFile(conn, contract); // TODO overkill ??
        SprFilesDAO newVersion = fileStorageService.saveFile(inputStream, String.format("%s.adoc", file.getFil_name()), "application/vnd.lt.archyvai.adoc-2008",
                conn);
        sprFilesDBService.markAsConfirmed(conn, newVersion.getFil_id());
        MDC.put(MDC_FIL_ID, String.valueOf(newVersion.getFil_id()));

        if (isFirstSigning(processRequest)) {
            if (contract.getCot_sign_1_fil_id() != null) {
                throw new Exception("Attempt at first signing when contract already has first signature.");

            } else if (contract.getCot_sign_2_fil_id() != null) {
                throw new Exception("Attempt at first signing when contract already has second signature.");
            }

            log.info("Storing edoc as signed once version of file.");
            contract.setCot_sign_1_fil_id(newVersion.getFil_id());
            contract.setCot_state(NtisContractStatus.SIGNED_BY_SRV_PROV.getCode());

        } else if (isSecondSigning(processRequest)) {
            if (contract.getCot_sign_2_fil_id() != null) {
                throw new Exception("Attempt at second signing when contract already has second signature.");
            }

            log.info("Storing edoc as signed twice version of file.");
            contract.setCot_sign_2_fil_id(newVersion.getFil_id());
            contract.setCot_state(NtisContractStatus.SIGNED_BY_BOTH.getCode());

        } else {
            throw new Exception("Could not identify which signing corresponds to received callback");
        }

        contract.setSigningPerId(null);
        contract.setSigningOrgId(null);
        ntisContractsDBService.saveRecord(conn, contract);
    }

    private boolean isFirstSigning(SprProcessRequestsDAO processRequest) {
        return NtisProcessRequestTypes.ISENSE_SIGNING_1_REQUEST.getCode().equals(processRequest.getPrq_type());
    }

    private boolean isSecondSigning(SprProcessRequestsDAO processRequest) {
        return NtisProcessRequestTypes.ISENSE_SIGNING_2_REQUEST.getCode().equals(processRequest.getPrq_type());
    }

    private SprProcessRequestsDAO loadProcessRequestByToken(Connection conn, String signingSessionToken) throws Exception {
        return sprProcessRequestsDBService.loadRecordByParams(conn, " WHERE PRQ_TOKEN = ? ", new SelectParamValue(signingSessionToken));
    }

    private boolean contractHasProcessRequests(Connection conn, Double cotId) throws Exception {
        List<SprProcessRequestsDAO> processRequests = sprProcessRequestsDBService.loadRecordsByParams(conn, " prq_reference_id = ? ",
                new SelectParamValue(cotId));
        return processRequests != null && !processRequests.isEmpty();
    }
}
