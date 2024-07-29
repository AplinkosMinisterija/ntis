package lt.project.ntis.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import lt.project.ntis.dao.NtisServedObjectsDAO;
import lt.project.ntis.service.gen.NtisServedObjectsDBServiceGen;

@Service
public class NtisServedObjectsDBService extends NtisServedObjectsDBServiceGen {

    @Autowired
    BaseControllerJDBC queryController;

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NtisServedObjectsDBService.class);

    public NtisServedObjectsDBService() {
    }

    @Override
    public NtisServedObjectsDAO newRecord() {
        NtisServedObjectsDAO daoObject = super.newRecord();
        return daoObject;
    }

    public ArrayList<NtisServedObjectsDAO> getRecordByWtfId(Connection conn, Double soId) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT * FROM ntis_served_objects WHERE so_wtf_id = ?::int");
        stmt.addSelectParam(soId);
        return (ArrayList<NtisServedObjectsDAO>) queryController.selectQueryAsObjectArrayList(conn, stmt, NtisServedObjectsDAO.class);
    }

    @Override
    public NtisServedObjectsDAO insertRecord(Connection conn, NtisServedObjectsDAO daoObject) throws Exception {
        daoObject.validateObject(Utils.OPERATION_INSERT, this);
        this.validateConstraints(conn, daoObject, null);
        String stmt = "INSERT INTO NTIS_SERVED_OBJECTS (SO_GEOM, SO_COORDINATE_LATITUDE, SO_COORDINATE_LONGITUDE, SO_AD_ID, SO_WTF_ID,SO_BN_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (ST_SetSRID(ST_MakePoint(?::float, ?::float), 3346), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING SO_ID;";

        try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
            pstmt.setObject(1, daoObject.getSo_coordinate_latitude());
            pstmt.setObject(2, daoObject.getSo_coordinate_longitude());
            pstmt.setObject(3, daoObject.getSo_coordinate_latitude());
            pstmt.setObject(4, daoObject.getSo_coordinate_longitude());
            pstmt.setObject(5, daoObject.getSo_ad_id());
            pstmt.setObject(6, daoObject.getSo_wtf_id());
            pstmt.setObject(7, daoObject.getSo_bn_id());
            pstmt.setObject(8, daoObject.getN01());
            pstmt.setObject(9, daoObject.getN02());
            pstmt.setObject(10, daoObject.getN03());
            pstmt.setObject(11, daoObject.getN04());
            pstmt.setObject(12, daoObject.getN05());
            pstmt.setString(13, daoObject.getC01());
            pstmt.setString(14, daoObject.getC02());
            pstmt.setString(15, daoObject.getC03());
            pstmt.setString(16, daoObject.getC04());
            pstmt.setString(17, daoObject.getC05());
            pstmt.setObject(18, Utils.getSqlTimestamp(daoObject.getD01()));
            pstmt.setObject(19, Utils.getSqlTimestamp(daoObject.getD02()));
            pstmt.setObject(20, Utils.getSqlTimestamp(daoObject.getD03()));
            pstmt.setObject(21, Utils.getSqlTimestamp(daoObject.getD04()));
            pstmt.setObject(22, Utils.getSqlTimestamp(daoObject.getD05()));
            // Record audit information (start)
            pstmt.setInt(23, 1);
            pstmt.setObject(24, new Timestamp(System.currentTimeMillis()));
            pstmt.setObject(25, new Timestamp(System.currentTimeMillis()));
            String userName = getUserName();
            pstmt.setObject(26, userName);
            // Record audit information (end)
            pstmt.execute();
            try (ResultSet rs = pstmt.getResultSet()) {
                rs.next();
                daoObject.setSo_id(rs.getDouble(1));
            } catch (Exception ex1) {
                throw ex1;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return daoObject;
    }

}