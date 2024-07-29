package lt.project.common.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import lt.project.tx.PrototypeBackendTransactionManager;

@Configuration
public class DataSourcesConfig {

    @Bean
    @ConfigurationProperties("app.datasources.main-datasource")
    public DataSourceProperties projectBackendDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "mainDataSource")
    @Primary
    @ConfigurationProperties("app.datasources.pool.hikari")
    public DataSource mainAppDatasource() {
        return projectBackendDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("app.datasources.jobs-datasource")
    public DataSourceProperties projectBackendSchedulerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ntisBackendSchedulerDataSource")
    @ConfigurationProperties("app.datasources.pool.hikari")
    public DataSource getProjectBackendSchedulerDataSource() {
        return projectBackendSchedulerDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgreDataSource")
    @ConfigurationProperties("app.datasources.main-datasource")
    public DataSource getPostgresDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl(projectBackendDataSourceProperties().getUrl());
        dataSource.setUsername(projectBackendDataSourceProperties().determineUsername());
        dataSource.setPassword(projectBackendDataSourceProperties().getPassword());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager(@Autowired S2RestRequestContext<BackendUserSession> requestContext) {
        PrototypeBackendTransactionManager txManager = new PrototypeBackendTransactionManager();
        txManager.setDataSource(mainAppDatasource());
        txManager.setRequestContext(requestContext);
        txManager.setCheckSessionURL("/common/check-session");
        txManager.setUrlsNotUsedForPasswordExpiredLogic(new ArrayList<>(getUrlsNotUsedForPasswordExpiredLogic()));
        return txManager;

    }

    protected List<String> getUrlsNotUsedForPasswordExpiredLogic() {
        return Arrays.asList("/auth/login", //
                "/auth/admin-login", //
                "/auth/google-login", //
                "/auth/logout", //
                "/auth/accept-user-terms", //
                "/auth/get-internal-menu", //
                "/auth/get-public-menu", //
                "/auth/change-language", //
                "/auth/reset-password", //
                "/auth/get-role-forms", //
                "/auth/reset-new-user-password", //
                "/auth/confirm-email", //
                "/auth/get-password-params" //
        );
    }

}
