import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface SewageDeliveriesBrowseRow extends BaseBrowseRow {
  wd_id: number;
  wd_delivery_date: string;
  wd_delivered_quantity: number;
  cr_reg_no: string;
  wto_name: string;
  wd_state_clsf: string;
  wd_state: string;
  orders: string;
}

export interface ServiceProviderDashboardBrowse {
  service_provider: string;
  srv_type: string;
  service: string;
  total_submitted: number;
  total_confirmed: number;
}

export interface ServiceProviderFinishedGraphBar {
  order_count: number;
  label: string;
}

export interface ServiceProviderSludgeGraphBar {
  sludge_amount: number;
  label: string;
}

export interface OrdersListBrowseRow extends BaseBrowseRow {
  ord_id: number;
  ord_created: Date;
  ord_created_in_ntis_portal: string;
  ord_method: string;
  ord_customer: string;
  ord_state: string;
  ord_status_meaning: string;
  ord_removed_sewage_date: Date;
  ocw_discharged_sludge_amount: number;
  wd_id: number;
  wd_state: string;
  sewage_delivery_status: string;
}

export interface SelectOptionForGroup {
  label: string;
  value: string | number;
}

export interface GroupedSelectOption {
  group_key: string;
  values: string;
  optionList: SelectOptionForGroup[];
}
