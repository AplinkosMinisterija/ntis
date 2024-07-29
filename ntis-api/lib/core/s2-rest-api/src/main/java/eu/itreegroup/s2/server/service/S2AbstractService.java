package eu.itreegroup.s2.server.service;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import lt.jmsys.spark.bind.executor.plsql.PLSQLExecutor;
import lt.jmsys.spark.bind.service.Service;
import lt.jmsys.spark.bind.service.ServiceFactory;

public class S2AbstractService<S extends BackendUserSession> {

    @Autowired
	private DataSource dataSource;

    @Autowired
    protected ApplicationContext springContext;

    @Autowired
    protected S2RestRequestContext<S> requestContext;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected <T extends Service> T getDbService(Class<T> serviceClass, boolean initTransaction) {
        try {
            if (initTransaction && !TransactionSynchronizationManager.isActualTransactionActive()) {
                throw new Exception("Service call not @Transactional");
            }
            Connection conn = DataSourceUtils.getConnection(dataSource);
            PLSQLExecutor executor = new PLSQLExecutor(conn);
            return ServiceFactory.newService(serviceClass, executor);

        } catch (Exception e) {
            throw new RuntimeException("Could not get '" + serviceClass.getSimpleName() + "' DB service: " + e, e);
        }
    }
    
    protected <T extends Service> T getDbService(Class<T> serviceClass) {
        return getDbService(serviceClass, true);
    }
    protected Connection getDBConnection(boolean initTransaction) {
    	try {
    	  if (initTransaction && !TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new Exception("Service call not @Transactional");
          }
          return DataSourceUtils.getConnection(dataSource);
    	} catch (Exception e) {
            throw new RuntimeException("Could not get db connection service: " + e, e);
        }
    }
    protected Connection getDBConnection() {
    	return getDBConnection(true);
    }

}
