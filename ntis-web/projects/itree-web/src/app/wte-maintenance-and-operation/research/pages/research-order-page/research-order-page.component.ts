import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisResearchOrderModel, SprFile } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_RESEARCH_ORDER_PAGE } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { OrderStatus } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { Subject, takeUntil } from 'rxjs';
import { SharedModule } from '../../../tech-support/shared/shared.module';
import { ResearchCriteriaResultInfoComponent } from '../../components/research-criteria-result-info/research-criteria-result-info.component';
import { ResearchOptionsCheckboxListComponent } from '../../components/research-options-checkbox-list/research-options-checkbox-list.component';
import { ResearchService } from '../../services/research.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { DB_BOOLEAN_FALSE } from '@itree-commons/src/constants/db.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-research-order-page',
  templateUrl: './research-order-page.component.html',
  styleUrls: ['./research-order-page.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    DialogModule,
    FontAwesomeModule,
    ItreeCommonsModule,
    ReactiveFormsModule,
    ResearchCriteriaResultInfoComponent,
    ResearchOptionsCheckboxListComponent,
    SharedModule,
    NtisSharedModule,
  ],
})
export class ResearchOrderPageComponent implements OnInit {
  readonly reviewsTranslationsReference = 'ntisShared.pages.reviewCreate';
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.researchOrderPage';
  readonly formCode = NTIS_RESEARCH_ORDER_PAGE;
  readonly orderStatus = OrderStatus;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  data: NtisResearchOrderModel;
  ordId: string;
  headerString: string;
  ordCreatedStr: string;
  requestedDateFromStr: string;
  requestedDateToStr: string;
  completionEstimateStr: string;
  sampleDateStr: string;
  researchDateStr: string;
  resultsDateStr: string;
  dialogText: string;
  reasonLabel: string;
  showDialog: boolean = false;
  enterResults: boolean = false;
  userIsOwner: boolean;
  userIsLab: boolean;
  dialogButtonString: string;
  changedStatus: string;
  minDate: Date = new Date();
  uploadedFiles: SprFile[] = [];
  disabledUpload: boolean = false;
  destroy$: Subject<boolean> = new Subject();
  returnUrl: string;
  filterValue: string;
  actionType: string;
  submit: boolean = false;
  resultsValid: boolean = true;
  defaultResearchDate: Date = new Date();
  disableSubmit: boolean = false;
  showReviewDialog = false;

  form = new FormGroup({
    rejectionReason: new FormControl<string>(null, Validators.compose([Validators.maxLength(500)])),
    completionEstimate: new FormControl<Date>(null),
    responsiblePerson: new FormControl<string>(null, Validators.compose([Validators.maxLength(100)])),
  });

  resultsForm = new FormGroup({
    sampleDate: new FormControl<Date>(null),
    researchDate: new FormControl<Date>(null),
    samplePerson: new FormControl<string>(null, Validators.compose([Validators.maxLength(100)])),
    researchPerson: new FormControl<string>(null, Validators.compose([Validators.maxLength(100)])),
    researchComments: new FormControl<string>(null, Validators.compose([Validators.maxLength(1000)])),
  });

