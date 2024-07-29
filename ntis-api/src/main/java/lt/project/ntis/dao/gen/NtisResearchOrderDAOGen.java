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

public class NtisResearchOrderDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_RESEARCH_ORDER.RO_ID";
   private Double ro_id;
   private String ro_additional_description;
   private String ro_date_from;
   private String ro_created_in_ntis_portal;
   private String ro_phone_number;
   private String ro_rejection_reason;
   private String ro_compliance_norms;
   private String ro_state;
   private String ro_created;
   private String ro_email;
   private String ro_date_to;
   private Double ro_wtf_id;
   private Double ro_org_id;
   private Double ro_fil_id;
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

   protected boolean ro_id_changed;
   protected boolean ro_additional_description_changed;
   protected boolean ro_date_from_changed;
   protected boolean ro_created_in_ntis_portal_changed;
   protected boolean ro_phone_number_changed;
   protected boolean ro_rejection_reason_changed;
   protected boolean ro_compliance_norms_changed;
   protected boolean ro_state_changed;
   protected boolean ro_created_changed;
   protected boolean ro_email_changed;
   protected boolean ro_date_to_changed;
   protected boolean ro_wtf_id_changed;
   protected boolean ro_org_id_changed;
   protected boolean ro_fil_id_changed;
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
   public NtisResearchOrderDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisResearchOrderDAOGen(Double ro_id, String ro_additional_description, String ro_date_from, String ro_created_in_ntis_portal, String ro_phone_number, String ro_rejection_reason, String ro_compliance_norms, String ro_state, String ro_created, String ro_email, String ro_date_to, Double ro_wtf_id, Double ro_org_id, Double ro_fil_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.ro_id = ro_id;
      this.ro_additional_description = ro_additional_description;
      this.ro_date_from = ro_date_from;
      this.ro_created_in_ntis_portal = ro_created_in_ntis_portal;
      this.ro_phone_number = ro_phone_number;
      this.ro_rejection_reason = ro_rejection_reason;
      this.ro_compliance_norms = ro_compliance_norms;
      this.ro_state = ro_state;
      this.ro_created = ro_created;
      this.ro_email = ro_email;
      this.ro_date_to = ro_date_to;
      this.ro_wtf_id = ro_wtf_id;
      this.ro_org_id = ro_org_id;
      this.ro_fil_id = ro_fil_id;
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
   public void copyValues(NtisResearchOrderDAOGen obj) {
      this.setRo_id(obj.getRo_id());
      this.setRo_additional_description(obj.getRo_additional_description());
      this.setRo_date_from(obj.getRo_date_from());
      this.setRo_created_in_ntis_portal(obj.getRo_created_in_ntis_portal());
      this.setRo_phone_number(obj.getRo_phone_number());
      this.setRo_rejection_reason(obj.getRo_rejection_reason());
      this.setRo_compliance_norms(obj.getRo_compliance_norms());
      this.setRo_state(obj.getRo_state());
      this.setRo_created(obj.getRo_created());
      this.setRo_email(obj.getRo_email());
      this.setRo_date_to(obj.getRo_date_to());
      this.setRo_wtf_id(obj.getRo_wtf_id());
      this.setRo_org_id(obj.getRo_org_id());
      this.setRo_fil_id(obj.getRo_fil_id());
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
      this.ro_id_changed = false;
      this.ro_additional_description_changed = false;
      this.ro_date_from_changed = false;
      this.ro_created_in_ntis_portal_changed = false;
      this.ro_phone_number_changed = false;
      this.ro_rejection_reason_changed = false;
      this.ro_compliance_norms_changed = false;
      this.ro_state_changed = false;
      this.ro_created_changed = false;
      this.ro_email_changed = false;
      this.ro_date_to_changed = false;
      this.ro_wtf_id_changed = false;
      this.ro_org_id_changed = false;
      this.ro_fil_id_changed = false;
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
   public void setRo_id(Double ro_id) {
      if (this.isForceUpdate() || (this.ro_id != null && !this.ro_id.equals(ro_id)) || (ro_id != null && !ro_id.equals(this.ro_id))){
         this.ro_id_changed = true; 
         this.record_changed = true;
         this.ro_id = ro_id;
      }
   }
   public Double getRo_id() {
      return this.ro_id;
   }
   public void setRo_additional_description(String ro_additional_description) {
      if (this.isForceUpdate() || (this.ro_additional_description != null && !this.ro_additional_description.equals(ro_additional_description)) || (ro_additional_description != null && !ro_additional_description.equals(this.ro_additional_description))){
         this.ro_additional_description_changed = true; 
         this.record_changed = true;
         this.ro_additional_description = ro_additional_description;
      }
   }
   public String getRo_additional_description() {
      return this.ro_additional_description;
   }
   public void setRo_date_from(String ro_date_from) {
      if (this.isForceUpdate() || (this.ro_date_from != null && !this.ro_date_from.equals(ro_date_from)) || (ro_date_from != null && !ro_date_from.equals(this.ro_date_from))){
         this.ro_date_from_changed = true; 
         this.record_changed = true;
         this.ro_date_from = ro_date_from;
      }
   }
   public String getRo_date_from() {
      return this.ro_date_from;
   }
   public void setRo_created_in_ntis_portal(String ro_created_in_ntis_portal) {
      if (this.isForceUpdate() || (this.ro_created_in_ntis_portal != null && !this.ro_created_in_ntis_portal.equals(ro_created_in_ntis_portal)) || (ro_created_in_ntis_portal != null && !ro_created_in_ntis_portal.equals(this.ro_created_in_ntis_portal))){
         this.ro_created_in_ntis_portal_changed = true; 
         this.record_changed = true;
         this.ro_created_in_ntis_portal = ro_created_in_ntis_portal;
      }
   }
   public String getRo_created_in_ntis_portal() {
      return this.ro_created_in_ntis_portal;
   }
   public void setRo_phone_number(String ro_phone_number) {
      if (this.isForceUpdate() || (this.ro_phone_number != null && !this.ro_phone_number.equals(ro_phone_number)) || (ro_phone_number != null && !ro_phone_number.equals(this.ro_phone_number))){
         this.ro_phone_number_changed = true; 
         this.record_changed = true;
         this.ro_phone_number = ro_phone_number;
      }
   }
   public String getRo_phone_number() {
      return this.ro_phone_number;
   }
   public void setRo_rejection_reason(String ro_rejection_reason) {
      if (this.isForceUpdate() || (this.ro_rejection_reason != null && !this.ro_rejection_reason.equals(ro_rejection_reason)) || (ro_rejection_reason != null && !ro_rejection_reason.equals(this.ro_rejection_reason))){
         this.ro_rejection_reason_changed = true; 
         this.record_changed = true;
         this.ro_rejection_reason = ro_rejection_reason;
      }
   }
   public String getRo_rejection_reason() {
      return this.ro_rejection_reason;
   }
   public void setRo_compliance_norms(String ro_compliance_norms) {
      if (this.isForceUpdate() || (this.ro_compliance_norms != null && !this.ro_compliance_norms.equals(ro_compliance_norms)) || (ro_compliance_norms != null && !ro_compliance_norms.equals(this.ro_compliance_norms))){
         this.ro_compliance_norms_changed = true; 
         this.record_changed = true;
         this.ro_compliance_norms = ro_compliance_norms;
      }
   }
   public String getRo_compliance_norms() {
      return this.ro_compliance_norms;
   }
   public void setRo_state(String ro_state) {
      if (this.isForceUpdate() || (this.ro_state != null && !this.ro_state.equals(ro_state)) || (ro_state != null && !ro_state.equals(this.ro_state))){
         this.ro_state_changed = true; 
         this.record_changed = true;
         this.ro_state = ro_state;
      }
   }
   public String getRo_state() {
      return this.ro_state;
   }
   public void setRo_created(String ro_created) {
      if (this.isForceUpdate() || (this.ro_created != null && !this.ro_created.equals(ro_created)) || (ro_created != null && !ro_created.equals(this.ro_created))){
         this.ro_created_changed = true; 
         this.record_changed = true;
         this.ro_created = ro_created;
      }
   }
   public String getRo_created() {
      return this.ro_created;
   }
   public void setRo_email(String ro_email) {
      if (this.isForceUpdate() || (this.ro_email != null && !this.ro_email.equals(ro_email)) || (ro_email != null && !ro_email.equals(this.ro_email))){
         this.ro_email_changed = true; 
         this.record_changed = true;
         this.ro_email = ro_email;
      }
   }
   public String getRo_email() {
      return this.ro_email;
   }
   public void setRo_date_to(String ro_date_to) {
      if (this.isForceUpdate() || (this.ro_date_to != null && !this.ro_date_to.equals(ro_date_to)) || (ro_date_to != null && !ro_date_to.equals(this.ro_date_to))){
         this.ro_date_to_changed = true; 
         this.record_changed = true;
         this.ro_date_to = ro_date_to;
      }
   }
   public String getRo_date_to() {
      return this.ro_date_to;
   }
   public void setRo_wtf_id(Double ro_wtf_id) {
      if (this.isForceUpdate() || (this.ro_wtf_id != null && !this.ro_wtf_id.equals(ro_wtf_id)) || (ro_wtf_id != null && !ro_wtf_id.equals(this.ro_wtf_id))){
         this.ro_wtf_id_changed = true; 
         this.record_changed = true;
         this.ro_wtf_id = ro_wtf_id;
      }
   }
   public Double getRo_wtf_id() {
      return this.ro_wtf_id;
   }
   public void setRo_org_id(Double ro_org_id) {
      if (this.isForceUpdate() || (this.ro_org_id != null && !this.ro_org_id.equals(ro_org_id)) || (ro_org_id != null && !ro_org_id.equals(this.ro_org_id))){
         this.ro_org_id_changed = true; 
         this.record_changed = true;
         this.ro_org_id = ro_org_id;
      }
   }
   public Double getRo_org_id() {
      return this.ro_org_id;
   }
   public void setRo_fil_id(Double ro_fil_id) {
      if (this.isForceUpdate() || (this.ro_fil_id != null && !this.ro_fil_id.equals(ro_fil_id)) || (ro_fil_id != null && !ro_fil_id.equals(this.ro_fil_id))){
         this.ro_fil_id_changed = true; 
         this.record_changed = true;
         this.ro_fil_id = ro_fil_id;
      }
   }
   public Double getRo_fil_id() {
      return this.ro_fil_id;
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
            this.ro_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.ro_id;
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
      if (ro_additional_description_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_additional_description!= null && ro_additional_description.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_ADDITIONAL_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ro_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_date_from!= null && ro_date_from.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_DATE_FROM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ro_created_in_ntis_portal_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_created_in_ntis_portal== null || EMPTY_STRING.equals(ro_created_in_ntis_portal)) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_CREATED_IN_NTIS_PORTAL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ro_created_in_ntis_portal!= null && ro_created_in_ntis_portal.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_CREATED_IN_NTIS_PORTAL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ro_phone_number_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_phone_number== null || EMPTY_STRING.equals(ro_phone_number)) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_PHONE_NUMBER", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ro_phone_number!= null && ro_phone_number.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_PHONE_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ro_rejection_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_rejection_reason!= null && ro_rejection_reason.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_REJECTION_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ro_compliance_norms_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_compliance_norms!= null && ro_compliance_norms.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_COMPLIANCE_NORMS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ro_state_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_state== null || EMPTY_STRING.equals(ro_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ro_state!= null && ro_state.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ro_created_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_created== null || EMPTY_STRING.equals(ro_created)) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_CREATED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ro_created!= null && ro_created.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_CREATED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ro_email_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_email== null || EMPTY_STRING.equals(ro_email)) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_EMAIL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ro_email!= null && ro_email.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ro_date_to_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_date_to!= null && ro_date_to.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_DATE_TO", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ro_wtf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_wtf_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_WTF_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ro_wtf_id!= null && (""+ro_wtf_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_WTF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (ro_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_org_id!= null && (""+ro_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (ro_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ro_fil_id!= null && (""+ro_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "RO_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_ORDER", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_RESEARCH_ORDER SET ";
      boolean changedExists = false;      if (ro_additional_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_ADDITIONAL_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_additional_description);
      }
      if (ro_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_date_from);
      }
      if (ro_created_in_ntis_portal_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_CREATED_IN_NTIS_PORTAL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_created_in_ntis_portal);
      }
      if (ro_phone_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_PHONE_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_phone_number);
      }
      if (ro_rejection_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_REJECTION_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_rejection_reason);
      }
      if (ro_compliance_norms_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_COMPLIANCE_NORMS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_compliance_norms);
      }
      if (ro_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_state);
      }
      if (ro_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_created);
      }
      if (ro_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_email);
      }
      if (ro_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_date_to);
      }
      if (ro_wtf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_WTF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_wtf_id);
      }
      if (ro_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_org_id);
      }
      if (ro_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RO_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ro_fil_id);
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
      answer = answer +" WHERE  RO_ID = ? ";
      updateStatementPart.addStatementParam(ro_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisResearchOrderDAO\":{\"ro_id\": \""+getRo_id()+"\", \"ro_additional_description\": \""+getRo_additional_description()+"\", \"ro_date_from\": \""+getRo_date_from()+"\", \"ro_created_in_ntis_portal\": \""+getRo_created_in_ntis_portal()+"\", \"ro_phone_number\": \""+getRo_phone_number()+"\", \"ro_rejection_reason\": \""+getRo_rejection_reason()+"\", \"ro_compliance_norms\": \""+getRo_compliance_norms()+"\", \"ro_state\": \""+getRo_state()+"\", \"ro_created\": \""+getRo_created()+"\", \"ro_email\": \""+getRo_email()+"\", \"ro_date_to\": \""+getRo_date_to()+"\", \"ro_wtf_id\": \""+getRo_wtf_id()+"\", \"ro_org_id\": \""+getRo_org_id()+"\", \"ro_fil_id\": \""+getRo_fil_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}