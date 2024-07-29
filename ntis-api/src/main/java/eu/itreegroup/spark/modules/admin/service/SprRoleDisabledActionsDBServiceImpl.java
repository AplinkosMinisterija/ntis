package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprRoleDisabledActionsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprRoleDisabledActionsDBServiceGen;

@Service
public class SprRoleDisabledActionsDBServiceImpl extends SprRoleDisabledActionsDBServiceGen implements SprRoleDisabledActionsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprRoleDisabledActionsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprRoleDisabledActionsDBServiceImpl() {
    }

    @Override
    public SprRoleDisabledActionsDAO newRecord() {
        SprRoleDisabledActionsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public void deleteSpecifiedRoleFormActions(Connection conn, Double fra_id, Double roa_id) throws Exception {
        String executionsStmt = "DELETE FROM SPR_ROLE_DISABLED_ACTIONS WHERE RDA_ROA_ID=? AND RDA_FRA_ID=?::int ";
        log.debug("delete stmt: " + executionsStmt);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(executionsStmt);
        stmt.addSelectParam(roa_id);
        stmt.addSelectParam(fra_id);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

    @Override
    public void deleteSpecifiedRoleFormActions(Connection conn, Double identifier, String[] frm_ids) throws Exception {
        String stmt = "DELETE FROM SPR_ROLE_DISABLED_ACTIONS "//
                + "     WHERE RDA_ROA_ID=? AND RDA_FRA_ID NOT IN (";//
        for (int i = 1; i <= frm_ids.length; i++) {
            stmt = stmt + "?";
            if (i == frm_ids.length) {
                stmt = stmt + ")";
                break;
            }
            stmt = stmt + ",";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
            pstmt.setObject(1, identifier);
            int i = 2;
            for (String id : frm_ids) {
                pstmt.setObject(i, id);
                i++;
            }
            pstmt.execute();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteAllRoleFormActions(Connection conn, Double identifier) throws Exception {
        String executionsStmt = "DELETE FROM SPR_ROLE_DISABLED_ACTIONS  WHERE RDA_ROA_ID=?";
        log.debug("delete stmt: " + executionsStmt);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(executionsStmt);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}