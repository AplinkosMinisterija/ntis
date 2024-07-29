package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprOrgUsersDBServiceGen;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;

@Service
public class SprOrgUsersDBServiceImpl extends SprOrgUsersDBServiceGen implements SprOrgUsersDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprOrgUsersDBServiceImpl.class);

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    SprUsersDBServiceImpl sprUsersDBService;

    public SprOrgUsersDBServiceImpl() {
    }

    @Override
    public SprOrgUsersDAO newRecord() {
        SprOrgUsersDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public String getProfileForUserInOrganization(Connection conn, Double userId, Double orgId) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("Select ou.ou_profile_token " //
                + "  from spr_org_users ou " //
                + " where ou.ou_usr_id = ?" //
                + "   and ou.ou_org_id = ?" //
                + "   and " + dbStatementManager.getPeriodValidationForCurrentDateStr("ou.ou_date_from", "ou.ou_date_to"));
        stmt.addSelectParam(userId);
        stmt.addSelectParam(orgId);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        if (data.size() > 0) {
            return data.get(0).get("ou_profile_token");
        } else {
            return null;
        }
    }

    private void manageUserOrganization(Connection conn, SprOrgUsersDAO daoObject) throws Exception {
        SprUsersDAO userDAO = sprUsersDBService.loadRecord(conn, daoObject.getOu_usr_id());
        if (userDAO.getUsr_org_id() != null && userDAO.getUsr_org_id().equals(daoObject.getOu_org_id())) {
            userDAO.setUsr_org_id(null);
            sprUsersDBService.saveRecord(conn, userDAO);
        }
    }

    private void manageUserOrganization(Connection conn, Double identifier) throws Exception {
        manageUserOrganization(conn, loadRecord(conn, identifier));
    }

    @Override
    public void deleteRecord(Connection conn, Double identifier) throws Exception {
        manageUserOrganization(conn, identifier);
        StatementAndParams stmt = new StatementAndParams("""
                delete from spr_org_user_roles where our_ou_id = ?::int
                """);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        super.deleteRecord(conn, identifier);
    }

    @Override
    public SprOrgUsersDAO updateRecord(Connection conn, SprOrgUsersDAO daoObject) throws Exception {
        if (daoObject.getOu_date_to() != null) {
            manageUserOrganization(conn, daoObject);
        }
        return super.updateRecord(conn, daoObject);
    }

    public Boolean checkIsUserInOrganization(Connection conn, Double userId, Double orgId) throws SQLException, SparkBusinessException, Exception {
        StatementAndParams statement = new StatementAndParams("""
                SELECT ou_id FROM spr_org_users
                 WHERE CURRENT_DATE between ou_date_from and COALESCE(ou_date_to, now())
                   AND ou_usr_id = ?::int
                   AND ou_org_id = ?::int
                """);
        statement.addSelectParam(userId);
        statement.addSelectParam(orgId);
        List<HashMap<String, String>> queryResult = baseControllerJDBC.selectQueryAsDataArrayList(conn, statement);
        return queryResult != null && !queryResult.isEmpty();
    }

    public List<SprUsersDAO> getOrganizationUsers(Connection conn, Double orgId, String roleCodes) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT distinct usr_id,
                        usr_email,
                        ou_org_id as usr_org_id,
                        per.c01 as usr_email_confirmed
                 FROM spr_users usr
                 LEFT JOIN spr_persons per on per.per_id = usr.usr_per_id
                 JOIN spr_org_users ou ON usr_id = ou_usr_id
                 JOIN spr_roles rol ON rol_code in (%s)
                 JOIN spr_org_user_roles our ON our_ou_id = ou_id AND our_rol_id = rol.rol_id
                 WHERE ou_org_id = ?::int
                 AND now() BETWEEN ou_date_from AND COALESCE(ou_date_to, now())
                 AND now() BETWEEN our_date_from AND COALESCE(our_date_to, now())
                                                 """.formatted(roleCodes));
        stmt.addSelectParam(orgId);
        return baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprUsersDAO.class);
    }
}