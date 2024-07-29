package eu.itreegroup.spark.modules.common.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.common.DbForeignKeyManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;

@Component("sprCommonDbForeignKeyManager")
public class SprCommonDbForeignKeyManagerImpl implements DbForeignKeyManager {

    private static final Logger log = LoggerFactory.getLogger(SprCommonDbForeignKeyManagerImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Override
    public void manageDeleteOperation(Connection conn, String foreignKeyName, Object identifier) throws Exception {
        log.debug("Start manageDeleteOperation");
        if (foreignKeyName.equals(SprUsersDAO.IDENTIFIER)) {
            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("delete from spr_notifications where ntf_usr_id = ? ");
            stmt.addSelectParam((Double) identifier);
            baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        }
    }
}
