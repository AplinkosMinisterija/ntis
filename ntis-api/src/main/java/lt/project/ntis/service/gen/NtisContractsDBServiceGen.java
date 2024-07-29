package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisContractsDAO;
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

public class NtisContractsDBServiceGen extends SprBaseDBServiceImpl<NtisContractsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisContractsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT COT_ID, COT_CODE, COT_CREATED_IN_NTIS_PORTAL, COT_STATE, COT_FROM_DATE, COT_TO_DATE, COT_CREATED, COT_PROJECT_CREATED, COT_REJECTION_REASON, COT_REJECTION_DATE, COT_CLIENT_PHONE_NO, COT_CLIENT_EMAIL, COT_FIL_ID, COT_WTF_ID, COT_ORG_ID, COT_PER_ID, COT_SIGN_1_FIL_ID, COT_SIGN_2_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_CONTRACTS ";

   @Override
   public NtisContractsDAO newRecord() {
	  	  NtisContractsDAO daoObject = new NtisContractsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisContractsDAO object4ForceUpdate() {
       NtisContractsDAO daoObject = new NtisContractsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisContractsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisContractsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisContractsDAO> data = new ArrayList<NtisContractsDAO>();
      while (rs.next()) {
         data.add(new NtisContractsDAO(Utils.getDouble(rs.getObject("COT_ID")), //
                               rs.getString("COT_CODE"), //
                               rs.getString("COT_CREATED_IN_NTIS_PORTAL"), //
                               rs.getString("COT_STATE"), //
                               rs.getTimestamp("COT_FROM_DATE"), //
                               rs.getTimestamp("COT_TO_DATE"), //
                               rs.getTimestamp("COT_CREATED"), //
                               rs.getTimestamp("COT_PROJECT_CREATED"), //
                               rs.getString("COT_REJECTION_REASON"), //
                               rs.getTimestamp("COT_REJECTION_DATE"), //
                               rs.getString("COT_CLIENT_PHONE_NO"), //
                               rs.getString("COT_CLIENT_EMAIL"), //
                               Utils.getDouble(rs.getObject("COT_FIL_ID")), //
                               Utils.getDouble(rs.getObject("COT_WTF_ID")), //
                               Utils.getDouble(rs.getObject("COT_ORG_ID")), //
                               Utils.getDouble(rs.getObject("COT_PER_ID")), //
                               Utils.getDouble(rs.getObject("COT_SIGN_1_FIL_ID")), //
                               Utils.getDouble(rs.getObject("COT_SIGN_2_FIL_ID")), //
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
   public NtisContractsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE COT_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisContractsDAO> data = null;
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
      NtisContractsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisContractsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_CONTRACTS WHERE COT_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisContractsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getCot_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisContractsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_CONTRACTS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisContractsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisContractsDAO insertRecord(Connection conn, NtisContractsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_CONTRACTS (COT_CODE, COT_CREATED_IN_NTIS_PORTAL, COT_STATE, COT_FROM_DATE, COT_TO_DATE, COT_CREATED, COT_PROJECT_CREATED, COT_REJECTION_REASON, COT_REJECTION_DATE, COT_CLIENT_PHONE_NO, COT_CLIENT_EMAIL, COT_FIL_ID, COT_WTF_ID, COT_ORG_ID, COT_PER_ID, COT_SIGN_1_FIL_ID, COT_SIGN_2_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING COT_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getCot_code());
         pstmt.setString(2, daoObject.getCot_created_in_ntis_portal());
         pstmt.setString(3, daoObject.getCot_state());
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getCot_from_date()));
         pstmt.setObject(5,  Utils.getSqlTimestamp(daoObject.getCot_to_date()));
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getCot_created()));
         pstmt.setObject(7,  Utils.getSqlTimestamp(daoObject.getCot_project_created()));
         pstmt.setString(8, daoObject.getCot_rejection_reason());
         pstmt.setObject(9,  Utils.getSqlTimestamp(daoObject.getCot_rejection_date()));
         pstmt.setString(10, daoObject.getCot_client_phone_no());
         pstmt.setString(11, daoObject.getCot_client_email());
         pstmt.setObject(12, daoObject.getCot_fil_id());
         pstmt.setObject(13, daoObject.getCot_wtf_id());
         pstmt.setObject(14, daoObject.getCot_org_id());
         pstmt.setObject(15, daoObject.getCot_per_id());
         pstmt.setObject(16, daoObject.getCot_sign_1_fil_id());
         pstmt.setObject(17, daoObject.getCot_sign_2_fil_id());
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
            daoObject.setCot_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisContractsDAO updateRecord(Connection conn, NtisContractsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisContractsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisContractsDAO saveRecord(Connection conn, NtisContractsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}