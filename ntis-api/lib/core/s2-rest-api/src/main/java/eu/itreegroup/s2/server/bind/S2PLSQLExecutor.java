package eu.itreegroup.s2.server.bind;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.security.access.AccessDeniedException;

import lt.jmsys.spark.bind.executor.plsql.PLSQLExecutor;


public class S2PLSQLExecutor extends PLSQLExecutor {

    public static final int BUSINESS_EXCEPTION_SQL_CODE = 20222;

    public static final int ACCESS_DENIED_EXCEPTION_SQL_CODE = 20223;

    public S2PLSQLExecutor(Connection conn) {
        super(conn);
    }

    @Override
    protected void handleBusinessException(Method current, SQLException thrown) throws Exception {
        if (thrown.getErrorCode() == BUSINESS_EXCEPTION_SQL_CODE) {
            super.handleBusinessException(current, thrown);
        } else if (thrown.getErrorCode() == ACCESS_DENIED_EXCEPTION_SQL_CODE) {
            throw new AccessDeniedException(thrown.getMessage());
        }
    }
}
