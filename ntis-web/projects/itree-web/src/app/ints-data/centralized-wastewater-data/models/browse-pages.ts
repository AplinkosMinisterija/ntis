import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface CentralizedWastewaterDataReportBrowseRow extends BaseBrowseRow {
  org_id: number;
  org_name: string;
  date_in_period: Date;
  state_in_period: string;
  latest_date: Date;
  state_latest_date: string;
  org_address: string;
  org_delegation_person: string;
  org_email: string;
  org_phone: string;
  org_website: string;
}

export interface CwDataListBrowseRow extends BaseBrowseRow {
  cwf_id: number;
  cwf_status: string;
  cwf_import_date: Date;
  cwf_fil_id: number;
  usr_person_name: string;
  usr_person_surname: string;
  usr_phone_number: string;
  usr_email: string;
  org_name: string;
  fil_content_type: string;
  fil_key: string;
  fil_name: string;
  fil_size: number;
  fil_status: string;
  total_errors: number;
  fil_status_date: Date;
}

export interface WastewaterFileErrorListRow extends BaseBrowseRow {
  cwfde_column_nr: number;
  cwfde_column_value: string;
  cwfde_msg_code: string;
  cwfde_msg_text: string;
  cwfd_eil_nr: number;
}

export interface WastewaterFileDataListRow {
  cwfd_eil_nr: number;
  cwfd_pastato_kodas: string;
  cwfd_patalpos_kodas: string;
  cwfd_pastato_adr_kodas: string;
  cwfd_adresas: string;
  cwfd_nuot_salinimo_budas: string;
  cwfd_prijungimo_data: Date;
  cwfd_atjungimo_data: Date;
}

export interface CwAddressMappingsBrowseRow extends BaseBrowseRow {
  am_id: number;
  am_address_type: string;
  am_address_type_code: string;
  am_provided_addres_name: string;
  org_name: string;
  am_municipality_code: string;
  am_municipality_name: string;
  am_sen_id: number;
  sen_name: string;
  am_str_id: number;
  str_name: string;
  am_org_id: number;
  am_re_id: number;
}
