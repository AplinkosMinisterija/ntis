package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprSessionOpenDAO;
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

public class SprSessionOpenDBServiceGen extends SprBaseDBServiceImpl<SprSessionOpenDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprSessionOpenDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT SES_ID, SES_KEY, SES_USERNAME, SES_LOGIN_TIME, SES_LOGOUT_TIME, SES_LOGOUT_CAUSE, SES_LAST_ACTION_TIME, SES_LOGIN_METHOD, SES_IP, SES_OS, SES_BROWSER, SES_SCREEN_WIDTH, SES_SCREEN_HEIGHT, SES_PERSON_NAME, SES_PERSON_SURNAME, SES_ROLE, SES_LANGUAGE, SES_TERMS_ACCEPTED, SES_TERMS_ACCEPTED_DATE, SES_SUBSCRIPTED_MONTH, SES_DATE_FROM, SES_DATE_TO, SES_2FA_CONFIRMED, SES_2FA_CODE, SES_LOCKED_FORM_CODE, SES_CONFIRMED_RELEASE_VERSION, SES_ROL_TYPE, SES_USR_TYPE, SES_PROFILE_TOKEN, SES_ROL_ID, SES_PER_ID, SES_USR_ID, SES_ORG_ID, SES_PASSWORD_RESET_REQ_DATE, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_USER_SESSIONS_OPEN ";

   @Override
   public SprSessionOpenDAO newRecord() {
	  	  SprSessionOpenDAO daoObject = new SprSessionOpenDAO();
	  	  return daoObject;
	  }
   @Override
   public SprSessionOpenDAO object4ForceUpdate() {
       SprSessionOpenDAO daoObject = new SprSessionOpenDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprSessionOpenDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprSessionOpenDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprSessionOpenDAO> data = new ArrayList<SprSessionOpenDAO>();
      while (rs.next()) {
         data.add(new SprSessionOpenDAO(Utils.getDouble(rs.getObject("SES_ID")), //
                               rs.getString("SES_KEY"), //
                               rs.getString("SES_USERNAME"), //
                               rs.getTimestamp("SES_LOGIN_TIME"), //
                               rs.getTimestamp("SES_LOGOUT_TIME"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("SES_LOGOUT_CAUSE")), //
                               rs.getTimestamp("SES_LAST_ACTION_TIME"), //
                               rs.getString("SES_LOGIN_METHOD"), //
                               rs.getString("SES_IP"), //
                               rs.getString("SES_OS"), //
                               rs.getString("SES_BROWSER"), //
                               Utils.getDouble(rs.getObject("SES_SCREEN_WIDTH")), //
                               Utils.getDouble(rs.getObject("SES_SCREEN_HEIGHT")), //
                               rs.getString("SES_PERSON_NAME"), //
                               rs.getString("SES_PERSON_SURNAME"), //
                               rs.getString("SES_ROLE"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("SES_LANGUAGE")), //
                               Utils.removeWhitespacesFromEnd(rs.getString("SES_TERMS_ACCEPTED")), //
                               rs.getTimestamp("SES_TERMS_ACCEPTED_DATE"), //
                               Utils.getDouble(rs.getObject("SES_SUBSCRIPTED_MONTH")), //
                               rs.getTimestamp("SES_DATE_FROM"), //
                               rs.getTimestamp("SES_DATE_TO"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("SES_2FA_CONFIRMED")), //
                               rs.getString("SES_2FA_CODE"), //
                               rs.getString("SES_LOCKED_FORM_CODE"), //
                               rs.getString("SES_CONFIRMED_RELEASE_VERSION"), //
                               rs.getString("SES_ROL_TYPE"), //
                               rs.getString("SES_USR_TYPE"), //
                               rs.getString("SES_PROFILE_TOKEN"), //
                               Utils.getDouble(rs.getObject("SES_ROL_ID")), //
                               Utils.getDouble(rs.getObject("SES_PER_ID")), //
                               Utils.getDouble(rs.getObject("SES_USR_ID")), //
                               Utils.getDouble(rs.getObject("SES_ORG_ID")), //
                               rs.getTimestamp("SES_PASSWORD_RESET_REQ_DATE"), //
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
   public SprSessionOpenDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE SES_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprSessionOpenDAO> data = null;
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
      SprSessionOpenDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprSessionOpenDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_USER_SESSIONS_OPEN WHERE SES_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprSessionOpenDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getSes_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprSessionOpenDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_USER_SESSIONS_OPEN",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprSessionOpenDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "SPR_USER_SESSIONS_OPEN_SES_KEY_KEY".equalsIgnoreCase(constraintName)) {
            if (daoObject.getSes_key() != null) {
                String[] constraintColumns = { "ses_key" };
                String stmt = "select 1 from SPR_USER_SESSIONS_OPEN where " //
                        + constructStatementPart("ses_key", daoObject.getSes_key()) + " and " //
                        + (daoObject.getSes_id() != null ? "SES_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getSes_key());
                    if (daoObject.getSes_id() != null) {
                        pstmt.setObject(++idx, daoObject.getSes_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("SPR_USER_SESSIONS_OPEN_SES_KEY_KEY", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprSessionOpenDAO insertRecord(Connection conn, SprSessionOpenDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_USER_SESSIONS_OPEN (SES_KEY, SES_USERNAME, SES_LOGIN_TIME, SES_LOGOUT_TIME, SES_LOGOUT_CAUSE, SES_LAST_ACTION_TIME, SES_LOGIN_METHOD, SES_IP, SES_OS, SES_BROWSER, SES_SCREEN_WIDTH, SES_SCREEN_HEIGHT, SES_PERSON_NAME, SES_PERSON_SURNAME, SES_ROLE, SES_LANGUAGE, SES_TERMS_ACCEPTED, SES_TERMS_ACCEPTED_DATE, SES_SUBSCRIPTED_MONTH, SES_DATE_FROM, SES_DATE_TO, SES_2FA_CONFIRMED, SES_2FA_CODE, SES_LOCKED_FORM_CODE, SES_CONFIRMED_RELEASE_VERSION, SES_ROL_TYPE, SES_USR_TYPE, SES_PROFILE_TOKEN, SES_ROL_ID, SES_PER_ID, SES_USR_ID, SES_ORG_ID, SES_PASSWORD_RESET_REQ_DATE, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING SES_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getSes_key());
         pstmt.setString(2, daoObject.getSes_username());
         pstmt.setObject(3,  Utils.getSqlTimestamp(daoObject.getSes_login_time()));
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getSes_logout_time()));
         pstmt.setString(5, daoObject.getSes_logout_cause());
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getSes_last_action_time()));
         pstmt.setString(7, daoObject.getSes_login_method());
         pstmt.setString(8, daoObject.getSes_ip());
         pstmt.setString(9, daoObject.getSes_os());
         pstmt.setString(10, daoObject.getSes_browser());
         pstmt.setObject(11, daoObject.getSes_screen_width());
         pstmt.setObject(12, daoObject.getSes_screen_height());
         pstmt.setString(13, daoObject.getSes_person_name());
         pstmt.setString(14, daoObject.getSes_person_surname());
         pstmt.setString(15, daoObject.getSes_role());
         pstmt.setString(16, daoObject.getSes_language());
         pstmt.setString(17, daoObject.getSes_terms_accepted());
         pstmt.setObject(18,  Utils.getSqlDate(daoObject.getSes_terms_accepted_date()));
         pstmt.setObject(19, daoObject.getSes_subscripted_month());
         pstmt.setObject(20,  Utils.getSqlDate(daoObject.getSes_date_from()));
         pstmt.setObject(21,  Utils.getSqlDate(daoObject.getSes_date_to()));
         pstmt.setString(22, daoObject.getSes_2fa_confirmed());
         pstmt.setString(23, daoObject.getSes_2fa_code());
         pstmt.setString(24, daoObject.getSes_locked_form_code());
         pstmt.setString(25, daoObject.getSes_confirmed_release_version());
         pstmt.setString(26, daoObject.getSes_rol_type());
         pstmt.setString(27, daoObject.getSes_usr_type());
         pstmt.setString(28, daoObject.getSes_profile_token());
         pstmt.setObject(29, daoObject.getSes_rol_id());
         pstmt.setObject(30, daoObject.getSes_per_id());
         pstmt.setObject(31, daoObject.getSes_usr_id());
         pstmt.setObject(32, daoObject.getSes_org_id());
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getSes_password_reset_req_date()));
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
            daoObject.setSes_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprSessionOpenDAO updateRecord(Connection conn, SprSessionOpenDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprSessionOpenDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprSessionOpenDAO saveRecord(Connection conn, SprSessionOpenDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}