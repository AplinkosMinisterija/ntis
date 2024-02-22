import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DATA_TYPE_BOOLEAN, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NTIS_CARS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisCarsBrowseRow } from '../../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { map } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { RouterModule } from '@angular/router';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { TooltipModule } from 'primeng/tooltip';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

interface IsCarUsedOption {
  option: string;
  value: string;
}

@Component({
  selector: 'app-cars-browse-page',
  templateUrl: './cars-browse-page.component.html',
  styleUrls: ['./cars-browse-page.component.scss'],
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
export class CarsBrowsePageComponent extends BaseBrowseForm<NtisCarsBrowseRow> implements OnInit {
  readonly formCode = NTIS_CARS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'institutionsAdmin.pages.ntisCarsBrowse';
  readonly RoutingConst = RoutingConst;
  exportData: NtisCarsBrowseRow[] = [];
  DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  isUsedOption: string;
  isNotUsedOption: string;
  isCarUsedOptions: IsCarUsedOption[] = [];
  userCanCreate = false;

  cols: TableColumn[] = [
    { field: 'cr_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'cr_reg_no', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'cr_model', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'cr_capacity', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'cr_tube_length', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'cr_used', export: true, visible: true, type: DATA_TYPE_BOOLEAN },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.useExtendedSearchParams = true;
    this.commonFormServices.translate.get('common.action.yes').subscribe((translation: string) => {
      this.isUsedOption = translation;
      this.isCarUsedOptions.push({ option: this.isUsedOption, value: DB_BOOLEAN_TRUE });
    });
    this.commonFormServices.translate.get('common.action.no').subscribe((translation: string) => {
      this.isNotUsedOption = translation;
      this.isCarUsedOptions.push({ option: this.isNotUsedOption, value: DB_BOOLEAN_FALSE });
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('cr_id', new FormControl(''));
    this.searchForm.addControl('cr_reg_no', new FormControl(''));
    this.searchForm.addControl('cr_model', new FormControl(''));
    this.searchForm.addControl('cr_capacity', new FormControl(''));
    this.searchForm.addControl('cr_tube_length', new FormControl(''));
    this.searchForm.addControl('cr_used', new FormControl(''));
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
      .getCarsList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getActions(row);
            row.cr_used = row.cr_used === DB_BOOLEAN_TRUE ? this.isUsedOption : this.isNotUsedOption;
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
        .getCarsList(
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

  protected deleteRecord(recordId: string): void {
    this.adminService.deleteCar(recordId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  getActions(row: NtisCarsBrowseRow): NtisTableRowActionsItem[] {
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
              row.cr_id,
            ]);
          },
        });
      }
    });
    return itemActions;
  }

  onReturn(): void {
    void this.commonFormServices.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.INSTITUTIONS,
      NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS,
    ]);
  }
}
