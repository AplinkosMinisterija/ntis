package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisWastewaterTreatmentFaciDAO;
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

public class NtisWastewaterTreatmentFaciDBServiceGen extends SprBaseDBServiceImpl<NtisWastewaterTreatmentFaciDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisWastewaterTreatmentFaciDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT WTF_ID, WTF_MANUFACTURER, WTF_MANUFACTURER_DESCRIPTION, WTF_MODEL, WTF_TYPE, WTF_CAPACITY, WTF_DISTANCE, WTF_TECHNICAL_PASSPORT_ID, WTF_STATE, WTF_DATA_SOURCE, WTF_INSTALLATION_DATE, WTF_CHECKOUT_DATE, WTF_DELETED, WTF_FACILITY_LATITUDE, WTF_FACILITY_LONGITUDE, WTF_FACILITY_MUNICIPALITY_CODE, WTF_DISCHARGE_TYPE, WTF_DISCHARGE_LATITUDE, WTF_DISCHARGE_LONGITUDE, WTF_AD_ID, WTF_AGG_ID, WTF_WAITING_UPDATE_CONFIRMATION, WTF_NC_AD_ID, WTF_NC_AGG_ID, WTF_NC_MANUFACTURER, WTF_NC_MANUFACTURER_DESCRIPTION, WTF_NC_MODEL, WTF_NC_TYPE, WTF_NC_CAPACITY, WTF_NC_DISTANCE, WTF_NC_TECHNICAL_PASSPORT_ID, WTF_NC_DATA_SOURCE, WTF_NC_INSTALLATION_DATE, WTF_NC_CHECKOUT_DATE, WTF_NC_DELETED, WTF_NC_FACILITY_LATITUDE, WTF_NC_FACILITY_LONGITUDE, WTF_NC_FACILITY_MUNICIPALITY_CODE, WTF_NC_DISCHARGE_TYPE, WTF_NC_DISCHARGE_LATITUDE, WTF_NC_DISCHARGE_LONGITUDE, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, WTF_FAM_ID, WTF_NC_FAM_POP_EQUIVALENT, WTF_NC_FAM_TECH_PASS, WTF_NC_FAM_CHDS, WTF_NC_FAM_BDS, WTF_NC_FAM_FLOAT_MATERIAL, WTF_NC_FAM_PHOSPHOR, WTF_NC_FAM_NITROGEN, WTF_NC_FAM_MANUFACTURER, WTF_NC_FAM_MODEL, WTF_NC_FAM_DESCRIPTION FROM NTIS_WASTEWATER_TREATMENT_FACI ";

   @Override
   public NtisWastewaterTreatmentFaciDAO newRecord() {
         NtisWastewaterTreatmentFaciDAO daoObject = new NtisWastewaterTreatmentFaciDAO();
         return daoObject;
     }
   @Override
   public NtisWastewaterTreatmentFaciDAO object4ForceUpdate() {
       NtisWastewaterTreatmentFaciDAO daoObject = new NtisWastewaterTreatmentFaciDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisWastewaterTreatmentFaciDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisWastewaterTreatmentFaciDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisWastewaterTreatmentFaciDAO> data = new ArrayList<NtisWastewaterTreatmentFaciDAO>();
      while (rs.next()) {
         data.add(new NtisWastewaterTreatmentFaciDAO(Utils.getDouble(rs.getObject("WTF_ID")), //
                               rs.getString("WTF_MANUFACTURER"), //
                               rs.getString("WTF_MANUFACTURER_DESCRIPTION"), //
                               rs.getString("WTF_MODEL"), //
                               rs.getString("WTF_TYPE"), //
                               rs.getString("WTF_CAPACITY"), //
                               Utils.getDouble(rs.getObject("WTF_DISTANCE")), //
                               rs.getString("WTF_TECHNICAL_PASSPORT_ID"), //
                               rs.getString("WTF_STATE"), //
                               rs.getString("WTF_DATA_SOURCE"), //
                               rs.getTimestamp("WTF_INSTALLATION_DATE"), //
                               rs.getTimestamp("WTF_CHECKOUT_DATE"), //
                               rs.getString("WTF_DELETED"), //
                               Utils.getDouble(rs.getObject("WTF_FACILITY_LATITUDE")), //
                               Utils.getDouble(rs.getObject("WTF_FACILITY_LONGITUDE")), //
                               rs.getString("WTF_FACILITY_MUNICIPALITY_CODE"), //
                               rs.getString("WTF_DISCHARGE_TYPE"), //
                               Utils.getDouble(rs.getObject("WTF_DISCHARGE_LATITUDE")), //
                               Utils.getDouble(rs.getObject("WTF_DISCHARGE_LONGITUDE")), //
                               Utils.getDouble(rs.getObject("WTF_AD_ID")), //
                               Utils.getDouble(rs.getObject("WTF_AGG_ID")), //
                               rs.getString("WTF_WAITING_UPDATE_CONFIRMATION"), //
                               Utils.getDouble(rs.getObject("WTF_NC_AD_ID")), //
                               Utils.getDouble(rs.getObject("WTF_NC_AGG_ID")), //
                               rs.getString("WTF_NC_MANUFACTURER"), //
                               rs.getString("WTF_NC_MANUFACTURER_DESCRIPTION"), //
                               rs.getString("WTF_NC_MODEL"), //
                               rs.getString("WTF_NC_TYPE"), //
                               rs.getString("WTF_NC_CAPACITY"), //
                               Utils.getDouble(rs.getObject("WTF_NC_DISTANCE")), //
                               rs.getString("WTF_NC_TECHNICAL_PASSPORT_ID"), //
                               rs.getString("WTF_NC_DATA_SOURCE"), //
                               rs.getTimestamp("WTF_NC_INSTALLATION_DATE"), //
                               rs.getTimestamp("WTF_NC_CHECKOUT_DATE"), //
                               rs.getString("WTF_NC_DELETED"), //
                               Utils.getDouble(rs.getObject("WTF_NC_FACILITY_LATITUDE")), //
                               Utils.getDouble(rs.getObject("WTF_NC_FACILITY_LONGITUDE")), //
                               rs.getString("WTF_NC_FACILITY_MUNICIPALITY_CODE"), //
                               rs.getString("WTF_NC_DISCHARGE_TYPE"), //
                               Utils.getDouble(rs.getObject("WTF_NC_DISCHARGE_LATITUDE")), //
                               Utils.getDouble(rs.getObject("WTF_NC_DISCHARGE_LONGITUDE")), //
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
                               Utils.getDouble(rs.getObject("WTF_FAM_ID")), //
                               Utils.getDouble(rs.getObject("WTF_NC_FAM_POP_EQUIVALENT")), //
                               rs.getString("WTF_NC_FAM_TECH_PASS"), //
                               Utils.getDouble(rs.getObject("WTF_NC_FAM_CHDS")), //
                               Utils.getDouble(rs.getObject("WTF_NC_FAM_BDS")), //
                               Utils.getDouble(rs.getObject("WTF_NC_FAM_FLOAT_MATERIAL")), //
                               Utils.getDouble(rs.getObject("WTF_NC_FAM_PHOSPHOR")), //
                               Utils.getDouble(rs.getObject("WTF_NC_FAM_NITROGEN")), //
                               rs.getString("WTF_NC_FAM_MANUFACTURER"), //
                               rs.getString("WTF_NC_FAM_MODEL"), //
                               rs.getString("WTF_NC_FAM_DESCRIPTION")));
         }
      return data;
   }

   @Override
   public NtisWastewaterTreatmentFaciDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE WTF_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisWastewaterTreatmentFaciDAO> data = null;
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
      NtisWastewaterTreatmentFaciDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisWastewaterTreatmentFaciDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_WASTEWATER_TREATMENT_FACI WHERE WTF_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisWastewaterTreatmentFaciDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getWtf_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisWastewaterTreatmentFaciDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisWastewaterTreatmentFaciDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisWastewaterTreatmentFaciDAO insertRecord(Connection conn, NtisWastewaterTreatmentFaciDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_WASTEWATER_TREATMENT_FACI (WTF_MANUFACTURER, WTF_MANUFACTURER_DESCRIPTION, WTF_MODEL, WTF_TYPE, WTF_CAPACITY, WTF_DISTANCE, WTF_TECHNICAL_PASSPORT_ID, WTF_STATE, WTF_DATA_SOURCE, WTF_INSTALLATION_DATE, WTF_CHECKOUT_DATE, WTF_DELETED, WTF_FACILITY_LATITUDE, WTF_FACILITY_LONGITUDE, WTF_FACILITY_MUNICIPALITY_CODE, WTF_DISCHARGE_TYPE, WTF_DISCHARGE_LATITUDE, WTF_DISCHARGE_LONGITUDE, WTF_AD_ID, WTF_AGG_ID, WTF_WAITING_UPDATE_CONFIRMATION, WTF_NC_AD_ID, WTF_NC_AGG_ID, WTF_NC_MANUFACTURER, WTF_NC_MANUFACTURER_DESCRIPTION, WTF_NC_MODEL, WTF_NC_TYPE, WTF_NC_CAPACITY, WTF_NC_DISTANCE, WTF_NC_TECHNICAL_PASSPORT_ID, WTF_NC_DATA_SOURCE, WTF_NC_INSTALLATION_DATE, WTF_NC_CHECKOUT_DATE, WTF_NC_DELETED, WTF_NC_FACILITY_LATITUDE, WTF_NC_FACILITY_LONGITUDE, WTF_NC_FACILITY_MUNICIPALITY_CODE, WTF_NC_DISCHARGE_TYPE, WTF_NC_DISCHARGE_LATITUDE, WTF_NC_DISCHARGE_LONGITUDE, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, WTF_FAM_ID, WTF_NC_FAM_POP_EQUIVALENT, WTF_NC_FAM_TECH_PASS, WTF_NC_FAM_CHDS, WTF_NC_FAM_BDS, WTF_NC_FAM_FLOAT_MATERIAL, WTF_NC_FAM_PHOSPHOR, WTF_NC_FAM_NITROGEN, WTF_NC_FAM_MANUFACTURER, WTF_NC_FAM_MODEL, WTF_NC_FAM_DESCRIPTION, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING WTF_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getWtf_manufacturer());
         pstmt.setString(2, daoObject.getWtf_manufacturer_description());
         pstmt.setString(3, daoObject.getWtf_model());
         pstmt.setString(4, daoObject.getWtf_type());
         pstmt.setString(5, daoObject.getWtf_capacity());
         pstmt.setObject(6, daoObject.getWtf_distance());
         pstmt.setString(7, daoObject.getWtf_technical_passport_id());
         pstmt.setString(8, daoObject.getWtf_state());
         pstmt.setString(9, daoObject.getWtf_data_source());
         pstmt.setObject(10,  Utils.getSqlTimestamp(daoObject.getWtf_installation_date()));
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getWtf_checkout_date()));
         pstmt.setString(12, daoObject.getWtf_deleted());
         pstmt.setObject(13, daoObject.getWtf_facility_latitude());
         pstmt.setObject(14, daoObject.getWtf_facility_longitude());
         pstmt.setString(15, daoObject.getWtf_facility_municipality_code());
         pstmt.setString(16, daoObject.getWtf_discharge_type());
         pstmt.setObject(17, daoObject.getWtf_discharge_latitude());
         pstmt.setObject(18, daoObject.getWtf_discharge_longitude());
         pstmt.setObject(19, daoObject.getWtf_ad_id());
         pstmt.setObject(20, daoObject.getWtf_agg_id());
         pstmt.setString(21, daoObject.getWtf_waiting_update_confirmation());
         pstmt.setObject(22, daoObject.getWtf_nc_ad_id());
         pstmt.setObject(23, daoObject.getWtf_nc_agg_id());
         pstmt.setString(24, daoObject.getWtf_nc_manufacturer());
         pstmt.setString(25, daoObject.getWtf_nc_manufacturer_description());
         pstmt.setString(26, daoObject.getWtf_nc_model());
         pstmt.setString(27, daoObject.getWtf_nc_type());
         pstmt.setString(28, daoObject.getWtf_nc_capacity());
         pstmt.setObject(29, daoObject.getWtf_nc_distance());
         pstmt.setString(30, daoObject.getWtf_nc_technical_passport_id());
         pstmt.setString(31, daoObject.getWtf_nc_data_source());
         pstmt.setObject(32,  Utils.getSqlTimestamp(daoObject.getWtf_nc_installation_date()));
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getWtf_nc_checkout_date()));
         pstmt.setString(34, daoObject.getWtf_nc_deleted());
         pstmt.setObject(35, daoObject.getWtf_nc_facility_latitude());
         pstmt.setObject(36, daoObject.getWtf_nc_facility_longitude());
         pstmt.setString(37, daoObject.getWtf_nc_facility_municipality_code());
         pstmt.setString(38, daoObject.getWtf_nc_discharge_type());
         pstmt.setObject(39, daoObject.getWtf_nc_discharge_latitude());
         pstmt.setObject(40, daoObject.getWtf_nc_discharge_longitude());
         pstmt.setObject(41, daoObject.getN01());
         pstmt.setObject(42, daoObject.getN02());
         pstmt.setObject(43, daoObject.getN03());
         pstmt.setObject(44, daoObject.getN04());
         pstmt.setObject(45, daoObject.getN05());
         pstmt.setString(46, daoObject.getC01());
         pstmt.setString(47, daoObject.getC02());
         pstmt.setString(48, daoObject.getC03());
         pstmt.setString(49, daoObject.getC04());
         pstmt.setString(50, daoObject.getC05());
         pstmt.setObject(51,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(52,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(53,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(54,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(55,  Utils.getSqlTimestamp(daoObject.getD05()));
         pstmt.setObject(56, daoObject.getWtf_fam_id());
         pstmt.setObject(57, daoObject.getWtf_nc_fam_pop_equivalent());
         pstmt.setString(58, daoObject.getWtf_nc_fam_tech_pass());
         pstmt.setObject(59, daoObject.getWtf_nc_fam_chds());
         pstmt.setObject(60, daoObject.getWtf_nc_fam_bds());
         pstmt.setObject(61, daoObject.getWtf_nc_fam_float_material());
         pstmt.setObject(62, daoObject.getWtf_nc_fam_phosphor());
         pstmt.setObject(63, daoObject.getWtf_nc_fam_nitrogen());
         pstmt.setString(64, daoObject.getWtf_nc_fam_manufacturer());
         pstmt.setString(65, daoObject.getWtf_nc_fam_model());
         pstmt.setString(66, daoObject.getWtf_nc_fam_description());
         //Record audit information (start)
         pstmt.setInt(67, 1);
         pstmt.setObject(68,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(69,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(70, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setWtf_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisWastewaterTreatmentFaciDAO updateRecord(Connection conn, NtisWastewaterTreatmentFaciDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisWastewaterTreatmentFaciDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisWastewaterTreatmentFaciDAO saveRecord(Connection conn, NtisWastewaterTreatmentFaciDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}