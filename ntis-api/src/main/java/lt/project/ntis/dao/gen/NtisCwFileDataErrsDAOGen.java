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

public class NtisCwFileDataErrsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_CW_FILE_DATA_ERRS.CWFDE_ID";
   private Double cwfde_id;
   private String cwfde_type;
   private String cwfde_level;
   private Double cwfde_rec_nr;
   private Double cwfde_column_nr;
   private String cwfde_column_name;
   private String cwfde_column_value;
   private String cwfde_msg_code;
   private String cwfde_msg_text;
   private Double cwfde_cwf_id;
   private Double cwfde_cwfd_id;
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

   protected boolean cwfde_id_changed;
   protected boolean cwfde_type_changed;
   protected boolean cwfde_level_changed;
   protected boolean cwfde_rec_nr_changed;
   protected boolean cwfde_column_nr_changed;
   protected boolean cwfde_column_name_changed;
   protected boolean cwfde_column_value_changed;
   protected boolean cwfde_msg_code_changed;
   protected boolean cwfde_msg_text_changed;
   protected boolean cwfde_cwf_id_changed;
   protected boolean cwfde_cwfd_id_changed;
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
   public NtisCwFileDataErrsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisCwFileDataErrsDAOGen(Double cwfde_id, String cwfde_type, String cwfde_level, Double cwfde_rec_nr, Double cwfde_column_nr, String cwfde_column_name, String cwfde_column_value, String cwfde_msg_code, String cwfde_msg_text, Double cwfde_cwf_id, Double cwfde_cwfd_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.cwfde_id = cwfde_id;
      this.cwfde_type = cwfde_type;
      this.cwfde_level = cwfde_level;
      this.cwfde_rec_nr = cwfde_rec_nr;
      this.cwfde_column_nr = cwfde_column_nr;
      this.cwfde_column_name = cwfde_column_name;
      this.cwfde_column_value = cwfde_column_value;
      this.cwfde_msg_code = cwfde_msg_code;
      this.cwfde_msg_text = cwfde_msg_text;
      this.cwfde_cwf_id = cwfde_cwf_id;
      this.cwfde_cwfd_id = cwfde_cwfd_id;
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
   public void copyValues(NtisCwFileDataErrsDAOGen obj) {
      this.setCwfde_id(obj.getCwfde_id());
      this.setCwfde_type(obj.getCwfde_type());
      this.setCwfde_level(obj.getCwfde_level());
      this.setCwfde_rec_nr(obj.getCwfde_rec_nr());
      this.setCwfde_column_nr(obj.getCwfde_column_nr());
      this.setCwfde_column_name(obj.getCwfde_column_name());
      this.setCwfde_column_value(obj.getCwfde_column_value());
      this.setCwfde_msg_code(obj.getCwfde_msg_code());
      this.setCwfde_msg_text(obj.getCwfde_msg_text());
      this.setCwfde_cwf_id(obj.getCwfde_cwf_id());
      this.setCwfde_cwfd_id(obj.getCwfde_cwfd_id());
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
      this.cwfde_id_changed = false;
      this.cwfde_type_changed = false;
      this.cwfde_level_changed = false;
      this.cwfde_rec_nr_changed = false;
      this.cwfde_column_nr_changed = false;
      this.cwfde_column_name_changed = false;
      this.cwfde_column_value_changed = false;
      this.cwfde_msg_code_changed = false;
      this.cwfde_msg_text_changed = false;
      this.cwfde_cwf_id_changed = false;
      this.cwfde_cwfd_id_changed = false;
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
   public void setCwfde_id(Double cwfde_id) {
      if (this.isForceUpdate() || (this.cwfde_id != null && !this.cwfde_id.equals(cwfde_id)) || (cwfde_id != null && !cwfde_id.equals(this.cwfde_id))){
         this.cwfde_id_changed = true; 
         this.record_changed = true;
         this.cwfde_id = cwfde_id;
      }
   }
   public Double getCwfde_id() {
      return this.cwfde_id;
   }
   public void setCwfde_type(String cwfde_type) {
      if (this.isForceUpdate() || (this.cwfde_type != null && !this.cwfde_type.equals(cwfde_type)) || (cwfde_type != null && !cwfde_type.equals(this.cwfde_type))){
         this.cwfde_type_changed = true; 
         this.record_changed = true;
         this.cwfde_type = cwfde_type;
      }
   }
   public String getCwfde_type() {
      return this.cwfde_type;
   }
   public void setCwfde_level(String cwfde_level) {
      if (this.isForceUpdate() || (this.cwfde_level != null && !this.cwfde_level.equals(cwfde_level)) || (cwfde_level != null && !cwfde_level.equals(this.cwfde_level))){
         this.cwfde_level_changed = true; 
         this.record_changed = true;
         this.cwfde_level = cwfde_level;
      }
   }
   public String getCwfde_level() {
      return this.cwfde_level;
   }
   public void setCwfde_rec_nr(Double cwfde_rec_nr) {
      if (this.isForceUpdate() || (this.cwfde_rec_nr != null && !this.cwfde_rec_nr.equals(cwfde_rec_nr)) || (cwfde_rec_nr != null && !cwfde_rec_nr.equals(this.cwfde_rec_nr))){
         this.cwfde_rec_nr_changed = true; 
         this.record_changed = true;
         this.cwfde_rec_nr = cwfde_rec_nr;
      }
   }
   public Double getCwfde_rec_nr() {
      return this.cwfde_rec_nr;
   }
   public void setCwfde_column_nr(Double cwfde_column_nr) {
      if (this.isForceUpdate() || (this.cwfde_column_nr != null && !this.cwfde_column_nr.equals(cwfde_column_nr)) || (cwfde_column_nr != null && !cwfde_column_nr.equals(this.cwfde_column_nr))){
         this.cwfde_column_nr_changed = true; 
         this.record_changed = true;
         this.cwfde_column_nr = cwfde_column_nr;
      }
   }
   public Double getCwfde_column_nr() {
      return this.cwfde_column_nr;
   }
   public void setCwfde_column_name(String cwfde_column_name) {
      if (this.isForceUpdate() || (this.cwfde_column_name != null && !this.cwfde_column_name.equals(cwfde_column_name)) || (cwfde_column_name != null && !cwfde_column_name.equals(this.cwfde_column_name))){
         this.cwfde_column_name_changed = true; 
         this.record_changed = true;
         this.cwfde_column_name = cwfde_column_name;
      }
   }
   public String getCwfde_column_name() {
      return this.cwfde_column_name;
   }
   public void setCwfde_column_value(String cwfde_column_value) {
      if (this.isForceUpdate() || (this.cwfde_column_value != null && !this.cwfde_column_value.equals(cwfde_column_value)) || (cwfde_column_value != null && !cwfde_column_value.equals(this.cwfde_column_value))){
         this.cwfde_column_value_changed = true; 
         this.record_changed = true;
         this.cwfde_column_value = cwfde_column_value;
      }
   }
   public String getCwfde_column_value() {
      return this.cwfde_column_value;
   }
   public void setCwfde_msg_code(String cwfde_msg_code) {
      if (this.isForceUpdate() || (this.cwfde_msg_code != null && !this.cwfde_msg_code.equals(cwfde_msg_code)) || (cwfde_msg_code != null && !cwfde_msg_code.equals(this.cwfde_msg_code))){
         this.cwfde_msg_code_changed = true; 
         this.record_changed = true;
         this.cwfde_msg_code = cwfde_msg_code;
      }
   }
   public String getCwfde_msg_code() {
      return this.cwfde_msg_code;
   }
   public void setCwfde_msg_text(String cwfde_msg_text) {
      if (this.isForceUpdate() || (this.cwfde_msg_text != null && !this.cwfde_msg_text.equals(cwfde_msg_text)) || (cwfde_msg_text != null && !cwfde_msg_text.equals(this.cwfde_msg_text))){
         this.cwfde_msg_text_changed = true; 
         this.record_changed = true;
         this.cwfde_msg_text = cwfde_msg_text;
      }
   }
   public String getCwfde_msg_text() {
      return this.cwfde_msg_text;
   }
   public void setCwfde_cwf_id(Double cwfde_cwf_id) {
      if (this.isForceUpdate() || (this.cwfde_cwf_id != null && !this.cwfde_cwf_id.equals(cwfde_cwf_id)) || (cwfde_cwf_id != null && !cwfde_cwf_id.equals(this.cwfde_cwf_id))){
         this.cwfde_cwf_id_changed = true; 
         this.record_changed = true;
         this.cwfde_cwf_id = cwfde_cwf_id;
      }
   }
   public Double getCwfde_cwf_id() {
      return this.cwfde_cwf_id;
   }
   public void setCwfde_cwfd_id(Double cwfde_cwfd_id) {
      if (this.isForceUpdate() || (this.cwfde_cwfd_id != null && !this.cwfde_cwfd_id.equals(cwfde_cwfd_id)) || (cwfde_cwfd_id != null && !cwfde_cwfd_id.equals(this.cwfde_cwfd_id))){
         this.cwfde_cwfd_id_changed = true; 
         this.record_changed = true;
         this.cwfde_cwfd_id = cwfde_cwfd_id;
      }
   }
   public Double getCwfde_cwfd_id() {
      return this.cwfde_cwfd_id;
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
            this.cwfde_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.cwfde_id;
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
      if (cwfde_type_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_type== null || EMPTY_STRING.equals(cwfde_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (cwfde_type!= null && cwfde_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (cwfde_level_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_level== null || EMPTY_STRING.equals(cwfde_level)) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_LEVEL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (cwfde_level!= null && cwfde_level.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_LEVEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (cwfde_rec_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_rec_nr!= null && (""+cwfde_rec_nr.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_REC_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (cwfde_column_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_column_nr!= null && (""+cwfde_column_nr.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_COLUMN_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (cwfde_column_name_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_column_name!= null && cwfde_column_name.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_COLUMN_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (cwfde_column_value_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_column_value!= null && cwfde_column_value.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_COLUMN_VALUE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (cwfde_msg_code_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_msg_code== null || EMPTY_STRING.equals(cwfde_msg_code)) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_MSG_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (cwfde_msg_code!= null && cwfde_msg_code.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_MSG_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (cwfde_msg_text_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_msg_text== null || EMPTY_STRING.equals(cwfde_msg_text)) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_MSG_TEXT", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (cwfde_msg_text!= null && cwfde_msg_text.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_MSG_TEXT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (cwfde_cwf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_cwf_id!= null && (""+cwfde_cwf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_CWF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (cwfde_cwfd_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfde_cwfd_id!= null && (""+cwfde_cwfd_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "CWFDE_CWFD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA_ERRS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_CW_FILE_DATA_ERRS SET ";
      boolean changedExists = false;      if (cwfde_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_type);
      }
      if (cwfde_level_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_LEVEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_level);
      }
      if (cwfde_rec_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_REC_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_rec_nr);
      }
      if (cwfde_column_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_COLUMN_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_column_nr);
      }
      if (cwfde_column_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_COLUMN_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_column_name);
      }
      if (cwfde_column_value_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_COLUMN_VALUE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_column_value);
      }
      if (cwfde_msg_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_MSG_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_msg_code);
      }
      if (cwfde_msg_text_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_MSG_TEXT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_msg_text);
      }
      if (cwfde_cwf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_CWF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_cwf_id);
      }
      if (cwfde_cwfd_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFDE_CWFD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfde_cwfd_id);
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
      answer = answer +" WHERE  CWFDE_ID = ? ";
      updateStatementPart.addStatementParam(cwfde_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisCwFileDataErrsDAO\":{\"cwfde_id\": \""+getCwfde_id()+"\", \"cwfde_type\": \""+getCwfde_type()+"\", \"cwfde_level\": \""+getCwfde_level()+"\", \"cwfde_rec_nr\": \""+getCwfde_rec_nr()+"\", \"cwfde_column_nr\": \""+getCwfde_column_nr()+"\", \"cwfde_column_name\": \""+getCwfde_column_name()+"\", \"cwfde_column_value\": \""+getCwfde_column_value()+"\", \"cwfde_msg_code\": \""+getCwfde_msg_code()+"\", \"cwfde_msg_text\": \""+getCwfde_msg_text()+"\", \"cwfde_cwf_id\": \""+getCwfde_cwf_id()+"\", \"cwfde_cwfd_id\": \""+getCwfde_cwfd_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}