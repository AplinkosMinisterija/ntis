package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.Map;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestArgsDAO;

public interface SprJobRequestArgsDBService extends SprBaseDBService<SprJobRequestArgsDAO> {

    void deleteRecordsByParent(Connection conn, Double identifier) throws Exception;

    Map<String, String> getArgsMap(Connection conn, Double jrqId) throws Exception;
}
