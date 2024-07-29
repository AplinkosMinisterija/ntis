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
import java.sql.Timestamp;

public class NtisMunicipalitiesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_MUNICIPALITIES.MP_ID";
   private Double mp_id;
   private String mp_code;
   private String mp_name;
   private Date mp_date_from;
   private Date mp_date_to;
   private String rec_userid;
   private Date rec_timestamp;
   private Date rec_create_timestamp;
   private Double rec_version;

   protected boolean mp_id_changed;
   protected boolean mp_code_changed;
   protected boolean mp_name_changed;
   protected boolean mp_date_from_changed;
   protected boolean mp_date_to_changed;
   protected boolean rec_userid_changed;
   protected boolean rec_timestamp_changed;
   protected boolean rec_create_timestamp_changed;
   protected boolean rec_version_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisMunicipalitiesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisMunicipalitiesDAOGen(Double mp_id, String mp_code, String mp_name, Date mp_date_from, Date mp_date_to, String rec_userid, Date rec_timestamp, Date rec_create_timestamp, Double rec_version) {
      this.mp_id = mp_id;
      this.mp_code = mp_code;
      this.mp_name = mp_name;
      this.mp_date_from = mp_date_from;
      this.mp_date_to = mp_date_to;
      this.rec_userid = rec_userid;
      this.rec_timestamp = rec_timestamp;
      this.rec_create_timestamp = rec_create_timestamp;
      this.rec_version = rec_version;
   }
   public void copyValues(NtisMunicipalitiesDAOGen obj) {
      this.setMp_id(obj.getMp_id());
      this.setMp_code(obj.getMp_code());
      this.setMp_name(obj.getMp_name());
      this.setMp_date_from(obj.getMp_date_from());
      this.setMp_date_to(obj.getMp_date_to());
   }
   protected void markObjectAsInitial() {
      this.mp_id_changed = false;
      this.mp_code_changed = false;
      this.mp_name_changed = false;
      this.mp_date_from_changed = false;
      this.mp_date_to_changed = false;
      this.rec_userid_changed = false;
      this.rec_timestamp_changed = false;
      this.rec_create_timestamp_changed = false;
      this.rec_version_changed = false;
      this.record_changed = false;
   }
   public void setMp_id(Double mp_id) {
      if (this.isForceUpdate() || (this.mp_id != null && !this.mp_id.equals(mp_id)) || (mp_id != null && !mp_id.equals(this.mp_id))){
         this.mp_id_changed = true; 
         this.record_changed = true;
         this.mp_id = mp_id;
      }
   }
   public Double getMp_id() {
      return this.mp_id;
   }
   public void setMp_code(String mp_code) {
      if (this.isForceUpdate() || (this.mp_code != null && !this.mp_code.equals(mp_code)) || (mp_code != null && !mp_code.equals(this.mp_code))){
         this.mp_code_changed = true; 
         this.record_changed = true;
         this.mp_code = mp_code;
      }
   }
   public String getMp_code() {
      return this.mp_code;
   }
   public void setMp_name(String mp_name) {
      if (this.isForceUpdate() || (this.mp_name != null && !this.mp_name.equals(mp_name)) || (mp_name != null && !mp_name.equals(this.mp_name))){
         this.mp_name_changed = true; 
         this.record_changed = true;
         this.mp_name = mp_name;
      }
   }
   public String getMp_name() {
      return this.mp_name;
   }
   public void setMp_date_from(Date mp_date_from) {
      if (this.isForceUpdate() || (this.mp_date_from != null && !this.mp_date_from.equals(mp_date_from)) || (mp_date_from != null && !mp_date_from.equals(this.mp_date_from))){
         this.mp_date_from_changed = true; 
         this.record_changed = true;
         this.mp_date_from = mp_date_from;
      }
   }
   public Date getMp_date_from() {
      return this.mp_date_from;
   }
   public void setMp_date_to(Date mp_date_to) {
      if (this.isForceUpdate() || (this.mp_date_to != null && !this.mp_date_to.equals(mp_date_to)) || (mp_date_to != null && !mp_date_to.equals(this.mp_date_to))){
         this.mp_date_to_changed = true; 
         this.record_changed = true;
         this.mp_date_to = mp_date_to;
      }
   }
   public Date getMp_date_to() {
      return this.mp_date_to;
   }
   public void setRec_userid(String rec_userid) {
      if (this.isForceUpdate() || (this.rec_userid != null && !this.rec_userid.equals(rec_userid)) || (rec_userid != null && !rec_userid.equals(this.rec_userid))){
         this.rec_userid_changed = true; 
         this.record_changed = true;
         this.rec_userid = rec_userid;
      }
   }
   public String getRec_userid() {
      return this.rec_userid;
   }
   public void setRec_timestamp(Date rec_timestamp) {
      if (this.isForceUpdate() || (this.rec_timestamp != null && !this.rec_timestamp.equals(rec_timestamp)) || (rec_timestamp != null && !rec_timestamp.equals(this.rec_timestamp))){
         this.rec_timestamp_changed = true; 
         this.record_changed = true;
         this.rec_timestamp = rec_timestamp;
      }
   }
   public Date getRec_timestamp() {
      return this.rec_timestamp;
   }
   public void setRec_create_timestamp(Date rec_create_timestamp) {
      if (this.isForceUpdate() || (this.rec_create_timestamp != null && !this.rec_create_timestamp.equals(rec_create_timestamp)) || (rec_create_timestamp != null && !rec_create_timestamp.equals(this.rec_create_timestamp))){
         this.rec_create_timestamp_changed = true; 
         this.record_changed = true;
         this.rec_create_timestamp = rec_create_timestamp;
      }
   }
   public Date getRec_create_timestamp() {
      return this.rec_create_timestamp;
   }
   public void setRec_version(Double rec_version) {
      if (this.isForceUpdate() || (this.rec_version != null && !this.rec_version.equals(rec_version)) || (rec_version != null && !rec_version.equals(this.rec_version))){
         this.rec_version_changed = true; 
         this.record_changed = true;
         this.rec_version = rec_version;
      }
   }
   public Double getRec_version() {
      return this.rec_version;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.mp_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.mp_id;
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
      if (mp_code_changed || operation == Utils.OPERATION_INSERT) {
         if (mp_code!= null && mp_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_MUNICIPALITIES", "MP_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (mp_name_changed || operation == Utils.OPERATION_INSERT) {
         if (mp_name!= null && mp_name.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_MUNICIPALITIES", "MP_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_MUNICIPALITIES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_MUNICIPALITIES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_MUNICIPALITIES SET ";
      boolean changedExists = false;      if (mp_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MP_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mp_code);
      }
      if (mp_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MP_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mp_name);
      }
      if (mp_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MP_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(mp_date_from);
      }
      if (mp_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MP_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(mp_date_to);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  MP_ID = ? ";
      updateStatementPart.addStatementParam(mp_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisMunicipalitiesDAO\":{\"mp_id\": \""+getMp_id()+"\", \"mp_code\": \""+getMp_code()+"\", \"mp_name\": \""+getMp_name()+"\", \"mp_date_from\": \""+getMp_date_from()+"\", \"mp_date_to\": \""+getMp_date_to()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_version\": \""+getRec_version()+"\"}}";
   }

}