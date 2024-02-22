import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { NtisInstitutionEditModel } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { takeUntil } from 'rxjs';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { NtisOrgType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { SelectOption } from '@itree-web/src/app/ntis-shared/models/wtf-search';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';

@Component({
  selector: 'app-institutions-edit-page',
  templateUrl: './institutions-edit-page.component.html',
  styleUrls: ['./institutions-edit-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FormsModule, ReactiveFormsModule],
})
export class InstitutionsEditPageComponent extends DeprecatedBaseEditForm<NtisInstitutionEditModel> implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.institutionsEdit';
  checked: boolean = true;
  orgTypeOptions: SelectOption[] = [];

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.headerOrg',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.org_id',
          translateLabel: true,
          formControlName: 'org_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.org_name',
          translateLabel: true,
          formControlName: 'org_name',
          isRequired: true,
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: this.translationsReference + '.org_code',
          translateLabel: true,
          formControlName: 'org_code',
          isRequired: true,
          selector: 'org_code',
          errorDefs: { pattern: 'common.error.orgCode' },
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.org_type',
          translateLabel: true,
          formControlName: 'org_type',
          options: undefined,
          optionLabel: 'option',
          optionValue: 'value',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.municipality',
          translateLabel: true,
          formControlName: 'municipality',
          optionType: 'single',
          classifierCode: 'NTIS_MUNICIPALITIES',
          isRequired: true,
          hidden: true,
          filter: true,
          startFilteringLength: 3,
        },
      ],
    },
    {
      legend: this.translationsReference + '.headerPerson',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_name',
          translateLabel: true,
          formControlName: 'per_name',
          isRequired: true,
          maxLength: 100,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_surname',
          translateLabel: true,
          formControlName: 'per_surname',
          isRequired: true,
          maxLength: 100,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.usr_username',
          translateLabel: true,
          formControlName: 'usr_username',
          infoText: this.translationsReference + '.infoText',
          isRequired: false,
          maxLength: 200,
          errorDefs: {
            SPR_USR_UK: 'pages.sprUserEdit.error',
          },
          onFocusOut: (): void => {
            this.checkConstraints(
              this.adminService.checkUserRecordConstraints.bind(this.adminService, this.getData()),
              ['usr_username']
            );
          },
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_email',
          translateLabel: true,
          formControlName: 'per_email',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_phone_number',
          translateLabel: true,
          formControlName: 'per_phone_number',
          errorDefs: { pattern: 'common.error.phone' },
          maxLength: 200,
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: this.translationsReference + '.sendInvitation',
          translateLabel: true,
          formControlName: 'sendInvitation',
          selector: 'sendInvitation',
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_id',
          translateLabel: true,
          formControlName: 'per_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.ou_id',
          translateLabel: true,
          formControlName: 'ou_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.usr_id',
          translateLabel: true,
          formControlName: 'usr_id',
          hidden: true,
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected commonService: CommonFormServices,
    private clsfService: CommonService,
    private adminService: InstitutionsAdminService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonService, activatedRoute);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.clsfService.getClsf('NTIS_ORG_TYPE').subscribe((result) => {
      this.orgTypeOptions = result
        .filter((item) => item.key === NtisOrgType.INST || item.key === NtisOrgType.INST_LT)
        .map((item) => ({
          option: item.display,
          value: item.key,
        }));
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'org_type', 'options', this.orgTypeOptions);
    });
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      org_id: new FormControl(null),
      org_name: new FormControl('', Validators.required),
      org_code: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.maxLength(9), Validators.minLength(9)])
      ),
      per_name: new FormControl('', Validators.required),
      per_surname: new FormControl('', Validators.required),
      usr_username: new FormControl(''),
      per_email: new FormControl(
        '',
        Validators.compose([Validators.pattern(new RegExp(EMAIL_PATTERN)), Validators.required])
      ),
      per_phone_number: new FormControl(
        null,
        Validators.compose([Validators.maxLength(12), Validators.pattern(new RegExp(PHONE_PATTERN))])
      ),
      ou_id: new FormControl(''),
      per_id: new FormControl(''),
      usr_id: new FormControl(''),
      org_type: new FormControl('', Validators.required),
      municipality: new FormControl(''),
      sendInvitation: new FormControl(''),
    });
    this.form.controls.org_id.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.checked = value === null ? true : false;
      this.form.controls.sendInvitation.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });

    this.form.controls.org_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      if (value === NtisOrgType.INST) {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'municipality', true);
        this.form.controls.municipality.addValidators(Validators.required);
        this.form.controls.municipality.updateValueAndValidity();
      } else {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'municipality', false);
        this.form.controls.municipality.clearValidators();
        this.form.controls.municipality.setValue(null);
        this.form.controls.municipality.updateValueAndValidity();
      }
    });
  }

  protected doSave(value: NtisInstitutionEditModel): void {
    value.sendInvitation = this.checked ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE;
    this.adminService.saveNewInstitution(value).subscribe((res) => {
      this.onSaveSuccess(res);
      this.onCancel();
    });
  }
  protected doLoad(id: string, actionType?: string): void {
    this.adminService.getInstitutionAdmin(id, actionType).subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }
  protected setData(data: NtisInstitutionEditModel): void {
    this.data = data;
    this.form.controls.org_id.setValue(data.org_id);
    this.form.controls.org_name.setValue(data.org_name);
    this.form.controls.org_code.setValue(data.org_code);
    this.form.controls.per_name.setValue(data.per_name);
    this.form.controls.per_surname.setValue(data.per_surname);
    this.form.controls.usr_username.setValue(data.usr_username);
    this.form.controls.per_email.setValue(data.per_email);
    this.form.controls.per_phone_number.setValue(data.per_phone_number);
    this.form.controls.ou_id.setValue(data.ou_id);
    this.form.controls.per_id.setValue(data.per_id);
    this.form.controls.usr_id.setValue(data.usr_id);
    this.form.controls.org_type.setValue(data.org_type);
    this.form.controls.municipality.setValue(data.municipality?.toString());
    if (this.form.controls.usr_id.value) {
      this.form.controls.usr_username.addValidators(Validators.required);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_username', 'isRequired', true);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_username', 'infoText', null);
      this.form.controls.usr_username.updateValueAndValidity();
    }
  }

  protected getData(): NtisInstitutionEditModel {
    const result: NtisInstitutionEditModel = this.data != null ? this.data : ({} as NtisInstitutionEditModel);
    result.org_id = this.form.controls.org_id.value as NtisInstitutionEditModel['org_id'];
    result.org_name = this.form.controls.org_name.value as NtisInstitutionEditModel['org_name'];
    result.org_code = this.form.controls.org_code.value as NtisInstitutionEditModel['org_code'];
    result.per_name = this.form.controls.per_name.value as NtisInstitutionEditModel['per_name'];
    result.usr_username = this.form.controls.usr_username.value as NtisInstitutionEditModel['usr_username'];
    result.per_surname = this.form.controls.per_surname.value as NtisInstitutionEditModel['per_surname'];
    result.per_email = this.form.controls.per_email.value as NtisInstitutionEditModel['per_email'];
    result.per_phone_number = this.form.controls.per_phone_number.value as NtisInstitutionEditModel['per_phone_number'];
    result.ou_id = this.form.controls.ou_id.value as NtisInstitutionEditModel['ou_id'];
    result.per_id = this.form.controls.per_id.value as NtisInstitutionEditModel['per_id'];
    result.usr_id = this.form.controls.usr_id.value as NtisInstitutionEditModel['usr_id'];
    result.org_type = this.form.controls.org_type.value as NtisInstitutionEditModel['org_type'];
    result.municipality = this.form.controls.municipality.value as NtisInstitutionEditModel['municipality'];
    this.data = result;
    return result;
  }
  onCancel(): void {
    this.backToBrowseForm(
      `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_INSTITUTIONS_LIST}`
    );
  }

  onKeypressFilterChar(event: KeyboardEvent): boolean {
    const allowedKeys: string[] = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    if (allowedKeys.includes(event.key)) {
      return true;
    }
    return false;
  }
}
