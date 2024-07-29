package eu.itreegroup.spark.modules.tasks.dao.gen;

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

public class SprTasksDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_TASKS.TAS_ID";
   private Double tas_id;
   private String tas_name;
   private String tas_type;
   private String tas_status;
   private Date tas_date_from;
   private Date tas_date_to;
   private String tas_reject_reason;
   private String tas_priority;
   private String tas_repetitive;
   private String tas_description;
   private Double tas_usr_id;
   private Double tas_tas_id;
   private Double rec_version;
   private Date rec_create_timestamp;
   private String rec_userid;
   private Date rec_timestamp;
   private Double n01;
   private Double n02;
   private Double n03;
   private Double n04;
   private Double n05;
   private String c01;
   private String c02;
   private String c03;
   private String c04;
   private String c05;
   private Date d01;
   private Date d02;
   private Date d03;
   private Date d04;
   private Date d05;

   protected boolean tas_id_changed;
   protected boolean tas_name_changed;
   protected boolean tas_type_changed;
   protected boolean tas_status_changed;
   protected boolean tas_date_from_changed;
   protected boolean tas_date_to_changed;
   protected boolean tas_reject_reason_changed;
   protected boolean tas_priority_changed;
   protected boolean tas_repetitive_changed;
   protected boolean tas_description_changed;
   protected boolean tas_usr_id_changed;
   protected boolean tas_tas_id_changed;
   protected boolean rec_version_changed;
   protected boolean rec_create_timestamp_changed;
   protected boolean rec_userid_changed;
   protected boolean rec_timestamp_changed;
   protected boolean n01_changed;
   protected boolean n02_changed;
   protected boolean n03_changed;
   protected boolean n04_changed;
   protected boolean n05_changed;
   protected boolean c01_changed;
   protected boolean c02_changed;
   protected boolean c03_changed;
   protected boolean c04_changed;
   protected boolean c05_changed;
   protected boolean d01_changed;
   protected boolean d02_changed;
   protected boolean d03_changed;
   protected boolean d04_changed;
   protected boolean d05_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public SprTasksDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprTasksDAOGen(Double tas_id, String tas_name, String tas_type, String tas_status, Date tas_date_from, Date tas_date_to, String tas_reject_reason, String tas_priority, String tas_repetitive, String tas_description, Double tas_usr_id, Double tas_tas_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.tas_id = tas_id;
      this.tas_name = tas_name;
      this.tas_type = tas_type;
      this.tas_status = tas_status;
      this.tas_date_from = tas_date_from;
      this.tas_date_to = tas_date_to;
      this.tas_reject_reason = tas_reject_reason;
      this.tas_priority = tas_priority;
      this.tas_repetitive = tas_repetitive;
      this.tas_description = tas_description;
      this.tas_usr_id = tas_usr_id;
      this.tas_tas_id = tas_tas_id;
      this.rec_version = rec_version;
      this.rec_create_timestamp = rec_create_timestamp;
      this.rec_userid = rec_userid;
      this.rec_timestamp = rec_timestamp;
      this.n01 = n01;
      this.n02 = n02;
      this.n03 = n03;
      this.n04 = n04;
      this.n05 = n05;
      this.c01 = c01;
      this.c02 = c02;
      this.c03 = c03;
      this.c04 = c04;
      this.c05 = c05;
      this.d01 = d01;
      this.d02 = d02;
      this.d03 = d03;
      this.d04 = d04;
      this.d05 = d05;
   }
   public void copyValues(SprTasksDAOGen obj) {
      this.setTas_id(obj.getTas_id());
      this.setTas_name(obj.getTas_name());
      this.setTas_type(obj.getTas_type());
      this.setTas_status(obj.getTas_status());
      this.setTas_date_from(obj.getTas_date_from());
      this.setTas_date_to(obj.getTas_date_to());
      this.setTas_reject_reason(obj.getTas_reject_reason());
      this.setTas_priority(obj.getTas_priority());
      this.setTas_repetitive(obj.getTas_repetitive());
      this.setTas_description(obj.getTas_description());
      this.setTas_usr_id(obj.getTas_usr_id());
      this.setTas_tas_id(obj.getTas_tas_id());
      this.setN01(obj.getN01());
      this.setN02(obj.getN02());
      this.setN03(obj.getN03());
      this.setN04(obj.getN04());
      this.setN05(obj.getN05());
      this.setC01(obj.getC01());
      this.setC02(obj.getC02());
      this.setC03(obj.getC03());
      this.setC04(obj.getC04());
      this.setC05(obj.getC05());
      this.setD01(obj.getD01());
      this.setD02(obj.getD02());
      this.setD03(obj.getD03());
      this.setD04(obj.getD04());
      this.setD05(obj.getD05());
   }
   protected void markObjectAsInitial() {
      this.tas_id_changed = false;
      this.tas_name_changed = false;
      this.tas_type_changed = false;
      this.tas_status_changed = false;
      this.tas_date_from_changed = false;
      this.tas_date_to_changed = false;
      this.tas_reject_reason_changed = false;
      this.tas_priority_changed = false;
      this.tas_repetitive_changed = false;
      this.tas_description_changed = false;
      this.tas_usr_id_changed = false;
      this.tas_tas_id_changed = false;
      this.rec_version_changed = false;
      this.rec_create_timestamp_changed = false;
      this.rec_userid_changed = false;
      this.rec_timestamp_changed = false;
      this.n01_changed = false;
      this.n02_changed = false;
      this.n03_changed = false;
      this.n04_changed = false;
      this.n05_changed = false;
      this.c01_changed = false;
      this.c02_changed = false;
      this.c03_changed = false;
      this.c04_changed = false;
      this.c05_changed = false;
      this.d01_changed = false;
      this.d02_changed = false;
      this.d03_changed = false;
      this.d04_changed = false;
      this.d05_changed = false;
      this.record_changed = false;
   }
   public void setTas_id(Double tas_id) {
      if (this.isForceUpdate() || 
               (this.tas_id != null && !this.tas_id.equals(tas_id))  ||
               (tas_id != null && !tas_id.equals(this.tas_id)) ){
         this.tas_id_changed = true; 
         this.record_changed = true;
         this.tas_id = tas_id;
      }
   }
   public Double getTas_id() {
      return this.tas_id;
   }
   public void setTas_name(String tas_name) {
      if (this.isForceUpdate() || 
               (this.tas_name != null && !this.tas_name.equals(tas_name))  ||
               (tas_name != null && !tas_name.equals(this.tas_name)) ){
         this.tas_name_changed = true; 
         this.record_changed = true;
         this.tas_name = tas_name;
      }
   }
   public String getTas_name() {
      return this.tas_name;
   }
   public void setTas_type(String tas_type) {
      if (this.isForceUpdate() || 
               (this.tas_type != null && !this.tas_type.equals(tas_type))  ||
               (tas_type != null && !tas_type.equals(this.tas_type)) ){
         this.tas_type_changed = true; 
         this.record_changed = true;
         this.tas_type = tas_type;
      }
   }
   public String getTas_type() {
      return this.tas_type;
   }
   public void setTas_status(String tas_status) {
      if (this.isForceUpdate() || 
               (this.tas_status != null && !this.tas_status.equals(tas_status))  ||
               (tas_status != null && !tas_status.equals(this.tas_status)) ){
         this.tas_status_changed = true; 
         this.record_changed = true;
         this.tas_status = tas_status;
      }
   }
   public String getTas_status() {
      return this.tas_status;
   }
   public void setTas_date_from(Date tas_date_from) {
      if (this.isForceUpdate() || 
               (this.tas_date_from != null && (tas_date_from == null ||this.tas_date_from.compareTo(tas_date_from) != 0 )) ||
               (tas_date_from != null && (this.tas_date_from == null ||tas_date_from.compareTo(this.tas_date_from) != 0 ))){
         this.tas_date_from_changed = true; 
         this.record_changed = true;
         this.tas_date_from = tas_date_from;
      }
   }
   public Date getTas_date_from() {
      return this.tas_date_from;
   }
   public void setTas_date_to(Date tas_date_to) {
      if (this.isForceUpdate() || 
               (this.tas_date_to != null && (tas_date_to == null ||this.tas_date_to.compareTo(tas_date_to) != 0 )) ||
               (tas_date_to != null && (this.tas_date_to == null ||tas_date_to.compareTo(this.tas_date_to) != 0 ))){
         this.tas_date_to_changed = true; 
         this.record_changed = true;
         this.tas_date_to = tas_date_to;
      }
   }
   public Date getTas_date_to() {
      return this.tas_date_to;
   }
   public void setTas_reject_reason(String tas_reject_reason) {
      if (this.isForceUpdate() || 
               (this.tas_reject_reason != null && !this.tas_reject_reason.equals(tas_reject_reason))  ||
               (tas_reject_reason != null && !tas_reject_reason.equals(this.tas_reject_reason)) ){
         this.tas_reject_reason_changed = true; 
         this.record_changed = true;
         this.tas_reject_reason = tas_reject_reason;
      }
   }
   public String getTas_reject_reason() {
      return this.tas_reject_reason;
   }
   public void setTas_priority(String tas_priority) {
      if (this.isForceUpdate() || 
               (this.tas_priority != null && !this.tas_priority.equals(tas_priority))  ||
               (tas_priority != null && !tas_priority.equals(this.tas_priority)) ){
         this.tas_priority_changed = true; 
         this.record_changed = true;
         this.tas_priority = tas_priority;
      }
   }
   public String getTas_priority() {
      return this.tas_priority;
   }
   public void setTas_repetitive(String tas_repetitive) {
      if (this.isForceUpdate() || 
               (this.tas_repetitive != null && !this.tas_repetitive.equals(tas_repetitive))  ||
               (tas_repetitive != null && !tas_repetitive.equals(this.tas_repetitive)) ){
         this.tas_repetitive_changed = true; 
         this.record_changed = true;
         this.tas_repetitive = tas_repetitive;
      }
   }
   public String getTas_repetitive() {
      return this.tas_repetitive;
   }
   public void setTas_description(String tas_description) {
      if (this.isForceUpdate() || 
               (this.tas_description != null && !this.tas_description.equals(tas_description))  ||
               (tas_description != null && !tas_description.equals(this.tas_description)) ){
         this.tas_description_changed = true; 
         this.record_changed = true;
         this.tas_description = tas_description;
      }
   }
   public String getTas_description() {
      return this.tas_description;
   }
   public void setTas_usr_id(Double tas_usr_id) {
      if (this.isForceUpdate() || 
               (this.tas_usr_id != null && !this.tas_usr_id.equals(tas_usr_id))  ||
               (tas_usr_id != null && !tas_usr_id.equals(this.tas_usr_id)) ){
         this.tas_usr_id_changed = true; 
         this.record_changed = true;
         this.tas_usr_id = tas_usr_id;
      }
   }
   public Double getTas_usr_id() {
      return this.tas_usr_id;
   }
   public void setTas_tas_id(Double tas_tas_id) {
      if (this.isForceUpdate() || 
               (this.tas_tas_id != null && !this.tas_tas_id.equals(tas_tas_id))  ||
               (tas_tas_id != null && !tas_tas_id.equals(this.tas_tas_id)) ){
         this.tas_tas_id_changed = true; 
         this.record_changed = true;
         this.tas_tas_id = tas_tas_id;
      }
   }
   public Double getTas_tas_id() {
      return this.tas_tas_id;
   }
   public void setRec_version(Double rec_version) {
      if (this.isForceUpdate() || 
               (this.rec_version != null && !this.rec_version.equals(rec_version))  ||
               (rec_version != null && !rec_version.equals(this.rec_version)) ){
         this.rec_version_changed = true; 
         this.record_changed = true;
         this.rec_version = rec_version;
      }
   }
   public Double getRec_version() {
      return this.rec_version;
   }
   public void setRec_create_timestamp(Date rec_create_timestamp) {
      if (this.isForceUpdate() || 
               (this.rec_create_timestamp != null && (rec_create_timestamp == null ||this.rec_create_timestamp.compareTo(rec_create_timestamp) != 0 )) ||
               (rec_create_timestamp != null && (this.rec_create_timestamp == null ||rec_create_timestamp.compareTo(this.rec_create_timestamp) != 0 ))){
         this.rec_create_timestamp_changed = true; 
         this.record_changed = true;
         this.rec_create_timestamp = rec_create_timestamp;
      }
   }
   public Date getRec_create_timestamp() {
      return this.rec_create_timestamp;
   }
   public void setRec_userid(String rec_userid) {
      if (this.isForceUpdate() || 
               (this.rec_userid != null && !this.rec_userid.equals(rec_userid))  ||
               (rec_userid != null && !rec_userid.equals(this.rec_userid)) ){
         this.rec_userid_changed = true; 
         this.record_changed = true;
         this.rec_userid = rec_userid;
      }
   }
   public String getRec_userid() {
      return this.rec_userid;
   }
   public void setRec_timestamp(Date rec_timestamp) {
      if (this.isForceUpdate() || 
               (this.rec_timestamp != null && (rec_timestamp == null ||this.rec_timestamp.compareTo(rec_timestamp) != 0 )) ||
               (rec_timestamp != null && (this.rec_timestamp == null ||rec_timestamp.compareTo(this.rec_timestamp) != 0 ))){
         this.rec_timestamp_changed = true; 
         this.record_changed = true;
         this.rec_timestamp = rec_timestamp;
      }
   }
   public Date getRec_timestamp() {
      return this.rec_timestamp;
   }
   @JsonIgnore
   public void setN01(Double n01) {
      if (this.isForceUpdate() || 
               (this.n01 != null && !this.n01.equals(n01))  ||
               (n01 != null && !n01.equals(this.n01)) ){
         this.n01_changed = true; 
         this.record_changed = true;
         this.n01 = n01;
      }
   }
   @JsonIgnore
   public Double getN01() {
      return this.n01;
   }
   @JsonIgnore
   public void setN02(Double n02) {
      if (this.isForceUpdate() || 
               (this.n02 != null && !this.n02.equals(n02))  ||
               (n02 != null && !n02.equals(this.n02)) ){
         this.n02_changed = true; 
         this.record_changed = true;
         this.n02 = n02;
      }
   }
   @JsonIgnore
   public Double getN02() {
      return this.n02;
   }
   @JsonIgnore
   public void setN03(Double n03) {
      if (this.isForceUpdate() || 
               (this.n03 != null && !this.n03.equals(n03))  ||
               (n03 != null && !n03.equals(this.n03)) ){
         this.n03_changed = true; 
         this.record_changed = true;
         this.n03 = n03;
      }
   }
   @JsonIgnore
   public Double getN03() {
      return this.n03;
   }
   @JsonIgnore
   public void setN04(Double n04) {
      if (this.isForceUpdate() || 
               (this.n04 != null && !this.n04.equals(n04))  ||
               (n04 != null && !n04.equals(this.n04)) ){
         this.n04_changed = true; 
         this.record_changed = true;
         this.n04 = n04;
      }
   }
   @JsonIgnore
   public Double getN04() {
      return this.n04;
   }
   @JsonIgnore
   public void setN05(Double n05) {
      if (this.isForceUpdate() || 
               (this.n05 != null && !this.n05.equals(n05))  ||
               (n05 != null && !n05.equals(this.n05)) ){
         this.n05_changed = true; 
         this.record_changed = true;
         this.n05 = n05;
      }
   }
   @JsonIgnore
   public Double getN05() {
      return this.n05;
   }
   @JsonIgnore
   public void setC01(String c01) {
      if (this.isForceUpdate() || 
               (this.c01 != null && !this.c01.equals(c01))  ||
               (c01 != null && !c01.equals(this.c01)) ){
         this.c01_changed = true; 
         this.record_changed = true;
         this.c01 = c01;
      }
   }
   @JsonIgnore
   public String getC01() {
      return this.c01;
   }
   @JsonIgnore
   public void setC02(String c02) {
      if (this.isForceUpdate() || 
               (this.c02 != null && !this.c02.equals(c02))  ||
               (c02 != null && !c02.equals(this.c02)) ){
         this.c02_changed = true; 
         this.record_changed = true;
         this.c02 = c02;
      }
   }
   @JsonIgnore
   public String getC02() {
      return this.c02;
   }
   @JsonIgnore
   public void setC03(String c03) {
      if (this.isForceUpdate() || 
               (this.c03 != null && !this.c03.equals(c03))  ||
               (c03 != null && !c03.equals(this.c03)) ){
         this.c03_changed = true; 
         this.record_changed = true;
         this.c03 = c03;
      }
   }
   @JsonIgnore
   public String getC03() {
      return this.c03;
   }
   @JsonIgnore
   public void setC04(String c04) {
      if (this.isForceUpdate() || 
               (this.c04 != null && !this.c04.equals(c04))  ||
               (c04 != null && !c04.equals(this.c04)) ){
         this.c04_changed = true; 
         this.record_changed = true;
         this.c04 = c04;
      }
   }
   @JsonIgnore
   public String getC04() {
      return this.c04;
   }
   @JsonIgnore
   public void setC05(String c05) {
      if (this.isForceUpdate() || 
               (this.c05 != null && !this.c05.equals(c05))  ||
               (c05 != null && !c05.equals(this.c05)) ){
         this.c05_changed = true; 
         this.record_changed = true;
         this.c05 = c05;
      }
   }
   @JsonIgnore
   public String getC05() {
      return this.c05;
   }
   @JsonIgnore
   public void setD01(Date d01) {
      if (this.isForceUpdate() || 
               (this.d01 != null && (d01 == null ||this.d01.compareTo(d01) != 0 )) ||
               (d01 != null && (this.d01 == null ||d01.compareTo(this.d01) != 0 ))){
         this.d01_changed = true; 
         this.record_changed = true;
         this.d01 = d01;
      }
   }
   @JsonIgnore
   public Date getD01() {
      return this.d01;
   }
   @JsonIgnore
   public void setD02(Date d02) {
      if (this.isForceUpdate() || 
               (this.d02 != null && (d02 == null ||this.d02.compareTo(d02) != 0 )) ||
               (d02 != null && (this.d02 == null ||d02.compareTo(this.d02) != 0 ))){
         this.d02_changed = true; 
         this.record_changed = true;
         this.d02 = d02;
      }
   }
   @JsonIgnore
   public Date getD02() {
      return this.d02;
   }
   @JsonIgnore
   public void setD03(Date d03) {
      if (this.isForceUpdate() || 
               (this.d03 != null && (d03 == null ||this.d03.compareTo(d03) != 0 )) ||
               (d03 != null && (this.d03 == null ||d03.compareTo(this.d03) != 0 ))){
         this.d03_changed = true; 
         this.record_changed = true;
         this.d03 = d03;
      }
   }
   @JsonIgnore
   public Date getD03() {
      return this.d03;
   }
   @JsonIgnore
   public void setD04(Date d04) {
      if (this.isForceUpdate() || 
               (this.d04 != null && (d04 == null ||this.d04.compareTo(d04) != 0 )) ||
               (d04 != null && (this.d04 == null ||d04.compareTo(this.d04) != 0 ))){
         this.d04_changed = true; 
         this.record_changed = true;
         this.d04 = d04;
      }
   }
   @JsonIgnore
   public Date getD04() {
      return this.d04;
   }
   @JsonIgnore
   public void setD05(Date d05) {
      if (this.isForceUpdate() || 
               (this.d05 != null && (d05 == null ||this.d05.compareTo(d05) != 0 )) ||
               (d05 != null && (this.d05 == null ||d05.compareTo(this.d05) != 0 ))){
         this.d05_changed = true; 
         this.record_changed = true;
         this.d05 = d05;
      }
   }
   @JsonIgnore
   public Date getD05() {
      return this.d05;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.tas_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.tas_id;
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
      if (tas_name_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_name== null || EMPTY_STRING.equals(tas_name)) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (tas_name!= null && tas_name.length()>2000) {
               throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
            }
         }
      }
      if (tas_type_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_type== null || EMPTY_STRING.equals(tas_type)) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (tas_type!= null && tas_type.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (tas_status_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_status== null || EMPTY_STRING.equals(tas_status)) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_STATUS", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (tas_status!= null && tas_status.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_STATUS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (tas_reject_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_reject_reason!= null && tas_reject_reason.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_REJECT_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (tas_priority_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_priority!= null && tas_priority.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_PRIORITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (tas_repetitive_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_repetitive== null || EMPTY_STRING.equals(tas_repetitive)) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_REPETITIVE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (tas_repetitive!= null && tas_repetitive.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_REPETITIVE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (tas_description_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_description!= null && tas_description.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (tas_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_usr_id!= null && (""+tas_usr_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (tas_tas_id_changed || operation == Utils.OPERATION_INSERT) {
         if (tas_tas_id!= null && (""+tas_tas_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "TAS_TAS_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TASKS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_TASKS SET ";
      boolean changedExists = false;      if (tas_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_name);
      }
      if (tas_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_type);
      }
      if (tas_status_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_STATUS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_status);
      }
      if (tas_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_date_from);
      }
      if (tas_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_date_to);
      }
      if (tas_reject_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_REJECT_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_reject_reason);
      }
      if (tas_priority_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_PRIORITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_priority);
      }
      if (tas_repetitive_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_REPETITIVE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_repetitive);
      }
      if (tas_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_description);
      }
      if (tas_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_usr_id);
      }
      if (tas_tas_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TAS_TAS_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tas_tas_id);
      }
      if (n01_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"N01 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(n01);
      }
      if (n02_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"N02 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(n02);
      }
      if (n03_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"N03 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(n03);
      }
      if (n04_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"N04 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(n04);
      }
      if (n05_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"N05 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(n05);
      }
      if (c01_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"C01 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(c01);
      }
      if (c02_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"C02 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(c02);
      }
      if (c03_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"C03 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(c03);
      }
      if (c04_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"C04 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(c04);
      }
      if (c05_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"C05 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(c05);
      }
      if (d01_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D01 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d01);
      }
      if (d02_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D02 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d02);
      }
      if (d03_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D03 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d03);
      }
      if (d04_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D04 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d04);
      }
      if (d05_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D05 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d05);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  TAS_ID = ? ";
      updateStatementPart.addStatementParam(tas_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprTasksDAO\":{\"tas_id\": \""+getTas_id()+"\", \"tas_name\": \""+getTas_name()+"\", \"tas_type\": \""+getTas_type()+"\", \"tas_status\": \""+getTas_status()+"\", \"tas_date_from\": \""+getTas_date_from()+"\", \"tas_date_to\": \""+getTas_date_to()+"\", \"tas_reject_reason\": \""+getTas_reject_reason()+"\", \"tas_priority\": \""+getTas_priority()+"\", \"tas_repetitive\": \""+getTas_repetitive()+"\", \"tas_description\": \""+getTas_description()+"\", \"tas_usr_id\": \""+getTas_usr_id()+"\", \"tas_tas_id\": \""+getTas_tas_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}