package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprFormActionsDAO;

public interface SprFormActionsDBService extends SprBaseDBService<SprFormActionsDAO> {

    public SprFormActionsDAO loadRecordByFormIdAndCode(Connection conn, Double formId, String actionCode) throws Exception;

    public void deleteActionsForForms(Connection conn, Double formId) throws Exception;

}