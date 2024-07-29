package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisOrdersDAO;
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

public class NtisOrdersDBServiceGen extends SprBaseDBServiceImpl<NtisOrdersDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisOrdersDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT ORD_ID, ORD_ADDITIONAL_DESCRIPTION, ORD_STATE, ORD_EMAIL, ORD_TYPE, ORD_COMPLIANCE_NORMS, ORD_PHONE_NUMBER, ORD_CREATED_IN_NTIS_PORTAL, ORD_REJECTION_REASON, ORD_REJECTION_DATE, ORD_COMPLETION_REQUEST_DATE, ORD_COMPLETION_ESTIMATE_DATE, ORD_PLANNED_WORKS_DESCRIPTION, ORD_CREATED, ORD_REMOVED_SEWAGE_DATE, ORD_PREFERED_DATE_FROM, ORD_PREFERED_DATE_TO, ORD_ORG_ID, ORD_PER_ID, ORD_USR_ID, ORD_WTF_ID, ORD_SRV_ID, ORD_CS_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_ORDERS ";

   @Override
   public NtisOrdersDAO newRecord() {
	  	  NtisOrdersDAO daoObject = new NtisOrdersDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisOrdersDAO object4ForceUpdate() {
       NtisOrdersDAO daoObject = new NtisOrdersDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisOrdersDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisOrdersDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisOrdersDAO> data = new ArrayList<NtisOrdersDAO>();
      while (rs.next()) {
         data.add(new NtisOrdersDAO(Utils.getDouble(rs.getObject("ORD_ID")), //
                               rs.getString("ORD_ADDITIONAL_DESCRIPTION"), //
                               rs.getString("ORD_STATE"), //
                               rs.getString("ORD_EMAIL"), //
                               rs.getString("ORD_TYPE"), //
                               rs.getString("ORD_COMPLIANCE_NORMS"), //
                               rs.getString("ORD_PHONE_NUMBER"), //
                               rs.getString("ORD_CREATED_IN_NTIS_PORTAL"), //
                               rs.getString("ORD_REJECTION_REASON"), //
                               rs.getTimestamp("ORD_REJECTION_DATE"), //
                               rs.getTimestamp("ORD_COMPLETION_REQUEST_DATE"), //
                               rs.getTimestamp("ORD_COMPLETION_ESTIMATE_DATE"), //
                               rs.getString("ORD_PLANNED_WORKS_DESCRIPTION"), //
                               rs.getTimestamp("ORD_CREATED"), //
                               rs.getTimestamp("ORD_REMOVED_SEWAGE_DATE"), //
                               rs.getTimestamp("ORD_PREFERED_DATE_FROM"), //
                               rs.getTimestamp("ORD_PREFERED_DATE_TO"), //
                               Utils.getDouble(rs.getObject("ORD_ORG_ID")), //
                               Utils.getDouble(rs.getObject("ORD_PER_ID")), //
                               Utils.getDouble(rs.getObject("ORD_USR_ID")), //
                               Utils.getDouble(rs.getObject("ORD_WTF_ID")), //
                               Utils.getDouble(rs.getObject("ORD_SRV_ID")), //
                               Utils.getDouble(rs.getObject("ORD_CS_ID")), //
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
   public NtisOrdersDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE ORD_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisOrdersDAO> data = null;
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
      NtisOrdersDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisOrdersDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_ORDERS WHERE ORD_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisOrdersDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getOrd_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisOrdersDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_ORDERS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisOrdersDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisOrdersDAO insertRecord(Connection conn, NtisOrdersDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_ORDERS (ORD_ADDITIONAL_DESCRIPTION, ORD_STATE, ORD_EMAIL, ORD_TYPE, ORD_COMPLIANCE_NORMS, ORD_PHONE_NUMBER, ORD_CREATED_IN_NTIS_PORTAL, ORD_REJECTION_REASON, ORD_REJECTION_DATE, ORD_COMPLETION_REQUEST_DATE, ORD_COMPLETION_ESTIMATE_DATE, ORD_PLANNED_WORKS_DESCRIPTION, ORD_CREATED, ORD_REMOVED_SEWAGE_DATE, ORD_PREFERED_DATE_FROM, ORD_PREFERED_DATE_TO, ORD_ORG_ID, ORD_PER_ID, ORD_USR_ID, ORD_WTF_ID, ORD_SRV_ID, ORD_CS_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ORD_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getOrd_additional_description());
         pstmt.setString(2, daoObject.getOrd_state());
         pstmt.setString(3, daoObject.getOrd_email());
         pstmt.setString(4, daoObject.getOrd_type());
         pstmt.setString(5, daoObject.getOrd_compliance_norms());
         pstmt.setString(6, daoObject.getOrd_phone_number());
         pstmt.setString(7, daoObject.getOrd_created_in_ntis_portal());
         pstmt.setString(8, daoObject.getOrd_rejection_reason());
         pstmt.setObject(9,  Utils.getSqlTimestamp(daoObject.getOrd_rejection_date()));
         pstmt.setObject(10,  Utils.getSqlTimestamp(daoObject.getOrd_completion_request_date()));
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getOrd_completion_estimate_date()));
         pstmt.setString(12, daoObject.getOrd_planned_works_description());
         pstmt.setObject(13,  Utils.getSqlTimestamp(daoObject.getOrd_created()));
         pstmt.setObject(14,  Utils.getSqlTimestamp(daoObject.getOrd_removed_sewage_date()));
         pstmt.setObject(15,  Utils.getSqlTimestamp(daoObject.getOrd_prefered_date_from()));
         pstmt.setObject(16,  Utils.getSqlTimestamp(daoObject.getOrd_prefered_date_to()));
         pstmt.setObject(17, daoObject.getOrd_org_id());
         pstmt.setObject(18, daoObject.getOrd_per_id());
         pstmt.setObject(19, daoObject.getOrd_usr_id());
         pstmt.setObject(20, daoObject.getOrd_wtf_id());
         pstmt.setObject(21, daoObject.getOrd_srv_id());
         pstmt.setObject(22, daoObject.getOrd_cs_id());
         pstmt.setObject(23, daoObject.getN01());
         pstmt.setObject(24, daoObject.getN02());
         pstmt.setObject(25, daoObject.getN03());
         pstmt.setObject(26, daoObject.getN04());
         pstmt.setObject(27, daoObject.getN05());
         pstmt.setString(28, daoObject.getC01());
         pstmt.setString(29, daoObject.getC02());
         pstmt.setString(30, daoObject.getC03());
         pstmt.setString(31, daoObject.getC04());
         pstmt.setString(32, daoObject.getC05());
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(34,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(35,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(36,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(37,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(38, 1);
         pstmt.setObject(39,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(40,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(41, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setOrd_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisOrdersDAO updateRecord(Connection conn, NtisOrdersDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisOrdersDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisOrdersDAO saveRecord(Connection conn, NtisOrdersDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}