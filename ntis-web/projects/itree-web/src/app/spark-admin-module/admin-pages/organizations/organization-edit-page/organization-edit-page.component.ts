import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { ForeignKeyParams, SprListIdKeyValue, SprOrganizationsNtisDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { Observable, debounceTime, takeUntil } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { NtisOrgType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { EMAIL_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';

interface OrganizationParent {
  org_id: number;
  org_name: string;
  org_code: string;
}

@Component({
  selector: 'app-organization-edit-page',
  templateUrl: './organization-edit-page.component.html',
  styleUrls: ['./organization-edit-page.component.scss'],
})
export class OrganizationEditPageComponent extends DeprecatedBaseEditForm<SprOrganizationsNtisDAO> implements OnInit {
  readonly formTranslationsReference = 'pages.sprOrganizations';
  showMunicipality: boolean;
  isInstallationAdminValues: SprListIdKeyValue[] = [];

  editFormValues: EditFormValues = [
    {
      legend: 'pages.sprOrganizationEdit.group_general',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_id',
          translateLabel: true,
          formControlName: 'org_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_name',
          translateLabel: true,
          formControlName: 'org_name',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_code_edit',
          translateLabel: true,
          formControlName: 'org_code',
          maxLength: 9,
          isRequired: true,
          errorDefs: {
            spr_org_uq: this.formTranslationsReference + '.error',
            pattern: this.formTranslationsReference + '.error',
          },
          onFocusOut: (): void => {
            this.checkConstraints(
              this.adminService.checkOrganizationConstraints.bind(this.adminService, this.getData()),
              ['org_code']
            );
          },
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.org_type',
          translateLabel: true,
          formControlName: 'org_type',
          optionType: 'single',
          classifierCode: 'SPR_ORG_TYPE',
          isRequired: false,
        },
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.org_org',
          translateLabel: true,
          formControlName: 'org_org',
          hidden: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.orgType',
          translateLabel: true,
          formControlName: 'orgType',
          optionType: 'single',
          classifierCode: 'NTIS_ORG_TYPE',
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.isInstallationAdmin',
          translateLabel: true,
          formControlName: 'isInstallationAdmin',
          options: undefined,
          optionLabel: 'display',
          optionValue: 'key',
          optionType: 'single',
          isRequired: false,
          hidden: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.orgInstallationFrom',
          translateLabel: true,
          formControlName: 'facilityInstallerDateFrom',
          hidden: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.orgState',
          translateLabel: true,
          optionType: 'single',
          formControlName: 'orgState',
          classifierCode: 'NTIS_ORG_STATE',
          hidden: true,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.orgRejectionReason',
          translateLabel: true,
          formControlName: 'orgRejectionReason',
          hidden: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.municipalityCode',
          translateLabel: true,
          optionType: 'single',
          formControlName: 'municipalityCode',
          classifierCode: 'NTIS_MUNICIPALITIES',
          hidden: true,
          isRequired: true,
        },
      ],
    },
    {
      legend: 'pages.sprOrganizationEdit.group_contacts',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_region',
          translateLabel: true,
          formControlName: 'org_region',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_address',
          translateLabel: true,
          formControlName: 'org_address',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_house_number',
          translateLabel: true,
          formControlName: 'org_house_number',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_bank_account',
          translateLabel: true,
          formControlName: 'org_bank_account',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_bank_name',
          translateLabel: true,
          formControlName: 'org_bank_name',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_phone',
          translateLabel: true,
          formControlName: 'org_phone',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_email',
          translateLabel: true,
          formControlName: 'org_email',
        },
        {
          type: EditFormItemType.Custom,
          label: '',
          formControlName: 'emailNotifications',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.website',
          translateLabel: true,
          formControlName: 'website',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_delegation_person',
          translateLabel: true,
          formControlName: 'org_delegation_person',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.org_delegation_person_position',
          translateLabel: true,
          formControlName: 'org_delegation_person_position',
        },
      ],
    },
    {
      legend: 'pages.sprOrganizationEdit.group_other',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.org_date_from',
          translateLabel: true,
          formControlName: 'org_date_from',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.org_date_to',
          translateLabel: true,
          formControlName: 'org_date_to',
        },
      ],
    },
  ];

  availableParents: OrganizationParent[] = [];
  private availableParentsSearchText: string;

  constructor(
    protected override formBuilder: FormBuilder,
    protected override activatedRoute: ActivatedRoute,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
    this.commonFormServices.translate.get('common.generalUse.yes').subscribe((translation: string) => {
      const yesOption: SprListIdKeyValue = { key: 'Y', display: translation, id: null };
      this.isInstallationAdminValues.push(yesOption);
    });
    this.commonFormServices.translate.get('common.generalUse.no').subscribe((translation: string) => {
      const noOption: SprListIdKeyValue = { key: 'N', display: translation, id: null };
      this.isInstallationAdminValues.push(noOption);
    });
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      org_id: new FormControl({ value: null, disabled: true }),
      org_name: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
      org_org: new FormControl(),
      org_code: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(200)])),
      org_type: new FormControl(null),
      isInstallationAdmin: new FormControl(null),
      facilityInstallerDateFrom: new FormControl(null),
      orgState: new FormControl(null),
      orgRejectionReason: new FormControl(null, Validators.maxLength(200)),
      orgType: new FormControl(null),
      municipalityCode: new FormControl(null),
      org_region: new FormControl('', Validators.maxLength(200)),
      org_address: new FormControl('', Validators.maxLength(200)),
      org_house_number: new FormControl('', Validators.maxLength(20)),
      org_bank_account: new FormControl('', Validators.maxLength(20)),
      org_bank_name: new FormControl('', Validators.maxLength(200)),
      org_phone: new FormControl('', Validators.maxLength(200)),
      org_email: new FormControl(
        '',
        Validators.compose([Validators.maxLength(200), Validators.pattern(new RegExp(EMAIL_PATTERN))])
      ),
      emailNotifications: new FormControl(''),
      website: new FormControl(null, Validators.maxLength(200)),
      org_delegation_person: new FormControl('', Validators.maxLength(200)),
      org_delegation_person_position: new FormControl('', Validators.maxLength(200)),
      org_date_from: new FormControl('', Validators.required),
      org_date_to: new FormControl(''),
    });

    this.form.controls.org_code.addValidators(Validators.pattern(new RegExp('^\\d{9}$')));
    this.form.controls['org_date_from'].addValidators([
      SprDateValidators.validateDateFrom(this.form.controls['org_date_to']),
    ]);
    this.form.controls['org_date_to'].addValidators([
      SprDateValidators.validateDateTo(this.form.controls['org_date_from']),
    ]);
    this.form.controls.org_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.org_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.org_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.org_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.orgType.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.form.controls.isInstallationAdmin.clearValidators();
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isInstallationAdmin', false);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'isInstallationAdmin', 'isRequired', false);
      this.form.controls.isInstallationAdmin.setValue(null);
      this.form.controls.isInstallationAdmin.markAsUntouched();
      this.form.controls.facilityInstallerDateFrom.setValue(null);
      this.form.controls.isInstallationAdmin.updateValueAndValidity();
      this.form.controls.facilityInstallerDateFrom.updateValueAndValidity();
      this.form.controls.municipalityCode.clearValidators();
      this.form.controls.municipalityCode.setValue(null);
      this.form.controls.municipalityCode.updateValueAndValidity();
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgState', false);
      this.form.controls.orgState.clearValidators();
      this.form.controls.orgState.setValue(null);
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgRejectionReason', false);
      this.form.controls.orgState.updateValueAndValidity();
      this.form.controls.orgRejectionReason.setValue(null);
      if (value === NtisOrgType.INST) {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'municipalityCode', true);
        this.form.controls.municipalityCode.addValidators(Validators.required);
        this.form.controls.municipalityCode.updateValueAndValidity();
        this.form.controls.isInstallationAdmin.clearValidators();
      } else {
        this.form.controls.municipalityCode.clearValidators();
        this.form.controls.municipalityCode.setValue(null);
        this.form.controls.municipalityCode.updateValueAndValidity();
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'municipalityCode', false);
        if (value === NtisOrgType.PASLAUG || value === NtisOrgType.PASLAUG_VANDEN || value === NtisOrgType.VANDEN) {
          EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgState', true);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'orgState', 'isRequired', true);
          this.form.controls.orgState.addValidators(Validators.required);
          this.form.controls.orgState.updateValueAndValidity();
          if (value === NtisOrgType.PASLAUG || value === NtisOrgType.PASLAUG_VANDEN) {
            EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isInstallationAdmin', true);
            EditFormComponent.setItemPropertyInValues(
              this.editFormValues,
              'isInstallationAdmin',
              'options',
              this.isInstallationAdminValues
            );
            EditFormComponent.setItemPropertyInValues(this.editFormValues, 'isInstallationAdmin', 'isRequired', true);
            this.form.controls.isInstallationAdmin.addValidators(Validators.required);
            if (
              (this.form.controls.facilityInstallerDateFrom.value as Date) &&
              new Date(this.form.controls.facilityInstallerDateFrom.value as Date).getTime() <= new Date().getTime()
            ) {
              this.form.controls.isInstallationAdmin.setValue(DB_BOOLEAN_TRUE);
            } else {
              this.form.controls.isInstallationAdmin.setValue(null);
            }
            this.form.controls.isInstallationAdmin.updateValueAndValidity();
          } else {
            this.form.controls.isInstallationAdmin.clearValidators();
            EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isInstallationAdmin', false);
            EditFormComponent.setItemPropertyInValues(this.editFormValues, 'isInstallationAdmin', 'isRequired', false);
            this.form.controls.isInstallationAdmin.setValue(null);
            this.form.controls.facilityInstallerDateFrom.setValue(null);
            this.form.controls.isInstallationAdmin.updateValueAndValidity();
            this.form.controls.facilityInstallerDateFrom.updateValueAndValidity();
          }
        }
      }
    });
    this.form.controls.isInstallationAdmin.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      if (value === DB_BOOLEAN_TRUE) {
        if (
          this.form.controls.facilityInstallerDateFrom?.value &&
          new Date(this.form.controls.facilityInstallerDateFrom?.value as Date).getTime() <= new Date().getTime()
        ) {
          this.form.controls.facilityInstallerDateFrom.setValue(
            new Date(this.form.controls.facilityInstallerDateFrom.value as Date)
          );
        } else {
          this.form.controls.facilityInstallerDateFrom.setValue(new Date());
        }
      } else {
        this.form.controls.facilityInstallerDateFrom.setValue(null);
      }
    });
    this.form.controls.orgState.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      if (value === '2') {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgRejectionReason', true);
      } else {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgRejectionReason', false);
      }
    });
  }

  protected setData(data: SprOrganizationsNtisDAO): void {
    this.data = data;
    this.form.controls.org_id.setValue(data.org_id);
    EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'org_id', !!data.org_id);
    this.form.controls.org_name.setValue(data.org_name);
    this.loadParent(data.org_org_id);
    this.form.controls.org_code.setValue(data.org_code);
    this.form.controls.org_type.setValue(data.org_type);
    this.form.controls.orgType.setValue(data.orgType);
    this.form.controls.orgState.setValue(data.orgState?.toString());
    this.form.controls.orgRejectionReason.setValue(data.orgRejectionReason);
    this.form.controls.municipalityCode.setValue(data.municipalityCode?.toString());
    this.form.controls.org_region.setValue(data.org_region);
    this.form.controls.org_address.setValue(data.org_address);
    this.form.controls.org_house_number.setValue(data.org_house_number);
    this.form.controls.org_bank_account.setValue(data.org_bank_account);
    this.form.controls.org_bank_name.setValue(data.org_bank_name);
    this.form.controls.org_phone.setValue(data.org_phone);
    this.form.controls.org_email.setValue(data.org_email);
    this.form.controls.emailNotifications.setValue(data.emailNotifications);
    this.form.controls.website.setValue(data.website);
    this.form.controls.org_delegation_person.setValue(data.org_delegation_person);
    this.form.controls.org_delegation_person_position.setValue(data.org_delegation_person_position);
    this.form.controls.org_date_from.setValue(new Date(data.org_date_from));
    this.form.controls.org_date_to.setValue(data.org_date_to !== null ? new Date(data.org_date_to) : null);
    this.form.controls.facilityInstallerDateFrom.setValue(
      data.facilityInstallerDateFrom !== null ? new Date(data.facilityInstallerDateFrom) : null
    );
    this.form.controls.isInstallationAdmin.setValue(
      data.facilityInstallerDateFrom !== null &&
        new Date(data.facilityInstallerDateFrom).getTime() <= new Date().getTime()
        ? DB_BOOLEAN_TRUE
        : DB_BOOLEAN_FALSE
    );
  }

  protected getData(): SprOrganizationsNtisDAO {
    const result: SprOrganizationsNtisDAO = this.data != null ? this.data : ({} as SprOrganizationsNtisDAO);
    result.org_id = this.form.controls.org_id.value as SprOrganizationsNtisDAO['org_id'];
    result.org_name = this.form.controls.org_name.value as SprOrganizationsNtisDAO['org_name'];
    result.org_org_id = (this.form.controls.org_org.value as OrganizationParent)?.org_id || null;
    result.org_code = (this.form.controls.org_code.value as SprOrganizationsNtisDAO['org_code']).trim();
    result.org_type = this.form.controls.org_type.value as SprOrganizationsNtisDAO['org_type'];
    result.orgState = this.form.controls.orgState.value as SprOrganizationsNtisDAO['orgState'];
    result.orgRejectionReason = this.form.controls.orgRejectionReason
      .value as SprOrganizationsNtisDAO['orgRejectionReason'];
    result.orgType = this.form.controls.orgType.value as SprOrganizationsNtisDAO['orgType'];
    result.municipalityCode = this.form.controls.municipalityCode.value as SprOrganizationsNtisDAO['municipalityCode'];
    result.org_region = this.form.controls.org_region.value as SprOrganizationsNtisDAO['org_region'];
    result.org_address = this.form.controls.org_address.value as SprOrganizationsNtisDAO['org_address'];
    result.org_house_number = this.form.controls.org_house_number.value as SprOrganizationsNtisDAO['org_house_number'];
    result.org_bank_account = this.form.controls.org_bank_account.value as SprOrganizationsNtisDAO['org_bank_account'];
    result.org_bank_name = this.form.controls.org_bank_name.value as SprOrganizationsNtisDAO['org_bank_name'];
    result.org_phone = this.form.controls.org_phone.value as SprOrganizationsNtisDAO['org_phone'];
    result.org_email = this.form.controls.org_email.value as SprOrganizationsNtisDAO['org_email'];
    result.emailNotifications = this.form.controls.emailNotifications
      .value as SprOrganizationsNtisDAO['emailNotifications'];
    result.website = this.form.controls.website.value as SprOrganizationsNtisDAO['website'];
    result.org_delegation_person = this.form.controls.org_delegation_person
      .value as SprOrganizationsNtisDAO['org_delegation_person'];
    result.org_delegation_person_position = this.form.controls.org_delegation_person_position
      .value as SprOrganizationsNtisDAO['org_delegation_person_position'];
    result.org_date_from = this.form.controls.org_date_from.value as SprOrganizationsNtisDAO['org_date_from'];
    result.org_date_to = this.form.controls.org_date_to.value as SprOrganizationsNtisDAO['org_date_to'];
    result.facilityInstallerDateFrom = this.form.controls.facilityInstallerDateFrom
      .value as SprOrganizationsNtisDAO['facilityInstallerDateFrom'];
    this.data = result;
    return result;
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getOrganizationRecord(id, actionType).subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }

  protected doSave(value: SprOrganizationsNtisDAO): Observable<SprOrganizationsNtisDAO> {
    return this.adminService.setOrganizationRecord(value);
  }

  public override onSaveSuccess(data: SprOrganizationsNtisDAO): void {
    super.onSaveSuccess(data);
    this.onCancel();
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_ORGANIZATIONS_BROWSE}`);
  }

  onFilterAvailableParents(text: ForeignKeyParams): void {
    if (text && text.filterValue.length >= 3 && this.availableParentsSearchText !== text.filterValue) {
      this.availableParentsSearchText = text.filterValue;
      this.loadAvailableParents(this.form.get('org_id').value as string, text.filterValue);
    } else if (!text) {
      this.availableParents = [];
    }
  }

  protected loadAvailableParents(id: string, text: string): void {
    this.adminService
      .getParentOrganizations(id, text)
      .pipe(debounceTime(500))
      .subscribe((result) => {
        this.availableParents = result.data;
      });
  }

  protected loadParent(id: string | number): void {
    if (id !== null) {
      this.adminService.getOrganizationRecord(`${id}`).subscribe((result) => {
        const parentObject = { org_id: result.org_id, org_name: result.org_name, org_code: result.org_code };
        this.availableParents = [parentObject];
        this.form.get('org_org').setValue(parentObject);
      });
    }
  }
}
