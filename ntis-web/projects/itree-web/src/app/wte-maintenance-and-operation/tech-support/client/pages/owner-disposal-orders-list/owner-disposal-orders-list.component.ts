import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {
  ACTIONS_COPY,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { NTIS_OWNER_DISPOSAL_ORDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { Subject, map, takeUntil } from 'rxjs';
import { OwnerDisposalOrdersBrowseRow, SelectOption } from '../../../models/browse-pages';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { VEZIMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { ActivatedRoute } from '@angular/router';
import { DB_BOOLEAN_FALSE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NoRegisteredFaciDialogComponent } from '@itree-web/src/app/ntis-shared/components/no-registered-faci-dialog/no-registered-faci-dialog.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisReviewsDAO } from '@itree-commons/src/lib/model/api/api';
import { DialogModule } from 'primeng/dialog';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';

@Component({
  selector: 'app-owner-disposal-orders-list',
  templateUrl: './owner-disposal-orders-list.component.html',
  styleUrls: ['./owner-disposal-orders-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
    NoRegisteredFaciDialogComponent,
    DialogModule,
    FontAwesomeModule,
  ],
})
export class OwnerDisposalOrdersListComponent extends BaseBrowseForm<OwnerDisposalOrdersBrowseRow> implements OnInit {
  readonly formCode = NTIS_OWNER_DISPOSAL_ORDERS_LIST;
  readonly reviewsTranslationsReference = 'ntisShared.pages.reviewCreate';
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.client.pages.ownerDisposalOrdersList';
  exportData: OwnerDisposalOrdersBrowseRow[] = [];
  reviewInfo: NtisReviewsDAO = {} as NtisReviewsDAO;
  showReviewDialog: boolean = false;
  destroy$: Subject<boolean> = new Subject();
  wtfOptions: SelectOption[] = [];
  loaded: boolean = false;
  noWtfDialog: boolean = false;
  filterValue: string;
  wtfFilter = new FormGroup({
    wtf_id: new FormControl<string>(null),
  });
  cols: TableColumn[] = [
    { field: 'ord_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ord_created', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_state', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ocw_discharged_sludge_amount', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ocw_completed_date', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private techService: NtisTechSupportService,
    private ntisCommonService: NtisCommonService,
    private activatedRoute: ActivatedRoute,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.wtfFilter.controls.wtf_id.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      if (value) {
        this.techService.updateSelectedWtf(Number(value)).subscribe();
      }
      this.reload();
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('ord_id', new FormControl(''));
    this.searchForm.addControl('ord_created', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('ord_state', new FormControl(''));
    this.searchForm.addControl('ocw_discharged_sludge_amount', new FormControl(''));
    this.searchForm.addControl('ocw_completed_date', new FormControl(''));
    this.ntisCommonService.getWtfList().subscribe((result) => {
      this.wtfOptions = [];
      result.data.map((wtf) => {
        this.wtfOptions.push({
          value: wtf.value,
          option: wtf.option,
        });
      });
    });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      this.filterValue = param?.['filter'] ?? this.filterValue;
      if (this.filterValue === DB_BOOLEAN_FALSE) {
        this.wtfFilter.controls.wtf_id.setValue(null);
        this.loaded = true;
      } else {
        this.ntisCommonService.getSelectedWtf().subscribe((id) => {
          this.wtfFilter.controls.wtf_id.setValue(id ? id : null);
          this.loaded = true;
          this.reload();
        });
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
    if (this.loaded) {
      extendedParams.push({
        paramName: 'wtf_id',
        paramValue: {
          condition: ExtendedSearchCondition.Equals,
          value: this.wtfFilter.controls.wtf_id.value,
          upperLower: ExtendedSearchUpperLower.Regular,
        },
      });
      this.techService
        .getOwnerDisposalOrders(pagingParams, params, extendedParams)
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
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.techService
        .getOwnerDisposalOrders(
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

  getNtisActions(row: OwnerDisposalOrdersBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: () => {
            void this.commonFormServices.router.navigate(
              [this.commonFormServices.router.url.split('?')[0], NtisRoutingConst.SERVICE_ORDER_PAGE, row.ord_id],
              {
                queryParams: {
                  filter: this.wtfFilter.controls.wtf_id.value
                    ? this.wtfFilter.controls.wtf_id.value
                    : DB_BOOLEAN_FALSE,
                  returnUrl: this.commonFormServices.router.url.split('?')[0],
                },
              }
            );
          },
        };
        rowActions.push(action);
      } else if (action === ActionsEnum.ACTIONS_COPY) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faSquarePlus' },
          actionName: 'repeatOrder',
          iconTheme: 'default',
          action: () => {
            void this.commonFormServices.router.navigate(
              [this.commonFormServices.router.url.split('?')[0], NtisRoutingConst.SERVICE_ORDER_EDIT, row.ord_id],
              {
                queryParams: {
                  actionType: ACTIONS_COPY,
                  filter: this.wtfFilter.controls.wtf_id.value
                    ? this.wtfFilter.controls.wtf_id.value
                    : DB_BOOLEAN_FALSE,
                  returnUrl: this.commonFormServices.router.url.split('?')[0],
                },
              }
            );
          },
        };
        rowActions.push(action);
      }
    });
    if (row.rev_id !== null && row.rev_score === null) {
      rowActions.push({
        icon: { iconStyle: 'regular', iconName: 'faStar' },
        iconTheme: 'default',
        actionName: 'addReview',
        action: (): void => {
          void this.commonFormServices.router.navigate(
            [RoutingConst.INTERNAL, NtisRoutingConst.REVIEW_CREATE, row.rev_id],
            { queryParams: { returnUrl: this.commonFormServices.router.url } }
          );
        },
      });
    }
    if (row.rev_id !== null && row.rev_score !== null) {
      rowActions.push({
        icon: { iconStyle: 'solid', iconName: 'faStar' },
        iconTheme: 'default',
        actionName: 'viewReview',
        action: (): void => {
          this.viewReviewInfo(row.rev_id);
        },
      });
    }
    return rowActions;
  }

  toOrderNewService(): void {
    if (this.wtfOptions.length === 0) {
      this.noWtfDialog = true;
    } else {
      if (this.wtfFilter.controls.wtf_id.value) {
        this.techService.updateSelectedWtf(Number(this.wtfFilter.controls.wtf_id.value)).subscribe(() => {
          void this.commonFormServices.router.navigate(
            [this.commonFormServices.router.url.split('?')[0], NtisRoutingConst.SERVICE_SEARCH],
            {
              queryParams: {
                srvType: VEZIMAS,
              },
            }
          );
        });
      } else {
        void this.commonFormServices.router.navigate(
          [this.commonFormServices.router.url.split('?')[0], NtisRoutingConst.SERVICE_SEARCH],
          {
            queryParams: {
              srvType: VEZIMAS,
            },
          }
        );
      }
    }
  }

  viewReviewInfo(revId: string): void {
    this.ntisCommonService.getReviewInfo(revId).subscribe((result) => {
      this.reviewInfo = {} as NtisReviewsDAO;
      this.reviewInfo.rev_id = result.rev_id;
      this.reviewInfo.rev_score = result.rev_score;
      this.reviewInfo.rev_comment = result.rev_comment;
      this.showReviewDialog = true;
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }
}
