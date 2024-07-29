package eu.itreegroup.spark.modules.tasks.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.tasks.dao.SprTasksDAO;

public interface SprTasksDBService extends SprBaseDBService<SprTasksDAO> {

    public void deleteAllTaskInfo(Connection conn, RecordIdentifier recordIdentifier) throws Exception;

    @Override
    public SprTasksDAO insertRecord(Connection conn, SprTasksDAO daoObject) throws Exception;

    @Override
    public SprTasksDAO updateRecord(Connection conn, SprTasksDAO daoObject) throws Exception;
}