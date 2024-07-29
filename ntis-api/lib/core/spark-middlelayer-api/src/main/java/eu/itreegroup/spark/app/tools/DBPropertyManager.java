package eu.itreegroup.spark.app.tools;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.model.AppData;
import eu.itreegroup.spark.app.model.Version;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprPropertiesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.service.SprPropertiesDBService;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;

@Component
public class DBPropertyManager {

    private static final Logger log = LoggerFactory.getLogger(DBPropertyManager.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    SprPropertiesDBService sprPropertiesDBService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprFilesDBService sprFileDbService;

    @Autowired
    SprRefCodesDBService sprRefCodesDBService;

    @Value("${app.server.id}")
    private String serverID;

    @Value("${spring.profiles.active:}")
    String springProfilesActive;

    @Value("${build.branch}")
    String buildBranch;

    @Value("${build.number}")
    String buildNumber;

    @Value("${build.timestamp}")
    String buildTimestamp;

    private AppData appData = null;

    private List<SprPropertiesDAO> getPropertyData(String propertyName) {
        try (Connection conn = dataSource.getConnection()) {
            return sprPropertiesDBService.loadRecordsByParams(conn,
                    "where prp_name=? and case when prp_install_instance is null then ? else prp_install_instance end = ?", new SelectParamValue(propertyName),
                    new SelectParamValue(serverID), new SelectParamValue(serverID));
        } catch (Exception ex) {
            log.error("Erron while try get property by name: {}", propertyName);
            log.error("Exception: ", ex);
        }
        return Collections.emptyList();
    }

    /**
     * Function will return property value, for provided property name. In case such 
     * property do not found default value will be returned.
     * @param propertyName - property name which value should be returned
     * @param defaultValue - property default value
     * @return
     */
    @Cacheable(value = "dbPropertyManager", key = "{#propertyName}")
    public String getPropertyByName(String propertyName, String defaultValue) {
        List<SprPropertiesDAO> data = getPropertyData(propertyName);
        if (data.isEmpty()) {
            return defaultValue;
        } else {
            SprPropertiesDAO record = data.get(0);
            return record.getPrp_value();
        }
    }

    /**
     * Function will return file input stream that are assigned to provided property name.
     * @param propertyName - property name, for which should be returned file
     * @return file input stream
     */
    public InputStream getPropertyFileByName(String propertyName) {
        List<SprPropertiesDAO> data = getPropertyData(propertyName);
        if (data.isEmpty()) {
            return null;
        } else {
            SprPropertiesDAO record = data.get(0);
            try (Connection conn = dataSource.getConnection()) {
                SprFilesDAO fileDAO = sprFileDbService.loadRecord(conn, Utils.getDouble(record.getPrp_fil_id()));
                return fileStorageService.getFileBySprFilesDAO(fileDAO);
            } catch (Exception ex) {
                log.error("Erron while try get property file by name: {}", propertyName);
                log.error("Exception: ", ex);
            }
            return null;
        }
    }

    @CacheEvict(value = "dbPropertyManager", key = "{#propertyName}")
    public void updateCache(String propertyName) {
        log.debug("Will clear cashe for property by nam: " + propertyName);
    }

    /**
     * Function will update property in db side. In case if property not exist property will be created.
     * @param conn - connection that will be used for property storing
     * @param propertyName - property name that should be updated
     * @param propertyType - property type 
     * @param propertyValue - new property value, that should be stored in DB.
     */
    public void setPropertyByName(Connection conn, String propertyName, String propertyDescription, String propertyType, String propertyValue) {
        setPropertyByName(conn, propertyName, propertyDescription, propertyType, propertyValue, true);
    }

    /**
     * Function will create new property record in spr_properties table. In case if property with provided name exists
     * function will do nothing.
     * @param conn - connection to the db
     * @param propertyName - property name that should be created in db
     * @param propertyType - new property type
     * @param propertyValue - new property value
     */
    public void initNewPropertyByName(String propertyName, String propertyDescription, String propertyType, String propertyValue) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            setPropertyByName(conn, propertyName, propertyDescription, propertyType, propertyValue, false);
        } catch (Exception ex) {
            log.error("Error while try create property in db side by name: " + propertyName, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                    log.error("Error while try close connection", ex);
                }
            }
        }
    }

    /**
     * Function will create new property record in spr_properties table. In case if property with provided name exists
     * function will do nothing.
     * @param conn - connection to the db
     * @param propertyName - property name that should be created in db
     * @param propertyType - new property type
     * @param propertyValue - new property value
     */
    public void initNewPropertyByName(Connection conn, String propertyName, String propertyDescription, String propertyType, String propertyValue) {
        setPropertyByName(conn, propertyName, propertyDescription, propertyType, propertyValue, false);
    }

    @CacheEvict(value = "dbPropertyManager", key = "#propertyName")
    protected void setPropertyByName(Connection conn, String propertyName, String propertyDescription, String propertyType, String propertyValue,
            boolean updateIfExists) {
        SprPropertiesDAO sprPropertiesDAO = null;
        boolean performUpdate = false;
        try {
            sprPropertiesDAO = sprPropertiesDBService.loadRecordByName(conn, propertyName, serverID);
            performUpdate = updateIfExists;
        } catch (Exception ex) {
            sprPropertiesDAO = sprPropertiesDBService.newRecord();
            sprPropertiesDAO.setPrp_name(propertyName);
            sprPropertiesDAO.setPrp_description(propertyDescription);
            sprPropertiesDAO.setPrp_type(propertyType);
            sprPropertiesDAO.setPrp_install_instance(serverID);
            performUpdate = true;
        }
        if (performUpdate) {
            sprPropertiesDAO.setPrp_value(propertyValue);
            try {
                sprPropertiesDAO = sprPropertiesDBService.saveRecord(conn, sprPropertiesDAO);
            } catch (Exception ex) {
                log.error("Error while try update property by name: " + propertyName + " to the value: '" + propertyValue + "'", ex);
            }
        }
    }

    public AppData getAppData(Connection conn) {
        if (this.appData == null) {
            try {
                this.appData = new AppData();
                appData.setProperties(sprPropertiesDBService.loadPublicProperties(conn));
                List<IdKeyValuePair> languageList = this.sprRefCodesDBService.getRefCodesByName(conn, "SPR_LANGUAGE", null);
                appData.setLanguages(languageList.toArray(new IdKeyValuePair[languageList.size()]));
                appData.setSpringProfilesActive(springProfilesActive);
                appData.setVersion(new Version(this.buildBranch, this.buildNumber, this.buildTimestamp));
            } catch (Exception e) {
                Log.error("Error on creating AppData object", e);
            }
        }
        return this.appData;
    }

    public void clearAppData() {
        this.appData = null;
    }

}
