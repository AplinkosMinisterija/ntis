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

public class NtisOrderFileDataErrsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_ORDER_FILE_DATA_ERRS.ORFDE_ID";
   private Double orfde_id;
   private String orfde_type;
   private String orfde_level;
   private Double orfde_rec_nr;
   private Double orfde_column_nr;
   private String orfde_column_name;
   private String orfde_column_value;
   private String orfde_msg_code;
   private String orfde_msg_text;
   private Double orfde_orf_id;
   private Double orfde_orfd_id;
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

   protected boolean orfde_id_changed;
   protected boolean orfde_type_changed;
   protected boolean orfde_level_changed;
   protected boolean orfde_rec_nr_changed;
   protected boolean orfde_column_nr_changed;
   protected boolean orfde_column_name_changed;
   protected boolean orfde_column_value_changed;
   protected boolean orfde_msg_code_changed;
   protected boolean orfde_msg_text_changed;
   protected boolean orfde_orf_id_changed;
   protected boolean orfde_orfd_id_changed;
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
   public NtisOrderFileDataErrsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisOrderFileDataErrsDAOGen(Double orfde_id, String orfde_type, String orfde_level, Double orfde_rec_nr, Double orfde_column_nr, String orfde_column_name, String orfde_column_value, String orfde_msg_code, String orfde_msg_text, Double orfde_orf_id, Double orfde_orfd_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.orfde_id = orfde_id;
      this.orfde_type = orfde_type;
      this.orfde_level = orfde_level;
      this.orfde_rec_nr = orfde_rec_nr;
      this.orfde_column_nr = orfde_column_nr;
      this.orfde_column_name = orfde_column_name;
      this.orfde_column_value = orfde_column_value;
      this.orfde_msg_code = orfde_msg_code;
      this.orfde_msg_text = orfde_msg_text;
      this.orfde_orf_id = orfde_orf_id;
      this.orfde_orfd_id = orfde_orfd_id;
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
   public void copyValues(NtisOrderFileDataErrsDAOGen obj) {
      this.setOrfde_id(obj.getOrfde_id());
      this.setOrfde_type(obj.getOrfde_type());
      this.setOrfde_level(obj.getOrfde_level());
      this.setOrfde_rec_nr(obj.getOrfde_rec_nr());
      this.setOrfde_column_nr(obj.getOrfde_column_nr());
      this.setOrfde_column_name(obj.getOrfde_column_name());
      this.setOrfde_column_value(obj.getOrfde_column_value());
      this.setOrfde_msg_code(obj.getOrfde_msg_code());
      this.setOrfde_msg_text(obj.getOrfde_msg_text());
      this.setOrfde_orf_id(obj.getOrfde_orf_id());
      this.setOrfde_orfd_id(obj.getOrfde_orfd_id());
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
      this.orfde_id_changed = false;
      this.orfde_type_changed = false;
      this.orfde_level_changed = false;
      this.orfde_rec_nr_changed = false;
      this.orfde_column_nr_changed = false;
      this.orfde_column_name_changed = false;
      this.orfde_column_value_changed = false;
      this.orfde_msg_code_changed = false;
      this.orfde_msg_text_changed = false;
      this.orfde_orf_id_changed = false;
      this.orfde_orfd_id_changed = false;
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
   public void setOrfde_id(Double orfde_id) {
      if (this.isForceUpdate() || (this.orfde_id != null && !this.orfde_id.equals(orfde_id)) || (orfde_id != null && !orfde_id.equals(this.orfde_id))){
         this.orfde_id_changed = true; 
         this.record_changed = true;
         this.orfde_id = orfde_id;
      }
   }
   public Double getOrfde_id() {
      return this.orfde_id;
   }
   public void setOrfde_type(String orfde_type) {
      if (this.isForceUpdate() || (this.orfde_type != null && !this.orfde_type.equals(orfde_type)) || (orfde_type != null && !orfde_type.equals(this.orfde_type))){
         this.orfde_type_changed = true; 
         this.record_changed = true;
         this.orfde_type = orfde_type;
      }
   }
   public String getOrfde_type() {
      return this.orfde_type;
   }
   public void setOrfde_level(String orfde_level) {
      if (this.isForceUpdate() || (this.orfde_level != null && !this.orfde_level.equals(orfde_level)) || (orfde_level != null && !orfde_level.equals(this.orfde_level))){
         this.orfde_level_changed = true; 
         this.record_changed = true;
         this.orfde_level = orfde_level;
      }
   }
   public String getOrfde_level() {
      return this.orfde_level;
   }
   public void setOrfde_rec_nr(Double orfde_rec_nr) {
      if (this.isForceUpdate() || (this.orfde_rec_nr != null && !this.orfde_rec_nr.equals(orfde_rec_nr)) || (orfde_rec_nr != null && !orfde_rec_nr.equals(this.orfde_rec_nr))){
         this.orfde_rec_nr_changed = true; 
         this.record_changed = true;
         this.orfde_rec_nr = orfde_rec_nr;
      }
   }
   public Double getOrfde_rec_nr() {
      return this.orfde_rec_nr;
   }
   public void setOrfde_column_nr(Double orfde_column_nr) {
      if (this.isForceUpdate() || (this.orfde_column_nr != null && !this.orfde_column_nr.equals(orfde_column_nr)) || (orfde_column_nr != null && !orfde_column_nr.equals(this.orfde_column_nr))){
         this.orfde_column_nr_changed = true; 
         this.record_changed = true;
         this.orfde_column_nr = orfde_column_nr;
      }
   }
   public Double getOrfde_column_nr() {
      return this.orfde_column_nr;
   }
   public void setOrfde_column_name(String orfde_column_name) {
      if (this.isForceUpdate() || (this.orfde_column_name != null && !this.orfde_column_name.equals(orfde_column_name)) || (orfde_column_name != null && !orfde_column_name.equals(this.orfde_column_name))){
         this.orfde_column_name_changed = true; 
         this.record_changed = true;
         this.orfde_column_name = orfde_column_name;
      }
   }
   public String getOrfde_column_name() {
      return this.orfde_column_name;
   }
   public void setOrfde_column_value(String orfde_column_value) {
      if (this.isForceUpdate() || (this.orfde_column_value != null && !this.orfde_column_value.equals(orfde_column_value)) || (orfde_column_value != null && !orfde_column_value.equals(this.orfde_column_value))){
         this.orfde_column_value_changed = true; 
         this.record_changed = true;
         this.orfde_column_value = orfde_column_value;
      }
   }
   public String getOrfde_column_value() {
      return this.orfde_column_value;
   }
   public void setOrfde_msg_code(String orfde_msg_code) {
      if (this.isForceUpdate() || (this.orfde_msg_code != null && !this.orfde_msg_code.equals(orfde_msg_code)) || (orfde_msg_code != null && !orfde_msg_code.equals(this.orfde_msg_code))){
         this.orfde_msg_code_changed = true; 
         this.record_changed = true;
         this.orfde_msg_code = orfde_msg_code;
      }
   }
   public String getOrfde_msg_code() {
      return this.orfde_msg_code;
   }
   public void setOrfde_msg_text(String orfde_msg_text) {
      if (this.isForceUpdate() || (this.orfde_msg_text != null && !this.orfde_msg_text.equals(orfde_msg_text)) || (orfde_msg_text != null && !orfde_msg_text.equals(this.orfde_msg_text))){
         this.orfde_msg_text_changed = true; 
         this.record_changed = true;
         this.orfde_msg_text = orfde_msg_text;
      }
   }
   public String getOrfde_msg_text() {
      return this.orfde_msg_text;
   }
   public void setOrfde_orf_id(Double orfde_orf_id) {
      if (this.isForceUpdate() || (this.orfde_orf_id != null && !this.orfde_orf_id.equals(orfde_orf_id)) || (orfde_orf_id != null && !orfde_orf_id.equals(this.orfde_orf_id))){
         this.orfde_orf_id_changed = true; 
         this.record_changed = true;
         this.orfde_orf_id = orfde_orf_id;
      }
   }
   public Double getOrfde_orf_id() {
      return this.orfde_orf_id;
   }
   public void setOrfde_orfd_id(Double orfde_orfd_id) {
      if (this.isForceUpdate() || (this.orfde_orfd_id != null && !this.orfde_orfd_id.equals(orfde_orfd_id)) || (orfde_orfd_id != null && !orfde_orfd_id.equals(this.orfde_orfd_id))){
         this.orfde_orfd_id_changed = true; 
         this.record_changed = true;
         this.orfde_orfd_id = orfde_orfd_id;
      }
   }
   public Double getOrfde_orfd_id() {
      return this.orfde_orfd_id;
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
            this.orfde_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.orfde_id;
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
      if (orfde_type_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_type== null || EMPTY_STRING.equals(orfde_type)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (orfde_type!= null && orfde_type.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (orfde_level_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_level== null || EMPTY_STRING.equals(orfde_level)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_LEVEL", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (orfde_level!= null && orfde_level.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_LEVEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (orfde_rec_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_rec_nr!= null && (""+orfde_rec_nr.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_REC_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (orfde_column_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_column_nr!= null && (""+orfde_column_nr.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_COLUMN_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (orfde_column_name_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_column_name!= null && orfde_column_name.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_COLUMN_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (orfde_column_value_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_column_value!= null && orfde_column_value.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_COLUMN_VALUE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfde_msg_code_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_msg_code== null || EMPTY_STRING.equals(orfde_msg_code)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_MSG_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (orfde_msg_code!= null && orfde_msg_code.length()>50) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_MSG_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (orfde_msg_text_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_msg_text== null || EMPTY_STRING.equals(orfde_msg_text)) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_MSG_TEXT", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (orfde_msg_text!= null && orfde_msg_text.length()>2147483647) {
               throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_MSG_TEXT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
            }
         }
      }
      if (orfde_orf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_orf_id!= null && (""+orfde_orf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_ORF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (orfde_orfd_id_changed || operation == Utils.OPERATION_INSERT) {
         if (orfde_orfd_id!= null && (""+orfde_orfd_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "ORFDE_ORFD_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA_ERRS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_ORDER_FILE_DATA_ERRS SET ";
      boolean changedExists = false;      if (orfde_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_type);
      }
      if (orfde_level_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_LEVEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_level);
      }
      if (orfde_rec_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_REC_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_rec_nr);
      }
      if (orfde_column_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_COLUMN_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_column_nr);
      }
      if (orfde_column_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_COLUMN_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_column_name);
      }
      if (orfde_column_value_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_COLUMN_VALUE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_column_value);
      }
      if (orfde_msg_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_MSG_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_msg_code);
      }
      if (orfde_msg_text_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_MSG_TEXT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_msg_text);
      }
      if (orfde_orf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_ORF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_orf_id);
      }
      if (orfde_orfd_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFDE_ORFD_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfde_orfd_id);
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
      answer = answer +" WHERE  ORFDE_ID = ? ";
      updateStatementPart.addStatementParam(orfde_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisOrderFileDataErrsDAO\":{\"orfde_id\": \""+getOrfde_id()+"\", \"orfde_type\": \""+getOrfde_type()+"\", \"orfde_level\": \""+getOrfde_level()+"\", \"orfde_rec_nr\": \""+getOrfde_rec_nr()+"\", \"orfde_column_nr\": \""+getOrfde_column_nr()+"\", \"orfde_column_name\": \""+getOrfde_column_name()+"\", \"orfde_column_value\": \""+getOrfde_column_value()+"\", \"orfde_msg_code\": \""+getOrfde_msg_code()+"\", \"orfde_msg_text\": \""+getOrfde_msg_text()+"\", \"orfde_orf_id\": \""+getOrfde_orf_id()+"\", \"orfde_orfd_id\": \""+getOrfde_orfd_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}