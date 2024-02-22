import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, map, takeUntil } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { SludgeTreatmentDeliveriesBrowseRow } from '../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { SewageDeliveryStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisWastewaterDeliveriesDAO } from '@itree-commons/src/lib/model/api/api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

enum dialogType {
  confirm = 'confirm',
  reject = 'reject',
}

@Component({
  selector: 'app-sludge-treatment-deliveries-list-page',
  templateUrl: './sludge-treatment-deliveries-list-page.component.html',
  styleUrls: ['./sludge-treatment-deliveries-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    TableModule,
    NtisSharedModule,
    ReactiveFormsModule,
    DialogModule,
  ],
})
export class SludgeTreatmentDeliveriesListPageComponent
  extends BaseBrowseForm<SludgeTreatmentDeliveriesBrowseRow>
  implements OnInit
{
  readonly formCode = NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly swg_sts_submitted = SewageDeliveryStatus.submitted;
  readonly formTranslationsReference =
    'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDeliveriesList';
  swgStatus: string;
  orgId: string;
  exportData: SludgeTreatmentDeliveriesBrowseRow[] = [];
  destroy$: Subject<boolean> = new Subject();
  confirmationDialog: boolean;
  rejectionDialog: boolean;
  wd_id: number;

  confirmationDialogForm = new FormGroup({
    wd_id: new FormControl({ disabled: true, hidden: true }),
    wd_delivered_wastewater_description: new FormControl<string>('', Validators.maxLength(200)),
    wd_accepted_sewage_quantity: new FormControl<number>(
      null,
      Validators.compose([Validators.min(0.01), Validators.required])
    ),
  });
  rejectionDialogForm = new FormGroup({
    wd_id: new FormControl({ disabled: true, hidden: true }),
    wd_rejection_reason: new FormControl<string>('', Validators.maxLength(200)),
  });
  cols: TableColumn[] = [
    { field: 'wd_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'wd_delivery_date', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'wd_delivered_quantity', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'cr_reg_no', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wd_state_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    private techSuppService: NtisTechSupportService,
    protected override commonFormServices: CommonFormServices,
    protected activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.swgStatus = params.status as string;
      this.orgId = params.orgId as string;
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('wd_id', new FormControl('', Validators.maxLength(10)));
    this.searchForm.addControl('wd_delivery_date', new FormControl('', Validators.maxLength(20)));
    this.searchForm.addControl('wd_delivered_quantity', new FormControl('', Validators.maxLength(12)));
    this.searchForm.addControl('cr_reg_no', new FormControl('', Validators.maxLength(20)));
    this.searchForm.addControl('org_name', new FormControl('', Validators.maxLength(200)));
    this.searchForm.addControl('wd_state', new FormControl(''));
    this.searchForm.addControl('org_id', new FormControl(''));
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
    if (this.swg_sts_submitted != null && params.size === 0 && extendedParams.length === 0) {
      params.set('p_wd_state', this.swgStatus);
      if (this.orgId != null) {
        params.set('p_org_id', this.orgId);
      }
    }

    this.techSuppService
      .getSludgeTreatmentDeliveriesList(pagingParams, params, extendedParams)
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
      this.techSuppService
        .getSludgeTreatmentDeliveriesList(
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

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  openConfirmationDialog(row: SludgeTreatmentDeliveriesBrowseRow): void {
    this.confirmationDialog = true;
    this.wd_id = row.wd_id;
  }

  openRejectionDialog(row: SludgeTreatmentDeliveriesBrowseRow): void {
    this.rejectionDialog = true;
    this.wd_id = row.wd_id;
  }

  hideDialog(type: string): void {
    if (type === dialogType.confirm) {
      this.confirmationDialog = false;
      this.confirmationDialogForm.reset();
    } else if (type === dialogType.reject) {
      this.rejectionDialog = false;
      this.rejectionDialogForm.reset();
    }
  }

  getActions(row: SludgeTreatmentDeliveriesBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          iconTheme: 'default',
          actionName: 'view',
          action: (): void => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.SLUDGE_TREATMENT_DELIVERY,
              row.wd_id,
            ]);
          },
        });
      }
      if (action === ActionsEnum.ACTIONS_UPDATE && row.wd_state === SewageDeliveryStatus.submitted) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faCheckCircle' },
          iconTheme: 'default',
          actionName: 'confirmDelivery',
          action: (): void => {
            this.openConfirmationDialog(row);
          },
        });
      }
      if (action === ActionsEnum.ACTIONS_UPDATE && row.wd_state === SewageDeliveryStatus.submitted) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faTimesCircle' },
          iconTheme: 'default',
          actionName: 'rejectDelivery',
          action: (): void => {
            this.openRejectionDialog(row);
          },
        });
      }
    });
    return itemActions;
  }

  checkFields(type: string): boolean {
    if (type === dialogType.confirm) {
      for (const field in this.confirmationDialogForm.controls) {
        if (this.confirmationDialogForm.get(field).dirty === true) {
          return true;
        }
      }
    } else if (type === dialogType.reject) {
      for (const field in this.rejectionDialogForm.controls) {
        if (this.rejectionDialogForm.get(field).dirty === true) {
          return true;
        }
      }
    }
    return false;
  }

  onCancel(type: string): void {
    if (!this.checkFields(type)) {
      this.hideDialog(type);
    } else {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.hideDialog(type);
          this.reload();
        },
      });
    }
  }

  onConfirmDelivery(): void {
    this.commonFormServices.appMessages.clearMessages();
    this.confirmationDialogForm.markAllAsTouched();
    if (this.confirmationDialogForm.valid) {
      const delivery: NtisWastewaterDeliveriesDAO = {} as NtisWastewaterDeliveriesDAO;
      delivery.wd_id = this.wd_id;
      delivery.wd_delivered_wastewater_description =
        this.confirmationDialogForm.controls.wd_delivered_wastewater_description.value;
      delivery.wd_accepted_sewage_quantity = this.confirmationDialogForm.controls.wd_accepted_sewage_quantity.value;
      delivery.wd_state = SewageDeliveryStatus.confirmed;
      this.techSuppService.setSludgeTreatmentDeliveryData(delivery).subscribe(() => {
        this.hideDialog(dialogType.confirm);
        this.reload();
        this.techSuppService.sendSludgeDeliveryNotifications(this.wd_id).subscribe();
        this.commonFormServices.translate
          .get(this.formTranslationsReference + '.deliveryConfirmed', { wd_id: this.wd_id })
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
      });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  onRejectDelivery(): void {
    this.rejectionDialogForm.markAllAsTouched();
    if (this.rejectionDialogForm.valid) {
      const delivery: NtisWastewaterDeliveriesDAO = {} as NtisWastewaterDeliveriesDAO;
      delivery.wd_id = this.wd_id;
      delivery.wd_rejection_reason = this.rejectionDialogForm.controls.wd_rejection_reason.value;
      delivery.wd_state = SewageDeliveryStatus.rejected;
      this.techSuppService.setSludgeTreatmentDeliveryData(delivery).subscribe(() => {
        this.hideDialog(dialogType.reject);
        this.reload();
        this.techSuppService.sendSludgeDeliveryNotifications(this.wd_id).subscribe();
        this.commonFormServices.translate
          .get(this.formTranslationsReference + '.deliveryRejected', { wd_id: this.wd_id })
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
      });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.confirmationDialog = undefined;
    this.rejectionDialog = undefined;
  }
}
