import { BaseBrowseRow } from '@itree-commons/src/lib/model/browse-pages';

export interface ContractsListBrowseRow extends BaseBrowseRow {
  cot_id: number;
  cot_code: string;
  cot_created: Date;
  cot_services: string;
  cot_services_json: string;
  cot_service_provider: string;
  cot_customer: string;
  cot_per_id: number;
  cot_org_id: number;
  cot_state: string;
  cot_method: string;
  cot_method_code: string;
}

export interface WastewaterTreatmentFacility {
  wtf_id: number;
  wtf_type: string;
  wtf_type_name: string;
  address: string;
}
