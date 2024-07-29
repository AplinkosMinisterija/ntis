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

public class NtisAgglomerationsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_AGGLOMERATIONS.AGG_ID";
   private Double agg_id;
   private Double agg_municipality;
   private String agg_state;
   private Date agg_state_date;
   private String agg_confirmed_document_number;
   private Date agg_confirmed_date;
   private Date agg_created;
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
   private String agg_agglo_type;

   protected boolean agg_id_changed;
   protected boolean agg_municipality_changed;
   protected boolean agg_state_changed;
   protected boolean agg_state_date_changed;
   protected boolean agg_confirmed_document_number_changed;
   protected boolean agg_confirmed_date_changed;
   protected boolean agg_created_changed;
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
   protected boolean agg_agglo_type_changed;
   protected boolean record_changed;
   protected boolean recordLoaded;
   protected boolean forceUpdate;
   public NtisAgglomerationsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisAgglomerationsDAOGen(Double agg_id, Double agg_municipality, String agg_state, Date agg_state_date, String agg_confirmed_document_number, Date agg_confirmed_date, Date agg_created, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05, String agg_agglo_type) {
      this.agg_id = agg_id;
      this.agg_municipality = agg_municipality;
      this.agg_state = agg_state;
      this.agg_state_date = agg_state_date;
      this.agg_confirmed_document_number = agg_confirmed_document_number;
      this.agg_confirmed_date = agg_confirmed_date;
      this.agg_created = agg_created;
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
      this.agg_agglo_type = agg_agglo_type;
   }
   public void copyValues(NtisAgglomerationsDAOGen obj) {
      this.setAgg_id(obj.getAgg_id());
      this.setAgg_municipality(obj.getAgg_municipality());
      this.setAgg_state(obj.getAgg_state());
      this.setAgg_state_date(obj.getAgg_state_date());
      this.setAgg_confirmed_document_number(obj.getAgg_confirmed_document_number());
      this.setAgg_confirmed_date(obj.getAgg_confirmed_date());
      this.setAgg_created(obj.getAgg_created());
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
      this.setAgg_agglo_type(obj.getAgg_agglo_type());
   }
   protected void markObjectAsInitial() {
      this.agg_id_changed = false;
      this.agg_municipality_changed = false;
      this.agg_state_changed = false;
      this.agg_state_date_changed = false;
      this.agg_confirmed_document_number_changed = false;
      this.agg_confirmed_date_changed = false;
      this.agg_created_changed = false;
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
      this.agg_agglo_type_changed = false;
      this.record_changed = false;
   }
   public void setAgg_id(Double agg_id) {
      if (this.isForceUpdate() || (this.agg_id != null && !this.agg_id.equals(agg_id)) || (agg_id != null && !agg_id.equals(this.agg_id))){
         this.agg_id_changed = true; 
         this.record_changed = true;
         this.agg_id = agg_id;
      }
   }
   public Double getAgg_id() {
      return this.agg_id;
   }
   public void setAgg_municipality(Double agg_municipality) {
      if (this.isForceUpdate() || (this.agg_municipality != null && !this.agg_municipality.equals(agg_municipality)) || (agg_municipality != null && !agg_municipality.equals(this.agg_municipality))){
         this.agg_municipality_changed = true; 
         this.record_changed = true;
         this.agg_municipality = agg_municipality;
      }
   }
   public Double getAgg_municipality() {
      return this.agg_municipality;
   }
   public void setAgg_state(String agg_state) {
      if (this.isForceUpdate() || (this.agg_state != null && !this.agg_state.equals(agg_state)) || (agg_state != null && !agg_state.equals(this.agg_state))){
         this.agg_state_changed = true; 
         this.record_changed = true;
         this.agg_state = agg_state;
      }
   }
   public String getAgg_state() {
      return this.agg_state;
   }
   public void setAgg_state_date(Date agg_state_date) {
      if (this.isForceUpdate() || (this.agg_state_date != null && !this.agg_state_date.equals(agg_state_date)) || (agg_state_date != null && !agg_state_date.equals(this.agg_state_date))){
         this.agg_state_date_changed = true; 
         this.record_changed = true;
         this.agg_state_date = agg_state_date;
      }
   }
   public Date getAgg_state_date() {
      return this.agg_state_date;
   }
   public void setAgg_confirmed_document_number(String agg_confirmed_document_number) {
      if (this.isForceUpdate() || (this.agg_confirmed_document_number != null && !this.agg_confirmed_document_number.equals(agg_confirmed_document_number)) || (agg_confirmed_document_number != null && !agg_confirmed_document_number.equals(this.agg_confirmed_document_number))){
         this.agg_confirmed_document_number_changed = true; 
         this.record_changed = true;
         this.agg_confirmed_document_number = agg_confirmed_document_number;
      }
   }
   public String getAgg_confirmed_document_number() {
      return this.agg_confirmed_document_number;
   }
   public void setAgg_confirmed_date(Date agg_confirmed_date) {
      if (this.isForceUpdate() || (this.agg_confirmed_date != null && !this.agg_confirmed_date.equals(agg_confirmed_date)) || (agg_confirmed_date != null && !agg_confirmed_date.equals(this.agg_confirmed_date))){
         this.agg_confirmed_date_changed = true; 
         this.record_changed = true;
         this.agg_confirmed_date = agg_confirmed_date;
      }
   }
   public Date getAgg_confirmed_date() {
      return this.agg_confirmed_date;
   }
   public void setAgg_created(Date agg_created) {
      if (this.isForceUpdate() || (this.agg_created != null && !this.agg_created.equals(agg_created)) || (agg_created != null && !agg_created.equals(this.agg_created))){
         this.agg_created_changed = true; 
         this.record_changed = true;
         this.agg_created = agg_created;
      }
   }
   public Date getAgg_created() {
      return this.agg_created;
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
   public void setAgg_agglo_type(String agg_agglo_type) {
      if (this.isForceUpdate() || (this.agg_agglo_type != null && !this.agg_agglo_type.equals(agg_agglo_type)) || (agg_agglo_type != null && !agg_agglo_type.equals(this.agg_agglo_type))){
         this.agg_agglo_type_changed = true; 
         this.record_changed = true;
         this.agg_agglo_type = agg_agglo_type;
      }
   }
   public String getAgg_agglo_type() {
      return this.agg_agglo_type;
   }
    @JsonIgnore
    public boolean isRecordChanged() {
        return record_changed;
     }
    @JsonIgnore
    public void setRecordIdentifier(Object recIdentifier) {
        if (recIdentifier != null && !EMPTY_STRING.equals(recIdentifier)  && !"null".equalsIgnoreCase(recIdentifier.toString())){
            this.agg_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.agg_id;
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
      if (agg_municipality_changed || operation == Utils.OPERATION_INSERT) {
         if (agg_municipality== null) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_MUNICIPALITY", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (agg_municipality!= null && (""+agg_municipality.intValue()).length()>10) {
               throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_MUNICIPALITY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
            }
         }
      }
      if (agg_state_changed || operation == Utils.OPERATION_INSERT) {
         if (agg_state== null || EMPTY_STRING.equals(agg_state)) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_STATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (agg_state!= null && agg_state.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_STATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (agg_confirmed_document_number_changed || operation == Utils.OPERATION_INSERT) {
         if (agg_confirmed_document_number!= null && agg_confirmed_document_number.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_CONFIRMED_DOCUMENT_NUMBER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (agg_created_changed || operation == Utils.OPERATION_INSERT) {
         if (agg_created== null) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_CREATED", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (agg_agglo_type_changed || operation == Utils.OPERATION_INSERT) {
         if (agg_agglo_type!= null && agg_agglo_type.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_AGGLOMERATIONS", "AGG_AGGLO_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_AGGLOMERATIONS SET ";
      boolean changedExists = false;      if (agg_municipality_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_MUNICIPALITY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(agg_municipality);
      }
      if (agg_state_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_STATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(agg_state);
      }
      if (agg_state_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_STATE_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(agg_state_date);
      }
      if (agg_confirmed_document_number_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_CONFIRMED_DOCUMENT_NUMBER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(agg_confirmed_document_number);
      }
      if (agg_confirmed_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_CONFIRMED_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(agg_confirmed_date);
      }
      if (agg_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(agg_created);
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
      if (agg_agglo_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"AGG_AGGLO_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(agg_agglo_type);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  AGG_ID = ? ";
      updateStatementPart.addStatementParam(agg_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisAgglomerationsDAO\":{\"agg_id\": \""+getAgg_id()+"\", \"agg_municipality\": \""+getAgg_municipality()+"\", \"agg_state\": \""+getAgg_state()+"\", \"agg_state_date\": \""+getAgg_state_date()+"\", \"agg_confirmed_document_number\": \""+getAgg_confirmed_document_number()+"\", \"agg_confirmed_date\": \""+getAgg_confirmed_date()+"\", \"agg_created\": \""+getAgg_created()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\", \"agg_agglo_type\": \""+getAgg_agglo_type()+"\"}}";
   }

}