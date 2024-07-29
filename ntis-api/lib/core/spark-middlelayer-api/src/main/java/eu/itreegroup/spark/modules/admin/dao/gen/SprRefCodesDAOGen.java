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

public class SprRefCodesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_REF_CODES.RFC_ID";
   private Double rfc_id;
   private String rfc_domain;
   private String rfc_code;
   private String rfc_meaning;
   private String rfc_description;
   private String rfc_parent_domain;
   private String rfc_parent_domain_code;
   private Double rfc_order;
   private Date rfc_date_from;
   private Date rfc_date_to;
   private Double rfc_rfd_id;
   private String rfc_ref_code_1;
   private String rfc_ref_code_2;
   private String rfc_ref_code_3;
   private String rfc_ref_code_4;
   private String rfc_ref_code_5;
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

   protected boolean rfc_id_changed;
   protected boolean rfc_domain_changed;
   protected boolean rfc_code_changed;
   protected boolean rfc_meaning_changed;
   protected boolean rfc_description_changed;
   protected boolean rfc_parent_domain_changed;
   protected boolean rfc_parent_domain_code_changed;
   protected boolean rfc_order_changed;
   protected boolean rfc_date_from_changed;
   protected boolean rfc_date_to_changed;
   protected boolean rfc_rfd_id_changed;
   protected boolean rfc_ref_code_1_changed;
   protected boolean rfc_ref_code_2_changed;
   protected boolean rfc_ref_code_3_changed;
   protected boolean rfc_ref_code_4_changed;
   protected boolean rfc_ref_code_5_changed;
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
   public SprRefCodesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprRefCodesDAOGen(Double rfc_id, String rfc_domain, String rfc_code, String rfc_meaning, String rfc_description, String rfc_parent_domain, String rfc_parent_domain_code, Double rfc_order, Date rfc_date_from, Date rfc_date_to, Double rfc_rfd_id, String rfc_ref_code_1, String rfc_ref_code_2, String rfc_ref_code_3, String rfc_ref_code_4, String rfc_ref_code_5, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.rfc_id = rfc_id;
      this.rfc_domain = rfc_domain;
      this.rfc_code = rfc_code;
      this.rfc_meaning = rfc_meaning;
      this.rfc_description = rfc_description;
      this.rfc_parent_domain = rfc_parent_domain;
      this.rfc_parent_domain_code = rfc_parent_domain_code;
      this.rfc_order = rfc_order;
      this.rfc_date_from = rfc_date_from;
      this.rfc_date_to = rfc_date_to;
      this.rfc_rfd_id = rfc_rfd_id;
      this.rfc_ref_code_1 = rfc_ref_code_1;
      this.rfc_ref_code_2 = rfc_ref_code_2;
      this.rfc_ref_code_3 = rfc_ref_code_3;
      this.rfc_ref_code_4 = rfc_ref_code_4;
      this.rfc_ref_code_5 = rfc_ref_code_5;
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
   public void copyValues(SprRefCodesDAOGen obj) {
      this.setRfc_id(obj.getRfc_id());
      this.setRfc_domain(obj.getRfc_domain());
      this.setRfc_code(obj.getRfc_code());
      this.setRfc_meaning(obj.getRfc_meaning());
      this.setRfc_description(obj.getRfc_description());
      this.setRfc_parent_domain(obj.getRfc_parent_domain());
      this.setRfc_parent_domain_code(obj.getRfc_parent_domain_code());
      this.setRfc_order(obj.getRfc_order());
      this.setRfc_date_from(obj.getRfc_date_from());
      this.setRfc_date_to(obj.getRfc_date_to());
      this.setRfc_rfd_id(obj.getRfc_rfd_id());
      this.setRfc_ref_code_1(obj.getRfc_ref_code_1());
      this.setRfc_ref_code_2(obj.getRfc_ref_code_2());
      this.setRfc_ref_code_3(obj.getRfc_ref_code_3());
      this.setRfc_ref_code_4(obj.getRfc_ref_code_4());
      this.setRfc_ref_code_5(obj.getRfc_ref_code_5());
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
      this.rfc_id_changed = false;
      this.rfc_domain_changed = false;
      this.rfc_code_changed = false;
      this.rfc_meaning_changed = false;
      this.rfc_description_changed = false;
      this.rfc_parent_domain_changed = false;
      this.rfc_parent_domain_code_changed = false;
      this.rfc_order_changed = false;
      this.rfc_date_from_changed = false;
      this.rfc_date_to_changed = false;
      this.rfc_rfd_id_changed = false;
      this.rfc_ref_code_1_changed = false;
      this.rfc_ref_code_2_changed = false;
      this.rfc_ref_code_3_changed = false;
      this.rfc_ref_code_4_changed = false;
      this.rfc_ref_code_5_changed = false;
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
   public void setRfc_id(Double rfc_id) {
      if (this.isForceUpdate() || 
               (this.rfc_id != null && !this.rfc_id.equals(rfc_id))  ||
               (rfc_id != null && !rfc_id.equals(this.rfc_id)) ){
         this.rfc_id_changed = true; 
         this.record_changed = true;
         this.rfc_id = rfc_id;
      }
   }
   public Double getRfc_id() {
      return this.rfc_id;
   }
   public void setRfc_domain(String rfc_domain) {
      if (this.isForceUpdate() || 
               (this.rfc_domain != null && !this.rfc_domain.equals(rfc_domain))  ||
               (rfc_domain != null && !rfc_domain.equals(this.rfc_domain)) ){
         this.rfc_domain_changed = true; 
         this.record_changed = true;
         this.rfc_domain = rfc_domain;
      }
   }
   public String getRfc_domain() {
      return this.rfc_domain;
   }
   public void setRfc_code(String rfc_code) {
      if (this.isForceUpdate() || 
               (this.rfc_code != null && !this.rfc_code.equals(rfc_code))  ||
               (rfc_code != null && !rfc_code.equals(this.rfc_code)) ){
         this.rfc_code_changed = true; 
         this.record_changed = true;
         this.rfc_code = rfc_code;
      }
   }
   public String getRfc_code() {
      return this.rfc_code;
   }
   public void setRfc_meaning(String rfc_meaning) {
      if (this.isForceUpdate() || 
               (this.rfc_meaning != null && !this.rfc_meaning.equals(rfc_meaning))  ||
               (rfc_meaning != null && !rfc_meaning.equals(this.rfc_meaning)) ){
         this.rfc_meaning_changed = true; 
         this.record_changed = true;
         this.rfc_meaning = rfc_meaning;
      }
   }
   public String getRfc_meaning() {
      return this.rfc_meaning;
   }
   public void setRfc_description(String rfc_description) {
      if (this.isForceUpdate() || 
               (this.rfc_description != null && !this.rfc_description.equals(rfc_description))  ||
               (rfc_description != null && !rfc_description.equals(this.rfc_description)) ){
         this.rfc_description_changed = true; 
         this.record_changed = true;
         this.rfc_description = rfc_description;
      }
   }
   public String getRfc_description() {
      return this.rfc_description;
   }
   public void setRfc_parent_domain(String rfc_parent_domain) {
      if (this.isForceUpdate() || 
               (this.rfc_parent_domain != null && !this.rfc_parent_domain.equals(rfc_parent_domain))  ||
               (rfc_parent_domain != null && !rfc_parent_domain.equals(this.rfc_parent_domain)) ){
         this.rfc_parent_domain_changed = true; 
         this.record_changed = true;
         this.rfc_parent_domain = rfc_parent_domain;
      }
   }
   public String getRfc_parent_domain() {
      return this.rfc_parent_domain;
   }
   public void setRfc_parent_domain_code(String rfc_parent_domain_code) {
      if (this.isForceUpdate() || 
               (this.rfc_parent_domain_code != null && !this.rfc_parent_domain_code.equals(rfc_parent_domain_code))  ||
               (rfc_parent_domain_code != null && !rfc_parent_domain_code.equals(this.rfc_parent_domain_code)) ){
         this.rfc_parent_domain_code_changed = true; 
         this.record_changed = true;
         this.rfc_parent_domain_code = rfc_parent_domain_code;
      }
   }
   public String getRfc_parent_domain_code() {
      return this.rfc_parent_domain_code;
   }
   public void setRfc_order(Double rfc_order) {
      if (this.isForceUpdate() || 
               (this.rfc_order != null && !this.rfc_order.equals(rfc_order))  ||
               (rfc_order != null && !rfc_order.equals(this.rfc_order)) ){
         this.rfc_order_changed = true; 
         this.record_changed = true;
         this.rfc_order = rfc_order;
      }
   }
   public Double getRfc_order() {
      return this.rfc_order;
   }
   public void setRfc_date_from(Date rfc_date_from) {
      if (this.isForceUpdate() || 
               (this.rfc_date_from != null && (rfc_date_from == null ||this.rfc_date_from.compareTo(rfc_date_from) != 0 )) ||
               (rfc_date_from != null && (this.rfc_date_from == null ||rfc_date_from.compareTo(this.rfc_date_from) != 0 ))){
         this.rfc_date_from_changed = true; 
         this.record_changed = true;
         this.rfc_date_from = rfc_date_from;
      }
   }
   public Date getRfc_date_from() {
      return this.rfc_date_from;
   }
   public void setRfc_date_to(Date rfc_date_to) {
      if (this.isForceUpdate() || 
               (this.rfc_date_to != null && (rfc_date_to == null ||this.rfc_date_to.compareTo(rfc_date_to) != 0 )) ||
               (rfc_date_to != null && (this.rfc_date_to == null ||rfc_date_to.compareTo(this.rfc_date_to) != 0 ))){
         this.rfc_date_to_changed = true; 
         this.record_changed = true;
         this.rfc_date_to = rfc_date_to;
      }
   }
   public Date getRfc_date_to() {
      return this.rfc_date_to;
   }
   public void setRfc_rfd_id(Double rfc_rfd_id) {
      if (this.isForceUpdate() || 
               (this.rfc_rfd_id != null && !this.rfc_rfd_id.equals(rfc_rfd_id))  ||
               (rfc_rfd_id != null && !rfc_rfd_id.equals(this.rfc_rfd_id)) ){
         this.rfc_rfd_id_changed = true; 
         this.record_changed = true;
         this.rfc_rfd_id = rfc_rfd_id;
      }
   }
   public Double getRfc_rfd_id() {
      return this.rfc_rfd_id;
   }
   public void setRfc_ref_code_1(String rfc_ref_code_1) {
      if (this.isForceUpdate() || 
               (this.rfc_ref_code_1 != null && !this.rfc_ref_code_1.equals(rfc_ref_code_1))  ||
               (rfc_ref_code_1 != null && !rfc_ref_code_1.equals(this.rfc_ref_code_1)) ){
         this.rfc_ref_code_1_changed = true; 
         this.record_changed = true;
         this.rfc_ref_code_1 = rfc_ref_code_1;
      }
   }
   public String getRfc_ref_code_1() {
      return this.rfc_ref_code_1;
   }
   public void setRfc_ref_code_2(String rfc_ref_code_2) {
      if (this.isForceUpdate() || 
               (this.rfc_ref_code_2 != null && !this.rfc_ref_code_2.equals(rfc_ref_code_2))  ||
               (rfc_ref_code_2 != null && !rfc_ref_code_2.equals(this.rfc_ref_code_2)) ){
         this.rfc_ref_code_2_changed = true; 
         this.record_changed = true;
         this.rfc_ref_code_2 = rfc_ref_code_2;
      }
   }
   public String getRfc_ref_code_2() {
      return this.rfc_ref_code_2;
   }
   public void setRfc_ref_code_3(String rfc_ref_code_3) {
      if (this.isForceUpdate() || 
               (this.rfc_ref_code_3 != null && !this.rfc_ref_code_3.equals(rfc_ref_code_3))  ||
               (rfc_ref_code_3 != null && !rfc_ref_code_3.equals(this.rfc_ref_code_3)) ){
         this.rfc_ref_code_3_changed = true; 
         this.record_changed = true;
         this.rfc_ref_code_3 = rfc_ref_code_3;
      }
   }
   public String getRfc_ref_code_3() {
      return this.rfc_ref_code_3;
   }
   public void setRfc_ref_code_4(String rfc_ref_code_4) {
      if (this.isForceUpdate() || 
               (this.rfc_ref_code_4 != null && !this.rfc_ref_code_4.equals(rfc_ref_code_4))  ||
               (rfc_ref_code_4 != null && !rfc_ref_code_4.equals(this.rfc_ref_code_4)) ){
         this.rfc_ref_code_4_changed = true; 
         this.record_changed = true;
         this.rfc_ref_code_4 = rfc_ref_code_4;
      }
   }
   public String getRfc_ref_code_4() {
      return this.rfc_ref_code_4;
   }
   public void setRfc_ref_code_5(String rfc_ref_code_5) {
      if (this.isForceUpdate() || 
               (this.rfc_ref_code_5 != null && !this.rfc_ref_code_5.equals(rfc_ref_code_5))  ||
               (rfc_ref_code_5 != null && !rfc_ref_code_5.equals(this.rfc_ref_code_5)) ){
         this.rfc_ref_code_5_changed = true; 
         this.record_changed = true;
         this.rfc_ref_code_5 = rfc_ref_code_5;
      }
   }
   public String getRfc_ref_code_5() {
      return this.rfc_ref_code_5;
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
            this.rfc_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.rfc_id;
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
      if (rfc_domain_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_domain== null || EMPTY_STRING.equals(rfc_domain)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_DOMAIN", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfc_domain!= null && rfc_domain.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_DOMAIN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (rfc_code_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_code== null || EMPTY_STRING.equals(rfc_code)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfc_code!= null && rfc_code.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (rfc_meaning_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_meaning== null || EMPTY_STRING.equals(rfc_meaning)) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_MEANING", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (rfc_meaning!= null && rfc_meaning.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_MEANING", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (rfc_description_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_description!= null && rfc_description.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (rfc_parent_domain_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_parent_domain!= null && rfc_parent_domain.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_PARENT_DOMAIN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfc_parent_domain_code_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_parent_domain_code!= null && rfc_parent_domain_code.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_PARENT_DOMAIN_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfc_order_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_order!= null && (""+rfc_order.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_ORDER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rfc_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_date_from== null) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (rfc_rfd_id_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_rfd_id!= null && (""+rfc_rfd_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_RFD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rfc_ref_code_1_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_ref_code_1!= null && rfc_ref_code_1.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_REF_CODE_1", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfc_ref_code_2_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_ref_code_2!= null && rfc_ref_code_2.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_REF_CODE_2", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfc_ref_code_3_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_ref_code_3!= null && rfc_ref_code_3.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_REF_CODE_3", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfc_ref_code_4_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_ref_code_4!= null && rfc_ref_code_4.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_REF_CODE_4", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rfc_ref_code_5_changed || operation == Utils.OPERATION_INSERT) {
         if (rfc_ref_code_5!= null && rfc_ref_code_5.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "RFC_REF_CODE_5", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_REF_CODES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_REF_CODES SET ";
      boolean changedExists = false;      if (rfc_domain_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_DOMAIN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_domain);
      }
      if (rfc_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_code);
      }
      if (rfc_meaning_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_MEANING = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_meaning);
      }
      if (rfc_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_description);
      }
      if (rfc_parent_domain_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_PARENT_DOMAIN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_parent_domain);
      }
      if (rfc_parent_domain_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_PARENT_DOMAIN_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_parent_domain_code);
      }
      if (rfc_order_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_ORDER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_order);
      }
      if (rfc_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_date_from);
      }
      if (rfc_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_date_to);
      }
      if (rfc_rfd_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_RFD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_rfd_id);
      }
      if (rfc_ref_code_1_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_REF_CODE_1 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_ref_code_1);
      }
      if (rfc_ref_code_2_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_REF_CODE_2 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_ref_code_2);
      }
      if (rfc_ref_code_3_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_REF_CODE_3 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_ref_code_3);
      }
      if (rfc_ref_code_4_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_REF_CODE_4 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_ref_code_4);
      }
      if (rfc_ref_code_5_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RFC_REF_CODE_5 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rfc_ref_code_5);
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
      answer = answer +" WHERE  RFC_ID = ? ";
      updateStatementPart.addStatementParam(rfc_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprRefCodesDAO\":{\"rfc_id\": \""+getRfc_id()+"\", \"rfc_domain\": \""+getRfc_domain()+"\", \"rfc_code\": \""+getRfc_code()+"\", \"rfc_meaning\": \""+getRfc_meaning()+"\", \"rfc_description\": \""+getRfc_description()+"\", \"rfc_parent_domain\": \""+getRfc_parent_domain()+"\", \"rfc_parent_domain_code\": \""+getRfc_parent_domain_code()+"\", \"rfc_order\": \""+getRfc_order()+"\", \"rfc_date_from\": \""+getRfc_date_from()+"\", \"rfc_date_to\": \""+getRfc_date_to()+"\", \"rfc_rfd_id\": \""+getRfc_rfd_id()+"\", \"rfc_ref_code_1\": \""+getRfc_ref_code_1()+"\", \"rfc_ref_code_2\": \""+getRfc_ref_code_2()+"\", \"rfc_ref_code_3\": \""+getRfc_ref_code_3()+"\", \"rfc_ref_code_4\": \""+getRfc_ref_code_4()+"\", \"rfc_ref_code_5\": \""+getRfc_ref_code_5()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}