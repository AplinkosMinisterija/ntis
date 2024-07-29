package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprQuestionAnswersDAO;
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

public class SprQuestionAnswersDBServiceGen extends SprBaseDBServiceImpl<SprQuestionAnswersDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprQuestionAnswersDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT FAC_ID, FAC_GROUP, FAC_TYPE, FAC_LANG, FAC_CODE, FAC_QUESTION, FAC_ANSWER, FAC_CONTENT_FOR_SEARCH, FAC_DATE_FROM, FAC_DATE_TO, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_QUESTION_ANSWERS ";

   @Override
   public SprQuestionAnswersDAO newRecord() {
	  	  SprQuestionAnswersDAO daoObject = new SprQuestionAnswersDAO();
	  	  return daoObject;
	  }
   @Override
   public SprQuestionAnswersDAO object4ForceUpdate() {
       SprQuestionAnswersDAO daoObject = new SprQuestionAnswersDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprQuestionAnswersDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprQuestionAnswersDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprQuestionAnswersDAO> data = new ArrayList<SprQuestionAnswersDAO>();
      while (rs.next()) {
         data.add(new SprQuestionAnswersDAO(Utils.getDouble(rs.getObject("FAC_ID")), //
                               rs.getString("FAC_GROUP"), //
                               rs.getString("FAC_TYPE"), //
                               rs.getString("FAC_LANG"), //
                               rs.getString("FAC_CODE"), //
                               rs.getString("FAC_QUESTION"), //
                               rs.getString("FAC_ANSWER"), //
                               rs.getString("FAC_CONTENT_FOR_SEARCH"), //
                               rs.getTimestamp("FAC_DATE_FROM"), //
                               rs.getTimestamp("FAC_DATE_TO"), //
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
   public SprQuestionAnswersDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE FAC_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprQuestionAnswersDAO> data = null;
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
      SprQuestionAnswersDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprQuestionAnswersDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_QUESTION_ANSWERS WHERE FAC_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprQuestionAnswersDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getFac_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprQuestionAnswersDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_QUESTION_ANSWERS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprQuestionAnswersDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "FAC_FAC_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getFac_type() != null || //
                    daoObject.getFac_lang() != null || //
                    daoObject.getFac_group() != null || //
                    daoObject.getFac_date_from() != null || //
                    daoObject.getFac_code() != null) {
                String[] constraintColumns = { "fac_type", "fac_lang", "fac_group", "fac_date_from", "fac_code" };
                String stmt = "select 1 from SPR_QUESTION_ANSWERS where " //
                        + constructStatementPart("fac_type", daoObject.getFac_type()) + " and " //
                        + constructStatementPart("fac_lang", daoObject.getFac_lang()) + " and " //
                        + constructStatementPart("fac_group", daoObject.getFac_group()) + " and " //
                        + constructStatementPart("fac_date_from", daoObject.getFac_date_from()) + " and " //
                        + constructStatementPart("fac_code", daoObject.getFac_code()) + " and " //
                        + (daoObject.getFac_id() != null ? "FAC_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getFac_type());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFac_lang());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFac_group());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFac_date_from());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFac_code());
                    if (daoObject.getFac_id() != null) {
                        pstmt.setObject(++idx, daoObject.getFac_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("FAC_FAC_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprQuestionAnswersDAO insertRecord(Connection conn, SprQuestionAnswersDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_QUESTION_ANSWERS (FAC_GROUP, FAC_TYPE, FAC_LANG, FAC_CODE, FAC_QUESTION, FAC_ANSWER, FAC_CONTENT_FOR_SEARCH, FAC_DATE_FROM, FAC_DATE_TO, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING FAC_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getFac_group());
         pstmt.setString(2, daoObject.getFac_type());
         pstmt.setString(3, daoObject.getFac_lang());
         pstmt.setString(4, daoObject.getFac_code());
         pstmt.setString(5, daoObject.getFac_question());
         pstmt.setString(6, daoObject.getFac_answer());
         pstmt.setString(7, daoObject.getFac_content_for_search());
         pstmt.setObject(8,  Utils.getSqlTimestamp(daoObject.getFac_date_from()));
         pstmt.setObject(9,  Utils.getSqlTimestamp(daoObject.getFac_date_to()));
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
            daoObject.setFac_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprQuestionAnswersDAO updateRecord(Connection conn, SprQuestionAnswersDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprQuestionAnswersDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprQuestionAnswersDAO saveRecord(Connection conn, SprQuestionAnswersDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}