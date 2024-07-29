package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.logic.forms.security.NtisCarsBrowseSecurityManager;

/**
 * Klasė skirta formos "Transporto priemonės" biznio logikai apibrėžti
 */
@Component
public class NtisCarsList extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Override
    public String getFormName() {
        return "CARS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "NTIS organizacijos transporto priemonės", "NTIS organizacijos transporto priemonės");
        addFormActionCRUD();
    }

    /**
     * Function will return a list of cars for provided organization id
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param orgId - organization id
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public String getCarsList(Connection conn, SelectRequestParams params, String lang, Double orgId, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisCarsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        select cr_id,
                             cr_reg_no,
                             cr_model,
                             cr_capacity,
                             cr_tube_length,
                             case when
                                COALESCE(cr_date_to, now())>=now()
                                then 'Y' else 'N'
                             end as cr_used
                        from ntis_cars
                        join spr_org_users on ou_org_id = cr_org_id and cr_org_id = ?::int and ou_usr_id = ?::int and CURRENT_DATE between ou_date_from and COALESCE(ou_date_to, now())
                        """);
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("cr_reg_no", StatementAndParams.PARAM_STRING, advancedParamList.get("cr_reg_no"));
        stmt.addParam4WherePart("cr_model", StatementAndParams.PARAM_STRING, advancedParamList.get("cr_model"));
        stmt.addParam4WherePart("cr_capacity", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("cr_capacity"));
        stmt.addParam4WherePart("cr_tube_length", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("cr_tube_length"));

        HashMap<String, String> paramList = params.getParamList();
        stmt.addParam4WherePart("case when COALESCE(cr_date_to, now())>=now() then 'Y' else 'N' end = ? ", paramList.get("p_cr_used"));

        stmt.addParam4WherePart(dbstatementManager.colNamesToConcatString("cr_reg_no", "cr_model", "cr_capacity", "cr_tube_length"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart(" order by cr_date_to nulls first, cr_date_from desc, cr_id desc ");
        NtisCarsBrowseSecurityManager sqm = new NtisCarsBrowseSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisCarsList.ACTION_READ, NtisCarsList.ACTION_UPDATE };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
