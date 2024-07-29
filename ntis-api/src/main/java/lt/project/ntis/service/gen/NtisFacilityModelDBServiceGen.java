package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisFacilityModelDAO;
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

public class NtisFacilityModelDBServiceGen extends SprBaseDBServiceImpl<NtisFacilityModelDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisFacilityModelDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT FAM_ID, FAM_RFC_ID, FAM_POP_EQUIVALENT, FAM_TECH_PASS, FAM_FIL_ID, FAM_CHDS, FAM_BDS, FAM_FLOAT_MATERIAL, FAM_PHOSPHOR, FAM_NITROGEN, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, FAM_MANUFACTURER, FAM_MODEL, FAM_DESCRIPTION FROM NTIS_FACILITY_MODEL ";

   @Override
   public NtisFacilityModelDAO newRecord() {
         NtisFacilityModelDAO daoObject = new NtisFacilityModelDAO();
         return daoObject;
     }
   @Override
   public NtisFacilityModelDAO object4ForceUpdate() {
       NtisFacilityModelDAO daoObject = new NtisFacilityModelDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisFacilityModelDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisFacilityModelDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisFacilityModelDAO> data = new ArrayList<NtisFacilityModelDAO>();
      while (rs.next()) {
         data.add(new NtisFacilityModelDAO(Utils.getDouble(rs.getObject("FAM_ID")), //
                               Utils.getDouble(rs.getObject("FAM_RFC_ID")), //
                               Utils.getDouble(rs.getObject("FAM_POP_EQUIVALENT")), //
                               rs.getString("FAM_TECH_PASS"), //
                               Utils.getDouble(rs.getObject("FAM_FIL_ID")), //
                               Utils.getDouble(rs.getObject("FAM_CHDS")), //
                               Utils.getDouble(rs.getObject("FAM_BDS")), //
                               Utils.getDouble(rs.getObject("FAM_FLOAT_MATERIAL")), //
                               Utils.getDouble(rs.getObject("FAM_PHOSPHOR")), //
                               Utils.getDouble(rs.getObject("FAM_NITROGEN")), //
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
                               rs.getString("FAM_MANUFACTURER"), //
                               rs.getString("FAM_MODEL"), //
                               rs.getString("FAM_DESCRIPTION")));
         }
      return data;
   }

   @Override
   public NtisFacilityModelDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE FAM_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisFacilityModelDAO> data = null;
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
      NtisFacilityModelDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisFacilityModelDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_FACILITY_MODEL WHERE FAM_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisFacilityModelDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getFam_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisFacilityModelDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_FACILITY_MODEL",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisFacilityModelDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisFacilityModelDAO insertRecord(Connection conn, NtisFacilityModelDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_FACILITY_MODEL (FAM_RFC_ID, FAM_POP_EQUIVALENT, FAM_TECH_PASS, FAM_FIL_ID, FAM_CHDS, FAM_BDS, FAM_FLOAT_MATERIAL, FAM_PHOSPHOR, FAM_NITROGEN, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, FAM_MANUFACTURER, FAM_MODEL, FAM_DESCRIPTION, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING FAM_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getFam_rfc_id());
         pstmt.setObject(2, daoObject.getFam_pop_equivalent());
         pstmt.setString(3, daoObject.getFam_tech_pass());
         pstmt.setObject(4, daoObject.getFam_fil_id());
         pstmt.setObject(5, daoObject.getFam_chds());
         pstmt.setObject(6, daoObject.getFam_bds());
         pstmt.setObject(7, daoObject.getFam_float_material());
         pstmt.setObject(8, daoObject.getFam_phosphor());
         pstmt.setObject(9, daoObject.getFam_nitrogen());
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
         pstmt.setString(25, daoObject.getFam_manufacturer());
         pstmt.setString(26, daoObject.getFam_model());
         pstmt.setString(27, daoObject.getFam_description());
         //Record audit information (start)
         pstmt.setInt(28, 1);
         pstmt.setObject(29,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(30,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(31, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setFam_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisFacilityModelDAO updateRecord(Connection conn, NtisFacilityModelDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisFacilityModelDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisFacilityModelDAO saveRecord(Connection conn, NtisFacilityModelDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}