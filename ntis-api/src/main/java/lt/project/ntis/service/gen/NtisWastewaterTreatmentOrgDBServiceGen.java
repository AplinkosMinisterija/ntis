package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisWastewaterTreatmentOrgDAO;
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

public class NtisWastewaterTreatmentOrgDBServiceGen extends SprBaseDBServiceImpl<NtisWastewaterTreatmentOrgDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisWastewaterTreatmentOrgDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT WTO_ID, WTO_NAME, WTO_ADDRESS, WTO_PRODUCTIVITY, WTO_DOMESTIC_SEWAGE, WTO_INDUSTRIAL_SEWAGE, WTO_SLUDGE, WTO_IS_IT_USED, WTO_AD_ID, WTO_ORG_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, WTO_AUTO_ACCEPT, WTO_NO_NOTIFICATIONS FROM NTIS_WASTEWATER_TREATMENT_ORG ";

   @Override
   public NtisWastewaterTreatmentOrgDAO newRecord() {
         NtisWastewaterTreatmentOrgDAO daoObject = new NtisWastewaterTreatmentOrgDAO();
         return daoObject;
     }
   @Override
   public NtisWastewaterTreatmentOrgDAO object4ForceUpdate() {
       NtisWastewaterTreatmentOrgDAO daoObject = new NtisWastewaterTreatmentOrgDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisWastewaterTreatmentOrgDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisWastewaterTreatmentOrgDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisWastewaterTreatmentOrgDAO> data = new ArrayList<NtisWastewaterTreatmentOrgDAO>();
      while (rs.next()) {
         data.add(new NtisWastewaterTreatmentOrgDAO(Utils.getDouble(rs.getObject("WTO_ID")), //
                               rs.getString("WTO_NAME"), //
                               rs.getString("WTO_ADDRESS"), //
                               Utils.getDouble(rs.getObject("WTO_PRODUCTIVITY")), //
                               rs.getString("WTO_DOMESTIC_SEWAGE"), //
                               rs.getString("WTO_INDUSTRIAL_SEWAGE"), //
                               rs.getString("WTO_SLUDGE"), //
                               rs.getString("WTO_IS_IT_USED"), //
                               Utils.getDouble(rs.getObject("WTO_AD_ID")), //
                               Utils.getDouble(rs.getObject("WTO_ORG_ID")), //
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
                               rs.getTimestamp("D05"), //
                               rs.getString("WTO_AUTO_ACCEPT"), //
                               rs.getString("WTO_NO_NOTIFICATIONS")));
         }
      return data;
   }

   @Override
   public NtisWastewaterTreatmentOrgDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE WTO_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisWastewaterTreatmentOrgDAO> data = null;
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
      NtisWastewaterTreatmentOrgDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisWastewaterTreatmentOrgDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_WASTEWATER_TREATMENT_ORG WHERE WTO_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisWastewaterTreatmentOrgDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getWto_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisWastewaterTreatmentOrgDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisWastewaterTreatmentOrgDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisWastewaterTreatmentOrgDAO insertRecord(Connection conn, NtisWastewaterTreatmentOrgDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_WASTEWATER_TREATMENT_ORG (WTO_NAME, WTO_ADDRESS, WTO_PRODUCTIVITY, WTO_DOMESTIC_SEWAGE, WTO_INDUSTRIAL_SEWAGE, WTO_SLUDGE, WTO_IS_IT_USED, WTO_AD_ID, WTO_ORG_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, WTO_AUTO_ACCEPT, WTO_NO_NOTIFICATIONS, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING WTO_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getWto_name());
         pstmt.setString(2, daoObject.getWto_address());
         pstmt.setObject(3, daoObject.getWto_productivity());
         pstmt.setString(4, daoObject.getWto_domestic_sewage());
         pstmt.setString(5, daoObject.getWto_industrial_sewage());
         pstmt.setString(6, daoObject.getWto_sludge());
         pstmt.setString(7, daoObject.getWto_is_it_used());
         pstmt.setObject(8, daoObject.getWto_ad_id());
         pstmt.setObject(9, daoObject.getWto_org_id());
         pstmt.setObject(10, daoObject.getN01());
         pstmt.setObject(11, daoObject.getN02());
         pstmt.setObject(12, daoObject.getN03());
         pstmt.setObject(13, daoObject.getN04());
         pstmt.setObject(14, daoObject.getN05());
         pstmt.setString(15, daoObject.getC01());
         pstmt.setString(16, daoObject.getC02());
         pstmt.setString(17, daoObject.getC03());
         pstmt.setString(18, daoObject.getC04());
         pstmt.setString(19, daoObject.getC05());
         pstmt.setObject(20,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(21,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getD05()));
         pstmt.setString(25, daoObject.getWto_auto_accept());
         pstmt.setString(26, daoObject.getWto_no_notifications());
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
            daoObject.setWto_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisWastewaterTreatmentOrgDAO updateRecord(Connection conn, NtisWastewaterTreatmentOrgDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisWastewaterTreatmentOrgDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisWastewaterTreatmentOrgDAO saveRecord(Connection conn, NtisWastewaterTreatmentOrgDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}