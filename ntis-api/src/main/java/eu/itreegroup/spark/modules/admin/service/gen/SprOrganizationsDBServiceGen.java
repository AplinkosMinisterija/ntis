package eu.itreegroup.spark.modules.admin.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import eu.itreegroup.spark.modules.admin.dao.SprOrganizationsDAO;
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

public class SprOrganizationsDBServiceGen extends SprBaseDBServiceImpl<SprOrganizationsDAO> {
  private static final Logger log = LoggerFactory.getLogger(SprOrganizationsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT ORG_ID, ORG_NAME, ORG_CODE, ORG_TYPE, ORG_REGION, ORG_PHONE, ORG_EMAIL, ORG_WEBSITE, ORG_ADDRESS, ORG_HOUSE_NUMBER, ORG_BANK_ACCOUNT, ORG_BANK_NAME, ORG_DELEGATION_PERSON, ORG_DELEGATION_PERSON_POSITION, ORG_DATE_FROM, ORG_DATE_TO, ORG_ORG_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM SPR_ORGANIZATIONS ";

   @Override
   public SprOrganizationsDAO newRecord() {
         SprOrganizationsDAO daoObject = new SprOrganizationsDAO();
         return daoObject;
     }
   @Override
   public SprOrganizationsDAO object4ForceUpdate() {
       SprOrganizationsDAO daoObject = new SprOrganizationsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return SprOrganizationsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<SprOrganizationsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<SprOrganizationsDAO> data = new ArrayList<SprOrganizationsDAO>();
      while (rs.next()) {
         data.add(new SprOrganizationsDAO(Utils.getDouble(rs.getObject("ORG_ID")), //
                               rs.getString("ORG_NAME"), //
                               rs.getString("ORG_CODE"), //
                               rs.getString("ORG_TYPE"), //
                               rs.getString("ORG_REGION"), //
                               rs.getString("ORG_PHONE"), //
                               rs.getString("ORG_EMAIL"), //
                               rs.getString("ORG_WEBSITE"), //
                               rs.getString("ORG_ADDRESS"), //
                               rs.getString("ORG_HOUSE_NUMBER"), //
                               rs.getString("ORG_BANK_ACCOUNT"), //
                               rs.getString("ORG_BANK_NAME"), //
                               rs.getString("ORG_DELEGATION_PERSON"), //
                               rs.getString("ORG_DELEGATION_PERSON_POSITION"), //
                               rs.getTimestamp("ORG_DATE_FROM"), //
                               rs.getTimestamp("ORG_DATE_TO"), //
                               Utils.getDouble(rs.getObject("ORG_ORG_ID")), //
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
   public SprOrganizationsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE ORG_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<SprOrganizationsDAO> data = null;
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
      SprOrganizationsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, SprOrganizationsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM SPR_ORGANIZATIONS WHERE ORG_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, SprOrganizationsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getOrg_id());
   }

   @Override
   public void validateConstraints(Connection conn, SprOrganizationsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("SPR_ORGANIZATIONS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, SprOrganizationsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "SPR_ORG_UQ".equalsIgnoreCase(constraintName)) {
            if (daoObject.getOrg_code() != null) {
                String[] constraintColumns = { "org_code" };
                String stmt = "select 1 from SPR_ORGANIZATIONS where " //
                        + constructStatementPart("org_code", daoObject.getOrg_code()) + " and " //
                        + (daoObject.getOrg_id() != null ? "ORG_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getOrg_code());
                    if (daoObject.getOrg_id() != null) {
                        pstmt.setObject(++idx, daoObject.getOrg_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("SPR_ORG_UQ", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public SprOrganizationsDAO insertRecord(Connection conn, SprOrganizationsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO SPR_ORGANIZATIONS (ORG_NAME, ORG_CODE, ORG_TYPE, ORG_REGION, ORG_PHONE, ORG_EMAIL, ORG_WEBSITE, ORG_ADDRESS, ORG_HOUSE_NUMBER, ORG_BANK_ACCOUNT, ORG_BANK_NAME, ORG_DELEGATION_PERSON, ORG_DELEGATION_PERSON_POSITION, ORG_DATE_FROM, ORG_DATE_TO, ORG_ORG_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ORG_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getOrg_name());
         pstmt.setString(2, daoObject.getOrg_code());
         pstmt.setString(3, daoObject.getOrg_type());
         pstmt.setString(4, daoObject.getOrg_region());
         pstmt.setString(5, daoObject.getOrg_phone());
         pstmt.setString(6, daoObject.getOrg_email());
         pstmt.setString(7, daoObject.getOrg_website());
         pstmt.setString(8, daoObject.getOrg_address());
         pstmt.setString(9, daoObject.getOrg_house_number());
         pstmt.setString(10, daoObject.getOrg_bank_account());
         pstmt.setString(11, daoObject.getOrg_bank_name());
         pstmt.setString(12, daoObject.getOrg_delegation_person());
         pstmt.setString(13, daoObject.getOrg_delegation_person_position());
         pstmt.setObject(14,  Utils.getSqlDate(daoObject.getOrg_date_from()));
         pstmt.setObject(15,  Utils.getSqlDate(daoObject.getOrg_date_to()));
         pstmt.setObject(16, daoObject.getOrg_org_id());
         pstmt.setObject(17, daoObject.getN01());
         pstmt.setObject(18, daoObject.getN02());
         pstmt.setObject(19, daoObject.getN03());
         pstmt.setObject(20, daoObject.getN04());
         pstmt.setObject(21, daoObject.getN05());
         pstmt.setString(22, daoObject.getC01());
         pstmt.setString(23, daoObject.getC02());
         pstmt.setString(24, daoObject.getC03());
         pstmt.setString(25, daoObject.getC04());
         pstmt.setString(26, daoObject.getC05());
         pstmt.setObject(27,  Utils.getSqlDate(daoObject.getD01()));
         pstmt.setObject(28,  Utils.getSqlDate(daoObject.getD02()));
         pstmt.setObject(29,  Utils.getSqlDate(daoObject.getD03()));
         pstmt.setObject(30,  Utils.getSqlDate(daoObject.getD04()));
         pstmt.setObject(31,  Utils.getSqlDate(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(32, 1);
         pstmt.setObject(33,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(34,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(35, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setOrg_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public SprOrganizationsDAO updateRecord(Connection conn, SprOrganizationsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         SprOrganizationsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public SprOrganizationsDAO saveRecord(Connection conn, SprOrganizationsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}