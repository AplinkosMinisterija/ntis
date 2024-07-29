package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.enums.YesNo;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprPersonsDBServiceGen;

@Service
public class SprPersonsDBServiceImpl extends SprPersonsDBServiceGen implements SprPersonsDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprPersonsDBServiceImpl.class);

    @Autowired
    SprUsersDBService sprUsersDBService;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprPersonsDBServiceImpl() {
    }

    @Override
    public SprPersonsDAO newRecord() {
        SprPersonsDAO daoObject = super.newRecord();
        return daoObject;
    }

    @Override
    public SprPersonsDAO saveRecord(Connection conn, SprPersonsDAO daoObject) throws Exception {
        if (daoObject.getPer_code() != null) {
            daoObject.setPer_code(daoObject.getPer_code().trim());
        }
        if ((daoObject.getPer_id() != null
                && (daoObject.getPer_email() != null && !daoObject.getPer_email().equals(this.loadRecord(conn, daoObject.getPer_id()).getPer_email())))
                || daoObject.getPer_email_confirmed() == null) {
            daoObject.setPer_email_confirmed(YesNo.N.getCode());
        }
        daoObject = super.saveRecord(conn, daoObject);
        if (daoObject.isPerformSyncWithUser()) {
            saveUserInfo(conn, daoObject);
        }
        return daoObject;
    }

    /**
    * Function will save person object name and surname to corresponding user objects
    * @param conn - connection to the DB
    * @param record - person object
    * @throws Exception
    */
    private void saveUserInfo(Connection conn, SprPersonsDAO record) throws Exception {
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" select usr_id as id from spr_users where usr_per_id = ? ");
        stmt.addSelectParam(record.getPer_id());
        List<RecordIdentifier> usersIds = baseControllerJDBC.selectQueryAsObjectArrayList(conn, stmt, RecordIdentifier.class);
        for (RecordIdentifier userId : usersIds) {
            SprUsersDAO userDAO = sprUsersDBService.loadRecord(conn, userId.getIdAsDouble());
            userDAO.setPerformSyncWithPerson(false);
            userDAO.setUsr_person_name(record.getPer_name());
            userDAO.setUsr_person_surname(record.getPer_surname());
            userDAO.setUsr_email(record.getPer_email());
            userDAO.setUsr_email_confirmed(record.getPer_email_confirmed());
            userDAO.setUsr_phone_number(record.getPer_phone_number());
            sprUsersDBService.saveRecord(conn, userDAO);
        }
    }

}