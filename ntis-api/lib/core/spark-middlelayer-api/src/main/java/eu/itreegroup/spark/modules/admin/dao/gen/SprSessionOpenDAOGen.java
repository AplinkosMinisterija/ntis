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

public class SprSessionOpenDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_USER_SESSIONS_OPEN.SES_ID";
   private Double ses_id;
   private String ses_key;
   private String ses_username;
   private Date ses_login_time;
   private Date ses_logout_time;
   private String ses_logout_cause;
   private Date ses_last_action_time;
   private String ses_login_method;
   private String ses_ip;
   private String ses_os;
   private String ses_browser;
   private Double ses_screen_width;
   private Double ses_screen_height;
   private String ses_person_name;
   private String ses_person_surname;
   private String ses_role;
   private String ses_language;
   private String ses_terms_accepted;
   private Date ses_terms_accepted_date;
   private Double ses_subscripted_month;
   private Date ses_date_from;
   private Date ses_date_to;
   private String ses_2fa_confirmed;
   private String ses_2fa_code;
   private String ses_locked_form_code;
   private String ses_confirmed_release_version;
   private String ses_rol_type;
   private String ses_usr_type;
   private String ses_profile_token;
   private Double ses_rol_id;
   private Double ses_per_id;
   private Double ses_usr_id;
   private Double ses_org_id;
   private Date ses_password_reset_req_date;
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

   protected boolean ses_id_changed;
   protected boolean ses_key_changed;
   protected boolean ses_username_changed;
   protected boolean ses_login_time_changed;
   protected boolean ses_logout_time_changed;
   protected boolean ses_logout_cause_changed;
   protected boolean ses_last_action_time_changed;
   protected boolean ses_login_method_changed;
   protected boolean ses_ip_changed;
   protected boolean ses_os_changed;
   protected boolean ses_browser_changed;
   protected boolean ses_screen_width_changed;
   protected boolean ses_screen_height_changed;
   protected boolean ses_person_name_changed;
   protected boolean ses_person_surname_changed;
   protected boolean ses_role_changed;
   protected boolean ses_language_changed;
   protected boolean ses_terms_accepted_changed;
   protected boolean ses_terms_accepted_date_changed;
   protected boolean ses_subscripted_month_changed;
   protected boolean ses_date_from_changed;
   protected boolean ses_date_to_changed;
   protected boolean ses_2fa_confirmed_changed;
   protected boolean ses_2fa_code_changed;
   protected boolean ses_locked_form_code_changed;
   protected boolean ses_confirmed_release_version_changed;
   protected boolean ses_rol_type_changed;
   protected boolean ses_usr_type_changed;
   protected boolean ses_profile_token_changed;
   protected boolean ses_rol_id_changed;
   protected boolean ses_per_id_changed;
   protected boolean ses_usr_id_changed;
   protected boolean ses_org_id_changed;
   protected boolean ses_password_reset_req_date_changed;
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
   public SprSessionOpenDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprSessionOpenDAOGen(Double ses_id, String ses_key, String ses_username, Date ses_login_time, Date ses_logout_time, String ses_logout_cause, Date ses_last_action_time, String ses_login_method, String ses_ip, String ses_os, String ses_browser, Double ses_screen_width, Double ses_screen_height, String ses_person_name, String ses_person_surname, String ses_role, String ses_language, String ses_terms_accepted, Date ses_terms_accepted_date, Double ses_subscripted_month, Date ses_date_from, Date ses_date_to, String ses_2fa_confirmed, String ses_2fa_code, String ses_locked_form_code, String ses_confirmed_release_version, String ses_rol_type, String ses_usr_type, String ses_profile_token, Double ses_rol_id, Double ses_per_id, Double ses_usr_id, Double ses_org_id, Date ses_password_reset_req_date, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.ses_id = ses_id;
      this.ses_key = ses_key;
      this.ses_username = ses_username;
      this.ses_login_time = ses_login_time;
      this.ses_logout_time = ses_logout_time;
      this.ses_logout_cause = ses_logout_cause;
      this.ses_last_action_time = ses_last_action_time;
      this.ses_login_method = ses_login_method;
      this.ses_ip = ses_ip;
      this.ses_os = ses_os;
      this.ses_browser = ses_browser;
      this.ses_screen_width = ses_screen_width;
      this.ses_screen_height = ses_screen_height;
      this.ses_person_name = ses_person_name;
      this.ses_person_surname = ses_person_surname;
      this.ses_role = ses_role;
      this.ses_language = ses_language;
      this.ses_terms_accepted = ses_terms_accepted;
      this.ses_terms_accepted_date = ses_terms_accepted_date;
      this.ses_subscripted_month = ses_subscripted_month;
      this.ses_date_from = ses_date_from;
      this.ses_date_to = ses_date_to;
      this.ses_2fa_confirmed = ses_2fa_confirmed;
      this.ses_2fa_code = ses_2fa_code;
      this.ses_locked_form_code = ses_locked_form_code;
      this.ses_confirmed_release_version = ses_confirmed_release_version;
      this.ses_rol_type = ses_rol_type;
      this.ses_usr_type = ses_usr_type;
      this.ses_profile_token = ses_profile_token;
      this.ses_rol_id = ses_rol_id;
      this.ses_per_id = ses_per_id;
      this.ses_usr_id = ses_usr_id;
      this.ses_org_id = ses_org_id;
      this.ses_password_reset_req_date = ses_password_reset_req_date;
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
   public void copyValues(SprSessionOpenDAOGen obj) {
      this.setSes_id(obj.getSes_id());
      this.setSes_key(obj.getSes_key());
      this.setSes_username(obj.getSes_username());
      this.setSes_login_time(obj.getSes_login_time());
      this.setSes_logout_time(obj.getSes_logout_time());
      this.setSes_logout_cause(obj.getSes_logout_cause());
      this.setSes_last_action_time(obj.getSes_last_action_time());
      this.setSes_login_method(obj.getSes_login_method());
      this.setSes_ip(obj.getSes_ip());
      this.setSes_os(obj.getSes_os());
      this.setSes_browser(obj.getSes_browser());
      this.setSes_screen_width(obj.getSes_screen_width());
      this.setSes_screen_height(obj.getSes_screen_height());
      this.setSes_person_name(obj.getSes_person_name());
      this.setSes_person_surname(obj.getSes_person_surname());
      this.setSes_role(obj.getSes_role());
      this.setSes_language(obj.getSes_language());
      this.setSes_terms_accepted(obj.getSes_terms_accepted());
      this.setSes_terms_accepted_date(obj.getSes_terms_accepted_date());
      this.setSes_subscripted_month(obj.getSes_subscripted_month());
      this.setSes_date_from(obj.getSes_date_from());
      this.setSes_date_to(obj.getSes_date_to());
      this.setSes_2fa_confirmed(obj.getSes_2fa_confirmed());
      this.setSes_2fa_code(obj.getSes_2fa_code());
      this.setSes_locked_form_code(obj.getSes_locked_form_code());
      this.setSes_confirmed_release_version(obj.getSes_confirmed_release_version());
      this.setSes_rol_type(obj.getSes_rol_type());
      this.setSes_usr_type(obj.getSes_usr_type());
      this.setSes_profile_token(obj.getSes_profile_token());
      this.setSes_rol_id(obj.getSes_rol_id());
      this.setSes_per_id(obj.getSes_per_id());
      this.setSes_usr_id(obj.getSes_usr_id());
      this.setSes_org_id(obj.getSes_org_id());
      this.setSes_password_reset_req_date(obj.getSes_password_reset_req_date());
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
      this.ses_id_changed = false;
      this.ses_key_changed = false;
      this.ses_username_changed = false;
      this.ses_login_time_changed = false;
      this.ses_logout_time_changed = false;
      this.ses_logout_cause_changed = false;
      this.ses_last_action_time_changed = false;
      this.ses_login_method_changed = false;
      this.ses_ip_changed = false;
      this.ses_os_changed = false;
      this.ses_browser_changed = false;
      this.ses_screen_width_changed = false;
      this.ses_screen_height_changed = false;
      this.ses_person_name_changed = false;
      this.ses_person_surname_changed = false;
      this.ses_role_changed = false;
      this.ses_language_changed = false;
      this.ses_terms_accepted_changed = false;
      this.ses_terms_accepted_date_changed = false;
      this.ses_subscripted_month_changed = false;
      this.ses_date_from_changed = false;
      this.ses_date_to_changed = false;
      this.ses_2fa_confirmed_changed = false;
      this.ses_2fa_code_changed = false;
      this.ses_locked_form_code_changed = false;
      this.ses_confirmed_release_version_changed = false;
      this.ses_rol_type_changed = false;
      this.ses_usr_type_changed = false;
      this.ses_profile_token_changed = false;
      this.ses_rol_id_changed = false;
      this.ses_per_id_changed = false;
      this.ses_usr_id_changed = false;
      this.ses_org_id_changed = false;
      this.ses_password_reset_req_date_changed = false;
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
   public void setSes_id(Double ses_id) {
      if (this.isForceUpdate() || 
               (this.ses_id != null && !this.ses_id.equals(ses_id))  ||
               (ses_id != null && !ses_id.equals(this.ses_id)) ){
         this.ses_id_changed = true; 
         this.record_changed = true;
         this.ses_id = ses_id;
      }
   }
   public Double getSes_id() {
      return this.ses_id;
   }
   public void setSes_key(String ses_key) {
      if (this.isForceUpdate() || 
               (this.ses_key != null && !this.ses_key.equals(ses_key))  ||
               (ses_key != null && !ses_key.equals(this.ses_key)) ){
         this.ses_key_changed = true; 
         this.record_changed = true;
         this.ses_key = ses_key;
      }
   }
   public String getSes_key() {
      return this.ses_key;
   }
   public void setSes_username(String ses_username) {
      if (this.isForceUpdate() || 
               (this.ses_username != null && !this.ses_username.equals(ses_username))  ||
               (ses_username != null && !ses_username.equals(this.ses_username)) ){
         this.ses_username_changed = true; 
         this.record_changed = true;
         this.ses_username = ses_username;
      }
   }
   public String getSes_username() {
      return this.ses_username;
   }
   public void setSes_login_time(Date ses_login_time) {
      if (this.isForceUpdate() || 
               (this.ses_login_time != null && (ses_login_time == null ||this.ses_login_time.compareTo(ses_login_time) != 0 )) ||
               (ses_login_time != null && (this.ses_login_time == null ||ses_login_time.compareTo(this.ses_login_time) != 0 ))){
         this.ses_login_time_changed = true; 
         this.record_changed = true;
         this.ses_login_time = ses_login_time;
      }
   }
   public Date getSes_login_time() {
      return this.ses_login_time;
   }
   public void setSes_logout_time(Date ses_logout_time) {
      if (this.isForceUpdate() || 
               (this.ses_logout_time != null && (ses_logout_time == null ||this.ses_logout_time.compareTo(ses_logout_time) != 0 )) ||
               (ses_logout_time != null && (this.ses_logout_time == null ||ses_logout_time.compareTo(this.ses_logout_time) != 0 ))){
         this.ses_logout_time_changed = true; 
         this.record_changed = true;
         this.ses_logout_time = ses_logout_time;
      }
   }
   public Date getSes_logout_time() {
      return this.ses_logout_time;
   }
   public void setSes_logout_cause(String ses_logout_cause) {
      if (this.isForceUpdate() || 
               (this.ses_logout_cause != null && !this.ses_logout_cause.equals(ses_logout_cause))  ||
               (ses_logout_cause != null && !ses_logout_cause.equals(this.ses_logout_cause)) ){
         this.ses_logout_cause_changed = true; 
         this.record_changed = true;
         this.ses_logout_cause = ses_logout_cause;
      }
   }
   public String getSes_logout_cause() {
      return this.ses_logout_cause;
   }
   public void setSes_last_action_time(Date ses_last_action_time) {
      if (this.isForceUpdate() || 
               (this.ses_last_action_time != null && (ses_last_action_time == null ||this.ses_last_action_time.compareTo(ses_last_action_time) != 0 )) ||
               (ses_last_action_time != null && (this.ses_last_action_time == null ||ses_last_action_time.compareTo(this.ses_last_action_time) != 0 ))){
         this.ses_last_action_time_changed = true; 
         this.record_changed = true;
         this.ses_last_action_time = ses_last_action_time;
      }
   }
   public Date getSes_last_action_time() {
      return this.ses_last_action_time;
   }
   public void setSes_login_method(String ses_login_method) {
      if (this.isForceUpdate() || 
               (this.ses_login_method != null && !this.ses_login_method.equals(ses_login_method))  ||
               (ses_login_method != null && !ses_login_method.equals(this.ses_login_method)) ){
         this.ses_login_method_changed = true; 
         this.record_changed = true;
         this.ses_login_method = ses_login_method;
      }
   }
   public String getSes_login_method() {
      return this.ses_login_method;
   }
   public void setSes_ip(String ses_ip) {
      if (this.isForceUpdate() || 
               (this.ses_ip != null && !this.ses_ip.equals(ses_ip))  ||
               (ses_ip != null && !ses_ip.equals(this.ses_ip)) ){
         this.ses_ip_changed = true; 
         this.record_changed = true;
         this.ses_ip = ses_ip;
      }
   }
   public String getSes_ip() {
      return this.ses_ip;
   }
   public void setSes_os(String ses_os) {
      if (this.isForceUpdate() || 
               (this.ses_os != null && !this.ses_os.equals(ses_os))  ||
               (ses_os != null && !ses_os.equals(this.ses_os)) ){
         this.ses_os_changed = true; 
         this.record_changed = true;
         this.ses_os = ses_os;
      }
   }
   public String getSes_os() {
      return this.ses_os;
   }
   public void setSes_browser(String ses_browser) {
      if (this.isForceUpdate() || 
               (this.ses_browser != null && !this.ses_browser.equals(ses_browser))  ||
               (ses_browser != null && !ses_browser.equals(this.ses_browser)) ){
         this.ses_browser_changed = true; 
         this.record_changed = true;
         this.ses_browser = ses_browser;
      }
   }
   public String getSes_browser() {
      return this.ses_browser;
   }
   public void setSes_screen_width(Double ses_screen_width) {
      if (this.isForceUpdate() || 
               (this.ses_screen_width != null && !this.ses_screen_width.equals(ses_screen_width))  ||
               (ses_screen_width != null && !ses_screen_width.equals(this.ses_screen_width)) ){
         this.ses_screen_width_changed = true; 
         this.record_changed = true;
         this.ses_screen_width = ses_screen_width;
      }
   }
   public Double getSes_screen_width() {
      return this.ses_screen_width;
   }
   public void setSes_screen_height(Double ses_screen_height) {
      if (this.isForceUpdate() || 
               (this.ses_screen_height != null && !this.ses_screen_height.equals(ses_screen_height))  ||
               (ses_screen_height != null && !ses_screen_height.equals(this.ses_screen_height)) ){
         this.ses_screen_height_changed = true; 
         this.record_changed = true;
         this.ses_screen_height = ses_screen_height;
      }
   }
   public Double getSes_screen_height() {
      return this.ses_screen_height;
   }
   public void setSes_person_name(String ses_person_name) {
      if (this.isForceUpdate() || 
               (this.ses_person_name != null && !this.ses_person_name.equals(ses_person_name))  ||
               (ses_person_name != null && !ses_person_name.equals(this.ses_person_name)) ){
         this.ses_person_name_changed = true; 
         this.record_changed = true;
         this.ses_person_name = ses_person_name;
      }
   }
   public String getSes_person_name() {
      return this.ses_person_name;
   }
   public void setSes_person_surname(String ses_person_surname) {
      if (this.isForceUpdate() || 
               (this.ses_person_surname != null && !this.ses_person_surname.equals(ses_person_surname))  ||
               (ses_person_surname != null && !ses_person_surname.equals(this.ses_person_surname)) ){
         this.ses_person_surname_changed = true; 
         this.record_changed = true;
         this.ses_person_surname = ses_person_surname;
      }
   }
   public String getSes_person_surname() {
      return this.ses_person_surname;
   }
   public void setSes_role(String ses_role) {
      if (this.isForceUpdate() || 
               (this.ses_role != null && !this.ses_role.equals(ses_role))  ||
               (ses_role != null && !ses_role.equals(this.ses_role)) ){
         this.ses_role_changed = true; 
         this.record_changed = true;
         this.ses_role = ses_role;
      }
   }
   public String getSes_role() {
      return this.ses_role;
   }
   public void setSes_language(String ses_language) {
      if (this.isForceUpdate() || 
               (this.ses_language != null && !this.ses_language.equals(ses_language))  ||
               (ses_language != null && !ses_language.equals(this.ses_language)) ){
         this.ses_language_changed = true; 
         this.record_changed = true;
         this.ses_language = ses_language;
      }
   }
   public String getSes_language() {
      return this.ses_language;
   }
   public void setSes_terms_accepted(String ses_terms_accepted) {
      if (this.isForceUpdate() || 
               (this.ses_terms_accepted != null && !this.ses_terms_accepted.equals(ses_terms_accepted))  ||
               (ses_terms_accepted != null && !ses_terms_accepted.equals(this.ses_terms_accepted)) ){
         this.ses_terms_accepted_changed = true; 
         this.record_changed = true;
         this.ses_terms_accepted = ses_terms_accepted;
      }
   }
   public String getSes_terms_accepted() {
      return this.ses_terms_accepted;
   }
   public void setSes_terms_accepted_date(Date ses_terms_accepted_date) {
      if (this.isForceUpdate() || 
               (this.ses_terms_accepted_date != null && (ses_terms_accepted_date == null ||this.ses_terms_accepted_date.compareTo(ses_terms_accepted_date) != 0 )) ||
               (ses_terms_accepted_date != null && (this.ses_terms_accepted_date == null ||ses_terms_accepted_date.compareTo(this.ses_terms_accepted_date) != 0 ))){
         this.ses_terms_accepted_date_changed = true; 
         this.record_changed = true;
         this.ses_terms_accepted_date = ses_terms_accepted_date;
      }
   }
   public Date getSes_terms_accepted_date() {
      return this.ses_terms_accepted_date;
   }
   public void setSes_subscripted_month(Double ses_subscripted_month) {
      if (this.isForceUpdate() || 
               (this.ses_subscripted_month != null && !this.ses_subscripted_month.equals(ses_subscripted_month))  ||
               (ses_subscripted_month != null && !ses_subscripted_month.equals(this.ses_subscripted_month)) ){
         this.ses_subscripted_month_changed = true; 
         this.record_changed = true;
         this.ses_subscripted_month = ses_subscripted_month;
      }
   }
   public Double getSes_subscripted_month() {
      return this.ses_subscripted_month;
   }
   public void setSes_date_from(Date ses_date_from) {
      if (this.isForceUpdate() || 
               (this.ses_date_from != null && (ses_date_from == null ||this.ses_date_from.compareTo(ses_date_from) != 0 )) ||
               (ses_date_from != null && (this.ses_date_from == null ||ses_date_from.compareTo(this.ses_date_from) != 0 ))){
         this.ses_date_from_changed = true; 
         this.record_changed = true;
         this.ses_date_from = ses_date_from;
      }
   }
   public Date getSes_date_from() {
      return this.ses_date_from;
   }
   public void setSes_date_to(Date ses_date_to) {
      if (this.isForceUpdate() || 
               (this.ses_date_to != null && (ses_date_to == null ||this.ses_date_to.compareTo(ses_date_to) != 0 )) ||
               (ses_date_to != null && (this.ses_date_to == null ||ses_date_to.compareTo(this.ses_date_to) != 0 ))){
         this.ses_date_to_changed = true; 
         this.record_changed = true;
         this.ses_date_to = ses_date_to;
      }
   }
   public Date getSes_date_to() {
      return this.ses_date_to;
   }
   public void setSes_2fa_confirmed(String ses_2fa_confirmed) {
      if (this.isForceUpdate() || 
               (this.ses_2fa_confirmed != null && !this.ses_2fa_confirmed.equals(ses_2fa_confirmed))  ||
               (ses_2fa_confirmed != null && !ses_2fa_confirmed.equals(this.ses_2fa_confirmed)) ){
         this.ses_2fa_confirmed_changed = true; 
         this.record_changed = true;
         this.ses_2fa_confirmed = ses_2fa_confirmed;
      }
   }
   public String getSes_2fa_confirmed() {
      return this.ses_2fa_confirmed;
   }
   public void setSes_2fa_code(String ses_2fa_code) {
      if (this.isForceUpdate() || 
               (this.ses_2fa_code != null && !this.ses_2fa_code.equals(ses_2fa_code))  ||
               (ses_2fa_code != null && !ses_2fa_code.equals(this.ses_2fa_code)) ){
         this.ses_2fa_code_changed = true; 
         this.record_changed = true;
         this.ses_2fa_code = ses_2fa_code;
      }
   }
   public String getSes_2fa_code() {
      return this.ses_2fa_code;
   }
   public void setSes_locked_form_code(String ses_locked_form_code) {
      if (this.isForceUpdate() || 
               (this.ses_locked_form_code != null && !this.ses_locked_form_code.equals(ses_locked_form_code))  ||
               (ses_locked_form_code != null && !ses_locked_form_code.equals(this.ses_locked_form_code)) ){
         this.ses_locked_form_code_changed = true; 
         this.record_changed = true;
         this.ses_locked_form_code = ses_locked_form_code;
      }
   }
   public String getSes_locked_form_code() {
      return this.ses_locked_form_code;
   }
   public void setSes_confirmed_release_version(String ses_confirmed_release_version) {
      if (this.isForceUpdate() || 
               (this.ses_confirmed_release_version != null && !this.ses_confirmed_release_version.equals(ses_confirmed_release_version))  ||
               (ses_confirmed_release_version != null && !ses_confirmed_release_version.equals(this.ses_confirmed_release_version)) ){
         this.ses_confirmed_release_version_changed = true; 
         this.record_changed = true;
         this.ses_confirmed_release_version = ses_confirmed_release_version;
      }
   }
   public String getSes_confirmed_release_version() {
      return this.ses_confirmed_release_version;
   }
   public void setSes_rol_type(String ses_rol_type) {
      if (this.isForceUpdate() || 
               (this.ses_rol_type != null && !this.ses_rol_type.equals(ses_rol_type))  ||
               (ses_rol_type != null && !ses_rol_type.equals(this.ses_rol_type)) ){
         this.ses_rol_type_changed = true; 
         this.record_changed = true;
         this.ses_rol_type = ses_rol_type;
      }
   }
   public String getSes_rol_type() {
      return this.ses_rol_type;
   }
   public void setSes_usr_type(String ses_usr_type) {
      if (this.isForceUpdate() || 
               (this.ses_usr_type != null && !this.ses_usr_type.equals(ses_usr_type))  ||
               (ses_usr_type != null && !ses_usr_type.equals(this.ses_usr_type)) ){
         this.ses_usr_type_changed = true; 
         this.record_changed = true;
         this.ses_usr_type = ses_usr_type;
      }
   }
   public String getSes_usr_type() {
      return this.ses_usr_type;
   }
   public void setSes_profile_token(String ses_profile_token) {
      if (this.isForceUpdate() || 
               (this.ses_profile_token != null && !this.ses_profile_token.equals(ses_profile_token))  ||
               (ses_profile_token != null && !ses_profile_token.equals(this.ses_profile_token)) ){
         this.ses_profile_token_changed = true; 
         this.record_changed = true;
         this.ses_profile_token = ses_profile_token;
      }
   }
   public String getSes_profile_token() {
      return this.ses_profile_token;
   }
   public void setSes_rol_id(Double ses_rol_id) {
      if (this.isForceUpdate() || 
               (this.ses_rol_id != null && !this.ses_rol_id.equals(ses_rol_id))  ||
               (ses_rol_id != null && !ses_rol_id.equals(this.ses_rol_id)) ){
         this.ses_rol_id_changed = true; 
         this.record_changed = true;
         this.ses_rol_id = ses_rol_id;
      }
   }
   public Double getSes_rol_id() {
      return this.ses_rol_id;
   }
   public void setSes_per_id(Double ses_per_id) {
      if (this.isForceUpdate() || 
               (this.ses_per_id != null && !this.ses_per_id.equals(ses_per_id))  ||
               (ses_per_id != null && !ses_per_id.equals(this.ses_per_id)) ){
         this.ses_per_id_changed = true; 
         this.record_changed = true;
         this.ses_per_id = ses_per_id;
      }
   }
   public Double getSes_per_id() {
      return this.ses_per_id;
   }
   public void setSes_usr_id(Double ses_usr_id) {
      if (this.isForceUpdate() || 
               (this.ses_usr_id != null && !this.ses_usr_id.equals(ses_usr_id))  ||
               (ses_usr_id != null && !ses_usr_id.equals(this.ses_usr_id)) ){
         this.ses_usr_id_changed = true; 
         this.record_changed = true;
         this.ses_usr_id = ses_usr_id;
      }
   }
   public Double getSes_usr_id() {
      return this.ses_usr_id;
   }
   public void setSes_org_id(Double ses_org_id) {
      if (this.isForceUpdate() || 
               (this.ses_org_id != null && !this.ses_org_id.equals(ses_org_id))  ||
               (ses_org_id != null && !ses_org_id.equals(this.ses_org_id)) ){
         this.ses_org_id_changed = true; 
         this.record_changed = true;
         this.ses_org_id = ses_org_id;
      }
   }
   public Double getSes_org_id() {
      return this.ses_org_id;
   }
   public void setSes_password_reset_req_date(Date ses_password_reset_req_date) {
      if (this.isForceUpdate() || 
               (this.ses_password_reset_req_date != null && (ses_password_reset_req_date == null ||this.ses_password_reset_req_date.compareTo(ses_password_reset_req_date) != 0 )) ||
               (ses_password_reset_req_date != null && (this.ses_password_reset_req_date == null ||ses_password_reset_req_date.compareTo(this.ses_password_reset_req_date) != 0 ))){
         this.ses_password_reset_req_date_changed = true; 
         this.record_changed = true;
         this.ses_password_reset_req_date = ses_password_reset_req_date;
      }
   }
   public Date getSes_password_reset_req_date() {
      return this.ses_password_reset_req_date;
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
            this.ses_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.ses_id;
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
      if (ses_key_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_key== null || EMPTY_STRING.equals(ses_key)) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_KEY", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ses_key!= null && ses_key.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_KEY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (ses_username_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_username!= null && ses_username.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_USERNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_logout_cause_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_logout_cause!= null && ses_logout_cause.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_LOGOUT_CAUSE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_login_method_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_login_method!= null && ses_login_method.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_LOGIN_METHOD", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_ip_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_ip!= null && ses_ip.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_IP", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_os_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_os!= null && ses_os.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_OS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_browser_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_browser!= null && ses_browser.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_BROWSER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_screen_width_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_screen_width!= null && (""+ses_screen_width.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_SCREEN_WIDTH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (ses_screen_height_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_screen_height!= null && (""+ses_screen_height.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_SCREEN_HEIGHT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (ses_person_name_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_person_name!= null && ses_person_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_PERSON_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_person_surname_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_person_surname!= null && ses_person_surname.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_PERSON_SURNAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_role_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_role!= null && ses_role.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_ROLE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_language_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_language!= null && ses_language.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_LANGUAGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_terms_accepted_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_terms_accepted!= null && ses_terms_accepted.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_TERMS_ACCEPTED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (ses_subscripted_month_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_subscripted_month!= null && (""+ses_subscripted_month.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_SUBSCRIPTED_MONTH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (ses_2fa_confirmed_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_2fa_confirmed!= null && ses_2fa_confirmed.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_2FA_CONFIRMED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (ses_2fa_code_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_2fa_code!= null && ses_2fa_code.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_2FA_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_locked_form_code_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_locked_form_code!= null && ses_locked_form_code.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_LOCKED_FORM_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_confirmed_release_version_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_confirmed_release_version!= null && ses_confirmed_release_version.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_CONFIRMED_RELEASE_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ses_rol_type_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_rol_type!= null && ses_rol_type.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_ROL_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_usr_type_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_usr_type!= null && ses_usr_type.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_USR_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ses_profile_token_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_profile_token!= null && ses_profile_token.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_PROFILE_TOKEN", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (ses_rol_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_rol_id!= null && (""+ses_rol_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_ROL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (ses_per_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_per_id!= null && (""+ses_per_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_PER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (ses_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_usr_id!= null && (""+ses_usr_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (ses_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ses_org_id!= null && (""+ses_org_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "SES_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_USER_SESSIONS_OPEN", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_USER_SESSIONS_OPEN SET ";
      boolean changedExists = false;      if (ses_key_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_KEY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_key);
      }
      if (ses_username_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_USERNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_username);
      }
      if (ses_login_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LOGIN_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ses_login_time);
      }
      if (ses_logout_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LOGOUT_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ses_logout_time);
      }
      if (ses_logout_cause_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LOGOUT_CAUSE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_logout_cause);
      }
      if (ses_last_action_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LAST_ACTION_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ses_last_action_time);
      }
      if (ses_login_method_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LOGIN_METHOD = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_login_method);
      }
      if (ses_ip_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_IP = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_ip);
      }
      if (ses_os_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_OS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_os);
      }
      if (ses_browser_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_BROWSER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_browser);
      }
      if (ses_screen_width_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_SCREEN_WIDTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_screen_width);
      }
      if (ses_screen_height_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_SCREEN_HEIGHT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_screen_height);
      }
      if (ses_person_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_PERSON_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_person_name);
      }
      if (ses_person_surname_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_PERSON_SURNAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_person_surname);
      }
      if (ses_role_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_ROLE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_role);
      }
      if (ses_language_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LANGUAGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_language);
      }
      if (ses_terms_accepted_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_TERMS_ACCEPTED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_terms_accepted);
      }
      if (ses_terms_accepted_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_TERMS_ACCEPTED_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_terms_accepted_date);
      }
      if (ses_subscripted_month_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_SUBSCRIPTED_MONTH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_subscripted_month);
      }
      if (ses_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_date_from);
      }
      if (ses_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_date_to);
      }
      if (ses_2fa_confirmed_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_2FA_CONFIRMED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_2fa_confirmed);
      }
      if (ses_2fa_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_2FA_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_2fa_code);
      }
      if (ses_locked_form_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_LOCKED_FORM_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_locked_form_code);
      }
      if (ses_confirmed_release_version_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_CONFIRMED_RELEASE_VERSION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_confirmed_release_version);
      }
      if (ses_rol_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_ROL_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_rol_type);
      }
      if (ses_usr_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_USR_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_usr_type);
      }
      if (ses_profile_token_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_PROFILE_TOKEN = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_profile_token);
      }
      if (ses_rol_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_ROL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_rol_id);
      }
      if (ses_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_per_id);
      }
      if (ses_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_usr_id);
      }
      if (ses_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ses_org_id);
      }
      if (ses_password_reset_req_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SES_PASSWORD_RESET_REQ_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ses_password_reset_req_date);
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
      answer = answer +" WHERE  SES_ID = ? ";
      updateStatementPart.addStatementParam(ses_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprSessionOpenDAO\":{\"ses_id\": \""+getSes_id()+"\", \"ses_key\": \""+getSes_key()+"\", \"ses_username\": \""+getSes_username()+"\", \"ses_login_time\": \""+getSes_login_time()+"\", \"ses_logout_time\": \""+getSes_logout_time()+"\", \"ses_logout_cause\": \""+getSes_logout_cause()+"\", \"ses_last_action_time\": \""+getSes_last_action_time()+"\", \"ses_login_method\": \""+getSes_login_method()+"\", \"ses_ip\": \""+getSes_ip()+"\", \"ses_os\": \""+getSes_os()+"\", \"ses_browser\": \""+getSes_browser()+"\", \"ses_screen_width\": \""+getSes_screen_width()+"\", \"ses_screen_height\": \""+getSes_screen_height()+"\", \"ses_person_name\": \""+getSes_person_name()+"\", \"ses_person_surname\": \""+getSes_person_surname()+"\", \"ses_role\": \""+getSes_role()+"\", \"ses_language\": \""+getSes_language()+"\", \"ses_terms_accepted\": \""+getSes_terms_accepted()+"\", \"ses_terms_accepted_date\": \""+getSes_terms_accepted_date()+"\", \"ses_subscripted_month\": \""+getSes_subscripted_month()+"\", \"ses_date_from\": \""+getSes_date_from()+"\", \"ses_date_to\": \""+getSes_date_to()+"\", \"ses_2fa_confirmed\": \""+getSes_2fa_confirmed()+"\", \"ses_2fa_code\": \""+getSes_2fa_code()+"\", \"ses_locked_form_code\": \""+getSes_locked_form_code()+"\", \"ses_confirmed_release_version\": \""+getSes_confirmed_release_version()+"\", \"ses_rol_type\": \""+getSes_rol_type()+"\", \"ses_usr_type\": \""+getSes_usr_type()+"\", \"ses_profile_token\": \""+getSes_profile_token()+"\", \"ses_rol_id\": \""+getSes_rol_id()+"\", \"ses_per_id\": \""+getSes_per_id()+"\", \"ses_usr_id\": \""+getSes_usr_id()+"\", \"ses_org_id\": \""+getSes_org_id()+"\", \"ses_password_reset_req_date\": \""+getSes_password_reset_req_date()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}