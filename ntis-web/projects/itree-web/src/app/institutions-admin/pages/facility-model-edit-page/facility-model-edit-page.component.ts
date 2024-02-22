import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NtisFacilityModelDAO, NtisFacilityModelEditModel, SprFile } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable, takeUntil } from 'rxjs';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { ItreeCommonsModule } from '../../../../../../itree-commons/src/lib/itree-commons.module';
import { CommonModule } from '@angular/common';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';

@Component({
  selector: 'app-facility-model-edit-page',
  templateUrl: './facility-model-edit-page.component.html',
  styleUrls: ['./facility-model-edit-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FormsModule, ReactiveFormsModule],
})
export class FacilityModelEditPageComponent extends BaseEditForm<NtisFacilityModelEditModel> implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.facilityModelEdit';

  protected uploadedFiles: SprFile[] = [];

  override form = new FormGroup({
    rfc_manufa: new FormControl('', Validators.compose([Validators.required])),
    rfc_code: new FormControl('', Validators.compose([Validators.maxLength(20), Validators.required])),
    rfc_meaning: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
    rfc_description: new FormControl('', Validators.compose([Validators.maxLength(100), Validators.required])),
    rfc_date_from: new FormControl<Date>(null, Validators.compose([Validators.required])),
    rfc_date_to: new FormControl<Date>(null),
    fam_pop_equivalent: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(20)])),
    fam_tech_pass: new FormControl('', Validators.compose([Validators.maxLength(20)])),
    fam_chds: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(10)])),
    fam_bds: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(10)])),
    fam_float_material: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(10)])),
    fam_phosphor: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(10)])),
    fam_nitrogen: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(10)])),
  });

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.headerText',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.rfc_manufa',
          translateLabel: true,
          formControlName: 'rfc_manufa',
          optionType: 'single',
          classifierCode: 'NTIS_FACIL_MANUFA',
          isRequired: true,
          filter: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.rfc_code',
          translateLabel: true,
          formControlName: 'rfc_code',
          isRequired: true,
          maxLength: 20,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.rfc_meaning',
          translateLabel: true,
          formControlName: 'rfc_meaning',
          isRequired: true,
          maxLength: 50,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.rfc_description',
          translateLabel: true,
          formControlName: 'rfc_description',
          isRequired: true,
          maxLength: 100,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.rfc_date_from',
          translateLabel: true,
          formControlName: 'rfc_date_from',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.rfc_date_to',
          translateLabel: true,
          formControlName: 'rfc_date_to',
        },
      ],
    },
    {
      legend: this.translationsReference + '.extraInfo',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.fam_pop_equivalent',
          translateLabel: true,
          formControlName: 'fam_pop_equivalent',
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 20,
          format: false,
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: this.translationsReference + '.fam_tech_pass',
          translateLabel: true,
          formControlName: 'fam_tech_pass',
          selector: 'fam_tech_pass',
        },
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.fam_chds',
          translateLabel: true,
          formControlName: 'fam_chds',
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 10,
          format: false,
        },
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.fam_bds',
          translateLabel: true,
          formControlName: 'fam_bds',
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 10,
          format: false,
        },
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.fam_float_material',
          translateLabel: true,
          formControlName: 'fam_float_material',
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 10,
          format: false,
        },
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.fam_phosphor',
          translateLabel: true,
          formControlName: 'fam_phosphor',
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 10,
          format: false,
        },
        {
          type: EditFormItemType.Number,
          label: this.translationsReference + '.fam_nitrogen',
          translateLabel: true,
          formControlName: 'fam_nitrogen',
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 10,
          format: false,
        },
      ],
    },
  ];

  constructor(
    protected commonService: CommonFormServices,
    private adminService: InstitutionsAdminService,
    protected override activatedRoute: ActivatedRoute
  ) {
    super(commonService, activatedRoute);
    this.validateDates();
  }

  protected doLoad(id?: string): void {
    this.adminService.getFacilityModel(id).subscribe((res) => {
      if (res && !res.facilityModelAdditionalDetails) {
        res.facilityModelAdditionalDetails = {} as NtisFacilityModelDAO;
      }
      this.onLoadSuccess(res);
    });
  }

  protected setData(data: NtisFacilityModelEditModel): void {
    this.data = data;
    this.form.controls.rfc_manufa.setValue(data.facilityModel.rfc_ref_code_1);
    this.form.controls.rfc_code.setValue(data.facilityModel.rfc_code);
    this.form.controls.rfc_meaning.setValue(data.facilityModel.rfc_meaning);
    this.form.controls.rfc_description.setValue(data.facilityModel.rfc_description);
    this.form.controls.rfc_date_from.setValue(
      data.facilityModel?.rfc_date_from != null ? new Date(data.facilityModel.rfc_date_from) : null
    );
    this.form.controls.rfc_date_to.setValue(
      data.facilityModel?.rfc_date_to != null ? new Date(data.facilityModel.rfc_date_to) : null
    );
    this.form.controls.fam_pop_equivalent.setValue(data.facilityModelAdditionalDetails?.fam_pop_equivalent);
    this.form.controls.fam_tech_pass.setValue(data.facilityModelAdditionalDetails?.fam_tech_pass);
    this.form.controls.fam_chds.setValue(data.facilityModelAdditionalDetails?.fam_chds);
    this.form.controls.fam_bds.setValue(data.facilityModelAdditionalDetails?.fam_bds);
    this.form.controls.fam_float_material.setValue(data.facilityModelAdditionalDetails?.fam_float_material);
    this.form.controls.fam_phosphor.setValue(data.facilityModelAdditionalDetails?.fam_phosphor);
    this.form.controls.fam_nitrogen.setValue(data.facilityModelAdditionalDetails?.fam_nitrogen);

    if (this.data.passport !== null) {
      this.uploadedFiles[0] = this.data.passport;
    }
  }

  protected getData(): NtisFacilityModelEditModel {
    const result: NtisFacilityModelEditModel = this.data
      ? this.data
      : ({ facilityModel: {}, facilityModelAdditionalDetails: {} } as NtisFacilityModelEditModel);
    result.facilityModel.rfc_ref_code_1 = this.form.controls.rfc_manufa.value;
    result.facilityModel.rfc_code = this.form.controls.rfc_code.value;
    result.facilityModel.rfc_meaning = this.form.controls.rfc_meaning.value;
    result.facilityModel.rfc_description = this.form.controls.rfc_description.value;
    result.facilityModel.rfc_date_from = this.form.controls.rfc_date_from.value;
    result.facilityModel.rfc_date_to = this.form.controls.rfc_date_to?.value;
    result.facilityModelAdditionalDetails.fam_pop_equivalent = this.form.controls.fam_pop_equivalent?.value;
    result.facilityModelAdditionalDetails.fam_tech_pass = this.form.controls.fam_tech_pass?.value;
    result.facilityModelAdditionalDetails.fam_chds = this.form.controls.fam_chds?.value;
    result.facilityModelAdditionalDetails.fam_bds = this.form.controls.fam_bds?.value;
    result.facilityModelAdditionalDetails.fam_float_material = this.form.controls.fam_float_material?.value;
    result.facilityModelAdditionalDetails.fam_phosphor = this.form.controls.fam_phosphor?.value;
    result.facilityModelAdditionalDetails.fam_nitrogen = this.form.controls.fam_nitrogen?.value;
    result.passport = this.uploadedFiles[0];
    this.data = result;
    return result;
  }

  protected doSave(value: NtisFacilityModelEditModel): Observable<NtisFacilityModelEditModel> {
    return this.adminService.setFacilityModel(value);
  }

  public override onSaveSuccess(data: NtisFacilityModelEditModel): void {
    super.onSaveSuccess(data);
    this.onCancel();
  }

  onCancel(): void {
    this.backToBrowseForm(
      `${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.NTIS_FACILITY_MODELS}`
    );
  }

  validateDates(): void {
    this.form.controls.rfc_date_from.addValidators([
      SprDateValidators.validateDateFromEqual(this.form.controls.rfc_date_to),
    ]);
    this.form.controls.rfc_date_to.addValidators([
      SprDateValidators.validateDateToEqual(this.form.controls.rfc_date_from),
    ]);
    this.form.controls.rfc_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.rfc_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.rfc_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue: Date) => {
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'rfc_date_to', 'minDate', newValue);
      this.form.controls.rfc_date_to.updateValueAndValidity();
    });
  }
}
