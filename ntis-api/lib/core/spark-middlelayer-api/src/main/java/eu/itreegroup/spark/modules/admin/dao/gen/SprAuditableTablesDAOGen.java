package eu.itreegroup.spark.modules.admin.dao.gen;

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

public class SprAuditableTablesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_AUDITABLE_TABLES.AUT_ID";
   private Double aut_id;
   private String aut_table_schema;
   private String aut_table_name;
   private String aut_trigger_enabled;
   private String aut_trigger_name;
   private Double aut_num_of_days_in_audit;
   private Double aut_num_of_days_in_archive;
   private String aut_audit_table_name;
   private String aut_archive_table_name;
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

   protected boolean aut_id_changed;
   protected boolean aut_table_schema_changed;
   protected boolean aut_table_name_changed;
   protected boolean aut_trigger_enabled_changed;
   protected boolean aut_trigger_name_changed;
   protected boolean aut_num_of_days_in_audit_changed;
   protected boolean aut_num_of_days_in_archive_changed;
   protected boolean aut_audit_table_name_changed;
   protected boolean aut_archive_table_name_changed;
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
   public SprAuditableTablesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprAuditableTablesDAOGen(Double aut_id, String aut_table_schema, String aut_table_name, String aut_trigger_enabled, String aut_trigger_name, Double aut_num_of_days_in_audit, Double aut_num_of_days_in_archive, String aut_audit_table_name, String aut_archive_table_name, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.aut_id = aut_id;
      this.aut_table_schema = aut_table_schema;
      this.aut_table_name = aut_table_name;
      this.aut_trigger_enabled = aut_trigger_enabled;
      this.aut_trigger_name = aut_trigger_name;
      this.aut_num_of_days_in_audit = aut_num_of_days_in_audit;
      this.aut_num_of_days_in_archive = aut_num_of_days_in_archive;
      this.aut_audit_table_name = aut_audit_table_name;
      this.aut_archive_table_name = aut_archive_table_name;
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
   public void copyValues(SprAuditableTablesDAOGen obj) {
      this.setAut_id(obj.getAut_id());
      this.setAut_table_schema(obj.getAut_table_schema());
      this.setAut_table_name(obj.getAut_table_name());
      this.setAut_trigger_enabled(obj.getAut_trigger_enabled());
      this.setAut_trigger_name(obj.getAut_trigger_name());
      this.setAut_num_of_days_in_audit(obj.getAut_num_of_days_in_audit());
      this.setAut_num_of_days_in_archive(obj.getAut_num_of_days_in_archive());
      this.setAut_audit_table_name(obj.getAut_audit_table_name());
      this.setAut_archive_table_name(obj.getAut_archive_table_name());
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
      this.aut_id_changed = false;
      this.aut_table_schema_changed = false;
      this.aut_table_name_changed = false;
      this.aut_trigger_enabled_changed = false;
      this.aut_trigger_name_changed = false;
      this.aut_num_of_days_in_audit_changed = false;
      this.aut_num_of_days_in_archive_changed = false;
      this.aut_audit_table_name_changed = false;
      this.aut_archive_table_name_changed = false;
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
   public void setAut_id(Double aut_id) {
      if (this.isForceUpdate() || 
               (this.aut_id != null && !this.aut_id.equals(aut_id))  ||
               (aut_id != null && !aut_id.equals(this.aut_id)) ){
         this.aut_id_changed = true; 
         this.record_changed = true;
         this.aut_id = aut_id;
      }
   }
   public Double getAut_id() {
      return this.aut_id;
   }
   public void setAut_table_schema(String aut_table_schema) {
      if (this.isForceUpdate() || 
               (this.aut_table_schema != null && !this.aut_table_schema.equals(aut_table_schema))  ||
               (aut_table_schema != null && !aut_table_schema.equals(this.aut_table_schema)) ){
         this.aut_table_schema_changed = true; 
         this.record_changed = true;
         this.aut_table_schema = aut_table_schema;
      }
   }
   public String getAut_table_schema() {
      return this.aut_table_schema;
   }
   public void setAut_table_name(String aut_table_name) {
      if (this.isForceUpdate() || 
               (this.aut_table_name != null && !this.aut_table_name.equals(aut_table_name))  ||
               (aut_table_name != null && !aut_table_name.equals(this.aut_table_name)) ){
         this.aut_table_name_changed = true; 
         this.record_changed = true;
         this.aut_table_name = aut_table_name;
      }
   }
   public String getAut_table_name() {
      return this.aut_table_name;
   }
   public void setAut_trigger_enabled(String aut_trigger_enabled) {
      if (this.isForceUpdate() || 
               (this.aut_trigger_enabled != null && !this.aut_trigger_enabled.equals(aut_trigger_enabled))  ||
               (aut_trigger_enabled != null && !aut_trigger_enabled.equals(this.aut_trigger_enabled)) ){
         this.aut_trigger_enabled_changed = true; 
         this.record_changed = true;
         this.aut_trigger_enabled = aut_trigger_enabled;
      }
   }
   public String getAut_trigger_enabled() {
      return this.aut_trigger_enabled;
   }
   public void setAut_trigger_name(String aut_trigger_name) {
      if (this.isForceUpdate() || 
               (this.aut_trigger_name != null && !this.aut_trigger_name.equals(aut_trigger_name))  ||
               (aut_trigger_name != null && !aut_trigger_name.equals(this.aut_trigger_name)) ){
         this.aut_trigger_name_changed = true; 
         this.record_changed = true;
         this.aut_trigger_name = aut_trigger_name;
      }
   }
   public String getAut_trigger_name() {
      return this.aut_trigger_name;
   }
   public void setAut_num_of_days_in_audit(Double aut_num_of_days_in_audit) {
      if (this.isForceUpdate() || 
               (this.aut_num_of_days_in_audit != null && !this.aut_num_of_days_in_audit.equals(aut_num_of_days_in_audit))  ||
               (aut_num_of_days_in_audit != null && !aut_num_of_days_in_audit.equals(this.aut_num_of_days_in_audit)) ){
         this.aut_num_of_days_in_audit_changed = true; 
         this.record_changed = true;
         this.aut_num_of_days_in_audit = aut_num_of_days_in_audit;
      }
   }
   public Double getAut_num_of_days_in_audit() {
      return this.aut_num_of_days_in_audit;
   }
   public void setAut_num_of_days_in_archive(Double aut_num_of_days_in_archive) {
      if (this.isForceUpdate() || 
               (this.aut_num_of_days_in_archive != null && !this.aut_num_of_days_in_archive.equals(aut_num_of_days_in_archive))  ||
               (aut_num_of_days_in_archive != null && !aut_num_of_days_in_archive.equals(this.aut_num_of_days_in_archive)) ){
         this.aut_num_of_days_in_archive_changed = true; 
         this.record_changed = true;
         this.aut_num_of_days_in_archive = aut_num_of_days_in_archive;
      }
   }
   public Double getAut_num_of_days_in_archive() {
      return this.aut_num_of_days_in_archive;
   }
   public void setAut_audit_table_name(String aut_audit_table_name) {
      if (this.isForceUpdate() || 
               (this.aut_audit_table_name != null && !this.aut_audit_table_name.equals(aut_audit_table_name))  ||
               (aut_audit_table_name != null && !aut_audit_table_name.equals(this.aut_audit_table_name)) ){
         this.aut_audit_table_name_changed = true; 
         this.record_changed = true;
         this.aut_audit_table_name = aut_audit_table_name;
      }
   }
   public String getAut_audit_table_name() {
      return this.aut_audit_table_name;
   }
   public void setAut_archive_table_name(String aut_archive_table_name) {
      if (this.isForceUpdate() || 
               (this.aut_archive_table_name != null && !this.aut_archive_table_name.equals(aut_archive_table_name))  ||
               (aut_archive_table_name != null && !aut_archive_table_name.equals(this.aut_archive_table_name)) ){
         this.aut_archive_table_name_changed = true; 
         this.record_changed = true;
         this.aut_archive_table_name = aut_archive_table_name;
      }
   }
   public String getAut_archive_table_name() {
      return this.aut_archive_table_name;
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
            this.aut_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.aut_id;
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
      if (aut_table_schema_changed || operation == Utils.OPERATION_INSERT) {
         if (aut_table_schema!= null && aut_table_schema.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "AUT_TABLE_SCHEMA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (aut_table_name_changed || operation == Utils.OPERATION_INSERT) {
         if (aut_table_name!= null && aut_table_name.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "AUT_TABLE_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (aut_trigger_enabled_changed || operation == Utils.OPERATION_INSERT) {
         if (aut_trigger_enabled!= null && aut_trigger_enabled.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "AUT_TRIGGER_ENABLED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (aut_trigger_name_changed || operation == Utils.OPERATION_INSERT) {
         if (aut_trigger_name!= null && aut_trigger_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "AUT_TRIGGER_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (aut_audit_table_name_changed || operation == Utils.OPERATION_INSERT) {
         if (aut_audit_table_name!= null && aut_audit_table_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "AUT_AUDIT_TABLE_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (aut_archive_table_name_changed || operation == Utils.OPERATION_INSERT) {
         if (aut_archive_table_name!= null && aut_archive_table_name.length()>240) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "AUT_ARCHIVE_TABLE_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"240"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_AUDITABLE_TABLES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_AUDITABLE_TABLES SET ";
      boolean changedExists = false;      if (aut_table_schema_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_TABLE_SCHEMA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_table_schema);
      }
      if (aut_table_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_TABLE_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_table_name);
      }
      if (aut_trigger_enabled_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_TRIGGER_ENABLED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_trigger_enabled);
      }
      if (aut_trigger_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_TRIGGER_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_trigger_name);
      }
      if (aut_num_of_days_in_audit_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_NUM_OF_DAYS_IN_AUDIT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_num_of_days_in_audit);
      }
      if (aut_num_of_days_in_archive_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_NUM_OF_DAYS_IN_ARCHIVE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_num_of_days_in_archive);
      }
      if (aut_audit_table_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_AUDIT_TABLE_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_audit_table_name);
      }
      if (aut_archive_table_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AUT_ARCHIVE_TABLE_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(aut_archive_table_name);
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
      answer = answer +" WHERE  AUT_ID = ? ";
      updateStatementPart.addStatementParam(aut_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprAuditableTablesDAO\":{\"aut_id\": \""+getAut_id()+"\", \"aut_table_schema\": \""+getAut_table_schema()+"\", \"aut_table_name\": \""+getAut_table_name()+"\", \"aut_trigger_enabled\": \""+getAut_trigger_enabled()+"\", \"aut_trigger_name\": \""+getAut_trigger_name()+"\", \"aut_num_of_days_in_audit\": \""+getAut_num_of_days_in_audit()+"\", \"aut_num_of_days_in_archive\": \""+getAut_num_of_days_in_archive()+"\", \"aut_audit_table_name\": \""+getAut_audit_table_name()+"\", \"aut_archive_table_name\": \""+getAut_archive_table_name()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}