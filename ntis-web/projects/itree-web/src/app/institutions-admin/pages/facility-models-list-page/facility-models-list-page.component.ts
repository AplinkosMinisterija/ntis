import { Component, OnInit } from '@angular/core';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { FacilityModelsBrowseRow, TableColumn } from '../../models/browse-pages';
import { NTIS_FACILITY_MODELS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
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

@Component({
  selector: 'app-facility-models-list-page',
  templateUrl: './facility-models-list-page.component.html',
  styleUrls: ['./facility-models-list-page.component.scss'],
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
  ],
})
export class FacilityModelsListPageComponent extends BaseBrowseForm<FacilityModelsBrowseRow> implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.facilityModelsList';
  readonly formCode = NTIS_FACILITY_MODELS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly RoutingConst = RoutingConst;

  exportData: FacilityModelsBrowseRow[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};

  cols: TableColumn[] = [
    { field: 'rfc_id', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'rfc_manufa', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfc_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfc_description', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfc_date_from', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rfc_date_to', export: true, visible: true, type: DATA_TYPE_STRING },
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
    this.searchForm.addControl('rfc_manufa', new FormControl(''));
    this.searchForm.addControl('rfc_meaning', new FormControl(''));
    this.searchForm.addControl('rfc_description', new FormControl(''));
    this.searchForm.addControl('rfc_date_from', new FormControl(''));
    this.searchForm.addControl('rfc_date_to', new FormControl(''));
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
      .getFacilityModelsList(pagingParams, params, extendedParams)
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
        .getFacilityModelsList(
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

  getActions(row: FacilityModelsBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_UPDATE) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faPencil' },
          iconTheme: 'default',
          actionName: 'update',
          action: (): void => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              RoutingConst.EDIT,
              row.rfc_id,
            ]);
          },
        });
      }
      if (action === ActionsEnum.ACTIONS_DELETE) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faTrash' },
          actionName: 'delete',
          iconTheme: 'default',
          action: (): void => {
            this.deleteRecordWithConfirmation(row.rfc_id.toString());
          },
        });
      }
    });
    return itemActions;
  }

  protected override deleteRecord(recId: string): void {
    this.adminService.delFacilityModelRecord(recId).subscribe(() => {
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
        this.reload();
      });
    });
  }
}
