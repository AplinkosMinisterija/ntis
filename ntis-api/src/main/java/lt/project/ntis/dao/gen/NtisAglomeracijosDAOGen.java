package lt.project.ntis.dao.gen;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.spark.dao.common.SprBaseDAO;
import eu.itreegroup.spark.dao.common.SprBaseDBServiceImpl;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.common.statementparams.UpdateStatementPart;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class NtisAglomeracijosDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_AGLOMERACIJOS.OGC_FID";
   private Double ogc_fid;
   private String geom;
   private String a_uuid;
   private Double a_ter_id;
   private String pav;
   private Double sav_kodas;
   private Double ge;
   private Double gyv_tankis;
   private Double a_plotas;
   private String a_dok_pav;
   private String a_dok_nr;
   private Date a_dok_data;

   protected boolean ogc_fid_changed;
   protected boolean geom_changed;
   protected boolean a_uuid_changed;
   protected boolean a_ter_id_changed;
   protected boolean pav_changed;
   protected boolean sav_kodas_changed;
   protected boolean ge_changed;
   protected boolean gyv_tankis_changed;
   protected boolean a_plotas_changed;
   protected boolean a_dok_pav_changed;
   protected boolean a_dok_nr_changed;
   protected boolean a_dok_data_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisAglomeracijosDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisAglomeracijosDAOGen(Double ogc_fid, String geom, String a_uuid, Double a_ter_id, String pav, Double sav_kodas, Double ge, Double gyv_tankis, Double a_plotas, String a_dok_pav, String a_dok_nr, Date a_dok_data) {
      this.ogc_fid = ogc_fid;
      this.geom = geom;
      this.a_uuid = a_uuid;
      this.a_ter_id = a_ter_id;
      this.pav = pav;
      this.sav_kodas = sav_kodas;
      this.ge = ge;
      this.gyv_tankis = gyv_tankis;
      this.a_plotas = a_plotas;
      this.a_dok_pav = a_dok_pav;
      this.a_dok_nr = a_dok_nr;
      this.a_dok_data = a_dok_data;
   }
   public void copyValues(NtisAglomeracijosDAOGen obj) {
      this.setOgc_fid(obj.getOgc_fid());
      this.setGeom(obj.getGeom());
      this.setA_uuid(obj.getA_uuid());
      this.setA_ter_id(obj.getA_ter_id());
      this.setPav(obj.getPav());
      this.setSav_kodas(obj.getSav_kodas());
      this.setGe(obj.getGe());
      this.setGyv_tankis(obj.getGyv_tankis());
      this.setA_plotas(obj.getA_plotas());
      this.setA_dok_pav(obj.getA_dok_pav());
      this.setA_dok_nr(obj.getA_dok_nr());
      this.setA_dok_data(obj.getA_dok_data());
   }
   protected void markObjectAsInitial() {
      this.ogc_fid_changed = false;
      this.geom_changed = false;
      this.a_uuid_changed = false;
      this.a_ter_id_changed = false;
      this.pav_changed = false;
      this.sav_kodas_changed = false;
      this.ge_changed = false;
      this.gyv_tankis_changed = false;
      this.a_plotas_changed = false;
      this.a_dok_pav_changed = false;
      this.a_dok_nr_changed = false;
      this.a_dok_data_changed = false;
      this.record_changed = false;
   }
   public void setOgc_fid(Double ogc_fid) {
      if (this.isForceUpdate() || (this.ogc_fid != null && !this.ogc_fid.equals(ogc_fid)) || (ogc_fid != null && !ogc_fid.equals(this.ogc_fid))){
         this.ogc_fid_changed = true; 
         this.record_changed = true;
         this.ogc_fid = ogc_fid;
      }
   }
   public Double getOgc_fid() {
      return this.ogc_fid;
   }
   public void setGeom(String geom) {
      if (this.isForceUpdate() || (this.geom != null && !this.geom.equals(geom)) || (geom != null && !geom.equals(this.geom))){
         this.geom_changed = true; 
         this.record_changed = true;
         this.geom = geom;
      }
   }
   public String getGeom() {
      return this.geom;
   }
   public void setA_uuid(String a_uuid) {
      if (this.isForceUpdate() || (this.a_uuid != null && !this.a_uuid.equals(a_uuid)) || (a_uuid != null && !a_uuid.equals(this.a_uuid))){
         this.a_uuid_changed = true; 
         this.record_changed = true;
         this.a_uuid = a_uuid;
      }
   }
   public String getA_uuid() {
      return this.a_uuid;
   }
   public void setA_ter_id(Double a_ter_id) {
      if (this.isForceUpdate() || (this.a_ter_id != null && !this.a_ter_id.equals(a_ter_id)) || (a_ter_id != null && !a_ter_id.equals(this.a_ter_id))){
         this.a_ter_id_changed = true; 
         this.record_changed = true;
         this.a_ter_id = a_ter_id;
      }
   }
   public Double getA_ter_id() {
      return this.a_ter_id;
   }
   public void setPav(String pav) {
      if (this.isForceUpdate() || (this.pav != null && !this.pav.equals(pav)) || (pav != null && !pav.equals(this.pav))){
         this.pav_changed = true; 
         this.record_changed = true;
         this.pav = pav;
      }
   }
   public String getPav() {
      return this.pav;
   }
   public void setSav_kodas(Double sav_kodas) {
      if (this.isForceUpdate() || (this.sav_kodas != null && !this.sav_kodas.equals(sav_kodas)) || (sav_kodas != null && !sav_kodas.equals(this.sav_kodas))){
         this.sav_kodas_changed = true; 
         this.record_changed = true;
         this.sav_kodas = sav_kodas;
      }
   }
   public Double getSav_kodas() {
      return this.sav_kodas;
   }
   public void setGe(Double ge) {
      if (this.isForceUpdate() || (this.ge != null && !this.ge.equals(ge)) || (ge != null && !ge.equals(this.ge))){
         this.ge_changed = true; 
         this.record_changed = true;
         this.ge = ge;
      }
   }
   public Double getGe() {
      return this.ge;
   }
   public void setGyv_tankis(Double gyv_tankis) {
      if (this.isForceUpdate() || (this.gyv_tankis != null && !this.gyv_tankis.equals(gyv_tankis)) || (gyv_tankis != null && !gyv_tankis.equals(this.gyv_tankis))){
         this.gyv_tankis_changed = true; 
         this.record_changed = true;
         this.gyv_tankis = gyv_tankis;
      }
   }
   public Double getGyv_tankis() {
      return this.gyv_tankis;
   }
   public void setA_plotas(Double a_plotas) {
      if (this.isForceUpdate() || (this.a_plotas != null && !this.a_plotas.equals(a_plotas)) || (a_plotas != null && !a_plotas.equals(this.a_plotas))){
         this.a_plotas_changed = true; 
         this.record_changed = true;
         this.a_plotas = a_plotas;
      }
   }
   public Double getA_plotas() {
      return this.a_plotas;
   }
   public void setA_dok_pav(String a_dok_pav) {
      if (this.isForceUpdate() || (this.a_dok_pav != null && !this.a_dok_pav.equals(a_dok_pav)) || (a_dok_pav != null && !a_dok_pav.equals(this.a_dok_pav))){
         this.a_dok_pav_changed = true; 
         this.record_changed = true;
         this.a_dok_pav = a_dok_pav;
      }
   }
   public String getA_dok_pav() {
      return this.a_dok_pav;
   }
   public void setA_dok_nr(String a_dok_nr) {
      if (this.isForceUpdate() || (this.a_dok_nr != null && !this.a_dok_nr.equals(a_dok_nr)) || (a_dok_nr != null && !a_dok_nr.equals(this.a_dok_nr))){
         this.a_dok_nr_changed = true; 
         this.record_changed = true;
         this.a_dok_nr = a_dok_nr;
      }
   }
   public String getA_dok_nr() {
      return this.a_dok_nr;
   }
   public void setA_dok_data(Date a_dok_data) {
      if (this.isForceUpdate() || (this.a_dok_data != null && !this.a_dok_data.equals(a_dok_data)) || (a_dok_data != null && !a_dok_data.equals(this.a_dok_data))){
         this.a_dok_data_changed = true; 
         this.record_changed = true;
         this.a_dok_data = a_dok_data;
      }
   }
   public Date getA_dok_data() {
      return this.a_dok_data;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.ogc_fid = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.ogc_fid;
    }
    @JsonIgnore
    public void setRecordLoaded(boolean recordLoaded){
        this.recordLoaded = recordLoaded;
    }
    @JsonIgnore
    public boolean isRecordLoaded(){
        return this.recordLoaded;
    }
    @JsonIgnore
    public void setForceUpdate(boolean forceUpdate){
        this.forceUpdate = forceUpdate;
    }
    @JsonIgnore
    public boolean isForceUpdate(){
        return this.forceUpdate;
    }
    public void validateObject(int operation, SprBaseDBServiceImpl<?> baseService) throws SparkBusinessException{
      if (geom_changed || operation == Utils.OPERATION_INSERT) {
         if (geom!= null && geom.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "GEOM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (a_uuid_changed || operation == Utils.OPERATION_INSERT) {
         if (a_uuid!= null && a_uuid.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "A_UUID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (a_ter_id_changed || operation == Utils.OPERATION_INSERT) {
         if (a_ter_id!= null && (""+a_ter_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "A_TER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (pav_changed || operation == Utils.OPERATION_INSERT) {
         if (pav!= null && pav.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "PAV", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (sav_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (sav_kodas!= null && (""+sav_kodas.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "SAV_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (ge_changed || operation == Utils.OPERATION_INSERT) {
         if (ge!= null && (""+ge.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "GE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (gyv_tankis_changed || operation == Utils.OPERATION_INSERT) {
         if (gyv_tankis!= null && (""+gyv_tankis.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "GYV_TANKIS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (a_plotas_changed || operation == Utils.OPERATION_INSERT) {
         if (a_plotas!= null && a_plotas.toString().length()>17) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "A_PLOTAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"17"));
         }
      }
      if (a_dok_pav_changed || operation == Utils.OPERATION_INSERT) {
         if (a_dok_pav!= null && a_dok_pav.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "A_DOK_PAV", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (a_dok_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (a_dok_nr!= null && a_dok_nr.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGLOMERACIJOS", "A_DOK_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_AGLOMERACIJOS SET ";
      boolean changedExists = false;      if (geom_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"GEOM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(geom);
      }
      if (a_uuid_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"A_UUID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(a_uuid);
      }
      if (a_ter_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"A_TER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(a_ter_id);
      }
      if (pav_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PAV = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(pav);
      }
      if (sav_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SAV_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sav_kodas);
      }
      if (ge_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"GE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ge);
      }
      if (gyv_tankis_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"GYV_TANKIS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(gyv_tankis);
      }
      if (a_plotas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"A_PLOTAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(a_plotas);
      }
      if (a_dok_pav_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"A_DOK_PAV = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(a_dok_pav);
      }
      if (a_dok_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"A_DOK_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(a_dok_nr);
      }
      if (a_dok_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"A_DOK_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(a_dok_data);
      }
      answer = answer +" WHERE  OGC_FID = ? ";
      updateStatementPart.addStatementParam(ogc_fid);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisAglomeracijosDAO\":{\"ogc_fid\": \""+getOgc_fid()+"\", \"geom\": \""+getGeom()+"\", \"a_uuid\": \""+getA_uuid()+"\", \"a_ter_id\": \""+getA_ter_id()+"\", \"pav\": \""+getPav()+"\", \"sav_kodas\": \""+getSav_kodas()+"\", \"ge\": \""+getGe()+"\", \"gyv_tankis\": \""+getGyv_tankis()+"\", \"a_plotas\": \""+getA_plotas()+"\", \"a_dok_pav\": \""+getA_dok_pav()+"\", \"a_dok_nr\": \""+getA_dok_nr()+"\", \"a_dok_data\": \""+getA_dok_data()+"\"}}";
   }

}