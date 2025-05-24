package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUserRolesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersNtisDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprUsersDBServiceGen;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

@Service
public class SprUsersDBServiceImpl extends SprUsersDBServiceGen implements SprUsersDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprUsersDBServiceImpl.class);

    private static final String PRIVATE_ORG = "PRIVATE_ORG";

    private static final String PRIVATE = "PRIVATE";

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    DBPropertyManager dBPropertyManager;

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    SprRolesDBService sprRolesDBService;

    @Autowired
    SprUserRolesDBService sprUserRolesDBService;

    @Autowired
    SprPersonsDBService sprPersonsDBService;

    public SprUsersDBServiceImpl() {
    }

    @Override
    public SprUsersDAO newRecord() {
        SprUsersDAO daoObject = new SprUsersNtisDAO();
        return daoObject;
    }

    @Override
    public SprUsersDAO saveRecord(Connection conn, SprUsersDAO daoObject) throws Exception {
        daoObject = super.saveRecord(conn, daoObject);
        if (daoObject.isPerformSyncWithPerson()) {
            savePersonInfo(conn, daoObject);
        }
        return daoObject;
    }

    /**
    * Function will save user object name and surname to corresponding person object
    * @param conn - connection to the DB
    * @param record - user object
    * @throws Exception
    */
    private void savePersonInfo(Connection conn, SprUsersDAO userRecord) throws Exception {
        SprPersonsDAO personDAO = sprPersonsDBService.loadRecord(conn, userRecord.getUsr_per_id());
        personDAO.setPerformSyncWithUser(false);
        personDAO.setPer_name(userRecord.getUsr_person_name());
        personDAO.setPer_surname(userRecord.getUsr_person_surname());
        personDAO.setPer_phone_number(userRecord.getUsr_phone_number());
        personDAO.setPer_email(userRecord.getUsr_email());
        personDAO.setPer_email_confirmed(userRecord.getUsr_email_confirmed());
        sprPersonsDBService.saveRecord(conn, personDAO);
    }

    /**
     * Function will add default role to the user. Default role should be defined as system parameter by name DEFAULT_NEW_USER_ROLE_CODE.
     * @param conn - connection to the db
     * @param userId - reference to the user for which should be added default role.
     */
    private void addDefaultRole(Connection conn, Double userId) {
        String defaultRoleCode = dBPropertyManager.getPropertyByName("DEFAULT_NEW_USER_ROLE_CODE", null);
        log.debug("createUser: DEFAULT_NEW_USER_ROLE_CODE: " + defaultRoleCode);

        if (defaultRoleCode != null) {
            try {
                SprRolesDAO rolesDAO = sprRolesDBService.loadRecordByRoleCode(conn, defaultRoleCode);
                SprUserRolesDAO userRolesDAO = sprUserRolesDBService.newRecord();
                userRolesDAO.setUro_usr_id(userId);
                userRolesDAO.setUro_rol_id(rolesDAO.getRol_id());
                userRolesDAO.setUro_date_from(Utils.getDate());
                sprUserRolesDBService.saveRecord(conn, userRolesDAO);
            } catch (Exception ex) {
                log.error("Role by name '" + defaultRoleCode + "' not found ", ex);
            }
        }
    }

    /**
     * Function will manage person information based on the passed user record.
     * First, the function will check if the user record has person code.
     * If both person code and per id is not provided, 
     *      the function will create a new person record and add a reference to the newly created person to user record.
     * If there's no person code provided but user record has per id (reference to person record), function will update the referenced
     *      person record.
     * If person code is provided but user record does not have a reference to person record, function will check if a person record with such 
     *      person code exists, if such record exists, reference to this record will be added to the user record, 
     *      otherwise, a new person record will be created
     * If person code is provided and user record has a reference to already existing person record, function will check if the person code of user record
     *      matches with the person code of the existing referenced person record. If it does not match, it will check if another person record
     *      with such person code exists and if so, it will add reference to the user record, if a record with such person code does not exists, it will
     *      create a new record and update user record reference. If person code of the user record matches with the referenced person record,
     *      function will update the person record.
     * @param conn - connection to the db
     * @param record - user record
     * @param userId - reference to the user for which should be added default role.
     */
    private void managePersonInfo(Connection conn, SprUsersNtisDAO userRecord) throws Exception {
        SprPersonsDAO personDAO;
        if (userRecord.getUsr_id() != null) {
            SprUsersNtisDAO ntisUserDAO = this.loadRecord(conn, userRecord.getUsr_id());
            if (userRecord.getPersonCode() == null || userRecord.getPersonCode().isEmpty()) {
                userRecord.setPersonCode(ntisUserDAO.getPersonCode());
            }
            if (ntisUserDAO.getUsr_email() == null || !ntisUserDAO.getUsr_email().equalsIgnoreCase(userRecord.getUsr_email())) {
                userRecord.setUsr_email_confirmed(YesNo.N.getCode());
            }
        } else {
            if (userRecord.getPersonCode() == null && userRecord.getUsr_per_id() != null) {
                return;
            }
        }

        // checking if person code is provided
        if (userRecord.getPersonCode() == null || "".equals(userRecord.getPersonCode().trim())) {
            // checking if the user record has reference to person,
            // if reference exists, person record is loaded, if not, new one created and linked to the user record
            if (userRecord.getUsr_per_id() != null) {
                personDAO = sprPersonsDBService.loadRecord(conn, userRecord.getUsr_per_id());
            } else {
                personDAO = sprPersonsDBService.newRecord();
                personDAO.setPer_lrt_resident(YesNo.Y.getCode());
                personDAO.setPer_code_exists(YesNo.N.getCode());
                personDAO
                        .setPer_date_of_birth(Utils.getDateFromString("1900-01-01", dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
            }
            personDAO.setPerformSyncWithUser(false);
            personDAO.setPer_name(userRecord.getUsr_person_name());
            personDAO.setPer_surname(userRecord.getUsr_person_surname());
            personDAO.setPer_phone_number(userRecord.getUsr_phone_number());
            personDAO.setPer_email(userRecord.getUsr_email());
            personDAO.setPer_email_confirmed(userRecord.getUsr_email_confirmed());
            personDAO.setPer_code(userRecord.getPersonCode());
            sprPersonsDBService.saveRecord(conn, personDAO);
            userRecord.setUsr_per_id(personDAO.getPer_id());
        } else {
            // for when person code exists, checking if user record has reference to person
            if (userRecord.getUsr_per_id() != null) {
                personDAO = sprPersonsDBService.loadRecord(conn, userRecord.getUsr_per_id());
                // will check if the code matches with the person code of the person record
                // referenced by the user record, if the code doesn't match, will check if there's a person record with this person code
                // if person with this person code exists, data will be renewed and reference to person will be created for user record
                if (personDAO.getPer_code() != null && !personDAO.getPer_code().equalsIgnoreCase(userRecord.getPersonCode())) {
                    SprPersonsDAO checkForCodePerson = sprPersonsDBService.loadRecordByParams(conn, " per_code = ? ",
                            new SelectParamValue(userRecord.getPersonCode()));
                    if (checkForCodePerson != null) {
                        personDAO = checkForCodePerson;
                    }
                }

                personDAO.setPerformSyncWithUser(false);
                personDAO.setPer_name(userRecord.getUsr_person_name());
                personDAO.setPer_surname(userRecord.getUsr_person_surname());
                personDAO.setPer_phone_number(userRecord.getUsr_phone_number());
                personDAO.setPer_email(userRecord.getUsr_email());
                personDAO.setPer_email_confirmed(userRecord.getUsr_email_confirmed());
                personDAO.setPer_code_exists(YesNo.Y.getCode());
                personDAO.setPer_code(userRecord.getPersonCode());
                sprPersonsDBService.saveRecord(conn, personDAO);
            } else {
                // if user record has per code but doesn't have a reference to person record,
                // checking if a person record with such per code exists, if not,
                // new record with such person code is created and reference added to the user record
                personDAO = sprPersonsDBService.loadRecordByParams(conn, " per_code = ? ", new SelectParamValue(userRecord.getPersonCode()));
                if (personDAO == null) {
                    personDAO = sprPersonsDBService.newRecord();
                    personDAO.setPer_date_of_birth(
                            Utils.getDateFromString("1900-01-01", dbStatementManager.getDateFormat(DBStatementManager.DATE_FORMAT_DAY_JAVA)));
                    personDAO.setPer_code_exists(YesNo.Y.getCode());
                    personDAO.setPer_lrt_resident(YesNo.Y.getCode());
                    personDAO.setPer_code(userRecord.getPersonCode());
                }
                personDAO.setPerformSyncWithUser(false);
                personDAO.setPer_name(userRecord.getUsr_person_name());
                personDAO.setPer_surname(userRecord.getUsr_person_surname());
                personDAO.setPer_phone_number(userRecord.getUsr_phone_number());
                personDAO.setPer_email(userRecord.getUsr_email());
                personDAO.setPer_email_confirmed(userRecord.getUsr_email_confirmed());
                personDAO.setPer_code(userRecord.getPersonCode());
                sprPersonsDBService.saveRecord(conn, personDAO);
                userRecord.setUsr_per_id(personDAO.getPer_id());
            }
        }
        userRecord.setC01(null);
        userRecord.setPerformSyncWithPerson(false);

    }

    /**
     * Function will save changes to the user. In case if new user record will be created and in system setup is defined property by name DEFAULT_NEW_USER_ROLE_CODE
     * default role will be assigned.
     * @param conn - connection to the db
     * @param recored - user data, that should be saved to the db
     * @return - updated (saved) user record.
     */
    @Override
    public SprUsersDAO insertRecord(Connection conn, SprUsersDAO usrRecord) throws Exception {

        managePersonInfo(conn, (SprUsersNtisDAO) usrRecord);
        usrRecord = super.insertRecord(conn, usrRecord);
        addDefaultRole(conn, usrRecord.getUsr_id());
        return usrRecord;
    }

    @Override
    public SprUsersDAO updateRecord(Connection conn, SprUsersDAO usrRecord) throws Exception {
        managePersonInfo(conn, (SprUsersNtisDAO) usrRecord);
        return super.updateRecord(conn, usrRecord);
    }

    @Override
    public SprUsersDAO loadRecordByIdentifier(Connection conn, Object identifier, Map<String, Object> authExtData) throws Exception {
        SprUsersDAO usrRec = null;
        if (authExtData == null || authExtData.isEmpty()) {
            usrRec = this.loadRecordByParams(conn, " WHERE USR_USERNAME = ? ", new SelectParamValue((String) identifier));
        } else {
            String authType = (String) authExtData.get("AUTH_TYPE");
            switch (authType) {
                case "USER_PASSWORD_AUTH":
                    if (PRIVATE.equals(authExtData.get("USER_TYPE"))) {
                        usrRec = this.loadRecordByParams(conn, " WHERE USR_USERNAME = ? and USR_TYPE = ? ", new SelectParamValue((String) identifier),
                                new SelectParamValue((String) PRIVATE_ORG));
                        if (usrRec == null) {
                            usrRec = this.loadRecordByParams(conn, " WHERE USR_USERNAME = ? and USR_TYPE = ? ", new SelectParamValue((String) identifier),
                                    new SelectParamValue((String) PRIVATE));
                        }
                    } else {
                        usrRec = this.loadRecordByParams(conn, " WHERE USR_USERNAME = ? and USR_TYPE = ? ", new SelectParamValue((String) identifier),
                                new SelectParamValue((String) authExtData.get("USER_TYPE")));
                    }
                    break;
                case "VIISP_AUTH", "ISENSE_AUTH":
                    usrRec = this.loadRecordByParams(conn, " WHERE USR_PER_ID = ?::int and USR_TYPE = ? ", new SelectParamValue((Double) identifier),
                            new SelectParamValue((String) authExtData.get("USER_TYPE")));
                    break;
                case "GOOGLE_AUTH":
                    usrRec = this.loadRecordByParams(conn, " WHERE USR_EMAIL = ? and USR_TYPE = ? ", new SelectParamValue((String) identifier),
                            new SelectParamValue((String) authExtData.get("USER_TYPE")));
                    break;
                default:
                    usrRec = this.loadRecordByParams(conn, " WHERE USR_USERNAME = ? ", new SelectParamValue((String) identifier));
            }
        }
        if (usrRec != null && usrRec.getUsr_per_id() != null) {
            SprPersonsDAO perDAO = sprPersonsDBService.loadRecord(conn, usrRec.getUsr_per_id());
            ((SprUsersNtisDAO) usrRec).setPersonCode(perDAO.getPer_code());
        }
        return usrRec;

    }

    @Override
    public SprUsersDAO loadRecordByEmail(Connection conn, String email) throws Exception {
        return this.loadRecordByParams(conn, " WHERE USR_EMAIL = ? ", new SelectParamValue(email));
    }

    @Override
    public void checkUserNameUk(Connection conn, Double id, String code) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select 1 id from spr_users  where usr_username = ? ");
        stmt.addSelectParam(code);
        stmt.setWhereExists(true);
        stmt.addParam4WherePart(" usr_id <> ? ", id);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);

        if (!data.isEmpty()) {
            throw new SparkBusinessException(new S2Message("common.error.recExist", SparkMessageType.ERROR));
        }

    }

    @Override
    public void checkEmailUk(Connection conn, Double id, String code) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select 1 id from spr_users  where usr_email = ?  ");
        stmt.addSelectParam(code);
        stmt.setWhereExists(true);
        stmt.addParam4WherePart(" usr_id <> ? ", id);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);

        if (!data.isEmpty()) {
            throw new SparkBusinessException(new S2Message("common.error.recExist", SparkMessageType.ERROR));
        }

    }

    @Override
    public void deleteRecord(Connection conn, Double identifier) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("DELETE FROM SPR_USER_ROLES WHERE URO_USR_ID=?::int ");
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        stmt = new StatementAndParams();
        stmt.setStatement("delete from spr_org_user_roles where our_ou_id in (select ou.ou_id from spr_org_users ou where ou.ou_usr_id = ?::int) ");
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        stmt = new StatementAndParams();
        stmt.setStatement("delete from spr_org_users ou where ou.ou_usr_id = ?::int ");
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        stmt = new StatementAndParams("""
                update spr_users
                set usr_org_id = null,
                usr_date_to = current_date-1,
                usr_lock_date = current_date-1
                where usr_id = ?::int
                """);
        stmt.addSelectParam(identifier);
        baseControllerJDBC.adjustRecordsInDB(conn, stmt);
        conn.commit();
        try {
            super.deleteRecord(conn, identifier);
        } catch (Exception ex) {
            throw new SparkBusinessException(new S2Message("common.error.userCannotBeDeleted", SparkMessageType.WARNING));
        }
    }

    /**
     * Funkcija atstato sisteminius duomenis.
     */
    @Override
    public SprUsersDAO restoreSystemData(Connection conn, SprUsersDAO daoObject) throws Exception {
        SprUsersDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
        daoObject.restoreSystemData(loadedObj);
        return daoObject;

    }

    @Override
    protected List<SprUsersDAO> setDBDataToObject(ResultSet rs) throws Exception {
        List<SprUsersDAO> data = new ArrayList<>();
        while (rs.next()) {
            data.add(new SprUsersNtisDAO(Utils.getDouble(rs.getObject("USR_ID")), //
                    rs.getString("USR_USERNAME"), //
                    rs.getString("USR_EMAIL"), //
                    rs.getString("USR_EMAIL_CONFIRMED"), //
                    rs.getString("USR_PERSON_NAME"), //
                    rs.getString("USR_PERSON_SURNAME"), //
                    Utils.getDouble(rs.getObject("USR_WRONG_LOGIN_ATTEMPTS")), //
                    rs.getTimestamp("USR_LOCK_DATE"), //
                    rs.getString("USR_SALT"), //
                    rs.getString("USR_PASSWORD_ALGORITHM"), //
                    rs.getString("USR_PASSWORD_HASH"), //
                    rs.getTimestamp("USR_PASSWORD_RESET_REQ_DATE"), //
                    rs.getTimestamp("USR_PASSWORD_CHANGE_DATE"), //
                    rs.getTimestamp("USR_PASSWORD_NEXT_CHANGE_DATE"), //
                    rs.getString("USR_PASSWORD_HISTORY"), //
                    rs.getString("USR_PHONE_NUMBER"), //
                    rs.getString("USR_LANGUAGE"), //
                    rs.getString("USR_TYPE"), //
                    rs.getTimestamp("USR_DATE_FROM"), //
                    rs.getTimestamp("USR_DATE_TO"), //
                    Utils.getDouble(rs.getObject("USR_SUBSCRIPTED_MONTH")), //
                    rs.getString("USR_TERMS_ACCEPTED"), //
                    rs.getTimestamp("USR_TERMS_ACCEPTED_DATE"), //
                    rs.getString("USR_2FA_KEY"), //
                    rs.getString("USR_2FA_KEY_CONFIRM"), //
                    rs.getString("USR_2FA_USED"), //
                    rs.getString("USR_CONFIRMED_RELEASE_VERSION"), //
                    rs.getString("USR_PROFILE_TOKEN"), //
                    Utils.getDouble(rs.getObject("USR_PER_ID")), //
                    Utils.getDouble(rs.getObject("USR_ORG_ID")), //
                    Utils.getDouble(rs.getObject("USR_ROL_ID")), //
                    Utils.getDouble(rs.getObject("USR_PHOTO_FIL_ID")), //
                    Utils.getDouble(rs.getObject("USR_AVATAR_FIL_ID")), //
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

    @Override
    public SprUsersNtisDAO loadRecord(Connection conn, Object identifier) throws Exception {
        SprUsersNtisDAO dao = (SprUsersNtisDAO) super.loadRecord(conn, identifier);
        if (dao.getUsr_per_id() != null) {
            SprPersonsDAO perDAO = sprPersonsDBService.loadRecord(conn, dao.getUsr_per_id());
            dao.setPersonCode(perDAO.getPer_code());
            dao.setPersonCodeConfirmed(perDAO.getPer_data_confirmed());
        }
        return dao;
    }

}