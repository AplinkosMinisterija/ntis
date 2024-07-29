package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprRoleActionsDAO;
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

public class SprRoleActionsDBServiceGen extends SprBaseDBServiceImpl<SprRoleActionsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprRoleActionsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT ROA_ID, ROA_TYPE, ROA_DATE_FROM, ROA_DATE_TO, ROA_DEFAULT_MENU_ITEM, ROA_ROL_ID, ROA_MST_ID, ROA_FRA_ID, ROA_FRM_ID, ROA_ASSIGNED_ROL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_ROLE_ACTIONS ";

   @Override
   public SprRoleActionsDAO newRecord() {
	  	  SprRoleActionsDAO daoObject = new SprRoleActionsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprRoleActionsDAO object4ForceUpdate() {
       SprRoleActionsDAO daoObject = new SprRoleActionsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprRoleActionsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprRoleActionsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprRoleActionsDAO> data = new ArrayList<SprRoleActionsDAO>();
      while (rs.next()) {
         data.add(new SprRoleActionsDAO(Utils.getDouble(rs.getObject("ROA_ID")), //
                               rs.getString("ROA_TYPE"), //
                               rs.getTimestamp("ROA_DATE_FROM"), //
                               rs.getTimestamp("ROA_DATE_TO"), //
                               rs.getString("ROA_DEFAULT_MENU_ITEM"), //
                               Utils.getDouble(rs.getObject("ROA_ROL_ID")), //
                               Utils.getDouble(rs.getObject("ROA_MST_ID")), //
                               Utils.getDouble(rs.getObject("ROA_FRA_ID")), //
                               Utils.getDouble(rs.getObject("ROA_FRM_ID")), //
                               Utils.getDouble(rs.getObject("ROA_ASSIGNED_ROL_ID")), //
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
   public SprRoleActionsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE ROA_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprRoleActionsDAO> data = null;
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
      SprRoleActionsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprRoleActionsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_ROLE_ACTIONS WHERE ROA_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprRoleActionsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getRoa_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprRoleActionsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_ROLE_ACTIONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprRoleActionsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprRoleActionsDAO insertRecord(Connection conn, SprRoleActionsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_ROLE_ACTIONS (ROA_TYPE, ROA_DATE_FROM, ROA_DATE_TO, ROA_DEFAULT_MENU_ITEM, ROA_ROL_ID, ROA_MST_ID, ROA_FRA_ID, ROA_FRM_ID, ROA_ASSIGNED_ROL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ROA_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getRoa_type());
         pstmt.setObject(2,  Utils.getSqlDate(daoObject.getRoa_date_from()));
         pstmt.setObject(3,  Utils.getSqlDate(daoObject.getRoa_date_to()));
         pstmt.setString(4, daoObject.getRoa_default_menu_item());
         pstmt.setObject(5, daoObject.getRoa_rol_id());
         pstmt.setObject(6, daoObject.getRoa_mst_id());
         pstmt.setObject(7, daoObject.getRoa_fra_id());
         pstmt.setObject(8, daoObject.getRoa_frm_id());
         pstmt.setObject(9, daoObject.getRoa_assigned_rol_id());
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
            daoObject.setRoa_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprRoleActionsDAO updateRecord(Connection conn, SprRoleActionsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprRoleActionsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprRoleActionsDAO saveRecord(Connection conn, SprRoleActionsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}