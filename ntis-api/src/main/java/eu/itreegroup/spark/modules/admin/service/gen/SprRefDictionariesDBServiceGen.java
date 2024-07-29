package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprRefDictionariesDAO;
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

public class SprRefDictionariesDBServiceGen extends SprBaseDBServiceImpl<SprRefDictionariesDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprRefDictionariesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT RFD_ID, RFD_SUBSYSTEM, RFD_TABLE_NAME, RFD_NAME, RFD_DESCRIPTION, RFD_CODE_TYPE, RFD_CODE_LENGTH, RFD_CODE_COLNAME, RFD_DESC_COLNAME, RFD_REF_DOMAIN_1, RFD_REF_DOMAIN_2, RFD_REF_DOMAIN_3, RFD_REF_DOMAIN_4, RFD_REF_DOMAIN_5, RFD_N1_COLNAME, RFD_N2_COLNAME, RFD_N3_COLNAME, RFD_N4_COLNAME, RFD_N5_COLNAME, RFD_C1_COLNAME, RFD_C2_COLNAME, RFD_C3_COLNAME, RFD_C4_COLNAME, RFD_C5_COLNAME, RFD_D1_COLNAME, RFD_D2_COLNAME, RFD_D3_COLNAME, RFD_D4_COLNAME, RFD_D5_COLNAME, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_REF_DICTIONARIES ";

   @Override
   public SprRefDictionariesDAO newRecord() {
	  	  SprRefDictionariesDAO daoObject = new SprRefDictionariesDAO();
	  	  return daoObject;
	  }
   @Override
   public SprRefDictionariesDAO object4ForceUpdate() {
       SprRefDictionariesDAO daoObject = new SprRefDictionariesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprRefDictionariesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprRefDictionariesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprRefDictionariesDAO> data = new ArrayList<SprRefDictionariesDAO>();
      while (rs.next()) {
         data.add(new SprRefDictionariesDAO(Utils.getDouble(rs.getObject("RFD_ID")), //
                               rs.getString("RFD_SUBSYSTEM"), //
                               rs.getString("RFD_TABLE_NAME"), //
                               rs.getString("RFD_NAME"), //
                               rs.getString("RFD_DESCRIPTION"), //
                               rs.getString("RFD_CODE_TYPE"), //
                               Utils.getDouble(rs.getObject("RFD_CODE_LENGTH")), //
                               rs.getString("RFD_CODE_COLNAME"), //
                               rs.getString("RFD_DESC_COLNAME"), //
                               rs.getString("RFD_REF_DOMAIN_1"), //
                               rs.getString("RFD_REF_DOMAIN_2"), //
                               rs.getString("RFD_REF_DOMAIN_3"), //
                               rs.getString("RFD_REF_DOMAIN_4"), //
                               rs.getString("RFD_REF_DOMAIN_5"), //
                               rs.getString("RFD_N1_COLNAME"), //
                               rs.getString("RFD_N2_COLNAME"), //
                               rs.getString("RFD_N3_COLNAME"), //
                               rs.getString("RFD_N4_COLNAME"), //
                               rs.getString("RFD_N5_COLNAME"), //
                               rs.getString("RFD_C1_COLNAME"), //
                               rs.getString("RFD_C2_COLNAME"), //
                               rs.getString("RFD_C3_COLNAME"), //
                               rs.getString("RFD_C4_COLNAME"), //
                               rs.getString("RFD_C5_COLNAME"), //
                               rs.getString("RFD_D1_COLNAME"), //
                               rs.getString("RFD_D2_COLNAME"), //
                               rs.getString("RFD_D3_COLNAME"), //
                               rs.getString("RFD_D4_COLNAME"), //
                               rs.getString("RFD_D5_COLNAME"), //
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
   public SprRefDictionariesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE RFD_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprRefDictionariesDAO> data = null;
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
      SprRefDictionariesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprRefDictionariesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_REF_DICTIONARIES WHERE RFD_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprRefDictionariesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getRfd_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprRefDictionariesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_REF_DICTIONARIES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprRefDictionariesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprRefDictionariesDAO insertRecord(Connection conn, SprRefDictionariesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_REF_DICTIONARIES (RFD_SUBSYSTEM, RFD_TABLE_NAME, RFD_NAME, RFD_DESCRIPTION, RFD_CODE_TYPE, RFD_CODE_LENGTH, RFD_CODE_COLNAME, RFD_DESC_COLNAME, RFD_REF_DOMAIN_1, RFD_REF_DOMAIN_2, RFD_REF_DOMAIN_3, RFD_REF_DOMAIN_4, RFD_REF_DOMAIN_5, RFD_N1_COLNAME, RFD_N2_COLNAME, RFD_N3_COLNAME, RFD_N4_COLNAME, RFD_N5_COLNAME, RFD_C1_COLNAME, RFD_C2_COLNAME, RFD_C3_COLNAME, RFD_C4_COLNAME, RFD_C5_COLNAME, RFD_D1_COLNAME, RFD_D2_COLNAME, RFD_D3_COLNAME, RFD_D4_COLNAME, RFD_D5_COLNAME, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING RFD_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getRfd_subsystem());
         pstmt.setString(2, daoObject.getRfd_table_name());
         pstmt.setString(3, daoObject.getRfd_name());
         pstmt.setString(4, daoObject.getRfd_description());
         pstmt.setString(5, daoObject.getRfd_code_type());
         pstmt.setObject(6, daoObject.getRfd_code_length());
         pstmt.setString(7, daoObject.getRfd_code_colname());
         pstmt.setString(8, daoObject.getRfd_desc_colname());
         pstmt.setString(9, daoObject.getRfd_ref_domain_1());
         pstmt.setString(10, daoObject.getRfd_ref_domain_2());
         pstmt.setString(11, daoObject.getRfd_ref_domain_3());
         pstmt.setString(12, daoObject.getRfd_ref_domain_4());
         pstmt.setString(13, daoObject.getRfd_ref_domain_5());
         pstmt.setString(14, daoObject.getRfd_n1_colname());
         pstmt.setString(15, daoObject.getRfd_n2_colname());
         pstmt.setString(16, daoObject.getRfd_n3_colname());
         pstmt.setString(17, daoObject.getRfd_n4_colname());
         pstmt.setString(18, daoObject.getRfd_n5_colname());
         pstmt.setString(19, daoObject.getRfd_c1_colname());
         pstmt.setString(20, daoObject.getRfd_c2_colname());
         pstmt.setString(21, daoObject.getRfd_c3_colname());
         pstmt.setString(22, daoObject.getRfd_c4_colname());
         pstmt.setString(23, daoObject.getRfd_c5_colname());
         pstmt.setString(24, daoObject.getRfd_d1_colname());
         pstmt.setString(25, daoObject.getRfd_d2_colname());
         pstmt.setString(26, daoObject.getRfd_d3_colname());
         pstmt.setString(27, daoObject.getRfd_d4_colname());
         pstmt.setString(28, daoObject.getRfd_d5_colname());
         pstmt.setObject(29, daoObject.getN01());
         pstmt.setObject(30, daoObject.getN02());
         pstmt.setObject(31, daoObject.getN03());
         pstmt.setObject(32, daoObject.getN04());
         pstmt.setObject(33, daoObject.getN05());
         pstmt.setString(34, daoObject.getC01());
         pstmt.setString(35, daoObject.getC02());
         pstmt.setString(36, daoObject.getC03());
         pstmt.setString(37, daoObject.getC04());
         pstmt.setString(38, daoObject.getC05());
         pstmt.setObject(39,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(40,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(41,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(42,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(43,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(44, 1);
         pstmt.setObject(45,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(46,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(47, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setRfd_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprRefDictionariesDAO updateRecord(Connection conn, SprRefDictionariesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprRefDictionariesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprRefDictionariesDAO saveRecord(Connection conn, SprRefDictionariesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}