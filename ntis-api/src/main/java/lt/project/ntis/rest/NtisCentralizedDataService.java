package lt.project.ntis.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
import lt.project.ntis.dao.NtisCwFilesDAO;
import lt.project.ntis.logic.forms.NtisCentralizedWastewaterDataViewPage;
import lt.project.ntis.logic.forms.NtisCwDataList;
import lt.project.ntis.logic.forms.NtisCwDataReport;
import lt.project.ntis.logic.forms.NtisCwForOrgDataList;
import lt.project.ntis.models.NtisWastewaterDataImportFile;

/**
 * Klasė skirta formos "Centralizuoto nuotekų tvarkymo" modulio rest metodams
 * 
 */

@RestController
@RequestMapping("/ntis-centralized-data")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisCentralizedDataService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    @Qualifier("ntisCwDataList")
    NtisCwDataList cwDataList;

    @Autowired
    @Qualifier("ntisCwForOrgDataList")
    NtisCwForOrgDataList cw4OrgDataList;

    @Autowired
    NtisCwDataReport centralizedWastewaterDataReport;

    @Autowired
    NtisCentralizedWastewaterDataViewPage centralizedDataViewPage;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // CENTRALIZED WASTEWATER DATA LIST NTIS ADMIN (START)
    @RequestMapping(value = "/get-centralized-ww-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCentralizedWWData(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cwDataList.getCwFilesList(this.getDBConnection(), params, Utils.getDouble(params.getParamList().get("orgId"))));
    }

    @RequestMapping(value = "/get-org-name", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getOrgName(@RequestBody String id) throws Exception {
        return okResponse(cwDataList.getOrgName(this.getDBConnection(), Utils.getDouble(id)));
    }

    @RequestMapping(value = "/get-prp-doc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdKeyValuePair> getPrpDoc() throws Exception {
        return okResponse(cwDataList.getDocumentLink(this.getDBConnection()));
    }

    @RequestMapping(value = "/set-file-for-processing", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void setFileForErrProcessing(@RequestBody String id) throws Exception {
        cwDataList.setFileForErrProcessing(this.getDBConnection(), Utils.getDouble(id), null);
    }

    @RequestMapping(value = "/validate-errs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void validateFileErrs(@RequestBody SelectRequestParams params) throws Exception {
        String sessLang = this.requestContext.getUserSession().getLanguage();
        Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
        cwDataList.validateFileErrs(getDBConnection(), Utils.getDouble(params.getParamList().get("cwfId")), Utils.getDouble(params.getParamList().get("orgId")),
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id(), language);
    }

    @RequestMapping(value = "/set-centralized-ww-data-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisCwFilesDAO> saveFile(@RequestBody SelectRequestParams params) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        String sessLang = this.requestContext.getUserSession().getLanguage();
        Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
        return okResponse(cwDataList.saveNewFile(this.getDBConnection(), params.getParamList().get("file"), userId,
                Utils.getDouble(params.getParamList().get("orgId")), language));
    }

    @RequestMapping(value = "/get-newly-imported-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisWastewaterDataImportFile> getNewlyImportedFile(@RequestBody String cwfId) throws Exception {
        return okResponse(cwDataList.getFileRecord(this.getDBConnection(), Utils.getDouble(cwfId), null));
    }
    // CENTRALIZED WASTEWATER DATA LIST NTIS ADMIN (END)

    // CENTRALIZED WASTEWATER DATA LIST ORGANIZATION (START)
    @RequestMapping(value = "/set-file-for-processing-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void setFileForErrProcessingOrg(@RequestBody String id) throws Exception {
        cw4OrgDataList.setFileForErrProcessing(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_org_id());
    }

    @RequestMapping(value = "/get-centralized-org-ww-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCentralizedOrgWWData(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cw4OrgDataList.getCwFilesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-org-name-for-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrgNameForOrg() throws Exception {
        return okResponse(cw4OrgDataList.getOrgName(this.getDBConnection(), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/set-centralized-org-ww-data-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisCwFilesDAO> saveOrgFile(@RequestBody SelectRequestParams params) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        Double orgId = this.requestContext.getUserSession().getSes_org_id();
        String sessLang = this.requestContext.getUserSession().getLanguage();
        Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
        return okResponse(cw4OrgDataList.saveNewFile(this.getDBConnection(), params.getParamList().get("file"), userId, orgId, language));
    }

    @RequestMapping(value = "/validate-errs-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void validateFileErrsOrg(@RequestBody SelectRequestParams params) throws Exception {
        Double orgId = this.requestContext.getUserSession().getSes_org_id();
        String sessLang = this.requestContext.getUserSession().getLanguage();
        Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
        cw4OrgDataList.validateFileErrs(getDBConnection(), Utils.getDouble(params.getParamList().get("cwfId")), orgId,
                this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id(), language );
    }

    @RequestMapping(value = "/del-pending-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delWastewaterDataListFile(@RequestBody SprFile sprFile) throws Exception {
        cwDataList.deletePendingFile(this.getDBConnection(), sprFile, null);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-prp-doc-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdKeyValuePair> getPrpDocOrg() throws Exception {
        return okResponse(cw4OrgDataList.getDocumentLink(this.getDBConnection()));
    }

    @RequestMapping(value = "/update-ww-file-state", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateFileState(@RequestBody String id) throws Exception {
        cwDataList.updateExistingFile(this.getDBConnection(), id, null, this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }
    // CENTRALIZED WASTEWATER DATA LIST ORGANIZATION (END)

    // CENTRALIZED WASTEWATER DATA ADDRESS MAPPINGS NTIS ADMIN (START)
    @RequestMapping(value = "/get-centralized-adr-map", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAdrMappings(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cwDataList.getAddressMappings(this.getDBConnection(), params, null, this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-centralized-adr-map-adm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAdrMappingsAdminList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cwDataList.getAddressMappings(this.getDBConnection(), params, null, this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/get-cw-adr-mapping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAdrMappingsDAO> getAdrMapping(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(cwDataList.getAdrMapping(getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/set-adr-mapping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAdrMappingsDAO> setAdrMapping(@RequestBody NtisAdrMappingsDAO record) throws Exception {
        return okResponse(cwDataList.saveAdrMapping(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/del-adr-mapping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delAddressMapping(@RequestBody String id) throws Exception {
        cwDataList.deleteAdrMapping(this.getDBConnection(), Utils.getDouble(id), null);
        return okResponse(null);
    }

    @RequestMapping(value = "/revalidate-errors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> revalidateErrors(@RequestBody SelectRequestParams params) throws Exception {
        String sessLang = this.requestContext.getUserSession().getLanguage();
        Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
        cwDataList.revalidateErrors(this.getDBConnection(), Utils.getDouble(params.getParamList().get("cwfId")),
                Utils.getDouble(params.getParamList().get("orgId")), this.requestContext.getUserSession().getSes_usr_id(), this.requestContext.getUserSession().getSes_org_id(), language);
        return okResponse(null);
    }

    @RequestMapping(value = "/get-newly-imported-file-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<NtisWastewaterDataImportFile> getNewlyImportedFileOrg(@RequestBody String cwfId) throws Exception {
        return okResponse(cw4OrgDataList.getFileRecord(this.getDBConnection(), Utils.getDouble(cwfId), this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/del-pending-file-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delWastewaterDataListFileOrg(@RequestBody SprFile sprFile) throws Exception {
        cw4OrgDataList.deletePendingFile(this.getDBConnection(), sprFile, this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/update-ww-file-state-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateFileStateOrg(@RequestBody String id) throws Exception {
        cw4OrgDataList.updateExistingFile(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    // CENTRALIZED WASTEWATER DATA ADDRESS MAPPINGS NTIS ADMIN (END)

    // CENTRALIZED WASTEWATER DATA ADDRESS MAPPINGS ORGANIZATION (START)
    @RequestMapping(value = "/get-centralized-adr-map-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAdrMappingsOrg(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cw4OrgDataList.getAddressMappings(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id(),
                this.requestContext.getUserSession().getSes_language()));
    }

    @RequestMapping(value = "/del-adr-mapping-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delAddressMappingOrg(@RequestBody String id) throws Exception {
        cw4OrgDataList.deleteAdrMapping(this.getDBConnection(), Utils.getDouble(id), this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/get-cw-adr-mapping-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAdrMappingsDAO> getAdrMappingOrg(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(cw4OrgDataList.getAdrMapping(getDBConnection(), recordIdentifier, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/set-adr-mapping-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisAdrMappingsDAO> setAdrMappingOrg(@RequestBody NtisAdrMappingsDAO record) throws Exception {
        return okResponse(cw4OrgDataList.saveAdrMapping(this.getDBConnection(), record, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/revalidate-errors-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> revalidateErrorsOrg(@RequestBody String id) throws Exception {
        Double orgId = this.requestContext.getUserSession().getSes_org_id();
        String sessLang = this.requestContext.getUserSession().getLanguage();
        Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
        cw4OrgDataList.revalidateErrors(this.getDBConnection(), Utils.getDouble(id), orgId, this.requestContext.getUserSession().getSes_usr_id(), orgId, language);
        return okResponse(null);
    }
    // CENTRALIZED WASTEWATER DATA ADDRESS MAPPINGS ORGANIZATION (END)

    @RequestMapping(value = "/get-exemplary-files", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SprFile>> getExemplaryFiles() throws Exception {
        return okResponse(cwDataList.getExemplaryFiles(this.getDBConnection()));
    }

    @RequestMapping(value = "/get-exemplary-files-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SprFile>> getExemplaryFilesOrg() throws Exception {
        return okResponse(cw4OrgDataList.getExemplaryFiles(this.getDBConnection()));
    }

    // file import related
    @RequestMapping(value = "/get-errors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getErrors(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cwDataList.getErrorsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-errors-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getErrorsOrg(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cw4OrgDataList.getErrorsList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-data-lines", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLines(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cwDataList.getFileLinesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/get-data-lines-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLinesOrg(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(cw4OrgDataList.getFileLinesList(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_org_id()));
    }

    // CENTRALIZED WASTEWATER DATA VIEW PAGE START

    @RequestMapping(value = "/get-wastewater-file-rec", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NtisWastewaterDataImportFile> getWastewaterFileRecord(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(centralizedDataViewPage.getFileRecord(this.getDBConnection(), params.getParamList().get("cwfId"), params.getParamList().get("orgId"),
                this.requestContext.getUserSession().getSes_org_id()));
    }

    @RequestMapping(value = "/update-ww-data-file-state", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> updateExistingFile(@RequestBody String id) throws Exception {
        centralizedDataViewPage.updateExistingFile(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_org_id(), this.requestContext.getUserSession().getSes_usr_id());
        return okResponse(null);
    }

    @RequestMapping(value = "/del-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFile(@RequestBody SprFile file) throws Exception {
        centralizedDataViewPage.delete(this.getDBConnection(), file, this.requestContext.getUserSession().getSes_org_id());
        return okResponse(null);
    }

    // CENTRALIZED WASTEWATER DATA VIEW PAGE END

    // CENTRALIZED WASTEWATER DATA REPORT (START)
    @RequestMapping(value = "/get-centralized-data-report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDataReport(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(centralizedWastewaterDataReport.getReport(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-water-managers-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getWaterManagersList() throws Exception {
        return okResponse(centralizedWastewaterDataReport.getWaterManagers(this.getDBConnection()));
    }
    // CENTRALIZED WASTEWATER DATA REPORT (END)

}
