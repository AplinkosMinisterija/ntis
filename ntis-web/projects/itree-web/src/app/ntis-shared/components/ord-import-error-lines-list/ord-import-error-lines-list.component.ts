import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { OrderImportErrorDataListRow } from '../../models/browse-page';
import { OrderImportService } from '../../services/order-import.service';
import { ActivatedRoute, Params } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { DialogModule } from 'primeng/dialog';
import { FacilityForErrors } from '../../models/facility-for-errors';

@Component({
  selector: 'ntis-ord-import-error-lines-list',
  templateUrl: './ord-import-error-lines-list.component.html',
  styleUrls: ['./ord-import-error-lines-list.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, TableModule, TooltipModule, DialogModule],
})
export class OrdImportErrorLinesListComponent extends BaseBrowseForm<OrderImportErrorDataListRow> {
  readonly translationsReference = 'ntisShared.pages.ordersImportView';
  orfId: number;
  orgId: number;
  showSelectDialog: boolean = false;
  returnUrl: string;
  availableFacilities: FacilityForErrors[] = [];
  readonly multipleWtf = 'ERR-MULTIPLE';
  readonly municipalityName = 'ERR-COLMN-006';
  readonly senName = 'ERR-COLMN-008';
  readonly reName = 'ERR-COLMN-010';
  readonly strName = 'ERR-COLMN-012';

  cols: TableColumn[] = [
    { field: 'orfd_eil_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'orfde_column_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'orfde_column_value', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'orfde_msg_text', export: false, visible: true, type: DATA_TYPE_STRING },
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
    this.activatedRoute.queryParams.pipe(takeUntilDestroyed()).subscribe((params: Params) => {
      this.returnUrl = (params?.['returnUrl'] as string) ?? this.returnUrl;
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
        .getErrors(this.orfId, this.orgId, pagingParams, params, extendedParams)
        .subscribe((result) => {
          this.data = result.data;
          this.totalRecords = result.paging.cnt;
        });
    } else {
      this.orderImportService.getErrorsOrg(this.orfId, pagingParams, params, extendedParams).subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
      });
    }
  }

  navigateToAdrMapping(row: OrderImportErrorDataListRow): void {
    if (this.orgId) {
      void this.commonFormServices.router.navigate(
        [this.commonFormServices.router.url, NtisRoutingConst.CW_ADDRESS_MAPPINGS, RoutingConst.EDIT, this.orgId],
        {
          queryParams: {
            actionType: RoutingConst.NEW,
            errorCode: row.orfde_msg_code,
            value: row.orfde_column_value,
            orfId: this.orfId,
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
            errorCode: row.orfde_msg_code,
            value: row.orfde_column_value,
            orfId: this.orfId,
            returnUrl: this.commonFormServices.router.url,
          },
        }
      );
    }
  }

  selectWtf(row: OrderImportErrorDataListRow): void {
    this.orderImportService.getAvailableWtfs(row.orfde_id?.toString()).subscribe((result) => {
      this.availableFacilities = result.data;
      this.showSelectDialog = true;
    });
  }

  selectFacility(row: FacilityForErrors): void {
    this.orderImportService.updateWithSelectedWtf(row.wtf_id?.toString(), row.orfde_id?.toString()).subscribe(() => {
      this.showSelectDialog = false;
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant(this.translationsReference + '.wtfAddedSuccessfully') as string
      );
      const currentUrl = this.commonFormServices.router.url;
      void this.commonFormServices.router.navigateByUrl(this.returnUrl, { skipLocationChange: true }).then(() => {
        void this.commonFormServices.router.navigateByUrl(currentUrl);
      });
    });
  }

  // eslint-disable-next-line  @typescript-eslint/no-empty-function
  protected deleteRecord(): void {}
}
