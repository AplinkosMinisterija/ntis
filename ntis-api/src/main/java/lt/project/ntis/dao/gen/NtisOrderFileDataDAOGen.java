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

public class NtisOrderFileDataDAOGen extends SprBaseDAO{
   public static String IDENTIFIER="NTIS_ORDER_FILE_DATA.ORFD_ID";
   private Double orfd_id;
   private Double orfd_eil_nr;
   private Double orfd_wtf_id;
   private Double orfd_orf_id;
   private Double orfd_org_id;
   private String orfd_paslaugos_kodas;
   private String orfd_pastato_kodas;
   private String orfd_patalpos_kodas;
   private String orfd_pastato_adr_kodas;
   private String orfd_adresas;
   private String orfd_savivaldybes_kodas;
   private String orfd_savivaldybe;
   private String orfd_seniunijos_kodas;
   private String orfd_seniunija;
   private String orfd_gyv_vietos_kodas;
   private String orfd_gyv_vieta;
   private String orfd_gatves_kodas;
   private String orfd_gatve;
   private String orfd_pastato_nr;
   private String orfd_korpuso_nr;
   private String orfd_buto_nr;
   private String orfd_paslauga;
   private String orfd_uzsakymo_data;
   private String orfd_atlikimo_data;
   private String orfd_isvezimo_data;
   private String orfd_isveztas_kiekis;
   private String orfd_transporto_priemone;
   private Double orfd_cr_id;
   private String orfd_uzsakymo_informacija;
   private String orfd_atlikti_darbai;
   private String orfd_uzsakovas;
   private String orfd_uzsakovo_email;
   private String orfd_uzsakovo_tel;
   private String orfd_uzsakovo_komentaras;
   private String orfd_laboratorijos_komentaras;
   private String orfd_deguonis;
   private String orfd_skendincios;
   private String orfd_azotas;
   private String orfd_fosforas;
   private String orfd_meginio_data;
   private String orfd_meginio_darbuotojas;
   private String orfd_tyrimo_data;
   private String orfd_tyrimo_darbuotojas;
   private String orfd_pastaba_rezultatams;
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

