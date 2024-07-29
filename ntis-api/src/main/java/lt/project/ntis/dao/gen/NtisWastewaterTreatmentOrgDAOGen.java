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

public class NtisWastewaterTreatmentOrgDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_WASTEWATER_TREATMENT_ORG.WTO_ID";
   private Double wto_id;
   private String wto_name;
   private String wto_address;
   private Double wto_productivity;
   private String wto_domestic_sewage;
   private String wto_industrial_sewage;
   private String wto_sludge;
   private String wto_is_it_used;
   private Double wto_ad_id;
   private Double wto_org_id;
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
   private String wto_auto_accept;
   private String wto_no_notifications;

   protected boolean wto_id_changed;
   protected boolean wto_name_changed;
   protected boolean wto_address_changed;
   protected boolean wto_productivity_changed;
   protected boolean wto_domestic_sewage_changed;
   protected boolean wto_industrial_sewage_changed;
   protected boolean wto_sludge_changed;
   protected boolean wto_is_it_used_changed;
   protected boolean wto_ad_id_changed;
   protected boolean wto_org_id_changed;
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
   protected boolean wto_auto_accept_changed;
   protected boolean wto_no_notifications_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisWastewaterTreatmentOrgDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisWastewaterTreatmentOrgDAOGen(Double wto_id, String wto_name, String wto_address, Double wto_productivity, String wto_domestic_sewage, String wto_industrial_sewage, String wto_sludge, String wto_is_it_used, Double wto_ad_id, Double wto_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String wto_auto_accept, String wto_no_notifications) {
      this.wto_id = wto_id;
      this.wto_name = wto_name;
      this.wto_address = wto_address;
      this.wto_productivity = wto_productivity;
      this.wto_domestic_sewage = wto_domestic_sewage;
      this.wto_industrial_sewage = wto_industrial_sewage;
      this.wto_sludge = wto_sludge;
      this.wto_is_it_used = wto_is_it_used;
      this.wto_ad_id = wto_ad_id;
      this.wto_org_id = wto_org_id;
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
      this.wto_auto_accept = wto_auto_accept;
      this.wto_no_notifications = wto_no_notifications;
   }
   public void copyValues(NtisWastewaterTreatmentOrgDAOGen obj) {
      this.setWto_id(obj.getWto_id());
      this.setWto_name(obj.getWto_name());
      this.setWto_address(obj.getWto_address());
      this.setWto_productivity(obj.getWto_productivity());
      this.setWto_domestic_sewage(obj.getWto_domestic_sewage());
      this.setWto_industrial_sewage(obj.getWto_industrial_sewage());
      this.setWto_sludge(obj.getWto_sludge());
      this.setWto_is_it_used(obj.getWto_is_it_used());
      this.setWto_ad_id(obj.getWto_ad_id());
      this.setWto_org_id(obj.getWto_org_id());
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
      this.setWto_auto_accept(obj.getWto_auto_accept());
      this.setWto_no_notifications(obj.getWto_no_notifications());
   }
   protected void markObjectAsInitial() {
      this.wto_id_changed = false;
      this.wto_name_changed = false;
      this.wto_address_changed = false;
      this.wto_productivity_changed = false;
      this.wto_domestic_sewage_changed = false;
      this.wto_industrial_sewage_changed = false;
      this.wto_sludge_changed = false;
      this.wto_is_it_used_changed = false;
      this.wto_ad_id_changed = false;
      this.wto_org_id_changed = false;
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
      this.wto_auto_accept_changed = false;
      this.wto_no_notifications_changed = false;
      this.record_changed = false;
   }
   public void setWto_id(Double wto_id) {
      if (this.isForceUpdate() || (this.wto_id != null && !this.wto_id.equals(wto_id)) || (wto_id != null && !wto_id.equals(this.wto_id))){
         this.wto_id_changed = true; 
         this.record_changed = true;
         this.wto_id = wto_id;
      }
   }
   public Double getWto_id() {
      return this.wto_id;
   }
   public void setWto_name(String wto_name) {
      if (this.isForceUpdate() || (this.wto_name != null && !this.wto_name.equals(wto_name)) || (wto_name != null && !wto_name.equals(this.wto_name))){
         this.wto_name_changed = true; 
         this.record_changed = true;
         this.wto_name = wto_name;
      }
   }
   public String getWto_name() {
      return this.wto_name;
   }
   public void setWto_address(String wto_address) {
      if (this.isForceUpdate() || (this.wto_address != null && !this.wto_address.equals(wto_address)) || (wto_address != null && !wto_address.equals(this.wto_address))){
         this.wto_address_changed = true; 
         this.record_changed = true;
         this.wto_address = wto_address;
      }
   }
   public String getWto_address() {
      return this.wto_address;
   }
   public void setWto_productivity(Double wto_productivity) {
      if (this.isForceUpdate() || (this.wto_productivity != null && !this.wto_productivity.equals(wto_productivity)) || (wto_productivity != null && !wto_productivity.equals(this.wto_productivity))){
         this.wto_productivity_changed = true; 
         this.record_changed = true;
         this.wto_productivity = wto_productivity;
      }
   }
   public Double getWto_productivity() {
      return this.wto_productivity;
   }
   public void setWto_domestic_sewage(String wto_domestic_sewage) {
      if (this.isForceUpdate() || (this.wto_domestic_sewage != null && !this.wto_domestic_sewage.equals(wto_domestic_sewage)) || (wto_domestic_sewage != null && !wto_domestic_sewage.equals(this.wto_domestic_sewage))){
         this.wto_domestic_sewage_changed = true; 
         this.record_changed = true;
         this.wto_domestic_sewage = wto_domestic_sewage;
      }
   }
   public String getWto_domestic_sewage() {
      return this.wto_domestic_sewage;
   }
   public void setWto_industrial_sewage(String wto_industrial_sewage) {
      if (this.isForceUpdate() || (this.wto_industrial_sewage != null && !this.wto_industrial_sewage.equals(wto_industrial_sewage)) || (wto_industrial_sewage != null && !wto_industrial_sewage.equals(this.wto_industrial_sewage))){
         this.wto_industrial_sewage_changed = true; 
         this.record_changed = true;
         this.wto_industrial_sewage = wto_industrial_sewage;
      }
   }
   public String getWto_industrial_sewage() {
      return this.wto_industrial_sewage;
   }
   public void setWto_sludge(String wto_sludge) {
      if (this.isForceUpdate() || (this.wto_sludge != null && !this.wto_sludge.equals(wto_sludge)) || (wto_sludge != null && !wto_sludge.equals(this.wto_sludge))){
         this.wto_sludge_changed = true; 
         this.record_changed = true;
         this.wto_sludge = wto_sludge;
      }
   }
   public String getWto_sludge() {
      return this.wto_sludge;
   }
   public void setWto_is_it_used(String wto_is_it_used) {
      if (this.isForceUpdate() || (this.wto_is_it_used != null && !this.wto_is_it_used.equals(wto_is_it_used)) || (wto_is_it_used != null && !wto_is_it_used.equals(this.wto_is_it_used))){
         this.wto_is_it_used_changed = true; 
         this.record_changed = true;
         this.wto_is_it_used = wto_is_it_used;
      }
   }
   public String getWto_is_it_used() {
      return this.wto_is_it_used;
   }
   public void setWto_ad_id(Double wto_ad_id) {
      if (this.isForceUpdate() || (this.wto_ad_id != null && !this.wto_ad_id.equals(wto_ad_id)) || (wto_ad_id != null && !wto_ad_id.equals(this.wto_ad_id))){
         this.wto_ad_id_changed = true; 
         this.record_changed = true;
         this.wto_ad_id = wto_ad_id;
      }
   }
   public Double getWto_ad_id() {
      return this.wto_ad_id;
   }
   public void setWto_org_id(Double wto_org_id) {
      if (this.isForceUpdate() || (this.wto_org_id != null && !this.wto_org_id.equals(wto_org_id)) || (wto_org_id != null && !wto_org_id.equals(this.wto_org_id))){
         this.wto_org_id_changed = true; 
         this.record_changed = true;
         this.wto_org_id = wto_org_id;
      }
   }
   public Double getWto_org_id() {
      return this.wto_org_id;
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
   public void setWto_auto_accept(String wto_auto_accept) {
      if (this.isForceUpdate() || (this.wto_auto_accept != null && !this.wto_auto_accept.equals(wto_auto_accept)) || (wto_auto_accept != null && !wto_auto_accept.equals(this.wto_auto_accept))){
         this.wto_auto_accept_changed = true; 
         this.record_changed = true;
         this.wto_auto_accept = wto_auto_accept;
      }
   }
   public String getWto_auto_accept() {
      return this.wto_auto_accept;
   }
   public void setWto_no_notifications(String wto_no_notifications) {
      if (this.isForceUpdate() || (this.wto_no_notifications != null && !this.wto_no_notifications.equals(wto_no_notifications)) || (wto_no_notifications != null && !wto_no_notifications.equals(this.wto_no_notifications))){
         this.wto_no_notifications_changed = true; 
         this.record_changed = true;
         this.wto_no_notifications = wto_no_notifications;
      }
   }
   public String getWto_no_notifications() {
      return this.wto_no_notifications;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.wto_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.wto_id;
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
      if (wto_name_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_name== null || EMPTY_STRING.equals(wto_name)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_NAME", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wto_name!= null && wto_name.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (wto_address_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_address== null || EMPTY_STRING.equals(wto_address)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_ADDRESS", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wto_address!= null && wto_address.length()>200) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_ADDRESS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (wto_productivity_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_productivity!= null && (""+wto_productivity.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_PRODUCTIVITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (wto_domestic_sewage_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_domestic_sewage== null || EMPTY_STRING.equals(wto_domestic_sewage)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_DOMESTIC_SEWAGE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wto_domestic_sewage!= null && wto_domestic_sewage.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_DOMESTIC_SEWAGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (wto_industrial_sewage_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_industrial_sewage== null || EMPTY_STRING.equals(wto_industrial_sewage)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_INDUSTRIAL_SEWAGE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wto_industrial_sewage!= null && wto_industrial_sewage.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_INDUSTRIAL_SEWAGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (wto_sludge_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_sludge== null || EMPTY_STRING.equals(wto_sludge)) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_SLUDGE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (wto_sludge!= null && wto_sludge.length()>1) {
               throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_SLUDGE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (wto_is_it_used_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_is_it_used!= null && wto_is_it_used.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_IS_IT_USED", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (wto_ad_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_ad_id!= null && (""+wto_ad_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_AD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (wto_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_org_id!= null && (""+wto_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (wto_auto_accept_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_auto_accept!= null && wto_auto_accept.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_AUTO_ACCEPT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (wto_no_notifications_changed || operation == Utils.OPERATION_INSERT) {
         if (wto_no_notifications!= null && wto_no_notifications.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_WASTEWATER_TREATMENT_ORG", "WTO_NO_NOTIFICATIONS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_WASTEWATER_TREATMENT_ORG SET ";
      boolean changedExists = false;      if (wto_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_name);
      }
      if (wto_address_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_ADDRESS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_address);
      }
      if (wto_productivity_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_PRODUCTIVITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_productivity);
      }
      if (wto_domestic_sewage_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_DOMESTIC_SEWAGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_domestic_sewage);
      }
      if (wto_industrial_sewage_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_INDUSTRIAL_SEWAGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_industrial_sewage);
      }
      if (wto_sludge_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_SLUDGE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_sludge);
      }
      if (wto_is_it_used_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_IS_IT_USED = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_is_it_used);
      }
      if (wto_ad_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_AD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_ad_id);
      }
      if (wto_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_org_id);
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
      if (wto_auto_accept_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_AUTO_ACCEPT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_auto_accept);
      }
      if (wto_no_notifications_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"WTO_NO_NOTIFICATIONS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(wto_no_notifications);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  WTO_ID = ? ";
      updateStatementPart.addStatementParam(wto_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisWastewaterTreatmentOrgDAO\":{\"wto_id\": \""+getWto_id()+"\", \"wto_name\": \""+getWto_name()+"\", \"wto_address\": \""+getWto_address()+"\", \"wto_productivity\": \""+getWto_productivity()+"\", \"wto_domestic_sewage\": \""+getWto_domestic_sewage()+"\", \"wto_industrial_sewage\": \""+getWto_industrial_sewage()+"\", \"wto_sludge\": \""+getWto_sludge()+"\", \"wto_is_it_used\": \""+getWto_is_it_used()+"\", \"wto_ad_id\": \""+getWto_ad_id()+"\", \"wto_org_id\": \""+getWto_org_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\", \"wto_auto_accept\": \""+getWto_auto_accept()+"\", \"wto_no_notifications\": \""+getWto_no_notifications()+"\"}}";
   }

}