  constructor(
    private researchService: ResearchService,
    private commonFormServices: CommonFormServices,
    public faIconsService: FaIconsService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private datePipe: S2DatePipe,
    private location: Location,
    public fileUploadService: FileUploadService
  ) {
    this.userIsOwner = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.INTS_OWNER_ACTIONS);
    this.userIsLab = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.SERVICE_PROVIDER_ACTIONS);
    activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((param) => {
      this.ordId = param.id as string;
    });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      this.returnUrl = param?.['returnUrl'] ?? this.returnUrl;
      this.filterValue = param?.['filter'] ?? this.filterValue;
      this.actionType = param?.['actionType'] ?? this.actionType;
    });
  }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.queryParams.token) {
      this.researchService
        .getResearchOrderDetailsByToken(this.activatedRoute.snapshot.queryParams.token as string)
        .subscribe((result) => {
          this.ordId = result?.toString();
          this.getOrderDetails();
        });
    } else {
      this.getOrderDetails();
    }
  }

  getOrderDetails(): void {
    this.researchService.getResearchOrderDetails(this.ordId).subscribe((result) => {
      if (this.actionType === RoutingConst.EDIT && this.userIsLab && result.statusClsf === OrderStatus.finished) {
        this.showEnterResults(result);
      }
      if (this.userIsLab && result.statusClsf === this.orderStatus.confirmed && this.enterResults) {
        this.headerString = '.headerTextEnterResults';
      } else {
        this.headerString = result.statusClsf === this.orderStatus.finished ? '.headerTextViewResults' : '.headerText';
      }
      this.ordCreatedStr = this.datePipe.transform(result.ordCreated);
      this.requestedDateFromStr = this.datePipe.transform(result.requestedDateFrom);
      this.requestedDateToStr = this.datePipe.transform(result.requestedDateTo);
      this.completionEstimateStr = this.datePipe.transformDateHoursMinutes(result.completionEstimate);
      this.sampleDateStr = this.datePipe.transform(result.sampleDate);
      this.researchDateStr = this.datePipe.transform(result.researchDate);
      this.resultsDateStr = this.datePipe.transform(result.resultsDate);
      this.data = result;
    });
    this.resultsForm.controls.sampleDate.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.resultsForm.controls.researchDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.defaultResearchDate = value;
    });
    this.resultsForm.controls.researchDate.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.resultsForm.controls.sampleDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
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
        this.commonFormServices.router.url.split(NtisRoutingConst.RESEARCH_ORDER_PAGE)[0],
        navigationExtras
      );
    }
  }

  cancelOrder(): void {
    this.form.reset();
    this.changedStatus = this.orderStatus.cancelled;
    this.commonFormServices.translate
      .get(this.translationsReference + '.cancellationText')
      .subscribe((translation: string) => {
        this.dialogText = translation;
      });
    this.commonFormServices.translate
      .get(this.translationsReference + '.cancellationReason')
      .subscribe((translation: string) => {
        this.reasonLabel = translation;
      });
    this.commonFormServices.translate
      .get(this.translationsReference + '.cancelOrder')
      .subscribe((translation: string) => {
        this.dialogButtonString = translation;
      });
    this.showDialog = true;
  }

  rejectOrder(): void {
    this.form.reset();
    this.changedStatus = this.orderStatus.rejected;
    this.commonFormServices.translate
      .get(this.translationsReference + '.rejectionText')
      .subscribe((translation: string) => {
        this.dialogText = translation;
      });
    this.commonFormServices.translate
      .get(this.translationsReference + '.rejectionReason')
      .subscribe((translation: string) => {
        this.reasonLabel = translation;
      });
    this.commonFormServices.translate
      .get(this.translationsReference + '.rejectOrder')
      .subscribe((translation: string) => {
        this.dialogButtonString = translation;
      });
    this.showDialog = true;
  }

  confirmOrder(): void {
    this.form.reset();
    this.changedStatus = this.orderStatus.confirmed;
    this.commonFormServices.translate
      .get(this.translationsReference + '.confirmationText')
      .subscribe((translation: string) => {
        this.dialogText = translation;
      });
    this.commonFormServices.translate.get('common.action.confirm').subscribe((translation: string) => {
      this.dialogButtonString = translation;
    });
    this.showDialog = true;
  }

  updateOrder(): void {
    this.submit = true;
    if (!this.disableSubmit) {
      if (this.data.results != null) {
        if (this.data.results.find((result) => (result.resId && !result.result) || result.result > 1000)) {
          this.resultsValid = false;
        } else {
          this.resultsValid = true;
        }
      }
      this.form.markAllAsTouched();
      this.resultsForm.markAllAsTouched();
      if (
        (this.changedStatus === this.orderStatus.finished && this.resultsForm.valid && this.resultsValid) ||
        (this.data.statusClsf === this.orderStatus.finished && this.resultsForm.valid && this.resultsValid) ||
        (this.changedStatus !== this.orderStatus.finished && this.form.valid)
      ) {
        if (this.changedStatus === this.orderStatus.finished || this.data.statusClsf === this.orderStatus.finished) {
          this.changedStatus = this.orderStatus.finished;
          this.data = {
            ...this.data,
            statusClsf: this.changedStatus,
            sampleDate: this.resultsForm.controls.sampleDate.value,
            researchDate: this.resultsForm.controls.researchDate.value,
            samplePerson: this.resultsForm.controls.samplePerson.value,
            researchPerson: this.resultsForm.controls.researchPerson.value,
            researchComments: this.resultsForm.controls.researchComments.value,
          };
        } else {
          this.data = {
            ...this.data,
            statusClsf: this.changedStatus,
            rejectionReason: this.form.controls.rejectionReason.value,
            rejectionDate: this.changedStatus !== this.orderStatus.confirmed ? new Date() : undefined,
            completionEstimate: this.form.controls.completionEstimate.value,
            responsiblePerson: this.form.controls.responsiblePerson.value,
          };
        }
        this.researchService.updateOrder(this.data).subscribe(() => {
          this.commonFormServices.translate
            .get(this.translationsReference + '.' + this.changedStatus)
            .subscribe((translation: string) => {
              this.commonFormServices.appMessages.showSuccess('', translation);
              this.researchService.getResearchOrderDetails(this.ordId).subscribe((result) => {
                this.data = result;
                this.showDialog = false;
                this.completionEstimateStr = this.datePipe.transformDateHoursMinutes(result.completionEstimate);
                void this.commonFormServices.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
              });
            });

          this.researchService.sendOrderNotifications(this.data.ordId).subscribe();
        });
      }
      this.disableSubmit = true;
    }
  }

  showEnterResults(result?: NtisResearchOrderModel): void {
    this.resultsForm.controls.researchDate.addValidators(Validators.compose([Validators.required]));
    this.resultsForm.controls.sampleDate.addValidators(Validators.compose([Validators.required]));
    if (result) {
      this.resultsForm.controls.researchDate.setValue(new Date(result.researchDate) ?? null);
      this.resultsForm.controls.sampleDate.setValue(new Date(result.sampleDate) ?? null);
      this.resultsForm.controls.samplePerson.setValue(result.samplePerson);
      this.resultsForm.controls.researchPerson.setValue(result.researchPerson);
      this.resultsForm.controls.researchComments.setValue(result.researchComments);
      this.uploadedFiles = result?.resultsFile?.fil_name ? [result.resultsFile] : [];
    }

    this.resultsForm.controls.samplePerson.addValidators(
      Validators.compose([Validators.required, Validators.maxLength(100)])
    );
    this.resultsForm.controls.researchPerson.addValidators(
      Validators.compose([Validators.required, Validators.maxLength(100)])
    );
    this.resultsForm.controls.sampleDate.addValidators([
      SprDateValidators.validateDateFromEqual(this.resultsForm.controls.researchDate),
    ]);
    this.resultsForm.controls.researchDate.addValidators([
      SprDateValidators.validateDateToEqual(this.resultsForm.controls.sampleDate),
    ]);

    this.headerString = '.headerTextEnterResults';
    this.changedStatus = this.orderStatus.finished;
    this.enterResults = true;
  }

  onFileUpload(): void {
    this.data.resultsFile = this.uploadedFiles[0];
    this.disabledUpload = true;
  }

  onDeleteFile(file: SprFile): void {
    if (file.fil_key) {
      this.fileUploadService.deleteFile(file).subscribe();
    }
    this.data.resultsFile = undefined;
    this.disabledUpload = false;
  }

  navigateToReviewForm(): void {
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, NtisRoutingConst.REVIEW_CREATE, this.data?.revId],
      { queryParams: { returnUrl: this.commonFormServices.router.url } }
    );
  }
}
