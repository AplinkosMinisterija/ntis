import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_CONTRACT_EDIT } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { NtisContractsService } from '../../services/ntis-contracts.service';
import { NtisContractEditModel, NtisContractRequestService, SprFile } from '@itree-commons/src/lib/model/api/api';
import { NTIS_COT_STATE, NTIS_SRV_ITEM_TYPE } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { CalendarModule } from 'primeng/calendar';
import { SprFormArrayValidators } from '@itree-commons/src/lib/validators/form-array-validators';
import { ServiceItemType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { Subject, takeUntil } from 'rxjs';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { ContractState } from '../../enums/contract-state.enums';

@Component({
  selector: 'app-contract-upload-page',
  templateUrl: './contract-upload-page.component.html',
  styleUrls: ['./contract-upload-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    CalendarModule,
  ],
})
export class ContractUploadPageComponent {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.pages.contractUpload';
  readonly formCode = NTIS_CONTRACT_EDIT;
  readonly ContractState = ContractState;
  readonly destroy$ = new Subject<void>();
  data: NtisContractEditModel;
  isClient: boolean;
  isServiceProvider: boolean;
  editMode: boolean = true;
  splitStateString: string[] = [];
  dateFrom: string;
  dateTo: string;
  contractState: string;
  today: Date = new Date(new Date().setHours(0, 0, 0, 0));
  defaultDateTo: Date = new Date();
  userCanDelete: boolean;

  form = new FormGroup({
    cot_code: new FormControl<string>('', Validators.required),
    cot_from_date: new FormControl<Date>(null, Validators.required),
    cot_to_date: new FormControl<Date>(null, Validators.required),
    cot_wtf_id: new FormControl<number>(null, Validators.required),
    prices: new FormArray<FormControl<number>>([]),
    services: new FormArray<FormControl<boolean>>([], SprFormArrayValidators.atLeastOneTruthy),
    srvId: new FormArray<FormControl<number>>([]),
  });
  availableServices: NtisContractRequestService[] = [];

  constructor(
    public faIconsService: FaIconsService,
    private activatedRoute: ActivatedRoute,
    private s2DatePipe: S2DatePipe,
    private commonFormServices: CommonFormServices,
    private ntisContractsService: NtisContractsService,
    private fileUploadService: FileUploadService,
    private authService: AuthService,
    private commonService: CommonService
  ) {
    this.userCanDelete = authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_DELETE);
    activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.isServiceProvider = params.type === NtisRoutingConst.PASLAUG;
      this.isClient = params.type === NtisRoutingConst.CLIENT;
      this.editMode = params.mode === RoutingConst.EDIT;
      if (
        (this.isServiceProvider &&
          this.authService.isFormActionEnabled(this.formCode, ActionsEnum.SERVICE_PROVIDER_ACTIONS)) ||
        (this.isClient && this.authService.isFormActionEnabled(this.formCode, ActionsEnum.INTS_OWNER_ACTIONS))
      ) {
        this.setData();
      } else {
        void this.commonFormServices.router.navigate([RoutingConst.DASHBOARD]);
      }
    });
  }

  setData(): void {
    this.data = this.activatedRoute.snapshot.data['contractData'] as NtisContractEditModel;
    this.contractState = this.data.cot_state;
    if (this.editMode) {
      this.handleStateDisplay();

      this.commonService.getClsf(NTIS_SRV_ITEM_TYPE).subscribe(() => {
        this.availableServices = [];
        this.data.cot_services
          .filter((srv) => srv.type !== ServiceItemType.treatment && srv.type !== ServiceItemType.installation)
          .map((srv) => {
            this.availableServices.push({
              type: srv.type,
              name: srv.name,
              description: srv.description ? srv.description : '-',
            });
          });
        this.availableServices.forEach((service) => {
          this.form.controls.services.push(new FormControl(false));
          this.form.controls.prices.push(new FormControl<number>(null));
          this.form.controls.srvId.push(
            new FormControl(this.data.cot_services.find((srv) => srv.type == service.type).srv_id)
          );
        });
      });
      this.validateDates();
    } else {
      this.transformDates();
      this.handleStateDisplay();
      this.availableServices = this.data.cot_services;
    }
    this.data.cot_wtf_id ? this.form.controls.cot_wtf_id.setValue(this.data.cot_wtf_id) : null;
  }

  handleStateDisplay(): void {
    this.commonService.getClsf(NTIS_COT_STATE).subscribe((result) => {
      this.data.cot_state_meaning = result.filter((value) => value.key === this.data.cot_state)[0].display;
      this.splitStateString = this.data.cot_state_meaning.split(' ');
      this.data.cot_state_meaning = this.splitStateString.shift();
    });
  }

  handleServicesAndPricesChanges(): void {
    this.form.controls.services.controls.map((control, index) => {
      control.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: boolean) => {
        if (value) {
          this.form.controls.prices.controls[index].setValue(null);
          this.form.controls.prices.controls[index].setValidators(Validators.compose([Validators.min(0.01)]));
          this.form.controls.prices.controls[index].updateValueAndValidity();
        } else {
          this.form.controls.prices.controls[index].clearValidators();
          this.form.controls.prices.controls[index].updateValueAndValidity();
        }
      });
    });
  }

  validateDates(): void {
    this.form.controls.cot_from_date.addValidators([
      SprDateValidators.validateDateFrom(this.form.controls.cot_to_date),
    ]);
    this.form.controls.cot_to_date.addValidators([SprDateValidators.validateDateTo(this.form.controls.cot_from_date)]);
    this.form.controls.cot_from_date.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.form.controls.cot_to_date.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.defaultDateTo = value;
    });
    this.form.controls.cot_to_date.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.cot_from_date.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  onSave(): void {
    this.form.markAllAsTouched();
    if (!this.form.controls.cot_wtf_id.value || (this.form.controls.cot_wtf_id.value as number) === 0) {
      this.form.controls.cot_wtf_id.setValue(null);
      this.form.controls.cot_wtf_id.updateValueAndValidity();
      this.form.controls.cot_wtf_id.markAsTouched();
    }
    if (this.form.valid) {
      // contract
      const contract = {} as NtisContractEditModel;
      contract.cot_state = this.data.cot_state;
      contract.cot_code = this.form.controls.cot_code.value;
      contract.cot_from_date = this.form.controls.cot_from_date.value;
      contract.cot_to_date = this.form.controls.cot_to_date.value;
      contract.cot_wtf_id = this.form.controls.cot_wtf_id.value;
      contract.attachment = this.data.attachment;
      contract.sp_info = this.data.sp_info;

      // services
      contract.cot_services = [];
      this.form.controls.services.controls.forEach((control, index) => {
        if (control.value === true) {
          const service = {} as NtisContractRequestService;
          service.type = this.availableServices[index].type;
          service.name = this.availableServices[index].name;
          service.description = this.availableServices[index].description;
          service.srv_id = this.form.controls.srvId.controls[index].value;
          service.price = this.form.controls.prices.controls[index].value;
          contract.cot_services.push(service);
        }
      });

      // save contract
      this.ntisContractsService.saveContract(contract).subscribe((res) => {
        void this.commonFormServices.router.navigate(
          [
            RoutingConst.INTERNAL,
            NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
            NtisRoutingConst.CONTRACTS,
            NtisRoutingConst.CONTRACTS_LIST,
            NtisRoutingConst.UPLOAD,
            this.isServiceProvider ? NtisRoutingConst.PASLAUG : NtisRoutingConst.CLIENT,
            RoutingConst.VIEW,
          ],
          {
            queryParams: {
              cot: res.cot_id,
            },
          }
        );
        if (this.isClient) {
          this.ntisContractsService.sendNotifications(res.cot_id).subscribe();
        }
      });
    } else if (this.form.controls.cot_wtf_id.touched && this.form.controls.cot_wtf_id.errors) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.noSelectedFacilityError')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else if (this.form.controls.services.touched && this.form.controls.services.errors) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.noSelectedServicesError')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  onCancel(): void {
    void this.commonFormServices.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
      NtisRoutingConst.CONTRACTS,
      NtisRoutingConst.CONTRACTS_LIST,
    ]);
  }

  transformDates(): void {
    this.dateFrom = this.s2DatePipe.transform(this.data.cot_from_date);
    this.dateTo = this.s2DatePipe.transform(this.data.cot_to_date);
  }

  download(file: SprFile): void {
    this.fileUploadService.downloadFile(file);
  }

  onDeleteContract(): void {
    this.commonFormServices.translate.get('common.message.deleteConfirmationMsg').subscribe((translation: string) => {
      this.commonFormServices.confirmationService.confirm({
        message: translation,
        accept: () => {
          this.ntisContractsService.deleteContract(this.data.cot_id).subscribe(() => {
            this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translate: string) => {
              this.commonFormServices.appMessages.showSuccess('', translate);
            });
            this.onCancel();
          });
        },
      });
    });
  }

  handleWtfSelection(wtfId: number): void {
    this.form.controls.cot_wtf_id.setValue(wtfId);
  }

  toOrderNewService(service: NtisContractRequestService): void {
    void this.commonFormServices.router.navigate(
      [this.commonFormServices.router.url, NtisRoutingConst.SERVICE_ORDER_EDIT, service.srv_id],
      { queryParams: { csId: service.cs_id } }
    );
  }
}
