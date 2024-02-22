import { RefTranslationsObj, UserRoleRequest } from '@itree-commons/src/lib/model/api/api';
import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface OrgUserRolesDetails extends BaseBrowseRow {
  user_id: number;
  our_date_from: string;
  our_date_to: string;
  our_usr_id: number;
  our_id: number;
  ou_id: number;
  rol_code: string;
  rol_type: string;
  rol_name: string;
  rol_description: string;
  rol_id: number;
}

export interface RoleFormActions {
  fra_code: string;
  fra_id: string;
  fra_name: string;
  rda_fra_id: string;
}

export interface RoleFormActionsSelected {
  fra_code: string;
  fra_id: string;
  fra_name: string;
  rda_fra_id: string;
  select: boolean;
}

export interface SprClosedSessionsBrowseRow {
  sec_id: number;
  sec_username: string;
  usr_person_name: string;
  usr_person_surname: string;
  sec_login_time: string;
  sec_logout_time: string;
  sec_last_action_time: string;
  sec_logout_cause: string;
  org_name: string;
}

export interface SprCodesTranslationsBrowseRow extends BaseBrowseRow {
  rfc_id: number;
  rfc_code: string;
  rfc_domain: string;
  rfc_meaning: string;
  rfc_rfd_id: string;
  rfc_parent_domain: string;
  rfc_parent_domain_code: string;
  rfc_description: string;
  rfc_order: number;
  rfc_date_from: Date;
  rfc_date_to: Date;
  rft_id?: number;
  rft_lang?: string;
  rft_display_code?: string;
  rft_description?: string;
  rft_rfc_id?: number;
}

export interface SprDictionariesBrowseRow extends BaseBrowseRow {
  rec_create_timestamp: string;
  rfd_id: string;
  rfd_subsystem: string;
  rfd_table_name: string;
  rfd_code_type: string;
  rfd_code_length: number;
  rfd_code_colname: string;
  rfd_desc_colname: string;
}

export interface SprOpenSessionsBrowseRow {
  ses_id: string;
  ses_username: string;
  usr_person_name: string;
  usr_person_surname: string;
  ses_login_time: Date;
  ses_last_action_time: Date;
  org_name: string;
  ses_key: string;
}

export interface SprOrgUsersListRow extends BaseBrowseRow {
  ou_date_from: Date;
  ou_date_to: Date;
  ou_org_id: string;
  ou_id: string;
  ou_position: string;
  ou_position_code: string;
  usr_email?: string;
  usr_person_surname?: string;
  usr_person_name?: string;
  usr_username?: string;
  usr_id: string;
  belongs?: string;
  isSelected?: boolean;
  update?: boolean;
}

export interface SprUserOrgsListRow extends BaseBrowseRow {
  ou_date_from: Date;
  ou_date_to: Date;
  ou_usr_id: string;
  ou_id: string;
  ou_position: string;
  ou_position_code: string;
  org_email?: string;
  org_code?: string;
  org_name?: string;
  org_phone?: string;
  org_id: string;
  belongs?: string;
  isSelected?: boolean;
  update?: boolean;
}

export interface RefCodesClassifierObj extends BaseBrowseRow {
  rfc_id: number;
  rfc_domain: string;
  rfc_code: string;
  rfc_parent_domain: string;
  rfc_parent_domain_code: string;
  rfc_rfd_id: string;
  rfc_meaning: string;
  rfc_description: string;
  rfc_order: number;
  rfc_date_from: Date;
  rfc_date_to: Date;
  rft: RefTranslationsObj[];
}

export interface RefCodesClassifierObj {
  rfc_code: string;
  rfc_description: string;
  rfc_domain: string;
  rfc_id: number;
  rfc_meaning: string;
  rfc_order: number;
  rfc_parent_domain: string;
  rfc_parent_domain_code: string;
  rft: RefTranslationsObj[];
}

export interface UserRolesDetails extends BaseBrowseRow {
  uro_date_from: Date;
  uro_date_to: Date;
  uro_rol_id: string;
  uro_usr_id: string;
  uro_id: string;
  rol_code: string;
  rol_type: string;
  rol_name: string;
  rol_description: string;
  rol_id: string;
  belongs: string;
}

export interface RoleActionsRoleDetails extends BaseBrowseRow {
  roa_date_from: string;
  roa_date_to: string;
  roa_rol_id: string;
  roa_id: string;
  roa_assigned_rol_id: string;
  rol_code: string;
  rol_type: string;
  rol_name: string;
  rol_description: string;
  rol_id: string;
  belongs: string;
}

export interface SprRoleOrganizationsBrowseRow extends BaseBrowseRow {
  org_id: string;
  oar_id: string;
  oar_date_from: Date;
  oar_date_to: Date;
  org_code: string;
  org_name: string;
  org_email: string;
  org_phone: string;
  belongs?: string;
  isSelected?: boolean;
}

export interface RoleUsersDetails extends BaseBrowseRow {
  belongs: string;
  isSelected: boolean;
  org_name: string;
  uro_date_from: Date;
  uro_date_to: Date;
  uro_id: string;
  uro_rol_id: string;
  usr_id: string;
  usr_person_name: string;
  usr_person_surname: string;
  usr_type: string;
  usr_username: string;
}

