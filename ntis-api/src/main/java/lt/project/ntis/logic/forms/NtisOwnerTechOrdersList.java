package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.security.NtisOwnerTechOrdersListSecurityManager;

/**
 * Klasė skirta formos "Užsakytų paslaugų sąrašas (tech. priež.)"  biznio logikai apibrėžti
 */

@Component
public class NtisOwnerTechOrdersList extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Override
    public String getFormName() {
        return "NTIS_OWNER_TECH_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Užsakytų techninės priežiūros paslaugų sąrašas", "Užsakytų techninės priežiūros paslaugų sąrašo forma");
        addFormActions(ACTION_READ, ACTION_COPY);
    }

    public String getRecList(Connection conn, SelectRequestParams params, Double usrId, Double perId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOwnerTechOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT ORD.ORD_ID, " + //
                "TO_CHAR(ORD.ORD_CREATED, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS ORD_CREATED, " + //
                "ORG.ORG_NAME, " + //
                "coalesce(RFC.RFC_MEANING, ORD.ORD_STATE) AS ORD_STATE, " + //
                "REV.REV_ID, " +//
                "REV.REV_SCORE, " +//
                "TO_CHAR(OCW.OCW_COMPLETED_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS OCW_COMPLETED_DATE " + //
                "FROM NTIS_ORDERS ORD " + //
                "INNER JOIN NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID AND SRV.SRV_TYPE = '" + NtisServiceItemType.PRIEZIURA + "' " + //
                "INNER JOIN SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = SRV.SRV_ORG_ID " + //
                "LEFT JOIN NTIS_ORDER_COMPLETED_WORKS OCW ON OCW.OCW_ORD_ID = ORD.ORD_ID " + //
                "INNER JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON WTF.WTF_ID = ORD.ORD_WTF_ID " + //
                "LEFT JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = ORD.ORD_STATE AND RFC.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RFC.RFT_LANG = ? " + //
                "LEFT JOIN NTIS_REVIEWS REV ON REV.REV_ORD_ID = ORD.ORD_ID ");
        this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        stmt.addSelectParam(lang);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("ord_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ord_created", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ocw_completed_date", StatementAndParams.PARAM_DATE, advancedParamList.get("ocw_completed_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        HashMap<String, String> paramList = params.getParamList();

        Double wtfId = null;
        if (advancedParamList.get("wtf_id") != null && advancedParamList.get("wtf_id").getParamValue() != null
                && advancedParamList.get("wtf_id").getParamValue().getValue() != null) {
            wtfId = Utils.getDouble(advancedParamList.get("wtf_id").getParamValue().getValue());
        }

        stmt.addParam4WherePart("ord_wtf_id = ?::int", wtfId);
        stmt.addParam4WherePart("ord.ord_state = ?", paramList.get("p_ord_state"));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ord_id", "org_name",
                        "TO_CHAR(ord_created,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(ocw_completed_date,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord_created desc, ord_id desc");
        NtisOwnerTechOrdersListSecurityManager sqm = new NtisOwnerTechOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisOwnerTechOrdersList.ACTION_READ, NtisOwnerTechOrdersList.ACTION_COPY };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
