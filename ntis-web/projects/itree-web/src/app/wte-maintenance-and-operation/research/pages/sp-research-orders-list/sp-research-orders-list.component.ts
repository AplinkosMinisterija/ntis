import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { Subject, map, takeUntil } from 'rxjs';
import { SpResearchOrdersListBrowseRow } from '../../models/browse-pages';
import { ResearchService } from '../../services/research.service';
import { ActivatedRoute } from '@angular/router';
import { OrderStatus } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-sp-research-orders-list',
  templateUrl: './sp-research-orders-list.component.html',
  styleUrls: ['./sp-research-orders-list.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, NtisSharedModule, TableModule, ReactiveFormsModule],
})
export class SpResearchOrdersListComponent extends BaseBrowseForm<SpResearchOrdersListBrowseRow> implements OnInit {
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.ntisSpResearchOrdersList';
  readonly orderStatus = OrderStatus;
  destroy$: Subject<boolean> = new Subject<boolean>();
  ordStatus: string;
  exportData: SpResearchOrdersListBrowseRow[] = [];

  cols: TableColumn[] = [
    { field: 'ord_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ord_created', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'ord_created_in_ntis_portal', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_state', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'orderer', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'address', export: true, visible: true, type: DATA_TYPE_STRING },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private researchService: ResearchService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      if (params.ordState == OrderStatus.confirmed || params.ordState == OrderStatus.submitted) {
        this.ordStatus = params.ordState as string;
      }
    });
  }
  ngOnInit(): void {
    this.searchForm.addControl('ord_id', new FormControl(''));
    this.searchForm.addControl('ord_created', new FormControl(''));
    this.searchForm.addControl('ord_created_in_ntis_portal', new FormControl(''));
    this.searchForm.addControl('ord_state', new FormControl(this.ordStatus));
    this.searchForm.addControl('orderer', new FormControl(''));
    this.searchForm.addControl('address', new FormControl(''));
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
    this.researchService
      .getSpResearchOrdersList(pagingParams, params, extendedParams)
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
      this.researchService
        .getSpResearchOrdersList(
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

  getActions(row: SpResearchOrdersListBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          iconTheme: 'default',
          actionName: 'read',
          action: (): void => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.RESEARCH_ORDER_PAGE,
              row.ord_id.toString(),
            ]);
          },
        });
      }
    });
    return itemActions;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  toAddNewOrder(): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      NtisRoutingConst.RESEARCH_OUTSIDE_NTIS,
    ]);
  }

  navigateToOrdersImport(): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, NtisRoutingConst.ORDERS_IMPORT]);
  }
}
