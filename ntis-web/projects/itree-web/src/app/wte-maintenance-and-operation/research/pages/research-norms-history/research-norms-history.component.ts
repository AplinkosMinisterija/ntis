import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { DATA_TYPE_DATE, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { ResearchNormsHistoryBrowseRow } from '../../models/browse-pages';
import { ResearchService } from '../../services/research.service';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-research-norms-history',
  templateUrl: './research-norms-history.component.html',
  styleUrls: ['./research-norms-history.component.scss'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ItreeCommonsModule, TableModule],
})
export class ResearchNormsHistoryComponent extends BaseBrowseForm<ResearchNormsHistoryBrowseRow> implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.ntisResearchNormsHistory';
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  exportData: ResearchNormsHistoryBrowseRow[] = [];

  cols: TableColumn[] = [
    { field: 'research_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'research_norm', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'facility_installation', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];

  constructor(protected override commonFormServices: CommonFormServices, private researchService: ResearchService) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('research_type', new FormControl(''));
    this.searchForm.addControl('research_norm', new FormControl(''));
    this.searchForm.addControl('facility_installation', new FormControl(''));
    this.searchForm.addControl('date_from', new FormControl(''));
    this.searchForm.addControl('date_to', new FormControl(''));
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
    this.researchService.getResearchNormsHistory(pagingParams, params, extendedParams).subscribe((result) => {
      this.data = result.data;
      this.totalRecords = result.paging.cnt;
      this.exportData = result.data;
    });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.researchService
        .getResearchNormsHistory(
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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.RESEARCH,
        NtisRoutingConst.RESEARCH_NORMS_MANAGEMENT,
      ],
      navigationExtras
    );
  }
}
