import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NTIS_SERVICE_REQUESTS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, map, takeUntil } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { ServiceRequestsBrowseRow } from '../../../models/browse-pages';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { ActivatedRoute } from '@angular/router';
import { ServiceRequestStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-service-requests-list-page',
  templateUrl: './service-requests-list-page.component.html',
  styleUrls: ['./service-requests-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    NtisSharedModule,
    TableModule,
  ],
})
export class ServiceRequestsListPageComponent extends BaseBrowseForm<ServiceRequestsBrowseRow> implements OnInit {
  readonly formCode = NTIS_SERVICE_REQUESTS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'institutionsAdmin.pages.serviceRequestsList';
  destroy$: Subject<boolean> = new Subject();
  exportData: ServiceRequestsBrowseRow[] = [];
  labsCertificatesUrl: string;

  cols: TableColumn[] = [
    { field: 'sr_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'sr_registration_date', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_req_applicant', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_srv_provider', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_status_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'sr_status_date', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    public faIconsService: FaIconsService,
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService,
    private appDataService: AppDataService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.labsCertificatesUrl = this.appDataService.getPropertyValue('LABS_CERTIFICATES_URL');
  }

  ngOnInit(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      if (Object.keys(params).length > 0) {
        if (params.status === ServiceRequestStatus.confirmed.toLowerCase()) {
          this.commonFormServices.translate
            .get(`${this.formTranslationsReference}.requestConfirmed`, { sr_id: params[RoutingConst.ID] as string })
            .subscribe((translation: string) => {
              this.commonFormServices.appMessages.showSuccess('', translation);
            });
        } else if (params.status === ServiceRequestStatus.rejected.toLowerCase()) {
          this.commonFormServices.translate
            .get(`${this.formTranslationsReference}.requestRejected`, { sr_id: params[RoutingConst.ID] as string })
            .subscribe((translation: string) => {
              this.commonFormServices.appMessages.showError('', translation);
            });
        }
      }
    });

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
      .getServiceRequestsList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getActions(row);
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
        .getServiceRequestsList(
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
          action: (): void => {
            void this.commonFormServices.router.navigate([
              RoutingConst.INTERNAL,
              NtisRoutingConst.INSTITUTIONS,
              NtisRoutingConst.SERVICE_REQUESTS_LIST,
              NtisRoutingConst.SERVICE_REQUEST_REVIEW,
              row.sr_type.toLowerCase(),
              row.sr_id,
            ]);
          },
        });
      }
    });
    return itemActions;
  }
}
