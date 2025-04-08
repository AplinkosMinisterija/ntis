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

public class SprPropertiesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_PROPERTIES.PRP_ID";
   private Double prp_id;
   private String prp_name;
   private String prp_description;
   private String prp_type;
   private String prp_value;
   private Double prp_fil_id;
   private String prp_guid;
   private String prp_install_instance;
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

   protected boolean prp_id_changed;
   protected boolean prp_name_changed;
   protected boolean prp_description_changed;
   protected boolean prp_type_changed;
   protected boolean prp_value_changed;
   protected boolean prp_fil_id_changed;
   protected boolean prp_guid_changed;
   protected boolean prp_install_instance_changed;
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
   public SprPropertiesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprPropertiesDAOGen(Double prp_id, String prp_name, String prp_description, String prp_type, String prp_value, Double prp_fil_id, String prp_guid, String prp_install_instance, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.prp_id = prp_id;
      this.prp_name = prp_name;
      this.prp_description = prp_description;
      this.prp_type = prp_type;
      this.prp_value = prp_value;
      this.prp_fil_id = prp_fil_id;
      this.prp_guid = prp_guid;
      this.prp_install_instance = prp_install_instance;
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
   public void copyValues(SprPropertiesDAOGen obj) {
      this.setPrp_id(obj.getPrp_id());
      this.setPrp_name(obj.getPrp_name());
      this.setPrp_description(obj.getPrp_description());
      this.setPrp_type(obj.getPrp_type());
      this.setPrp_value(obj.getPrp_value());
      this.setPrp_fil_id(obj.getPrp_fil_id());
      this.setPrp_guid(obj.getPrp_guid());
      this.setPrp_install_instance(obj.getPrp_install_instance());
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
      this.prp_id_changed = false;
      this.prp_name_changed = false;
      this.prp_description_changed = false;
      this.prp_type_changed = false;
      this.prp_value_changed = false;
      this.prp_fil_id_changed = false;
      this.prp_guid_changed = false;
      this.prp_install_instance_changed = false;
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
   public void setPrp_id(Double prp_id) {
      if (this.isForceUpdate() || 
               (this.prp_id != null && !this.prp_id.equals(prp_id))  ||
               (prp_id != null && !prp_id.equals(this.prp_id)) ){
         this.prp_id_changed = true; 
         this.record_changed = true;
         this.prp_id = prp_id;
      }
   }
   public Double getPrp_id() {
      return this.prp_id;
   }
   public void setPrp_name(String prp_name) {
      if (this.isForceUpdate() || 
               (this.prp_name != null && !this.prp_name.equals(prp_name))  ||
               (prp_name != null && !prp_name.equals(this.prp_name)) ){
         this.prp_name_changed = true; 
         this.record_changed = true;
         this.prp_name = prp_name;
      }
   }
   public String getPrp_name() {
      return this.prp_name;
   }
   public void setPrp_description(String prp_description) {
      if (this.isForceUpdate() || 
               (this.prp_description != null && !this.prp_description.equals(prp_description))  ||
               (prp_description != null && !prp_description.equals(this.prp_description)) ){
         this.prp_description_changed = true; 
         this.record_changed = true;
         this.prp_description = prp_description;
      }
   }
   public String getPrp_description() {
      return this.prp_description;
   }
   public void setPrp_type(String prp_type) {
      if (this.isForceUpdate() || 
               (this.prp_type != null && !this.prp_type.equals(prp_type))  ||
               (prp_type != null && !prp_type.equals(this.prp_type)) ){
         this.prp_type_changed = true; 
         this.record_changed = true;
         this.prp_type = prp_type;
      }
   }
   public String getPrp_type() {
      return this.prp_type;
   }
   public void setPrp_value(String prp_value) {
      if (this.isForceUpdate() || 
               (this.prp_value != null && !this.prp_value.equals(prp_value))  ||
               (prp_value != null && !prp_value.equals(this.prp_value)) ){
         this.prp_value_changed = true; 
         this.record_changed = true;
         this.prp_value = prp_value;
      }
   }
   public String getPrp_value() {
      return this.prp_value;
   }
   public void setPrp_fil_id(Double prp_fil_id) {
      if (this.isForceUpdate() || 
               (this.prp_fil_id != null && !this.prp_fil_id.equals(prp_fil_id))  ||
               (prp_fil_id != null && !prp_fil_id.equals(this.prp_fil_id)) ){
         this.prp_fil_id_changed = true; 
         this.record_changed = true;
         this.prp_fil_id = prp_fil_id;
      }
   }
   public Double getPrp_fil_id() {
      return this.prp_fil_id;
   }
   public void setPrp_guid(String prp_guid) {
      if (this.isForceUpdate() || 
               (this.prp_guid != null && !this.prp_guid.equals(prp_guid))  ||
               (prp_guid != null && !prp_guid.equals(this.prp_guid)) ){
         this.prp_guid_changed = true; 
         this.record_changed = true;
         this.prp_guid = prp_guid;
      }
   }
   public String getPrp_guid() {
      return this.prp_guid;
   }
   public void setPrp_install_instance(String prp_install_instance) {
      if (this.isForceUpdate() || 
               (this.prp_install_instance != null && !this.prp_install_instance.equals(prp_install_instance))  ||
               (prp_install_instance != null && !prp_install_instance.equals(this.prp_install_instance)) ){
         this.prp_install_instance_changed = true; 
         this.record_changed = true;
         this.prp_install_instance = prp_install_instance;
      }
   }
   public String getPrp_install_instance() {
      return this.prp_install_instance;
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
            this.prp_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.prp_id;
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
      if (prp_name_changed || operation == Utils.OPERATION_INSERT) {
         if (prp_name== null || EMPTY_STRING.equals(prp_name)) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (prp_name!= null && prp_name.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (prp_description_changed || operation == Utils.OPERATION_INSERT) {
         if (prp_description!= null && prp_description.length()>1000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (prp_type_changed || operation == Utils.OPERATION_INSERT) {
         if (prp_type== null || EMPTY_STRING.equals(prp_type)) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (prp_type!= null && prp_type.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (prp_value_changed || operation == Utils.OPERATION_INSERT) {
         if (prp_value!= null && prp_value.length()>1000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_VALUE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (prp_guid_changed || operation == Utils.OPERATION_INSERT) {
         if (prp_guid!= null && prp_guid.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_GUID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (prp_install_instance_changed || operation == Utils.OPERATION_INSERT) {
         if (prp_install_instance!= null && prp_install_instance.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "PRP_INSTALL_INSTANCE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PROPERTIES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_PROPERTIES SET ";
      boolean changedExists = false;      if (prp_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_name);
      }
      if (prp_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_description);
      }
      if (prp_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_type);
      }
      if (prp_value_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_VALUE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_value);
      }
      if (prp_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_fil_id);
      }
      if (prp_guid_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_GUID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_guid);
      }
      if (prp_install_instance_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PRP_INSTALL_INSTANCE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(prp_install_instance);
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
      answer = answer +" WHERE  PRP_ID = ? ";
      updateStatementPart.addStatementParam(prp_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprPropertiesDAO\":{\"prp_id\": \""+getPrp_id()+"\", \"prp_name\": \""+getPrp_name()+"\", \"prp_description\": \""+getPrp_description()+"\", \"prp_type\": \""+getPrp_type()+"\", \"prp_value\": \""+getPrp_value()+"\", \"prp_fil_id\": \""+getPrp_fil_id()+"\", \"prp_guid\": \""+getPrp_guid()+"\", \"prp_install_instance\": \""+getPrp_install_instance()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}