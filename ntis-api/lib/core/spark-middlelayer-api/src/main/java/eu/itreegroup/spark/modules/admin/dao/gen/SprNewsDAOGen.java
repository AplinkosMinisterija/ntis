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

public class SprNewsDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_NEWS.NW_ID";
   private Double nw_id;
   private String nw_type;
   private String nw_lang;
   private String nw_title;
   private String nw_summary;
   private String nw_text;
   private String nw_content_for_search;
   private Date nw_publication_date;
   private Date nw_date_from;
   private Date nw_date_to;
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

   protected boolean nw_id_changed;
   protected boolean nw_type_changed;
   protected boolean nw_lang_changed;
   protected boolean nw_title_changed;
   protected boolean nw_summary_changed;
   protected boolean nw_text_changed;
   protected boolean nw_content_for_search_changed;
   protected boolean nw_publication_date_changed;
   protected boolean nw_date_from_changed;
   protected boolean nw_date_to_changed;
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
   public SprNewsDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprNewsDAOGen(Double nw_id, String nw_type, String nw_lang, String nw_title, String nw_summary, String nw_text, String nw_content_for_search, Date nw_publication_date, Date nw_date_from, Date nw_date_to, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.nw_id = nw_id;
      this.nw_type = nw_type;
      this.nw_lang = nw_lang;
      this.nw_title = nw_title;
      this.nw_summary = nw_summary;
      this.nw_text = nw_text;
      this.nw_content_for_search = nw_content_for_search;
      this.nw_publication_date = nw_publication_date;
      this.nw_date_from = nw_date_from;
      this.nw_date_to = nw_date_to;
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
   public void copyValues(SprNewsDAOGen obj) {
      this.setNw_id(obj.getNw_id());
      this.setNw_type(obj.getNw_type());
      this.setNw_lang(obj.getNw_lang());
      this.setNw_title(obj.getNw_title());
      this.setNw_summary(obj.getNw_summary());
      this.setNw_text(obj.getNw_text());
      this.setNw_content_for_search(obj.getNw_content_for_search());
      this.setNw_publication_date(obj.getNw_publication_date());
      this.setNw_date_from(obj.getNw_date_from());
      this.setNw_date_to(obj.getNw_date_to());
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
      this.nw_id_changed = false;
      this.nw_type_changed = false;
      this.nw_lang_changed = false;
      this.nw_title_changed = false;
      this.nw_summary_changed = false;
      this.nw_text_changed = false;
      this.nw_content_for_search_changed = false;
      this.nw_publication_date_changed = false;
      this.nw_date_from_changed = false;
      this.nw_date_to_changed = false;
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
   public void setNw_id(Double nw_id) {
      if (this.isForceUpdate() || 
               (this.nw_id != null && !this.nw_id.equals(nw_id))  ||
               (nw_id != null && !nw_id.equals(this.nw_id)) ){
         this.nw_id_changed = true; 
         this.record_changed = true;
         this.nw_id = nw_id;
      }
   }
   public Double getNw_id() {
      return this.nw_id;
   }
   public void setNw_type(String nw_type) {
      if (this.isForceUpdate() || 
               (this.nw_type != null && !this.nw_type.equals(nw_type))  ||
               (nw_type != null && !nw_type.equals(this.nw_type)) ){
         this.nw_type_changed = true; 
         this.record_changed = true;
         this.nw_type = nw_type;
      }
   }
   public String getNw_type() {
      return this.nw_type;
   }
   public void setNw_lang(String nw_lang) {
      if (this.isForceUpdate() || 
               (this.nw_lang != null && !this.nw_lang.equals(nw_lang))  ||
               (nw_lang != null && !nw_lang.equals(this.nw_lang)) ){
         this.nw_lang_changed = true; 
         this.record_changed = true;
         this.nw_lang = nw_lang;
      }
   }
   public String getNw_lang() {
      return this.nw_lang;
   }
   public void setNw_title(String nw_title) {
      if (this.isForceUpdate() || 
               (this.nw_title != null && !this.nw_title.equals(nw_title))  ||
               (nw_title != null && !nw_title.equals(this.nw_title)) ){
         this.nw_title_changed = true; 
         this.record_changed = true;
         this.nw_title = nw_title;
      }
   }
   public String getNw_title() {
      return this.nw_title;
   }
   public void setNw_summary(String nw_summary) {
      if (this.isForceUpdate() || 
               (this.nw_summary != null && !this.nw_summary.equals(nw_summary))  ||
               (nw_summary != null && !nw_summary.equals(this.nw_summary)) ){
         this.nw_summary_changed = true; 
         this.record_changed = true;
         this.nw_summary = nw_summary;
      }
   }
   public String getNw_summary() {
      return this.nw_summary;
   }
   public void setNw_text(String nw_text) {
      if (this.isForceUpdate() || 
               (this.nw_text != null && !this.nw_text.equals(nw_text))  ||
               (nw_text != null && !nw_text.equals(this.nw_text)) ){
         this.nw_text_changed = true; 
         this.record_changed = true;
         this.nw_text = nw_text;
      }
   }
   public String getNw_text() {
      return this.nw_text;
   }
   public void setNw_content_for_search(String nw_content_for_search) {
      if (this.isForceUpdate() || 
               (this.nw_content_for_search != null && !this.nw_content_for_search.equals(nw_content_for_search))  ||
               (nw_content_for_search != null && !nw_content_for_search.equals(this.nw_content_for_search)) ){
         this.nw_content_for_search_changed = true; 
         this.record_changed = true;
         this.nw_content_for_search = nw_content_for_search;
      }
   }
   public String getNw_content_for_search() {
      return this.nw_content_for_search;
   }
   public void setNw_publication_date(Date nw_publication_date) {
      if (this.isForceUpdate() || 
               (this.nw_publication_date != null && (nw_publication_date == null ||this.nw_publication_date.compareTo(nw_publication_date) != 0 )) ||
               (nw_publication_date != null && (this.nw_publication_date == null ||nw_publication_date.compareTo(this.nw_publication_date) != 0 ))){
         this.nw_publication_date_changed = true; 
         this.record_changed = true;
         this.nw_publication_date = nw_publication_date;
      }
   }
   public Date getNw_publication_date() {
      return this.nw_publication_date;
   }
   public void setNw_date_from(Date nw_date_from) {
      if (this.isForceUpdate() || 
               (this.nw_date_from != null && (nw_date_from == null ||this.nw_date_from.compareTo(nw_date_from) != 0 )) ||
               (nw_date_from != null && (this.nw_date_from == null ||nw_date_from.compareTo(this.nw_date_from) != 0 ))){
         this.nw_date_from_changed = true; 
         this.record_changed = true;
         this.nw_date_from = nw_date_from;
      }
   }
   public Date getNw_date_from() {
      return this.nw_date_from;
   }
   public void setNw_date_to(Date nw_date_to) {
      if (this.isForceUpdate() || 
               (this.nw_date_to != null && (nw_date_to == null ||this.nw_date_to.compareTo(nw_date_to) != 0 )) ||
               (nw_date_to != null && (this.nw_date_to == null ||nw_date_to.compareTo(this.nw_date_to) != 0 ))){
         this.nw_date_to_changed = true; 
         this.record_changed = true;
         this.nw_date_to = nw_date_to;
      }
   }
   public Date getNw_date_to() {
      return this.nw_date_to;
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
            this.nw_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.nw_id;
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
      if (nw_type_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_type== null || EMPTY_STRING.equals(nw_type)) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_TYPE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (nw_type!= null && nw_type.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (nw_lang_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_lang== null || EMPTY_STRING.equals(nw_lang)) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_LANG", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (nw_lang!= null && nw_lang.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_LANG", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (nw_title_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_title== null || EMPTY_STRING.equals(nw_title)) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_TITLE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (nw_title!= null && nw_title.length()>2000) {
               throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_TITLE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
            }
         }
      }
      if (nw_summary_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_summary== null || EMPTY_STRING.equals(nw_summary)) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_SUMMARY", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (nw_summary!= null && nw_summary.length()>2000000000) {
               throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_SUMMARY", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000000000"));
            }
         }
      }
      if (nw_text_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_text== null || EMPTY_STRING.equals(nw_text)) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_TEXT", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (nw_text!= null && nw_text.length()>2000000000) {
               throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_TEXT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000000000"));
            }
         }
      }
      if (nw_content_for_search_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_content_for_search!= null && nw_content_for_search.length()>2000000000) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_CONTENT_FOR_SEARCH", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000000000"));
         }
      }
      if (nw_publication_date_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_publication_date== null) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_PUBLICATION_DATE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (nw_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (nw_date_from== null) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "NW_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>4000) {
            throw new SparkBusinessException(new S2Message("SPR_NEWS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"4000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_NEWS SET ";
      boolean changedExists = false;      if (nw_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_type);
      }
      if (nw_lang_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_LANG = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_lang);
      }
      if (nw_title_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_TITLE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_title);
      }
      if (nw_summary_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_SUMMARY = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_summary);
      }
      if (nw_text_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_TEXT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_text);
      }
      if (nw_content_for_search_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_CONTENT_FOR_SEARCH = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_content_for_search);
      }
      if (nw_publication_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_PUBLICATION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(nw_publication_date);
      }
      if (nw_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_date_from);
      }
      if (nw_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NW_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nw_date_to);
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
      answer = answer +" WHERE  NW_ID = ? ";
      updateStatementPart.addStatementParam(nw_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprNewsDAO\":{\"nw_id\": \""+getNw_id()+"\", \"nw_type\": \""+getNw_type()+"\", \"nw_lang\": \""+getNw_lang()+"\", \"nw_title\": \""+getNw_title()+"\", \"nw_summary\": \""+getNw_summary()+"\", \"nw_text\": \""+getNw_text()+"\", \"nw_content_for_search\": \""+getNw_content_for_search()+"\", \"nw_publication_date\": \""+getNw_publication_date()+"\", \"nw_date_from\": \""+getNw_date_from()+"\", \"nw_date_to\": \""+getNw_date_to()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}