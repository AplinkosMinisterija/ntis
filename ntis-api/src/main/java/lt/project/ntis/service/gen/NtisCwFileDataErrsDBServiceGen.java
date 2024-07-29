package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisCwFileDataErrsDAO;
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

public class NtisCwFileDataErrsDBServiceGen extends SprBaseDBServiceImpl<NtisCwFileDataErrsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisCwFileDataErrsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT CWFDE_ID, CWFDE_TYPE, CWFDE_LEVEL, CWFDE_REC_NR, CWFDE_COLUMN_NR, CWFDE_COLUMN_NAME, CWFDE_COLUMN_VALUE, CWFDE_MSG_CODE, CWFDE_MSG_TEXT, CWFDE_CWF_ID, CWFDE_CWFD_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_CW_FILE_DATA_ERRS ";

   @Override
   public NtisCwFileDataErrsDAO newRecord() {
	  	  NtisCwFileDataErrsDAO daoObject = new NtisCwFileDataErrsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisCwFileDataErrsDAO object4ForceUpdate() {
       NtisCwFileDataErrsDAO daoObject = new NtisCwFileDataErrsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisCwFileDataErrsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisCwFileDataErrsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisCwFileDataErrsDAO> data = new ArrayList<NtisCwFileDataErrsDAO>();
      while (rs.next()) {
         data.add(new NtisCwFileDataErrsDAO(Utils.getDouble(rs.getObject("CWFDE_ID")), //
                               rs.getString("CWFDE_TYPE"), //
                               rs.getString("CWFDE_LEVEL"), //
                               Utils.getDouble(rs.getObject("CWFDE_REC_NR")), //
                               Utils.getDouble(rs.getObject("CWFDE_COLUMN_NR")), //
                               rs.getString("CWFDE_COLUMN_NAME"), //
                               rs.getString("CWFDE_COLUMN_VALUE"), //
                               rs.getString("CWFDE_MSG_CODE"), //
                               rs.getString("CWFDE_MSG_TEXT"), //
                               Utils.getDouble(rs.getObject("CWFDE_CWF_ID")), //
                               Utils.getDouble(rs.getObject("CWFDE_CWFD_ID")), //
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
   public NtisCwFileDataErrsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE CWFDE_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisCwFileDataErrsDAO> data = null;
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
      NtisCwFileDataErrsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisCwFileDataErrsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_CW_FILE_DATA_ERRS WHERE CWFDE_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisCwFileDataErrsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getCwfde_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisCwFileDataErrsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_CW_FILE_DATA_ERRS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisCwFileDataErrsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisCwFileDataErrsDAO insertRecord(Connection conn, NtisCwFileDataErrsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_CW_FILE_DATA_ERRS (CWFDE_TYPE, CWFDE_LEVEL, CWFDE_REC_NR, CWFDE_COLUMN_NR, CWFDE_COLUMN_NAME, CWFDE_COLUMN_VALUE, CWFDE_MSG_CODE, CWFDE_MSG_TEXT, CWFDE_CWF_ID, CWFDE_CWFD_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING CWFDE_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getCwfde_type());
         pstmt.setString(2, daoObject.getCwfde_level());
         pstmt.setObject(3, daoObject.getCwfde_rec_nr());
         pstmt.setObject(4, daoObject.getCwfde_column_nr());
         pstmt.setString(5, daoObject.getCwfde_column_name());
         pstmt.setString(6, daoObject.getCwfde_column_value());
         pstmt.setString(7, daoObject.getCwfde_msg_code());
         pstmt.setString(8, daoObject.getCwfde_msg_text());
         pstmt.setObject(9, daoObject.getCwfde_cwf_id());
         pstmt.setObject(10, daoObject.getCwfde_cwfd_id());
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
            daoObject.setCwfde_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisCwFileDataErrsDAO updateRecord(Connection conn, NtisCwFileDataErrsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisCwFileDataErrsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisCwFileDataErrsDAO saveRecord(Connection conn, NtisCwFileDataErrsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}