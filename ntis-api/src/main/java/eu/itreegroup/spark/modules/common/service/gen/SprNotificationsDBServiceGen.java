package eu.itreegroup.spark.modules.common.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.common.dao.SprNotificationsDAO;
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

public class SprNotificationsDBServiceGen extends SprBaseDBServiceImpl<SprNotificationsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprNotificationsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT NTF_ID, NTF_TYPE, NTF_REFERENCE, NTF_TITLE, NTF_MESSAGE, NTF_CREATION_DATE, NTF_MARK_AS_READ_DATE, NTF_USR_ID, NTF_ROL_ID, NTF_ORG_ID, NTF_DATE_FROM, NTF_DATE_TO, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_NOTIFICATIONS ";

   @Override
   public SprNotificationsDAO newRecord() {
	  	  SprNotificationsDAO daoObject = new SprNotificationsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprNotificationsDAO object4ForceUpdate() {
       SprNotificationsDAO daoObject = new SprNotificationsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprNotificationsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprNotificationsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprNotificationsDAO> data = new ArrayList<SprNotificationsDAO>();
      while (rs.next()) {
         data.add(new SprNotificationsDAO(Utils.getDouble(rs.getObject("NTF_ID")), //
                               rs.getString("NTF_TYPE"), //
                               Utils.getDouble(rs.getObject("NTF_REFERENCE")), //
                               rs.getString("NTF_TITLE"), //
                               rs.getString("NTF_MESSAGE"), //
                               rs.getTimestamp("NTF_CREATION_DATE"), //
                               rs.getTimestamp("NTF_MARK_AS_READ_DATE"), //
                               Utils.getDouble(rs.getObject("NTF_USR_ID")), //
                               Utils.getDouble(rs.getObject("NTF_ROL_ID")), //
                               Utils.getDouble(rs.getObject("NTF_ORG_ID")), //
                               rs.getTimestamp("NTF_DATE_FROM"), //
                               rs.getTimestamp("NTF_DATE_TO"), //
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
   public SprNotificationsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE NTF_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprNotificationsDAO> data = null;
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
      SprNotificationsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprNotificationsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_NOTIFICATIONS WHERE NTF_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprNotificationsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getNtf_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprNotificationsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_NOTIFICATIONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprNotificationsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprNotificationsDAO insertRecord(Connection conn, SprNotificationsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_NOTIFICATIONS (NTF_TYPE, NTF_REFERENCE, NTF_TITLE, NTF_MESSAGE, NTF_CREATION_DATE, NTF_MARK_AS_READ_DATE, NTF_USR_ID, NTF_ROL_ID, NTF_ORG_ID, NTF_DATE_FROM, NTF_DATE_TO, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING NTF_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getNtf_type());
         pstmt.setObject(2, daoObject.getNtf_reference());
         pstmt.setString(3, daoObject.getNtf_title());
         pstmt.setString(4, daoObject.getNtf_message());
         pstmt.setObject(5,  Utils.getSqlTimestamp(daoObject.getNtf_creation_date()));
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getNtf_mark_as_read_date()));
         pstmt.setObject(7, daoObject.getNtf_usr_id());
         pstmt.setObject(8, daoObject.getNtf_rol_id());
         pstmt.setObject(9, daoObject.getNtf_org_id());
         pstmt.setObject(10,  Utils.getSqlTimestamp(daoObject.getNtf_date_from()));
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getNtf_date_to()));
         pstmt.setObject(12, daoObject.getN01());
         pstmt.setObject(13, daoObject.getN02());
         pstmt.setObject(14, daoObject.getN03());
         pstmt.setObject(15, daoObject.getN04());
         pstmt.setObject(16, daoObject.getN05());
         pstmt.setString(17, daoObject.getC01());
         pstmt.setString(18, daoObject.getC02());
         pstmt.setString(19, daoObject.getC03());
         pstmt.setString(20, daoObject.getC04());
         pstmt.setString(21, daoObject.getC05());
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(25,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(26,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(27, 1);
         pstmt.setObject(28,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(29,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(30, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setNtf_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprNotificationsDAO updateRecord(Connection conn, SprNotificationsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprNotificationsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprNotificationsDAO saveRecord(Connection conn, SprNotificationsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}