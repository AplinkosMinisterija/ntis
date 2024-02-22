import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { Table } from 'primeng/table';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { SPR_PROPERTIES_LIST } from '@itree-commons/src/constants/forms.constants';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { PropertiesBrowse } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-properties-browse-page',
  templateUrl: './properties-browse-page.component.html',
  styleUrls: ['./properties-browse-page.component.scss'],
})
export class PropertiesBrowsePageComponent extends BaseBrowseForm<PropertiesBrowse> implements OnInit {
  readonly formCode = SPR_PROPERTIES_LIST;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  propertiesEditLink = RoutingConst.EDIT;
  userCanSetFormActions: boolean = false;
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: PropertiesBrowse[] = [];

  cols: TableColumn[] = [
    { field: 'prp_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'prp_name', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
    { field: 'prp_value', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'prp_guid', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'prp_description', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
    { field: 'prp_install_instance', export: true, visible: true, type: DATA_TYPE_NUMBER },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private router: Router,
    private adminService: AdminService,
    private confirmationService: ConfirmationService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('prp_name', new FormControl(''));
    this.searchForm.addControl('prp_id', new FormControl(''));
    this.searchForm.addControl('prp_value', new FormControl(''));
    this.searchForm.addControl('prp_description', new FormControl(''));
    this.searchForm.addControl('prp_guid', new FormControl(''));
    this.searchForm.addControl('prp_install_instance', new FormControl(''));
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
      .getParameterList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.prp_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<PropertiesBrowse>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getParameterList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<PropertiesBrowse>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recId: string): void {
    this.adminService.delParameterRecord(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
      );
    });
  }

  navigateToEdit(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.propertiesEditLink + '/' + recId]);
  }

  navigateToCopy(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.propertiesEditLink + '/' + recId], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  getActions(row: PropertiesBrowse): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          icon: getActionIcons(action as ActionsEnum),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              this.navigateToEdit(row.prp_id);
            } else if (action === ActionsEnum.ACTIONS_COPY) {
              this.navigateToCopy(row.prp_id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(row.prp_id);
            }
          },
        });
      });
    return menu;
  }
}
