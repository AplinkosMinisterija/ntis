import { Spr_paging_ot } from '@itree/ngx-s2-commons';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { FilterInputType } from '../enums/input-type.enums';
import { ActionsEnum } from '../enums/table-row-actions.enums';
import { Serializable } from './api/api';

export interface BaseBrowseRow {
  availableActions: ActionsEnum[];
  actions?: NtisTableRowActionsItem[];
}

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
}

export interface MenuStructure extends Serializable {
  iconName: string;
  level: string;
  link: string;
  name: string;
  row: MenuStructure[];
  rows: MenuStructure[];
  type: string;
  toolTip: string;
  title: string;
  code: string;
}

export interface SprNotificationsBrowseRow extends BaseBrowseRow {
  ntf_creation_date: Date;
  ntf_id: number;
  ntf_mark_as_read_date: Date;
  not_read: string;
  ntf_message: string;
  ntf_reference: number;
  ntf_title: string;
  ntf_type: string;
  ntf_usr_id: number;
}
