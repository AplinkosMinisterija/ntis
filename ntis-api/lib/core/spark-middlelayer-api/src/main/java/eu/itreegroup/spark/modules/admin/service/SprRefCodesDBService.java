package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.List;

import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.CodeDictionaryModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;

public interface SprRefCodesDBService extends SprBaseDBService<SprRefCodesDAO> {

    public List<SprListIdKeyValue> getRefCodes(Connection conn, String refCode, String language) throws Exception;

    public List<IdKeyValuePair> getRefCodesByName(Connection conn, String refCode, String language) throws Exception;

    public void clearCasheForRefCodesManagerJSON(String refCode, String language);

    public void clearCacheForRefCodesManager(String refCode, String language);

    public CodeDictionaryModel loadDictionary(Connection conn, Double id) throws Exception;
}