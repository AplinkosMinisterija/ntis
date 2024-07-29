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
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;

/**
 * Klasė skirta formos "Tyrimų normų istorija" formos kodas T3130 biznio logikai apibrėžti
 */
@Component
public class NtisResearchNormsHistory extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "NTIS_RESEARCH_NORMS_HISTORY";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų normų istorija", "Tyrimų normų istorijos forma");
        addFormActions(ACTION_READ);
    }

    /**
     * Metodas grąžins pagrindinius tyrimų kriterijus ir kiekvienam kriterijui reglamentuotas istorines normas. 
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param lang - sesijos kalba
     * @return json objektas
     * @throws Exception
     */
    public String getResearchNormsHistory(Connection conn, SelectRequestParams params, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisResearchNormsHistory.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams(
                """
                        select
                           coalesce(t.rfc_meaning, rn_research_type) as research_type,
                           rn_research_norm as research_norm,
                           coalesce(i.rfc_meaning, rn_facility_installation_date) as facility_installation,
                           to_char(rn_date_from, '%s') as date_from,
                           to_char(rn_date_to , '%s') as date_to
                        from ntis_research_norms
                        left join spr_ref_codes_vw t on t.rfc_code = rn_research_type and t.rfc_domain = 'NTIS_RESEARCH_TYPE' and t.rft_lang = ?
                        left join spr_ref_codes_vw i on i.rfc_code = rn_facility_installation_date and i.rfc_domain = 'NTIS_WTF_INSTALL_PERIOD' and i.rft_lang = t.rft_lang
                                   """
                        .formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB),
                                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB)));
        stmt.addSelectParam(lang);
        stmt.setStatementOrderPart(" order by rn_research_type ");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        stmt.addParam4WherePart("rn_research_type", StatementAndParams.PARAM_STRING, advancedParamList.get("research_type"));
        stmt.addParam4WherePart("rn_research_norm", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("research_norm"));
        stmt.addParam4WherePart("rn_facility_installation_date", StatementAndParams.PARAM_STRING, advancedParamList.get("facility_installation"));
        stmt.addParam4WherePart("rn_date_from", StatementAndParams.PARAM_DATE, advancedParamList.get("date_from"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("rn_date_to", StatementAndParams.PARAM_DATE, advancedParamList.get("date_to"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("t.rfc_meaning", "rn_research_norm", "i.rfc_meaning",
                        "to_char(rn_date_from,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "to_char(rn_date_to,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        QueryResultSecurityManager<SprBackendUserSession> sqm = new QueryResultSecurityManager<SprBackendUserSession>();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisResearchNormsHistory.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
