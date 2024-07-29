package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisReviewsDAO;
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

public class NtisReviewsDBServiceGen extends SprBaseDBServiceImpl<NtisReviewsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisReviewsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT REV_ID, REV_COMMENT, REV_SCORE, REV_USR_ID, REV_ORD_ID, REV_WD_ID, REV_PASL_ORG_ID, REV_VAND_ORG_ID, REV_COMPLETED_DATE, REV_RECEIVER_READ, REV_ADMIN_READ, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_REVIEWS ";

   @Override
   public NtisReviewsDAO newRecord() {
         NtisReviewsDAO daoObject = new NtisReviewsDAO();
         return daoObject;
     }
   @Override
   public NtisReviewsDAO object4ForceUpdate() {
       NtisReviewsDAO daoObject = new NtisReviewsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisReviewsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisReviewsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisReviewsDAO> data = new ArrayList<NtisReviewsDAO>();
      while (rs.next()) {
         data.add(new NtisReviewsDAO(Utils.getDouble(rs.getObject("REV_ID")), //
                               rs.getString("REV_COMMENT"), //
                               Utils.getDouble(rs.getObject("REV_SCORE")), //
                               Utils.getDouble(rs.getObject("REV_USR_ID")), //
                               Utils.getDouble(rs.getObject("REV_ORD_ID")), //
                               Utils.getDouble(rs.getObject("REV_WD_ID")), //
                               Utils.getDouble(rs.getObject("REV_PASL_ORG_ID")), //
                               Utils.getDouble(rs.getObject("REV_VAND_ORG_ID")), //
                               rs.getTimestamp("REV_COMPLETED_DATE"), //
                               rs.getString("REV_RECEIVER_READ"), //
                               rs.getString("REV_ADMIN_READ"), //
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
   public NtisReviewsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE REV_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisReviewsDAO> data = null;
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
      NtisReviewsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisReviewsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_REVIEWS WHERE REV_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisReviewsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getRev_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisReviewsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_REVIEWS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisReviewsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisReviewsDAO insertRecord(Connection conn, NtisReviewsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_REVIEWS (REV_COMMENT, REV_SCORE, REV_USR_ID, REV_ORD_ID, REV_WD_ID, REV_PASL_ORG_ID, REV_VAND_ORG_ID, REV_COMPLETED_DATE, REV_RECEIVER_READ, REV_ADMIN_READ, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING REV_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getRev_comment());
         pstmt.setObject(2, daoObject.getRev_score());
         pstmt.setObject(3, daoObject.getRev_usr_id());
         pstmt.setObject(4, daoObject.getRev_ord_id());
         pstmt.setObject(5, daoObject.getRev_wd_id());
         pstmt.setObject(6, daoObject.getRev_pasl_org_id());
         pstmt.setObject(7, daoObject.getRev_vand_org_id());
         pstmt.setObject(8,  Utils.getSqlTimestamp(daoObject.getRev_completed_date()));
         pstmt.setString(9, daoObject.getRev_receiver_read());
         pstmt.setString(10, daoObject.getRev_admin_read());
         pstmt.setObject(11, daoObject.getN01());
         pstmt.setObject(12, daoObject.getN02());
         pstmt.setObject(13, daoObject.getN03());
         pstmt.setObject(14, daoObject.getN04());
         pstmt.setObject(15, daoObject.getN05());
         pstmt.setString(16, daoObject.getC01());
         pstmt.setString(17, daoObject.getC02());
         pstmt.setString(18, daoObject.getC03());
         pstmt.setString(19, daoObject.getC04());
         pstmt.setString(20, daoObject.getC05());
         pstmt.setObject(21,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(25,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(26, 1);
         pstmt.setObject(27,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(28,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(29, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setRev_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisReviewsDAO updateRecord(Connection conn, NtisReviewsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisReviewsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisReviewsDAO saveRecord(Connection conn, NtisReviewsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}