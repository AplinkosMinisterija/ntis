package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprPersonsDAO;
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
import java.util.Arrays;

public class SprPersonsDBServiceGen extends SprBaseDBServiceImpl<SprPersonsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprPersonsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT PER_ID, PER_NAME, PER_SURNAME, PER_DATA_CONFIRMED, PER_CONFIRMATION_DATE, PER_DATE_OF_BIRTH, PER_CODE_EXISTS, PER_CODE, PER_PHONE_NUMBER, PER_EMAIL, PER_EMAIL_CONFIRMED, PER_DOCUMENT_TYPE, PER_DOCUMENT_NUMBER, PER_DOCUMENT_VALID_UNTIL, PER_LRT_RESIDENT, PER_ADDRESS_COUNTRY_CODE, PER_ADDRESS_CITY, PER_ADDRESS_STREET, PER_ADDRESS_POST_CODE, PER_ADDRESS_HOUSE_NUMBER, PER_ADDRESS_FLAT_NUMBER, PER_PHOTO_FIL_ID, PER_AVATAR_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_PERSONS ";

   @Override
   public SprPersonsDAO newRecord() {
	  	  SprPersonsDAO daoObject = new SprPersonsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprPersonsDAO object4ForceUpdate() {
       SprPersonsDAO daoObject = new SprPersonsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprPersonsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprPersonsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprPersonsDAO> data = new ArrayList<SprPersonsDAO>();
      while (rs.next()) {
         data.add(new SprPersonsDAO(Utils.getDouble(rs.getObject("PER_ID")), //
                               rs.getString("PER_NAME"), //
                               rs.getString("PER_SURNAME"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("PER_DATA_CONFIRMED")), //
                               rs.getTimestamp("PER_CONFIRMATION_DATE"), //
                               rs.getTimestamp("PER_DATE_OF_BIRTH"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("PER_CODE_EXISTS")), //
                               rs.getString("PER_CODE"), //
                               rs.getString("PER_PHONE_NUMBER"), //
                               rs.getString("PER_EMAIL"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("PER_EMAIL_CONFIRMED")), //
                               rs.getString("PER_DOCUMENT_TYPE"), //
                               rs.getString("PER_DOCUMENT_NUMBER"), //
                               rs.getTimestamp("PER_DOCUMENT_VALID_UNTIL"), //
                               Utils.removeWhitespacesFromEnd(rs.getString("PER_LRT_RESIDENT")), //
                               rs.getString("PER_ADDRESS_COUNTRY_CODE"), //
                               rs.getString("PER_ADDRESS_CITY"), //
                               rs.getString("PER_ADDRESS_STREET"), //
                               rs.getString("PER_ADDRESS_POST_CODE"), //
                               rs.getString("PER_ADDRESS_HOUSE_NUMBER"), //
                               rs.getString("PER_ADDRESS_FLAT_NUMBER"), //
                               Utils.getDouble(rs.getObject("PER_PHOTO_FIL_ID")), //
                               Utils.getDouble(rs.getObject("PER_AVATAR_FIL_ID")), //
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
   public SprPersonsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE PER_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprPersonsDAO> data = null;
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
      SprPersonsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprPersonsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_PERSONS WHERE PER_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprPersonsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getPer_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprPersonsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_PERSONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprPersonsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "SPR_PERSONS_PER_CODE_PER_DOCUMENT_NUMBER_KEY".equalsIgnoreCase(constraintName)) {
            if (daoObject.getPer_code() != null || //
                    daoObject.getPer_document_number() != null) {
                String[] constraintColumns = { "per_code", "per_document_number" };
                String stmt = "select 1 from SPR_PERSONS where " //
                        + constructStatementPart("per_code", daoObject.getPer_code()) + " and " //
                        + constructStatementPart("per_document_number", daoObject.getPer_document_number()) + " and " //
                        + (daoObject.getPer_id() != null ? "PER_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getPer_code());
                    idx = setValueToStatement(pstmt, idx, daoObject.getPer_document_number());
                    if (daoObject.getPer_id() != null) {
                        pstmt.setObject(++idx, daoObject.getPer_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("SPR_PERSONS_PER_CODE_PER_DOCUMENT_NUMBER_KEY", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprPersonsDAO insertRecord(Connection conn, SprPersonsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_PERSONS (PER_NAME, PER_SURNAME, PER_DATA_CONFIRMED, PER_CONFIRMATION_DATE, PER_DATE_OF_BIRTH, PER_CODE_EXISTS, PER_CODE, PER_PHONE_NUMBER, PER_EMAIL, PER_EMAIL_CONFIRMED, PER_DOCUMENT_TYPE, PER_DOCUMENT_NUMBER, PER_DOCUMENT_VALID_UNTIL, PER_LRT_RESIDENT, PER_ADDRESS_COUNTRY_CODE, PER_ADDRESS_CITY, PER_ADDRESS_STREET, PER_ADDRESS_POST_CODE, PER_ADDRESS_HOUSE_NUMBER, PER_ADDRESS_FLAT_NUMBER, PER_PHOTO_FIL_ID, PER_AVATAR_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING PER_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getPer_name());
         pstmt.setString(2, daoObject.getPer_surname());
         pstmt.setString(3, daoObject.getPer_data_confirmed());
         pstmt.setObject(4,  Utils.getSqlDate(daoObject.getPer_confirmation_date()));
         pstmt.setObject(5,  Utils.getSqlDate(daoObject.getPer_date_of_birth()));
         pstmt.setString(6, daoObject.getPer_code_exists());
         pstmt.setString(7, daoObject.getPer_code());
         pstmt.setString(8, daoObject.getPer_phone_number());
         pstmt.setString(9, daoObject.getPer_email());
         pstmt.setString(10, daoObject.getPer_email_confirmed());
         pstmt.setString(11, daoObject.getPer_document_type());
         pstmt.setString(12, daoObject.getPer_document_number());
         pstmt.setObject(13,  Utils.getSqlDate(daoObject.getPer_document_valid_until()));
         pstmt.setString(14, daoObject.getPer_lrt_resident());
         pstmt.setString(15, daoObject.getPer_address_country_code());
         pstmt.setString(16, daoObject.getPer_address_city());
         pstmt.setString(17, daoObject.getPer_address_street());
         pstmt.setString(18, daoObject.getPer_address_post_code());
         pstmt.setString(19, daoObject.getPer_address_house_number());
         pstmt.setString(20, daoObject.getPer_address_flat_number());
         pstmt.setObject(21, daoObject.getPer_photo_fil_id());
         pstmt.setObject(22, daoObject.getPer_avatar_fil_id());
         pstmt.setObject(23, daoObject.getN01());
         pstmt.setObject(24, daoObject.getN02());
         pstmt.setObject(25, daoObject.getN03());
         pstmt.setObject(26, daoObject.getN04());
         pstmt.setObject(27, daoObject.getN05());
         pstmt.setString(28, daoObject.getC01());
         pstmt.setString(29, daoObject.getC02());
         pstmt.setString(30, daoObject.getC03());
         pstmt.setString(31, daoObject.getC04());
         pstmt.setString(32, daoObject.getC05());
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(34,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(35,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(36,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(37,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(38, 1);
         pstmt.setObject(39,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(40,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(41, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setPer_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprPersonsDAO updateRecord(Connection conn, SprPersonsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprPersonsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprPersonsDAO saveRecord(Connection conn, SprPersonsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}