import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';
import { Spr_paging_ot } from '@itree/ngx-s2-commons';

export interface BrowseFormSearchPostBody {
  pagingParams: Spr_paging_ot;
  params: [string, unknown][];
}

export interface NtisNotificationsBrowseRow extends BaseBrowseRow {
  ntf_id: number;
  ntf_creation_date: string;
  ntf_title: string;
  ntf_message: string;
  not_read: string;
  is_important: string;
}

export interface NtisOrdersImportBrowseRow extends BaseBrowseRow {
  orf_id: number;
  orf_import_date: string;
  orf_status_clsf: string;
  orf_status: string;
  orf_fil_id: number;
  usr_person_name: string;
  usr_person_surname: string;
  usr_email: string;
  usr_phone_number: string;
  srv_type: string;
  fil_name: string;
  fil_content_type: string;
  fil_key: string;
  fil_size: number;
  fil_status: string;
  total_errors: number;
  fil_status_date: Date;
}

export interface OrderImportFileDataListRow {
  orfd_eil_nr: number;
  wtf_address: string;
  orfd_uzsakymo_data: string;
  orfd_uzsakovo_tel: string;
  orfd_uzsakovo_email: string;
  orfd_atlikimo_data: string;
  orfd_atlikti_darbai: string;
  orfd_uzsakovo_komentaras: string;
}

export interface OrderImportResearchLinesListRow {
  orfd_eil_nr: number;
  wtf_address: string;
  orfd_uzsakymo_data: string;
  orfd_uzsakovo_tel: string;
  orfd_uzsakovo_email: string;
  orfd_uzsakovas: string;
  orfd_laboratorijos_komentaras: string;
  orfd_deguonis: number;
  orfd_skendincios: number;
  orfd_azotas: number;
  orfd_fosforas: number;
  orfd_meginio_data: string;
  orfd_meginio_darbuotojas: string;
  orfd_tyrimo_data: string;
  orfd_tyrimo_darbuotojas: string;
  orfd_pastaba_rezultatams: string;
}

export interface OrderImportDisposalLinesListRow {
  orfd_eil_nr: number;
  wtf_address: string;
  orfd_uzsakymo_data: string;
  orfd_uzsakovo_tel: string;
  orfd_uzsakovo_email: string;
  orfd_isvezimo_data: string;
  orfd_isveztas_kiekis: number;
  orfd_transporto_priemone: string;
  orfd_uzsakymo_informacija: string;
  orfd_uzsakovo_komentaras: string;
}

export interface OrderImportErrorDataListRow extends BaseBrowseRow {
  orfde_id: number;
  orfde_column_nr: number;
  orfde_column_value: string;
  orfde_msg_code: string;
  orfde_msg_text: string;
  orfd_eil_nr: number;
}

export interface FaqThemesBrowseRow {
  rfc_meaning: string;
  rfc_code: string;
}

export interface FaqBrowseRow extends BaseBrowseRow {
  fac_id: number;
  fac_group: string;
  fac_code: string;
  fac_question: string;
  fac_answer: string;
  attachment: SprFile[];
  link: string;
}
