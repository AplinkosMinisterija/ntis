package lt.project.ntis.app.job.executor.impl;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import lt.project.ntis.enums.NtisOrgState;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.logic.forms.processRequests.WtcReminderProcessRequest;

/**
 * Suplanuoto darbo klasė, siunčianti kasmėnesinius pranešimus nuotekų tvarkytojams.
 */
@Service("WTC_REMINDER_EMAIL_GENERATOR")
public class WtcReminderEmailExecutor extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(WtcReminderEmailExecutor.class);

    @Autowired
    WtcReminderProcessRequest wtcReminderProcessRequest;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        createReminderRequests(conn);
    }

    private void createReminderRequests(Connection conn) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                  SELECT
                      org.org_id,
                      org.org_email
                 FROM spr_organizations org
                WHERE org.c01 in (?, ?)
                  AND org.n01 = ?
                  AND org.org_id NOT IN (
                      SELECT
                         cwf_org_id
                      FROM ntis_cw_files
                     WHERE cwf_status = 'CW_FIL_FINAL'
                       AND cwf_import_date BETWEEN
                           date_trunc('month', now()) AND
                           date_trunc('month', now()) + interval '1 month - 1 day')
                                  """);
        stmt.addSelectParam(NtisOrgType.VANDEN.getCode());
        stmt.addSelectParam(NtisOrgType.PASLAUG_VANDEN.getCode());
        stmt.addSelectParam(Utils.getDouble(NtisOrgState.REGISTERED.getCode()));
        List<SprOrganizationsDAO> wtcList = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprOrganizationsDAO.class);

        for (SprOrganizationsDAO wtc : wtcList) {
            if (wtc.getOrg_email() != null && !wtc.getOrg_email().isBlank()) {
                wtcReminderProcessRequest.createReminderRequest(conn, null, wtc.getOrg_id(), wtc.getOrg_email(), Languages.LT);
            }
        }
    }
}
