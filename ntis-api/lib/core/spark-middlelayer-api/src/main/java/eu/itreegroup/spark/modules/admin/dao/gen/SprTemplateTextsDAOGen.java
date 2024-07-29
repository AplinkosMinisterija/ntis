package eu.itreegroup.spark.modules.admin.dao.gen;

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

public class SprTemplateTextsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_TEMPLATE_TEXTS.TMT_ID";
   private Double tmt_id;
   private String tmt_code;
   private String tmt_name;
   private String tmt_description;
   private String tmt_lang;
   private String tmt_text;
   private Date tmt_date_from;
   private Date tmt_date_to;
   private Double tmt_tml_id;
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

   protected boolean tmt_id_changed;
   protected boolean tmt_code_changed;
   protected boolean tmt_name_changed;
   protected boolean tmt_description_changed;
   protected boolean tmt_lang_changed;
   protected boolean tmt_text_changed;
   protected boolean tmt_date_from_changed;
   protected boolean tmt_date_to_changed;
   protected boolean tmt_tml_id_changed;
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
   public SprTemplateTextsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprTemplateTextsDAOGen(Double tmt_id, String tmt_code, String tmt_name, String tmt_description, String tmt_lang, String tmt_text, Date tmt_date_from, Date tmt_date_to, Double tmt_tml_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.tmt_id = tmt_id;
      this.tmt_code = tmt_code;
      this.tmt_name = tmt_name;
      this.tmt_description = tmt_description;
      this.tmt_lang = tmt_lang;
      this.tmt_text = tmt_text;
      this.tmt_date_from = tmt_date_from;
      this.tmt_date_to = tmt_date_to;
      this.tmt_tml_id = tmt_tml_id;
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
   public void copyValues(SprTemplateTextsDAOGen obj) {
      this.setTmt_id(obj.getTmt_id());
      this.setTmt_code(obj.getTmt_code());
      this.setTmt_name(obj.getTmt_name());
      this.setTmt_description(obj.getTmt_description());
      this.setTmt_lang(obj.getTmt_lang());
      this.setTmt_text(obj.getTmt_text());
      this.setTmt_date_from(obj.getTmt_date_from());
      this.setTmt_date_to(obj.getTmt_date_to());
      this.setTmt_tml_id(obj.getTmt_tml_id());
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
      this.tmt_id_changed = false;
      this.tmt_code_changed = false;
      this.tmt_name_changed = false;
      this.tmt_description_changed = false;
      this.tmt_lang_changed = false;
      this.tmt_text_changed = false;
      this.tmt_date_from_changed = false;
      this.tmt_date_to_changed = false;
      this.tmt_tml_id_changed = false;
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
   public void setTmt_id(Double tmt_id) {
      if (this.isForceUpdate() || 
               (this.tmt_id != null && !this.tmt_id.equals(tmt_id))  ||
               (tmt_id != null && !tmt_id.equals(this.tmt_id)) ){
         this.tmt_id_changed = true; 
         this.record_changed = true;
         this.tmt_id = tmt_id;
      }
   }
   public Double getTmt_id() {
      return this.tmt_id;
   }
   public void setTmt_code(String tmt_code) {
      if (this.isForceUpdate() || 
               (this.tmt_code != null && !this.tmt_code.equals(tmt_code))  ||
               (tmt_code != null && !tmt_code.equals(this.tmt_code)) ){
         this.tmt_code_changed = true; 
         this.record_changed = true;
         this.tmt_code = tmt_code;
      }
   }
   public String getTmt_code() {
      return this.tmt_code;
   }
   public void setTmt_name(String tmt_name) {
      if (this.isForceUpdate() || 
               (this.tmt_name != null && !this.tmt_name.equals(tmt_name))  ||
               (tmt_name != null && !tmt_name.equals(this.tmt_name)) ){
         this.tmt_name_changed = true; 
         this.record_changed = true;
         this.tmt_name = tmt_name;
      }
   }
   public String getTmt_name() {
      return this.tmt_name;
   }
   public void setTmt_description(String tmt_description) {
      if (this.isForceUpdate() || 
               (this.tmt_description != null && !this.tmt_description.equals(tmt_description))  ||
               (tmt_description != null && !tmt_description.equals(this.tmt_description)) ){
         this.tmt_description_changed = true; 
         this.record_changed = true;
         this.tmt_description = tmt_description;
      }
   }
   public String getTmt_description() {
      return this.tmt_description;
   }
   public void setTmt_lang(String tmt_lang) {
      if (this.isForceUpdate() || 
               (this.tmt_lang != null && !this.tmt_lang.equals(tmt_lang))  ||
               (tmt_lang != null && !tmt_lang.equals(this.tmt_lang)) ){
         this.tmt_lang_changed = true; 
         this.record_changed = true;
         this.tmt_lang = tmt_lang;
      }
   }
   public String getTmt_lang() {
      return this.tmt_lang;
   }
   public void setTmt_text(String tmt_text) {
      if (this.isForceUpdate() || 
               (this.tmt_text != null && !this.tmt_text.equals(tmt_text))  ||
               (tmt_text != null && !tmt_text.equals(this.tmt_text)) ){
         this.tmt_text_changed = true; 
         this.record_changed = true;
         this.tmt_text = tmt_text;
      }
   }
   public String getTmt_text() {
      return this.tmt_text;
   }
   public void setTmt_date_from(Date tmt_date_from) {
      if (this.isForceUpdate() || 
               (this.tmt_date_from != null && (tmt_date_from == null ||this.tmt_date_from.compareTo(tmt_date_from) != 0 )) ||
               (tmt_date_from != null && (this.tmt_date_from == null ||tmt_date_from.compareTo(this.tmt_date_from) != 0 ))){
         this.tmt_date_from_changed = true; 
         this.record_changed = true;
         this.tmt_date_from = tmt_date_from;
      }
   }
   public Date getTmt_date_from() {
      return this.tmt_date_from;
   }
   public void setTmt_date_to(Date tmt_date_to) {
      if (this.isForceUpdate() || 
               (this.tmt_date_to != null && (tmt_date_to == null ||this.tmt_date_to.compareTo(tmt_date_to) != 0 )) ||
               (tmt_date_to != null && (this.tmt_date_to == null ||tmt_date_to.compareTo(this.tmt_date_to) != 0 ))){
         this.tmt_date_to_changed = true; 
         this.record_changed = true;
         this.tmt_date_to = tmt_date_to;
      }
   }
   public Date getTmt_date_to() {
      return this.tmt_date_to;
   }
   public void setTmt_tml_id(Double tmt_tml_id) {
      if (this.isForceUpdate() || 
               (this.tmt_tml_id != null && !this.tmt_tml_id.equals(tmt_tml_id))  ||
               (tmt_tml_id != null && !tmt_tml_id.equals(this.tmt_tml_id)) ){
         this.tmt_tml_id_changed = true; 
         this.record_changed = true;
         this.tmt_tml_id = tmt_tml_id;
      }
   }
   public Double getTmt_tml_id() {
      return this.tmt_tml_id;
   }
   public void setRec_version(Double rec_version) {
      if (this.isForceUpdate() || 
               (this.rec_version != null && !this.rec_version.equals(rec_version))  ||
               (rec_version != null && !rec_version.equals(this.rec_version)) ){
         this.rec_version_changed = true; 
         this.record_changed = true;
         this.rec_version = rec_version;
      }
   }
   public Double getRec_version() {
      return this.rec_version;
   }
   public void setRec_create_timestamp(Date rec_create_timestamp) {
      if (this.isForceUpdate() || 
               (this.rec_create_timestamp != null && (rec_create_timestamp == null ||this.rec_create_timestamp.compareTo(rec_create_timestamp) != 0 )) ||
               (rec_create_timestamp != null && (this.rec_create_timestamp == null ||rec_create_timestamp.compareTo(this.rec_create_timestamp) != 0 ))){
         this.rec_create_timestamp_changed = true; 
         this.record_changed = true;
         this.rec_create_timestamp = rec_create_timestamp;
      }
   }
   public Date getRec_create_timestamp() {
      return this.rec_create_timestamp;
   }
   public void setRec_userid(String rec_userid) {
      if (this.isForceUpdate() || 
               (this.rec_userid != null && !this.rec_userid.equals(rec_userid))  ||
               (rec_userid != null && !rec_userid.equals(this.rec_userid)) ){
         this.rec_userid_changed = true; 
         this.record_changed = true;
         this.rec_userid = rec_userid;
      }
   }
   public String getRec_userid() {
      return this.rec_userid;
   }
   public void setRec_timestamp(Date rec_timestamp) {
      if (this.isForceUpdate() || 
               (this.rec_timestamp != null && (rec_timestamp == null ||this.rec_timestamp.compareTo(rec_timestamp) != 0 )) ||
               (rec_timestamp != null && (this.rec_timestamp == null ||rec_timestamp.compareTo(this.rec_timestamp) != 0 ))){
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
      if (this.isForceUpdate() || 
               (this.n01 != null && !this.n01.equals(n01))  ||
               (n01 != null && !n01.equals(this.n01)) ){
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
      if (this.isForceUpdate() || 
               (this.n02 != null && !this.n02.equals(n02))  ||
               (n02 != null && !n02.equals(this.n02)) ){
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
      if (this.isForceUpdate() || 
               (this.n03 != null && !this.n03.equals(n03))  ||
               (n03 != null && !n03.equals(this.n03)) ){
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
      if (this.isForceUpdate() || 
               (this.n04 != null && !this.n04.equals(n04))  ||
               (n04 != null && !n04.equals(this.n04)) ){
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
      if (this.isForceUpdate() || 
               (this.n05 != null && !this.n05.equals(n05))  ||
               (n05 != null && !n05.equals(this.n05)) ){
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
      if (this.isForceUpdate() || 
               (this.c01 != null && !this.c01.equals(c01))  ||
               (c01 != null && !c01.equals(this.c01)) ){
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
      if (this.isForceUpdate() || 
               (this.c02 != null && !this.c02.equals(c02))  ||
               (c02 != null && !c02.equals(this.c02)) ){
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
      if (this.isForceUpdate() || 
               (this.c03 != null && !this.c03.equals(c03))  ||
               (c03 != null && !c03.equals(this.c03)) ){
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
      if (this.isForceUpdate() || 
               (this.c04 != null && !this.c04.equals(c04))  ||
               (c04 != null && !c04.equals(this.c04)) ){
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
      if (this.isForceUpdate() || 
               (this.c05 != null && !this.c05.equals(c05))  ||
               (c05 != null && !c05.equals(this.c05)) ){
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
      if (this.isForceUpdate() || 
               (this.d01 != null && (d01 == null ||this.d01.compareTo(d01) != 0 )) ||
               (d01 != null && (this.d01 == null ||d01.compareTo(this.d01) != 0 ))){
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
      if (this.isForceUpdate() || 
               (this.d02 != null && (d02 == null ||this.d02.compareTo(d02) != 0 )) ||
               (d02 != null && (this.d02 == null ||d02.compareTo(this.d02) != 0 ))){
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
      if (this.isForceUpdate() || 
               (this.d03 != null && (d03 == null ||this.d03.compareTo(d03) != 0 )) ||
               (d03 != null && (this.d03 == null ||d03.compareTo(this.d03) != 0 ))){
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
      if (this.isForceUpdate() || 
               (this.d04 != null && (d04 == null ||this.d04.compareTo(d04) != 0 )) ||
               (d04 != null && (this.d04 == null ||d04.compareTo(this.d04) != 0 ))){
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
      if (this.isForceUpdate() || 
               (this.d05 != null && (d05 == null ||this.d05.compareTo(d05) != 0 )) ||
               (d05 != null && (this.d05 == null ||d05.compareTo(this.d05) != 0 ))){
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
            this.tmt_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.tmt_id;
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
      if (tmt_code_changed || operation == Utils.OPERATION_INSERT) {
         if (tmt_code!= null && tmt_code.length()>50) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "TMT_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (tmt_name_changed || operation == Utils.OPERATION_INSERT) {
         if (tmt_name!= null && tmt_name.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "TMT_NAME", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (tmt_description_changed || operation == Utils.OPERATION_INSERT) {
         if (tmt_description!= null && tmt_description.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "TMT_DESCRIPTION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (tmt_lang_changed || operation == Utils.OPERATION_INSERT) {
         if (tmt_lang!= null && tmt_lang.length()>240) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "TMT_LANG", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"240"));
         }
      }
      if (tmt_text_changed || operation == Utils.OPERATION_INSERT) {
         if (tmt_text!= null && tmt_text.length()>2000000000) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "TMT_TEXT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000000000"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_TEMPLATE_TEXTS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_TEMPLATE_TEXTS SET ";
      boolean changedExists = false;      if (tmt_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_code);
      }
      if (tmt_name_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_NAME = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_name);
      }
      if (tmt_description_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_DESCRIPTION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_description);
      }
      if (tmt_lang_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_LANG = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_lang);
      }
      if (tmt_text_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_TEXT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_text);
      }
       if (tmt_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_date_from);
      }
      if (tmt_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_date_to);
      }
      if (tmt_tml_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"TMT_TML_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(tmt_tml_id);
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
         updateStatementPart.addStatementParam(d01);
      }
      if (d02_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D02 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d02);
      }
      if (d03_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D03 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d03);
      }
      if (d04_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D04 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d04);
      }
      if (d05_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"D05 = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(d05);
      }
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_version = rec_version+1 ";
      changedExists = true;
      answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"rec_timestamp = ? ";
      changedExists = true;
      updateStatementPart.addStatementParam(new Timestamp(System.currentTimeMillis()));
      changedExists = true;
      answer = answer +  (changedExists?  COMMA : EMPTY_STRING)+"rec_userid = ? ";
      updateStatementPart.addStatementParam(userName);
      answer = answer +" WHERE  TMT_ID = ? ";
      updateStatementPart.addStatementParam(tmt_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprTemplateTextsDAO\":{\"tmt_id\": \""+getTmt_id()+"\", \"tmt_code\": \""+getTmt_code()+"\", \"tmt_name\": \""+getTmt_name()+"\", \"tmt_description\": \""+getTmt_description()+"\", \"tmt_lang\": \""+getTmt_lang()+"\", \"tmt_text\": \""+getTmt_text()+"\", \"tmt_date_from\": \""+getTmt_date_from()+"\", \"tmt_date_to\": \""+getTmt_date_to()+"\", \"tmt_tml_id\": \""+getTmt_tml_id()+"\",  \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}