package lt.project.ntis.app;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.SprExternalAuthorization;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.EidType;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprOrgAvailableRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersNtisDAO;
import eu.itreegroup.spark.modules.admin.service.SprOrgAvailableRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUserExtIdentificationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;

public class SprExternalNtisAuthorization extends SprExternalAuthorization {

    private static final Logger log = LoggerFactory.getLogger(SprExternalNtisAuthorization.class);

    private static final String PRIVATE_LOGIN_TYPE = "PRIVATE";

    private static final String ORGANIZATION = "ORGANIZATION";

    private static final String ORGANIZATION_LOGGED = "ORGANIZATION_LOGGED";

    private static final String ORG_FROM_PRIVATE = "ORG_FROM_PRIVATE";

    public static final String ORG_PRIVATE_LOGIN_TYPE = "PRIVATE_ORG";

    public SprExternalNtisAuthorization(SprUserExtIdentificationsDBService sprUserExtIdentificationsDBService, //
            SprUsersDBService sprUsersDBService, //
            SprRolesDBService sprRolesDBService, //
            SprUserRolesDBService sprUserRolesDBService, //
            DBPropertyManager dBPropertyManager, //
            SprAuthorization<SprBackendUserSession> sprAuthorization, //
            BaseControllerJDBC baseControllerJDBC, //
            SprPersonsDBService sprPersonsDBService, //
            SprOrganizationsDBService sprOrganizationsDBService, //
            SprOrgUsersDBService sprOrgUsersDBService, //
            SprOrgAvailableRolesDBService sprOrgAvailableRolesDBService, //
            SprOrgUserRolesDBService sprOrgUserRolesDBService) {
        super(sprUserExtIdentificationsDBService, //
                sprUsersDBService, //
                sprRolesDBService, //
                sprUserRolesDBService, //
                dBPropertyManager, //
                sprAuthorization, //
                baseControllerJDBC, //
                sprPersonsDBService, //
                sprOrganizationsDBService, //
                sprOrgUsersDBService, //
                sprOrgAvailableRolesDBService, //
                sprOrgUserRolesDBService);
    }

    protected SprRolesDAO loadDefaultRoleDataByLoginType(Connection conn, String orgType) throws Exception {
        return sprRolesDBService.loadRecordByRoleCode(conn,
                orgType.equals(ORG_PRIVATE_LOGIN_TYPE) ? NtisRolesConstants.INTS_OWNER : NtisRolesConstants.ORG_NEW);
    }

