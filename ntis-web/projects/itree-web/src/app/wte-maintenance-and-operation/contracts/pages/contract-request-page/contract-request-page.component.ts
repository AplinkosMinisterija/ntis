import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import {
  NtisCheckWtfSelectionRequest,
  NtisCheckWtfSelectionResponse,
  NtisContractRequestComment,
  NtisNewContractRequest,
  NtisNewContractRequestInfo,
  NtisSubmitContractRequestInfo,
} from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprFormArrayValidators } from '@itree-commons/src/lib/validators/form-array-validators';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { AuthUtil, CommonFormServices } from '@itree/ngx-s2-commons';
import { TranslateService } from '@ngx-translate/core';
import { NtisSharedModule } from 'projects/itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommentsComponent } from '../../components/comments/comments.component';
import { NtisContractsService } from '../../services/ntis-contracts.service';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { Subject, takeUntil } from 'rxjs';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { SelectOrAddWtfDialogComponent } from '@itree-web/src/app/ntis-shared/components/select-or-add-wtf-dialog/select-or-add-wtf-dialog.component';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';

@Component({
  selector: 'app-contract-request-page',
  standalone: true,
  templateUrl: './contract-request-page.component.html',
  styleUrls: ['./contract-request-page.component.scss'],
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    CommentsComponent,
    SelectOrAddWtfDialogComponent,
  ],
})
export class ContractRequestPageComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.pages.contractRequest';
  destroy$: Subject<boolean> = new Subject<boolean>();
  form = new FormGroup({
    services: new FormArray<FormControl<boolean>>([], SprFormArrayValidators.atLeastOneTruthy),
    startDate: new FormControl<Date>(null, Validators.required),
    endDate: new FormControl<Date>(null, Validators.required),
    applicantPhone: new FormControl<string>(null, [Validators.required, Validators.pattern(new RegExp(PHONE_PATTERN))]),
    applicantEmail: new FormControl<string>(null, [
      Validators.required,
      Validators.pattern(new RegExp(EMAIL_PATTERN)),
      Validators.maxLength(100),
    ]),
    comment: new FormControl<string>(null),
  });
  data: NtisNewContractRequestInfo;
  wtfSelectionData: NtisCheckWtfSelectionResponse;
  comments: NtisContractRequestComment[] = [];
  addCommentMode = false;
  isWtfSelected: boolean = undefined;
  params = {} as NtisCheckWtfSelectionRequest;
  contractRequest = {} as NtisNewContractRequest;
  wtfSearchAddress: string;
  addrDialog: boolean = false;

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.informationAboutServices',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'services',
        },
      ],
    },
    {
      legend: this.translationsReference + '.contractValidityInformation',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.startDate',
          translateLabel: true,
          formControlName: 'startDate',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.endDate',
          translateLabel: true,
          formControlName: 'endDate',
          isRequired: true,
          defaultDateTo: undefined,
        },
      ],
    },
    {
      legend: this.translationsReference + '.clientContactInformation',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.phoneNo',
          translateLabel: true,
          formControlName: 'applicantPhone',
          isRequired: true,
          errorDefs: { pattern: 'common.error.phone' },
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.emailAddress',
          translateLabel: true,
          formControlName: 'applicantEmail',
          isRequired: true,
          maxLength: 100,
        },
      ],
    },
    {
      legend: this.translationsReference + '.comments',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'comments',
        },
      ],
    },
  ];

  constructor(
    private activatedRoute: ActivatedRoute,
    private commonFormServices: CommonFormServices,
    private router: Router,
    private s2DatePipe: S2DatePipe,
    public faIconsService: FaIconsService,
    private ntisContractsService: NtisContractsService,
    private translateService: TranslateService,
    private ntisCommonService: NtisCommonService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.contractRequest.srvProvId = Number(params.serviceProviderId);
    });

    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.contractRequest.wtfId = params.id ? Number(params.id) : null;
      this.wtfSearchAddress = params.address as string;
      this.isWtfSelected = true;

      if (params.adId || (params.x && params.y)) {
        this.params.adId = params.adId ? Number(params.adId) : null;
        this.params.lksX = params.x ? Number(params.x) : null;
        this.params.lksY = params.y ? Number(params.y) : null;
        this.params.wtfType = params.type as string;
        this.params.wtfDistance = Number(params.distance);

        this.ntisCommonService.checkWtfSelection(this.params).subscribe((res) => {
          this.wtfSelectionData = res;
          this.isWtfSelected = this.wtfSelectionData.selected;
          if (this.wtfSelectionData.wtfs?.length > 1) {
            this.addrDialog = true;
          } else {
            this.contractRequest.wtfId =
              this.wtfSelectionData.wtfs?.length > 0 ? this.wtfSelectionData.wtfs[0].wtfId : null;
            this.loadContract();
          }
        });
      } else {
        this.loadContract();
      }
    });
  }

  loadContract(): void {
    this.form.controls['startDate'].addValidators([SprDateValidators.validateDateFrom(this.form.controls['endDate'])]);
    this.form.controls['endDate'].addValidators([SprDateValidators.validateDateTo(this.form.controls['startDate'])]);
    this.form.controls.startDate.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.form.controls.endDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'endDate', 'defaultDateTo', value);
    });
    this.form.controls.endDate.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.startDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    if (this.isWtfSelected || this.contractRequest.wtfId) {
      this.ntisContractsService.loadNewContractRequest(this.contractRequest).subscribe((result) => {
        this.data = result;
        this.form.controls.services.clear();
        if (this.data.availableServices) {
          this.data.availableServices.forEach(() => {
            this.form.controls.services.push(new FormControl(false));
          });
        }
        this.form.controls.applicantPhone.setValue(this.data.applicantPhone);
        this.form.controls.applicantEmail.setValue(this.data.applicantEmail);
      });
    } else {
      this.toAddNewFacility();
    }
  }

  getSelectedServices(): string[] {
    return this.form.controls.services.controls
      .map((control, index) => (control.value === true ? this.data.availableServices[index].type : null))
      .filter((value) => value);
  }

  handleAddComment(commentText: string): void {
    const sessionInfo = AuthUtil.getSessionInfo();
    this.comments.push({
      author: `${sessionInfo.personName} ${sessionInfo.personLastName}`,
      text: commentText,
      time: this.s2DatePipe.transform(new Date(), true),
    });
    this.addCommentMode = false;
  }

  handleSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const contractRequest: NtisSubmitContractRequestInfo = {
        applicantEmail: this.form.controls.applicantEmail.value,
        applicantPhone: this.form.controls.applicantPhone.value,
        comments: this.comments.map((comment) => comment.text),
        startDate: this.s2DatePipe.transform(this.form.controls.startDate.value),
        endDate: this.s2DatePipe.transform(this.form.controls.endDate.value),
        orgId: this.data.orgId,
        services: this.getSelectedServices(),
        wtfId: this.data.wtfInfo.id,
      };
      this.ntisContractsService.submitContractRequest(contractRequest).subscribe(() => {
        void this.router.navigate(['/', RoutingConst.INTERNAL, NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD]);
      });
    } else {
      this.translateService.get('common.message.correctValidationErrors').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation, 'VALIDATION');
      });
    }
  }

  setFacility(wtfId: number): void {
    this.contractRequest.wtfId = wtfId;
    this.addrDialog = false;
    this.loadContract();
  }

  onCancel(): void {
    void this.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
      NtisRoutingConst.CONTRACTS,
      NtisRoutingConst.SERVICE_SEARCH,
    ]);
  }

  toAddNewFacility(): void {
    void this.commonFormServices.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD,
        NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        RoutingConst.EDIT,
        RoutingConst.NEW,
      ],
      {
        queryParams: {
          returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.CONTRACT_REQUEST}/${RoutingConst.NEW}/${this.contractRequest.srvProvId}`,
          adId: this.params.adId,
          x: !this.params.adId ? this.params.lksX : undefined,
          y: !this.params.adId ? this.params.lksY : undefined,
          type: this.params.wtfType,
          address: this.wtfSearchAddress,
          distance: this.params.wtfDistance,
        },
      }
    );
  }
}
