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

public class SprSessionClosedDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_USER_SESSIONS_CLOSED.SEC_ID";
   private Double sec_id;
   private String sec_key;
   private String sec_username;
   private Date sec_login_time;
   private Date sec_logout_time;
   private String sec_logout_cause;
   private Date sec_last_action_time;
   private String sec_login_method;
   private String sec_ip;
   private String sec_os;
   private String sec_browser;
   private Double sec_screen_width;
   private Double sec_screen_height;
   private String sec_person_name;
   private String sec_person_surname;
   private String sec_role;
   private String sec_language;
   private String sec_terms_accepted;
   private Date sec_terms_accepted_date;
   private Double sec_subscripted_month;
   private Date sec_date_from;
   private Date sec_date_to;
   private String sec_2fa_confirmed;
   private String sec_2fa_code;
   private String sec_locked_form_code;
   private String sec_confirmed_release_version;
   private String sec_rol_type;
   private String sec_usr_type;
   private String sec_profile_token;
   private Double sec_rol_id;
   private Double sec_per_id;
   private Double sec_usr_id;
   private Double sec_org_id;
   private Date sec_password_reset_req_date;
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

   protected boolean sec_id_changed;
   protected boolean sec_key_changed;
   protected boolean sec_username_changed;
   protected boolean sec_login_time_changed;
   protected boolean sec_logout_time_changed;
   protected boolean sec_logout_cause_changed;
   protected boolean sec_last_action_time_changed;
   protected boolean sec_login_method_changed;
   protected boolean sec_ip_changed;
   protected boolean sec_os_changed;
   protected boolean sec_browser_changed;
   protected boolean sec_screen_width_changed;
   protected boolean sec_screen_height_changed;
   protected boolean sec_person_name_changed;
   protected boolean sec_person_surname_changed;
   protected boolean sec_role_changed;
   protected boolean sec_language_changed;
   protected boolean sec_terms_accepted_changed;
   protected boolean sec_terms_accepted_date_changed;
   protected boolean sec_subscripted_month_changed;
   protected boolean sec_date_from_changed;
   protected boolean sec_date_to_changed;
   protected boolean sec_2fa_confirmed_changed;
   protected boolean sec_2fa_code_changed;
   protected boolean sec_locked_form_code_changed;
   protected boolean sec_confirmed_release_version_changed;
   protected boolean sec_rol_type_changed;
   protected boolean sec_usr_type_changed;
   protected boolean sec_profile_token_changed;
   protected boolean sec_rol_id_changed;
   protected boolean sec_per_id_changed;
   protected boolean sec_usr_id_changed;
   protected boolean sec_org_id_changed;
   protected boolean sec_password_reset_req_date_changed;
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
   public SprSessionClosedDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprSessionClosedDAOGen(Double sec_id, String sec_key, String sec_username, Date sec_login_time, Date sec_logout_time, String sec_logout_cause, Date sec_last_action_time, String sec_login_method, String sec_ip, String sec_os, String sec_browser, Double sec_screen_width, Double sec_screen_height, String sec_person_name, String sec_person_surname, String sec_role, String sec_language, String sec_terms_accepted, Date sec_terms_accepted_date, Double sec_subscripted_month, Date sec_date_from, Date sec_date_to, String sec_2fa_confirmed, String sec_2fa_code, String sec_locked_form_code, String sec_confirmed_release_version, String sec_rol_type, String sec_usr_type, String sec_profile_token, Double sec_rol_id, Double sec_per_id, Double sec_usr_id, Double sec_org_id, Date sec_password_reset_req_date, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.sec_id = sec_id;
      this.sec_key = sec_key;
      this.sec_username = sec_username;
      this.sec_login_time = sec_login_time;
      this.sec_logout_time = sec_logout_time;
      this.sec_logout_cause = sec_logout_cause;
      this.sec_last_action_time = sec_last_action_time;
      this.sec_login_method = sec_login_method;
      this.sec_ip = sec_ip;
      this.sec_os = sec_os;
      this.sec_browser = sec_browser;
      this.sec_screen_width = sec_screen_width;
      this.sec_screen_height = sec_screen_height;
      this.sec_person_name = sec_person_name;
      this.sec_person_surname = sec_person_surname;
      this.sec_role = sec_role;
      this.sec_language = sec_language;
      this.sec_terms_accepted = sec_terms_accepted;
      this.sec_terms_accepted_date = sec_terms_accepted_date;
      this.sec_subscripted_month = sec_subscripted_month;
      this.sec_date_from = sec_date_from;
      this.sec_date_to = sec_date_to;
      this.sec_2fa_confirmed = sec_2fa_confirmed;
      this.sec_2fa_code = sec_2fa_code;
      this.sec_locked_form_code = sec_locked_form_code;
      this.sec_confirmed_release_version = sec_confirmed_release_version;
      this.sec_rol_type = sec_rol_type;
      this.sec_usr_type = sec_usr_type;
      this.sec_profile_token = sec_profile_token;
      this.sec_rol_id = sec_rol_id;
      this.sec_per_id = sec_per_id;
      this.sec_usr_id = sec_usr_id;
      this.sec_org_id = sec_org_id;
      this.sec_password_reset_req_date = sec_password_reset_req_date;
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
   public void copyValues(SprSessionClosedDAOGen obj) {
      this.setSec_id(obj.getSec_id());
      this.setSec_key(obj.getSec_key());
      this.setSec_username(obj.getSec_username());
      this.setSec_login_time(obj.getSec_login_time());
      this.setSec_logout_time(obj.getSec_logout_time());
      this.setSec_logout_cause(obj.getSec_logout_cause());
      this.setSec_last_action_time(obj.getSec_last_action_time());
      this.setSec_login_method(obj.getSec_login_method());
      this.setSec_ip(obj.getSec_ip());
      this.setSec_os(obj.getSec_os());
      this.setSec_browser(obj.getSec_browser());
      this.setSec_screen_width(obj.getSec_screen_width());
      this.setSec_screen_height(obj.getSec_screen_height());
      this.setSec_person_name(obj.getSec_person_name());
      this.setSec_person_surname(obj.getSec_person_surname());
      this.setSec_role(obj.getSec_role());
      this.setSec_language(obj.getSec_language());
      this.setSec_terms_accepted(obj.getSec_terms_accepted());
      this.setSec_terms_accepted_date(obj.getSec_terms_accepted_date());
      this.setSec_subscripted_month(obj.getSec_subscripted_month());
      this.setSec_date_from(obj.getSec_date_from());
      this.setSec_date_to(obj.getSec_date_to());
      this.setSec_2fa_confirmed(obj.getSec_2fa_confirmed());
      this.setSec_2fa_code(obj.getSec_2fa_code());
      this.setSec_locked_form_code(obj.getSec_locked_form_code());
      this.setSec_confirmed_release_version(obj.getSec_confirmed_release_version());
      this.setSec_rol_type(obj.getSec_rol_type());
      this.setSec_usr_type(obj.getSec_usr_type());
      this.setSec_profile_token(obj.getSec_profile_token());
      this.setSec_rol_id(obj.getSec_rol_id());
      this.setSec_per_id(obj.getSec_per_id());
      this.setSec_usr_id(obj.getSec_usr_id());
      this.setSec_org_id(obj.getSec_org_id());
      this.setSec_password_reset_req_date(obj.getSec_password_reset_req_date());
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
      this.sec_id_changed = false;
      this.sec_key_changed = false;
      this.sec_username_changed = false;
      this.sec_login_time_changed = false;
      this.sec_logout_time_changed = false;
      this.sec_logout_cause_changed = false;
      this.sec_last_action_time_changed = false;
      this.sec_login_method_changed = false;
      this.sec_ip_changed = false;
      this.sec_os_changed = false;
      this.sec_browser_changed = false;
      this.sec_screen_width_changed = false;
      this.sec_screen_height_changed = false;
      this.sec_person_name_changed = false;
      this.sec_person_surname_changed = false;
      this.sec_role_changed = false;
      this.sec_language_changed = false;
      this.sec_terms_accepted_changed = false;
      this.sec_terms_accepted_date_changed = false;
      this.sec_subscripted_month_changed = false;
      this.sec_date_from_changed = false;
      this.sec_date_to_changed = false;
      this.sec_2fa_confirmed_changed = false;
      this.sec_2fa_code_changed = false;
      this.sec_locked_form_code_changed = false;
      this.sec_confirmed_release_version_changed = false;
      this.sec_rol_type_changed = false;
      this.sec_usr_type_changed = false;
      this.sec_profile_token_changed = false;
      this.sec_rol_id_changed = false;
      this.sec_per_id_changed = false;
      this.sec_usr_id_changed = false;
      this.sec_org_id_changed = false;
      this.sec_password_reset_req_date_changed = false;
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
   public void setSec_id(Double sec_id) {
      if (this.isForceUpdate() || 
               (this.sec_id != null && !this.sec_id.equals(sec_id))  ||
               (sec_id != null && !sec_id.equals(this.sec_id)) ){
         this.sec_id_changed = true; 
         this.record_changed = true;
         this.sec_id = sec_id;
      }
   }
   public Double getSec_id() {
      return this.sec_id;
   }
   public void setSec_key(String sec_key) {
      if (this.isForceUpdate() || 
               (this.sec_key != null && !this.sec_key.equals(sec_key))  ||
               (sec_key != null && !sec_key.equals(this.sec_key)) ){
         this.sec_key_changed = true; 
         this.record_changed = true;
         this.sec_key = sec_key;
      }
   }
   public String getSec_key() {
      return this.sec_key;
   }
   public void setSec_username(String sec_username) {
      if (this.isForceUpdate() || 
               (this.sec_username != null && !this.sec_username.equals(sec_username))  ||
               (sec_username != null && !sec_username.equals(this.sec_username)) ){
         this.sec_username_changed = true; 
         this.record_changed = true;
         this.sec_username = sec_username;
      }
   }
   public String getSec_username() {
      return this.sec_username;
   }
   public void setSec_login_time(Date sec_login_time) {
      if (this.isForceUpdate() || 
               (this.sec_login_time != null && (sec_login_time == null ||this.sec_login_time.compareTo(sec_login_time) != 0 )) ||
               (sec_login_time != null && (this.sec_login_time == null ||sec_login_time.compareTo(this.sec_login_time) != 0 ))){
         this.sec_login_time_changed = true; 
         this.record_changed = true;
         this.sec_login_time = sec_login_time;
      }
   }
   public Date getSec_login_time() {
      return this.sec_login_time;
   }
   public void setSec_logout_time(Date sec_logout_time) {
      if (this.isForceUpdate() || 
               (this.sec_logout_time != null && (sec_logout_time == null ||this.sec_logout_time.compareTo(sec_logout_time) != 0 )) ||
               (sec_logout_time != null && (this.sec_logout_time == null ||sec_logout_time.compareTo(this.sec_logout_time) != 0 ))){
         this.sec_logout_time_changed = true; 
         this.record_changed = true;
         this.sec_logout_time = sec_logout_time;
      }
   }
   public Date getSec_logout_time() {
      return this.sec_logout_time;
   }
   public void setSec_logout_cause(String sec_logout_cause) {
      if (this.isForceUpdate() || 
               (this.sec_logout_cause != null && !this.sec_logout_cause.equals(sec_logout_cause))  ||
               (sec_logout_cause != null && !sec_logout_cause.equals(this.sec_logout_cause)) ){
         this.sec_logout_cause_changed = true; 
         this.record_changed = true;
         this.sec_logout_cause = sec_logout_cause;
      }
   }
   public String getSec_logout_cause() {
      return this.sec_logout_cause;
   }
   public void setSec_last_action_time(Date sec_last_action_time) {
      if (this.isForceUpdate() || 
               (this.sec_last_action_time != null && (sec_last_action_time == null ||this.sec_last_action_time.compareTo(sec_last_action_time) != 0 )) ||
               (sec_last_action_time != null && (this.sec_last_action_time == null ||sec_last_action_time.compareTo(this.sec_last_action_time) != 0 ))){
         this.sec_last_action_time_changed = true; 
         this.record_changed = true;
         this.sec_last_action_time = sec_last_action_time;
      }
   }
   public Date getSec_last_action_time() {
      return this.sec_last_action_time;
   }
   public void setSec_login_method(String sec_login_method) {
      if (this.isForceUpdate() || 
               (this.sec_login_method != null && !this.sec_login_method.equals(sec_login_method))  ||
               (sec_login_method != null && !sec_login_method.equals(this.sec_login_method)) ){
         this.sec_login_method_changed = true; 
         this.record_changed = true;
         this.sec_login_method = sec_login_method;
      }
   }
   public String getSec_login_method() {
      return this.sec_login_method;
   }
   public void setSec_ip(String sec_ip) {
      if (this.isForceUpdate() || 
               (this.sec_ip != null && !this.sec_ip.equals(sec_ip))  ||
               (sec_ip != null && !sec_ip.equals(this.sec_ip)) ){
         this.sec_ip_changed = true; 
         this.record_changed = true;
         this.sec_ip = sec_ip;
      }
   }
   public String getSec_ip() {
      return this.sec_ip;
   }
   public void setSec_os(String sec_os) {
      if (this.isForceUpdate() || 
               (this.sec_os != null && !this.sec_os.equals(sec_os))  ||
               (sec_os != null && !sec_os.equals(this.sec_os)) ){
         this.sec_os_changed = true; 
         this.record_changed = true;
         this.sec_os = sec_os;
      }
   }
   public String getSec_os() {
      return this.sec_os;
   }
   public void setSec_browser(String sec_browser) {
      if (this.isForceUpdate() || 
               (this.sec_browser != null && !this.sec_browser.equals(sec_browser))  ||
               (sec_browser != null && !sec_browser.equals(this.sec_browser)) ){
         this.sec_browser_changed = true; 
         this.record_changed = true;
         this.sec_browser = sec_browser;
      }
   }
   public String getSec_browser() {
      return this.sec_browser;
   }
   public void setSec_screen_width(Double sec_screen_width) {
      if (this.isForceUpdate() || 
               (this.sec_screen_width != null && !this.sec_screen_width.equals(sec_screen_width))  ||
               (sec_screen_width != null && !sec_screen_width.equals(this.sec_screen_width)) ){
         this.sec_screen_width_changed = true; 
         this.record_changed = true;
         this.sec_screen_width = sec_screen_width;
      }
   }
   public Double getSec_screen_width() {
      return this.sec_screen_width;
   }
   public void setSec_screen_height(Double sec_screen_height) {
      if (this.isForceUpdate() || 
               (this.sec_screen_height != null && !this.sec_screen_height.equals(sec_screen_height))  ||
               (sec_screen_height != null && !sec_screen_height.equals(this.sec_screen_height)) ){
         this.sec_screen_height_changed = true; 
         this.record_changed = true;
         this.sec_screen_height = sec_screen_height;
      }
   }
   public Double getSec_screen_height() {
      return this.sec_screen_height;
   }
   public void setSec_person_name(String sec_person_name) {
      if (this.isForceUpdate() || 
               (this.sec_person_name != null && !this.sec_person_name.equals(sec_person_name))  ||
               (sec_person_name != null && !sec_person_name.equals(this.sec_person_name)) ){
         this.sec_person_name_changed = true; 
         this.record_changed = true;
         this.sec_person_name = sec_person_name;
      }
   }
   public String getSec_person_name() {
      return this.sec_person_name;
   }
   public void setSec_person_surname(String sec_person_surname) {
      if (this.isForceUpdate() || 
               (this.sec_person_surname != null && !this.sec_person_surname.equals(sec_person_surname))  ||
               (sec_person_surname != null && !sec_person_surname.equals(this.sec_person_surname)) ){
         this.sec_person_surname_changed = true; 
         this.record_changed = true;
         this.sec_person_surname = sec_person_surname;
      }
   }
   public String getSec_person_surname() {
      return this.sec_person_surname;
   }
   public void setSec_role(String sec_role) {
      if (this.isForceUpdate() || 
               (this.sec_role != null && !this.sec_role.equals(sec_role))  ||
               (sec_role != null && !sec_role.equals(this.sec_role)) ){
         this.sec_role_changed = true; 
         this.record_changed = true;
         this.sec_role = sec_role;
      }
   }
   public String getSec_role() {
      return this.sec_role;
   }
   public void setSec_language(String sec_language) {
      if (this.isForceUpdate() || 
               (this.sec_language != null && !this.sec_language.equals(sec_language))  ||
               (sec_language != null && !sec_language.equals(this.sec_language)) ){
         this.sec_language_changed = true; 
         this.record_changed = true;
         this.sec_language = sec_language;
      }
   }
   public String getSec_language() {
      return this.sec_language;
   }
   public void setSec_terms_accepted(String sec_terms_accepted) {
      if (this.isForceUpdate() || 
               (this.sec_terms_accepted != null && !this.sec_terms_accepted.equals(sec_terms_accepted))  ||
               (sec_terms_accepted != null && !sec_terms_accepted.equals(this.sec_terms_accepted)) ){
         this.sec_terms_accepted_changed = true; 
         this.record_changed = true;
         this.sec_terms_accepted = sec_terms_accepted;
      }
   }
   public String getSec_terms_accepted() {
      return this.sec_terms_accepted;
   }
   public void setSec_terms_accepted_date(Date sec_terms_accepted_date) {
      if (this.isForceUpdate() || 
               (this.sec_terms_accepted_date != null && (sec_terms_accepted_date == null ||this.sec_terms_accepted_date.compareTo(sec_terms_accepted_date) != 0 )) ||
               (sec_terms_accepted_date != null && (this.sec_terms_accepted_date == null ||sec_terms_accepted_date.compareTo(this.sec_terms_accepted_date) != 0 ))){
         this.sec_terms_accepted_date_changed = true; 
         this.record_changed = true;
         this.sec_terms_accepted_date = sec_terms_accepted_date;
      }
   }
   public Date getSec_terms_accepted_date() {
      return this.sec_terms_accepted_date;
   }
   public void setSec_subscripted_month(Double sec_subscripted_month) {
      if (this.isForceUpdate() || 
               (this.sec_subscripted_month != null && !this.sec_subscripted_month.equals(sec_subscripted_month))  ||
               (sec_subscripted_month != null && !sec_subscripted_month.equals(this.sec_subscripted_month)) ){
         this.sec_subscripted_month_changed = true; 
         this.record_changed = true;
         this.sec_subscripted_month = sec_subscripted_month;
      }
   }
   public Double getSec_subscripted_month() {
      return this.sec_subscripted_month;
   }
   public void setSec_date_from(Date sec_date_from) {
      if (this.isForceUpdate() || 
               (this.sec_date_from != null && (sec_date_from == null ||this.sec_date_from.compareTo(sec_date_from) != 0 )) ||
               (sec_date_from != null && (this.sec_date_from == null ||sec_date_from.compareTo(this.sec_date_from) != 0 ))){
         this.sec_date_from_changed = true; 
         this.record_changed = true;
         this.sec_date_from = sec_date_from;
      }
   }
   public Date getSec_date_from() {
      return this.sec_date_from;
   }
   public void setSec_date_to(Date sec_date_to) {
      if (this.isForceUpdate() || 
               (this.sec_date_to != null && (sec_date_to == null ||this.sec_date_to.compareTo(sec_date_to) != 0 )) ||
               (sec_date_to != null && (this.sec_date_to == null ||sec_date_to.compareTo(this.sec_date_to) != 0 ))){
         this.sec_date_to_changed = true; 
         this.record_changed = true;
         this.sec_date_to = sec_date_to;
      }
   }
   public Date getSec_date_to() {
      return this.sec_date_to;
   }
   public void setSec_2fa_confirmed(String sec_2fa_confirmed) {
      if (this.isForceUpdate() || 
               (this.sec_2fa_confirmed != null && !this.sec_2fa_confirmed.equals(sec_2fa_confirmed))  ||
               (sec_2fa_confirmed != null && !sec_2fa_confirmed.equals(this.sec_2fa_confirmed)) ){
         this.sec_2fa_confirmed_changed = true; 
         this.record_changed = true;
         this.sec_2fa_confirmed = sec_2fa_confirmed;
      }
   }
   public String getSec_2fa_confirmed() {
      return this.sec_2fa_confirmed;
   }
   public void setSec_2fa_code(String sec_2fa_code) {
      if (this.isForceUpdate() || 
               (this.sec_2fa_code != null && !this.sec_2fa_code.equals(sec_2fa_code))  ||
               (sec_2fa_code != null && !sec_2fa_code.equals(this.sec_2fa_code)) ){
         this.sec_2fa_code_changed = true; 
         this.record_changed = true;
         this.sec_2fa_code = sec_2fa_code;
      }
   }
   public String getSec_2fa_code() {
      return this.sec_2fa_code;
   }
   public void setSec_locked_form_code(String sec_locked_form_code) {
      if (this.isForceUpdate() || 
               (this.sec_locked_form_code != null && !this.sec_locked_form_code.equals(sec_locked_form_code))  ||
               (sec_locked_form_code != null && !sec_locked_form_code.equals(this.sec_locked_form_code)) ){
         this.sec_locked_form_code_changed = true; 
         this.record_changed = true;
         this.sec_locked_form_code = sec_locked_form_code;
      }
   }
   public String getSec_locked_form_code() {
      return this.sec_locked_form_code;
   }
   public void setSec_confirmed_release_version(String sec_confirmed_release_version) {
      if (this.isForceUpdate() || 
               (this.sec_confirmed_release_version != null && !this.sec_confirmed_release_version.equals(sec_confirmed_release_version))  ||
               (sec_confirmed_release_version != null && !sec_confirmed_release_version.equals(this.sec_confirmed_release_version)) ){
         this.sec_confirmed_release_version_changed = true; 
         this.record_changed = true;
         this.sec_confirmed_release_version = sec_confirmed_release_version;
      }
   }
   public String getSec_confirmed_release_version() {
      return this.sec_confirmed_release_version;
   }
   public void setSec_rol_type(String sec_rol_type) {
      if (this.isForceUpdate() || 
               (this.sec_rol_type != null && !this.sec_rol_type.equals(sec_rol_type))  ||
               (sec_rol_type != null && !sec_rol_type.equals(this.sec_rol_type)) ){
         this.sec_rol_type_changed = true; 
         this.record_changed = true;
         this.sec_rol_type = sec_rol_type;
      }
   }
   public String getSec_rol_type() {
      return this.sec_rol_type;
   }
   public void setSec_usr_type(String sec_usr_type) {
      if (this.isForceUpdate() || 
               (this.sec_usr_type != null && !this.sec_usr_type.equals(sec_usr_type))  ||
               (sec_usr_type != null && !sec_usr_type.equals(this.sec_usr_type)) ){
         this.sec_usr_type_changed = true; 
         this.record_changed = true;
         this.sec_usr_type = sec_usr_type;
      }
   }
   public String getSec_usr_type() {
      return this.sec_usr_type;
   }
   public void setSec_profile_token(String sec_profile_token) {
      if (this.isForceUpdate() || 
               (this.sec_profile_token != null && !this.sec_profile_token.equals(sec_profile_token))  ||
               (sec_profile_token != null && !sec_profile_token.equals(this.sec_profile_token)) ){
         this.sec_profile_token_changed = true; 
         this.record_changed = true;
         this.sec_profile_token = sec_profile_token;
      }
   }
   public String getSec_profile_token() {
      return this.sec_profile_token;
   }
   public void setSec_rol_id(Double sec_rol_id) {
      if (this.isForceUpdate() || 
               (this.sec_rol_id != null && !this.sec_rol_id.equals(sec_rol_id))  ||
               (sec_rol_id != null && !sec_rol_id.equals(this.sec_rol_id)) ){
         this.sec_rol_id_changed = true; 
         this.record_changed = true;
         this.sec_rol_id = sec_rol_id;
      }
   }
   public Double getSec_rol_id() {
      return this.sec_rol_id;
   }
   public void setSec_per_id(Double sec_per_id) {
      if (this.isForceUpdate() || 
               (this.sec_per_id != null && !this.sec_per_id.equals(sec_per_id))  ||
               (sec_per_id != null && !sec_per_id.equals(this.sec_per_id)) ){
         this.sec_per_id_changed = true; 
         this.record_changed = true;
         this.sec_per_id = sec_per_id;
      }
   }
   public Double getSec_per_id() {
      return this.sec_per_id;
   }
   public void setSec_usr_id(Double sec_usr_id) {
      if (this.isForceUpdate() || 
               (this.sec_usr_id != null && !this.sec_usr_id.equals(sec_usr_id))  ||
               (sec_usr_id != null && !sec_usr_id.equals(this.sec_usr_id)) ){
         this.sec_usr_id_changed = true; 
         this.record_changed = true;
         this.sec_usr_id = sec_usr_id;
      }
   }
   public Double getSec_usr_id() {
      return this.sec_usr_id;
   }
   public void setSec_org_id(Double sec_org_id) {
      if (this.isForceUpdate() || 
               (this.sec_org_id != null && !this.sec_org_id.equals(sec_org_id))  ||
               (sec_org_id != null && !sec_org_id.equals(this.sec_org_id)) ){
         this.sec_org_id_changed = true; 
         this.record_changed = true;
         this.sec_org_id = sec_org_id;
      }
   }
   public Double getSec_org_id() {
      return this.sec_org_id;
   }
   public void setSec_password_reset_req_date(Date sec_password_reset_req_date) {
      if (this.isForceUpdate() || 
               (this.sec_password_reset_req_date != null && (sec_password_reset_req_date == null ||this.sec_password_reset_req_date.compareTo(sec_password_reset_req_date) != 0 )) ||
               (sec_password_reset_req_date != null && (this.sec_password_reset_req_date == null ||sec_password_reset_req_date.compareTo(this.sec_password_reset_req_date) != 0 ))){
         this.sec_password_reset_req_date_changed = true; 
         this.record_changed = true;
         this.sec_password_reset_req_date = sec_password_reset_req_date;
      }
   }
   public Date getSec_password_reset_req_date() {
      return this.sec_password_reset_req_date;
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
            this.sec_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.sec_id;
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
      if (sec_key_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_key== null || EMPTY_STRING.equals(sec_key)) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_KEY", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sec_key!= null && sec_key.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_KEY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (sec_username_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_username!= null && sec_username.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_USERNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_logout_cause_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_logout_cause!= null && sec_logout_cause.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_LOGOUT_CAUSE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (sec_login_method_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_login_method!= null && sec_login_method.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_LOGIN_METHOD", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (sec_ip_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_ip!= null && sec_ip.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_IP", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_os_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_os!= null && sec_os.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_OS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_browser_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_browser!= null && sec_browser.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_BROWSER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_screen_width_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_screen_width!= null && (""+sec_screen_width.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_SCREEN_WIDTH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (sec_screen_height_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_screen_height!= null && (""+sec_screen_height.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_SCREEN_HEIGHT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (sec_person_name_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_person_name!= null && sec_person_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_PERSON_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_person_surname_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_person_surname!= null && sec_person_surname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_PERSON_SURNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_role_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_role!= null && sec_role.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_ROLE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (sec_language_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_language!= null && sec_language.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_LANGUAGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (sec_terms_accepted_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_terms_accepted!= null && sec_terms_accepted.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_TERMS_ACCEPTED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (sec_subscripted_month_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_subscripted_month!= null && (""+sec_subscripted_month.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_SUBSCRIPTED_MONTH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (sec_2fa_confirmed_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_2fa_confirmed!= null && sec_2fa_confirmed.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_2FA_CONFIRMED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (sec_2fa_code_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_2fa_code!= null && sec_2fa_code.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_2FA_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_locked_form_code_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_locked_form_code!= null && sec_locked_form_code.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_LOCKED_FORM_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (sec_confirmed_release_version_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_confirmed_release_version!= null && sec_confirmed_release_version.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_CONFIRMED_RELEASE_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sec_rol_type_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_rol_type!= null && sec_rol_type.length()>240) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_ROL_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"240"));
         }
      }
      if (sec_usr_type_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_usr_type!= null && sec_usr_type.length()>240) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_USR_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"240"));
         }
      }
      if (sec_profile_token_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_profile_token!= null && sec_profile_token.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_PROFILE_TOKEN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (sec_rol_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_rol_id!= null && (""+sec_rol_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_ROL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (sec_per_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_per_id!= null && (""+sec_per_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_PER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (sec_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_usr_id!= null && (""+sec_usr_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (sec_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sec_org_id!= null && (""+sec_org_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "SEC_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_CLOSED", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_USER_SESSIONS_CLOSED SET ";
      boolean changedExists = false;      if (sec_key_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_KEY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_key);
      }
      if (sec_username_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_USERNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_username);
      }
      if (sec_login_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LOGIN_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sec_login_time);
      }
      if (sec_logout_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LOGOUT_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sec_logout_time);
      }
      if (sec_logout_cause_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LOGOUT_CAUSE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_logout_cause);
      }
      if (sec_last_action_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LAST_ACTION_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sec_last_action_time);
      }
      if (sec_login_method_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LOGIN_METHOD = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_login_method);
      }
      if (sec_ip_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_IP = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_ip);
      }
      if (sec_os_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_OS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_os);
      }
      if (sec_browser_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_BROWSER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_browser);
      }
      if (sec_screen_width_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_SCREEN_WIDTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_screen_width);
      }
      if (sec_screen_height_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_SCREEN_HEIGHT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_screen_height);
      }
      if (sec_person_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_PERSON_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_person_name);
      }
      if (sec_person_surname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_PERSON_SURNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_person_surname);
      }
      if (sec_role_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_ROLE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_role);
      }
      if (sec_language_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LANGUAGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_language);
      }
      if (sec_terms_accepted_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_TERMS_ACCEPTED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_terms_accepted);
      }
      if (sec_terms_accepted_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_TERMS_ACCEPTED_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_terms_accepted_date);
      }
      if (sec_subscripted_month_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_SUBSCRIPTED_MONTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_subscripted_month);
      }
      if (sec_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_date_from);
      }
      if (sec_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_date_to);
      }
      if (sec_2fa_confirmed_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_2FA_CONFIRMED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_2fa_confirmed);
      }
      if (sec_2fa_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_2FA_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_2fa_code);
      }
      if (sec_locked_form_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_LOCKED_FORM_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_locked_form_code);
      }
      if (sec_confirmed_release_version_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_CONFIRMED_RELEASE_VERSION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_confirmed_release_version);
      }
      if (sec_rol_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_ROL_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_rol_type);
      }
      if (sec_usr_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_USR_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_usr_type);
      }
      if (sec_profile_token_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_PROFILE_TOKEN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_profile_token);
      }
      if (sec_rol_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_ROL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_rol_id);
      }
      if (sec_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_per_id);
      }
      if (sec_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_usr_id);
      }
      if (sec_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sec_org_id);
      }
      if (sec_password_reset_req_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SEC_PASSWORD_RESET_REQ_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sec_password_reset_req_date);
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
      answer = answer +" WHERE  SEC_ID = ? ";
      updateStatementPart.addStatementParam(sec_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprSessionClosedDAO\":{\"sec_id\": \""+getSec_id()+"\", \"sec_key\": \""+getSec_key()+"\", \"sec_username\": \""+getSec_username()+"\", \"sec_login_time\": \""+getSec_login_time()+"\", \"sec_logout_time\": \""+getSec_logout_time()+"\", \"sec_logout_cause\": \""+getSec_logout_cause()+"\", \"sec_last_action_time\": \""+getSec_last_action_time()+"\", \"sec_login_method\": \""+getSec_login_method()+"\", \"sec_ip\": \""+getSec_ip()+"\", \"sec_os\": \""+getSec_os()+"\", \"sec_browser\": \""+getSec_browser()+"\", \"sec_screen_width\": \""+getSec_screen_width()+"\", \"sec_screen_height\": \""+getSec_screen_height()+"\", \"sec_person_name\": \""+getSec_person_name()+"\", \"sec_person_surname\": \""+getSec_person_surname()+"\", \"sec_role\": \""+getSec_role()+"\", \"sec_language\": \""+getSec_language()+"\", \"sec_terms_accepted\": \""+getSec_terms_accepted()+"\", \"sec_terms_accepted_date\": \""+getSec_terms_accepted_date()+"\", \"sec_subscripted_month\": \""+getSec_subscripted_month()+"\", \"sec_date_from\": \""+getSec_date_from()+"\", \"sec_date_to\": \""+getSec_date_to()+"\", \"sec_2fa_confirmed\": \""+getSec_2fa_confirmed()+"\", \"sec_2fa_code\": \""+getSec_2fa_code()+"\", \"sec_locked_form_code\": \""+getSec_locked_form_code()+"\", \"sec_confirmed_release_version\": \""+getSec_confirmed_release_version()+"\", \"sec_rol_type\": \""+getSec_rol_type()+"\", \"sec_usr_type\": \""+getSec_usr_type()+"\", \"sec_profile_token\": \""+getSec_profile_token()+"\", \"sec_rol_id\": \""+getSec_rol_id()+"\", \"sec_per_id\": \""+getSec_per_id()+"\", \"sec_usr_id\": \""+getSec_usr_id()+"\", \"sec_org_id\": \""+getSec_org_id()+"\", \"sec_password_reset_req_date\": \""+getSec_password_reset_req_date()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}