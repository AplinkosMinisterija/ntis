import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { IdKeyValuePair, NtisReviewsDAO } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_OWNER_RESEARCH_ORDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { Subject, map, takeUntil } from 'rxjs';
import { OwnerResearchOrdersListBrowseRow } from '../../models/browse-pages';
import { ResearchService } from '../../services/research.service';
import { SelectOption } from '../../../tech-support/models/browse-pages';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { TYRIMAI } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NtisTechSupportService } from '../../../tech-support/shared/services/ntis-tech-support.service';
import { ActivatedRoute } from '@angular/router';
import { DB_BOOLEAN_FALSE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NoRegisteredFaciDialogComponent } from '@itree-web/src/app/ntis-shared/components/no-registered-faci-dialog/no-registered-faci-dialog.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-owner-research-orders-list',
  templateUrl: './owner-research-orders-list.component.html',
  styleUrls: ['./owner-research-orders-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
    NoRegisteredFaciDialogComponent,
    FontAwesomeModule,
    DialogModule,
  ],
})
export class OwnerResearchOrdersListComponent
  extends BaseBrowseForm<OwnerResearchOrdersListBrowseRow>
  implements OnInit
{
  readonly formCode = NTIS_OWNER_RESEARCH_ORDERS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.ntisOwnerResearchOrdersList';
  readonly reviewsTranslationsReference = 'ntisShared.pages.reviewCreate';
  reviewInfo: NtisReviewsDAO = {} as NtisReviewsDAO;
  showReviewDialog: boolean = false;
  destroy$: Subject<boolean> = new Subject();
  exportData: OwnerResearchOrdersListBrowseRow[] = [];
  orderComplianceOptions: IdKeyValuePair[] = [];
  complies: string;
  doesNotComply: string;
  filterValue: string;
  wtfOptions: SelectOption[] = [];
  loaded: boolean = false;
  noWtfDialog: boolean = false;
  wtfFilter = new FormGroup({
    wtf_id: new FormControl<string>(null),
  });

  cols: TableColumn[] = [
    { field: 'ord_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ord_created', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'ord_state', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_compliance_norms', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private researchService: ResearchService,
    private ntisCommonService: NtisCommonService,
    private activatedRoute: ActivatedRoute,
    private techService: NtisTechSupportService,
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
    this.searchForm.addControl('ord_state', new FormControl(''));
    this.searchForm.addControl('ord_compliance_norms', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));

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
      this.researchService
        .getOwnerResearchOrdersList(pagingParams, params, extendedParams)
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
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.researchService
        .getOwnerResearchOrdersList(
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

  getActions(row: OwnerResearchOrdersListBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          iconTheme: 'default',
          actionName: 'read',
          action: (): void => {
            void this.commonFormServices.router.navigate(
              [this.commonFormServices.router.url.split('?')[0], NtisRoutingConst.RESEARCH_ORDER_PAGE, row.ord_id],
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
        });
      }
    });
    if (row.rev_id !== null && row.rev_score === null) {
      itemActions.push({
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
      itemActions.push({
        icon: { iconStyle: 'solid', iconName: 'faStar' },
        iconTheme: 'default',
        actionName: 'viewReview',
        action: (): void => {
          this.viewReviewInfo(row.rev_id);
        },
      });
    }
    return itemActions;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
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
              queryParams: { srvType: TYRIMAI },
            }
          );
        });
      } else {
        void this.commonFormServices.router.navigate(
          [this.commonFormServices.router.url.split('?')[0], NtisRoutingConst.SERVICE_SEARCH],
          {
            queryParams: { srvType: TYRIMAI },
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
}
