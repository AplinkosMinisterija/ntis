import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_FORMS_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { TranslateService } from '@ngx-translate/core';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { Table } from 'primeng/table';
import { SprFormsBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-forms-browse-page',
  templateUrl: './forms-browse-page.component.html',
  styleUrls: ['./forms-browse-page.component.scss'],
})
export class FormsBrowsePageComponent extends BaseBrowseForm<SprFormsBrowseRow> implements OnInit {
  readonly formCode = SPR_FORMS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  editLink = RoutingConst.EDIT;
  maximumDate = new Date();
  rows = false;
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprFormsBrowseRow[] = [];
  userCanCreate = false;

  cols: TableColumn[] = [
    { field: 'frm_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'frm_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'frm_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'frm_description', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
    { field: 'rec_create_timestamp', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'availableActions', export: false, visible: false, type: DATA_TYPE_STRING },
  ];
  visibleColumns: TableColumn[] = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private router: Router,
    private authService: AuthService,
    private translateService: TranslateService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.searchForm.addControl('frm_name', new FormControl(''));
    this.searchForm.addControl('frm_code', new FormControl(''));
    this.searchForm.addControl('frm_id', new FormControl(''));
    this.searchForm.addControl('frm_description', new FormControl(''));
    this.searchForm.addControl('rec_create_timestamp', new FormControl(''));
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
      .getFormsList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.frm_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprFormsBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getFormsList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprFormsBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recordId: string): void {
    this.adminService.delFormRecord(recordId).subscribe(() => {
      this.reload();
      this.translateService.get('common.message.deleteSuccess').subscribe((txt: string) => {
        this.commonFormServices.appMessages.showSuccess('', txt);
      });
    });
  }

  navigateToEdit(id: string): void {
    void this.router.navigate([this.router.url, RoutingConst.EDIT, id]);
  }

  navigateToCopy(id: string): void {
    void this.router.navigate([this.router.url, RoutingConst.EDIT, id], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  getActions(application: SprFormsBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const frm_id: string = application.frm_id.toString();
    const actions: ActionsEnum[] = [...application.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !application.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.translateService.instant('common.action.' + action.toString().toLowerCase()) as string,
          id: frm_id,
          icon: getActionIcons(action),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              this.navigateToEdit(frm_id);
            } else if (action === ActionsEnum.ACTIONS_COPY) {
              this.navigateToCopy(frm_id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(frm_id);
            }
          },
        });
      });
    return menu;
  }
}
