package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
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

public class SprJobRequestsDBServiceGen extends SprBaseDBServiceImpl<SprJobRequestsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprJobRequestsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT JRQ_ID, JRQ_STATUS, JRQ_TYPE, JRQ_FIL_ID, JRQ_HOST_CREATED, JRQ_JDE_ID, JRQ_USR_ID, JRQ_EXECUTER_NAME, JRQ_REQUEST_TIME, JRQ_RESULT_TIME, JRQ_START_DATE, JRQ_END_DATE, JRQ_RESULT_TYPE, JRQ_ERROR, JRQ_PRIORITY, JRQ_DATA, JRQ_LANG, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_JOB_REQUESTS ";

   @Override
   public SprJobRequestsDAO newRecord() {
	  	  SprJobRequestsDAO daoObject = new SprJobRequestsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprJobRequestsDAO object4ForceUpdate() {
       SprJobRequestsDAO daoObject = new SprJobRequestsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprJobRequestsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprJobRequestsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprJobRequestsDAO> data = new ArrayList<SprJobRequestsDAO>();
      while (rs.next()) {
         data.add(new SprJobRequestsDAO(Utils.getDouble(rs.getObject("JRQ_ID")), //
                               rs.getString("JRQ_STATUS"), //
                               rs.getString("JRQ_TYPE"), //
                               Utils.getDouble(rs.getObject("JRQ_FIL_ID")), //
                               rs.getString("JRQ_HOST_CREATED"), //
                               Utils.getDouble(rs.getObject("JRQ_JDE_ID")), //
                               Utils.getDouble(rs.getObject("JRQ_USR_ID")), //
                               rs.getString("JRQ_EXECUTER_NAME"), //
                               rs.getTimestamp("JRQ_REQUEST_TIME"), //
                               rs.getTimestamp("JRQ_RESULT_TIME"), //
                               rs.getTimestamp("JRQ_START_DATE"), //
                               rs.getTimestamp("JRQ_END_DATE"), //
                               rs.getString("JRQ_RESULT_TYPE"), //
                               rs.getString("JRQ_ERROR"), //
                               rs.getString("JRQ_PRIORITY"), //
                               rs.getString("JRQ_DATA"), //
                               rs.getString("JRQ_LANG"), //
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
   public SprJobRequestsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE JRQ_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprJobRequestsDAO> data = null;
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
      SprJobRequestsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprJobRequestsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_JOB_REQUESTS WHERE JRQ_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprJobRequestsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getJrq_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprJobRequestsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_JOB_REQUESTS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprJobRequestsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprJobRequestsDAO insertRecord(Connection conn, SprJobRequestsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_JOB_REQUESTS (JRQ_STATUS, JRQ_TYPE, JRQ_FIL_ID, JRQ_HOST_CREATED, JRQ_JDE_ID, JRQ_USR_ID, JRQ_EXECUTER_NAME, JRQ_REQUEST_TIME, JRQ_RESULT_TIME, JRQ_START_DATE, JRQ_END_DATE, JRQ_RESULT_TYPE, JRQ_ERROR, JRQ_PRIORITY, JRQ_DATA, JRQ_LANG, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING JRQ_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getJrq_status());
         pstmt.setString(2, daoObject.getJrq_type());
         pstmt.setObject(3, daoObject.getJrq_fil_id());
         pstmt.setString(4, daoObject.getJrq_host_created());
         pstmt.setObject(5, daoObject.getJrq_jde_id());
         pstmt.setObject(6, daoObject.getJrq_usr_id());
         pstmt.setString(7, daoObject.getJrq_executer_name());
         pstmt.setObject(8,  Utils.getSqlTimestamp(daoObject.getJrq_request_time()));
         pstmt.setObject(9,  Utils.getSqlTimestamp(daoObject.getJrq_result_time()));
         pstmt.setObject(10,  Utils.getSqlTimestamp(daoObject.getJrq_start_date()));
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getJrq_end_date()));
         pstmt.setString(12, daoObject.getJrq_result_type());
         pstmt.setString(13, daoObject.getJrq_error());
         pstmt.setString(14, daoObject.getJrq_priority());
         pstmt.setString(15, daoObject.getJrq_data());
         pstmt.setString(16, daoObject.getJrq_lang());
         pstmt.setObject(17, daoObject.getN01());
         pstmt.setObject(18, daoObject.getN02());
         pstmt.setObject(19, daoObject.getN03());
         pstmt.setObject(20, daoObject.getN04());
         pstmt.setObject(21, daoObject.getN05());
         pstmt.setString(22, daoObject.getC01());
         pstmt.setString(23, daoObject.getC02());
         pstmt.setString(24, daoObject.getC03());
         pstmt.setString(25, daoObject.getC04());
         pstmt.setString(26, daoObject.getC05());
         pstmt.setObject(27,  Utils.getSqlDate(daoObject.getD01()));
         pstmt.setObject(28,  Utils.getSqlDate(daoObject.getD02()));
         pstmt.setObject(29,  Utils.getSqlDate(daoObject.getD03()));
         pstmt.setObject(30,  Utils.getSqlDate(daoObject.getD04()));
         pstmt.setObject(31,  Utils.getSqlDate(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(32, 1);
         pstmt.setObject(33,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(34,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(35, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setJrq_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprJobRequestsDAO updateRecord(Connection conn, SprJobRequestsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprJobRequestsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprJobRequestsDAO saveRecord(Connection conn, SprJobRequestsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}