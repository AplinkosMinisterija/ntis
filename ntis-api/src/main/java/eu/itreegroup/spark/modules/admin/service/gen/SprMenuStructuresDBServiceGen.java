package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprMenuStructuresDAO;
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

public class SprMenuStructuresDBServiceGen extends SprBaseDBServiceImpl<SprMenuStructuresDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprMenuStructuresDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT MST_ID, MST_SITE, MST_LANG, MST_CODE, MST_TITLE, MST_ICON, MST_ORDER, MST_URI, MST_IS_PUBLIC, MST_DATE_FROM, MST_DATE_TO, MST_FRM_ID, MST_MST_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_MENU_STRUCTURES ";

   @Override
   public SprMenuStructuresDAO newRecord() {
	  	  SprMenuStructuresDAO daoObject = new SprMenuStructuresDAO();
	  	  return daoObject;
	  }
   @Override
   public SprMenuStructuresDAO object4ForceUpdate() {
       SprMenuStructuresDAO daoObject = new SprMenuStructuresDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprMenuStructuresDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprMenuStructuresDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprMenuStructuresDAO> data = new ArrayList<SprMenuStructuresDAO>();
      while (rs.next()) {
         data.add(new SprMenuStructuresDAO(Utils.getDouble(rs.getObject("MST_ID")), //
                               rs.getString("MST_SITE"), //
                               rs.getString("MST_LANG"), //
                               rs.getString("MST_CODE"), //
                               rs.getString("MST_TITLE"), //
                               rs.getString("MST_ICON"), //
                               Utils.getDouble(rs.getObject("MST_ORDER")), //
                               rs.getString("MST_URI"), //
                               rs.getString("MST_IS_PUBLIC"), //
                               rs.getTimestamp("MST_DATE_FROM"), //
                               rs.getTimestamp("MST_DATE_TO"), //
                               Utils.getDouble(rs.getObject("MST_FRM_ID")), //
                               Utils.getDouble(rs.getObject("MST_MST_ID")), //
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
   public SprMenuStructuresDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE MST_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprMenuStructuresDAO> data = null;
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
      SprMenuStructuresDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprMenuStructuresDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_MENU_STRUCTURES WHERE MST_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprMenuStructuresDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getMst_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprMenuStructuresDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_MENU_STRUCTURES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprMenuStructuresDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprMenuStructuresDAO insertRecord(Connection conn, SprMenuStructuresDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_MENU_STRUCTURES (MST_SITE, MST_LANG, MST_CODE, MST_TITLE, MST_ICON, MST_ORDER, MST_URI, MST_IS_PUBLIC, MST_DATE_FROM, MST_DATE_TO, MST_FRM_ID, MST_MST_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING MST_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getMst_site());
         pstmt.setString(2, daoObject.getMst_lang());
         pstmt.setString(3, daoObject.getMst_code());
         pstmt.setString(4, daoObject.getMst_title());
         pstmt.setString(5, daoObject.getMst_icon());
         pstmt.setObject(6, daoObject.getMst_order());
         pstmt.setString(7, daoObject.getMst_uri());
         pstmt.setString(8, daoObject.getMst_is_public());
         pstmt.setObject(9,  Utils.getSqlDate(daoObject.getMst_date_from()));
         pstmt.setObject(10,  Utils.getSqlDate(daoObject.getMst_date_to()));
         pstmt.setObject(11, daoObject.getMst_frm_id());
         pstmt.setObject(12, daoObject.getMst_mst_id());
         pstmt.setObject(13, daoObject.getN01());
         pstmt.setObject(14, daoObject.getN02());
         pstmt.setObject(15, daoObject.getN03());
         pstmt.setObject(16, daoObject.getN04());
         pstmt.setObject(17, daoObject.getN05());
         pstmt.setString(18, daoObject.getC01());
         pstmt.setString(19, daoObject.getC02());
         pstmt.setString(20, daoObject.getC03());
         pstmt.setString(21, daoObject.getC04());
         pstmt.setString(22, daoObject.getC05());
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(25,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(26,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(27,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(28, 1);
         pstmt.setObject(29,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(30,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(31, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setMst_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprMenuStructuresDAO updateRecord(Connection conn, SprMenuStructuresDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprMenuStructuresDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprMenuStructuresDAO saveRecord(Connection conn, SprMenuStructuresDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}