package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisServicesDAO;
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

public class NtisServicesDBServiceGen extends SprBaseDBServiceImpl<NtisServicesDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisServicesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT SRV_ID, SRV_TYPE, SRV_CONTRACT_AVAILABLE, SRV_AVAILABLE_IN_NTIS_PORTAL, SRV_LITHUANIAN_LEVEL, SRV_EMAIL, SRV_PHONE_NO, SRV_PRICE_FROM, SRV_PRICE_TO, SRV_COMPLETION_IN_DAYS_FROM, SRV_COMPLETION_IN_DAYS_TO, SRV_DESCRIPTION, SRV_DATE_FROM, SRV_DATE_TO, SRV_ORG_ID, SRV_FIL_ID, SRV_LAB_INSTR_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_SERVICES ";

   @Override
   public NtisServicesDAO newRecord() {
	  	  NtisServicesDAO daoObject = new NtisServicesDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisServicesDAO object4ForceUpdate() {
       NtisServicesDAO daoObject = new NtisServicesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisServicesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisServicesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisServicesDAO> data = new ArrayList<NtisServicesDAO>();
      while (rs.next()) {
         data.add(new NtisServicesDAO(Utils.getDouble(rs.getObject("SRV_ID")), //
                               rs.getString("SRV_TYPE"), //
                               rs.getString("SRV_CONTRACT_AVAILABLE"), //
                               rs.getString("SRV_AVAILABLE_IN_NTIS_PORTAL"), //
                               rs.getString("SRV_LITHUANIAN_LEVEL"), //
                               rs.getString("SRV_EMAIL"), //
                               rs.getString("SRV_PHONE_NO"), //
                               Utils.getDouble(rs.getObject("SRV_PRICE_FROM")), //
                               Utils.getDouble(rs.getObject("SRV_PRICE_TO")), //
                               Utils.getDouble(rs.getObject("SRV_COMPLETION_IN_DAYS_FROM")), //
                               Utils.getDouble(rs.getObject("SRV_COMPLETION_IN_DAYS_TO")), //
                               rs.getString("SRV_DESCRIPTION"), //
                               rs.getTimestamp("SRV_DATE_FROM"), //
                               rs.getTimestamp("SRV_DATE_TO"), //
                               Utils.getDouble(rs.getObject("SRV_ORG_ID")), //
                               Utils.getDouble(rs.getObject("SRV_FIL_ID")), //
                               Utils.getDouble(rs.getObject("SRV_LAB_INSTR_FIL_ID")), //
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
   public NtisServicesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE SRV_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisServicesDAO> data = null;
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
      NtisServicesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisServicesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_SERVICES WHERE SRV_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisServicesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getSrv_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisServicesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_SERVICES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisServicesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisServicesDAO insertRecord(Connection conn, NtisServicesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_SERVICES (SRV_TYPE, SRV_CONTRACT_AVAILABLE, SRV_AVAILABLE_IN_NTIS_PORTAL, SRV_LITHUANIAN_LEVEL, SRV_EMAIL, SRV_PHONE_NO, SRV_PRICE_FROM, SRV_PRICE_TO, SRV_COMPLETION_IN_DAYS_FROM, SRV_COMPLETION_IN_DAYS_TO, SRV_DESCRIPTION, SRV_DATE_FROM, SRV_DATE_TO, SRV_ORG_ID, SRV_FIL_ID, SRV_LAB_INSTR_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING SRV_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getSrv_type());
         pstmt.setString(2, daoObject.getSrv_contract_available());
         pstmt.setString(3, daoObject.getSrv_available_in_ntis_portal());
         pstmt.setString(4, daoObject.getSrv_lithuanian_level());
         pstmt.setString(5, daoObject.getSrv_email());
         pstmt.setString(6, daoObject.getSrv_phone_no());
         pstmt.setObject(7, daoObject.getSrv_price_from());
         pstmt.setObject(8, daoObject.getSrv_price_to());
         pstmt.setObject(9, daoObject.getSrv_completion_in_days_from());
         pstmt.setObject(10, daoObject.getSrv_completion_in_days_to());
         pstmt.setString(11, daoObject.getSrv_description());
         pstmt.setObject(12,  Utils.getSqlTimestamp(daoObject.getSrv_date_from()));
         pstmt.setObject(13,  Utils.getSqlTimestamp(daoObject.getSrv_date_to()));
         pstmt.setObject(14, daoObject.getSrv_org_id());
         pstmt.setObject(15, daoObject.getSrv_fil_id());
         pstmt.setObject(16, daoObject.getSrv_lab_instr_fil_id());
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
         pstmt.setObject(27,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(28,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(29,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(30,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(31,  Utils.getSqlTimestamp(daoObject.getD05()));
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
            daoObject.setSrv_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisServicesDAO updateRecord(Connection conn, NtisServicesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisServicesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisServicesDAO saveRecord(Connection conn, NtisServicesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}