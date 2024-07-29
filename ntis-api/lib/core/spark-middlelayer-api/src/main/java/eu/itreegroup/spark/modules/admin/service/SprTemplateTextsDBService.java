package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.List;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprTemplateTextsDAO;

public interface SprTemplateTextsDBService extends SprBaseDBService<SprTemplateTextsDAO> {

    public List<SprTemplateTextsDAO> loadRecordsByParent(Connection conn, Double identifier) throws Exception;

    public void deleteRecordsByParent(Connection conn, Double identifier) throws Exception;

}