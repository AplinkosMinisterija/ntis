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

public class NtisResearchesDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_RESEARCHES.RES_ID";
   private Double res_id;
   private String res_reserch_type;
   private Date res_sample_date;
   private Date res_research_date;
   private Double res_value;
   private String res_description;
   private Date res_created;
   private Double res_ord_id;
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
   private Double res_rn_id;

   protected boolean res_id_changed;
   protected boolean res_reserch_type_changed;
   protected boolean res_sample_date_changed;
   protected boolean res_research_date_changed;
   protected boolean res_value_changed;
   protected boolean res_description_changed;
   protected boolean res_created_changed;
   protected boolean res_ord_id_changed;
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
   protected boolean res_rn_id_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisResearchesDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisResearchesDAOGen(Double res_id, String res_reserch_type, Date res_sample_date, Date res_research_date, Double res_value, String res_description, Date res_created, Double res_ord_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, Double res_rn_id) {
      this.res_id = res_id;
      this.res_reserch_type = res_reserch_type;
      this.res_sample_date = res_sample_date;
      this.res_research_date = res_research_date;
      this.res_value = res_value;
      this.res_description = res_description;
      this.res_created = res_created;
      this.res_ord_id = res_ord_id;
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
      this.res_rn_id = res_rn_id;
   }
   public void copyValues(NtisResearchesDAOGen obj) {
      this.setRes_id(obj.getRes_id());
      this.setRes_reserch_type(obj.getRes_reserch_type());
      this.setRes_sample_date(obj.getRes_sample_date());
      this.setRes_research_date(obj.getRes_research_date());
      this.setRes_value(obj.getRes_value());
      this.setRes_description(obj.getRes_description());
      this.setRes_created(obj.getRes_created());
      this.setRes_ord_id(obj.getRes_ord_id());
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
      this.setRes_rn_id(obj.getRes_rn_id());
   }
   protected void markObjectAsInitial() {
      this.res_id_changed = false;
      this.res_reserch_type_changed = false;
      this.res_sample_date_changed = false;
      this.res_research_date_changed = false;
      this.res_value_changed = false;
      this.res_description_changed = false;
      this.res_created_changed = false;
      this.res_ord_id_changed = false;
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
      this.res_rn_id_changed = false;
      this.record_changed = false;
   }
   public void setRes_id(Double res_id) {
      if (this.isForceUpdate() || (this.res_id != null && !this.res_id.equals(res_id)) || (res_id != null && !res_id.equals(this.res_id))){
         this.res_id_changed = true; 
         this.record_changed = true;
         this.res_id = res_id;
      }
   }
   public Double getRes_id() {
      return this.res_id;
   }
   public void setRes_reserch_type(String res_reserch_type) {
      if (this.isForceUpdate() || (this.res_reserch_type != null && !this.res_reserch_type.equals(res_reserch_type)) || (res_reserch_type != null && !res_reserch_type.equals(this.res_reserch_type))){
         this.res_reserch_type_changed = true; 
         this.record_changed = true;
         this.res_reserch_type = res_reserch_type;
      }
   }
   public String getRes_reserch_type() {
      return this.res_reserch_type;
   }
   public void setRes_sample_date(Date res_sample_date) {
      if (this.isForceUpdate() || (this.res_sample_date != null && !this.res_sample_date.equals(res_sample_date)) || (res_sample_date != null && !res_sample_date.equals(this.res_sample_date))){
         this.res_sample_date_changed = true; 
         this.record_changed = true;
         this.res_sample_date = res_sample_date;
      }
   }
   public Date getRes_sample_date() {
      return this.res_sample_date;
   }
   public void setRes_research_date(Date res_research_date) {
      if (this.isForceUpdate() || (this.res_research_date != null && !this.res_research_date.equals(res_research_date)) || (res_research_date != null && !res_research_date.equals(this.res_research_date))){
         this.res_research_date_changed = true; 
         this.record_changed = true;
         this.res_research_date = res_research_date;
      }
   }
   public Date getRes_research_date() {
      return this.res_research_date;
   }
   public void setRes_value(Double res_value) {
      if (this.isForceUpdate() || (this.res_value != null && !this.res_value.equals(res_value)) || (res_value != null && !res_value.equals(this.res_value))){
         this.res_value_changed = true; 
         this.record_changed = true;
         this.res_value = res_value;
      }
   }
   public Double getRes_value() {
      return this.res_value;
   }
   public void setRes_description(String res_description) {
      if (this.isForceUpdate() || (this.res_description != null && !this.res_description.equals(res_description)) || (res_description != null && !res_description.equals(this.res_description))){
         this.res_description_changed = true; 
         this.record_changed = true;
         this.res_description = res_description;
      }
   }
   public String getRes_description() {
      return this.res_description;
   }
   public void setRes_created(Date res_created) {
      if (this.isForceUpdate() || (this.res_created != null && !this.res_created.equals(res_created)) || (res_created != null && !res_created.equals(this.res_created))){
         this.res_created_changed = true; 
         this.record_changed = true;
         this.res_created = res_created;
      }
   }
   public Date getRes_created() {
      return this.res_created;
   }
   public void setRes_ord_id(Double res_ord_id) {
      if (this.isForceUpdate() || (this.res_ord_id != null && !this.res_ord_id.equals(res_ord_id)) || (res_ord_id != null && !res_ord_id.equals(this.res_ord_id))){
         this.res_ord_id_changed = true; 
         this.record_changed = true;
         this.res_ord_id = res_ord_id;
      }
   }
   public Double getRes_ord_id() {
      return this.res_ord_id;
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
   public void setRes_rn_id(Double res_rn_id) {
      if (this.isForceUpdate() || (this.res_rn_id != null && !this.res_rn_id.equals(res_rn_id)) || (res_rn_id != null && !res_rn_id.equals(this.res_rn_id))){
         this.res_rn_id_changed = true; 
         this.record_changed = true;
         this.res_rn_id = res_rn_id;
      }
   }
   public Double getRes_rn_id() {
      return this.res_rn_id;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.res_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.res_id;
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
      if (res_reserch_type_changed || operation == Utils.OPERATION_INSERT) {
         if (res_reserch_type== null || EMPTY_STRING.equals(res_reserch_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_RESERCH_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (res_reserch_type!= null && res_reserch_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_RESERCH_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (res_value_changed || operation == Utils.OPERATION_INSERT) {
         if (res_value!= null && (""+res_value.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_VALUE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (res_description_changed || operation == Utils.OPERATION_INSERT) {
         if (res_description!= null && res_description.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (res_ord_id_changed || operation == Utils.OPERATION_INSERT) {
         if (res_ord_id== null) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_ORD_ID", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (res_ord_id!= null && (""+res_ord_id.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_ORD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (res_rn_id_changed || operation == Utils.OPERATION_INSERT) {
         if (res_rn_id!= null && (""+res_rn_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCHES", "RES_RN_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_RESEARCHES SET ";
      boolean changedExists = false;      if (res_reserch_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_RESERCH_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(res_reserch_type);
      }
      if (res_sample_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_SAMPLE_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(res_sample_date);
      }
      if (res_research_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_RESEARCH_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(res_research_date);
      }
      if (res_value_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_VALUE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(res_value);
      }
      if (res_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(res_description);
      }
      if (res_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(res_created);
      }
      if (res_ord_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_ORD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(res_ord_id);
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
      if (res_rn_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RES_RN_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(res_rn_id);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  RES_ID = ? ";
      updateStatementPart.addStatementParam(res_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisResearchesDAO\":{\"res_id\": \""+getRes_id()+"\", \"res_reserch_type\": \""+getRes_reserch_type()+"\", \"res_sample_date\": \""+getRes_sample_date()+"\", \"res_research_date\": \""+getRes_research_date()+"\", \"res_value\": \""+getRes_value()+"\", \"res_description\": \""+getRes_description()+"\", \"res_created\": \""+getRes_created()+"\", \"res_ord_id\": \""+getRes_ord_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\", \"res_rn_id\": \""+getRes_rn_id()+"\"}}";
   }

}