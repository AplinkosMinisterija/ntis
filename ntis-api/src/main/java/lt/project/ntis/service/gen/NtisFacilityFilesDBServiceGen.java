package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisFacilityFilesDAO;
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

public class NtisFacilityFilesDBServiceGen extends SprBaseDBServiceImpl<NtisFacilityFilesDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisFacilityFilesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT FF_ID, FF_FIL_ID, FF_WTF_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_FACILITY_FILES ";

   @Override
   public NtisFacilityFilesDAO newRecord() {
	  	  NtisFacilityFilesDAO daoObject = new NtisFacilityFilesDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisFacilityFilesDAO object4ForceUpdate() {
       NtisFacilityFilesDAO daoObject = new NtisFacilityFilesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisFacilityFilesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisFacilityFilesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisFacilityFilesDAO> data = new ArrayList<NtisFacilityFilesDAO>();
      while (rs.next()) {
         data.add(new NtisFacilityFilesDAO(Utils.getDouble(rs.getObject("FF_ID")), //
                               Utils.getDouble(rs.getObject("FF_FIL_ID")), //
                               Utils.getDouble(rs.getObject("FF_WTF_ID")), //
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
   public NtisFacilityFilesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE FF_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisFacilityFilesDAO> data = null;
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
      NtisFacilityFilesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisFacilityFilesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_FACILITY_FILES WHERE FF_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisFacilityFilesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getFf_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisFacilityFilesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_FACILITY_FILES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisFacilityFilesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisFacilityFilesDAO insertRecord(Connection conn, NtisFacilityFilesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_FACILITY_FILES (FF_FIL_ID, FF_WTF_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING FF_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getFf_fil_id());
         pstmt.setObject(2, daoObject.getFf_wtf_id());
         pstmt.setObject(3, daoObject.getN01());
         pstmt.setObject(4, daoObject.getN02());
         pstmt.setObject(5, daoObject.getN03());
         pstmt.setObject(6, daoObject.getN04());
         pstmt.setObject(7, daoObject.getN05());
         pstmt.setString(8, daoObject.getC01());
         pstmt.setString(9, daoObject.getC02());
         pstmt.setString(10, daoObject.getC03());
         pstmt.setString(11, daoObject.getC04());
         pstmt.setString(12, daoObject.getC05());
         pstmt.setObject(13,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(14,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(15,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(16,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(17,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(18, 1);
         pstmt.setObject(19,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(20,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(21, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setFf_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisFacilityFilesDAO updateRecord(Connection conn, NtisFacilityFilesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisFacilityFilesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisFacilityFilesDAO saveRecord(Connection conn, NtisFacilityFilesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}