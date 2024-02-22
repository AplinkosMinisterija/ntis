import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  ACTIONS_CREATE,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_TASKS_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { BaseBrowseForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { tap } from 'rxjs';
import { SprTasksBrowseRow } from '../../models/browse-pages';
import { TasksAdminService } from '../../task-services/tasks-admin.service';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-tasks-browse-page',
  templateUrl: './tasks-browse-page.component.html',
  styleUrls: ['./tasks-browse-page.component.scss'],
})
export class TasksBrowsePageComponent extends BaseBrowseForm<SprTasksBrowseRow> implements OnInit {
  readonly formCode = SPR_TASKS_LIST;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  editLink = RoutingConst.EDIT;
  newActionAvailable: boolean;
  cols: TableColumn[];
  visibleColumns: TableColumn[];
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprTasksBrowseRow[] = [];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private router: Router,
    private authService: AuthService,
    private adminService: TasksAdminService
  ) {
    super(commonFormServices);
    this.newActionAvailable = this.authService.isFormActionEnabled(SPR_TASKS_LIST, ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.cols = [
      { field: 'id', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'tas_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'tas_type', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'tas_status', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'tas_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'tas_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'tas_reject_reason', export: true, visible: false, type: DATA_TYPE_STRING },
      { field: 'tas_description', export: true, visible: false, type: DATA_TYPE_STRING },
    ];
    this.visibleColumns = this.cols.filter((res) => res.visible);
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.adminService
      .findTasks(pagingParams, params)
      .pipe(
        tap((res) => {
          res.data.forEach((row) => {
            this.rowMenuItems[row.id] = this.getActions(row);
          });
          return res;
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprTasksBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .findTasks(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprTasksBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recordId: string): void {
    this.adminService.delTasksRecord(recordId).subscribe(() => {
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
    this.reload();
  }

  navigateToEdit(id: string): void {
    void this.router.navigate([this.router.url, RoutingConst.EDIT, id]);
  }

  navigateToCopy(id: string): void {
    void this.router.navigate([this.router.url, RoutingConst.EDIT, id], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  getActions(row: SprTasksBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    row.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action: ActionsEnum) => {
        menu.push({
          label: this.commonFormServices.translate.instant(
            'common.action.' + action.toString().toLowerCase()
          ) as string,
          id: row.id,
          icon: getActionIcons(action),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              this.navigateToEdit(row.id);
            } else if (action === ActionsEnum.ACTIONS_COPY) {
              this.navigateToCopy(row.id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(row.id);
            }
          },
        });
      });
    return menu;
  }
}