    /**
     * Create user session for VIISP authenticated user. Session will be created for user that was authenticated as responsible person of organization.
     * In case if such user and organization do not exists in system they will be created. For new created user will be added role that are assigned to the
     * parameter by name DEFAULT_NEW_ORG_ROLE_CODE
     * @param conn - connection to the db
     * @param userSession - back end user session
     * @param name - user first name
     * @param lastname - user last name
     * @param personCode - user person code
     * @param orgName - organization name
     * @param orgCode - organization code
     * @param authExtData - external authorization data that will be provided from front end.
     * @return back end session for provided data.
     * @throws Exception
     */
    @Override
    public SprBackendUserSession createVIISPOrganizationUserSession(Connection conn, SprBackendUserSession userSession, String name, String lastname,
            String personCode, String orgName, String orgCode, Map<String, Object> authExtData) throws Exception {
        String roleForOrg;
        if (authExtData.get("USER_TYPE").equals(PRIVATE_LOGIN_TYPE)) {
            authExtData.put("USER_TYPE", ORG_PRIVATE_LOGIN_TYPE);
            roleForOrg = NtisRolesConstants.INTS_OWNER;
        } else {
            roleForOrg = NtisRolesConstants.ORG_NEW;
        }
        if (authExtData.get("USER_TYPE").equals(ORGANIZATION)) {
            authExtData.put("USER_TYPE", ORGANIZATION_LOGGED);
        }
        SprBackendUserSession localUserSession = createVIISPUserSession(conn, userSession, name, lastname, personCode.trim(), authExtData);
        SprOrganizationsDAO organizationDAO = sprOrganizationsDBService.loadRecordByParams(conn, "org_code = trim(?)  ", new SelectParamValue(orgCode));

        boolean orgHasAdminRoleAvailable = false;
        List<SprOrgAvailableRolesDAO> orgAvailableRoles = null;
        SprRolesDAO roleDAO = null;
        if (orgName != null) {
            orgName = orgName.trim();
        }
        if (orgCode != null) {
            orgCode = orgCode.trim();
        }
        roleDAO = loadDefaultRoleDataByLoginType(conn, authExtData.get("USER_TYPE").toString());
        if (organizationDAO == null) {
            if (orgName == null || orgName.isBlank()) {
                throw new SparkBusinessException(
                        new S2Message("common.error.orgNameNotProvided", SparkMessageType.ERROR, "Organization name was not provided"));
            }
            organizationDAO = sprOrganizationsDBService.newRecord();
            organizationDAO.setOrg_name(orgName);
            organizationDAO.setOrg_code(orgCode);
            organizationDAO.setOrg_date_from(Utils.getDate());
            organizationDAO.setOrg_delegation_person(buildFullName(name, lastname));
            organizationDAO.setC05(roleForOrg); // Role by login type
            organizationDAO = sprOrganizationsDBService.saveRecord(conn, organizationDAO);
        } else if (!authExtData.get("USER_TYPE").equals(ORG_FROM_PRIVATE)) {
            StatementAndParams stmt = new StatementAndParams();
            if (roleForOrg.equalsIgnoreCase(NtisRolesConstants.ORG_NEW)) {
                stmt.setStatement("""
                        SELECT oar_id,
                               oar_rol_id
                          FROM spr_org_available_roles oar
                          JOIN spr_roles rol ON oar_rol_id = rol_id
                         WHERE oar_org_id = ?::int
                           AND CURRENT_DATE between oar_date_from and COALESCE(oar_date_to, now())
                           AND rol_code <> ?
                           AND rol_id <> ?::int
                        """);
            } else if (roleForOrg.equalsIgnoreCase(NtisRolesConstants.INTS_OWNER)) {
                stmt.setStatement("""
                        SELECT oar_id,
                               oar_rol_id
                          FROM spr_org_available_roles oar
                          JOIN spr_roles rol ON oar_rol_id = rol_id
                         WHERE oar_org_id = ?::int
                           AND CURRENT_DATE between oar_date_from and COALESCE(oar_date_to, now())
                           AND rol_code = ?
                           AND rol_id <> ?::int
                        """);
            }
            stmt.addSelectParam(organizationDAO.getOrg_id());
            stmt.addSelectParam(NtisRolesConstants.INTS_OWNER);
            stmt.addSelectParam(roleDAO.getRol_id());
            orgAvailableRoles = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprOrgAvailableRolesDAO.class);
            if (orgAvailableRoles == null || orgAvailableRoles.isEmpty()) {
                roleDAO = loadDefaultRoleDataByLoginType(conn, authExtData.get("USER_TYPE").toString());
            } else {
                Set<Double> orgAvailableRolesIds = collectOrgRolesIds(orgAvailableRoles);
                Set<Double> adminRolesIds = collectAdminRolesIds(conn);
                orgHasAdminRoleAvailable = !Collections.disjoint(orgAvailableRolesIds, adminRolesIds);
            }
            organizationDAO.setOrg_delegation_person(buildFullName(name, lastname));
            if (orgName != null && !"".equals(orgName.trim())) {
                organizationDAO.setOrg_name(orgName);
            }
            organizationDAO.setC05(roleForOrg); // Role by login type
            organizationDAO = sprOrganizationsDBService.saveRecord(conn, organizationDAO);
        }
        SprOrgUsersDAO orgUser = sprOrgUsersDBService.loadRecordByParams(conn, "ou_org_id = ?::int and ou_usr_id = ?::int ",
                new SelectParamValue(organizationDAO.getOrg_id()), new SelectParamValue(localUserSession.getSes_usr_id()));
        if (orgUser == null) {
            orgUser = sprOrgUsersDBService.newRecord();
            orgUser.setOu_org_id(organizationDAO.getOrg_id());
            orgUser.setOu_usr_id(localUserSession.getSes_usr_id());
            orgUser.setOu_date_from(Utils.getDate());
            orgUser = sprOrgUsersDBService.saveRecord(conn, orgUser);
        }
        roleDAO = loadDefaultRoleDataByLoginType(conn, authExtData.get("USER_TYPE").toString());
        SprOrgUserRolesDAO orgUserRole = sprOrgUserRolesDBService.loadRecordByParams(conn, "our_ou_id = ?::int and our_rol_id = ?::int ",
                new SelectParamValue(orgUser.getOu_id()), new SelectParamValue(roleDAO.getRol_id()));
        SprOrgAvailableRolesDAO defaultRoleAvailable = sprOrgAvailableRolesDBService.loadRecordByParams(conn,
                "oar_rol_id = ?::int and CURRENT_DATE between oar_date_from and COALESCE(oar_date_to, now()) and oar_org_id = ?::int",
                new SelectParamValue(roleDAO.getRol_id()), new SelectParamValue(organizationDAO.getOrg_id()));
        if (!authExtData.get("USER_TYPE").equals(ORG_FROM_PRIVATE)) {
            if (orgUserRole == null && defaultRoleAvailable != null) {
                orgUserRole = sprOrgUserRolesDBService.newRecord();
                orgUserRole.setOur_ou_id(orgUser.getOu_id());
                orgUserRole.setOur_rol_id(roleDAO.getRol_id());
                orgUserRole.setOur_date_from(Utils.getDate());
                sprOrgUserRolesDBService.saveRecord(conn, orgUserRole);
            } else if (orgUserRole != null && defaultRoleAvailable == null) {
                StatementAndParams stmt = new StatementAndParams("""
                        delete from spr_org_user_roles
                        where our_rol_id = ?::int
                          and our_ou_id = ?::int
                        """);
                stmt.addSelectParam(roleDAO.getRol_id());
                stmt.addSelectParam(orgUser.getOu_id());
                baseControllerJDBC.adjustRecordsInDB(conn, stmt);
            }
            if (orgHasAdminRoleAvailable) {
                giveUserMissingOrgRoles(conn, orgUser, orgAvailableRoles);
            }
        } else {
            authExtData.put("USER_TYPE", ORGANIZATION);
        }
        sprAuthorization.setUserOrganization(conn, organizationDAO.getOrg_id().toString());
        return sprAuthorization.getUserSessionFromConetext();
    }

    @Override
    public SprBackendUserSession createExternalUserSession(Connection conn, EidType eidType, SprBackendUserSession userSession, String name, String lastname,
            String personCode, Map<String, Object> authExtData) throws Exception {
        List<SprOrganizationsDAO> dataOrg = new ArrayList<SprOrganizationsDAO>();
        SprUsersDAO sprUserDAO = null;
        boolean orgUserNotFound = false;
        if (authExtData.get("USER_TYPE").equals(ORGANIZATION)) {
            authExtData.put("USER_TYPE", ORG_FROM_PRIVATE);
            StatementAndParams stmt = new StatementAndParams("""
                    select per_name,
                           per_surname,
                           per_id
                           from spr_persons
                           where per_code = ?
                    """);
            stmt.setWhereExists(true);
            stmt.addSelectParam(personCode);
            List<SprPersonsDAO> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprPersonsDAO.class);
            if (data != null && !data.isEmpty()) {
                SprUsersDAO userDAO = sprUsersDBService.loadRecordByParams(conn, "usr_per_id = ?::int and usr_type = 'ORGANIZATION'",
                        new SelectParamValue(data.get(0).getPer_id()));
                if (userDAO != null && userDAO.getUsr_id() != null) {
                    StatementAndParams stmtForOrg = new StatementAndParams(
                            """
                                        select org_code,
                                               org_name,
                                               org_id
                                         from spr_organizations org
                                         inner join spr_org_users on ou_org_id = org_id and CURRENT_DATE between ou_date_from and coalesce(ou_date_to, now()) and ou_usr_id = ?::int
                                         where (org.c01 in ('PASLAUG', 'VANDEN', 'PASLAUG_VANDEN') or org.c05 = 'ORG_NEW')
                                    """);
                    stmtForOrg.addSelectParam(userDAO.getUsr_id());
                    dataOrg = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmtForOrg, SprOrganizationsDAO.class);
                    if (dataOrg != null && !dataOrg.isEmpty()) {
                        return this.createVIISPOrganizationUserSession(conn, userSession, name, lastname, personCode, dataOrg.get(0).getOrg_name(),
                                dataOrg.get(0).getOrg_code(), authExtData);
                    } else {
                        orgUserNotFound = true;
                    }
                } else {
                    orgUserNotFound = true;
                }
            } else {
                orgUserNotFound = true;
            }
        } else if (authExtData.get("USER_TYPE").equals(ORGANIZATION_LOGGED)) {
            authExtData.put("USER_TYPE", ORGANIZATION);
        }
        sprUserDAO = null;
        StatementAndParams stmt = new StatementAndParams("""
                select per_name,
                       per_surname,
                       per_id
                       from spr_persons
                       where per_code = ?
                """);
        stmt.setWhereExists(true);
        stmt.addSelectParam(personCode);
        stmt.setStatementOrderPart(" per_id DESC ");

        List<SprPersonsDAO> data = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, SprPersonsDAO.class);

        String perName = null;

        String perSurName = null;

        Double perId = null;

        SprPersonsDAO person = null;
        if (data.size() != 0 && !data.isEmpty()) {
            SprPersonsDAO rec = data.get(0);
            perName = rec.getPer_name();
            perSurName = rec.getPer_surname();
            perId = rec.getPer_id();
            person = sprPersonsDBService.loadRecord(conn, Utils.getDouble(perId));
            if ((perName == null || !perName.equals(name)) || (perSurName == null || !perSurName.equals(lastname))) {
                person.setPer_name(name);
                person.setPer_surname(lastname);
                person = sprPersonsDBService.saveRecord(conn, person);
            }
            boolean isOrgFromPrivate = authExtData.get("USER_TYPE").equals(ORG_FROM_PRIVATE);
            if (isOrgFromPrivate) {
                authExtData.put("USER_TYPE", ORGANIZATION);
            }
            sprUserDAO = sprUsersDBService.loadRecordByIdentifier(conn, person.getPer_id(), authExtData);
            if (isOrgFromPrivate) {
                authExtData.put("USER_TYPE", ORG_FROM_PRIVATE);
            }

            if (sprUserDAO != null && authExtData.get("LANGUAGE") != null && !((String) authExtData.get("LANGUAGE")).equals(sprUserDAO.getUsr_language())) {
                sprUserDAO.setUsr_language((String) authExtData.get("LANGUAGE"));
                sprUserDAO = sprUsersDBService.saveRecord(conn, sprUserDAO);
            }
            if (sprUserDAO != null && ((sprUserDAO.getUsr_person_name() == null || !sprUserDAO.getUsr_person_name().equals(name))
                    || (sprUserDAO.getUsr_person_surname() == null || !sprUserDAO.getUsr_person_surname().equals(lastname)))) {
                sprUserDAO.setUsr_person_name(name);
                sprUserDAO.setUsr_person_surname(lastname);
                sprUserDAO = sprUsersDBService.saveRecord(conn, sprUserDAO);
            }
        }

        if (sprUserDAO == null) {
            if (person == null) {
                person = createNewPerson(conn, name, lastname, personCode, null, false, authExtData);
            }
            String userName = ((Utils.replaceNationalCharacters(name.substring(0, Math.min(name.length(), 80))) + "."
                    + Utils.replaceNationalCharacters(lastname.substring(0, Math.min(lastname.length(), 80)))).replaceAll("[^a-zA-Z-\\.]", "") + "."
                    + person.getPer_id().intValue()).toLowerCase();
            List<SprUsersDAO> usrNameCheck = this.sprUsersDBService.loadRecordsByParams(conn, "usr_username like ? order by usr_id desc",
                    new SelectParamValue(userName + "%"));
            if (usrNameCheck != null && !usrNameCheck.isEmpty()) {
                String lastUserName = usrNameCheck.get(0).getUsr_username();
                userName = lastUserName + "1";
            }
            boolean isOrgFromPrivate = authExtData.get("USER_TYPE").equals(ORG_FROM_PRIVATE);
            if (isOrgFromPrivate) {
                authExtData.put("USER_TYPE", ORGANIZATION);
            }
            sprUserDAO = createUser(conn, eidType, null, userName, person.getPer_email(), false, name, lastname, person, authExtData);
            if (isOrgFromPrivate) {
                authExtData.put("USER_TYPE", ORG_FROM_PRIVATE);
                if (dataOrg.isEmpty()) {
                    SprOrganizationsDAO newOrg = sprOrganizationsDBService.newRecord();
                    newOrg.setOrg_name(person.getPer_name() + " " + person.getPer_surname() + " Individuali veikla");
                    newOrg.setOrg_code("IV-" + person.getPer_code());
                    newOrg.setOrg_type("IV");
                    newOrg.setOrg_date_from(Utils.getDate(new Date()));
                    sprOrganizationsDBService.saveRecord(conn, newOrg);

                    SprOrgUsersDAO orgUser = sprOrgUsersDBService.newRecord();
                    orgUser.setOu_date_from(Utils.getDate(new Date()));
                    orgUser.setOu_usr_id(sprUserDAO.getUsr_id());
                    orgUser.setOu_org_id(newOrg.getOrg_id());
                    sprOrgUsersDBService.saveRecord(conn, orgUser);

                    authExtData.put("USER_TYPE", ORGANIZATION);

                    userSession = this.createVIISPOrganizationUserSession(conn, userSession, person.getPer_name(), person.getPer_surname(), person.getPer_code(),
                            newOrg.getOrg_name(), newOrg.getOrg_code(), authExtData);
                    authExtData.put("USER_TYPE", ORG_FROM_PRIVATE);
                    if (orgUserNotFound) {
                        userSession.setRole_type(ORG_FROM_PRIVATE);
                        userSession.setSes_usr_type(ORG_FROM_PRIVATE);
                    }
                    return userSession;
                }
            }
        }
        userSession = sprAuthorization.createUserSession(conn, userSession, sprUserDAO, SprAuthorization.EXTERNAL_AUTHENTICATION);
        if (orgUserNotFound) {
            userSession.setRole_type(ORG_FROM_PRIVATE);
            userSession.setSes_usr_type(ORG_FROM_PRIVATE);
        }
        return userSession;

    }

    private void giveUserMissingOrgRoles(Connection conn, SprOrgUsersDAO orgUser, List<SprOrgAvailableRolesDAO> orgAvailableRoles) throws Exception {
        if (orgAvailableRoles != null && !orgAvailableRoles.isEmpty()) {
            Date now = Utils.getDate();
            Set<Double> userCurrentRoles = collectUserCurrentRolesIds(conn, orgUser);
            for (SprOrgAvailableRolesDAO role : orgAvailableRoles) {
                Double roleId = role.getOar_rol_id();
                if (!userCurrentRoles.contains(roleId)) {
                    SprOrgUserRolesDAO orgUserRole = sprOrgUserRolesDBService.newRecord();
                    orgUserRole.setOur_ou_id(orgUser.getOu_id());
                    orgUserRole.setOur_rol_id(roleId);
                    orgUserRole.setOur_date_from(now);
                    sprOrgUserRolesDBService.saveRecord(conn, orgUserRole);
                }
            }
        }
    }

    private Set<Double> collectUserCurrentRolesIds(Connection conn, SprOrgUsersDAO orgUser) throws Exception {
        List<SprOrgUserRolesDAO> currentRoles = sprOrgUserRolesDBService.loadRecordsByParams(conn,
                " our_ou_id = ?::int AND now() BETWEEN our_date_from AND COALESCE(our_date_to - INTERVAL '1 day', now()) ",
                new SelectParamValue(orgUser.getOu_id()));

        Set<Double> result = new HashSet<>();
        if (currentRoles != null && !currentRoles.isEmpty()) {
            for (SprOrgUserRolesDAO role : currentRoles) {
                result.add(role.getOur_rol_id());
            }
        }
        return result;
    }

    private Set<Double> collectOrgRolesIds(List<SprOrgAvailableRolesDAO> rolesList) {
        Set<Double> result = new HashSet<>();
        if (rolesList != null && !rolesList.isEmpty()) {
            for (SprOrgAvailableRolesDAO role : rolesList) {
                result.add(role.getOar_rol_id());
            }
        }
        return result;
    }

    private Set<Double> collectAdminRolesIds(Connection conn) throws Exception {
        List<SprRolesDAO> rolesList = sprRolesDBService.loadRecordsByParams(conn, " rol_code in (?, ?) ", new SelectParamValue(NtisRolesConstants.VAND_ADMIN),
                new SelectParamValue(NtisRolesConstants.PASL_ADMIN));

        Set<Double> result = new HashSet<>();
        if (rolesList != null && !rolesList.isEmpty()) {
            for (SprRolesDAO role : rolesList) {
                result.add(role.getRol_id());
            }
        }
        return result;
    }

    private static String buildFullName(String name, String surname) {
        return Stream.of(name, surname).filter(Objects::nonNull).collect(Collectors.joining(" "));
    }

    @Override
    protected SprUsersDAO createUser(Connection conn, EidType identificationType, String identificationCode, String username, String email,
            Boolean emailConfirmed, String name, String surname, SprPersonsDAO personDAO, Map<String, Object> authExtData) throws Exception {
        personDAO.setC05(identificationType.getValue());
        SprUsersNtisDAO userDAO = (SprUsersNtisDAO) super.createUser(conn, identificationType, identificationCode, username, email, emailConfirmed, name,
                surname, personDAO, authExtData);
        userDAO.setIsViispUser("Y");
        sprUsersDBService.saveRecord(conn, userDAO);
        if (userDAO.getUsr_per_id() != null && EidType.VIISP.equals(identificationType)) {
            personDAO = sprPersonsDBService.loadRecord(conn, userDAO.getUsr_per_id());
            personDAO.setPer_data_confirmed(YesNo.Y.getCode());
            personDAO.setPer_confirmation_date(personDAO.getPer_confirmation_date() != null ? personDAO.getPer_confirmation_date() : Utils.getDate(new Date()));
            sprPersonsDBService.saveRecord(conn, personDAO);
        }
        if (PRIVATE_LOGIN_TYPE.equals(userDAO.getUsr_type())) {
            SprRolesDAO rolesDAO = sprRolesDBService.loadRecordByRoleCode(conn, NtisRolesConstants.INTS_OWNER);
            SprUserRolesDAO userRolesDAO = sprUserRolesDBService.newRecord();
            userRolesDAO.setUro_usr_id(userDAO.getUsr_id());
            userRolesDAO.setUro_rol_id(rolesDAO.getRol_id());
            userRolesDAO.setUro_date_from(Utils.getDate());
            sprUserRolesDBService.saveRecord(conn, userRolesDAO);
        }
        return userDAO;
    }
}
