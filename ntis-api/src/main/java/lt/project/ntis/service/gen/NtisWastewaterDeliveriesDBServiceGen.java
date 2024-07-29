package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisWastewaterDeliveriesDAO;
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

public class NtisWastewaterDeliveriesDBServiceGen extends SprBaseDBServiceImpl<NtisWastewaterDeliveriesDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisWastewaterDeliveriesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT WD_ID, WD_SEWAGE_TYPE, WD_DELIVERED_QUANTITY, WD_USED_SLUDGE_QUANTITY, WD_ADDITIONAL_INFORMATION_SLUDGE_DELIVERY, WD_DELIVERED_WASTEWATER_DESCRIPTION, WD_STATE, WD_REJECTION_REASON, WD_DESCRIPTION, WD_DELIVERY_DATE, WD_ACCEPTED_SEWAGE_QUANTITY, WD_CR_ID, WD_ORG_ID, WD_ORD_ID, WD_WTO_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, WD_WD_ID FROM NTIS_WASTEWATER_DELIVERIES ";

   @Override
   public NtisWastewaterDeliveriesDAO newRecord() {
         NtisWastewaterDeliveriesDAO daoObject = new NtisWastewaterDeliveriesDAO();
         return daoObject;
     }
   @Override
   public NtisWastewaterDeliveriesDAO object4ForceUpdate() {
       NtisWastewaterDeliveriesDAO daoObject = new NtisWastewaterDeliveriesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisWastewaterDeliveriesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisWastewaterDeliveriesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisWastewaterDeliveriesDAO> data = new ArrayList<NtisWastewaterDeliveriesDAO>();
      while (rs.next()) {
         data.add(new NtisWastewaterDeliveriesDAO(Utils.getDouble(rs.getObject("WD_ID")), //
                               rs.getString("WD_SEWAGE_TYPE"), //
                               Utils.getDouble(rs.getObject("WD_DELIVERED_QUANTITY")), //
                               Utils.getDouble(rs.getObject("WD_USED_SLUDGE_QUANTITY")), //
                               rs.getString("WD_ADDITIONAL_INFORMATION_SLUDGE_DELIVERY"), //
                               rs.getString("WD_DELIVERED_WASTEWATER_DESCRIPTION"), //
                               rs.getString("WD_STATE"), //
                               rs.getString("WD_REJECTION_REASON"), //
                               rs.getString("WD_DESCRIPTION"), //
                               rs.getTimestamp("WD_DELIVERY_DATE"), //
                               Utils.getDouble(rs.getObject("WD_ACCEPTED_SEWAGE_QUANTITY")), //
                               Utils.getDouble(rs.getObject("WD_CR_ID")), //
                               Utils.getDouble(rs.getObject("WD_ORG_ID")), //
                               Utils.getDouble(rs.getObject("WD_ORD_ID")), //
                               Utils.getDouble(rs.getObject("WD_WTO_ID")), //
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
                               Utils.getDouble(rs.getObject("WD_WD_ID"))));
         }
      return data;
   }

   @Override
   public NtisWastewaterDeliveriesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE WD_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisWastewaterDeliveriesDAO> data = null;
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
      NtisWastewaterDeliveriesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisWastewaterDeliveriesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_WASTEWATER_DELIVERIES WHERE WD_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisWastewaterDeliveriesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getWd_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisWastewaterDeliveriesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_WASTEWATER_DELIVERIES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisWastewaterDeliveriesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisWastewaterDeliveriesDAO insertRecord(Connection conn, NtisWastewaterDeliveriesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_WASTEWATER_DELIVERIES (WD_SEWAGE_TYPE, WD_DELIVERED_QUANTITY, WD_USED_SLUDGE_QUANTITY, WD_ADDITIONAL_INFORMATION_SLUDGE_DELIVERY, WD_DELIVERED_WASTEWATER_DESCRIPTION, WD_STATE, WD_REJECTION_REASON, WD_DESCRIPTION, WD_DELIVERY_DATE, WD_ACCEPTED_SEWAGE_QUANTITY, WD_CR_ID, WD_ORG_ID, WD_ORD_ID, WD_WTO_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, WD_WD_ID, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING WD_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getWd_sewage_type());
         pstmt.setObject(2, daoObject.getWd_delivered_quantity());
         pstmt.setObject(3, daoObject.getWd_used_sludge_quantity());
         pstmt.setString(4, daoObject.getWd_additional_information_sludge_delivery());
         pstmt.setString(5, daoObject.getWd_delivered_wastewater_description());
         pstmt.setString(6, daoObject.getWd_state());
         pstmt.setString(7, daoObject.getWd_rejection_reason());
         pstmt.setString(8, daoObject.getWd_description());
         pstmt.setObject(9,  Utils.getSqlTimestamp(daoObject.getWd_delivery_date()));
         pstmt.setObject(10, daoObject.getWd_accepted_sewage_quantity());
         pstmt.setObject(11, daoObject.getWd_cr_id());
         pstmt.setObject(12, daoObject.getWd_org_id());
         pstmt.setObject(13, daoObject.getWd_ord_id());
         pstmt.setObject(14, daoObject.getWd_wto_id());
         pstmt.setObject(15, daoObject.getN01());
         pstmt.setObject(16, daoObject.getN02());
         pstmt.setObject(17, daoObject.getN03());
         pstmt.setObject(18, daoObject.getN04());
         pstmt.setObject(19, daoObject.getN05());
         pstmt.setString(20, daoObject.getC01());
         pstmt.setString(21, daoObject.getC02());
         pstmt.setString(22, daoObject.getC03());
         pstmt.setString(23, daoObject.getC04());
         pstmt.setString(24, daoObject.getC05());
         pstmt.setObject(25,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(26,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(27,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(28,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(29,  Utils.getSqlTimestamp(daoObject.getD05()));
         pstmt.setObject(30, daoObject.getWd_wd_id());
         //Record audit information (start)
         pstmt.setInt(31, 1);
         pstmt.setObject(32,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(33,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(34, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setWd_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisWastewaterDeliveriesDAO updateRecord(Connection conn, NtisWastewaterDeliveriesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisWastewaterDeliveriesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisWastewaterDeliveriesDAO saveRecord(Connection conn, NtisWastewaterDeliveriesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}