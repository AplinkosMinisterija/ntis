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
import lt.project.ntis.enums.NtisServiceReqStatus;
import lt.project.ntis.logic.forms.security.NtisServiceRequestsListSecurityManager;
import lt.project.ntis.service.NtisServiceRequestsDBService;

/**
 * Klasė skirta formos "Prašymų teikti paslaugas sąrašas (sistemos administratoriui)" biznio logikai apibrėžti
 */
@Component
public class NtisServiceRequestsList extends FormBase {

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    NtisServiceRequestsDBService ntisServiceRequestsDBService;

    @Override
    public String getFormName() {
        return "NTIS_SERVICE_REQUESTS_LIST";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Prašymų teikti paslaugas sąrašas", "Prašymų teikti paslaugas sąrašas");
        addFormActions(FormBase.ACTION_READ);
    }

    /**
     * Function will return a list of service requests
     * @param conn - connection to the DB
     * @param params - paging and search params
     * @param lang - language code, used for translation
     * @param usrId - user id
     * @return JSON object
     * @throws Exception
     */
    public String getRecList(Connection conn, SelectRequestParams params, String lang, Double usrId) throws Exception {
        this.checkIsFormActionAssigned(conn, NtisServiceRequestsList.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT SR_ID, " //
                + " TO_CHAR(SR_REGISTRATION_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') AS SR_REGISTRATION_DATE, " //
                + " SR_USR_ID, " //
                + " USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME AS SR_REQ_APPLICANT, " //
                + " SR_ORG_ID, " //
                + " ORG_NAME AS SR_SRV_PROVIDER, " //
                + " SR_STATUS, " //
                + " CASE " //
                + "     WHEN SR_STATUS IN ('" + NtisServiceReqStatus.REJECTED + "','" + NtisServiceReqStatus.CONFIRMED + "') " //
                + "     THEN TO_CHAR(SR_STATUS_DATE, '" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') " //
                + " END AS SR_STATUS_DATE, " //
                + " COALESCE(RCV.RFT_DISPLAY_CODE, RCV.RFC_MEANING) AS SR_STATUS_MEANING, " //
                + " SR_TYPE " //
                + " FROM NTIS_SERVICE_REQUESTS AS SR " //
                + " LEFT JOIN SPR_ORGANIZATIONS ON ORG_ID = SR_ORG_ID " //
                + " LEFT JOIN SPR_USERS ON USR_ID = SR_USR_ID " //
                + " LEFT JOIN SPR_REF_CODES_VW RCV ON RCV.RFC_CODE = SR_STATUS AND RCV.RFC_DOMAIN = 'NTIS_SRV_REQ_STATUS' AND RCV.RFT_LANG = ? ");
        HashMap<String, AdvancedSearchParameterStatement> advancedParamList = params.getAdvancedParameters();
        this.managePredefinedFilterStructure(conn, advancedParamList.get(PREDEFINED_FILTER_PARAM), usrId, stmt, advancedParamList);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(lang);
        stmt.addParam4WherePart("SR_ID", StatementAndParams.PARAM_DOUBLE, advancedParamList.get("sr_id"));
        stmt.addParam4WherePart("USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME", StatementAndParams.PARAM_STRING, advancedParamList.get("sr_req_applicant"));
        stmt.addParam4WherePart("ORG_NAME", StatementAndParams.PARAM_STRING, advancedParamList.get("sr_srv_provider"));
        stmt.addParam4WherePart("SR_STATUS", StatementAndParams.PARAM_STRING, advancedParamList.get("sr_status"));
        stmt.addParam4WherePart("SR_STATUS = ? ", paramList.get("p_sr_status"));
        stmt.addParam4WherePart("SR_STATUS_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("sr_status_date"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
        stmt.addParam4WherePart("SR_REGISTRATION_DATE", StatementAndParams.PARAM_DATE, advancedParamList.get("sr_registration_date"),
                dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));

        stmt.addParam4WherePart(
                dbstatementManager.colNamesToConcatString("SR_ID", "ORG_NAME", "USR_PERSON_NAME || ' ' || USR_PERSON_SURNAME",
                        "COALESCE(RCV.RFT_DISPLAY_CODE, RCV.RFC_MEANING)",
                        "TO_CHAR(SR_STATUS_DATE,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')",
                        "TO_CHAR(SR_REGISTRATION_DATE,'" + dbstatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')"),
                StatementAndParams.PARAM_STRING, advancedParamList.get("quickSearch"));

        stmt.setStatementOrderPart("order by sr_registration_date desc, sr_id desc");
        NtisServiceRequestsListSecurityManager sqm = new NtisServiceRequestsListSecurityManager();
        FormActions formActions = this.getFormActions(conn);
        String[] recordActionMenu = { NtisServiceRequestsList.ACTION_READ };
        formActions.prepareAvailableMenuAction(recordActionMenu);
        sqm.setFormActions(formActions);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }
}