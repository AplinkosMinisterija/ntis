package eu.itreegroup.spark.dao.common;

import java.sql.Connection;

public interface DbForeignKeyManager {

    public void manageDeleteOperation(Connection conn, String foreignKeyName, Object identifier) throws Exception;
}
