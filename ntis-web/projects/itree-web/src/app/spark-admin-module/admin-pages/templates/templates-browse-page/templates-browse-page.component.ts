import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_TEMPLATES_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
} from '@itree/ngx-s2-commons';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprTemplatesBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-templates-browse-page',
  templateUrl: './templates-browse-page.component.html',
  styleUrls: ['./templates-browse-page.component.scss'],
})
export class TemplatesBrowsePageComponent extends BaseBrowseForm<SprTemplatesBrowseRow> implements OnInit {
  readonly formCode = SPR_TEMPLATES_LIST;
  readonly RoutingConst = RoutingConst;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprTemplatesBrowseRow[] = [];
  cols: TableColumn[] = [
    { field: 'tml_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'tml_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'tml_code', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 20 },
    { field: 'tml_status', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'tml_name', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 35 },
    { field: 'tml_description', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 90 },
  ];
  visibleColumns = this.cols.filter((col) => col.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('tml_id', new FormControl(null));
    this.searchForm.addControl('tml_type', new FormControl(null));
    this.searchForm.addControl('tml_code', new FormControl(null));
    this.searchForm.addControl('tml_status', new FormControl(null));
    this.searchForm.addControl('tml_name', new FormControl(null));
    this.searchForm.addControl('tml_description', new FormControl(null));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.adminService
      .getTemplatesList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.tml_id] = this.getActions(row);
          });
        })
      )
      .subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
        this.exportData = result.data;
      });
  }
  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getTemplatesList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprTemplatesBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recId: string): void {
    this.adminService.deleteTemplate(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  getActions(row: SprTemplatesBrowseRow): MenuItem[] {
    return row.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .map((action) => {
        const menuItem: MenuItem = {
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          id: row.tml_id,
          icon: getActionIcons(action),
        };
        switch (action) {
          case ActionsEnum.ACTIONS_UPDATE:
          case ActionsEnum.ACTIONS_READ:
            menuItem.command = (): void => {
              void this.router.navigate([this.router.url, RoutingConst.EDIT, row.tml_id]);
            };
            break;
          case ActionsEnum.ACTIONS_COPY:
            menuItem.command = (): void => {
              void this.router.navigate([this.router.url, RoutingConst.EDIT, row.tml_id], {
                queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
              });
            };
            break;
          case ActionsEnum.ACTIONS_DELETE:
            menuItem.command = (): void => {
              this.deleteRecordWithConfirmation(row.tml_id);
            };
            break;
        }
        return menuItem;
      });
  }
}
