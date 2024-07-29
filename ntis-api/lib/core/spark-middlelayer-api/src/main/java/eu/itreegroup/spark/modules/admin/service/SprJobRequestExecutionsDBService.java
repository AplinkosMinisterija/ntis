package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestExecutionsDAO;

public interface SprJobRequestExecutionsDBService extends SprBaseDBService<SprJobRequestExecutionsDAO> {

    public void deleteRecordsByParent(Connection conn, Double identifier) throws Exception;
}