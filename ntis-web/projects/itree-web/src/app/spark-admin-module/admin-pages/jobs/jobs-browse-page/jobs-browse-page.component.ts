import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_JOBS_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { ConfirmationService, MenuItem, PrimeIcons } from 'primeng/api';
import { Table } from 'primeng/table';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprJobsBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-jobs-browse-page',
  templateUrl: './jobs-browse-page.component.html',
  styleUrls: ['./jobs-browse-page.component.scss'],
})
export class JobsBrowsePageComponent extends BaseBrowseForm<SprJobsBrowseRow> implements OnInit {
  readonly formCode = SPR_JOBS_LIST;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly RoutingConst = RoutingConst;
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprJobsBrowseRow[] = [];
  cols: TableColumn[] = [
    { field: 'jde_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jde_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jde_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jde_status', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jde_name', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
    { field: 'jde_description', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
    { field: 'jde_last_action_time', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'jde_next_action_time', export: true, visible: true, type: DATA_TYPE_DATE },
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
    this.searchForm.addControl('jde_id', new FormControl(null));
    this.searchForm.addControl('jde_type', new FormControl(null));
    this.searchForm.addControl('jde_code', new FormControl(null));
    this.searchForm.addControl('jde_status', new FormControl(null));
    this.searchForm.addControl('jde_name', new FormControl(null));
    this.searchForm.addControl('jde_description', new FormControl(null));
    this.searchForm.addControl('jde_last_action_time', new FormControl(null));
    this.searchForm.addControl('jde_next_action_time', new FormControl(null));
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
      .getJobsList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.jde_id] = this.getActions(row);
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
        .getJobsList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprJobsBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recId: string): void {
    this.adminService.deleteJob(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  getActions(row: SprJobsBrowseRow): MenuItem[] {
    return row.availableActions.map((action) => {
      const menuItem: MenuItem = {
        label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
        icon: getActionIcons(action),
      };
      switch (action) {
        case ActionsEnum.ACTIONS_READ:
          menuItem.label = this.commonFormServices.translate.instant('common.generalUse.log') as string;
          menuItem.icon = PrimeIcons.BOOK;
          menuItem.command = (): void => {
            void this.router.navigate([this.router.url, RoutingConst.SPARK_JOB_REQUESTS, row.jde_id]);
          };
          break;
        case ActionsEnum.ACTIONS_UPDATE:
          menuItem.command = (): void => {
            void this.router.navigate([this.router.url, RoutingConst.EDIT, row.jde_id]);
          };
          break;
        case ActionsEnum.ACTIONS_COPY:
          menuItem.command = (): void => {
            void this.router.navigate([this.router.url, RoutingConst.EDIT, row.jde_id], {
              queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
            });
          };
          break;
        case ActionsEnum.ACTIONS_DELETE:
          menuItem.command = (): void => {
            this.deleteRecordWithConfirmation(row.jde_id);
          };
          break;
      }
      return menuItem;
    });
  }
}
