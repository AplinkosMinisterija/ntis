package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.AdvancedSearchParameterStatement;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.logic.forms.security.NtisInspectorResearchOrdersSecurityManager;

@Component
public class NtisInspectorResearchOrdersList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    public NtisInspectorResearchOrdersList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
    }

    @Override
    public String getFormName() {
        return "NTIS_INSPECTOR_RESEARCH_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų sąrašas", "Tyrimų sąrašo forma inspektoriaus rolei");
        addFormActions(FormBase.ACTION_READ);
    }

    public String getResearchOrdersList(Connection conn, SelectRequestParams params, Double usrId, String lang, Double wtfId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOwnerDisposalOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                              select ord.ord_id,
                                     to_char(ord.ord_created, '%s') as ord_created,
                                     coalesce(rfc.rfc_meaning, ord.ord_state) as ord_state,
                                     coalesce(norms.rfc_meaning, ord.ord_compliance_norms) as ord_compliance_norms,
                                     coalesce(rfco.rfc_meaning, org.org_type || ' ' || org.org_name, org.org_name) as org_name
                              from ntis.ntis_orders ord
                        inner join ntis.ntis_services srv on srv.srv_id = ord.ord_srv_id and srv.srv_type = 'TYRIMAI'
                        inner join spark.spr_organizations org on org.org_id = srv.srv_org_id
                        inner join ntis.ntis_wastewater_treatment_faci wtf on wtf.wtf_id = ord.ord_wtf_id
                        left join spark.spr_ref_codes_vw rfc on rfc.rfc_code = ord.ord_state and rfc.rfc_domain = 'NTIS_ORDER_STATUS' and rfc.rft_lang = ?
                        left join spark.spr_ref_codes_vw norms on norms.rfc_code = ord.ord_compliance_norms and norms.rfc_domain = 'NTIS_ORD_RESEARCH_NORMS' and norms.rft_lang = rfc.rft_lang
                        left join spark.spr_ref_codes_vw rfco on rfco.rfc_code = org.org_type and rfco.rfc_domain = 'SPR_ORG_TYPE' and rfco.rft_lang = rfc.rft_lang
                            where wtf.wtf_id = ?::int
                              and ord.ord_type = 'ORD_TYPE_ANALYSIS'
                                              """
                        .formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.setWhereExists(true);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wtfId);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();

        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("ord.ord_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("org.org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ord.ord_compliance_norms", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_compliance_norms"));
        stmt.addParam4WherePart("ord.ord_state", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_state"));
        stmt.addParam4WherePart("ord.ord_created", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ord_id", "org_name", "rfc.rfc_meaning", "norms.rfc_meaning",
                        "TO_CHAR(ord_created,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord_created desc, ord_id desc");
        NtisInspectorResearchOrdersSecurityManager sqm = new NtisInspectorResearchOrdersSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisInspectorResearchOrdersList.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
