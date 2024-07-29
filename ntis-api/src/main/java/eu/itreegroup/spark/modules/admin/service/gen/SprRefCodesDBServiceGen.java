package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
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

public class SprRefCodesDBServiceGen extends SprBaseDBServiceImpl<SprRefCodesDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprRefCodesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT RFC_ID, RFC_DOMAIN, RFC_CODE, RFC_MEANING, RFC_DESCRIPTION, RFC_PARENT_DOMAIN, RFC_PARENT_DOMAIN_CODE, RFC_ORDER, RFC_DATE_FROM, RFC_DATE_TO, RFC_RFD_ID, RFC_REF_CODE_1, RFC_REF_CODE_2, RFC_REF_CODE_3, RFC_REF_CODE_4, RFC_REF_CODE_5, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_REF_CODES ";

   @Override
   public SprRefCodesDAO newRecord() {
	  	  SprRefCodesDAO daoObject = new SprRefCodesDAO();
	  	  return daoObject;
	  }
   @Override
   public SprRefCodesDAO object4ForceUpdate() {
       SprRefCodesDAO daoObject = new SprRefCodesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprRefCodesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprRefCodesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprRefCodesDAO> data = new ArrayList<SprRefCodesDAO>();
      while (rs.next()) {
         data.add(new SprRefCodesDAO(Utils.getDouble(rs.getObject("RFC_ID")), //
                               rs.getString("RFC_DOMAIN"), //
                               rs.getString("RFC_CODE"), //
                               rs.getString("RFC_MEANING"), //
                               rs.getString("RFC_DESCRIPTION"), //
                               rs.getString("RFC_PARENT_DOMAIN"), //
                               rs.getString("RFC_PARENT_DOMAIN_CODE"), //
                               Utils.getDouble(rs.getObject("RFC_ORDER")), //
                               rs.getTimestamp("RFC_DATE_FROM"), //
                               rs.getTimestamp("RFC_DATE_TO"), //
                               Utils.getDouble(rs.getObject("RFC_RFD_ID")), //
                               rs.getString("RFC_REF_CODE_1"), //
                               rs.getString("RFC_REF_CODE_2"), //
                               rs.getString("RFC_REF_CODE_3"), //
                               rs.getString("RFC_REF_CODE_4"), //
                               rs.getString("RFC_REF_CODE_5"), //
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
   public SprRefCodesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE RFC_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprRefCodesDAO> data = null;
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
      SprRefCodesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprRefCodesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_REF_CODES WHERE RFC_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprRefCodesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getRfc_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprRefCodesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_REF_CODES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprRefCodesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "SPR_REF_CODES_RFC_ID_KEY".equalsIgnoreCase(constraintName)) {
            if (daoObject.getRfc_id() != null) {
                String[] constraintColumns = { "rfc_id" };
                String stmt = "select 1 from SPR_REF_CODES where " //
                        + constructStatementPart("rfc_id", daoObject.getRfc_id()) + " and " //
                        + (daoObject.getRfc_id() != null ? "RFC_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getRfc_id());
                    if (daoObject.getRfc_id() != null) {
                        pstmt.setObject(++idx, daoObject.getRfc_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("SPR_REF_CODES_RFC_ID_KEY", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprRefCodesDAO insertRecord(Connection conn, SprRefCodesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_REF_CODES (RFC_DOMAIN, RFC_CODE, RFC_MEANING, RFC_DESCRIPTION, RFC_PARENT_DOMAIN, RFC_PARENT_DOMAIN_CODE, RFC_ORDER, RFC_DATE_FROM, RFC_DATE_TO, RFC_RFD_ID, RFC_REF_CODE_1, RFC_REF_CODE_2, RFC_REF_CODE_3, RFC_REF_CODE_4, RFC_REF_CODE_5, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING RFC_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getRfc_domain());
         pstmt.setString(2, daoObject.getRfc_code());
         pstmt.setString(3, daoObject.getRfc_meaning());
         pstmt.setString(4, daoObject.getRfc_description());
         pstmt.setString(5, daoObject.getRfc_parent_domain());
         pstmt.setString(6, daoObject.getRfc_parent_domain_code());
         pstmt.setObject(7, daoObject.getRfc_order());
         pstmt.setObject(8,  Utils.getSqlDate(daoObject.getRfc_date_from()));
         pstmt.setObject(9,  Utils.getSqlDate(daoObject.getRfc_date_to()));
         pstmt.setObject(10, daoObject.getRfc_rfd_id());
         pstmt.setString(11, daoObject.getRfc_ref_code_1());
         pstmt.setString(12, daoObject.getRfc_ref_code_2());
         pstmt.setString(13, daoObject.getRfc_ref_code_3());
         pstmt.setString(14, daoObject.getRfc_ref_code_4());
         pstmt.setString(15, daoObject.getRfc_ref_code_5());
         pstmt.setObject(16, daoObject.getN01());
         pstmt.setObject(17, daoObject.getN02());
         pstmt.setObject(18, daoObject.getN03());
         pstmt.setObject(19, daoObject.getN04());
         pstmt.setObject(20, daoObject.getN05());
         pstmt.setString(21, daoObject.getC01());
         pstmt.setString(22, daoObject.getC02());
         pstmt.setString(23, daoObject.getC03());
         pstmt.setString(24, daoObject.getC04());
         pstmt.setString(25, daoObject.getC05());
         pstmt.setObject(26,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(27,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(28,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(29,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(30,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(31, 1);
         pstmt.setObject(32,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(33,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(34, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setRfc_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprRefCodesDAO updateRecord(Connection conn, SprRefCodesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprRefCodesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprRefCodesDAO saveRecord(Connection conn, SprRefCodesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}