package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisAdrMappingsDAO;
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

public class NtisAdrMappingsDBServiceGen extends SprBaseDBServiceImpl<NtisAdrMappingsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisAdrMappingsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT AM_ID, AM_ADDRESS_TYPE, AM_PROVIDED_ADDRES_NAME, AM_MUNICIPALITY_CODE, AM_SEN_ID, AM_STR_ID, AM_ORG_ID, AM_RE_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_ADR_MAPPINGS ";

   @Override
   public NtisAdrMappingsDAO newRecord() {
	  	  NtisAdrMappingsDAO daoObject = new NtisAdrMappingsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisAdrMappingsDAO object4ForceUpdate() {
       NtisAdrMappingsDAO daoObject = new NtisAdrMappingsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisAdrMappingsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisAdrMappingsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisAdrMappingsDAO> data = new ArrayList<NtisAdrMappingsDAO>();
      while (rs.next()) {
         data.add(new NtisAdrMappingsDAO(Utils.getDouble(rs.getObject("AM_ID")), //
                               rs.getString("AM_ADDRESS_TYPE"), //
                               rs.getString("AM_PROVIDED_ADDRES_NAME"), //
                               rs.getString("AM_MUNICIPALITY_CODE"), //
                               Utils.getDouble(rs.getObject("AM_SEN_ID")), //
                               Utils.getDouble(rs.getObject("AM_STR_ID")), //
                               Utils.getDouble(rs.getObject("AM_ORG_ID")), //
                               Utils.getDouble(rs.getObject("AM_RE_ID")), //
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
   public NtisAdrMappingsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE AM_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisAdrMappingsDAO> data = null;
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
      NtisAdrMappingsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisAdrMappingsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_ADR_MAPPINGS WHERE AM_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisAdrMappingsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getAm_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisAdrMappingsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_ADR_MAPPINGS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisAdrMappingsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisAdrMappingsDAO insertRecord(Connection conn, NtisAdrMappingsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_ADR_MAPPINGS (AM_ADDRESS_TYPE, AM_PROVIDED_ADDRES_NAME, AM_MUNICIPALITY_CODE, AM_SEN_ID, AM_STR_ID, AM_ORG_ID, AM_RE_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING AM_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getAm_address_type());
         pstmt.setString(2, daoObject.getAm_provided_addres_name());
         pstmt.setString(3, daoObject.getAm_municipality_code());
         pstmt.setObject(4, daoObject.getAm_sen_id());
         pstmt.setObject(5, daoObject.getAm_str_id());
         pstmt.setObject(6, daoObject.getAm_org_id());
         pstmt.setObject(7, daoObject.getAm_re_id());
         pstmt.setObject(8, daoObject.getN01());
         pstmt.setObject(9, daoObject.getN02());
         pstmt.setObject(10, daoObject.getN03());
         pstmt.setObject(11, daoObject.getN04());
         pstmt.setObject(12, daoObject.getN05());
         pstmt.setString(13, daoObject.getC01());
         pstmt.setString(14, daoObject.getC02());
         pstmt.setString(15, daoObject.getC03());
         pstmt.setString(16, daoObject.getC04());
         pstmt.setString(17, daoObject.getC05());
         pstmt.setObject(18,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(19,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(20,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(21,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(23, 1);
         pstmt.setObject(24,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(25,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(26, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setAm_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisAdrMappingsDAO updateRecord(Connection conn, NtisAdrMappingsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisAdrMappingsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisAdrMappingsDAO saveRecord(Connection conn, NtisAdrMappingsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}