import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_ORGS_BROWSE } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprOrganizationsBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

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

@Component({
  selector: 'app-organizations-browse-page',
  templateUrl: './organizations-browse-page.component.html',
  styleUrls: ['./organizations-browse-page.component.scss'],
})
export class OrganizationsBrowsePageComponent extends BaseBrowseForm<SprOrganizationsBrowseRow> implements OnInit {
  readonly formCode = SPR_ORGS_BROWSE;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  organizationsEditLink = RoutingConst.EDIT;
  organizationsRolesLink = RoutingConst.SPARK_ORGANIZATION_ROLES_BROWSE;
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprOrganizationsBrowseRow[] = [];
  paramsIsEmpty: boolean = false;
  sortClause: string;
  sortOrder: number;

  cols: TableColumn[] = [
    { field: 'org_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_address', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_email', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_phone', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(protected override commonFormServices: CommonFormServices, private adminService: AdminService) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
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
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('org_id', new FormControl(''));
    this.searchForm.addControl('org_type', new FormControl(''));
    this.searchForm.addControl('org_code', new FormControl(''));
    this.searchForm.addControl('org_address', new FormControl(''));
    this.searchForm.addControl('org_email', new FormControl(''));
    this.searchForm.addControl('org_phone', new FormControl(''));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.paramsIsEmpty = params && extendedParams.length === 0;
    const pagingParams = this.getPagingParams(
      first,
      pageSize,
      this.sortClause ? this.sortClause : sortField,
      this.sortOrder ? this.sortOrder : order,
      null
    );
    this.adminService
      .getOrganizationsList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.org_id] = this.getActions(row);
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
        .getOrganizationsList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprOrganizationsBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recordId: string): void {
    this.adminService.delOrganizationRecord(recordId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  navigateToEdit(recId: string): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      this.organizationsEditLink,
      recId,
    ]);
  }

  navigateToCopy(recId: string): void {
    void this.commonFormServices.router.navigate(
      [this.commonFormServices.router.url, this.organizationsEditLink, recId],
      {
        queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
      }
    );
  }

  navigateToAssignRole(recId: string): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      this.organizationsRolesLink,
      recId,
    ]);
  }

  navigateToAssignUsers(recId: string): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      RoutingConst.SPARK_ORG_USERS_LIST,
      recId,
    ]);
  }

  getActions(row: SprOrganizationsBrowseRow): MenuItem[] {
    return row.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .map((action) => {
        const menuItem: MenuItem = {
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          id: row.org_id,
          icon: getActionIcons(action),
        };
        switch (action) {
          case ActionsEnum.ACTIONS_UPDATE:
          case ActionsEnum.ACTIONS_READ:
            menuItem.command = (): void => {
              this.navigateToEdit(row.org_id);
            };
            break;
          case ActionsEnum.ACTIONS_COPY:
            menuItem.command = (): void => {
              this.navigateToCopy(row.org_id);
            };
            break;
          case ActionsEnum.ACTIONS_DELETE:
            menuItem.command = (): void => {
              this.deleteRecordWithConfirmation(row.org_id);
            };
            break;
          case ActionsEnum.ASSIGN_ROLE:
            menuItem.command = (): void => {
              this.navigateToAssignRole(row.org_id);
            };
            break;
          case ActionsEnum.ASSIGN_USERS:
            menuItem.command = (): void => {
              this.navigateToAssignUsers(row.org_id);
            };
        }
        return menuItem;
      });
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
