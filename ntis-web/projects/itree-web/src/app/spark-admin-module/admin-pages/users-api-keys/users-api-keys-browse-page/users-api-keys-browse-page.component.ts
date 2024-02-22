import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_API_KEYS_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { AdminService } from '../../../admin-services/admin.service';
import { SprApiKeyBrowseRow, SprUserApiKeyBrowseRow } from '../../../models/browse-pages';
import { FilterInputType } from '@itree-commons/src/lib/enums/input-type.enums';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-users-api-keys-browse-page',
  templateUrl: './users-api-keys-browse-page.component.html',
  styleUrls: ['./users-api-keys-browse-page.component.scss'],
})
export class UsersApiKeysBrowsePageComponent extends BaseBrowseForm<SprApiKeyBrowseRow> implements OnInit {
  readonly formTranslationsReference = 'pages.sprApiKeys';
  readonly formCode = SPR_API_KEYS_LIST;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly routingConst = RoutingConst;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  userCanEditActions: boolean = false;
  rowMenuItems: Record<string, MenuItem[]> = {};
  extendedMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprApiKeyBrowseRow[] = [];
  dataKey = 'api_id';
  form = new FormGroup({});

  cols: TableColumn[] = [
    {
      field: this.dataKey,
      export: true,
      visible: true,
      type: DATA_TYPE_NUMBER,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'usr_username',
      export: true,
      visible: true,
      type: DATA_TYPE_STRING,
      textLength: 100,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'per_name',
      export: true,
      visible: true,
      type: DATA_TYPE_STRING,
      textLength: 100,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'org_name',
      export: true,
      visible: true,
      type: DATA_TYPE_STRING,
      textLength: 100,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'last_date',
      export: true,
      visible: true,
      type: DATA_TYPE_DATE,
      filterable: true,
      filterType: FilterInputType.date,
    },
  ];

  expansionColumns: TableColumn[] = [
    {
      field: 'api_id',
      export: true,
      visible: true,
      type: DATA_TYPE_STRING,
      textLength: 100,
    },
    {
      field: 'api_key',
      export: true,
      visible: true,
      type: DATA_TYPE_STRING,
      textLength: 100,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'api_date_from',
      export: true,
      visible: true,
      type: DATA_TYPE_DATE,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'api_date_to',
      export: true,
      visible: true,
      type: DATA_TYPE_DATE,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'api_on',
      export: true,
      visible: true,
      type: DATA_TYPE_DATE,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'last_api_date',
      export: true,
      visible: true,
      type: DATA_TYPE_DATE,
      filterable: true,
      filterType: FilterInputType.date,
    },
  ];

  visibleColumns = this.cols.filter((res) => res.visible);

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
    this.cols.forEach((col) => {
      if (col.filterable) {
        this.searchForm.addControl(col.field, new FormControl(''));
      }
    });
    this.expansionColumns.forEach((col) => {
      if (col.filterable) {
        this.searchForm.addControl(col.field, new FormControl(''));
      }
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
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.adminService
      .getNtisApiKeyRecList(pagingParams, params as Map<string, string>, extendedParams)
      .subscribe((tableData) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        this.data.forEach((value) => {
          if (value.api_list) {
            value.api_list = JSON.parse(value.api_list as string) as SprUserApiKeyBrowseRow[];
          }
          if (Array.isArray(value.api_list)) {
            value.api_list.forEach((apiItem: SprUserApiKeyBrowseRow) => {
              // Vaikianiams įrašamas priskiriami unikalūs form control'ai, kadangi jie turi atskiras būsenas
              const uniqueFormControlName = `api_on_${apiItem.api_id}`;
              this.extendedMenuItems[apiItem.api_id] = this.getExtendedActions(apiItem, value);
              this.form.addControl(uniqueFormControlName, new FormControl(apiItem.api_date_to === null));
            });
          }
        });
      });
  }

  loadAllRecordsForExport(allRecords: boolean): void {
    if (allRecords) {
      this.adminService
        .getNtisApiKeyRecList(
          this.getPagingParams(
            0,
            MAX_ROWS_FOR_EXPORT,
            this.dataTableComponent.sortField,
            this.dataTableComponent.sortOrder,
            null
          ),
          this.getSearchParamMap(this.dataTableComponent) as Map<string, string>,
          this.getExtendedSearchParams(this.dataTableComponent)
        )
        .subscribe((result) => {
          this.exportData = result.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  protected deleteRecord(recId: string): void {
    this.adminService.deleteApiKeyRecord(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
      );
    });
  }

  getExtendedActions(row: SprUserApiKeyBrowseRow, parentRow: SprApiKeyBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    parentRow.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !parentRow.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          icon: getActionIcons(action),
          routerLink:
            action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ
              ? `${this.routingConst.EDIT}/${row.api_id}`
              : null,
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(row.api_id.toString());
            }
          },
        });
      });
    return menu;
  }

  // Įjungia / išjungia API raktą ir dinamiškai atnaujina lentą su rakto galiojimo data
  onInputSwitch(apiId: number, row: SprApiKeyBrowseRow): void {
    if (apiId) {
      this.adminService.changeApiKeyStatus(apiId).subscribe(() => {
        if (Array.isArray(row.api_list)) {
          const apiObject = row.api_list.find((api) => api.api_id === apiId);
          apiObject.api_date_to = apiObject.api_date_to ? null : this.datePipe.transform(new Date());
        }
      });
    }
  }

  // Pagalbinė funkcija, kuri suteikia dinamiškus (unikalius) pavadinimus API raktams (vaikiniams įrašams)
  getFormControl(apiId: number): FormControl | null {
    const control = this.form.get(`api_on_${apiId}`);
    return control instanceof FormControl ? control : null;
  }
}
