import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_USERS_BROWSE } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, SprListIdKeyValue, SprUsersDAO } from '@itree-commons/src/lib/model/api/api';
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
import { SprUsersBrowseOrgListRow, SprUsersBrowseRow } from '../../../models/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';

interface BrowseSearchData {
  url: string;
  paramsObj: Record<string, unknown>;
  extendedParams: ExtendedSearchParam[];
  searchFormValues: Record<string, unknown>;
  first: number;
  pageSize: number;
  sortField: string;
  order: number;
}

export enum ThisFormActionsEnum {
  ACTIONS_ASSIGN_ROLE = 'ASSIGN_ROLE',
  ACTIONS_ASSIGN_ORG = 'ASSIGN_ORG',
  ACTIONS_INFORM_NEW_USER = 'INFORM_NEW_USER',
  ACTIONS_RESET_USER_PASSWORD = 'RESET_USER_PASSWORD',
}
const NO_LIMIT_ON_ORG_LEVEL = 'NO_LIMIT_ON_ORG_LEVEL';

export interface SprUsersWithoutRoles {
  usr_id: string;
  usr_username: string;
  usr_person_name: string;
  usr_person_surname: string;
  ou_id: string;
  org_name: string;
}

@Component({
  selector: 'app-users-browse-page',
  templateUrl: './users-browse-page.component.html',
  styleUrls: ['./users-browse-page.component.scss'],
})
export class UsersBrowsePageComponent extends BaseBrowseForm<SprUsersBrowseRow> implements OnInit {
  transalation: string = 'pages.sprUsersBrowse';

  readonly formCode = SPR_USERS_BROWSE;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly INTERNAL = 'INTERNAL';
  readonly EXTERNAL = 'EXTERNAL';
  usersEditLink = RoutingConst.EDIT;
  cols: TableColumn[];
  visibleColumns: TableColumn[];
  dateLimit = new Date();
  rows = false;
  disabledForm = new FormGroup({});
  menuActions: Record<string, MenuItem[]> = {};
  disableActionEnabled = true;
  exportData: SprUsersBrowseRow[] = [];
  expansionColumns: TableColumn[];
  formAvailableActions: string[] = [];
  today: Date = new Date();
  yesterday: Date = new Date();
  userTypeSelection: SprListIdKeyValue[] = [];
  usersWithoutRoles: SprUsersWithoutRoles[] = [];
  sortClause: string;
  sortOrder: number;

  noLimitOnOrLevelAction: boolean;
  userCanCreate: boolean;

