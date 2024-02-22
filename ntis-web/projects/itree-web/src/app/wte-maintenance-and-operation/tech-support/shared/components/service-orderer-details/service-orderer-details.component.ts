import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NtisServiceOrderRequest, SprFile } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { TYRIMAI, VEZIMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { Subject, takeUntil } from 'rxjs';
import { NtisTechSupportService } from '../../services/ntis-tech-support.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { CommonFormServices } from '@itree/ngx-s2-commons';

@Component({
  selector: 'app-service-orderer-details',
  templateUrl: './service-orderer-details.component.html',
  styleUrls: ['./service-orderer-details.component.scss'],
})
export class ServiceOrdererDetailsComponent implements OnChanges {
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.client.components.serviceOrdererDetails';
  readonly editTranslationsReference =
    'wteMaintenanceAndOperation.techSupport.client.components.serviceOrdererDetailsEdit';
  readonly researchOutsideNtisReference = 'wteMaintenanceAndOperation.research.pages.researchOutsideNtis';
  readonly VEZIMAS = VEZIMAS;
  readonly TYRIMAI = TYRIMAI;
  destroy$: Subject<boolean> = new Subject();
  returnUrl: string;
  filterValue: string;
  defaultDateTo: Date = new Date();
  isValid: boolean = true;

  @Input() editMode: boolean = false;
  @Input() orderDetails: NtisServiceOrderRequest;
  @Input() isClient: boolean = true;
  @Input() srvType: string;
  @Input() hideAccordion: boolean = true;
  @Input() selectedServices: string[] = [];
  @Input() csId: number;
  @Output() orderDetailsChange = new EventEmitter<NtisServiceOrderRequest>();

  minDate: Date = new Date();
  dateStr: string;

  form = new FormGroup({
    ord_name: new FormControl<string>(null),
    ord_email: new FormControl<string>(
      null,
      Validators.compose([Validators.pattern(new RegExp(EMAIL_PATTERN)), Validators.maxLength(100)])
    ),
    ord_phone_number: new FormControl<string>(null, [
      Validators.required,
      Validators.compose([Validators.maxLength(12), Validators.pattern(new RegExp(PHONE_PATTERN))]),
    ]),
    ord_completion_request_date: new FormControl<Date>(
      null,
      Validators.compose([Validators.minLength(10), Validators.maxLength(10)])
    ),
    ord_additional_description: new FormControl<string>(null),
    ord_prefered_date_from: new FormControl<Date>(
      null,
      Validators.compose([Validators.minLength(10), Validators.maxLength(10)])
    ),
    ord_prefered_date_to: new FormControl<Date>(
      null,
      Validators.compose([Validators.minLength(10), Validators.maxLength(10)])
    ),
  });
  constructor(
    private techSuppService: NtisTechSupportService,
    private datePipe: S2DatePipe,
    private commonFormServices: CommonFormServices,
    public faIconsService: FaIconsService,
    private fileUploadService: FileUploadService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.form.controls.ord_prefered_date_to.addValidators([
      SprDateValidators.validateDateToEqual(this.form.controls.ord_prefered_date_from),
    ]);
    this.form.controls.ord_prefered_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.form.controls.ord_prefered_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.defaultDateTo = value;
    });
    this.form.controls.ord_prefered_date_from.addValidators([
      SprDateValidators.validateDateFromEqual(this.form.controls.ord_prefered_date_to),
    ]);
    this.form.controls.ord_prefered_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.ord_prefered_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    {
      this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
        this.returnUrl = param?.['returnUrl'] ?? this.returnUrl;
        this.filterValue = param?.['filter'] ?? this.filterValue;
      });
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.orderDetails) {
      const currVal = changes.orderDetails.currentValue as NtisServiceOrderRequest;
      this.setOrderValuesToForm(currVal);
    }
    if (changes.selectedServices) {
      this.orderDetails.selectedResearchTypes = changes.selectedServices.currentValue as string[];
    }
  }

  setOrderValuesToForm(orderDetails: NtisServiceOrderRequest = this.orderDetails): void {
    this.form.controls.ord_name.setValue(orderDetails?.orderDetails.ord_name || null);
    this.form.controls.ord_email.setValue(orderDetails?.orderDetails.ord_email || null);
    this.form.controls.ord_phone_number.setValue(orderDetails?.orderDetails.ord_phone_number || null);
    this.form.controls.ord_completion_request_date.setValue(
      orderDetails?.orderDetails.ord_completion_request_date
        ? new Date(orderDetails.orderDetails.ord_completion_request_date)
        : null
    );
    this.form.controls.ord_additional_description.setValue(
      orderDetails?.orderDetails.ord_additional_description || null
    );
    this.dateStr = this.datePipe.transform(this.form.controls.ord_completion_request_date.value);
  }

  submitChanges(): void {
    this.form.markAllAsTouched();
    if (
      this.orderDetails.serviceDescription.srv_type === this.TYRIMAI &&
      (this.selectedServices?.length === 0 || this.selectedServices === null)
    ) {
      this.commonFormServices.translate
        .get(this.researchOutsideNtisReference + '.selectCriteria')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
          this.isValid = false;
        });
    } else {
      this.isValid = true;
    }
    if (this.form.valid && this.isValid) {
      this.orderDetails.orderDetails = {
        ...this.orderDetails.orderDetails,
        ord_name: this.form.controls.ord_name.value,
        ord_email: this.form.controls.ord_email.value,
        ord_phone_number: this.form.controls.ord_phone_number.value,
        ord_completion_request_date: this.form.controls.ord_completion_request_date.value,
        ord_additional_description: this.form.controls.ord_additional_description.value,
        ord_prefered_date_from: this.form.controls.ord_prefered_date_from.value,
        ord_prefered_date_to: this.form.controls.ord_prefered_date_to.value,
        ord_cs_id: this.csId,
      };

      this.techSuppService.saveServiceOrdererDetails(this.orderDetails).subscribe((result) => {
        this.orderDetails = result;
        this.orderDetailsChange.emit(result);
        this.handleNavigation();
        this.techSuppService.sendOrderNotifications(result.orderDetails.ord_id).subscribe();
      });
    }
  }

  handleNavigation(): void {
    if (this.isClient) {
      if (this.filterValue && this.returnUrl) {
        void this.router.navigate([this.returnUrl], {
          queryParams: {
            filter: this.filterValue,
          },
        });
      } else {
        if (this.srvType === TYRIMAI) {
          void this.router.navigate([
            RoutingConst.INTERNAL,
            NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
            NtisRoutingConst.RESEARCH,
            NtisRoutingConst.OWNER_RESEARCH_LIST,
          ]);
        } else {
          void this.router.navigate([
            RoutingConst.INTERNAL,
            NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
            NtisRoutingConst.TECH_SUPPORT,
            this.srvType === this.VEZIMAS
              ? NtisRoutingConst.OWNER_DISPOSAL_ORDERS_LIST
              : NtisRoutingConst.OWNER_TECH_ORDERS_LIST,
          ]);
        }
      }
    } else {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.SERVICE_PROVIDER,
        this.srvType === this.VEZIMAS ? NtisRoutingConst.DISPOSAL_ORDERS_LIST : NtisRoutingConst.TECH_ORDERS_LIST,
      ]);
    }
  }

  download(): void {
    let file: SprFile;
    if (this.orderDetails.researchFile !== null) {
      file = {
        fil_content_type: this.orderDetails.researchFile.fil_content_type,
        fil_key: this.orderDetails.researchFile.fil_key,
        fil_name: this.orderDetails.researchFile.fil_name,
        fil_size: this.orderDetails.researchFile.fil_size,
        fil_status: this.orderDetails.researchFile.fil_status,
        fil_status_date: this.orderDetails.researchFile.fil_status_date,
      };
    }
    this.fileUploadService.downloadFile(file);
  }
}
