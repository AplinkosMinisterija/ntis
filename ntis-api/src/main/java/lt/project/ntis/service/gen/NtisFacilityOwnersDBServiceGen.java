package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisFacilityOwnersDAO;
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

public class NtisFacilityOwnersDBServiceGen extends SprBaseDBServiceImpl<NtisFacilityOwnersDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisFacilityOwnersDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT FO_ID, FO_OWNER_TYPE, FO_SELECTED, FO_DATE_FROM, FO_DATE_TO, FO_ORG_ID, FO_PER_ID, FO_WTF_ID, FO_SO_ID, FO_MANAGING_PER_ID, FO_MANAGING_ORG_ID, FO_FO_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_FACILITY_OWNERS ";

   @Override
   public NtisFacilityOwnersDAO newRecord() {
         NtisFacilityOwnersDAO daoObject = new NtisFacilityOwnersDAO();
         return daoObject;
     }
   @Override
   public NtisFacilityOwnersDAO object4ForceUpdate() {
       NtisFacilityOwnersDAO daoObject = new NtisFacilityOwnersDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisFacilityOwnersDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisFacilityOwnersDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisFacilityOwnersDAO> data = new ArrayList<NtisFacilityOwnersDAO>();
      while (rs.next()) {
         data.add(new NtisFacilityOwnersDAO(Utils.getDouble(rs.getObject("FO_ID")), //
                               rs.getString("FO_OWNER_TYPE"), //
                               rs.getString("FO_SELECTED"), //
                               rs.getTimestamp("FO_DATE_FROM"), //
                               rs.getTimestamp("FO_DATE_TO"), //
                               Utils.getDouble(rs.getObject("FO_ORG_ID")), //
                               Utils.getDouble(rs.getObject("FO_PER_ID")), //
                               Utils.getDouble(rs.getObject("FO_WTF_ID")), //
                               Utils.getDouble(rs.getObject("FO_SO_ID")), //
                               Utils.getDouble(rs.getObject("FO_MANAGING_PER_ID")), //
                               Utils.getDouble(rs.getObject("FO_MANAGING_ORG_ID")), //
                               Utils.getDouble(rs.getObject("FO_FO_ID")), //
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
   public NtisFacilityOwnersDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE FO_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisFacilityOwnersDAO> data = null;
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
      NtisFacilityOwnersDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisFacilityOwnersDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_FACILITY_OWNERS WHERE FO_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisFacilityOwnersDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getFo_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisFacilityOwnersDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_FACILITY_OWNERS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisFacilityOwnersDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "_TREATMENT_FACILITY_OWNER_U_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getFo_owner_type() != null || //
                    daoObject.getFo_date_to() != null || //
                    daoObject.getFo_per_id() != null || //
                    daoObject.getFo_org_id() != null || //
                    daoObject.getFo_wtf_id() != null || //
                    daoObject.getFo_so_id() != null) {
                String[] constraintColumns = { "fo_owner_type", "fo_date_to", "fo_per_id", "fo_org_id", "fo_wtf_id", "fo_so_id" };
                String stmt = "select 1 from NTIS_FACILITY_OWNERS where " //
                        + constructStatementPart("fo_owner_type", daoObject.getFo_owner_type()) + " and " //
                        + constructStatementPart("fo_date_to", daoObject.getFo_date_to()) + " and " //
                        + constructStatementPart("fo_per_id", daoObject.getFo_per_id()) + " and " //
                        + constructStatementPart("fo_org_id", daoObject.getFo_org_id()) + " and " //
                        + constructStatementPart("fo_wtf_id", daoObject.getFo_wtf_id()) + " and " //
                        + constructStatementPart("fo_so_id", daoObject.getFo_so_id()) + " and " //
                        + (daoObject.getFo_id() != null ? "FO_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getFo_owner_type());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFo_date_to());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFo_per_id());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFo_org_id());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFo_wtf_id());
                    idx = setValueToStatement(pstmt, idx, daoObject.getFo_so_id());
                    if (daoObject.getFo_id() != null) {
                        pstmt.setObject(++idx, daoObject.getFo_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("_TREATMENT_FACILITY_OWNER_U_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public NtisFacilityOwnersDAO insertRecord(Connection conn, NtisFacilityOwnersDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_FACILITY_OWNERS (FO_OWNER_TYPE, FO_SELECTED, FO_DATE_FROM, FO_DATE_TO, FO_ORG_ID, FO_PER_ID, FO_WTF_ID, FO_SO_ID, FO_MANAGING_PER_ID, FO_MANAGING_ORG_ID, FO_FO_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING FO_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getFo_owner_type());
         pstmt.setString(2, daoObject.getFo_selected());
         pstmt.setObject(3,  Utils.getSqlTimestamp(daoObject.getFo_date_from()));
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getFo_date_to()));
         pstmt.setObject(5, daoObject.getFo_org_id());
         pstmt.setObject(6, daoObject.getFo_per_id());
         pstmt.setObject(7, daoObject.getFo_wtf_id());
         pstmt.setObject(8, daoObject.getFo_so_id());
         pstmt.setObject(9, daoObject.getFo_managing_per_id());
         pstmt.setObject(10, daoObject.getFo_managing_org_id());
         pstmt.setObject(11, daoObject.getFo_fo_id());
         pstmt.setObject(12, daoObject.getN01());
         pstmt.setObject(13, daoObject.getN02());
         pstmt.setObject(14, daoObject.getN03());
         pstmt.setObject(15, daoObject.getN04());
         pstmt.setObject(16, daoObject.getN05());
         pstmt.setString(17, daoObject.getC01());
         pstmt.setString(18, daoObject.getC02());
         pstmt.setString(19, daoObject.getC03());
         pstmt.setString(20, daoObject.getC04());
         pstmt.setString(21, daoObject.getC05());
         pstmt.setObject(22,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(23,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(24,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(25,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(26,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(27, 1);
         pstmt.setObject(28,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(29,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(30, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setFo_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisFacilityOwnersDAO updateRecord(Connection conn, NtisFacilityOwnersDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisFacilityOwnersDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisFacilityOwnersDAO saveRecord(Connection conn, NtisFacilityOwnersDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}