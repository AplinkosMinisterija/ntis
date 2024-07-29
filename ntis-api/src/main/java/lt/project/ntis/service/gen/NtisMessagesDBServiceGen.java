package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisMessagesDAO;
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

public class NtisMessagesDBServiceGen extends SprBaseDBServiceImpl<NtisMessagesDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisMessagesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT MES_ID, MES_SUBJECT, MES_DESCRIPTION, MES_PRIORITY, MES_CREATED, MES_COT_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_MESSAGES ";

   @Override
   public NtisMessagesDAO newRecord() {
	  	  NtisMessagesDAO daoObject = new NtisMessagesDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisMessagesDAO object4ForceUpdate() {
       NtisMessagesDAO daoObject = new NtisMessagesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisMessagesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisMessagesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisMessagesDAO> data = new ArrayList<NtisMessagesDAO>();
      while (rs.next()) {
         data.add(new NtisMessagesDAO(Utils.getDouble(rs.getObject("MES_ID")), //
                               rs.getString("MES_SUBJECT"), //
                               rs.getString("MES_DESCRIPTION"), //
                               rs.getString("MES_PRIORITY"), //
                               rs.getTimestamp("MES_CREATED"), //
                               Utils.getDouble(rs.getObject("MES_COT_ID")), //
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
   public NtisMessagesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE MES_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisMessagesDAO> data = null;
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
      NtisMessagesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisMessagesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_MESSAGES WHERE MES_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisMessagesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getMes_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisMessagesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_MESSAGES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisMessagesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisMessagesDAO insertRecord(Connection conn, NtisMessagesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_MESSAGES (MES_SUBJECT, MES_DESCRIPTION, MES_PRIORITY, MES_CREATED, MES_COT_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING MES_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getMes_subject());
         pstmt.setString(2, daoObject.getMes_description());
         pstmt.setString(3, daoObject.getMes_priority());
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getMes_created()));
         pstmt.setObject(5, daoObject.getMes_cot_id());
         pstmt.setObject(6, daoObject.getN01());
         pstmt.setObject(7, daoObject.getN02());
         pstmt.setObject(8, daoObject.getN03());
         pstmt.setObject(9, daoObject.getN04());
         pstmt.setObject(10, daoObject.getN05());
         pstmt.setString(11, daoObject.getC01());
         pstmt.setString(12, daoObject.getC02());
         pstmt.setString(13, daoObject.getC03());
         pstmt.setString(14, daoObject.getC04());
         pstmt.setString(15, daoObject.getC05());
         pstmt.setObject(16,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(17,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(18,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(19,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(20,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(21, 1);
         pstmt.setObject(22,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(23,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(24, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setMes_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisMessagesDAO updateRecord(Connection conn, NtisMessagesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisMessagesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisMessagesDAO saveRecord(Connection conn, NtisMessagesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}