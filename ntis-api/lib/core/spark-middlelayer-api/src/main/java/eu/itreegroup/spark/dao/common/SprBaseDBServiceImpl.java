package eu.itreegroup.spark.dao.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.query.SelectParamValue;

public abstract class SprBaseDBServiceImpl<DAO extends SprBaseDAO> implements SprBaseDBService<DAO> {

    private static final Logger log = LoggerFactory.getLogger(SprBaseDBServiceImpl.class);

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    protected DBPropertyManager dbPropertyManager;

    @Autowired(required = false)
    @Qualifier("sprCommonDbForeignKeyManager")
    DbForeignKeyManager sprCommonDbForeignKeyManager;

    @Autowired(required = false)
    @Qualifier("sprTasksDbForeignKeyManager")
    DbForeignKeyManager sprTasksDbForeignKeyManager;

    @Autowired(required = false)
    @Qualifier("projectDbForeignKeyManager")
    DbForeignKeyManager projectDbForeignKeyManager;

    @Override
    public S2RestRequestContext<BackendUserSession> getRequestContext() {
        return requestContext;
    }

    @Override
    public DBPropertyManager getDbPropertyManager() {
        return dbPropertyManager;
    }

    @Override
    public String getUserName() {
        try {
            return requestContext.getUserSession().getUsername();
        } catch (Exception ex) {
            return "system";
        }
    }

    @Override
    public BackendUserSession getUserSession() {
        return requestContext.getUserSession();
    }

    @Override
    public List<DAO> loadRecordsByParams(Connection conn, String dataFilterStr, Long longParam) throws Exception {
        return loadRecordsByParams(conn, dataFilterStr, new SelectParamValue(longParam));
    }

    @Override
    public List<DAO> loadRecordsByParams(Connection conn, String dataFilterStr, String stringParam) throws Exception {
        return loadRecordsByParams(conn, dataFilterStr, new SelectParamValue(stringParam));
    }

    @Override
    public List<DAO> loadRecordsByParams(Connection conn, String dataFilterStr, SelectParamValue... paramValues) throws Exception {
        if (!dataFilterStr.toLowerCase().trim().startsWith("where ")) {
            dataFilterStr = " WHERE " + dataFilterStr;
        }
        String stmt = getTableRecordStatement() + dataFilterStr;
        log.debug("load stmt: " + stmt);
        List<DAO> data;
        int idx = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
            for (SelectParamValue paramValue : paramValues) {
                paramValue.setValue(pstmt, ++idx);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                data = setDBDataToObject(rs);
            } catch (Exception ex1) {
                throw ex1;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return data;
    }

    @Override
    public DAO loadRecordByParams(Connection conn, String dataFilterStr, SelectParamValue... paramValues) throws Exception {
        List<DAO> data = loadRecordsByParams(conn, dataFilterStr, paramValues);
        if (data == null || data.isEmpty()) {
            return null;
        } else {
            return data.get(0);
        }
    }

    @Override
    public void manageForeignKeysOnDelete(Connection conn, String reference, Object identifier) throws Exception {
        if (sprCommonDbForeignKeyManager != null) {
            sprCommonDbForeignKeyManager.manageDeleteOperation(conn, reference, identifier);
        }
        if (sprTasksDbForeignKeyManager != null) {
            sprTasksDbForeignKeyManager.manageDeleteOperation(conn, reference, identifier);
        }
        if (projectDbForeignKeyManager != null) {
            projectDbForeignKeyManager.manageDeleteOperation(conn, reference, identifier);
        }
    }

    @Override
    public abstract String getTableRecordStatement();

    /**
     * Function will construct statement part according to the provided parameter. This function will be used for UK statement creation.
     * @param fieldName - field name that should be used for statement part construction
     * @param value - field value that will be checked by constructed statement
     * @return - constructed statement.
     */
    protected String constructStatementPart(String fieldName, Object value) {
        return (value != null ? fieldName + " = ? " : fieldName + " is null");
    }

    /**
     * Function will set value to the statement. If value not null function will set value to the statement
     * @param pstmt - statement that should be used for value set
     * @param idx - parameters count in statement, that was already set
     * @param value - value that should be set to the statement 
     * @return - current parameter count, that was set in provided statement
     * @throws Exception
     */
    protected int setValueToStatement(PreparedStatement pstmt, int idx, Object value) throws Exception {
        if (value != null) {
            pstmt.setObject(++idx, value);
        }
        return idx;
    }

    /**
     * Function will set value to the statement. If value not null function will set value to the statement
     * @param pstmt - statement that should be used for value set
     * @param idx - parameters count in statement, that was already set
     * @param value - value that should be set to the statement 
     * @return - current parameter count, that was set in provided statement
     * @throws Exception
     */
    protected int setValueToStatement(PreparedStatement pstmt, int idx, String value) throws Exception {
        if (value != null) {
            pstmt.setString(++idx, value);
        }
        return idx;
    }

    /**
     * Function will set value to the statement. If value not null function will set value to the statement
     * @param pstmt - statement that should be used for value set
     * @param idx - parameters count in statement, that was already set
     * @param value - value that should be set to the statement 
     * @return - current parameter count, that was set in provided statement
     * @throws Exception
     */
    protected int setValueToStatement(PreparedStatement pstmt, int idx, Date value) throws Exception {
        if (value != null) {
            pstmt.setObject(++idx, Utils.getSqlTimestamp(value));
        }
        return idx;
    }

    protected abstract List<DAO> setDBDataToObject(ResultSet rs) throws Exception;

    // @Override
    // public ArrayList<S2ViolatedConstraint> getViolatedConstraints(Connection conn, DAO daoObject) throws Exception {
    // return getViolatedConstraints(conn, daoObject, null);
    // }

}
