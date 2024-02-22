import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_MENU_STR_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  getLang,
} from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprMenuStructureBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-menu-structure-browse-page',
  templateUrl: './menu-structure-browse-page.component.html',
  styleUrls: ['./menu-structure-browse-page.component.scss'],
})
export class MenuStructureBrowsePageComponent extends BaseBrowseForm<SprMenuStructureBrowseRow> implements OnInit {
  readonly formCode = SPR_MENU_STR_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  menuEditLink = RoutingConst.EDIT;
  cols: TableColumn[];
  exportColumns: unknown;
  rows = false;
  menuActions: Record<string, MenuItem[]> = {};
  exportData: SprMenuStructureBrowseRow[] = [];

  constructor(
    protected override commonFormServices: CommonFormServices,
    public faIconsService: FaIconsService,
    private router: Router,
    private adminService: AdminService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('mst_id', new FormControl(null));
    this.searchForm.addControl('mst_title', new FormControl(''));
    this.searchForm.addControl('mst_lang', new FormControl(''));
    this.searchForm.addControl('mst_site', new FormControl(''));
    this.searchForm.addControl('mst_uri', new FormControl(''));
    this.searchForm.addControl('mst_is_public', new FormControl(''));
    this.searchForm.addControl('mst_date_from', new FormControl(''));
    this.searchForm.addControl('mst_date_to', new FormControl(''));
    this.searchForm.addControl('file', new FormControl(''));
    this.cols = [
      { field: 'id', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_title', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_site', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_uri', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_lang', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_icon', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_is_public', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'mst_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'mst_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
    ];

    this.exportColumns = this.cols.map((col) => ({
      title: this.commonFormServices.translate.instant('pages.sprMenuStructure.' + col.field) as string,
      dataKey: col.field,
    }));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const lang = getLang();
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.adminService
      .getMenuStructureList(pagingParams, params, lang, extendedParams)
      .pipe(
        tap((res) => {
          this.menuActions = {};
          res.data.forEach((row) => {
            this.menuActions[row.id] = this.getActions(row);
          });
          return res;
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprMenuStructureBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getMenuStructureList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          getLang(),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprMenuStructureBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recId: string): void {
    this.adminService.delMenuStructureRecord(recId).subscribe(() => {
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
      );
      this.reload();
    });
  }

  navigateToEdit(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.menuEditLink + '/' + recId]);
  }

  navigateToCopy(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.menuEditLink + '/' + recId], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  getActions(application: SprMenuStructureBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    application.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !application.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action: ActionsEnum) => {
        menu.push({
          label: this.commonFormServices.translate.instant(
            'common.action.' + action.toString().toLowerCase()
          ) as string,
          id: application.id,
          icon: getActionIcons(action),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              this.navigateToEdit(application.id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(application.id);
            } else if (action === ActionsEnum.ACTIONS_COPY) {
              this.navigateToCopy(application.id);
            }
          },
        });
      });
    return menu;
  }
}
