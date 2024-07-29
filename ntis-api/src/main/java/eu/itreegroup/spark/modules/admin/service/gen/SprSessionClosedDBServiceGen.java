package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprSessionClosedDAO;
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

public class SprSessionClosedDBServiceGen extends SprBaseDBServiceImpl<SprSessionClosedDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprSessionClosedDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT SEC_ID, SEC_KEY, SEC_USERNAME, SEC_LOGIN_TIME, SEC_LOGOUT_TIME, SEC_LOGOUT_CAUSE, SEC_LAST_ACTION_TIME, SEC_LOGIN_METHOD, SEC_IP, SEC_OS, SEC_BROWSER, SEC_SCREEN_WIDTH, SEC_SCREEN_HEIGHT, SEC_PERSON_NAME, SEC_PERSON_SURNAME, SEC_ROLE, SEC_LANGUAGE, SEC_TERMS_ACCEPTED, SEC_TERMS_ACCEPTED_DATE, SEC_SUBSCRIPTED_MONTH, SEC_DATE_FROM, SEC_DATE_TO, SEC_2FA_CONFIRMED, SEC_2FA_CODE, SEC_LOCKED_FORM_CODE, SEC_CONFIRMED_RELEASE_VERSION, SEC_ROL_TYPE, SEC_USR_TYPE, SEC_PROFILE_TOKEN, SEC_ROL_ID, SEC_PER_ID, SEC_USR_ID, SEC_ORG_ID, SEC_PASSWORD_RESET_REQ_DATE, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_USER_SESSIONS_CLOSED ";

   @Override
   public SprSessionClosedDAO newRecord() {
	  	  SprSessionClosedDAO daoObject = new SprSessionClosedDAO();
	  	  return daoObject;
	  }
   @Override
   public SprSessionClosedDAO object4ForceUpdate() {
       SprSessionClosedDAO daoObject = new SprSessionClosedDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprSessionClosedDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprSessionClosedDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprSessionClosedDAO> data = new ArrayList<SprSessionClosedDAO>();
      while (rs.next()) {
         data.add(new SprSessionClosedDAO(Utils.getDouble(rs.getObject("SEC_ID")), //
                               rs.getString("SEC_KEY"), //
                               rs.getString("SEC_USERNAME"), //
                               rs.getTimestamp("SEC_LOGIN_TIME"), //
                               rs.getTimestamp("SEC_LOGOUT_TIME"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("SEC_LOGOUT_CAUSE")), //
                               rs.getTimestamp("SEC_LAST_ACTION_TIME"), //
                               rs.getString("SEC_LOGIN_METHOD"), //
                               rs.getString("SEC_IP"), //
                               rs.getString("SEC_OS"), //
                               rs.getString("SEC_BROWSER"), //
                               Utils.getDouble(rs.getObject("SEC_SCREEN_WIDTH")), //
                               Utils.getDouble(rs.getObject("SEC_SCREEN_HEIGHT")), //
                               rs.getString("SEC_PERSON_NAME"), //
                               rs.getString("SEC_PERSON_SURNAME"), //
                               rs.getString("SEC_ROLE"), //
                               rs.getString("SEC_LANGUAGE"), //
                               rs.getString("SEC_TERMS_ACCEPTED"), //
                               rs.getTimestamp("SEC_TERMS_ACCEPTED_DATE"), //
                               Utils.getDouble(rs.getObject("SEC_SUBSCRIPTED_MONTH")), //
                               rs.getTimestamp("SEC_DATE_FROM"), //
                               rs.getTimestamp("SEC_DATE_TO"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("SEC_2FA_CONFIRMED")), //
                               rs.getString("SEC_2FA_CODE"), //
                               rs.getString("SEC_LOCKED_FORM_CODE"), //
                               rs.getString("SEC_CONFIRMED_RELEASE_VERSION"), //
                               rs.getString("SEC_ROL_TYPE"), //
                               rs.getString("SEC_USR_TYPE"), //
                               rs.getString("SEC_PROFILE_TOKEN"), //
                               Utils.getDouble(rs.getObject("SEC_ROL_ID")), //
                               Utils.getDouble(rs.getObject("SEC_PER_ID")), //
                               Utils.getDouble(rs.getObject("SEC_USR_ID")), //
                               Utils.getDouble(rs.getObject("SEC_ORG_ID")), //
                               rs.getTimestamp("SEC_PASSWORD_RESET_REQ_DATE"), //
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
   public SprSessionClosedDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE SEC_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprSessionClosedDAO> data = null;
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
      SprSessionClosedDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprSessionClosedDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_USER_SESSIONS_CLOSED WHERE SEC_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprSessionClosedDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getSec_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprSessionClosedDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_USER_SESSIONS_CLOSED",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprSessionClosedDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprSessionClosedDAO insertRecord(Connection conn, SprSessionClosedDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_USER_SESSIONS_CLOSED (SEC_KEY, SEC_USERNAME, SEC_LOGIN_TIME, SEC_LOGOUT_TIME, SEC_LOGOUT_CAUSE, SEC_LAST_ACTION_TIME, SEC_LOGIN_METHOD, SEC_IP, SEC_OS, SEC_BROWSER, SEC_SCREEN_WIDTH, SEC_SCREEN_HEIGHT, SEC_PERSON_NAME, SEC_PERSON_SURNAME, SEC_ROLE, SEC_LANGUAGE, SEC_TERMS_ACCEPTED, SEC_TERMS_ACCEPTED_DATE, SEC_SUBSCRIPTED_MONTH, SEC_DATE_FROM, SEC_DATE_TO, SEC_2FA_CONFIRMED, SEC_2FA_CODE, SEC_LOCKED_FORM_CODE, SEC_CONFIRMED_RELEASE_VERSION, SEC_ROL_TYPE, SEC_USR_TYPE, SEC_PROFILE_TOKEN, SEC_ROL_ID, SEC_PER_ID, SEC_USR_ID, SEC_ORG_ID, SEC_PASSWORD_RESET_REQ_DATE, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING SEC_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getSec_key());
         pstmt.setString(2, daoObject.getSec_username());
         pstmt.setObject(3,  Utils.getSqlTimestamp(daoObject.getSec_login_time()));
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getSec_logout_time()));
         pstmt.setString(5, daoObject.getSec_logout_cause());
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getSec_last_action_time()));
         pstmt.setString(7, daoObject.getSec_login_method());
         pstmt.setString(8, daoObject.getSec_ip());
         pstmt.setString(9, daoObject.getSec_os());
         pstmt.setString(10, daoObject.getSec_browser());
         pstmt.setObject(11, daoObject.getSec_screen_width());
         pstmt.setObject(12, daoObject.getSec_screen_height());
         pstmt.setString(13, daoObject.getSec_person_name());
         pstmt.setString(14, daoObject.getSec_person_surname());
         pstmt.setString(15, daoObject.getSec_role());
         pstmt.setString(16, daoObject.getSec_language());
         pstmt.setString(17, daoObject.getSec_terms_accepted());
         pstmt.setObject(18,  Utils.getSqlDate(daoObject.getSec_terms_accepted_date()));
         pstmt.setObject(19, daoObject.getSec_subscripted_month());
         pstmt.setObject(20,  Utils.getSqlDate(daoObject.getSec_date_from()));
         pstmt.setObject(21,  Utils.getSqlDate(daoObject.getSec_date_to()));
         pstmt.setString(22, daoObject.getSec_2fa_confirmed());
         pstmt.setString(23, daoObject.getSec_2fa_code());
         pstmt.setString(24, daoObject.getSec_locked_form_code());
         pstmt.setString(25, daoObject.getSec_confirmed_release_version());
         pstmt.setString(26, daoObject.getSec_rol_type());
         pstmt.setString(27, daoObject.getSec_usr_type());
         pstmt.setString(28, daoObject.getSec_profile_token());
         pstmt.setObject(29, daoObject.getSec_rol_id());
         pstmt.setObject(30, daoObject.getSec_per_id());
         pstmt.setObject(31, daoObject.getSec_usr_id());
         pstmt.setObject(32, daoObject.getSec_org_id());
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getSec_password_reset_req_date()));
         pstmt.setObject(34, daoObject.getN01());
         pstmt.setObject(35, daoObject.getN02());
         pstmt.setObject(36, daoObject.getN03());
         pstmt.setObject(37, daoObject.getN04());
         pstmt.setObject(38, daoObject.getN05());
         pstmt.setString(39, daoObject.getC01());
         pstmt.setString(40, daoObject.getC02());
         pstmt.setString(41, daoObject.getC03());
         pstmt.setString(42, daoObject.getC04());
         pstmt.setString(43, daoObject.getC05());
         pstmt.setObject(44,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(45,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(46,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(47,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(48,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(49, 1);
         pstmt.setObject(50,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(51,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(52, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setSec_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprSessionClosedDAO updateRecord(Connection conn, SprSessionClosedDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprSessionClosedDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprSessionClosedDAO saveRecord(Connection conn, SprSessionClosedDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}