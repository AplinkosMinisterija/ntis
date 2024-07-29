package eu.itreegroup.spark.dao.common;

import java.sql.Connection;
import java.util.List;

import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.query.SelectParamValue;

public interface SprBaseDBService<DAO extends SprBaseDAO> {

    public S2RestRequestContext<BackendUserSession> getRequestContext();

    public DBPropertyManager getDbPropertyManager();

    public String getUserName();

    public BackendUserSession getUserSession();

    public List<DAO> loadRecordsByParams(Connection conn, String dataFilterStr, Long longParam) throws Exception;

    public List<DAO> loadRecordsByParams(Connection conn, String dataFilterStr, String stringParam) throws Exception;

    public List<DAO> loadRecordsByParams(Connection conn, String dataFilterStr, SelectParamValue... paramValues) throws Exception;

    public DAO loadRecordByParams(Connection conn, String dataFilterStr, SelectParamValue... paramValues) throws Exception;

    public String getTableRecordStatement();

    public DAO newRecord();

    public DAO object4ForceUpdate();

    public DAO loadRecord(Connection conn, Object identifier) throws Exception;

    public void deleteRecord(Connection conn, Double identifier) throws Exception;

    public void deleteRecord(Connection conn, DAO daoObject) throws Exception;

    public void validateConstraints(Connection conn, DAO daoObject, String constraintName) throws Exception;

    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, DAO daoObject, String constraintName) throws Exception;

    public DAO insertRecord(Connection conn, DAO daoObject) throws Exception;

    public DAO updateRecord(Connection conn, DAO daoObject) throws Exception;

    public DAO saveRecord(Connection conn, DAO daoObject) throws Exception;

    public void manageForeignKeysOnDelete(Connection conn, String reference, Object identifier) throws Exception;

}
