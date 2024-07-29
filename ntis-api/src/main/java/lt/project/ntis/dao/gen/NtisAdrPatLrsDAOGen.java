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

public class NtisAdrPatLrsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_ADR_PAT_LRS.APL_ID";
   private Double apl_id;
   private Double apl_municipality_code;
   private Double apl_pat_code;
   private Double apl_aob_code;
   private String apl_pat_nr;
   private Date apl_date_from;
   private Date apl_date_to;
   private Double apl_ads_id;
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

   protected boolean apl_id_changed;
   protected boolean apl_municipality_code_changed;
   protected boolean apl_pat_code_changed;
   protected boolean apl_aob_code_changed;
   protected boolean apl_pat_nr_changed;
   protected boolean apl_date_from_changed;
   protected boolean apl_date_to_changed;
   protected boolean apl_ads_id_changed;
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
   public NtisAdrPatLrsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisAdrPatLrsDAOGen(Double apl_id, Double apl_municipality_code, Double apl_pat_code, Double apl_aob_code, String apl_pat_nr, Date apl_date_from, Date apl_date_to, Double apl_ads_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.apl_id = apl_id;
      this.apl_municipality_code = apl_municipality_code;
      this.apl_pat_code = apl_pat_code;
      this.apl_aob_code = apl_aob_code;
      this.apl_pat_nr = apl_pat_nr;
      this.apl_date_from = apl_date_from;
      this.apl_date_to = apl_date_to;
      this.apl_ads_id = apl_ads_id;
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
   public void copyValues(NtisAdrPatLrsDAOGen obj) {
      this.setApl_id(obj.getApl_id());
      this.setApl_municipality_code(obj.getApl_municipality_code());
      this.setApl_pat_code(obj.getApl_pat_code());
      this.setApl_aob_code(obj.getApl_aob_code());
      this.setApl_pat_nr(obj.getApl_pat_nr());
      this.setApl_date_from(obj.getApl_date_from());
      this.setApl_date_to(obj.getApl_date_to());
      this.setApl_ads_id(obj.getApl_ads_id());
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
      this.apl_id_changed = false;
      this.apl_municipality_code_changed = false;
      this.apl_pat_code_changed = false;
      this.apl_aob_code_changed = false;
      this.apl_pat_nr_changed = false;
      this.apl_date_from_changed = false;
      this.apl_date_to_changed = false;
      this.apl_ads_id_changed = false;
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
   public void setApl_id(Double apl_id) {
      if (this.isForceUpdate() || (this.apl_id != null && !this.apl_id.equals(apl_id)) || (apl_id != null && !apl_id.equals(this.apl_id))){
         this.apl_id_changed = true; 
         this.record_changed = true;
         this.apl_id = apl_id;
      }
   }
   public Double getApl_id() {
      return this.apl_id;
   }
   public void setApl_municipality_code(Double apl_municipality_code) {
      if (this.isForceUpdate() || (this.apl_municipality_code != null && !this.apl_municipality_code.equals(apl_municipality_code)) || (apl_municipality_code != null && !apl_municipality_code.equals(this.apl_municipality_code))){
         this.apl_municipality_code_changed = true; 
         this.record_changed = true;
         this.apl_municipality_code = apl_municipality_code;
      }
   }
   public Double getApl_municipality_code() {
      return this.apl_municipality_code;
   }
   public void setApl_pat_code(Double apl_pat_code) {
      if (this.isForceUpdate() || (this.apl_pat_code != null && !this.apl_pat_code.equals(apl_pat_code)) || (apl_pat_code != null && !apl_pat_code.equals(this.apl_pat_code))){
         this.apl_pat_code_changed = true; 
         this.record_changed = true;
         this.apl_pat_code = apl_pat_code;
      }
   }
   public Double getApl_pat_code() {
      return this.apl_pat_code;
   }
   public void setApl_aob_code(Double apl_aob_code) {
      if (this.isForceUpdate() || (this.apl_aob_code != null && !this.apl_aob_code.equals(apl_aob_code)) || (apl_aob_code != null && !apl_aob_code.equals(this.apl_aob_code))){
         this.apl_aob_code_changed = true; 
         this.record_changed = true;
         this.apl_aob_code = apl_aob_code;
      }
   }
   public Double getApl_aob_code() {
      return this.apl_aob_code;
   }
   public void setApl_pat_nr(String apl_pat_nr) {
      if (this.isForceUpdate() || (this.apl_pat_nr != null && !this.apl_pat_nr.equals(apl_pat_nr)) || (apl_pat_nr != null && !apl_pat_nr.equals(this.apl_pat_nr))){
         this.apl_pat_nr_changed = true; 
         this.record_changed = true;
         this.apl_pat_nr = apl_pat_nr;
      }
   }
   public String getApl_pat_nr() {
      return this.apl_pat_nr;
   }
   public void setApl_date_from(Date apl_date_from) {
      if (this.isForceUpdate() || (this.apl_date_from != null && !this.apl_date_from.equals(apl_date_from)) || (apl_date_from != null && !apl_date_from.equals(this.apl_date_from))){
         this.apl_date_from_changed = true; 
         this.record_changed = true;
         this.apl_date_from = apl_date_from;
      }
   }
   public Date getApl_date_from() {
      return this.apl_date_from;
   }
   public void setApl_date_to(Date apl_date_to) {
      if (this.isForceUpdate() || (this.apl_date_to != null && !this.apl_date_to.equals(apl_date_to)) || (apl_date_to != null && !apl_date_to.equals(this.apl_date_to))){
         this.apl_date_to_changed = true; 
         this.record_changed = true;
         this.apl_date_to = apl_date_to;
      }
   }
   public Date getApl_date_to() {
      return this.apl_date_to;
   }
   public void setApl_ads_id(Double apl_ads_id) {
      if (this.isForceUpdate() || (this.apl_ads_id != null && !this.apl_ads_id.equals(apl_ads_id)) || (apl_ads_id != null && !apl_ads_id.equals(this.apl_ads_id))){
         this.apl_ads_id_changed = true; 
         this.record_changed = true;
         this.apl_ads_id = apl_ads_id;
      }
   }
   public Double getApl_ads_id() {
      return this.apl_ads_id;
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
            this.apl_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.apl_id;
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
      if (apl_municipality_code_changed || operation == Utils.OPERATION_INSERT) {
         if (apl_municipality_code== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_MUNICIPALITY_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (apl_municipality_code!= null && (""+apl_municipality_code.intValue()).length()>12) {
               throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_MUNICIPALITY_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
            }
         }
      }
      if (apl_pat_code_changed || operation == Utils.OPERATION_INSERT) {
         if (apl_pat_code== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_PAT_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (apl_pat_code!= null && (""+apl_pat_code.intValue()).length()>12) {
               throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_PAT_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
            }
         }
      }
      if (apl_aob_code_changed || operation == Utils.OPERATION_INSERT) {
         if (apl_aob_code== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_AOB_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (apl_aob_code!= null && (""+apl_aob_code.intValue()).length()>12) {
               throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_AOB_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
            }
         }
      }
      if (apl_pat_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (apl_pat_nr== null || EMPTY_STRING.equals(apl_pat_nr)) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_PAT_NR", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (apl_pat_nr!= null && apl_pat_nr.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_PAT_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (apl_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (apl_date_from== null) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (apl_ads_id_changed || operation == Utils.OPERATION_INSERT) {
         if (apl_ads_id!= null && (""+apl_ads_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "APL_ADS_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ADR_PAT_LRS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_ADR_PAT_LRS SET ";
      boolean changedExists = false;      if (apl_municipality_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_MUNICIPALITY_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(apl_municipality_code);
      }
      if (apl_pat_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_PAT_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(apl_pat_code);
      }
      if (apl_aob_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_AOB_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(apl_aob_code);
      }
      if (apl_pat_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_PAT_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(apl_pat_nr);
      }
      if (apl_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(apl_date_from);
      }
      if (apl_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(apl_date_to);
      }
      if (apl_ads_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"APL_ADS_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(apl_ads_id);
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
      answer = answer +" WHERE  APL_ID = ? ";
      updateStatementPart.addStatementParam(apl_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisAdrPatLrsDAO\":{\"apl_id\": \""+getApl_id()+"\", \"apl_municipality_code\": \""+getApl_municipality_code()+"\", \"apl_pat_code\": \""+getApl_pat_code()+"\", \"apl_aob_code\": \""+getApl_aob_code()+"\", \"apl_pat_nr\": \""+getApl_pat_nr()+"\", \"apl_date_from\": \""+getApl_date_from()+"\", \"apl_date_to\": \""+getApl_date_to()+"\", \"apl_ads_id\": \""+getApl_ads_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}