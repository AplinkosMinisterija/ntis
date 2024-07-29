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

public class SprJobDefinitionsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_JOB_DEFINITIONS.JDE_ID";
   private Double jde_id;
   private String jde_system;
   private String jde_name;
   private String jde_type;
   private String jde_code;
   private String jde_status;
   private String jde_path;
   private Double jde_tml_id;
   private String jde_default_executer;
   private String jde_execution_parameter;
   private String jde_execution_unit;
   private String jde_action_type;
   private String jde_default_output_type;
   private Date jde_last_action_time;
   private Date jde_next_action_time;
   private String jde_description;
   private String jde_period;
   private Double jde_week_day;
   private Double jde_month_day;
   private Double jde_year_day;
   private Double jde_hour;
   private Double jde_minutes;
   private Double jde_period_in_minutes;
   private Date jde_date_from;
   private Date jde_date_to;
   private Double jde_days_in_history;
   private Double jde_days_in_request;
   private String jde_adjust_to_current_date;
   private String jde_ntf_on_completeion;
   private Double jde_ntf_tml_id;
   private String jde_ntf_tml_tmt_code;
   private Double jde_email_tml_id;
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

   protected boolean jde_id_changed;
   protected boolean jde_system_changed;
   protected boolean jde_name_changed;
   protected boolean jde_type_changed;
   protected boolean jde_code_changed;
   protected boolean jde_status_changed;
   protected boolean jde_path_changed;
   protected boolean jde_tml_id_changed;
   protected boolean jde_default_executer_changed;
   protected boolean jde_execution_parameter_changed;
   protected boolean jde_execution_unit_changed;
   protected boolean jde_action_type_changed;
   protected boolean jde_default_output_type_changed;
   protected boolean jde_last_action_time_changed;
   protected boolean jde_next_action_time_changed;
   protected boolean jde_description_changed;
   protected boolean jde_period_changed;
   protected boolean jde_week_day_changed;
   protected boolean jde_month_day_changed;
   protected boolean jde_year_day_changed;
   protected boolean jde_hour_changed;
   protected boolean jde_minutes_changed;
   protected boolean jde_period_in_minutes_changed;
   protected boolean jde_date_from_changed;
   protected boolean jde_date_to_changed;
   protected boolean jde_days_in_history_changed;
   protected boolean jde_days_in_request_changed;
   protected boolean jde_adjust_to_current_date_changed;
   protected boolean jde_ntf_on_completeion_changed;
   protected boolean jde_ntf_tml_id_changed;
   protected boolean jde_ntf_tml_tmt_code_changed;
   protected boolean jde_email_tml_id_changed;
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
   public SprJobDefinitionsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprJobDefinitionsDAOGen(Double jde_id, String jde_system, String jde_name, String jde_type, String jde_code, String jde_status, String jde_path, Double jde_tml_id, String jde_default_executer, String jde_execution_parameter, String jde_execution_unit, String jde_action_type, String jde_default_output_type, Date jde_last_action_time, Date jde_next_action_time, String jde_description, String jde_period, Double jde_week_day, Double jde_month_day, Double jde_year_day, Double jde_hour, Double jde_minutes, Double jde_period_in_minutes, Date jde_date_from, Date jde_date_to, Double jde_days_in_history, Double jde_days_in_request, String jde_adjust_to_current_date, String jde_ntf_on_completeion, Double jde_ntf_tml_id, String jde_ntf_tml_tmt_code, Double jde_email_tml_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.jde_id = jde_id;
      this.jde_system = jde_system;
      this.jde_name = jde_name;
      this.jde_type = jde_type;
      this.jde_code = jde_code;
      this.jde_status = jde_status;
      this.jde_path = jde_path;
      this.jde_tml_id = jde_tml_id;
      this.jde_default_executer = jde_default_executer;
      this.jde_execution_parameter = jde_execution_parameter;
      this.jde_execution_unit = jde_execution_unit;
      this.jde_action_type = jde_action_type;
      this.jde_default_output_type = jde_default_output_type;
      this.jde_last_action_time = jde_last_action_time;
      this.jde_next_action_time = jde_next_action_time;
      this.jde_description = jde_description;
      this.jde_period = jde_period;
      this.jde_week_day = jde_week_day;
      this.jde_month_day = jde_month_day;
      this.jde_year_day = jde_year_day;
      this.jde_hour = jde_hour;
      this.jde_minutes = jde_minutes;
      this.jde_period_in_minutes = jde_period_in_minutes;
      this.jde_date_from = jde_date_from;
      this.jde_date_to = jde_date_to;
      this.jde_days_in_history = jde_days_in_history;
      this.jde_days_in_request = jde_days_in_request;
      this.jde_adjust_to_current_date = jde_adjust_to_current_date;
      this.jde_ntf_on_completeion = jde_ntf_on_completeion;
      this.jde_ntf_tml_id = jde_ntf_tml_id;
      this.jde_ntf_tml_tmt_code = jde_ntf_tml_tmt_code;
      this.jde_email_tml_id = jde_email_tml_id;
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
   public void copyValues(SprJobDefinitionsDAOGen obj) {
      this.setJde_id(obj.getJde_id());
      this.setJde_system(obj.getJde_system());
      this.setJde_name(obj.getJde_name());
      this.setJde_type(obj.getJde_type());
      this.setJde_code(obj.getJde_code());
      this.setJde_status(obj.getJde_status());
      this.setJde_path(obj.getJde_path());
      this.setJde_tml_id(obj.getJde_tml_id());
      this.setJde_default_executer(obj.getJde_default_executer());
      this.setJde_execution_parameter(obj.getJde_execution_parameter());
      this.setJde_execution_unit(obj.getJde_execution_unit());
      this.setJde_action_type(obj.getJde_action_type());
      this.setJde_default_output_type(obj.getJde_default_output_type());
      this.setJde_last_action_time(obj.getJde_last_action_time());
      this.setJde_next_action_time(obj.getJde_next_action_time());
      this.setJde_description(obj.getJde_description());
      this.setJde_period(obj.getJde_period());
      this.setJde_week_day(obj.getJde_week_day());
      this.setJde_month_day(obj.getJde_month_day());
      this.setJde_year_day(obj.getJde_year_day());
      this.setJde_hour(obj.getJde_hour());
      this.setJde_minutes(obj.getJde_minutes());
      this.setJde_period_in_minutes(obj.getJde_period_in_minutes());
      this.setJde_date_from(obj.getJde_date_from());
      this.setJde_date_to(obj.getJde_date_to());
      this.setJde_days_in_history(obj.getJde_days_in_history());
      this.setJde_days_in_request(obj.getJde_days_in_request());
      this.setJde_adjust_to_current_date(obj.getJde_adjust_to_current_date());
      this.setJde_ntf_on_completeion(obj.getJde_ntf_on_completeion());
      this.setJde_ntf_tml_id(obj.getJde_ntf_tml_id());
      this.setJde_ntf_tml_tmt_code(obj.getJde_ntf_tml_tmt_code());
      this.setJde_email_tml_id(obj.getJde_email_tml_id());
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
      this.jde_id_changed = false;
      this.jde_system_changed = false;
      this.jde_name_changed = false;
      this.jde_type_changed = false;
      this.jde_code_changed = false;
      this.jde_status_changed = false;
      this.jde_path_changed = false;
      this.jde_tml_id_changed = false;
      this.jde_default_executer_changed = false;
      this.jde_execution_parameter_changed = false;
      this.jde_execution_unit_changed = false;
      this.jde_action_type_changed = false;
      this.jde_default_output_type_changed = false;
      this.jde_last_action_time_changed = false;
      this.jde_next_action_time_changed = false;
      this.jde_description_changed = false;
      this.jde_period_changed = false;
      this.jde_week_day_changed = false;
      this.jde_month_day_changed = false;
      this.jde_year_day_changed = false;
      this.jde_hour_changed = false;
      this.jde_minutes_changed = false;
      this.jde_period_in_minutes_changed = false;
      this.jde_date_from_changed = false;
      this.jde_date_to_changed = false;
      this.jde_days_in_history_changed = false;
      this.jde_days_in_request_changed = false;
      this.jde_adjust_to_current_date_changed = false;
      this.jde_ntf_on_completeion_changed = false;
      this.jde_ntf_tml_id_changed = false;
      this.jde_ntf_tml_tmt_code_changed = false;
      this.jde_email_tml_id_changed = false;
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
   public void setJde_id(Double jde_id) {
      if (this.isForceUpdate() || 
               (this.jde_id != null && !this.jde_id.equals(jde_id))  ||
               (jde_id != null && !jde_id.equals(this.jde_id)) ){
         this.jde_id_changed = true; 
         this.record_changed = true;
         this.jde_id = jde_id;
      }
   }
   public Double getJde_id() {
      return this.jde_id;
   }
   public void setJde_system(String jde_system) {
      if (this.isForceUpdate() || 
               (this.jde_system != null && !this.jde_system.equals(jde_system))  ||
               (jde_system != null && !jde_system.equals(this.jde_system)) ){
         this.jde_system_changed = true; 
         this.record_changed = true;
         this.jde_system = jde_system;
      }
   }
   public String getJde_system() {
      return this.jde_system;
   }
   public void setJde_name(String jde_name) {
      if (this.isForceUpdate() || 
               (this.jde_name != null && !this.jde_name.equals(jde_name))  ||
               (jde_name != null && !jde_name.equals(this.jde_name)) ){
         this.jde_name_changed = true; 
         this.record_changed = true;
         this.jde_name = jde_name;
      }
   }
   public String getJde_name() {
      return this.jde_name;
   }
   public void setJde_type(String jde_type) {
      if (this.isForceUpdate() || 
               (this.jde_type != null && !this.jde_type.equals(jde_type))  ||
               (jde_type != null && !jde_type.equals(this.jde_type)) ){
         this.jde_type_changed = true; 
         this.record_changed = true;
         this.jde_type = jde_type;
      }
   }
   public String getJde_type() {
      return this.jde_type;
   }
   public void setJde_code(String jde_code) {
      if (this.isForceUpdate() || 
               (this.jde_code != null && !this.jde_code.equals(jde_code))  ||
               (jde_code != null && !jde_code.equals(this.jde_code)) ){
         this.jde_code_changed = true; 
         this.record_changed = true;
         this.jde_code = jde_code;
      }
   }
   public String getJde_code() {
      return this.jde_code;
   }
   public void setJde_status(String jde_status) {
      if (this.isForceUpdate() || 
               (this.jde_status != null && !this.jde_status.equals(jde_status))  ||
               (jde_status != null && !jde_status.equals(this.jde_status)) ){
         this.jde_status_changed = true; 
         this.record_changed = true;
         this.jde_status = jde_status;
      }
   }
   public String getJde_status() {
      return this.jde_status;
   }
   public void setJde_path(String jde_path) {
      if (this.isForceUpdate() || 
               (this.jde_path != null && !this.jde_path.equals(jde_path))  ||
               (jde_path != null && !jde_path.equals(this.jde_path)) ){
         this.jde_path_changed = true; 
         this.record_changed = true;
         this.jde_path = jde_path;
      }
   }
   public String getJde_path() {
      return this.jde_path;
   }
   public void setJde_tml_id(Double jde_tml_id) {
      if (this.isForceUpdate() || 
               (this.jde_tml_id != null && !this.jde_tml_id.equals(jde_tml_id))  ||
               (jde_tml_id != null && !jde_tml_id.equals(this.jde_tml_id)) ){
         this.jde_tml_id_changed = true; 
         this.record_changed = true;
         this.jde_tml_id = jde_tml_id;
      }
   }
   public Double getJde_tml_id() {
      return this.jde_tml_id;
   }
   public void setJde_default_executer(String jde_default_executer) {
      if (this.isForceUpdate() || 
               (this.jde_default_executer != null && !this.jde_default_executer.equals(jde_default_executer))  ||
               (jde_default_executer != null && !jde_default_executer.equals(this.jde_default_executer)) ){
         this.jde_default_executer_changed = true; 
         this.record_changed = true;
         this.jde_default_executer = jde_default_executer;
      }
   }
   public String getJde_default_executer() {
      return this.jde_default_executer;
   }
   public void setJde_execution_parameter(String jde_execution_parameter) {
      if (this.isForceUpdate() || 
               (this.jde_execution_parameter != null && !this.jde_execution_parameter.equals(jde_execution_parameter))  ||
               (jde_execution_parameter != null && !jde_execution_parameter.equals(this.jde_execution_parameter)) ){
         this.jde_execution_parameter_changed = true; 
         this.record_changed = true;
         this.jde_execution_parameter = jde_execution_parameter;
      }
   }
   public String getJde_execution_parameter() {
      return this.jde_execution_parameter;
   }
   public void setJde_execution_unit(String jde_execution_unit) {
      if (this.isForceUpdate() || 
               (this.jde_execution_unit != null && !this.jde_execution_unit.equals(jde_execution_unit))  ||
               (jde_execution_unit != null && !jde_execution_unit.equals(this.jde_execution_unit)) ){
         this.jde_execution_unit_changed = true; 
         this.record_changed = true;
         this.jde_execution_unit = jde_execution_unit;
      }
   }
   public String getJde_execution_unit() {
      return this.jde_execution_unit;
   }
   public void setJde_action_type(String jde_action_type) {
      if (this.isForceUpdate() || 
               (this.jde_action_type != null && !this.jde_action_type.equals(jde_action_type))  ||
               (jde_action_type != null && !jde_action_type.equals(this.jde_action_type)) ){
         this.jde_action_type_changed = true; 
         this.record_changed = true;
         this.jde_action_type = jde_action_type;
      }
   }
   public String getJde_action_type() {
      return this.jde_action_type;
   }
   public void setJde_default_output_type(String jde_default_output_type) {
      if (this.isForceUpdate() || 
               (this.jde_default_output_type != null && !this.jde_default_output_type.equals(jde_default_output_type))  ||
               (jde_default_output_type != null && !jde_default_output_type.equals(this.jde_default_output_type)) ){
         this.jde_default_output_type_changed = true; 
         this.record_changed = true;
         this.jde_default_output_type = jde_default_output_type;
      }
   }
   public String getJde_default_output_type() {
      return this.jde_default_output_type;
   }
   public void setJde_last_action_time(Date jde_last_action_time) {
      if (this.isForceUpdate() || 
               (this.jde_last_action_time != null && (jde_last_action_time == null ||this.jde_last_action_time.compareTo(jde_last_action_time) != 0 )) ||
               (jde_last_action_time != null && (this.jde_last_action_time == null ||jde_last_action_time.compareTo(this.jde_last_action_time) != 0 ))){
         this.jde_last_action_time_changed = true; 
         this.record_changed = true;
         this.jde_last_action_time = jde_last_action_time;
      }
   }
   public Date getJde_last_action_time() {
      return this.jde_last_action_time;
   }
   public void setJde_next_action_time(Date jde_next_action_time) {
      if (this.isForceUpdate() || 
               (this.jde_next_action_time != null && (jde_next_action_time == null ||this.jde_next_action_time.compareTo(jde_next_action_time) != 0 )) ||
               (jde_next_action_time != null && (this.jde_next_action_time == null ||jde_next_action_time.compareTo(this.jde_next_action_time) != 0 ))){
         this.jde_next_action_time_changed = true; 
         this.record_changed = true;
         this.jde_next_action_time = jde_next_action_time;
      }
   }
   public Date getJde_next_action_time() {
      return this.jde_next_action_time;
   }
   public void setJde_description(String jde_description) {
      if (this.isForceUpdate() || 
               (this.jde_description != null && !this.jde_description.equals(jde_description))  ||
               (jde_description != null && !jde_description.equals(this.jde_description)) ){
         this.jde_description_changed = true; 
         this.record_changed = true;
         this.jde_description = jde_description;
      }
   }
   public String getJde_description() {
      return this.jde_description;
   }
   public void setJde_period(String jde_period) {
      if (this.isForceUpdate() || 
               (this.jde_period != null && !this.jde_period.equals(jde_period))  ||
               (jde_period != null && !jde_period.equals(this.jde_period)) ){
         this.jde_period_changed = true; 
         this.record_changed = true;
         this.jde_period = jde_period;
      }
   }
   public String getJde_period() {
      return this.jde_period;
   }
   public void setJde_week_day(Double jde_week_day) {
      if (this.isForceUpdate() || 
               (this.jde_week_day != null && !this.jde_week_day.equals(jde_week_day))  ||
               (jde_week_day != null && !jde_week_day.equals(this.jde_week_day)) ){
         this.jde_week_day_changed = true; 
         this.record_changed = true;
         this.jde_week_day = jde_week_day;
      }
   }
   public Double getJde_week_day() {
      return this.jde_week_day;
   }
   public void setJde_month_day(Double jde_month_day) {
      if (this.isForceUpdate() || 
               (this.jde_month_day != null && !this.jde_month_day.equals(jde_month_day))  ||
               (jde_month_day != null && !jde_month_day.equals(this.jde_month_day)) ){
         this.jde_month_day_changed = true; 
         this.record_changed = true;
         this.jde_month_day = jde_month_day;
      }
   }
   public Double getJde_month_day() {
      return this.jde_month_day;
   }
   public void setJde_year_day(Double jde_year_day) {
      if (this.isForceUpdate() || 
               (this.jde_year_day != null && !this.jde_year_day.equals(jde_year_day))  ||
               (jde_year_day != null && !jde_year_day.equals(this.jde_year_day)) ){
         this.jde_year_day_changed = true; 
         this.record_changed = true;
         this.jde_year_day = jde_year_day;
      }
   }
   public Double getJde_year_day() {
      return this.jde_year_day;
   }
   public void setJde_hour(Double jde_hour) {
      if (this.isForceUpdate() || 
               (this.jde_hour != null && !this.jde_hour.equals(jde_hour))  ||
               (jde_hour != null && !jde_hour.equals(this.jde_hour)) ){
         this.jde_hour_changed = true; 
         this.record_changed = true;
         this.jde_hour = jde_hour;
      }
   }
   public Double getJde_hour() {
      return this.jde_hour;
   }
   public void setJde_minutes(Double jde_minutes) {
      if (this.isForceUpdate() || 
               (this.jde_minutes != null && !this.jde_minutes.equals(jde_minutes))  ||
               (jde_minutes != null && !jde_minutes.equals(this.jde_minutes)) ){
         this.jde_minutes_changed = true; 
         this.record_changed = true;
         this.jde_minutes = jde_minutes;
      }
   }
   public Double getJde_minutes() {
      return this.jde_minutes;
   }
   public void setJde_period_in_minutes(Double jde_period_in_minutes) {
      if (this.isForceUpdate() || 
               (this.jde_period_in_minutes != null && !this.jde_period_in_minutes.equals(jde_period_in_minutes))  ||
               (jde_period_in_minutes != null && !jde_period_in_minutes.equals(this.jde_period_in_minutes)) ){
         this.jde_period_in_minutes_changed = true; 
         this.record_changed = true;
         this.jde_period_in_minutes = jde_period_in_minutes;
      }
   }
   public Double getJde_period_in_minutes() {
      return this.jde_period_in_minutes;
   }
   public void setJde_date_from(Date jde_date_from) {
      if (this.isForceUpdate() || 
               (this.jde_date_from != null && (jde_date_from == null ||this.jde_date_from.compareTo(jde_date_from) != 0 )) ||
               (jde_date_from != null && (this.jde_date_from == null ||jde_date_from.compareTo(this.jde_date_from) != 0 ))){
         this.jde_date_from_changed = true; 
         this.record_changed = true;
         this.jde_date_from = jde_date_from;
      }
   }
   public Date getJde_date_from() {
      return this.jde_date_from;
   }
   public void setJde_date_to(Date jde_date_to) {
      if (this.isForceUpdate() || 
               (this.jde_date_to != null && (jde_date_to == null ||this.jde_date_to.compareTo(jde_date_to) != 0 )) ||
               (jde_date_to != null && (this.jde_date_to == null ||jde_date_to.compareTo(this.jde_date_to) != 0 ))){
         this.jde_date_to_changed = true; 
         this.record_changed = true;
         this.jde_date_to = jde_date_to;
      }
   }
   public Date getJde_date_to() {
      return this.jde_date_to;
   }
   public void setJde_days_in_history(Double jde_days_in_history) {
      if (this.isForceUpdate() || 
               (this.jde_days_in_history != null && !this.jde_days_in_history.equals(jde_days_in_history))  ||
               (jde_days_in_history != null && !jde_days_in_history.equals(this.jde_days_in_history)) ){
         this.jde_days_in_history_changed = true; 
         this.record_changed = true;
         this.jde_days_in_history = jde_days_in_history;
      }
   }
   public Double getJde_days_in_history() {
      return this.jde_days_in_history;
   }
   public void setJde_days_in_request(Double jde_days_in_request) {
      if (this.isForceUpdate() || 
               (this.jde_days_in_request != null && !this.jde_days_in_request.equals(jde_days_in_request))  ||
               (jde_days_in_request != null && !jde_days_in_request.equals(this.jde_days_in_request)) ){
         this.jde_days_in_request_changed = true; 
         this.record_changed = true;
         this.jde_days_in_request = jde_days_in_request;
      }
   }
   public Double getJde_days_in_request() {
      return this.jde_days_in_request;
   }
   public void setJde_adjust_to_current_date(String jde_adjust_to_current_date) {
      if (this.isForceUpdate() || 
               (this.jde_adjust_to_current_date != null && !this.jde_adjust_to_current_date.equals(jde_adjust_to_current_date))  ||
               (jde_adjust_to_current_date != null && !jde_adjust_to_current_date.equals(this.jde_adjust_to_current_date)) ){
         this.jde_adjust_to_current_date_changed = true; 
         this.record_changed = true;
         this.jde_adjust_to_current_date = jde_adjust_to_current_date;
      }
   }
   public String getJde_adjust_to_current_date() {
      return this.jde_adjust_to_current_date;
   }
   public void setJde_ntf_on_completeion(String jde_ntf_on_completeion) {
      if (this.isForceUpdate() || 
               (this.jde_ntf_on_completeion != null && !this.jde_ntf_on_completeion.equals(jde_ntf_on_completeion))  ||
               (jde_ntf_on_completeion != null && !jde_ntf_on_completeion.equals(this.jde_ntf_on_completeion)) ){
         this.jde_ntf_on_completeion_changed = true; 
         this.record_changed = true;
         this.jde_ntf_on_completeion = jde_ntf_on_completeion;
      }
   }
   public String getJde_ntf_on_completeion() {
      return this.jde_ntf_on_completeion;
   }
   public void setJde_ntf_tml_id(Double jde_ntf_tml_id) {
      if (this.isForceUpdate() || 
               (this.jde_ntf_tml_id != null && !this.jde_ntf_tml_id.equals(jde_ntf_tml_id))  ||
               (jde_ntf_tml_id != null && !jde_ntf_tml_id.equals(this.jde_ntf_tml_id)) ){
         this.jde_ntf_tml_id_changed = true; 
         this.record_changed = true;
         this.jde_ntf_tml_id = jde_ntf_tml_id;
      }
   }
   public Double getJde_ntf_tml_id() {
      return this.jde_ntf_tml_id;
   }
   public void setJde_ntf_tml_tmt_code(String jde_ntf_tml_tmt_code) {
      if (this.isForceUpdate() || 
               (this.jde_ntf_tml_tmt_code != null && !this.jde_ntf_tml_tmt_code.equals(jde_ntf_tml_tmt_code))  ||
               (jde_ntf_tml_tmt_code != null && !jde_ntf_tml_tmt_code.equals(this.jde_ntf_tml_tmt_code)) ){
         this.jde_ntf_tml_tmt_code_changed = true; 
         this.record_changed = true;
         this.jde_ntf_tml_tmt_code = jde_ntf_tml_tmt_code;
      }
   }
   public String getJde_ntf_tml_tmt_code() {
      return this.jde_ntf_tml_tmt_code;
   }
   public void setJde_email_tml_id(Double jde_email_tml_id) {
      if (this.isForceUpdate() || 
               (this.jde_email_tml_id != null && !this.jde_email_tml_id.equals(jde_email_tml_id))  ||
               (jde_email_tml_id != null && !jde_email_tml_id.equals(this.jde_email_tml_id)) ){
         this.jde_email_tml_id_changed = true; 
         this.record_changed = true;
         this.jde_email_tml_id = jde_email_tml_id;
      }
   }
   public Double getJde_email_tml_id() {
      return this.jde_email_tml_id;
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
            this.jde_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.jde_id;
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
      if (jde_system_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_system== null || EMPTY_STRING.equals(jde_system)) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_SYSTEM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (jde_system!= null && jde_system.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_SYSTEM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (jde_name_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_name== null || EMPTY_STRING.equals(jde_name)) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (jde_name!= null && jde_name.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (jde_type_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_type== null || EMPTY_STRING.equals(jde_type)) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (jde_type!= null && jde_type.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (jde_code_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_code== null || EMPTY_STRING.equals(jde_code)) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (jde_code!= null && jde_code.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (jde_status_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_status!= null && jde_status.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_STATUS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (jde_path_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_path!= null && jde_path.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_PATH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (jde_tml_id_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_tml_id!= null && (""+jde_tml_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_TML_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (jde_default_executer_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_default_executer!= null && jde_default_executer.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_DEFAULT_EXECUTER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (jde_execution_parameter_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_execution_parameter!= null && jde_execution_parameter.length()>2000000000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_EXECUTION_PARAMETER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000000000"));
         }
      }
      if (jde_execution_unit_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_execution_unit!= null && jde_execution_unit.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_EXECUTION_UNIT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (jde_action_type_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_action_type!= null && jde_action_type.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_ACTION_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (jde_default_output_type_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_default_output_type!= null && jde_default_output_type.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_DEFAULT_OUTPUT_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (jde_description_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_description!= null && jde_description.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (jde_period_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_period!= null && jde_period.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_PERIOD", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (jde_week_day_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_week_day!= null && (""+jde_week_day.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_WEEK_DAY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_month_day_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_month_day!= null && (""+jde_month_day.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_MONTH_DAY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_year_day_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_year_day!= null && (""+jde_year_day.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_YEAR_DAY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_hour_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_hour!= null && (""+jde_hour.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_HOUR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_minutes_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_minutes!= null && (""+jde_minutes.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_MINUTES", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_period_in_minutes_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_period_in_minutes!= null && (""+jde_period_in_minutes.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_PERIOD_IN_MINUTES", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_date_from== null) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (jde_days_in_history_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_days_in_history!= null && (""+jde_days_in_history.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_DAYS_IN_HISTORY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_days_in_request_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_days_in_request!= null && (""+jde_days_in_request.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_DAYS_IN_REQUEST", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jde_adjust_to_current_date_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_adjust_to_current_date!= null && jde_adjust_to_current_date.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_ADJUST_TO_CURRENT_DATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (jde_ntf_on_completeion_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_ntf_on_completeion!= null && jde_ntf_on_completeion.length()>1) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_NTF_ON_COMPLETEION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (jde_ntf_tml_id_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_ntf_tml_id!= null && (""+jde_ntf_tml_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_NTF_TML_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (jde_ntf_tml_tmt_code_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_ntf_tml_tmt_code!= null && jde_ntf_tml_tmt_code.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_NTF_TML_TMT_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (jde_email_tml_id_changed || operation == Utils.OPERATION_INSERT) {
         if (jde_email_tml_id!= null && (""+jde_email_tml_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "JDE_EMAIL_TML_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_DEFINITIONS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_JOB_DEFINITIONS SET ";
      boolean changedExists = false;      if (jde_system_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_SYSTEM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_system);
      }
      if (jde_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_name);
      }
      if (jde_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_type);
      }
      if (jde_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_code);
      }
      if (jde_status_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_STATUS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_status);
      }
      if (jde_path_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_PATH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_path);
      }
      if (jde_tml_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_TML_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_tml_id);
      }
      if (jde_default_executer_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DEFAULT_EXECUTER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_default_executer);
      }
      if (jde_execution_parameter_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_EXECUTION_PARAMETER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_execution_parameter);
      }
      if (jde_execution_unit_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_EXECUTION_UNIT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_execution_unit);
      }
      if (jde_action_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_ACTION_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_action_type);
      }
      if (jde_default_output_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DEFAULT_OUTPUT_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_default_output_type);
      }
      if (jde_last_action_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_LAST_ACTION_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(jde_last_action_time);
      }
      if (jde_next_action_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_NEXT_ACTION_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(jde_next_action_time);
      }
      if (jde_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_description);
      }
      if (jde_period_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_PERIOD = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_period);
      }
      if (jde_week_day_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_WEEK_DAY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_week_day);
      }
      if (jde_month_day_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_MONTH_DAY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_month_day);
      }
      if (jde_year_day_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_YEAR_DAY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_year_day);
      }
      if (jde_hour_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_HOUR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_hour);
      }
      if (jde_minutes_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_MINUTES = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_minutes);
      }
      if (jde_period_in_minutes_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_PERIOD_IN_MINUTES = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_period_in_minutes);
      }
      if (jde_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_date_from);
      }
      if (jde_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_date_to);
      }
      if (jde_days_in_history_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DAYS_IN_HISTORY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_days_in_history);
      }
      if (jde_days_in_request_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_DAYS_IN_REQUEST = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_days_in_request);
      }
      if (jde_adjust_to_current_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_ADJUST_TO_CURRENT_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_adjust_to_current_date);
      }
      if (jde_ntf_on_completeion_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_NTF_ON_COMPLETEION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_ntf_on_completeion);
      }
      if (jde_ntf_tml_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_NTF_TML_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_ntf_tml_id);
      }
      if (jde_ntf_tml_tmt_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_NTF_TML_TMT_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_ntf_tml_tmt_code);
      }
      if (jde_email_tml_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JDE_EMAIL_TML_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jde_email_tml_id);
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
      answer = answer +" WHERE  JDE_ID = ? ";
      updateStatementPart.addStatementParam(jde_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprJobDefinitionsDAO\":{\"jde_id\": \""+getJde_id()+"\", \"jde_system\": \""+getJde_system()+"\", \"jde_name\": \""+getJde_name()+"\", \"jde_type\": \""+getJde_type()+"\", \"jde_code\": \""+getJde_code()+"\", \"jde_status\": \""+getJde_status()+"\", \"jde_path\": \""+getJde_path()+"\", \"jde_tml_id\": \""+getJde_tml_id()+"\", \"jde_default_executer\": \""+getJde_default_executer()+"\", \"jde_execution_parameter\": \""+getJde_execution_parameter()+"\", \"jde_execution_unit\": \""+getJde_execution_unit()+"\", \"jde_action_type\": \""+getJde_action_type()+"\", \"jde_default_output_type\": \""+getJde_default_output_type()+"\", \"jde_last_action_time\": \""+getJde_last_action_time()+"\", \"jde_next_action_time\": \""+getJde_next_action_time()+"\", \"jde_description\": \""+getJde_description()+"\", \"jde_period\": \""+getJde_period()+"\", \"jde_week_day\": \""+getJde_week_day()+"\", \"jde_month_day\": \""+getJde_month_day()+"\", \"jde_year_day\": \""+getJde_year_day()+"\", \"jde_hour\": \""+getJde_hour()+"\", \"jde_minutes\": \""+getJde_minutes()+"\", \"jde_period_in_minutes\": \""+getJde_period_in_minutes()+"\", \"jde_date_from\": \""+getJde_date_from()+"\", \"jde_date_to\": \""+getJde_date_to()+"\", \"jde_days_in_history\": \""+getJde_days_in_history()+"\", \"jde_days_in_request\": \""+getJde_days_in_request()+"\", \"jde_adjust_to_current_date\": \""+getJde_adjust_to_current_date()+"\", \"jde_ntf_on_completeion\": \""+getJde_ntf_on_completeion()+"\", \"jde_ntf_tml_id\": \""+getJde_ntf_tml_id()+"\", \"jde_ntf_tml_tmt_code\": \""+getJde_ntf_tml_tmt_code()+"\", \"jde_email_tml_id\": \""+getJde_email_tml_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}