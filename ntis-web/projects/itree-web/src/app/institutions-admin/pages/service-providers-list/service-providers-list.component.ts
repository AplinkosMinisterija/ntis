import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import {
  SRV_PRVD_DEREGISTERED,
  SRV_PRVD_REGISTERED,
  VALYMAS,
} from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NTIS_SERVICE_PROVIDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisServiceProviderRejectionModel, SprListIdKeyValue } from '@itree-commons/src/lib/model/api/api';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { map } from 'rxjs';
import { NtisTableRowActionsItem } from '../../../ntis-shared/models/table-row-actions';
import { ServiceProvidersBrowseRow, TableColumn } from '../../models/browse-pages';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { DialogModule } from 'primeng/dialog';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
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
  selector: 'app-service-providers-list',
  templateUrl: './service-providers-list.component.html',
  styleUrls: ['./service-providers-list.component.scss'],
  standalone: true,
  imports: [CommonModule, DialogModule, ItreeCommonsModule, NtisSharedModule, ReactiveFormsModule, TableModule],
})
export class ServiceProvidersListComponent extends BaseBrowseForm<ServiceProvidersBrowseRow> implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.serviceProvidersList';
  readonly formCode = NTIS_SERVICE_PROVIDERS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly registered = SRV_PRVD_REGISTERED;
  readonly deregistered = SRV_PRVD_DEREGISTERED;
  readonly institution = 'INST';
  readonly institutionLt = 'INST_LT';

  exportData: ServiceProvidersBrowseRow[];
  deregisteredProviders: ServiceProvidersBrowseRow[];
  dialog: boolean = false;
  edit: boolean = false;
  org_name: string;
  org_code: string;
  orgTypeSelection: SprListIdKeyValue[] = [];
  orgStatusSelection: SprListIdKeyValue[] = [];
  servicesSelection: SprListIdKeyValue[] = [];

  cols: TableColumn[] = [
    { field: 'org_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'org_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'services_provided', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_state', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_state_clsf', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'org_date_from', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'org_date_to', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'org_rejection_reason', export: false, visible: false, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService,
    private clsfService: CommonService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  form = new FormGroup({
    org_id: new FormControl<number>(null),
    org_registered_date: new FormControl<Date>(null),
    org_deregistered_date: new FormControl<Date>(null),
    org_state: new FormControl<string>(null),
    org_rejection_reason: new FormControl<string>(
      null,
      Validators.compose([Validators.required, Validators.maxLength(1000)])
    ),
    org_address: new FormControl<string>(null),
    org_email: new FormControl<string>(null),
    org_phone: new FormControl<string>(null),
    org_delegation_person: new FormControl<string>(null),
  });

  ngOnInit(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        this.loadSearchDataIntoSearchForm();
      }
    }
    this.clsfService.getClsf('NTIS_ORG_TYPE').subscribe((options) => {
      this.orgTypeSelection = options.filter((type) => {
        return type.key !== this.institution && type.key !== this.institutionLt;
      });
    });
    this.clsfService.getClsf('NTIS_SRV_PRVD_STATE').subscribe((options) => {
      this.orgStatusSelection = options.filter((type) => {
        return type.key === this.deregistered || type.key === this.registered;
      });
    });
    this.clsfService.getClsf('NTIS_SRV_ITEM_TYPE').subscribe((options) => {
      this.servicesSelection = options.filter((type) => {
        return type.key !== VALYMAS;
      });
    });
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('org_type', new FormControl(''));
    this.searchForm.addControl('org_code', new FormControl(''));
    this.searchForm.addControl('services_provided', new FormControl(''));
    this.searchForm.addControl('org_state', new FormControl(''));
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
    const searchData = this.getSearchDataFromSession();
    this.adminService
      .getServiceProvidersList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getNtisActions(row);
          });
          return result;
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
        .getServiceProvidersList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  getNtisActions(row: ServiceProvidersBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faInfo' },
          actionName: 'showInfo',
          iconTheme: 'default',
          action: () => {
            this.openDialog(row, false);
          },
        };
        rowActions.push(action);
      } else if (action === ActionsEnum.IMPORT_ORDERS && row.org_state_clsf == this.registered) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faFileCsv' },
          actionName: 'importOrders',
          iconTheme: 'default',
          action: () => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.ORDERS_IMPORT,
              row.org_id,
            ]);
          },
        };
        rowActions.push(action);
      } else if (action === ActionsEnum.ACTION_DISABLE_ORGANIZATION && row.org_state_clsf == this.registered) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faBan' },
          actionName: 'disableOrg',
          iconTheme: 'risk',
          action: () => {
            this.openDialog(row, true);
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  openDialog(row: ServiceProvidersBrowseRow, isEdit: boolean): void {
    this.edit = isEdit;
    this.org_name = row.org_name;
    this.org_code = row.org_code;
    this.form.reset();
    this.dialog = true;
    this.form.controls.org_id.setValue(row.org_id);
    this.form.controls.org_registered_date.setValue(row.org_registered_date);
    this.form.controls.org_deregistered_date.setValue(row.org_deregistered_date);
    this.form.controls.org_state.setValue(row.org_state);
    this.form.controls.org_rejection_reason.setValue(row.org_rejection_reason);
    this.form.controls.org_phone.setValue(row.org_phone);
    this.form.controls.org_email.setValue(row.org_email);
    this.form.controls.org_address.setValue(row.org_address);
    this.form.controls.org_delegation_person.setValue(row.org_delegation_person);
  }

  closeDialog(): void {
    this.dialog = false;
    this.org_name = null;
    this.org_code = null;
  }

  setDisabled(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const provider: NtisServiceProviderRejectionModel = {
        org_id: this.form.controls.org_id.value,
        org_rejection_reason: this.form.controls.org_rejection_reason.value,
      };
      this.adminService.setServiceProviderDisabled(provider).subscribe(() => {
        this.reload();
        this.dialog = false;
        this.commonFormServices.translate
          .get(this.translationsReference + '.orgDisabled', { org_name: this.org_name, org_code: this.org_code })
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
        this.adminService.sendSpListNotifications(provider.org_id).subscribe();
      });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.dialog = false;
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
