package eu.itreegroup.spark.modules.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.modules.admin.dao.SprNotificationsNtisDAO;
import eu.itreegroup.spark.modules.common.dao.SprNotificationsDAO;
import eu.itreegroup.spark.modules.common.service.gen.SprNotificationsDBServiceGen;

@Service
public class SprNotificationsDBServiceImpl extends SprNotificationsDBServiceGen implements SprNotificationsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprNotificationsDBServiceImpl.class);

    public SprNotificationsDBServiceImpl() {
    }

    @Override
    public SprNotificationsDAO newRecord() {
        SprNotificationsNtisDAO daoObject = new SprNotificationsNtisDAO();
        return daoObject;
    }

    @Override
    protected List<SprNotificationsDAO> setDBDataToObject(ResultSet rs) throws Exception {
        List<SprNotificationsDAO> data = new ArrayList<SprNotificationsDAO>();
        while (rs.next()) {
            data.add(new SprNotificationsNtisDAO(Utils.getDouble(rs.getObject("NTF_ID")), //
                    rs.getString("NTF_TYPE"), //
                    Utils.getDouble(rs.getObject("NTF_REFERENCE")), //
                    rs.getString("NTF_TITLE"), //
                    rs.getString("NTF_MESSAGE"), //
                    rs.getTimestamp("NTF_CREATION_DATE"), //
                    rs.getTimestamp("NTF_MARK_AS_READ_DATE"), //
                    Utils.getDouble(rs.getObject("NTF_USR_ID")), //
                    Utils.getDouble(rs.getObject("NTF_ROL_ID")), //
                    Utils.getDouble(rs.getObject("NTF_ORG_ID")), //
                    rs.getTimestamp("NTF_DATE_FROM"), //
                    rs.getTimestamp("NTF_DATE_TO"), //
                    Utils.getDouble(rs.getObject("REC_VERSION")), //
                    rs.getTimestamp("REC_CREATE_TIMESTAMP"), //
                    rs.getString("REC_USERID"), //
                    rs.getTimestamp("REC_TIMESTAMP"), //
                    Utils.getDouble(rs.getObject("N01")), //
                    Utils.getDouble(rs.getObject("N02")), //
                    Utils.getDouble(rs.getObject("N03")), //
                    Utils.getDouble(rs.getObject("N04")), //
                    Utils.getDouble(rs.getObject("N05")), //
                    rs.getString("C01"), //
                    rs.getString("C02"), //
                    rs.getString("C03"), //
                    rs.getString("C04"), //
                    rs.getString("C05"), //
                    rs.getTimestamp("D01"), //
                    rs.getTimestamp("D02"), //
                    rs.getTimestamp("D03"), //
                    rs.getTimestamp("D04"), //
                    rs.getTimestamp("D05")));
        }
        return data;
    }

    @Override
    public SprNotificationsDAO insertRecord(Connection conn, SprNotificationsDAO daoObject) throws Exception {
        return super.insertRecord(conn, (SprNotificationsNtisDAO) daoObject);
    }

    @Override
    public SprNotificationsDAO updateRecord(Connection conn, SprNotificationsDAO daoObject) throws Exception {
        return super.updateRecord(conn, (SprNotificationsNtisDAO) daoObject);
    }

    public SprNotificationsDAO loadRecordByUsrAndOrgId(Connection conn, Double ntfId, Double usrId, Double orgId) throws Exception {
        return this.loadRecordByParams(conn, "  WHERE ntf_id = ? AND (ntf_usr_id = ? or ntf_org_id = ?) ", new SelectParamValue(ntfId),
                new SelectParamValue(usrId), new SelectParamValue(orgId));
    }
}
