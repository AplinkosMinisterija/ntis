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

public class NtisServiceRequestsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_SERVICE_REQUESTS.SR_ID";
   private Double sr_id;
   private String sr_reg_no;
   private String sr_type;
   private String sr_email;
   private String sr_email_verified;
   private String sr_phone;
   private String sr_resp_person_description;
   private String sr_homepage;
   private String sr_data_is_correct;
   private String sr_rules_accepted;
   private String sr_status;
   private Date sr_status_date;
   private Date sr_registration_date;
   private String sr_removal_reason;
   private Date sr_removal_date;
   private Double sr_org_id;
   private Double sr_usr_id;
   private Double sr_per_id;
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

   protected boolean sr_id_changed;
   protected boolean sr_reg_no_changed;
   protected boolean sr_type_changed;
   protected boolean sr_email_changed;
   protected boolean sr_email_verified_changed;
   protected boolean sr_phone_changed;
   protected boolean sr_resp_person_description_changed;
   protected boolean sr_homepage_changed;
   protected boolean sr_data_is_correct_changed;
   protected boolean sr_rules_accepted_changed;
   protected boolean sr_status_changed;
   protected boolean sr_status_date_changed;
   protected boolean sr_registration_date_changed;
   protected boolean sr_removal_reason_changed;
   protected boolean sr_removal_date_changed;
   protected boolean sr_org_id_changed;
   protected boolean sr_usr_id_changed;
   protected boolean sr_per_id_changed;
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
   public NtisServiceRequestsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisServiceRequestsDAOGen(Double sr_id, String sr_reg_no, String sr_type, String sr_email, String sr_email_verified, String sr_phone, String sr_resp_person_description, String sr_homepage, String sr_data_is_correct, String sr_rules_accepted, String sr_status, Date sr_status_date, Date sr_registration_date, String sr_removal_reason, Date sr_removal_date, Double sr_org_id, Double sr_usr_id, Double sr_per_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.sr_id = sr_id;
      this.sr_reg_no = sr_reg_no;
      this.sr_type = sr_type;
      this.sr_email = sr_email;
      this.sr_email_verified = sr_email_verified;
      this.sr_phone = sr_phone;
      this.sr_resp_person_description = sr_resp_person_description;
      this.sr_homepage = sr_homepage;
      this.sr_data_is_correct = sr_data_is_correct;
      this.sr_rules_accepted = sr_rules_accepted;
      this.sr_status = sr_status;
      this.sr_status_date = sr_status_date;
      this.sr_registration_date = sr_registration_date;
      this.sr_removal_reason = sr_removal_reason;
      this.sr_removal_date = sr_removal_date;
      this.sr_org_id = sr_org_id;
      this.sr_usr_id = sr_usr_id;
      this.sr_per_id = sr_per_id;
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
   public void copyValues(NtisServiceRequestsDAOGen obj) {
      this.setSr_id(obj.getSr_id());
      this.setSr_reg_no(obj.getSr_reg_no());
      this.setSr_type(obj.getSr_type());
      this.setSr_email(obj.getSr_email());
      this.setSr_email_verified(obj.getSr_email_verified());
      this.setSr_phone(obj.getSr_phone());
      this.setSr_resp_person_description(obj.getSr_resp_person_description());
      this.setSr_homepage(obj.getSr_homepage());
      this.setSr_data_is_correct(obj.getSr_data_is_correct());
      this.setSr_rules_accepted(obj.getSr_rules_accepted());
      this.setSr_status(obj.getSr_status());
      this.setSr_status_date(obj.getSr_status_date());
      this.setSr_registration_date(obj.getSr_registration_date());
      this.setSr_removal_reason(obj.getSr_removal_reason());
      this.setSr_removal_date(obj.getSr_removal_date());
      this.setSr_org_id(obj.getSr_org_id());
      this.setSr_usr_id(obj.getSr_usr_id());
      this.setSr_per_id(obj.getSr_per_id());
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
      this.sr_id_changed = false;
      this.sr_reg_no_changed = false;
      this.sr_type_changed = false;
      this.sr_email_changed = false;
      this.sr_email_verified_changed = false;
      this.sr_phone_changed = false;
      this.sr_resp_person_description_changed = false;
      this.sr_homepage_changed = false;
      this.sr_data_is_correct_changed = false;
      this.sr_rules_accepted_changed = false;
      this.sr_status_changed = false;
      this.sr_status_date_changed = false;
      this.sr_registration_date_changed = false;
      this.sr_removal_reason_changed = false;
      this.sr_removal_date_changed = false;
      this.sr_org_id_changed = false;
      this.sr_usr_id_changed = false;
      this.sr_per_id_changed = false;
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
   public void setSr_id(Double sr_id) {
      if (this.isForceUpdate() || (this.sr_id != null && !this.sr_id.equals(sr_id)) || (sr_id != null && !sr_id.equals(this.sr_id))){
         this.sr_id_changed = true; 
         this.record_changed = true;
         this.sr_id = sr_id;
      }
   }
   public Double getSr_id() {
      return this.sr_id;
   }
   public void setSr_reg_no(String sr_reg_no) {
      if (this.isForceUpdate() || (this.sr_reg_no != null && !this.sr_reg_no.equals(sr_reg_no)) || (sr_reg_no != null && !sr_reg_no.equals(this.sr_reg_no))){
         this.sr_reg_no_changed = true; 
         this.record_changed = true;
         this.sr_reg_no = sr_reg_no;
      }
   }
   public String getSr_reg_no() {
      return this.sr_reg_no;
   }
   public void setSr_type(String sr_type) {
      if (this.isForceUpdate() || (this.sr_type != null && !this.sr_type.equals(sr_type)) || (sr_type != null && !sr_type.equals(this.sr_type))){
         this.sr_type_changed = true; 
         this.record_changed = true;
         this.sr_type = sr_type;
      }
   }
   public String getSr_type() {
      return this.sr_type;
   }
   public void setSr_email(String sr_email) {
      if (this.isForceUpdate() || (this.sr_email != null && !this.sr_email.equals(sr_email)) || (sr_email != null && !sr_email.equals(this.sr_email))){
         this.sr_email_changed = true; 
         this.record_changed = true;
         this.sr_email = sr_email;
      }
   }
   public String getSr_email() {
      return this.sr_email;
   }
   public void setSr_email_verified(String sr_email_verified) {
      if (this.isForceUpdate() || (this.sr_email_verified != null && !this.sr_email_verified.equals(sr_email_verified)) || (sr_email_verified != null && !sr_email_verified.equals(this.sr_email_verified))){
         this.sr_email_verified_changed = true; 
         this.record_changed = true;
         this.sr_email_verified = sr_email_verified;
      }
   }
   public String getSr_email_verified() {
      return this.sr_email_verified;
   }
   public void setSr_phone(String sr_phone) {
      if (this.isForceUpdate() || (this.sr_phone != null && !this.sr_phone.equals(sr_phone)) || (sr_phone != null && !sr_phone.equals(this.sr_phone))){
         this.sr_phone_changed = true; 
         this.record_changed = true;
         this.sr_phone = sr_phone;
      }
   }
   public String getSr_phone() {
      return this.sr_phone;
   }
   public void setSr_resp_person_description(String sr_resp_person_description) {
      if (this.isForceUpdate() || (this.sr_resp_person_description != null && !this.sr_resp_person_description.equals(sr_resp_person_description)) || (sr_resp_person_description != null && !sr_resp_person_description.equals(this.sr_resp_person_description))){
         this.sr_resp_person_description_changed = true; 
         this.record_changed = true;
         this.sr_resp_person_description = sr_resp_person_description;
      }
   }
   public String getSr_resp_person_description() {
      return this.sr_resp_person_description;
   }
   public void setSr_homepage(String sr_homepage) {
      if (this.isForceUpdate() || (this.sr_homepage != null && !this.sr_homepage.equals(sr_homepage)) || (sr_homepage != null && !sr_homepage.equals(this.sr_homepage))){
         this.sr_homepage_changed = true; 
         this.record_changed = true;
         this.sr_homepage = sr_homepage;
      }
   }
   public String getSr_homepage() {
      return this.sr_homepage;
   }
   public void setSr_data_is_correct(String sr_data_is_correct) {
      if (this.isForceUpdate() || (this.sr_data_is_correct != null && !this.sr_data_is_correct.equals(sr_data_is_correct)) || (sr_data_is_correct != null && !sr_data_is_correct.equals(this.sr_data_is_correct))){
         this.sr_data_is_correct_changed = true; 
         this.record_changed = true;
         this.sr_data_is_correct = sr_data_is_correct;
      }
   }
   public String getSr_data_is_correct() {
      return this.sr_data_is_correct;
   }
   public void setSr_rules_accepted(String sr_rules_accepted) {
      if (this.isForceUpdate() || (this.sr_rules_accepted != null && !this.sr_rules_accepted.equals(sr_rules_accepted)) || (sr_rules_accepted != null && !sr_rules_accepted.equals(this.sr_rules_accepted))){
         this.sr_rules_accepted_changed = true; 
         this.record_changed = true;
         this.sr_rules_accepted = sr_rules_accepted;
      }
   }
   public String getSr_rules_accepted() {
      return this.sr_rules_accepted;
   }
   public void setSr_status(String sr_status) {
      if (this.isForceUpdate() || (this.sr_status != null && !this.sr_status.equals(sr_status)) || (sr_status != null && !sr_status.equals(this.sr_status))){
         this.sr_status_changed = true; 
         this.record_changed = true;
         this.sr_status = sr_status;
      }
   }
   public String getSr_status() {
      return this.sr_status;
   }
   public void setSr_status_date(Date sr_status_date) {
      if (this.isForceUpdate() || (this.sr_status_date != null && !this.sr_status_date.equals(sr_status_date)) || (sr_status_date != null && !sr_status_date.equals(this.sr_status_date))){
         this.sr_status_date_changed = true; 
         this.record_changed = true;
         this.sr_status_date = sr_status_date;
      }
   }
   public Date getSr_status_date() {
      return this.sr_status_date;
   }
   public void setSr_registration_date(Date sr_registration_date) {
      if (this.isForceUpdate() || (this.sr_registration_date != null && !this.sr_registration_date.equals(sr_registration_date)) || (sr_registration_date != null && !sr_registration_date.equals(this.sr_registration_date))){
         this.sr_registration_date_changed = true; 
         this.record_changed = true;
         this.sr_registration_date = sr_registration_date;
      }
   }
   public Date getSr_registration_date() {
      return this.sr_registration_date;
   }
   public void setSr_removal_reason(String sr_removal_reason) {
      if (this.isForceUpdate() || (this.sr_removal_reason != null && !this.sr_removal_reason.equals(sr_removal_reason)) || (sr_removal_reason != null && !sr_removal_reason.equals(this.sr_removal_reason))){
         this.sr_removal_reason_changed = true; 
         this.record_changed = true;
         this.sr_removal_reason = sr_removal_reason;
      }
   }
   public String getSr_removal_reason() {
      return this.sr_removal_reason;
   }
   public void setSr_removal_date(Date sr_removal_date) {
      if (this.isForceUpdate() || (this.sr_removal_date != null && !this.sr_removal_date.equals(sr_removal_date)) || (sr_removal_date != null && !sr_removal_date.equals(this.sr_removal_date))){
         this.sr_removal_date_changed = true; 
         this.record_changed = true;
         this.sr_removal_date = sr_removal_date;
      }
   }
   public Date getSr_removal_date() {
      return this.sr_removal_date;
   }
   public void setSr_org_id(Double sr_org_id) {
      if (this.isForceUpdate() || (this.sr_org_id != null && !this.sr_org_id.equals(sr_org_id)) || (sr_org_id != null && !sr_org_id.equals(this.sr_org_id))){
         this.sr_org_id_changed = true; 
         this.record_changed = true;
         this.sr_org_id = sr_org_id;
      }
   }
   public Double getSr_org_id() {
      return this.sr_org_id;
   }
   public void setSr_usr_id(Double sr_usr_id) {
      if (this.isForceUpdate() || (this.sr_usr_id != null && !this.sr_usr_id.equals(sr_usr_id)) || (sr_usr_id != null && !sr_usr_id.equals(this.sr_usr_id))){
         this.sr_usr_id_changed = true; 
         this.record_changed = true;
         this.sr_usr_id = sr_usr_id;
      }
   }
   public Double getSr_usr_id() {
      return this.sr_usr_id;
   }
   public void setSr_per_id(Double sr_per_id) {
      if (this.isForceUpdate() || (this.sr_per_id != null && !this.sr_per_id.equals(sr_per_id)) || (sr_per_id != null && !sr_per_id.equals(this.sr_per_id))){
         this.sr_per_id_changed = true; 
         this.record_changed = true;
         this.sr_per_id = sr_per_id;
      }
   }
   public Double getSr_per_id() {
      return this.sr_per_id;
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
            this.sr_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.sr_id;
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
      if (sr_reg_no_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_reg_no!= null && sr_reg_no.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_REG_NO", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (sr_type_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_type== null || EMPTY_STRING.equals(sr_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sr_type!= null && sr_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (sr_email_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_email== null || EMPTY_STRING.equals(sr_email)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_EMAIL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sr_email!= null && sr_email.length()>200) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (sr_email_verified_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_email_verified== null || EMPTY_STRING.equals(sr_email_verified)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_EMAIL_VERIFIED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sr_email_verified!= null && sr_email_verified.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_EMAIL_VERIFIED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (sr_phone_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_phone== null || EMPTY_STRING.equals(sr_phone)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_PHONE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sr_phone!= null && sr_phone.length()>200) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_PHONE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (sr_resp_person_description_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_resp_person_description!= null && sr_resp_person_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_RESP_PERSON_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (sr_homepage_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_homepage!= null && sr_homepage.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_HOMEPAGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sr_data_is_correct_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_data_is_correct!= null && sr_data_is_correct.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_DATA_IS_CORRECT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (sr_rules_accepted_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_rules_accepted!= null && sr_rules_accepted.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_RULES_ACCEPTED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (sr_status_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_status== null || EMPTY_STRING.equals(sr_status)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_STATUS", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sr_status!= null && sr_status.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_STATUS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (sr_status_date_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_status_date== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_STATUS_DATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (sr_removal_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_removal_reason!= null && sr_removal_reason.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_REMOVAL_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (sr_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_org_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_ORG_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (sr_org_id!= null && (""+sr_org_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (sr_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_usr_id!= null && (""+sr_usr_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (sr_per_id_changed || operation == Utils.OPERATION_INSERT) {
         if (sr_per_id!= null && (""+sr_per_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "SR_PER_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICE_REQUESTS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_SERVICE_REQUESTS SET ";
      boolean changedExists = false;      if (sr_reg_no_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_REG_NO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_reg_no);
      }
      if (sr_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_type);
      }
      if (sr_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_email);
      }
      if (sr_email_verified_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_EMAIL_VERIFIED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_email_verified);
      }
      if (sr_phone_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_PHONE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_phone);
      }
      if (sr_resp_person_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_RESP_PERSON_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_resp_person_description);
      }
      if (sr_homepage_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_HOMEPAGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_homepage);
      }
      if (sr_data_is_correct_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_DATA_IS_CORRECT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_data_is_correct);
      }
      if (sr_rules_accepted_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_RULES_ACCEPTED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_rules_accepted);
      }
      if (sr_status_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_STATUS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_status);
      }
      if (sr_status_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_STATUS_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sr_status_date);
      }
      if (sr_registration_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_REGISTRATION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sr_registration_date);
      }
      if (sr_removal_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_REMOVAL_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_removal_reason);
      }
      if (sr_removal_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_REMOVAL_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(sr_removal_date);
      }
      if (sr_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_org_id);
      }
      if (sr_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_usr_id);
      }
      if (sr_per_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SR_PER_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(sr_per_id);
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
      answer = answer +" WHERE  SR_ID = ? ";
      updateStatementPart.addStatementParam(sr_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisServiceRequestsDAO\":{\"sr_id\": \""+getSr_id()+"\", \"sr_reg_no\": \""+getSr_reg_no()+"\", \"sr_type\": \""+getSr_type()+"\", \"sr_email\": \""+getSr_email()+"\", \"sr_email_verified\": \""+getSr_email_verified()+"\", \"sr_phone\": \""+getSr_phone()+"\", \"sr_resp_person_description\": \""+getSr_resp_person_description()+"\", \"sr_homepage\": \""+getSr_homepage()+"\", \"sr_data_is_correct\": \""+getSr_data_is_correct()+"\", \"sr_rules_accepted\": \""+getSr_rules_accepted()+"\", \"sr_status\": \""+getSr_status()+"\", \"sr_status_date\": \""+getSr_status_date()+"\", \"sr_registration_date\": \""+getSr_registration_date()+"\", \"sr_removal_reason\": \""+getSr_removal_reason()+"\", \"sr_removal_date\": \""+getSr_removal_date()+"\", \"sr_org_id\": \""+getSr_org_id()+"\", \"sr_usr_id\": \""+getSr_usr_id()+"\", \"sr_per_id\": \""+getSr_per_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}