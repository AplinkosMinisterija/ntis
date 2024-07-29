package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprOrgUsersDAO;
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

public class SprOrgUsersDBServiceGen extends SprBaseDBServiceImpl<SprOrgUsersDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprOrgUsersDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT OU_ID, OU_POSITION, OU_PROFILE_TOKEN, OU_DATE_FROM, OU_DATE_TO, OU_USR_ID, OU_ORG_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_ORG_USERS ";

   @Override
   public SprOrgUsersDAO newRecord() {
	  	  SprOrgUsersDAO daoObject = new SprOrgUsersDAO();
	  	  return daoObject;
	  }
   @Override
   public SprOrgUsersDAO object4ForceUpdate() {
       SprOrgUsersDAO daoObject = new SprOrgUsersDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprOrgUsersDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprOrgUsersDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprOrgUsersDAO> data = new ArrayList<SprOrgUsersDAO>();
      while (rs.next()) {
         data.add(new SprOrgUsersDAO(Utils.getDouble(rs.getObject("OU_ID")), //
                               rs.getString("OU_POSITION"), //
                               rs.getString("OU_PROFILE_TOKEN"), //
                               rs.getTimestamp("OU_DATE_FROM"), //
                               rs.getTimestamp("OU_DATE_TO"), //
                               Utils.getDouble(rs.getObject("OU_USR_ID")), //
                               Utils.getDouble(rs.getObject("OU_ORG_ID")), //
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
   public SprOrgUsersDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE OU_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprOrgUsersDAO> data = null;
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
      SprOrgUsersDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprOrgUsersDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_ORG_USERS WHERE OU_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprOrgUsersDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getOu_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprOrgUsersDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_ORG_USERS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprOrgUsersDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "OU_ORG_USER_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getOu_usr_id() != null || //
                    daoObject.getOu_org_id() != null || //
                    daoObject.getOu_date_to() != null) {
                String[] constraintColumns = { "ou_usr_id", "ou_org_id", "ou_date_to" };
                String stmt = "select 1 from SPR_ORG_USERS where " //
                        + constructStatementPart("ou_usr_id", daoObject.getOu_usr_id()) + " and " //
                        + constructStatementPart("ou_org_id", daoObject.getOu_org_id()) + " and " //
                        + constructStatementPart("ou_date_to", daoObject.getOu_date_to()) + " and " //
                        + (daoObject.getOu_id() != null ? "OU_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getOu_usr_id());
                    idx = setValueToStatement(pstmt, idx, daoObject.getOu_org_id());
                    idx = setValueToStatement(pstmt, idx, daoObject.getOu_date_to());
                    if (daoObject.getOu_id() != null) {
                        pstmt.setObject(++idx, daoObject.getOu_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("OU_ORG_USER_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprOrgUsersDAO insertRecord(Connection conn, SprOrgUsersDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_ORG_USERS (OU_POSITION, OU_PROFILE_TOKEN, OU_DATE_FROM, OU_DATE_TO, OU_USR_ID, OU_ORG_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING OU_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getOu_position());
         pstmt.setString(2, daoObject.getOu_profile_token());
         pstmt.setObject(3,  Utils.getSqlTimestamp(daoObject.getOu_date_from()));
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getOu_date_to()));
         pstmt.setObject(5, daoObject.getOu_usr_id());
         pstmt.setObject(6, daoObject.getOu_org_id());
         pstmt.setObject(7, daoObject.getN01());
         pstmt.setObject(8, daoObject.getN02());
         pstmt.setObject(9, daoObject.getN03());
         pstmt.setObject(10, daoObject.getN04());
         pstmt.setObject(11, daoObject.getN05());
         pstmt.setString(12, daoObject.getC01());
         pstmt.setString(13, daoObject.getC02());
         pstmt.setString(14, daoObject.getC03());
         pstmt.setString(15, daoObject.getC04());
         pstmt.setString(16, daoObject.getC05());
         pstmt.setObject(17,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(18,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(19,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(20,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(21,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(22, 1);
         pstmt.setObject(23,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(24,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(25, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setOu_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprOrgUsersDAO updateRecord(Connection conn, SprOrgUsersDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprOrgUsersDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprOrgUsersDAO saveRecord(Connection conn, SprOrgUsersDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}