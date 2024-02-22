import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { InstitutionsAdminService } from '../../../institutions-admin/services/institutions-admin.service';
import {
  NtisMunicipalitiesRequest,
  NtisServiceDescription,
  NtisServiceDescriptionEditModel,
  SprFile,
} from '@itree-commons/src/lib/model/api/api';
import { takeUntil } from 'rxjs';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { EditorModule, EditorTextChangeEvent } from 'primeng/editor';
import { TYRIMAI } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { FILE_STATUS_UPLOADED } from '@itree-commons/src/constants/files.const';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { SprValueMatchValidators } from '@itree-commons/src/lib/validators/value-match-validators';

@Component({
  selector: 'app-service-description-page',
  standalone: true,
  templateUrl: './service-description-page.component.html',
  styleUrls: ['./service-description-page.component.scss'],
  imports: [CommonModule, EditorModule, ItreeCommonsModule, ReactiveFormsModule],
})
export class ServiceDescriptionPageComponent
  extends DeprecatedBaseEditForm<NtisServiceDescriptionEditModel>
  implements OnInit
{
  readonly translationsReference = 'institutionsAdmin.pages.institutionsServiceDescription';
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  readonly TYRIMAI = TYRIMAI;

  checked = false;
  disabledContractUpload = false;
  disabledLabUpload = false;
  addDocument: boolean = false;
  addMunicipalities: boolean = false;
  srv_id: string;
  confirmDescription: boolean = false;
  uploadedContractFiles: SprFile[] = [];
  uploadedLabInstructions: SprFile[] = [];

  constructor(
    private institutionsService: InstitutionsAdminService,
    private fileUploadService: FileUploadService,
    protected override activatedRoute: ActivatedRoute,
    protected override commonFormServices: CommonFormServices,
    protected override formBuilder: FormBuilder,
    public faIconsService: FaIconsService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      srv_id: new FormControl({ value: null, disabled: true }),
      srv_org_id: new FormControl({ value: null, disabled: true }),
      srv_org_name: new FormControl({ value: null, disabled: true }),
      srv_name: new FormControl({ value: null, disabled: true }),
      srv_date_from: new FormControl({ value: null, disabled: true }),
      srv_date_to: new FormControl({ value: null, disabled: true }),
      srv_type: new FormControl('', [Validators.required]),
      srv_contract_available: new FormControl('', [Validators.required]),
      srv_available_in_ntis_portal: new FormControl('', [Validators.required]),
      srv_lithuanian_level: new FormControl('', Validators.required),
      srv_email: new FormControl('', [
        Validators.required,
        Validators.maxLength(200),
        Validators.pattern(new RegExp(EMAIL_PATTERN)),
      ]),
      srv_phone_no: new FormControl('', [
        Validators.required,
        Validators.maxLength(200),
        Validators.pattern(new RegExp(PHONE_PATTERN)),
      ]),
      srv_price_from: new FormControl('', [Validators.required, Validators.max(999999999), Validators.min(1)]),
      srv_price_to: new FormControl('', [Validators.max(999999999), Validators.min(1)]),
      srv_completion_in_days_from: new FormControl('', [
        Validators.required,
        Validators.max(999999999999),
        Validators.min(1),
      ]),
      srv_completion_in_days_to: new FormControl('', [Validators.max(999999999999), Validators.min(1)]),
      srv_description: new FormControl('', [Validators.required, Validators.maxLength(1500)]),
      contractFile: new FormControl(),
      labInstructions: new FormControl(),
      municipalities: new FormControl('', Validators.required),
    });
    this.form.controls.srv_contract_available.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((changedContract: string) => {
        this.addDocument = changedContract === DB_BOOLEAN_TRUE;
        if (this.addDocument) {
          this.form.controls.contractFile.addValidators(Validators.required);
        } else {
          this.form.controls.contractFile.clearValidators();
        }
        this.form.controls.contractFile.updateValueAndValidity();
      });

    this.form.controls.srv_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((srvType: string) => {
      if (srvType === TYRIMAI) {
        this.form.controls.labInstructions.addValidators(Validators.required);
      } else {
        this.form.controls.labInstructions.clearValidators();
      }
      this.form.controls.labInstructions.updateValueAndValidity();
    });

    this.form.controls.srv_lithuanian_level.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((changedLevel: string) => (this.addMunicipalities = changedLevel === DB_BOOLEAN_FALSE));

    this.form.controls.srv_price_from.addValidators([
      SprValueMatchValidators.validateNumberLessThanEqual(this.form.controls.srv_price_to),
    ]);
    this.form.controls.srv_price_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.srv_price_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.form.controls.srv_price_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.srv_price_to.addValidators([
      SprValueMatchValidators.validateNumberMoreThanEqual(this.form.controls.srv_price_from),
    ]);
    this.form.controls.srv_price_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.srv_price_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.form.controls.srv_price_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });

    this.form.controls.srv_completion_in_days_from.addValidators([
      SprValueMatchValidators.validateNumberLessThanEqual(this.form.controls.srv_completion_in_days_to),
    ]);
    this.form.controls.srv_completion_in_days_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.srv_completion_in_days_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.form.controls.srv_completion_in_days_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.srv_completion_in_days_to.addValidators([
      SprValueMatchValidators.validateNumberMoreThanEqual(this.form.controls.srv_completion_in_days_from),
    ]);
    this.form.controls.srv_completion_in_days_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.srv_completion_in_days_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.form.controls.srv_completion_in_days_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  protected doLoad(id?: string): void {
    this.institutionsService.getServiceRecord(id).subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }

  protected setData(data: NtisServiceDescriptionEditModel): void {
    this.data = data;
    this.form.controls.srv_id.setValue(data.ntisServiceDescription.srv_id);
    this.form.controls.srv_name.setValue(data.ntisServiceDescription.srv_name);
    this.form.controls.srv_org_name.setValue(data.ntisServiceDescription.srv_org_name);
    this.form.controls.srv_date_from.setValue(data.ntisServiceDescription.srv_date_from);
    this.form.controls.srv_date_to.setValue(data.ntisServiceDescription.srv_date_to);
    this.form.controls.srv_contract_available.setValue(data.ntisServiceDescription.srv_contract_available);
    this.form.controls.srv_available_in_ntis_portal.setValue(data.ntisServiceDescription.srv_available_in_ntis_portal);
    this.form.controls.srv_type.setValue(data.ntisServiceDescription.srv_type);
    this.form.controls.srv_lithuanian_level.setValue(data.ntisServiceDescription.srv_lithuanian_level);
    this.form.controls.srv_email.setValue(data.ntisServiceDescription.srv_email);
    this.form.controls.srv_phone_no.setValue(data.ntisServiceDescription.srv_phone_no);
    this.form.controls.srv_price_from.setValue(data.ntisServiceDescription.srv_price_from);
    this.form.controls.srv_price_to.setValue(data.ntisServiceDescription.srv_price_to);
    this.form.controls.srv_completion_in_days_from.setValue(data.ntisServiceDescription.srv_completion_in_days_from);
    this.form.controls.srv_completion_in_days_to.setValue(data.ntisServiceDescription.srv_completion_in_days_to);
    this.form.controls.srv_description.setValue(data.ntisServiceDescription.srv_description);
    this.form.controls.contractFile.setValue(data.contractFile);
    if (this.data.contractFile && this.data.contractFile.fil_key) {
      this.uploadedContractFiles = [this.data.contractFile];
      this.disabledContractUpload = this.uploadedContractFiles.length >= 1;
    }
    this.form.controls.labInstructions.setValue(data.labInstructions);
    if (this.data.labInstructions && this.data.labInstructions.fil_key) {
      this.uploadedLabInstructions = [this.data.labInstructions];
      this.disabledLabUpload = this.uploadedLabInstructions.length >= 1;
    }
    this.form.controls.municipalities.setValue(data.municipalities);
  }

  protected getData(): NtisServiceDescriptionEditModel {
    const result: NtisServiceDescriptionEditModel =
      this.data != null ? this.data : ({} as NtisServiceDescriptionEditModel);
    // service settings
    result.ntisServiceDescription.srv_id = this.form.controls.srv_id.value as NtisServiceDescription['srv_id'];
    result.ntisServiceDescription.srv_name = this.form.controls.srv_name.value as NtisServiceDescription['srv_name'];
    result.ntisServiceDescription.srv_org_name = this.form.controls.srv_org_name
      .value as NtisServiceDescription['srv_org_name'];
    result.ntisServiceDescription.srv_contract_available = this.form.controls.srv_contract_available
      .value as NtisServiceDescription['srv_contract_available'];
    result.ntisServiceDescription.srv_available_in_ntis_portal = this.form.controls.srv_available_in_ntis_portal
      .value as NtisServiceDescription['srv_available_in_ntis_portal'];
    result.ntisServiceDescription.srv_type = this.form.controls.srv_type.value as NtisServiceDescription['srv_type'];
    result.ntisServiceDescription.srv_lithuanian_level = this.form.controls.srv_lithuanian_level
      .value as NtisServiceDescription['srv_lithuanian_level'];
    result.ntisServiceDescription.srv_date_from = this.form.controls.srv_date_from
      .value as NtisServiceDescription['srv_date_from'];
    result.ntisServiceDescription.srv_date_to = this.form.controls.srv_date_to
      .value as NtisServiceDescription['srv_date_to'];
    // service information
    result.ntisServiceDescription.srv_email = this.form.controls.srv_email.value as NtisServiceDescription['srv_email'];
    result.ntisServiceDescription.srv_phone_no = this.form.controls.srv_phone_no
      .value as NtisServiceDescription['srv_phone_no'];
    result.ntisServiceDescription.srv_price_from = this.form.controls.srv_price_from
      .value as NtisServiceDescription['srv_price_from'];
    result.ntisServiceDescription.srv_price_to = this.form.controls.srv_price_to
      .value as NtisServiceDescription['srv_price_to'];
    result.ntisServiceDescription.srv_completion_in_days_from = this.form.controls.srv_completion_in_days_from
      .value as NtisServiceDescription['srv_completion_in_days_from'];
    result.ntisServiceDescription.srv_completion_in_days_to = this.form.controls.srv_completion_in_days_to
      .value as NtisServiceDescription['srv_completion_in_days_to'];
    result.ntisServiceDescription.srv_description = this.form.controls.srv_description
      .value as NtisServiceDescription['srv_description'];
    //additional conditional data
    result.contractFile = this.uploadedContractFiles[0];
    result.labInstructions = this.uploadedLabInstructions[0];
    result.municipalities = this.form.controls.municipalities
      .value as NtisServiceDescriptionEditModel['municipalities'];
    this.data = result;

    return result;
  }

  protected doSave(value: NtisServiceDescriptionEditModel): void {
    this.handleDescriptionSave(value.ntisServiceDescription?.srv_description);
    if (
      this.form.controls.srv_contract_available.value === DB_BOOLEAN_TRUE &&
      this.uploadedContractFiles.length === 0
    ) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.contractNotUploaded')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else if (this.form.controls.srv_type.value === TYRIMAI && this.uploadedLabInstructions.length === 0) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.labInstructionsNotUploaded')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else if (
      !(this.form.controls.municipalities.value as NtisServiceDescriptionEditModel['municipalities']).find(
        (municipality) => municipality.selected
      ) &&
      this.form.controls.srv_lithuanian_level.value === DB_BOOLEAN_FALSE
    ) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.selectMunicipality')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else {
      if (this.confirmDescription) {
        value.ntisServiceDescription.srv_date_from = new Date();
      } else {
        value.ntisServiceDescription.srv_date_from = null;
      }
      this.institutionsService.setServiceRecord(value).subscribe((res) => {
        this.institutionsService.serviceStatusSubject.next();
        this.onSaveSuccess(res);
        this.onCancel();
      });
    }
  }

  onCancel(): void {
    void this.commonFormServices.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
  }

  onCheckboxesChange(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    this.checked = eventTarget.checked;
    for (const data of this.form.controls.municipalities.value as NtisServiceDescriptionEditModel['municipalities']) {
      data.selected = eventTarget.checked;
    }
  }

  onCheckboxChange($event: Event, data: NtisMunicipalitiesRequest): void {
    this.checked = false;
    const eventTarget = $event.target as HTMLInputElement;
    data.selected = eventTarget.checked;
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_status === FILE_STATUS_UPLOADED) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
    this.form.controls.contractFile.setValue(this.uploadedContractFiles);
    this.disabledContractUpload = false;
  }

  onFileUpload(): void {
    this.form.controls.contractFile.setValue(this.uploadedContractFiles);
    this.disabledContractUpload = true;
  }

  onLabInstructionsUpload(): void {
    this.form.controls.labInstructions.setValue(this.uploadedLabInstructions);
    this.disabledLabUpload = true;
  }

  onLabInstructionsDelete(fileToDelete: SprFile): void {
    if (fileToDelete.fil_status === FILE_STATUS_UPLOADED) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
    this.form.controls.labInstructions.setValue(this.uploadedLabInstructions);
    this.disabledLabUpload = false;
  }

  scrollToCheckboxes(checkboxes: HTMLDivElement): void {
    setTimeout(() => {
      if (checkboxes) {
        checkboxes.scrollIntoView({ behavior: 'smooth', block: 'start', inline: 'nearest' });
      }
    }, 0);
  }

  handleChange(event: EditorTextChangeEvent): void {
    if (event.htmlValue?.includes('a href')) {
      const firstPartOfString = event.htmlValue.split('a href="')[0];
      const partialString = event.htmlValue.split('a href="')[1];
      const linkEntered = partialString.split('"')[0];
      const lastPartOfString = partialString.split(linkEntered + '"')[1];
      if (!linkEntered.includes('http://') && !linkEntered.includes('https://')) {
        const updatedLink = 'http://' + linkEntered;
        event.htmlValue = firstPartOfString + 'a href="' + updatedLink + '"' + lastPartOfString;
        this.form.controls.srv_description.setValue(
          firstPartOfString + 'a href="' + updatedLink + '"' + lastPartOfString
        );
        this.data.ntisServiceDescription.srv_description =
          firstPartOfString + 'a href="' + updatedLink + '"' + lastPartOfString;
      }
    }
  }
  handleDescriptionSave(description: string): void {
    if (description?.includes('a href')) {
      const firstPartOfString = description.split('a href="')[0];
      const partialString = description.split('a href="')[1];
      const linkEntered = partialString.split('"')[0];
      const lastPartOfString = partialString.split(linkEntered + '"')[1];
      if (!linkEntered.includes('http://') && !linkEntered.includes('https://')) {
        const updatedLink = 'http://' + linkEntered;
        this.form.controls.srv_description.setValue(
          firstPartOfString + 'a href="' + updatedLink + '"' + lastPartOfString
        );
        this.data.ntisServiceDescription.srv_description =
          firstPartOfString + 'a href="' + updatedLink + '"' + lastPartOfString;
      }
    }
  }
}
