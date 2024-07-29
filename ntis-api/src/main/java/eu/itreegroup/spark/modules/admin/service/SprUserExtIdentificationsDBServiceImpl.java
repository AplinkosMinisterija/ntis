package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.enums.EidType;
import eu.itreegroup.spark.modules.admin.dao.SprUserExtIdentificationsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprUserExtIdentificationsDBServiceGen;

@Service
public class SprUserExtIdentificationsDBServiceImpl extends SprUserExtIdentificationsDBServiceGen implements SprUserExtIdentificationsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprUserExtIdentificationsDBServiceImpl.class);

    public SprUserExtIdentificationsDBServiceImpl() {
    }

    @Override
    public SprUserExtIdentificationsDAO newRecord() {
        SprUserExtIdentificationsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public Double getUserId(Connection conn, EidType identificationType, String identificationCode) throws Exception {
        String stmt = "SELECT eid_usr_id FROM spr_user_ext_identifications WHERE eid_type = ? AND eid_token = ?";
        log.debug("load stmt: " + stmt);
        Double result = null;
        try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
            pstmt.setObject(1, identificationType.name());
            pstmt.setObject(2, identificationCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = Utils.getDouble(rs.getObject("EID_USR_ID"));
                }
            } catch (Exception ex1) {
                throw ex1;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }
}