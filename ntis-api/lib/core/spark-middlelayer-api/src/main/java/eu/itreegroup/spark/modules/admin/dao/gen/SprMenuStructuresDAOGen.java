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

public class SprMenuStructuresDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="SPR_MENU_STRUCTURES.MST_ID";
   private Double mst_id;
   private String mst_site;
   private String mst_lang;
   private String mst_code;
   private String mst_title;
   private String mst_icon;
   private Double mst_order;
   private String mst_uri;
   private String mst_is_public;
   private Date mst_date_from;
   private Date mst_date_to;
   private Double mst_frm_id;
   private Double mst_mst_id;
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

   protected boolean mst_id_changed;
   protected boolean mst_site_changed;
   protected boolean mst_lang_changed;
   protected boolean mst_code_changed;
   protected boolean mst_title_changed;
   protected boolean mst_icon_changed;
   protected boolean mst_order_changed;
   protected boolean mst_uri_changed;
   protected boolean mst_is_public_changed;
   protected boolean mst_date_from_changed;
   protected boolean mst_date_to_changed;
   protected boolean mst_frm_id_changed;
   protected boolean mst_mst_id_changed;
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
   public SprMenuStructuresDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public SprMenuStructuresDAOGen(Double mst_id, String mst_site, String mst_lang, String mst_code, String mst_title, String mst_icon, Double mst_order, String mst_uri, String mst_is_public, Date mst_date_from, Date mst_date_to, Double mst_frm_id, Double mst_mst_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.mst_id = mst_id;
      this.mst_site = mst_site;
      this.mst_lang = mst_lang;
      this.mst_code = mst_code;
      this.mst_title = mst_title;
      this.mst_icon = mst_icon;
      this.mst_order = mst_order;
      this.mst_uri = mst_uri;
      this.mst_is_public = mst_is_public;
      this.mst_date_from = mst_date_from;
      this.mst_date_to = mst_date_to;
      this.mst_frm_id = mst_frm_id;
      this.mst_mst_id = mst_mst_id;
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
   public void copyValues(SprMenuStructuresDAOGen obj) {
      this.setMst_id(obj.getMst_id());
      this.setMst_site(obj.getMst_site());
      this.setMst_lang(obj.getMst_lang());
      this.setMst_code(obj.getMst_code());
      this.setMst_title(obj.getMst_title());
      this.setMst_icon(obj.getMst_icon());
      this.setMst_order(obj.getMst_order());
      this.setMst_uri(obj.getMst_uri());
      this.setMst_is_public(obj.getMst_is_public());
      this.setMst_date_from(obj.getMst_date_from());
      this.setMst_date_to(obj.getMst_date_to());
      this.setMst_frm_id(obj.getMst_frm_id());
      this.setMst_mst_id(obj.getMst_mst_id());
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
      this.mst_id_changed = false;
      this.mst_site_changed = false;
      this.mst_lang_changed = false;
      this.mst_code_changed = false;
      this.mst_title_changed = false;
      this.mst_icon_changed = false;
      this.mst_order_changed = false;
      this.mst_uri_changed = false;
      this.mst_is_public_changed = false;
      this.mst_date_from_changed = false;
      this.mst_date_to_changed = false;
      this.mst_frm_id_changed = false;
      this.mst_mst_id_changed = false;
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
   public void setMst_id(Double mst_id) {
      if (this.isForceUpdate() || 
               (this.mst_id != null && !this.mst_id.equals(mst_id))  ||
               (mst_id != null && !mst_id.equals(this.mst_id)) ){
         this.mst_id_changed = true; 
         this.record_changed = true;
         this.mst_id = mst_id;
      }
   }
   public Double getMst_id() {
      return this.mst_id;
   }
   public void setMst_site(String mst_site) {
      if (this.isForceUpdate() || 
               (this.mst_site != null && !this.mst_site.equals(mst_site))  ||
               (mst_site != null && !mst_site.equals(this.mst_site)) ){
         this.mst_site_changed = true; 
         this.record_changed = true;
         this.mst_site = mst_site;
      }
   }
   public String getMst_site() {
      return this.mst_site;
   }
   public void setMst_lang(String mst_lang) {
      if (this.isForceUpdate() || 
               (this.mst_lang != null && !this.mst_lang.equals(mst_lang))  ||
               (mst_lang != null && !mst_lang.equals(this.mst_lang)) ){
         this.mst_lang_changed = true; 
         this.record_changed = true;
         this.mst_lang = mst_lang;
      }
   }
   public String getMst_lang() {
      return this.mst_lang;
   }
   public void setMst_code(String mst_code) {
      if (this.isForceUpdate() || 
               (this.mst_code != null && !this.mst_code.equals(mst_code))  ||
               (mst_code != null && !mst_code.equals(this.mst_code)) ){
         this.mst_code_changed = true; 
         this.record_changed = true;
         this.mst_code = mst_code;
      }
   }
   public String getMst_code() {
      return this.mst_code;
   }
   public void setMst_title(String mst_title) {
      if (this.isForceUpdate() || 
               (this.mst_title != null && !this.mst_title.equals(mst_title))  ||
               (mst_title != null && !mst_title.equals(this.mst_title)) ){
         this.mst_title_changed = true; 
         this.record_changed = true;
         this.mst_title = mst_title;
      }
   }
   public String getMst_title() {
      return this.mst_title;
   }
   public void setMst_icon(String mst_icon) {
      if (this.isForceUpdate() || 
               (this.mst_icon != null && !this.mst_icon.equals(mst_icon))  ||
               (mst_icon != null && !mst_icon.equals(this.mst_icon)) ){
         this.mst_icon_changed = true; 
         this.record_changed = true;
         this.mst_icon = mst_icon;
      }
   }
   public String getMst_icon() {
      return this.mst_icon;
   }
   public void setMst_order(Double mst_order) {
      if (this.isForceUpdate() || 
               (this.mst_order != null && !this.mst_order.equals(mst_order))  ||
               (mst_order != null && !mst_order.equals(this.mst_order)) ){
         this.mst_order_changed = true; 
         this.record_changed = true;
         this.mst_order = mst_order;
      }
   }
   public Double getMst_order() {
      return this.mst_order;
   }
   public void setMst_uri(String mst_uri) {
      if (this.isForceUpdate() || 
               (this.mst_uri != null && !this.mst_uri.equals(mst_uri))  ||
               (mst_uri != null && !mst_uri.equals(this.mst_uri)) ){
         this.mst_uri_changed = true; 
         this.record_changed = true;
         this.mst_uri = mst_uri;
      }
   }
   public String getMst_uri() {
      return this.mst_uri;
   }
   public void setMst_is_public(String mst_is_public) {
      if (this.isForceUpdate() || 
               (this.mst_is_public != null && !this.mst_is_public.equals(mst_is_public))  ||
               (mst_is_public != null && !mst_is_public.equals(this.mst_is_public)) ){
         this.mst_is_public_changed = true; 
         this.record_changed = true;
         this.mst_is_public = mst_is_public;
      }
   }
   public String getMst_is_public() {
      return this.mst_is_public;
   }
   public void setMst_date_from(Date mst_date_from) {
      if (this.isForceUpdate() || 
               (this.mst_date_from != null && (mst_date_from == null ||this.mst_date_from.compareTo(mst_date_from) != 0 )) ||
               (mst_date_from != null && (this.mst_date_from == null ||mst_date_from.compareTo(this.mst_date_from) != 0 ))){
         this.mst_date_from_changed = true; 
         this.record_changed = true;
         this.mst_date_from = mst_date_from;
      }
   }
   public Date getMst_date_from() {
      return this.mst_date_from;
   }
   public void setMst_date_to(Date mst_date_to) {
      if (this.isForceUpdate() || 
               (this.mst_date_to != null && (mst_date_to == null ||this.mst_date_to.compareTo(mst_date_to) != 0 )) ||
               (mst_date_to != null && (this.mst_date_to == null ||mst_date_to.compareTo(this.mst_date_to) != 0 ))){
         this.mst_date_to_changed = true; 
         this.record_changed = true;
         this.mst_date_to = mst_date_to;
      }
   }
   public Date getMst_date_to() {
      return this.mst_date_to;
   }
   public void setMst_frm_id(Double mst_frm_id) {
      if (this.isForceUpdate() || 
               (this.mst_frm_id != null && !this.mst_frm_id.equals(mst_frm_id))  ||
               (mst_frm_id != null && !mst_frm_id.equals(this.mst_frm_id)) ){
         this.mst_frm_id_changed = true; 
         this.record_changed = true;
         this.mst_frm_id = mst_frm_id;
      }
   }
   public Double getMst_frm_id() {
      return this.mst_frm_id;
   }
   public void setMst_mst_id(Double mst_mst_id) {
      if (this.isForceUpdate() || 
               (this.mst_mst_id != null && !this.mst_mst_id.equals(mst_mst_id))  ||
               (mst_mst_id != null && !mst_mst_id.equals(this.mst_mst_id)) ){
         this.mst_mst_id_changed = true; 
         this.record_changed = true;
         this.mst_mst_id = mst_mst_id;
      }
   }
   public Double getMst_mst_id() {
      return this.mst_mst_id;
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
            this.mst_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.mst_id;
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
      if (mst_site_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_site== null || EMPTY_STRING.equals(mst_site)) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_SITE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (mst_site!= null && mst_site.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_SITE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (mst_lang_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_lang== null || EMPTY_STRING.equals(mst_lang)) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_LANG", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (mst_lang!= null && mst_lang.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_LANG", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (mst_code_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_code== null || EMPTY_STRING.equals(mst_code)) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_CODE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (mst_code!= null && mst_code.length()>50) {
               throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_CODE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
            }
         }
      }
      if (mst_title_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_title== null || EMPTY_STRING.equals(mst_title)) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_TITLE", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (mst_title!= null && mst_title.length()>200) {
               throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_TITLE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
            }
         }
      }
      if (mst_icon_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_icon!= null && mst_icon.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_ICON", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (mst_order_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_order!= null && (""+mst_order.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_ORDER", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (mst_uri_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_uri!= null && mst_uri.length()>200) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_URI", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (mst_is_public_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_is_public== null || EMPTY_STRING.equals(mst_is_public)) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_IS_PUBLIC", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }else{
            if (mst_is_public!= null && mst_is_public.length()>1) {
               throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_IS_PUBLIC", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
            }
         }
      }
      if (mst_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_date_from== null) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (mst_frm_id_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_frm_id!= null && (""+mst_frm_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_FRM_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (mst_mst_id_changed || operation == Utils.OPERATION_INSERT) {
         if (mst_mst_id!= null && (""+mst_mst_id.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "MST_MST_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2000) {
            throw new SparkBusinessException(new S2Message("SPR_MENU_STRUCTURES", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2000"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE SPR_MENU_STRUCTURES SET ";
      boolean changedExists = false;      if (mst_site_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_SITE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_site);
      }
      if (mst_lang_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_LANG = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_lang);
      }
      if (mst_code_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_CODE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_code);
      }
      if (mst_title_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_TITLE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_title);
      }
      if (mst_icon_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_ICON = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_icon);
      }
      if (mst_order_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_ORDER = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_order);
      }
      if (mst_uri_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_URI = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_uri);
      }
      if (mst_is_public_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_IS_PUBLIC = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_is_public);
      }
      if (mst_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_date_from);
      }
      if (mst_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_date_to);
      }
      if (mst_frm_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_FRM_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_frm_id);
      }
      if (mst_mst_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"MST_MST_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(mst_mst_id);
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
      answer = answer +" WHERE  MST_ID = ? ";
      updateStatementPart.addStatementParam(mst_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"SprMenuStructuresDAO\":{\"mst_id\": \""+getMst_id()+"\", \"mst_site\": \""+getMst_site()+"\", \"mst_lang\": \""+getMst_lang()+"\", \"mst_code\": \""+getMst_code()+"\", \"mst_title\": \""+getMst_title()+"\", \"mst_icon\": \""+getMst_icon()+"\", \"mst_order\": \""+getMst_order()+"\", \"mst_uri\": \""+getMst_uri()+"\", \"mst_is_public\": \""+getMst_is_public()+"\", \"mst_date_from\": \""+getMst_date_from()+"\", \"mst_date_to\": \""+getMst_date_to()+"\", \"mst_frm_id\": \""+getMst_frm_id()+"\", \"mst_mst_id\": \""+getMst_mst_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}