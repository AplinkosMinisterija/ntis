package eu.itreegroup.spark.modules.tasks.service;

import java.sql.Connection;
import java.util.ArrayList;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.tasks.dao.SprTaskFilesDAO;
import eu.itreegroup.spark.modules.tasks.logic.forms.model.SprTaskFileKey;

public interface SprTaskFilesDBService extends SprBaseDBService<SprTaskFilesDAO> {

    public ArrayList<SprTaskFileKey> loadAllTaskRecords(Connection conn, Double tasId) throws Exception;

    public SprFile[] getTaskFiles(Connection conn, Double tasId) throws Exception;

    @Override
    public String getUserName();
}