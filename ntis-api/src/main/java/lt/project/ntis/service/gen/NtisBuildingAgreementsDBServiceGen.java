package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisBuildingAgreementsDAO;
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

public class NtisBuildingAgreementsDBServiceGen extends SprBaseDBServiceImpl<NtisBuildingAgreementsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisBuildingAgreementsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT BA_ID, BA_SOURCE, BA_WASTEWATER_TREATMENT, BA_STATE, BA_REJECTION_REASON, BA_NETWORK_CONNECTION_DATE, BA_NETWORK_DISCONNECTION_DATE, BA_CREATED, BA_BN_ID, BA_FIL_ID, BA_ORG_ID, BA_MANUAL_NETWORK_CON_DATE, BA_MANUAL_ORG_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_BUILDING_AGREEMENTS ";

   @Override
   public NtisBuildingAgreementsDAO newRecord() {
	  	  NtisBuildingAgreementsDAO daoObject = new NtisBuildingAgreementsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisBuildingAgreementsDAO object4ForceUpdate() {
       NtisBuildingAgreementsDAO daoObject = new NtisBuildingAgreementsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisBuildingAgreementsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisBuildingAgreementsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisBuildingAgreementsDAO> data = new ArrayList<NtisBuildingAgreementsDAO>();
      while (rs.next()) {
         data.add(new NtisBuildingAgreementsDAO(Utils.getDouble(rs.getObject("BA_ID")), //
                               rs.getString("BA_SOURCE"), //
                               rs.getString("BA_WASTEWATER_TREATMENT"), //
                               rs.getString("BA_STATE"), //
                               rs.getString("BA_REJECTION_REASON"), //
                               rs.getTimestamp("BA_NETWORK_CONNECTION_DATE"), //
                               rs.getTimestamp("BA_NETWORK_DISCONNECTION_DATE"), //
                               rs.getTimestamp("BA_CREATED"), //
                               Utils.getDouble(rs.getObject("BA_BN_ID")), //
                               Utils.getDouble(rs.getObject("BA_FIL_ID")), //
                               Utils.getDouble(rs.getObject("BA_ORG_ID")), //
                               rs.getTimestamp("BA_MANUAL_NETWORK_CON_DATE"), //
                               Utils.getDouble(rs.getObject("BA_MANUAL_ORG_ID")), //
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
   public NtisBuildingAgreementsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE BA_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisBuildingAgreementsDAO> data = null;
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
      NtisBuildingAgreementsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisBuildingAgreementsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_BUILDING_AGREEMENTS WHERE BA_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisBuildingAgreementsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getBa_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisBuildingAgreementsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_BUILDING_AGREEMENTS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisBuildingAgreementsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        if (constraintName == null || "BA_BA_UK".equalsIgnoreCase(constraintName)) {
            if (daoObject.getBa_bn_id() != null) {
                String[] constraintColumns = { "ba_bn_id" };
                String stmt = "select 1 from NTIS_BUILDING_AGREEMENTS where " //
                        + constructStatementPart("ba_bn_id", daoObject.getBa_bn_id()) + " and " //
                        + (daoObject.getBa_id() != null ? "BA_ID != ?::int" : " 1=1");
                try (PreparedStatement pstmt = conn.prepareStatement(stmt)) {
                    int idx = 0;
                    idx = setValueToStatement(pstmt, idx, daoObject.getBa_bn_id());
                    if (daoObject.getBa_id() != null) {
                        pstmt.setObject(++idx, daoObject.getBa_id());
                    }
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            answer.add(new S2ViolatedConstraint("BA_BA_UK", new ArrayList<String>(Arrays.asList(constraintColumns))));
                        }
                    }
                }
            }
        }
        return answer;
    }

   @Override
   public NtisBuildingAgreementsDAO insertRecord(Connection conn, NtisBuildingAgreementsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_BUILDING_AGREEMENTS (BA_SOURCE, BA_WASTEWATER_TREATMENT, BA_STATE, BA_REJECTION_REASON, BA_NETWORK_CONNECTION_DATE, BA_NETWORK_DISCONNECTION_DATE, BA_CREATED, BA_BN_ID, BA_FIL_ID, BA_ORG_ID, BA_MANUAL_NETWORK_CON_DATE, BA_MANUAL_ORG_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING BA_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getBa_source());
         pstmt.setString(2, daoObject.getBa_wastewater_treatment());
         pstmt.setString(3, daoObject.getBa_state());
         pstmt.setString(4, daoObject.getBa_rejection_reason());
         pstmt.setObject(5,  Utils.getSqlTimestamp(daoObject.getBa_network_connection_date()));
         pstmt.setObject(6,  Utils.getSqlTimestamp(daoObject.getBa_network_disconnection_date()));
         pstmt.setObject(7,  Utils.getSqlTimestamp(daoObject.getBa_created()));
         pstmt.setObject(8, daoObject.getBa_bn_id());
         pstmt.setObject(9, daoObject.getBa_fil_id());
         pstmt.setObject(10, daoObject.getBa_org_id());
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getBa_manual_network_con_date()));
         pstmt.setObject(12, daoObject.getBa_manual_org_id());
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
            daoObject.setBa_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisBuildingAgreementsDAO updateRecord(Connection conn, NtisBuildingAgreementsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisBuildingAgreementsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisBuildingAgreementsDAO saveRecord(Connection conn, NtisBuildingAgreementsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}