package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprSessionOpenDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprSessionOpenDBServiceGen;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.logic.forms.brokerws.RcBroker;

@Service
public class SprSessionOpenDBServiceImpl extends SprSessionOpenDBServiceGen implements SprSessionOpenDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprSessionOpenDBServiceImpl.class);

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprSessionOpenDBService thisObject;

    @Autowired
    BaseControllerJDBC queryController;

    @SuppressWarnings("rawtypes")
    @Autowired
    SprAuthorization sprAuthorization;

    @Autowired
    RcBroker rcBroker;

    public SprSessionOpenDBServiceImpl() {
    }

    @Override
    public SprSessionOpenDAO newRecord() {
        SprSessionOpenDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    @Cacheable(value = "userSessions", key = "#sessionKey")
    public SprSessionOpenDAO loadRecordByKey(Connection conn, String sessionKey) throws Exception {
        log.debug("=========================================================================");
        log.debug("== load session from db with sessionkey: " + sessionKey);
        log.debug("=========================================================================");
        return this.loadRecordByParams(conn, "  WHERE SES_KEY = ? ", new SelectParamValue(sessionKey));
    }

    @Override
    public SprSessionOpenDAO saveRecord(Connection conn, SprSessionOpenDAO daoObject) throws Exception {
        daoObject = super.saveRecord(conn, daoObject);
        thisObject.removeCachedSession(daoObject.getSes_key());
        return daoObject;
    }

    @Override
    public SprSessionOpenDAO insertRecord(Connection conn, SprSessionOpenDAO daoObject) throws Exception {
        loadIntsOwnerNtrData(conn, daoObject);
        return super.insertRecord(conn, daoObject);
    }

    private void loadIntsOwnerNtrData(Connection conn, SprSessionOpenDAO daoObject) throws Exception {
        if (daoObject.getSes_id() == null && hasIntsOwnerRole(conn, daoObject)) {
            if (daoObject.getSes_org_id() != null) {
                rcBroker.startOrganizationNtrDataRequest(conn, daoObject.getSes_org_id());
            } else {
                rcBroker.startPersonNtrDataRequest(conn, daoObject.getSes_per_id());
            }
        }
    }

    private boolean hasIntsOwnerRole(Connection conn, SprSessionOpenDAO daoObject) throws Exception {
        return sprAuthorization.getUserRoleManager(conn).userHasRole(NtisRolesConstants.INTS_OWNER);
    }

    @Override
    @CacheEvict(value = "userSessions", key = "#sessionKey")
    public void removeCachedSession(String sessionKey) {
        log.debug("=========================================================================");
        log.debug("Remove cached session record from cache. Will be removed session by key: " + sessionKey);
        log.debug("=========================================================================");
    }

    @Override
    public ArrayList<RecordIdentifier> getSessionsKeysByUserName(Connection conn, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select ses_key as id from spr_user_sessions_open where ses_usr_id = ?::int");
        stmt.addSelectParam(userId);
        return (ArrayList<RecordIdentifier>) queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
    }

    @Override
    public ArrayList<RecordIdentifier> getSessionsKeysByUserData(Connection conn, Double userId, Double orgId, Double rolId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select ses_key as id from spr_user_sessions_open ");
        stmt.addParam4WherePart(" ses_usr_id = ?::int ", userId);
        stmt.addParam4WherePart(" ses_org_id = ?::int ", orgId);
        stmt.addParam4WherePart(" ses_rol_id = ?::int  ", rolId);
        return (ArrayList<RecordIdentifier>) queryController.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
    }
}