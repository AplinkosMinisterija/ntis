import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NTIS_SP_WM_SERVICE_REQUESTS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { map } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { ServiceRequestsBrowseRow } from '../../../models/browse-pages';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { ServiceRequestStatus, ServiceType } from '../../../../ntis-shared/enums/classifiers.enums';
import { NtisTableRowActionsItem } from '../../../../ntis-shared/models/table-row-actions';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { ClickedOutsideDirective } from '../../../directives/clicked-outside.directive';
import { TooltipModule } from 'primeng/tooltip';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

enum RoleAction {
  serviceProvider = 'SERVICE_PROVIDER_ACTIONS',
  waterManager = 'WATER_MANAGER_ACTIONS',
}

@Component({
  selector: 'app-sp-wm-service-requests-list-page',
  templateUrl: './sp-wm-service-requests-list-page.component.html',
  styleUrls: ['./sp-wm-service-requests-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    ReactiveFormsModule,
    FormsModule,
    TableModule,
    NtisSharedModule,
    ClickedOutsideDirective,
    TooltipModule,
  ],
})
export class SpWmServiceRequestsListPageComponent extends BaseBrowseForm<ServiceRequestsBrowseRow> implements OnInit {
  readonly formCode = NTIS_SP_WM_SERVICE_REQUESTS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'institutionsAdmin.pages.serviceRequestsList';
  readonly ServiceType = ServiceType;
  exportData: ServiceRequestsBrowseRow[] = [];
  serviceType: string;
  dialog: boolean = false;
  userCanCreate: boolean;

  cols: TableColumn[] = [
    { field: 'sr_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'sr_registration_date', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_req_applicant', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_status_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_status_date', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.serviceType = this.authService.isFormActionEnabled(this.formCode, RoleAction.serviceProvider)
      ? ServiceType.serviceProvider
      : this.authService.isFormActionEnabled(this.formCode, RoleAction.waterManager)
      ? ServiceType.waterManager
      : null;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.searchForm.addControl('sr_id', new FormControl(''));
    this.searchForm.addControl('sr_registration_date', new FormControl(''));
    this.searchForm.addControl('sr_req_applicant', new FormControl(''));
    this.searchForm.addControl('sr_srv_provider', new FormControl(''));
    this.searchForm.addControl('sr_status', new FormControl(''));
    this.searchForm.addControl('sr_status_date', new FormControl(''));
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
      .getSpWmServiceRequestsList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getActions(row);
            row.sr_status === ServiceRequestStatus.submitted ? (row.sr_status_date = null) : null;
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
        .getSpWmServiceRequestsList(
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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  protected deleteRecord(recordId: string): void {}

  getActions(row: ServiceRequestsBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: () =>
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.SERVICE_REQUEST_REVIEW,
              row.sr_type.toLowerCase(),
              row.sr_id,
            ]),
        });
      }
    });
    return itemActions;
  }

  toNewRequest(serviceType: string): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      NtisRoutingConst.SERVICE_REQUEST_CREATE,
      serviceType.toLowerCase(),
    ]);
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.dialog = false;
  }
}
