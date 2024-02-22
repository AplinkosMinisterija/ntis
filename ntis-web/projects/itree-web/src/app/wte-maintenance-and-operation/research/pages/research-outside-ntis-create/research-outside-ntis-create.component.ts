import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import {
  NtisResearchOrderModel,
  ResearchCriteriaResultsModel,
  ResearchRequestedCriteriaModel,
  SprFile,
} from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { OrderStatus } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { CalendarModule } from 'primeng/calendar';
import { Subject, takeUntil, tap } from 'rxjs';
import { ResearchCriteriaResultInfoComponent } from '../../components/research-criteria-result-info/research-criteria-result-info.component';
import { ResearchOptionsCheckboxListComponent } from '../../components/research-options-checkbox-list/research-options-checkbox-list.component';
import { ResearchService } from '../../services/research.service';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-research-outside-ntis-create',
  templateUrl: './research-outside-ntis-create.component.html',
  styleUrls: ['./research-outside-ntis-create.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    ReactiveFormsModule,
    NtisSharedModule,
    ResearchOptionsCheckboxListComponent,
    ResearchCriteriaResultInfoComponent,
    CalendarModule,
  ],
})
export class ResearchOutsideNtisCreateComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.researchOutsideNtis';
  readonly orderStatus = OrderStatus;
  readonly linkForDescription = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.INST_SERVICE_MANAGEMENT}`;
  readonly linkForRequest = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SP_WM_SERVICE_REQUESTS_LIST}`;
  isAdmin: boolean;
  srvId: number = -1;
  destroy$: Subject<boolean> = new Subject();
  selectedResearchTypes: string[] = [];
  newResearchOrder: NtisResearchOrderModel = {} as NtisResearchOrderModel;
  showEnterResults = false;
  completionEstimateStr: string;
  minDate: Date = new Date();
  uploadedFiles: SprFile[] = [];
  disabledUpload: boolean = false;
  hasResearchService: boolean = true;
  requestedCriteria: ResearchRequestedCriteriaModel[] = [];
  researchResults: ResearchCriteriaResultsModel[] = [];
  orderFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.contactDetails',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.wtfId',
          translateLabel: true,
          formControlName: 'wtfId',
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.name',
          translateLabel: true,
          formControlName: 'name',
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.phone',
          translateLabel: true,
          formControlName: 'phone',
          isRequired: true,
          errorDefs: { pattern: 'common.error.phone' },
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.email',
          translateLabel: true,
          formControlName: 'email',
          maxLength: 100,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.completionEstimate',
          translateLabel: true,
          formControlName: 'completionEstimate',
          isRequired: true,
          minDate: new Date(),
          showTime: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.responsiblePerson',
          translateLabel: true,
          formControlName: 'responsiblePerson',
          isRequired: true,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.translationsReference + '.comment',
          translateLabel: true,
          formControlName: 'comment',
        },
      ],
    },
  ];

  form = new FormGroup({
    wtfId: new FormControl<number>(null, Validators.compose([Validators.required])),
    name: new FormControl<string>('', Validators.compose([Validators.maxLength(100)])),
    phone: new FormControl<string>(
      null,
      Validators.compose([Validators.required, Validators.pattern(new RegExp(PHONE_PATTERN))])
    ),
    email: new FormControl<string>(
      null,
      Validators.compose([Validators.maxLength(100), Validators.pattern(new RegExp(EMAIL_PATTERN))])
    ),
    completionEstimate: new FormControl<Date>(null, Validators.compose([Validators.required])),
    responsiblePerson: new FormControl<string>(
      '',
      Validators.compose([Validators.maxLength(100), Validators.required])
    ),
    comment: new FormControl<string>('', Validators.compose([Validators.maxLength(1000)])),
  });

  resultsForm = new FormGroup({
    sampleDate: new FormControl<Date>(null),
    researchDate: new FormControl<Date>(null),
    samplePerson: new FormControl<string>(null, Validators.compose([Validators.maxLength(100)])),
    researchPerson: new FormControl<string>(null, Validators.compose([Validators.maxLength(100)])),
    researchComments: new FormControl<string>(null, Validators.compose([Validators.maxLength(1000)])),
  });

  constructor(
    private commonFormServices: CommonFormServices,
    private researchService: ResearchService,
    private datePipe: S2DatePipe,
    private fileUploadService: FileUploadService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isFormActionEnabled('RESEARCH_OUTSIDE_NTIS_CREATE', ActionsEnum.ADMIN_ACTIONS);

    this.researchService.checkIfHasResearch().subscribe((result) => {
      this.hasResearchService = result;
    });

    this.researchService.checkResearchId().subscribe((result) => {
      this.srvId = result;
    });

    this.resultsForm.controls.researchPerson.addValidators(
      Validators.compose([Validators.required, Validators.maxLength(100)])
    );
    this.resultsForm.controls.sampleDate.addValidators([
      SprDateValidators.validateDateFromEqual(this.resultsForm.controls.researchDate),
    ]);
    this.resultsForm.controls.researchDate.addValidators([
      SprDateValidators.validateDateToEqual(this.resultsForm.controls.sampleDate),
    ]);
    this.resultsForm.controls.sampleDate.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.resultsForm.controls.researchDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.resultsForm.controls.researchDate.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.resultsForm.controls.sampleDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  onCancel(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.RESEARCH,
        NtisRoutingConst.SP_RESEARCH_LIST,
      ],
      navigationExtras
    );
  }

  setSelectedWtf(wtfId: number): void {
    this.form.controls.wtfId.setValue(wtfId);
  }

  saveConfirmedOrder(): void {
    this.form.markAllAsTouched();
    if (this.checkIfFormValid()) {
      this.researchService
        .getCurrentNorms(this.form.controls.wtfId.value)
        .pipe(
          tap((result) => {
            result.forEach((criteria) => {
              const selectedCriteria: ResearchRequestedCriteriaModel = {
                code: criteria.code,
                display: criteria.display,
                belongs: undefined,
                isSelected: undefined,
                rn_id: criteria.rn_id,
              };
              if (this.selectedResearchTypes.includes(criteria.code)) {
                selectedCriteria.isSelected = true;
                this.researchResults.push(criteria);
              } else {
                selectedCriteria.isSelected = false;
              }
              this.requestedCriteria.push(selectedCriteria);
            });
          })
        )
        .subscribe(() => {
          this.newResearchOrder.selectedCriteria = this.requestedCriteria;
          this.newResearchOrder.results = this.researchResults;
          this.newResearchOrder = {
            ...this.newResearchOrder,
            ordCreated: new Date(),
            ordererPhone: this.form.controls.phone.value,
            ordererEmail: this.form.controls.email.value,
            ordererName: this.form.controls.name.value,
            responsiblePerson: this.form.controls.responsiblePerson.value,
            completionEstimate: this.form.controls.completionEstimate.value,
            comment: this.form.controls.comment.value,
            wtfId: this.form.controls.wtfId.value,
            statusClsf: this.orderStatus.confirmed,
          };
          this.researchService.saveNewOrder(this.newResearchOrder).subscribe((res) => {
            this.commonFormServices.translate
              .get(this.translationsReference + '.orderSuccessfullyCreated')
              .subscribe((translation: string) => {
                this.commonFormServices.appMessages.showSuccess('', translation);
              });
            this.onCancel();
            this.researchService.sendOutsideOrderNotifications(res.ord_id).subscribe();
          });
        });
    }
  }

  saveFinishedOrder(): void {
    this.resultsForm.markAllAsTouched();
    if (this.resultsForm.valid) {
      this.newResearchOrder = {
        ...this.newResearchOrder,
        statusClsf: this.orderStatus.finished,
        sampleDate: this.resultsForm.controls.sampleDate.value,
        samplePerson: this.resultsForm.controls.samplePerson.value,
        researchDate: this.resultsForm.controls.researchDate.value,
        researchPerson: this.resultsForm.controls.researchPerson.value,
        researchComments: this.resultsForm.controls.researchComments.value,
      };
      this.researchService.saveNewOrder(this.newResearchOrder).subscribe((res) => {
        this.commonFormServices.translate
          .get(this.translationsReference + '.orderSuccessfullyCreated')
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
        this.onCancel();
        this.researchService.sendOutsideOrderNotifications(res.ord_id).subscribe();
      });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) =>
          this.commonFormServices.appMessages.showError('', translation, 'VALIDATION')
        );
    }
  }

  changeToEnterResults(): void {
    const isValid = this.checkIfFormValid();
    if (isValid) {
      this.addResultsValidators();
      this.researchService
        .getCurrentNorms(this.form.controls.wtfId.value)
        .pipe(
          tap((result) => {
            result.forEach((criteria) => {
              const selectedCriteria: ResearchRequestedCriteriaModel = {
                code: criteria.code,
                display: criteria.display,
                belongs: undefined,
                isSelected: undefined,
                rn_id: criteria.rn_id,
              };
              if (this.selectedResearchTypes.includes(criteria.code)) {
                selectedCriteria.isSelected = true;
                criteria.resId = 1;
                this.researchResults.push(criteria);
              } else {
                selectedCriteria.isSelected = false;
                this.researchResults.push(criteria);
              }
              this.researchResults.sort((a, b) => b.resId - a.resId);

              this.requestedCriteria.push(selectedCriteria);
              this.requestedCriteria.sort((a, b) => (b.isSelected && !a.isSelected ? 1 : -1));
            });
          })
        )
        .subscribe(() => {
          this.newResearchOrder.selectedCriteria = this.requestedCriteria;
          this.newResearchOrder.results = this.researchResults;
          this.newResearchOrder = {
            ...this.newResearchOrder,
            ordCreated: new Date(),
            ordererPhone: this.form.controls.phone.value,
            ordererEmail: this.form.controls.email.value,
            ordererName: this.form.controls.name.value,
            responsiblePerson: this.form.controls.responsiblePerson.value,
            completionEstimate: this.form.controls.completionEstimate.value,
            comment: this.form.controls.comment.value,
            wtfId: this.form.controls.wtfId.value,
            statusClsf: this.orderStatus.confirmed,
          };
          this.showEnterResults = isValid;
        });
    }
  }

  checkIfFormValid(): boolean {
    let isValid: boolean = false;
    this.form.markAllAsTouched();
    if (!this.form.controls.wtfId.value) {
      this.commonFormServices.translate.get(this.translationsReference + '.addWtf').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
        isValid = false;
      });
    } else if (this.selectedResearchTypes.length === 0) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.selectCriteria')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
          isValid = false;
        });
    } else if (!this.form.valid) {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
          isValid = false;
        });
    } else {
      isValid = true;
    }
    this.completionEstimateStr = this.datePipe.transformDateHoursMinutes(this.form.controls.completionEstimate.value);
    return isValid;
  }

  addResultsValidators(): void {
    this.resultsForm.controls.sampleDate.addValidators(Validators.required);
    this.resultsForm.controls.sampleDate.updateValueAndValidity();
    this.resultsForm.controls.samplePerson.addValidators(Validators.required);
    this.resultsForm.controls.samplePerson.updateValueAndValidity();
    this.resultsForm.controls.researchPerson.addValidators(Validators.required);
    this.resultsForm.controls.researchPerson.updateValueAndValidity();
    this.resultsForm.controls.researchDate.addValidators(Validators.required);
    this.resultsForm.controls.researchDate.updateValueAndValidity();
  }

  onFileUpload(): void {
    this.newResearchOrder.resultsFile = this.uploadedFiles[0];
    this.disabledUpload = true;
  }

  onDeleteFile(file: SprFile): void {
    if (file.fil_key) {
      this.fileUploadService.deleteFile(file).subscribe();
    }
    this.newResearchOrder.resultsFile = undefined;
    this.disabledUpload = false;
  }
}
