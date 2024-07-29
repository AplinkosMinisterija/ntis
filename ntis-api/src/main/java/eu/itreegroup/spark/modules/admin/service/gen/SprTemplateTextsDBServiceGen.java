package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprTemplateTextsDAO;
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

public class SprTemplateTextsDBServiceGen extends SprBaseDBServiceImpl<SprTemplateTextsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprTemplateTextsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT TMT_ID, TMT_CODE, TMT_NAME, TMT_DESCRIPTION, TMT_LANG, TMT_TEXT, TMT_DATE_FROM, TMT_DATE_TO, TMT_TML_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_TEMPLATE_TEXTS ";

   @Override
   public SprTemplateTextsDAO newRecord() {
	  	  SprTemplateTextsDAO daoObject = new SprTemplateTextsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprTemplateTextsDAO object4ForceUpdate() {
       SprTemplateTextsDAO daoObject = new SprTemplateTextsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprTemplateTextsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprTemplateTextsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprTemplateTextsDAO> data = new ArrayList<SprTemplateTextsDAO>();
      while (rs.next()) {
         data.add(new SprTemplateTextsDAO(Utils.getDouble(rs.getObject("TMT_ID")), //
                               rs.getString("TMT_CODE"), //
                               rs.getString("TMT_NAME"), //
                               rs.getString("TMT_DESCRIPTION"), //
                               rs.getString("TMT_LANG"), //
                               rs.getString("TMT_TEXT"), //
                               rs.getTimestamp("TMT_DATE_FROM"), //
                               rs.getTimestamp("TMT_DATE_TO"), //
                               Utils.getDouble(rs.getObject("TMT_TML_ID")), //
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
   public SprTemplateTextsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE TMT_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprTemplateTextsDAO> data = null;
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
      SprTemplateTextsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprTemplateTextsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_TEMPLATE_TEXTS WHERE TMT_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprTemplateTextsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getTmt_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprTemplateTextsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_TEMPLATE_TEXTS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprTemplateTextsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "TMT_CODE_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getTmt_code() != null || //
                    daoObject.getTmt_tml_id() != null || //
                    daoObject.getTmt_lang() != null) {
                String[] constraintColumns = { "tmt_code", "tmt_tml_id", "tmt_lang" };
                String stmt = "select 1 from SPR_TEMPLATE_TEXTS where " //
                        + constructStatementPart("tmt_code", daoObject.getTmt_code()) + " and " //
                        + constructStatementPart("tmt_tml_id", daoObject.getTmt_tml_id()) + " and " //
                        + constructStatementPart("tmt_lang", daoObject.getTmt_lang()) + " and " //
                        + (daoObject.getTmt_id() != null ? "TMT_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getTmt_code());
                    idx = setValueToStatement(pstmt, idx, daoObject.getTmt_tml_id());
                    idx = setValueToStatement(pstmt, idx, daoObject.getTmt_lang());
                    if (daoObject.getTmt_id() != null) {
                        pstmt.setObject(++idx, daoObject.getTmt_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("TMT_CODE_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprTemplateTextsDAO insertRecord(Connection conn, SprTemplateTextsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_TEMPLATE_TEXTS (TMT_CODE, TMT_NAME, TMT_DESCRIPTION, TMT_LANG, TMT_TEXT, TMT_DATE_FROM, TMT_DATE_TO, TMT_TML_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING TMT_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getTmt_code());
         pstmt.setString(2, daoObject.getTmt_name());
         pstmt.setString(3, daoObject.getTmt_description());
         pstmt.setString(4, daoObject.getTmt_lang());
         pstmt.setString(5, daoObject.getTmt_text());
         pstmt.setObject(6,  Utils.getSqlDate(daoObject.getTmt_date_from()));
         pstmt.setObject(7,  Utils.getSqlDate(daoObject.getTmt_date_to()));
         pstmt.setObject(8, daoObject.getTmt_tml_id());
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
         pstmt.setObject(19,  Utils.getSqlDate(daoObject.getD01()));
         pstmt.setObject(20,  Utils.getSqlDate(daoObject.getD02()));
         pstmt.setObject(21,  Utils.getSqlDate(daoObject.getD03()));
         pstmt.setObject(22,  Utils.getSqlDate(daoObject.getD04()));
         pstmt.setObject(23,  Utils.getSqlDate(daoObject.getD05()));
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
            daoObject.setTmt_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprTemplateTextsDAO updateRecord(Connection conn, SprTemplateTextsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprTemplateTextsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprTemplateTextsDAO saveRecord(Connection conn, SprTemplateTextsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}