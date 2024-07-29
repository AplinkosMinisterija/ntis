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

public class NtisWastewaterTreatmentFaciDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_WASTEWATER_TREATMENT_FACI.WTF_ID";
   private Double wtf_id;
   private String wtf_manufacturer;
   private String wtf_manufacturer_description;
   private String wtf_model;
   private String wtf_type;
   private String wtf_capacity;
   private Double wtf_distance;
   private String wtf_technical_passport_id;
   private String wtf_state;
   private String wtf_data_source;
   private Date wtf_installation_date;
   private Date wtf_checkout_date;
   private String wtf_deleted;
   private Double wtf_facility_latitude;
   private Double wtf_facility_longitude;
   private String wtf_facility_municipality_code;
   private String wtf_discharge_type;
   private Double wtf_discharge_latitude;
   private Double wtf_discharge_longitude;
   private Double wtf_ad_id;
   private Double wtf_agg_id;
   private String wtf_waiting_update_confirmation;
   private Double wtf_nc_ad_id;
   private Double wtf_nc_agg_id;
   private String wtf_nc_manufacturer;
   private String wtf_nc_manufacturer_description;
   private String wtf_nc_model;
   private String wtf_nc_type;
   private String wtf_nc_capacity;
   private Double wtf_nc_distance;
   private String wtf_nc_technical_passport_id;
   private String wtf_nc_data_source;
   private Date wtf_nc_installation_date;
   private Date wtf_nc_checkout_date;
   private String wtf_nc_deleted;
   private Double wtf_nc_facility_latitude;
   private Double wtf_nc_facility_longitude;
   private String wtf_nc_facility_municipality_code;
   private String wtf_nc_discharge_type;
   private Double wtf_nc_discharge_latitude;
   private Double wtf_nc_discharge_longitude;
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
   private Double wtf_fam_id;
   private Double wtf_nc_fam_pop_equivalent;
   private String wtf_nc_fam_tech_pass;
   private Double wtf_nc_fam_chds;
   private Double wtf_nc_fam_bds;
   private Double wtf_nc_fam_float_material;
   private Double wtf_nc_fam_phosphor;
   private Double wtf_nc_fam_nitrogen;
   private String wtf_nc_fam_manufacturer;
   private String wtf_nc_fam_model;
   private String wtf_nc_fam_description;

   protected boolean wtf_id_changed;
   protected boolean wtf_manufacturer_changed;
   protected boolean wtf_manufacturer_description_changed;
   protected boolean wtf_model_changed;
   protected boolean wtf_type_changed;
   protected boolean wtf_capacity_changed;
   protected boolean wtf_distance_changed;
   protected boolean wtf_technical_passport_id_changed;
   protected boolean wtf_state_changed;
   protected boolean wtf_data_source_changed;
   protected boolean wtf_installation_date_changed;
   protected boolean wtf_checkout_date_changed;
   protected boolean wtf_deleted_changed;
   protected boolean wtf_facility_latitude_changed;
   protected boolean wtf_facility_longitude_changed;
   protected boolean wtf_facility_municipality_code_changed;
   protected boolean wtf_discharge_type_changed;
   protected boolean wtf_discharge_latitude_changed;
   protected boolean wtf_discharge_longitude_changed;
   protected boolean wtf_ad_id_changed;
   protected boolean wtf_agg_id_changed;
   protected boolean wtf_waiting_update_confirmation_changed;
   protected boolean wtf_nc_ad_id_changed;
   protected boolean wtf_nc_agg_id_changed;
   protected boolean wtf_nc_manufacturer_changed;
   protected boolean wtf_nc_manufacturer_description_changed;
   protected boolean wtf_nc_model_changed;
   protected boolean wtf_nc_type_changed;
   protected boolean wtf_nc_capacity_changed;
   protected boolean wtf_nc_distance_changed;
   protected boolean wtf_nc_technical_passport_id_changed;
   protected boolean wtf_nc_data_source_changed;
   protected boolean wtf_nc_installation_date_changed;
   protected boolean wtf_nc_checkout_date_changed;
   protected boolean wtf_nc_deleted_changed;
   protected boolean wtf_nc_facility_latitude_changed;
   protected boolean wtf_nc_facility_longitude_changed;
   protected boolean wtf_nc_facility_municipality_code_changed;
   protected boolean wtf_nc_discharge_type_changed;
   protected boolean wtf_nc_discharge_latitude_changed;
   protected boolean wtf_nc_discharge_longitude_changed;
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
   protected boolean wtf_fam_id_changed;
   protected boolean wtf_nc_fam_pop_equivalent_changed;
   protected boolean wtf_nc_fam_tech_pass_changed;
   protected boolean wtf_nc_fam_chds_changed;
   protected boolean wtf_nc_fam_bds_changed;
   protected boolean wtf_nc_fam_float_material_changed;
   protected boolean wtf_nc_fam_phosphor_changed;
   protected boolean wtf_nc_fam_nitrogen_changed;
   protected boolean wtf_nc_fam_manufacturer_changed;
   protected boolean wtf_nc_fam_model_changed;
   protected boolean wtf_nc_fam_description_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisWastewaterTreatmentFaciDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisWastewaterTreatmentFaciDAOGen(Double wtf_id, String wtf_manufacturer, String wtf_manufacturer_description, String wtf_model, String wtf_type, String wtf_capacity, Double wtf_distance, String wtf_technical_passport_id, String wtf_state, String wtf_data_source, Date wtf_installation_date, Date wtf_checkout_date, String wtf_deleted, Double wtf_facility_latitude, Double wtf_facility_longitude, String wtf_facility_municipality_code, String wtf_discharge_type, Double wtf_discharge_latitude, Double wtf_discharge_longitude, Double wtf_ad_id, Double wtf_agg_id, String wtf_waiting_update_confirmation, Double wtf_nc_ad_id, Double wtf_nc_agg_id, String wtf_nc_manufacturer, String wtf_nc_manufacturer_description, String wtf_nc_model, String wtf_nc_type, String wtf_nc_capacity, Double wtf_nc_distance, String wtf_nc_technical_passport_id, String wtf_nc_data_source, Date wtf_nc_installation_date, Date wtf_nc_checkout_date, String wtf_nc_deleted, Double wtf_nc_facility_latitude, Double wtf_nc_facility_longitude, String wtf_nc_facility_municipality_code, String wtf_nc_discharge_type, Double wtf_nc_discharge_latitude, Double wtf_nc_discharge_longitude, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Double wtf_fam_id, Double wtf_nc_fam_pop_equivalent, String wtf_nc_fam_tech_pass, Double wtf_nc_fam_chds, Double wtf_nc_fam_bds, Double wtf_nc_fam_float_material, Double wtf_nc_fam_phosphor, Double wtf_nc_fam_nitrogen, String wtf_nc_fam_manufacturer, String wtf_nc_fam_model, String wtf_nc_fam_description) {
      this.wtf_id = wtf_id;
      this.wtf_manufacturer = wtf_manufacturer;
      this.wtf_manufacturer_description = wtf_manufacturer_description;
      this.wtf_model = wtf_model;
      this.wtf_type = wtf_type;
      this.wtf_capacity = wtf_capacity;
      this.wtf_distance = wtf_distance;
      this.wtf_technical_passport_id = wtf_technical_passport_id;
      this.wtf_state = wtf_state;
      this.wtf_data_source = wtf_data_source;
      this.wtf_installation_date = wtf_installation_date;
      this.wtf_checkout_date = wtf_checkout_date;
      this.wtf_deleted = wtf_deleted;
      this.wtf_facility_latitude = wtf_facility_latitude;
      this.wtf_facility_longitude = wtf_facility_longitude;
      this.wtf_facility_municipality_code = wtf_facility_municipality_code;
      this.wtf_discharge_type = wtf_discharge_type;
      this.wtf_discharge_latitude = wtf_discharge_latitude;
      this.wtf_discharge_longitude = wtf_discharge_longitude;
      this.wtf_ad_id = wtf_ad_id;
      this.wtf_agg_id = wtf_agg_id;
      this.wtf_waiting_update_confirmation = wtf_waiting_update_confirmation;
      this.wtf_nc_ad_id = wtf_nc_ad_id;
      this.wtf_nc_agg_id = wtf_nc_agg_id;
      this.wtf_nc_manufacturer = wtf_nc_manufacturer;
      this.wtf_nc_manufacturer_description = wtf_nc_manufacturer_description;
      this.wtf_nc_model = wtf_nc_model;
      this.wtf_nc_type = wtf_nc_type;
      this.wtf_nc_capacity = wtf_nc_capacity;
      this.wtf_nc_distance = wtf_nc_distance;
      this.wtf_nc_technical_passport_id = wtf_nc_technical_passport_id;
      this.wtf_nc_data_source = wtf_nc_data_source;
      this.wtf_nc_installation_date = wtf_nc_installation_date;
      this.wtf_nc_checkout_date = wtf_nc_checkout_date;
      this.wtf_nc_deleted = wtf_nc_deleted;
      this.wtf_nc_facility_latitude = wtf_nc_facility_latitude;
      this.wtf_nc_facility_longitude = wtf_nc_facility_longitude;
      this.wtf_nc_facility_municipality_code = wtf_nc_facility_municipality_code;
      this.wtf_nc_discharge_type = wtf_nc_discharge_type;
      this.wtf_nc_discharge_latitude = wtf_nc_discharge_latitude;
      this.wtf_nc_discharge_longitude = wtf_nc_discharge_longitude;
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
      this.wtf_fam_id = wtf_fam_id;
      this.wtf_nc_fam_pop_equivalent = wtf_nc_fam_pop_equivalent;
      this.wtf_nc_fam_tech_pass = wtf_nc_fam_tech_pass;
      this.wtf_nc_fam_chds = wtf_nc_fam_chds;
      this.wtf_nc_fam_bds = wtf_nc_fam_bds;
      this.wtf_nc_fam_float_material = wtf_nc_fam_float_material;
      this.wtf_nc_fam_phosphor = wtf_nc_fam_phosphor;
      this.wtf_nc_fam_nitrogen = wtf_nc_fam_nitrogen;
      this.wtf_nc_fam_manufacturer = wtf_nc_fam_manufacturer;
      this.wtf_nc_fam_model = wtf_nc_fam_model;
      this.wtf_nc_fam_description = wtf_nc_fam_description;
   }
   public void copyValues(NtisWastewaterTreatmentFaciDAOGen obj) {
      this.setWtf_id(obj.getWtf_id());
      this.setWtf_manufacturer(obj.getWtf_manufacturer());
      this.setWtf_manufacturer_description(obj.getWtf_manufacturer_description());
      this.setWtf_model(obj.getWtf_model());
      this.setWtf_type(obj.getWtf_type());
      this.setWtf_capacity(obj.getWtf_capacity());
      this.setWtf_distance(obj.getWtf_distance());
      this.setWtf_technical_passport_id(obj.getWtf_technical_passport_id());
      this.setWtf_state(obj.getWtf_state());
      this.setWtf_data_source(obj.getWtf_data_source());
      this.setWtf_installation_date(obj.getWtf_installation_date());
      this.setWtf_checkout_date(obj.getWtf_checkout_date());
      this.setWtf_deleted(obj.getWtf_deleted());
      this.setWtf_facility_latitude(obj.getWtf_facility_latitude());
      this.setWtf_facility_longitude(obj.getWtf_facility_longitude());
      this.setWtf_facility_municipality_code(obj.getWtf_facility_municipality_code());
      this.setWtf_discharge_type(obj.getWtf_discharge_type());
      this.setWtf_discharge_latitude(obj.getWtf_discharge_latitude());
      this.setWtf_discharge_longitude(obj.getWtf_discharge_longitude());
      this.setWtf_ad_id(obj.getWtf_ad_id());
      this.setWtf_agg_id(obj.getWtf_agg_id());
      this.setWtf_waiting_update_confirmation(obj.getWtf_waiting_update_confirmation());
      this.setWtf_nc_ad_id(obj.getWtf_nc_ad_id());
      this.setWtf_nc_agg_id(obj.getWtf_nc_agg_id());
      this.setWtf_nc_manufacturer(obj.getWtf_nc_manufacturer());
      this.setWtf_nc_manufacturer_description(obj.getWtf_nc_manufacturer_description());
      this.setWtf_nc_model(obj.getWtf_nc_model());
      this.setWtf_nc_type(obj.getWtf_nc_type());
      this.setWtf_nc_capacity(obj.getWtf_nc_capacity());
      this.setWtf_nc_distance(obj.getWtf_nc_distance());
      this.setWtf_nc_technical_passport_id(obj.getWtf_nc_technical_passport_id());
      this.setWtf_nc_data_source(obj.getWtf_nc_data_source());
      this.setWtf_nc_installation_date(obj.getWtf_nc_installation_date());
      this.setWtf_nc_checkout_date(obj.getWtf_nc_checkout_date());
      this.setWtf_nc_deleted(obj.getWtf_nc_deleted());
      this.setWtf_nc_facility_latitude(obj.getWtf_nc_facility_latitude());
      this.setWtf_nc_facility_longitude(obj.getWtf_nc_facility_longitude());
      this.setWtf_nc_facility_municipality_code(obj.getWtf_nc_facility_municipality_code());
      this.setWtf_nc_discharge_type(obj.getWtf_nc_discharge_type());
      this.setWtf_nc_discharge_latitude(obj.getWtf_nc_discharge_latitude());
      this.setWtf_nc_discharge_longitude(obj.getWtf_nc_discharge_longitude());
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
      this.setWtf_fam_id(obj.getWtf_fam_id());
      this.setWtf_nc_fam_pop_equivalent(obj.getWtf_nc_fam_pop_equivalent());
      this.setWtf_nc_fam_tech_pass(obj.getWtf_nc_fam_tech_pass());
      this.setWtf_nc_fam_chds(obj.getWtf_nc_fam_chds());
      this.setWtf_nc_fam_bds(obj.getWtf_nc_fam_bds());
      this.setWtf_nc_fam_float_material(obj.getWtf_nc_fam_float_material());
      this.setWtf_nc_fam_phosphor(obj.getWtf_nc_fam_phosphor());
      this.setWtf_nc_fam_nitrogen(obj.getWtf_nc_fam_nitrogen());
      this.setWtf_nc_fam_manufacturer(obj.getWtf_nc_fam_manufacturer());
      this.setWtf_nc_fam_model(obj.getWtf_nc_fam_model());
      this.setWtf_nc_fam_description(obj.getWtf_nc_fam_description());
   }
   protected void markObjectAsInitial() {
      this.wtf_id_changed = false;
      this.wtf_manufacturer_changed = false;
      this.wtf_manufacturer_description_changed = false;
      this.wtf_model_changed = false;
      this.wtf_type_changed = false;
      this.wtf_capacity_changed = false;
      this.wtf_distance_changed = false;
      this.wtf_technical_passport_id_changed = false;
      this.wtf_state_changed = false;
      this.wtf_data_source_changed = false;
      this.wtf_installation_date_changed = false;
      this.wtf_checkout_date_changed = false;
      this.wtf_deleted_changed = false;
      this.wtf_facility_latitude_changed = false;
      this.wtf_facility_longitude_changed = false;
      this.wtf_facility_municipality_code_changed = false;
      this.wtf_discharge_type_changed = false;
      this.wtf_discharge_latitude_changed = false;
      this.wtf_discharge_longitude_changed = false;
      this.wtf_ad_id_changed = false;
      this.wtf_agg_id_changed = false;
      this.wtf_waiting_update_confirmation_changed = false;
      this.wtf_nc_ad_id_changed = false;
      this.wtf_nc_agg_id_changed = false;
      this.wtf_nc_manufacturer_changed = false;
      this.wtf_nc_manufacturer_description_changed = false;
      this.wtf_nc_model_changed = false;
      this.wtf_nc_type_changed = false;
      this.wtf_nc_capacity_changed = false;
      this.wtf_nc_distance_changed = false;
      this.wtf_nc_technical_passport_id_changed = false;
      this.wtf_nc_data_source_changed = false;
      this.wtf_nc_installation_date_changed = false;
      this.wtf_nc_checkout_date_changed = false;
      this.wtf_nc_deleted_changed = false;
      this.wtf_nc_facility_latitude_changed = false;
      this.wtf_nc_facility_longitude_changed = false;
      this.wtf_nc_facility_municipality_code_changed = false;
      this.wtf_nc_discharge_type_changed = false;
      this.wtf_nc_discharge_latitude_changed = false;
      this.wtf_nc_discharge_longitude_changed = false;
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
      this.wtf_fam_id_changed = false;
      this.wtf_nc_fam_pop_equivalent_changed = false;
      this.wtf_nc_fam_tech_pass_changed = false;
      this.wtf_nc_fam_chds_changed = false;
      this.wtf_nc_fam_bds_changed = false;
      this.wtf_nc_fam_float_material_changed = false;
      this.wtf_nc_fam_phosphor_changed = false;
      this.wtf_nc_fam_nitrogen_changed = false;
      this.wtf_nc_fam_manufacturer_changed = false;
      this.wtf_nc_fam_model_changed = false;
      this.wtf_nc_fam_description_changed = false;
      this.record_changed = false;
   }
   public void setWtf_id(Double wtf_id) {
      if (this.isForceUpdate() || 
               (this.wtf_id != null && !this.wtf_id.equals(wtf_id))  ||
               (wtf_id != null && !wtf_id.equals(this.wtf_id)) ){
         this.wtf_id_changed = true; 
         this.record_changed = true;
         this.wtf_id = wtf_id;
      }
   }
   public Double getWtf_id() {
      return this.wtf_id;
   }
   public void setWtf_manufacturer(String wtf_manufacturer) {
      if (this.isForceUpdate() || 
               (this.wtf_manufacturer != null && !this.wtf_manufacturer.equals(wtf_manufacturer))  ||
               (wtf_manufacturer != null && !wtf_manufacturer.equals(this.wtf_manufacturer)) ){
         this.wtf_manufacturer_changed = true; 
         this.record_changed = true;
         this.wtf_manufacturer = wtf_manufacturer;
      }
   }
   public String getWtf_manufacturer() {
      return this.wtf_manufacturer;
   }
   public void setWtf_manufacturer_description(String wtf_manufacturer_description) {
      if (this.isForceUpdate() || 
               (this.wtf_manufacturer_description != null && !this.wtf_manufacturer_description.equals(wtf_manufacturer_description))  ||
               (wtf_manufacturer_description != null && !wtf_manufacturer_description.equals(this.wtf_manufacturer_description)) ){
         this.wtf_manufacturer_description_changed = true; 
         this.record_changed = true;
         this.wtf_manufacturer_description = wtf_manufacturer_description;
      }
   }
   public String getWtf_manufacturer_description() {
      return this.wtf_manufacturer_description;
   }
   public void setWtf_model(String wtf_model) {
      if (this.isForceUpdate() || 
               (this.wtf_model != null && !this.wtf_model.equals(wtf_model))  ||
               (wtf_model != null && !wtf_model.equals(this.wtf_model)) ){
         this.wtf_model_changed = true; 
         this.record_changed = true;
         this.wtf_model = wtf_model;
      }
   }
   public String getWtf_model() {
      return this.wtf_model;
   }
   public void setWtf_type(String wtf_type) {
      if (this.isForceUpdate() || 
               (this.wtf_type != null && !this.wtf_type.equals(wtf_type))  ||
               (wtf_type != null && !wtf_type.equals(this.wtf_type)) ){
         this.wtf_type_changed = true; 
         this.record_changed = true;
         this.wtf_type = wtf_type;
      }
   }
   public String getWtf_type() {
      return this.wtf_type;
   }
   public void setWtf_capacity(String wtf_capacity) {
      if (this.isForceUpdate() || 
               (this.wtf_capacity != null && !this.wtf_capacity.equals(wtf_capacity))  ||
               (wtf_capacity != null && !wtf_capacity.equals(this.wtf_capacity)) ){
         this.wtf_capacity_changed = true; 
         this.record_changed = true;
         this.wtf_capacity = wtf_capacity;
      }
   }
   public String getWtf_capacity() {
      return this.wtf_capacity;
   }
   public void setWtf_distance(Double wtf_distance) {
      if (this.isForceUpdate() || 
               (this.wtf_distance != null && !this.wtf_distance.equals(wtf_distance))  ||
               (wtf_distance != null && !wtf_distance.equals(this.wtf_distance)) ){
         this.wtf_distance_changed = true; 
         this.record_changed = true;
         this.wtf_distance = wtf_distance;
      }
   }
   public Double getWtf_distance() {
      return this.wtf_distance;
   }
   public void setWtf_technical_passport_id(String wtf_technical_passport_id) {
      if (this.isForceUpdate() || 
               (this.wtf_technical_passport_id != null && !this.wtf_technical_passport_id.equals(wtf_technical_passport_id))  ||
               (wtf_technical_passport_id != null && !wtf_technical_passport_id.equals(this.wtf_technical_passport_id)) ){
         this.wtf_technical_passport_id_changed = true; 
         this.record_changed = true;
         this.wtf_technical_passport_id = wtf_technical_passport_id;
      }
   }
   public String getWtf_technical_passport_id() {
      return this.wtf_technical_passport_id;
   }
   public void setWtf_state(String wtf_state) {
      if (this.isForceUpdate() || 
               (this.wtf_state != null && !this.wtf_state.equals(wtf_state))  ||
               (wtf_state != null && !wtf_state.equals(this.wtf_state)) ){
         this.wtf_state_changed = true; 
         this.record_changed = true;
         this.wtf_state = wtf_state;
      }
   }
   public String getWtf_state() {
      return this.wtf_state;
   }
   public void setWtf_data_source(String wtf_data_source) {
      if (this.isForceUpdate() || 
               (this.wtf_data_source != null && !this.wtf_data_source.equals(wtf_data_source))  ||
               (wtf_data_source != null && !wtf_data_source.equals(this.wtf_data_source)) ){
         this.wtf_data_source_changed = true; 
         this.record_changed = true;
         this.wtf_data_source = wtf_data_source;
      }
   }
   public String getWtf_data_source() {
      return this.wtf_data_source;
   }
   public void setWtf_installation_date(Date wtf_installation_date) {
      if (this.isForceUpdate() || 
               (this.wtf_installation_date != null && (wtf_installation_date == null ||this.wtf_installation_date.compareTo(wtf_installation_date) != 0 )) ||
               (wtf_installation_date != null && (this.wtf_installation_date == null ||wtf_installation_date.compareTo(this.wtf_installation_date) != 0 ))){
         this.wtf_installation_date_changed = true; 
         this.record_changed = true;
         this.wtf_installation_date = wtf_installation_date;
      }
   }
   public Date getWtf_installation_date() {
      return this.wtf_installation_date;
   }
   public void setWtf_checkout_date(Date wtf_checkout_date) {
      if (this.isForceUpdate() || 
               (this.wtf_checkout_date != null && (wtf_checkout_date == null ||this.wtf_checkout_date.compareTo(wtf_checkout_date) != 0 )) ||
               (wtf_checkout_date != null && (this.wtf_checkout_date == null ||wtf_checkout_date.compareTo(this.wtf_checkout_date) != 0 ))){
         this.wtf_checkout_date_changed = true; 
         this.record_changed = true;
         this.wtf_checkout_date = wtf_checkout_date;
      }
   }
   public Date getWtf_checkout_date() {
      return this.wtf_checkout_date;
   }
   public void setWtf_deleted(String wtf_deleted) {
      if (this.isForceUpdate() || 
               (this.wtf_deleted != null && !this.wtf_deleted.equals(wtf_deleted))  ||
               (wtf_deleted != null && !wtf_deleted.equals(this.wtf_deleted)) ){
         this.wtf_deleted_changed = true; 
         this.record_changed = true;
         this.wtf_deleted = wtf_deleted;
      }
   }
   public String getWtf_deleted() {
      return this.wtf_deleted;
   }
   public void setWtf_facility_latitude(Double wtf_facility_latitude) {
      if (this.isForceUpdate() || 
               (this.wtf_facility_latitude != null && !this.wtf_facility_latitude.equals(wtf_facility_latitude))  ||
               (wtf_facility_latitude != null && !wtf_facility_latitude.equals(this.wtf_facility_latitude)) ){
         this.wtf_facility_latitude_changed = true; 
         this.record_changed = true;
         this.wtf_facility_latitude = wtf_facility_latitude;
      }
   }
   public Double getWtf_facility_latitude() {
      return this.wtf_facility_latitude;
   }
   public void setWtf_facility_longitude(Double wtf_facility_longitude) {
      if (this.isForceUpdate() || 
               (this.wtf_facility_longitude != null && !this.wtf_facility_longitude.equals(wtf_facility_longitude))  ||
               (wtf_facility_longitude != null && !wtf_facility_longitude.equals(this.wtf_facility_longitude)) ){
         this.wtf_facility_longitude_changed = true; 
         this.record_changed = true;
         this.wtf_facility_longitude = wtf_facility_longitude;
      }
   }
   public Double getWtf_facility_longitude() {
      return this.wtf_facility_longitude;
   }
   public void setWtf_facility_municipality_code(String wtf_facility_municipality_code) {
      if (this.isForceUpdate() || 
               (this.wtf_facility_municipality_code != null && !this.wtf_facility_municipality_code.equals(wtf_facility_municipality_code))  ||
               (wtf_facility_municipality_code != null && !wtf_facility_municipality_code.equals(this.wtf_facility_municipality_code)) ){
         this.wtf_facility_municipality_code_changed = true; 
         this.record_changed = true;
         this.wtf_facility_municipality_code = wtf_facility_municipality_code;
      }
   }
   public String getWtf_facility_municipality_code() {
      return this.wtf_facility_municipality_code;
   }
   public void setWtf_discharge_type(String wtf_discharge_type) {
      if (this.isForceUpdate() || 
               (this.wtf_discharge_type != null && !this.wtf_discharge_type.equals(wtf_discharge_type))  ||
               (wtf_discharge_type != null && !wtf_discharge_type.equals(this.wtf_discharge_type)) ){
         this.wtf_discharge_type_changed = true; 
         this.record_changed = true;
         this.wtf_discharge_type = wtf_discharge_type;
      }
   }
   public String getWtf_discharge_type() {
      return this.wtf_discharge_type;
   }
   public void setWtf_discharge_latitude(Double wtf_discharge_latitude) {
      if (this.isForceUpdate() || 
               (this.wtf_discharge_latitude != null && !this.wtf_discharge_latitude.equals(wtf_discharge_latitude))  ||
               (wtf_discharge_latitude != null && !wtf_discharge_latitude.equals(this.wtf_discharge_latitude)) ){
         this.wtf_discharge_latitude_changed = true; 
         this.record_changed = true;
         this.wtf_discharge_latitude = wtf_discharge_latitude;
      }
   }
   public Double getWtf_discharge_latitude() {
      return this.wtf_discharge_latitude;
   }
   public void setWtf_discharge_longitude(Double wtf_discharge_longitude) {
      if (this.isForceUpdate() || 
               (this.wtf_discharge_longitude != null && !this.wtf_discharge_longitude.equals(wtf_discharge_longitude))  ||
               (wtf_discharge_longitude != null && !wtf_discharge_longitude.equals(this.wtf_discharge_longitude)) ){
         this.wtf_discharge_longitude_changed = true; 
         this.record_changed = true;
         this.wtf_discharge_longitude = wtf_discharge_longitude;
      }
   }
   public Double getWtf_discharge_longitude() {
      return this.wtf_discharge_longitude;
   }
   public void setWtf_ad_id(Double wtf_ad_id) {
      if (this.isForceUpdate() || 
               (this.wtf_ad_id != null && !this.wtf_ad_id.equals(wtf_ad_id))  ||
               (wtf_ad_id != null && !wtf_ad_id.equals(this.wtf_ad_id)) ){
         this.wtf_ad_id_changed = true; 
         this.record_changed = true;
         this.wtf_ad_id = wtf_ad_id;
      }
   }
   public Double getWtf_ad_id() {
      return this.wtf_ad_id;
   }
   public void setWtf_agg_id(Double wtf_agg_id) {
      if (this.isForceUpdate() || 
               (this.wtf_agg_id != null && !this.wtf_agg_id.equals(wtf_agg_id))  ||
               (wtf_agg_id != null && !wtf_agg_id.equals(this.wtf_agg_id)) ){
         this.wtf_agg_id_changed = true; 
         this.record_changed = true;
         this.wtf_agg_id = wtf_agg_id;
      }
   }
   public Double getWtf_agg_id() {
      return this.wtf_agg_id;
   }
   public void setWtf_waiting_update_confirmation(String wtf_waiting_update_confirmation) {
      if (this.isForceUpdate() || 
               (this.wtf_waiting_update_confirmation != null && !this.wtf_waiting_update_confirmation.equals(wtf_waiting_update_confirmation))  ||
               (wtf_waiting_update_confirmation != null && !wtf_waiting_update_confirmation.equals(this.wtf_waiting_update_confirmation)) ){
         this.wtf_waiting_update_confirmation_changed = true; 
         this.record_changed = true;
         this.wtf_waiting_update_confirmation = wtf_waiting_update_confirmation;
      }
   }
   public String getWtf_waiting_update_confirmation() {
      return this.wtf_waiting_update_confirmation;
   }
   public void setWtf_nc_ad_id(Double wtf_nc_ad_id) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_ad_id != null && !this.wtf_nc_ad_id.equals(wtf_nc_ad_id))  ||
               (wtf_nc_ad_id != null && !wtf_nc_ad_id.equals(this.wtf_nc_ad_id)) ){
         this.wtf_nc_ad_id_changed = true; 
         this.record_changed = true;
         this.wtf_nc_ad_id = wtf_nc_ad_id;
      }
   }
   public Double getWtf_nc_ad_id() {
      return this.wtf_nc_ad_id;
   }
   public void setWtf_nc_agg_id(Double wtf_nc_agg_id) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_agg_id != null && !this.wtf_nc_agg_id.equals(wtf_nc_agg_id))  ||
               (wtf_nc_agg_id != null && !wtf_nc_agg_id.equals(this.wtf_nc_agg_id)) ){
         this.wtf_nc_agg_id_changed = true; 
         this.record_changed = true;
         this.wtf_nc_agg_id = wtf_nc_agg_id;
      }
   }
   public Double getWtf_nc_agg_id() {
      return this.wtf_nc_agg_id;
   }
   public void setWtf_nc_manufacturer(String wtf_nc_manufacturer) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_manufacturer != null && !this.wtf_nc_manufacturer.equals(wtf_nc_manufacturer))  ||
               (wtf_nc_manufacturer != null && !wtf_nc_manufacturer.equals(this.wtf_nc_manufacturer)) ){
         this.wtf_nc_manufacturer_changed = true; 
         this.record_changed = true;
         this.wtf_nc_manufacturer = wtf_nc_manufacturer;
      }
   }
   public String getWtf_nc_manufacturer() {
      return this.wtf_nc_manufacturer;
   }
   public void setWtf_nc_manufacturer_description(String wtf_nc_manufacturer_description) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_manufacturer_description != null && !this.wtf_nc_manufacturer_description.equals(wtf_nc_manufacturer_description))  ||
               (wtf_nc_manufacturer_description != null && !wtf_nc_manufacturer_description.equals(this.wtf_nc_manufacturer_description)) ){
         this.wtf_nc_manufacturer_description_changed = true; 
         this.record_changed = true;
         this.wtf_nc_manufacturer_description = wtf_nc_manufacturer_description;
      }
   }
   public String getWtf_nc_manufacturer_description() {
      return this.wtf_nc_manufacturer_description;
   }
   public void setWtf_nc_model(String wtf_nc_model) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_model != null && !this.wtf_nc_model.equals(wtf_nc_model))  ||
               (wtf_nc_model != null && !wtf_nc_model.equals(this.wtf_nc_model)) ){
         this.wtf_nc_model_changed = true; 
         this.record_changed = true;
         this.wtf_nc_model = wtf_nc_model;
      }
   }
   public String getWtf_nc_model() {
      return this.wtf_nc_model;
   }
   public void setWtf_nc_type(String wtf_nc_type) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_type != null && !this.wtf_nc_type.equals(wtf_nc_type))  ||
               (wtf_nc_type != null && !wtf_nc_type.equals(this.wtf_nc_type)) ){
         this.wtf_nc_type_changed = true; 
         this.record_changed = true;
         this.wtf_nc_type = wtf_nc_type;
      }
   }
   public String getWtf_nc_type() {
      return this.wtf_nc_type;
   }
   public void setWtf_nc_capacity(String wtf_nc_capacity) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_capacity != null && !this.wtf_nc_capacity.equals(wtf_nc_capacity))  ||
               (wtf_nc_capacity != null && !wtf_nc_capacity.equals(this.wtf_nc_capacity)) ){
         this.wtf_nc_capacity_changed = true; 
         this.record_changed = true;
         this.wtf_nc_capacity = wtf_nc_capacity;
      }
   }
   public String getWtf_nc_capacity() {
      return this.wtf_nc_capacity;
   }
   public void setWtf_nc_distance(Double wtf_nc_distance) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_distance != null && !this.wtf_nc_distance.equals(wtf_nc_distance))  ||
               (wtf_nc_distance != null && !wtf_nc_distance.equals(this.wtf_nc_distance)) ){
         this.wtf_nc_distance_changed = true; 
         this.record_changed = true;
         this.wtf_nc_distance = wtf_nc_distance;
      }
   }
   public Double getWtf_nc_distance() {
      return this.wtf_nc_distance;
   }
   public void setWtf_nc_technical_passport_id(String wtf_nc_technical_passport_id) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_technical_passport_id != null && !this.wtf_nc_technical_passport_id.equals(wtf_nc_technical_passport_id))  ||
               (wtf_nc_technical_passport_id != null && !wtf_nc_technical_passport_id.equals(this.wtf_nc_technical_passport_id)) ){
         this.wtf_nc_technical_passport_id_changed = true; 
         this.record_changed = true;
         this.wtf_nc_technical_passport_id = wtf_nc_technical_passport_id;
      }
   }
   public String getWtf_nc_technical_passport_id() {
      return this.wtf_nc_technical_passport_id;
   }
   public void setWtf_nc_data_source(String wtf_nc_data_source) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_data_source != null && !this.wtf_nc_data_source.equals(wtf_nc_data_source))  ||
               (wtf_nc_data_source != null && !wtf_nc_data_source.equals(this.wtf_nc_data_source)) ){
         this.wtf_nc_data_source_changed = true; 
         this.record_changed = true;
         this.wtf_nc_data_source = wtf_nc_data_source;
      }
   }
   public String getWtf_nc_data_source() {
      return this.wtf_nc_data_source;
   }
   public void setWtf_nc_installation_date(Date wtf_nc_installation_date) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_installation_date != null && (wtf_nc_installation_date == null ||this.wtf_nc_installation_date.compareTo(wtf_nc_installation_date) != 0 )) ||
               (wtf_nc_installation_date != null && (this.wtf_nc_installation_date == null ||wtf_nc_installation_date.compareTo(this.wtf_nc_installation_date) != 0 ))){
         this.wtf_nc_installation_date_changed = true; 
         this.record_changed = true;
         this.wtf_nc_installation_date = wtf_nc_installation_date;
      }
   }
   public Date getWtf_nc_installation_date() {
      return this.wtf_nc_installation_date;
   }
   public void setWtf_nc_checkout_date(Date wtf_nc_checkout_date) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_checkout_date != null && (wtf_nc_checkout_date == null ||this.wtf_nc_checkout_date.compareTo(wtf_nc_checkout_date) != 0 )) ||
               (wtf_nc_checkout_date != null && (this.wtf_nc_checkout_date == null ||wtf_nc_checkout_date.compareTo(this.wtf_nc_checkout_date) != 0 ))){
         this.wtf_nc_checkout_date_changed = true; 
         this.record_changed = true;
         this.wtf_nc_checkout_date = wtf_nc_checkout_date;
      }
   }
   public Date getWtf_nc_checkout_date() {
      return this.wtf_nc_checkout_date;
   }
   public void setWtf_nc_deleted(String wtf_nc_deleted) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_deleted != null && !this.wtf_nc_deleted.equals(wtf_nc_deleted))  ||
               (wtf_nc_deleted != null && !wtf_nc_deleted.equals(this.wtf_nc_deleted)) ){
         this.wtf_nc_deleted_changed = true; 
         this.record_changed = true;
         this.wtf_nc_deleted = wtf_nc_deleted;
      }
   }
   public String getWtf_nc_deleted() {
      return this.wtf_nc_deleted;
   }
   public void setWtf_nc_facility_latitude(Double wtf_nc_facility_latitude) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_facility_latitude != null && !this.wtf_nc_facility_latitude.equals(wtf_nc_facility_latitude))  ||
               (wtf_nc_facility_latitude != null && !wtf_nc_facility_latitude.equals(this.wtf_nc_facility_latitude)) ){
         this.wtf_nc_facility_latitude_changed = true; 
         this.record_changed = true;
         this.wtf_nc_facility_latitude = wtf_nc_facility_latitude;
      }
   }
   public Double getWtf_nc_facility_latitude() {
      return this.wtf_nc_facility_latitude;
   }
   public void setWtf_nc_facility_longitude(Double wtf_nc_facility_longitude) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_facility_longitude != null && !this.wtf_nc_facility_longitude.equals(wtf_nc_facility_longitude))  ||
               (wtf_nc_facility_longitude != null && !wtf_nc_facility_longitude.equals(this.wtf_nc_facility_longitude)) ){
         this.wtf_nc_facility_longitude_changed = true; 
         this.record_changed = true;
         this.wtf_nc_facility_longitude = wtf_nc_facility_longitude;
      }
   }
   public Double getWtf_nc_facility_longitude() {
      return this.wtf_nc_facility_longitude;
   }
   public void setWtf_nc_facility_municipality_code(String wtf_nc_facility_municipality_code) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_facility_municipality_code != null && !this.wtf_nc_facility_municipality_code.equals(wtf_nc_facility_municipality_code))  ||
               (wtf_nc_facility_municipality_code != null && !wtf_nc_facility_municipality_code.equals(this.wtf_nc_facility_municipality_code)) ){
         this.wtf_nc_facility_municipality_code_changed = true; 
         this.record_changed = true;
         this.wtf_nc_facility_municipality_code = wtf_nc_facility_municipality_code;
      }
   }
   public String getWtf_nc_facility_municipality_code() {
      return this.wtf_nc_facility_municipality_code;
   }
   public void setWtf_nc_discharge_type(String wtf_nc_discharge_type) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_discharge_type != null && !this.wtf_nc_discharge_type.equals(wtf_nc_discharge_type))  ||
               (wtf_nc_discharge_type != null && !wtf_nc_discharge_type.equals(this.wtf_nc_discharge_type)) ){
         this.wtf_nc_discharge_type_changed = true; 
         this.record_changed = true;
         this.wtf_nc_discharge_type = wtf_nc_discharge_type;
      }
   }
   public String getWtf_nc_discharge_type() {
      return this.wtf_nc_discharge_type;
   }
   public void setWtf_nc_discharge_latitude(Double wtf_nc_discharge_latitude) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_discharge_latitude != null && !this.wtf_nc_discharge_latitude.equals(wtf_nc_discharge_latitude))  ||
               (wtf_nc_discharge_latitude != null && !wtf_nc_discharge_latitude.equals(this.wtf_nc_discharge_latitude)) ){
         this.wtf_nc_discharge_latitude_changed = true; 
         this.record_changed = true;
         this.wtf_nc_discharge_latitude = wtf_nc_discharge_latitude;
      }
   }
   public Double getWtf_nc_discharge_latitude() {
      return this.wtf_nc_discharge_latitude;
   }
   public void setWtf_nc_discharge_longitude(Double wtf_nc_discharge_longitude) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_discharge_longitude != null && !this.wtf_nc_discharge_longitude.equals(wtf_nc_discharge_longitude))  ||
               (wtf_nc_discharge_longitude != null && !wtf_nc_discharge_longitude.equals(this.wtf_nc_discharge_longitude)) ){
         this.wtf_nc_discharge_longitude_changed = true; 
         this.record_changed = true;
         this.wtf_nc_discharge_longitude = wtf_nc_discharge_longitude;
      }
   }
   public Double getWtf_nc_discharge_longitude() {
      return this.wtf_nc_discharge_longitude;
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
   public void setWtf_fam_id(Double wtf_fam_id) {
      if (this.isForceUpdate() || 
               (this.wtf_fam_id != null && !this.wtf_fam_id.equals(wtf_fam_id))  ||
               (wtf_fam_id != null && !wtf_fam_id.equals(this.wtf_fam_id)) ){
         this.wtf_fam_id_changed = true; 
         this.record_changed = true;
         this.wtf_fam_id = wtf_fam_id;
      }
   }
   public Double getWtf_fam_id() {
      return this.wtf_fam_id;
   }
   public void setWtf_nc_fam_pop_equivalent(Double wtf_nc_fam_pop_equivalent) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_pop_equivalent != null && !this.wtf_nc_fam_pop_equivalent.equals(wtf_nc_fam_pop_equivalent))  ||
               (wtf_nc_fam_pop_equivalent != null && !wtf_nc_fam_pop_equivalent.equals(this.wtf_nc_fam_pop_equivalent)) ){
         this.wtf_nc_fam_pop_equivalent_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_pop_equivalent = wtf_nc_fam_pop_equivalent;
      }
   }
   public Double getWtf_nc_fam_pop_equivalent() {
      return this.wtf_nc_fam_pop_equivalent;
   }
   public void setWtf_nc_fam_tech_pass(String wtf_nc_fam_tech_pass) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_tech_pass != null && !this.wtf_nc_fam_tech_pass.equals(wtf_nc_fam_tech_pass))  ||
               (wtf_nc_fam_tech_pass != null && !wtf_nc_fam_tech_pass.equals(this.wtf_nc_fam_tech_pass)) ){
         this.wtf_nc_fam_tech_pass_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_tech_pass = wtf_nc_fam_tech_pass;
      }
   }
   public String getWtf_nc_fam_tech_pass() {
      return this.wtf_nc_fam_tech_pass;
   }
   public void setWtf_nc_fam_chds(Double wtf_nc_fam_chds) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_chds != null && !this.wtf_nc_fam_chds.equals(wtf_nc_fam_chds))  ||
               (wtf_nc_fam_chds != null && !wtf_nc_fam_chds.equals(this.wtf_nc_fam_chds)) ){
         this.wtf_nc_fam_chds_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_chds = wtf_nc_fam_chds;
      }
   }
   public Double getWtf_nc_fam_chds() {
      return this.wtf_nc_fam_chds;
   }
   public void setWtf_nc_fam_bds(Double wtf_nc_fam_bds) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_bds != null && !this.wtf_nc_fam_bds.equals(wtf_nc_fam_bds))  ||
               (wtf_nc_fam_bds != null && !wtf_nc_fam_bds.equals(this.wtf_nc_fam_bds)) ){
         this.wtf_nc_fam_bds_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_bds = wtf_nc_fam_bds;
      }
   }
   public Double getWtf_nc_fam_bds() {
      return this.wtf_nc_fam_bds;
   }
   public void setWtf_nc_fam_float_material(Double wtf_nc_fam_float_material) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_float_material != null && !this.wtf_nc_fam_float_material.equals(wtf_nc_fam_float_material))  ||
               (wtf_nc_fam_float_material != null && !wtf_nc_fam_float_material.equals(this.wtf_nc_fam_float_material)) ){
         this.wtf_nc_fam_float_material_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_float_material = wtf_nc_fam_float_material;
      }
   }
   public Double getWtf_nc_fam_float_material() {
      return this.wtf_nc_fam_float_material;
   }
   public void setWtf_nc_fam_phosphor(Double wtf_nc_fam_phosphor) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_phosphor != null && !this.wtf_nc_fam_phosphor.equals(wtf_nc_fam_phosphor))  ||
               (wtf_nc_fam_phosphor != null && !wtf_nc_fam_phosphor.equals(this.wtf_nc_fam_phosphor)) ){
         this.wtf_nc_fam_phosphor_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_phosphor = wtf_nc_fam_phosphor;
      }
   }
   public Double getWtf_nc_fam_phosphor() {
      return this.wtf_nc_fam_phosphor;
   }
   public void setWtf_nc_fam_nitrogen(Double wtf_nc_fam_nitrogen) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_nitrogen != null && !this.wtf_nc_fam_nitrogen.equals(wtf_nc_fam_nitrogen))  ||
               (wtf_nc_fam_nitrogen != null && !wtf_nc_fam_nitrogen.equals(this.wtf_nc_fam_nitrogen)) ){
         this.wtf_nc_fam_nitrogen_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_nitrogen = wtf_nc_fam_nitrogen;
      }
   }
   public Double getWtf_nc_fam_nitrogen() {
      return this.wtf_nc_fam_nitrogen;
   }
   public void setWtf_nc_fam_manufacturer(String wtf_nc_fam_manufacturer) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_manufacturer != null && !this.wtf_nc_fam_manufacturer.equals(wtf_nc_fam_manufacturer))  ||
               (wtf_nc_fam_manufacturer != null && !wtf_nc_fam_manufacturer.equals(this.wtf_nc_fam_manufacturer)) ){
         this.wtf_nc_fam_manufacturer_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_manufacturer = wtf_nc_fam_manufacturer;
      }
   }
   public String getWtf_nc_fam_manufacturer() {
      return this.wtf_nc_fam_manufacturer;
   }
   public void setWtf_nc_fam_model(String wtf_nc_fam_model) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_model != null && !this.wtf_nc_fam_model.equals(wtf_nc_fam_model))  ||
               (wtf_nc_fam_model != null && !wtf_nc_fam_model.equals(this.wtf_nc_fam_model)) ){
         this.wtf_nc_fam_model_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_model = wtf_nc_fam_model;
      }
   }
   public String getWtf_nc_fam_model() {
      return this.wtf_nc_fam_model;
   }
   public void setWtf_nc_fam_description(String wtf_nc_fam_description) {
      if (this.isForceUpdate() || 
               (this.wtf_nc_fam_description != null && !this.wtf_nc_fam_description.equals(wtf_nc_fam_description))  ||
               (wtf_nc_fam_description != null && !wtf_nc_fam_description.equals(this.wtf_nc_fam_description)) ){
         this.wtf_nc_fam_description_changed = true; 
         this.record_changed = true;
         this.wtf_nc_fam_description = wtf_nc_fam_description;
      }
   }
   public String getWtf_nc_fam_description() {
      return this.wtf_nc_fam_description;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.wtf_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.wtf_id;
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
      if (wtf_manufacturer_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_manufacturer!= null && wtf_manufacturer.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_MANUFACTURER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_manufacturer_description_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_manufacturer_description!= null && wtf_manufacturer_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_MANUFACTURER_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (wtf_model_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_model!= null && wtf_model.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_MODEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_type_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_type== null || EMPTY_STRING.equals(wtf_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wtf_type!= null && wtf_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (wtf_capacity_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_capacity!= null && wtf_capacity.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_CAPACITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_distance_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_distance!= null && (""+wtf_distance.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_DISTANCE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_technical_passport_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_technical_passport_id!= null && wtf_technical_passport_id.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_TECHNICAL_PASSPORT_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_state_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_state== null || EMPTY_STRING.equals(wtf_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wtf_state!= null && wtf_state.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (wtf_data_source_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_data_source!= null && wtf_data_source.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_DATA_SOURCE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (wtf_deleted_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_deleted!= null && wtf_deleted.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_DELETED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (wtf_facility_latitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_facility_latitude!= null && (""+wtf_facility_latitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_FACILITY_LATITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_facility_longitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_facility_longitude!= null && (""+wtf_facility_longitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_FACILITY_LONGITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_facility_municipality_code_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_facility_municipality_code!= null && wtf_facility_municipality_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_FACILITY_MUNICIPALITY_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_discharge_type_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_discharge_type!= null && wtf_discharge_type.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_DISCHARGE_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_discharge_latitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_discharge_latitude!= null && (""+wtf_discharge_latitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_DISCHARGE_LATITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_discharge_longitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_discharge_longitude!= null && (""+wtf_discharge_longitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_DISCHARGE_LONGITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_ad_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_ad_id!= null && (""+wtf_ad_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_AD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_agg_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_agg_id!= null && (""+wtf_agg_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_AGG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_waiting_update_confirmation_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_waiting_update_confirmation!= null && wtf_waiting_update_confirmation.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_WAITING_UPDATE_CONFIRMATION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (wtf_nc_ad_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_ad_id!= null && (""+wtf_nc_ad_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_AD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_agg_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_agg_id!= null && (""+wtf_nc_agg_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_AGG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_manufacturer_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_manufacturer!= null && wtf_nc_manufacturer.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_MANUFACTURER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_manufacturer_description_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_manufacturer_description!= null && wtf_nc_manufacturer_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_MANUFACTURER_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (wtf_nc_model_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_model!= null && wtf_nc_model.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_MODEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_type_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_type!= null && wtf_nc_type.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_capacity_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_capacity!= null && wtf_nc_capacity.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_CAPACITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_distance_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_distance!= null && (""+wtf_nc_distance.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_DISTANCE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_nc_technical_passport_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_technical_passport_id!= null && wtf_nc_technical_passport_id.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_TECHNICAL_PASSPORT_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_data_source_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_data_source!= null && wtf_nc_data_source.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_DATA_SOURCE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (wtf_nc_deleted_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_deleted!= null && wtf_nc_deleted.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_DELETED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (wtf_nc_facility_latitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_facility_latitude!= null && (""+wtf_nc_facility_latitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FACILITY_LATITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_nc_facility_longitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_facility_longitude!= null && (""+wtf_nc_facility_longitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FACILITY_LONGITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_nc_facility_municipality_code_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_facility_municipality_code!= null && wtf_nc_facility_municipality_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FACILITY_MUNICIPALITY_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_discharge_type_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_discharge_type!= null && wtf_nc_discharge_type.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_DISCHARGE_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_discharge_latitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_discharge_latitude!= null && (""+wtf_nc_discharge_latitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_DISCHARGE_LATITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wtf_nc_discharge_longitude_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_discharge_longitude!= null && (""+wtf_nc_discharge_longitude.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_DISCHARGE_LONGITUDE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (wtf_fam_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_fam_id!= null && (""+wtf_fam_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_FAM_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_pop_equivalent_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_pop_equivalent!= null && (""+wtf_nc_fam_pop_equivalent.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_POP_EQUIVALENT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_tech_pass_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_tech_pass!= null && wtf_nc_fam_tech_pass.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_TECH_PASS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (wtf_nc_fam_chds_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_chds!= null && (""+wtf_nc_fam_chds.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_CHDS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_bds_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_bds!= null && (""+wtf_nc_fam_bds.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_BDS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_float_material_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_float_material!= null && (""+wtf_nc_fam_float_material.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_FLOAT_MATERIAL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_phosphor_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_phosphor!= null && (""+wtf_nc_fam_phosphor.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_PHOSPHOR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_nitrogen_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_nitrogen!= null && (""+wtf_nc_fam_nitrogen.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_NITROGEN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wtf_nc_fam_manufacturer_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_manufacturer!= null && wtf_nc_fam_manufacturer.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_MANUFACTURER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_fam_model_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_model!= null && wtf_nc_fam_model.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_MODEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (wtf_nc_fam_description_changed || operation == Utils.OPERATION_INSERT) {
         if (wtf_nc_fam_description!= null && wtf_nc_fam_description.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_FACI", "WTF_NC_FAM_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_WASTEWATER_TREATMENT_FACI SET ";
      boolean changedExists = false;      if (wtf_manufacturer_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_MANUFACTURER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_manufacturer);
      }
      if (wtf_manufacturer_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_MANUFACTURER_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_manufacturer_description);
      }
      if (wtf_model_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_MODEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_model);
      }
      if (wtf_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_type);
      }
      if (wtf_capacity_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_CAPACITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_capacity);
      }
      if (wtf_distance_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_DISTANCE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_distance);
      }
      if (wtf_technical_passport_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_TECHNICAL_PASSPORT_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_technical_passport_id);
      }
      if (wtf_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_state);
      }
      if (wtf_data_source_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_DATA_SOURCE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_data_source);
      }
      if (wtf_installation_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_INSTALLATION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(wtf_installation_date);
      }
      if (wtf_checkout_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_CHECKOUT_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(wtf_checkout_date);
      }
      if (wtf_deleted_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_DELETED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_deleted);
      }
      if (wtf_facility_latitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_FACILITY_LATITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_facility_latitude);
      }
      if (wtf_facility_longitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_FACILITY_LONGITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_facility_longitude);
      }
      if (wtf_facility_municipality_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_FACILITY_MUNICIPALITY_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_facility_municipality_code);
      }
      if (wtf_discharge_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_DISCHARGE_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_discharge_type);
      }
      if (wtf_discharge_latitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_DISCHARGE_LATITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_discharge_latitude);
      }
      if (wtf_discharge_longitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_DISCHARGE_LONGITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_discharge_longitude);
      }
      if (wtf_ad_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_AD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_ad_id);
      }
      if (wtf_agg_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_AGG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_agg_id);
      }
      if (wtf_waiting_update_confirmation_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_WAITING_UPDATE_CONFIRMATION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_waiting_update_confirmation);
      }
      if (wtf_nc_ad_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_AD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_ad_id);
      }
      if (wtf_nc_agg_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_AGG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_agg_id);
      }
      if (wtf_nc_manufacturer_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_MANUFACTURER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_manufacturer);
      }
      if (wtf_nc_manufacturer_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_MANUFACTURER_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_manufacturer_description);
      }
      if (wtf_nc_model_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_MODEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_model);
      }
      if (wtf_nc_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_type);
      }
      if (wtf_nc_capacity_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_CAPACITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_capacity);
      }
      if (wtf_nc_distance_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_DISTANCE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_distance);
      }
      if (wtf_nc_technical_passport_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_TECHNICAL_PASSPORT_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_technical_passport_id);
      }
      if (wtf_nc_data_source_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_DATA_SOURCE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_data_source);
      }
      if (wtf_nc_installation_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_INSTALLATION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(wtf_nc_installation_date);
      }
      if (wtf_nc_checkout_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_CHECKOUT_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(wtf_nc_checkout_date);
      }
      if (wtf_nc_deleted_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_DELETED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_deleted);
      }
      if (wtf_nc_facility_latitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FACILITY_LATITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_facility_latitude);
      }
      if (wtf_nc_facility_longitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FACILITY_LONGITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_facility_longitude);
      }
      if (wtf_nc_facility_municipality_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FACILITY_MUNICIPALITY_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_facility_municipality_code);
      }
      if (wtf_nc_discharge_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_DISCHARGE_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_discharge_type);
      }
      if (wtf_nc_discharge_latitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_DISCHARGE_LATITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_discharge_latitude);
      }
      if (wtf_nc_discharge_longitude_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_DISCHARGE_LONGITUDE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_discharge_longitude);
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
      if (wtf_fam_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_FAM_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_fam_id);
      }
      if (wtf_nc_fam_pop_equivalent_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_POP_EQUIVALENT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_pop_equivalent);
      }
      if (wtf_nc_fam_tech_pass_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_TECH_PASS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_tech_pass);
      }
      if (wtf_nc_fam_chds_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_CHDS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_chds);
      }
      if (wtf_nc_fam_bds_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_BDS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_bds);
      }
      if (wtf_nc_fam_float_material_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_FLOAT_MATERIAL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_float_material);
      }
      if (wtf_nc_fam_phosphor_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_PHOSPHOR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_phosphor);
      }
      if (wtf_nc_fam_nitrogen_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_NITROGEN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_nitrogen);
      }
      if (wtf_nc_fam_manufacturer_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_MANUFACTURER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_manufacturer);
      }
      if (wtf_nc_fam_model_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_MODEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_model);
      }
      if (wtf_nc_fam_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTF_NC_FAM_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wtf_nc_fam_description);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  WTF_ID = ? ";
      updateStatementPart.addStatementParam(wtf_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisWastewaterTreatmentFaciDAO\":{\"wtf_id\": \""+getWtf_id()+"\", \"wtf_manufacturer\": \""+getWtf_manufacturer()+"\", \"wtf_manufacturer_description\": \""+getWtf_manufacturer_description()+"\", \"wtf_model\": \""+getWtf_model()+"\", \"wtf_type\": \""+getWtf_type()+"\", \"wtf_capacity\": \""+getWtf_capacity()+"\", \"wtf_distance\": \""+getWtf_distance()+"\", \"wtf_technical_passport_id\": \""+getWtf_technical_passport_id()+"\", \"wtf_state\": \""+getWtf_state()+"\", \"wtf_data_source\": \""+getWtf_data_source()+"\", \"wtf_installation_date\": \""+getWtf_installation_date()+"\", \"wtf_checkout_date\": \""+getWtf_checkout_date()+"\", \"wtf_deleted\": \""+getWtf_deleted()+"\", \"wtf_facility_latitude\": \""+getWtf_facility_latitude()+"\", \"wtf_facility_longitude\": \""+getWtf_facility_longitude()+"\", \"wtf_facility_municipality_code\": \""+getWtf_facility_municipality_code()+"\", \"wtf_discharge_type\": \""+getWtf_discharge_type()+"\", \"wtf_discharge_latitude\": \""+getWtf_discharge_latitude()+"\", \"wtf_discharge_longitude\": \""+getWtf_discharge_longitude()+"\", \"wtf_ad_id\": \""+getWtf_ad_id()+"\", \"wtf_agg_id\": \""+getWtf_agg_id()+"\", \"wtf_waiting_update_confirmation\": \""+getWtf_waiting_update_confirmation()+"\", \"wtf_nc_ad_id\": \""+getWtf_nc_ad_id()+"\", \"wtf_nc_agg_id\": \""+getWtf_nc_agg_id()+"\", \"wtf_nc_manufacturer\": \""+getWtf_nc_manufacturer()+"\", \"wtf_nc_manufacturer_description\": \""+getWtf_nc_manufacturer_description()+"\", \"wtf_nc_model\": \""+getWtf_nc_model()+"\", \"wtf_nc_type\": \""+getWtf_nc_type()+"\", \"wtf_nc_capacity\": \""+getWtf_nc_capacity()+"\", \"wtf_nc_distance\": \""+getWtf_nc_distance()+"\", \"wtf_nc_technical_passport_id\": \""+getWtf_nc_technical_passport_id()+"\", \"wtf_nc_data_source\": \""+getWtf_nc_data_source()+"\", \"wtf_nc_installation_date\": \""+getWtf_nc_installation_date()+"\", \"wtf_nc_checkout_date\": \""+getWtf_nc_checkout_date()+"\", \"wtf_nc_deleted\": \""+getWtf_nc_deleted()+"\", \"wtf_nc_facility_latitude\": \""+getWtf_nc_facility_latitude()+"\", \"wtf_nc_facility_longitude\": \""+getWtf_nc_facility_longitude()+"\", \"wtf_nc_facility_municipality_code\": \""+getWtf_nc_facility_municipality_code()+"\", \"wtf_nc_discharge_type\": \""+getWtf_nc_discharge_type()+"\", \"wtf_nc_discharge_latitude\": \""+getWtf_nc_discharge_latitude()+"\", \"wtf_nc_discharge_longitude\": \""+getWtf_nc_discharge_longitude()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\", \"wtf_fam_id\": \""+getWtf_fam_id()+"\", \"wtf_nc_fam_pop_equivalent\": \""+getWtf_nc_fam_pop_equivalent()+"\", \"wtf_nc_fam_tech_pass\": \""+getWtf_nc_fam_tech_pass()+"\", \"wtf_nc_fam_chds\": \""+getWtf_nc_fam_chds()+"\", \"wtf_nc_fam_bds\": \""+getWtf_nc_fam_bds()+"\", \"wtf_nc_fam_float_material\": \""+getWtf_nc_fam_float_material()+"\", \"wtf_nc_fam_phosphor\": \""+getWtf_nc_fam_phosphor()+"\", \"wtf_nc_fam_nitrogen\": \""+getWtf_nc_fam_nitrogen()+"\", \"wtf_nc_fam_manufacturer\": \""+getWtf_nc_fam_manufacturer()+"\", \"wtf_nc_fam_model\": \""+getWtf_nc_fam_model()+"\", \"wtf_nc_fam_description\": \""+getWtf_nc_fam_description()+"\"}}";
   }

}