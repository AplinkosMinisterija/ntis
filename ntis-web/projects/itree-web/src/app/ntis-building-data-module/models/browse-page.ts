import { FilterInputType } from '@itree-commons/src/lib/enums/input-type.enums';
import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';
import { Spr_paging_ot } from '@itree/ngx-s2-commons';

export interface BrowseFormSearchPostBody {
  pagingParams: Spr_paging_ot;
  params: [string, unknown][];
}

export interface TableColumn {
  field: string;
  export: boolean;
  visible: boolean;
  type?: string;
  textLength?: number;
  filterable?: boolean;
  filterType?: FilterInputType;
  filterDomain?: string;
  customDomain?: boolean;
  customDomainParameters?: Record<string, string>;
}

export interface NtisWastewaterTreatmentFaciBrowseRow extends BaseBrowseRow {
  wtf_served_objects: string;
  wtf_fl_address: string;
  wtf_fl_latitude: string;
  wtf_fl_longitude: string;
  wtf_id: number;
  wtf_state: string;
  wtf_type: string;
  wtf_fua_id: number;
  wtf_state_meaning: string;
}

export interface NtisWastewaterTreatmentFaciExportRow extends NtisWastewaterTreatmentFaciBrowseRow {
  coordinates: string;
  served_objects: string;
}

export interface NtisWastewaterFacilityView extends BaseBrowseRow {
  wtf_id: number;
  wtf_latitude: number;
  wtf_longitude: number;
  fl_ad_id: number;
  full_address_text: string;
  wtf_title: string;
  wtf_type: string;
  wtf_state: string;
  wtf_state_code: string;
  wtf_manufacturer: string;
  wtf_model: string;
  wtf_capacity: string;
  wtf_checkout_date: string;
  wtf_distance: number;
  wtf_installation_date: string;
  wtf_manufacturer_description: string;
  wtf_technical_passport_id: string;
  wtf_discharge_wastewater_type: string;
  dw_coordinate_latitude: number;
  dw_coordinate_longitude: number;
  wtf_served_objects_json: string;
  wtf_files_json: string;
  wtf_data_source: string;
  wtf_updated_at: string;
  wtf_updated_by: string;
  wtf_created_at: string;
  wtf_created_by: string;
  fam_pop_equivalent: number;
  fam_chds: number;
  fam_bds: number;
  fam_float_material: number;
  fam_phosphor: number;
  fam_nitrogen: number;
  wtf_identification_number: string;
  fam_manufacturer: string;
  fam_model: string;
  fam_description: string;
}

export interface WfManagersListRow extends BaseBrowseRow {
  manager: string;
  fo_id: number;
  status: string;
  date_from: Date;
  date_to: Date;
  full_date: string;
}

export class WtfServedObject {
  so_id: number;
  so_ad_id: number;
  so_address: string;
  so_latitude: number;
  so_longitude: number;
  so_inv_code: string;
  so_purp_name: string;
  fo_so_id?: number;
  so_building_no?: number;
  so_flat_no?: number;
}

export interface NtisServedObjects {
  so_ntr_building_id: number;
  so_ad_id: number;
  so_coordinate_latitude: number;
  so_coordinate_longitude: number;
  so_id: number;
  so_wtf_id: number;
}

export interface NtisWtfAgreements extends NtisWastewaterFacilityView {
  fua_id: number;
  fua_so_id: number;
  fua_wtf_id: number;
  fua_created: string;
  fua_state: string;
  fua_state_text: string;
  person: string;
  full_address_text: string;
  fua_type: string;
  fua_type_text: string;
  per_email: string;
  org_name: string;
  file_ot: string;
  fua_network_connection_date: string;
  per_phone_number: string;
  fua_wtf_old_info_json: string;
  fua_wtf_new_info_json: string;
  fua_wtf_object_info_json: string;
  fua_cancellation_reason: string;
  fam_pop_equivalent: number;
  fam_tech_pass: string;
  fam_chds: number;
  fam_bds: number;
  fam_float_material: number;
  fam_phosphor: number;
  fam_nitrogen: number;
  fam_manufacturer: string;
  fam_model: string;
  fam_description: string;
}