  constructor(
    protected override commonFormServices: CommonFormServices,
    private router: Router,
    private datePipe: S2DatePipe,
    private adminService: AdminService,
    private authService: AuthService,
    private clsfService: CommonService,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.noLimitOnOrLevelAction = this.authService.isFormActionEnabled(this.formCode, NO_LIMIT_ON_ORG_LEVEL);
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        this.sortClause = searchData.sortField ?? null;
        this.sortOrder = searchData.order ?? null;
        this.loadSearchDataIntoSearchForm();
      }
    }
    this.searchForm.addControl('usr_username', new FormControl(''));
    this.searchForm.addControl('usr_id', new FormControl(''));
    this.searchForm.addControl('usr_email', new FormControl(''));
    this.searchForm.addControl('usr_person_name', new FormControl(''));
    this.searchForm.addControl('usr_person_surname', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('usr_date_from', new FormControl(''));
    this.searchForm.addControl('usr_date_to', new FormControl(''));
    this.searchForm.addControl('usr_type', new FormControl(''));
    this.searchForm.addControl('usr_reg_type_status', new FormControl(''));
    this.cols = [
      { field: 'usr_id', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'ou_id', export: false, visible: false, type: DATA_TYPE_STRING },
      { field: 'usr_reg_type_status', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_username', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_email', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_person_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_person_surname', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'usr_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'usr_type', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_lock_date', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_list', export: true, visible: false, type: DATA_TYPE_STRING },
    ];
    this.visibleColumns = this.cols.filter((res) => res.visible);
    this.expansionColumns = [
      { field: 'ou_id', export: false, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_code', export: false, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_name', export: false, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_date_from', export: false, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_date_to', export: false, visible: true, type: DATA_TYPE_STRING },
    ];
    this.clsfService.getClsf('SPR_USER_TYPE').subscribe((options) => {
      this.userTypeSelection = options.filter((type) => {
        return type.key !== this.INTERNAL && type.key !== this.EXTERNAL;
      });
    });
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
    const pagingParams = this.getPagingParams(
      first,
      pageSize,
      this.sortClause ? this.sortClause : sortField,
      this.sortOrder ? this.sortOrder : order,
      null
    );
    this.adminService
      .getUsersList(pagingParams, this.fixDate(params), lang, extendedParams)
      .pipe(
        tap((res) => {
          res.data.forEach((row) => {
            this.menuActions[row.usr_id] = this.getActions(row);
          });
          return res;
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprUsersBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        this.formAvailableActions = tableData.formActions.availableActions;
        this.data.forEach((value) => {
          if (value.org_list) {
            value.org_list = JSON.parse(value.org_list as string) as SprUsersBrowseOrgListRow[];
          }
          this.disabledForm.addControl(value.usr_id, new FormControl(!!value.usr_lock_date));
        });
      });
    if (!this.noLimitOnOrLevelAction) {
      this.adminService.getUsersWithoutRoles().subscribe((result) => {
        this.usersWithoutRoles = result.data;
      });
    }
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getUsersList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.fixDate(this.getSearchParamMap(dataTable)),
          getLang(),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprUsersBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_usr_date_from', this.datePipe.transform(params.get('p_usr_date_from') as string))
      .set('p_usr_date_to', this.datePipe.transform(params.get('p_usr_date_to') as string))
      .set('p_usr_lock_date', this.datePipe.transform(params.get('p_usr_lock_date') as string));
  }

  protected deleteRecord(recId: string): void {
    if (this.noLimitOnOrLevelAction) {
      const user = this.data.find((usr) => usr.usr_id === recId);
      user.usr_date_to = this.datePipe.transform(this.yesterday.setDate(this.today.getDate() - 1));
      user.usr_lock_date = this.datePipe.transform(this.yesterday.setDate(this.today.getDate() - 1));
      this.disabledForm.get([recId]).setValue(true);
      this.adminService.delUsersRecord(recId).subscribe(() => {
        this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.adminService.orgUserRolesAssignedSubject.next();
          this.reload();
        });
      });
    } else {
      this.adminService.delNtisOrgUserRecord(recId).subscribe(() => {
        this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.adminService.orgUserRolesAssignedSubject.next();
          this.reload();
        });
      });
    }
  }

  navigateToEdit(recId: string, action: string): void {
    void this.router.navigate([this.router.url + '/' + this.usersEditLink + '/' + recId, { action: action }]);
  }

  navigateToUserRoleBrowse(recId: string): void {
    void this.router.navigate([this.router.url + '/' + RoutingConst.SPARK_USERS_ROLES_BROWSE + '/' + recId]);
  }

  navigateToUserOrgRoleBrowse(rec: SprUsersBrowseOrgListRow, row: SprUsersBrowseRow): void {
    void this.router.navigate([this.router.url + '/' + RoutingConst.SPARK_ORG_USER_ROLES + '/' + rec.ou_id], {
      queryParams: { id: row.usr_id, org: rec.org_name, user: row.usr_person_name + ' ' + row.usr_person_surname },
    });
  }
  navigateToUserOrgRoleBrowse2(row: SprUsersBrowseRow): void {
    void this.router.navigate([this.router.url + '/' + RoutingConst.SPARK_ORG_USER_ROLES + '/' + row.ou_id], {
      queryParams: { id: row.usr_id, org: row.org_name, user: row.usr_person_name + ' ' + row.usr_person_surname },
    });
  }
  navigateToUserOrgsList(recId: string): void {
    void this.router.navigate([this.router.url + '/' + RoutingConst.SPARK_USER_ORGS_LIST + '/' + recId]);
  }

  navigateToCopy(recId: string): void {
    void this.router.navigate([this.router.url + '/' + this.usersEditLink + '/' + recId], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  navigateToInformNewUser(recId: string): void {
    this.adminService.informNewUser(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get(this.transalation + '.informationSend').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  navigateToInformPasswordReset(recId: string): void {
    this.adminService.informUserPasswordReset(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get(this.transalation + '.informationSend').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  getActions(application: SprUsersBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    type AllActionsEnum = ActionsEnum | ThisFormActionsEnum;
    application.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !application.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action: AllActionsEnum) => {
        if (
          action === ThisFormActionsEnum.ACTIONS_ASSIGN_ROLE ||
          action === ThisFormActionsEnum.ACTIONS_INFORM_NEW_USER ||
          action === ThisFormActionsEnum.ACTIONS_RESET_USER_PASSWORD ||
          action === ThisFormActionsEnum.ACTIONS_ASSIGN_ORG
        ) {
          menu.push({
            label: this.commonFormServices.translate.instant(this.getThisFormMenuCodes(action)) as string,
            id: application.usr_id,
            icon: this.getThisFormActionsIcon(action),
            tooltip:
              action === ThisFormActionsEnum.ACTIONS_INFORM_NEW_USER
                ? 'Išsiųsti slaptažodžio keitimo nuorodą naudotojui el. paštu'
                : 'Išsiųsti slaptažodžio keitimo nuorodą naudotojui el. paštu',
            command: (): void => {
              if (action == ThisFormActionsEnum.ACTIONS_ASSIGN_ROLE) {
                if (this.formAvailableActions.indexOf(NO_LIMIT_ON_ORG_LEVEL) != -1) {
                  this.navigateToUserRoleBrowse(application.usr_id);
                } else {
                  this.navigateToUserOrgRoleBrowse2(application);
                }
              } else if (action == ThisFormActionsEnum.ACTIONS_INFORM_NEW_USER) {
                this.navigateToInformNewUser(application.usr_id);
              } else if (action == ThisFormActionsEnum.ACTIONS_ASSIGN_ORG) {
                this.navigateToUserOrgsList(application.usr_id);
              } else if (action == ThisFormActionsEnum.ACTIONS_RESET_USER_PASSWORD) {
                this.navigateToInformPasswordReset(application.usr_id);
              }
            },
          });
        } else {
          menu.push({
            label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
            id: application.usr_id,
            icon: getActionIcons(action),
            command: (): void => {
              if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
                this.navigateToEdit(application.usr_id, action);
              } else if (action === ActionsEnum.ACTIONS_DELETE) {
                this.deleteRecordWithConfirmation(application.usr_id);
              } else if (action === ActionsEnum.ACTIONS_COPY) {
                this.navigateToCopy(application.usr_id);
              }
            },
          });
        }
      });
    return menu;
  }

  getThisFormMenuCodes(actionCode: ThisFormActionsEnum): string {
    const menuIcons = {
      [ThisFormActionsEnum.ACTIONS_ASSIGN_ROLE]: 'pages.sprRolesBrowse.' + actionCode.toLocaleLowerCase(),
      [ThisFormActionsEnum.ACTIONS_ASSIGN_ORG]: 'pages.sprOrgUserRoles.' + actionCode.toLocaleLowerCase(),
      [ThisFormActionsEnum.ACTIONS_INFORM_NEW_USER]: 'pages.sprUsersBrowse.' + actionCode.toLocaleLowerCase(),
      [ThisFormActionsEnum.ACTIONS_RESET_USER_PASSWORD]: 'pages.sprUsersBrowse.' + actionCode.toLocaleLowerCase(),
    };
    return menuIcons[actionCode];
  }

  getThisFormActionsIcon(actionCode: ThisFormActionsEnum): string {
    const menuIcons = {
      [ThisFormActionsEnum.ACTIONS_ASSIGN_ROLE]: 'pi pi-fw  pi-users ',
      [ThisFormActionsEnum.ACTIONS_ASSIGN_ORG]: 'pi pi-fw  pi-building',
      [ThisFormActionsEnum.ACTIONS_INFORM_NEW_USER]: 'pi pi-fw pi-external-link',
      [ThisFormActionsEnum.ACTIONS_RESET_USER_PASSWORD]: 'pi pi-fw  pi-lock-open',
    };
    return menuIcons[actionCode];
  }

  onInputSwitch(usrId: string): void {
    if (usrId != null && usrId !== '') {
      this.adminService.changeUserDisabledStatus(usrId).subscribe();
    }
  }

  getLinkToAddRole(user: SprUsersWithoutRoles): string {
    return this.router
      .createUrlTree([`${this.router.url}/${RoutingConst.SPARK_ORG_USER_ROLES}/${user.ou_id}`], {
        queryParams: {
          id: user.usr_id,
          org: user.org_name,
          user: `${user.usr_person_name} ${user.usr_person_surname}`,
        },
      })
      .toString();
  }

  protected override saveSearchDataToSession(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const browseSearchData: BrowseSearchData = {
      url: this.commonFormServices.router.url,
      paramsObj: Object.fromEntries(params),
      extendedParams,
      searchFormValues: Object.keys(this.searchForm.controls)
        .filter((controlName) => this.searchForm.controls[controlName]?.value)
        .reduce(
          (valuesObj, controlName) => ({
            ...valuesObj,
            [controlName]: this.searchForm.controls[controlName].value as unknown,
          }),
          {} as Record<string, unknown>
        ),
      first,
      pageSize,
      sortField,
      order,
    };
    sessionStorage.setItem(this.formCode, JSON.stringify(browseSearchData));
  }

  protected override getSearchDataFromSession(): BrowseSearchData | null {
    const searchData = JSON.parse(sessionStorage.getItem(this.formCode)) as BrowseSearchData;
    if (searchData) {
      if (searchData?.url === this.commonFormServices.router.url) {
        return searchData;
      }
    }
    return null;
  }

  protected override removeSearchDataFromSession(): void {
    sessionStorage.removeItem(this.formCode);
  }
}
