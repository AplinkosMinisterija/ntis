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
import lt.project.ntis.logic.forms.security.NtisOwnerDisposalOrdersListSecurityManager;

/**
 * Klasė skirta formos "Užsakytų paslaugų sąrašas (išvežimas)"  biznio logikai apibrėžti
 */

@Component
public class NtisOwnerDisposalOrdersList extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Override
    public String getFormName() {
        return "NTIS_OWNER_DISPOSAL_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Užsakytų nuotekų išvežimo paslaugų sąrašas", "Užsakytų nuotekų išvežimo paslaugų sąrašo forma");
        addFormActions(ACTION_READ, ACTION_COPY);
    }

    public String getRecList(Connection conn, SelectRequestParams params, Double usrId, Double perId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOwnerDisposalOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        String coreFromPatrt;
        if (orgId != null) {
            coreFromPatrt = """
                                       where  (ord_org_id = ?::int or (ord_org_id is null and exists (select 1
                                        from ntis_facility_owners fo
                                  inner join spr_org_users ou on ou.ou_org_id = fo.fo_org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                                       where ord_wtf_id = fo.fo_wtf_id
                                         and fo_org_id = ?::int
                                         and fo_owner_type in ('OWNER','MANAGER')
                                         and current_date between fo_date_from and COALESCE(fo_date_to, current_date))))
                    """;
            stmt.addSelectParam(orgId);
            stmt.addSelectParam(usrId);
            stmt.addSelectParam(orgId);

        } else {
            coreFromPatrt = """
                                       where  (ord_per_id = ?::int or (ord.ord_per_id is null and exists (select 1
                                        from ntis_facility_owners fo
                                       where ord_wtf_id = fo.fo_wtf_id
                                         and fo_per_id = ?::int
                                         and fo_owner_type in ('OWNER','MANAGER')
                                         and current_date between fo_date_from and COALESCE(fo_date_to, current_date))))
                    """;

            stmt.addSelectParam(perId);
            stmt.addSelectParam(perId);
        }

        coreFromPatrt = coreFromPatrt + (params.getExtendedParams().isEmpty() ? " order by ord_created desc, ord_id desc limit 100 " : "");

        stmt.setStatement("SELECT ORD.ORD_ID , " + //
                "TO_CHAR(ORD.ORD_CREATED, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS ORD_CREATED, " + //
                "ORG.ORG_NAME, " + //
                "coalesce(RFC.RFC_MEANING, ORD.ORD_STATE) AS ORD_STATE, " + //
                "OCW.OCW_DISCHARGED_SLUDGE_AMOUNT, " + //
                "REV.REV_ID, " +//
                "REV.REV_SCORE, " +//
                "TO_CHAR(OCW.OCW_COMPLETED_DATE, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS OCW_COMPLETED_DATE " + //
                "FROM (select * from  NTIS_ORDERS ORD " + coreFromPatrt + ") ORD " + //
                "INNER JOIN NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID AND SRV.SRV_TYPE = '" + NtisServiceItemType.VEZIMAS + "' " + //
                "INNER JOIN SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = SRV.SRV_ORG_ID " + //
                "LEFT JOIN NTIS_ORDER_COMPLETED_WORKS OCW ON OCW.OCW_ORD_ID = ORD.ORD_ID " + //
                "INNER JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON WTF.WTF_ID = ORD.ORD_WTF_ID  " + //
                "LEFT JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = ORD.ORD_STATE AND RFC.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RFC.RFT_LANG = ? " +//
                "LEFT JOIN NTIS_REVIEWS REV ON REV.REV_ORD_ID = ORD.ORD_ID  ");

        // this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        stmt.addSelectParam(lang);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("ord_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ord_created", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ocw_discharged_sludge_amount", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ocw_discharged_sludge_amount"));
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
                dbStatementManager.colNamesToConcatString("ord_id", "org_name", "ocw_discharged_sludge_amount",
                        "TO_CHAR(ord_created,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(ocw_completed_date,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord_created desc, ord_id desc");
        NtisOwnerDisposalOrdersListSecurityManager sqm = new NtisOwnerDisposalOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisOwnerDisposalOrdersList.ACTION_READ, NtisOwnerDisposalOrdersList.ACTION_COPY };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
