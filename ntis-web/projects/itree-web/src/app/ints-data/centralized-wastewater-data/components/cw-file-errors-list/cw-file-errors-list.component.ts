import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { TableModule } from 'primeng/table';
import { WastewaterFileErrorListRow } from '../../models/browse-pages';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Subject, takeUntil } from 'rxjs';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-cw-file-errors-list',
  templateUrl: './cw-file-errors-list.component.html',
  styleUrls: ['./cw-file-errors-list.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, TableModule, TooltipModule],
})
export class CwFileErrorsListComponent extends BaseBrowseForm<WastewaterFileErrorListRow> {
  cwfId: number;
  orgId: number;
  readonly translationsReference = 'intsData.centralizedData.pages.centralizedWastewaterDataViewPage';
  readonly municipalityName = 'ERR-COLMN-005';
  readonly senName = 'ERR-COLMN-007';
  readonly reName = 'ERR-COLMN-009';
  readonly strName = 'ERR-COLMN-011';
  destroy$: Subject<boolean> = new Subject<boolean>();
  cols: TableColumn[] = [
    { field: 'cwfd_eil_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'cwfde_column_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'cwfde_column_value', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfde_msg_text', export: false, visible: true, type: DATA_TYPE_STRING },
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
      this.centralizedService.getErrors(this.cwfId, pagingParams, params, extendedParams).subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
      });
    } else {
      this.centralizedService.getErrorsOrg(this.cwfId, pagingParams, params, extendedParams).subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
      });
    }
  }

  navigateToAdrMapping(row: WastewaterFileErrorListRow): void {
    if (this.orgId) {
      void this.commonFormServices.router.navigate(
        [this.commonFormServices.router.url, NtisRoutingConst.CW_ADDRESS_MAPPINGS, RoutingConst.EDIT, this.orgId],
        {
          queryParams: {
            actionType: RoutingConst.NEW,
            errorCode: row.cwfde_msg_code,
            value: row.cwfde_column_value,
            cwfId: this.cwfId,
            returnUrl: this.commonFormServices.router.url,
          },
        }
      );
    } else {
      void this.commonFormServices.router.navigate(
        [this.commonFormServices.router.url, NtisRoutingConst.CW_ADDRESS_MAPPINGS, RoutingConst.EDIT],
        {
          queryParams: {
            actionType: RoutingConst.NEW,
            errorCode: row.cwfde_msg_code,
            value: row.cwfde_column_value,
            cwfId: this.cwfId,
            returnUrl: this.commonFormServices.router.url,
          },
        }
      );
    }
  }

  // eslint-disable-next-line  @typescript-eslint/no-empty-function
  protected deleteRecord(): void {}
}
