package eu.itreegroup.spark.modules.common.service;

import java.sql.Connection;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.gen.SprFilesDBServiceGen;

@Service
public class SprFilesDBServiceImpl extends SprFilesDBServiceGen implements SprFilesDBService {

    public SprFilesDBServiceImpl() {
    }

    @Override
    public SprFilesDAO newRecord() {
        SprFilesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprFilesDAO loadRecordByKey(Connection conn, Object identifier) throws Exception {
        return this.loadRecordByParams(conn, " WHERE FIL_KEY = ? ", new SelectParamValue((String) identifier));

    }

    /**
     * Function will mark file as complete. This mean that this file properly saved and attached to the business record
     * @param conn - connection to the DB
     * @param sprFilesDAO - DAO object that should be marked as completed
     * @return updated DAO object
     * @throws Exception
     */
    @Override
    public SprFilesDAO markAsConfirmed(Connection conn, SprFilesDAO sprFilesDAO) throws Exception {
        sprFilesDAO.setFil_status(CONFIRMED);
        sprFilesDAO.setFil_status_date(new Timestamp(System.currentTimeMillis()));
        this.saveRecord(conn, sprFilesDAO);
        return sprFilesDAO;
    }

    /**
     * Function will mark file as complete. This mean that this file properly saved and attached to the business record
     * @param conn - connection to the DB
     * @param identifier DAO object identifier that should be marked as completed
     * @return DAO object that was marked as CONFIRMED.
     * @throws Exception
     */
    @Override
    public SprFilesDAO markAsConfirmed(Connection conn, Object identifier) throws Exception {
        SprFilesDAO sprFilesDAO = this.loadRecord(conn, identifier);
        return markAsConfirmed(conn, sprFilesDAO);
    }

    /**
     * Function will mark file as deleted. This mean that background job can process this file (delete from system).
     * @param conn - connection to the db
     * @param sprFilesDAO - sprfile DAO object that should mark as deleted
     */
    @Override
    public void markAsDeleted(Connection conn, SprFilesDAO sprFilesDAO) throws Exception {
        sprFilesDAO.setFil_status(DELETED);
        sprFilesDAO.setFil_status_date(new Timestamp(System.currentTimeMillis()));
        this.saveRecord(conn, sprFilesDAO);
    }

    /**
     * Function will mark file as deleted. This mean that background job can process this file (delete from system).
     * @param conn - connection to the db
     * @param identifier - identifier of sprfile DAO object record that should mark as deleted
     */

    @Override
    public void markAsDeleted(Connection conn, Object identifier) throws Exception {
        SprFilesDAO sprFilesDAO = this.loadRecord(conn, identifier);
        markAsDeleted(conn, sprFilesDAO);
    }
}