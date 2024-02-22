import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface ResearchNormsBrowseRow extends BaseBrowseRow {
  rn_id: number;
  rn_research_type: string;
  research_type_clsf: string;
  rn_research_norm: number;
  rn_facility_installation_date: string;
  rn_date_from: string;
  rn_date_to: string;
  installation_clsf: string;
}

export interface SpResearchOrdersListBrowseRow extends BaseBrowseRow {
  ord_id: number;
  ord_created: Date;
  ord_created_in_ntis_portal: string;
  ord_state: string;
  ord_state_clsf: string;
  orderer: string;
}

export interface OwnerResearchOrdersListBrowseRow extends BaseBrowseRow {
  ord_id: string;
  ord_created: string;
  org_name: string;
  ord_compliance_norms: string;
  ord_state: string;
  rev_id: string;
  rev_score: number;
}

export interface ResearchNormsHistoryBrowseRow extends BaseBrowseRow {
  research_type: string;
  research_norm: string;
  facility_installation: string;
  date_from: Date;
  date_to: Date;
}

export interface ResearchesListBrowseRow extends BaseBrowseRow {
  org_name: string;
  org_email: string;
  org_phone: string;
  srv_price_from: number;
  srv_description: string;
  srv_id: number;
}
