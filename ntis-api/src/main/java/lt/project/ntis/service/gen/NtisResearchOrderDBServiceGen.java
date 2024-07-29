package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisResearchOrderDAO;
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

public class NtisResearchOrderDBServiceGen extends SprBaseDBServiceImpl<NtisResearchOrderDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisResearchOrderDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT RO_ID, RO_ADDITIONAL_DESCRIPTION, RO_DATE_FROM, RO_CREATED_IN_NTIS_PORTAL, RO_PHONE_NUMBER, RO_REJECTION_REASON, RO_COMPLIANCE_NORMS, RO_STATE, RO_CREATED, RO_EMAIL, RO_DATE_TO, RO_WTF_ID, RO_ORG_ID, RO_FIL_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_RESEARCH_ORDER ";

   @Override
   public NtisResearchOrderDAO newRecord() {
	  	  NtisResearchOrderDAO daoObject = new NtisResearchOrderDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisResearchOrderDAO object4ForceUpdate() {
       NtisResearchOrderDAO daoObject = new NtisResearchOrderDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisResearchOrderDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisResearchOrderDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisResearchOrderDAO> data = new ArrayList<NtisResearchOrderDAO>();
      while (rs.next()) {
         data.add(new NtisResearchOrderDAO(Utils.getDouble(rs.getObject("RO_ID")), //
                               rs.getString("RO_ADDITIONAL_DESCRIPTION"), //
                               rs.getString("RO_DATE_FROM"), //
                               rs.getString("RO_CREATED_IN_NTIS_PORTAL"), //
                               rs.getString("RO_PHONE_NUMBER"), //
                               rs.getString("RO_REJECTION_REASON"), //
                               rs.getString("RO_COMPLIANCE_NORMS"), //
                               rs.getString("RO_STATE"), //
                               rs.getString("RO_CREATED"), //
                               rs.getString("RO_EMAIL"), //
                               rs.getString("RO_DATE_TO"), //
                               Utils.getDouble(rs.getObject("RO_WTF_ID")), //
                               Utils.getDouble(rs.getObject("RO_ORG_ID")), //
                               Utils.getDouble(rs.getObject("RO_FIL_ID")), //
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
   public NtisResearchOrderDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE RO_ID = ?";
      log.debug("load stmt: "+stmt);
      List<NtisResearchOrderDAO> data = null;
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
      NtisResearchOrderDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisResearchOrderDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_RESEARCH_ORDER WHERE RO_ID=?";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisResearchOrderDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getRo_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisResearchOrderDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_RESEARCH_ORDER",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisResearchOrderDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisResearchOrderDAO insertRecord(Connection conn, NtisResearchOrderDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_RESEARCH_ORDER (RO_ADDITIONAL_DESCRIPTION, RO_DATE_FROM, RO_CREATED_IN_NTIS_PORTAL, RO_PHONE_NUMBER, RO_REJECTION_REASON, RO_COMPLIANCE_NORMS, RO_STATE, RO_CREATED, RO_EMAIL, RO_DATE_TO, RO_WTF_ID, RO_ORG_ID, RO_FIL_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING RO_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getRo_additional_description());
         pstmt.setString(2, daoObject.getRo_date_from());
         pstmt.setString(3, daoObject.getRo_created_in_ntis_portal());
         pstmt.setString(4, daoObject.getRo_phone_number());
         pstmt.setString(5, daoObject.getRo_rejection_reason());
         pstmt.setString(6, daoObject.getRo_compliance_norms());
         pstmt.setString(7, daoObject.getRo_state());
         pstmt.setString(8, daoObject.getRo_created());
         pstmt.setString(9, daoObject.getRo_email());
         pstmt.setString(10, daoObject.getRo_date_to());
         pstmt.setObject(11, daoObject.getRo_wtf_id());
         pstmt.setObject(12, daoObject.getRo_org_id());
         pstmt.setObject(13, daoObject.getRo_fil_id());
         pstmt.setObject(14, daoObject.getN01());
         pstmt.setObject(15, daoObject.getN02());
         pstmt.setObject(16, daoObject.getN03());
         pstmt.setObject(17, daoObject.getN04());
         pstmt.setObject(18, daoObject.getN05());
         pstmt.setString(19, daoObject.getC01());
         pstmt.setString(20, daoObject.getC02());
         pstmt.setString(21, daoObject.getC03());
         pstmt.setString(22, daoObject.getC04());
         pstmt.setString(23, daoObject.getC05());
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(25,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(26,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(27,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(28,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(29, 1);
         pstmt.setObject(30,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(31,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(32, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setRo_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisResearchOrderDAO updateRecord(Connection conn, NtisResearchOrderDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisResearchOrderDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisResearchOrderDAO saveRecord(Connection conn, NtisResearchOrderDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}