import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AdminService } from '../../../admin-services/admin.service';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { takeUntil } from 'rxjs';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { SprRolesDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';

@Component({
  selector: 'app-role-edit-page',
  templateUrl: './role-edit-page.component.html',
  styleUrls: ['./role-edit-page.component.scss'],
})
export class RoleEditPageComponent extends DeprecatedBaseEditForm<SprRolesDAO> implements OnInit {
  readonly formTranslationsReference = 'pages.sprRoles';

  editFormValues: EditFormValues = [
    {
      legend: 'pages.sprRolesEdit.roleGroupGeneral',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.id',
          translateLabel: true,
          formControlName: 'rol_id',
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.rol_type',
          translateLabel: true,
          formControlName: 'rol_type',
          isRequired: true,
          classifierCode: 'SPR_ROLE_TYPE',
          optionType: 'single',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.rol_name',
          translateLabel: true,
          formControlName: 'rol_name',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.rol_code',
          translateLabel: true,
          formControlName: 'rol_code',
          isRequired: true,
          maxLength: 240,
        },
      ],
    },
    {
      legend: 'pages.sprRolesEdit.roleGroupOther',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.rol_date_from',
          translateLabel: true,
          formControlName: 'rol_date_from',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.rol_date_to',
          translateLabel: true,
          formControlName: 'rol_date_to',
          isRequired: false,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.rol_description',
          translateLabel: true,
          formControlName: 'rol_description',
          isRequired: false,
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    protected datePipe: S2DatePipe,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      rol_id: new FormControl({ value: null, disabled: true }),
      rol_type: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      rol_name: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
      rol_code: new FormControl('', Validators.compose([Validators.maxLength(240), Validators.required])),
      rol_description: new FormControl('', Validators.maxLength(200)),
      rol_date_from: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10)])
      ),
      rol_date_to: new FormControl('', Validators.compose([Validators.minLength(10), Validators.maxLength(10)])),
    });
    this.form.controls['rol_date_from'].addValidators([
      SprDateValidators.validateDateFrom(this.form.controls['rol_date_to']),
    ]);
    this.form.controls['rol_date_to'].addValidators([
      SprDateValidators.validateDateTo(this.form.controls['rol_date_from']),
    ]);
    this.form.controls.rol_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.rol_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.rol_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.rol_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getRoleRecord(id, actionType).subscribe((result) => {
      this.onLoadSuccess(result);
    });
  }

  protected setData(data: SprRolesDAO): void {
    this.data = data;
    this.form.controls.rol_id.setValue(data.rol_id);
    EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'rol_id', !!data.rol_id);
    this.form.controls.rol_type.setValue(data.rol_type);
    this.form.controls.rol_name.setValue(data.rol_name);
    this.form.controls.rol_code.setValue(data.rol_code);
    this.form.controls.rol_description.setValue(data.rol_description);
    this.form.controls.rol_date_from.setValue(data ? new Date(data.rol_date_from) : data.rol_date_from);
    this.form.controls.rol_date_to.setValue(data.rol_date_to !== null ? new Date(data.rol_date_to) : null);
  }

  protected getData(): SprRolesDAO {
    const result = this.data != null ? this.data : ({} as SprRolesDAO);
    result.rol_id = this.form.controls.rol_id.value as SprRolesDAO['rol_id'];
    result.rol_type = this.form.controls.rol_type.value as SprRolesDAO['rol_type'];
    result.rol_name = this.form.controls.rol_name.value as SprRolesDAO['rol_name'];
    result.rol_code = this.form.controls.rol_code.value as SprRolesDAO['rol_code'];
    result.rol_description = this.form.controls.rol_description.value as SprRolesDAO['rol_description'];
    result.rol_date_from = this.form.controls.rol_date_from.value as SprRolesDAO['rol_date_from'];
    result.rol_date_to = this.form.controls.rol_date_to.value as SprRolesDAO['rol_date_to'];
    this.data = result;
    return result;
  }

  protected doSave(value: SprRolesDAO): void {
    this.adminService.setRoleRecord(value).subscribe((res) => {
      this.onSaveSuccess(res);
      this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_ROLES_BROWSE}`);
    });
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_ROLES_BROWSE}`);
  }
}
