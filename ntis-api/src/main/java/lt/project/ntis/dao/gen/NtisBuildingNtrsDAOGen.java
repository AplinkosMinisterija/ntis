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

public class NtisBuildingNtrsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_BUILDING_NTRS.BN_ID";
   private Double bn_id;
   private Double bn_aob_code;
   private String bn_status;
   private String bn_status_desc;
   private String bn_obj_inv_code;
   private String bn_reg_nr;
   private Date bn_reg_date;
   private Double bn_adr_id;
   private String bn_municipality_code;
   private String bn_municipality;
   private String bn_sen_code;
   private String bn_sen_name;
   private String bn_place_code;
   private String bn_place_name;
   private String bn_street_code;
   private String bn_street;
   private String bn_house_nr;
   private String bn_housing_nr;
   private String bn_flat_nr;
   private Double bn_coordinate_latitude_ntr;
   private Double bn_coordinate_longitude_ntr;
   private Double bn_coordinate_latitude_adr;
   private Double bn_coordinate_longitude_adr;
   private String bn_obj_inv_parent_code;
   private Date bn_object_inv_date;
   private Double bn_object_type;
   private String bn_object_name;
   private Double bn_pask_type;
   private String bn_pask_name;
   private Double bn_construction_start_year;
   private Double bn_construction_end_year;
   private Double bn_construction_completion;
   private Double bn_total_area;
   private Double bn_useable_area;
   private Double bn_living_area;
   private String bn_wastewater_treatment;
   private String bn_water_supply;
   private String bn_declr_type;
   private Double bn_live_count;
   private Double bn_ad_id;
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
   private Date bn_date_from;
   private Date bn_date_to;

   protected boolean bn_id_changed;
   protected boolean bn_aob_code_changed;
   protected boolean bn_status_changed;
   protected boolean bn_status_desc_changed;
   protected boolean bn_obj_inv_code_changed;
   protected boolean bn_reg_nr_changed;
   protected boolean bn_reg_date_changed;
   protected boolean bn_adr_id_changed;
   protected boolean bn_municipality_code_changed;
   protected boolean bn_municipality_changed;
   protected boolean bn_sen_code_changed;
   protected boolean bn_sen_name_changed;
   protected boolean bn_place_code_changed;
   protected boolean bn_place_name_changed;
   protected boolean bn_street_code_changed;
   protected boolean bn_street_changed;
   protected boolean bn_house_nr_changed;
   protected boolean bn_housing_nr_changed;
   protected boolean bn_flat_nr_changed;
   protected boolean bn_coordinate_latitude_ntr_changed;
   protected boolean bn_coordinate_longitude_ntr_changed;
   protected boolean bn_coordinate_latitude_adr_changed;
   protected boolean bn_coordinate_longitude_adr_changed;
   protected boolean bn_obj_inv_parent_code_changed;
   protected boolean bn_object_inv_date_changed;
   protected boolean bn_object_type_changed;
   protected boolean bn_object_name_changed;
   protected boolean bn_pask_type_changed;
   protected boolean bn_pask_name_changed;
   protected boolean bn_construction_start_year_changed;
   protected boolean bn_construction_end_year_changed;
   protected boolean bn_construction_completion_changed;
   protected boolean bn_total_area_changed;
   protected boolean bn_useable_area_changed;
   protected boolean bn_living_area_changed;
   protected boolean bn_wastewater_treatment_changed;
   protected boolean bn_water_supply_changed;
   protected boolean bn_declr_type_changed;
   protected boolean bn_live_count_changed;
   protected boolean bn_ad_id_changed;
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
   protected boolean bn_date_from_changed;
   protected boolean bn_date_to_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisBuildingNtrsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisBuildingNtrsDAOGen(Double bn_id, Double bn_aob_code, String bn_status, String bn_status_desc, String bn_obj_inv_code, String bn_reg_nr, Date bn_reg_date, Double bn_adr_id, String bn_municipality_code, String bn_municipality, String bn_sen_code, String bn_sen_name, String bn_place_code, String bn_place_name, String bn_street_code, String bn_street, String bn_house_nr, String bn_housing_nr, String bn_flat_nr, Double bn_coordinate_latitude_ntr, Double bn_coordinate_longitude_ntr, Double bn_coordinate_latitude_adr, Double bn_coordinate_longitude_adr, String bn_obj_inv_parent_code, Date bn_object_inv_date, Double bn_object_type, String bn_object_name, Double bn_pask_type, String bn_pask_name, Double bn_construction_start_year, Double bn_construction_end_year, Double bn_construction_completion, Double bn_total_area, Double bn_useable_area, Double bn_living_area, String bn_wastewater_treatment, String bn_water_supply, String bn_declr_type, Double bn_live_count, Double bn_ad_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Date bn_date_from, Date bn_date_to) {
      this.bn_id = bn_id;
      this.bn_aob_code = bn_aob_code;
      this.bn_status = bn_status;
      this.bn_status_desc = bn_status_desc;
      this.bn_obj_inv_code = bn_obj_inv_code;
      this.bn_reg_nr = bn_reg_nr;
      this.bn_reg_date = bn_reg_date;
      this.bn_adr_id = bn_adr_id;
      this.bn_municipality_code = bn_municipality_code;
      this.bn_municipality = bn_municipality;
      this.bn_sen_code = bn_sen_code;
      this.bn_sen_name = bn_sen_name;
      this.bn_place_code = bn_place_code;
      this.bn_place_name = bn_place_name;
      this.bn_street_code = bn_street_code;
      this.bn_street = bn_street;
      this.bn_house_nr = bn_house_nr;
      this.bn_housing_nr = bn_housing_nr;
      this.bn_flat_nr = bn_flat_nr;
      this.bn_coordinate_latitude_ntr = bn_coordinate_latitude_ntr;
      this.bn_coordinate_longitude_ntr = bn_coordinate_longitude_ntr;
      this.bn_coordinate_latitude_adr = bn_coordinate_latitude_adr;
      this.bn_coordinate_longitude_adr = bn_coordinate_longitude_adr;
      this.bn_obj_inv_parent_code = bn_obj_inv_parent_code;
      this.bn_object_inv_date = bn_object_inv_date;
      this.bn_object_type = bn_object_type;
      this.bn_object_name = bn_object_name;
      this.bn_pask_type = bn_pask_type;
      this.bn_pask_name = bn_pask_name;
      this.bn_construction_start_year = bn_construction_start_year;
      this.bn_construction_end_year = bn_construction_end_year;
      this.bn_construction_completion = bn_construction_completion;
      this.bn_total_area = bn_total_area;
      this.bn_useable_area = bn_useable_area;
      this.bn_living_area = bn_living_area;
      this.bn_wastewater_treatment = bn_wastewater_treatment;
      this.bn_water_supply = bn_water_supply;
      this.bn_declr_type = bn_declr_type;
      this.bn_live_count = bn_live_count;
      this.bn_ad_id = bn_ad_id;
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
      this.bn_date_from = bn_date_from;
      this.bn_date_to = bn_date_to;
   }
   public void copyValues(NtisBuildingNtrsDAOGen obj) {
      this.setBn_id(obj.getBn_id());
      this.setBn_aob_code(obj.getBn_aob_code());
      this.setBn_status(obj.getBn_status());
      this.setBn_status_desc(obj.getBn_status_desc());
      this.setBn_obj_inv_code(obj.getBn_obj_inv_code());
      this.setBn_reg_nr(obj.getBn_reg_nr());
      this.setBn_reg_date(obj.getBn_reg_date());
      this.setBn_adr_id(obj.getBn_adr_id());
      this.setBn_municipality_code(obj.getBn_municipality_code());
      this.setBn_municipality(obj.getBn_municipality());
      this.setBn_sen_code(obj.getBn_sen_code());
      this.setBn_sen_name(obj.getBn_sen_name());
      this.setBn_place_code(obj.getBn_place_code());
      this.setBn_place_name(obj.getBn_place_name());
      this.setBn_street_code(obj.getBn_street_code());
      this.setBn_street(obj.getBn_street());
      this.setBn_house_nr(obj.getBn_house_nr());
      this.setBn_housing_nr(obj.getBn_housing_nr());
      this.setBn_flat_nr(obj.getBn_flat_nr());
      this.setBn_coordinate_latitude_ntr(obj.getBn_coordinate_latitude_ntr());
      this.setBn_coordinate_longitude_ntr(obj.getBn_coordinate_longitude_ntr());
      this.setBn_coordinate_latitude_adr(obj.getBn_coordinate_latitude_adr());
      this.setBn_coordinate_longitude_adr(obj.getBn_coordinate_longitude_adr());
      this.setBn_obj_inv_parent_code(obj.getBn_obj_inv_parent_code());
      this.setBn_object_inv_date(obj.getBn_object_inv_date());
      this.setBn_object_type(obj.getBn_object_type());
      this.setBn_object_name(obj.getBn_object_name());
      this.setBn_pask_type(obj.getBn_pask_type());
      this.setBn_pask_name(obj.getBn_pask_name());
      this.setBn_construction_start_year(obj.getBn_construction_start_year());
      this.setBn_construction_end_year(obj.getBn_construction_end_year());
      this.setBn_construction_completion(obj.getBn_construction_completion());
      this.setBn_total_area(obj.getBn_total_area());
      this.setBn_useable_area(obj.getBn_useable_area());
      this.setBn_living_area(obj.getBn_living_area());
      this.setBn_wastewater_treatment(obj.getBn_wastewater_treatment());
      this.setBn_water_supply(obj.getBn_water_supply());
      this.setBn_declr_type(obj.getBn_declr_type());
      this.setBn_live_count(obj.getBn_live_count());
      this.setBn_ad_id(obj.getBn_ad_id());
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
      this.setBn_date_from(obj.getBn_date_from());
      this.setBn_date_to(obj.getBn_date_to());
   }
   protected void markObjectAsInitial() {
      this.bn_id_changed = false;
      this.bn_aob_code_changed = false;
      this.bn_status_changed = false;
      this.bn_status_desc_changed = false;
      this.bn_obj_inv_code_changed = false;
      this.bn_reg_nr_changed = false;
      this.bn_reg_date_changed = false;
      this.bn_adr_id_changed = false;
      this.bn_municipality_code_changed = false;
      this.bn_municipality_changed = false;
      this.bn_sen_code_changed = false;
      this.bn_sen_name_changed = false;
      this.bn_place_code_changed = false;
      this.bn_place_name_changed = false;
      this.bn_street_code_changed = false;
      this.bn_street_changed = false;
      this.bn_house_nr_changed = false;
      this.bn_housing_nr_changed = false;
      this.bn_flat_nr_changed = false;
      this.bn_coordinate_latitude_ntr_changed = false;
      this.bn_coordinate_longitude_ntr_changed = false;
      this.bn_coordinate_latitude_adr_changed = false;
      this.bn_coordinate_longitude_adr_changed = false;
      this.bn_obj_inv_parent_code_changed = false;
      this.bn_object_inv_date_changed = false;
      this.bn_object_type_changed = false;
      this.bn_object_name_changed = false;
      this.bn_pask_type_changed = false;
      this.bn_pask_name_changed = false;
      this.bn_construction_start_year_changed = false;
      this.bn_construction_end_year_changed = false;
      this.bn_construction_completion_changed = false;
      this.bn_total_area_changed = false;
      this.bn_useable_area_changed = false;
      this.bn_living_area_changed = false;
      this.bn_wastewater_treatment_changed = false;
      this.bn_water_supply_changed = false;
      this.bn_declr_type_changed = false;
      this.bn_live_count_changed = false;
      this.bn_ad_id_changed = false;
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
      this.bn_date_from_changed = false;
      this.bn_date_to_changed = false;
      this.record_changed = false;
   }
   public void setBn_id(Double bn_id) {
      if (this.isForceUpdate() || (this.bn_id != null && !this.bn_id.equals(bn_id)) || (bn_id != null && !bn_id.equals(this.bn_id))){
         this.bn_id_changed = true; 
         this.record_changed = true;
         this.bn_id = bn_id;
      }
   }
   public Double getBn_id() {
      return this.bn_id;
   }
   public void setBn_aob_code(Double bn_aob_code) {
      if (this.isForceUpdate() || (this.bn_aob_code != null && !this.bn_aob_code.equals(bn_aob_code)) || (bn_aob_code != null && !bn_aob_code.equals(this.bn_aob_code))){
         this.bn_aob_code_changed = true; 
         this.record_changed = true;
         this.bn_aob_code = bn_aob_code;
      }
   }
   public Double getBn_aob_code() {
      return this.bn_aob_code;
   }
   public void setBn_status(String bn_status) {
      if (this.isForceUpdate() || (this.bn_status != null && !this.bn_status.equals(bn_status)) || (bn_status != null && !bn_status.equals(this.bn_status))){
         this.bn_status_changed = true; 
         this.record_changed = true;
         this.bn_status = bn_status;
      }
   }
   public String getBn_status() {
      return this.bn_status;
   }
   public void setBn_status_desc(String bn_status_desc) {
      if (this.isForceUpdate() || (this.bn_status_desc != null && !this.bn_status_desc.equals(bn_status_desc)) || (bn_status_desc != null && !bn_status_desc.equals(this.bn_status_desc))){
         this.bn_status_desc_changed = true; 
         this.record_changed = true;
         this.bn_status_desc = bn_status_desc;
      }
   }
   public String getBn_status_desc() {
      return this.bn_status_desc;
   }
   public void setBn_obj_inv_code(String bn_obj_inv_code) {
      if (this.isForceUpdate() || (this.bn_obj_inv_code != null && !this.bn_obj_inv_code.equals(bn_obj_inv_code)) || (bn_obj_inv_code != null && !bn_obj_inv_code.equals(this.bn_obj_inv_code))){
         this.bn_obj_inv_code_changed = true; 
         this.record_changed = true;
         this.bn_obj_inv_code = bn_obj_inv_code;
      }
   }
   public String getBn_obj_inv_code() {
      return this.bn_obj_inv_code;
   }
   public void setBn_reg_nr(String bn_reg_nr) {
      if (this.isForceUpdate() || (this.bn_reg_nr != null && !this.bn_reg_nr.equals(bn_reg_nr)) || (bn_reg_nr != null && !bn_reg_nr.equals(this.bn_reg_nr))){
         this.bn_reg_nr_changed = true; 
         this.record_changed = true;
         this.bn_reg_nr = bn_reg_nr;
      }
   }
   public String getBn_reg_nr() {
      return this.bn_reg_nr;
   }
   public void setBn_reg_date(Date bn_reg_date) {
      if (this.isForceUpdate() || (this.bn_reg_date != null && !this.bn_reg_date.equals(bn_reg_date)) || (bn_reg_date != null && !bn_reg_date.equals(this.bn_reg_date))){
         this.bn_reg_date_changed = true; 
         this.record_changed = true;
         this.bn_reg_date = bn_reg_date;
      }
   }
   public Date getBn_reg_date() {
      return this.bn_reg_date;
   }
   public void setBn_adr_id(Double bn_adr_id) {
      if (this.isForceUpdate() || (this.bn_adr_id != null && !this.bn_adr_id.equals(bn_adr_id)) || (bn_adr_id != null && !bn_adr_id.equals(this.bn_adr_id))){
         this.bn_adr_id_changed = true; 
         this.record_changed = true;
         this.bn_adr_id = bn_adr_id;
      }
   }
   public Double getBn_adr_id() {
      return this.bn_adr_id;
   }
   public void setBn_municipality_code(String bn_municipality_code) {
      if (this.isForceUpdate() || (this.bn_municipality_code != null && !this.bn_municipality_code.equals(bn_municipality_code)) || (bn_municipality_code != null && !bn_municipality_code.equals(this.bn_municipality_code))){
         this.bn_municipality_code_changed = true; 
         this.record_changed = true;
         this.bn_municipality_code = bn_municipality_code;
      }
   }
   public String getBn_municipality_code() {
      return this.bn_municipality_code;
   }
   public void setBn_municipality(String bn_municipality) {
      if (this.isForceUpdate() || (this.bn_municipality != null && !this.bn_municipality.equals(bn_municipality)) || (bn_municipality != null && !bn_municipality.equals(this.bn_municipality))){
         this.bn_municipality_changed = true; 
         this.record_changed = true;
         this.bn_municipality = bn_municipality;
      }
   }
   public String getBn_municipality() {
      return this.bn_municipality;
   }
   public void setBn_sen_code(String bn_sen_code) {
      if (this.isForceUpdate() || (this.bn_sen_code != null && !this.bn_sen_code.equals(bn_sen_code)) || (bn_sen_code != null && !bn_sen_code.equals(this.bn_sen_code))){
         this.bn_sen_code_changed = true; 
         this.record_changed = true;
         this.bn_sen_code = bn_sen_code;
      }
   }
   public String getBn_sen_code() {
      return this.bn_sen_code;
   }
   public void setBn_sen_name(String bn_sen_name) {
      if (this.isForceUpdate() || (this.bn_sen_name != null && !this.bn_sen_name.equals(bn_sen_name)) || (bn_sen_name != null && !bn_sen_name.equals(this.bn_sen_name))){
         this.bn_sen_name_changed = true; 
         this.record_changed = true;
         this.bn_sen_name = bn_sen_name;
      }
   }
   public String getBn_sen_name() {
      return this.bn_sen_name;
   }
   public void setBn_place_code(String bn_place_code) {
      if (this.isForceUpdate() || (this.bn_place_code != null && !this.bn_place_code.equals(bn_place_code)) || (bn_place_code != null && !bn_place_code.equals(this.bn_place_code))){
         this.bn_place_code_changed = true; 
         this.record_changed = true;
         this.bn_place_code = bn_place_code;
      }
   }
   public String getBn_place_code() {
      return this.bn_place_code;
   }
   public void setBn_place_name(String bn_place_name) {
      if (this.isForceUpdate() || (this.bn_place_name != null && !this.bn_place_name.equals(bn_place_name)) || (bn_place_name != null && !bn_place_name.equals(this.bn_place_name))){
         this.bn_place_name_changed = true; 
         this.record_changed = true;
         this.bn_place_name = bn_place_name;
      }
   }
   public String getBn_place_name() {
      return this.bn_place_name;
   }
   public void setBn_street_code(String bn_street_code) {
      if (this.isForceUpdate() || (this.bn_street_code != null && !this.bn_street_code.equals(bn_street_code)) || (bn_street_code != null && !bn_street_code.equals(this.bn_street_code))){
         this.bn_street_code_changed = true; 
         this.record_changed = true;
         this.bn_street_code = bn_street_code;
      }
   }
   public String getBn_street_code() {
      return this.bn_street_code;
   }
   public void setBn_street(String bn_street) {
      if (this.isForceUpdate() || (this.bn_street != null && !this.bn_street.equals(bn_street)) || (bn_street != null && !bn_street.equals(this.bn_street))){
         this.bn_street_changed = true; 
         this.record_changed = true;
         this.bn_street = bn_street;
      }
   }
   public String getBn_street() {
      return this.bn_street;
   }
   public void setBn_house_nr(String bn_house_nr) {
      if (this.isForceUpdate() || (this.bn_house_nr != null && !this.bn_house_nr.equals(bn_house_nr)) || (bn_house_nr != null && !bn_house_nr.equals(this.bn_house_nr))){
         this.bn_house_nr_changed = true; 
         this.record_changed = true;
         this.bn_house_nr = bn_house_nr;
      }
   }
   public String getBn_house_nr() {
      return this.bn_house_nr;
   }
   public void setBn_housing_nr(String bn_housing_nr) {
      if (this.isForceUpdate() || (this.bn_housing_nr != null && !this.bn_housing_nr.equals(bn_housing_nr)) || (bn_housing_nr != null && !bn_housing_nr.equals(this.bn_housing_nr))){
         this.bn_housing_nr_changed = true; 
         this.record_changed = true;
         this.bn_housing_nr = bn_housing_nr;
      }
   }
   public String getBn_housing_nr() {
      return this.bn_housing_nr;
   }
   public void setBn_flat_nr(String bn_flat_nr) {
      if (this.isForceUpdate() || (this.bn_flat_nr != null && !this.bn_flat_nr.equals(bn_flat_nr)) || (bn_flat_nr != null && !bn_flat_nr.equals(this.bn_flat_nr))){
         this.bn_flat_nr_changed = true; 
         this.record_changed = true;
         this.bn_flat_nr = bn_flat_nr;
      }
   }
   public String getBn_flat_nr() {
      return this.bn_flat_nr;
   }
   public void setBn_coordinate_latitude_ntr(Double bn_coordinate_latitude_ntr) {
      if (this.isForceUpdate() || (this.bn_coordinate_latitude_ntr != null && !this.bn_coordinate_latitude_ntr.equals(bn_coordinate_latitude_ntr)) || (bn_coordinate_latitude_ntr != null && !bn_coordinate_latitude_ntr.equals(this.bn_coordinate_latitude_ntr))){
         this.bn_coordinate_latitude_ntr_changed = true; 
         this.record_changed = true;
         this.bn_coordinate_latitude_ntr = bn_coordinate_latitude_ntr;
      }
   }
   public Double getBn_coordinate_latitude_ntr() {
      return this.bn_coordinate_latitude_ntr;
   }
   public void setBn_coordinate_longitude_ntr(Double bn_coordinate_longitude_ntr) {
      if (this.isForceUpdate() || (this.bn_coordinate_longitude_ntr != null && !this.bn_coordinate_longitude_ntr.equals(bn_coordinate_longitude_ntr)) || (bn_coordinate_longitude_ntr != null && !bn_coordinate_longitude_ntr.equals(this.bn_coordinate_longitude_ntr))){
         this.bn_coordinate_longitude_ntr_changed = true; 
         this.record_changed = true;
         this.bn_coordinate_longitude_ntr = bn_coordinate_longitude_ntr;
      }
   }
   public Double getBn_coordinate_longitude_ntr() {
      return this.bn_coordinate_longitude_ntr;
   }
   public void setBn_coordinate_latitude_adr(Double bn_coordinate_latitude_adr) {
      if (this.isForceUpdate() || (this.bn_coordinate_latitude_adr != null && !this.bn_coordinate_latitude_adr.equals(bn_coordinate_latitude_adr)) || (bn_coordinate_latitude_adr != null && !bn_coordinate_latitude_adr.equals(this.bn_coordinate_latitude_adr))){
         this.bn_coordinate_latitude_adr_changed = true; 
         this.record_changed = true;
         this.bn_coordinate_latitude_adr = bn_coordinate_latitude_adr;
      }
   }
   public Double getBn_coordinate_latitude_adr() {
      return this.bn_coordinate_latitude_adr;
   }
   public void setBn_coordinate_longitude_adr(Double bn_coordinate_longitude_adr) {
      if (this.isForceUpdate() || (this.bn_coordinate_longitude_adr != null && !this.bn_coordinate_longitude_adr.equals(bn_coordinate_longitude_adr)) || (bn_coordinate_longitude_adr != null && !bn_coordinate_longitude_adr.equals(this.bn_coordinate_longitude_adr))){
         this.bn_coordinate_longitude_adr_changed = true; 
         this.record_changed = true;
         this.bn_coordinate_longitude_adr = bn_coordinate_longitude_adr;
      }
   }
   public Double getBn_coordinate_longitude_adr() {
      return this.bn_coordinate_longitude_adr;
   }
   public void setBn_obj_inv_parent_code(String bn_obj_inv_parent_code) {
      if (this.isForceUpdate() || (this.bn_obj_inv_parent_code != null && !this.bn_obj_inv_parent_code.equals(bn_obj_inv_parent_code)) || (bn_obj_inv_parent_code != null && !bn_obj_inv_parent_code.equals(this.bn_obj_inv_parent_code))){
         this.bn_obj_inv_parent_code_changed = true; 
         this.record_changed = true;
         this.bn_obj_inv_parent_code = bn_obj_inv_parent_code;
      }
   }
   public String getBn_obj_inv_parent_code() {
      return this.bn_obj_inv_parent_code;
   }
   public void setBn_object_inv_date(Date bn_object_inv_date) {
      if (this.isForceUpdate() || (this.bn_object_inv_date != null && !this.bn_object_inv_date.equals(bn_object_inv_date)) || (bn_object_inv_date != null && !bn_object_inv_date.equals(this.bn_object_inv_date))){
         this.bn_object_inv_date_changed = true; 
         this.record_changed = true;
         this.bn_object_inv_date = bn_object_inv_date;
      }
   }
   public Date getBn_object_inv_date() {
      return this.bn_object_inv_date;
   }
   public void setBn_object_type(Double bn_object_type) {
      if (this.isForceUpdate() || (this.bn_object_type != null && !this.bn_object_type.equals(bn_object_type)) || (bn_object_type != null && !bn_object_type.equals(this.bn_object_type))){
         this.bn_object_type_changed = true; 
         this.record_changed = true;
         this.bn_object_type = bn_object_type;
      }
   }
   public Double getBn_object_type() {
      return this.bn_object_type;
   }
   public void setBn_object_name(String bn_object_name) {
      if (this.isForceUpdate() || (this.bn_object_name != null && !this.bn_object_name.equals(bn_object_name)) || (bn_object_name != null && !bn_object_name.equals(this.bn_object_name))){
         this.bn_object_name_changed = true; 
         this.record_changed = true;
         this.bn_object_name = bn_object_name;
      }
   }
   public String getBn_object_name() {
      return this.bn_object_name;
   }
   public void setBn_pask_type(Double bn_pask_type) {
      if (this.isForceUpdate() || (this.bn_pask_type != null && !this.bn_pask_type.equals(bn_pask_type)) || (bn_pask_type != null && !bn_pask_type.equals(this.bn_pask_type))){
         this.bn_pask_type_changed = true; 
         this.record_changed = true;
         this.bn_pask_type = bn_pask_type;
      }
   }
   public Double getBn_pask_type() {
      return this.bn_pask_type;
   }
   public void setBn_pask_name(String bn_pask_name) {
      if (this.isForceUpdate() || (this.bn_pask_name != null && !this.bn_pask_name.equals(bn_pask_name)) || (bn_pask_name != null && !bn_pask_name.equals(this.bn_pask_name))){
         this.bn_pask_name_changed = true; 
         this.record_changed = true;
         this.bn_pask_name = bn_pask_name;
      }
   }
   public String getBn_pask_name() {
      return this.bn_pask_name;
   }
   public void setBn_construction_start_year(Double bn_construction_start_year) {
      if (this.isForceUpdate() || (this.bn_construction_start_year != null && !this.bn_construction_start_year.equals(bn_construction_start_year)) || (bn_construction_start_year != null && !bn_construction_start_year.equals(this.bn_construction_start_year))){
         this.bn_construction_start_year_changed = true; 
         this.record_changed = true;
         this.bn_construction_start_year = bn_construction_start_year;
      }
   }
   public Double getBn_construction_start_year() {
      return this.bn_construction_start_year;
   }
   public void setBn_construction_end_year(Double bn_construction_end_year) {
      if (this.isForceUpdate() || (this.bn_construction_end_year != null && !this.bn_construction_end_year.equals(bn_construction_end_year)) || (bn_construction_end_year != null && !bn_construction_end_year.equals(this.bn_construction_end_year))){
         this.bn_construction_end_year_changed = true; 
         this.record_changed = true;
         this.bn_construction_end_year = bn_construction_end_year;
      }
   }
   public Double getBn_construction_end_year() {
      return this.bn_construction_end_year;
   }
   public void setBn_construction_completion(Double bn_construction_completion) {
      if (this.isForceUpdate() || (this.bn_construction_completion != null && !this.bn_construction_completion.equals(bn_construction_completion)) || (bn_construction_completion != null && !bn_construction_completion.equals(this.bn_construction_completion))){
         this.bn_construction_completion_changed = true; 
         this.record_changed = true;
         this.bn_construction_completion = bn_construction_completion;
      }
   }
   public Double getBn_construction_completion() {
      return this.bn_construction_completion;
   }
   public void setBn_total_area(Double bn_total_area) {
      if (this.isForceUpdate() || (this.bn_total_area != null && !this.bn_total_area.equals(bn_total_area)) || (bn_total_area != null && !bn_total_area.equals(this.bn_total_area))){
         this.bn_total_area_changed = true; 
         this.record_changed = true;
         this.bn_total_area = bn_total_area;
      }
   }
   public Double getBn_total_area() {
      return this.bn_total_area;
   }
   public void setBn_useable_area(Double bn_useable_area) {
      if (this.isForceUpdate() || (this.bn_useable_area != null && !this.bn_useable_area.equals(bn_useable_area)) || (bn_useable_area != null && !bn_useable_area.equals(this.bn_useable_area))){
         this.bn_useable_area_changed = true; 
         this.record_changed = true;
         this.bn_useable_area = bn_useable_area;
      }
   }
   public Double getBn_useable_area() {
      return this.bn_useable_area;
   }
   public void setBn_living_area(Double bn_living_area) {
      if (this.isForceUpdate() || (this.bn_living_area != null && !this.bn_living_area.equals(bn_living_area)) || (bn_living_area != null && !bn_living_area.equals(this.bn_living_area))){
         this.bn_living_area_changed = true; 
         this.record_changed = true;
         this.bn_living_area = bn_living_area;
      }
   }
   public Double getBn_living_area() {
      return this.bn_living_area;
   }
   public void setBn_wastewater_treatment(String bn_wastewater_treatment) {
      if (this.isForceUpdate() || (this.bn_wastewater_treatment != null && !this.bn_wastewater_treatment.equals(bn_wastewater_treatment)) || (bn_wastewater_treatment != null && !bn_wastewater_treatment.equals(this.bn_wastewater_treatment))){
         this.bn_wastewater_treatment_changed = true; 
         this.record_changed = true;
         this.bn_wastewater_treatment = bn_wastewater_treatment;
      }
   }
   public String getBn_wastewater_treatment() {
      return this.bn_wastewater_treatment;
   }
   public void setBn_water_supply(String bn_water_supply) {
      if (this.isForceUpdate() || (this.bn_water_supply != null && !this.bn_water_supply.equals(bn_water_supply)) || (bn_water_supply != null && !bn_water_supply.equals(this.bn_water_supply))){
         this.bn_water_supply_changed = true; 
         this.record_changed = true;
         this.bn_water_supply = bn_water_supply;
      }
   }
   public String getBn_water_supply() {
      return this.bn_water_supply;
   }
   public void setBn_declr_type(String bn_declr_type) {
      if (this.isForceUpdate() || (this.bn_declr_type != null && !this.bn_declr_type.equals(bn_declr_type)) || (bn_declr_type != null && !bn_declr_type.equals(this.bn_declr_type))){
         this.bn_declr_type_changed = true; 
         this.record_changed = true;
         this.bn_declr_type = bn_declr_type;
      }
   }
   public String getBn_declr_type() {
      return this.bn_declr_type;
   }
   public void setBn_live_count(Double bn_live_count) {
      if (this.isForceUpdate() || (this.bn_live_count != null && !this.bn_live_count.equals(bn_live_count)) || (bn_live_count != null && !bn_live_count.equals(this.bn_live_count))){
         this.bn_live_count_changed = true; 
         this.record_changed = true;
         this.bn_live_count = bn_live_count;
      }
   }
   public Double getBn_live_count() {
      return this.bn_live_count;
   }
   public void setBn_ad_id(Double bn_ad_id) {
      if (this.isForceUpdate() || (this.bn_ad_id != null && !this.bn_ad_id.equals(bn_ad_id)) || (bn_ad_id != null && !bn_ad_id.equals(this.bn_ad_id))){
         this.bn_ad_id_changed = true; 
         this.record_changed = true;
         this.bn_ad_id = bn_ad_id;
      }
   }
   public Double getBn_ad_id() {
      return this.bn_ad_id;
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
   @JsonIgnore
   public void setN01(Double n01) {
      if (this.isForceUpdate() || (this.n01 != null && !this.n01.equals(n01)) || (n01 != null && !n01.equals(this.n01))){
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
      if (this.isForceUpdate() || (this.n02 != null && !this.n02.equals(n02)) || (n02 != null && !n02.equals(this.n02))){
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
      if (this.isForceUpdate() || (this.n03 != null && !this.n03.equals(n03)) || (n03 != null && !n03.equals(this.n03))){
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
      if (this.isForceUpdate() || (this.n04 != null && !this.n04.equals(n04)) || (n04 != null && !n04.equals(this.n04))){
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
      if (this.isForceUpdate() || (this.n05 != null && !this.n05.equals(n05)) || (n05 != null && !n05.equals(this.n05))){
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
      if (this.isForceUpdate() || (this.c01 != null && !this.c01.equals(c01)) || (c01 != null && !c01.equals(this.c01))){
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
      if (this.isForceUpdate() || (this.c02 != null && !this.c02.equals(c02)) || (c02 != null && !c02.equals(this.c02))){
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
      if (this.isForceUpdate() || (this.c03 != null && !this.c03.equals(c03)) || (c03 != null && !c03.equals(this.c03))){
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
      if (this.isForceUpdate() || (this.c04 != null && !this.c04.equals(c04)) || (c04 != null && !c04.equals(this.c04))){
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
      if (this.isForceUpdate() || (this.c05 != null && !this.c05.equals(c05)) || (c05 != null && !c05.equals(this.c05))){
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
      if (this.isForceUpdate() || (this.d01 != null && !this.d01.equals(d01)) || (d01 != null && !d01.equals(this.d01))){
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
      if (this.isForceUpdate() || (this.d02 != null && !this.d02.equals(d02)) || (d02 != null && !d02.equals(this.d02))){
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
      if (this.isForceUpdate() || (this.d03 != null && !this.d03.equals(d03)) || (d03 != null && !d03.equals(this.d03))){
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
      if (this.isForceUpdate() || (this.d04 != null && !this.d04.equals(d04)) || (d04 != null && !d04.equals(this.d04))){
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
      if (this.isForceUpdate() || (this.d05 != null && !this.d05.equals(d05)) || (d05 != null && !d05.equals(this.d05))){
         this.d05_changed = true; 
         this.record_changed = true;
         this.d05 = d05;
      }
   }
   @JsonIgnore
   public Date getD05() {
      return this.d05;
   }
   public void setBn_date_from(Date bn_date_from) {
      if (this.isForceUpdate() || (this.bn_date_from != null && !this.bn_date_from.equals(bn_date_from)) || (bn_date_from != null && !bn_date_from.equals(this.bn_date_from))){
         this.bn_date_from_changed = true; 
         this.record_changed = true;
         this.bn_date_from = bn_date_from;
      }
   }
   public Date getBn_date_from() {
      return this.bn_date_from;
   }
   public void setBn_date_to(Date bn_date_to) {
      if (this.isForceUpdate() || (this.bn_date_to != null && !this.bn_date_to.equals(bn_date_to)) || (bn_date_to != null && !bn_date_to.equals(this.bn_date_to))){
         this.bn_date_to_changed = true; 
         this.record_changed = true;
         this.bn_date_to = bn_date_to;
      }
   }
   public Date getBn_date_to() {
      return this.bn_date_to;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.bn_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.bn_id;
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
      if (bn_aob_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_aob_code!= null && (""+bn_aob_code.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_AOB_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_status_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_status!= null && bn_status.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_STATUS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_status_desc_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_status_desc!= null && bn_status_desc.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_STATUS_DESC", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (bn_obj_inv_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_obj_inv_code!= null && bn_obj_inv_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_OBJ_INV_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_reg_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_reg_nr!= null && bn_reg_nr.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_REG_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_adr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_adr_id!= null && (""+bn_adr_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_ADR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_municipality_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_municipality_code!= null && bn_municipality_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_MUNICIPALITY_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_municipality_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_municipality!= null && bn_municipality.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_MUNICIPALITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (bn_sen_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_sen_code!= null && bn_sen_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_SEN_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_sen_name_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_sen_name!= null && bn_sen_name.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_SEN_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (bn_place_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_place_code!= null && bn_place_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_PLACE_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_place_name_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_place_name!= null && bn_place_name.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_PLACE_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (bn_street_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_street_code!= null && bn_street_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_STREET_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_street_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_street!= null && bn_street.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_STREET", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (bn_house_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_house_nr!= null && bn_house_nr.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_HOUSE_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (bn_housing_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_housing_nr!= null && bn_housing_nr.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_HOUSING_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (bn_flat_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_flat_nr!= null && bn_flat_nr.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_FLAT_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (bn_coordinate_latitude_ntr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_coordinate_latitude_ntr!= null && bn_coordinate_latitude_ntr.toString().length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_COORDINATE_LATITUDE_NTR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_coordinate_longitude_ntr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_coordinate_longitude_ntr!= null && bn_coordinate_longitude_ntr.toString().length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_COORDINATE_LONGITUDE_NTR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_coordinate_latitude_adr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_coordinate_latitude_adr!= null && bn_coordinate_latitude_adr.toString().length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_COORDINATE_LATITUDE_ADR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_coordinate_longitude_adr_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_coordinate_longitude_adr!= null && bn_coordinate_longitude_adr.toString().length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_COORDINATE_LONGITUDE_ADR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_obj_inv_parent_code_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_obj_inv_parent_code!= null && bn_obj_inv_parent_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_OBJ_INV_PARENT_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_object_type_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_object_type!= null && (""+bn_object_type.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_OBJECT_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_object_name_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_object_name!= null && bn_object_name.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_OBJECT_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (bn_pask_type_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_pask_type!= null && (""+bn_pask_type.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_PASK_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_pask_name_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_pask_name!= null && bn_pask_name.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_PASK_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (bn_construction_start_year_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_construction_start_year!= null && (""+bn_construction_start_year.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_CONSTRUCTION_START_YEAR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_construction_end_year_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_construction_end_year!= null && (""+bn_construction_end_year.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_CONSTRUCTION_END_YEAR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_construction_completion_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_construction_completion!= null && (""+bn_construction_completion.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_CONSTRUCTION_COMPLETION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_wastewater_treatment_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_wastewater_treatment!= null && bn_wastewater_treatment.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_WASTEWATER_TREATMENT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (bn_water_supply_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_water_supply!= null && bn_water_supply.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_WATER_SUPPLY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (bn_declr_type_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_declr_type!= null && bn_declr_type.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_DECLR_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (bn_live_count_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_live_count!= null && (""+bn_live_count.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_LIVE_COUNT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (bn_ad_id_changed || operation == Utils.OPERATION_INSERT) {
         if (bn_ad_id!= null && (""+bn_ad_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "BN_AD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_NTRS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_BUILDING_NTRS SET ";
      boolean changedExists = false;      if (bn_aob_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_AOB_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_aob_code);
      }
      if (bn_status_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_STATUS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_status);
      }
      if (bn_status_desc_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_STATUS_DESC = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_status_desc);
      }
      if (bn_obj_inv_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_OBJ_INV_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_obj_inv_code);
      }
      if (bn_reg_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_REG_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_reg_nr);
      }
      if (bn_reg_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_REG_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(bn_reg_date);
      }
      if (bn_adr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_ADR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_adr_id);
      }
      if (bn_municipality_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_MUNICIPALITY_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_municipality_code);
      }
      if (bn_municipality_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_MUNICIPALITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_municipality);
      }
      if (bn_sen_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_SEN_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_sen_code);
      }
      if (bn_sen_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_SEN_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_sen_name);
      }
      if (bn_place_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_PLACE_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_place_code);
      }
      if (bn_place_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_PLACE_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_place_name);
      }
      if (bn_street_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_STREET_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_street_code);
      }
      if (bn_street_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_STREET = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_street);
      }
      if (bn_house_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_HOUSE_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_house_nr);
      }
      if (bn_housing_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_HOUSING_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_housing_nr);
      }
      if (bn_flat_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_FLAT_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_flat_nr);
      }
      if (bn_coordinate_latitude_ntr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_COORDINATE_LATITUDE_NTR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_coordinate_latitude_ntr);
      }
      if (bn_coordinate_longitude_ntr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_COORDINATE_LONGITUDE_NTR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_coordinate_longitude_ntr);
      }
      if (bn_coordinate_latitude_adr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_COORDINATE_LATITUDE_ADR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_coordinate_latitude_adr);
      }
      if (bn_coordinate_longitude_adr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_COORDINATE_LONGITUDE_ADR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_coordinate_longitude_adr);
      }
      if (bn_obj_inv_parent_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_OBJ_INV_PARENT_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_obj_inv_parent_code);
      }
      if (bn_object_inv_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_OBJECT_INV_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(bn_object_inv_date);
      }
      if (bn_object_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_OBJECT_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_object_type);
      }
      if (bn_object_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_OBJECT_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_object_name);
      }
      if (bn_pask_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_PASK_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_pask_type);
      }
      if (bn_pask_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_PASK_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_pask_name);
      }
      if (bn_construction_start_year_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_CONSTRUCTION_START_YEAR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_construction_start_year);
      }
      if (bn_construction_end_year_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_CONSTRUCTION_END_YEAR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_construction_end_year);
      }
      if (bn_construction_completion_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_CONSTRUCTION_COMPLETION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_construction_completion);
      }
      if (bn_total_area_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_TOTAL_AREA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_total_area);
      }
      if (bn_useable_area_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_USEABLE_AREA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_useable_area);
      }
      if (bn_living_area_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_LIVING_AREA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_living_area);
      }
      if (bn_wastewater_treatment_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_WASTEWATER_TREATMENT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_wastewater_treatment);
      }
      if (bn_water_supply_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_WATER_SUPPLY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_water_supply);
      }
      if (bn_declr_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_DECLR_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_declr_type);
      }
      if (bn_live_count_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_LIVE_COUNT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_live_count);
      }
      if (bn_ad_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_AD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(bn_ad_id);
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
      if (bn_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(bn_date_from);
      }
      if (bn_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BN_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(bn_date_to);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  BN_ID = ? ";
      updateStatementPart.addStatementParam(bn_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisBuildingNtrsDAO\":{\"bn_id\": \""+getBn_id()+"\", \"bn_aob_code\": \""+getBn_aob_code()+"\", \"bn_status\": \""+getBn_status()+"\", \"bn_status_desc\": \""+getBn_status_desc()+"\", \"bn_obj_inv_code\": \""+getBn_obj_inv_code()+"\", \"bn_reg_nr\": \""+getBn_reg_nr()+"\", \"bn_reg_date\": \""+getBn_reg_date()+"\", \"bn_adr_id\": \""+getBn_adr_id()+"\", \"bn_municipality_code\": \""+getBn_municipality_code()+"\", \"bn_municipality\": \""+getBn_municipality()+"\", \"bn_sen_code\": \""+getBn_sen_code()+"\", \"bn_sen_name\": \""+getBn_sen_name()+"\", \"bn_place_code\": \""+getBn_place_code()+"\", \"bn_place_name\": \""+getBn_place_name()+"\", \"bn_street_code\": \""+getBn_street_code()+"\", \"bn_street\": \""+getBn_street()+"\", \"bn_house_nr\": \""+getBn_house_nr()+"\", \"bn_housing_nr\": \""+getBn_housing_nr()+"\", \"bn_flat_nr\": \""+getBn_flat_nr()+"\", \"bn_coordinate_latitude_ntr\": \""+getBn_coordinate_latitude_ntr()+"\", \"bn_coordinate_longitude_ntr\": \""+getBn_coordinate_longitude_ntr()+"\", \"bn_coordinate_latitude_adr\": \""+getBn_coordinate_latitude_adr()+"\", \"bn_coordinate_longitude_adr\": \""+getBn_coordinate_longitude_adr()+"\", \"bn_obj_inv_parent_code\": \""+getBn_obj_inv_parent_code()+"\", \"bn_object_inv_date\": \""+getBn_object_inv_date()+"\", \"bn_object_type\": \""+getBn_object_type()+"\", \"bn_object_name\": \""+getBn_object_name()+"\", \"bn_pask_type\": \""+getBn_pask_type()+"\", \"bn_pask_name\": \""+getBn_pask_name()+"\", \"bn_construction_start_year\": \""+getBn_construction_start_year()+"\", \"bn_construction_end_year\": \""+getBn_construction_end_year()+"\", \"bn_construction_completion\": \""+getBn_construction_completion()+"\", \"bn_total_area\": \""+getBn_total_area()+"\", \"bn_useable_area\": \""+getBn_useable_area()+"\", \"bn_living_area\": \""+getBn_living_area()+"\", \"bn_wastewater_treatment\": \""+getBn_wastewater_treatment()+"\", \"bn_water_supply\": \""+getBn_water_supply()+"\", \"bn_declr_type\": \""+getBn_declr_type()+"\", \"bn_live_count\": \""+getBn_live_count()+"\", \"bn_ad_id\": \""+getBn_ad_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\", \"bn_date_from\": \""+getBn_date_from()+"\", \"bn_date_to\": \""+getBn_date_to()+"\"}}";
   }

}