   protected boolean orfd_id_changed;
   protected boolean orfd_eil_nr_changed;
   protected boolean orfd_wtf_id_changed;
   protected boolean orfd_orf_id_changed;
   protected boolean orfd_org_id_changed;
   protected boolean orfd_paslaugos_kodas_changed;
   protected boolean orfd_pastato_kodas_changed;
   protected boolean orfd_patalpos_kodas_changed;
   protected boolean orfd_pastato_adr_kodas_changed;
   protected boolean orfd_adresas_changed;
   protected boolean orfd_savivaldybes_kodas_changed;
   protected boolean orfd_savivaldybe_changed;
   protected boolean orfd_seniunijos_kodas_changed;
   protected boolean orfd_seniunija_changed;
   protected boolean orfd_gyv_vietos_kodas_changed;
   protected boolean orfd_gyv_vieta_changed;
   protected boolean orfd_gatves_kodas_changed;
   protected boolean orfd_gatve_changed;
   protected boolean orfd_pastato_nr_changed;
   protected boolean orfd_korpuso_nr_changed;
   protected boolean orfd_buto_nr_changed;
   protected boolean orfd_paslauga_changed;
   protected boolean orfd_uzsakymo_data_changed;
   protected boolean orfd_atlikimo_data_changed;
   protected boolean orfd_isvezimo_data_changed;
   protected boolean orfd_isveztas_kiekis_changed;
   protected boolean orfd_transporto_priemone_changed;
   protected boolean orfd_cr_id_changed;
   protected boolean orfd_uzsakymo_informacija_changed;
   protected boolean orfd_atlikti_darbai_changed;
   protected boolean orfd_uzsakovas_changed;
   protected boolean orfd_uzsakovo_email_changed;
   protected boolean orfd_uzsakovo_tel_changed;
   protected boolean orfd_uzsakovo_komentaras_changed;
   protected boolean orfd_laboratorijos_komentaras_changed;
   protected boolean orfd_deguonis_changed;
   protected boolean orfd_skendincios_changed;
   protected boolean orfd_azotas_changed;
   protected boolean orfd_fosforas_changed;
   protected boolean orfd_meginio_data_changed;
   protected boolean orfd_meginio_darbuotojas_changed;
   protected boolean orfd_tyrimo_data_changed;
   protected boolean orfd_tyrimo_darbuotojas_changed;
   protected boolean orfd_pastaba_rezultatams_changed;
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
   public NtisOrderFileDataDAOGen() {
      markObjectAsInitial();
      setRecordLoaded(false);
      setForceUpdate(false);
   }
   public NtisOrderFileDataDAOGen(Double orfd_id, Double orfd_eil_nr, Double orfd_wtf_id, Double orfd_orf_id, Double orfd_org_id, String orfd_paslaugos_kodas, String orfd_pastato_kodas, String orfd_patalpos_kodas, String orfd_pastato_adr_kodas, String orfd_adresas, String orfd_savivaldybes_kodas, String orfd_savivaldybe, String orfd_seniunijos_kodas, String orfd_seniunija, String orfd_gyv_vietos_kodas, String orfd_gyv_vieta, String orfd_gatves_kodas, String orfd_gatve, String orfd_pastato_nr, String orfd_korpuso_nr, String orfd_buto_nr, String orfd_paslauga, String orfd_uzsakymo_data, String orfd_atlikimo_data, String orfd_isvezimo_data, String orfd_isveztas_kiekis, String orfd_transporto_priemone, Double orfd_cr_id, String orfd_uzsakymo_informacija, String orfd_atlikti_darbai, String orfd_uzsakovas, String orfd_uzsakovo_email, String orfd_uzsakovo_tel, String orfd_uzsakovo_komentaras, String orfd_laboratorijos_komentaras, String orfd_deguonis, String orfd_skendincios, String orfd_azotas, String orfd_fosforas, String orfd_meginio_data, String orfd_meginio_darbuotojas, String orfd_tyrimo_data, String orfd_tyrimo_darbuotojas, String orfd_pastaba_rezultatams, Double rec_version, Date rec_create_timestamp, String rec_userid, Date rec_timestamp, Double n01, Double n02, Double n03, Double n04, Double n05, String c01, String c02, String c03, String c04, String c05, Date d01, Date d02, Date d03, Date d04, Date d05) {
      this.orfd_id = orfd_id;
      this.orfd_eil_nr = orfd_eil_nr;
      this.orfd_wtf_id = orfd_wtf_id;
      this.orfd_orf_id = orfd_orf_id;
      this.orfd_org_id = orfd_org_id;
      this.orfd_paslaugos_kodas = orfd_paslaugos_kodas;
      this.orfd_pastato_kodas = orfd_pastato_kodas;
      this.orfd_patalpos_kodas = orfd_patalpos_kodas;
      this.orfd_pastato_adr_kodas = orfd_pastato_adr_kodas;
      this.orfd_adresas = orfd_adresas;
      this.orfd_savivaldybes_kodas = orfd_savivaldybes_kodas;
      this.orfd_savivaldybe = orfd_savivaldybe;
      this.orfd_seniunijos_kodas = orfd_seniunijos_kodas;
      this.orfd_seniunija = orfd_seniunija;
      this.orfd_gyv_vietos_kodas = orfd_gyv_vietos_kodas;
      this.orfd_gyv_vieta = orfd_gyv_vieta;
      this.orfd_gatves_kodas = orfd_gatves_kodas;
      this.orfd_gatve = orfd_gatve;
      this.orfd_pastato_nr = orfd_pastato_nr;
      this.orfd_korpuso_nr = orfd_korpuso_nr;
      this.orfd_buto_nr = orfd_buto_nr;
      this.orfd_paslauga = orfd_paslauga;
      this.orfd_uzsakymo_data = orfd_uzsakymo_data;
      this.orfd_atlikimo_data = orfd_atlikimo_data;
      this.orfd_isvezimo_data = orfd_isvezimo_data;
      this.orfd_isveztas_kiekis = orfd_isveztas_kiekis;
      this.orfd_transporto_priemone = orfd_transporto_priemone;
      this.orfd_cr_id = orfd_cr_id;
      this.orfd_uzsakymo_informacija = orfd_uzsakymo_informacija;
      this.orfd_atlikti_darbai = orfd_atlikti_darbai;
      this.orfd_uzsakovas = orfd_uzsakovas;
      this.orfd_uzsakovo_email = orfd_uzsakovo_email;
      this.orfd_uzsakovo_tel = orfd_uzsakovo_tel;
      this.orfd_uzsakovo_komentaras = orfd_uzsakovo_komentaras;
      this.orfd_laboratorijos_komentaras = orfd_laboratorijos_komentaras;
      this.orfd_deguonis = orfd_deguonis;
      this.orfd_skendincios = orfd_skendincios;
      this.orfd_azotas = orfd_azotas;
      this.orfd_fosforas = orfd_fosforas;
      this.orfd_meginio_data = orfd_meginio_data;
      this.orfd_meginio_darbuotojas = orfd_meginio_darbuotojas;
      this.orfd_tyrimo_data = orfd_tyrimo_data;
      this.orfd_tyrimo_darbuotojas = orfd_tyrimo_darbuotojas;
      this.orfd_pastaba_rezultatams = orfd_pastaba_rezultatams;
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
   public void copyValues(NtisOrderFileDataDAOGen obj) {
      this.setOrfd_id(obj.getOrfd_id());
      this.setOrfd_eil_nr(obj.getOrfd_eil_nr());
      this.setOrfd_wtf_id(obj.getOrfd_wtf_id());
      this.setOrfd_orf_id(obj.getOrfd_orf_id());
      this.setOrfd_org_id(obj.getOrfd_org_id());
      this.setOrfd_paslaugos_kodas(obj.getOrfd_paslaugos_kodas());
      this.setOrfd_pastato_kodas(obj.getOrfd_pastato_kodas());
      this.setOrfd_patalpos_kodas(obj.getOrfd_patalpos_kodas());
      this.setOrfd_pastato_adr_kodas(obj.getOrfd_pastato_adr_kodas());
      this.setOrfd_adresas(obj.getOrfd_adresas());
      this.setOrfd_savivaldybes_kodas(obj.getOrfd_savivaldybes_kodas());
      this.setOrfd_savivaldybe(obj.getOrfd_savivaldybe());
      this.setOrfd_seniunijos_kodas(obj.getOrfd_seniunijos_kodas());
      this.setOrfd_seniunija(obj.getOrfd_seniunija());
      this.setOrfd_gyv_vietos_kodas(obj.getOrfd_gyv_vietos_kodas());
      this.setOrfd_gyv_vieta(obj.getOrfd_gyv_vieta());
      this.setOrfd_gatves_kodas(obj.getOrfd_gatves_kodas());
      this.setOrfd_gatve(obj.getOrfd_gatve());
      this.setOrfd_pastato_nr(obj.getOrfd_pastato_nr());
      this.setOrfd_korpuso_nr(obj.getOrfd_korpuso_nr());
      this.setOrfd_buto_nr(obj.getOrfd_buto_nr());
      this.setOrfd_paslauga(obj.getOrfd_paslauga());
      this.setOrfd_uzsakymo_data(obj.getOrfd_uzsakymo_data());
      this.setOrfd_atlikimo_data(obj.getOrfd_atlikimo_data());
      this.setOrfd_isvezimo_data(obj.getOrfd_isvezimo_data());
      this.setOrfd_isveztas_kiekis(obj.getOrfd_isveztas_kiekis());
      this.setOrfd_transporto_priemone(obj.getOrfd_transporto_priemone());
      this.setOrfd_cr_id(obj.getOrfd_cr_id());
      this.setOrfd_uzsakymo_informacija(obj.getOrfd_uzsakymo_informacija());
      this.setOrfd_atlikti_darbai(obj.getOrfd_atlikti_darbai());
      this.setOrfd_uzsakovas(obj.getOrfd_uzsakovas());
      this.setOrfd_uzsakovo_email(obj.getOrfd_uzsakovo_email());
      this.setOrfd_uzsakovo_tel(obj.getOrfd_uzsakovo_tel());
      this.setOrfd_uzsakovo_komentaras(obj.getOrfd_uzsakovo_komentaras());
      this.setOrfd_laboratorijos_komentaras(obj.getOrfd_laboratorijos_komentaras());
      this.setOrfd_deguonis(obj.getOrfd_deguonis());
      this.setOrfd_skendincios(obj.getOrfd_skendincios());
      this.setOrfd_azotas(obj.getOrfd_azotas());
      this.setOrfd_fosforas(obj.getOrfd_fosforas());
      this.setOrfd_meginio_data(obj.getOrfd_meginio_data());
      this.setOrfd_meginio_darbuotojas(obj.getOrfd_meginio_darbuotojas());
      this.setOrfd_tyrimo_data(obj.getOrfd_tyrimo_data());
      this.setOrfd_tyrimo_darbuotojas(obj.getOrfd_tyrimo_darbuotojas());
      this.setOrfd_pastaba_rezultatams(obj.getOrfd_pastaba_rezultatams());
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
      this.orfd_id_changed = false;
      this.orfd_eil_nr_changed = false;
      this.orfd_wtf_id_changed = false;
      this.orfd_orf_id_changed = false;
      this.orfd_org_id_changed = false;
      this.orfd_paslaugos_kodas_changed = false;
      this.orfd_pastato_kodas_changed = false;
      this.orfd_patalpos_kodas_changed = false;
      this.orfd_pastato_adr_kodas_changed = false;
      this.orfd_adresas_changed = false;
      this.orfd_savivaldybes_kodas_changed = false;
      this.orfd_savivaldybe_changed = false;
      this.orfd_seniunijos_kodas_changed = false;
      this.orfd_seniunija_changed = false;
      this.orfd_gyv_vietos_kodas_changed = false;
      this.orfd_gyv_vieta_changed = false;
      this.orfd_gatves_kodas_changed = false;
      this.orfd_gatve_changed = false;
      this.orfd_pastato_nr_changed = false;
      this.orfd_korpuso_nr_changed = false;
      this.orfd_buto_nr_changed = false;
      this.orfd_paslauga_changed = false;
      this.orfd_uzsakymo_data_changed = false;
      this.orfd_atlikimo_data_changed = false;
      this.orfd_isvezimo_data_changed = false;
      this.orfd_isveztas_kiekis_changed = false;
      this.orfd_transporto_priemone_changed = false;
      this.orfd_cr_id_changed = false;
      this.orfd_uzsakymo_informacija_changed = false;
      this.orfd_atlikti_darbai_changed = false;
      this.orfd_uzsakovas_changed = false;
      this.orfd_uzsakovo_email_changed = false;
      this.orfd_uzsakovo_tel_changed = false;
      this.orfd_uzsakovo_komentaras_changed = false;
      this.orfd_laboratorijos_komentaras_changed = false;
      this.orfd_deguonis_changed = false;
      this.orfd_skendincios_changed = false;
      this.orfd_azotas_changed = false;
      this.orfd_fosforas_changed = false;
      this.orfd_meginio_data_changed = false;
      this.orfd_meginio_darbuotojas_changed = false;
      this.orfd_tyrimo_data_changed = false;
      this.orfd_tyrimo_darbuotojas_changed = false;
      this.orfd_pastaba_rezultatams_changed = false;
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
   public void setOrfd_id(Double orfd_id) {
      if (this.isForceUpdate() || (this.orfd_id != null && !this.orfd_id.equals(orfd_id)) || (orfd_id != null && !orfd_id.equals(this.orfd_id))){
         this.orfd_id_changed = true; 
         this.record_changed = true;
         this.orfd_id = orfd_id;
      }
   }
   public Double getOrfd_id() {
      return this.orfd_id;
   }
   public void setOrfd_eil_nr(Double orfd_eil_nr) {
      if (this.isForceUpdate() || (this.orfd_eil_nr != null && !this.orfd_eil_nr.equals(orfd_eil_nr)) || (orfd_eil_nr != null && !orfd_eil_nr.equals(this.orfd_eil_nr))){
         this.orfd_eil_nr_changed = true; 
         this.record_changed = true;
         this.orfd_eil_nr = orfd_eil_nr;
      }
   }
   public Double getOrfd_eil_nr() {
      return this.orfd_eil_nr;
   }
   public void setOrfd_wtf_id(Double orfd_wtf_id) {
      if (this.isForceUpdate() || (this.orfd_wtf_id != null && !this.orfd_wtf_id.equals(orfd_wtf_id)) || (orfd_wtf_id != null && !orfd_wtf_id.equals(this.orfd_wtf_id))){
         this.orfd_wtf_id_changed = true; 
         this.record_changed = true;
         this.orfd_wtf_id = orfd_wtf_id;
      }
   }
   public Double getOrfd_wtf_id() {
      return this.orfd_wtf_id;
   }
   public void setOrfd_orf_id(Double orfd_orf_id) {
      if (this.isForceUpdate() || (this.orfd_orf_id != null && !this.orfd_orf_id.equals(orfd_orf_id)) || (orfd_orf_id != null && !orfd_orf_id.equals(this.orfd_orf_id))){
         this.orfd_orf_id_changed = true; 
         this.record_changed = true;
         this.orfd_orf_id = orfd_orf_id;
      }
   }
   public Double getOrfd_orf_id() {
      return this.orfd_orf_id;
   }
   public void setOrfd_org_id(Double orfd_org_id) {
      if (this.isForceUpdate() || (this.orfd_org_id != null && !this.orfd_org_id.equals(orfd_org_id)) || (orfd_org_id != null && !orfd_org_id.equals(this.orfd_org_id))){
         this.orfd_org_id_changed = true; 
         this.record_changed = true;
         this.orfd_org_id = orfd_org_id;
      }
   }
   public Double getOrfd_org_id() {
      return this.orfd_org_id;
   }
   public void setOrfd_paslaugos_kodas(String orfd_paslaugos_kodas) {
      if (this.isForceUpdate() || (this.orfd_paslaugos_kodas != null && !this.orfd_paslaugos_kodas.equals(orfd_paslaugos_kodas)) || (orfd_paslaugos_kodas != null && !orfd_paslaugos_kodas.equals(this.orfd_paslaugos_kodas))){
         this.orfd_paslaugos_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_paslaugos_kodas = orfd_paslaugos_kodas;
      }
   }
   public String getOrfd_paslaugos_kodas() {
      return this.orfd_paslaugos_kodas;
   }
   public void setOrfd_pastato_kodas(String orfd_pastato_kodas) {
      if (this.isForceUpdate() || (this.orfd_pastato_kodas != null && !this.orfd_pastato_kodas.equals(orfd_pastato_kodas)) || (orfd_pastato_kodas != null && !orfd_pastato_kodas.equals(this.orfd_pastato_kodas))){
         this.orfd_pastato_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_pastato_kodas = orfd_pastato_kodas;
      }
   }
   public String getOrfd_pastato_kodas() {
      return this.orfd_pastato_kodas;
   }
   public void setOrfd_patalpos_kodas(String orfd_patalpos_kodas) {
      if (this.isForceUpdate() || (this.orfd_patalpos_kodas != null && !this.orfd_patalpos_kodas.equals(orfd_patalpos_kodas)) || (orfd_patalpos_kodas != null && !orfd_patalpos_kodas.equals(this.orfd_patalpos_kodas))){
         this.orfd_patalpos_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_patalpos_kodas = orfd_patalpos_kodas;
      }
   }
   public String getOrfd_patalpos_kodas() {
      return this.orfd_patalpos_kodas;
   }
   public void setOrfd_pastato_adr_kodas(String orfd_pastato_adr_kodas) {
      if (this.isForceUpdate() || (this.orfd_pastato_adr_kodas != null && !this.orfd_pastato_adr_kodas.equals(orfd_pastato_adr_kodas)) || (orfd_pastato_adr_kodas != null && !orfd_pastato_adr_kodas.equals(this.orfd_pastato_adr_kodas))){
         this.orfd_pastato_adr_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_pastato_adr_kodas = orfd_pastato_adr_kodas;
      }
   }
   public String getOrfd_pastato_adr_kodas() {
      return this.orfd_pastato_adr_kodas;
   }
   public void setOrfd_adresas(String orfd_adresas) {
      if (this.isForceUpdate() || (this.orfd_adresas != null && !this.orfd_adresas.equals(orfd_adresas)) || (orfd_adresas != null && !orfd_adresas.equals(this.orfd_adresas))){
         this.orfd_adresas_changed = true; 
         this.record_changed = true;
         this.orfd_adresas = orfd_adresas;
      }
   }
   public String getOrfd_adresas() {
      return this.orfd_adresas;
   }
   public void setOrfd_savivaldybes_kodas(String orfd_savivaldybes_kodas) {
      if (this.isForceUpdate() || (this.orfd_savivaldybes_kodas != null && !this.orfd_savivaldybes_kodas.equals(orfd_savivaldybes_kodas)) || (orfd_savivaldybes_kodas != null && !orfd_savivaldybes_kodas.equals(this.orfd_savivaldybes_kodas))){
         this.orfd_savivaldybes_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_savivaldybes_kodas = orfd_savivaldybes_kodas;
      }
   }
   public String getOrfd_savivaldybes_kodas() {
      return this.orfd_savivaldybes_kodas;
   }
   public void setOrfd_savivaldybe(String orfd_savivaldybe) {
      if (this.isForceUpdate() || (this.orfd_savivaldybe != null && !this.orfd_savivaldybe.equals(orfd_savivaldybe)) || (orfd_savivaldybe != null && !orfd_savivaldybe.equals(this.orfd_savivaldybe))){
         this.orfd_savivaldybe_changed = true; 
         this.record_changed = true;
         this.orfd_savivaldybe = orfd_savivaldybe;
      }
   }
   public String getOrfd_savivaldybe() {
      return this.orfd_savivaldybe;
   }
   public void setOrfd_seniunijos_kodas(String orfd_seniunijos_kodas) {
      if (this.isForceUpdate() || (this.orfd_seniunijos_kodas != null && !this.orfd_seniunijos_kodas.equals(orfd_seniunijos_kodas)) || (orfd_seniunijos_kodas != null && !orfd_seniunijos_kodas.equals(this.orfd_seniunijos_kodas))){
         this.orfd_seniunijos_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_seniunijos_kodas = orfd_seniunijos_kodas;
      }
   }
   public String getOrfd_seniunijos_kodas() {
      return this.orfd_seniunijos_kodas;
   }
   public void setOrfd_seniunija(String orfd_seniunija) {
      if (this.isForceUpdate() || (this.orfd_seniunija != null && !this.orfd_seniunija.equals(orfd_seniunija)) || (orfd_seniunija != null && !orfd_seniunija.equals(this.orfd_seniunija))){
         this.orfd_seniunija_changed = true; 
         this.record_changed = true;
         this.orfd_seniunija = orfd_seniunija;
      }
   }
   public String getOrfd_seniunija() {
      return this.orfd_seniunija;
   }
   public void setOrfd_gyv_vietos_kodas(String orfd_gyv_vietos_kodas) {
      if (this.isForceUpdate() || (this.orfd_gyv_vietos_kodas != null && !this.orfd_gyv_vietos_kodas.equals(orfd_gyv_vietos_kodas)) || (orfd_gyv_vietos_kodas != null && !orfd_gyv_vietos_kodas.equals(this.orfd_gyv_vietos_kodas))){
         this.orfd_gyv_vietos_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_gyv_vietos_kodas = orfd_gyv_vietos_kodas;
      }
   }
   public String getOrfd_gyv_vietos_kodas() {
      return this.orfd_gyv_vietos_kodas;
   }
   public void setOrfd_gyv_vieta(String orfd_gyv_vieta) {
      if (this.isForceUpdate() || (this.orfd_gyv_vieta != null && !this.orfd_gyv_vieta.equals(orfd_gyv_vieta)) || (orfd_gyv_vieta != null && !orfd_gyv_vieta.equals(this.orfd_gyv_vieta))){
         this.orfd_gyv_vieta_changed = true; 
         this.record_changed = true;
         this.orfd_gyv_vieta = orfd_gyv_vieta;
      }
   }
   public String getOrfd_gyv_vieta() {
      return this.orfd_gyv_vieta;
   }
   public void setOrfd_gatves_kodas(String orfd_gatves_kodas) {
      if (this.isForceUpdate() || (this.orfd_gatves_kodas != null && !this.orfd_gatves_kodas.equals(orfd_gatves_kodas)) || (orfd_gatves_kodas != null && !orfd_gatves_kodas.equals(this.orfd_gatves_kodas))){
         this.orfd_gatves_kodas_changed = true; 
         this.record_changed = true;
         this.orfd_gatves_kodas = orfd_gatves_kodas;
      }
   }
   public String getOrfd_gatves_kodas() {
      return this.orfd_gatves_kodas;
   }
   public void setOrfd_gatve(String orfd_gatve) {
      if (this.isForceUpdate() || (this.orfd_gatve != null && !this.orfd_gatve.equals(orfd_gatve)) || (orfd_gatve != null && !orfd_gatve.equals(this.orfd_gatve))){
         this.orfd_gatve_changed = true; 
         this.record_changed = true;
         this.orfd_gatve = orfd_gatve;
      }
   }
   public String getOrfd_gatve() {
      return this.orfd_gatve;
   }
   public void setOrfd_pastato_nr(String orfd_pastato_nr) {
      if (this.isForceUpdate() || (this.orfd_pastato_nr != null && !this.orfd_pastato_nr.equals(orfd_pastato_nr)) || (orfd_pastato_nr != null && !orfd_pastato_nr.equals(this.orfd_pastato_nr))){
         this.orfd_pastato_nr_changed = true; 
         this.record_changed = true;
         this.orfd_pastato_nr = orfd_pastato_nr;
      }
   }
   public String getOrfd_pastato_nr() {
      return this.orfd_pastato_nr;
   }
   public void setOrfd_korpuso_nr(String orfd_korpuso_nr) {
      if (this.isForceUpdate() || (this.orfd_korpuso_nr != null && !this.orfd_korpuso_nr.equals(orfd_korpuso_nr)) || (orfd_korpuso_nr != null && !orfd_korpuso_nr.equals(this.orfd_korpuso_nr))){
         this.orfd_korpuso_nr_changed = true; 
         this.record_changed = true;
         this.orfd_korpuso_nr = orfd_korpuso_nr;
      }
   }
   public String getOrfd_korpuso_nr() {
      return this.orfd_korpuso_nr;
   }
   public void setOrfd_buto_nr(String orfd_buto_nr) {
      if (this.isForceUpdate() || (this.orfd_buto_nr != null && !this.orfd_buto_nr.equals(orfd_buto_nr)) || (orfd_buto_nr != null && !orfd_buto_nr.equals(this.orfd_buto_nr))){
         this.orfd_buto_nr_changed = true; 
         this.record_changed = true;
         this.orfd_buto_nr = orfd_buto_nr;
      }
   }
   public String getOrfd_buto_nr() {
      return this.orfd_buto_nr;
   }
   public void setOrfd_paslauga(String orfd_paslauga) {
      if (this.isForceUpdate() || (this.orfd_paslauga != null && !this.orfd_paslauga.equals(orfd_paslauga)) || (orfd_paslauga != null && !orfd_paslauga.equals(this.orfd_paslauga))){
         this.orfd_paslauga_changed = true; 
         this.record_changed = true;
         this.orfd_paslauga = orfd_paslauga;
      }
   }
   public String getOrfd_paslauga() {
      return this.orfd_paslauga;
   }
   public void setOrfd_uzsakymo_data(String orfd_uzsakymo_data) {
      if (this.isForceUpdate() || (this.orfd_uzsakymo_data != null && !this.orfd_uzsakymo_data.equals(orfd_uzsakymo_data)) || (orfd_uzsakymo_data != null && !orfd_uzsakymo_data.equals(this.orfd_uzsakymo_data))){
         this.orfd_uzsakymo_data_changed = true; 
         this.record_changed = true;
         this.orfd_uzsakymo_data = orfd_uzsakymo_data;
      }
   }
   public String getOrfd_uzsakymo_data() {
      return this.orfd_uzsakymo_data;
   }
   public void setOrfd_atlikimo_data(String orfd_atlikimo_data) {
      if (this.isForceUpdate() || (this.orfd_atlikimo_data != null && !this.orfd_atlikimo_data.equals(orfd_atlikimo_data)) || (orfd_atlikimo_data != null && !orfd_atlikimo_data.equals(this.orfd_atlikimo_data))){
         this.orfd_atlikimo_data_changed = true; 
         this.record_changed = true;
         this.orfd_atlikimo_data = orfd_atlikimo_data;
      }
   }
   public String getOrfd_atlikimo_data() {
      return this.orfd_atlikimo_data;
   }
   public void setOrfd_isvezimo_data(String orfd_isvezimo_data) {
      if (this.isForceUpdate() || (this.orfd_isvezimo_data != null && !this.orfd_isvezimo_data.equals(orfd_isvezimo_data)) || (orfd_isvezimo_data != null && !orfd_isvezimo_data.equals(this.orfd_isvezimo_data))){
         this.orfd_isvezimo_data_changed = true; 
         this.record_changed = true;
         this.orfd_isvezimo_data = orfd_isvezimo_data;
      }
   }
   public String getOrfd_isvezimo_data() {
      return this.orfd_isvezimo_data;
   }
   public void setOrfd_isveztas_kiekis(String orfd_isveztas_kiekis) {
      if (this.isForceUpdate() || (this.orfd_isveztas_kiekis != null && !this.orfd_isveztas_kiekis.equals(orfd_isveztas_kiekis)) || (orfd_isveztas_kiekis != null && !orfd_isveztas_kiekis.equals(this.orfd_isveztas_kiekis))){
         this.orfd_isveztas_kiekis_changed = true; 
         this.record_changed = true;
         this.orfd_isveztas_kiekis = orfd_isveztas_kiekis;
      }
   }
   public String getOrfd_isveztas_kiekis() {
      return this.orfd_isveztas_kiekis;
   }
   public void setOrfd_transporto_priemone(String orfd_transporto_priemone) {
      if (this.isForceUpdate() || (this.orfd_transporto_priemone != null && !this.orfd_transporto_priemone.equals(orfd_transporto_priemone)) || (orfd_transporto_priemone != null && !orfd_transporto_priemone.equals(this.orfd_transporto_priemone))){
         this.orfd_transporto_priemone_changed = true; 
         this.record_changed = true;
         this.orfd_transporto_priemone = orfd_transporto_priemone;
      }
   }
   public String getOrfd_transporto_priemone() {
      return this.orfd_transporto_priemone;
   }
   public void setOrfd_cr_id(Double orfd_cr_id) {
      if (this.isForceUpdate() || (this.orfd_cr_id != null && !this.orfd_cr_id.equals(orfd_cr_id)) || (orfd_cr_id != null && !orfd_cr_id.equals(this.orfd_cr_id))){
         this.orfd_cr_id_changed = true; 
         this.record_changed = true;
         this.orfd_cr_id = orfd_cr_id;
      }
   }
   public Double getOrfd_cr_id() {
      return this.orfd_cr_id;
   }
   public void setOrfd_uzsakymo_informacija(String orfd_uzsakymo_informacija) {
      if (this.isForceUpdate() || (this.orfd_uzsakymo_informacija != null && !this.orfd_uzsakymo_informacija.equals(orfd_uzsakymo_informacija)) || (orfd_uzsakymo_informacija != null && !orfd_uzsakymo_informacija.equals(this.orfd_uzsakymo_informacija))){
         this.orfd_uzsakymo_informacija_changed = true; 
         this.record_changed = true;
         this.orfd_uzsakymo_informacija = orfd_uzsakymo_informacija;
      }
   }
   public String getOrfd_uzsakymo_informacija() {
      return this.orfd_uzsakymo_informacija;
   }
   public void setOrfd_atlikti_darbai(String orfd_atlikti_darbai) {
      if (this.isForceUpdate() || (this.orfd_atlikti_darbai != null && !this.orfd_atlikti_darbai.equals(orfd_atlikti_darbai)) || (orfd_atlikti_darbai != null && !orfd_atlikti_darbai.equals(this.orfd_atlikti_darbai))){
         this.orfd_atlikti_darbai_changed = true; 
         this.record_changed = true;
         this.orfd_atlikti_darbai = orfd_atlikti_darbai;
      }
   }
   public String getOrfd_atlikti_darbai() {
      return this.orfd_atlikti_darbai;
   }
   public void setOrfd_uzsakovas(String orfd_uzsakovas) {
      if (this.isForceUpdate() || (this.orfd_uzsakovas != null && !this.orfd_uzsakovas.equals(orfd_uzsakovas)) || (orfd_uzsakovas != null && !orfd_uzsakovas.equals(this.orfd_uzsakovas))){
         this.orfd_uzsakovas_changed = true; 
         this.record_changed = true;
         this.orfd_uzsakovas = orfd_uzsakovas;
      }
   }
   public String getOrfd_uzsakovas() {
      return this.orfd_uzsakovas;
   }
   public void setOrfd_uzsakovo_email(String orfd_uzsakovo_email) {
      if (this.isForceUpdate() || (this.orfd_uzsakovo_email != null && !this.orfd_uzsakovo_email.equals(orfd_uzsakovo_email)) || (orfd_uzsakovo_email != null && !orfd_uzsakovo_email.equals(this.orfd_uzsakovo_email))){
         this.orfd_uzsakovo_email_changed = true; 
         this.record_changed = true;
         this.orfd_uzsakovo_email = orfd_uzsakovo_email;
      }
   }
   public String getOrfd_uzsakovo_email() {
      return this.orfd_uzsakovo_email;
   }
   public void setOrfd_uzsakovo_tel(String orfd_uzsakovo_tel) {
      if (this.isForceUpdate() || (this.orfd_uzsakovo_tel != null && !this.orfd_uzsakovo_tel.equals(orfd_uzsakovo_tel)) || (orfd_uzsakovo_tel != null && !orfd_uzsakovo_tel.equals(this.orfd_uzsakovo_tel))){
         this.orfd_uzsakovo_tel_changed = true; 
         this.record_changed = true;
         this.orfd_uzsakovo_tel = orfd_uzsakovo_tel;
      }
   }
   public String getOrfd_uzsakovo_tel() {
      return this.orfd_uzsakovo_tel;
   }
   public void setOrfd_uzsakovo_komentaras(String orfd_uzsakovo_komentaras) {
      if (this.isForceUpdate() || (this.orfd_uzsakovo_komentaras != null && !this.orfd_uzsakovo_komentaras.equals(orfd_uzsakovo_komentaras)) || (orfd_uzsakovo_komentaras != null && !orfd_uzsakovo_komentaras.equals(this.orfd_uzsakovo_komentaras))){
         this.orfd_uzsakovo_komentaras_changed = true; 
         this.record_changed = true;
         this.orfd_uzsakovo_komentaras = orfd_uzsakovo_komentaras;
      }
   }
   public String getOrfd_uzsakovo_komentaras() {
      return this.orfd_uzsakovo_komentaras;
   }
   public void setOrfd_laboratorijos_komentaras(String orfd_laboratorijos_komentaras) {
      if (this.isForceUpdate() || (this.orfd_laboratorijos_komentaras != null && !this.orfd_laboratorijos_komentaras.equals(orfd_laboratorijos_komentaras)) || (orfd_laboratorijos_komentaras != null && !orfd_laboratorijos_komentaras.equals(this.orfd_laboratorijos_komentaras))){
         this.orfd_laboratorijos_komentaras_changed = true; 
         this.record_changed = true;
         this.orfd_laboratorijos_komentaras = orfd_laboratorijos_komentaras;
      }
   }
   public String getOrfd_laboratorijos_komentaras() {
      return this.orfd_laboratorijos_komentaras;
   }
   public void setOrfd_deguonis(String orfd_deguonis) {
      if (this.isForceUpdate() || (this.orfd_deguonis != null && !this.orfd_deguonis.equals(orfd_deguonis)) || (orfd_deguonis != null && !orfd_deguonis.equals(this.orfd_deguonis))){
         this.orfd_deguonis_changed = true; 
         this.record_changed = true;
         this.orfd_deguonis = orfd_deguonis;
      }
   }
   public String getOrfd_deguonis() {
      return this.orfd_deguonis;
   }
   public void setOrfd_skendincios(String orfd_skendincios) {
      if (this.isForceUpdate() || (this.orfd_skendincios != null && !this.orfd_skendincios.equals(orfd_skendincios)) || (orfd_skendincios != null && !orfd_skendincios.equals(this.orfd_skendincios))){
         this.orfd_skendincios_changed = true; 
         this.record_changed = true;
         this.orfd_skendincios = orfd_skendincios;
      }
   }
   public String getOrfd_skendincios() {
      return this.orfd_skendincios;
   }
   public void setOrfd_azotas(String orfd_azotas) {
      if (this.isForceUpdate() || (this.orfd_azotas != null && !this.orfd_azotas.equals(orfd_azotas)) || (orfd_azotas != null && !orfd_azotas.equals(this.orfd_azotas))){
         this.orfd_azotas_changed = true; 
         this.record_changed = true;
         this.orfd_azotas = orfd_azotas;
      }
   }
   public String getOrfd_azotas() {
      return this.orfd_azotas;
   }
   public void setOrfd_fosforas(String orfd_fosforas) {
      if (this.isForceUpdate() || (this.orfd_fosforas != null && !this.orfd_fosforas.equals(orfd_fosforas)) || (orfd_fosforas != null && !orfd_fosforas.equals(this.orfd_fosforas))){
         this.orfd_fosforas_changed = true; 
         this.record_changed = true;
         this.orfd_fosforas = orfd_fosforas;
      }
   }
   public String getOrfd_fosforas() {
      return this.orfd_fosforas;
   }
   public void setOrfd_meginio_data(String orfd_meginio_data) {
      if (this.isForceUpdate() || (this.orfd_meginio_data != null && !this.orfd_meginio_data.equals(orfd_meginio_data)) || (orfd_meginio_data != null && !orfd_meginio_data.equals(this.orfd_meginio_data))){
         this.orfd_meginio_data_changed = true; 
         this.record_changed = true;
         this.orfd_meginio_data = orfd_meginio_data;
      }
   }
   public String getOrfd_meginio_data() {
      return this.orfd_meginio_data;
   }
   public void setOrfd_meginio_darbuotojas(String orfd_meginio_darbuotojas) {
      if (this.isForceUpdate() || (this.orfd_meginio_darbuotojas != null && !this.orfd_meginio_darbuotojas.equals(orfd_meginio_darbuotojas)) || (orfd_meginio_darbuotojas != null && !orfd_meginio_darbuotojas.equals(this.orfd_meginio_darbuotojas))){
         this.orfd_meginio_darbuotojas_changed = true; 
         this.record_changed = true;
         this.orfd_meginio_darbuotojas = orfd_meginio_darbuotojas;
      }
   }
   public String getOrfd_meginio_darbuotojas() {
      return this.orfd_meginio_darbuotojas;
   }
   public void setOrfd_tyrimo_data(String orfd_tyrimo_data) {
      if (this.isForceUpdate() || (this.orfd_tyrimo_data != null && !this.orfd_tyrimo_data.equals(orfd_tyrimo_data)) || (orfd_tyrimo_data != null && !orfd_tyrimo_data.equals(this.orfd_tyrimo_data))){
         this.orfd_tyrimo_data_changed = true; 
         this.record_changed = true;
         this.orfd_tyrimo_data = orfd_tyrimo_data;
      }
   }
   public String getOrfd_tyrimo_data() {
      return this.orfd_tyrimo_data;
   }
   public void setOrfd_tyrimo_darbuotojas(String orfd_tyrimo_darbuotojas) {
      if (this.isForceUpdate() || (this.orfd_tyrimo_darbuotojas != null && !this.orfd_tyrimo_darbuotojas.equals(orfd_tyrimo_darbuotojas)) || (orfd_tyrimo_darbuotojas != null && !orfd_tyrimo_darbuotojas.equals(this.orfd_tyrimo_darbuotojas))){
         this.orfd_tyrimo_darbuotojas_changed = true; 
         this.record_changed = true;
         this.orfd_tyrimo_darbuotojas = orfd_tyrimo_darbuotojas;
      }
   }
   public String getOrfd_tyrimo_darbuotojas() {
      return this.orfd_tyrimo_darbuotojas;
   }
   public void setOrfd_pastaba_rezultatams(String orfd_pastaba_rezultatams) {
      if (this.isForceUpdate() || (this.orfd_pastaba_rezultatams != null && !this.orfd_pastaba_rezultatams.equals(orfd_pastaba_rezultatams)) || (orfd_pastaba_rezultatams != null && !orfd_pastaba_rezultatams.equals(this.orfd_pastaba_rezultatams))){
         this.orfd_pastaba_rezultatams_changed = true; 
         this.record_changed = true;
         this.orfd_pastaba_rezultatams = orfd_pastaba_rezultatams;
      }
   }
   public String getOrfd_pastaba_rezultatams() {
      return this.orfd_pastaba_rezultatams;
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
            this.orfd_id = (Double)recIdentifier;
        }
    }
    @JsonIgnore
    public Object getRecordIdentifier() {
        return this.orfd_id;
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
      if (orfd_eil_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_eil_nr!= null && (""+orfd_eil_nr.intValue()).length()>12) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_EIL_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"12"));
         }
      }
      if (orfd_wtf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_wtf_id!= null && (""+orfd_wtf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_WTF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (orfd_orf_id_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_orf_id!= null && (""+orfd_orf_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ORF_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (orfd_org_id_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_org_id!= null && (""+orfd_org_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ORG_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (orfd_paslaugos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_paslaugos_kodas!= null && orfd_paslaugos_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PASLAUGOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_pastato_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_pastato_kodas!= null && orfd_pastato_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PASTATO_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_patalpos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_patalpos_kodas!= null && orfd_patalpos_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PATALPOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_pastato_adr_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_pastato_adr_kodas!= null && orfd_pastato_adr_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PASTATO_ADR_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_adresas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_adresas!= null && orfd_adresas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ADRESAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_savivaldybes_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_savivaldybes_kodas!= null && orfd_savivaldybes_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_SAVIVALDYBES_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_savivaldybe_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_savivaldybe!= null && orfd_savivaldybe.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_SAVIVALDYBE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_seniunijos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_seniunijos_kodas!= null && orfd_seniunijos_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_SENIUNIJOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_seniunija_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_seniunija!= null && orfd_seniunija.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_SENIUNIJA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_gyv_vietos_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_gyv_vietos_kodas!= null && orfd_gyv_vietos_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_GYV_VIETOS_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_gyv_vieta_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_gyv_vieta!= null && orfd_gyv_vieta.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_GYV_VIETA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_gatves_kodas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_gatves_kodas!= null && orfd_gatves_kodas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_GATVES_KODAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_gatve_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_gatve!= null && orfd_gatve.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_GATVE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_pastato_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_pastato_nr!= null && orfd_pastato_nr.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PASTATO_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_korpuso_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_korpuso_nr!= null && orfd_korpuso_nr.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_KORPUSO_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_buto_nr_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_buto_nr!= null && orfd_buto_nr.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_BUTO_NR", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_paslauga_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_paslauga!= null && orfd_paslauga.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PASLAUGA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_uzsakymo_data_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_uzsakymo_data!= null && orfd_uzsakymo_data.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_UZSAKYMO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_atlikimo_data_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_atlikimo_data!= null && orfd_atlikimo_data.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ATLIKIMO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_isvezimo_data_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_isvezimo_data!= null && orfd_isvezimo_data.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ISVEZIMO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_isveztas_kiekis_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_isveztas_kiekis!= null && orfd_isveztas_kiekis.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ISVEZTAS_KIEKIS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_transporto_priemone_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_transporto_priemone!= null && orfd_transporto_priemone.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_TRANSPORTO_PRIEMONE", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_cr_id_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_cr_id!= null && (""+orfd_cr_id.intValue()).length()>10) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_CR_ID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"10"));
         }
      }
      if (orfd_uzsakymo_informacija_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_uzsakymo_informacija!= null && orfd_uzsakymo_informacija.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_UZSAKYMO_INFORMACIJA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_atlikti_darbai_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_atlikti_darbai!= null && orfd_atlikti_darbai.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_ATLIKTI_DARBAI", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_uzsakovas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_uzsakovas!= null && orfd_uzsakovas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_UZSAKOVAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_uzsakovo_email_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_uzsakovo_email!= null && orfd_uzsakovo_email.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_UZSAKOVO_EMAIL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_uzsakovo_tel_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_uzsakovo_tel!= null && orfd_uzsakovo_tel.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_UZSAKOVO_TEL", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_uzsakovo_komentaras_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_uzsakovo_komentaras!= null && orfd_uzsakovo_komentaras.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_UZSAKOVO_KOMENTARAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_laboratorijos_komentaras_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_laboratorijos_komentaras!= null && orfd_laboratorijos_komentaras.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_LABORATORIJOS_KOMENTARAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_deguonis_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_deguonis!= null && orfd_deguonis.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_DEGUONIS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_skendincios_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_skendincios!= null && orfd_skendincios.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_SKENDINCIOS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_azotas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_azotas!= null && orfd_azotas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_AZOTAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_fosforas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_fosforas!= null && orfd_fosforas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_FOSFORAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_meginio_data_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_meginio_data!= null && orfd_meginio_data.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_MEGINIO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_meginio_darbuotojas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_meginio_darbuotojas!= null && orfd_meginio_darbuotojas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_MEGINIO_DARBUOTOJAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_tyrimo_data_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_tyrimo_data!= null && orfd_tyrimo_data.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_TYRIMO_DATA", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_tyrimo_darbuotojas_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_tyrimo_darbuotojas!= null && orfd_tyrimo_darbuotojas.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_TYRIMO_DARBUOTOJAS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (orfd_pastaba_rezultatams_changed || operation == Utils.OPERATION_INSERT) {
         if (orfd_pastaba_rezultatams!= null && orfd_pastaba_rezultatams.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "ORFD_PASTABA_REZULTATAMS", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (rec_version_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_version!= null && (""+rec_version.intValue()).length()>6) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "REC_VERSION", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"6"));
         }
      }
      if (rec_userid_changed || operation == Utils.OPERATION_INSERT) {
         if (rec_userid!= null && rec_userid.length()>128) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "REC_USERID", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"128"));
         }
      }
      if (c01_changed || operation == Utils.OPERATION_INSERT) {
         if (c01!= null && c01.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "C01", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c02_changed || operation == Utils.OPERATION_INSERT) {
         if (c02!= null && c02.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "C02", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c03_changed || operation == Utils.OPERATION_INSERT) {
         if (c03!= null && c03.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "C03", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c04_changed || operation == Utils.OPERATION_INSERT) {
         if (c04!= null && c04.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "C04", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
      if (c05_changed || operation == Utils.OPERATION_INSERT) {
         if (c05!= null && c05.length()>2147483647) {
            throw new SparkBusinessException(new S2Message("NTIS_ORDER_FILE_DATA", "C05", DB_ERROR_LENGTH_PATH, SparkMessageType.ERROR,"2147483647"));
         }
      }
   }

   public UpdateStatementPart constructUpdatePart(String userName) {
      UpdateStatementPart updateStatementPart = new UpdateStatementPart();
      String answer = "UPDATE NTIS_ORDER_FILE_DATA SET ";
      boolean changedExists = false;      if (orfd_eil_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_EIL_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_eil_nr);
      }
      if (orfd_wtf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_WTF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_wtf_id);
      }
      if (orfd_orf_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ORF_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_orf_id);
      }
      if (orfd_org_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ORG_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_org_id);
      }
      if (orfd_paslaugos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PASLAUGOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_paslaugos_kodas);
      }
      if (orfd_pastato_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PASTATO_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_pastato_kodas);
      }
      if (orfd_patalpos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PATALPOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_patalpos_kodas);
      }
      if (orfd_pastato_adr_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PASTATO_ADR_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_pastato_adr_kodas);
      }
      if (orfd_adresas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ADRESAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_adresas);
      }
      if (orfd_savivaldybes_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_SAVIVALDYBES_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_savivaldybes_kodas);
      }
      if (orfd_savivaldybe_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_SAVIVALDYBE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_savivaldybe);
      }
      if (orfd_seniunijos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_SENIUNIJOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_seniunijos_kodas);
      }
      if (orfd_seniunija_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_SENIUNIJA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_seniunija);
      }
      if (orfd_gyv_vietos_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_GYV_VIETOS_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_gyv_vietos_kodas);
      }
      if (orfd_gyv_vieta_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_GYV_VIETA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_gyv_vieta);
      }
      if (orfd_gatves_kodas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_GATVES_KODAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_gatves_kodas);
      }
      if (orfd_gatve_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_GATVE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_gatve);
      }
      if (orfd_pastato_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PASTATO_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_pastato_nr);
      }
      if (orfd_korpuso_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_KORPUSO_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_korpuso_nr);
      }
      if (orfd_buto_nr_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_BUTO_NR = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_buto_nr);
      }
      if (orfd_paslauga_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PASLAUGA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_paslauga);
      }
      if (orfd_uzsakymo_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_UZSAKYMO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_uzsakymo_data);
      }
      if (orfd_atlikimo_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ATLIKIMO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_atlikimo_data);
      }
      if (orfd_isvezimo_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ISVEZIMO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_isvezimo_data);
      }
      if (orfd_isveztas_kiekis_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ISVEZTAS_KIEKIS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_isveztas_kiekis);
      }
      if (orfd_transporto_priemone_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_TRANSPORTO_PRIEMONE = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_transporto_priemone);
      }
      if (orfd_cr_id_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_CR_ID = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_cr_id);
      }
      if (orfd_uzsakymo_informacija_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_UZSAKYMO_INFORMACIJA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_uzsakymo_informacija);
      }
      if (orfd_atlikti_darbai_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_ATLIKTI_DARBAI = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_atlikti_darbai);
      }
      if (orfd_uzsakovas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_UZSAKOVAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_uzsakovas);
      }
      if (orfd_uzsakovo_email_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_UZSAKOVO_EMAIL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_uzsakovo_email);
      }
      if (orfd_uzsakovo_tel_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_UZSAKOVO_TEL = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_uzsakovo_tel);
      }
      if (orfd_uzsakovo_komentaras_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_UZSAKOVO_KOMENTARAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_uzsakovo_komentaras);
      }
      if (orfd_laboratorijos_komentaras_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_LABORATORIJOS_KOMENTARAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_laboratorijos_komentaras);
      }
      if (orfd_deguonis_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_DEGUONIS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_deguonis);
      }
      if (orfd_skendincios_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_SKENDINCIOS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_skendincios);
      }
      if (orfd_azotas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_AZOTAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_azotas);
      }
      if (orfd_fosforas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_FOSFORAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_fosforas);
      }
      if (orfd_meginio_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_MEGINIO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_meginio_data);
      }
      if (orfd_meginio_darbuotojas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_MEGINIO_DARBUOTOJAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_meginio_darbuotojas);
      }
      if (orfd_tyrimo_data_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_TYRIMO_DATA = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_tyrimo_data);
      }
      if (orfd_tyrimo_darbuotojas_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_TYRIMO_DARBUOTOJAS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_tyrimo_darbuotojas);
      }
      if (orfd_pastaba_rezultatams_changed) {
         answer = answer +  (changedExists? COMMA : EMPTY_STRING)+"ORFD_PASTABA_REZULTATAMS = ? ";
         changedExists = true;
         updateStatementPart.addStatementParam(orfd_pastaba_rezultatams);
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
      answer = answer +" WHERE  ORFD_ID = ? ";
      updateStatementPart.addStatementParam(orfd_id);
      updateStatementPart.setStatementPart(answer);
      return updateStatementPart;
   }
   public String toString() {
      return "{\"NtisOrderFileDataDAO\":{\"orfd_id\": \""+getOrfd_id()+"\", \"orfd_eil_nr\": \""+getOrfd_eil_nr()+"\", \"orfd_wtf_id\": \""+getOrfd_wtf_id()+"\", \"orfd_orf_id\": \""+getOrfd_orf_id()+"\", \"orfd_org_id\": \""+getOrfd_org_id()+"\", \"orfd_paslaugos_kodas\": \""+getOrfd_paslaugos_kodas()+"\", \"orfd_pastato_kodas\": \""+getOrfd_pastato_kodas()+"\", \"orfd_patalpos_kodas\": \""+getOrfd_patalpos_kodas()+"\", \"orfd_pastato_adr_kodas\": \""+getOrfd_pastato_adr_kodas()+"\", \"orfd_adresas\": \""+getOrfd_adresas()+"\", \"orfd_savivaldybes_kodas\": \""+getOrfd_savivaldybes_kodas()+"\", \"orfd_savivaldybe\": \""+getOrfd_savivaldybe()+"\", \"orfd_seniunijos_kodas\": \""+getOrfd_seniunijos_kodas()+"\", \"orfd_seniunija\": \""+getOrfd_seniunija()+"\", \"orfd_gyv_vietos_kodas\": \""+getOrfd_gyv_vietos_kodas()+"\", \"orfd_gyv_vieta\": \""+getOrfd_gyv_vieta()+"\", \"orfd_gatves_kodas\": \""+getOrfd_gatves_kodas()+"\", \"orfd_gatve\": \""+getOrfd_gatve()+"\", \"orfd_pastato_nr\": \""+getOrfd_pastato_nr()+"\", \"orfd_korpuso_nr\": \""+getOrfd_korpuso_nr()+"\", \"orfd_buto_nr\": \""+getOrfd_buto_nr()+"\", \"orfd_paslauga\": \""+getOrfd_paslauga()+"\", \"orfd_uzsakymo_data\": \""+getOrfd_uzsakymo_data()+"\", \"orfd_atlikimo_data\": \""+getOrfd_atlikimo_data()+"\", \"orfd_isvezimo_data\": \""+getOrfd_isvezimo_data()+"\", \"orfd_isveztas_kiekis\": \""+getOrfd_isveztas_kiekis()+"\", \"orfd_transporto_priemone\": \""+getOrfd_transporto_priemone()+"\", \"orfd_cr_id\": \""+getOrfd_cr_id()+"\", \"orfd_uzsakymo_informacija\": \""+getOrfd_uzsakymo_informacija()+"\", \"orfd_atlikti_darbai\": \""+getOrfd_atlikti_darbai()+"\", \"orfd_uzsakovas\": \""+getOrfd_uzsakovas()+"\", \"orfd_uzsakovo_email\": \""+getOrfd_uzsakovo_email()+"\", \"orfd_uzsakovo_tel\": \""+getOrfd_uzsakovo_tel()+"\", \"orfd_uzsakovo_komentaras\": \""+getOrfd_uzsakovo_komentaras()+"\", \"orfd_laboratorijos_komentaras\": \""+getOrfd_laboratorijos_komentaras()+"\", \"orfd_deguonis\": \""+getOrfd_deguonis()+"\", \"orfd_skendincios\": \""+getOrfd_skendincios()+"\", \"orfd_azotas\": \""+getOrfd_azotas()+"\", \"orfd_fosforas\": \""+getOrfd_fosforas()+"\", \"orfd_meginio_data\": \""+getOrfd_meginio_data()+"\", \"orfd_meginio_darbuotojas\": \""+getOrfd_meginio_darbuotojas()+"\", \"orfd_tyrimo_data\": \""+getOrfd_tyrimo_data()+"\", \"orfd_tyrimo_darbuotojas\": \""+getOrfd_tyrimo_darbuotojas()+"\", \"orfd_pastaba_rezultatams\": \""+getOrfd_pastaba_rezultatams()+"\", \"rec_version\": \""+getRec_version()+"\", \"rec_create_timestamp\": \""+getRec_create_timestamp()+"\", \"rec_userid\": \""+getRec_userid()+"\", \"rec_timestamp\": \""+getRec_timestamp()+"\", \"n01\": \""+getN01()+"\", \"n02\": \""+getN02()+"\", \"n03\": \""+getN03()+"\", \"n04\": \""+getN04()+"\", \"n05\": \""+getN05()+"\", \"c01\": \""+getC01()+"\", \"c02\": \""+getC02()+"\", \"c03\": \""+getC03()+"\", \"c04\": \""+getC04()+"\", \"c05\": \""+getC05()+"\", \"d01\": \""+getD01()+"\", \"d02\": \""+getD02()+"\", \"d03\": \""+getD03()+"\", \"d04\": \""+getD04()+"\", \"d05\": \""+getD05()+"\"}}";
   }

}