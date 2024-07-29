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

public class NtisFacilityUpdateAgreementDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_FACILITY_UPDATE_AGREEMENT.FUA_ID";
   private Double fua_id;
   private String fua_manufecturer;
   private String fua_model;
   private String fua_type;
   private String fua_state;
   private Date fua_created;
   private Double fua_per_id;
   private Double fua_wtf_id;
   private Double fua_confirmed_usr_id;
   private String fua_wtf_old_info_json;
   private String fua_wtf_new_info_json;
   private String fua_wtf_object_info_json;
   private Date fua_network_connection_date;
   private Double fua_fil_id;
   private Double fua_bn_id;
   private Double fua_org_id;
   private Double fua_so_id;
   private Double fua_req_org_id;
   private String fua_cancellation_reason;
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

   protected boolean fua_id_changed;
   protected boolean fua_manufecturer_changed;
   protected boolean fua_model_changed;
   protected boolean fua_type_changed;
   protected boolean fua_state_changed;
   protected boolean fua_created_changed;
   protected boolean fua_per_id_changed;
   protected boolean fua_wtf_id_changed;
   protected boolean fua_confirmed_usr_id_changed;
   protected boolean fua_wtf_old_info_json_changed;
   protected boolean fua_wtf_new_info_json_changed;
   protected boolean fua_wtf_object_info_json_changed;
   protected boolean fua_network_connection_date_changed;
   protected boolean fua_fil_id_changed;
   protected boolean fua_bn_id_changed;
   protected boolean fua_org_id_changed;
   protected boolean fua_so_id_changed;
   protected boolean fua_req_org_id_changed;
   protected boolean fua_cancellation_reason_changed;
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
   public NtisFacilityUpdateAgreementDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisFacilityUpdateAgreementDAOGen(Double fua_id, String fua_manufecturer, String fua_model, String fua_type, String fua_state, Date fua_created, Double fua_per_id, Double fua_wtf_id, Double fua_confirmed_usr_id, String fua_wtf_old_info_json, String fua_wtf_new_info_json, String fua_wtf_object_info_json, Date fua_network_connection_date, Double fua_fil_id, Double fua_bn_id, Double fua_org_id, Double fua_so_id, Double fua_req_org_id, String fua_cancellation_reason, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.fua_id = fua_id;
      this.fua_manufecturer = fua_manufecturer;
      this.fua_model = fua_model;
      this.fua_type = fua_type;
      this.fua_state = fua_state;
      this.fua_created = fua_created;
      this.fua_per_id = fua_per_id;
      this.fua_wtf_id = fua_wtf_id;
      this.fua_confirmed_usr_id = fua_confirmed_usr_id;
      this.fua_wtf_old_info_json = fua_wtf_old_info_json;
      this.fua_wtf_new_info_json = fua_wtf_new_info_json;
      this.fua_wtf_object_info_json = fua_wtf_object_info_json;
      this.fua_network_connection_date = fua_network_connection_date;
      this.fua_fil_id = fua_fil_id;
      this.fua_bn_id = fua_bn_id;
      this.fua_org_id = fua_org_id;
      this.fua_so_id = fua_so_id;
      this.fua_req_org_id = fua_req_org_id;
      this.fua_cancellation_reason = fua_cancellation_reason;
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
   public void copyValues(NtisFacilityUpdateAgreementDAOGen obj) {
      this.setFua_id(obj.getFua_id());
      this.setFua_manufecturer(obj.getFua_manufecturer());
      this.setFua_model(obj.getFua_model());
      this.setFua_type(obj.getFua_type());
      this.setFua_state(obj.getFua_state());
      this.setFua_created(obj.getFua_created());
      this.setFua_per_id(obj.getFua_per_id());
      this.setFua_wtf_id(obj.getFua_wtf_id());
      this.setFua_confirmed_usr_id(obj.getFua_confirmed_usr_id());
      this.setFua_wtf_old_info_json(obj.getFua_wtf_old_info_json());
      this.setFua_wtf_new_info_json(obj.getFua_wtf_new_info_json());
      this.setFua_wtf_object_info_json(obj.getFua_wtf_object_info_json());
      this.setFua_network_connection_date(obj.getFua_network_connection_date());
      this.setFua_fil_id(obj.getFua_fil_id());
      this.setFua_bn_id(obj.getFua_bn_id());
      this.setFua_org_id(obj.getFua_org_id());
      this.setFua_so_id(obj.getFua_so_id());
      this.setFua_req_org_id(obj.getFua_req_org_id());
      this.setFua_cancellation_reason(obj.getFua_cancellation_reason());
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
      this.fua_id_changed = false;
      this.fua_manufecturer_changed = false;
      this.fua_model_changed = false;
      this.fua_type_changed = false;
      this.fua_state_changed = false;
      this.fua_created_changed = false;
      this.fua_per_id_changed = false;
      this.fua_wtf_id_changed = false;
      this.fua_confirmed_usr_id_changed = false;
      this.fua_wtf_old_info_json_changed = false;
      this.fua_wtf_new_info_json_changed = false;
      this.fua_wtf_object_info_json_changed = false;
      this.fua_network_connection_date_changed = false;
      this.fua_fil_id_changed = false;
      this.fua_bn_id_changed = false;
      this.fua_org_id_changed = false;
      this.fua_so_id_changed = false;
      this.fua_req_org_id_changed = false;
      this.fua_cancellation_reason_changed = false;
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
   public void setFua_id(Double fua_id) {
      if (this.isForceUpdate() || (this.fua_id != null && !this.fua_id.equals(fua_id)) || (fua_id != null && !fua_id.equals(this.fua_id))){
         this.fua_id_changed = true; 
         this.record_changed = true;
         this.fua_id = fua_id;
      }
   }
   public Double getFua_id() {
      return this.fua_id;
   }
   public void setFua_manufecturer(String fua_manufecturer) {
      if (this.isForceUpdate() || (this.fua_manufecturer != null && !this.fua_manufecturer.equals(fua_manufecturer)) || (fua_manufecturer != null && !fua_manufecturer.equals(this.fua_manufecturer))){
         this.fua_manufecturer_changed = true; 
         this.record_changed = true;
         this.fua_manufecturer = fua_manufecturer;
      }
   }
   public String getFua_manufecturer() {
      return this.fua_manufecturer;
   }
   public void setFua_model(String fua_model) {
      if (this.isForceUpdate() || (this.fua_model != null && !this.fua_model.equals(fua_model)) || (fua_model != null && !fua_model.equals(this.fua_model))){
         this.fua_model_changed = true; 
         this.record_changed = true;
         this.fua_model = fua_model;
      }
   }
   public String getFua_model() {
      return this.fua_model;
   }
   public void setFua_type(String fua_type) {
      if (this.isForceUpdate() || (this.fua_type != null && !this.fua_type.equals(fua_type)) || (fua_type != null && !fua_type.equals(this.fua_type))){
         this.fua_type_changed = true; 
         this.record_changed = true;
         this.fua_type = fua_type;
      }
   }
   public String getFua_type() {
      return this.fua_type;
   }
   public void setFua_state(String fua_state) {
      if (this.isForceUpdate() || (this.fua_state != null && !this.fua_state.equals(fua_state)) || (fua_state != null && !fua_state.equals(this.fua_state))){
         this.fua_state_changed = true; 
         this.record_changed = true;
         this.fua_state = fua_state;
      }
   }
   public String getFua_state() {
      return this.fua_state;
   }
   public void setFua_created(Date fua_created) {
      if (this.isForceUpdate() || (this.fua_created != null && !this.fua_created.equals(fua_created)) || (fua_created != null && !fua_created.equals(this.fua_created))){
         this.fua_created_changed = true; 
         this.record_changed = true;
         this.fua_created = fua_created;
      }
   }
   public Date getFua_created() {
      return this.fua_created;
   }
   public void setFua_per_id(Double fua_per_id) {
      if (this.isForceUpdate() || (this.fua_per_id != null && !this.fua_per_id.equals(fua_per_id)) || (fua_per_id != null && !fua_per_id.equals(this.fua_per_id))){
         this.fua_per_id_changed = true; 
         this.record_changed = true;
         this.fua_per_id = fua_per_id;
      }
   }
   public Double getFua_per_id() {
      return this.fua_per_id;
   }
   public void setFua_wtf_id(Double fua_wtf_id) {
      if (this.isForceUpdate() || (this.fua_wtf_id != null && !this.fua_wtf_id.equals(fua_wtf_id)) || (fua_wtf_id != null && !fua_wtf_id.equals(this.fua_wtf_id))){
         this.fua_wtf_id_changed = true; 
         this.record_changed = true;
         this.fua_wtf_id = fua_wtf_id;
      }
   }
   public Double getFua_wtf_id() {
      return this.fua_wtf_id;
   }
   public void setFua_confirmed_usr_id(Double fua_confirmed_usr_id) {
      if (this.isForceUpdate() || (this.fua_confirmed_usr_id != null && !this.fua_confirmed_usr_id.equals(fua_confirmed_usr_id)) || (fua_confirmed_usr_id != null && !fua_confirmed_usr_id.equals(this.fua_confirmed_usr_id))){
         this.fua_confirmed_usr_id_changed = true; 
         this.record_changed = true;
         this.fua_confirmed_usr_id = fua_confirmed_usr_id;
      }
   }
   public Double getFua_confirmed_usr_id() {
      return this.fua_confirmed_usr_id;
   }
   public void setFua_wtf_old_info_json(String fua_wtf_old_info_json) {
      if (this.isForceUpdate() || (this.fua_wtf_old_info_json != null && !this.fua_wtf_old_info_json.equals(fua_wtf_old_info_json)) || (fua_wtf_old_info_json != null && !fua_wtf_old_info_json.equals(this.fua_wtf_old_info_json))){
         this.fua_wtf_old_info_json_changed = true; 
         this.record_changed = true;
         this.fua_wtf_old_info_json = fua_wtf_old_info_json;
      }
   }
   public String getFua_wtf_old_info_json() {
      return this.fua_wtf_old_info_json;
   }
   public void setFua_wtf_new_info_json(String fua_wtf_new_info_json) {
      if (this.isForceUpdate() || (this.fua_wtf_new_info_json != null && !this.fua_wtf_new_info_json.equals(fua_wtf_new_info_json)) || (fua_wtf_new_info_json != null && !fua_wtf_new_info_json.equals(this.fua_wtf_new_info_json))){
         this.fua_wtf_new_info_json_changed = true; 
         this.record_changed = true;
         this.fua_wtf_new_info_json = fua_wtf_new_info_json;
      }
   }
   public String getFua_wtf_new_info_json() {
      return this.fua_wtf_new_info_json;
   }
   public void setFua_wtf_object_info_json(String fua_wtf_object_info_json) {
      if (this.isForceUpdate() || (this.fua_wtf_object_info_json != null && !this.fua_wtf_object_info_json.equals(fua_wtf_object_info_json)) || (fua_wtf_object_info_json != null && !fua_wtf_object_info_json.equals(this.fua_wtf_object_info_json))){
         this.fua_wtf_object_info_json_changed = true; 
         this.record_changed = true;
         this.fua_wtf_object_info_json = fua_wtf_object_info_json;
      }
   }
   public String getFua_wtf_object_info_json() {
      return this.fua_wtf_object_info_json;
   }
   public void setFua_network_connection_date(Date fua_network_connection_date) {
      if (this.isForceUpdate() || (this.fua_network_connection_date != null && !this.fua_network_connection_date.equals(fua_network_connection_date)) || (fua_network_connection_date != null && !fua_network_connection_date.equals(this.fua_network_connection_date))){
         this.fua_network_connection_date_changed = true; 
         this.record_changed = true;
         this.fua_network_connection_date = fua_network_connection_date;
      }
   }
   public Date getFua_network_connection_date() {
      return this.fua_network_connection_date;
   }
   public void setFua_fil_id(Double fua_fil_id) {
      if (this.isForceUpdate() || (this.fua_fil_id != null && !this.fua_fil_id.equals(fua_fil_id)) || (fua_fil_id != null && !fua_fil_id.equals(this.fua_fil_id))){
         this.fua_fil_id_changed = true; 
         this.record_changed = true;
         this.fua_fil_id = fua_fil_id;
      }
   }
   public Double getFua_fil_id() {
      return this.fua_fil_id;
   }
   public void setFua_bn_id(Double fua_bn_id) {
      if (this.isForceUpdate() || (this.fua_bn_id != null && !this.fua_bn_id.equals(fua_bn_id)) || (fua_bn_id != null && !fua_bn_id.equals(this.fua_bn_id))){
         this.fua_bn_id_changed = true; 
         this.record_changed = true;
         this.fua_bn_id = fua_bn_id;
      }
   }
   public Double getFua_bn_id() {
      return this.fua_bn_id;
   }
   public void setFua_org_id(Double fua_org_id) {
      if (this.isForceUpdate() || (this.fua_org_id != null && !this.fua_org_id.equals(fua_org_id)) || (fua_org_id != null && !fua_org_id.equals(this.fua_org_id))){
         this.fua_org_id_changed = true; 
         this.record_changed = true;
         this.fua_org_id = fua_org_id;
      }
   }
   public Double getFua_org_id() {
      return this.fua_org_id;
   }
   public void setFua_so_id(Double fua_so_id) {
      if (this.isForceUpdate() || (this.fua_so_id != null && !this.fua_so_id.equals(fua_so_id)) || (fua_so_id != null && !fua_so_id.equals(this.fua_so_id))){
         this.fua_so_id_changed = true; 
         this.record_changed = true;
         this.fua_so_id = fua_so_id;
      }
   }
   public Double getFua_so_id() {
      return this.fua_so_id;
   }
   public void setFua_req_org_id(Double fua_req_org_id) {
      if (this.isForceUpdate() || (this.fua_req_org_id != null && !this.fua_req_org_id.equals(fua_req_org_id)) || (fua_req_org_id != null && !fua_req_org_id.equals(this.fua_req_org_id))){
         this.fua_req_org_id_changed = true; 
         this.record_changed = true;
         this.fua_req_org_id = fua_req_org_id;
      }
   }
   public Double getFua_req_org_id() {
      return this.fua_req_org_id;
   }
   public void setFua_cancellation_reason(String fua_cancellation_reason) {
      if (this.isForceUpdate() || (this.fua_cancellation_reason != null && !this.fua_cancellation_reason.equals(fua_cancellation_reason)) || (fua_cancellation_reason != null && !fua_cancellation_reason.equals(this.fua_cancellation_reason))){
         this.fua_cancellation_reason_changed = true; 
         this.record_changed = true;
         this.fua_cancellation_reason = fua_cancellation_reason;
      }
   }
   public String getFua_cancellation_reason() {
      return this.fua_cancellation_reason;
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
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.fua_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.fua_id;
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
      if (fua_manufecturer_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_manufecturer!= null && fua_manufecturer.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_MANUFECTURER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (fua_model_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_model!= null && fua_model.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_MODEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (fua_type_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_type!= null && fua_type.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (fua_state_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_state== null || EMPTY_STRING.equals(fua_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (fua_state!= null && fua_state.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (fua_created_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_created== null) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_CREATED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (fua_per_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_per_id!= null && (""+fua_per_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_PER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_wtf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_wtf_id!= null && (""+fua_wtf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_WTF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_wtf_old_info_json_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_wtf_old_info_json!= null && fua_wtf_old_info_json.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_WTF_OLD_INFO_JSON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (fua_wtf_new_info_json_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_wtf_new_info_json!= null && fua_wtf_new_info_json.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_WTF_NEW_INFO_JSON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (fua_wtf_object_info_json_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_wtf_object_info_json!= null && fua_wtf_object_info_json.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_WTF_OBJECT_INFO_JSON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (fua_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_fil_id!= null && (""+fua_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_bn_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_bn_id!= null && (""+fua_bn_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_BN_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_org_id!= null && (""+fua_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_so_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_so_id!= null && (""+fua_so_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_SO_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_req_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_req_org_id!= null && (""+fua_req_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_REQ_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (fua_cancellation_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (fua_cancellation_reason!= null && fua_cancellation_reason.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "FUA_CANCELLATION_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_FACILITY_UPDATE_AGREEMENT", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_FACILITY_UPDATE_AGREEMENT SET ";
      boolean changedExists = false;      if (fua_manufecturer_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_MANUFECTURER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_manufecturer);
      }
      if (fua_model_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_MODEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_model);
      }
      if (fua_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_type);
      }
      if (fua_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_state);
      }
      if (fua_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(fua_created);
      }
      if (fua_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_per_id);
      }
      if (fua_wtf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_WTF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_wtf_id);
      }
      if (fua_confirmed_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_CONFIRMED_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_confirmed_usr_id);
      }
      if (fua_wtf_old_info_json_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_WTF_OLD_INFO_JSON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_wtf_old_info_json);
      }
      if (fua_wtf_new_info_json_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_WTF_NEW_INFO_JSON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_wtf_new_info_json);
      }
      if (fua_wtf_object_info_json_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_WTF_OBJECT_INFO_JSON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_wtf_object_info_json);
      }
      if (fua_network_connection_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_NETWORK_CONNECTION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(fua_network_connection_date);
      }
      if (fua_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_fil_id);
      }
      if (fua_bn_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_BN_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_bn_id);
      }
      if (fua_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_org_id);
      }
      if (fua_so_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_SO_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_so_id);
      }
      if (fua_req_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_REQ_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_req_org_id);
      }
      if (fua_cancellation_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"FUA_CANCELLATION_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(fua_cancellation_reason);
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
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  FUA_ID = ? ";
      updateStatementPart.addStatementParam(fua_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisFacilityUpdateAgreementDAO\":{\"fua_id\": \""+getFua_id()+"\", \"fua_manufecturer\": \""+getFua_manufecturer()+"\", \"fua_model\": \""+getFua_model()+"\", \"fua_type\": \""+getFua_type()+"\", \"fua_state\": \""+getFua_state()+"\", \"fua_created\": \""+getFua_created()+"\", \"fua_per_id\": \""+getFua_per_id()+"\", \"fua_wtf_id\": \""+getFua_wtf_id()+"\", \"fua_confirmed_usr_id\": \""+getFua_confirmed_usr_id()+"\", \"fua_wtf_old_info_json\": \""+getFua_wtf_old_info_json()+"\", \"fua_wtf_new_info_json\": \""+getFua_wtf_new_info_json()+"\", \"fua_wtf_object_info_json\": \""+getFua_wtf_object_info_json()+"\", \"fua_network_connection_date\": \""+getFua_network_connection_date()+"\", \"fua_fil_id\": \""+getFua_fil_id()+"\", \"fua_bn_id\": \""+getFua_bn_id()+"\", \"fua_org_id\": \""+getFua_org_id()+"\", \"fua_so_id\": \""+getFua_so_id()+"\", \"fua_req_org_id\": \""+getFua_req_org_id()+"\", \"fua_cancellation_reason\": \""+getFua_cancellation_reason()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}