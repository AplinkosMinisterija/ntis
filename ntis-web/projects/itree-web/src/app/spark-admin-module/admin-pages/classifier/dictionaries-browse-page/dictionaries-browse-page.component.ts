import { Component, OnInit } from '@angular/core';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AdminService } from '../../../admin-services/admin.service';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { FormControl } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { tap } from 'rxjs';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { Table } from 'primeng/table';
import { SPR_REF_DICTIONARIES_LIST } from '@itree-commons/src/constants/forms.constants';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SprDictionariesBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-dictionaries-browse-page',
  templateUrl: './dictionaries-browse-page.component.html',
  styleUrls: ['./dictionaries-browse-page.component.scss'],
})
export class DictionariesBrowsePageComponent extends BaseBrowseForm<SprDictionariesBrowseRow> implements OnInit {
  readonly formCode = SPR_REF_DICTIONARIES_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  classifierEditLink = RoutingConst.EDIT;
  rows = false;
  rowMenuItems: Record<string, MenuItem[]> = {};

  exportData: SprDictionariesBrowseRow[] = [];

  cols: TableColumn[] = [
    { field: 'rfd_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfd_subsystem', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfd_table_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfd_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfd_description', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'rfd_code_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfd_code_length', export: true, visible: true, type: DATA_TYPE_NUMBER },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(protected override commonFormServices: CommonFormServices, private adminService: AdminService) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('rfd_id', new FormControl(''));
    this.searchForm.addControl('rfd_subsystem', new FormControl(''));
    this.searchForm.addControl('rfd_table_name', new FormControl(''));
    this.searchForm.addControl('rfd_name', new FormControl(''));
    this.searchForm.addControl('rfd_code_type', new FormControl(''));
    this.searchForm.addControl('rfd_code_length', new FormControl(''));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, string>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.adminService
      .getDictionaryList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.rfd_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprDictionariesBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getDictionaryList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprDictionariesBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(id: string): void {
    this.adminService.delDictionaryRecord(id).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((txt: string) => {
        this.commonFormServices.appMessages.showSuccess('', txt);
      });
    });
  }

  navigateToEdit(id: string): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, RoutingConst.EDIT, id]);
  }

  getActions(row: SprDictionariesBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const rfd_id: string = row.rfd_id.toString();
    const actions: ActionsEnum[] = [...row.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant(
            'common.action.' + action.toString().toLowerCase()
          ) as string,
          id: rfd_id,
          icon: getActionIcons(action),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              this.navigateToEdit(rfd_id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(rfd_id);
            } else if (action === ActionsEnum.SET_CODES_AND_TRANSLATIONS) {
              void this.commonFormServices.router.navigate([
                this.commonFormServices.router.url,
                RoutingConst.SPARK_REF_CODES_BROWSE,
                rfd_id,
                row.rfd_table_name,
              ]);
            }
          },
        });
      });
    return menu;
  }
}
