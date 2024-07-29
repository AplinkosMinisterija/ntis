package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprAuditableTablesDAO;
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

public class SprAuditableTablesDBServiceGen extends SprBaseDBServiceImpl<SprAuditableTablesDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprAuditableTablesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT AUT_ID, AUT_TABLE_SCHEMA, AUT_TABLE_NAME, AUT_TRIGGER_ENABLED, AUT_TRIGGER_NAME, AUT_NUM_OF_DAYS_IN_AUDIT, AUT_NUM_OF_DAYS_IN_ARCHIVE, AUT_AUDIT_TABLE_NAME, AUT_ARCHIVE_TABLE_NAME, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_AUDITABLE_TABLES ";

   @Override
   public SprAuditableTablesDAO newRecord() {
	  	  SprAuditableTablesDAO daoObject = new SprAuditableTablesDAO();
	  	  return daoObject;
	  }
   @Override
   public SprAuditableTablesDAO object4ForceUpdate() {
       SprAuditableTablesDAO daoObject = new SprAuditableTablesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprAuditableTablesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprAuditableTablesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprAuditableTablesDAO> data = new ArrayList<SprAuditableTablesDAO>();
      while (rs.next()) {
         data.add(new SprAuditableTablesDAO(Utils.getDouble(rs.getObject("AUT_ID")), //
                               rs.getString("AUT_TABLE_SCHEMA"), //
                               rs.getString("AUT_TABLE_NAME"), //
                               rs.getString("AUT_TRIGGER_ENABLED"), //
                               rs.getString("AUT_TRIGGER_NAME"), //
                               Utils.getDouble(rs.getObject("AUT_NUM_OF_DAYS_IN_AUDIT")), //
                               Utils.getDouble(rs.getObject("AUT_NUM_OF_DAYS_IN_ARCHIVE")), //
                               rs.getString("AUT_AUDIT_TABLE_NAME"), //
                               rs.getString("AUT_ARCHIVE_TABLE_NAME"), //
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
   public SprAuditableTablesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE AUT_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprAuditableTablesDAO> data = null;
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
      SprAuditableTablesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprAuditableTablesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_AUDITABLE_TABLES WHERE AUT_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprAuditableTablesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getAut_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprAuditableTablesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_AUDITABLE_TABLES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprAuditableTablesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprAuditableTablesDAO insertRecord(Connection conn, SprAuditableTablesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_AUDITABLE_TABLES (AUT_TABLE_SCHEMA, AUT_TABLE_NAME, AUT_TRIGGER_ENABLED, AUT_TRIGGER_NAME, AUT_NUM_OF_DAYS_IN_AUDIT, AUT_NUM_OF_DAYS_IN_ARCHIVE, AUT_AUDIT_TABLE_NAME, AUT_ARCHIVE_TABLE_NAME, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING AUT_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getAut_table_schema());
         pstmt.setString(2, daoObject.getAut_table_name());
         pstmt.setString(3, daoObject.getAut_trigger_enabled());
         pstmt.setString(4, daoObject.getAut_trigger_name());
         pstmt.setObject(5, daoObject.getAut_num_of_days_in_audit());
         pstmt.setObject(6, daoObject.getAut_num_of_days_in_archive());
         pstmt.setString(7, daoObject.getAut_audit_table_name());
         pstmt.setString(8, daoObject.getAut_archive_table_name());
         pstmt.setObject(9, daoObject.getN01());
         pstmt.setObject(10, daoObject.getN02());
         pstmt.setObject(11, daoObject.getN03());
         pstmt.setObject(12, daoObject.getN04());
         pstmt.setObject(13, daoObject.getN05());
         pstmt.setString(14, daoObject.getC01());
         pstmt.setString(15, daoObject.getC02());
         pstmt.setString(16, daoObject.getC03());
         pstmt.setString(17, daoObject.getC04());
         pstmt.setString(18, daoObject.getC05());
         pstmt.setObject(19,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(20,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(21,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(24, 1);
         pstmt.setObject(25,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(26,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(27, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setAut_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprAuditableTablesDAO updateRecord(Connection conn, SprAuditableTablesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprAuditableTablesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprAuditableTablesDAO saveRecord(Connection conn, SprAuditableTablesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}