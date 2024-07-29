package lt.project.common.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ImportResource({ "classpath*:applicationContext.xml", "classpath*:viisp/viisp.xml", "classpath*:viisp/viispConfig.xml", "classpath*:viisp/viispConfigTest.xml",
        "classpath*:viisp/viispMock.xml", "classpath*:isense.xml" })
@EnableTransactionManagement
public class AppConfig {

}
