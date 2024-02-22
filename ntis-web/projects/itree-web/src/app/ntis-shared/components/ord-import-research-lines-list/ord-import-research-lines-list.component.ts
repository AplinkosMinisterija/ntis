import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { TableModule } from 'primeng/table';
import { OrderImportResearchLinesListRow } from '../../models/browse-page';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { OrderImportService } from '../../services/order-import.service';
import { ActivatedRoute, Params } from '@angular/router';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';

@Component({
  selector: ' ntis-ord-import-research-lines-list',
  templateUrl: './ord-import-research-lines-list.component.html',
  styleUrls: ['./ord-import-research-lines-list.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, TableModule],
})
export class OrdImportResearchLinesListComponent extends BaseBrowseForm<OrderImportResearchLinesListRow> {
  readonly translationsReference = 'ntisShared.pages.ordersImportView';
  orfId: number;
  orgId: number;
  cols: TableColumn[] = [
    { field: 'orfd_eil_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'wtf_address', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_uzsakymo_data', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'orfd_uzsakovo_tel', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_uzsakovo_email', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_uzsakovas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_laboratorijos_komentaras', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_deguonis', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'orfd_skendincios', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'orfd_azotas', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'orfd_fosforas', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'orfd_meginio_data', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'orfd_meginio_darbuotojas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_tyrimo_data', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'orfd_tyrimo_darbuotojas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfd_pastaba_rezultatams', export: false, visible: true, type: DATA_TYPE_STRING },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private orderImportService: OrderImportService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.activatedRoute.params.pipe(takeUntilDestroyed()).subscribe((params: Params) => {
      this.orfId = params.id as number;
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
      this.orderImportService
        .getResearchLines(this.orfId, this.orgId, pagingParams, params, extendedParams)
        .subscribe((result) => {
          this.data = result.data;
          this.totalRecords = result.paging.cnt;
        });
    } else {
      this.orderImportService
        .getResearchLinesOrg(this.orfId, pagingParams, params, extendedParams)
        .subscribe((result) => {
          this.data = result.data;
          this.totalRecords = result.paging.cnt;
        });
    }
  }

  // eslint-disable-next-line  @typescript-eslint/no-empty-function
  protected deleteRecord(): void {}
}
