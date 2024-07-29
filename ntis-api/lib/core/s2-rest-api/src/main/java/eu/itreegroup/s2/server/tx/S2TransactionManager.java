package eu.itreegroup.s2.server.tx;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import eu.itreegroup.s2.client.exception.SparkRuntimeException;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;

public abstract class S2TransactionManager<T extends BackendUserSession> extends DataSourceTransactionManager {

    protected S2RestRequestContext<T> requestContext;

    /**
     * Init DB session before server service call.
     * Method should start with 'resetPackageState(conn);' and must be finished with 'conn.commit();'
     * Method return Object because user session bean can be different in projects.
     * @param conn connection
     * @return User session bean. as object.
     * @throws Exception
     */
    protected abstract T initDBSession(Connection conn) throws Exception;

    /**
     * Clear DB session state after server service call.
     * Method must be finished with 'conn.commit();'
     * @param conn connection
     * @throws Exception
     */
    protected abstract void clearDBSession(Connection conn) throws Exception;

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(S2TransactionManager.class);

    @Override
    protected Object doGetTransaction() {
        boolean isNewConnectionHolder = TransactionSynchronizationManager.getResource(getDataSource()) == null;
        Object txObject = super.doGetTransaction();
        TransactionSynchronizationManager.bindResource(txObject, Boolean.valueOf(isNewConnectionHolder));
        return txObject;
    }

    @Override
    protected void doBegin(Object txObject, TransactionDefinition definition) {
        try {
            super.doBegin(txObject, definition);
        } catch (Throwable t) {
            RuntimeException runtimeException = new RuntimeException("dbNotAvailableError");
            runtimeException.initCause(t);
            throw runtimeException;
        }

        if (isNewConnectionHolder(txObject)) {
            initSession(txObject);
        } else {
            log.debug("====== Skipping init_XXX_session() call");
        }
    }

    @Override
    protected void doCleanupAfterCompletion(Object txObject) {
        if (isNewConnectionHolder(txObject)) {
            clearSession(txObject);
        }
        TransactionSynchronizationManager.unbindResource(txObject);
        super.doCleanupAfterCompletion(txObject);
    }

    private boolean isNewConnectionHolder(Object txObject) {
        Object status = TransactionSynchronizationManager.getResource(txObject);
        return status == null || ((Boolean) status).booleanValue();
    }

    private void initSession(Object txObject) {
        T userSession = null;
        try {
            ConnectionHolder connHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(getDataSource());
            Connection conn = connHolder.getConnection();
            userSession = initDBSession(conn);
            // update user session by DB
            requestContext.setUserSession(userSession);
        } catch (SQLRecoverableException t) {
            // EP: Don't need to mark DB as DOWN, just show DB not available message to user.
            // Caused by: java.sql.SQLRecoverableException: Closed Connection.
            super.doCleanupAfterCompletion(txObject);
            // log.error("DB ACCESS: init user session failed " + userSessionBean, t);
            throw new RuntimeException("dbNotAvailableError");
        } catch (SparkRuntimeException ex) {
            super.doCleanupAfterCompletion(txObject);
            throw ex;
        } catch (Throwable t) {
            super.doCleanupAfterCompletion(txObject);
            throw new CannotCreateTransactionException("Could not init " + userSession + " session: " + t, t);
        }
    }

    private void clearSession(Object txObject) {
        try {
            ConnectionHolder connHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(getDataSource());
            Connection conn = connHolder.getConnection();
            clearDBSession(conn);
        } catch (Exception e) {
            log.error("Could not clear session state: " + e, e);
        }
    }

    /**
    * RESET_PACKAGE ensures that no plsql vars state is left inside connection
    *  + allows to avoid "package state discarded" errors after pl/sql package recompilation...
    *  Can not call RESET_PACKAGE inside init_xx procedures:
    *  package states cannot be reinstantiated until the outermost PL/SQL calling scope within which RESET_PACKAGE was called completes"
    *  see: https://www.toadworld.com/platforms/oracle/w/wiki/3233.dbms-session-reset-package
    * @param conn
    * @throws SQLException
    */
    protected static void resetPackageState(Connection conn) {
        try {
            CallableStatement stmt = null;
            try {
                stmt = conn.prepareCall("begin dbms_session.reset_package; end;");
                stmt.execute();
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            log.error("DB ACCESS: Unable to reset package state", e);
        }
    }

    public void setRequestContext(S2RestRequestContext<T> requestContext) {
        this.requestContext = requestContext;
    }

    public S2RestRequestContext<T> getRequestContext() {
        return requestContext;
    }

    // protected BackendUserSession getUserSession() {
    // return requestContext.getUserSession();
    // }
}