export interface PropertiesBrowse extends BaseBrowseRow {
  prp_id: string;
  prp_name: string;
  prp_value: string;
  prp_type: string;
  prp_guid: string;
  prp_install_instance: string;
}

export interface SprMenuStructureBrowseRow extends BaseBrowseRow {
  id: string;
  mst_title: string;
  mst_site: string;
  mst_uri: string;
  mst_lang: string;
  mst_icon: string;
  mst_is_public: string;
  mst_date_from: string;
  mst_date_to: string;
}

export interface SprRolesBrowseRow extends BaseBrowseRow {
  id: string;
  rol_code: string;
  rol_name: string;
  rol_date_from: string;
  rol_date_to: string;
  rol_type: string;
}

export interface SprUsersBrowseOrgListRow {
  ou_id: string;
  org_code: string;
  org_name: string;
  org_date_from: string;
  org_date_to: string;
}

export interface SprUsersBrowseRow extends BaseBrowseRow {
  ou_id: string;
  usr_id: string;
  new_user: string;
  per_code: string;
  per_data_confirmed: string;
  usr_username: string;
  usr_email: string;
  usr_person_name: string;
  usr_person_surname: string;
  org_name: string;
  usr_date_from: string;
  usr_date_to: string;
  usr_type: string;
  usr_lock_date: string;
  org_list: SprUsersBrowseOrgListRow[] | string;
}

export interface SprJobsBrowseRow extends BaseBrowseRow {
  jde_id: string;
  jde_name: string;
  jde_type: string;
  jde_code: string;
  jde_status: string;
  jde_last_action_time: string;
  jde_next_action_time: string;
}

export interface SprJobRequestsJobItem {
  jde_id: string;
  jde_name: string;
}

export interface SprJobRequestExecutionsRow {
  jre_time: string;
  jre_name: string;
  jre_event_info: string;
  jre_err: string;
}

export interface SprJobRequestsBrowseRow extends BaseBrowseRow {
  jrq_id: string;
  jrq_status: string;
  jrq_code: string;
  jrq_start_date: string;
  jrq_end_date: string;
  jrq_executer_name: string;
  executions: SprJobRequestExecutionsRow[];
}

export interface SprTemplatesBrowseRow extends BaseBrowseRow {
  tml_id: string;
  tml_type: string;
  tml_code: string;
  tml_status: string;
  tml_name: string;
  tml_description: string;
}

export interface SprOrganizationsBrowseRow extends BaseBrowseRow {
  org_id: string;
  org_type: string;
  org_code: string;
  org_name: string;
  org_address: string;
  org_email: string;
  org_phone: string;
}

export interface SprOrgAvailableRolesBrowseRow extends BaseBrowseRow {
  oar_date_from: Date;
  oar_date_to: Date;
  oar_org_id: string;
  oar_id: string;
  rol_code: string;
  rol_type: string;
  rol_name: string;
  rol_description: string;
  rol_id: string;
  belongs?: string;
  isSelected?: boolean;
}

export interface SprFormsBrowseRow extends BaseBrowseRow {
  frm_id: number;
  frm_code: string;
  frm_name: string;
  frm_description: string;
  rec_create_timestamp: Date;
}

export interface SprPersonBrowseRow extends BaseBrowseRow {
  per_id: number;
  per_name: string;
  per_surname: string;
  per_date_of_birth: Date;
  per_email: string;
  rec_create_timestamp: Date;
}

export interface SprRoleActions extends BaseBrowseRow {
  frm_code: string;
  frm_name: string;
  roa_date_from: Date;
  roa_date_to: Date;
  roa_rol_id: string;
  roa_id: string;
  frm_id: number;
  belongs: string;
  select: boolean;
  roa_default_menu_item: string;
}

export interface SprAuditableTablesBrowseRow extends BaseBrowseRow {
  aut_id: string;
  aut_table_schema: string;
  aut_table_name: string;
  aut_trigger_enabled: string;
}

export interface SprUserApiKeyBrowseRow {
  api_id: number;
  api_key: string;
  api_date_from: string;
  api_date_to: string;
  api_on: string;
  last_api_date: Date;
}

export interface SprApiKeyBrowseRow extends BaseBrowseRow {
  api_id: number;
  api_belongs: string;
  usr_username: string;
  per_name: string;
  per_surname: string;
  org_name: string;
  last_date: Date;
  api_list: SprUserApiKeyBrowseRow[] | string;
}

export interface UserRequest extends Omit<UserRoleRequest, 'update'> {
  isSelected?: boolean;
  update?: boolean;
}

export interface NewsBrowseRow extends BaseBrowseRow {
  nw_id: number;
  nw_publication_date: string;
  nw_summary: string;
  nw_title: string;
  nw_text: string;
  nw_type: string;
  is_public: string;
  is_template: string;
  page_template: string;
  page_template_meaning: string;
}

export interface AdminReviewsBrowseRow extends BaseBrowseRow {
  rev_id: number;
  rev_score: number;
  rev_comment: string;
  rev_completed_date: string;
  org_name: string;
  org_type: string;
  rev_admin_read: string;
}
