package eu.itreegroup.spark.modules.tasks.logic.forms;

import java.sql.Connection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.model.FormActions;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.tasks.logic.forms.security.SpTasksBrowseSecurityManager;

@Component
public class SprTasksBrowse extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprTasksBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_TASKS_LIST";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark tasks list", "Spark task list");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params, String lang) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprTasksBrowse.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT TAS_ID AS ID, "//
                + "TAS_NAME, " //
                + "coalesce(RTT.RFT_DISPLAY_CODE, TAS_TYPE) as TAS_TYPE, "//
                + "coalesce(RTS.RFT_DISPLAY_CODE, TAS_STATUS) as TAS_STATUS, "//
                + "TO_CHAR(TAS_DATE_FROM, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') as TAS_DATE_FROM, "//
                + "TO_CHAR(TAS_DATE_TO, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') as TAS_DATE_TO, "//
                + "TAS_REJECT_REASON, "//
                + "TAS_PRIORITY, "//
                + "TAS_DESCRIPTION, "//
                + "TAS_TAS_ID, "//
                + "TAS_USR_ID, "//
                + "(SELECT COUNT(*) FROM SPR_TASKS T2 WHERE T2.TAS_TAS_ID = T1.TAS_ID) SUBTASK_COUNT "//
                + "FROM SPR_TASKS T1 " //
                + " INNER JOIN SPR_REF_CODES RCT ON RCT.RFC_CODE = TAS_TYPE AND RCT.RFC_DOMAIN = 'SPR_TASK_CATEGORY'"//
                + " INNER JOIN SPR_REF_TRANSLATIONS RTT ON RTT.RFT_RFC_ID = RCT.RFC_ID" //
                + " INNER JOIN SPR_REF_CODES RCS ON RCS.RFC_CODE = TAS_STATUS AND RCS.RFC_DOMAIN = 'SPR_TASK_STATUS'"//
                + " INNER JOIN SPR_REF_TRANSLATIONS RTS ON RTS.RFT_RFC_ID = RCS.RFC_ID");

        stmt.addParam4WherePart("RTT.RFT_LANG = ? ", lang);
        stmt.addParam4WherePart("RTS.RFT_LANG = ? ", lang);

        HashMap<String, String> paramList = params.getParamList();
        stmt.addParam4WherePart("upper("
                + dbStatementManager.colNamesToConcatString("TAS_NAME", "TAS_ID", "TAS_TYPE", "TAS_STATUS",
                        "TO_CHAR(TAS_DATE_FROM,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')",
                        "TO_CHAR(TAS_DATE_TO,'" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "')")
                + ") like upper('%'||?||'%')", paramList.get("p_quickSearch"));

        SpTasksBrowseSecurityManager sqm = new SpTasksBrowseSecurityManager();
        sqm.setFormActions(this.getFormActions(conn));
        FormActions formActions = this.getFormActions(conn);
        formActions.prepareAvailableMenuAction(SprTasksBrowse.DEFAULT_ACTIONS_ORDER);
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
