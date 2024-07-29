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

public class SprUsersDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_USERS.USR_ID";
   private Double usr_id;
   private String usr_username;
   private String usr_email;
   private String usr_email_confirmed;
   private String usr_person_name;
   private String usr_person_surname;
   private Double usr_wrong_login_attempts;
   private Date usr_lock_date;
   private String usr_salt;
   private String usr_password_algorithm;
   private String usr_password_hash;
   private Date usr_password_reset_req_date;
   private Date usr_password_change_date;
   private Date usr_password_next_change_date;
   private String usr_password_history;
   private String usr_phone_number;
   private String usr_language;
   private String usr_type;
   private Date usr_date_from;
   private Date usr_date_to;
   private Double usr_subscripted_month;
   private String usr_terms_accepted;
   private Date usr_terms_accepted_date;
   private String usr_2fa_key;
   private String usr_2fa_key_confirm;
   private String usr_2fa_used;
   private String usr_confirmed_release_version;
   private String usr_profile_token;
   private Double usr_per_id;
   private Double usr_org_id;
   private Double usr_rol_id;
   private Double usr_photo_fil_id;
   private Double usr_avatar_fil_id;
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

   protected boolean usr_id_changed;
   protected boolean usr_username_changed;
   protected boolean usr_email_changed;
   protected boolean usr_email_confirmed_changed;
   protected boolean usr_person_name_changed;
   protected boolean usr_person_surname_changed;
   protected boolean usr_wrong_login_attempts_changed;
   protected boolean usr_lock_date_changed;
   protected boolean usr_salt_changed;
   protected boolean usr_password_algorithm_changed;
   protected boolean usr_password_hash_changed;
   protected boolean usr_password_reset_req_date_changed;
   protected boolean usr_password_change_date_changed;
   protected boolean usr_password_next_change_date_changed;
   protected boolean usr_password_history_changed;
   protected boolean usr_phone_number_changed;
   protected boolean usr_language_changed;
   protected boolean usr_type_changed;
   protected boolean usr_date_from_changed;
   protected boolean usr_date_to_changed;
   protected boolean usr_subscripted_month_changed;
   protected boolean usr_terms_accepted_changed;
   protected boolean usr_terms_accepted_date_changed;
   protected boolean usr_2fa_key_changed;
   protected boolean usr_2fa_key_confirm_changed;
   protected boolean usr_2fa_used_changed;
   protected boolean usr_confirmed_release_version_changed;
   protected boolean usr_profile_token_changed;
   protected boolean usr_per_id_changed;
   protected boolean usr_org_id_changed;
   protected boolean usr_rol_id_changed;
   protected boolean usr_photo_fil_id_changed;
   protected boolean usr_avatar_fil_id_changed;
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
   public SprUsersDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprUsersDAOGen(Double usr_id, String usr_username, String usr_email, String usr_email_confirmed, String usr_person_name, String usr_person_surname, Double usr_wrong_login_attempts, Date usr_lock_date, String usr_salt, String usr_password_algorithm, String usr_password_hash, Date usr_password_reset_req_date, Date usr_password_change_date, Date usr_password_next_change_date, String usr_password_history, String usr_phone_number, String usr_language, String usr_type, Date usr_date_from, Date usr_date_to, Double usr_subscripted_month, String usr_terms_accepted, Date usr_terms_accepted_date, String usr_2fa_key, String usr_2fa_key_confirm, String usr_2fa_used, String usr_confirmed_release_version, String usr_profile_token, Double usr_per_id, Double usr_org_id, Double usr_rol_id, Double usr_photo_fil_id, Double usr_avatar_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.usr_id = usr_id;
      this.usr_username = usr_username;
      this.usr_email = usr_email;
      this.usr_email_confirmed = usr_email_confirmed;
      this.usr_person_name = usr_person_name;
      this.usr_person_surname = usr_person_surname;
      this.usr_wrong_login_attempts = usr_wrong_login_attempts;
      this.usr_lock_date = usr_lock_date;
      this.usr_salt = usr_salt;
      this.usr_password_algorithm = usr_password_algorithm;
      this.usr_password_hash = usr_password_hash;
      this.usr_password_reset_req_date = usr_password_reset_req_date;
      this.usr_password_change_date = usr_password_change_date;
      this.usr_password_next_change_date = usr_password_next_change_date;
      this.usr_password_history = usr_password_history;
      this.usr_phone_number = usr_phone_number;
      this.usr_language = usr_language;
      this.usr_type = usr_type;
      this.usr_date_from = usr_date_from;
      this.usr_date_to = usr_date_to;
      this.usr_subscripted_month = usr_subscripted_month;
      this.usr_terms_accepted = usr_terms_accepted;
      this.usr_terms_accepted_date = usr_terms_accepted_date;
      this.usr_2fa_key = usr_2fa_key;
      this.usr_2fa_key_confirm = usr_2fa_key_confirm;
      this.usr_2fa_used = usr_2fa_used;
      this.usr_confirmed_release_version = usr_confirmed_release_version;
      this.usr_profile_token = usr_profile_token;
      this.usr_per_id = usr_per_id;
      this.usr_org_id = usr_org_id;
      this.usr_rol_id = usr_rol_id;
      this.usr_photo_fil_id = usr_photo_fil_id;
      this.usr_avatar_fil_id = usr_avatar_fil_id;
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
   public void copyValues(SprUsersDAOGen obj) {
      this.setUsr_id(obj.getUsr_id());
      this.setUsr_username(obj.getUsr_username());
      this.setUsr_email(obj.getUsr_email());
      this.setUsr_email_confirmed(obj.getUsr_email_confirmed());
      this.setUsr_person_name(obj.getUsr_person_name());
      this.setUsr_person_surname(obj.getUsr_person_surname());
      this.setUsr_wrong_login_attempts(obj.getUsr_wrong_login_attempts());
      this.setUsr_lock_date(obj.getUsr_lock_date());
      this.setUsr_salt(obj.getUsr_salt());
      this.setUsr_password_algorithm(obj.getUsr_password_algorithm());
      this.setUsr_password_hash(obj.getUsr_password_hash());
      this.setUsr_password_reset_req_date(obj.getUsr_password_reset_req_date());
      this.setUsr_password_change_date(obj.getUsr_password_change_date());
      this.setUsr_password_next_change_date(obj.getUsr_password_next_change_date());
      this.setUsr_password_history(obj.getUsr_password_history());
      this.setUsr_phone_number(obj.getUsr_phone_number());
      this.setUsr_language(obj.getUsr_language());
      this.setUsr_type(obj.getUsr_type());
      this.setUsr_date_from(obj.getUsr_date_from());
      this.setUsr_date_to(obj.getUsr_date_to());
      this.setUsr_subscripted_month(obj.getUsr_subscripted_month());
      this.setUsr_terms_accepted(obj.getUsr_terms_accepted());
      this.setUsr_terms_accepted_date(obj.getUsr_terms_accepted_date());
      this.setUsr_2fa_key(obj.getUsr_2fa_key());
      this.setUsr_2fa_key_confirm(obj.getUsr_2fa_key_confirm());
      this.setUsr_2fa_used(obj.getUsr_2fa_used());
      this.setUsr_confirmed_release_version(obj.getUsr_confirmed_release_version());
      this.setUsr_profile_token(obj.getUsr_profile_token());
      this.setUsr_per_id(obj.getUsr_per_id());
      this.setUsr_org_id(obj.getUsr_org_id());
      this.setUsr_rol_id(obj.getUsr_rol_id());
      this.setUsr_photo_fil_id(obj.getUsr_photo_fil_id());
      this.setUsr_avatar_fil_id(obj.getUsr_avatar_fil_id());
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
      this.usr_id_changed = false;
      this.usr_username_changed = false;
      this.usr_email_changed = false;
      this.usr_email_confirmed_changed = false;
      this.usr_person_name_changed = false;
      this.usr_person_surname_changed = false;
      this.usr_wrong_login_attempts_changed = false;
      this.usr_lock_date_changed = false;
      this.usr_salt_changed = false;
      this.usr_password_algorithm_changed = false;
      this.usr_password_hash_changed = false;
      this.usr_password_reset_req_date_changed = false;
      this.usr_password_change_date_changed = false;
      this.usr_password_next_change_date_changed = false;
      this.usr_password_history_changed = false;
      this.usr_phone_number_changed = false;
      this.usr_language_changed = false;
      this.usr_type_changed = false;
      this.usr_date_from_changed = false;
      this.usr_date_to_changed = false;
      this.usr_subscripted_month_changed = false;
      this.usr_terms_accepted_changed = false;
      this.usr_terms_accepted_date_changed = false;
      this.usr_2fa_key_changed = false;
      this.usr_2fa_key_confirm_changed = false;
      this.usr_2fa_used_changed = false;
      this.usr_confirmed_release_version_changed = false;
      this.usr_profile_token_changed = false;
      this.usr_per_id_changed = false;
      this.usr_org_id_changed = false;
      this.usr_rol_id_changed = false;
      this.usr_photo_fil_id_changed = false;
      this.usr_avatar_fil_id_changed = false;
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
   public void setUsr_id(Double usr_id) {
      if (this.isForceUpdate() || 
               (this.usr_id != null && !this.usr_id.equals(usr_id))  ||
               (usr_id != null && !usr_id.equals(this.usr_id)) ){
         this.usr_id_changed = true; 
         this.record_changed = true;
         this.usr_id = usr_id;
      }
   }
   public Double getUsr_id() {
      return this.usr_id;
   }
   public void setUsr_username(String usr_username) {
      if (this.isForceUpdate() || 
               (this.usr_username != null && !this.usr_username.equals(usr_username))  ||
               (usr_username != null && !usr_username.equals(this.usr_username)) ){
         this.usr_username_changed = true; 
         this.record_changed = true;
         this.usr_username = usr_username;
      }
   }
   public String getUsr_username() {
      return this.usr_username;
   }
   public void setUsr_email(String usr_email) {
      if (this.isForceUpdate() || 
               (this.usr_email != null && !this.usr_email.equals(usr_email))  ||
               (usr_email != null && !usr_email.equals(this.usr_email)) ){
         this.usr_email_changed = true; 
         this.record_changed = true;
         this.usr_email = usr_email;
      }
   }
   public String getUsr_email() {
      return this.usr_email;
   }
   public void setUsr_email_confirmed(String usr_email_confirmed) {
      if (this.isForceUpdate() || 
               (this.usr_email_confirmed != null && !this.usr_email_confirmed.equals(usr_email_confirmed))  ||
               (usr_email_confirmed != null && !usr_email_confirmed.equals(this.usr_email_confirmed)) ){
         this.usr_email_confirmed_changed = true; 
         this.record_changed = true;
         this.usr_email_confirmed = usr_email_confirmed;
      }
   }
   public String getUsr_email_confirmed() {
      return this.usr_email_confirmed;
   }
   public void setUsr_person_name(String usr_person_name) {
      if (this.isForceUpdate() || 
               (this.usr_person_name != null && !this.usr_person_name.equals(usr_person_name))  ||
               (usr_person_name != null && !usr_person_name.equals(this.usr_person_name)) ){
         this.usr_person_name_changed = true; 
         this.record_changed = true;
         this.usr_person_name = usr_person_name;
      }
   }
   public String getUsr_person_name() {
      return this.usr_person_name;
   }
   public void setUsr_person_surname(String usr_person_surname) {
      if (this.isForceUpdate() || 
               (this.usr_person_surname != null && !this.usr_person_surname.equals(usr_person_surname))  ||
               (usr_person_surname != null && !usr_person_surname.equals(this.usr_person_surname)) ){
         this.usr_person_surname_changed = true; 
         this.record_changed = true;
         this.usr_person_surname = usr_person_surname;
      }
   }
   public String getUsr_person_surname() {
      return this.usr_person_surname;
   }
   public void setUsr_wrong_login_attempts(Double usr_wrong_login_attempts) {
      if (this.isForceUpdate() || 
               (this.usr_wrong_login_attempts != null && !this.usr_wrong_login_attempts.equals(usr_wrong_login_attempts))  ||
               (usr_wrong_login_attempts != null && !usr_wrong_login_attempts.equals(this.usr_wrong_login_attempts)) ){
         this.usr_wrong_login_attempts_changed = true; 
         this.record_changed = true;
         this.usr_wrong_login_attempts = usr_wrong_login_attempts;
      }
   }
   public Double getUsr_wrong_login_attempts() {
      return this.usr_wrong_login_attempts;
   }
   public void setUsr_lock_date(Date usr_lock_date) {
      if (this.isForceUpdate() || 
               (this.usr_lock_date != null && (usr_lock_date == null ||this.usr_lock_date.compareTo(usr_lock_date) != 0 )) ||
               (usr_lock_date != null && (this.usr_lock_date == null ||usr_lock_date.compareTo(this.usr_lock_date) != 0 ))){
         this.usr_lock_date_changed = true; 
         this.record_changed = true;
         this.usr_lock_date = usr_lock_date;
      }
   }
   public Date getUsr_lock_date() {
      return this.usr_lock_date;
   }
   public void setUsr_salt(String usr_salt) {
      if (this.isForceUpdate() || 
               (this.usr_salt != null && !this.usr_salt.equals(usr_salt))  ||
               (usr_salt != null && !usr_salt.equals(this.usr_salt)) ){
         this.usr_salt_changed = true; 
         this.record_changed = true;
         this.usr_salt = usr_salt;
      }
   }
   public String getUsr_salt() {
      return this.usr_salt;
   }
   public void setUsr_password_algorithm(String usr_password_algorithm) {
      if (this.isForceUpdate() || 
               (this.usr_password_algorithm != null && !this.usr_password_algorithm.equals(usr_password_algorithm))  ||
               (usr_password_algorithm != null && !usr_password_algorithm.equals(this.usr_password_algorithm)) ){
         this.usr_password_algorithm_changed = true; 
         this.record_changed = true;
         this.usr_password_algorithm = usr_password_algorithm;
      }
   }
   public String getUsr_password_algorithm() {
      return this.usr_password_algorithm;
   }
   public void setUsr_password_hash(String usr_password_hash) {
      if (this.isForceUpdate() || 
               (this.usr_password_hash != null && !this.usr_password_hash.equals(usr_password_hash))  ||
               (usr_password_hash != null && !usr_password_hash.equals(this.usr_password_hash)) ){
         this.usr_password_hash_changed = true; 
         this.record_changed = true;
         this.usr_password_hash = usr_password_hash;
      }
   }
   public String getUsr_password_hash() {
      return this.usr_password_hash;
   }
   public void setUsr_password_reset_req_date(Date usr_password_reset_req_date) {
      if (this.isForceUpdate() || 
               (this.usr_password_reset_req_date != null && (usr_password_reset_req_date == null ||this.usr_password_reset_req_date.compareTo(usr_password_reset_req_date) != 0 )) ||
               (usr_password_reset_req_date != null && (this.usr_password_reset_req_date == null ||usr_password_reset_req_date.compareTo(this.usr_password_reset_req_date) != 0 ))){
         this.usr_password_reset_req_date_changed = true; 
         this.record_changed = true;
         this.usr_password_reset_req_date = usr_password_reset_req_date;
      }
   }
   public Date getUsr_password_reset_req_date() {
      return this.usr_password_reset_req_date;
   }
   public void setUsr_password_change_date(Date usr_password_change_date) {
      if (this.isForceUpdate() || 
               (this.usr_password_change_date != null && (usr_password_change_date == null ||this.usr_password_change_date.compareTo(usr_password_change_date) != 0 )) ||
               (usr_password_change_date != null && (this.usr_password_change_date == null ||usr_password_change_date.compareTo(this.usr_password_change_date) != 0 ))){
         this.usr_password_change_date_changed = true; 
         this.record_changed = true;
         this.usr_password_change_date = usr_password_change_date;
      }
   }
   public Date getUsr_password_change_date() {
      return this.usr_password_change_date;
   }
   public void setUsr_password_next_change_date(Date usr_password_next_change_date) {
      if (this.isForceUpdate() || 
               (this.usr_password_next_change_date != null && (usr_password_next_change_date == null ||this.usr_password_next_change_date.compareTo(usr_password_next_change_date) != 0 )) ||
               (usr_password_next_change_date != null && (this.usr_password_next_change_date == null ||usr_password_next_change_date.compareTo(this.usr_password_next_change_date) != 0 ))){
         this.usr_password_next_change_date_changed = true; 
         this.record_changed = true;
         this.usr_password_next_change_date = usr_password_next_change_date;
      }
   }
   public Date getUsr_password_next_change_date() {
      return this.usr_password_next_change_date;
   }
   public void setUsr_password_history(String usr_password_history) {
      if (this.isForceUpdate() || 
               (this.usr_password_history != null && !this.usr_password_history.equals(usr_password_history))  ||
               (usr_password_history != null && !usr_password_history.equals(this.usr_password_history)) ){
         this.usr_password_history_changed = true; 
         this.record_changed = true;
         this.usr_password_history = usr_password_history;
      }
   }
   public String getUsr_password_history() {
      return this.usr_password_history;
   }
   public void setUsr_phone_number(String usr_phone_number) {
      if (this.isForceUpdate() || 
               (this.usr_phone_number != null && !this.usr_phone_number.equals(usr_phone_number))  ||
               (usr_phone_number != null && !usr_phone_number.equals(this.usr_phone_number)) ){
         this.usr_phone_number_changed = true; 
         this.record_changed = true;
         this.usr_phone_number = usr_phone_number;
      }
   }
   public String getUsr_phone_number() {
      return this.usr_phone_number;
   }
   public void setUsr_language(String usr_language) {
      if (this.isForceUpdate() || 
               (this.usr_language != null && !this.usr_language.equals(usr_language))  ||
               (usr_language != null && !usr_language.equals(this.usr_language)) ){
         this.usr_language_changed = true; 
         this.record_changed = true;
         this.usr_language = usr_language;
      }
   }
   public String getUsr_language() {
      return this.usr_language;
   }
   public void setUsr_type(String usr_type) {
      if (this.isForceUpdate() || 
               (this.usr_type != null && !this.usr_type.equals(usr_type))  ||
               (usr_type != null && !usr_type.equals(this.usr_type)) ){
         this.usr_type_changed = true; 
         this.record_changed = true;
         this.usr_type = usr_type;
      }
   }
   public String getUsr_type() {
      return this.usr_type;
   }
   public void setUsr_date_from(Date usr_date_from) {
      if (this.isForceUpdate() || 
               (this.usr_date_from != null && (usr_date_from == null ||this.usr_date_from.compareTo(usr_date_from) != 0 )) ||
               (usr_date_from != null && (this.usr_date_from == null ||usr_date_from.compareTo(this.usr_date_from) != 0 ))){
         this.usr_date_from_changed = true; 
         this.record_changed = true;
         this.usr_date_from = usr_date_from;
      }
   }
   public Date getUsr_date_from() {
      return this.usr_date_from;
   }
   public void setUsr_date_to(Date usr_date_to) {
      if (this.isForceUpdate() || 
               (this.usr_date_to != null && (usr_date_to == null ||this.usr_date_to.compareTo(usr_date_to) != 0 )) ||
               (usr_date_to != null && (this.usr_date_to == null ||usr_date_to.compareTo(this.usr_date_to) != 0 ))){
         this.usr_date_to_changed = true; 
         this.record_changed = true;
         this.usr_date_to = usr_date_to;
      }
   }
   public Date getUsr_date_to() {
      return this.usr_date_to;
   }
   public void setUsr_subscripted_month(Double usr_subscripted_month) {
      if (this.isForceUpdate() || 
               (this.usr_subscripted_month != null && !this.usr_subscripted_month.equals(usr_subscripted_month))  ||
               (usr_subscripted_month != null && !usr_subscripted_month.equals(this.usr_subscripted_month)) ){
         this.usr_subscripted_month_changed = true; 
         this.record_changed = true;
         this.usr_subscripted_month = usr_subscripted_month;
      }
   }
   public Double getUsr_subscripted_month() {
      return this.usr_subscripted_month;
   }
   public void setUsr_terms_accepted(String usr_terms_accepted) {
      if (this.isForceUpdate() || 
               (this.usr_terms_accepted != null && !this.usr_terms_accepted.equals(usr_terms_accepted))  ||
               (usr_terms_accepted != null && !usr_terms_accepted.equals(this.usr_terms_accepted)) ){
         this.usr_terms_accepted_changed = true; 
         this.record_changed = true;
         this.usr_terms_accepted = usr_terms_accepted;
      }
   }
   public String getUsr_terms_accepted() {
      return this.usr_terms_accepted;
   }
   public void setUsr_terms_accepted_date(Date usr_terms_accepted_date) {
      if (this.isForceUpdate() || 
               (this.usr_terms_accepted_date != null && (usr_terms_accepted_date == null ||this.usr_terms_accepted_date.compareTo(usr_terms_accepted_date) != 0 )) ||
               (usr_terms_accepted_date != null && (this.usr_terms_accepted_date == null ||usr_terms_accepted_date.compareTo(this.usr_terms_accepted_date) != 0 ))){
         this.usr_terms_accepted_date_changed = true; 
         this.record_changed = true;
         this.usr_terms_accepted_date = usr_terms_accepted_date;
      }
   }
   public Date getUsr_terms_accepted_date() {
      return this.usr_terms_accepted_date;
   }
   public void setUsr_2fa_key(String usr_2fa_key) {
      if (this.isForceUpdate() || 
               (this.usr_2fa_key != null && !this.usr_2fa_key.equals(usr_2fa_key))  ||
               (usr_2fa_key != null && !usr_2fa_key.equals(this.usr_2fa_key)) ){
         this.usr_2fa_key_changed = true; 
         this.record_changed = true;
         this.usr_2fa_key = usr_2fa_key;
      }
   }
   public String getUsr_2fa_key() {
      return this.usr_2fa_key;
   }
   public void setUsr_2fa_key_confirm(String usr_2fa_key_confirm) {
      if (this.isForceUpdate() || 
               (this.usr_2fa_key_confirm != null && !this.usr_2fa_key_confirm.equals(usr_2fa_key_confirm))  ||
               (usr_2fa_key_confirm != null && !usr_2fa_key_confirm.equals(this.usr_2fa_key_confirm)) ){
         this.usr_2fa_key_confirm_changed = true; 
         this.record_changed = true;
         this.usr_2fa_key_confirm = usr_2fa_key_confirm;
      }
   }
   public String getUsr_2fa_key_confirm() {
      return this.usr_2fa_key_confirm;
   }
   public void setUsr_2fa_used(String usr_2fa_used) {
      if (this.isForceUpdate() || 
               (this.usr_2fa_used != null && !this.usr_2fa_used.equals(usr_2fa_used))  ||
               (usr_2fa_used != null && !usr_2fa_used.equals(this.usr_2fa_used)) ){
         this.usr_2fa_used_changed = true; 
         this.record_changed = true;
         this.usr_2fa_used = usr_2fa_used;
      }
   }
   public String getUsr_2fa_used() {
      return this.usr_2fa_used;
   }
   public void setUsr_confirmed_release_version(String usr_confirmed_release_version) {
      if (this.isForceUpdate() || 
               (this.usr_confirmed_release_version != null && !this.usr_confirmed_release_version.equals(usr_confirmed_release_version))  ||
               (usr_confirmed_release_version != null && !usr_confirmed_release_version.equals(this.usr_confirmed_release_version)) ){
         this.usr_confirmed_release_version_changed = true; 
         this.record_changed = true;
         this.usr_confirmed_release_version = usr_confirmed_release_version;
      }
   }
   public String getUsr_confirmed_release_version() {
      return this.usr_confirmed_release_version;
   }
   public void setUsr_profile_token(String usr_profile_token) {
      if (this.isForceUpdate() || 
               (this.usr_profile_token != null && !this.usr_profile_token.equals(usr_profile_token))  ||
               (usr_profile_token != null && !usr_profile_token.equals(this.usr_profile_token)) ){
         this.usr_profile_token_changed = true; 
         this.record_changed = true;
         this.usr_profile_token = usr_profile_token;
      }
   }
   public String getUsr_profile_token() {
      return this.usr_profile_token;
   }
   public void setUsr_per_id(Double usr_per_id) {
      if (this.isForceUpdate() || 
               (this.usr_per_id != null && !this.usr_per_id.equals(usr_per_id))  ||
               (usr_per_id != null && !usr_per_id.equals(this.usr_per_id)) ){
         this.usr_per_id_changed = true; 
         this.record_changed = true;
         this.usr_per_id = usr_per_id;
      }
   }
   public Double getUsr_per_id() {
      return this.usr_per_id;
   }
   public void setUsr_org_id(Double usr_org_id) {
      if (this.isForceUpdate() || 
               (this.usr_org_id != null && !this.usr_org_id.equals(usr_org_id))  ||
               (usr_org_id != null && !usr_org_id.equals(this.usr_org_id)) ){
         this.usr_org_id_changed = true; 
         this.record_changed = true;
         this.usr_org_id = usr_org_id;
      }
   }
   public Double getUsr_org_id() {
      return this.usr_org_id;
   }
   public void setUsr_rol_id(Double usr_rol_id) {
      if (this.isForceUpdate() || 
               (this.usr_rol_id != null && !this.usr_rol_id.equals(usr_rol_id))  ||
               (usr_rol_id != null && !usr_rol_id.equals(this.usr_rol_id)) ){
         this.usr_rol_id_changed = true; 
         this.record_changed = true;
         this.usr_rol_id = usr_rol_id;
      }
   }
   public Double getUsr_rol_id() {
      return this.usr_rol_id;
   }
   public void setUsr_photo_fil_id(Double usr_photo_fil_id) {
      if (this.isForceUpdate() || 
               (this.usr_photo_fil_id != null && !this.usr_photo_fil_id.equals(usr_photo_fil_id))  ||
               (usr_photo_fil_id != null && !usr_photo_fil_id.equals(this.usr_photo_fil_id)) ){
         this.usr_photo_fil_id_changed = true; 
         this.record_changed = true;
         this.usr_photo_fil_id = usr_photo_fil_id;
      }
   }
   public Double getUsr_photo_fil_id() {
      return this.usr_photo_fil_id;
   }
   public void setUsr_avatar_fil_id(Double usr_avatar_fil_id) {
      if (this.isForceUpdate() || 
               (this.usr_avatar_fil_id != null && !this.usr_avatar_fil_id.equals(usr_avatar_fil_id))  ||
               (usr_avatar_fil_id != null && !usr_avatar_fil_id.equals(this.usr_avatar_fil_id)) ){
         this.usr_avatar_fil_id_changed = true; 
         this.record_changed = true;
         this.usr_avatar_fil_id = usr_avatar_fil_id;
      }
   }
   public Double getUsr_avatar_fil_id() {
      return this.usr_avatar_fil_id;
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
            this.usr_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.usr_id;
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
      if (usr_username_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_username== null || EMPTY_STRING.equals(usr_username)) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_USERNAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (usr_username!= null && usr_username.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_USERNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (usr_email_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_email!= null && usr_email.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_email_confirmed_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_email_confirmed== null || EMPTY_STRING.equals(usr_email_confirmed)) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_EMAIL_CONFIRMED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (usr_email_confirmed!= null && usr_email_confirmed.length()>1) {
               throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_EMAIL_CONFIRMED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (usr_person_name_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_person_name!= null && usr_person_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PERSON_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_person_surname_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_person_surname!= null && usr_person_surname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PERSON_SURNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_salt_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_salt!= null && usr_salt.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_SALT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_password_algorithm_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_password_algorithm!= null && usr_password_algorithm.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PASSWORD_ALGORITHM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (usr_password_hash_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_password_hash!= null && usr_password_hash.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PASSWORD_HASH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_password_history_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_password_history!= null && usr_password_history.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PASSWORD_HISTORY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (usr_phone_number_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_phone_number!= null && usr_phone_number.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PHONE_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (usr_language_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_language!= null && usr_language.length()>20) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_LANGUAGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (usr_type_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_type!= null && usr_type.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (usr_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_date_from== null) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (usr_terms_accepted_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_terms_accepted!= null && usr_terms_accepted.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_TERMS_ACCEPTED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (usr_2fa_key_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_2fa_key!= null && usr_2fa_key.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_2FA_KEY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_2fa_key_confirm_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_2fa_key_confirm!= null && usr_2fa_key_confirm.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_2FA_KEY_CONFIRM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_2fa_used_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_2fa_used!= null && usr_2fa_used.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_2FA_USED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (usr_confirmed_release_version_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_confirmed_release_version!= null && usr_confirmed_release_version.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_CONFIRMED_RELEASE_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (usr_profile_token_changed || operation == Utils.OPERATION_INSERT) {
         if (usr_profile_token!= null && usr_profile_token.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "USR_PROFILE_TOKEN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USERS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_USERS SET ";
      boolean changedExists = false;      if (usr_username_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_USERNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_username);
      }
      if (usr_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_email);
      }
      if (usr_email_confirmed_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_EMAIL_CONFIRMED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_email_confirmed);
      }
      if (usr_person_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PERSON_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_person_name);
      }
      if (usr_person_surname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PERSON_SURNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_person_surname);
      }
      if (usr_wrong_login_attempts_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_WRONG_LOGIN_ATTEMPTS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_wrong_login_attempts);
      }
      if (usr_lock_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_LOCK_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_lock_date);
      }
      if (usr_salt_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_SALT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_salt);
      }
      if (usr_password_algorithm_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PASSWORD_ALGORITHM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_password_algorithm);
      }
      if (usr_password_hash_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PASSWORD_HASH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_password_hash);
      }
      if (usr_password_reset_req_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PASSWORD_RESET_REQ_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_password_reset_req_date);
      }
      if (usr_password_change_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PASSWORD_CHANGE_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_password_change_date);
      }
      if (usr_password_next_change_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PASSWORD_NEXT_CHANGE_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_password_next_change_date);
      }
      if (usr_password_history_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PASSWORD_HISTORY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_password_history);
      }
      if (usr_phone_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PHONE_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_phone_number);
      }
      if (usr_language_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_LANGUAGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_language);
      }
      if (usr_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_type);
      }
      if (usr_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_date_from);
      }
      if (usr_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_date_to);
      }
      if (usr_subscripted_month_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_SUBSCRIPTED_MONTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_subscripted_month);
      }
      if (usr_terms_accepted_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_TERMS_ACCEPTED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_terms_accepted);
      }
      if (usr_terms_accepted_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_TERMS_ACCEPTED_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_terms_accepted_date);
      }
      if (usr_2fa_key_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_2FA_KEY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_2fa_key);
      }
      if (usr_2fa_key_confirm_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_2FA_KEY_CONFIRM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_2fa_key_confirm);
      }
      if (usr_2fa_used_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_2FA_USED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_2fa_used);
      }
      if (usr_confirmed_release_version_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_CONFIRMED_RELEASE_VERSION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_confirmed_release_version);
      }
      if (usr_profile_token_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PROFILE_TOKEN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_profile_token);
      }
      if (usr_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_per_id);
      }
      if (usr_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_org_id);
      }
      if (usr_rol_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_ROL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_rol_id);
      }
      if (usr_photo_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_PHOTO_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_photo_fil_id);
      }
      if (usr_avatar_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"USR_AVATAR_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(usr_avatar_fil_id);
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
      answer = answer +" WHERE  USR_ID = ? ";
      updateStatementPart.addStatementParam(usr_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprUsersDAO\":{\"usr_id\": \""+getUsr_id()+"\", \"usr_username\": \""+getUsr_username()+"\", \"usr_email\": \""+getUsr_email()+"\", \"usr_email_confirmed\": \""+getUsr_email_confirmed()+"\", \"usr_person_name\": \""+getUsr_person_name()+"\", \"usr_person_surname\": \""+getUsr_person_surname()+"\", \"usr_wrong_login_attempts\": \""+getUsr_wrong_login_attempts()+"\", \"usr_lock_date\": \""+getUsr_lock_date()+"\", \"usr_salt\": \""+getUsr_salt()+"\", \"usr_password_algorithm\": \""+getUsr_password_algorithm()+"\", \"usr_password_hash\": \""+getUsr_password_hash()+"\", \"usr_password_reset_req_date\": \""+getUsr_password_reset_req_date()+"\", \"usr_password_change_date\": \""+getUsr_password_change_date()+"\", \"usr_password_next_change_date\": \""+getUsr_password_next_change_date()+"\", \"usr_password_history\": \""+getUsr_password_history()+"\", \"usr_phone_number\": \""+getUsr_phone_number()+"\", \"usr_language\": \""+getUsr_language()+"\", \"usr_type\": \""+getUsr_type()+"\", \"usr_date_from\": \""+getUsr_date_from()+"\", \"usr_date_to\": \""+getUsr_date_to()+"\", \"usr_subscripted_month\": \""+getUsr_subscripted_month()+"\", \"usr_terms_accepted\": \""+getUsr_terms_accepted()+"\", \"usr_terms_accepted_date\": \""+getUsr_terms_accepted_date()+"\", \"usr_2fa_key\": \""+getUsr_2fa_key()+"\", \"usr_2fa_key_confirm\": \""+getUsr_2fa_key_confirm()+"\", \"usr_2fa_used\": \""+getUsr_2fa_used()+"\", \"usr_confirmed_release_version\": \""+getUsr_confirmed_release_version()+"\", \"usr_profile_token\": \""+getUsr_profile_token()+"\", \"usr_per_id\": \""+getUsr_per_id()+"\", \"usr_org_id\": \""+getUsr_org_id()+"\", \"usr_rol_id\": \""+getUsr_rol_id()+"\", \"usr_photo_fil_id\": \""+getUsr_photo_fil_id()+"\", \"usr_avatar_fil_id\": \""+getUsr_avatar_fil_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}