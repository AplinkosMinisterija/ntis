import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface NtisCarsBrowseRow extends BaseBrowseRow {
  cr_id: string;
  cr_reg_no: string;
  cr_model: string;
  cr_capacity: string;
  cr_tube_length: string;
  cr_date_from: Date;
  cr_date_to: Date;
  cr_org_id: string;
  cr_used: string;
}

export interface ServiceRequestsBrowseRow extends BaseBrowseRow {
  sr_id: number;
  sr_org_id?: number;
  sr_registration_date: string;
  sr_req_applicant: string;
  sr_srv_provider?: string;
  sr_status: string;
  sr_status_meaning: string;
  sr_status_date: string;
  sr_usr_id?: number;
  sr_type: string;
}

export interface ServiceRequestItem {
  code: string;
  name: string;
  registered: boolean;
  exists?: string;
}

export interface NtisInstitutionsBrowseRow extends BaseBrowseRow {
  org_id: number;
  org_name: string;
  org_code: string;
  per_email: string;
  usr_id: number;
}

export interface WaterManagerFacilitiesBrowseRow extends BaseBrowseRow {
  wto_id: number;
  wto_name: string;
  wto_address: string;
  wto_productivity: number;
  wto_domestic_sewage: string;
  wto_industrial_sewage: string;
  wto_sludge: string;
  wto_is_it_used: string;
  address_id: number;
  municipality: number;
  city: number;
  street: number;
  building: string;
  wto_auto_accept: string;
  wto_no_notifications: string;
}

export interface ServiceProvidersBrowseRow extends BaseBrowseRow {
  org_id: number;
  org_name: string;
  org_code: string;
  org_address: string;
  org_email: string;
  org_phone: string;
  org_delegation_person: string;
  org_type: string;
  org_state: string;
  is_facility_installer: string;
  org_state_clsf: string;
  org_registered_date: Date;
  org_deregistered_date: Date;
  org_rejection_reason: string;
}

export interface TableColumn {
  field: string;
  export: boolean;
  visible: boolean;
  type?: string;
  textLength?: number;
}

export interface WaterManagerBrowseRow extends BaseBrowseRow {
  org_id: number;
  org_name: string;
  org_phone: string;
  org_email: string;
  org_website: string;
  facilities_info: string | WaterManagerFacility[];
}

export interface WaterManagerFacility {
  wto_id: number;
  wto_name: string;
  wto_address: string;
  wto_selected: string;
  isSelected: boolean;
}

export interface PriorityFacilitiesList {
  org_id: number;
  wto_id: number[];
}

export interface FacilityModelsBrowseRow extends BaseBrowseRow {
  rfc_id: number;
  rfc_manufa: string;
  rfc_meaning: string;
  rfc_description: string;
  rfc_date_from: Date;
  rfc_date_to: Date;
}

export interface SrvReviewsBrowseRow extends BaseBrowseRow {
  rev_id: number;
  rev_score: number;
  rev_comment: string;
  rev_receiver_read: string;
}
