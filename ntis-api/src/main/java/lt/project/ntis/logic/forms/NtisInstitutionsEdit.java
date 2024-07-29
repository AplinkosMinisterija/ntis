package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import eu.itreegroup.spark.app.SprProcessRequest;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprPropertiesBrowse;
import eu.itreegroup.spark.modules.admin.service.SprOrgUserRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrgUsersDBService;
import eu.itreegroup.spark.modules.admin.service.SprOrganizationsDBService;
import eu.itreegroup.spark.modules.admin.service.SprPersonsDBService;
import eu.itreegroup.spark.modules.admin.service.SprRolesDBService;
import eu.itreegroup.spark.modules.admin.service.SprUsersDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.constants.NtisRolesConstants;
import lt.project.ntis.enums.NtisOrgType;
import lt.project.ntis.models.NtisInstitutionEditModel;

/**
 * Klasė skirta Valyklų sąrašo redagavimo formos biznio logikai apibrėžti
 */

@Component
public class NtisInstitutionsEdit extends FormBase {

    @Autowired
    SprOrganizationsDBService organizationsDbService;

    @Autowired
    SprPersonsDBService personsDbService;

    @Autowired
    SprRolesDBService rolesDbService;

    @Autowired
    SprOrgUserRolesDBService sprOrgUserRolesDbService;

    @Autowired
    SprUsersDBService usersDbService;

    @Autowired
    SprOrgUsersDBService orgUsersDbService;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprProcessRequest sprProcessRequest;

