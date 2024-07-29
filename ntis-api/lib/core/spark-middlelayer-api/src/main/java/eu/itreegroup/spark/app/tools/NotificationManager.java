package eu.itreegroup.spark.app.tools;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.service.SprSessionOpenDBService;
import eu.itreegroup.spark.modules.common.dao.SprNotificationsDAO;

@Service
public class NotificationManager {

    private static final Logger log = LoggerFactory.getLogger(NotificationManager.class);

    @Autowired
    SprSessionOpenDBService sprSessionOpenDBService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(Connection conn, SprNotificationsDAO notificationDAO) throws Exception {
        Date currentDate = Utils.getDate(new Date());
        if ((Utils.getDate(notificationDAO.getNtf_date_from()).after(currentDate) || Utils.getDate(notificationDAO.getNtf_date_from()).equals(currentDate))
                && (notificationDAO.getNtf_date_to() == null || Utils.getDate(notificationDAO.getNtf_date_to()).before(currentDate))) {
            List<RecordIdentifier> sessionList = sprSessionOpenDBService.getSessionsKeysByUserData(conn, notificationDAO.getNtf_usr_id(),
                    notificationDAO.getNtf_org_id(), notificationDAO.getNtf_rol_id());
            if (sessionList != null) {
                for (RecordIdentifier identifier : sessionList) {
                    log.debug("Send notification to session: " + identifier.getId());
                    String address = "/message/listen-messages/" + identifier.getId();
                    simpMessagingTemplate.convertAndSend(address, notificationDAO);
                }
            }
        }
    }
}
