import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface SubmittedAggloDataListRow extends BaseBrowseRow {
  id: number;
  municipality: string;
  status_code: string;
  status_date: string;
  status: string;
}
