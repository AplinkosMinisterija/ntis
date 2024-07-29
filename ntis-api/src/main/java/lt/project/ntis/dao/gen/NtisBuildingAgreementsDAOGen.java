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

public class NtisBuildingAgreementsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_BUILDING_AGREEMENTS.BA_ID";
   private Double ba_id;
   private String ba_source;
   private String ba_wastewater_treatment;
   private String ba_state;
   private String ba_rejection_reason;
   private Date ba_network_connection_date;
   private Date ba_network_disconnection_date;
   private Date ba_created;
   private Double ba_bn_id;
   private Double ba_fil_id;
   private Double ba_org_id;
   private Date ba_manual_network_con_date;
   private Double ba_manual_org_id;
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

   protected boolean ba_id_changed;
   protected boolean ba_source_changed;
   protected boolean ba_wastewater_treatment_changed;
   protected boolean ba_state_changed;
   protected boolean ba_rejection_reason_changed;
   protected boolean ba_network_connection_date_changed;
   protected boolean ba_network_disconnection_date_changed;
   protected boolean ba_created_changed;
   protected boolean ba_bn_id_changed;
   protected boolean ba_fil_id_changed;
   protected boolean ba_org_id_changed;
   protected boolean ba_manual_network_con_date_changed;
   protected boolean ba_manual_org_id_changed;
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
   public NtisBuildingAgreementsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisBuildingAgreementsDAOGen(Double ba_id, String ba_source, String ba_wastewater_treatment, String ba_state, String ba_rejection_reason, Date ba_network_connection_date, Date ba_network_disconnection_date, Date ba_created, Double ba_bn_id, Double ba_fil_id, Double ba_org_id, Date ba_manual_network_con_date, Double ba_manual_org_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.ba_id = ba_id;
      this.ba_source = ba_source;
      this.ba_wastewater_treatment = ba_wastewater_treatment;
      this.ba_state = ba_state;
      this.ba_rejection_reason = ba_rejection_reason;
      this.ba_network_connection_date = ba_network_connection_date;
      this.ba_network_disconnection_date = ba_network_disconnection_date;
      this.ba_created = ba_created;
      this.ba_bn_id = ba_bn_id;
      this.ba_fil_id = ba_fil_id;
      this.ba_org_id = ba_org_id;
      this.ba_manual_network_con_date = ba_manual_network_con_date;
      this.ba_manual_org_id = ba_manual_org_id;
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
   public void copyValues(NtisBuildingAgreementsDAOGen obj) {
      this.setBa_id(obj.getBa_id());
      this.setBa_source(obj.getBa_source());
      this.setBa_wastewater_treatment(obj.getBa_wastewater_treatment());
      this.setBa_state(obj.getBa_state());
      this.setBa_rejection_reason(obj.getBa_rejection_reason());
      this.setBa_network_connection_date(obj.getBa_network_connection_date());
      this.setBa_network_disconnection_date(obj.getBa_network_disconnection_date());
      this.setBa_created(obj.getBa_created());
      this.setBa_bn_id(obj.getBa_bn_id());
      this.setBa_fil_id(obj.getBa_fil_id());
      this.setBa_org_id(obj.getBa_org_id());
      this.setBa_manual_network_con_date(obj.getBa_manual_network_con_date());
      this.setBa_manual_org_id(obj.getBa_manual_org_id());
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
      this.ba_id_changed = false;
      this.ba_source_changed = false;
      this.ba_wastewater_treatment_changed = false;
      this.ba_state_changed = false;
      this.ba_rejection_reason_changed = false;
      this.ba_network_connection_date_changed = false;
      this.ba_network_disconnection_date_changed = false;
      this.ba_created_changed = false;
      this.ba_bn_id_changed = false;
      this.ba_fil_id_changed = false;
      this.ba_org_id_changed = false;
      this.ba_manual_network_con_date_changed = false;
      this.ba_manual_org_id_changed = false;
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
   public void setBa_id(Double ba_id) {
      if (this.isForceUpdate() || (this.ba_id != null && !this.ba_id.equals(ba_id)) || (ba_id != null && !ba_id.equals(this.ba_id))){
         this.ba_id_changed = true; 
         this.record_changed = true;
         this.ba_id = ba_id;
      }
   }
   public Double getBa_id() {
      return this.ba_id;
   }
   public void setBa_source(String ba_source) {
      if (this.isForceUpdate() || (this.ba_source != null && !this.ba_source.equals(ba_source)) || (ba_source != null && !ba_source.equals(this.ba_source))){
         this.ba_source_changed = true; 
         this.record_changed = true;
         this.ba_source = ba_source;
      }
   }
   public String getBa_source() {
      return this.ba_source;
   }
   public void setBa_wastewater_treatment(String ba_wastewater_treatment) {
      if (this.isForceUpdate() || (this.ba_wastewater_treatment != null && !this.ba_wastewater_treatment.equals(ba_wastewater_treatment)) || (ba_wastewater_treatment != null && !ba_wastewater_treatment.equals(this.ba_wastewater_treatment))){
         this.ba_wastewater_treatment_changed = true; 
         this.record_changed = true;
         this.ba_wastewater_treatment = ba_wastewater_treatment;
      }
   }
   public String getBa_wastewater_treatment() {
      return this.ba_wastewater_treatment;
   }
   public void setBa_state(String ba_state) {
      if (this.isForceUpdate() || (this.ba_state != null && !this.ba_state.equals(ba_state)) || (ba_state != null && !ba_state.equals(this.ba_state))){
         this.ba_state_changed = true; 
         this.record_changed = true;
         this.ba_state = ba_state;
      }
   }
   public String getBa_state() {
      return this.ba_state;
   }
   public void setBa_rejection_reason(String ba_rejection_reason) {
      if (this.isForceUpdate() || (this.ba_rejection_reason != null && !this.ba_rejection_reason.equals(ba_rejection_reason)) || (ba_rejection_reason != null && !ba_rejection_reason.equals(this.ba_rejection_reason))){
         this.ba_rejection_reason_changed = true; 
         this.record_changed = true;
         this.ba_rejection_reason = ba_rejection_reason;
      }
   }
   public String getBa_rejection_reason() {
      return this.ba_rejection_reason;
   }
   public void setBa_network_connection_date(Date ba_network_connection_date) {
      if (this.isForceUpdate() || (this.ba_network_connection_date != null && !this.ba_network_connection_date.equals(ba_network_connection_date)) || (ba_network_connection_date != null && !ba_network_connection_date.equals(this.ba_network_connection_date))){
         this.ba_network_connection_date_changed = true; 
         this.record_changed = true;
         this.ba_network_connection_date = ba_network_connection_date;
      }
   }
   public Date getBa_network_connection_date() {
      return this.ba_network_connection_date;
   }
   public void setBa_network_disconnection_date(Date ba_network_disconnection_date) {
      if (this.isForceUpdate() || (this.ba_network_disconnection_date != null && !this.ba_network_disconnection_date.equals(ba_network_disconnection_date)) || (ba_network_disconnection_date != null && !ba_network_disconnection_date.equals(this.ba_network_disconnection_date))){
         this.ba_network_disconnection_date_changed = true; 
         this.record_changed = true;
         this.ba_network_disconnection_date = ba_network_disconnection_date;
      }
   }
   public Date getBa_network_disconnection_date() {
      return this.ba_network_disconnection_date;
   }
   public void setBa_created(Date ba_created) {
      if (this.isForceUpdate() || (this.ba_created != null && !this.ba_created.equals(ba_created)) || (ba_created != null && !ba_created.equals(this.ba_created))){
         this.ba_created_changed = true; 
         this.record_changed = true;
         this.ba_created = ba_created;
      }
   }
   public Date getBa_created() {
      return this.ba_created;
   }
   public void setBa_bn_id(Double ba_bn_id) {
      if (this.isForceUpdate() || (this.ba_bn_id != null && !this.ba_bn_id.equals(ba_bn_id)) || (ba_bn_id != null && !ba_bn_id.equals(this.ba_bn_id))){
         this.ba_bn_id_changed = true; 
         this.record_changed = true;
         this.ba_bn_id = ba_bn_id;
      }
   }
   public Double getBa_bn_id() {
      return this.ba_bn_id;
   }
   public void setBa_fil_id(Double ba_fil_id) {
      if (this.isForceUpdate() || (this.ba_fil_id != null && !this.ba_fil_id.equals(ba_fil_id)) || (ba_fil_id != null && !ba_fil_id.equals(this.ba_fil_id))){
         this.ba_fil_id_changed = true; 
         this.record_changed = true;
         this.ba_fil_id = ba_fil_id;
      }
   }
   public Double getBa_fil_id() {
      return this.ba_fil_id;
   }
   public void setBa_org_id(Double ba_org_id) {
      if (this.isForceUpdate() || (this.ba_org_id != null && !this.ba_org_id.equals(ba_org_id)) || (ba_org_id != null && !ba_org_id.equals(this.ba_org_id))){
         this.ba_org_id_changed = true; 
         this.record_changed = true;
         this.ba_org_id = ba_org_id;
      }
   }
   public Double getBa_org_id() {
      return this.ba_org_id;
   }
   public void setBa_manual_network_con_date(Date ba_manual_network_con_date) {
      if (this.isForceUpdate() || (this.ba_manual_network_con_date != null && !this.ba_manual_network_con_date.equals(ba_manual_network_con_date)) || (ba_manual_network_con_date != null && !ba_manual_network_con_date.equals(this.ba_manual_network_con_date))){
         this.ba_manual_network_con_date_changed = true; 
         this.record_changed = true;
         this.ba_manual_network_con_date = ba_manual_network_con_date;
      }
   }
   public Date getBa_manual_network_con_date() {
      return this.ba_manual_network_con_date;
   }
   public void setBa_manual_org_id(Double ba_manual_org_id) {
      if (this.isForceUpdate() || (this.ba_manual_org_id != null && !this.ba_manual_org_id.equals(ba_manual_org_id)) || (ba_manual_org_id != null && !ba_manual_org_id.equals(this.ba_manual_org_id))){
         this.ba_manual_org_id_changed = true; 
         this.record_changed = true;
         this.ba_manual_org_id = ba_manual_org_id;
      }
   }
   public Double getBa_manual_org_id() {
      return this.ba_manual_org_id;
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
            this.ba_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.ba_id;
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
      if (ba_source_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_source!= null && ba_source.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_SOURCE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (ba_wastewater_treatment_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_wastewater_treatment== null || EMPTY_STRING.equals(ba_wastewater_treatment)) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_WASTEWATER_TREATMENT", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ba_wastewater_treatment!= null && ba_wastewater_treatment.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_WASTEWATER_TREATMENT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ba_state_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_state== null || EMPTY_STRING.equals(ba_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ba_state!= null && ba_state.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (ba_rejection_reason_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_rejection_reason!= null && ba_rejection_reason.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_REJECTION_REASON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (ba_network_connection_date_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_network_connection_date== null) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_NETWORK_CONNECTION_DATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (ba_created_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_created== null) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_CREATED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (ba_bn_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_bn_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_BN_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ba_bn_id!= null && (""+ba_bn_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_BN_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (ba_fil_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_fil_id!= null && (""+ba_fil_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_FIL_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (ba_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_org_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_ORG_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (ba_org_id!= null && (""+ba_org_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (ba_manual_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (ba_manual_org_id!= null && (""+ba_manual_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "BA_MANUAL_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_BUILDING_AGREEMENTS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_BUILDING_AGREEMENTS SET ";
      boolean changedExists = false;      if (ba_source_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_SOURCE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_source);
      }
      if (ba_wastewater_treatment_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_WASTEWATER_TREATMENT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_wastewater_treatment);
      }
      if (ba_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_state);
      }
      if (ba_rejection_reason_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_REJECTION_REASON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_rejection_reason);
      }
      if (ba_network_connection_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_NETWORK_CONNECTION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ba_network_connection_date);
      }
      if (ba_network_disconnection_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_NETWORK_DISCONNECTION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ba_network_disconnection_date);
      }
      if (ba_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ba_created);
      }
      if (ba_bn_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_BN_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_bn_id);
      }
      if (ba_fil_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_FIL_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_fil_id);
      }
      if (ba_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_org_id);
      }
      if (ba_manual_network_con_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_MANUAL_NETWORK_CON_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(ba_manual_network_con_date);
      }
      if (ba_manual_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"BA_MANUAL_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(ba_manual_org_id);
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
      answer = answer +" WHERE  BA_ID = ? ";
      updateStatementPart.addStatementParam(ba_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisBuildingAgreementsDAO\":{\"ba_id\": \""+getBa_id()+"\", \"ba_source\": \""+getBa_source()+"\", \"ba_wastewater_treatment\": \""+getBa_wastewater_treatment()+"\", \"ba_state\": \""+getBa_state()+"\", \"ba_rejection_reason\": \""+getBa_rejection_reason()+"\", \"ba_network_connection_date\": \""+getBa_network_connection_date()+"\", \"ba_network_disconnection_date\": \""+getBa_network_disconnection_date()+"\", \"ba_created\": \""+getBa_created()+"\", \"ba_bn_id\": \""+getBa_bn_id()+"\", \"ba_fil_id\": \""+getBa_fil_id()+"\", \"ba_org_id\": \""+getBa_org_id()+"\", \"ba_manual_network_con_date\": \""+getBa_manual_network_con_date()+"\", \"ba_manual_org_id\": \""+getBa_manual_org_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}