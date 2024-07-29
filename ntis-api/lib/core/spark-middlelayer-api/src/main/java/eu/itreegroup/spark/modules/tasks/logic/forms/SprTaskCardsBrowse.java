package eu.itreegroup.spark.modules.tasks.logic.forms;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.tasks.logic.forms.security.SprTaskCardsBrowseSecurityManager;

@Component
public class SprTaskCardsBrowse extends FormBase {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprTaskCardsBrowse.class);

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    S2RestRequestContext<BackendUserSession> requestContext;

    @Override
    public String getFormName() {
        return "SPR_TASKS_CARDS";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark tasks list", "Spark task list");
        addFormActions(FormBase.ACTION_READ);
    }

    public String getTaskCards(Connection conn, Double userId, String language, SelectRequestParams params) throws Exception {
        checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        StatementAndParams stmt = new StatementAndParams();
        HashMap<String, String> paramList = params.getParamList();
        if (paramList.get("filterByUser") != null && paramList.get("filterByUser") == "true") {
            stmt.setStatement("" //
                    + "select t.tas_id, t.tas_name, t.tas_usr_id, t.tas_description, t.tas_status, t.tas_priority, t.tas_date_to, " //
                    + "       case t.tas_status " //
                    + "         when 'COMPLETED' then 1.0  " //
                    + "         ELSE 0.0  END progress, " //
                    + "        u.usr_person_name creator_name,  " //
                    + "        u.usr_person_surname creator_surname,  " //
                    + "        t.tas_usr_id creator_user_id,  " //
                    + " CASE count(a.tat_usr_id) WHEN 0 THEN '[]' ELSE " //
                    + "'['||" + dbStatementManager.getListAggFunctionName()
                    + "('{\"user_id\":'||(a.tat_usr_id)||', \"name\":\"'||(trim(u2.usr_person_name))||'\", \"surname\":\"'||(trim(u2.usr_person_surname))||'\"}',',')||']' END assignees, "
                    + "        we.wfe_id workflow_execute_id " //
                    + "  from spr_tasks t " //
                    + "  inner join spr_task_assignments ma " //
                    + "    on ma.tat_tas_id = t.tas_id "//
                    + "  left join spr_task_assignments a " //
                    + "    on a.tat_tas_id = t.tas_id "//
                    + "  left join spr_users u " //
                    + "    on t.tas_usr_id = u.usr_id " //
                    + "  left join spr_users u2 " //
                    + "    on a.tat_usr_id = u2.usr_id " //
                    + "  left join spr_wf_executions we " //
                    + "    on we.wfe_tas_id = t.tas_id " //
                    + " where ma.tat_usr_id = ? ");
            stmt.setWhereExists(true);
            stmt.addSelectParam(userId);
        } else {
            stmt.setStatement("" //
                    + "select t.tas_id, t.tas_name, t.tas_usr_id, t.tas_description, t.tas_status, t.tas_priority, t.tas_date_to, " //
                    + "       case t.tas_status " //
                    + "         when 'COMPLETED' then 1.0  " //
                    + "         ELSE 0.0  END progress, " //
                    + "        u.usr_person_name creator_name,  " //
                    + "        u.usr_person_surname creator_surname,  " //
                    + "        t.tas_usr_id creator_user_id,  " //
                    + " CASE count(a.tat_usr_id) WHEN 0 THEN '[]' ELSE " //
                    + "'['||" + dbStatementManager.getListAggFunctionName()
                    + "('{\"user_id\":'||(a.tat_usr_id)||', \"name\":\"'||(trim(u2.usr_person_name))||'\", \"surname\":\"'||(trim(u2.usr_person_surname))||'\"}',',')||']' END assignees, "
                    + "        we.wfe_id workflow_execute_id " //
                    + "  from spr_tasks t " //
                    + "  left join spr_task_assignments a " //
                    + "    on a.tat_tas_id = t.tas_id "//
                    + "  left join spr_users u " //
                    + "    on t.tas_usr_id = u.usr_id " //
                    + "  left join spr_users u2 " //
                    + "    on a.tat_usr_id = u2.usr_id " + "  left join spr_wf_executions we " //
                    + "    on we.wfe_tas_id = t.tas_id ");
        }
        stmt.setStatementGroupPart("  group by t.tas_id, t.tas_name, t.tas_usr_id, t.tas_description, t.tas_status, t.tas_priority, t.tas_date_to, " //
                + "       case t.tas_status " //
                + "         when 'COMPLETED' then 1.0 " //
                + "         ELSE 0.0 END, " //
                + "        u.usr_person_name,  " //
                + "        u.usr_person_surname,  " //
                + "        t.tas_usr_id,  " //
                + "        we.wfe_id ");

        stmt.addParam4WherePart(" upper(t.tas_name) like upper('%'||?||'%') ", paramList.get("p_tas_name"));
        stmt.addJsonColumn("assignees");

        // filter only current user tasks:

        // filter buttons (tabs)
        if (paramList.get("filter") != null) {
            switch (paramList.get("filter")) {
                case "completed": {
                    stmt.addParam4WherePart(" t.tas_status = ? ", "COMPLETED");
                    break;
                }
                case "new": {
                    String currentDate = Utils.getStringFromDate(new Date(), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
                    stmt.addParam4WherePart(
                            " t.rec_create_timestamp >= to_date(?, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') ",
                            currentDate);
                    stmt.addParam4WherePart(
                            " t.rec_create_timestamp < (to_date(?, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')+1) ",
                            currentDate);
                    break;
                }
                case "previous": {
                    String currentDateTime = Utils.getStringFromDate(new Date(),
                            dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
                    stmt.addParam4WherePart(
                            " t.tas_date_to < to_date(?, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') ",
                            currentDateTime);
                    break;
                }
                case "todays": {
                    String currentDate = Utils.getStringFromDate(new Date(), dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA));
                    stmt.addParam4WherePart(
                            " t.tas_date_from < (to_date(?, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "')+1) ",
                            currentDate);
                    stmt.addParam4WherePart(" t.tas_date_to >= to_date(?, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_DB) + "') ",
                            currentDate);
                    break;
                }
                case "upcoming": {
                    String currentDateTime = Utils.getStringFromDate(new Date(),
                            dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_JAVA));
                    stmt.addParam4WherePart(
                            " t.tas_date_from > to_date(?, '" + dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_TIME_DB) + "') ",
                            currentDateTime);
                    break;
                }
            }
        }

        // filter for day timeline view (by specific tas_date_to):
        if (paramList.get("filterByDateTo") != null) {
            stmt.addParam4WherePart(" t.tas_date_to >= to_date(?, 'YYYY-MM-DD') ", paramList.get("filterByDateTo"));
            stmt.addParam4WherePart(" t.tas_date_to < (to_date(?, 'YYYY-MM-DD')+1) ", paramList.get("filterByDateTo"));
        }

        // // set minimum tas_date_from:
        // if (paramList.get("minDateFrom") != null) {
        // stmt.addParam4WherePart(" t.tas_date_from >= to_date(?, 'YYYY-MM-DD') ", paramList.get("minDateFrom"));
        // }
        //
        // // set maximum tas_date_to:
        // if (paramList.get("maxDateTo") != null) {
        // stmt.addParam4WherePart(" t.tas_date_to < (to_date(?, 'YYYY-MM-DD')+1) ", paramList.get("maxDateTo"));
        // }

        SprTaskCardsBrowseSecurityManager sqm = new SprTaskCardsBrowseSecurityManager();
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, params, sqm);
    }

}
