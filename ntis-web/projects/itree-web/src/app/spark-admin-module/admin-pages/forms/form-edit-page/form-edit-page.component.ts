import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { AdminService } from '../../../admin-services/admin.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { SprFormsDAO } from '@itree-commons/src/lib/model/api/api';

@Component({
  selector: 'app-form-edit-page',
  templateUrl: './form-edit-page.component.html',
  styleUrls: ['./form-edit-page.component.scss'],
})
export class FormEditPageComponent extends DeprecatedBaseEditForm<SprFormsDAO> implements OnInit {
  readonly formTranslationsReference = 'pages.sprFormsBrowse';

  editFormValues: EditFormValues = [
    {
      // legend: 'pages.sprFormsBrowse.generalH1',
      // translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.frm_id',
          translateLabel: true,
          formControlName: 'frm_id',
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.frm_name',
          translateLabel: true,
          formControlName: 'frm_name',
          isRequired: true,
          maxLength: 50,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.frm_code',
          translateLabel: true,
          formControlName: 'frm_code',
          isRequired: true,
          maxLength: 32,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.frm_description',
          translateLabel: true,
          formControlName: 'frm_description',
          isRequired: false,
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      frm_id: new FormControl({ value: null, disabled: true }),
      frm_code: new FormControl('', [Validators.maxLength(32), Validators.required]),
      frm_name: new FormControl('', [Validators.maxLength(50), Validators.required]),
      frm_description: new FormControl('', Validators.compose([Validators.maxLength(500)])),
    });
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getFormRecord(id, actionType).subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }

  protected setData(data: SprFormsDAO): void {
    this.data = data;
    this.form.controls.frm_id.setValue(data.frm_id);
    EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'frm_id', !!data.frm_id);
    this.form.controls.frm_code.setValue(data.frm_code);
    this.form.controls.frm_name.setValue(data.frm_name);
    this.form.controls.frm_description.setValue(data.frm_description);
  }

  protected getData(): SprFormsDAO {
    const result: SprFormsDAO = this.data != null ? this.data : ({} as SprFormsDAO);
    result.frm_id = this.form.controls.frm_id.value as SprFormsDAO['frm_id'];
    result.frm_code = this.form.controls.frm_code.value as SprFormsDAO['frm_code'];
    result.frm_name = this.form.controls.frm_name.value as SprFormsDAO['frm_name'];
    result.frm_description = this.form.controls.frm_description.value as SprFormsDAO['frm_description'];
    this.data = result;
    return result;
  }

  protected doSave(value: SprFormsDAO): void {
    this.adminService.setFormRecord(value).subscribe((res) => {
      this.onSaveSuccess(res);
      this.onCancel();
    });
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_FORMS_BROWSE}`);
  }
}
