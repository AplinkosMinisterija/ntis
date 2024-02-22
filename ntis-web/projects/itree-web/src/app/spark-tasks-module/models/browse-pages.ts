import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface SprTasksBrowseRow extends BaseBrowseRow {
  id: string;
  tas_name: string;
  tas_type: string;
  tas_status: string;
  tas_date_from: string;
  tas_date_to: string;
  tas_reject_reason: string;
  tas_description: string;
}
