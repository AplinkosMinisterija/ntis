package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprOrgAvailableRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsNtisDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprOrganizationsDBServiceGen;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisOrgStateConstants;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.enums.NtisOrgState;
import lt.project.ntis.enums.NtisOrgType;

@Service
public class SprOrganizationsDBServiceImpl extends SprOrganizationsDBServiceGen implements SprOrganizationsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprOrganizationsDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    SprRolesDBService sprRolesDBService;

    @Autowired
    SprOrgUserRolesDBService sprOrgUserRolesDBService;

    @Autowired
    SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService;

    @Autowired
    DBPropertyManager dBPropertyManager;

    @Autowired
    SprOrgUsersDBService sprOrgUsersDbService;

    @Autowired
    SprUserRolesDBService sprUserRolesDBService;

    public SprOrganizationsDBServiceImpl() {
    }

    @Override
    public SprOrganizationsDAO newRecord() {
        SprOrganizationsNtisDAO daoObject = new SprOrganizationsNtisDAO();
        return daoObject;
    }

    @Override
    protected ArrayList<SprOrganizationsDAO> setDBDataToObject(ResultSet rs) throws Exception {
        ArrayList<SprOrganizationsDAO> data = new ArrayList<SprOrganizationsDAO>();
        while (rs.next()) {
            data.add(new SprOrganizationsNtisDAO(Utils.getDouble(rs.getObject("ORG_ID")), //
                    rs.getString("ORG_NAME"), //
                    rs.getString("ORG_CODE"), //
                    rs.getString("ORG_TYPE"), //
                    rs.getString("ORG_REGION"), //
                    rs.getString("ORG_PHONE"), //
                    rs.getString("ORG_EMAIL"), //
                    rs.getString("ORG_WEBSITE"), //
                    rs.getString("ORG_ADDRESS"), //
                    rs.getString("ORG_HOUSE_NUMBER"), //
                    rs.getString("ORG_BANK_ACCOUNT"), //
                    rs.getString("ORG_BANK_NAME"), //
                    rs.getString("ORG_DELEGATION_PERSON"), //
                    rs.getString("ORG_DELEGATION_PERSON_POSITION"), //
                    rs.getTimestamp("ORG_DATE_FROM"), //
                    rs.getTimestamp("ORG_DATE_TO"), //
                    Utils.getDouble(rs.getObject("ORG_ORG_ID")), //
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

    private SprOrganizationsNtisDAO manageObjectData(Connection conn, SprOrganizationsNtisDAO daoObject) throws Exception {
        NtisOrgType orgType = null;
        if (daoObject.getOrgType() != null && !daoObject.getOrgType().isBlank()) {
            orgType = NtisOrgType.valueOf(daoObject.getOrgType());
        }
        if (orgType == NtisOrgType.PASLAUG || orgType == NtisOrgType.PASLAUG_VANDEN || orgType == NtisOrgType.VANDEN || orgType == NtisOrgType.INST_LT) {
            daoObject.setMunicipalityCode(null);
            if (daoObject.getOrgState() != null) {
                if (Utils.getLong(daoObject.getOrgState()).intValue() != NtisOrgState.DEREGISTERED.getCode()) {
                    if (daoObject.getOrgRegisteredDate() == null) {
                        daoObject.setOrgRegisteredDate(new Date());
                    }
                    daoObject.setOrgDeregisteredDate(null);
                    daoObject.setOrgRejectionReason(null);
                } else {
                    daoObject.setOrgDeregisteredDate(new Date());
                }
            }
        } else {
            daoObject.setOrgRejectionReason(null);
            daoObject.setOrgRegisteredDate(null);
            daoObject.setOrgDeregisteredDate(null);
            if (orgType == NtisOrgType.INST && daoObject.getMunicipalityCode() == null) {
                throw new SparkBusinessException(
                        new S2Message("SPR_ORGANIZATIONS", "orgMunicipality", "Institucijai turi būti nuroadytas savivaldybės kodas", SparkMessageType.ERROR));
            }
        }
        if (daoObject.getOrg_id() != null) {
            this.manageOrgRoles(conn, daoObject);
        }
        return daoObject;
    }

    @Override
    public void deleteRecord(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams("""
                update spr_organizations
                set org_date_to = current_date - 1
                where org_id = ?::int
                """);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

        stmt = new StatementAndParams("""
                update spr_org_available_roles
                set oar_date_to = current_date - 1
                where oar_org_id = ?::int
                """);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

        stmt = new StatementAndParams("""
                update spr_org_users
                set ou_date_to = current_date - 1
                where ou_org_id = ?::int
                """);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

        stmt = new StatementAndParams("""
                update spr_users
                set usr_org_id = null
                where usr_org_id = ?::int
                """);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);

        stmt = new StatementAndParams("""
                with org_users as
                        (select
                            usr_id
                        from spr_users
                        inner join spr_org_users on usr_id = ou_usr_id
                        where ou_org_id = ?::int),
                     users_in_other_orgs as
                        (select
                            ou_usr_id
                        from spr_org_users
                        where current_date between ou_date_from and coalesce(ou_date_to, current_date)
                              and ou_usr_id in (select usr_id from org_users)
                              and ou_org_id <> ?::int
                        group by ou_usr_id)
                update spr_users
                    set usr_date_to = current_date - 1,
                        usr_lock_date = current_date - 1
                where usr_id in (select usr_id from org_users) and
                      usr_id not in (select ou_usr_id from users_in_other_orgs)
                """);
        stmt.addSelectParam(identifier);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        conn.commit();
        try {
            super.deleteRecord(conn, identifier);
        } catch (Exception ex) {
            throw new SparkBusinessException(new S2Message("common.error.orgCannotBeDeleted", SparkMessageType.WARNING));
        }

    }

    private void manageOrgRoles(Connection conn, SprOrganizationsNtisDAO updatedOrg) throws Exception {
        SprOrganizationsNtisDAO oldOrgDAO = (SprOrganizationsNtisDAO) this.loadRecord(conn, updatedOrg.getOrg_id());
        NtisOrgType oldOrgType = null;
        NtisOrgType updatedOrgType = null;
        List<SprRolesDAO> rolesToAdd = new ArrayList<SprRolesDAO>();

        if (updatedOrg.getOrgType() != null && !updatedOrg.getOrgType().isBlank()) {
            updatedOrgType = NtisOrgType.valueOf(updatedOrg.getOrgType());
            rolesToAdd = this.getRoleIds(conn, updatedOrg);
        }

        if (oldOrgDAO.getOrgType() != null && !oldOrgDAO.getOrgType().isBlank()) {
            oldOrgType = NtisOrgType.valueOf(oldOrgDAO.getOrgType().trim());
        }

        if ((updatedOrgType != null && oldOrgType != null && !updatedOrgType.equals(oldOrgType)) || (updatedOrgType == null && oldOrgType != null)
                || (updatedOrgType != null && oldOrgType == null) || (updatedOrg.getOrgState() != null && oldOrgDAO.getOrgState() == null)
                || (updatedOrg.getOrgState() == null && oldOrgDAO.getOrgState() != null
                        && oldOrgDAO.getOrgState().compareTo(NtisOrgStateConstants.DEREGISTERED) == 0)
                || (updatedOrg.getOrgState() != null && oldOrgDAO.getOrgState() != null && updatedOrg.getOrgState().compareTo(oldOrgDAO.getOrgState()) != 0)
                || ((updatedOrg.getFacilityInstallerDateFrom() != null && oldOrgDAO.getFacilityInstallerDateFrom() == null)
                        || updatedOrg.getFacilityInstallerDateFrom() == null && oldOrgDAO.getFacilityInstallerDateFrom() != null)) {

            // set date_to for all org roles
            StatementAndParams stmt = new StatementAndParams("""
                    update spr_org_available_roles
                    set oar_date_to = current_date - 1
                    where oar_org_id = ?::int
                    and oar_rol_id not in (select rol_id from spr_roles where rol_code in (?, ?, ?))
                                """);
            stmt.addSelectParam(updatedOrg.getOrg_id());
            stmt.addSelectParam(NtisRolesConstants.INTS_OWNER_ORG_ADMIN);
            stmt.addSelectParam(NtisRolesConstants.ADMIN);
            stmt.addSelectParam(NtisRolesConstants.NTIS_ADMIN);
            baseControllerJDBC.adjustRecordsInDB(conn, stmt);

            // add new org available roles or remove date_to
            SprOrgAvailableRolesDAO existingOrgInstallationAdminRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.INSTALLATION_ADMIN).getRol_id()));
            SprOrgAvailableRolesDAO existingOrgInstallationSpecialistRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.INSTALLATION_SPECIALIST).getRol_id()));
            SprOrgAvailableRolesDAO existingOrgLabSpecRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.LAB_SPECIALIST).getRol_id()));
            SprOrgAvailableRolesDAO existingOrgPlantSpecRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.PLANT_SPECIALIST).getRol_id()));
            SprOrgAvailableRolesDAO existingOrgCarSpecRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.CAR_SPECIALIST).getRol_id()));
            SprOrgAvailableRolesDAO existingOrgTechSpecRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.TECH_SPECIALIST).getRol_id()));
            SprOrgAvailableRolesDAO existingOrgCotManagerRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                    " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()),
                    new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.CONTRACT_MANAGER).getRol_id()));

            for (SprRolesDAO role : rolesToAdd) {
                SprOrgAvailableRolesDAO existingRole = this.sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                        " oar_org_id = ?::int and oar_rol_id = ?::int ", new SelectParamValue(updatedOrg.getOrg_id()), new SelectParamValue(role.getRol_id()));
                if (existingRole != null) {
                    existingRole.setOar_date_to(null);
                    this.sprOrgAvailableRolesDBService.saveRecord(conn, existingRole);
                } else if ((!role.getRol_code().equals(NtisRolesConstants.CAR_SPECIALIST) && !role.getRol_code().equals(NtisRolesConstants.TECH_SPECIALIST)
                        && !role.getRol_code().equals(NtisRolesConstants.PLANT_SPECIALIST) && !role.getRol_code().equals(NtisRolesConstants.LAB_SPECIALIST)
                        && !role.getRol_code().equals(NtisRolesConstants.CAR_SPECIALIST_DISABLED)
                        && !role.getRol_code().equals(NtisRolesConstants.TECH_SPECIALIST_DISABLED)
                        && !role.getRol_code().equals(NtisRolesConstants.PLANT_SPECIALIST_DISABLED)
                        && !role.getRol_code().equals(NtisRolesConstants.LAB_SPECIALIST_DISABLED)
                        && !role.getRol_code().equals(NtisRolesConstants.CONTRACT_MANAGER)
                        && !role.getRol_code().equals(NtisRolesConstants.CONTRACT_MANAGER_DISABLED)
                        && !role.getRol_code().equals(NtisRolesConstants.INSTALLATION_ADMIN)
                        && !role.getRol_code().equals(NtisRolesConstants.INSTALLATION_ADMIN_DISABLED)
                        && !role.getRol_code().equals(NtisRolesConstants.INSTALLATION_SPECIALIST)
                        && !role.getRol_code().equals(NtisRolesConstants.INSTALLATION_SPECIALIST_DISABLED))
                        || (existingOrgLabSpecRole != null && role.getRol_code().equals(NtisRolesConstants.LAB_SPECIALIST_DISABLED))
                        || (existingOrgInstallationAdminRole != null && role.getRol_code().equals(NtisRolesConstants.INSTALLATION_ADMIN_DISABLED))
                        || (existingOrgInstallationAdminRole == null && role.getRol_code().equals(NtisRolesConstants.INSTALLATION_ADMIN))
                        || (existingOrgInstallationSpecialistRole == null && role.getRol_code().equals(NtisRolesConstants.INSTALLATION_SPECIALIST))
                        || (existingOrgInstallationSpecialistRole != null && role.getRol_code().equals(NtisRolesConstants.INSTALLATION_SPECIALIST_DISABLED))
                        || (existingOrgPlantSpecRole != null && role.getRol_code().equals(NtisRolesConstants.PLANT_SPECIALIST_DISABLED))
                        || (existingOrgCarSpecRole != null && role.getRol_code().equals(NtisRolesConstants.CAR_SPECIALIST_DISABLED))
                        || (existingOrgTechSpecRole != null && role.getRol_code().equals(NtisRolesConstants.TECH_SPECIALIST_DISABLED))
                        || (existingOrgCotManagerRole != null && role.getRol_code().equals(NtisRolesConstants.CONTRACT_MANAGER_DISABLED))) {
                    SprOrgAvailableRolesDAO newRole = this.sprOrgAvailableRolesDBService.newRecord();
                    newRole.setOar_date_from(Utils.getDate(new Date()));
                    newRole.setOar_org_id(updatedOrg.getOrg_id());
                    newRole.setOar_rol_id(role.getRol_id());
                    this.sprOrgAvailableRolesDBService.saveRecord(conn, newRole);
                }
            }

            // manage org users roles
            List<SprOrgUsersDAO> orgUsers = sprOrgUsersDbService.loadRecordsByParams(conn, " ou_org_id = ?::int ",
                    new SelectParamValue(updatedOrg.getOrg_id()));

            for (SprOrgUsersDAO user : orgUsers) {
                SprOrgUserRolesDAO existingInstallationAdminRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn,
                        " our_ou_id = ?::int and our_rol_id = ?::int ", new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.INSTALLATION_ADMIN).getRol_id()));
                SprOrgUserRolesDAO existingInstallationSpecialistRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn,
                        " our_ou_id = ?::int and our_rol_id = ?::int ", new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.INSTALLATION_SPECIALIST).getRol_id()));
                SprOrgUserRolesDAO existingPaslAdminRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn,
                        " our_ou_id = ?::int and our_rol_id = ?::int ", new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.PASL_ADMIN).getRol_id()));
                SprOrgUserRolesDAO existingVandAdminRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn,
                        " our_ou_id = ?::int and our_rol_id = ?::int ", new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.VAND_ADMIN).getRol_id()));
                SprOrgUserRolesDAO existingLabSpecRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn, " our_ou_id = ?::int and our_rol_id = ?::int ",
                        new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.LAB_SPECIALIST).getRol_id()));
                SprOrgUserRolesDAO existingPlantSpecRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn,
                        " our_ou_id = ?::int and our_rol_id = ?::int ", new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.PLANT_SPECIALIST).getRol_id()));
                SprOrgUserRolesDAO existingCarSpecRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn, " our_ou_id = ?::int and our_rol_id = ?::int ",
                        new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.CAR_SPECIALIST).getRol_id()));
                SprOrgUserRolesDAO existingTechSpecRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn, " our_ou_id = ?::int and our_rol_id = ?::int ",
                        new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.TECH_SPECIALIST).getRol_id()));
                SprOrgUserRolesDAO existingCotManagerRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn,
                        " our_ou_id = ?::int and our_rol_id = ?::int ", new SelectParamValue(user.getOu_id()),
                        new SelectParamValue(this.sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.CONTRACT_MANAGER).getRol_id()));

                StatementAndParams orgUserRolesStatement = new StatementAndParams();
                orgUserRolesStatement.setStatement("""
                        update spr_org_user_roles
                          set our_date_to = current_date - 1
                          where our_ou_id = ?::int
                          and our_rol_id not in (select rol_id from spr_roles where rol_code in (?, ?, ?))
                        """);
                orgUserRolesStatement.addSelectParam(user.getOu_id());
                orgUserRolesStatement.addSelectParam(NtisRolesConstants.INTS_OWNER_ORG_ADMIN);
                orgUserRolesStatement.addSelectParam(NtisRolesConstants.ADMIN);
                orgUserRolesStatement.addSelectParam(NtisRolesConstants.NTIS_ADMIN);
                baseControllerJDBC.adjustRecordsInDB(conn, orgUserRolesStatement);

                for (SprRolesDAO role : rolesToAdd) {
                    SprOrgUserRolesDAO existingRole = this.sprOrgUserRolesDBService.loadRecordByParams(conn, " our_ou_id = ?::int and our_rol_id = ?::int ",
                            new SelectParamValue(user.getOu_id()), new SelectParamValue(role.getRol_id()));
                    if (existingRole != null) {
                        existingRole.setOur_date_to(null);
                        this.sprOrgUserRolesDBService.saveRecord(conn, existingRole);
                    } else if (existingInstallationAdminRole != null && role.getRol_code().equals(NtisRolesConstants.INSTALLATION_ADMIN_DISABLED)
                            || existingInstallationSpecialistRole != null && role.getRol_code().equals(NtisRolesConstants.INSTALLATION_SPECIALIST_DISABLED)
                            || existingPaslAdminRole != null && role.getRol_code().equals(NtisRolesConstants.PASL_ADMIN_DISABLED)
                            || existingVandAdminRole != null && role.getRol_code().equals(NtisRolesConstants.VAND_ADMIN_DISABLED)
                            || existingLabSpecRole != null && role.getRol_code().equals(NtisRolesConstants.LAB_SPECIALIST_DISABLED)
                            || existingTechSpecRole != null && role.getRol_code().equals(NtisRolesConstants.TECH_SPECIALIST_DISABLED)
                            || existingCarSpecRole != null && role.getRol_code().equals(NtisRolesConstants.CAR_SPECIALIST_DISABLED)
                            || existingPlantSpecRole != null && role.getRol_code().equals(NtisRolesConstants.PLANT_SPECIALIST_DISABLED)
                            || existingCotManagerRole != null && role.getRol_code().equals(NtisRolesConstants.CONTRACT_MANAGER_DISABLED)) {
                        SprOrgUserRolesDAO newRole = sprOrgUserRolesDBService.newRecord();
                        newRole.setOur_ou_id(user.getOu_id());
                        newRole.setOur_date_from(Utils.getDate(new Date()));
                        newRole.setOur_rol_id(role.getRol_id());
                        sprOrgUserRolesDBService.saveRecord(conn, newRole);
                    }
                }
                sprUserRolesDBService.refreshUserProfile(conn, user.getOu_usr_id());
            }
        }
    }

    private void addRolesForNewPrivateOrg(Connection conn, SprOrganizationsNtisDAO newOrg) throws Exception {
        List<SprRolesDAO> rolesToAdd = new ArrayList<>();
        List<SprOrgAvailableRolesDAO> existingNewOrgRoles = this.sprOrgAvailableRolesDBService.loadRecordsByParams(conn, """
                where oar_org_id = ?::int and oar_rol_id IN (select rol_id from spr_roles where rol_code in (?))
                """, new SelectParamValue(newOrg.getOrg_id()), new SelectParamValue(NtisRolesConstants.INTS_OWNER_ORG_ADMIN));
        if (existingNewOrgRoles == null || existingNewOrgRoles.isEmpty()) {
            rolesToAdd.addAll(
                    this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?) ", new SelectParamValue(NtisRolesConstants.INTS_OWNER_ORG_ADMIN)));
        }
        existingNewOrgRoles = this.sprOrgAvailableRolesDBService.loadRecordsByParams(conn, """
                where oar_org_id = ?::int and oar_rol_id IN (select rol_id from spr_roles where rol_code in (?))
                """, new SelectParamValue(newOrg.getOrg_id()), new SelectParamValue(NtisRolesConstants.INTS_OWNER));
        if (existingNewOrgRoles == null || existingNewOrgRoles.isEmpty()) {
            rolesToAdd.addAll(this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?) ", new SelectParamValue(NtisRolesConstants.INTS_OWNER)));
        }
        for (SprRolesDAO role : rolesToAdd) {
            SprOrgAvailableRolesDAO newRole = this.sprOrgAvailableRolesDBService.newRecord();
            newRole.setOar_date_from(Utils.getDate(new Date()));
            newRole.setOar_org_id(newOrg.getOrg_id());
            newRole.setOar_rol_id(role.getRol_id());
            this.sprOrgAvailableRolesDBService.saveRecord(conn, newRole);
        }
    }

    private void addRolesForNewOrg(Connection conn, SprOrganizationsNtisDAO newOrg) throws Exception {
        List<SprRolesDAO> rolesToAdd = new ArrayList<>();
        if (newOrg.getOrgType() != null && !newOrg.getOrgType().isBlank()) {
            rolesToAdd = this.getRoleIds(conn, newOrg);
        } else {
            List<SprOrgAvailableRolesDAO> existingNewOrgRoles = this.sprOrgAvailableRolesDBService.loadRecordsByParams(conn, """
                    where oar_org_id = ?::int and oar_rol_id = (select rol_id from spr_roles where rol_code = ?)::int
                    """, new SelectParamValue(newOrg.getOrg_id()), new SelectParamValue(NtisRolesConstants.ORG_NEW));
            if (existingNewOrgRoles == null || existingNewOrgRoles.isEmpty()) {
                rolesToAdd.addAll(this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?) ", new SelectParamValue(NtisRolesConstants.ORG_NEW)));
            }
            existingNewOrgRoles = this.sprOrgAvailableRolesDBService.loadRecordsByParams(conn, """
                    where oar_org_id = ?::int and oar_rol_id = (select rol_id from spr_roles where rol_code = ?)::int
                     """, new SelectParamValue(newOrg.getOrg_id()), new SelectParamValue(NtisRolesConstants.INTS_OWNER_ORG_ADMIN));
            if (existingNewOrgRoles == null || existingNewOrgRoles.isEmpty()) {
                rolesToAdd.addAll(
                        this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?) ", new SelectParamValue(NtisRolesConstants.INTS_OWNER_ORG_ADMIN)));
            }
            existingNewOrgRoles = this.sprOrgAvailableRolesDBService.loadRecordsByParams(conn, """
                    where oar_org_id = ?::int and oar_rol_id = (select rol_id from spr_roles where rol_code = ?)::int
                     """, new SelectParamValue(newOrg.getOrg_id()), new SelectParamValue(NtisRolesConstants.INTS_OWNER));
            if (existingNewOrgRoles == null || existingNewOrgRoles.isEmpty()) {
                rolesToAdd.addAll(this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?) ", new SelectParamValue(NtisRolesConstants.INTS_OWNER)));
            }

        }
        for (SprRolesDAO role : rolesToAdd) {
            SprOrgAvailableRolesDAO newRole = this.sprOrgAvailableRolesDBService.newRecord();
            newRole.setOar_date_from(Utils.getDate(new Date()));
            newRole.setOar_org_id(newOrg.getOrg_id());
            newRole.setOar_rol_id(role.getRol_id());
            this.sprOrgAvailableRolesDBService.saveRecord(conn, newRole);
        }
    }

    private List<SprRolesDAO> getRoleIds(Connection conn, SprOrganizationsNtisDAO org) throws Exception {
        List<SprRolesDAO> roleList = new ArrayList<SprRolesDAO>();
        if (org.getOrgType().equalsIgnoreCase(NtisOrgType.INST_LT.getCode())) {
            roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?) ", new SelectParamValue(NtisRolesConstants.INST_ADMIN),
                    new SelectParamValue(NtisRolesConstants.INST_WORK), new SelectParamValue(NtisRolesConstants.INSPECTOR));
        } else if (org.getOrgType().equalsIgnoreCase(NtisOrgType.INST.getCode())) {
            roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?) ", new SelectParamValue(NtisRolesConstants.INST_ADMIN),
                    new SelectParamValue(NtisRolesConstants.INST_WORK), new SelectParamValue(NtisRolesConstants.MUNICI_SPECIALIST),
                    new SelectParamValue(NtisRolesConstants.INSPECTOR));
        } else if (org.getOrgType().equalsIgnoreCase(NtisOrgType.VANDEN.getCode())) {
            if (org.getOrgState() == null || org.getOrgState().compareTo(NtisOrgStateConstants.REGISTERED) == 0) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?) ", new SelectParamValue(NtisRolesConstants.VAND_ADMIN),
                        new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST));
            } else {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?) ",
                        new SelectParamValue(NtisRolesConstants.VAND_ADMIN_DISABLED), new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST_DISABLED));
            }
        } else if (org.getOrgType().equalsIgnoreCase(NtisOrgType.PASLAUG.getCode())) {
            if ((org.getOrgState() == null || org.getOrgState().compareTo(NtisOrgStateConstants.REGISTERED) == 0) && org.getFacilityInstallerDateFrom() != null
                    && org.getFacilityInstallerDateFrom().before(new Date())) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN), new SelectParamValue(NtisRolesConstants.INSTALLATION_ADMIN),
                        new SelectParamValue(NtisRolesConstants.INSTALLATION_SPECIALIST), new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST), new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER));
            } else if ((org.getOrgState() == null || org.getOrgState().compareTo(NtisOrgStateConstants.REGISTERED) == 0)
                    && (org.getFacilityInstallerDateFrom() == null || org.getFacilityInstallerDateFrom().after(new Date()))) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN), new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST), new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER));
            } else if (org.getFacilityInstallerDateFrom() != null && org.getFacilityInstallerDateFrom().before(new Date())) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN_DISABLED), new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.INSTALLATION_ADMIN_DISABLED),
                        new SelectParamValue(NtisRolesConstants.INSTALLATION_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST_DISABLED), new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER_DISABLED));
            } else {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN_DISABLED), new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST_DISABLED), new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER_DISABLED));
            }
        } else if (org.getOrgType().equalsIgnoreCase(NtisOrgType.PASLAUG_VANDEN.getCode())) {
            if ((org.getOrgState() == null || org.getOrgState().compareTo(NtisOrgStateConstants.REGISTERED) == 0) && org.getFacilityInstallerDateFrom() != null
                    && org.getFacilityInstallerDateFrom().before(new Date())) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN), new SelectParamValue(NtisRolesConstants.VAND_ADMIN),
                        new SelectParamValue(NtisRolesConstants.INSTALLATION_ADMIN), new SelectParamValue(NtisRolesConstants.INSTALLATION_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST), new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST), new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER));
            } else if ((org.getOrgState() == null || org.getOrgState().compareTo(NtisOrgStateConstants.REGISTERED) == 0)
                    && (org.getFacilityInstallerDateFrom() == null || org.getFacilityInstallerDateFrom().after(new Date()))) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?, ?,  ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN), new SelectParamValue(NtisRolesConstants.VAND_ADMIN),
                        new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST), new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST), new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER));
            } else if (org.getFacilityInstallerDateFrom() != null && org.getFacilityInstallerDateFrom().before(new Date())) {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN_DISABLED), new SelectParamValue(NtisRolesConstants.VAND_ADMIN_DISABLED),
                        new SelectParamValue(NtisRolesConstants.INSTALLATION_ADMIN_DISABLED),
                        new SelectParamValue(NtisRolesConstants.INSTALLATION_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST_DISABLED), new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST_DISABLED), new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER_DISABLED));
            } else {
                roleList = this.sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?, ?, ?, ?, ?, ?) ",
                        new SelectParamValue(NtisRolesConstants.PASL_ADMIN_DISABLED), new SelectParamValue(NtisRolesConstants.VAND_ADMIN_DISABLED),
                        new SelectParamValue(NtisRolesConstants.CAR_SPECIALIST_DISABLED), new SelectParamValue(NtisRolesConstants.TECH_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.LAB_SPECIALIST_DISABLED), new SelectParamValue(NtisRolesConstants.PLANT_SPECIALIST_DISABLED),
                        new SelectParamValue(NtisRolesConstants.CONTRACT_MANAGER_DISABLED));
            }
        }
        return roleList;
    }

    @Override
    public SprOrganizationsDAO insertRecord(Connection conn, SprOrganizationsDAO daoObject) throws Exception {
        manageObjectData(conn, (SprOrganizationsNtisDAO) daoObject);
        super.insertRecord(conn, daoObject);
        this.addRolesForNewOrg(conn, (SprOrganizationsNtisDAO) daoObject);
        return daoObject;
    }

    @Override
    public SprOrganizationsDAO updateRecord(Connection conn, SprOrganizationsDAO daoObject) throws Exception {
        String roleToAdd = daoObject.getC05();
        if (daoObject.getN04() == null || (daoObject.getN04() != null && daoObject.getN04().compareTo(1.0) != 0)) {
            manageObjectData(conn, (SprOrganizationsNtisDAO) daoObject);
            List<SprOrgAvailableRolesDAO> orgAvailableRoles;
            if (roleToAdd != null && NtisRolesConstants.INTS_OWNER_ORG_ADMIN.equals(roleToAdd)) {
                orgAvailableRoles = sprOrgAvailableRolesDBService.loadRecordsByParams(conn,
                        " oar_org_id = ?::int AND now() BETWEEN oar_date_from AND COALESCE(oar_date_to, now())"
                                + " and exists (select 1 from spr_roles where rol_code = ? and oar_rol_id = rol_id ) ",
                        new SelectParamValue(daoObject.getOrg_id()), new SelectParamValue(roleToAdd));

                if (orgAvailableRoles == null || orgAvailableRoles.isEmpty()) {
                    this.addRolesForNewPrivateOrg(conn, (SprOrganizationsNtisDAO) daoObject);
                }
            } else {
                orgAvailableRoles = sprOrgAvailableRolesDBService.loadRecordsByParams(conn,
                        " oar_org_id = ?::int AND now() BETWEEN oar_date_from AND COALESCE(oar_date_to, now())"
                                + " and exists (select 1 from spr_roles where rol_code != ? and oar_rol_id = rol_id ) ",
                        new SelectParamValue(daoObject.getOrg_id()), new SelectParamValue(NtisRolesConstants.INTS_OWNER_ORG_ADMIN));
                if (orgAvailableRoles == null || orgAvailableRoles.isEmpty()) {
                    this.addRolesForNewOrg(conn, (SprOrganizationsNtisDAO) daoObject);
                }
            }
        }
        daoObject.setN04(null);
        return super.updateRecord(conn, daoObject);
    }
}