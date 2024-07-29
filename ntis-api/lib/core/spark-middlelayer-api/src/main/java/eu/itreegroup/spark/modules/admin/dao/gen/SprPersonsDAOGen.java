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

public class SprPersonsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_PERSONS.PER_ID";
   private Double per_id;
   private String per_name;
   private String per_surname;
   private String per_data_confirmed;
   private Date per_confirmation_date;
   private Date per_date_of_birth;
   private String per_code_exists;
   private String per_code;
   private String per_phone_number;
   private String per_email;
   private String per_email_confirmed;
   private String per_document_type;
   private String per_document_number;
   private Date per_document_valid_until;
   private String per_lrt_resident;
   private String per_address_country_code;
   private String per_address_city;
   private String per_address_street;
   private String per_address_post_code;
   private String per_address_house_number;
   private String per_address_flat_number;
   private Double per_photo_fil_id;
   private Double per_avatar_fil_id;
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

   protected boolean per_id_changed;
   protected boolean per_name_changed;
   protected boolean per_surname_changed;
   protected boolean per_data_confirmed_changed;
   protected boolean per_confirmation_date_changed;
   protected boolean per_date_of_birth_changed;
   protected boolean per_code_exists_changed;
   protected boolean per_code_changed;
   protected boolean per_phone_number_changed;
   protected boolean per_email_changed;
   protected boolean per_email_confirmed_changed;
   protected boolean per_document_type_changed;
   protected boolean per_document_number_changed;
   protected boolean per_document_valid_until_changed;
   protected boolean per_lrt_resident_changed;
   protected boolean per_address_country_code_changed;
   protected boolean per_address_city_changed;
   protected boolean per_address_street_changed;
   protected boolean per_address_post_code_changed;
   protected boolean per_address_house_number_changed;
   protected boolean per_address_flat_number_changed;
   protected boolean per_photo_fil_id_changed;
   protected boolean per_avatar_fil_id_changed;
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
   public SprPersonsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprPersonsDAOGen(Double per_id, String per_name, String per_surname, String per_data_confirmed, Date per_confirmation_date, Date per_date_of_birth, String per_code_exists, String per_code, String per_phone_number, String per_email, String per_email_confirmed, String per_document_type, String per_document_number, Date per_document_valid_until, String per_lrt_resident, String per_address_country_code, String per_address_city, String per_address_street, String per_address_post_code, String per_address_house_number, String per_address_flat_number, Double per_photo_fil_id, Double per_avatar_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.per_id = per_id;
      this.per_name = per_name;
      this.per_surname = per_surname;
      this.per_data_confirmed = per_data_confirmed;
      this.per_confirmation_date = per_confirmation_date;
      this.per_date_of_birth = per_date_of_birth;
      this.per_code_exists = per_code_exists;
      this.per_code = per_code;
      this.per_phone_number = per_phone_number;
      this.per_email = per_email;
      this.per_email_confirmed = per_email_confirmed;
      this.per_document_type = per_document_type;
      this.per_document_number = per_document_number;
      this.per_document_valid_until = per_document_valid_until;
      this.per_lrt_resident = per_lrt_resident;
      this.per_address_country_code = per_address_country_code;
      this.per_address_city = per_address_city;
      this.per_address_street = per_address_street;
      this.per_address_post_code = per_address_post_code;
      this.per_address_house_number = per_address_house_number;
      this.per_address_flat_number = per_address_flat_number;
      this.per_photo_fil_id = per_photo_fil_id;
      this.per_avatar_fil_id = per_avatar_fil_id;
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
   public void copyValues(SprPersonsDAOGen obj) {
      this.setPer_id(obj.getPer_id());
      this.setPer_name(obj.getPer_name());
      this.setPer_surname(obj.getPer_surname());
      this.setPer_data_confirmed(obj.getPer_data_confirmed());
      this.setPer_confirmation_date(obj.getPer_confirmation_date());
      this.setPer_date_of_birth(obj.getPer_date_of_birth());
      this.setPer_code_exists(obj.getPer_code_exists());
      this.setPer_code(obj.getPer_code());
      this.setPer_phone_number(obj.getPer_phone_number());
      this.setPer_email(obj.getPer_email());
      this.setPer_email_confirmed(obj.getPer_email_confirmed());
      this.setPer_document_type(obj.getPer_document_type());
      this.setPer_document_number(obj.getPer_document_number());
      this.setPer_document_valid_until(obj.getPer_document_valid_until());
      this.setPer_lrt_resident(obj.getPer_lrt_resident());
      this.setPer_address_country_code(obj.getPer_address_country_code());
      this.setPer_address_city(obj.getPer_address_city());
      this.setPer_address_street(obj.getPer_address_street());
      this.setPer_address_post_code(obj.getPer_address_post_code());
      this.setPer_address_house_number(obj.getPer_address_house_number());
      this.setPer_address_flat_number(obj.getPer_address_flat_number());
      this.setPer_photo_fil_id(obj.getPer_photo_fil_id());
      this.setPer_avatar_fil_id(obj.getPer_avatar_fil_id());
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
      this.per_id_changed = false;
      this.per_name_changed = false;
      this.per_surname_changed = false;
      this.per_data_confirmed_changed = false;
      this.per_confirmation_date_changed = false;
      this.per_date_of_birth_changed = false;
      this.per_code_exists_changed = false;
      this.per_code_changed = false;
      this.per_phone_number_changed = false;
      this.per_email_changed = false;
      this.per_email_confirmed_changed = false;
      this.per_document_type_changed = false;
      this.per_document_number_changed = false;
      this.per_document_valid_until_changed = false;
      this.per_lrt_resident_changed = false;
      this.per_address_country_code_changed = false;
      this.per_address_city_changed = false;
      this.per_address_street_changed = false;
      this.per_address_post_code_changed = false;
      this.per_address_house_number_changed = false;
      this.per_address_flat_number_changed = false;
      this.per_photo_fil_id_changed = false;
      this.per_avatar_fil_id_changed = false;
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
   public void setPer_id(Double per_id) {
      if (this.isForceUpdate() || 
               (this.per_id != null && !this.per_id.equals(per_id))  ||
               (per_id != null && !per_id.equals(this.per_id)) ){
         this.per_id_changed = true; 
         this.record_changed = true;
         this.per_id = per_id;
      }
   }
   public Double getPer_id() {
      return this.per_id;
   }
   public void setPer_name(String per_name) {
      if (this.isForceUpdate() || 
               (this.per_name != null && !this.per_name.equals(per_name))  ||
               (per_name != null && !per_name.equals(this.per_name)) ){
         this.per_name_changed = true; 
         this.record_changed = true;
         this.per_name = per_name;
      }
   }
   public String getPer_name() {
      return this.per_name;
   }
   public void setPer_surname(String per_surname) {
      if (this.isForceUpdate() || 
               (this.per_surname != null && !this.per_surname.equals(per_surname))  ||
               (per_surname != null && !per_surname.equals(this.per_surname)) ){
         this.per_surname_changed = true; 
         this.record_changed = true;
         this.per_surname = per_surname;
      }
   }
   public String getPer_surname() {
      return this.per_surname;
   }
   public void setPer_data_confirmed(String per_data_confirmed) {
      if (this.isForceUpdate() || 
               (this.per_data_confirmed != null && !this.per_data_confirmed.equals(per_data_confirmed))  ||
               (per_data_confirmed != null && !per_data_confirmed.equals(this.per_data_confirmed)) ){
         this.per_data_confirmed_changed = true; 
         this.record_changed = true;
         this.per_data_confirmed = per_data_confirmed;
      }
   }
   public String getPer_data_confirmed() {
      return this.per_data_confirmed;
   }
   public void setPer_confirmation_date(Date per_confirmation_date) {
      if (this.isForceUpdate() || 
               (this.per_confirmation_date != null && (per_confirmation_date == null ||this.per_confirmation_date.compareTo(per_confirmation_date) != 0 )) ||
               (per_confirmation_date != null && (this.per_confirmation_date == null ||per_confirmation_date.compareTo(this.per_confirmation_date) != 0 ))){
         this.per_confirmation_date_changed = true; 
         this.record_changed = true;
         this.per_confirmation_date = per_confirmation_date;
      }
   }
   public Date getPer_confirmation_date() {
      return this.per_confirmation_date;
   }
   public void setPer_date_of_birth(Date per_date_of_birth) {
      if (this.isForceUpdate() || 
               (this.per_date_of_birth != null && (per_date_of_birth == null ||this.per_date_of_birth.compareTo(per_date_of_birth) != 0 )) ||
               (per_date_of_birth != null && (this.per_date_of_birth == null ||per_date_of_birth.compareTo(this.per_date_of_birth) != 0 ))){
         this.per_date_of_birth_changed = true; 
         this.record_changed = true;
         this.per_date_of_birth = per_date_of_birth;
      }
   }
   public Date getPer_date_of_birth() {
      return this.per_date_of_birth;
   }
   public void setPer_code_exists(String per_code_exists) {
      if (this.isForceUpdate() || 
               (this.per_code_exists != null && !this.per_code_exists.equals(per_code_exists))  ||
               (per_code_exists != null && !per_code_exists.equals(this.per_code_exists)) ){
         this.per_code_exists_changed = true; 
         this.record_changed = true;
         this.per_code_exists = per_code_exists;
      }
   }
   public String getPer_code_exists() {
      return this.per_code_exists;
   }
   public void setPer_code(String per_code) {
      if (this.isForceUpdate() || 
               (this.per_code != null && !this.per_code.equals(per_code))  ||
               (per_code != null && !per_code.equals(this.per_code)) ){
         this.per_code_changed = true; 
         this.record_changed = true;
         this.per_code = per_code;
      }
   }
   public String getPer_code() {
      return this.per_code;
   }
   public void setPer_phone_number(String per_phone_number) {
      if (this.isForceUpdate() || 
               (this.per_phone_number != null && !this.per_phone_number.equals(per_phone_number))  ||
               (per_phone_number != null && !per_phone_number.equals(this.per_phone_number)) ){
         this.per_phone_number_changed = true; 
         this.record_changed = true;
         this.per_phone_number = per_phone_number;
      }
   }
   public String getPer_phone_number() {
      return this.per_phone_number;
   }
   public void setPer_email(String per_email) {
      if (this.isForceUpdate() || 
               (this.per_email != null && !this.per_email.equals(per_email))  ||
               (per_email != null && !per_email.equals(this.per_email)) ){
         this.per_email_changed = true; 
         this.record_changed = true;
         this.per_email = per_email;
      }
   }
   public String getPer_email() {
      return this.per_email;
   }
   public void setPer_email_confirmed(String per_email_confirmed) {
      if (this.isForceUpdate() || 
               (this.per_email_confirmed != null && !this.per_email_confirmed.equals(per_email_confirmed))  ||
               (per_email_confirmed != null && !per_email_confirmed.equals(this.per_email_confirmed)) ){
         this.per_email_confirmed_changed = true; 
         this.record_changed = true;
         this.per_email_confirmed = per_email_confirmed;
      }
   }
   public String getPer_email_confirmed() {
      return this.per_email_confirmed;
   }
   public void setPer_document_type(String per_document_type) {
      if (this.isForceUpdate() || 
               (this.per_document_type != null && !this.per_document_type.equals(per_document_type))  ||
               (per_document_type != null && !per_document_type.equals(this.per_document_type)) ){
         this.per_document_type_changed = true; 
         this.record_changed = true;
         this.per_document_type = per_document_type;
      }
   }
   public String getPer_document_type() {
      return this.per_document_type;
   }
   public void setPer_document_number(String per_document_number) {
      if (this.isForceUpdate() || 
               (this.per_document_number != null && !this.per_document_number.equals(per_document_number))  ||
               (per_document_number != null && !per_document_number.equals(this.per_document_number)) ){
         this.per_document_number_changed = true; 
         this.record_changed = true;
         this.per_document_number = per_document_number;
      }
   }
   public String getPer_document_number() {
      return this.per_document_number;
   }
   public void setPer_document_valid_until(Date per_document_valid_until) {
      if (this.isForceUpdate() || 
               (this.per_document_valid_until != null && (per_document_valid_until == null ||this.per_document_valid_until.compareTo(per_document_valid_until) != 0 )) ||
               (per_document_valid_until != null && (this.per_document_valid_until == null ||per_document_valid_until.compareTo(this.per_document_valid_until) != 0 ))){
         this.per_document_valid_until_changed = true; 
         this.record_changed = true;
         this.per_document_valid_until = per_document_valid_until;
      }
   }
   public Date getPer_document_valid_until() {
      return this.per_document_valid_until;
   }
   public void setPer_lrt_resident(String per_lrt_resident) {
      if (this.isForceUpdate() || 
               (this.per_lrt_resident != null && !this.per_lrt_resident.equals(per_lrt_resident))  ||
               (per_lrt_resident != null && !per_lrt_resident.equals(this.per_lrt_resident)) ){
         this.per_lrt_resident_changed = true; 
         this.record_changed = true;
         this.per_lrt_resident = per_lrt_resident;
      }
   }
   public String getPer_lrt_resident() {
      return this.per_lrt_resident;
   }
   public void setPer_address_country_code(String per_address_country_code) {
      if (this.isForceUpdate() || 
               (this.per_address_country_code != null && !this.per_address_country_code.equals(per_address_country_code))  ||
               (per_address_country_code != null && !per_address_country_code.equals(this.per_address_country_code)) ){
         this.per_address_country_code_changed = true; 
         this.record_changed = true;
         this.per_address_country_code = per_address_country_code;
      }
   }
   public String getPer_address_country_code() {
      return this.per_address_country_code;
   }
   public void setPer_address_city(String per_address_city) {
      if (this.isForceUpdate() || 
               (this.per_address_city != null && !this.per_address_city.equals(per_address_city))  ||
               (per_address_city != null && !per_address_city.equals(this.per_address_city)) ){
         this.per_address_city_changed = true; 
         this.record_changed = true;
         this.per_address_city = per_address_city;
      }
   }
   public String getPer_address_city() {
      return this.per_address_city;
   }
   public void setPer_address_street(String per_address_street) {
      if (this.isForceUpdate() || 
               (this.per_address_street != null && !this.per_address_street.equals(per_address_street))  ||
               (per_address_street != null && !per_address_street.equals(this.per_address_street)) ){
         this.per_address_street_changed = true; 
         this.record_changed = true;
         this.per_address_street = per_address_street;
      }
   }
   public String getPer_address_street() {
      return this.per_address_street;
   }
   public void setPer_address_post_code(String per_address_post_code) {
      if (this.isForceUpdate() || 
               (this.per_address_post_code != null && !this.per_address_post_code.equals(per_address_post_code))  ||
               (per_address_post_code != null && !per_address_post_code.equals(this.per_address_post_code)) ){
         this.per_address_post_code_changed = true; 
         this.record_changed = true;
         this.per_address_post_code = per_address_post_code;
      }
   }
   public String getPer_address_post_code() {
      return this.per_address_post_code;
   }
   public void setPer_address_house_number(String per_address_house_number) {
      if (this.isForceUpdate() || 
               (this.per_address_house_number != null && !this.per_address_house_number.equals(per_address_house_number))  ||
               (per_address_house_number != null && !per_address_house_number.equals(this.per_address_house_number)) ){
         this.per_address_house_number_changed = true; 
         this.record_changed = true;
         this.per_address_house_number = per_address_house_number;
      }
   }
   public String getPer_address_house_number() {
      return this.per_address_house_number;
   }
   public void setPer_address_flat_number(String per_address_flat_number) {
      if (this.isForceUpdate() || 
               (this.per_address_flat_number != null && !this.per_address_flat_number.equals(per_address_flat_number))  ||
               (per_address_flat_number != null && !per_address_flat_number.equals(this.per_address_flat_number)) ){
         this.per_address_flat_number_changed = true; 
         this.record_changed = true;
         this.per_address_flat_number = per_address_flat_number;
      }
   }
   public String getPer_address_flat_number() {
      return this.per_address_flat_number;
   }
   public void setPer_photo_fil_id(Double per_photo_fil_id) {
      if (this.isForceUpdate() || 
               (this.per_photo_fil_id != null && !this.per_photo_fil_id.equals(per_photo_fil_id))  ||
               (per_photo_fil_id != null && !per_photo_fil_id.equals(this.per_photo_fil_id)) ){
         this.per_photo_fil_id_changed = true; 
         this.record_changed = true;
         this.per_photo_fil_id = per_photo_fil_id;
      }
   }
   public Double getPer_photo_fil_id() {
      return this.per_photo_fil_id;
   }
   public void setPer_avatar_fil_id(Double per_avatar_fil_id) {
      if (this.isForceUpdate() || 
               (this.per_avatar_fil_id != null && !this.per_avatar_fil_id.equals(per_avatar_fil_id))  ||
               (per_avatar_fil_id != null && !per_avatar_fil_id.equals(this.per_avatar_fil_id)) ){
         this.per_avatar_fil_id_changed = true; 
         this.record_changed = true;
         this.per_avatar_fil_id = per_avatar_fil_id;
      }
   }
   public Double getPer_avatar_fil_id() {
      return this.per_avatar_fil_id;
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
            this.per_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.per_id;
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
      if (per_name_changed || operation == Utils.OPERATION_INSERT) {
         if (per_name== null || EMPTY_STRING.equals(per_name)) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (per_name!= null && per_name.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (per_surname_changed || operation == Utils.OPERATION_INSERT) {
         if (per_surname== null || EMPTY_STRING.equals(per_surname)) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_SURNAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (per_surname!= null && per_surname.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_SURNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (per_data_confirmed_changed || operation == Utils.OPERATION_INSERT) {
         if (per_data_confirmed!= null && per_data_confirmed.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_DATA_CONFIRMED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (per_date_of_birth_changed || operation == Utils.OPERATION_INSERT) {
         if (per_date_of_birth== null) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_DATE_OF_BIRTH", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (per_code_exists_changed || operation == Utils.OPERATION_INSERT) {
         if (per_code_exists== null || EMPTY_STRING.equals(per_code_exists)) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_CODE_EXISTS", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (per_code_exists!= null && per_code_exists.length()>1) {
               throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_CODE_EXISTS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (per_code_changed || operation == Utils.OPERATION_INSERT) {
         if (per_code!= null && per_code.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (per_phone_number_changed || operation == Utils.OPERATION_INSERT) {
         if (per_phone_number!= null && per_phone_number.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_PHONE_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (per_email_changed || operation == Utils.OPERATION_INSERT) {
         if (per_email!= null && per_email.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (per_email_confirmed_changed || operation == Utils.OPERATION_INSERT) {
         if (per_email_confirmed!= null && per_email_confirmed.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_EMAIL_CONFIRMED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (per_document_type_changed || operation == Utils.OPERATION_INSERT) {
         if (per_document_type!= null && per_document_type.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_DOCUMENT_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (per_document_number_changed || operation == Utils.OPERATION_INSERT) {
         if (per_document_number!= null && per_document_number.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_DOCUMENT_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (per_lrt_resident_changed || operation == Utils.OPERATION_INSERT) {
         if (per_lrt_resident== null || EMPTY_STRING.equals(per_lrt_resident)) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_LRT_RESIDENT", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (per_lrt_resident!= null && per_lrt_resident.length()>1) {
               throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_LRT_RESIDENT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (per_address_country_code_changed || operation == Utils.OPERATION_INSERT) {
         if (per_address_country_code!= null && per_address_country_code.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_ADDRESS_COUNTRY_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (per_address_city_changed || operation == Utils.OPERATION_INSERT) {
         if (per_address_city!= null && per_address_city.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_ADDRESS_CITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (per_address_street_changed || operation == Utils.OPERATION_INSERT) {
         if (per_address_street!= null && per_address_street.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_ADDRESS_STREET", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (per_address_post_code_changed || operation == Utils.OPERATION_INSERT) {
         if (per_address_post_code!= null && per_address_post_code.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_ADDRESS_POST_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (per_address_house_number_changed || operation == Utils.OPERATION_INSERT) {
         if (per_address_house_number!= null && per_address_house_number.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_ADDRESS_HOUSE_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (per_address_flat_number_changed || operation == Utils.OPERATION_INSERT) {
         if (per_address_flat_number!= null && per_address_flat_number.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "PER_ADDRESS_FLAT_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_PERSONS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_PERSONS SET ";
      boolean changedExists = false;      if (per_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_name);
      }
      if (per_surname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_SURNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_surname);
      }
      if (per_data_confirmed_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_DATA_CONFIRMED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_data_confirmed);
      }
      if (per_confirmation_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_CONFIRMATION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_confirmation_date);
      }
      if (per_date_of_birth_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_DATE_OF_BIRTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_date_of_birth);
      }
      if (per_code_exists_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_CODE_EXISTS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_code_exists);
      }
      if (per_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_code);
      }
      if (per_phone_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_PHONE_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_phone_number);
      }
      if (per_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_email);
      }
      if (per_email_confirmed_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_EMAIL_CONFIRMED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_email_confirmed);
      }
      if (per_document_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_DOCUMENT_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_document_type);
      }
      if (per_document_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_DOCUMENT_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_document_number);
      }
      if (per_document_valid_until_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_DOCUMENT_VALID_UNTIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_document_valid_until);
      }
      if (per_lrt_resident_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_LRT_RESIDENT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_lrt_resident);
      }
      if (per_address_country_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_ADDRESS_COUNTRY_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_address_country_code);
      }
      if (per_address_city_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_ADDRESS_CITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_address_city);
      }
      if (per_address_street_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_ADDRESS_STREET = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_address_street);
      }
      if (per_address_post_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_ADDRESS_POST_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_address_post_code);
      }
      if (per_address_house_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_ADDRESS_HOUSE_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_address_house_number);
      }
      if (per_address_flat_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_ADDRESS_FLAT_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_address_flat_number);
      }
      if (per_photo_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_PHOTO_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_photo_fil_id);
      }
      if (per_avatar_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"PER_AVATAR_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(per_avatar_fil_id);
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
      answer = answer +" WHERE  PER_ID = ? ";
      updateStatementPart.addStatementParam(per_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprPersonsDAO\":{\"per_id\": \""+getPer_id()+"\", \"per_name\": \""+getPer_name()+"\", \"per_surname\": \""+getPer_surname()+"\", \"per_data_confirmed\": \""+getPer_data_confirmed()+"\", \"per_confirmation_date\": \""+getPer_confirmation_date()+"\", \"per_date_of_birth\": \""+getPer_date_of_birth()+"\", \"per_code_exists\": \""+getPer_code_exists()+"\", \"per_code\": \""+getPer_code()+"\", \"per_phone_number\": \""+getPer_phone_number()+"\", \"per_email\": \""+getPer_email()+"\", \"per_email_confirmed\": \""+getPer_email_confirmed()+"\", \"per_document_type\": \""+getPer_document_type()+"\", \"per_document_number\": \""+getPer_document_number()+"\", \"per_document_valid_until\": \""+getPer_document_valid_until()+"\", \"per_lrt_resident\": \""+getPer_lrt_resident()+"\", \"per_address_country_code\": \""+getPer_address_country_code()+"\", \"per_address_city\": \""+getPer_address_city()+"\", \"per_address_street\": \""+getPer_address_street()+"\", \"per_address_post_code\": \""+getPer_address_post_code()+"\", \"per_address_house_number\": \""+getPer_address_house_number()+"\", \"per_address_flat_number\": \""+getPer_address_flat_number()+"\", \"per_photo_fil_id\": \""+getPer_photo_fil_id()+"\", \"per_avatar_fil_id\": \""+getPer_avatar_fil_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}