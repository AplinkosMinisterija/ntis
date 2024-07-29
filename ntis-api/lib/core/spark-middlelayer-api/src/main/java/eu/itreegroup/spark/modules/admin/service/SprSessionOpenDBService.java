package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.List;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprSessionOpenDAO;

public interface SprSessionOpenDBService extends SprBaseDBService<SprSessionOpenDAO> {

    public SprSessionOpenDAO loadRecordByKey(Connection conn, String sessionKey) throws Exception;

    public List<RecordIdentifier> getSessionsKeysByUserName(Connection conn, Double userId) throws Exception;

    public List<RecordIdentifier> getSessionsKeysByUserData(Connection conn, Double userId, Double orgId, Double rolId) throws Exception;

    public void removeCachedSession(String sessionKey);
}