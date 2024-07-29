package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisMunicipalitiesDAO;
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

public class NtisMunicipalitiesDBServiceGen extends SprBaseDBServiceImpl<NtisMunicipalitiesDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisMunicipalitiesDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT MP_ID, MP_CODE, MP_NAME, MP_DATE_FROM, MP_DATE_TO, REC_USERID, REC_TIMESTAMP, REC_CREATE_TIMESTAMP, REC_VERSION FROM NTIS_MUNICIPALITIES ";

   @Override
   public NtisMunicipalitiesDAO newRecord() {
	  	  NtisMunicipalitiesDAO daoObject = new NtisMunicipalitiesDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisMunicipalitiesDAO object4ForceUpdate() {
       NtisMunicipalitiesDAO daoObject = new NtisMunicipalitiesDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisMunicipalitiesDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisMunicipalitiesDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisMunicipalitiesDAO> data = new ArrayList<NtisMunicipalitiesDAO>();
      while (rs.next()) {
         data.add(new NtisMunicipalitiesDAO(Utils.getDouble(rs.getObject("MP_ID")), //
                               rs.getString("MP_CODE"), //
                               rs.getString("MP_NAME"), //
                               rs.getTimestamp("MP_DATE_FROM"), //
                               rs.getTimestamp("MP_DATE_TO"), //
                               rs.getString("REC_USERID"), //
                               rs.getTimestamp("REC_TIMESTAMP"), //
                               rs.getTimestamp("REC_CREATE_TIMESTAMP"), //
                               Utils.getDouble(rs.getObject("REC_VERSION"))));
         }
      return data;
   }

   @Override
   public NtisMunicipalitiesDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE MP_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisMunicipalitiesDAO> data = null;
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
      NtisMunicipalitiesDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisMunicipalitiesDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_MUNICIPALITIES WHERE MP_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisMunicipalitiesDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getMp_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisMunicipalitiesDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_MUNICIPALITIES",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisMunicipalitiesDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisMunicipalitiesDAO insertRecord(Connection conn, NtisMunicipalitiesDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_MUNICIPALITIES (MP_CODE, MP_NAME, MP_DATE_FROM, MP_DATE_TO, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?) RETURNING MP_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setString(1, daoObject.getMp_code());
         pstmt.setString(2, daoObject.getMp_name());
         pstmt.setObject(3,  Utils.getSqlTimestamp(daoObject.getMp_date_from()));
         pstmt.setObject(4,  Utils.getSqlTimestamp(daoObject.getMp_date_to()));
         //Record audit information (start)
         pstmt.setInt(5, 1);
         pstmt.setObject(6,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(7,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(8, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setMp_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisMunicipalitiesDAO updateRecord(Connection conn, NtisMunicipalitiesDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisMunicipalitiesDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisMunicipalitiesDAO saveRecord(Connection conn, NtisMunicipalitiesDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}