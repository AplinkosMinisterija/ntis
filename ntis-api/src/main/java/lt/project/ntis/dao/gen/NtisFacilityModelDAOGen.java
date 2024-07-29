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

public class NtisFacilityModelDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_FACILITY_MODEL.FAM_ID";
   private Double fam_id;
   private Double fam_rfc_id;
   private Double fam_pop_equivalent;
   private String fam_tech_pass;
   private Double fam_fil_id;
   private Double fam_chds;
   private Double fam_bds;
   private Double fam_float_material;
   private Double fam_phosphor;
   private Double fam_nitrogen;
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
   private String fam_manufacturer;
   private String fam_model;
   private String fam_description;

   protected boolean fam_id_changed;
   protected boolean fam_rfc_id_changed;
   protected boolean fam_pop_equivalent_changed;
   protected boolean fam_tech_pass_changed;
   protected boolean fam_fil_id_changed;
   protected boolean fam_chds_changed;
   protected boolean fam_bds_changed;
   protected boolean fam_float_material_changed;
   protected boolean fam_phosphor_changed;
   protected boolean fam_nitrogen_changed;
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
   protected boolean fam_manufacturer_changed;
   protected boolean fam_model_changed;
   protected boolean fam_description_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisFacilityModelDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisFacilityModelDAOGen(Double fam_id, Double fam_rfc_id, Double fam_pop_equivalent, String fam_tech_pass, Double fam_fil_id, Double fam_chds, Double fam_bds, Double fam_float_material, Double fam_phosphor, Double fam_nitrogen, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String fam_manufacturer, String fam_model, String fam_description) {
      this.fam_id = fam_id;
      this.fam_rfc_id = fam_rfc_id;
      this.fam_pop_equivalent = fam_pop_equivalent;
      this.fam_tech_pass = fam_tech_pass;
      this.fam_fil_id = fam_fil_id;
      this.fam_chds = fam_chds;
      this.fam_bds = fam_bds;
      this.fam_float_material = fam_float_material;
      this.fam_phosphor = fam_phosphor;
      this.fam_nitrogen = fam_nitrogen;
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
      this.fam_manufacturer = fam_manufacturer;
      this.fam_model = fam_model;
      this.fam_description = fam_description;
   }
   public void copyValues(NtisFacilityModelDAOGen obj) {
      this.setFam_id(obj.getFam_id());
      this.setFam_rfc_id(obj.getFam_rfc_id());
      this.setFam_pop_equivalent(obj.getFam_pop_equivalent());
      this.setFam_tech_pass(obj.getFam_tech_pass());
      this.setFam_fil_id(obj.getFam_fil_id());
      this.setFam_chds(obj.getFam_chds());
      this.setFam_bds(obj.getFam_bds());
      this.setFam_float_material(obj.getFam_float_material());
      this.setFam_phosphor(obj.getFam_phosphor());
      this.setFam_nitrogen(obj.getFam_nitrogen());
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
      this.setFam_manufacturer(obj.getFam_manufacturer());
      this.setFam_model(obj.getFam_model());
      this.setFam_description(obj.getFam_description());
   }
   protected void markObjectAsInitial() {
      this.fam_id_changed = false;
      this.fam_rfc_id_changed = false;
      this.fam_pop_equivalent_changed = false;
      this.fam_tech_pass_changed = false;
      this.fam_fil_id_changed = false;
      this.fam_chds_changed = false;
      this.fam_bds_changed = false;
      this.fam_float_material_changed = false;
      this.fam_phosphor_changed = false;
      this.fam_nitrogen_changed = false;
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
      this.fam_manufacturer_changed = false;
      this.fam_model_changed = false;
      this.fam_description_changed = false;
      this.record_changed = false;
   }
   public void setFam_id(Double fam_id) {
      if (this.isForceUpdate() || 
               (this.fam_id != null && !this.fam_id.equals(fam_id))  ||
               (fam_id != null && !fam_id.equals(this.fam_id)) ){
         this.fam_id_changed = true; 
         this.record_changed = true;
         this.fam_id = fam_id;
      }
   }
   public Double getFam_id() {
      return this.fam_id;
   }
   public void setFam_rfc_id(Double fam_rfc_id) {
      if (this.isForceUpdate() || 
               (this.fam_rfc_id != null && !this.fam_rfc_id.equals(fam_rfc_id))  ||
               (fam_rfc_id != null && !fam_rfc_id.equals(this.fam_rfc_id)) ){
         this.fam_rfc_id_changed = true; 
         this.record_changed = true;
         this.fam_rfc_id = fam_rfc_id;
      }
   }
   public Double getFam_rfc_id() {
      return this.fam_rfc_id;
   }
   public void setFam_pop_equivalent(Double fam_pop_equivalent) {
      if (this.isForceUpdate() || 
               (this.fam_pop_equivalent != null && !this.fam_pop_equivalent.equals(fam_pop_equivalent))  ||
               (fam_pop_equivalent != null && !fam_pop_equivalent.equals(this.fam_pop_equivalent)) ){
         this.fam_pop_equivalent_changed = true; 
         this.record_changed = true;
         this.fam_pop_equivalent = fam_pop_equivalent;
      }
   }
   public Double getFam_pop_equivalent() {
      return this.fam_pop_equivalent;
   }
   public void setFam_tech_pass(String fam_tech_pass) {
      if (this.isForceUpdate() || 
               (this.fam_tech_pass != null && !this.fam_tech_pass.equals(fam_tech_pass))  ||
               (fam_tech_pass != null && !fam_tech_pass.equals(this.fam_tech_pass)) ){
         this.fam_tech_pass_changed = true; 
         this.record_changed = true;
         this.fam_tech_pass = fam_tech_pass;
      }
   }
   public String getFam_tech_pass() {
      return this.fam_tech_pass;
   }
   public void setFam_fil_id(Double fam_fil_id) {
      if (this.isForceUpdate() || 
               (this.fam_fil_id != null && !this.fam_fil_id.equals(fam_fil_id))  ||
               (fam_fil_id != null && !fam_fil_id.equals(this.fam_fil_id)) ){
         this.fam_fil_id_changed = true; 
         this.record_changed = true;
         this.fam_fil_id = fam_fil_id;
      }
   }
   public Double getFam_fil_id() {
      return this.fam_fil_id;
   }
   public void setFam_chds(Double fam_chds) {
      if (this.isForceUpdate() || 
               (this.fam_chds != null && !this.fam_chds.equals(fam_chds))  ||
               (fam_chds != null && !fam_chds.equals(this.fam_chds)) ){
         this.fam_chds_changed = true; 
         this.record_changed = true;
         this.fam_chds = fam_chds;
      }
   }
   public Double getFam_chds() {
      return this.fam_chds;
   }
   public void setFam_bds(Double fam_bds) {
      if (this.isForceUpdate() || 
               (this.fam_bds != null && !this.fam_bds.equals(fam_bds))  ||
               (fam_bds != null && !fam_bds.equals(this.fam_bds)) ){
         this.fam_bds_changed = true; 
         this.record_changed = true;
         this.fam_bds = fam_bds;
      }
   }
   public Double getFam_bds() {
      return this.fam_bds;
   }
   public void setFam_float_material(Double fam_float_material) {
      if (this.isForceUpdate() || 
               (this.fam_float_material != null && !this.fam_float_material.equals(fam_float_material))  ||
               (fam_float_material != null && !fam_float_material.equals(this.fam_float_material)) ){
         this.fam_float_material_changed = true; 
         this.record_changed = true;
         this.fam_float_material = fam_float_material;
      }
   }
   public Double getFam_float_material() {
      return this.fam_float_material;
   }
   public void setFam_phosphor(Double fam_phosphor) {
      if (this.isForceUpdate() || 
               (this.fam_phosphor != null && !this.fam_phosphor.equals(fam_phosphor))  ||
               (fam_phosphor != null && !fam_phosphor.equals(this.fam_phosphor)) ){
         this.fam_phosphor_changed = true; 
         this.record_changed = true;
         this.fam_phosphor = fam_phosphor;
      }
   }
   public Double getFam_phosphor() {
      return this.fam_phosphor;
   }
   public void setFam_nitrogen(Double fam_nitrogen) {
      if (this.isForceUpdate() || 
               (this.fam_nitrogen != null && !this.fam_nitrogen.equals(fam_nitrogen))  ||
               (fam_nitrogen != null && !fam_nitrogen.equals(this.fam_nitrogen)) ){
         this.fam_nitrogen_changed = true; 
         this.record_changed = true;
         this.fam_nitrogen = fam_nitrogen;
      }
   }
   public Double getFam_nitrogen() {
      return this.fam_nitrogen;
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
   public void setFam_manufacturer(String fam_manufacturer) {
      if (this.isForceUpdate() || 
               (this.fam_manufacturer != null && !this.fam_manufacturer.equals(fam_manufacturer))  ||
               (fam_manufacturer != null && !fam_manufacturer.equals(this.fam_manufacturer)) ){
         this.fam_manufacturer_changed = true; 
         this.record_changed = true;
         this.fam_manufacturer = fam_manufacturer;
      }
   }
   public String getFam_manufacturer() {
      return this.fam_manufacturer;
   }
   public void setFam_model(String fam_model) {
      if (this.isForceUpdate() || 
               (this.fam_model != null && !this.fam_model.equals(fam_model))  ||
               (fam_model != null && !fam_model.equals(this.fam_model)) ){
         this.fam_model_changed = true; 
         this.record_changed = true;
         this.fam_model = fam_model;
      }
   }
   public String getFam_model() {
      return this.fam_model;
   }
   public void setFam_description(String fam_description) {
      if (this.isForceUpdate() || 
               (this.fam_description != null && !this.fam_description.equals(fam_description))  ||
               (fam_description != null && !fam_description.equals(this.fam_description)) ){
         this.fam_description_changed = true; 
         this.record_changed = true;
         this.fam_description = fam_description;
      }
   }
   public String getFam_description() {
      return this.fam_description;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.fam_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.fam_id;
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
      if (fam_rfc_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_rfc_id!= null && (""+fam_rfc_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_RFC_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fam_pop_equivalent_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_pop_equivalent!= null && (""+fam_pop_equivalent.intValue()).length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_POP_EQUIVALENT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (fam_tech_pass_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_tech_pass!= null && fam_tech_pass.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_TECH_PASS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (fam_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_fil_id!= null && (""+fam_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fam_chds_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_chds!= null && fam_chds.toString().length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_CHDS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fam_bds_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_bds!= null && fam_bds.toString().length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_BDS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fam_float_material_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_float_material!= null && fam_float_material.toString().length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_FLOAT_MATERIAL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fam_phosphor_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_phosphor!= null && fam_phosphor.toString().length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_PHOSPHOR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fam_nitrogen_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_nitrogen!= null && fam_nitrogen.toString().length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_NITROGEN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (fam_manufacturer_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_manufacturer!= null && fam_manufacturer.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_MANUFACTURER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (fam_model_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_model!= null && fam_model.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_MODEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (fam_description_changed || operation == Utils.OPERATION_INSERT) {
         if (fam_description!= null && fam_description.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_MODEL", "FAM_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_FACILITY_MODEL SET ";
      boolean changedExists = false;      if (fam_rfc_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_RFC_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_rfc_id);
      }
      if (fam_pop_equivalent_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_POP_EQUIVALENT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_pop_equivalent);
      }
      if (fam_tech_pass_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_TECH_PASS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_tech_pass);
      }
      if (fam_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_fil_id);
      }
      if (fam_chds_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_CHDS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_chds);
      }
      if (fam_bds_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_BDS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_bds);
      }
      if (fam_float_material_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_FLOAT_MATERIAL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_float_material);
      }
      if (fam_phosphor_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_PHOSPHOR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_phosphor);
      }
      if (fam_nitrogen_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_NITROGEN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_nitrogen);
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
         updateStatementPart.addStatementTimestampParam(d01);
      }
      if (d02_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D02 = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(d02);
      }
      if (d03_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D03 = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(d03);
      }
      if (d04_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D04 = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(d04);
      }
      if (d05_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D05 = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(d05);
      }
      if (fam_manufacturer_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_MANUFACTURER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_manufacturer);
      }
      if (fam_model_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_MODEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_model);
      }
      if (fam_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FAM_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fam_description);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  FAM_ID = ? ";
      updateStatementPart.addStatementParam(fam_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisFacilityModelDAO\":{\"fam_id\": \""+getFam_id()+"\", \"fam_rfc_id\": \""+getFam_rfc_id()+"\", \"fam_pop_equivalent\": \""+getFam_pop_equivalent()+"\", \"fam_tech_pass\": \""+getFam_tech_pass()+"\", \"fam_fil_id\": \""+getFam_fil_id()+"\", \"fam_chds\": \""+getFam_chds()+"\", \"fam_bds\": \""+getFam_bds()+"\", \"fam_float_material\": \""+getFam_float_material()+"\", \"fam_phosphor\": \""+getFam_phosphor()+"\", \"fam_nitrogen\": \""+getFam_nitrogen()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\", \"fam_manufacturer\": \""+getFam_manufacturer()+"\", \"fam_model\": \""+getFam_model()+"\", \"fam_description\": \""+getFam_description()+"\"}}";
   }

}