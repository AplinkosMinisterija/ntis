package lt.project.ntis.service.gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import lt.project.ntis.dao.NtisOrderFileDataDAO;
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

public class NtisOrderFileDataDBServiceGen extends SprBaseDBServiceImpl<NtisOrderFileDataDAO> {
  private static final Logger log = LoggerFactory.getLogger(NtisOrderFileDataDBServiceGen.class);
    protected static String TABLE_RECORD_STMT = "SELECT ORFD_ID, ORFD_EIL_NR, ORFD_WTF_ID, ORFD_ORF_ID, ORFD_ORG_ID, ORFD_PASLAUGOS_KODAS, ORFD_PASTATO_KODAS, ORFD_PATALPOS_KODAS, ORFD_PASTATO_ADR_KODAS, ORFD_ADRESAS, ORFD_SAVIVALDYBES_KODAS, ORFD_SAVIVALDYBE, ORFD_SENIUNIJOS_KODAS, ORFD_SENIUNIJA, ORFD_GYV_VIETOS_KODAS, ORFD_GYV_VIETA, ORFD_GATVES_KODAS, ORFD_GATVE, ORFD_PASTATO_NR, ORFD_KORPUSO_NR, ORFD_BUTO_NR, ORFD_PASLAUGA, ORFD_UZSAKYMO_DATA, ORFD_ATLIKIMO_DATA, ORFD_ISVEZIMO_DATA, ORFD_ISVEZTAS_KIEKIS, ORFD_TRANSPORTO_PRIEMONE, ORFD_CR_ID, ORFD_UZSAKYMO_INFORMACIJA, ORFD_ATLIKTI_DARBAI, ORFD_UZSAKOVAS, ORFD_UZSAKOVO_EMAIL, ORFD_UZSAKOVO_TEL, ORFD_UZSAKOVO_KOMENTARAS, ORFD_LABORATORIJOS_KOMENTARAS, ORFD_DEGUONIS, ORFD_SKENDINCIOS, ORFD_AZOTAS, ORFD_FOSFORAS, ORFD_MEGINIO_DATA, ORFD_MEGINIO_DARBUOTOJAS, ORFD_TYRIMO_DATA, ORFD_TYRIMO_DARBUOTOJAS, ORFD_PASTABA_REZULTATAMS, REC_VERSION, REC_CREATE_TIMESTAMP, REC_USERID, REC_TIMESTAMP, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05 FROM NTIS_ORDER_FILE_DATA ";

