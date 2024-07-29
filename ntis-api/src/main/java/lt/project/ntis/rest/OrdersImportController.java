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
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
import lt.project.ntis.logic.forms.NtisOrdImportForOrgList;
import lt.project.ntis.logic.forms.NtisOrdImportList;
import lt.project.ntis.logic.forms.NtisOrdImportViewPage;
import lt.project.ntis.models.NtisOrdImportFile;
import lt.project.ntis.models.OrgDetailsForOrderImport;

/**
 * Klasė skirta gauti/saugoti duomenis reikalingus užsakymų importavimui
 * 
 * 
 */

@RestController
@RequestMapping("/ntis-orders-import")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrdersImportController extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

	@Override
	protected SprBackendUserSession createNewSession() {
		return new SprBackendUserSession();
	}

	@Autowired
	@Qualifier("ntisOrdImportList")
	NtisOrdImportList ntisOrdImportList;

	@Autowired
	@Qualifier("ntisOrdImportForOrgList")
	NtisOrdImportForOrgList ntisOrdImportForOrgList;

	@Autowired
	NtisOrdImportViewPage ntisOrdImportViewPage;

	// ORDERS IMPORT DATA LIST START
	@RequestMapping(value = "/get-orders-imports", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOrdersImports(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportList.getOrdersImportFiles(this.getDBConnection(), params,
				Utils.getDouble(params.getParamList().get("orgId")),
				this.requestContext.getUserSession().getSes_language()));
	}

	@RequestMapping(value = "/get-orders-imports-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOrdersImportsForOrg(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportForOrgList.getOrdersImportFiles(this.getDBConnection(), params,
				this.requestContext.getUserSession().getSes_org_id(),
				this.requestContext.getUserSession().getSes_language()));
	}

	@RequestMapping(value = "/get-org-details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrgDetailsForOrderImport>> getOrgDetails(@RequestBody String id) throws Exception {
		return okResponse(ntisOrdImportList.getOrgDetails(this.getDBConnection(), Utils.getDouble(id)));
	}

	@RequestMapping(value = "/get-org-details-for-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrgDetailsForOrderImport>> getOrgDetailsForOrg() throws Exception {
		return okResponse(ntisOrdImportForOrgList.getOrgDetails(this.getDBConnection(),
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/set-new-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> uploadNewImportFile(@RequestBody SelectRequestParams params) throws Exception {
		String sessLang = this.requestContext.getUserSession().getLanguage();
		Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
		return okResponse(this.ntisOrdImportList.saveNewFile(getDBConnection(), params.getParamList().get("file"),
				Utils.getDouble(params.getParamList().get("srvId")),
				this.requestContext.getUserSession().getSes_usr_id(),
				Utils.getDouble(params.getParamList().get("orgId")), language));
	}

	@RequestMapping(value = "/set-new-file-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> uploadNewImportFileOrg(@RequestBody SelectRequestParams params) throws Exception {
		String sessLang = this.requestContext.getUserSession().getLanguage();
		Languages language = Languages.getLanguageByCode(sessLang != null ? sessLang : "lt");
		return okResponse(this.ntisOrdImportForOrgList.saveNewFile(getDBConnection(), params.getParamList().get("file"),
				Utils.getDouble(params.getParamList().get("srvId")),
				this.requestContext.getUserSession().getSes_usr_id(),
				this.requestContext.getUserSession().getSes_org_id(), language));
	}

	@RequestMapping(value = "/del-pending-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletePendingImportFile(@RequestBody String id) throws Exception {
		ntisOrdImportList.deletePendingFile(this.getDBConnection(), Utils.getDouble(id), null);
		return okResponse(null);
	}

	@RequestMapping(value = "/del-pending-file-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletePendingImportFileOrg(@RequestBody String id) throws Exception {
		ntisOrdImportForOrgList.deletePendingFile(this.getDBConnection(), Utils.getDouble(id),
				this.requestContext.getUserSession().getSes_org_id());
		return okResponse(null);
	}

	@RequestMapping(value = "/update-file-state", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateFileState(@RequestBody String id) throws Exception {
		ntisOrdImportList.updateExistingFile(this.getDBConnection(), Utils.getDouble(id), null);
		return okResponse(null);
	}

	@RequestMapping(value = "/update-file-state-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateFileStateOrg(@RequestBody String id) throws Exception {
		ntisOrdImportForOrgList.updateExistingFile(this.getDBConnection(), Utils.getDouble(id),
				this.requestContext.getUserSession().getSes_org_id());
		return okResponse(null);
	}

	@RequestMapping(value = "/validate-errors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void validateFileErrs(@RequestBody SelectRequestParams params) throws Exception {
		ntisOrdImportList.validateFileErrs(getDBConnection(), Utils.getDouble(params.getParamList().get("orfId")),
				Utils.getDouble(params.getParamList().get("orgId")),
				this.requestContext.getUserSession().getSes_usr_id());
	}

	@RequestMapping(value = "/validate-errors-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void validateFileErrsOrg(@RequestBody SelectRequestParams params) throws Exception {
		Double orgId = this.requestContext.getUserSession().getSes_org_id();
		ntisOrdImportForOrgList.validateFileErrs(getDBConnection(), Utils.getDouble(params.getParamList().get("orfId")),
				orgId, this.requestContext.getUserSession().getSes_usr_id());
	}
	// ORDERS IMPORT DATA LIST END

	// ORDER IMPORT VIEW PAGE START
	@RequestMapping(value = "/get-file-rec", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NtisOrdImportFile> getWastewaterFileRecord(@RequestBody SelectRequestParams params)
			throws Exception {
		return okResponse(
				ntisOrdImportViewPage.getFileRecord(this.getDBConnection(), params.getParamList().get("orfId"),
						params.getParamList().get("orgId"), this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/get-data-lines", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLines(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportViewPage.getFileLinesList(this.getDBConnection(), params,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/get-data-lines-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getLinesOrg(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportViewPage.getFileLinesList(this.getDBConnection(), params,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/get-error-lines", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getErrorLines(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportViewPage.getErrorsList(this.getDBConnection(), params,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/get-error-lines-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getErrorLinesOrg(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportViewPage.getErrorsList(this.getDBConnection(), params,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/del-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteFile(@RequestBody SprFile file) throws Exception {
		ntisOrdImportViewPage.delete(this.getDBConnection(), file,
				this.requestContext.getUserSession().getSes_org_id());
		return okResponse(null);
	}

	@RequestMapping(value = "/get-available-wtfs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAvailableWtfs(@RequestBody String id) throws Exception {
		Double orfdeId = Utils.getDouble(id);
		String lang = this.requestContext.getUserSession().getSes_language();
		return okResponse(ntisOrdImportViewPage.getAvailableWtfs(this.getDBConnection(), orfdeId, lang));
	}
	
	@RequestMapping(value = "/update-selected-wtf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateWithSelectedWtf(@RequestBody SelectRequestParams params) throws Exception {
		ntisOrdImportViewPage.updateWithSelectedWtf(getDBConnection(), Utils.getDouble(params.getParamList().get("wtfId")), Utils.getDouble(params.getParamList().get("orfdeId"))
				);
	}
	// ORDER IMPORT VIEW PAGE END

//    ADDRESS MAPPINGS RELATED
	@RequestMapping(value = "/get-adr-map-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAdrMappings(@RequestBody SelectRequestParams params) throws Exception {
		return okResponse(ntisOrdImportForOrgList.getAddressMappings(this.getDBConnection(), params, null,
				this.requestContext.getUserSession().getSes_language()));
	}

	@RequestMapping(value = "/get-adr-mapping-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NtisAdrMappingsDAO> getAdrMappingOrg(@RequestBody RecordIdentifier recordIdentifier)
			throws Exception {
		return okResponse(ntisOrdImportForOrgList.getAdrMapping(getDBConnection(), recordIdentifier,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/set-adr-mapping-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NtisAdrMappingsDAO> setAdrMappingOrg(@RequestBody NtisAdrMappingsDAO record)
			throws Exception {
		return okResponse(ntisOrdImportForOrgList.saveAdrMapping(this.getDBConnection(), record,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/set-adr-mapping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NtisAdrMappingsDAO> setAdrMapping(@RequestBody NtisAdrMappingsDAO record) throws Exception {
		return okResponse(ntisOrdImportList.saveAdrMapping(this.getDBConnection(), record,
				this.requestContext.getUserSession().getSes_org_id()));
	}

	@RequestMapping(value = "/del-adr-mapping-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delAddressMappingOrg(@RequestBody String id) throws Exception {
		ntisOrdImportForOrgList.deleteAdrMapping(this.getDBConnection(), Utils.getDouble(id),
				this.requestContext.getUserSession().getSes_org_id());
		return okResponse(null);
	}

	@RequestMapping(value = "/set-file-for-processing", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public void setFileForErrProcessing(@RequestBody String id) throws Exception {
		ntisOrdImportList.setFileForErrProcessing(this.getDBConnection(), Utils.getDouble(id), null);
	}

	@RequestMapping(value = "/set-file-for-processing-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public void setFileForErrProcessingOrg(@RequestBody String id) throws Exception {
		ntisOrdImportForOrgList.setFileForErrProcessing(this.getDBConnection(), Utils.getDouble(id),
				this.requestContext.getUserSession().getSes_org_id());
	}

	@RequestMapping(value = "/revalidate-errors-org", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> revalidateErrorsOrg(@RequestBody String id) throws Exception {
		Double orfId = Utils.getDouble(id);
		Double orgId = this.requestContext.getUserSession().getSes_org_id();
		Double usrId = this.requestContext.getUserSession().getSes_usr_id();
		ntisOrdImportForOrgList.revalidateErrors(this.getDBConnection(), orfId, orgId, usrId);
		return okResponse(null);
	}

	@RequestMapping(value = "/revalidate-errors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> revalidateErrors(@RequestBody SelectRequestParams params) throws Exception {
		Double orfId = Utils.getDouble(params.getParamList().get("orfId"));
		Double orgId = Utils.getDouble(params.getParamList().get("orgId"));
		Double usrId = this.requestContext.getUserSession().getSes_usr_id();
		ntisOrdImportList.revalidateErrors(this.getDBConnection(), orfId, orgId, usrId);
		return okResponse(null);
	}

}
