package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprFormsDAO;

public interface SprFormsDBService extends SprBaseDBService<SprFormsDAO> {

    public SprFormsDAO loadRecordByCode(Connection conn, String frmCode) throws Exception;
}