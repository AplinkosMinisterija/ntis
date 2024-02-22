import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { SEWAGE_DELIV_STATUS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NTIS_SEWAGE_DELIVERIES_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import {
  SWG_DLV_STS_REJECTED,
  SWG_DLV_STS_SUBMITTED,
} from 'projects/itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { map } from 'rxjs';
import { NtisTechSupportService } from '../../../../shared/services/ntis-tech-support.service';
import { SewageDeliveriesBrowseRow } from '../../../models/browse-pages';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { TooltipModule } from 'primeng/tooltip';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-sewage-deliveries-list',
  templateUrl: './sewage-deliveries-list.component.html',
  styleUrls: ['./sewage-deliveries-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    RouterModule,
    TableModule,
    TooltipModule,
  ],
})
export class SewageDeliveriesListComponent extends BaseBrowseForm<SewageDeliveriesBrowseRow> implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveriesList';
  readonly formCode = NTIS_SEWAGE_DELIVERIES_LIST;
  readonly SEWAGE_DELIV_STATUS = SEWAGE_DELIV_STATUS;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly RoutingConst = RoutingConst;
  readonly SWG_DLV_STS_REJECTED = SWG_DLV_STS_REJECTED;
  readonly SWG_DLV_STS_SUBMITTED = SWG_DLV_STS_SUBMITTED;
  exportData: SewageDeliveriesBrowseRow[];
  userCanCreate: boolean;

  cols: TableColumn[] = [
    { field: 'wd_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'wd_delivery_date', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wd_delivered_quantity', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'cr_reg_no', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wd_state', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'address', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private techSupportService: NtisTechSupportService,
    private router: Router,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('wd_id', new FormControl(''));
    this.searchForm.addControl('wd_delivery_date', new FormControl(''));
    this.searchForm.addControl('wd_delivered_quantity', new FormControl(''));
    this.searchForm.addControl('cr_reg_no', new FormControl(''));
    this.searchForm.addControl('wto_name', new FormControl(''));
    this.searchForm.addControl('wd_state', new FormControl(''));
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
    this.techSupportService
      .getSewageDeliveriesList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getNtisActions(row);
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
      this.techSupportService
        .getSewageDeliveriesList(
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

  getNtisActions(row: SewageDeliveriesBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: () => {
            this.navigateToView(row.wd_id.toString());
          },
        };
        rowActions.push(action);
      } else if (action === ActionsEnum.ACTIONS_CANCEL && row.wd_state_clsf == this.SWG_DLV_STS_SUBMITTED) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faCircleXmark' },
          iconTheme: 'default',
          actionName: 'cancel',
          action: () => {
            this.cancelRecordWithConfirmation(row.wd_id.toString());
          },
        };
        rowActions.push(action);
      } else if (action === ActionsEnum.ACTIONS_COPY && row.wd_state_clsf == this.SWG_DLV_STS_REJECTED) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faSquarePlus' },
          iconTheme: 'default',
          actionName: 'createNewBasedOnOld',
          action: () => {
            this.navigateToCopy(row.wd_id.toString());
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  protected deleteRecord(recordId: string): void {
    this.techSupportService.deleteSewageDelivery(recordId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  navigateToCopy(recordId: string): void {
    void this.router.navigate([this.router.url, RoutingConst.EDIT, recordId], {
      queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
    });
  }

  navigateToView(recordId: string): void {
    void this.router.navigate([this.router.url, RoutingConst.VIEW, recordId]);
  }

  cancelRecordWithConfirmation(
    recordId: string,
    message: string = 'common.message.cancelConfirmation',
    translateMessage: boolean = true
  ): void {
    const showConfirmationMessage = (confirmationMessage: string): void => {
      this.commonFormServices.confirmationService.confirm({
        message: confirmationMessage,
        accept: () => {
          this.techSupportService.cancelSewageDelivery(recordId).subscribe(() => {
            this.reload();
            this.commonFormServices.translate.get('common.message.cancelSuccess').subscribe((translation: string) => {
              this.commonFormServices.appMessages.showSuccess('', translation);
            });
          });
        },
      });
    };
    if (translateMessage) {
      this.commonFormServices.translate.get(message).subscribe((translation: string) => {
        showConfirmationMessage(translation);
      });
    } else {
      showConfirmationMessage(message);
    }
  }
}
