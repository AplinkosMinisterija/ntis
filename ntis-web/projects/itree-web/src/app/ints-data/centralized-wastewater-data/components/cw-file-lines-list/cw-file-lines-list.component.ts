import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { TableModule } from 'primeng/table';
import { WastewaterFileDataListRow } from '../../models/browse-pages';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-cw-file-lines-list',
  templateUrl: './cw-file-lines-list.component.html',
  styleUrls: ['./cw-file-lines-list.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, TableModule],
})
export class CwFileLinesListComponent extends BaseBrowseForm<WastewaterFileDataListRow> {
  cwfId: number;
  orgId: number;
  readonly translationsReference = 'intsData.centralizedData.pages.centralizedWastewaterDataViewPage';
  destroy$: Subject<boolean> = new Subject<boolean>();
  cols: TableColumn[] = [
    { field: 'cwfd_eil_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'cwfd_pastato_kodas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_pastato_adr_kodas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_patalpos_kodas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_adresas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_nuot_salinimo_budas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_prijungimo_data', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'cwfd_atjungimo_data', export: false, visible: true, type: DATA_TYPE_DATE },
  ];
  constructor(
    protected override commonFormServices: CommonFormServices,
    private centralizedService: CentralizedWastewaterDataService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.cwfId = params.id as number;
      this.orgId = params.orgId as number;
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
    if (this.orgId) {
      this.centralizedService.getLines(this.cwfId, pagingParams, params, extendedParams).subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
      });
    } else {
      this.centralizedService.getLinesOrg(this.cwfId, pagingParams, params, extendedParams).subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
      });
    }
  }

  // eslint-disable-next-line  @typescript-eslint/no-empty-function
  protected deleteRecord(): void {}
}
