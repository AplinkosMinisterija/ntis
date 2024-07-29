package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisServiceRequestsDAO;
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

public class NtisServiceRequestsDBServiceGen extends SprBaseDBServiceImpl<NtisServiceRequestsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisServiceRequestsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT SR_ID, SR_REG_NO, SR_TYPE, SR_EMAIL, SR_EMAIL_VERIFIED, SR_PHONE, SR_RESP_PERSON_DESCRIPTION, SR_HOMEPAGE, SR_DATA_IS_CORRECT, SR_RULES_ACCEPTED, SR_STATUS, SR_STATUS_DATE, SR_REGISTRATION_DATE, SR_REMOVAL_REASON, SR_REMOVAL_DATE, SR_ORG_ID, SR_USR_ID, SR_PER_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_SERVICE_REQUESTS ";

   @Override
   public NtisServiceRequestsDAO newRecord() {
	  	  NtisServiceRequestsDAO daoObject = new NtisServiceRequestsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisServiceRequestsDAO object4ForceUpdate() {
       NtisServiceRequestsDAO daoObject = new NtisServiceRequestsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisServiceRequestsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisServiceRequestsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisServiceRequestsDAO> data = new ArrayList<NtisServiceRequestsDAO>();
      while (rs.next()) {
         data.add(new NtisServiceRequestsDAO(Utils.getDouble(rs.getObject("SR_ID")), //
                               rs.getString("SR_REG_NO"), //
                               rs.getString("SR_TYPE"), //
                               rs.getString("SR_EMAIL"), //
                               rs.getString("SR_EMAIL_VERIFIED"), //
                               rs.getString("SR_PHONE"), //
                               rs.getString("SR_RESP_PERSON_DESCRIPTION"), //
                               rs.getString("SR_HOMEPAGE"), //
                               rs.getString("SR_DATA_IS_CORRECT"), //
                               rs.getString("SR_RULES_ACCEPTED"), //
                               rs.getString("SR_STATUS"), //
                               rs.getTimestamp("SR_STATUS_DATE"), //
                               rs.getTimestamp("SR_REGISTRATION_DATE"), //
                               rs.getString("SR_REMOVAL_REASON"), //
                               rs.getTimestamp("SR_REMOVAL_DATE"), //
                               Utils.getDouble(rs.getObject("SR_ORG_ID")), //
                               Utils.getDouble(rs.getObject("SR_USR_ID")), //
                               Utils.getDouble(rs.getObject("SR_PER_ID")), //
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
   public NtisServiceRequestsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE SR_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisServiceRequestsDAO> data = null;
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
      NtisServiceRequestsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisServiceRequestsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_SERVICE_REQUESTS WHERE SR_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisServiceRequestsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getSr_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisServiceRequestsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_SERVICE_REQUESTS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisServiceRequestsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisServiceRequestsDAO insertRecord(Connection conn, NtisServiceRequestsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_SERVICE_REQUESTS (SR_REG_NO, SR_TYPE, SR_EMAIL, SR_EMAIL_VERIFIED, SR_PHONE, SR_RESP_PERSON_DESCRIPTION, SR_HOMEPAGE, SR_DATA_IS_CORRECT, SR_RULES_ACCEPTED, SR_STATUS, SR_STATUS_DATE, SR_REGISTRATION_DATE, SR_REMOVAL_REASON, SR_REMOVAL_DATE, SR_ORG_ID, SR_USR_ID, SR_PER_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING SR_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getSr_reg_no());
         pstmt.setString(2, daoObject.getSr_type());
         pstmt.setString(3, daoObject.getSr_email());
         pstmt.setString(4, daoObject.getSr_email_verified());
         pstmt.setString(5, daoObject.getSr_phone());
         pstmt.setString(6, daoObject.getSr_resp_person_description());
         pstmt.setString(7, daoObject.getSr_homepage());
         pstmt.setString(8, daoObject.getSr_data_is_correct());
         pstmt.setString(9, daoObject.getSr_rules_accepted());
         pstmt.setString(10, daoObject.getSr_status());
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getSr_status_date()));
         pstmt.setObject(12,  Utils.getSqlTimestamp(daoObject.getSr_registration_date()));
         pstmt.setString(13, daoObject.getSr_removal_reason());
         pstmt.setObject(14,  Utils.getSqlTimestamp(daoObject.getSr_removal_date()));
         pstmt.setObject(15, daoObject.getSr_org_id());
         pstmt.setObject(16, daoObject.getSr_usr_id());
         pstmt.setObject(17, daoObject.getSr_per_id());
         pstmt.setObject(18, daoObject.getN01());
         pstmt.setObject(19, daoObject.getN02());
         pstmt.setObject(20, daoObject.getN03());
         pstmt.setObject(21, daoObject.getN04());
         pstmt.setObject(22, daoObject.getN05());
         pstmt.setString(23, daoObject.getC01());
         pstmt.setString(24, daoObject.getC02());
         pstmt.setString(25, daoObject.getC03());
         pstmt.setString(26, daoObject.getC04());
         pstmt.setString(27, daoObject.getC05());
         pstmt.setObject(28,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(29,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(30,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(31,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(32,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(33, 1);
         pstmt.setObject(34,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(35,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(36, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setSr_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisServiceRequestsDAO updateRecord(Connection conn, NtisServiceRequestsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisServiceRequestsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisServiceRequestsDAO saveRecord(Connection conn, NtisServiceRequestsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}