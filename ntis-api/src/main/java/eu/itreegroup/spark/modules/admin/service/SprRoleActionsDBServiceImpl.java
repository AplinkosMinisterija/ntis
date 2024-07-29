package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprRoleActionsDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprRoleActionsDBServiceGen;

@Service
public class SprRoleActionsDBServiceImpl extends SprRoleActionsDBServiceGen implements SprRoleActionsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprRoleActionsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprRoleActionsDBServiceImpl() {
    }

    @Override
    public SprRoleActionsDAO newRecord() {
        SprRoleActionsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprRoleActionsDAO loadRecord(Connection conn, Double rol_id, Double frm_id) throws Exception {
        return this.loadRecordByParams(conn, " WHERE ROA_ROL_ID = ?::int AND ROA_FRM_ID = ?::int ", new SelectParamValue(rol_id), new SelectParamValue(frm_id));
    }

    @Override
    public void deleteDisabledForms(Connection conn, Double rol_id, Double frm_id) throws Exception {
        String stmtStr = "DELETE FROM SPR_ROLE_ACTIONS"//
                + "     WHERE ROA_ROL_ID=?::int AND ROA_FRM_ID=?::int";//
        log.debug("delete stmt: " + stmtStr);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(stmtStr);
        stmt.addSelectParam(rol_id);
        stmt.addSelectParam(frm_id);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

    @Override
    public void updateRecord(Connection conn, Double rol_id, Double frm_id, Date roa_date_from) throws Exception {
        String stmtStr = "UPDATE SPR_ROLE_ACTIONS" + "       SET ROA_DATE_FROM = ? "//
                + "     WHERE ROA_ROL_ID=?::int AND ROA_FRM_ID=?::int";//
        log.debug("delete stmt: " + stmtStr);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(stmtStr);
        stmt.addSelectParam(roa_date_from);
        stmt.addSelectParam(rol_id);
        stmt.addSelectParam(frm_id);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }

    @Override
    @CacheEvict(value = "roleActions", key = "#rolId")
    public void removeCachedRole(Double rolId) {
        log.debug("=========================================================================");
        log.debug("Remove cached role record from cache. Will be removed role by key: " + rolId);
        log.debug("=========================================================================");
    }
}