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
import lt.project.common.NtisCommonMethods;
import lt.project.ntis.constants.NtisOrderTypeConstants;
import lt.project.ntis.enums.NtisServiceItemType;
import lt.project.ntis.logic.forms.security.NtisOwnerResearchOrdersListSecurityManager;

/**
 * Klasė skirta formos "Forma Tyrimų sąrašas (INTS savininkams/ valdytojams)" (formos kodas T3140) biznio logikai apibrėžti
 */

@Component
public class NtisOwnerResearchOrdersList extends FormBase {

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    NtisCommonMethods ntisCommonMethods;

    @Override
    public String getFormName() {
        return "NTIS_OWNER_RESEARCH_ORDERS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Tyrimų sąrašas", "Užsakytų tyrimų sąrašo forma");
        addFormActions(ACTION_READ);
    }

    /**
     * Metodas grąžins ints savininko/valdytojo užsakytų tyrimo paslaugų sąrašą
     * @param conn - prisijungimas prie DB
     * @param params - select parametrai
     * @param usrId - sesijos naudotojo id
     * @param orgId - naudotojo organizacijios id, kuriai priklauso dirbantis naudotojas
     * @param perId - nuoroda į asmens informaciją, kuriai yra priskirtas prisijungęs naudotojas.
     * @param lang - sesijoje naudojama kalba
     * @return json objektas
     * @throws Exception
     */
    public String getResearchOrdersList(Connection conn, SelectRequestParams params, Double usrId, Double perId, Double orgId, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisOwnerDisposalOrdersList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(
                """
                        SELECT ORD.ORD_ID,
                               TO_CHAR(ORD.ORD_CREATED, '%s') AS ORD_CREATED,
                               coalesce(RFC.RFC_MEANING, ORD.ORD_STATE) AS ORD_STATE,
                               coalesce(NORMS.RFC_MEANING, ORD.ORD_COMPLIANCE_NORMS) AS ORD_COMPLIANCE_NORMS,
                               coalesce(coalesce(RFCO.RFC_MEANING, ORG.ORG_TYPE) || ' ' || ORG.ORG_NAME, ORG.ORG_NAME) AS ORG_NAME,
                               REV.REV_ID,
                               REV.REV_SCORE
                        FROM NTIS_ORDERS ORD
                        INNER JOIN NTIS_SERVICES SRV ON SRV.SRV_ID = ORD.ORD_SRV_ID AND SRV.SRV_TYPE = '%s'
                        INNER JOIN SPR_ORGANIZATIONS ORG ON ORG.ORG_ID = SRV.SRV_ORG_ID
                        INNER JOIN NTIS_WASTEWATER_TREATMENT_FACI WTF ON WTF.WTF_ID = ORD.ORD_WTF_ID
                        JOIN SPR_REF_CODES_VW RFC ON RFC.RFC_CODE = ORD.ORD_STATE AND RFC.RFC_DOMAIN = 'NTIS_ORDER_STATUS' AND RFC.RFT_LANG = ?
                        LEFT JOIN SPR_REF_CODES_VW NORMS ON NORMS.RFC_CODE = ORD.ORD_COMPLIANCE_NORMS AND NORMS.RFC_DOMAIN = 'NTIS_ORD_RESEARCH_NORMS' AND NORMS.RFT_LANG = RFC.RFT_LANG
                        LEFT JOIN SPR_REF_CODES_VW RFCO ON RFCO.RFC_CODE = ORG.ORG_TYPE AND RFCO.RFC_DOMAIN = 'SPR_ORG_TYPE' AND RFCO.RFT_LANG = RFC.RFT_LANG
                        LEFT JOIN NTIS_REVIEWS REV ON REV.REV_ORD_ID = ORD.ORD_ID
                                        """
                        .formatted(dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB), NtisServiceItemType.TYRIMAI));
        stmt.addParam4WherePart(" ORD.ORD_TYPE = ? ", NtisOrderTypeConstants.ANALYSIS);
        this.ntisCommonMethods.addConditionForOwner(stmt, orgId, perId, usrId);
        stmt.addSelectParam(lang);

        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();

        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        stmt.addParam4WherePart("ORD.ORD_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("ord_id"));
        stmt.addParam4WherePart("ORG.ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("org_name"));
        stmt.addParam4WherePart("ORD.ORD_COMPLIANCE_NORMS", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_compliance_norms"));
        stmt.addParam4WherePart("ORD.ORD_STATE", StatementAndParams.PARAM_STRING, advancedParamList.get("ord_state"));
        stmt.addParam4WherePart("ORD.ORD_CREATED", StatementAndParams.PARAM_DATE, advancedParamList.get("ord_created"),
                dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("ord_wtf_id", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("wtf_id"));

        stmt.addParam4WherePart(
                dbStatementManager.colNamesToConcatString("ord_id", "org_name", "rfc.rfc_meaning", "norms.rfc_meaning",
                        "TO_CHAR(ord_created,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by ord_created desc, ord_id desc");
        NtisOwnerResearchOrdersListSecurityManager sqm = new NtisOwnerResearchOrdersListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisOwnerResearchOrdersList.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return baseControllerJDBC.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}
