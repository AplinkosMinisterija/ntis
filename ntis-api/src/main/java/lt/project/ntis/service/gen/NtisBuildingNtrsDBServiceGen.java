package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisBuildingNtrsDAO;
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

public class NtisBuildingNtrsDBServiceGen extends SprBaseDBServiceImpl<NtisBuildingNtrsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisBuildingNtrsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT BN_ID, BN_AOB_CODE, BN_STATUS, BN_STATUS_DESC, BN_OBJ_INV_CODE, BN_REG_NR, BN_REG_DATE, BN_ADR_ID, BN_MUNICIPALITY_CODE, BN_MUNICIPALITY, BN_SEN_CODE, BN_SEN_NAME, BN_PLACE_CODE, BN_PLACE_NAME, BN_STREET_CODE, BN_STREET, BN_HOUSE_NR, BN_HOUSING_NR, BN_FLAT_NR, BN_COORDINATE_LATITUDE_NTR, BN_COORDINATE_LONGITUDE_NTR, BN_COORDINATE_LATITUDE_ADR, BN_COORDINATE_LONGITUDE_ADR, BN_OBJ_INV_PARENT_CODE, BN_OBJECT_INV_DATE, BN_OBJECT_TYPE, BN_OBJECT_NAME, BN_PASK_TYPE, BN_PASK_NAME, BN_CONSTRUCTION_START_YEAR, BN_CONSTRUCTION_END_YEAR, BN_CONSTRUCTION_COMPLETION, BN_TOTAL_AREA, BN_USEABLE_AREA, BN_LIVING_AREA, BN_WASTEWATER_TREATMENT, BN_WATER_SUPPLY, BN_DECLR_TYPE, BN_LIVE_COUNT, BN_AD_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, BN_DATE_FROM, BN_DATE_TO FROM NTIS_BUILDING_NTRS ";

   @Override
   public NtisBuildingNtrsDAO newRecord() {
         NtisBuildingNtrsDAO daoObject = new NtisBuildingNtrsDAO();
         return daoObject;
     }
   @Override
   public NtisBuildingNtrsDAO object4ForceUpdate() {
       NtisBuildingNtrsDAO daoObject = new NtisBuildingNtrsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisBuildingNtrsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisBuildingNtrsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisBuildingNtrsDAO> data = new ArrayList<NtisBuildingNtrsDAO>();
      while (rs.next()) {
         data.add(new NtisBuildingNtrsDAO(Utils.getDouble(rs.getObject("BN_ID")), //
                               Utils.getDouble(rs.getObject("BN_AOB_CODE")), //
                               rs.getString("BN_STATUS"), //
                               rs.getString("BN_STATUS_DESC"), //
                               rs.getString("BN_OBJ_INV_CODE"), //
                               rs.getString("BN_REG_NR"), //
                               rs.getTimestamp("BN_REG_DATE"), //
                               Utils.getDouble(rs.getObject("BN_ADR_ID")), //
                               rs.getString("BN_MUNICIPALITY_CODE"), //
                               rs.getString("BN_MUNICIPALITY"), //
                               rs.getString("BN_SEN_CODE"), //
                               rs.getString("BN_SEN_NAME"), //
                               rs.getString("BN_PLACE_CODE"), //
                               rs.getString("BN_PLACE_NAME"), //
                               rs.getString("BN_STREET_CODE"), //
                               rs.getString("BN_STREET"), //
                               rs.getString("BN_HOUSE_NR"), //
                               rs.getString("BN_HOUSING_NR"), //
                               rs.getString("BN_FLAT_NR"), //
                               Utils.getDouble(rs.getObject("BN_COORDINATE_LATITUDE_NTR")), //
                               Utils.getDouble(rs.getObject("BN_COORDINATE_LONGITUDE_NTR")), //
                               Utils.getDouble(rs.getObject("BN_COORDINATE_LATITUDE_ADR")), //
                               Utils.getDouble(rs.getObject("BN_COORDINATE_LONGITUDE_ADR")), //
                               rs.getString("BN_OBJ_INV_PARENT_CODE"), //
                               rs.getTimestamp("BN_OBJECT_INV_DATE"), //
                               Utils.getDouble(rs.getObject("BN_OBJECT_TYPE")), //
                               rs.getString("BN_OBJECT_NAME"), //
                               Utils.getDouble(rs.getObject("BN_PASK_TYPE")), //
                               rs.getString("BN_PASK_NAME"), //
                               Utils.getDouble(rs.getObject("BN_CONSTRUCTION_START_YEAR")), //
                               Utils.getDouble(rs.getObject("BN_CONSTRUCTION_END_YEAR")), //
                               Utils.getDouble(rs.getObject("BN_CONSTRUCTION_COMPLETION")), //
                               Utils.getDouble(rs.getObject("BN_TOTAL_AREA")), //
                               Utils.getDouble(rs.getObject("BN_USEABLE_AREA")), //
                               Utils.getDouble(rs.getObject("BN_LIVING_AREA")), //
                               rs.getString("BN_WASTEWATER_TREATMENT"), //
                               rs.getString("BN_WATER_SUPPLY"), //
                               rs.getString("BN_DECLR_TYPE"), //
                               Utils.getDouble(rs.getObject("BN_LIVE_COUNT")), //
                               Utils.getDouble(rs.getObject("BN_AD_ID")), //
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
                               rs.getTimestamp("BN_DATE_FROM"), //
                               rs.getTimestamp("BN_DATE_TO")));
         }
      return data;
   }

   @Override
   public NtisBuildingNtrsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE BN_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisBuildingNtrsDAO> data = null;
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
      NtisBuildingNtrsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisBuildingNtrsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_BUILDING_NTRS WHERE BN_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisBuildingNtrsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getBn_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisBuildingNtrsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_BUILDING_NTRS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisBuildingNtrsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisBuildingNtrsDAO insertRecord(Connection conn, NtisBuildingNtrsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_BUILDING_NTRS (BN_AOB_CODE, BN_STATUS, BN_STATUS_DESC, BN_OBJ_INV_CODE, BN_REG_NR, BN_REG_DATE, BN_ADR_ID, BN_MUNICIPALITY_CODE, BN_MUNICIPALITY, BN_SEN_CODE, BN_SEN_NAME, BN_PLACE_CODE, BN_PLACE_NAME, BN_STREET_CODE, BN_STREET, BN_HOUSE_NR, BN_HOUSING_NR, BN_FLAT_NR, BN_COORDINATE_LATITUDE_NTR, BN_COORDINATE_LONGITUDE_NTR, BN_COORDINATE_LATITUDE_ADR, BN_COORDINATE_LONGITUDE_ADR, BN_OBJ_INV_PARENT_CODE, BN_OBJECT_INV_DATE, BN_OBJECT_TYPE, BN_OBJECT_NAME, BN_PASK_TYPE, BN_PASK_NAME, BN_CONSTRUCTION_START_YEAR, BN_CONSTRUCTION_END_YEAR, BN_CONSTRUCTION_COMPLETION, BN_TOTAL_AREA, BN_USEABLE_AREA, BN_LIVING_AREA, BN_WASTEWATER_TREATMENT, BN_WATER_SUPPLY, BN_DECLR_TYPE, BN_LIVE_COUNT, BN_AD_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, BN_DATE_FROM, BN_DATE_TO, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING BN_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getBn_aob_code());
         pstmt.setString(2, daoObject.getBn_status());
         pstmt.setString(3, daoObject.getBn_status_desc());
         pstmt.setString(4, daoObject.getBn_obj_inv_code());
         pstmt.setString(5, daoObject.getBn_reg_nr());
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getBn_reg_date()));
         pstmt.setObject(7, daoObject.getBn_adr_id());
         pstmt.setString(8, daoObject.getBn_municipality_code());
         pstmt.setString(9, daoObject.getBn_municipality());
         pstmt.setString(10, daoObject.getBn_sen_code());
         pstmt.setString(11, daoObject.getBn_sen_name());
         pstmt.setString(12, daoObject.getBn_place_code());
         pstmt.setString(13, daoObject.getBn_place_name());
         pstmt.setString(14, daoObject.getBn_street_code());
         pstmt.setString(15, daoObject.getBn_street());
         pstmt.setString(16, daoObject.getBn_house_nr());
         pstmt.setString(17, daoObject.getBn_housing_nr());
         pstmt.setString(18, daoObject.getBn_flat_nr());
         pstmt.setObject(19, daoObject.getBn_coordinate_latitude_ntr());
         pstmt.setObject(20, daoObject.getBn_coordinate_longitude_ntr());
         pstmt.setObject(21, daoObject.getBn_coordinate_latitude_adr());
         pstmt.setObject(22, daoObject.getBn_coordinate_longitude_adr());
         pstmt.setString(23, daoObject.getBn_obj_inv_parent_code());
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getBn_object_inv_date()));
         pstmt.setObject(25, daoObject.getBn_object_type());
         pstmt.setString(26, daoObject.getBn_object_name());
         pstmt.setObject(27, daoObject.getBn_pask_type());
         pstmt.setString(28, daoObject.getBn_pask_name());
         pstmt.setObject(29, daoObject.getBn_construction_start_year());
         pstmt.setObject(30, daoObject.getBn_construction_end_year());
         pstmt.setObject(31, daoObject.getBn_construction_completion());
         pstmt.setObject(32, daoObject.getBn_total_area());
         pstmt.setObject(33, daoObject.getBn_useable_area());
         pstmt.setObject(34, daoObject.getBn_living_area());
         pstmt.setString(35, daoObject.getBn_wastewater_treatment());
         pstmt.setString(36, daoObject.getBn_water_supply());
         pstmt.setString(37, daoObject.getBn_declr_type());
         pstmt.setObject(38, daoObject.getBn_live_count());
         pstmt.setObject(39, daoObject.getBn_ad_id());
         pstmt.setObject(40, daoObject.getN01());
         pstmt.setObject(41, daoObject.getN02());
         pstmt.setObject(42, daoObject.getN03());
         pstmt.setObject(43, daoObject.getN04());
         pstmt.setObject(44, daoObject.getN05());
         pstmt.setString(45, daoObject.getC01());
         pstmt.setString(46, daoObject.getC02());
         pstmt.setString(47, daoObject.getC03());
         pstmt.setString(48, daoObject.getC04());
         pstmt.setString(49, daoObject.getC05());
         pstmt.setObject(50,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(51,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(52,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(53,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(54,  Utils.getSqlTimestamp(daoObject.getD05()));
         pstmt.setObject(55,  Utils.getSqlTimestamp(daoObject.getBn_date_from()));
         pstmt.setObject(56,  Utils.getSqlTimestamp(daoObject.getBn_date_to()));
         //Record audit information (start)
         pstmt.setInt(57, 1);
         pstmt.setObject(58,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(59,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(60, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setBn_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisBuildingNtrsDAO updateRecord(Connection conn, NtisBuildingNtrsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisBuildingNtrsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisBuildingNtrsDAO saveRecord(Connection conn, NtisBuildingNtrsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}