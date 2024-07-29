package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.Map;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;

public interface SprUsersDBService extends SprBaseDBService<SprUsersDAO> {

    public SprUsersDAO loadRecordByIdentifier(Connection conn, Object identifier, Map<String, Object> authExtData) throws Exception;

    public SprUsersDAO loadRecordByEmail(Connection conn, String email) throws Exception;

    public void checkUserNameUk(Connection conn, Double id, String code) throws Exception;

    public void checkEmailUk(Connection conn, Double id, String code) throws Exception;

    public SprUsersDAO restoreSystemData(Connection conn, SprUsersDAO daoObject) throws Exception;

}