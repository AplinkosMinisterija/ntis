package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprPropertiesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;

public interface SprPropertiesDBService extends SprBaseDBService<SprPropertiesDAO> {

    public SprPropertiesDAO loadRecordByName(Connection conn, String propertyName, String serverId) throws Exception;

    public IdKeyValuePair[] loadPublicProperties(Connection conn) throws Exception;

    public String[] getPublicPropertyNames();

}