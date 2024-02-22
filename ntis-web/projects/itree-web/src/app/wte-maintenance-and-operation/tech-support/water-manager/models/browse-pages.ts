import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';

export interface SludgeTreatmentDeliveriesBrowseRow extends BaseBrowseRow {
  wd_id: number;
  wd_delivery_date: Date;
  wd_delivered_quantity: number;
  cr_reg_no: string;
  wd_state: string;
  wd_state_meaning: string;
  org_name: string;
  actions?: NtisTableRowActionsItem[];
}

export interface WmDashboardRow {
  carrier: string;
  org_id: string;
  total_submitted: number;
}

export interface WmGraphBar {
  count: number;
  label: string;
}