    @Override
    public String getFormName() {
        return "NTIS_INSTITUTIONS_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Institucijos redagavimas", "Institucijos redagavimo forma");
        addFormActions(FormBase.ACTION_READ, FormBase.ACTION_UPDATE, FormBase.ACTION_CREATE);
    }

    public NtisInstitutionEditModel getInstitutionAdmin(Connection conn, RecordIdentifier recordIdentifier) throws Exception {
        List<NtisInstitutionEditModel> data = new ArrayList<NtisInstitutionEditModel>();
        if ("new".equalsIgnoreCase(recordIdentifier.getId()) || "new".equalsIgnoreCase(recordIdentifier.getActionType())) {
            this.checkIsFormActionAssigned(conn, NtisInstitutionsEdit.ACTION_CREATE);
        } else {
            this.checkIsFormActionAssigned(conn, NtisInstitutionsEdit.ACTION_READ);
            StatementAndParams stmt = new StatementAndParams("""
                    select
                        org.org_id,
                        org.org_name,
                        org.org_code,
                        per.per_id,
                        ou.ou_id,
                        usr.usr_id,
                        usr.usr_username,
                        per.per_name,
                        per.per_surname,
                        per.per_email,
                        per.per_phone_number,
                        org.c01 as org_type,
                        org.n02 as municipality
                    from spr_organizations org
                    left join spr_org_users ou on ou.ou_org_id = org.org_id
                                                   and org.c01 in (?, ?) and ou.ou_usr_id = org.n05
                    left join spr_users usr on ou.ou_usr_id = usr.usr_id
                    left join spr_persons per on per.per_id = usr.usr_per_id
                    where org.org_id = ?::int
                    """);
            stmt.setWhereExists(true);
            stmt.addSelectParam(NtisOrgType.INST.getCode());
            stmt.addSelectParam(NtisOrgType.INST_LT.getCode());
            stmt.addSelectParam(recordIdentifier.getIdAsDouble());
            data = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisInstitutionEditModel.class);
        }
        return data != null && !data.isEmpty() ? data.get(0) : null;
    }

    public NtisInstitutionEditModel save(Connection conn, NtisInstitutionEditModel record, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, record.getOrg_id() == null ? SprPropertiesBrowse.ACTION_CREATE : SprPropertiesBrowse.ACTION_UPDATE);
        SprOrganizationsDAO orgDao = null;

        if (record.getOrg_code() != null) {
            orgDao = organizationsDbService.loadRecordByParams(conn, "org_code = ? ", new SelectParamValue(record.getOrg_code()));
            if (orgDao != null && ((record.getOrg_id() != null && orgDao.getOrg_id().compareTo(record.getOrg_id()) != 0) || record.getOrg_id() == null)) {
                throw new SparkBusinessException(
                        new S2Message("common.error.institutionAlreadyRegistered", SparkMessageType.ERROR, "Institution with this code is already registered"));
            }
        }

        if (record.getOrg_id() != null) {
            orgDao = organizationsDbService.loadRecord(conn, record.getOrg_id());
        } else {
            orgDao = organizationsDbService.newRecord();
        }
        orgDao.setOrg_code(record.getOrg_code());
        orgDao.setOrg_name(record.getOrg_name());
        orgDao.setC01(record.getOrg_type());
        orgDao.setN02(record.getMunicipality());
        orgDao.setOrg_date_from(Utils.getDate(new Date()));
        orgDao.setOrg_delegation_person(record.getPer_name() + ' ' + record.getPer_surname());
        organizationsDbService.saveRecord(conn, orgDao);
        record.setOrg_id(orgDao.getOrg_id());

        SprPersonsDAO perDao = null;
        if (record.getPer_id() != null) {
            perDao = personsDbService.loadRecord(conn, record.getPer_id());
        } else {
            perDao = personsDbService.newRecord();
        }
        perDao.setPer_name(record.getPer_name());
        perDao.setPer_surname(record.getPer_surname());
        perDao.setPer_email(record.getPer_email());
        perDao.setPer_phone_number(record.getPer_phone_number());
        if (record.getPer_id() == null) {
            perDao.setPer_date_of_birth(Utils.getDateFromString("1900-01-01", dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
            perDao.setPer_code_exists(DbConstants.BOOLEAN_FALSE);
            perDao.setPer_lrt_resident(DbConstants.BOOLEAN_TRUE);
        }
        personsDbService.saveRecord(conn, perDao);
        record.setPer_id(perDao.getPer_id());

        SprUsersDAO userDao = null;
        if (record.getUsr_id() != null) {
            userDao = usersDbService.loadRecord(conn, record.getUsr_id());
        } else {
            userDao = usersDbService.newRecord();
        }
        userDao.setUsr_per_id(perDao.getPer_id());
        userDao.setUsr_username(record.getUsr_username());
        userDao.setUsr_email(record.getPer_email());
        userDao.setUsr_person_name(record.getPer_name());
        userDao.setUsr_person_surname(record.getPer_surname());
        userDao.setUsr_phone_number(record.getPer_phone_number());
        userDao.setUsr_org_id(orgDao.getOrg_id());
        userDao.setUsr_email_confirmed(DbConstants.BOOLEAN_FALSE);
        userDao.setUsr_type("ORGANIZATION");
        if (record.getUsr_id() == null) {
            String userName = record.getUsr_username() != null ? record.getUsr_username() : record.getPer_email();
            List<SprUsersDAO> usrNameCheck = usersDbService.loadRecordsByParams(conn, "usr_username like ? order by usr_id desc",
                    new SelectParamValue(userName + "%"));
            if (usrNameCheck != null && !usrNameCheck.isEmpty()) {
                String lastUserName = usrNameCheck.get(0).getUsr_username();
                userName = lastUserName + "1";
            }
            userDao.setUsr_username(userName);
            userDao.setUsr_date_from(Utils.getDate(new Date()));
        }
        usersDbService.saveRecord(conn, userDao);
        if (record.getSendInvitation().equalsIgnoreCase(DbConstants.BOOLEAN_TRUE)) {
            if (userDao.getUsr_email() != null) {
                // handle context
                Map<String, Object> context = new HashMap<String, Object>();
                context.put("userName", userDao.getUsr_username());
                sprProcessRequest.createNewUserInformRequest(conn, userDao.getUsr_id(), userDao.getUsr_email(), Languages.getLanguageByCode(lang), context);
            } else {
                throw new SparkBusinessException(new S2Message("pages.sprUsersBrowse.emaiIsEmpty", SparkMessageType.ERROR, "User does not have an email"));
            }
        }

        record.setUsr_id(userDao.getUsr_id());

        SprOrgUsersDAO orgUsrDao = null;
        if (record.getOu_id() != null) {
            orgUsrDao = orgUsersDbService.loadRecord(conn, record.getOu_id());
        } else {
            orgUsrDao = orgUsersDbService.newRecord();
        }
        orgUsrDao.setOu_usr_id(userDao.getUsr_id());
        orgUsrDao.setOu_org_id(orgDao.getOrg_id());
        if (record.getOu_id() == null) {
            orgUsrDao.setOu_date_from(Utils.getDate(new Date()));
        }
        orgUsersDbService.saveRecord(conn, orgUsrDao);
        record.setOu_id(orgUsrDao.getOu_id());

        SprRolesDAO instAdminRol = rolesDbService.loadRecordByParams(conn, "rol_code = ? ", new SelectParamValue(NtisRolesConstants.INST_ADMIN));

        if (instAdminRol != null && instAdminRol.getRol_id() != null) {
            SprOrgUserRolesDAO userRole = sprOrgUserRolesDbService.loadRecordByParams(conn,
                    "our_rol_id = ?::int and our_ou_id=?::int and current_date between our_date_from and coalesce(our_date_to, now())",
                    new SelectParamValue(instAdminRol.getRol_id()), new SelectParamValue(record.getOu_id()));
            if (userRole == null || userRole.getOur_id() == null) {
                userRole = sprOrgUserRolesDbService.newRecord();
                userRole.setOur_rol_id(instAdminRol.getRol_id());
                userRole.setOur_ou_id(record.getOu_id());
                userRole.setOur_date_from(Utils.getDate(new Date()));
                sprOrgUserRolesDbService.saveRecord(conn, userRole);

                SprRolesDAO inspectorRole = rolesDbService.loadRecordByParams(conn, "rol_code = ? ", new SelectParamValue(NtisRolesConstants.INSPECTOR));
                if (inspectorRole != null && inspectorRole.getRol_id() != null) {
                    SprOrgUserRolesDAO userInspector = sprOrgUserRolesDbService.loadRecordByParams(conn,
                            "our_rol_id = ?::int and our_ou_id=?::int and current_date between our_date_from and coalesce(our_date_to, now())",
                            new SelectParamValue(inspectorRole.getRol_id()), new SelectParamValue(record.getOu_id()));
                    if (userInspector == null || userInspector.getOur_id() == null) {
                        userInspector = sprOrgUserRolesDbService.newRecord();
                        userInspector.setOur_rol_id(inspectorRole.getRol_id());
                        userInspector.setOur_ou_id(record.getOu_id());
                        userInspector.setOur_date_from(Utils.getDate(new Date()));
                        sprOrgUserRolesDbService.saveRecord(conn, userInspector);
                    }
                }
            }
        }

        if (orgDao.getC01().equalsIgnoreCase(NtisOrgType.INST.getCode())) {
            SprRolesDAO municiSpecialist = rolesDbService.loadRecordByParams(conn, "rol_code = ? ", new SelectParamValue(NtisRolesConstants.MUNICI_SPECIALIST));
            if (municiSpecialist != null && municiSpecialist.getRol_id() != null) {
                SprOrgUserRolesDAO userRole = sprOrgUserRolesDbService.loadRecordByParams(conn,
                        "our_rol_id = ?::int and our_ou_id=?::int and current_date between our_date_from and coalesce(our_date_to, now())",
                        new SelectParamValue(municiSpecialist.getRol_id()), new SelectParamValue(record.getOu_id()));
                if (userRole == null || userRole.getOur_id() == null) {
                    userRole = sprOrgUserRolesDbService.newRecord();
                    userRole.setOur_rol_id(municiSpecialist.getRol_id());
                    userRole.setOur_ou_id(record.getOu_id());
                    userRole.setOur_date_from(Utils.getDate(new Date()));
                    sprOrgUserRolesDbService.saveRecord(conn, userRole);
                }
            }
        }
        orgDao.setN05(userDao.getUsr_id());
        organizationsDbService.saveRecord(conn, orgDao);

        return record;
    }

    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisInstitutionEditModel record) throws SparkBusinessException, Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_UPDATE);
        SprUsersDAO userDAO = new SprUsersDAO();
        userDAO.setUsr_username(record.getUsr_username());
        if (record.getUsr_id() != null) {
            userDAO.setUsr_id(record.getUsr_id());
        }
        return usersDbService.getViolatedConstraints(conn, userDAO, "SPR_USR_UK");
    }
}
