package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisAdrStreetsDAO;
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

public class NtisAdrStreetsDBServiceGen extends SprBaseDBServiceImpl<NtisAdrStreetsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisAdrStreetsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT STR_ID, STR_STREET_CODE, STR_TYPE, STR_TYPE_ABBREVIATION, STR_NAME, STR_RESIDENCE_CODE, STR_DATE_FROM, STR_DATE_TO, STR_RE_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_ADR_STREETS ";

   @Override
   public NtisAdrStreetsDAO newRecord() {
	  	  NtisAdrStreetsDAO daoObject = new NtisAdrStreetsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisAdrStreetsDAO object4ForceUpdate() {
       NtisAdrStreetsDAO daoObject = new NtisAdrStreetsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisAdrStreetsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisAdrStreetsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisAdrStreetsDAO> data = new ArrayList<NtisAdrStreetsDAO>();
      while (rs.next()) {
         data.add(new NtisAdrStreetsDAO(Utils.getDouble(rs.getObject("STR_ID")), //
                               Utils.getDouble(rs.getObject("STR_STREET_CODE")), //
                               rs.getString("STR_TYPE"), //
                               rs.getString("STR_TYPE_ABBREVIATION"), //
                               rs.getString("STR_NAME"), //
                               Utils.getDouble(rs.getObject("STR_RESIDENCE_CODE")), //
                               rs.getTimestamp("STR_DATE_FROM"), //
                               rs.getTimestamp("STR_DATE_TO"), //
                               Utils.getDouble(rs.getObject("STR_RE_ID")), //
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
   public NtisAdrStreetsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE STR_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisAdrStreetsDAO> data = null;
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
      NtisAdrStreetsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisAdrStreetsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_ADR_STREETS WHERE STR_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisAdrStreetsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getStr_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisAdrStreetsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_ADR_STREETS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisAdrStreetsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisAdrStreetsDAO insertRecord(Connection conn, NtisAdrStreetsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_ADR_STREETS (STR_STREET_CODE, STR_TYPE, STR_TYPE_ABBREVIATION, STR_NAME, STR_RESIDENCE_CODE, STR_DATE_FROM, STR_DATE_TO, STR_RE_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING STR_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getStr_street_code());
         pstmt.setString(2, daoObject.getStr_type());
         pstmt.setString(3, daoObject.getStr_type_abbreviation());
         pstmt.setString(4, daoObject.getStr_name());
         pstmt.setObject(5, daoObject.getStr_residence_code());
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getStr_date_from()));
         pstmt.setObject(7,  Utils.getSqlTimestamp(daoObject.getStr_date_to()));
         pstmt.setObject(8, daoObject.getStr_re_id());
         pstmt.setObject(9, daoObject.getN01());
         pstmt.setObject(10, daoObject.getN02());
         pstmt.setObject(11, daoObject.getN03());
         pstmt.setObject(12, daoObject.getN04());
         pstmt.setObject(13, daoObject.getN05());
         pstmt.setString(14, daoObject.getC01());
         pstmt.setString(15, daoObject.getC02());
         pstmt.setString(16, daoObject.getC03());
         pstmt.setString(17, daoObject.getC04());
         pstmt.setString(18, daoObject.getC05());
         pstmt.setObject(19,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(20,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(21,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(24, 1);
         pstmt.setObject(25,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(26,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(27, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setStr_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisAdrStreetsDAO updateRecord(Connection conn, NtisAdrStreetsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisAdrStreetsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisAdrStreetsDAO saveRecord(Connection conn, NtisAdrStreetsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}