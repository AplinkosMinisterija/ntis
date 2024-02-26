import { Component, OnInit } from '@angular/core';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { NTIS_ADR_ADDRESS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '../../../ntis-shared/ntis-shared.module';
import { Table, TableModule } from 'primeng/table';
import { map } from 'rxjs';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { CommonModule } from '@angular/common';
import { MenuItem } from 'primeng/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TooltipModule } from 'primeng/tooltip';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { AdrAddressesBrowseRow, TableColumn } from '../../models/browse-pages';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-adr-addresses-list-page',
  templateUrl: './adr-addresses-list-page.component.html',
  styleUrls: ['./adr-addresses-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    RouterModule,
    TableModule,
    NtisSharedModule,
    FormsModule,
    ReactiveFormsModule,
    TooltipModule,
    DialogModule,
  ],
})
export class AdrAddressesListPageComponent extends BaseBrowseForm<AdrAddressesBrowseRow> implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.adrAddressList';
  readonly formCode = NTIS_ADR_ADDRESS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly RoutingConst = RoutingConst;

  exportData: AdrAddressesBrowseRow[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  showDetailsDialog: boolean = false;
  addressDetails: AdrAddressesBrowseRow;

  cols: TableColumn[] = [
    { field: 'ad_id', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'ads_aob_code', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'apl_pat_code', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ad_address', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'update_timestamp', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'str_street_code', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'str_name', export: true, visible: false, type: DATA_TYPE_STRING },
    { field: 're_recidence_code', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 're_name', export: true, visible: false, type: DATA_TYPE_STRING },
    { field: 'sen_code', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'sen_name', export: true, visible: false, type: DATA_TYPE_STRING },
    { field: 'ad_coordinate_latitude', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'ad_coordinate_longitude', export: true, visible: false, type: DATA_TYPE_NUMBER },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('ads_aob_code', new FormControl(''));
    this.searchForm.addControl('apl_pat_code', new FormControl(''));
    this.searchForm.addControl('ad_address', new FormControl(''));
    this.searchForm.addControl('update_timestamp', new FormControl(''));
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
      .getAdrAddressesList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          this.rowMenuItems = {};
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
        .getAdrAddressesList(
          this.getPagingParams(dataTable.first, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
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

  getActions(row: AdrAddressesBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faInfo' },
          iconTheme: 'default',
          actionName: 'showInfo',
          action: () => {
            this.showDetailsDialog = true;
            this.addressDetails = row;
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  protected override deleteRecord(recId: string): void {
    throw new Error('Method not implemented.');
  }
}
