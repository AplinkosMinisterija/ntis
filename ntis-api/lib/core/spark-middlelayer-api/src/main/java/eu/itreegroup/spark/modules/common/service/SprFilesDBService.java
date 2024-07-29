package eu.itreegroup.spark.modules.common.service;

import java.sql.Connection;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

public interface SprFilesDBService extends SprBaseDBService<SprFilesDAO> {

    public static String CONFIRMED = "CONFIRMED";

    public static String DELETED = "DELETED";

    public SprFilesDAO loadRecordByKey(Connection conn, Object identifier) throws Exception;

    /**
     * Function will mark file as complete. This mean that this file properly saved and attached to the business record
     * @param conn - connection to the DB
     * @param sprFilesDAO - DAO object that should be marked as completed
     * @return updated DAO object
     * @throws Exception
     */
    public SprFilesDAO markAsConfirmed(Connection conn, SprFilesDAO sprFilesDAO) throws Exception;

    /**
     * Function will mark file as complete. This mean that this file properly saved and attached to the business record
     * @param conn - connection to the DB
     * @param dentifier DAO object identifier that should be marked as completed
     * @return DAO object that was marked as CONFIRMED.
     * @throws Exception
     */
    public SprFilesDAO markAsConfirmed(Connection conn, Object identifier) throws Exception;

    /**
     * Function will mark file as deleted. This mean that background job can process this file (delete from system).
     * @param conn - connection to the db
     * @param sprFilesDAO - sprfile DAO object that should mark as deleted
     */
    public void markAsDeleted(Connection conn, SprFilesDAO sprFilesDAO) throws Exception;

    /**
     * Function will mark file as deleted. This mean that background job can process this file (delete from system).
     * @param conn - connection to the db
     * @param identifier - identifier of sprfile DAO object record that should mark as deleted
     */
    public void markAsDeleted(Connection conn, Object identifier) throws Exception;

}