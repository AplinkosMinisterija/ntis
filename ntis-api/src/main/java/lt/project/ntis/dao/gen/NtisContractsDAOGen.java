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

public class NtisContractsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_CONTRACTS.COT_ID";
   private Double cot_id;
   private String cot_code;
   private String cot_created_in_ntis_portal;
   private String cot_state;
   private Date cot_from_date;
   private Date cot_to_date;
   private Date cot_created;
   private Date cot_project_created;
   private String cot_rejection_reason;
   private Date cot_rejection_date;
   private String cot_client_phone_no;
   private String cot_client_email;
   private Double cot_fil_id;
   private Double cot_wtf_id;
   private Double cot_org_id;
   private Double cot_per_id;
   private Double cot_sign_1_fil_id;
   private Double cot_sign_2_fil_id;
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

   protected boolean cot_id_changed;
   protected boolean cot_code_changed;
   protected boolean cot_created_in_ntis_portal_changed;
   protected boolean cot_state_changed;
   protected boolean cot_from_date_changed;
   protected boolean cot_to_date_changed;
   protected boolean cot_created_changed;
   protected boolean cot_project_created_changed;
   protected boolean cot_rejection_reason_changed;
   protected boolean cot_rejection_date_changed;
   protected boolean cot_client_phone_no_changed;
   protected boolean cot_client_email_changed;
   protected boolean cot_fil_id_changed;
   protected boolean cot_wtf_id_changed;
   protected boolean cot_org_id_changed;
   protected boolean cot_per_id_changed;
   protected boolean cot_sign_1_fil_id_changed;
   protected boolean cot_sign_2_fil_id_changed;
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
   public NtisContractsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisContractsDAOGen(Double cot_id, String cot_code, String cot_created_in_ntis_portal, String cot_state, Date cot_from_date, Date cot_to_date, Date cot_created, Date cot_project_created, String cot_rejection_reason, Date cot_rejection_date, String cot_client_phone_no, String cot_client_email, Double cot_fil_id, Double cot_wtf_id, Double cot_org_id, Double cot_per_id, Double cot_sign_1_fil_id, Double cot_sign_2_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.cot_id = cot_id;
      this.cot_code = cot_code;
      this.cot_created_in_ntis_portal = cot_created_in_ntis_portal;
      this.cot_state = cot_state;
      this.cot_from_date = cot_from_date;
      this.cot_to_date = cot_to_date;
      this.cot_created = cot_created;
      this.cot_project_created = cot_project_created;
      this.cot_rejection_reason = cot_rejection_reason;
      this.cot_rejection_date = cot_rejection_date;
      this.cot_client_phone_no = cot_client_phone_no;
      this.cot_client_email = cot_client_email;
      this.cot_fil_id = cot_fil_id;
      this.cot_wtf_id = cot_wtf_id;
      this.cot_org_id = cot_org_id;
      this.cot_per_id = cot_per_id;
      this.cot_sign_1_fil_id = cot_sign_1_fil_id;
      this.cot_sign_2_fil_id = cot_sign_2_fil_id;
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
   public void copyValues(NtisContractsDAOGen obj) {
      this.setCot_id(obj.getCot_id());
      this.setCot_code(obj.getCot_code());
      this.setCot_created_in_ntis_portal(obj.getCot_created_in_ntis_portal());
      this.setCot_state(obj.getCot_state());
      this.setCot_from_date(obj.getCot_from_date());
      this.setCot_to_date(obj.getCot_to_date());
      this.setCot_created(obj.getCot_created());
      this.setCot_project_created(obj.getCot_project_created());
      this.setCot_rejection_reason(obj.getCot_rejection_reason());
      this.setCot_rejection_date(obj.getCot_rejection_date());
      this.setCot_client_phone_no(obj.getCot_client_phone_no());
      this.setCot_client_email(obj.getCot_client_email());
      this.setCot_fil_id(obj.getCot_fil_id());
      this.setCot_wtf_id(obj.getCot_wtf_id());
      this.setCot_org_id(obj.getCot_org_id());
      this.setCot_per_id(obj.getCot_per_id());
      this.setCot_sign_1_fil_id(obj.getCot_sign_1_fil_id());
      this.setCot_sign_2_fil_id(obj.getCot_sign_2_fil_id());
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
      this.cot_id_changed = false;
      this.cot_code_changed = false;
      this.cot_created_in_ntis_portal_changed = false;
      this.cot_state_changed = false;
      this.cot_from_date_changed = false;
      this.cot_to_date_changed = false;
      this.cot_created_changed = false;
      this.cot_project_created_changed = false;
      this.cot_rejection_reason_changed = false;
      this.cot_rejection_date_changed = false;
      this.cot_client_phone_no_changed = false;
      this.cot_client_email_changed = false;
      this.cot_fil_id_changed = false;
      this.cot_wtf_id_changed = false;
      this.cot_org_id_changed = false;
      this.cot_per_id_changed = false;
      this.cot_sign_1_fil_id_changed = false;
      this.cot_sign_2_fil_id_changed = false;
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
   public void setCot_id(Double cot_id) {
      if (this.isForceUpdate() || (this.cot_id != null && !this.cot_id.equals(cot_id)) || (cot_id != null && !cot_id.equals(this.cot_id))){
         this.cot_id_changed = true; 
         this.record_changed = true;
         this.cot_id = cot_id;
      }
   }
   public Double getCot_id() {
      return this.cot_id;
   }
   public void setCot_code(String cot_code) {
      if (this.isForceUpdate() || (this.cot_code != null && !this.cot_code.equals(cot_code)) || (cot_code != null && !cot_code.equals(this.cot_code))){
         this.cot_code_changed = true; 
         this.record_changed = true;
         this.cot_code = cot_code;
      }
   }
   public String getCot_code() {
      return this.cot_code;
   }
   public void setCot_created_in_ntis_portal(String cot_created_in_ntis_portal) {
      if (this.isForceUpdate() || (this.cot_created_in_ntis_portal != null && !this.cot_created_in_ntis_portal.equals(cot_created_in_ntis_portal)) || (cot_created_in_ntis_portal != null && !cot_created_in_ntis_portal.equals(this.cot_created_in_ntis_portal))){
         this.cot_created_in_ntis_portal_changed = true; 
         this.record_changed = true;
         this.cot_created_in_ntis_portal = cot_created_in_ntis_portal;
      }
   }
   public String getCot_created_in_ntis_portal() {
      return this.cot_created_in_ntis_portal;
   }
   public void setCot_state(String cot_state) {
      if (this.isForceUpdate() || (this.cot_state != null && !this.cot_state.equals(cot_state)) || (cot_state != null && !cot_state.equals(this.cot_state))){
         this.cot_state_changed = true; 
         this.record_changed = true;
         this.cot_state = cot_state;
      }
   }
   public String getCot_state() {
      return this.cot_state;
   }
   public void setCot_from_date(Date cot_from_date) {
      if (this.isForceUpdate() || (this.cot_from_date != null && !this.cot_from_date.equals(cot_from_date)) || (cot_from_date != null && !cot_from_date.equals(this.cot_from_date))){
         this.cot_from_date_changed = true; 
         this.record_changed = true;
         this.cot_from_date = cot_from_date;
      }
   }
   public Date getCot_from_date() {
      return this.cot_from_date;
   }
   public void setCot_to_date(Date cot_to_date) {
      if (this.isForceUpdate() || (this.cot_to_date != null && !this.cot_to_date.equals(cot_to_date)) || (cot_to_date != null && !cot_to_date.equals(this.cot_to_date))){
         this.cot_to_date_changed = true; 
         this.record_changed = true;
         this.cot_to_date = cot_to_date;
      }
   }
   public Date getCot_to_date() {
      return this.cot_to_date;
   }
   public void setCot_created(Date cot_created) {
      if (this.isForceUpdate() || (this.cot_created != null && !this.cot_created.equals(cot_created)) || (cot_created != null && !cot_created.equals(this.cot_created))){
         this.cot_created_changed = true; 
         this.record_changed = true;
         this.cot_created = cot_created;
      }
   }
   public Date getCot_created() {
      return this.cot_created;
   }
   public void setCot_project_created(Date cot_project_created) {
      if (this.isForceUpdate() || (this.cot_project_created != null && !this.cot_project_created.equals(cot_project_created)) || (cot_project_created != null && !cot_project_created.equals(this.cot_project_created))){
         this.cot_project_created_changed = true; 
         this.record_changed = true;
         this.cot_project_created = cot_project_created;
      }
   }
   public Date getCot_project_created() {
      return this.cot_project_created;
   }
   public void setCot_rejection_reason(String cot_rejection_reason) {
      if (this.isForceUpdate() || (this.cot_rejection_reason != null && !this.cot_rejection_reason.equals(cot_rejection_reason)) || (cot_rejection_reason != null && !cot_rejection_reason.equals(this.cot_rejection_reason))){
         this.cot_rejection_reason_changed = true; 
         this.record_changed = true;
         this.cot_rejection_reason = cot_rejection_reason;
      }
   }
   public String getCot_rejection_reason() {
      return this.cot_rejection_reason;
   }
   public void setCot_rejection_date(Date cot_rejection_date) {
      if (this.isForceUpdate() || (this.cot_rejection_date != null && !this.cot_rejection_date.equals(cot_rejection_date)) || (cot_rejection_date != null && !cot_rejection_date.equals(this.cot_rejection_date))){
         this.cot_rejection_date_changed = true; 
         this.record_changed = true;
         this.cot_rejection_date = cot_rejection_date;
      }
   }
   public Date getCot_rejection_date() {
      return this.cot_rejection_date;
   }
   public void setCot_client_phone_no(String cot_client_phone_no) {
      if (this.isForceUpdate() || (this.cot_client_phone_no != null && !this.cot_client_phone_no.equals(cot_client_phone_no)) || (cot_client_phone_no != null && !cot_client_phone_no.equals(this.cot_client_phone_no))){
         this.cot_client_phone_no_changed = true; 
         this.record_changed = true;
         this.cot_client_phone_no = cot_client_phone_no;
      }
   }
   public String getCot_client_phone_no() {
      return this.cot_client_phone_no;
   }
   public void setCot_client_email(String cot_client_email) {
      if (this.isForceUpdate() || (this.cot_client_email != null && !this.cot_client_email.equals(cot_client_email)) || (cot_client_email != null && !cot_client_email.equals(this.cot_client_email))){
         this.cot_client_email_changed = true; 
         this.record_changed = true;
         this.cot_client_email = cot_client_email;
      }
   }
   public String getCot_client_email() {
      return this.cot_client_email;
   }
   public void setCot_fil_id(Double cot_fil_id) {
      if (this.isForceUpdate() || (this.cot_fil_id != null && !this.cot_fil_id.equals(cot_fil_id)) || (cot_fil_id != null && !cot_fil_id.equals(this.cot_fil_id))){
         this.cot_fil_id_changed = true; 
         this.record_changed = true;
         this.cot_fil_id = cot_fil_id;
      }
   }
   public Double getCot_fil_id() {
      return this.cot_fil_id;
   }
   public void setCot_wtf_id(Double cot_wtf_id) {
      if (this.isForceUpdate() || (this.cot_wtf_id != null && !this.cot_wtf_id.equals(cot_wtf_id)) || (cot_wtf_id != null && !cot_wtf_id.equals(this.cot_wtf_id))){
         this.cot_wtf_id_changed = true; 
         this.record_changed = true;
         this.cot_wtf_id = cot_wtf_id;
      }
   }
   public Double getCot_wtf_id() {
      return this.cot_wtf_id;
   }
   public void setCot_org_id(Double cot_org_id) {
      if (this.isForceUpdate() || (this.cot_org_id != null && !this.cot_org_id.equals(cot_org_id)) || (cot_org_id != null && !cot_org_id.equals(this.cot_org_id))){
         this.cot_org_id_changed = true; 
         this.record_changed = true;
         this.cot_org_id = cot_org_id;
      }
   }
   public Double getCot_org_id() {
      return this.cot_org_id;
   }
   public void setCot_per_id(Double cot_per_id) {
      if (this.isForceUpdate() || (this.cot_per_id != null && !this.cot_per_id.equals(cot_per_id)) || (cot_per_id != null && !cot_per_id.equals(this.cot_per_id))){
         this.cot_per_id_changed = true; 
         this.record_changed = true;
         this.cot_per_id = cot_per_id;
      }
   }
   public Double getCot_per_id() {
      return this.cot_per_id;
   }
   public void setCot_sign_1_fil_id(Double cot_sign_1_fil_id) {
      if (this.isForceUpdate() || (this.cot_sign_1_fil_id != null && !this.cot_sign_1_fil_id.equals(cot_sign_1_fil_id)) || (cot_sign_1_fil_id != null && !cot_sign_1_fil_id.equals(this.cot_sign_1_fil_id))){
         this.cot_sign_1_fil_id_changed = true; 
         this.record_changed = true;
         this.cot_sign_1_fil_id = cot_sign_1_fil_id;
      }
   }
   public Double getCot_sign_1_fil_id() {
      return this.cot_sign_1_fil_id;
   }
   public void setCot_sign_2_fil_id(Double cot_sign_2_fil_id) {
      if (this.isForceUpdate() || (this.cot_sign_2_fil_id != null && !this.cot_sign_2_fil_id.equals(cot_sign_2_fil_id)) || (cot_sign_2_fil_id != null && !cot_sign_2_fil_id.equals(this.cot_sign_2_fil_id))){
         this.cot_sign_2_fil_id_changed = true; 
         this.record_changed = true;
         this.cot_sign_2_fil_id = cot_sign_2_fil_id;
      }
   }
   public Double getCot_sign_2_fil_id() {
      return this.cot_sign_2_fil_id;
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
            this.cot_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.cot_id;
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
      if (cot_code_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_code!= null && cot_code.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (cot_created_in_ntis_portal_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_created_in_ntis_portal== null || EMPTY_STRING.equals(cot_created_in_ntis_portal)) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_CREATED_IN_NTIS_PORTAL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (cot_created_in_ntis_portal!= null && cot_created_in_ntis_portal.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_CREATED_IN_NTIS_PORTAL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (cot_state_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_state== null || EMPTY_STRING.equals(cot_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (cot_state!= null && cot_state.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (cot_from_date_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_from_date== null) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_FROM_DATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (cot_to_date_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_to_date== null) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_TO_DATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (cot_created_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_created== null) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_CREATED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (cot_rejection_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_rejection_reason!= null && cot_rejection_reason.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_REJECTION_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (cot_client_phone_no_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_client_phone_no!= null && cot_client_phone_no.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_CLIENT_PHONE_NO", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cot_client_email_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_client_email!= null && cot_client_email.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_CLIENT_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cot_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_fil_id!= null && (""+cot_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (cot_wtf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_wtf_id!= null && (""+cot_wtf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_WTF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (cot_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_org_id!= null && (""+cot_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (cot_per_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_per_id!= null && (""+cot_per_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_PER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (cot_sign_1_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_sign_1_fil_id!= null && (""+cot_sign_1_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_SIGN_1_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (cot_sign_2_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cot_sign_2_fil_id!= null && (""+cot_sign_2_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "COT_SIGN_2_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CONTRACTS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_CONTRACTS SET ";
      boolean changedExists = false;      if (cot_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_code);
      }
      if (cot_created_in_ntis_portal_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_CREATED_IN_NTIS_PORTAL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_created_in_ntis_portal);
      }
      if (cot_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_state);
      }
      if (cot_from_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_FROM_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(cot_from_date);
      }
      if (cot_to_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_TO_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(cot_to_date);
      }
      if (cot_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(cot_created);
      }
      if (cot_project_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_PROJECT_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(cot_project_created);
      }
      if (cot_rejection_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_REJECTION_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_rejection_reason);
      }
      if (cot_rejection_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_REJECTION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(cot_rejection_date);
      }
      if (cot_client_phone_no_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_CLIENT_PHONE_NO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_client_phone_no);
      }
      if (cot_client_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_CLIENT_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_client_email);
      }
      if (cot_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_fil_id);
      }
      if (cot_wtf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_WTF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_wtf_id);
      }
      if (cot_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_org_id);
      }
      if (cot_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_per_id);
      }
      if (cot_sign_1_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_SIGN_1_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_sign_1_fil_id);
      }
      if (cot_sign_2_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"COT_SIGN_2_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cot_sign_2_fil_id);
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
      answer = answer +" WHERE  COT_ID = ? ";
      updateStatementPart.addStatementParam(cot_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisContractsDAO\":{\"cot_id\": \""+getCot_id()+"\", \"cot_code\": \""+getCot_code()+"\", \"cot_created_in_ntis_portal\": \""+getCot_created_in_ntis_portal()+"\", \"cot_state\": \""+getCot_state()+"\", \"cot_from_date\": \""+getCot_from_date()+"\", \"cot_to_date\": \""+getCot_to_date()+"\", \"cot_created\": \""+getCot_created()+"\", \"cot_project_created\": \""+getCot_project_created()+"\", \"cot_rejection_reason\": \""+getCot_rejection_reason()+"\", \"cot_rejection_date\": \""+getCot_rejection_date()+"\", \"cot_client_phone_no\": \""+getCot_client_phone_no()+"\", \"cot_client_email\": \""+getCot_client_email()+"\", \"cot_fil_id\": \""+getCot_fil_id()+"\", \"cot_wtf_id\": \""+getCot_wtf_id()+"\", \"cot_org_id\": \""+getCot_org_id()+"\", \"cot_per_id\": \""+getCot_per_id()+"\", \"cot_sign_1_fil_id\": \""+getCot_sign_1_fil_id()+"\", \"cot_sign_2_fil_id\": \""+getCot_sign_2_fil_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}