import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NtisSludgeDeliveryDetails, NtisWastewaterDeliveriesDAO } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { SewageDeliveryStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { Subject, takeUntil } from 'rxjs';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NTIS_SLUDGE_TREATMENT_DELIVERY } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';

enum DialogType {
  confirm = 'confirm',
  reject = 'reject',
}

@Component({
  selector: 'app-sludge-treatment-delivery-page',
  templateUrl: './sludge-treatment-delivery-page.component.html',
  styleUrls: ['./sludge-treatment-delivery-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    DialogModule,
    FormsModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    ReactiveFormsModule,
  ],
})
export class SludgeTreatmentDeliveryPageComponent implements OnInit {
  readonly formTranslationsReference =
    'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDelivery';
  readonly confirm = DialogType.confirm;
  readonly reject = DialogType.reject;
  readonly sts_confirmed = SewageDeliveryStatus.confirmed;
  readonly sts_rejected = SewageDeliveryStatus.rejected;
  data: NtisSludgeDeliveryDetails;
  showConfirmRejectButtons: boolean = false;
  dialog: DialogType;
  wd_id: number;
  destroy$: Subject<boolean> = new Subject();

  confirmationDialogForm = new FormGroup({
    wd_delivered_wastewater_description: new FormControl<string>('', Validators.maxLength(200)),
    wd_accepted_sewage_quantity: new FormControl<number>(
      null,
      Validators.compose([Validators.min(0.01), Validators.required])
    ),
  });
  rejectionDialogForm = new FormGroup({
    wd_rejection_reason: new FormControl<string>('', Validators.maxLength(200)),
  });
  constructor(
    private commonFormServices: CommonFormServices,
    private techSuppService: NtisTechSupportService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.queryParams.token) {
      this.techSuppService
        .getDeliveryByToken(this.activatedRoute.snapshot.queryParams.token as string)
        .subscribe((result) => {
          this.wd_id = result;
          this.getDeliveryDetails(this.wd_id);
        });
    } else {
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        this.wd_id = params.id as number;
        this.getDeliveryDetails(this.wd_id);
      });
    }
  }

  getDeliveryDetails(id: number): void {
    this.techSuppService.getSludgeDeliveryInfo(id).subscribe((result) => {
      this.data = result;
      this.showConfirmRejectButtons =
        this.data.wd_state_clsf === SewageDeliveryStatus.submitted &&
        this.authService.isFormActionEnabled(NTIS_SLUDGE_TREATMENT_DELIVERY, ActionsEnum.ACTIONS_UPDATE)
          ? true
          : false;
    });
  }

  onCancel(): void {
    void this.commonFormServices.router.navigate(['.'], {
      relativeTo: this.activatedRoute.parent,
      state: { keepFilters: true },
    });
  }

  onOpenDialog(dType: DialogType): void {
    this.dialog = dType;
  }

  hideDialog(): void {
    this.dialog = null;
    this.ngOnInit();
  }

  checkFields(type: DialogType): boolean {
    if (type === this.confirm) {
      for (const field in this.confirmationDialogForm.controls) {
        if (this.confirmationDialogForm.get(field).dirty === true) {
          return true;
        }
      }
    } else if (type === this.reject) {
      for (const field in this.rejectionDialogForm.controls) {
        if (this.rejectionDialogForm.get(field).dirty === true) {
          return true;
        }
      }
    }
    return false;
  }

  onCancelDialog(type: DialogType): void {
    if (!this.checkFields(type)) {
      this.hideDialog();
    } else {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.hideDialog();
        },
      });
    }
  }

  onSubmitDelivery(dType: DialogType): void {
    this.confirmationDialogForm.markAllAsTouched();
    if (dType === this.confirm) {
      this.confirmationDialogForm.valid ? this.setDeliveryData(dType) : this.showValidationError();
    } else if (dType === this.reject) {
      this.rejectionDialogForm.valid ? this.setDeliveryData(dType) : this.showValidationError();
    }
  }

  showValidationError(): void {
    this.commonFormServices.translate.get('common.message.correctValidationErrors').subscribe((translation: string) => {
      this.commonFormServices.appMessages.showError('', translation);
    });
  }

  setDeliveryData(dType: DialogType): void {
    const delivery: NtisWastewaterDeliveriesDAO = {} as NtisWastewaterDeliveriesDAO;
    delivery.wd_id = this.wd_id;
    if (dType === this.reject) {
      delivery.wd_rejection_reason = this.rejectionDialogForm.controls.wd_rejection_reason.value;
      delivery.wd_state = this.sts_rejected;
    } else if (dType === this.confirm) {
      delivery.wd_delivered_wastewater_description =
        this.confirmationDialogForm.controls.wd_delivered_wastewater_description.value;
      delivery.wd_accepted_sewage_quantity = this.confirmationDialogForm.controls.wd_accepted_sewage_quantity.value;
      delivery.wd_state = this.sts_confirmed;
    }
    this.techSuppService.setSludgeTreatmentDeliveryData(delivery).subscribe(() => {
      this.hideDialog();
      if (dType === this.confirm) {
        this.commonFormServices.translate
          .get(this.formTranslationsReference + '.deliveryConfirmed', { wd_id: this.wd_id })
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
      } else if (dType === this.reject) {
        this.commonFormServices.translate
          .get(this.formTranslationsReference + '.deliveryRejected', { wd_id: this.wd_id })
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
      }
      this.techSuppService.sendSludgeDeliveryNotifications(this.wd_id).subscribe();
    });
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.dialog = undefined;
  }
}
