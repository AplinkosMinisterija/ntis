import { NewsCommentModel, SprFile } from '@itree-commons/src/lib/model/api/api';

export interface MenuStructureForm {
  frm_id: string;
  frm_code: string;
  frm_name: string;
}

export interface SprDictionary {
  rfd_table_name: string;
  rfd_name: string;
}

export interface SprMenuStructureTreeItem {
  level: number;
  mst_id: number;
  mst_mst_id: number;
  menu_type: string;
  uri: string;
  icon: string;
  form: string;
  title: string;
}

export interface SprMenuStructureAdditionalTextItem {
  mst_id: string;
  mst_tooltip: string;
}

export interface SprOrganizationResult {
  skip: number;
  org_id: number;
  org_name: string;
  org_code: string;
}

export interface EnotarNewsEdit {
  attachment: SprFile[];
  comments: NewsCommentModel[];
  nwDateFrom: Date;
  nwDateTo: Date;
  nwId: number;
  nwLang: string;
  nwPublished: Date;
  nwSummary: string;
  nwText: string;
  nwTitle: string;
  nwType: string;
  favorite: boolean;
  isUsedForNewsCategory: boolean;
}
