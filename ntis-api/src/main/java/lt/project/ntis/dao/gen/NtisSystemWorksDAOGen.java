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

public class NtisSystemWorksDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_SYSTEM_WORKS.NSW_ID";
   private Double nsw_id;
   private Date nsw_show_date_from;
   private Date nsw_show_date_to;
   private Date nsw_works_date_from;
   private Date nsw_works_date_to;
   private String nsw_additional_information;
   private String nsw_is_active;
   private String nsw_notification_sent;
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

   protected boolean nsw_id_changed;
   protected boolean nsw_show_date_from_changed;
   protected boolean nsw_show_date_to_changed;
   protected boolean nsw_works_date_from_changed;
   protected boolean nsw_works_date_to_changed;
   protected boolean nsw_additional_information_changed;
   protected boolean nsw_is_active_changed;
   protected boolean nsw_notification_sent_changed;
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
   public NtisSystemWorksDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisSystemWorksDAOGen(Double nsw_id, Date nsw_show_date_from, Date nsw_show_date_to, Date nsw_works_date_from, Date nsw_works_date_to, String nsw_additional_information, String nsw_is_active, String nsw_notification_sent, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.nsw_id = nsw_id;
      this.nsw_show_date_from = nsw_show_date_from;
      this.nsw_show_date_to = nsw_show_date_to;
      this.nsw_works_date_from = nsw_works_date_from;
      this.nsw_works_date_to = nsw_works_date_to;
      this.nsw_additional_information = nsw_additional_information;
      this.nsw_is_active = nsw_is_active;
      this.nsw_notification_sent = nsw_notification_sent;
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
   public void copyValues(NtisSystemWorksDAOGen obj) {
      this.setNsw_id(obj.getNsw_id());
      this.setNsw_show_date_from(obj.getNsw_show_date_from());
      this.setNsw_show_date_to(obj.getNsw_show_date_to());
      this.setNsw_works_date_from(obj.getNsw_works_date_from());
      this.setNsw_works_date_to(obj.getNsw_works_date_to());
      this.setNsw_additional_information(obj.getNsw_additional_information());
      this.setNsw_is_active(obj.getNsw_is_active());
      this.setNsw_notification_sent(obj.getNsw_notification_sent());
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
      this.nsw_id_changed = false;
      this.nsw_show_date_from_changed = false;
      this.nsw_show_date_to_changed = false;
      this.nsw_works_date_from_changed = false;
      this.nsw_works_date_to_changed = false;
      this.nsw_additional_information_changed = false;
      this.nsw_is_active_changed = false;
      this.nsw_notification_sent_changed = false;
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
   public void setNsw_id(Double nsw_id) {
      if (this.isForceUpdate() || 
               (this.nsw_id != null && !this.nsw_id.equals(nsw_id))  ||
               (nsw_id != null && !nsw_id.equals(this.nsw_id)) ){
         this.nsw_id_changed = true; 
         this.record_changed = true;
         this.nsw_id = nsw_id;
      }
   }
   public Double getNsw_id() {
      return this.nsw_id;
   }
   public void setNsw_show_date_from(Date nsw_show_date_from) {
      if (this.isForceUpdate() || 
               (this.nsw_show_date_from != null && (nsw_show_date_from == null ||this.nsw_show_date_from.compareTo(nsw_show_date_from) != 0 )) ||
               (nsw_show_date_from != null && (this.nsw_show_date_from == null ||nsw_show_date_from.compareTo(this.nsw_show_date_from) != 0 ))){
         this.nsw_show_date_from_changed = true; 
         this.record_changed = true;
         this.nsw_show_date_from = nsw_show_date_from;
      }
   }
   public Date getNsw_show_date_from() {
      return this.nsw_show_date_from;
   }
   public void setNsw_show_date_to(Date nsw_show_date_to) {
      if (this.isForceUpdate() || 
               (this.nsw_show_date_to != null && (nsw_show_date_to == null ||this.nsw_show_date_to.compareTo(nsw_show_date_to) != 0 )) ||
               (nsw_show_date_to != null && (this.nsw_show_date_to == null ||nsw_show_date_to.compareTo(this.nsw_show_date_to) != 0 ))){
         this.nsw_show_date_to_changed = true; 
         this.record_changed = true;
         this.nsw_show_date_to = nsw_show_date_to;
      }
   }
   public Date getNsw_show_date_to() {
      return this.nsw_show_date_to;
   }
   public void setNsw_works_date_from(Date nsw_works_date_from) {
      if (this.isForceUpdate() || 
               (this.nsw_works_date_from != null && (nsw_works_date_from == null ||this.nsw_works_date_from.compareTo(nsw_works_date_from) != 0 )) ||
               (nsw_works_date_from != null && (this.nsw_works_date_from == null ||nsw_works_date_from.compareTo(this.nsw_works_date_from) != 0 ))){
         this.nsw_works_date_from_changed = true; 
         this.record_changed = true;
         this.nsw_works_date_from = nsw_works_date_from;
      }
   }
   public Date getNsw_works_date_from() {
      return this.nsw_works_date_from;
   }
   public void setNsw_works_date_to(Date nsw_works_date_to) {
      if (this.isForceUpdate() || 
               (this.nsw_works_date_to != null && (nsw_works_date_to == null ||this.nsw_works_date_to.compareTo(nsw_works_date_to) != 0 )) ||
               (nsw_works_date_to != null && (this.nsw_works_date_to == null ||nsw_works_date_to.compareTo(this.nsw_works_date_to) != 0 ))){
         this.nsw_works_date_to_changed = true; 
         this.record_changed = true;
         this.nsw_works_date_to = nsw_works_date_to;
      }
   }
   public Date getNsw_works_date_to() {
      return this.nsw_works_date_to;
   }
   public void setNsw_additional_information(String nsw_additional_information) {
      if (this.isForceUpdate() || 
               (this.nsw_additional_information != null && !this.nsw_additional_information.equals(nsw_additional_information))  ||
               (nsw_additional_information != null && !nsw_additional_information.equals(this.nsw_additional_information)) ){
         this.nsw_additional_information_changed = true; 
         this.record_changed = true;
         this.nsw_additional_information = nsw_additional_information;
      }
   }
   public String getNsw_additional_information() {
      return this.nsw_additional_information;
   }
   public void setNsw_is_active(String nsw_is_active) {
      if (this.isForceUpdate() || 
               (this.nsw_is_active != null && !this.nsw_is_active.equals(nsw_is_active))  ||
               (nsw_is_active != null && !nsw_is_active.equals(this.nsw_is_active)) ){
         this.nsw_is_active_changed = true; 
         this.record_changed = true;
         this.nsw_is_active = nsw_is_active;
      }
   }
   public String getNsw_is_active() {
      return this.nsw_is_active;
   }
   public void setNsw_notification_sent(String nsw_notification_sent) {
      if (this.isForceUpdate() || 
               (this.nsw_notification_sent != null && !this.nsw_notification_sent.equals(nsw_notification_sent))  ||
               (nsw_notification_sent != null && !nsw_notification_sent.equals(this.nsw_notification_sent)) ){
         this.nsw_notification_sent_changed = true; 
         this.record_changed = true;
         this.nsw_notification_sent = nsw_notification_sent;
      }
   }
   public String getNsw_notification_sent() {
      return this.nsw_notification_sent;
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
            this.nsw_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.nsw_id;
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
      if (nsw_show_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_show_date_from== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_SHOW_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (nsw_show_date_to_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_show_date_to== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_SHOW_DATE_TO", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (nsw_works_date_from_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_works_date_from== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_WORKS_DATE_FROM", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (nsw_works_date_to_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_works_date_to== null) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_WORKS_DATE_TO", DB_ERROR_MANDATORY_PATH, SparkMessageType.ERROR));
         }
      }
      if (nsw_additional_information_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_additional_information!= null && nsw_additional_information.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_ADDITIONAL_INFORMATION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (nsw_is_active_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_is_active!= null && nsw_is_active.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_IS_ACTIVE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (nsw_notification_sent_changed || operation == Utils.OPERATION_INSERT) {
         if (nsw_notification_sent!= null && nsw_notification_sent.length()>1) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "NSW_NOTIFICATION_SENT", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"1"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_SYSTEM_WORKS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_SYSTEM_WORKS SET ";
      boolean changedExists = false;      if (nsw_show_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_SHOW_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(nsw_show_date_from);
      }
      if (nsw_show_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_SHOW_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(nsw_show_date_to);
      }
      if (nsw_works_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_WORKS_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(nsw_works_date_from);
      }
      if (nsw_works_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_WORKS_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(nsw_works_date_to);
      }
      if (nsw_additional_information_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_ADDITIONAL_INFORMATION = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nsw_additional_information);
      }
      if (nsw_is_active_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_IS_ACTIVE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nsw_is_active);
      }
      if (nsw_notification_sent_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"NSW_NOTIFICATION_SENT = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(nsw_notification_sent);
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
      answer = answer +" WHERE  NSW_ID = ? ";
      updateStatementPart.addStatementParam(nsw_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisSystemWorksDAO\":{\"nsw_id\": \""+getNsw_id()+"\", \"nsw_show_date_from\": \""+getNsw_show_date_from()+"\", \"nsw_show_date_to\": \""+getNsw_show_date_to()+"\", \"nsw_works_date_from\": \""+getNsw_works_date_from()+"\", \"nsw_works_date_to\": \""+getNsw_works_date_to()+"\", \"nsw_additional_information\": \""+getNsw_additional_information()+"\", \"nsw_is_active\": \""+getNsw_is_active()+"\", \"nsw_notification_sent\": \""+getNsw_notification_sent()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}