package eu.itreegroup.spark.dao;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.tools.DBPropertyManager;

@Primary
@Component
public class DBStatementManager extends DBStatementConstructor {

    private static final Logger log = LoggerFactory.getLogger(DBStatementManager.class);

    public static String DATE_FORMAT_DAY_PARAM_DB = "DATE_FORMAT_DAY_DB";

    public static String DATE_FORMAT_DAY_TIME_PARAM_DB = "DATE_FORMAT_DAY_TIME_DB";

    public static String DATE_FORMAT_DAY_PARAM_JAVA = "DATE_FORMAT_DAY_JAVA";

    public static String DATE_FORMAT_DAY_TIME_PARAM_JAVA = "DATE_FORMAT_DAY_TIME_JAVA";

    public static String DATE_FORMAT_DAY_DESC_DB = "Date format for db statements precision til date";

    public static String DATE_FORMAT_DAY_TIME_DESC_DB = "Date format for db statements precision til second";

    public static String DATE_FORMAT_DAY_DESC_JAVA = "Date format for java statements precision til date";

    public static String DATE_FORMAT_DAY_TIME_DESC_JAVA = "Date format for java statements precision til second";

    public static String DATE_FORMAT_DAY_DEFAULT_DB = "YYYY-MM-DD";

    public static String DATE_FORMAT_DAY_TIME_DEFAULT_DB = "YYYY-MM-DD HH24:MI:SS";

    public static String DATE_FORMAT_DAY_DEFAULT_JAVA = "yyyy-MM-dd";

    public static String DATE_FORMAT_DAY_TIME_DEFAULT_JAVA = "yyyy-MM-dd HH:mm:ss";

    static String dbTypeStatic;

    private Properties prop;

    @Autowired
    DBPropertyManager dbPropertyManager;

    @EventListener({ ContextRefreshedEvent.class })
    public void loadResources() {
        String fileName = dbType.toLowerCase() + "_statements.xml";
        log.debug("Start loading statement properties file from file " + fileName);
        prop = new Properties();
        try {
            prop.loadFromXML(DBStatementManager.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("Init db settings related with date formats");
        dbPropertyManager.initNewPropertyByName(DATE_FORMAT_DAY_PARAM_DB, DATE_FORMAT_DAY_DESC_DB, "S", DATE_FORMAT_DAY_DEFAULT_DB);
        dbPropertyManager.initNewPropertyByName(DATE_FORMAT_DAY_TIME_PARAM_DB, DATE_FORMAT_DAY_TIME_DESC_DB, "S", DATE_FORMAT_DAY_TIME_DEFAULT_DB);
        dbPropertyManager.initNewPropertyByName(DATE_FORMAT_DAY_PARAM_JAVA, DATE_FORMAT_DAY_DESC_JAVA, "S", DATE_FORMAT_DAY_DEFAULT_JAVA);
        dbPropertyManager.initNewPropertyByName(DATE_FORMAT_DAY_TIME_PARAM_JAVA, DATE_FORMAT_DAY_TIME_DESC_JAVA, "S", DATE_FORMAT_DAY_TIME_DEFAULT_JAVA);
        dbTypeStatic = dbType;
    }

    public String getStatementByName(String statemenName) {
        return prop.getProperty(statemenName);
    }

    /**
     * Function will return date formats that should be used for date formating.
     * @param dateFormatFor - date format identifier. Can be used below listed date format identifiers:
     *    DATE_FORMAT_DAY_DB - date format that should be used in db select statement. This date format defines format till date precision.
     *    DATE_FORMAT_DAY_TIME_DB - date format that should be used in db select statement. This date format defines format till time precision.
     *    DATE_FORMAT_DAY_JAVA - date format that should be used in java side. This date format defines format till date precision.
     *    DATE_FORMAT_DAY_TIME_JAVA - date format that should be used in java side. This date format defines format till time precision.
     * @return
     * @throws Exception
     */
    public String getDateFormat(int dateFormatFor) throws Exception {
        switch (dateFormatFor) {
            case DATE_FORMAT_DAY_DB:
                return dbPropertyManager.getPropertyByName("DATE_FORMAT_DAY_DB", DATE_FORMAT_DAY_DEFAULT_DB);
            case DATE_FORMAT_DAY_TIME_DB:
                return dbPropertyManager.getPropertyByName("DATE_FORMAT_DAY_TIME_DB", DATE_FORMAT_DAY_TIME_DEFAULT_DB);
            case DATE_FORMAT_DAY_JAVA:
                return dbPropertyManager.getPropertyByName("DATE_FORMAT_DAY_JAVA", DATE_FORMAT_DAY_DEFAULT_JAVA);
            case DATE_FORMAT_DAY_TIME_JAVA:
                return dbPropertyManager.getPropertyByName("DATE_FORMAT_DAY_TIME_JAVA", DATE_FORMAT_DAY_TIME_DEFAULT_JAVA);
            default:
                throw new Exception("Undefined date format constant: " + dateFormatFor);
        }
    }
}
