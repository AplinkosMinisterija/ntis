package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.enums.ProcessRequestType;
import eu.itreegroup.spark.modules.admin.dao.SprProcessRequestsDAO;

public interface SprProcessRequestsDBService extends SprBaseDBService<SprProcessRequestsDAO> {

    public SprProcessRequestsDAO loadByToken(Connection conn, ProcessRequestType requestType, String token) throws Exception;
}