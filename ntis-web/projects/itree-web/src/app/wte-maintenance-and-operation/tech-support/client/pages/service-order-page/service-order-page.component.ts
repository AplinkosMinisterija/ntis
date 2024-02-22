import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { ORD_STS_FINISHED, PRIEZIURA, VEZIMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NTIS_SERVICE_ORDER_PAGE } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisServiceOrderRequest } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { OrderStatus, SewageDeliveryStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule, Location } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { SharedModule } from '../../../shared/shared.module';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-service-order-page',
  standalone: true,
  templateUrl: './service-order-page.component.html',
  styleUrls: ['./service-order-page.component.scss'],
  imports: [
    CalendarModule,
    CommonModule,
    DialogModule,
    FontAwesomeModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    SharedModule,
  ],
})
export class ServiceOrderPageComponent implements OnInit {
  ord_id: string;
  data: NtisServiceOrderRequest;
  minDate: Date = new Date();
  zeroToAdd: string = '';
  // date strings
  ordCreatedStr: string;
  rejectionDateStr: string;
  ordPlannedDateStr: string;
  ordCompletedStr: string;
  //  dialog and order status change related
  maxDateCompleted: Date = new Date();
  orderDialog: boolean = false;
  edit: boolean = false;
  status: OrderStatus;
  buttonString: string;
  changedStatus: string;
  actionType: string;
  completedDate: string = '.completed_date';
  completedWorks: string = '.completed_works_description';
  confirmationText: string = '.confirmationDisposal';
  finishText: string = '.finishTextDisposal';
  defaultDate: Date = new Date();
  showReviewDialog = false;

  readonly reviewsTranslationsReference = 'ntisShared.pages.reviewCreate';
  // constants
  readonly formCode = NTIS_SERVICE_ORDER_PAGE;
  readonly orderStatus = OrderStatus;
  readonly sewageDeliveryStatus = SewageDeliveryStatus;
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage';
  readonly PRIEZIURA = PRIEZIURA;
  readonly VEZIMAS = VEZIMAS;

  userIsOwner: boolean;
  userIsServiceProvider: boolean;
  destroy$: Subject<boolean> = new Subject();
  returnUrl: string;
  filterValue: string;
  userCanCreate: boolean;
  userCanUpdate: boolean;

