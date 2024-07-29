package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisFacilityUpdateAgreementDAO;
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

public class NtisFacilityUpdateAgreementDBServiceGen extends SprBaseDBServiceImpl<NtisFacilityUpdateAgreementDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisFacilityUpdateAgreementDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT FUA_ID, FUA_MANUFECTURER, FUA_MODEL, FUA_TYPE, FUA_STATE, FUA_CREATED, FUA_PER_ID, FUA_WTF_ID, FUA_CONFIRMED_USR_ID, FUA_WTF_OLD_INFO_JSON, FUA_WTF_NEW_INFO_JSON, FUA_WTF_OBJECT_INFO_JSON, FUA_NETWORK_CONNECTION_DATE, FUA_FIL_ID, FUA_BN_ID, FUA_ORG_ID, FUA_SO_ID, FUA_REQ_ORG_ID, FUA_CANCELLATION_REASON, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_FACILITY_UPDATE_AGREEMENT ";

   @Override
   public NtisFacilityUpdateAgreementDAO newRecord() {
	  	  NtisFacilityUpdateAgreementDAO daoObject = new NtisFacilityUpdateAgreementDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisFacilityUpdateAgreementDAO object4ForceUpdate() {
       NtisFacilityUpdateAgreementDAO daoObject = new NtisFacilityUpdateAgreementDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisFacilityUpdateAgreementDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisFacilityUpdateAgreementDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisFacilityUpdateAgreementDAO> data = new ArrayList<NtisFacilityUpdateAgreementDAO>();
      while (rs.next()) {
         data.add(new NtisFacilityUpdateAgreementDAO(Utils.getDouble(rs.getObject("FUA_ID")), //
                               rs.getString("FUA_MANUFECTURER"), //
                               rs.getString("FUA_MODEL"), //
                               rs.getString("FUA_TYPE"), //
                               rs.getString("FUA_STATE"), //
                               rs.getTimestamp("FUA_CREATED"), //
                               Utils.getDouble(rs.getObject("FUA_PER_ID")), //
                               Utils.getDouble(rs.getObject("FUA_WTF_ID")), //
                               Utils.getDouble(rs.getObject("FUA_CONFIRMED_USR_ID")), //
                               rs.getString("FUA_WTF_OLD_INFO_JSON"), //
                               rs.getString("FUA_WTF_NEW_INFO_JSON"), //
                               rs.getString("FUA_WTF_OBJECT_INFO_JSON"), //
                               rs.getTimestamp("FUA_NETWORK_CONNECTION_DATE"), //
                               Utils.getDouble(rs.getObject("FUA_FIL_ID")), //
                               Utils.getDouble(rs.getObject("FUA_BN_ID")), //
                               Utils.getDouble(rs.getObject("FUA_ORG_ID")), //
                               Utils.getDouble(rs.getObject("FUA_SO_ID")), //
                               Utils.getDouble(rs.getObject("FUA_REQ_ORG_ID")), //
                               rs.getString("FUA_CANCELLATION_REASON"), //
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
   public NtisFacilityUpdateAgreementDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE FUA_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisFacilityUpdateAgreementDAO> data = null;
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
      NtisFacilityUpdateAgreementDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisFacilityUpdateAgreementDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_FACILITY_UPDATE_AGREEMENT WHERE FUA_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisFacilityUpdateAgreementDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getFua_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisFacilityUpdateAgreementDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisFacilityUpdateAgreementDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisFacilityUpdateAgreementDAO insertRecord(Connection conn, NtisFacilityUpdateAgreementDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_FACILITY_UPDATE_AGREEMENT (FUA_MANUFECTURER, FUA_MODEL, FUA_TYPE, FUA_STATE, FUA_CREATED, FUA_PER_ID, FUA_WTF_ID, FUA_CONFIRMED_USR_ID, FUA_WTF_OLD_INFO_JSON, FUA_WTF_NEW_INFO_JSON, FUA_WTF_OBJECT_INFO_JSON, FUA_NETWORK_CONNECTION_DATE, FUA_FIL_ID, FUA_BN_ID, FUA_ORG_ID, FUA_SO_ID, FUA_REQ_ORG_ID, FUA_CANCELLATION_REASON, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING FUA_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getFua_manufecturer());
         pstmt.setString(2, daoObject.getFua_model());
         pstmt.setString(3, daoObject.getFua_type());
         pstmt.setString(4, daoObject.getFua_state());
         pstmt.setObject(5,  Utils.getSqlTimestamp(daoObject.getFua_created()));
         pstmt.setObject(6, daoObject.getFua_per_id());
         pstmt.setObject(7, daoObject.getFua_wtf_id());
         pstmt.setObject(8, daoObject.getFua_confirmed_usr_id());
         pstmt.setString(9, daoObject.getFua_wtf_old_info_json());
         pstmt.setString(10, daoObject.getFua_wtf_new_info_json());
         pstmt.setString(11, daoObject.getFua_wtf_object_info_json());
         pstmt.setObject(12,  Utils.getSqlTimestamp(daoObject.getFua_network_connection_date()));
         pstmt.setObject(13, daoObject.getFua_fil_id());
         pstmt.setObject(14, daoObject.getFua_bn_id());
         pstmt.setObject(15, daoObject.getFua_org_id());
         pstmt.setObject(16, daoObject.getFua_so_id());
         pstmt.setObject(17, daoObject.getFua_req_org_id());
         pstmt.setString(18, daoObject.getFua_cancellation_reason());
         pstmt.setObject(19, daoObject.getN01());
         pstmt.setObject(20, daoObject.getN02());
         pstmt.setObject(21, daoObject.getN03());
         pstmt.setObject(22, daoObject.getN04());
         pstmt.setObject(23, daoObject.getN05());
         pstmt.setString(24, daoObject.getC01());
         pstmt.setString(25, daoObject.getC02());
         pstmt.setString(26, daoObject.getC03());
         pstmt.setString(27, daoObject.getC04());
         pstmt.setString(28, daoObject.getC05());
         pstmt.setObject(29,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(30,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(31,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(32,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(34, 1);
         pstmt.setObject(35,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(36,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(37, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setFua_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisFacilityUpdateAgreementDAO updateRecord(Connection conn, NtisFacilityUpdateAgreementDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisFacilityUpdateAgreementDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisFacilityUpdateAgreementDAO saveRecord(Connection conn, NtisFacilityUpdateAgreementDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}