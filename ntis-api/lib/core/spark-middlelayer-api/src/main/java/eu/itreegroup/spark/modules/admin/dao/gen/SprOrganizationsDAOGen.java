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

public class SprOrganizationsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_ORGANIZATIONS.ORG_ID";
   private Double org_id;
   private String org_name;
   private String org_code;
   private String org_type;
   private String org_region;
   private String org_phone;
   private String org_email;
   private String org_website;
   private String org_address;
   private String org_house_number;
   private String org_bank_account;
   private String org_bank_name;
   private String org_delegation_person;
   private String org_delegation_person_position;
   private Date org_date_from;
   private Date org_date_to;
   private Double org_org_id;
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

   protected boolean org_id_changed;
   protected boolean org_name_changed;
   protected boolean org_code_changed;
   protected boolean org_type_changed;
   protected boolean org_region_changed;
   protected boolean org_phone_changed;
   protected boolean org_email_changed;
   protected boolean org_website_changed;
   protected boolean org_address_changed;
   protected boolean org_house_number_changed;
   protected boolean org_bank_account_changed;
   protected boolean org_bank_name_changed;
   protected boolean org_delegation_person_changed;
   protected boolean org_delegation_person_position_changed;
   protected boolean org_date_from_changed;
   protected boolean org_date_to_changed;
   protected boolean org_org_id_changed;
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
   public SprOrganizationsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprOrganizationsDAOGen(Double org_id, String org_name, String org_code, String org_type, String org_region, String org_phone, String org_email, String org_website, String org_address, String org_house_number, String org_bank_account, String org_bank_name, String org_delegation_person, String org_delegation_person_position, Date org_date_from, Date org_date_to, Double org_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.org_id = org_id;
      this.org_name = org_name;
      this.org_code = org_code;
      this.org_type = org_type;
      this.org_region = org_region;
      this.org_phone = org_phone;
      this.org_email = org_email;
      this.org_website = org_website;
      this.org_address = org_address;
      this.org_house_number = org_house_number;
      this.org_bank_account = org_bank_account;
      this.org_bank_name = org_bank_name;
      this.org_delegation_person = org_delegation_person;
      this.org_delegation_person_position = org_delegation_person_position;
      this.org_date_from = org_date_from;
      this.org_date_to = org_date_to;
      this.org_org_id = org_org_id;
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
   public void copyValues(SprOrganizationsDAOGen obj) {
      this.setOrg_id(obj.getOrg_id());
      this.setOrg_name(obj.getOrg_name());
      this.setOrg_code(obj.getOrg_code());
      this.setOrg_type(obj.getOrg_type());
      this.setOrg_region(obj.getOrg_region());
      this.setOrg_phone(obj.getOrg_phone());
      this.setOrg_email(obj.getOrg_email());
      this.setOrg_website(obj.getOrg_website());
      this.setOrg_address(obj.getOrg_address());
      this.setOrg_house_number(obj.getOrg_house_number());
      this.setOrg_bank_account(obj.getOrg_bank_account());
      this.setOrg_bank_name(obj.getOrg_bank_name());
      this.setOrg_delegation_person(obj.getOrg_delegation_person());
      this.setOrg_delegation_person_position(obj.getOrg_delegation_person_position());
      this.setOrg_date_from(obj.getOrg_date_from());
      this.setOrg_date_to(obj.getOrg_date_to());
      this.setOrg_org_id(obj.getOrg_org_id());
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
      this.org_id_changed = false;
      this.org_name_changed = false;
      this.org_code_changed = false;
      this.org_type_changed = false;
      this.org_region_changed = false;
      this.org_phone_changed = false;
      this.org_email_changed = false;
      this.org_website_changed = false;
      this.org_address_changed = false;
      this.org_house_number_changed = false;
      this.org_bank_account_changed = false;
      this.org_bank_name_changed = false;
      this.org_delegation_person_changed = false;
      this.org_delegation_person_position_changed = false;
      this.org_date_from_changed = false;
      this.org_date_to_changed = false;
      this.org_org_id_changed = false;
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
   public void setOrg_id(Double org_id) {
      if (this.isForceUpdate() || 
               (this.org_id != null && !this.org_id.equals(org_id))  ||
               (org_id != null && !org_id.equals(this.org_id)) ){
         this.org_id_changed = true; 
         this.record_changed = true;
         this.org_id = org_id;
      }
   }
   public Double getOrg_id() {
      return this.org_id;
   }
   public void setOrg_name(String org_name) {
      if (this.isForceUpdate() || 
               (this.org_name != null && !this.org_name.equals(org_name))  ||
               (org_name != null && !org_name.equals(this.org_name)) ){
         this.org_name_changed = true; 
         this.record_changed = true;
         this.org_name = org_name;
      }
   }
   public String getOrg_name() {
      return this.org_name;
   }
   public void setOrg_code(String org_code) {
      if (this.isForceUpdate() || 
               (this.org_code != null && !this.org_code.equals(org_code))  ||
               (org_code != null && !org_code.equals(this.org_code)) ){
         this.org_code_changed = true; 
         this.record_changed = true;
         this.org_code = org_code;
      }
   }
   public String getOrg_code() {
      return this.org_code;
   }
   public void setOrg_type(String org_type) {
      if (this.isForceUpdate() || 
               (this.org_type != null && !this.org_type.equals(org_type))  ||
               (org_type != null && !org_type.equals(this.org_type)) ){
         this.org_type_changed = true; 
         this.record_changed = true;
         this.org_type = org_type;
      }
   }
   public String getOrg_type() {
      return this.org_type;
   }
   public void setOrg_region(String org_region) {
      if (this.isForceUpdate() || 
               (this.org_region != null && !this.org_region.equals(org_region))  ||
               (org_region != null && !org_region.equals(this.org_region)) ){
         this.org_region_changed = true; 
         this.record_changed = true;
         this.org_region = org_region;
      }
   }
   public String getOrg_region() {
      return this.org_region;
   }
   public void setOrg_phone(String org_phone) {
      if (this.isForceUpdate() || 
               (this.org_phone != null && !this.org_phone.equals(org_phone))  ||
               (org_phone != null && !org_phone.equals(this.org_phone)) ){
         this.org_phone_changed = true; 
         this.record_changed = true;
         this.org_phone = org_phone;
      }
   }
   public String getOrg_phone() {
      return this.org_phone;
   }
   public void setOrg_email(String org_email) {
      if (this.isForceUpdate() || 
               (this.org_email != null && !this.org_email.equals(org_email))  ||
               (org_email != null && !org_email.equals(this.org_email)) ){
         this.org_email_changed = true; 
         this.record_changed = true;
         this.org_email = org_email;
      }
   }
   public String getOrg_email() {
      return this.org_email;
   }
   public void setOrg_website(String org_website) {
      if (this.isForceUpdate() || 
               (this.org_website != null && !this.org_website.equals(org_website))  ||
               (org_website != null && !org_website.equals(this.org_website)) ){
         this.org_website_changed = true; 
         this.record_changed = true;
         this.org_website = org_website;
      }
   }
   public String getOrg_website() {
      return this.org_website;
   }
   public void setOrg_address(String org_address) {
      if (this.isForceUpdate() || 
               (this.org_address != null && !this.org_address.equals(org_address))  ||
               (org_address != null && !org_address.equals(this.org_address)) ){
         this.org_address_changed = true; 
         this.record_changed = true;
         this.org_address = org_address;
      }
   }
   public String getOrg_address() {
      return this.org_address;
   }
   public void setOrg_house_number(String org_house_number) {
      if (this.isForceUpdate() || 
               (this.org_house_number != null && !this.org_house_number.equals(org_house_number))  ||
               (org_house_number != null && !org_house_number.equals(this.org_house_number)) ){
         this.org_house_number_changed = true; 
         this.record_changed = true;
         this.org_house_number = org_house_number;
      }
   }
   public String getOrg_house_number() {
      return this.org_house_number;
   }
   public void setOrg_bank_account(String org_bank_account) {
      if (this.isForceUpdate() || 
               (this.org_bank_account != null && !this.org_bank_account.equals(org_bank_account))  ||
               (org_bank_account != null && !org_bank_account.equals(this.org_bank_account)) ){
         this.org_bank_account_changed = true; 
         this.record_changed = true;
         this.org_bank_account = org_bank_account;
      }
   }
   public String getOrg_bank_account() {
      return this.org_bank_account;
   }
   public void setOrg_bank_name(String org_bank_name) {
      if (this.isForceUpdate() || 
               (this.org_bank_name != null && !this.org_bank_name.equals(org_bank_name))  ||
               (org_bank_name != null && !org_bank_name.equals(this.org_bank_name)) ){
         this.org_bank_name_changed = true; 
         this.record_changed = true;
         this.org_bank_name = org_bank_name;
      }
   }
   public String getOrg_bank_name() {
      return this.org_bank_name;
   }
   public void setOrg_delegation_person(String org_delegation_person) {
      if (this.isForceUpdate() || 
               (this.org_delegation_person != null && !this.org_delegation_person.equals(org_delegation_person))  ||
               (org_delegation_person != null && !org_delegation_person.equals(this.org_delegation_person)) ){
         this.org_delegation_person_changed = true; 
         this.record_changed = true;
         this.org_delegation_person = org_delegation_person;
      }
   }
   public String getOrg_delegation_person() {
      return this.org_delegation_person;
   }
   public void setOrg_delegation_person_position(String org_delegation_person_position) {
      if (this.isForceUpdate() || 
               (this.org_delegation_person_position != null && !this.org_delegation_person_position.equals(org_delegation_person_position))  ||
               (org_delegation_person_position != null && !org_delegation_person_position.equals(this.org_delegation_person_position)) ){
         this.org_delegation_person_position_changed = true; 
         this.record_changed = true;
         this.org_delegation_person_position = org_delegation_person_position;
      }
   }
   public String getOrg_delegation_person_position() {
      return this.org_delegation_person_position;
   }
   public void setOrg_date_from(Date org_date_from) {
      if (this.isForceUpdate() || 
               (this.org_date_from != null && (org_date_from == null ||this.org_date_from.compareTo(org_date_from) != 0 )) ||
               (org_date_from != null && (this.org_date_from == null ||org_date_from.compareTo(this.org_date_from) != 0 ))){
         this.org_date_from_changed = true; 
         this.record_changed = true;
         this.org_date_from = org_date_from;
      }
   }
   public Date getOrg_date_from() {
      return this.org_date_from;
   }
   public void setOrg_date_to(Date org_date_to) {
      if (this.isForceUpdate() || 
               (this.org_date_to != null && (org_date_to == null ||this.org_date_to.compareTo(org_date_to) != 0 )) ||
               (org_date_to != null && (this.org_date_to == null ||org_date_to.compareTo(this.org_date_to) != 0 ))){
         this.org_date_to_changed = true; 
         this.record_changed = true;
         this.org_date_to = org_date_to;
      }
   }
   public Date getOrg_date_to() {
      return this.org_date_to;
   }
   public void setOrg_org_id(Double org_org_id) {
      if (this.isForceUpdate() || 
               (this.org_org_id != null && !this.org_org_id.equals(org_org_id))  ||
               (org_org_id != null && !org_org_id.equals(this.org_org_id)) ){
         this.org_org_id_changed = true; 
         this.record_changed = true;
         this.org_org_id = org_org_id;
      }
   }
   public Double getOrg_org_id() {
      return this.org_org_id;
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
            this.org_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.org_id;
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
      if (org_name_changed || operation == Utils.OPERATION_INSERT) {
         if (org_name== null || EMPTY_STRING.equals(org_name)) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (org_name!= null && org_name.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (org_code_changed || operation == Utils.OPERATION_INSERT) {
         if (org_code== null || EMPTY_STRING.equals(org_code)) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (org_code!= null && org_code.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (org_type_changed || operation == Utils.OPERATION_INSERT) {
         if (org_type!= null && org_type.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (org_region_changed || operation == Utils.OPERATION_INSERT) {
         if (org_region!= null && org_region.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_REGION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_phone_changed || operation == Utils.OPERATION_INSERT) {
         if (org_phone!= null && org_phone.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_PHONE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_email_changed || operation == Utils.OPERATION_INSERT) {
         if (org_email!= null && org_email.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_website_changed || operation == Utils.OPERATION_INSERT) {
         if (org_website!= null && org_website.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_WEBSITE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_address_changed || operation == Utils.OPERATION_INSERT) {
         if (org_address!= null && org_address.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_ADDRESS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_house_number_changed || operation == Utils.OPERATION_INSERT) {
         if (org_house_number!= null && org_house_number.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_HOUSE_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (org_bank_account_changed || operation == Utils.OPERATION_INSERT) {
         if (org_bank_account!= null && org_bank_account.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_BANK_ACCOUNT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (org_bank_name_changed || operation == Utils.OPERATION_INSERT) {
         if (org_bank_name!= null && org_bank_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_BANK_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_delegation_person_changed || operation == Utils.OPERATION_INSERT) {
         if (org_delegation_person!= null && org_delegation_person.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_DELEGATION_PERSON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_delegation_person_position_changed || operation == Utils.OPERATION_INSERT) {
         if (org_delegation_person_position!= null && org_delegation_person_position.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_DELEGATION_PERSON_POSITION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (org_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (org_date_from== null) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "ORG_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_ORGANIZATIONS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_ORGANIZATIONS SET ";
      boolean changedExists = false;      if (org_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_name);
      }
      if (org_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_code);
      }
      if (org_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_type);
      }
      if (org_region_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_REGION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_region);
      }
      if (org_phone_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_PHONE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_phone);
      }
      if (org_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_email);
      }
      if (org_website_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_WEBSITE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_website);
      }
      if (org_address_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_ADDRESS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_address);
      }
      if (org_house_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_HOUSE_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_house_number);
      }
      if (org_bank_account_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_BANK_ACCOUNT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_bank_account);
      }
      if (org_bank_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_BANK_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_bank_name);
      }
      if (org_delegation_person_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_DELEGATION_PERSON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_delegation_person);
      }
      if (org_delegation_person_position_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_DELEGATION_PERSON_POSITION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_delegation_person_position);
      }
      if (org_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_date_from);
      }
      if (org_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_date_to);
      }
      if (org_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORG_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(org_org_id);
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
      answer = answer +" WHERE  ORG_ID = ? ";
      updateStatementPart.addStatementParam(org_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprOrganizationsDAO\":{\"org_id\": \""+getOrg_id()+"\", \"org_name\": \""+getOrg_name()+"\", \"org_code\": \""+getOrg_code()+"\", \"org_type\": \""+getOrg_type()+"\", \"org_region\": \""+getOrg_region()+"\", \"org_phone\": \""+getOrg_phone()+"\", \"org_email\": \""+getOrg_email()+"\", \"org_website\": \""+getOrg_website()+"\", \"org_address\": \""+getOrg_address()+"\", \"org_house_number\": \""+getOrg_house_number()+"\", \"org_bank_account\": \""+getOrg_bank_account()+"\", \"org_bank_name\": \""+getOrg_bank_name()+"\", \"org_delegation_person\": \""+getOrg_delegation_person()+"\", \"org_delegation_person_position\": \""+getOrg_delegation_person_position()+"\", \"org_date_from\": \""+getOrg_date_from()+"\", \"org_date_to\": \""+getOrg_date_to()+"\", \"org_org_id\": \""+getOrg_org_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}