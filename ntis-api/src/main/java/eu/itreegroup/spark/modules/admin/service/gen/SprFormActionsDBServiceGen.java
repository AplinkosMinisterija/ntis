package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprFormActionsDAO;
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

public class SprFormActionsDBServiceGen extends SprBaseDBServiceImpl<SprFormActionsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprFormActionsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT FRA_ID, FRA_CODE, FRA_NAME, FRA_DESCRIPTION, FRA_FRM_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_FORM_ACTIONS ";

   @Override
   public SprFormActionsDAO newRecord() {
	  	  SprFormActionsDAO daoObject = new SprFormActionsDAO();
	  	  return daoObject;
	  }
   @Override
   public SprFormActionsDAO object4ForceUpdate() {
       SprFormActionsDAO daoObject = new SprFormActionsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprFormActionsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprFormActionsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprFormActionsDAO> data = new ArrayList<SprFormActionsDAO>();
      while (rs.next()) {
         data.add(new SprFormActionsDAO(Utils.getDouble(rs.getObject("FRA_ID")), //
                               rs.getString("FRA_CODE"), //
                               rs.getString("FRA_NAME"), //
                               rs.getString("FRA_DESCRIPTION"), //
                               Utils.getDouble(rs.getObject("FRA_FRM_ID")), //
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
   public SprFormActionsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE FRA_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprFormActionsDAO> data = null;
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
      SprFormActionsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprFormActionsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_FORM_ACTIONS WHERE FRA_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprFormActionsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getFra_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprFormActionsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_FORM_ACTIONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprFormActionsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public SprFormActionsDAO insertRecord(Connection conn, SprFormActionsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_FORM_ACTIONS (FRA_CODE, FRA_NAME, FRA_DESCRIPTION, FRA_FRM_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING FRA_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getFra_code());
         pstmt.setString(2, daoObject.getFra_name());
         pstmt.setString(3, daoObject.getFra_description());
         pstmt.setObject(4, daoObject.getFra_frm_id());
         pstmt.setObject(5, daoObject.getN01());
         pstmt.setObject(6, daoObject.getN02());
         pstmt.setObject(7, daoObject.getN03());
         pstmt.setObject(8, daoObject.getN04());
         pstmt.setObject(9, daoObject.getN05());
         pstmt.setString(10, daoObject.getC01());
         pstmt.setString(11, daoObject.getC02());
         pstmt.setString(12, daoObject.getC03());
         pstmt.setString(13, daoObject.getC04());
         pstmt.setString(14, daoObject.getC05());
         pstmt.setObject(15,  Utils.getSqlDate(daoObject.getD01()));
         pstmt.setObject(16,  Utils.getSqlDate(daoObject.getD02()));
         pstmt.setObject(17,  Utils.getSqlDate(daoObject.getD03()));
         pstmt.setObject(18,  Utils.getSqlDate(daoObject.getD04()));
         pstmt.setObject(19,  Utils.getSqlDate(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(20, 1);
         pstmt.setObject(21,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(22,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(23, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setFra_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprFormActionsDAO updateRecord(Connection conn, SprFormActionsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprFormActionsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprFormActionsDAO saveRecord(Connection conn, SprFormActionsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}