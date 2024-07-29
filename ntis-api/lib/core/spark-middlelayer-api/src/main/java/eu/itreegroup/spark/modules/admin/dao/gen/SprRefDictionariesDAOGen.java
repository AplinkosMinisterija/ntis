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

public class SprRefDictionariesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_REF_DICTIONARIES.RFD_ID";
   private Double rfd_id;
   private String rfd_subsystem;
   private String rfd_table_name;
   private String rfd_name;
   private String rfd_description;
   private String rfd_code_type;
   private Double rfd_code_length;
   private String rfd_code_colname;
   private String rfd_desc_colname;
   private String rfd_ref_domain_1;
   private String rfd_ref_domain_2;
   private String rfd_ref_domain_3;
   private String rfd_ref_domain_4;
   private String rfd_ref_domain_5;
   private String rfd_n1_colname;
   private String rfd_n2_colname;
   private String rfd_n3_colname;
   private String rfd_n4_colname;
   private String rfd_n5_colname;
   private String rfd_c1_colname;
   private String rfd_c2_colname;
   private String rfd_c3_colname;
   private String rfd_c4_colname;
   private String rfd_c5_colname;
   private String rfd_d1_colname;
   private String rfd_d2_colname;
   private String rfd_d3_colname;
   private String rfd_d4_colname;
   private String rfd_d5_colname;
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

   protected boolean rfd_id_changed;
   protected boolean rfd_subsystem_changed;
   protected boolean rfd_table_name_changed;
   protected boolean rfd_name_changed;
   protected boolean rfd_description_changed;
   protected boolean rfd_code_type_changed;
   protected boolean rfd_code_length_changed;
   protected boolean rfd_code_colname_changed;
   protected boolean rfd_desc_colname_changed;
   protected boolean rfd_ref_domain_1_changed;
   protected boolean rfd_ref_domain_2_changed;
   protected boolean rfd_ref_domain_3_changed;
   protected boolean rfd_ref_domain_4_changed;
   protected boolean rfd_ref_domain_5_changed;
   protected boolean rfd_n1_colname_changed;
   protected boolean rfd_n2_colname_changed;
   protected boolean rfd_n3_colname_changed;
   protected boolean rfd_n4_colname_changed;
   protected boolean rfd_n5_colname_changed;
   protected boolean rfd_c1_colname_changed;
   protected boolean rfd_c2_colname_changed;
   protected boolean rfd_c3_colname_changed;
   protected boolean rfd_c4_colname_changed;
   protected boolean rfd_c5_colname_changed;
   protected boolean rfd_d1_colname_changed;
   protected boolean rfd_d2_colname_changed;
   protected boolean rfd_d3_colname_changed;
   protected boolean rfd_d4_colname_changed;
   protected boolean rfd_d5_colname_changed;
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
   public SprRefDictionariesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprRefDictionariesDAOGen(Double rfd_id, String rfd_subsystem, String rfd_table_name, String rfd_name, String rfd_description, String rfd_code_type, Double rfd_code_length, String rfd_code_colname, String rfd_desc_colname, String rfd_ref_domain_1, String rfd_ref_domain_2, String rfd_ref_domain_3, String rfd_ref_domain_4, String rfd_ref_domain_5, String rfd_n1_colname, String rfd_n2_colname, String rfd_n3_colname, String rfd_n4_colname, String rfd_n5_colname, String rfd_c1_colname, String rfd_c2_colname, String rfd_c3_colname, String rfd_c4_colname, String rfd_c5_colname, String rfd_d1_colname, String rfd_d2_colname, String rfd_d3_colname, String rfd_d4_colname, String rfd_d5_colname, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.rfd_id = rfd_id;
      this.rfd_subsystem = rfd_subsystem;
      this.rfd_table_name = rfd_table_name;
      this.rfd_name = rfd_name;
      this.rfd_description = rfd_description;
      this.rfd_code_type = rfd_code_type;
      this.rfd_code_length = rfd_code_length;
      this.rfd_code_colname = rfd_code_colname;
      this.rfd_desc_colname = rfd_desc_colname;
      this.rfd_ref_domain_1 = rfd_ref_domain_1;
      this.rfd_ref_domain_2 = rfd_ref_domain_2;
      this.rfd_ref_domain_3 = rfd_ref_domain_3;
      this.rfd_ref_domain_4 = rfd_ref_domain_4;
      this.rfd_ref_domain_5 = rfd_ref_domain_5;
      this.rfd_n1_colname = rfd_n1_colname;
      this.rfd_n2_colname = rfd_n2_colname;
      this.rfd_n3_colname = rfd_n3_colname;
      this.rfd_n4_colname = rfd_n4_colname;
      this.rfd_n5_colname = rfd_n5_colname;
      this.rfd_c1_colname = rfd_c1_colname;
      this.rfd_c2_colname = rfd_c2_colname;
      this.rfd_c3_colname = rfd_c3_colname;
      this.rfd_c4_colname = rfd_c4_colname;
      this.rfd_c5_colname = rfd_c5_colname;
      this.rfd_d1_colname = rfd_d1_colname;
      this.rfd_d2_colname = rfd_d2_colname;
      this.rfd_d3_colname = rfd_d3_colname;
      this.rfd_d4_colname = rfd_d4_colname;
      this.rfd_d5_colname = rfd_d5_colname;
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
   public void copyValues(SprRefDictionariesDAOGen obj) {
      this.setRfd_id(obj.getRfd_id());
      this.setRfd_subsystem(obj.getRfd_subsystem());
      this.setRfd_table_name(obj.getRfd_table_name());
      this.setRfd_name(obj.getRfd_name());
      this.setRfd_description(obj.getRfd_description());
      this.setRfd_code_type(obj.getRfd_code_type());
      this.setRfd_code_length(obj.getRfd_code_length());
      this.setRfd_code_colname(obj.getRfd_code_colname());
      this.setRfd_desc_colname(obj.getRfd_desc_colname());
      this.setRfd_ref_domain_1(obj.getRfd_ref_domain_1());
      this.setRfd_ref_domain_2(obj.getRfd_ref_domain_2());
      this.setRfd_ref_domain_3(obj.getRfd_ref_domain_3());
      this.setRfd_ref_domain_4(obj.getRfd_ref_domain_4());
      this.setRfd_ref_domain_5(obj.getRfd_ref_domain_5());
      this.setRfd_n1_colname(obj.getRfd_n1_colname());
      this.setRfd_n2_colname(obj.getRfd_n2_colname());
      this.setRfd_n3_colname(obj.getRfd_n3_colname());
      this.setRfd_n4_colname(obj.getRfd_n4_colname());
      this.setRfd_n5_colname(obj.getRfd_n5_colname());
      this.setRfd_c1_colname(obj.getRfd_c1_colname());
      this.setRfd_c2_colname(obj.getRfd_c2_colname());
      this.setRfd_c3_colname(obj.getRfd_c3_colname());
      this.setRfd_c4_colname(obj.getRfd_c4_colname());
      this.setRfd_c5_colname(obj.getRfd_c5_colname());
      this.setRfd_d1_colname(obj.getRfd_d1_colname());
      this.setRfd_d2_colname(obj.getRfd_d2_colname());
      this.setRfd_d3_colname(obj.getRfd_d3_colname());
      this.setRfd_d4_colname(obj.getRfd_d4_colname());
      this.setRfd_d5_colname(obj.getRfd_d5_colname());
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
      this.rfd_id_changed = false;
      this.rfd_subsystem_changed = false;
      this.rfd_table_name_changed = false;
      this.rfd_name_changed = false;
      this.rfd_description_changed = false;
      this.rfd_code_type_changed = false;
      this.rfd_code_length_changed = false;
      this.rfd_code_colname_changed = false;
      this.rfd_desc_colname_changed = false;
      this.rfd_ref_domain_1_changed = false;
      this.rfd_ref_domain_2_changed = false;
      this.rfd_ref_domain_3_changed = false;
      this.rfd_ref_domain_4_changed = false;
      this.rfd_ref_domain_5_changed = false;
      this.rfd_n1_colname_changed = false;
      this.rfd_n2_colname_changed = false;
      this.rfd_n3_colname_changed = false;
      this.rfd_n4_colname_changed = false;
      this.rfd_n5_colname_changed = false;
      this.rfd_c1_colname_changed = false;
      this.rfd_c2_colname_changed = false;
      this.rfd_c3_colname_changed = false;
      this.rfd_c4_colname_changed = false;
      this.rfd_c5_colname_changed = false;
      this.rfd_d1_colname_changed = false;
      this.rfd_d2_colname_changed = false;
      this.rfd_d3_colname_changed = false;
      this.rfd_d4_colname_changed = false;
      this.rfd_d5_colname_changed = false;
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
   public void setRfd_id(Double rfd_id) {
      if (this.isForceUpdate() || 
               (this.rfd_id != null && !this.rfd_id.equals(rfd_id))  ||
               (rfd_id != null && !rfd_id.equals(this.rfd_id)) ){
         this.rfd_id_changed = true; 
         this.record_changed = true;
         this.rfd_id = rfd_id;
      }
   }
   public Double getRfd_id() {
      return this.rfd_id;
   }
   public void setRfd_subsystem(String rfd_subsystem) {
      if (this.isForceUpdate() || 
               (this.rfd_subsystem != null && !this.rfd_subsystem.equals(rfd_subsystem))  ||
               (rfd_subsystem != null && !rfd_subsystem.equals(this.rfd_subsystem)) ){
         this.rfd_subsystem_changed = true; 
         this.record_changed = true;
         this.rfd_subsystem = rfd_subsystem;
      }
   }
   public String getRfd_subsystem() {
      return this.rfd_subsystem;
   }
   public void setRfd_table_name(String rfd_table_name) {
      if (this.isForceUpdate() || 
               (this.rfd_table_name != null && !this.rfd_table_name.equals(rfd_table_name))  ||
               (rfd_table_name != null && !rfd_table_name.equals(this.rfd_table_name)) ){
         this.rfd_table_name_changed = true; 
         this.record_changed = true;
         this.rfd_table_name = rfd_table_name;
      }
   }
   public String getRfd_table_name() {
      return this.rfd_table_name;
   }
   public void setRfd_name(String rfd_name) {
      if (this.isForceUpdate() || 
               (this.rfd_name != null && !this.rfd_name.equals(rfd_name))  ||
               (rfd_name != null && !rfd_name.equals(this.rfd_name)) ){
         this.rfd_name_changed = true; 
         this.record_changed = true;
         this.rfd_name = rfd_name;
      }
   }
   public String getRfd_name() {
      return this.rfd_name;
   }
   public void setRfd_description(String rfd_description) {
      if (this.isForceUpdate() || 
               (this.rfd_description != null && !this.rfd_description.equals(rfd_description))  ||
               (rfd_description != null && !rfd_description.equals(this.rfd_description)) ){
         this.rfd_description_changed = true; 
         this.record_changed = true;
         this.rfd_description = rfd_description;
      }
   }
   public String getRfd_description() {
      return this.rfd_description;
   }
   public void setRfd_code_type(String rfd_code_type) {
      if (this.isForceUpdate() || 
               (this.rfd_code_type != null && !this.rfd_code_type.equals(rfd_code_type))  ||
               (rfd_code_type != null && !rfd_code_type.equals(this.rfd_code_type)) ){
         this.rfd_code_type_changed = true; 
         this.record_changed = true;
         this.rfd_code_type = rfd_code_type;
      }
   }
   public String getRfd_code_type() {
      return this.rfd_code_type;
   }
   public void setRfd_code_length(Double rfd_code_length) {
      if (this.isForceUpdate() || 
               (this.rfd_code_length != null && !this.rfd_code_length.equals(rfd_code_length))  ||
               (rfd_code_length != null && !rfd_code_length.equals(this.rfd_code_length)) ){
         this.rfd_code_length_changed = true; 
         this.record_changed = true;
         this.rfd_code_length = rfd_code_length;
      }
   }
   public Double getRfd_code_length() {
      return this.rfd_code_length;
   }
   public void setRfd_code_colname(String rfd_code_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_code_colname != null && !this.rfd_code_colname.equals(rfd_code_colname))  ||
               (rfd_code_colname != null && !rfd_code_colname.equals(this.rfd_code_colname)) ){
         this.rfd_code_colname_changed = true; 
         this.record_changed = true;
         this.rfd_code_colname = rfd_code_colname;
      }
   }
   public String getRfd_code_colname() {
      return this.rfd_code_colname;
   }
   public void setRfd_desc_colname(String rfd_desc_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_desc_colname != null && !this.rfd_desc_colname.equals(rfd_desc_colname))  ||
               (rfd_desc_colname != null && !rfd_desc_colname.equals(this.rfd_desc_colname)) ){
         this.rfd_desc_colname_changed = true; 
         this.record_changed = true;
         this.rfd_desc_colname = rfd_desc_colname;
      }
   }
   public String getRfd_desc_colname() {
      return this.rfd_desc_colname;
   }
   public void setRfd_ref_domain_1(String rfd_ref_domain_1) {
      if (this.isForceUpdate() || 
               (this.rfd_ref_domain_1 != null && !this.rfd_ref_domain_1.equals(rfd_ref_domain_1))  ||
               (rfd_ref_domain_1 != null && !rfd_ref_domain_1.equals(this.rfd_ref_domain_1)) ){
         this.rfd_ref_domain_1_changed = true; 
         this.record_changed = true;
         this.rfd_ref_domain_1 = rfd_ref_domain_1;
      }
   }
   public String getRfd_ref_domain_1() {
      return this.rfd_ref_domain_1;
   }
   public void setRfd_ref_domain_2(String rfd_ref_domain_2) {
      if (this.isForceUpdate() || 
               (this.rfd_ref_domain_2 != null && !this.rfd_ref_domain_2.equals(rfd_ref_domain_2))  ||
               (rfd_ref_domain_2 != null && !rfd_ref_domain_2.equals(this.rfd_ref_domain_2)) ){
         this.rfd_ref_domain_2_changed = true; 
         this.record_changed = true;
         this.rfd_ref_domain_2 = rfd_ref_domain_2;
      }
   }
   public String getRfd_ref_domain_2() {
      return this.rfd_ref_domain_2;
   }
   public void setRfd_ref_domain_3(String rfd_ref_domain_3) {
      if (this.isForceUpdate() || 
               (this.rfd_ref_domain_3 != null && !this.rfd_ref_domain_3.equals(rfd_ref_domain_3))  ||
               (rfd_ref_domain_3 != null && !rfd_ref_domain_3.equals(this.rfd_ref_domain_3)) ){
         this.rfd_ref_domain_3_changed = true; 
         this.record_changed = true;
         this.rfd_ref_domain_3 = rfd_ref_domain_3;
      }
   }
   public String getRfd_ref_domain_3() {
      return this.rfd_ref_domain_3;
   }
   public void setRfd_ref_domain_4(String rfd_ref_domain_4) {
      if (this.isForceUpdate() || 
               (this.rfd_ref_domain_4 != null && !this.rfd_ref_domain_4.equals(rfd_ref_domain_4))  ||
               (rfd_ref_domain_4 != null && !rfd_ref_domain_4.equals(this.rfd_ref_domain_4)) ){
         this.rfd_ref_domain_4_changed = true; 
         this.record_changed = true;
         this.rfd_ref_domain_4 = rfd_ref_domain_4;
      }
   }
   public String getRfd_ref_domain_4() {
      return this.rfd_ref_domain_4;
   }
   public void setRfd_ref_domain_5(String rfd_ref_domain_5) {
      if (this.isForceUpdate() || 
               (this.rfd_ref_domain_5 != null && !this.rfd_ref_domain_5.equals(rfd_ref_domain_5))  ||
               (rfd_ref_domain_5 != null && !rfd_ref_domain_5.equals(this.rfd_ref_domain_5)) ){
         this.rfd_ref_domain_5_changed = true; 
         this.record_changed = true;
         this.rfd_ref_domain_5 = rfd_ref_domain_5;
      }
   }
   public String getRfd_ref_domain_5() {
      return this.rfd_ref_domain_5;
   }
   public void setRfd_n1_colname(String rfd_n1_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_n1_colname != null && !this.rfd_n1_colname.equals(rfd_n1_colname))  ||
               (rfd_n1_colname != null && !rfd_n1_colname.equals(this.rfd_n1_colname)) ){
         this.rfd_n1_colname_changed = true; 
         this.record_changed = true;
         this.rfd_n1_colname = rfd_n1_colname;
      }
   }
   public String getRfd_n1_colname() {
      return this.rfd_n1_colname;
   }
   public void setRfd_n2_colname(String rfd_n2_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_n2_colname != null && !this.rfd_n2_colname.equals(rfd_n2_colname))  ||
               (rfd_n2_colname != null && !rfd_n2_colname.equals(this.rfd_n2_colname)) ){
         this.rfd_n2_colname_changed = true; 
         this.record_changed = true;
         this.rfd_n2_colname = rfd_n2_colname;
      }
   }
   public String getRfd_n2_colname() {
      return this.rfd_n2_colname;
   }
   public void setRfd_n3_colname(String rfd_n3_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_n3_colname != null && !this.rfd_n3_colname.equals(rfd_n3_colname))  ||
               (rfd_n3_colname != null && !rfd_n3_colname.equals(this.rfd_n3_colname)) ){
         this.rfd_n3_colname_changed = true; 
         this.record_changed = true;
         this.rfd_n3_colname = rfd_n3_colname;
      }
   }
   public String getRfd_n3_colname() {
      return this.rfd_n3_colname;
   }
   public void setRfd_n4_colname(String rfd_n4_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_n4_colname != null && !this.rfd_n4_colname.equals(rfd_n4_colname))  ||
               (rfd_n4_colname != null && !rfd_n4_colname.equals(this.rfd_n4_colname)) ){
         this.rfd_n4_colname_changed = true; 
         this.record_changed = true;
         this.rfd_n4_colname = rfd_n4_colname;
      }
   }
   public String getRfd_n4_colname() {
      return this.rfd_n4_colname;
   }
   public void setRfd_n5_colname(String rfd_n5_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_n5_colname != null && !this.rfd_n5_colname.equals(rfd_n5_colname))  ||
               (rfd_n5_colname != null && !rfd_n5_colname.equals(this.rfd_n5_colname)) ){
         this.rfd_n5_colname_changed = true; 
         this.record_changed = true;
         this.rfd_n5_colname = rfd_n5_colname;
      }
   }
   public String getRfd_n5_colname() {
      return this.rfd_n5_colname;
   }
   public void setRfd_c1_colname(String rfd_c1_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_c1_colname != null && !this.rfd_c1_colname.equals(rfd_c1_colname))  ||
               (rfd_c1_colname != null && !rfd_c1_colname.equals(this.rfd_c1_colname)) ){
         this.rfd_c1_colname_changed = true; 
         this.record_changed = true;
         this.rfd_c1_colname = rfd_c1_colname;
      }
   }
   public String getRfd_c1_colname() {
      return this.rfd_c1_colname;
   }
   public void setRfd_c2_colname(String rfd_c2_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_c2_colname != null && !this.rfd_c2_colname.equals(rfd_c2_colname))  ||
               (rfd_c2_colname != null && !rfd_c2_colname.equals(this.rfd_c2_colname)) ){
         this.rfd_c2_colname_changed = true; 
         this.record_changed = true;
         this.rfd_c2_colname = rfd_c2_colname;
      }
   }
   public String getRfd_c2_colname() {
      return this.rfd_c2_colname;
   }
   public void setRfd_c3_colname(String rfd_c3_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_c3_colname != null && !this.rfd_c3_colname.equals(rfd_c3_colname))  ||
               (rfd_c3_colname != null && !rfd_c3_colname.equals(this.rfd_c3_colname)) ){
         this.rfd_c3_colname_changed = true; 
         this.record_changed = true;
         this.rfd_c3_colname = rfd_c3_colname;
      }
   }
   public String getRfd_c3_colname() {
      return this.rfd_c3_colname;
   }
   public void setRfd_c4_colname(String rfd_c4_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_c4_colname != null && !this.rfd_c4_colname.equals(rfd_c4_colname))  ||
               (rfd_c4_colname != null && !rfd_c4_colname.equals(this.rfd_c4_colname)) ){
         this.rfd_c4_colname_changed = true; 
         this.record_changed = true;
         this.rfd_c4_colname = rfd_c4_colname;
      }
   }
   public String getRfd_c4_colname() {
      return this.rfd_c4_colname;
   }
   public void setRfd_c5_colname(String rfd_c5_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_c5_colname != null && !this.rfd_c5_colname.equals(rfd_c5_colname))  ||
               (rfd_c5_colname != null && !rfd_c5_colname.equals(this.rfd_c5_colname)) ){
         this.rfd_c5_colname_changed = true; 
         this.record_changed = true;
         this.rfd_c5_colname = rfd_c5_colname;
      }
   }
   public String getRfd_c5_colname() {
      return this.rfd_c5_colname;
   }
   public void setRfd_d1_colname(String rfd_d1_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_d1_colname != null && !this.rfd_d1_colname.equals(rfd_d1_colname))  ||
               (rfd_d1_colname != null && !rfd_d1_colname.equals(this.rfd_d1_colname)) ){
         this.rfd_d1_colname_changed = true; 
         this.record_changed = true;
         this.rfd_d1_colname = rfd_d1_colname;
      }
   }
   public String getRfd_d1_colname() {
      return this.rfd_d1_colname;
   }
   public void setRfd_d2_colname(String rfd_d2_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_d2_colname != null && !this.rfd_d2_colname.equals(rfd_d2_colname))  ||
               (rfd_d2_colname != null && !rfd_d2_colname.equals(this.rfd_d2_colname)) ){
         this.rfd_d2_colname_changed = true; 
         this.record_changed = true;
         this.rfd_d2_colname = rfd_d2_colname;
      }
   }
   public String getRfd_d2_colname() {
      return this.rfd_d2_colname;
   }
   public void setRfd_d3_colname(String rfd_d3_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_d3_colname != null && !this.rfd_d3_colname.equals(rfd_d3_colname))  ||
               (rfd_d3_colname != null && !rfd_d3_colname.equals(this.rfd_d3_colname)) ){
         this.rfd_d3_colname_changed = true; 
         this.record_changed = true;
         this.rfd_d3_colname = rfd_d3_colname;
      }
   }
   public String getRfd_d3_colname() {
      return this.rfd_d3_colname;
   }
   public void setRfd_d4_colname(String rfd_d4_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_d4_colname != null && !this.rfd_d4_colname.equals(rfd_d4_colname))  ||
               (rfd_d4_colname != null && !rfd_d4_colname.equals(this.rfd_d4_colname)) ){
         this.rfd_d4_colname_changed = true; 
         this.record_changed = true;
         this.rfd_d4_colname = rfd_d4_colname;
      }
   }
   public String getRfd_d4_colname() {
      return this.rfd_d4_colname;
   }
   public void setRfd_d5_colname(String rfd_d5_colname) {
      if (this.isForceUpdate() || 
               (this.rfd_d5_colname != null && !this.rfd_d5_colname.equals(rfd_d5_colname))  ||
               (rfd_d5_colname != null && !rfd_d5_colname.equals(this.rfd_d5_colname)) ){
         this.rfd_d5_colname_changed = true; 
         this.record_changed = true;
         this.rfd_d5_colname = rfd_d5_colname;
      }
   }
   public String getRfd_d5_colname() {
      return this.rfd_d5_colname;
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
            this.rfd_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.rfd_id;
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
      if (rfd_subsystem_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_subsystem== null || EMPTY_STRING.equals(rfd_subsystem)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_SUBSYSTEM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfd_subsystem!= null && rfd_subsystem.length()>20) {
               throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_SUBSYSTEM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
            }
         }
      }
      if (rfd_table_name_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_table_name== null || EMPTY_STRING.equals(rfd_table_name)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_TABLE_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfd_table_name!= null && rfd_table_name.length()>100) {
               throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_TABLE_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
            }
         }
      }
      if (rfd_name_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_name== null || EMPTY_STRING.equals(rfd_name)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfd_name!= null && rfd_name.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (rfd_description_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_description!= null && rfd_description.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_code_type_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_code_type== null || EMPTY_STRING.equals(rfd_code_type)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_CODE_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfd_code_type!= null && rfd_code_type.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_CODE_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (rfd_code_length_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_code_length!= null && (""+rfd_code_length.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_CODE_LENGTH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rfd_code_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_code_colname!= null && rfd_code_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_CODE_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_desc_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_desc_colname!= null && rfd_desc_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_DESC_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_ref_domain_1_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_ref_domain_1!= null && rfd_ref_domain_1.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_REF_DOMAIN_1", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfd_ref_domain_2_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_ref_domain_2!= null && rfd_ref_domain_2.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_REF_DOMAIN_2", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfd_ref_domain_3_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_ref_domain_3!= null && rfd_ref_domain_3.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_REF_DOMAIN_3", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfd_ref_domain_4_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_ref_domain_4!= null && rfd_ref_domain_4.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_REF_DOMAIN_4", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfd_ref_domain_5_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_ref_domain_5!= null && rfd_ref_domain_5.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_REF_DOMAIN_5", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfd_n1_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_n1_colname!= null && rfd_n1_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_N1_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_n2_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_n2_colname!= null && rfd_n2_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_N2_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_n3_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_n3_colname!= null && rfd_n3_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_N3_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_n4_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_n4_colname!= null && rfd_n4_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_N4_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_n5_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_n5_colname!= null && rfd_n5_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_N5_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_c1_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_c1_colname!= null && rfd_c1_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_C1_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_c2_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_c2_colname!= null && rfd_c2_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_C2_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_c3_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_c3_colname!= null && rfd_c3_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_C3_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_c4_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_c4_colname!= null && rfd_c4_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_C4_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_c5_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_c5_colname!= null && rfd_c5_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_C5_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_d1_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_d1_colname!= null && rfd_d1_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_D1_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_d2_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_d2_colname!= null && rfd_d2_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_D2_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_d3_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_d3_colname!= null && rfd_d3_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_D3_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_d4_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_d4_colname!= null && rfd_d4_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_D4_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rfd_d5_colname_changed || operation == Utils.OPERATION_INSERT) {
         if (rfd_d5_colname!= null && rfd_d5_colname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "RFD_D5_COLNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_DICTIONARIES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_REF_DICTIONARIES SET ";
      boolean changedExists = false;      if (rfd_subsystem_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_SUBSYSTEM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_subsystem);
      }
      if (rfd_table_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_TABLE_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_table_name);
      }
      if (rfd_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_name);
      }
      if (rfd_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_description);
      }
      if (rfd_code_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_CODE_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_code_type);
      }
      if (rfd_code_length_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_CODE_LENGTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_code_length);
      }
      if (rfd_code_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_CODE_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_code_colname);
      }
      if (rfd_desc_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_DESC_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_desc_colname);
      }
      if (rfd_ref_domain_1_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_REF_DOMAIN_1 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_ref_domain_1);
      }
      if (rfd_ref_domain_2_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_REF_DOMAIN_2 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_ref_domain_2);
      }
      if (rfd_ref_domain_3_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_REF_DOMAIN_3 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_ref_domain_3);
      }
      if (rfd_ref_domain_4_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_REF_DOMAIN_4 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_ref_domain_4);
      }
      if (rfd_ref_domain_5_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_REF_DOMAIN_5 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_ref_domain_5);
      }
      if (rfd_n1_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_N1_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_n1_colname);
      }
      if (rfd_n2_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_N2_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_n2_colname);
      }
      if (rfd_n3_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_N3_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_n3_colname);
      }
      if (rfd_n4_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_N4_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_n4_colname);
      }
      if (rfd_n5_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_N5_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_n5_colname);
      }
      if (rfd_c1_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_C1_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_c1_colname);
      }
      if (rfd_c2_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_C2_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_c2_colname);
      }
      if (rfd_c3_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_C3_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_c3_colname);
      }
      if (rfd_c4_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_C4_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_c4_colname);
      }
      if (rfd_c5_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_C5_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_c5_colname);
      }
      if (rfd_d1_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_D1_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_d1_colname);
      }
      if (rfd_d2_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_D2_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_d2_colname);
      }
      if (rfd_d3_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_D3_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_d3_colname);
      }
      if (rfd_d4_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_D4_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_d4_colname);
      }
      if (rfd_d5_colname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFD_D5_COLNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfd_d5_colname);
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
      answer = answer +" WHERE  RFD_ID = ? ";
      updateStatementPart.addStatementParam(rfd_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprRefDictionariesDAO\":{\"rfd_id\": \""+getRfd_id()+"\", \"rfd_subsystem\": \""+getRfd_subsystem()+"\", \"rfd_table_name\": \""+getRfd_table_name()+"\", \"rfd_name\": \""+getRfd_name()+"\", \"rfd_description\": \""+getRfd_description()+"\", \"rfd_code_type\": \""+getRfd_code_type()+"\", \"rfd_code_length\": \""+getRfd_code_length()+"\", \"rfd_code_colname\": \""+getRfd_code_colname()+"\", \"rfd_desc_colname\": \""+getRfd_desc_colname()+"\", \"rfd_ref_domain_1\": \""+getRfd_ref_domain_1()+"\", \"rfd_ref_domain_2\": \""+getRfd_ref_domain_2()+"\", \"rfd_ref_domain_3\": \""+getRfd_ref_domain_3()+"\", \"rfd_ref_domain_4\": \""+getRfd_ref_domain_4()+"\", \"rfd_ref_domain_5\": \""+getRfd_ref_domain_5()+"\", \"rfd_n1_colname\": \""+getRfd_n1_colname()+"\", \"rfd_n2_colname\": \""+getRfd_n2_colname()+"\", \"rfd_n3_colname\": \""+getRfd_n3_colname()+"\", \"rfd_n4_colname\": \""+getRfd_n4_colname()+"\", \"rfd_n5_colname\": \""+getRfd_n5_colname()+"\", \"rfd_c1_colname\": \""+getRfd_c1_colname()+"\", \"rfd_c2_colname\": \""+getRfd_c2_colname()+"\", \"rfd_c3_colname\": \""+getRfd_c3_colname()+"\", \"rfd_c4_colname\": \""+getRfd_c4_colname()+"\", \"rfd_c5_colname\": \""+getRfd_c5_colname()+"\", \"rfd_d1_colname\": \""+getRfd_d1_colname()+"\", \"rfd_d2_colname\": \""+getRfd_d2_colname()+"\", \"rfd_d3_colname\": \""+getRfd_d3_colname()+"\", \"rfd_d4_colname\": \""+getRfd_d4_colname()+"\", \"rfd_d5_colname\": \""+getRfd_d5_colname()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}