package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisAgglomerationVersionsDAO;
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

public class NtisAgglomerationVersionsDBServiceGen extends SprBaseDBServiceImpl<NtisAgglomerationVersionsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisAgglomerationVersionsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT AV_ID, AV_STATUS, AV_CREATED, AV_AGG_ID, AV_FIL_ID, AV_PER_ID, AV_ADMIN_REVIEW, AV_ADMIN_REVIEW_DATE, AV_ADMIN_REVIEW_PER_ID, AV_ADMIN_REVIEW_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_AGGLOMERATION_VERSIONS ";

   @Override
   public NtisAgglomerationVersionsDAO newRecord() {
	  	  NtisAgglomerationVersionsDAO daoObject = new NtisAgglomerationVersionsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisAgglomerationVersionsDAO object4ForceUpdate() {
       NtisAgglomerationVersionsDAO daoObject = new NtisAgglomerationVersionsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisAgglomerationVersionsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisAgglomerationVersionsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisAgglomerationVersionsDAO> data = new ArrayList<NtisAgglomerationVersionsDAO>();
      while (rs.next()) {
         data.add(new NtisAgglomerationVersionsDAO(Utils.getDouble(rs.getObject("AV_ID")), //
                               rs.getString("AV_STATUS"), //
                               rs.getTimestamp("AV_CREATED"), //
                               Utils.getDouble(rs.getObject("AV_AGG_ID")), //
                               Utils.getDouble(rs.getObject("AV_FIL_ID")), //
                               Utils.getDouble(rs.getObject("AV_PER_ID")), //
                               rs.getString("AV_ADMIN_REVIEW"), //
                               rs.getTimestamp("AV_ADMIN_REVIEW_DATE"), //
                               Utils.getDouble(rs.getObject("AV_ADMIN_REVIEW_PER_ID")), //
                               Utils.getDouble(rs.getObject("AV_ADMIN_REVIEW_FIL_ID")), //
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
   public NtisAgglomerationVersionsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE AV_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisAgglomerationVersionsDAO> data = null;
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
      NtisAgglomerationVersionsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisAgglomerationVersionsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_AGGLOMERATION_VERSIONS WHERE AV_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisAgglomerationVersionsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getAv_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisAgglomerationVersionsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_AGGLOMERATION_VERSIONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisAgglomerationVersionsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisAgglomerationVersionsDAO insertRecord(Connection conn, NtisAgglomerationVersionsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_AGGLOMERATION_VERSIONS (AV_STATUS, AV_CREATED, AV_AGG_ID, AV_FIL_ID, AV_PER_ID, AV_ADMIN_REVIEW, AV_ADMIN_REVIEW_DATE, AV_ADMIN_REVIEW_PER_ID, AV_ADMIN_REVIEW_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING AV_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getAv_status());
         pstmt.setObject(2,  Utils.getSqlTimestamp(daoObject.getAv_created()));
         pstmt.setObject(3, daoObject.getAv_agg_id());
         pstmt.setObject(4, daoObject.getAv_fil_id());
         pstmt.setObject(5, daoObject.getAv_per_id());
         pstmt.setString(6, daoObject.getAv_admin_review());
         pstmt.setObject(7,  Utils.getSqlTimestamp(daoObject.getAv_admin_review_date()));
         pstmt.setObject(8, daoObject.getAv_admin_review_per_id());
         pstmt.setObject(9, daoObject.getAv_admin_review_fil_id());
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
         //Record audit information (start)
         pstmt.setInt(25, 1);
         pstmt.setObject(26,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(27,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(28, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setAv_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisAgglomerationVersionsDAO updateRecord(Connection conn, NtisAgglomerationVersionsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisAgglomerationVersionsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisAgglomerationVersionsDAO saveRecord(Connection conn, NtisAgglomerationVersionsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}