package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprUsersDAO;
import eu.itreegroup.spark.dao.common.statementparams.StatementParam;
import eu.itreegroup.spark.dao.common.statementparams.UpdateStatementPart;
import eu.itreegroup.spark.dao.common.SprBaseDBServiceImpl;
import eu.itreegroup.s2.client.util.S2ViolatedConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.dao.common.SprBaseDAO;
import eu.itreegroup.spark.dao.common.Utils;
import java.sql.Timestamp;
import java.util.Arrays;

public class SprUsersDBServiceGen extends SprBaseDBServiceImpl<SprUsersDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprUsersDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT USR_ID, USR_USERNAME, USR_EMAIL, USR_EMAIL_CONFIRMED, USR_PERSON_NAME, USR_PERSON_SURNAME, USR_WRONG_LOGIN_ATTEMPTS, USR_LOCK_DATE, USR_SALT, USR_PASSWORD_ALGORITHM, USR_PASSWORD_HASH, USR_PASSWORD_RESET_REQ_DATE, USR_PASSWORD_CHANGE_DATE, USR_PASSWORD_NEXT_CHANGE_DATE, USR_PASSWORD_HISTORY, USR_PHONE_NUMBER, USR_LANGUAGE, USR_TYPE, USR_DATE_FROM, USR_DATE_TO, USR_SUBSCRIPTED_MONTH, USR_TERMS_ACCEPTED, USR_TERMS_ACCEPTED_DATE, USR_2FA_KEY, USR_2FA_KEY_CONFIRM, USR_2FA_USED, USR_CONFIRMED_RELEASE_VERSION, USR_PROFILE_TOKEN, USR_PER_ID, USR_ORG_ID, USR_ROL_ID, USR_PHOTO_FIL_ID, USR_AVATAR_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_USERS ";

   @Override
   public SprUsersDAO newRecord() {
	  	  SprUsersDAO daoObject = new SprUsersDAO();
	  	  return daoObject;
	  }
   @Override
   public SprUsersDAO object4ForceUpdate() {
       SprUsersDAO daoObject = new SprUsersDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprUsersDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprUsersDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprUsersDAO> data = new ArrayList<SprUsersDAO>();
      while (rs.next()) {
         data.add(new SprUsersDAO(Utils.getDouble(rs.getObject("USR_ID")), //
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
   public SprUsersDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE USR_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprUsersDAO> data = null;
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1,(Double)identifier);
         try(ResultSet rs = pstmt.executeQuery()){
               data = setDBDataToObject(rs);
         }catch(Exception ex1){
            throw ex1;
         }
      }catch(Exception ex){
         throw ex;
      }
      if (data == null || data.isEmpty()) {
         throw new Exception("Object with identifier "+ identifier + " not found");
      }
      SprUsersDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprUsersDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_USERS WHERE USR_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprUsersDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getUsr_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprUsersDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_USERS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprUsersDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "SPR_USR_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getUsr_username() != null) {
                String[] constraintColumns = { "usr_username" };
                String stmt = "select 1 from SPR_USERS where " //
                        + constructStatementPart("usr_username", daoObject.getUsr_username()) + " and " //
                        + (daoObject.getUsr_id() != null ? "USR_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getUsr_username());
                    if (daoObject.getUsr_id() != null) {
                        pstmt.setObject(++idx, daoObject.getUsr_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("SPR_USR_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprUsersDAO insertRecord(Connection conn, SprUsersDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_USERS (USR_USERNAME, USR_EMAIL, USR_EMAIL_CONFIRMED, USR_PERSON_NAME, USR_PERSON_SURNAME, USR_WRONG_LOGIN_ATTEMPTS, USR_LOCK_DATE, USR_SALT, USR_PASSWORD_ALGORITHM, USR_PASSWORD_HASH, USR_PASSWORD_RESET_REQ_DATE, USR_PASSWORD_CHANGE_DATE, USR_PASSWORD_NEXT_CHANGE_DATE, USR_PASSWORD_HISTORY, USR_PHONE_NUMBER, USR_LANGUAGE, USR_TYPE, USR_DATE_FROM, USR_DATE_TO, USR_SUBSCRIPTED_MONTH, USR_TERMS_ACCEPTED, USR_TERMS_ACCEPTED_DATE, USR_2FA_KEY, USR_2FA_KEY_CONFIRM, USR_2FA_USED, USR_CONFIRMED_RELEASE_VERSION, USR_PROFILE_TOKEN, USR_PER_ID, USR_ORG_ID, USR_ROL_ID, USR_PHOTO_FIL_ID, USR_AVATAR_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING USR_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getUsr_username());
         pstmt.setString(2, daoObject.getUsr_email());
         pstmt.setString(3, daoObject.getUsr_email_confirmed());
         pstmt.setString(4, daoObject.getUsr_person_name());
         pstmt.setString(5, daoObject.getUsr_person_surname());
         pstmt.setObject(6, daoObject.getUsr_wrong_login_attempts());
         pstmt.setObject(7,  Utils.getSqlTimestamp(daoObject.getUsr_lock_date()));
         pstmt.setString(8, daoObject.getUsr_salt());
         pstmt.setString(9, daoObject.getUsr_password_algorithm());
         pstmt.setString(10, daoObject.getUsr_password_hash());
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getUsr_password_reset_req_date()));
         pstmt.setObject(12,  Utils.getSqlTimestamp(daoObject.getUsr_password_change_date()));
         pstmt.setObject(13,  Utils.getSqlTimestamp(daoObject.getUsr_password_next_change_date()));
         pstmt.setString(14, daoObject.getUsr_password_history());
         pstmt.setString(15, daoObject.getUsr_phone_number());
         pstmt.setString(16, daoObject.getUsr_language());
         pstmt.setString(17, daoObject.getUsr_type());
         pstmt.setObject(18,  Utils.getSqlTimestamp(daoObject.getUsr_date_from()));
         pstmt.setObject(19,  Utils.getSqlTimestamp(daoObject.getUsr_date_to()));
         pstmt.setObject(20, daoObject.getUsr_subscripted_month());
         pstmt.setString(21, daoObject.getUsr_terms_accepted());
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getUsr_terms_accepted_date()));
         pstmt.setString(23, daoObject.getUsr_2fa_key());
         pstmt.setString(24, daoObject.getUsr_2fa_key_confirm());
         pstmt.setString(25, daoObject.getUsr_2fa_used());
         pstmt.setString(26, daoObject.getUsr_confirmed_release_version());
         pstmt.setString(27, daoObject.getUsr_profile_token());
         pstmt.setObject(28, daoObject.getUsr_per_id());
         pstmt.setObject(29, daoObject.getUsr_org_id());
         pstmt.setObject(30, daoObject.getUsr_rol_id());
         pstmt.setObject(31, daoObject.getUsr_photo_fil_id());
         pstmt.setObject(32, daoObject.getUsr_avatar_fil_id());
         pstmt.setObject(33, daoObject.getN01());
         pstmt.setObject(34, daoObject.getN02());
         pstmt.setObject(35, daoObject.getN03());
         pstmt.setObject(36, daoObject.getN04());
         pstmt.setObject(37, daoObject.getN05());
         pstmt.setString(38, daoObject.getC01());
         pstmt.setString(39, daoObject.getC02());
         pstmt.setString(40, daoObject.getC03());
         pstmt.setString(41, daoObject.getC04());
         pstmt.setString(42, daoObject.getC05());
         pstmt.setObject(43,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(44,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(45,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(46,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(47,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(48, 1);
         pstmt.setObject(49,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(50,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(51, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setUsr_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprUsersDAO updateRecord(Connection conn, SprUsersDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprUsersDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
         loadedObj.copyValues(daoObject);
         daoObject = loadedObj;
      }
      if (daoObject.isRecordChanged()){
         daoObject.validateObject(Utils.OPERATION_UPDATE, this);
         this.validateConstraints(conn, daoObject, null);
         UpdateStatementPart updatePart = daoObject.constructUpdatePart(getUserName());
         String stmt = updatePart.getStatementPart();
         log.debug("update stmt: "+stmt);
         try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
            List<StatementParam> statementParams = updatePart.getStatementParams();
            for (int i=0;i<statementParams.size();i++) {
        	    statementParams.get(i).setParameter(pstmt, i+1);
            }
            pstmt.execute();
         }catch(Exception ex){
            throw ex;
         }
      }
      return daoObject;
   }

   @Override
   public SprUsersDAO saveRecord(Connection conn, SprUsersDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}