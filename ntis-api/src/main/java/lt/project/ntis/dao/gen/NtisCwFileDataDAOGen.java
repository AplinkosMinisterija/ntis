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

public class NtisCwFileDataDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_CW_FILE_DATA.CWFD_ID";
   private Double cwfd_id;
   private Double cwfd_eil_nr;
   private String cwfd_pastato_kodas;
   private String cwfd_patalpos_kodas;
   private String cwfd_pastato_adr_kodas;
   private String cwfd_savivaldybes_kodas;
   private String cwfd_savivaldybe;
   private String cwfd_seniunijos_kodas;
   private String cwfd_seniunija;
   private String cwfd_gyv_vietos_kodas;
   private String cwfd_gyv_vieta;
   private String cwfd_gatves_kodas;
   private String cwfd_gatve;
   private String cwfd_pastato_nr;
   private String cwfd_korpuso_nr;
   private String cwfd_buto_nr;
   private String cwfd_statinio_vald_kodas;
   private String cwfd_vandentvarkos_im_kod;
   private String cwfd_nuot_salinimo_budas;
   private String cwfd_prijungimo_data;
   private String cwfd_atjungimo_data;
   private String cwfd_status;
   private Double cwfd_cwf_id;
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

   protected boolean cwfd_id_changed;
   protected boolean cwfd_eil_nr_changed;
   protected boolean cwfd_pastato_kodas_changed;
   protected boolean cwfd_patalpos_kodas_changed;
   protected boolean cwfd_pastato_adr_kodas_changed;
   protected boolean cwfd_savivaldybes_kodas_changed;
   protected boolean cwfd_savivaldybe_changed;
   protected boolean cwfd_seniunijos_kodas_changed;
   protected boolean cwfd_seniunija_changed;
   protected boolean cwfd_gyv_vietos_kodas_changed;
   protected boolean cwfd_gyv_vieta_changed;
   protected boolean cwfd_gatves_kodas_changed;
   protected boolean cwfd_gatve_changed;
   protected boolean cwfd_pastato_nr_changed;
   protected boolean cwfd_korpuso_nr_changed;
   protected boolean cwfd_buto_nr_changed;
   protected boolean cwfd_statinio_vald_kodas_changed;
   protected boolean cwfd_vandentvarkos_im_kod_changed;
   protected boolean cwfd_nuot_salinimo_budas_changed;
   protected boolean cwfd_prijungimo_data_changed;
   protected boolean cwfd_atjungimo_data_changed;
   protected boolean cwfd_status_changed;
   protected boolean cwfd_cwf_id_changed;
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
   public NtisCwFileDataDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisCwFileDataDAOGen(Double cwfd_id, Double cwfd_eil_nr, String cwfd_pastato_kodas, String cwfd_patalpos_kodas, String cwfd_pastato_adr_kodas, String cwfd_savivaldybes_kodas, String cwfd_savivaldybe, String cwfd_seniunijos_kodas, String cwfd_seniunija, String cwfd_gyv_vietos_kodas, String cwfd_gyv_vieta, String cwfd_gatves_kodas, String cwfd_gatve, String cwfd_pastato_nr, String cwfd_korpuso_nr, String cwfd_buto_nr, String cwfd_statinio_vald_kodas, String cwfd_vandentvarkos_im_kod, String cwfd_nuot_salinimo_budas, String cwfd_prijungimo_data, String cwfd_atjungimo_data, String cwfd_status, Double cwfd_cwf_id, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.cwfd_id = cwfd_id;
      this.cwfd_eil_nr = cwfd_eil_nr;
      this.cwfd_pastato_kodas = cwfd_pastato_kodas;
      this.cwfd_patalpos_kodas = cwfd_patalpos_kodas;
      this.cwfd_pastato_adr_kodas = cwfd_pastato_adr_kodas;
      this.cwfd_savivaldybes_kodas = cwfd_savivaldybes_kodas;
      this.cwfd_savivaldybe = cwfd_savivaldybe;
      this.cwfd_seniunijos_kodas = cwfd_seniunijos_kodas;
      this.cwfd_seniunija = cwfd_seniunija;
      this.cwfd_gyv_vietos_kodas = cwfd_gyv_vietos_kodas;
      this.cwfd_gyv_vieta = cwfd_gyv_vieta;
      this.cwfd_gatves_kodas = cwfd_gatves_kodas;
      this.cwfd_gatve = cwfd_gatve;
      this.cwfd_pastato_nr = cwfd_pastato_nr;
      this.cwfd_korpuso_nr = cwfd_korpuso_nr;
      this.cwfd_buto_nr = cwfd_buto_nr;
      this.cwfd_statinio_vald_kodas = cwfd_statinio_vald_kodas;
      this.cwfd_vandentvarkos_im_kod = cwfd_vandentvarkos_im_kod;
      this.cwfd_nuot_salinimo_budas = cwfd_nuot_salinimo_budas;
      this.cwfd_prijungimo_data = cwfd_prijungimo_data;
      this.cwfd_atjungimo_data = cwfd_atjungimo_data;
      this.cwfd_status = cwfd_status;
      this.cwfd_cwf_id = cwfd_cwf_id;
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
   public void copyValues(NtisCwFileDataDAOGen obj) {
      this.setCwfd_id(obj.getCwfd_id());
      this.setCwfd_eil_nr(obj.getCwfd_eil_nr());
      this.setCwfd_pastato_kodas(obj.getCwfd_pastato_kodas());
      this.setCwfd_patalpos_kodas(obj.getCwfd_patalpos_kodas());
      this.setCwfd_pastato_adr_kodas(obj.getCwfd_pastato_adr_kodas());
      this.setCwfd_savivaldybes_kodas(obj.getCwfd_savivaldybes_kodas());
      this.setCwfd_savivaldybe(obj.getCwfd_savivaldybe());
      this.setCwfd_seniunijos_kodas(obj.getCwfd_seniunijos_kodas());
      this.setCwfd_seniunija(obj.getCwfd_seniunija());
      this.setCwfd_gyv_vietos_kodas(obj.getCwfd_gyv_vietos_kodas());
      this.setCwfd_gyv_vieta(obj.getCwfd_gyv_vieta());
      this.setCwfd_gatves_kodas(obj.getCwfd_gatves_kodas());
      this.setCwfd_gatve(obj.getCwfd_gatve());
      this.setCwfd_pastato_nr(obj.getCwfd_pastato_nr());
      this.setCwfd_korpuso_nr(obj.getCwfd_korpuso_nr());
      this.setCwfd_buto_nr(obj.getCwfd_buto_nr());
      this.setCwfd_statinio_vald_kodas(obj.getCwfd_statinio_vald_kodas());
      this.setCwfd_vandentvarkos_im_kod(obj.getCwfd_vandentvarkos_im_kod());
      this.setCwfd_nuot_salinimo_budas(obj.getCwfd_nuot_salinimo_budas());
      this.setCwfd_prijungimo_data(obj.getCwfd_prijungimo_data());
      this.setCwfd_atjungimo_data(obj.getCwfd_atjungimo_data());
      this.setCwfd_status(obj.getCwfd_status());
      this.setCwfd_cwf_id(obj.getCwfd_cwf_id());
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
      this.cwfd_id_changed = false;
      this.cwfd_eil_nr_changed = false;
      this.cwfd_pastato_kodas_changed = false;
      this.cwfd_patalpos_kodas_changed = false;
      this.cwfd_pastato_adr_kodas_changed = false;
      this.cwfd_savivaldybes_kodas_changed = false;
      this.cwfd_savivaldybe_changed = false;
      this.cwfd_seniunijos_kodas_changed = false;
      this.cwfd_seniunija_changed = false;
      this.cwfd_gyv_vietos_kodas_changed = false;
      this.cwfd_gyv_vieta_changed = false;
      this.cwfd_gatves_kodas_changed = false;
      this.cwfd_gatve_changed = false;
      this.cwfd_pastato_nr_changed = false;
      this.cwfd_korpuso_nr_changed = false;
      this.cwfd_buto_nr_changed = false;
      this.cwfd_statinio_vald_kodas_changed = false;
      this.cwfd_vandentvarkos_im_kod_changed = false;
      this.cwfd_nuot_salinimo_budas_changed = false;
      this.cwfd_prijungimo_data_changed = false;
      this.cwfd_atjungimo_data_changed = false;
      this.cwfd_status_changed = false;
      this.cwfd_cwf_id_changed = false;
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
   public void setCwfd_id(Double cwfd_id) {
      if (this.isForceUpdate() || (this.cwfd_id != null && !this.cwfd_id.equals(cwfd_id)) || (cwfd_id != null && !cwfd_id.equals(this.cwfd_id))){
         this.cwfd_id_changed = true; 
         this.record_changed = true;
         this.cwfd_id = cwfd_id;
      }
   }
   public Double getCwfd_id() {
      return this.cwfd_id;
   }
   public void setCwfd_eil_nr(Double cwfd_eil_nr) {
      if (this.isForceUpdate() || (this.cwfd_eil_nr != null && !this.cwfd_eil_nr.equals(cwfd_eil_nr)) || (cwfd_eil_nr != null && !cwfd_eil_nr.equals(this.cwfd_eil_nr))){
         this.cwfd_eil_nr_changed = true; 
         this.record_changed = true;
         this.cwfd_eil_nr = cwfd_eil_nr;
      }
   }
   public Double getCwfd_eil_nr() {
      return this.cwfd_eil_nr;
   }
   public void setCwfd_pastato_kodas(String cwfd_pastato_kodas) {
      if (this.isForceUpdate() || (this.cwfd_pastato_kodas != null && !this.cwfd_pastato_kodas.equals(cwfd_pastato_kodas)) || (cwfd_pastato_kodas != null && !cwfd_pastato_kodas.equals(this.cwfd_pastato_kodas))){
         this.cwfd_pastato_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_pastato_kodas = cwfd_pastato_kodas;
      }
   }
   public String getCwfd_pastato_kodas() {
      return this.cwfd_pastato_kodas;
   }
   public void setCwfd_patalpos_kodas(String cwfd_patalpos_kodas) {
      if (this.isForceUpdate() || (this.cwfd_patalpos_kodas != null && !this.cwfd_patalpos_kodas.equals(cwfd_patalpos_kodas)) || (cwfd_patalpos_kodas != null && !cwfd_patalpos_kodas.equals(this.cwfd_patalpos_kodas))){
         this.cwfd_patalpos_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_patalpos_kodas = cwfd_patalpos_kodas;
      }
   }
   public String getCwfd_patalpos_kodas() {
      return this.cwfd_patalpos_kodas;
   }
   public void setCwfd_pastato_adr_kodas(String cwfd_pastato_adr_kodas) {
      if (this.isForceUpdate() || (this.cwfd_pastato_adr_kodas != null && !this.cwfd_pastato_adr_kodas.equals(cwfd_pastato_adr_kodas)) || (cwfd_pastato_adr_kodas != null && !cwfd_pastato_adr_kodas.equals(this.cwfd_pastato_adr_kodas))){
         this.cwfd_pastato_adr_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_pastato_adr_kodas = cwfd_pastato_adr_kodas;
      }
   }
   public String getCwfd_pastato_adr_kodas() {
      return this.cwfd_pastato_adr_kodas;
   }
   public void setCwfd_savivaldybes_kodas(String cwfd_savivaldybes_kodas) {
      if (this.isForceUpdate() || (this.cwfd_savivaldybes_kodas != null && !this.cwfd_savivaldybes_kodas.equals(cwfd_savivaldybes_kodas)) || (cwfd_savivaldybes_kodas != null && !cwfd_savivaldybes_kodas.equals(this.cwfd_savivaldybes_kodas))){
         this.cwfd_savivaldybes_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_savivaldybes_kodas = cwfd_savivaldybes_kodas;
      }
   }
   public String getCwfd_savivaldybes_kodas() {
      return this.cwfd_savivaldybes_kodas;
   }
   public void setCwfd_savivaldybe(String cwfd_savivaldybe) {
      if (this.isForceUpdate() || (this.cwfd_savivaldybe != null && !this.cwfd_savivaldybe.equals(cwfd_savivaldybe)) || (cwfd_savivaldybe != null && !cwfd_savivaldybe.equals(this.cwfd_savivaldybe))){
         this.cwfd_savivaldybe_changed = true; 
         this.record_changed = true;
         this.cwfd_savivaldybe = cwfd_savivaldybe;
      }
   }
   public String getCwfd_savivaldybe() {
      return this.cwfd_savivaldybe;
   }
   public void setCwfd_seniunijos_kodas(String cwfd_seniunijos_kodas) {
      if (this.isForceUpdate() || (this.cwfd_seniunijos_kodas != null && !this.cwfd_seniunijos_kodas.equals(cwfd_seniunijos_kodas)) || (cwfd_seniunijos_kodas != null && !cwfd_seniunijos_kodas.equals(this.cwfd_seniunijos_kodas))){
         this.cwfd_seniunijos_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_seniunijos_kodas = cwfd_seniunijos_kodas;
      }
   }
   public String getCwfd_seniunijos_kodas() {
      return this.cwfd_seniunijos_kodas;
   }
   public void setCwfd_seniunija(String cwfd_seniunija) {
      if (this.isForceUpdate() || (this.cwfd_seniunija != null && !this.cwfd_seniunija.equals(cwfd_seniunija)) || (cwfd_seniunija != null && !cwfd_seniunija.equals(this.cwfd_seniunija))){
         this.cwfd_seniunija_changed = true; 
         this.record_changed = true;
         this.cwfd_seniunija = cwfd_seniunija;
      }
   }
   public String getCwfd_seniunija() {
      return this.cwfd_seniunija;
   }
   public void setCwfd_gyv_vietos_kodas(String cwfd_gyv_vietos_kodas) {
      if (this.isForceUpdate() || (this.cwfd_gyv_vietos_kodas != null && !this.cwfd_gyv_vietos_kodas.equals(cwfd_gyv_vietos_kodas)) || (cwfd_gyv_vietos_kodas != null && !cwfd_gyv_vietos_kodas.equals(this.cwfd_gyv_vietos_kodas))){
         this.cwfd_gyv_vietos_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_gyv_vietos_kodas = cwfd_gyv_vietos_kodas;
      }
   }
   public String getCwfd_gyv_vietos_kodas() {
      return this.cwfd_gyv_vietos_kodas;
   }
   public void setCwfd_gyv_vieta(String cwfd_gyv_vieta) {
      if (this.isForceUpdate() || (this.cwfd_gyv_vieta != null && !this.cwfd_gyv_vieta.equals(cwfd_gyv_vieta)) || (cwfd_gyv_vieta != null && !cwfd_gyv_vieta.equals(this.cwfd_gyv_vieta))){
         this.cwfd_gyv_vieta_changed = true; 
         this.record_changed = true;
         this.cwfd_gyv_vieta = cwfd_gyv_vieta;
      }
   }
   public String getCwfd_gyv_vieta() {
      return this.cwfd_gyv_vieta;
   }
   public void setCwfd_gatves_kodas(String cwfd_gatves_kodas) {
      if (this.isForceUpdate() || (this.cwfd_gatves_kodas != null && !this.cwfd_gatves_kodas.equals(cwfd_gatves_kodas)) || (cwfd_gatves_kodas != null && !cwfd_gatves_kodas.equals(this.cwfd_gatves_kodas))){
         this.cwfd_gatves_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_gatves_kodas = cwfd_gatves_kodas;
      }
   }
   public String getCwfd_gatves_kodas() {
      return this.cwfd_gatves_kodas;
   }
   public void setCwfd_gatve(String cwfd_gatve) {
      if (this.isForceUpdate() || (this.cwfd_gatve != null && !this.cwfd_gatve.equals(cwfd_gatve)) || (cwfd_gatve != null && !cwfd_gatve.equals(this.cwfd_gatve))){
         this.cwfd_gatve_changed = true; 
         this.record_changed = true;
         this.cwfd_gatve = cwfd_gatve;
      }
   }
   public String getCwfd_gatve() {
      return this.cwfd_gatve;
   }
   public void setCwfd_pastato_nr(String cwfd_pastato_nr) {
      if (this.isForceUpdate() || (this.cwfd_pastato_nr != null && !this.cwfd_pastato_nr.equals(cwfd_pastato_nr)) || (cwfd_pastato_nr != null && !cwfd_pastato_nr.equals(this.cwfd_pastato_nr))){
         this.cwfd_pastato_nr_changed = true; 
         this.record_changed = true;
         this.cwfd_pastato_nr = cwfd_pastato_nr;
      }
   }
   public String getCwfd_pastato_nr() {
      return this.cwfd_pastato_nr;
   }
   public void setCwfd_korpuso_nr(String cwfd_korpuso_nr) {
      if (this.isForceUpdate() || (this.cwfd_korpuso_nr != null && !this.cwfd_korpuso_nr.equals(cwfd_korpuso_nr)) || (cwfd_korpuso_nr != null && !cwfd_korpuso_nr.equals(this.cwfd_korpuso_nr))){
         this.cwfd_korpuso_nr_changed = true; 
         this.record_changed = true;
         this.cwfd_korpuso_nr = cwfd_korpuso_nr;
      }
   }
   public String getCwfd_korpuso_nr() {
      return this.cwfd_korpuso_nr;
   }
   public void setCwfd_buto_nr(String cwfd_buto_nr) {
      if (this.isForceUpdate() || (this.cwfd_buto_nr != null && !this.cwfd_buto_nr.equals(cwfd_buto_nr)) || (cwfd_buto_nr != null && !cwfd_buto_nr.equals(this.cwfd_buto_nr))){
         this.cwfd_buto_nr_changed = true; 
         this.record_changed = true;
         this.cwfd_buto_nr = cwfd_buto_nr;
      }
   }
   public String getCwfd_buto_nr() {
      return this.cwfd_buto_nr;
   }
   public void setCwfd_statinio_vald_kodas(String cwfd_statinio_vald_kodas) {
      if (this.isForceUpdate() || (this.cwfd_statinio_vald_kodas != null && !this.cwfd_statinio_vald_kodas.equals(cwfd_statinio_vald_kodas)) || (cwfd_statinio_vald_kodas != null && !cwfd_statinio_vald_kodas.equals(this.cwfd_statinio_vald_kodas))){
         this.cwfd_statinio_vald_kodas_changed = true; 
         this.record_changed = true;
         this.cwfd_statinio_vald_kodas = cwfd_statinio_vald_kodas;
      }
   }
   public String getCwfd_statinio_vald_kodas() {
      return this.cwfd_statinio_vald_kodas;
   }
   public void setCwfd_vandentvarkos_im_kod(String cwfd_vandentvarkos_im_kod) {
      if (this.isForceUpdate() || (this.cwfd_vandentvarkos_im_kod != null && !this.cwfd_vandentvarkos_im_kod.equals(cwfd_vandentvarkos_im_kod)) || (cwfd_vandentvarkos_im_kod != null && !cwfd_vandentvarkos_im_kod.equals(this.cwfd_vandentvarkos_im_kod))){
         this.cwfd_vandentvarkos_im_kod_changed = true; 
         this.record_changed = true;
         this.cwfd_vandentvarkos_im_kod = cwfd_vandentvarkos_im_kod;
      }
   }
   public String getCwfd_vandentvarkos_im_kod() {
      return this.cwfd_vandentvarkos_im_kod;
   }
   public void setCwfd_nuot_salinimo_budas(String cwfd_nuot_salinimo_budas) {
      if (this.isForceUpdate() || (this.cwfd_nuot_salinimo_budas != null && !this.cwfd_nuot_salinimo_budas.equals(cwfd_nuot_salinimo_budas)) || (cwfd_nuot_salinimo_budas != null && !cwfd_nuot_salinimo_budas.equals(this.cwfd_nuot_salinimo_budas))){
         this.cwfd_nuot_salinimo_budas_changed = true; 
         this.record_changed = true;
         this.cwfd_nuot_salinimo_budas = cwfd_nuot_salinimo_budas;
      }
   }
   public String getCwfd_nuot_salinimo_budas() {
      return this.cwfd_nuot_salinimo_budas;
   }
   public void setCwfd_prijungimo_data(String cwfd_prijungimo_data) {
      if (this.isForceUpdate() || (this.cwfd_prijungimo_data != null && !this.cwfd_prijungimo_data.equals(cwfd_prijungimo_data)) || (cwfd_prijungimo_data != null && !cwfd_prijungimo_data.equals(this.cwfd_prijungimo_data))){
         this.cwfd_prijungimo_data_changed = true; 
         this.record_changed = true;
         this.cwfd_prijungimo_data = cwfd_prijungimo_data;
      }
   }
   public String getCwfd_prijungimo_data() {
      return this.cwfd_prijungimo_data;
   }
   public void setCwfd_atjungimo_data(String cwfd_atjungimo_data) {
      if (this.isForceUpdate() || (this.cwfd_atjungimo_data != null && !this.cwfd_atjungimo_data.equals(cwfd_atjungimo_data)) || (cwfd_atjungimo_data != null && !cwfd_atjungimo_data.equals(this.cwfd_atjungimo_data))){
         this.cwfd_atjungimo_data_changed = true; 
         this.record_changed = true;
         this.cwfd_atjungimo_data = cwfd_atjungimo_data;
      }
   }
   public String getCwfd_atjungimo_data() {
      return this.cwfd_atjungimo_data;
   }
   public void setCwfd_status(String cwfd_status) {
      if (this.isForceUpdate() || (this.cwfd_status != null && !this.cwfd_status.equals(cwfd_status)) || (cwfd_status != null && !cwfd_status.equals(this.cwfd_status))){
         this.cwfd_status_changed = true; 
         this.record_changed = true;
         this.cwfd_status = cwfd_status;
      }
   }
   public String getCwfd_status() {
      return this.cwfd_status;
   }
   public void setCwfd_cwf_id(Double cwfd_cwf_id) {
      if (this.isForceUpdate() || (this.cwfd_cwf_id != null && !this.cwfd_cwf_id.equals(cwfd_cwf_id)) || (cwfd_cwf_id != null && !cwfd_cwf_id.equals(this.cwfd_cwf_id))){
         this.cwfd_cwf_id_changed = true; 
         this.record_changed = true;
         this.cwfd_cwf_id = cwfd_cwf_id;
      }
   }
   public Double getCwfd_cwf_id() {
      return this.cwfd_cwf_id;
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
            this.cwfd_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.cwfd_id;
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
      if (cwfd_eil_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_eil_nr!= null && (""+cwfd_eil_nr.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_EIL_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (cwfd_pastato_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_pastato_kodas!= null && cwfd_pastato_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_PASTATO_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_patalpos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_patalpos_kodas!= null && cwfd_patalpos_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_PATALPOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_pastato_adr_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_pastato_adr_kodas!= null && cwfd_pastato_adr_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_PASTATO_ADR_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_savivaldybes_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_savivaldybes_kodas!= null && cwfd_savivaldybes_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_SAVIVALDYBES_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_savivaldybe_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_savivaldybe!= null && cwfd_savivaldybe.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_SAVIVALDYBE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_seniunijos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_seniunijos_kodas!= null && cwfd_seniunijos_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_SENIUNIJOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_seniunija_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_seniunija!= null && cwfd_seniunija.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_SENIUNIJA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_gyv_vietos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_gyv_vietos_kodas!= null && cwfd_gyv_vietos_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_GYV_VIETOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_gyv_vieta_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_gyv_vieta!= null && cwfd_gyv_vieta.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_GYV_VIETA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_gatves_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_gatves_kodas!= null && cwfd_gatves_kodas.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_GATVES_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_gatve_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_gatve!= null && cwfd_gatve.length()>200) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_GATVE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"200"));
         }
      }
      if (cwfd_pastato_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_pastato_nr!= null && cwfd_pastato_nr.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_PASTATO_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (cwfd_korpuso_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_korpuso_nr!= null && cwfd_korpuso_nr.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_KORPUSO_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (cwfd_buto_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_buto_nr!= null && cwfd_buto_nr.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_BUTO_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (cwfd_statinio_vald_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_statinio_vald_kodas!= null && cwfd_statinio_vald_kodas.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_STATINIO_VALD_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (cwfd_vandentvarkos_im_kod_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_vandentvarkos_im_kod!= null && cwfd_vandentvarkos_im_kod.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_VANDENTVARKOS_IM_KOD", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (cwfd_nuot_salinimo_budas_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_nuot_salinimo_budas!= null && cwfd_nuot_salinimo_budas.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_NUOT_SALINIMO_BUDAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (cwfd_prijungimo_data_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_prijungimo_data!= null && cwfd_prijungimo_data.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_PRIJUNGIMO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (cwfd_atjungimo_data_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_atjungimo_data!= null && cwfd_atjungimo_data.length()>20) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_ATJUNGIMO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"20"));
         }
      }
      if (cwfd_status_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_status!= null && cwfd_status.length()>50) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_STATUS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"50"));
         }
      }
      if (cwfd_cwf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (cwfd_cwf_id!= null && (""+cwfd_cwf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "CWFD_CWF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_CW_FILE_DATA", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_CW_FILE_DATA SET ";
      boolean changedExists = false;      if (cwfd_eil_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_EIL_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_eil_nr);
      }
      if (cwfd_pastato_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_PASTATO_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_pastato_kodas);
      }
      if (cwfd_patalpos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_PATALPOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_patalpos_kodas);
      }
      if (cwfd_pastato_adr_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_PASTATO_ADR_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_pastato_adr_kodas);
      }
      if (cwfd_savivaldybes_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_SAVIVALDYBES_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_savivaldybes_kodas);
      }
      if (cwfd_savivaldybe_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_SAVIVALDYBE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_savivaldybe);
      }
      if (cwfd_seniunijos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_SENIUNIJOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_seniunijos_kodas);
      }
      if (cwfd_seniunija_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_SENIUNIJA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_seniunija);
      }
      if (cwfd_gyv_vietos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_GYV_VIETOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_gyv_vietos_kodas);
      }
      if (cwfd_gyv_vieta_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_GYV_VIETA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_gyv_vieta);
      }
      if (cwfd_gatves_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_GATVES_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_gatves_kodas);
      }
      if (cwfd_gatve_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_GATVE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_gatve);
      }
      if (cwfd_pastato_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_PASTATO_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_pastato_nr);
      }
      if (cwfd_korpuso_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_KORPUSO_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_korpuso_nr);
      }
      if (cwfd_buto_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_BUTO_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_buto_nr);
      }
      if (cwfd_statinio_vald_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_STATINIO_VALD_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_statinio_vald_kodas);
      }
      if (cwfd_vandentvarkos_im_kod_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_VANDENTVARKOS_IM_KOD = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_vandentvarkos_im_kod);
      }
      if (cwfd_nuot_salinimo_budas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_NUOT_SALINIMO_BUDAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_nuot_salinimo_budas);
      }
      if (cwfd_prijungimo_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_PRIJUNGIMO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_prijungimo_data);
      }
      if (cwfd_atjungimo_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_ATJUNGIMO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_atjungimo_data);
      }
      if (cwfd_status_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_STATUS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_status);
      }
      if (cwfd_cwf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"CWFD_CWF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(cwfd_cwf_id);
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
      answer = answer +" WHERE  CWFD_ID = ? ";
      updateStatementPart.addStatementParam(cwfd_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisCwFileDataDAO\":{\"cwfd_id\": \""+getCwfd_id()+"\", \"cwfd_eil_nr\": \""+getCwfd_eil_nr()+"\", \"cwfd_pastato_kodas\": \""+getCwfd_pastato_kodas()+"\", \"cwfd_patalpos_kodas\": \""+getCwfd_patalpos_kodas()+"\", \"cwfd_pastato_adr_kodas\": \""+getCwfd_pastato_adr_kodas()+"\", \"cwfd_savivaldybes_kodas\": \""+getCwfd_savivaldybes_kodas()+"\", \"cwfd_savivaldybe\": \""+getCwfd_savivaldybe()+"\", \"cwfd_seniunijos_kodas\": \""+getCwfd_seniunijos_kodas()+"\", \"cwfd_seniunija\": \""+getCwfd_seniunija()+"\", \"cwfd_gyv_vietos_kodas\": \""+getCwfd_gyv_vietos_kodas()+"\", \"cwfd_gyv_vieta\": \""+getCwfd_gyv_vieta()+"\", \"cwfd_gatves_kodas\": \""+getCwfd_gatves_kodas()+"\", \"cwfd_gatve\": \""+getCwfd_gatve()+"\", \"cwfd_pastato_nr\": \""+getCwfd_pastato_nr()+"\", \"cwfd_korpuso_nr\": \""+getCwfd_korpuso_nr()+"\", \"cwfd_buto_nr\": \""+getCwfd_buto_nr()+"\", \"cwfd_statinio_vald_kodas\": \""+getCwfd_statinio_vald_kodas()+"\", \"cwfd_vandentvarkos_im_kod\": \""+getCwfd_vandentvarkos_im_kod()+"\", \"cwfd_nuot_salinimo_budas\": \""+getCwfd_nuot_salinimo_budas()+"\", \"cwfd_prijungimo_data\": \""+getCwfd_prijungimo_data()+"\", \"cwfd_atjungimo_data\": \""+getCwfd_atjungimo_data()+"\", \"cwfd_status\": \""+getCwfd_status()+"\", \"cwfd_cwf_id\": \""+getCwfd_cwf_id()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}