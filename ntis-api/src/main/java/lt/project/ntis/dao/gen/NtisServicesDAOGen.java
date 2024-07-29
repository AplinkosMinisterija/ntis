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

public class NtisServicesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_SERVICES.SRV_ID";
   private Double srv_id;
   private String srv_type;
   private String srv_contract_available;
   private String srv_available_in_ntis_portal;
   private String srv_lithuanian_level;
   private String srv_email;
   private String srv_phone_no;
   private Double srv_price_from;
   private Double srv_price_to;
   private Double srv_completion_in_days_from;
   private Double srv_completion_in_days_to;
   private String srv_description;
   private Date srv_date_from;
   private Date srv_date_to;
   private Double srv_org_id;
   private Double srv_fil_id;
   private Double srv_lab_instr_fil_id;
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

   protected boolean srv_id_changed;
   protected boolean srv_type_changed;
   protected boolean srv_contract_available_changed;
   protected boolean srv_available_in_ntis_portal_changed;
   protected boolean srv_lithuanian_level_changed;
   protected boolean srv_email_changed;
   protected boolean srv_phone_no_changed;
   protected boolean srv_price_from_changed;
   protected boolean srv_price_to_changed;
   protected boolean srv_completion_in_days_from_changed;
   protected boolean srv_completion_in_days_to_changed;
   protected boolean srv_description_changed;
   protected boolean srv_date_from_changed;
   protected boolean srv_date_to_changed;
   protected boolean srv_org_id_changed;
   protected boolean srv_fil_id_changed;
   protected boolean srv_lab_instr_fil_id_changed;
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
   public NtisServicesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisServicesDAOGen(Double srv_id, String srv_type, String srv_contract_available, String srv_available_in_ntis_portal, String srv_lithuanian_level, String srv_email, String srv_phone_no, Double srv_price_from, Double srv_price_to, Double srv_completion_in_days_from, Double srv_completion_in_days_to, String srv_description, Date srv_date_from, Date srv_date_to, Double srv_org_id, Double srv_fil_id, Double srv_lab_instr_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.srv_id = srv_id;
      this.srv_type = srv_type;
      this.srv_contract_available = srv_contract_available;
      this.srv_available_in_ntis_portal = srv_available_in_ntis_portal;
      this.srv_lithuanian_level = srv_lithuanian_level;
      this.srv_email = srv_email;
      this.srv_phone_no = srv_phone_no;
      this.srv_price_from = srv_price_from;
      this.srv_price_to = srv_price_to;
      this.srv_completion_in_days_from = srv_completion_in_days_from;
      this.srv_completion_in_days_to = srv_completion_in_days_to;
      this.srv_description = srv_description;
      this.srv_date_from = srv_date_from;
      this.srv_date_to = srv_date_to;
      this.srv_org_id = srv_org_id;
      this.srv_fil_id = srv_fil_id;
      this.srv_lab_instr_fil_id = srv_lab_instr_fil_id;
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
   public void copyValues(NtisServicesDAOGen obj) {
      this.setSrv_id(obj.getSrv_id());
      this.setSrv_type(obj.getSrv_type());
      this.setSrv_contract_available(obj.getSrv_contract_available());
      this.setSrv_available_in_ntis_portal(obj.getSrv_available_in_ntis_portal());
      this.setSrv_lithuanian_level(obj.getSrv_lithuanian_level());
      this.setSrv_email(obj.getSrv_email());
      this.setSrv_phone_no(obj.getSrv_phone_no());
      this.setSrv_price_from(obj.getSrv_price_from());
      this.setSrv_price_to(obj.getSrv_price_to());
      this.setSrv_completion_in_days_from(obj.getSrv_completion_in_days_from());
      this.setSrv_completion_in_days_to(obj.getSrv_completion_in_days_to());
      this.setSrv_description(obj.getSrv_description());
      this.setSrv_date_from(obj.getSrv_date_from());
      this.setSrv_date_to(obj.getSrv_date_to());
      this.setSrv_org_id(obj.getSrv_org_id());
      this.setSrv_fil_id(obj.getSrv_fil_id());
      this.setSrv_lab_instr_fil_id(obj.getSrv_lab_instr_fil_id());
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
      this.srv_id_changed = false;
      this.srv_type_changed = false;
      this.srv_contract_available_changed = false;
      this.srv_available_in_ntis_portal_changed = false;
      this.srv_lithuanian_level_changed = false;
      this.srv_email_changed = false;
      this.srv_phone_no_changed = false;
      this.srv_price_from_changed = false;
      this.srv_price_to_changed = false;
      this.srv_completion_in_days_from_changed = false;
      this.srv_completion_in_days_to_changed = false;
      this.srv_description_changed = false;
      this.srv_date_from_changed = false;
      this.srv_date_to_changed = false;
      this.srv_org_id_changed = false;
      this.srv_fil_id_changed = false;
      this.srv_lab_instr_fil_id_changed = false;
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
   public void setSrv_id(Double srv_id) {
      if (this.isForceUpdate() || (this.srv_id != null && !this.srv_id.equals(srv_id)) || (srv_id != null && !srv_id.equals(this.srv_id))){
         this.srv_id_changed = true; 
         this.record_changed = true;
         this.srv_id = srv_id;
      }
   }
   public Double getSrv_id() {
      return this.srv_id;
   }
   public void setSrv_type(String srv_type) {
      if (this.isForceUpdate() || (this.srv_type != null && !this.srv_type.equals(srv_type)) || (srv_type != null && !srv_type.equals(this.srv_type))){
         this.srv_type_changed = true; 
         this.record_changed = true;
         this.srv_type = srv_type;
      }
   }
   public String getSrv_type() {
      return this.srv_type;
   }
   public void setSrv_contract_available(String srv_contract_available) {
      if (this.isForceUpdate() || (this.srv_contract_available != null && !this.srv_contract_available.equals(srv_contract_available)) || (srv_contract_available != null && !srv_contract_available.equals(this.srv_contract_available))){
         this.srv_contract_available_changed = true; 
         this.record_changed = true;
         this.srv_contract_available = srv_contract_available;
      }
   }
   public String getSrv_contract_available() {
      return this.srv_contract_available;
   }
   public void setSrv_available_in_ntis_portal(String srv_available_in_ntis_portal) {
      if (this.isForceUpdate() || (this.srv_available_in_ntis_portal != null && !this.srv_available_in_ntis_portal.equals(srv_available_in_ntis_portal)) || (srv_available_in_ntis_portal != null && !srv_available_in_ntis_portal.equals(this.srv_available_in_ntis_portal))){
         this.srv_available_in_ntis_portal_changed = true; 
         this.record_changed = true;
         this.srv_available_in_ntis_portal = srv_available_in_ntis_portal;
      }
   }
   public String getSrv_available_in_ntis_portal() {
      return this.srv_available_in_ntis_portal;
   }
   public void setSrv_lithuanian_level(String srv_lithuanian_level) {
      if (this.isForceUpdate() || (this.srv_lithuanian_level != null && !this.srv_lithuanian_level.equals(srv_lithuanian_level)) || (srv_lithuanian_level != null && !srv_lithuanian_level.equals(this.srv_lithuanian_level))){
         this.srv_lithuanian_level_changed = true; 
         this.record_changed = true;
         this.srv_lithuanian_level = srv_lithuanian_level;
      }
   }
   public String getSrv_lithuanian_level() {
      return this.srv_lithuanian_level;
   }
   public void setSrv_email(String srv_email) {
      if (this.isForceUpdate() || (this.srv_email != null && !this.srv_email.equals(srv_email)) || (srv_email != null && !srv_email.equals(this.srv_email))){
         this.srv_email_changed = true; 
         this.record_changed = true;
         this.srv_email = srv_email;
      }
   }
   public String getSrv_email() {
      return this.srv_email;
   }
   public void setSrv_phone_no(String srv_phone_no) {
      if (this.isForceUpdate() || (this.srv_phone_no != null && !this.srv_phone_no.equals(srv_phone_no)) || (srv_phone_no != null && !srv_phone_no.equals(this.srv_phone_no))){
         this.srv_phone_no_changed = true; 
         this.record_changed = true;
         this.srv_phone_no = srv_phone_no;
      }
   }
   public String getSrv_phone_no() {
      return this.srv_phone_no;
   }
   public void setSrv_price_from(Double srv_price_from) {
      if (this.isForceUpdate() || (this.srv_price_from != null && !this.srv_price_from.equals(srv_price_from)) || (srv_price_from != null && !srv_price_from.equals(this.srv_price_from))){
         this.srv_price_from_changed = true; 
         this.record_changed = true;
         this.srv_price_from = srv_price_from;
      }
   }
   public Double getSrv_price_from() {
      return this.srv_price_from;
   }
   public void setSrv_price_to(Double srv_price_to) {
      if (this.isForceUpdate() || (this.srv_price_to != null && !this.srv_price_to.equals(srv_price_to)) || (srv_price_to != null && !srv_price_to.equals(this.srv_price_to))){
         this.srv_price_to_changed = true; 
         this.record_changed = true;
         this.srv_price_to = srv_price_to;
      }
   }
   public Double getSrv_price_to() {
      return this.srv_price_to;
   }
   public void setSrv_completion_in_days_from(Double srv_completion_in_days_from) {
      if (this.isForceUpdate() || (this.srv_completion_in_days_from != null && !this.srv_completion_in_days_from.equals(srv_completion_in_days_from)) || (srv_completion_in_days_from != null && !srv_completion_in_days_from.equals(this.srv_completion_in_days_from))){
         this.srv_completion_in_days_from_changed = true; 
         this.record_changed = true;
         this.srv_completion_in_days_from = srv_completion_in_days_from;
      }
   }
   public Double getSrv_completion_in_days_from() {
      return this.srv_completion_in_days_from;
   }
   public void setSrv_completion_in_days_to(Double srv_completion_in_days_to) {
      if (this.isForceUpdate() || (this.srv_completion_in_days_to != null && !this.srv_completion_in_days_to.equals(srv_completion_in_days_to)) || (srv_completion_in_days_to != null && !srv_completion_in_days_to.equals(this.srv_completion_in_days_to))){
         this.srv_completion_in_days_to_changed = true; 
         this.record_changed = true;
         this.srv_completion_in_days_to = srv_completion_in_days_to;
      }
   }
   public Double getSrv_completion_in_days_to() {
      return this.srv_completion_in_days_to;
   }
   public void setSrv_description(String srv_description) {
      if (this.isForceUpdate() || (this.srv_description != null && !this.srv_description.equals(srv_description)) || (srv_description != null && !srv_description.equals(this.srv_description))){
         this.srv_description_changed = true; 
         this.record_changed = true;
         this.srv_description = srv_description;
      }
   }
   public String getSrv_description() {
      return this.srv_description;
   }
   public void setSrv_date_from(Date srv_date_from) {
      if (this.isForceUpdate() || (this.srv_date_from != null && !this.srv_date_from.equals(srv_date_from)) || (srv_date_from != null && !srv_date_from.equals(this.srv_date_from))){
         this.srv_date_from_changed = true; 
         this.record_changed = true;
         this.srv_date_from = srv_date_from;
      }
   }
   public Date getSrv_date_from() {
      return this.srv_date_from;
   }
   public void setSrv_date_to(Date srv_date_to) {
      if (this.isForceUpdate() || (this.srv_date_to != null && !this.srv_date_to.equals(srv_date_to)) || (srv_date_to != null && !srv_date_to.equals(this.srv_date_to))){
         this.srv_date_to_changed = true; 
         this.record_changed = true;
         this.srv_date_to = srv_date_to;
      }
   }
   public Date getSrv_date_to() {
      return this.srv_date_to;
   }
   public void setSrv_org_id(Double srv_org_id) {
      if (this.isForceUpdate() || (this.srv_org_id != null && !this.srv_org_id.equals(srv_org_id)) || (srv_org_id != null && !srv_org_id.equals(this.srv_org_id))){
         this.srv_org_id_changed = true; 
         this.record_changed = true;
         this.srv_org_id = srv_org_id;
      }
   }
   public Double getSrv_org_id() {
      return this.srv_org_id;
   }
   public void setSrv_fil_id(Double srv_fil_id) {
      if (this.isForceUpdate() || (this.srv_fil_id != null && !this.srv_fil_id.equals(srv_fil_id)) || (srv_fil_id != null && !srv_fil_id.equals(this.srv_fil_id))){
         this.srv_fil_id_changed = true; 
         this.record_changed = true;
         this.srv_fil_id = srv_fil_id;
      }
   }
   public Double getSrv_fil_id() {
      return this.srv_fil_id;
   }
   public void setSrv_lab_instr_fil_id(Double srv_lab_instr_fil_id) {
      if (this.isForceUpdate() || (this.srv_lab_instr_fil_id != null && !this.srv_lab_instr_fil_id.equals(srv_lab_instr_fil_id)) || (srv_lab_instr_fil_id != null && !srv_lab_instr_fil_id.equals(this.srv_lab_instr_fil_id))){
         this.srv_lab_instr_fil_id_changed = true; 
         this.record_changed = true;
         this.srv_lab_instr_fil_id = srv_lab_instr_fil_id;
      }
   }
   public Double getSrv_lab_instr_fil_id() {
      return this.srv_lab_instr_fil_id;
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
            this.srv_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.srv_id;
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
      if (srv_type_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_type== null || EMPTY_STRING.equals(srv_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_type!= null && srv_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (srv_contract_available_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_contract_available== null || EMPTY_STRING.equals(srv_contract_available)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_CONTRACT_AVAILABLE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_contract_available!= null && srv_contract_available.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_CONTRACT_AVAILABLE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (srv_available_in_ntis_portal_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_available_in_ntis_portal== null || EMPTY_STRING.equals(srv_available_in_ntis_portal)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_AVAILABLE_IN_NTIS_PORTAL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_available_in_ntis_portal!= null && srv_available_in_ntis_portal.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_AVAILABLE_IN_NTIS_PORTAL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (srv_lithuanian_level_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_lithuanian_level== null || EMPTY_STRING.equals(srv_lithuanian_level)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_LITHUANIAN_LEVEL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_lithuanian_level!= null && srv_lithuanian_level.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_LITHUANIAN_LEVEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (srv_email_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_email== null || EMPTY_STRING.equals(srv_email)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_EMAIL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_email!= null && srv_email.length()>200) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (srv_phone_no_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_phone_no== null || EMPTY_STRING.equals(srv_phone_no)) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_PHONE_NO", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_phone_no!= null && srv_phone_no.length()>200) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_PHONE_NO", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (srv_price_from_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_price_from== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_PRICE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (srv_completion_in_days_from_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_completion_in_days_from== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_COMPLETION_IN_DAYS_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (srv_completion_in_days_from!= null && (""+srv_completion_in_days_from.intValue()).length()>12) {
               throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_COMPLETION_IN_DAYS_FROM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
            }
         }
      }
      if (srv_completion_in_days_to_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_completion_in_days_to!= null && (""+srv_completion_in_days_to.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_COMPLETION_IN_DAYS_TO", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (srv_description_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_description!= null && srv_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (srv_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_org_id!= null && (""+srv_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (srv_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_fil_id!= null && (""+srv_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (srv_lab_instr_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (srv_lab_instr_fil_id!= null && (""+srv_lab_instr_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "SRV_LAB_INSTR_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SERVICES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_SERVICES SET ";
      boolean changedExists = false;      if (srv_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_type);
      }
      if (srv_contract_available_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_CONTRACT_AVAILABLE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_contract_available);
      }
      if (srv_available_in_ntis_portal_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_AVAILABLE_IN_NTIS_PORTAL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_available_in_ntis_portal);
      }
      if (srv_lithuanian_level_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_LITHUANIAN_LEVEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_lithuanian_level);
      }
      if (srv_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_email);
      }
      if (srv_phone_no_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_PHONE_NO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_phone_no);
      }
      if (srv_price_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_PRICE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_price_from);
      }
      if (srv_price_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_PRICE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_price_to);
      }
      if (srv_completion_in_days_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_COMPLETION_IN_DAYS_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_completion_in_days_from);
      }
      if (srv_completion_in_days_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_COMPLETION_IN_DAYS_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_completion_in_days_to);
      }
      if (srv_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_description);
      }
      if (srv_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(srv_date_from);
      }
      if (srv_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(srv_date_to);
      }
      if (srv_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_org_id);
      }
      if (srv_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_fil_id);
      }
      if (srv_lab_instr_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"SRV_LAB_INSTR_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(srv_lab_instr_fil_id);
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
      answer = answer +" WHERE  SRV_ID = ? ";
      updateStatementPart.addStatementParam(srv_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisServicesDAO\":{\"srv_id\": \""+getSrv_id()+"\", \"srv_type\": \""+getSrv_type()+"\", \"srv_contract_available\": \""+getSrv_contract_available()+"\", \"srv_available_in_ntis_portal\": \""+getSrv_available_in_ntis_portal()+"\", \"srv_lithuanian_level\": \""+getSrv_lithuanian_level()+"\", \"srv_email\": \""+getSrv_email()+"\", \"srv_phone_no\": \""+getSrv_phone_no()+"\", \"srv_price_from\": \""+getSrv_price_from()+"\", \"srv_price_to\": \""+getSrv_price_to()+"\", \"srv_completion_in_days_from\": \""+getSrv_completion_in_days_from()+"\", \"srv_completion_in_days_to\": \""+getSrv_completion_in_days_to()+"\", \"srv_description\": \""+getSrv_description()+"\", \"srv_date_from\": \""+getSrv_date_from()+"\", \"srv_date_to\": \""+getSrv_date_to()+"\", \"srv_org_id\": \""+getSrv_org_id()+"\", \"srv_fil_id\": \""+getSrv_fil_id()+"\", \"srv_lab_instr_fil_id\": \""+getSrv_lab_instr_fil_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}