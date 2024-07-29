package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import lt.project.ntis.dao.NtisReviewsDAO;
import lt.project.ntis.logic.forms.security.NtisReviewsForAdminListSecurityManager;
import lt.project.ntis.service.NtisReviewsDBService;

@Component
public class NtisReviewsForAdminList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;
    
    private final NtisReviewsDBService ntisReviewsDBService;

    public NtisReviewsForAdminList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager, NtisReviewsDBService ntisReviewsDBService) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
        this.ntisReviewsDBService = ntisReviewsDBService;
    }

    @Override
    public String getFormName() {
        return "NTIS_REVIEWS_FOR_ADMIN_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Atsiliepimų sąrašo forma sistemos administratoriui", "Atsiliepimų sąrašo forma sistemos administratoriui");
        this.addFormActions(ACTION_UPDATE, ACTION_READ);
    }

    public String getReviewsList(Connection conn, SelectRequestParams params, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                           select rev_id,
                                  rev_score,
                                  rev_comment,
                                  to_char(rev_completed_date, '%s') as rev_completed_date,
                                  coalesce(org1.org_name, org2.org_name) as org_name,
                                  coalesce(rfc1.rfc_meaning, org1.c01, rfc2.rfc_meaning, org2.c01) as org_type,
                                  rev_admin_read
                             from ntis_reviews
                        left join spr_organizations org1 on org1.org_id = rev_pasl_org_id
                        left join spr_organizations org2 on org2.org_id = rev_vand_org_id
                        left join spr_ref_codes_vw rfc1 on org1.c01 = rfc1.rfc_code and rfc1.rfc_domain = 'NTIS_ORG_TYPE' and rfc1.rfc_code in ('PASLAUG', 'VANDEN', 'PASLAUG_VANDEN') and rfc1.rft_lang = ?
                        left join spr_ref_codes_vw rfc2 on org2.c01 = rfc2.rfc_code and rfc2.rfc_domain = 'NTIS_ORG_TYPE' and rfc2.rfc_code in ('PASLAUG', 'VANDEN', 'PASLAUG_VANDEN') and rfc2.rft_lang = ?
                            where rev_score is not null and rev_completed_date is not null
                            """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.setWhereExists(true);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(lang);
        stmt.setStatementOrderPart(" order by rev_completed_date desc ");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("rev_completed_date", StatementAndParams.PARAM_DATE, advancedParamList.get("rev_completed_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("coalesce(org1.c01, org2.c01)", StatementAndParams.PARAM_STRING, advancedParamList.get("org_type"));
        stmt.addParam4WherePart("coalesce(org1.org_name, org2.org_name)", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("rev_score", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("rev_score"));
        stmt.addParam4WherePart("rev_comment", StatementAndParams.PARAM_STRING, advancedParamList.get("rev_comment"));
        stmt.addParam4WherePart("rev_admin_read", StatementAndParams.PARAM_STRING, advancedParamList.get("rev_admin_read"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("coalesce(rfc1.rfc_meaning, rfc2.rfc_meaning)", "rev_score", "rev_comment", "org1.org_name",
                        "org2.org_name", "to_char(rev_completed_date,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        NtisReviewsForAdminListSecurityManager sqm = new NtisReviewsForAdminListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisReviewsForAdminList.ACTION_READ, NtisReviewsForAdminList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }
    
    public void markReviewAsRead(Connection conn, String id) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        NtisReviewsDAO reviewDAO = ntisReviewsDBService.loadRecord(conn, Utils.getDouble(id));
        reviewDAO.setRev_admin_read(YesNo.Y.getCode());
        ntisReviewsDBService.saveRecord(conn, reviewDAO);
    }

}
