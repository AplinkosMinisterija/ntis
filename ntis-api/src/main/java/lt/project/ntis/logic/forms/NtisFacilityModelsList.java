package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.project.ntis.dao.NtisFacilityModelDAO;
import lt.project.ntis.logic.forms.security.NtisFacilityModelsBrowseSecurityManager;
import lt.project.ntis.service.NtisFacilityModelDBService;

/**
 * Klasė skirta formos "Nuotekų tvarkymo įrenginių modeliai" biznio logikai apibrėžti
 */
@Component
public class NtisFacilityModelsList extends FormBase {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisFacilityModelsList.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    SprRefCodesDBService sprRefCodesDBService;

    @Autowired
    NtisFacilityModelDBService ntisFacilityModelDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public String getFormName() {
        return "NTIS_FACILITY_MODELS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų tvarkymo įrenginių modeliai", "Nuotekų tvarkymo įrenginių modelių forma");
        addFormActionCRUD();
    }

    /**
     * Metodas grąžins sistemoje registruotų nuotekų tvarkymo įrenginių modelių sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param orgId - sesijos organizacijos id
     * @return json objektas
     * @throws Exception
     */
    public String getFacilityModels(Connection conn, SelectRequestParams params, Double orgId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisFacilityModelsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT
                rfc.rfc_id,
                coalesce(rfc2.rfc_meaning, rfc.rfc_ref_code_1) as rfc_manufa,
                rfc.rfc_meaning,
                rfc.rfc_description,
                rfc.rfc_date_from,
                rfc.rfc_date_to
                FROM spr_ref_codes rfc
                LEFT JOIN spr_ref_codes rfc2 on rfc.rfc_ref_code_1 = rfc2.rfc_code
                WHERE rfc.rfc_domain = 'NTIS_FACIL_MODEL'
                        """);
        stmt.setWhereExists(true);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), orgId, stmt, advancedParamList);
        stmt.addParam4WherePart("rfc.rfc_ref_code_1", StatementAndParams.PARAM_STRING, advancedParamList.get("rfc_manufa"));
        stmt.addParam4WherePart("rfc.rfc_meaning", StatementAndParams.PARAM_STRING, advancedParamList.get("rfc_meaning"));
        stmt.addParam4WherePart("rfc.rfc_description", StatementAndParams.PARAM_STRING, advancedParamList.get("rfc_description"));
        stmt.addParam4WherePart("rfc.rfc_date_from", StatementAndParams.PARAM_DATE, advancedParamList.get("rfc_date_from"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("rfc.rfc_date_to", StatementAndParams.PARAM_DATE, advancedParamList.get("rfc_date_to"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("coalesce(rfc2.rfc_meaning, rfc.rfc_ref_code_1)", "rfc.rfc_meaning", "rfc.rfc_description",
                        "TO_CHAR(rfc.rfc_date_from,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(rfc.rfc_date_to,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("ORDER BY rfc2.rfc_meaning ASC");
        NtisFacilityModelsBrowseSecurityManager sqm = new NtisFacilityModelsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisFacilityModelsList.ACTION_READ, NtisFacilityModelsList.ACTION_UPDATE, NtisFacilityModelsList.ACTION_DELETE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_DELETE);
        NtisFacilityModelDAO ntisFacilityModelDAO = ntisFacilityModelDBService.loadRecordByParams(conn, "fam_rfc_id = ?::int",
                new SelectParamValue(recordIdentifier.getIdAsDouble()));
        if (ntisFacilityModelDAO != null) {
            ntisFacilityModelDBService.deleteRecord(conn, ntisFacilityModelDAO);
            if (ntisFacilityModelDAO.getFam_fil_id() != null) {
                SprFilesDAO sprFileDAO = sprFilesDBService.loadRecord(conn, ntisFacilityModelDAO.getFam_fil_id());
                fileStorageService.deleteFile(sprFileDAO.getFil_key(), sprFileDAO.getFil_path());
                sprFilesDBService.deleteRecord(conn, sprFileDAO.getFil_id());
            }
        }
        SprRefCodesDAO sprRefCodesDAO = sprRefCodesDBService.loadRecord(conn, recordIdentifier.getIdAsDouble());
        sprRefCodesDBService.deleteRecord(conn, sprRefCodesDAO);
    }

}
