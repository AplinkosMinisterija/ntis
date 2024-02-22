import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { ItreeCommonsModule } from '@itree-commons/src/lib/itree-commons.module';
import {
  NtisContractEditModel,
  NtisContractRequestComment,
  NtisContractRequestService,
  SprFile,
} from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { NTIS_CONTRACT_EDIT } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { CommentsComponent } from '../../components/comments/comments.component';
import { ContractState } from '../../enums/contract-state.enums';
import { NtisContractsService } from '../../services/ntis-contracts.service';
import { Subject, takeUntil } from 'rxjs';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';

enum ContractUpdate {
  accept = 'accept',
  decline = 'decline',
}

@Component({
  selector: 'app-contract-edit-page',
  standalone: true,
  templateUrl: './contract-edit-page.component.html',
  styleUrls: ['./contract-edit-page.component.scss'],
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    CalendarModule,
    CommentsComponent,
  ],
})
export class ContractEditPageComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.pages.contractEdit';
  readonly ContractState = ContractState;
  readonly formCode = NTIS_CONTRACT_EDIT;
  destroy$: Subject<boolean> = new Subject<boolean>();
  data: NtisContractEditModel;
  contractState: string;
  isClient: boolean;
  isServiceProvider: boolean;
  dynamicTranslationRef: string;
  uploadedFiles: SprFile[] = [];
  splitStateString: string[] = [];
  dateFrom: string;
  dateTo: string;
  rejectionDate: string;
  dateProjectCreated: string;
  today: Date = new Date(new Date().setHours(0, 0, 0, 0));
  form = new FormGroup({
    prices: new FormArray<FormControl<number>>([]),
  });
  declineDialogForm = new FormGroup({
    cot_rejection_reason: new FormControl<string>('', Validators.required),
  });
  acceptDialogForm = new FormGroup({
    cot_code: new FormControl<string>('', Validators.required),
    cot_from_date: new FormControl<Date>(null, Validators.required),
    cot_to_date: new FormControl<Date>(null, Validators.required),
  });
  declineDialog: boolean = false;
  acceptDialog: boolean = false;
  defaultDateTo: Date = new Date();
  userCanUpdate: boolean;

  constructor(
    public faIconsService: FaIconsService,
    private activatedRoute: ActivatedRoute,
    private s2DatePipe: S2DatePipe,
    private commonFormServices: CommonFormServices,
    private ntisContractsService: NtisContractsService,
    private fileUploadService: FileUploadService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.isServiceProvider = params.type === NtisRoutingConst.PASLAUG;
      this.isClient = params.type === NtisRoutingConst.CLIENT;
    });
    if (
      (this.isServiceProvider &&
        this.authService.isFormActionEnabled(this.formCode, ActionsEnum.SERVICE_PROVIDER_ACTIONS)) ||
      (this.isClient && this.authService.isFormActionEnabled(this.formCode, ActionsEnum.INTS_OWNER_ACTIONS))
    ) {
      this.setVariables();
      this.transformDates();

      this.form.controls.prices.clear();
      if (this.contractState === ContractState.SUBMITTED && this.isServiceProvider && this.data.cot_services) {
        this.data.cot_services.forEach((srv) => {
          if (!srv.price) {
            this.form.controls.prices.push(new FormControl<number>(null, Validators.compose([Validators.min(0.01)])));
          }
        });
      }
    } else {
      void this.commonFormServices.router.navigate([RoutingConst.DASHBOARD]);
    }
    this.acceptDialogForm.controls.cot_from_date.addValidators([
      SprDateValidators.validateDateFrom(this.acceptDialogForm.controls.cot_to_date),
    ]);
    this.acceptDialogForm.controls.cot_to_date.addValidators([
      SprDateValidators.validateDateTo(this.acceptDialogForm.controls.cot_from_date),
    ]);
    this.acceptDialogForm.controls.cot_from_date.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.acceptDialogForm.controls.cot_to_date.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.defaultDateTo = value;
    });
    this.acceptDialogForm.controls.cot_to_date.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.acceptDialogForm.controls.cot_from_date.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  setVariables(): void {
    this.data = this.activatedRoute.snapshot.data['contractData'] as NtisContractEditModel;
    this.contractState = this.data.cot_state;
    if (this.data.attachment) {
      this.uploadedFiles[0] = this.data.attachment;
    }
    this.dynamicTranslationRef =
      (this.isServiceProvider ? '.paslaug.' : '.client.') +
      this.contractState.toLowerCase() +
      (this.data.cot_project_created && this.contractState === ContractState.REJECTED ? '.project' : '');

    if (
      this.contractState === ContractState.SIGNED_BY_SRV_PROVIDER ||
      this.contractState === ContractState.SIGNED_BY_BOTH
    ) {
      this.splitStateString = this.data.cot_state_meaning.split(' ');
      this.data.cot_state_meaning = this.splitStateString.shift();
    }
  }

  handleAddComment(commentText: string): void {
    const comment = {
      text: commentText,
      contractId: this.data.cot_id,
    } as NtisContractRequestComment;
    this.ntisContractsService.saveContractComment(comment).subscribe(() => {
      this.loadAndSetComments();
    });
  }

  handleDeleteComment(comment: NtisContractRequestComment): void {
    comment.contractId = this.data.cot_id;
    this.ntisContractsService.deleteContractComment(comment).subscribe(() => {
      this.loadAndSetComments();
    });
  }

  loadAndSetComments(): void {
    this.ntisContractsService.loadContractComments(this.data.cot_id).subscribe((res) => {
      this.data.cot_comments = res;
    });
  }

  onBackToList(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.CONTRACTS,
        NtisRoutingConst.CONTRACTS_LIST,
      ],
      navigationExtras
    );
  }

  transformDates(): void {
    this.dateFrom = this.s2DatePipe.transform(this.data.cot_from_date);
    this.dateTo = this.s2DatePipe.transform(this.data.cot_to_date);
    this.rejectionDate = this.s2DatePipe.transform(this.data.cot_rejection_date);
    this.dateProjectCreated = this.s2DatePipe.transform(this.data.cot_project_created, true);
  }

  handleAcceptAction(): void {
    if (this.contractState === ContractState.SIGNED_BY_SRV_PROVIDER) {
      this.ntisContractsService.viewAndSignContract(this.data.cot_id).subscribe((res) => {
        void this.commonFormServices.router.navigate([this.commonFormServices.router.url, 'sign'], {
          state: {
            url: res.url,
            cotId: this.data.cot_id,
            cotState: ContractState.SIGNED_BY_SRV_PROVIDER,
          },
        });
      });
    } else if (this.isServiceProvider && this.contractState === ContractState.SUBMITTED) {
      this.form.markAllAsTouched();
      if (this.form.valid) {
        this.acceptDialog = true;
      } else {
        this.showValidationErrorMessage();
      }
    }
  }

  hideDialog(): void {
    this.declineDialogForm.reset();
    this.acceptDialogForm.reset();
    this.declineDialog = false;
    this.acceptDialog = false;
  }

  onDecline(): void {
    this.declineDialogForm.markAllAsTouched();
    if (this.declineDialogForm.valid) {
      this.updateContract(ContractUpdate.decline);
    } else {
      this.showValidationErrorMessage();
    }
  }

  onAccept(): void {
    this.acceptDialogForm.markAllAsTouched();
    if (this.acceptDialogForm.valid) {
      this.updateContract(ContractUpdate.accept, ContractState.SUBMITTED);
    } else {
      this.showValidationErrorMessage();
    }
  }

  updateContract(action: ContractUpdate, state?: ContractState): void {
    const result: NtisContractEditModel = this.data;
    if (action === ContractUpdate.decline) {
      if (this.isClient && this.contractState === ContractState.SUBMITTED) {
        result.cot_state = ContractState.CANCELLED;
        result.cot_rejection_reason = this.declineDialogForm.controls.cot_rejection_reason.value;
      } else if (
        (this.isClient && this.contractState === ContractState.SIGNED_BY_SRV_PROVIDER) ||
        (this.isServiceProvider && this.contractState === ContractState.SUBMITTED)
      ) {
        result.cot_state = ContractState.REJECTED;
        result.cot_rejection_reason = this.declineDialogForm.controls.cot_rejection_reason.value;
      } else if (this.isClient && this.contractState === ContractState.SIGNED_BY_BOTH) {
        result.cot_rejection_reason = this.declineDialogForm.controls.cot_rejection_reason.value;
      } else if (this.isServiceProvider && this.contractState === ContractState.SIGNED_BY_BOTH) {
        result.cot_state = ContractState.TERMINATED;
        result.cot_rejection_reason = this.declineDialogForm.controls.cot_rejection_reason.value;
      }
    } else if (action === ContractUpdate.accept) {
      if (this.isServiceProvider && state === ContractState.SUBMITTED) {
        result.cot_code = this.acceptDialogForm.controls.cot_code.value;
        result.cot_from_date = this.acceptDialogForm.controls.cot_from_date.value || result.cot_from_date;
        result.cot_to_date = this.acceptDialogForm.controls.cot_to_date.value || result.cot_to_date;
        result.cot_services.map((service, index) => {
          service.price = this.form.controls.prices.controls[index]?.value || service.price;
        });
        if (!this.data.attachment) {
          result.attachment = this.uploadedFiles[0];
        }
      } else if (this.isServiceProvider && state === ContractState.SIGNED_BY_SRV_PROVIDER) {
        result.cot_state = ContractState.SIGNED_BY_SRV_PROVIDER;
      } else if (this.isClient && this.contractState === ContractState.SIGNED_BY_SRV_PROVIDER) {
        result.cot_state = ContractState.SIGNED_BY_BOTH;
      }
    }

    this.ntisContractsService.updateContract(result).subscribe(() => {
      if (action === ContractUpdate.accept && state === ContractState.SUBMITTED && result.cot_code) {
        this.ntisContractsService.viewAndSignContract(this.data.cot_id).subscribe((res) => {
          void this.commonFormServices.router.navigate([this.commonFormServices.router.url, 'sign'], {
            state: {
              url: res.url,
              cotId: result.cot_id,
              cotState: ContractState.SUBMITTED,
            },
          });
        });
      } else {
        this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.hideDialog();
          this.onBackToList();
        });
      }
    });
  }

  showValidationErrorMessage(): void {
    this.commonFormServices.translate.get('common.message.correctValidationErrors').subscribe((translation: string) => {
      this.commonFormServices.appMessages.showError('', translation);
    });
  }

  onUploadFile(): void {
    if (this.uploadedFiles[0].fil_size === 0) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.fileIsEmpty')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  download(file: SprFile): void {
    this.fileUploadService.downloadFile(file);
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_name) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
  }

  handlePreview(): void {
    if (this.data.cot_sign_1_fil_id || this.data.cot_sign_2_fil_id) {
      this.ntisContractsService.previewContract(this.data.cot_id).subscribe((res) => {
        void this.commonFormServices.router.navigate([this.commonFormServices.router.url, 'preview'], {
          state: {
            url: res.url,
          },
        });
      });
    } else {
      this.download(this.data.attachment);
    }
  }

  toOrderNewService(service: NtisContractRequestService): void {
    void this.commonFormServices.router.navigate(
      [this.commonFormServices.router.url, NtisRoutingConst.SERVICE_ORDER_EDIT, service.srv_id],
      { queryParams: { csId: service.cs_id } }
    );
  }

  onSave(): void {
    this.form.markAllAsTouched();
    if (this.uploadedFiles.length === 0) {
      this.commonFormServices.translate.get('common.error.addContract').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else if (this.form.valid) {
      this.updateContract(ContractUpdate.accept, ContractState.SUBMITTED);
    } else {
      this.showValidationErrorMessage();
    }
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.declineDialog = false;
    this.acceptDialog = false;
  }
}
