package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgAvailableRolesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprOrgAvailableRolesDBServiceGen;

@Service
public class SprOrgAvailableRolesDBServiceImpl extends SprOrgAvailableRolesDBServiceGen implements SprOrgAvailableRolesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprOrgAvailableRolesDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    SprOrgUserRolesDBService sprOrgUserRolesDBService;

    public SprOrgAvailableRolesDBServiceImpl() {
    }

    @Override
    public SprOrgAvailableRolesDAO newRecord() {
        SprOrgAvailableRolesDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public void deleteRecord(Connection conn, Double identifier) throws Exception {
        SprOrgAvailableRolesDAO rec = this.loadRecord(conn, identifier);
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("delete from spr_org_user_roles where our_ou_id in (select ou_id from spark.spr_org_users where ou_org_id = ?) and our_rol_id = ? ");
        stmt.addSelectParam(rec.getOar_org_id());
        stmt.addSelectParam(rec.getOar_rol_id());
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        sprOrgUserRolesDBService.refreshUserOrgProfile(conn, rec.getOar_org_id(), null);
        super.deleteRecord(conn, identifier);
    }
}