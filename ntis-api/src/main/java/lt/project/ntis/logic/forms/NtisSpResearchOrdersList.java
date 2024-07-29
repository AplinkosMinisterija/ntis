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
import lt.project.ntis.logic.forms.security.NtisSpResearchOrdersListSecurityManager;

/**
 * Klasė skirta formos "Tyrimų sąrašas (Laboratorijoms)" formos kodas T3150 biznio logikai apibrėžti
 */
@Component
public class NtisSpResearchOrdersList extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJdbc;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "NTIS_SP_RESEARCH_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų sąrašas", "Tyrimų sąrašo forma laboratorijai");
        addFormActions(FormBase.ACTION_READ);
    }

    /**
     * Metodas grąžins paslaugų teikėjo / laboratorijos gautų tyrimo paslaugų užsakymų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param usrId - sesijos naudotojo id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param usrId - naudotojo sesijos id
     * @param lang - sesijoje naudojama kalba
     * @return json objektas
     * @throws Exception
     */
    public String getResearchOrdersList(Connection conn, SelectRequestParams params, Double orgId, Double usrId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOwnerDisposalOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                            ord.ord_id,
                            to_char(ord.ord_created, '%s') as ord_created,
                            coalesce(r1.rfc_meaning, ord.ord_created_in_ntis_portal) as ord_created_in_ntis_portal,
                            coalesce(r2.rfc_meaning, ord.ord_state) as ord_state,
                            ord.ord_state as ord_state_clsf,
                            coalesce(ord.c01, coalesce(per.per_name || ' ' || per.per_surname, org.org_name)) as orderer,
                            ad.ad_address as address
                            from ntis_orders ord
                           left join spr_organizations org on org.org_id = ord.ord_org_id
                           left join spr_persons per on per.per_id = ord.ord_per_id
                           inner join ntis_services srv on ord.ord_srv_id = srv.srv_id and srv.srv_type = 'TYRIMAI'
                           inner join spr_organizations sp on sp.org_id = srv.srv_org_id and srv.srv_org_id = ?::int
                           inner join spr_org_users ou on ou.ou_org_id = sp.org_id and ou.ou_usr_id = ?::int and current_date between ou.ou_date_from and coalesce(ou.ou_date_to, now())
                           left join spr_ref_codes_vw r1 on r1.rfc_code = ord.ord_created_in_ntis_portal and r1.rfc_domain = 'NTIS_ORDER_METHOD' and r1.rft_lang = ?
                           left join spr_ref_codes_vw r2 on r2.rfc_code = ord.ord_state and r2.rfc_domain = 'NTIS_ORDER_STATUS' and r2.rft_lang = r1.rft_lang
                           left join ntis_wastewater_treatment_faci wtf on ord.ord_wtf_id = wtf.wtf_id
                           left join ntis_adr_addresses ad on wtf.wtf_ad_id = ad.ad_id
                         """
                        .formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(orgId);
        stmt.addSelectParam(usrId);
        stmt.addSelectParam(lang);
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();

        stmt.addParam4WherePart("ord_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("ord_created", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ord_created_in_ntis_portal", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_created_in_ntis_portal"));
        stmt.addParam4WherePart("ord_state", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_state"));
        stmt.addParam4WherePart("coalesce(per.per_name || ' ' || per.per_surname, org.org_name)", StatementAndParams.PARAM_STRING,
                advancedParamList.get("orderer"));
        stmt.addParam4WherePart("ad_address", StatementAndParams.PARAM_STRING, advancedParamList.get("address"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ord_id", "coalesce(r1.rfc_meaning, ord_created_in_ntis_portal)",
                        "coalesce(r2.rfc_meaning, ord_state)", "coalesce(per.per_name || ' ' || per.per_surname, org.org_name)",
                        "to_char(ord_created,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')", "ad_address"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord_created desc, ord_id desc");
        NtisSpResearchOrdersListSecurityManager sqm = new NtisSpResearchOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { FormBase.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJdbc.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
