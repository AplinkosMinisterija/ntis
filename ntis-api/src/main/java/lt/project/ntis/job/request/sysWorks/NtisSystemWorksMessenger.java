package lt.project.ntis.job.request.sysWorks;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.job.executor.BaseRequestLogicExecutor;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprSessionOpenDBServiceImpl;
import lt.project.ntis.dao.NtisSystemWorksDAO;
import lt.project.ntis.service.NtisSystemWorksDBService;

@Service("NTIS_SYSTEM_WORKS_MESSENGER")
public class NtisSystemWorksMessenger extends BaseRequestLogicExecutor {

    private static final Logger log = LoggerFactory.getLogger(NtisSystemWorksMessenger.class);

    @Autowired
    NtisSystemWorksDBService ntisSystemWorksDBService;

    @Autowired
    SprSessionOpenDBServiceImpl sprSessionOpenDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void executeSpecificLogic(Connection conn, String scriptName, SprJobRequestsDAO request) throws Exception {
        log.debug("Start checking if there are planned system works with message to be shown");
        StatementAndParams stmt = new StatementAndParams("""
                SELECT *
                    FROM ntis_system_works
                    WHERE now() between nsw_show_date_from and nsw_show_date_to
                      AND nsw_is_active = 'Y'
                      AND nsw_notification_sent = 'N'
                ORDER BY nsw_id desc
                   LIMIT 1
                """);
        List<NtisSystemWorksDAO> selectResult = this.baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, NtisSystemWorksDAO.class);
        NtisSystemWorksDAO sysWorksRecord = selectResult != null && selectResult.size() > 0 ? selectResult.get(0) : null;

        if (sysWorksRecord != null) {
            log.debug("Start executing logic of sending notifications after a system works record was found");
            List<RecordIdentifier> activeSessions = this.sprSessionOpenDBService.getActiveSessions(conn);
            if (activeSessions != null && !activeSessions.isEmpty()) {
                for (RecordIdentifier session : activeSessions) {
                    log.debug("Sending notification to session " + session.getId());
                    String address = "/message/listen-sys-works/" + session.getId();
                    simpMessagingTemplate.convertAndSend(address, sysWorksRecord);
                }
                log.debug("Updating system works record");
                sysWorksRecord.setNsw_notification_sent(YesNo.Y.getCode());
                this.ntisSystemWorksDBService.saveRecord(conn, sysWorksRecord);
                log.debug("NTIS_SYSTEM_WORKS_MESSENGER end");

            }

        }

    }

}
