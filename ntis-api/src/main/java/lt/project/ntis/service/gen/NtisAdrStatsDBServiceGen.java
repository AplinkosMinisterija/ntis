package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisAdrStatsDAO;
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

public class NtisAdrStatsDBServiceGen extends SprBaseDBServiceImpl<NtisAdrStatsDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisAdrStatsDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT ADS_ID, ADS_MUNICIPALITY_CODE, ADS_AOB_CODE, ADS_RESIDENCE_CODE, ADS_STREET_CODE, ADS_COORDINATE_LATITUDE, ADS_COORDINATE_LONGITUDE, ADS_NR, ADS_HOUSING_NR, ADS_POST_CODE, ADS_DATE_FROM, ADS_DATE_TO, ADS_RE_ID, ADS_STR_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_ADR_STATS ";

   @Override
   public NtisAdrStatsDAO newRecord() {
	  	  NtisAdrStatsDAO daoObject = new NtisAdrStatsDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisAdrStatsDAO object4ForceUpdate() {
       NtisAdrStatsDAO daoObject = new NtisAdrStatsDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisAdrStatsDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisAdrStatsDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisAdrStatsDAO> data = new ArrayList<NtisAdrStatsDAO>();
      while (rs.next()) {
         data.add(new NtisAdrStatsDAO(Utils.getDouble(rs.getObject("ADS_ID")), //
                               Utils.getDouble(rs.getObject("ADS_MUNICIPALITY_CODE")), //
                               Utils.getDouble(rs.getObject("ADS_AOB_CODE")), //
                               Utils.getDouble(rs.getObject("ADS_RESIDENCE_CODE")), //
                               Utils.getDouble(rs.getObject("ADS_STREET_CODE")), //
                               Utils.getDouble(rs.getObject("ADS_COORDINATE_LATITUDE")), //
                               Utils.getDouble(rs.getObject("ADS_COORDINATE_LONGITUDE")), //
                               rs.getString("ADS_NR"), //
                               rs.getString("ADS_HOUSING_NR"), //
                               rs.getString("ADS_POST_CODE"), //
                               rs.getTimestamp("ADS_DATE_FROM"), //
                               rs.getTimestamp("ADS_DATE_TO"), //
                               Utils.getDouble(rs.getObject("ADS_RE_ID")), //
                               Utils.getDouble(rs.getObject("ADS_STR_ID")), //
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
   public NtisAdrStatsDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE ADS_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisAdrStatsDAO> data = null;
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
      NtisAdrStatsDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisAdrStatsDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_ADR_STATS WHERE ADS_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisAdrStatsDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getAds_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisAdrStatsDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_ADR_STATS",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisAdrStatsDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisAdrStatsDAO insertRecord(Connection conn, NtisAdrStatsDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_ADR_STATS (ADS_MUNICIPALITY_CODE, ADS_AOB_CODE, ADS_RESIDENCE_CODE, ADS_STREET_CODE, ADS_COORDINATE_LATITUDE, ADS_COORDINATE_LONGITUDE, ADS_NR, ADS_HOUSING_NR, ADS_POST_CODE, ADS_DATE_FROM, ADS_DATE_TO, ADS_RE_ID, ADS_STR_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ADS_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getAds_municipality_code());
         pstmt.setObject(2, daoObject.getAds_aob_code());
         pstmt.setObject(3, daoObject.getAds_residence_code());
         pstmt.setObject(4, daoObject.getAds_street_code());
         pstmt.setObject(5, daoObject.getAds_coordinate_latitude());
         pstmt.setObject(6, daoObject.getAds_coordinate_longitude());
         pstmt.setString(7, daoObject.getAds_nr());
         pstmt.setString(8, daoObject.getAds_housing_nr());
         pstmt.setString(9, daoObject.getAds_post_code());
         pstmt.setObject(10,  Utils.getSqlTimestamp(daoObject.getAds_date_from()));
         pstmt.setObject(11,  Utils.getSqlTimestamp(daoObject.getAds_date_to()));
         pstmt.setObject(12, daoObject.getAds_re_id());
         pstmt.setObject(13, daoObject.getAds_str_id());
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
            daoObject.setAds_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisAdrStatsDAO updateRecord(Connection conn, NtisAdrStatsDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisAdrStatsDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisAdrStatsDAO saveRecord(Connection conn, NtisAdrStatsDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}