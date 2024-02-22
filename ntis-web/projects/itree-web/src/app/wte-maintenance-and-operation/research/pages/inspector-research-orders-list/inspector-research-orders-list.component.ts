import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { OwnerResearchOrdersListBrowseRow } from '../../models/browse-pages';
import { NTIS_INSPECTOR_RESEARCH_ORDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { ResearchService } from '../../services/research.service';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { ActivatedRoute, Params } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { map } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Component({
  selector: 'app-inspector-research-orders-list',
  templateUrl: './inspector-research-orders-list.component.html',
  styleUrls: ['./inspector-research-orders-list.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, TableModule, ReactiveFormsModule, NtisSharedModule],
})
export class InspectorResearchOrdersListComponent
  extends BaseBrowseForm<OwnerResearchOrdersListBrowseRow>
  implements OnInit
{
  readonly formCode = NTIS_INSPECTOR_RESEARCH_ORDERS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.ntisOwnerResearchOrdersList';
  exportData: OwnerResearchOrdersListBrowseRow[] = [];
  complies: string;
  doesNotComply: string;
  wtfId: string;
  selectedWtf: string;

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
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.activatedRoute.params.pipe(takeUntilDestroyed()).subscribe((param: Params) => {
      this.wtfId = param?.id as string;
      this.ntisCommonService.getSelectedWtfInfo(this.wtfId).subscribe((result) => {
        this.selectedWtf = result?.data[0]?.value;
      });
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('ord_id', new FormControl(''));
    this.searchForm.addControl('ord_created', new FormControl(''));
    this.searchForm.addControl('ord_state', new FormControl(''));
    this.searchForm.addControl('ord_compliance_norms', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
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
      .getInspectorResearchOrdersList(this.wtfId, pagingParams, params, extendedParams)
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
        .getInspectorResearchOrdersList(
          this.wtfId,
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
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.RESEARCH_ORDER_PAGE,
              row.ord_id,
            ]);
          },
        });
      }
    });
    return itemActions;
  }

  onReturn(): void {
    void this.commonFormServices.router.navigate([RoutingConst.INTERNAL, NtisRoutingConst.MAP]);
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }
}
