package lt.project.common;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.dao.common.DbForeignKeyManager;

@Component("projectDbForeignKeyManager")
public class ProjectDbForeignKeyManagerImpl implements DbForeignKeyManager {

    private static final Logger log = LoggerFactory.getLogger(ProjectDbForeignKeyManagerImpl.class);

    @Override
    public void manageDeleteOperation(Connection conn, String foreignKeyName, Object identifier) {
        log.debug("Start manageDeleteOperation");

    }

}
