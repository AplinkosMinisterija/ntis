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
import lt.project.ntis.logic.forms.security.NtisInspectorDisposalOrdersListSecurityManager;

@Component
public class NtisInspectorDisposalOrdersList extends FormBase {

    private final BaseControllerJDBC baseControllerJDBC;

    private final DBStatementManager dbStatementManager;

    public NtisInspectorDisposalOrdersList(BaseControllerJDBC baseControllerJDBC, DBStatementManager dbStatementManager) {
        super();
        this.baseControllerJDBC = baseControllerJDBC;
        this.dbStatementManager = dbStatementManager;
    }

    @Override
    public String getFormName() {
        return "NTIS_INSPECTOR_DISPOSAL_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Nuotekų išvežimo paslaugos užsakymai", "Nuotekų išvežimo paslaugos užsakymų sąrašo forma inspektoriaus rolei");
        addFormActions(FormBase.ACTION_READ);
    }

    public String getRecList(Connection conn, SelectRequestParams params, Double usrId, String lang, Double wtfId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisInspectorDisposalOrdersList.ACTION_READ);

        StatementAndParams stmt = new StatementAndParams("""
                    select ord.ord_id,
                           to_char(ord.ord_created, '%s') as ord_created,
                           org.org_name,
                           coalesce(rfc.rfc_meaning, ord.ord_state) as ord_state,
                           ocw.ocw_discharged_sludge_amount,
                           to_char(ocw.ocw_completed_date, '%s') as ocw_completed_date
                      from ntis.ntis_orders ord
                inner join ntis.ntis_services srv on srv.srv_id = ord.ord_srv_id and srv.srv_type = 'VEZIMAS'
                inner join spark.spr_organizations org on org.org_id = srv.srv_org_id
                left join ntis.ntis_order_completed_works ocw on ocw.ocw_ord_id = ord.ord_id
                inner join ntis.ntis_wastewater_treatment_faci wtf on wtf.wtf_id = ord.ord_wtf_id
                left join spark.spr_ref_codes_vw rfc on rfc.rfc_code = ord.ord_state and rfc.rfc_domain = 'NTIS_ORDER_STATUS' and rfc.rft_lang = ?
                where wtf.wtf_id = ?::int
                """.formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.setWhereExists(true);
        stmt.addSelectParam(lang);
        stmt.addSelectParam(wtfId);
        
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("ord_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("org_name", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ord_state", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_state"));
        stmt.addParam4WherePart("ord_created", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ocw_discharged_sludge_amount", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ocw_discharged_sludge_amount"));
        stmt.addParam4WherePart("ocw_completed_date", StatementAndParams.PARAM_DATE, advancedParamList.get("ocw_completed_date"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.setStatementOrderPart("order by ord_created desc, ord_id desc");
        NtisInspectorDisposalOrdersListSecurityManager sqm = new NtisInspectorDisposalOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisInspectorDisposalOrdersList.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
