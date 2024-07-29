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

public class SprJobRequestsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_JOB_REQUESTS.JRQ_ID";
   private Double jrq_id;
   private String jrq_status;
   private String jrq_type;
   private Double jrq_fil_id;
   private String jrq_host_created;
   private Double jrq_jde_id;
   private Double jrq_usr_id;
   private String jrq_executer_name;
   private Date jrq_request_time;
   private Date jrq_result_time;
   private Date jrq_start_date;
   private Date jrq_end_date;
   private String jrq_result_type;
   private String jrq_error;
   private String jrq_priority;
   private String jrq_data;
   private String jrq_lang;
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

   protected boolean jrq_id_changed;
   protected boolean jrq_status_changed;
   protected boolean jrq_type_changed;
   protected boolean jrq_fil_id_changed;
   protected boolean jrq_host_created_changed;
   protected boolean jrq_jde_id_changed;
   protected boolean jrq_usr_id_changed;
   protected boolean jrq_executer_name_changed;
   protected boolean jrq_request_time_changed;
   protected boolean jrq_result_time_changed;
   protected boolean jrq_start_date_changed;
   protected boolean jrq_end_date_changed;
   protected boolean jrq_result_type_changed;
   protected boolean jrq_error_changed;
   protected boolean jrq_priority_changed;
   protected boolean jrq_data_changed;
   protected boolean jrq_lang_changed;
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
   public SprJobRequestsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprJobRequestsDAOGen(Double jrq_id, String jrq_status, String jrq_type, Double jrq_fil_id, String jrq_host_created, Double jrq_jde_id, Double jrq_usr_id, String jrq_executer_name, Date jrq_request_time, Date jrq_result_time, Date jrq_start_date, Date jrq_end_date, String jrq_result_type, String jrq_error, String jrq_priority, String jrq_data, String jrq_lang, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.jrq_id = jrq_id;
      this.jrq_status = jrq_status;
      this.jrq_type = jrq_type;
      this.jrq_fil_id = jrq_fil_id;
      this.jrq_host_created = jrq_host_created;
      this.jrq_jde_id = jrq_jde_id;
      this.jrq_usr_id = jrq_usr_id;
      this.jrq_executer_name = jrq_executer_name;
      this.jrq_request_time = jrq_request_time;
      this.jrq_result_time = jrq_result_time;
      this.jrq_start_date = jrq_start_date;
      this.jrq_end_date = jrq_end_date;
      this.jrq_result_type = jrq_result_type;
      this.jrq_error = jrq_error;
      this.jrq_priority = jrq_priority;
      this.jrq_data = jrq_data;
      this.jrq_lang = jrq_lang;
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
   public void copyValues(SprJobRequestsDAOGen obj) {
      this.setJrq_id(obj.getJrq_id());
      this.setJrq_status(obj.getJrq_status());
      this.setJrq_type(obj.getJrq_type());
      this.setJrq_fil_id(obj.getJrq_fil_id());
      this.setJrq_host_created(obj.getJrq_host_created());
      this.setJrq_jde_id(obj.getJrq_jde_id());
      this.setJrq_usr_id(obj.getJrq_usr_id());
      this.setJrq_executer_name(obj.getJrq_executer_name());
      this.setJrq_request_time(obj.getJrq_request_time());
      this.setJrq_result_time(obj.getJrq_result_time());
      this.setJrq_start_date(obj.getJrq_start_date());
      this.setJrq_end_date(obj.getJrq_end_date());
      this.setJrq_result_type(obj.getJrq_result_type());
      this.setJrq_error(obj.getJrq_error());
      this.setJrq_priority(obj.getJrq_priority());
      this.setJrq_data(obj.getJrq_data());
      this.setJrq_lang(obj.getJrq_lang());
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
      this.jrq_id_changed = false;
      this.jrq_status_changed = false;
      this.jrq_type_changed = false;
      this.jrq_fil_id_changed = false;
      this.jrq_host_created_changed = false;
      this.jrq_jde_id_changed = false;
      this.jrq_usr_id_changed = false;
      this.jrq_executer_name_changed = false;
      this.jrq_request_time_changed = false;
      this.jrq_result_time_changed = false;
      this.jrq_start_date_changed = false;
      this.jrq_end_date_changed = false;
      this.jrq_result_type_changed = false;
      this.jrq_error_changed = false;
      this.jrq_priority_changed = false;
      this.jrq_data_changed = false;
      this.jrq_lang_changed = false;
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
   public void setJrq_id(Double jrq_id) {
      if (this.isForceUpdate() || 
               (this.jrq_id != null && !this.jrq_id.equals(jrq_id))  ||
               (jrq_id != null && !jrq_id.equals(this.jrq_id)) ){
         this.jrq_id_changed = true; 
         this.record_changed = true;
         this.jrq_id = jrq_id;
      }
   }
   public Double getJrq_id() {
      return this.jrq_id;
   }
   public void setJrq_status(String jrq_status) {
      if (this.isForceUpdate() || 
               (this.jrq_status != null && !this.jrq_status.equals(jrq_status))  ||
               (jrq_status != null && !jrq_status.equals(this.jrq_status)) ){
         this.jrq_status_changed = true; 
         this.record_changed = true;
         this.jrq_status = jrq_status;
      }
   }
   public String getJrq_status() {
      return this.jrq_status;
   }
   public void setJrq_type(String jrq_type) {
      if (this.isForceUpdate() || 
               (this.jrq_type != null && !this.jrq_type.equals(jrq_type))  ||
               (jrq_type != null && !jrq_type.equals(this.jrq_type)) ){
         this.jrq_type_changed = true; 
         this.record_changed = true;
         this.jrq_type = jrq_type;
      }
   }
   public String getJrq_type() {
      return this.jrq_type;
   }
   public void setJrq_fil_id(Double jrq_fil_id) {
      if (this.isForceUpdate() || 
               (this.jrq_fil_id != null && !this.jrq_fil_id.equals(jrq_fil_id))  ||
               (jrq_fil_id != null && !jrq_fil_id.equals(this.jrq_fil_id)) ){
         this.jrq_fil_id_changed = true; 
         this.record_changed = true;
         this.jrq_fil_id = jrq_fil_id;
      }
   }
   public Double getJrq_fil_id() {
      return this.jrq_fil_id;
   }
   public void setJrq_host_created(String jrq_host_created) {
      if (this.isForceUpdate() || 
               (this.jrq_host_created != null && !this.jrq_host_created.equals(jrq_host_created))  ||
               (jrq_host_created != null && !jrq_host_created.equals(this.jrq_host_created)) ){
         this.jrq_host_created_changed = true; 
         this.record_changed = true;
         this.jrq_host_created = jrq_host_created;
      }
   }
   public String getJrq_host_created() {
      return this.jrq_host_created;
   }
   public void setJrq_jde_id(Double jrq_jde_id) {
      if (this.isForceUpdate() || 
               (this.jrq_jde_id != null && !this.jrq_jde_id.equals(jrq_jde_id))  ||
               (jrq_jde_id != null && !jrq_jde_id.equals(this.jrq_jde_id)) ){
         this.jrq_jde_id_changed = true; 
         this.record_changed = true;
         this.jrq_jde_id = jrq_jde_id;
      }
   }
   public Double getJrq_jde_id() {
      return this.jrq_jde_id;
   }
   public void setJrq_usr_id(Double jrq_usr_id) {
      if (this.isForceUpdate() || 
               (this.jrq_usr_id != null && !this.jrq_usr_id.equals(jrq_usr_id))  ||
               (jrq_usr_id != null && !jrq_usr_id.equals(this.jrq_usr_id)) ){
         this.jrq_usr_id_changed = true; 
         this.record_changed = true;
         this.jrq_usr_id = jrq_usr_id;
      }
   }
   public Double getJrq_usr_id() {
      return this.jrq_usr_id;
   }
   public void setJrq_executer_name(String jrq_executer_name) {
      if (this.isForceUpdate() || 
               (this.jrq_executer_name != null && !this.jrq_executer_name.equals(jrq_executer_name))  ||
               (jrq_executer_name != null && !jrq_executer_name.equals(this.jrq_executer_name)) ){
         this.jrq_executer_name_changed = true; 
         this.record_changed = true;
         this.jrq_executer_name = jrq_executer_name;
      }
   }
   public String getJrq_executer_name() {
      return this.jrq_executer_name;
   }
   public void setJrq_request_time(Date jrq_request_time) {
      if (this.isForceUpdate() || 
               (this.jrq_request_time != null && (jrq_request_time == null ||this.jrq_request_time.compareTo(jrq_request_time) != 0 )) ||
               (jrq_request_time != null && (this.jrq_request_time == null ||jrq_request_time.compareTo(this.jrq_request_time) != 0 ))){
         this.jrq_request_time_changed = true; 
         this.record_changed = true;
         this.jrq_request_time = jrq_request_time;
      }
   }
   public Date getJrq_request_time() {
      return this.jrq_request_time;
   }
   public void setJrq_result_time(Date jrq_result_time) {
      if (this.isForceUpdate() || 
               (this.jrq_result_time != null && (jrq_result_time == null ||this.jrq_result_time.compareTo(jrq_result_time) != 0 )) ||
               (jrq_result_time != null && (this.jrq_result_time == null ||jrq_result_time.compareTo(this.jrq_result_time) != 0 ))){
         this.jrq_result_time_changed = true; 
         this.record_changed = true;
         this.jrq_result_time = jrq_result_time;
      }
   }
   public Date getJrq_result_time() {
      return this.jrq_result_time;
   }
   public void setJrq_start_date(Date jrq_start_date) {
      if (this.isForceUpdate() || 
               (this.jrq_start_date != null && (jrq_start_date == null ||this.jrq_start_date.compareTo(jrq_start_date) != 0 )) ||
               (jrq_start_date != null && (this.jrq_start_date == null ||jrq_start_date.compareTo(this.jrq_start_date) != 0 ))){
         this.jrq_start_date_changed = true; 
         this.record_changed = true;
         this.jrq_start_date = jrq_start_date;
      }
   }
   public Date getJrq_start_date() {
      return this.jrq_start_date;
   }
   public void setJrq_end_date(Date jrq_end_date) {
      if (this.isForceUpdate() || 
               (this.jrq_end_date != null && (jrq_end_date == null ||this.jrq_end_date.compareTo(jrq_end_date) != 0 )) ||
               (jrq_end_date != null && (this.jrq_end_date == null ||jrq_end_date.compareTo(this.jrq_end_date) != 0 ))){
         this.jrq_end_date_changed = true; 
         this.record_changed = true;
         this.jrq_end_date = jrq_end_date;
      }
   }
   public Date getJrq_end_date() {
      return this.jrq_end_date;
   }
   public void setJrq_result_type(String jrq_result_type) {
      if (this.isForceUpdate() || 
               (this.jrq_result_type != null && !this.jrq_result_type.equals(jrq_result_type))  ||
               (jrq_result_type != null && !jrq_result_type.equals(this.jrq_result_type)) ){
         this.jrq_result_type_changed = true; 
         this.record_changed = true;
         this.jrq_result_type = jrq_result_type;
      }
   }
   public String getJrq_result_type() {
      return this.jrq_result_type;
   }
   public void setJrq_error(String jrq_error) {
      if (this.isForceUpdate() || 
               (this.jrq_error != null && !this.jrq_error.equals(jrq_error))  ||
               (jrq_error != null && !jrq_error.equals(this.jrq_error)) ){
         this.jrq_error_changed = true; 
         this.record_changed = true;
         this.jrq_error = jrq_error;
      }
   }
   public String getJrq_error() {
      return this.jrq_error;
   }
   public void setJrq_priority(String jrq_priority) {
      if (this.isForceUpdate() || 
               (this.jrq_priority != null && !this.jrq_priority.equals(jrq_priority))  ||
               (jrq_priority != null && !jrq_priority.equals(this.jrq_priority)) ){
         this.jrq_priority_changed = true; 
         this.record_changed = true;
         this.jrq_priority = jrq_priority;
      }
   }
   public String getJrq_priority() {
      return this.jrq_priority;
   }
   public void setJrq_data(String jrq_data) {
      if (this.isForceUpdate() || 
               (this.jrq_data != null && !this.jrq_data.equals(jrq_data))  ||
               (jrq_data != null && !jrq_data.equals(this.jrq_data)) ){
         this.jrq_data_changed = true; 
         this.record_changed = true;
         this.jrq_data = jrq_data;
      }
   }
   public String getJrq_data() {
      return this.jrq_data;
   }
   public void setJrq_lang(String jrq_lang) {
      if (this.isForceUpdate() || 
               (this.jrq_lang != null && !this.jrq_lang.equals(jrq_lang))  ||
               (jrq_lang != null && !jrq_lang.equals(this.jrq_lang)) ){
         this.jrq_lang_changed = true; 
         this.record_changed = true;
         this.jrq_lang = jrq_lang;
      }
   }
   public String getJrq_lang() {
      return this.jrq_lang;
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
            this.jrq_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.jrq_id;
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
      if (jrq_status_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_status== null || EMPTY_STRING.equals(jrq_status)) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_STATUS", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (jrq_status!= null && jrq_status.length()>100) {
               throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_STATUS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
            }
         }
      }
      if (jrq_type_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_type!= null && jrq_type.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (jrq_host_created_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_host_created!= null && jrq_host_created.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_HOST_CREATED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (jrq_jde_id_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_jde_id== null) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_JDE_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (jrq_jde_id!= null && (""+jrq_jde_id.intValue()).length()>12) {
               throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_JDE_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
            }
         }
      }
      if (jrq_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_usr_id!= null && (""+jrq_usr_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (jrq_executer_name_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_executer_name!= null && jrq_executer_name.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_EXECUTER_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (jrq_result_type_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_result_type!= null && jrq_result_type.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_RESULT_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (jrq_error_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_error!= null && jrq_error.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_ERROR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (jrq_priority_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_priority!= null && jrq_priority.length()>100) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_PRIORITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"100"));
         }
      }
      if (jrq_data_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_data!= null && jrq_data.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (jrq_lang_changed || operation == Utils.OPERATION_INSERT) {
         if (jrq_lang!= null && jrq_lang.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "JRQ_LANG", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_JOB_REQUESTS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_JOB_REQUESTS SET ";
      boolean changedExists = false;      if (jrq_status_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_STATUS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_status);
      }
      if (jrq_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_type);
      }
      if (jrq_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_fil_id);
      }
      if (jrq_host_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_HOST_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_host_created);
      }
      if (jrq_jde_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_JDE_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_jde_id);
      }
      if (jrq_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_usr_id);
      }
      if (jrq_executer_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_EXECUTER_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_executer_name);
      }
      if (jrq_request_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_REQUEST_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(jrq_request_time);
      }
      if (jrq_result_time_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_RESULT_TIME = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(jrq_result_time);
      }
      if (jrq_start_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_START_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(jrq_start_date);
      }
      if (jrq_end_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_END_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(jrq_end_date);
      }
      if (jrq_result_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_RESULT_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_result_type);
      }
      if (jrq_error_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_ERROR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_error);
      }
      if (jrq_priority_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_PRIORITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_priority);
      }
      if (jrq_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_data);
      }
      if (jrq_lang_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"JRQ_LANG = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(jrq_lang);
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
      answer = answer +" WHERE  JRQ_ID = ? ";
      updateStatementPart.addStatementParam(jrq_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprJobRequestsDAO\":{\"jrq_id\": \""+getJrq_id()+"\", \"jrq_status\": \""+getJrq_status()+"\", \"jrq_type\": \""+getJrq_type()+"\", \"jrq_fil_id\": \""+getJrq_fil_id()+"\", \"jrq_host_created\": \""+getJrq_host_created()+"\", \"jrq_jde_id\": \""+getJrq_jde_id()+"\", \"jrq_usr_id\": \""+getJrq_usr_id()+"\", \"jrq_executer_name\": \""+getJrq_executer_name()+"\", \"jrq_request_time\": \""+getJrq_request_time()+"\", \"jrq_result_time\": \""+getJrq_result_time()+"\", \"jrq_start_date\": \""+getJrq_start_date()+"\", \"jrq_end_date\": \""+getJrq_end_date()+"\", \"jrq_result_type\": \""+getJrq_result_type()+"\", \"jrq_error\": \""+getJrq_error()+"\", \"jrq_priority\": \""+getJrq_priority()+"\", \"jrq_data\": \""+getJrq_data()+"\", \"jrq_lang\": \""+getJrq_lang()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}