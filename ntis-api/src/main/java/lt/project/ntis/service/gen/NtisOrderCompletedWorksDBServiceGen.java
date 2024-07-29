package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisOrderCompletedWorksDAO;
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

public class NtisOrderCompletedWorksDBServiceGen extends SprBaseDBServiceImpl<NtisOrderCompletedWorksDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisOrderCompletedWorksDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT OCW_ID, OCW_COMPLETED_WORKS_DESCRIPTION, OCW_DISCHARGED_SLUDGE_AMOUNT, OCW_COMPLETED_DATE, OCW_SMP_PERSON, OCW_RSR_PERSON, OCW_RES_PERSON, OCW_USR_ID, OCW_ORD_ID, OCW_CR_ID, OCW_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_ORDER_COMPLETED_WORKS ";

   @Override
   public NtisOrderCompletedWorksDAO newRecord() {
	  	  NtisOrderCompletedWorksDAO daoObject = new NtisOrderCompletedWorksDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisOrderCompletedWorksDAO object4ForceUpdate() {
       NtisOrderCompletedWorksDAO daoObject = new NtisOrderCompletedWorksDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisOrderCompletedWorksDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisOrderCompletedWorksDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisOrderCompletedWorksDAO> data = new ArrayList<NtisOrderCompletedWorksDAO>();
      while (rs.next()) {
         data.add(new NtisOrderCompletedWorksDAO(Utils.getDouble(rs.getObject("OCW_ID")), //
                               rs.getString("OCW_COMPLETED_WORKS_DESCRIPTION"), //
                               Utils.getDouble(rs.getObject("OCW_DISCHARGED_SLUDGE_AMOUNT")), //
                               rs.getTimestamp("OCW_COMPLETED_DATE"), //
                               rs.getString("OCW_SMP_PERSON"), //
                               rs.getString("OCW_RSR_PERSON"), //
                               rs.getString("OCW_RES_PERSON"), //
                               Utils.getDouble(rs.getObject("OCW_USR_ID")), //
                               Utils.getDouble(rs.getObject("OCW_ORD_ID")), //
                               Utils.getDouble(rs.getObject("OCW_CR_ID")), //
                               Utils.getDouble(rs.getObject("OCW_FIL_ID")), //
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
   public NtisOrderCompletedWorksDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE OCW_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisOrderCompletedWorksDAO> data = null;
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
      NtisOrderCompletedWorksDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisOrderCompletedWorksDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_ORDER_COMPLETED_WORKS WHERE OCW_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisOrderCompletedWorksDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getOcw_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisOrderCompletedWorksDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_ORDER_COMPLETED_WORKS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisOrderCompletedWorksDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisOrderCompletedWorksDAO insertRecord(Connection conn, NtisOrderCompletedWorksDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_ORDER_COMPLETED_WORKS (OCW_COMPLETED_WORKS_DESCRIPTION, OCW_DISCHARGED_SLUDGE_AMOUNT, OCW_COMPLETED_DATE, OCW_SMP_PERSON, OCW_RSR_PERSON, OCW_RES_PERSON, OCW_USR_ID, OCW_ORD_ID, OCW_CR_ID, OCW_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING OCW_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getOcw_completed_works_description());
         pstmt.setObject(2, daoObject.getOcw_discharged_sludge_amount());
         pstmt.setObject(3,  Utils.getSqlTimestamp(daoObject.getOcw_completed_date()));
         pstmt.setString(4, daoObject.getOcw_smp_person());
         pstmt.setString(5, daoObject.getOcw_rsr_person());
         pstmt.setString(6, daoObject.getOcw_res_person());
         pstmt.setObject(7, daoObject.getOcw_usr_id());
         pstmt.setObject(8, daoObject.getOcw_ord_id());
         pstmt.setObject(9, daoObject.getOcw_cr_id());
         pstmt.setObject(10, daoObject.getOcw_fil_id());
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
            daoObject.setOcw_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisOrderCompletedWorksDAO updateRecord(Connection conn, NtisOrderCompletedWorksDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisOrderCompletedWorksDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisOrderCompletedWorksDAO saveRecord(Connection conn, NtisOrderCompletedWorksDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}