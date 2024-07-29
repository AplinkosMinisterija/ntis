package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
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

public class SprJobDefinitionsDBServiceGen extends SprBaseDBServiceImpl<SprJobDefinitionsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprJobDefinitionsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT JDE_ID, JDE_SYSTEM, JDE_NAME, JDE_TYPE, JDE_CODE, JDE_STATUS, JDE_PATH, JDE_TML_ID, JDE_DEFAULT_EXECUTER, JDE_EXECUTION_PARAMETER, JDE_EXECUTION_UNIT, JDE_ACTION_TYPE, JDE_DEFAULT_OUTPUT_TYPE, JDE_LAST_ACTION_TIME, JDE_NEXT_ACTION_TIME, JDE_DESCRIPTION, JDE_PERIOD, JDE_WEEK_DAY, JDE_MONTH_DAY, JDE_YEAR_DAY, JDE_HOUR, JDE_MINUTES, JDE_PERIOD_IN_MINUTES, JDE_DATE_FROM, JDE_DATE_TO, JDE_DAYS_IN_HISTORY, JDE_DAYS_IN_REQUEST, JDE_ADJUST_TO_CURRENT_DATE, JDE_NTF_ON_COMPLETEION, JDE_NTF_TML_ID, JDE_NTF_TML_TMT_CODE, JDE_EMAIL_TML_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_JOB_DEFINITIONS ";

   @Override
   public SprJobDefinitionsDAO newRecord() {
	  	  SprJobDefinitionsDAO daoObject = new SprJobDefinitionsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprJobDefinitionsDAO object4ForceUpdate() {
       SprJobDefinitionsDAO daoObject = new SprJobDefinitionsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprJobDefinitionsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprJobDefinitionsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprJobDefinitionsDAO> data = new ArrayList<SprJobDefinitionsDAO>();
      while (rs.next()) {
         data.add(new SprJobDefinitionsDAO(Utils.getDouble(rs.getObject("JDE_ID")), //
                               rs.getString("JDE_SYSTEM"), //
                               rs.getString("JDE_NAME"), //
                               rs.getString("JDE_TYPE"), //
                               rs.getString("JDE_CODE"), //
                               rs.getString("JDE_STATUS"), //
                               rs.getString("JDE_PATH"), //
                               Utils.getDouble(rs.getObject("JDE_TML_ID")), //
                               rs.getString("JDE_DEFAULT_EXECUTER"), //
                               rs.getString("JDE_EXECUTION_PARAMETER"), //
                               rs.getString("JDE_EXECUTION_UNIT"), //
                               rs.getString("JDE_ACTION_TYPE"), //
                               rs.getString("JDE_DEFAULT_OUTPUT_TYPE"), //
                               rs.getTimestamp("JDE_LAST_ACTION_TIME"), //
                               rs.getTimestamp("JDE_NEXT_ACTION_TIME"), //
                               rs.getString("JDE_DESCRIPTION"), //
                               rs.getString("JDE_PERIOD"), //
                               Utils.getDouble(rs.getObject("JDE_WEEK_DAY")), //
                               Utils.getDouble(rs.getObject("JDE_MONTH_DAY")), //
                               Utils.getDouble(rs.getObject("JDE_YEAR_DAY")), //
                               Utils.getDouble(rs.getObject("JDE_HOUR")), //
                               Utils.getDouble(rs.getObject("JDE_MINUTES")), //
                               Utils.getDouble(rs.getObject("JDE_PERIOD_IN_MINUTES")), //
                               rs.getTimestamp("JDE_DATE_FROM"), //
                               rs.getTimestamp("JDE_DATE_TO"), //
                               Utils.getDouble(rs.getObject("JDE_DAYS_IN_HISTORY")), //
                               Utils.getDouble(rs.getObject("JDE_DAYS_IN_REQUEST")), //
                               rs.getString("JDE_ADJUST_TO_CURRENT_DATE"), //
                               rs.getString("JDE_NTF_ON_COMPLETEION"), //
                               Utils.getDouble(rs.getObject("JDE_NTF_TML_ID")), //
                               rs.getString("JDE_NTF_TML_TMT_CODE"), //
                               Utils.getDouble(rs.getObject("JDE_EMAIL_TML_ID")), //
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
   public SprJobDefinitionsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE JDE_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprJobDefinitionsDAO> data = null;
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
      SprJobDefinitionsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprJobDefinitionsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_JOB_DEFINITIONS WHERE JDE_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprJobDefinitionsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getJde_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprJobDefinitionsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_JOB_DEFINITIONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprJobDefinitionsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "JDE_CODE_IDX_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getJde_code() != null) {
                String[] constraintColumns = { "jde_code" };
                String stmt = "select 1 from SPR_JOB_DEFINITIONS where " //
                        + constructStatementPart("jde_code", daoObject.getJde_code()) + " and " //
                        + (daoObject.getJde_id() != null ? "JDE_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getJde_code());
                    if (daoObject.getJde_id() != null) {
                        pstmt.setObject(++idx, daoObject.getJde_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("JDE_CODE_IDX_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprJobDefinitionsDAO insertRecord(Connection conn, SprJobDefinitionsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_JOB_DEFINITIONS (JDE_SYSTEM, JDE_NAME, JDE_TYPE, JDE_CODE, JDE_STATUS, JDE_PATH, JDE_TML_ID, JDE_DEFAULT_EXECUTER, JDE_EXECUTION_PARAMETER, JDE_EXECUTION_UNIT, JDE_ACTION_TYPE, JDE_DEFAULT_OUTPUT_TYPE, JDE_LAST_ACTION_TIME, JDE_NEXT_ACTION_TIME, JDE_DESCRIPTION, JDE_PERIOD, JDE_WEEK_DAY, JDE_MONTH_DAY, JDE_YEAR_DAY, JDE_HOUR, JDE_MINUTES, JDE_PERIOD_IN_MINUTES, JDE_DATE_FROM, JDE_DATE_TO, JDE_DAYS_IN_HISTORY, JDE_DAYS_IN_REQUEST, JDE_ADJUST_TO_CURRENT_DATE, JDE_NTF_ON_COMPLETEION, JDE_NTF_TML_ID, JDE_NTF_TML_TMT_CODE, JDE_EMAIL_TML_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING JDE_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getJde_system());
         pstmt.setString(2, daoObject.getJde_name());
         pstmt.setString(3, daoObject.getJde_type());
         pstmt.setString(4, daoObject.getJde_code());
         pstmt.setString(5, daoObject.getJde_status());
         pstmt.setString(6, daoObject.getJde_path());
         pstmt.setObject(7, daoObject.getJde_tml_id());
         pstmt.setString(8, daoObject.getJde_default_executer());
         pstmt.setString(9, daoObject.getJde_execution_parameter());
         pstmt.setString(10, daoObject.getJde_execution_unit());
         pstmt.setString(11, daoObject.getJde_action_type());
         pstmt.setString(12, daoObject.getJde_default_output_type());
         pstmt.setObject(13,  Utils.getSqlTimestamp(daoObject.getJde_last_action_time()));
         pstmt.setObject(14,  Utils.getSqlTimestamp(daoObject.getJde_next_action_time()));
         pstmt.setString(15, daoObject.getJde_description());
         pstmt.setString(16, daoObject.getJde_period());
         pstmt.setObject(17, daoObject.getJde_week_day());
         pstmt.setObject(18, daoObject.getJde_month_day());
         pstmt.setObject(19, daoObject.getJde_year_day());
         pstmt.setObject(20, daoObject.getJde_hour());
         pstmt.setObject(21, daoObject.getJde_minutes());
         pstmt.setObject(22, daoObject.getJde_period_in_minutes());
         pstmt.setObject(23,  Utils.getSqlDate(daoObject.getJde_date_from()));
         pstmt.setObject(24,  Utils.getSqlDate(daoObject.getJde_date_to()));
         pstmt.setObject(25, daoObject.getJde_days_in_history());
         pstmt.setObject(26, daoObject.getJde_days_in_request());
         pstmt.setString(27, daoObject.getJde_adjust_to_current_date());
         pstmt.setString(28, daoObject.getJde_ntf_on_completeion());
         pstmt.setObject(29, daoObject.getJde_ntf_tml_id());
         pstmt.setString(30, daoObject.getJde_ntf_tml_tmt_code());
         pstmt.setObject(31, daoObject.getJde_email_tml_id());
         pstmt.setObject(32, daoObject.getN01());
         pstmt.setObject(33, daoObject.getN02());
         pstmt.setObject(34, daoObject.getN03());
         pstmt.setObject(35, daoObject.getN04());
         pstmt.setObject(36, daoObject.getN05());
         pstmt.setString(37, daoObject.getC01());
         pstmt.setString(38, daoObject.getC02());
         pstmt.setString(39, daoObject.getC03());
         pstmt.setString(40, daoObject.getC04());
         pstmt.setString(41, daoObject.getC05());
         pstmt.setObject(42,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(43,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(44,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(45,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(46,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(47, 1);
         pstmt.setObject(48,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(49,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(50, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setJde_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprJobDefinitionsDAO updateRecord(Connection conn, SprJobDefinitionsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprJobDefinitionsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprJobDefinitionsDAO saveRecord(Connection conn, SprJobDefinitionsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}