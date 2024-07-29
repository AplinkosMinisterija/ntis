package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprOrgUserRolesDBServiceGen;

@Service
public class SprOrgUserRolesDBServiceImpl extends SprOrgUserRolesDBServiceGen implements SprOrgUserRolesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprOrgUserRolesDBServiceImpl.class);

    public static final String USER_ORGANIZATION_PROFILE_TOKEN_REBUILD = "userOrganizationProfileTokenRebuild";

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBStatementManager dbStatementManager;

    public SprOrgUserRolesDBServiceImpl() {
    }

    @Override
    public SprOrgUserRolesDAO newRecord() {
        SprOrgUserRolesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public void refreshUserOrgProfile(Connection conn, Double orgId, Double userId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName(USER_ORGANIZATION_PROFILE_TOKEN_REBUILD));
        stmt.addParam4WherePart("ou_usr_id = ? ", userId);
        stmt.addParam4WherePart("ou_org_id = ? ", orgId);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
    }
}