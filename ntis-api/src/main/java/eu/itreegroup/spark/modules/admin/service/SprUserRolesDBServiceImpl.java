package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprUserRolesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprUserRolesDBServiceGen;

@Service
public class SprUserRolesDBServiceImpl extends SprUserRolesDBServiceGen implements SprUserRolesDBService {

    public static final String USER_PROFILE_TOKEN_REBUILD = "userProfileTokenRebuild";

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprUserRolesDBServiceImpl.class);

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprUserRolesDBServiceImpl() {
    }

    @Override
    public SprUserRolesDAO newRecord() {
        SprUserRolesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public void refreshUserProfile(Connection conn, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName(USER_PROFILE_TOKEN_REBUILD));
        stmt.addParam4WherePart("usr_id = ?::int ", userId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}