package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprTemplateTextsDBService;
import eu.itreegroup.spark.modules.admin.service.SprTemplatesDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

@Component
public class SprTemplatesBrowse extends FormBase {

    final private BaseControllerJDBC queryController;

    final private SprTemplatesDBService sprTemplatesDBService;

    final private SprTemplateTextsDBService sprTemplateTextsDBService;

    public SprTemplatesBrowse(BaseControllerJDBC baseControllerJDBC, SprTemplatesDBService sprTemplatesDBService,
            SprTemplateTextsDBService sprTemplateTextsDBService) {
        this.queryController = baseControllerJDBC;
        this.sprTemplatesDBService = sprTemplatesDBService;
        this.sprTemplateTextsDBService = sprTemplateTextsDBService;
    }

    private static final Logger log = LoggerFactory.getLogger(SprTemplatesBrowse.class);

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "SPR_TEMPLATES_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark templates list", "Spark templates browse page");
        addFormActionCRUD();
    }

    public String getTemplatesList(Connection conn, SelectRequestParams params, String language, Double userId) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprTemplatesBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("" //
                + "SELECT tmpl.tml_id, " //
                + "       CASE WHEN type_rft.rft_display_code IS NOT NULL THEN type_rft.rft_display_code ELSE type_rfc.rfc_meaning END AS tml_type, " //
                + "       tmpl.tml_code, " //
                + "       CASE WHEN status_rft.rft_display_code IS NOT NULL THEN status_rft.rft_display_code ELSE status_rfc.rfc_meaning END AS tml_status, " //
                + "       tmpl.tml_name, " //
                + "       tmpl.tml_description " //
                + "  FROM spr_templates tmpl " //
                + "  LEFT JOIN spr_ref_codes type_rfc ON type_rfc.rfc_code = tmpl.tml_type AND type_rfc.rfc_domain = 'SPR_TMPL_TYPE' " //
                + "  LEFT JOIN spr_ref_translations type_rft ON type_rfc.rfc_id = type_rft.rft_rfc_id and type_rft.rft_lang = ? " //
                + "  LEFT JOIN spr_ref_codes status_rfc ON status_rfc.rfc_code = tmpl.tml_status AND status_rfc.rfc_domain = 'SPR_TMPL_STATUS' " //
                + "  LEFT JOIN spr_ref_translations status_rft ON status_rfc.rfc_id = status_rft.rft_rfc_id and status_rft.rft_lang = ?");
        stmt.addSelectParam(language);
        stmt.addSelectParam(language);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), userId, stmt, advancedParamList);
        stmt.addParam4WherePart("tmpl.tml_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("tml_id"));
        stmt.addParam4WherePart("tmpl.tml_code", StatementAndParams.PARAM_STRING, advancedParamList.get("tml_code"));
        stmt.addParam4WherePart("tmpl.tml_name", StatementAndParams.PARAM_STRING, advancedParamList.get("tml_name"));
        stmt.addParam4WherePart("tmpl.tml_description", StatementAndParams.PARAM_STRING, advancedParamList.get("tml_description"));

        stmt.addParam4WherePart("tmpl.tml_type", StatementAndParams.PARAM_STRING, advancedParamList.get("tml_type"));
        stmt.addParam4WherePart("tmpl.tml_status", StatementAndParams.PARAM_STRING, advancedParamList.get("tml_status"));

        stmt.addParam4WherePart(dbstatementManager.colNamesToConcatString("tmpl.tml_id", "tmpl.tml_code", "tmpl.tml_name", "tmpl.tml_description",
                "status_rft.rft_display_code", "type_rft.rft_display_code"), StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(FormBase.DEFAULT_ACTIONS_ORDER);
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void deleteTemplate(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        try {
            sprTemplatesDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
        } catch (SQLIntegrityConstraintViolationException e) {
            sprTemplateTextsDBService.deleteRecordsByParent(conn, recordIdentifier.getIdAsDouble());
            sprTemplatesDBService.deleteRecord(conn, recordIdentifier.getIdAsDouble());
        }
    }

}