  constructor(
    private commonFormServices: CommonFormServices,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    private techSuppService: NtisTechSupportService,
    private datePipe: S2DatePipe,
    public faIconsService: FaIconsService,
    private location: Location
  ) {
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
    this.userIsOwner = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.INTS_OWNER_ACTIONS);
    this.userIsServiceProvider = this.authService.isFormActionEnabled(
      this.formCode,
      ActionsEnum.SERVICE_PROVIDER_ACTIONS
    );
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      this.returnUrl = param?.['returnUrl'] ?? this.returnUrl;
      this.filterValue = param?.['filter'] ?? this.filterValue;
    });
  }

  form = new FormGroup({
    ord_rejection_reason: new FormControl<string>(null),
    ord_completion_estimate_date: new FormControl<Date>(null),
    ord_planned_works_description: new FormControl<string>(null),
    ord_state: new FormControl<string>(null),
    ocw_completed_works_description: new FormControl<string>(null),
    ocw_completed_date: new FormControl<Date>(null),
    ocw_discharged_sludge_amount: new FormControl<number>(null, Validators.compose([Validators.max(999999999999)])),
    ocw_cr_id: new FormControl<number>(null),
  });

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.queryParams.token) {
      this.techSuppService
        .getOrderByToken(this.activatedRoute.snapshot.queryParams.token as string)
        .subscribe((result) => {
          this.ord_id = result.toString();
          this.getServiceOrderDetails(this.ord_id);
        });
    } else {
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        this.ord_id = params.id as string;
        this.getServiceOrderDetails(this.ord_id);
      });
    }
  }

  getServiceOrderDetails(id: string): void {
    this.techSuppService.getServiceOrderDetails(id).subscribe((result) => {
      this.data = result;
      if (this.data.orderDetails.ocw_discharged_sludge_amount % 1 === 0) {
        this.zeroToAdd = '.0';
      }
      this.minDate = new Date(this.data.orderDetails.ord_created);
      this.ordCreatedStr = this.datePipe.transform(this.data.orderDetails.ord_created, true);
      this.rejectionDateStr = this.datePipe.transform(this.data.orderDetails.ord_rejection_date, true);
      this.ordPlannedDateStr = this.datePipe.transform(this.data.orderDetails.ord_completion_estimate_date);
      this.ordCompletedStr = this.datePipe.transform(this.data.orderDetails.ocw_completed_date);
      if (this.data.serviceDescription.srv_type === this.PRIEZIURA) {
        this.completedDate = '.completed_date_tech';
        this.completedWorks = '.completed_works_description_tech';
        this.confirmationText = '.confirmationText';
        this.finishText = '.finishText';
      }
    });
  }

  onCancel(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };

    if (this.returnUrl && this.filterValue) {
      void this.commonFormServices.router.navigate([this.returnUrl], {
        queryParams: {
          filter: this.filterValue,
        },
        state: { keepFilters: true },
      });
    } else {
      void this.commonFormServices.router.navigateByUrl(
        this.commonFormServices.router.url.split('/' + NtisRoutingConst.SERVICE_ORDER_PAGE)[0],
        navigationExtras
      );
    }
  }

  updateOrder(): void {
    this.form.markAllAsTouched();
    if (
      this.form.controls.ocw_completed_date.valid &&
      this.form.controls.ocw_discharged_sludge_amount.valid &&
      this.form.controls.ocw_cr_id.valid
    ) {
      this.data.orderDetails = {
        ...this.data.orderDetails,
        ord_rejection_reason: this.form.controls.ord_rejection_reason.value,
        ord_completion_estimate_date: this.form.controls.ord_completion_estimate_date.value,
        ord_planned_works_description: this.form.controls.ord_planned_works_description.value,
        ocw_completed_date: this.form.controls.ocw_completed_date.value,
        ocw_completed_works_description: this.form.controls.ocw_completed_works_description.value,
        ocw_discharged_sludge_amount: this.form.controls.ocw_discharged_sludge_amount.value,
        ocw_cr_id: this.form.controls.ocw_cr_id.value,
      };
      if (this.edit) {
        this.changedStatus = this.translationsReference + '.orderEdited';
        this.actionType = 'update';
      } else {
        this.data.orderDetails = {
          ...this.data.orderDetails,
          ord_state_clsf: this.status,
        };
        this.changedStatus = this.translationsReference + '.' + this.status;
      }
      this.techSuppService.updateOrderDetails(this.data.orderDetails).subscribe((order) => {
        this.data.orderDetails = order;
        this.rejectionDateStr = this.datePipe.transform(this.data.orderDetails.ord_rejection_date, true);
        this.ordPlannedDateStr = this.datePipe.transform(this.data.orderDetails.ord_completion_estimate_date);
        this.ordCompletedStr = this.datePipe.transform(this.data.orderDetails.ocw_completed_date);
        this.commonFormServices.translate.get(this.changedStatus).subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
        if (
          order.ord_state_clsf === OrderStatus.cancelled ||
          (order.ord_state_clsf === OrderStatus.finished && this.data.serviceDescription.srv_type === this.PRIEZIURA)
        ) {
          this.onCancel();
        }
        this.techSuppService.sendOrderEditNotifications(this.data.orderDetails.ord_id, this.actionType).subscribe();
      });
      if (this.form.controls.ocw_discharged_sludge_amount.value % 1 === 0) {
        this.zeroToAdd = '.0';
      } else {
        this.zeroToAdd = '';
      }
      this.cancelDialog();
    }
  }

  // Dialog related methods
  editOrder(): void {
    this.form.reset();
    this.orderDialog = true;
    this.form.controls.ord_rejection_reason.setValue(this.data.orderDetails.ord_rejection_reason);
    this.form.controls.ord_completion_estimate_date.setValue(
      this.data.orderDetails.ord_completion_estimate_date
        ? new Date(this.data.orderDetails.ord_completion_estimate_date)
        : null
    );
    this.form.controls.ord_planned_works_description.setValue(this.data.orderDetails.ord_planned_works_description);
    this.form.controls.ord_state.setValue(this.data.orderDetails.ord_state);
    this.form.controls.ocw_completed_works_description.setValue(this.data.orderDetails.ocw_completed_works_description);
    this.form.controls.ocw_completed_date.setValue(
      this.data.orderDetails.ocw_completed_date && this.data.orderDetails.ord_state_clsf === ORD_STS_FINISHED
        ? new Date(this.data.orderDetails.ocw_completed_date)
        : null
    );
    this.form.controls.ocw_discharged_sludge_amount.setValue(this.data.orderDetails.ocw_discharged_sludge_amount);
    this.form.controls.ocw_cr_id.setValue(this.data.orderDetails.ocw_cr_id);
  }

  handleClickEdit(): void {
    this.editOrder();
    this.orderDialog = true;
    this.edit = true;
    this.buttonString = '.saveChanges';
  }

  createDelivery(): void {
    void this.commonFormServices.router.navigate(
      [this.commonFormServices.router.url, NtisRoutingConst.SEWAGE_DELIVERIES, RoutingConst.NEW, this.ord_id],
      {
        queryParams: { actionType: RoutingConst.NEW },
      }
    );
  }

  cancelOrder(): void {
    this.editOrder();
    this.status = OrderStatus.cancelled;
    this.buttonString = '.cancelOrder';
  }

  confirmOrder(): void {
    this.editOrder();
    this.status = OrderStatus.confirmed;
    this.buttonString = '.confirmOrder';
  }

  rejectOrder(): void {
    this.editOrder();
    this.status = OrderStatus.rejected;
    this.buttonString = '.rejectOrder';
  }

  finishOrder(): void {
    if (this.data.serviceDescription.srv_type === this.VEZIMAS) {
      this.form.controls.ocw_discharged_sludge_amount.addValidators(Validators.required);
      this.form.controls.ocw_cr_id.addValidators(Validators.required);
    }
    this.minDate = new Date(this.data.orderDetails.ord_created);
    this.form.controls.ocw_completed_date.addValidators(Validators.required);
    this.editOrder();
    this.status = OrderStatus.finished;
    this.buttonString = '.finishOrder';
  }

  cancelDialog(): void {
    this.orderDialog = false;
    this.edit = false;
    this.status = null;
    this.form.controls.ocw_completed_date.clearValidators();
    this.form.controls.ocw_discharged_sludge_amount.clearValidators();
    this.form.controls.ocw_cr_id.clearValidators();
  }

  navigateToReviewForm(): void {
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, NtisRoutingConst.REVIEW_CREATE, this.data?.revId],
      { queryParams: { returnUrl: this.commonFormServices.router.url } }
    );
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.orderDialog = false;
  }
}
