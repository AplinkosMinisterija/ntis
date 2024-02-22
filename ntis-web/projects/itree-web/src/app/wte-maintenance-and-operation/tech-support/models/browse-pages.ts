import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';
import { NtisTableRowActionsItem } from '../../../ntis-shared/models/table-row-actions';

export interface OwnerTechOrdersBrowseRow extends BaseBrowseRow {
  ord_id: number;
  ord_created: string;
  org_name: string;
  ord_state: string;
  ocw_completed_date: string;
  rev_id: string;
  rev_score: number;
  actions: NtisTableRowActionsItem[];
}

export interface OwnerDisposalOrdersBrowseRow extends BaseBrowseRow {
  ord_id: number;
  ord_created: string;
  org_name: string;
  ord_state: string;
  ocw_discharged_sludge_amount: number;
  ocw_completed_date: string;
  rev_id: string;
  rev_score: number;
  actions: NtisTableRowActionsItem[];
}

export interface SelectOption {
  option: string;
  value: number;
}
