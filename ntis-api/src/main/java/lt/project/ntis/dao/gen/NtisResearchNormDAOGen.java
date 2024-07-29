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

public class NtisResearchNormDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_RESEARCH_NORMS.RN_ID";
   private Double rn_id;
   private String rn_research_type;
   private Double rn_research_norm;
   private String rn_facility_installation_date;
   private Date rn_date_from;
   private Date rn_date_to;
   private Date rn_created;
   private String rn_newest;
   private Double rn_usr_id;
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

   protected boolean rn_id_changed;
   protected boolean rn_research_type_changed;
   protected boolean rn_research_norm_changed;
   protected boolean rn_facility_installation_date_changed;
   protected boolean rn_date_from_changed;
   protected boolean rn_date_to_changed;
   protected boolean rn_created_changed;
   protected boolean rn_newest_changed;
   protected boolean rn_usr_id_changed;
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
   public NtisResearchNormDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisResearchNormDAOGen(Double rn_id, String rn_research_type, Double rn_research_norm, String rn_facility_installation_date, Date rn_date_from, Date rn_date_to, Date rn_created, String rn_newest, Double rn_usr_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.rn_id = rn_id;
      this.rn_research_type = rn_research_type;
      this.rn_research_norm = rn_research_norm;
      this.rn_facility_installation_date = rn_facility_installation_date;
      this.rn_date_from = rn_date_from;
      this.rn_date_to = rn_date_to;
      this.rn_created = rn_created;
      this.rn_newest = rn_newest;
      this.rn_usr_id = rn_usr_id;
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
   public void copyValues(NtisResearchNormDAOGen obj) {
      this.setRn_id(obj.getRn_id());
      this.setRn_research_type(obj.getRn_research_type());
      this.setRn_research_norm(obj.getRn_research_norm());
      this.setRn_facility_installation_date(obj.getRn_facility_installation_date());
      this.setRn_date_from(obj.getRn_date_from());
      this.setRn_date_to(obj.getRn_date_to());
      this.setRn_created(obj.getRn_created());
      this.setRn_newest(obj.getRn_newest());
      this.setRn_usr_id(obj.getRn_usr_id());
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
      this.rn_id_changed = false;
      this.rn_research_type_changed = false;
      this.rn_research_norm_changed = false;
      this.rn_facility_installation_date_changed = false;
      this.rn_date_from_changed = false;
      this.rn_date_to_changed = false;
      this.rn_created_changed = false;
      this.rn_newest_changed = false;
      this.rn_usr_id_changed = false;
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
   public void setRn_id(Double rn_id) {
      if (this.isForceUpdate() || (this.rn_id != null && !this.rn_id.equals(rn_id)) || (rn_id != null && !rn_id.equals(this.rn_id))){
         this.rn_id_changed = true; 
         this.record_changed = true;
         this.rn_id = rn_id;
      }
   }
   public Double getRn_id() {
      return this.rn_id;
   }
   public void setRn_research_type(String rn_research_type) {
      if (this.isForceUpdate() || (this.rn_research_type != null && !this.rn_research_type.equals(rn_research_type)) || (rn_research_type != null && !rn_research_type.equals(this.rn_research_type))){
         this.rn_research_type_changed = true; 
         this.record_changed = true;
         this.rn_research_type = rn_research_type;
      }
   }
   public String getRn_research_type() {
      return this.rn_research_type;
   }
   public void setRn_research_norm(Double rn_research_norm) {
      if (this.isForceUpdate() || (this.rn_research_norm != null && !this.rn_research_norm.equals(rn_research_norm)) || (rn_research_norm != null && !rn_research_norm.equals(this.rn_research_norm))){
         this.rn_research_norm_changed = true; 
         this.record_changed = true;
         this.rn_research_norm = rn_research_norm;
      }
   }
   public Double getRn_research_norm() {
      return this.rn_research_norm;
   }
   public void setRn_facility_installation_date(String rn_facility_installation_date) {
      if (this.isForceUpdate() || (this.rn_facility_installation_date != null && !this.rn_facility_installation_date.equals(rn_facility_installation_date)) || (rn_facility_installation_date != null && !rn_facility_installation_date.equals(this.rn_facility_installation_date))){
         this.rn_facility_installation_date_changed = true; 
         this.record_changed = true;
         this.rn_facility_installation_date = rn_facility_installation_date;
      }
   }
   public String getRn_facility_installation_date() {
      return this.rn_facility_installation_date;
   }
   public void setRn_date_from(Date rn_date_from) {
      if (this.isForceUpdate() || (this.rn_date_from != null && !this.rn_date_from.equals(rn_date_from)) || (rn_date_from != null && !rn_date_from.equals(this.rn_date_from))){
         this.rn_date_from_changed = true; 
         this.record_changed = true;
         this.rn_date_from = rn_date_from;
      }
   }
   public Date getRn_date_from() {
      return this.rn_date_from;
   }
   public void setRn_date_to(Date rn_date_to) {
      if (this.isForceUpdate() || (this.rn_date_to != null && !this.rn_date_to.equals(rn_date_to)) || (rn_date_to != null && !rn_date_to.equals(this.rn_date_to))){
         this.rn_date_to_changed = true; 
         this.record_changed = true;
         this.rn_date_to = rn_date_to;
      }
   }
   public Date getRn_date_to() {
      return this.rn_date_to;
   }
   public void setRn_created(Date rn_created) {
      if (this.isForceUpdate() || (this.rn_created != null && !this.rn_created.equals(rn_created)) || (rn_created != null && !rn_created.equals(this.rn_created))){
         this.rn_created_changed = true; 
         this.record_changed = true;
         this.rn_created = rn_created;
      }
   }
   public Date getRn_created() {
      return this.rn_created;
   }
   public void setRn_newest(String rn_newest) {
      if (this.isForceUpdate() || (this.rn_newest != null && !this.rn_newest.equals(rn_newest)) || (rn_newest != null && !rn_newest.equals(this.rn_newest))){
         this.rn_newest_changed = true; 
         this.record_changed = true;
         this.rn_newest = rn_newest;
      }
   }
   public String getRn_newest() {
      return this.rn_newest;
   }
   public void setRn_usr_id(Double rn_usr_id) {
      if (this.isForceUpdate() || (this.rn_usr_id != null && !this.rn_usr_id.equals(rn_usr_id)) || (rn_usr_id != null && !rn_usr_id.equals(this.rn_usr_id))){
         this.rn_usr_id_changed = true; 
         this.record_changed = true;
         this.rn_usr_id = rn_usr_id;
      }
   }
   public Double getRn_usr_id() {
      return this.rn_usr_id;
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
            this.rn_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.rn_id;
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
      if (rn_research_type_changed || operation == Utils.OPERATION_INSERT) {
         if (rn_research_type!= null && rn_research_type.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "RN_RESEARCH_TYPE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (rn_research_norm_changed || operation == Utils.OPERATION_INSERT) {
         if (rn_research_norm!= null && (""+rn_research_norm.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "RN_RESEARCH_NORM", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (rn_facility_installation_date_changed || operation == Utils.OPERATION_INSERT) {
         if (rn_facility_installation_date!= null && rn_facility_installation_date.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "RN_FACILITY_INSTALLATION_DATE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (rn_newest_changed || operation == Utils.OPERATION_INSERT) {
         if (rn_newest!= null && rn_newest.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "RN_NEWEST", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (rn_usr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (rn_usr_id!= null && (""+rn_usr_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "RN_USR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_RESEARCH_NORMS", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_RESEARCH_NORMS SET ";
      boolean changedExists = false;      if (rn_research_type_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_RESEARCH_TYPE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rn_research_type);
      }
      if (rn_research_norm_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_RESEARCH_NORM = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rn_research_norm);
      }
      if (rn_facility_installation_date_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_FACILITY_INSTALLATION_DATE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rn_facility_installation_date);
      }
      if (rn_date_from_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_DATE_FROM = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(rn_date_from);
      }
      if (rn_date_to_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_DATE_TO = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(rn_date_to);
      }
      if (rn_created_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_CREATED = ? ";
         changedExists = true;
         updateStatementPart.addStatementTimestampParam(rn_created);
      }
      if (rn_newest_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_NEWEST = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rn_newest);
      }
      if (rn_usr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"RN_USR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(rn_usr_id);
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
      answer = answer +" WHERE  RN_ID = ? ";
      updateStatementPart.addStatementParam(rn_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisResearchNormDAO\":{\"rn_id\": \""+getRn_id()+"\", \"rn_research_type\": \""+getRn_research_type()+"\", \"rn_research_norm\": \""+getRn_research_norm()+"\", \"rn_facility_installation_date\": \""+getRn_facility_installation_date()+"\", \"rn_date_from\": \""+getRn_date_from()+"\", \"rn_date_to\": \""+getRn_date_to()+"\", \"rn_created\": \""+getRn_created()+"\", \"rn_newest\": \""+getRn_newest()+"\", \"rn_usr_id\": \""+getRn_usr_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}