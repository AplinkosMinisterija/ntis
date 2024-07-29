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

public class NtisOrdersDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_ORDERS.ORD_ID";
   private Double ord_id;
   private String ord_additional_description;
   private String ord_state;
   private String ord_email;
   private String ord_type;
   private String ord_compliance_norms;
   private String ord_phone_number;
   private String ord_created_in_ntis_portal;
   private String ord_rejection_reason;
   private Date ord_rejection_date;
   private Date ord_completion_request_date;
   private Date ord_completion_estimate_date;
   private String ord_planned_works_description;
   private Date ord_created;
   private Date ord_removed_sewage_date;
   private Date ord_prefered_date_from;
   private Date ord_prefered_date_to;
   private Double ord_org_id;
   private Double ord_per_id;
   private Double ord_usr_id;
   private Double ord_wtf_id;
   private Double ord_srv_id;
   private Double ord_cs_id;
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

   protected boolean ord_id_changed;
   protected boolean ord_additional_description_changed;
   protected boolean ord_state_changed;
   protected boolean ord_email_changed;
   protected boolean ord_type_changed;
   protected boolean ord_compliance_norms_changed;
   protected boolean ord_phone_number_changed;
   protected boolean ord_created_in_ntis_portal_changed;
   protected boolean ord_rejection_reason_changed;
   protected boolean ord_rejection_date_changed;
   protected boolean ord_completion_request_date_changed;
   protected boolean ord_completion_estimate_date_changed;
   protected boolean ord_planned_works_description_changed;
   protected boolean ord_created_changed;
   protected boolean ord_removed_sewage_date_changed;
   protected boolean ord_prefered_date_from_changed;
   protected boolean ord_prefered_date_to_changed;
   protected boolean ord_org_id_changed;
   protected boolean ord_per_id_changed;
   protected boolean ord_usr_id_changed;
   protected boolean ord_wtf_id_changed;
   protected boolean ord_srv_id_changed;
   protected boolean ord_cs_id_changed;
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
   public NtisOrdersDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisOrdersDAOGen(Double ord_id, String ord_additional_description, String ord_state, String ord_email, String ord_type, String ord_compliance_norms, String ord_phone_number, String ord_created_in_ntis_portal, String ord_rejection_reason, Date ord_rejection_date, Date ord_completion_request_date, Date ord_completion_estimate_date, String ord_planned_works_description, Date ord_created, Date ord_removed_sewage_date, Date ord_prefered_date_from, Date ord_prefered_date_to, Double ord_org_id, Double ord_per_id, Double ord_usr_id, Double ord_wtf_id, Double ord_srv_id, Double ord_cs_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.ord_id = ord_id;
      this.ord_additional_description = ord_additional_description;
      this.ord_state = ord_state;
      this.ord_email = ord_email;
      this.ord_type = ord_type;
      this.ord_compliance_norms = ord_compliance_norms;
      this.ord_phone_number = ord_phone_number;
      this.ord_created_in_ntis_portal = ord_created_in_ntis_portal;
      this.ord_rejection_reason = ord_rejection_reason;
      this.ord_rejection_date = ord_rejection_date;
      this.ord_completion_request_date = ord_completion_request_date;
      this.ord_completion_estimate_date = ord_completion_estimate_date;
      this.ord_planned_works_description = ord_planned_works_description;
      this.ord_created = ord_created;
      this.ord_removed_sewage_date = ord_removed_sewage_date;
      this.ord_prefered_date_from = ord_prefered_date_from;
      this.ord_prefered_date_to = ord_prefered_date_to;
      this.ord_org_id = ord_org_id;
      this.ord_per_id = ord_per_id;
      this.ord_usr_id = ord_usr_id;
      this.ord_wtf_id = ord_wtf_id;
      this.ord_srv_id = ord_srv_id;
      this.ord_cs_id = ord_cs_id;
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
   public void copyValues(NtisOrdersDAOGen obj) {
      this.setOrd_id(obj.getOrd_id());
      this.setOrd_additional_description(obj.getOrd_additional_description());
      this.setOrd_state(obj.getOrd_state());
      this.setOrd_email(obj.getOrd_email());
      this.setOrd_type(obj.getOrd_type());
      this.setOrd_compliance_norms(obj.getOrd_compliance_norms());
      this.setOrd_phone_number(obj.getOrd_phone_number());
      this.setOrd_created_in_ntis_portal(obj.getOrd_created_in_ntis_portal());
      this.setOrd_rejection_reason(obj.getOrd_rejection_reason());
      this.setOrd_rejection_date(obj.getOrd_rejection_date());
      this.setOrd_completion_request_date(obj.getOrd_completion_request_date());
      this.setOrd_completion_estimate_date(obj.getOrd_completion_estimate_date());
      this.setOrd_planned_works_description(obj.getOrd_planned_works_description());
      this.setOrd_created(obj.getOrd_created());
      this.setOrd_removed_sewage_date(obj.getOrd_removed_sewage_date());
      this.setOrd_prefered_date_from(obj.getOrd_prefered_date_from());
      this.setOrd_prefered_date_to(obj.getOrd_prefered_date_to());
      this.setOrd_org_id(obj.getOrd_org_id());
      this.setOrd_per_id(obj.getOrd_per_id());
      this.setOrd_usr_id(obj.getOrd_usr_id());
      this.setOrd_wtf_id(obj.getOrd_wtf_id());
      this.setOrd_srv_id(obj.getOrd_srv_id());
      this.setOrd_cs_id(obj.getOrd_cs_id());
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
      this.ord_id_changed = false;
      this.ord_additional_description_changed = false;
      this.ord_state_changed = false;
      this.ord_email_changed = false;
      this.ord_type_changed = false;
      this.ord_compliance_norms_changed = false;
      this.ord_phone_number_changed = false;
      this.ord_created_in_ntis_portal_changed = false;
      this.ord_rejection_reason_changed = false;
      this.ord_rejection_date_changed = false;
      this.ord_completion_request_date_changed = false;
      this.ord_completion_estimate_date_changed = false;
      this.ord_planned_works_description_changed = false;
      this.ord_created_changed = false;
      this.ord_removed_sewage_date_changed = false;
      this.ord_prefered_date_from_changed = false;
      this.ord_prefered_date_to_changed = false;
      this.ord_org_id_changed = false;
      this.ord_per_id_changed = false;
      this.ord_usr_id_changed = false;
      this.ord_wtf_id_changed = false;
      this.ord_srv_id_changed = false;
      this.ord_cs_id_changed = false;
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
   public void setOrd_id(Double ord_id) {
      if (this.isForceUpdate() || (this.ord_id != null && !this.ord_id.equals(ord_id)) || (ord_id != null && !ord_id.equals(this.ord_id))){
         this.ord_id_changed = true; 
         this.record_changed = true;
         this.ord_id = ord_id;
      }
   }
   public Double getOrd_id() {
      return this.ord_id;
   }
   public void setOrd_additional_description(String ord_additional_description) {
      if (this.isForceUpdate() || (this.ord_additional_description != null && !this.ord_additional_description.equals(ord_additional_description)) || (ord_additional_description != null && !ord_additional_description.equals(this.ord_additional_description))){
         this.ord_additional_description_changed = true; 
         this.record_changed = true;
         this.ord_additional_description = ord_additional_description;
      }
   }
   public String getOrd_additional_description() {
      return this.ord_additional_description;
   }
   public void setOrd_state(String ord_state) {
      if (this.isForceUpdate() || (this.ord_state != null && !this.ord_state.equals(ord_state)) || (ord_state != null && !ord_state.equals(this.ord_state))){
         this.ord_state_changed = true; 
         this.record_changed = true;
         this.ord_state = ord_state;
      }
   }
   public String getOrd_state() {
      return this.ord_state;
   }
   public void setOrd_email(String ord_email) {
      if (this.isForceUpdate() || (this.ord_email != null && !this.ord_email.equals(ord_email)) || (ord_email != null && !ord_email.equals(this.ord_email))){
         this.ord_email_changed = true; 
         this.record_changed = true;
         this.ord_email = ord_email;
      }
   }
   public String getOrd_email() {
      return this.ord_email;
   }
   public void setOrd_type(String ord_type) {
      if (this.isForceUpdate() || (this.ord_type != null && !this.ord_type.equals(ord_type)) || (ord_type != null && !ord_type.equals(this.ord_type))){
         this.ord_type_changed = true; 
         this.record_changed = true;
         this.ord_type = ord_type;
      }
   }
   public String getOrd_type() {
      return this.ord_type;
   }
   public void setOrd_compliance_norms(String ord_compliance_norms) {
      if (this.isForceUpdate() || (this.ord_compliance_norms != null && !this.ord_compliance_norms.equals(ord_compliance_norms)) || (ord_compliance_norms != null && !ord_compliance_norms.equals(this.ord_compliance_norms))){
         this.ord_compliance_norms_changed = true; 
         this.record_changed = true;
         this.ord_compliance_norms = ord_compliance_norms;
      }
   }
   public String getOrd_compliance_norms() {
      return this.ord_compliance_norms;
   }
   public void setOrd_phone_number(String ord_phone_number) {
      if (this.isForceUpdate() || (this.ord_phone_number != null && !this.ord_phone_number.equals(ord_phone_number)) || (ord_phone_number != null && !ord_phone_number.equals(this.ord_phone_number))){
         this.ord_phone_number_changed = true; 
         this.record_changed = true;
         this.ord_phone_number = ord_phone_number;
      }
   }
   public String getOrd_phone_number() {
      return this.ord_phone_number;
   }
   public void setOrd_created_in_ntis_portal(String ord_created_in_ntis_portal) {
      if (this.isForceUpdate() || (this.ord_created_in_ntis_portal != null && !this.ord_created_in_ntis_portal.equals(ord_created_in_ntis_portal)) || (ord_created_in_ntis_portal != null && !ord_created_in_ntis_portal.equals(this.ord_created_in_ntis_portal))){
         this.ord_created_in_ntis_portal_changed = true; 
         this.record_changed = true;
         this.ord_created_in_ntis_portal = ord_created_in_ntis_portal;
      }
   }
   public String getOrd_created_in_ntis_portal() {
      return this.ord_created_in_ntis_portal;
   }
   public void setOrd_rejection_reason(String ord_rejection_reason) {
      if (this.isForceUpdate() || (this.ord_rejection_reason != null && !this.ord_rejection_reason.equals(ord_rejection_reason)) || (ord_rejection_reason != null && !ord_rejection_reason.equals(this.ord_rejection_reason))){
         this.ord_rejection_reason_changed = true; 
         this.record_changed = true;
         this.ord_rejection_reason = ord_rejection_reason;
      }
   }
   public String getOrd_rejection_reason() {
      return this.ord_rejection_reason;
   }
   public void setOrd_rejection_date(Date ord_rejection_date) {
      if (this.isForceUpdate() || (this.ord_rejection_date != null && !this.ord_rejection_date.equals(ord_rejection_date)) || (ord_rejection_date != null && !ord_rejection_date.equals(this.ord_rejection_date))){
         this.ord_rejection_date_changed = true; 
         this.record_changed = true;
         this.ord_rejection_date = ord_rejection_date;
      }
   }
   public Date getOrd_rejection_date() {
      return this.ord_rejection_date;
   }
   public void setOrd_completion_request_date(Date ord_completion_request_date) {
      if (this.isForceUpdate() || (this.ord_completion_request_date != null && !this.ord_completion_request_date.equals(ord_completion_request_date)) || (ord_completion_request_date != null && !ord_completion_request_date.equals(this.ord_completion_request_date))){
         this.ord_completion_request_date_changed = true; 
         this.record_changed = true;
         this.ord_completion_request_date = ord_completion_request_date;
      }
   }
   public Date getOrd_completion_request_date() {
      return this.ord_completion_request_date;
   }
   public void setOrd_completion_estimate_date(Date ord_completion_estimate_date) {
      if (this.isForceUpdate() || (this.ord_completion_estimate_date != null && !this.ord_completion_estimate_date.equals(ord_completion_estimate_date)) || (ord_completion_estimate_date != null && !ord_completion_estimate_date.equals(this.ord_completion_estimate_date))){
         this.ord_completion_estimate_date_changed = true; 
         this.record_changed = true;
         this.ord_completion_estimate_date = ord_completion_estimate_date;
      }
   }
   public Date getOrd_completion_estimate_date() {
      return this.ord_completion_estimate_date;
   }
   public void setOrd_planned_works_description(String ord_planned_works_description) {
      if (this.isForceUpdate() || (this.ord_planned_works_description != null && !this.ord_planned_works_description.equals(ord_planned_works_description)) || (ord_planned_works_description != null && !ord_planned_works_description.equals(this.ord_planned_works_description))){
         this.ord_planned_works_description_changed = true; 
         this.record_changed = true;
         this.ord_planned_works_description = ord_planned_works_description;
      }
   }
   public String getOrd_planned_works_description() {
      return this.ord_planned_works_description;
   }
   public void setOrd_created(Date ord_created) {
      if (this.isForceUpdate() || (this.ord_created != null && !this.ord_created.equals(ord_created)) || (ord_created != null && !ord_created.equals(this.ord_created))){
         this.ord_created_changed = true; 
         this.record_changed = true;
         this.ord_created = ord_created;
      }
   }
   public Date getOrd_created() {
      return this.ord_created;
   }
   public void setOrd_removed_sewage_date(Date ord_removed_sewage_date) {
      if (this.isForceUpdate() || (this.ord_removed_sewage_date != null && !this.ord_removed_sewage_date.equals(ord_removed_sewage_date)) || (ord_removed_sewage_date != null && !ord_removed_sewage_date.equals(this.ord_removed_sewage_date))){
         this.ord_removed_sewage_date_changed = true; 
         this.record_changed = true;
         this.ord_removed_sewage_date = ord_removed_sewage_date;
      }
   }
   public Date getOrd_removed_sewage_date() {
      return this.ord_removed_sewage_date;
   }
   public void setOrd_prefered_date_from(Date ord_prefered_date_from) {
      if (this.isForceUpdate() || (this.ord_prefered_date_from != null && !this.ord_prefered_date_from.equals(ord_prefered_date_from)) || (ord_prefered_date_from != null && !ord_prefered_date_from.equals(this.ord_prefered_date_from))){
         this.ord_prefered_date_from_changed = true; 
         this.record_changed = true;
         this.ord_prefered_date_from = ord_prefered_date_from;
      }
   }
   public Date getOrd_prefered_date_from() {
      return this.ord_prefered_date_from;
   }
   public void setOrd_prefered_date_to(Date ord_prefered_date_to) {
      if (this.isForceUpdate() || (this.ord_prefered_date_to != null && !this.ord_prefered_date_to.equals(ord_prefered_date_to)) || (ord_prefered_date_to != null && !ord_prefered_date_to.equals(this.ord_prefered_date_to))){
         this.ord_prefered_date_to_changed = true; 
         this.record_changed = true;
         this.ord_prefered_date_to = ord_prefered_date_to;
      }
   }
   public Date getOrd_prefered_date_to() {
      return this.ord_prefered_date_to;
   }
   public void setOrd_org_id(Double ord_org_id) {
      if (this.isForceUpdate() || (this.ord_org_id != null && !this.ord_org_id.equals(ord_org_id)) || (ord_org_id != null && !ord_org_id.equals(this.ord_org_id))){
         this.ord_org_id_changed = true; 
         this.record_changed = true;
         this.ord_org_id = ord_org_id;
      }
   }
   public Double getOrd_org_id() {
      return this.ord_org_id;
   }
   public void setOrd_per_id(Double ord_per_id) {
      if (this.isForceUpdate() || (this.ord_per_id != null && !this.ord_per_id.equals(ord_per_id)) || (ord_per_id != null && !ord_per_id.equals(this.ord_per_id))){
         this.ord_per_id_changed = true; 
         this.record_changed = true;
         this.ord_per_id = ord_per_id;
      }
   }
   public Double getOrd_per_id() {
      return this.ord_per_id;
   }
   public void setOrd_usr_id(Double ord_usr_id) {
      if (this.isForceUpdate() || (this.ord_usr_id != null && !this.ord_usr_id.equals(ord_usr_id)) || (ord_usr_id != null && !ord_usr_id.equals(this.ord_usr_id))){
         this.ord_usr_id_changed = true; 
         this.record_changed = true;
         this.ord_usr_id = ord_usr_id;
      }
   }
   public Double getOrd_usr_id() {
      return this.ord_usr_id;
   }
   public void setOrd_wtf_id(Double ord_wtf_id) {
      if (this.isForceUpdate() || (this.ord_wtf_id != null && !this.ord_wtf_id.equals(ord_wtf_id)) || (ord_wtf_id != null && !ord_wtf_id.equals(this.ord_wtf_id))){
         this.ord_wtf_id_changed = true; 
         this.record_changed = true;
         this.ord_wtf_id = ord_wtf_id;
      }
   }
   public Double getOrd_wtf_id() {
      return this.ord_wtf_id;
   }
   public void setOrd_srv_id(Double ord_srv_id) {
      if (this.isForceUpdate() || (this.ord_srv_id != null && !this.ord_srv_id.equals(ord_srv_id)) || (ord_srv_id != null && !ord_srv_id.equals(this.ord_srv_id))){
         this.ord_srv_id_changed = true; 
         this.record_changed = true;
         this.ord_srv_id = ord_srv_id;
      }
   }
   public Double getOrd_srv_id() {
      return this.ord_srv_id;
   }
   public void setOrd_cs_id(Double ord_cs_id) {
      if (this.isForceUpdate() || (this.ord_cs_id != null && !this.ord_cs_id.equals(ord_cs_id)) || (ord_cs_id != null && !ord_cs_id.equals(this.ord_cs_id))){
         this.ord_cs_id_changed = true; 
         this.record_changed = true;
         this.ord_cs_id = ord_cs_id;
      }
   }
   public Double getOrd_cs_id() {
      return this.ord_cs_id;
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
            this.ord_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.ord_id;
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
      if (ord_additional_description_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_additional_description!= null && ord_additional_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_ADDITIONAL_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ord_state_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_state== null || EMPTY_STRING.equals(ord_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ord_state!= null && ord_state.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (ord_email_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_email!= null && ord_email.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ord_type_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_type== null || EMPTY_STRING.equals(ord_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ord_type!= null && ord_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (ord_compliance_norms_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_compliance_norms!= null && ord_compliance_norms.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_COMPLIANCE_NORMS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ord_phone_number_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_phone_number!= null && ord_phone_number.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_PHONE_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (ord_created_in_ntis_portal_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_created_in_ntis_portal== null || EMPTY_STRING.equals(ord_created_in_ntis_portal)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_CREATED_IN_NTIS_PORTAL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ord_created_in_ntis_portal!= null && ord_created_in_ntis_portal.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_CREATED_IN_NTIS_PORTAL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (ord_rejection_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_rejection_reason!= null && ord_rejection_reason.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_REJECTION_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ord_planned_works_description_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_planned_works_description!= null && ord_planned_works_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_PLANNED_WORKS_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ord_created_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_created== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_CREATED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (ord_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_org_id!= null && (""+ord_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (ord_per_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_per_id!= null && (""+ord_per_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_PER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (ord_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_usr_id!= null && (""+ord_usr_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (ord_wtf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_wtf_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_WTF_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ord_wtf_id!= null && (""+ord_wtf_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_WTF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (ord_srv_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_srv_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_SRV_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ord_srv_id!= null && (""+ord_srv_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_SRV_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (ord_cs_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ord_cs_id!= null && (""+ord_cs_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "ORD_CS_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDERS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_ORDERS SET ";
      boolean changedExists = false;      if (ord_additional_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_ADDITIONAL_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_additional_description);
      }
      if (ord_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_state);
      }
      if (ord_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_email);
      }
      if (ord_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_type);
      }
      if (ord_compliance_norms_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_COMPLIANCE_NORMS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_compliance_norms);
      }
      if (ord_phone_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_PHONE_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_phone_number);
      }
      if (ord_created_in_ntis_portal_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_CREATED_IN_NTIS_PORTAL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_created_in_ntis_portal);
      }
      if (ord_rejection_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_REJECTION_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_rejection_reason);
      }
      if (ord_rejection_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_REJECTION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_rejection_date);
      }
      if (ord_completion_request_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_COMPLETION_REQUEST_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_completion_request_date);
      }
      if (ord_completion_estimate_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_COMPLETION_ESTIMATE_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_completion_estimate_date);
      }
      if (ord_planned_works_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_PLANNED_WORKS_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_planned_works_description);
      }
      if (ord_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_created);
      }
      if (ord_removed_sewage_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_REMOVED_SEWAGE_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_removed_sewage_date);
      }
      if (ord_prefered_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_PREFERED_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_prefered_date_from);
      }
      if (ord_prefered_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_PREFERED_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ord_prefered_date_to);
      }
      if (ord_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_org_id);
      }
      if (ord_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_per_id);
      }
      if (ord_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_usr_id);
      }
      if (ord_wtf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_WTF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_wtf_id);
      }
      if (ord_srv_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_SRV_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_srv_id);
      }
      if (ord_cs_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORD_CS_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ord_cs_id);
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
      answer = answer +" WHERE  ORD_ID = ? ";
      updateStatementPart.addStatementParam(ord_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisOrdersDAO\":{\"ord_id\": \""+getOrd_id()+"\", \"ord_additional_description\": \""+getOrd_additional_description()+"\", \"ord_state\": \""+getOrd_state()+"\", \"ord_email\": \""+getOrd_email()+"\", \"ord_type\": \""+getOrd_type()+"\", \"ord_compliance_norms\": \""+getOrd_compliance_norms()+"\", \"ord_phone_number\": \""+getOrd_phone_number()+"\", \"ord_created_in_ntis_portal\": \""+getOrd_created_in_ntis_portal()+"\", \"ord_rejection_reason\": \""+getOrd_rejection_reason()+"\", \"ord_rejection_date\": \""+getOrd_rejection_date()+"\", \"ord_completion_request_date\": \""+getOrd_completion_request_date()+"\", \"ord_completion_estimate_date\": \""+getOrd_completion_estimate_date()+"\", \"ord_planned_works_description\": \""+getOrd_planned_works_description()+"\", \"ord_created\": \""+getOrd_created()+"\", \"ord_removed_sewage_date\": \""+getOrd_removed_sewage_date()+"\", \"ord_prefered_date_from\": \""+getOrd_prefered_date_from()+"\", \"ord_prefered_date_to\": \""+getOrd_prefered_date_to()+"\", \"ord_org_id\": \""+getOrd_org_id()+"\", \"ord_per_id\": \""+getOrd_per_id()+"\", \"ord_usr_id\": \""+getOrd_usr_id()+"\", \"ord_wtf_id\": \""+getOrd_wtf_id()+"\", \"ord_srv_id\": \""+getOrd_srv_id()+"\", \"ord_cs_id\": \""+getOrd_cs_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}