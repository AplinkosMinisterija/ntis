import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_ROLES_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
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
import { SprRolesBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-roles-browse-page',
  templateUrl: './roles-browse-page.component.html',
  styleUrls: ['./roles-browse-page.component.scss'],
})
export class RolesBrowsePageComponent extends BaseBrowseForm<SprRolesBrowseRow> implements OnInit {
  readonly formCode = SPR_ROLES_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  roleEditLink = RoutingConst.EDIT;
  roleSettingsLink = RoutingConst.SPARK_ROLE_ACTIONS;
  roleSettingsLinkRole = RoutingConst.SPARK_ROLE_ACTIONS_ROLE;
  roleUsersLink = RoutingConst.SPARK_ROLE_USERS;
  maximumDate = new Date();
  cols: TableColumn[];
  rows = false;
  menuActions: Record<string, MenuItem[]> = {};
  exportData: SprRolesBrowseRow[] = [];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private router: Router,
    private datePipe: S2DatePipe,
    private adminService: AdminService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('rol_id', new FormControl(null));
    this.searchForm.addControl('rol_code', new FormControl(''));
    this.searchForm.addControl('rol_name', new FormControl(''));
    this.searchForm.addControl('rol_date_from', new FormControl(''));
    this.searchForm.addControl('rol_date_to', new FormControl(''));
    this.searchForm.addControl('rol_type', new FormControl(''));
    this.searchForm.addControl('file', new FormControl(''));
    this.cols = [
      { field: 'id', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'rol_code', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'rol_type', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'rol_name', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
      { field: 'rol_description', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
      { field: 'rol_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'rol_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
    ];
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
      .getRoleList(pagingParams, this.fixDate(params), lang, extendedParams)
      .pipe(
        tap((res) => {
          res.data.forEach((row) => {
            this.menuActions[row.id] = this.getActions(row);
          });
          return res;
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprRolesBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getRoleList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.fixDate(this.getSearchParamMap(dataTable)),
          getLang(),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprRolesBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_rol_date_from', this.datePipe.transform(params.get('p_rol_date_from') as string))
      .set('p_rol_date_to', this.datePipe.transform(params.get('p_rol_date_to') as string));
  }

  protected deleteRecord(recId: string): void {
    this.adminService.delRoleRecord(recId).subscribe(() => {
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
      );
      this.reload();
    });
  }

  navigateToEdit(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.roleEditLink + '/' + recId]);
  }

  navigateToCopy(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.roleEditLink + '/' + recId], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  navigateToRoleSettings(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.roleSettingsLink + '/' + recId]);
  }

  navigateToRoleUsers(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.roleUsersLink + '/' + recId]);
  }

  getActions(application: SprRolesBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...application.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !application.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant(
            'common.action.' + action.toString().toLowerCase()
          ) as string,
          id: application.id,
          icon: getActionIcons(action as ActionsEnum),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              this.navigateToEdit(application.id);
            } else if (action === ActionsEnum.ACTIONS_COPY) {
              this.navigateToCopy(application.id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(application.id);
            }
          },
        });
      });
    this.addAdditionalAction(application, menu);
    return menu;
  }

  addAdditionalAction(application: SprRolesBrowseRow, menu: MenuItem[]): void {
    menu.push(
      {
        label: this.commonFormServices.translate.instant('pages.sprRolesBrowse.settings') as string,
        id: application.id,
        icon: 'pi pi-fw  pi-bars',
        command: (): void => {
          this.navigateToRoleSettings(application.id);
        },
      },
      {
        label: this.commonFormServices.translate.instant('pages.sprRolesBrowse.roleUsers') as string,
        id: application.id,
        icon: 'pi pi-fw pi-users',
        command: (): void => {
          this.navigateToRoleUsers(application.id);
        },
      }
    );
  }
}