   @Override
   public NtisOrderFileDataDAO newRecord() {
         NtisOrderFileDataDAO daoObject = new NtisOrderFileDataDAO();
         return daoObject;
     }
   @Override
   public NtisOrderFileDataDAO object4ForceUpdate() {
       NtisOrderFileDataDAO daoObject = new NtisOrderFileDataDAO();
       daoObject.setForceUpdate(true);
       return daoObject;
   }
   @Override
    public String getTableRecordStatement() {
        return NtisOrderFileDataDBServiceGen.TABLE_RECORD_STMT;
    }
   @Override
   protected List<NtisOrderFileDataDAO> setDBDataToObject(ResultSet rs) throws Exception { 
      List<NtisOrderFileDataDAO> data = new ArrayList<NtisOrderFileDataDAO>();
      while (rs.next()) {
         data.add(new NtisOrderFileDataDAO(Utils.getDouble(rs.getObject("ORFD_ID")), //
                               Utils.getDouble(rs.getObject("ORFD_EIL_NR")), //
                               Utils.getDouble(rs.getObject("ORFD_WTF_ID")), //
                               Utils.getDouble(rs.getObject("ORFD_ORF_ID")), //
                               Utils.getDouble(rs.getObject("ORFD_ORG_ID")), //
                               rs.getString("ORFD_PASLAUGOS_KODAS"), //
                               rs.getString("ORFD_PASTATO_KODAS"), //
                               rs.getString("ORFD_PATALPOS_KODAS"), //
                               rs.getString("ORFD_PASTATO_ADR_KODAS"), //
                               rs.getString("ORFD_ADRESAS"), //
                               rs.getString("ORFD_SAVIVALDYBES_KODAS"), //
                               rs.getString("ORFD_SAVIVALDYBE"), //
                               rs.getString("ORFD_SENIUNIJOS_KODAS"), //
                               rs.getString("ORFD_SENIUNIJA"), //
                               rs.getString("ORFD_GYV_VIETOS_KODAS"), //
                               rs.getString("ORFD_GYV_VIETA"), //
                               rs.getString("ORFD_GATVES_KODAS"), //
                               rs.getString("ORFD_GATVE"), //
                               rs.getString("ORFD_PASTATO_NR"), //
                               rs.getString("ORFD_KORPUSO_NR"), //
                               rs.getString("ORFD_BUTO_NR"), //
                               rs.getString("ORFD_PASLAUGA"), //
                               rs.getString("ORFD_UZSAKYMO_DATA"), //
                               rs.getString("ORFD_ATLIKIMO_DATA"), //
                               rs.getString("ORFD_ISVEZIMO_DATA"), //
                               rs.getString("ORFD_ISVEZTAS_KIEKIS"), //
                               rs.getString("ORFD_TRANSPORTO_PRIEMONE"), //
                               Utils.getDouble(rs.getObject("ORFD_CR_ID")), //
                               rs.getString("ORFD_UZSAKYMO_INFORMACIJA"), //
                               rs.getString("ORFD_ATLIKTI_DARBAI"), //
                               rs.getString("ORFD_UZSAKOVAS"), //
                               rs.getString("ORFD_UZSAKOVO_EMAIL"), //
                               rs.getString("ORFD_UZSAKOVO_TEL"), //
                               rs.getString("ORFD_UZSAKOVO_KOMENTARAS"), //
                               rs.getString("ORFD_LABORATORIJOS_KOMENTARAS"), //
                               rs.getString("ORFD_DEGUONIS"), //
                               rs.getString("ORFD_SKENDINCIOS"), //
                               rs.getString("ORFD_AZOTAS"), //
                               rs.getString("ORFD_FOSFORAS"), //
                               rs.getString("ORFD_MEGINIO_DATA"), //
                               rs.getString("ORFD_MEGINIO_DARBUOTOJAS"), //
                               rs.getString("ORFD_TYRIMO_DATA"), //
                               rs.getString("ORFD_TYRIMO_DARBUOTOJAS"), //
                               rs.getString("ORFD_PASTABA_REZULTATAMS"), //
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
   public NtisOrderFileDataDAO loadRecord(Connection conn, Object identifier) throws Exception{
        String stmt = getTableRecordStatement() + " WHERE ORFD_ID = ?::int";
      log.debug("load stmt: "+stmt);
      List<NtisOrderFileDataDAO> data = null;
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
      NtisOrderFileDataDAO daoObject = data.get(0);
      daoObject.setRecordLoaded(true);
      return daoObject;
   
   }
   @Override
   public void deleteRecord(Connection conn, Double identifier) throws Exception{
      manageForeignKeysOnDelete(conn, NtisOrderFileDataDAO.IDENTIFIER, identifier);
      String stmt = "DELETE FROM NTIS_ORDER_FILE_DATA WHERE ORFD_ID=?::int";
      log.debug("delete stmt: "+stmt);
      try(PreparedStatement pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, identifier);
         pstmt.execute();
      }catch(Exception ex){
          throw ex;
      }
   }
   public void deleteRecord(Connection conn, NtisOrderFileDataDAO daoObject) throws Exception{
      deleteRecord(conn, daoObject.getOrfd_id());
   }

   @Override
   public void validateConstraints(Connection conn, NtisOrderFileDataDAO daoObject, String constraintName) throws Exception {
       List<S2ViolatedConstraint> violatedConstraints = getViolatedConstraints(conn, daoObject, constraintName);
       if (violatedConstraints.size()>0){
           List<S2Message> messages = new ArrayList<S2Message>();
           for (int i=0; i<violatedConstraints.size(); i++){
               String[] columns = violatedConstraints.get(i).getFields().toArray(new String[violatedConstraints.get(i).getFields().size()]);
               messages.add(new S2Message("NTIS_ORDER_FILE_DATA",  violatedConstraints.get(i).getName(), SprBaseDAO.DB_ERROR_UK_CONSTRAINT, SparkMessageType.ERROR, columns));
           }
           throw new SparkBusinessException(messages.toArray(new S2Message[messages.size()]));
       }
   }
    @Override
    public List<S2ViolatedConstraint> getViolatedConstraints(Connection conn, NtisOrderFileDataDAO daoObject, String constraintName) throws Exception {
        List<S2ViolatedConstraint> answer = new ArrayList<S2ViolatedConstraint>();
        return answer;
    }

   @Override
   public NtisOrderFileDataDAO insertRecord(Connection conn, NtisOrderFileDataDAO daoObject) throws Exception{
      daoObject.validateObject(Utils.OPERATION_INSERT, this);
      this.validateConstraints(conn, daoObject, null);
      String stmt = "INSERT INTO NTIS_ORDER_FILE_DATA (ORFD_EIL_NR, ORFD_WTF_ID, ORFD_ORF_ID, ORFD_ORG_ID, ORFD_PASLAUGOS_KODAS, ORFD_PASTATO_KODAS, ORFD_PATALPOS_KODAS, ORFD_PASTATO_ADR_KODAS, ORFD_ADRESAS, ORFD_SAVIVALDYBES_KODAS, ORFD_SAVIVALDYBE, ORFD_SENIUNIJOS_KODAS, ORFD_SENIUNIJA, ORFD_GYV_VIETOS_KODAS, ORFD_GYV_VIETA, ORFD_GATVES_KODAS, ORFD_GATVE, ORFD_PASTATO_NR, ORFD_KORPUSO_NR, ORFD_BUTO_NR, ORFD_PASLAUGA, ORFD_UZSAKYMO_DATA, ORFD_ATLIKIMO_DATA, ORFD_ISVEZIMO_DATA, ORFD_ISVEZTAS_KIEKIS, ORFD_TRANSPORTO_PRIEMONE, ORFD_CR_ID, ORFD_UZSAKYMO_INFORMACIJA, ORFD_ATLIKTI_DARBAI, ORFD_UZSAKOVAS, ORFD_UZSAKOVO_EMAIL, ORFD_UZSAKOVO_TEL, ORFD_UZSAKOVO_KOMENTARAS, ORFD_LABORATORIJOS_KOMENTARAS, ORFD_DEGUONIS, ORFD_SKENDINCIOS, ORFD_AZOTAS, ORFD_FOSFORAS, ORFD_MEGINIO_DATA, ORFD_MEGINIO_DARBUOTOJAS, ORFD_TYRIMO_DATA, ORFD_TYRIMO_DARBUOTOJAS, ORFD_PASTABA_REZULTATAMS, N01, N02, N03, N04, N05, C01, C02, C03, C04, C05, D01, D02, D03, D04, D05, rec_version, rec_create_timestamp, rec_timestamp, rec_userid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING ORFD_ID;";
      log.debug("insert stmt: "+stmt);
      try(PreparedStatement  pstmt = conn.prepareStatement(stmt)){
         pstmt.setObject(1, daoObject.getOrfd_eil_nr());
         pstmt.setObject(2, daoObject.getOrfd_wtf_id());
         pstmt.setObject(3, daoObject.getOrfd_orf_id());
         pstmt.setObject(4, daoObject.getOrfd_org_id());
         pstmt.setString(5, daoObject.getOrfd_paslaugos_kodas());
         pstmt.setString(6, daoObject.getOrfd_pastato_kodas());
         pstmt.setString(7, daoObject.getOrfd_patalpos_kodas());
         pstmt.setString(8, daoObject.getOrfd_pastato_adr_kodas());
         pstmt.setString(9, daoObject.getOrfd_adresas());
         pstmt.setString(10, daoObject.getOrfd_savivaldybes_kodas());
         pstmt.setString(11, daoObject.getOrfd_savivaldybe());
         pstmt.setString(12, daoObject.getOrfd_seniunijos_kodas());
         pstmt.setString(13, daoObject.getOrfd_seniunija());
         pstmt.setString(14, daoObject.getOrfd_gyv_vietos_kodas());
         pstmt.setString(15, daoObject.getOrfd_gyv_vieta());
         pstmt.setString(16, daoObject.getOrfd_gatves_kodas());
         pstmt.setString(17, daoObject.getOrfd_gatve());
         pstmt.setString(18, daoObject.getOrfd_pastato_nr());
         pstmt.setString(19, daoObject.getOrfd_korpuso_nr());
         pstmt.setString(20, daoObject.getOrfd_buto_nr());
         pstmt.setString(21, daoObject.getOrfd_paslauga());
         pstmt.setString(22, daoObject.getOrfd_uzsakymo_data());
         pstmt.setString(23, daoObject.getOrfd_atlikimo_data());
         pstmt.setString(24, daoObject.getOrfd_isvezimo_data());
         pstmt.setString(25, daoObject.getOrfd_isveztas_kiekis());
         pstmt.setString(26, daoObject.getOrfd_transporto_priemone());
         pstmt.setObject(27, daoObject.getOrfd_cr_id());
         pstmt.setString(28, daoObject.getOrfd_uzsakymo_informacija());
         pstmt.setString(29, daoObject.getOrfd_atlikti_darbai());
         pstmt.setString(30, daoObject.getOrfd_uzsakovas());
         pstmt.setString(31, daoObject.getOrfd_uzsakovo_email());
         pstmt.setString(32, daoObject.getOrfd_uzsakovo_tel());
         pstmt.setString(33, daoObject.getOrfd_uzsakovo_komentaras());
         pstmt.setString(34, daoObject.getOrfd_laboratorijos_komentaras());
         pstmt.setString(35, daoObject.getOrfd_deguonis());
         pstmt.setString(36, daoObject.getOrfd_skendincios());
         pstmt.setString(37, daoObject.getOrfd_azotas());
         pstmt.setString(38, daoObject.getOrfd_fosforas());
         pstmt.setString(39, daoObject.getOrfd_meginio_data());
         pstmt.setString(40, daoObject.getOrfd_meginio_darbuotojas());
         pstmt.setString(41, daoObject.getOrfd_tyrimo_data());
         pstmt.setString(42, daoObject.getOrfd_tyrimo_darbuotojas());
         pstmt.setString(43, daoObject.getOrfd_pastaba_rezultatams());
         pstmt.setObject(44, daoObject.getN01());
         pstmt.setObject(45, daoObject.getN02());
         pstmt.setObject(46, daoObject.getN03());
         pstmt.setObject(47, daoObject.getN04());
         pstmt.setObject(48, daoObject.getN05());
         pstmt.setString(49, daoObject.getC01());
         pstmt.setString(50, daoObject.getC02());
         pstmt.setString(51, daoObject.getC03());
         pstmt.setString(52, daoObject.getC04());
         pstmt.setString(53, daoObject.getC05());
         pstmt.setObject(54,  Utils.getSqlTimestamp(daoObject.getD01()));
         pstmt.setObject(55,  Utils.getSqlTimestamp(daoObject.getD02()));
         pstmt.setObject(56,  Utils.getSqlTimestamp(daoObject.getD03()));
         pstmt.setObject(57,  Utils.getSqlTimestamp(daoObject.getD04()));
         pstmt.setObject(58,  Utils.getSqlTimestamp(daoObject.getD05()));
         //Record audit information (start)
         pstmt.setInt(59, 1);
         pstmt.setObject(60,  new Timestamp(System.currentTimeMillis()));
         pstmt.setObject(61,  new Timestamp(System.currentTimeMillis()));
         String userName = getUserName();
         pstmt.setObject(62, userName);
         //Record audit information (end)
         pstmt.execute();
         try(ResultSet rs = pstmt.getResultSet()){
            rs.next();
            daoObject.setOrfd_id(rs.getDouble(1));
         }catch(Exception ex1){
            throw ex1; 
         }
      }catch(Exception ex){ 
          throw ex;
      }
      return daoObject;
   }

   @Override
   public NtisOrderFileDataDAO updateRecord(Connection conn, NtisOrderFileDataDAO daoObject) throws Exception{
      if (!daoObject.isRecordLoaded() && !daoObject.isForceUpdate()){
         NtisOrderFileDataDAO loadedObj = this.loadRecord(conn, daoObject.getRecordIdentifier());
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
   public NtisOrderFileDataDAO saveRecord(Connection conn, NtisOrderFileDataDAO daoObject)  throws Exception{
      if (daoObject.getRecordIdentifier() == null) {
         return insertRecord(conn,daoObject);
      }else {
         return updateRecord(conn,daoObject);
      }
   }

}