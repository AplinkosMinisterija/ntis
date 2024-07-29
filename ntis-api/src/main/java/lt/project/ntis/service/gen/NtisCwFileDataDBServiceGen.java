package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisCwFileDataDAO;
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

public class NtisCwFileDataDBServiceGen extends SprBaseDBServiceImpl<NtisCwFileDataDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisCwFileDataDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT CWFD_ID, CWFD_EIL_NR, CWFD_PASTATO_KODAS, CWFD_PATALPOS_KODAS, CWFD_PASTATO_ADR_KODAS, CWFD_SAVIVALDYBES_KODAS, CWFD_SAVIVALDYBE, CWFD_SENIUNIJOS_KODAS, CWFD_SENIUNIJA, CWFD_GYV_VIETOS_KODAS, CWFD_GYV_VIETA, CWFD_GATVES_KODAS, CWFD_GATVE, CWFD_PASTATO_NR, CWFD_KORPUSO_NR, CWFD_BUTO_NR, CWFD_STATINIO_VALD_KODAS, CWFD_VANDENTVARKOS_IM_KOD, CWFD_NUOT_SALINIMO_BUDAS, CWFD_PRIJUNGIMO_DATA, CWFD_ATJUNGIMO_DATA, CWFD_STATUS, CWFD_CWF_ID, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_CW_FILE_DATA ";

   @Override
   public NtisCwFileDataDAO newRecord() {
	  	  NtisCwFileDataDAO daoObject = new NtisCwFileDataDAO();
	  	  return daoObject;
	  }
   @Override
   public NtisCwFileDataDAO object4ForceUpdate() {
       NtisCwFileDataDAO daoObject = new NtisCwFileDataDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisCwFileDataDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisCwFileDataDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisCwFileDataDAO> data = new ArrayList<NtisCwFileDataDAO>();
      while (rs.next()) {
         data.add(new NtisCwFileDataDAO(Utils.getDouble(rs.getObject("CWFD_ID")), //
                               Utils.getDouble(rs.getObject("CWFD_EIL_NR")), //
                               rs.getString("CWFD_PASTATO_KODAS"), //
                               rs.getString("CWFD_PATALPOS_KODAS"), //
                               rs.getString("CWFD_PASTATO_ADR_KODAS"), //
                               rs.getString("CWFD_SAVIVALDYBES_KODAS"), //
                               rs.getString("CWFD_SAVIVALDYBE"), //
                               rs.getString("CWFD_SENIUNIJOS_KODAS"), //
                               rs.getString("CWFD_SENIUNIJA"), //
                               rs.getString("CWFD_GYV_VIETOS_KODAS"), //
                               rs.getString("CWFD_GYV_VIETA"), //
                               rs.getString("CWFD_GATVES_KODAS"), //
                               rs.getString("CWFD_GATVE"), //
                               rs.getString("CWFD_PASTATO_NR"), //
                               rs.getString("CWFD_KORPUSO_NR"), //
                               rs.getString("CWFD_BUTO_NR"), //
                               rs.getString("CWFD_STATINIO_VALD_KODAS"), //
                               rs.getString("CWFD_VANDENTVARKOS_IM_KOD"), //
                               rs.getString("CWFD_NUOT_SALINIMO_BUDAS"), //
                               rs.getString("CWFD_PRIJUNGIMO_DATA"), //
                               rs.getString("CWFD_ATJUNGIMO_DATA"), //
                               rs.getString("CWFD_STATUS"), //
                               Utils.getDouble(rs.getObject("CWFD_CWF_ID")), //
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
   public NtisCwFileDataDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE CWFD_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisCwFileDataDAO> data = null;
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
      NtisCwFileDataDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
	
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisCwFileDataDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_CW_FILE_DATA WHERE CWFD_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisCwFileDataDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getCwfd_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisCwFileDataDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_CW_FILE_DATA",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisCwFileDataDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisCwFileDataDAO insertRecord(Connection conn, NtisCwFileDataDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_CW_FILE_DATA (CWFD_EIL_NR, CWFD_PASTATO_KODAS, CWFD_PATALPOS_KODAS, CWFD_PASTATO_ADR_KODAS, CWFD_SAVIVALDYBES_KODAS, CWFD_SAVIVALDYBE, CWFD_SENIUNIJOS_KODAS, CWFD_SENIUNIJA, CWFD_GYV_VIETOS_KODAS, CWFD_GYV_VIETA, CWFD_GATVES_KODAS, CWFD_GATVE, CWFD_PASTATO_NR, CWFD_KORPUSO_NR, CWFD_BUTO_NR, CWFD_STATINIO_VALD_KODAS, CWFD_VANDENTVARKOS_IM_KOD, CWFD_NUOT_SALINIMO_BUDAS, CWFD_PRIJUNGIMO_DATA, CWFD_ATJUNGIMO_DATA, CWFD_STATUS, CWFD_CWF_ID, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING CWFD_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getCwfd_eil_nr());
         pstmt.setString(2, daoObject.getCwfd_pastato_kodas());
         pstmt.setString(3, daoObject.getCwfd_patalpos_kodas());
         pstmt.setString(4, daoObject.getCwfd_pastato_adr_kodas());
         pstmt.setString(5, daoObject.getCwfd_savivaldybes_kodas());
         pstmt.setString(6, daoObject.getCwfd_savivaldybe());
         pstmt.setString(7, daoObject.getCwfd_seniunijos_kodas());
         pstmt.setString(8, daoObject.getCwfd_seniunija());
         pstmt.setString(9, daoObject.getCwfd_gyv_vietos_kodas());
         pstmt.setString(10, daoObject.getCwfd_gyv_vieta());
         pstmt.setString(11, daoObject.getCwfd_gatves_kodas());
         pstmt.setString(12, daoObject.getCwfd_gatve());
         pstmt.setString(13, daoObject.getCwfd_pastato_nr());
         pstmt.setString(14, daoObject.getCwfd_korpuso_nr());
         pstmt.setString(15, daoObject.getCwfd_buto_nr());
         pstmt.setString(16, daoObject.getCwfd_statinio_vald_kodas());
         pstmt.setString(17, daoObject.getCwfd_vandentvarkos_im_kod());
         pstmt.setString(18, daoObject.getCwfd_nuot_salinimo_budas());
         pstmt.setString(19, daoObject.getCwfd_prijungimo_data());
         pstmt.setString(20, daoObject.getCwfd_atjungimo_data());
         pstmt.setString(21, daoObject.getCwfd_status());
         pstmt.setObject(22, daoObject.getCwfd_cwf_id());
         pstmt.setObject(23, daoObject.getN01());
         pstmt.setObject(24, daoObject.getN02());
         pstmt.setObject(25, daoObject.getN03());
         pstmt.setObject(26, daoObject.getN04());
         pstmt.setObject(27, daoObject.getN05());
         pstmt.setString(28, daoObject.getC01());
         pstmt.setString(29, daoObject.getC02());
         pstmt.setString(30, daoObject.getC03());
         pstmt.setString(31, daoObject.getC04());
         pstmt.setString(32, daoObject.getC05());
         pstmt.setObject(33,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(34,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(35,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(36,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(37,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(38, 1);
         pstmt.setObject(39,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(40,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(41, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setCwfd_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisCwFileDataDAO updateRecord(Connection conn, NtisCwFileDataDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisCwFileDataDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisCwFileDataDAO saveRecord(Connection conn, NtisCwFileDataDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}