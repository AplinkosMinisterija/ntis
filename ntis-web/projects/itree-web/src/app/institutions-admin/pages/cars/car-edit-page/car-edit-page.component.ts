import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { NtisCar } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Observable } from 'rxjs';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-car-edit-page',
  templateUrl: './car-edit-page.component.html',
  styleUrls: ['./car-edit-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, ReactiveFormsModule, FormsModule],
})
export class CarEditPageComponent extends BaseEditForm<NtisCar> implements OnInit {
  readonly formTranslationsReference = 'institutionsAdmin.pages.ntisCarEdit';
  override form = new FormGroup({
    regNo: new FormControl('', Validators.compose([Validators.maxLength(20), Validators.required])),
    model: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
    capacity: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(12), Validators.required])),
    tubeLength: new FormControl<number>(undefined, Validators.compose([Validators.maxLength(12), Validators.required])),
    isInUse: new FormControl<boolean>(true),
  });

  editFormValues: EditFormValues = [
    {
      legend: this.formTranslationsReference + '.headerText',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.cr_reg_no',
          translateLabel: true,
          formControlName: 'regNo',
          isRequired: true,
          maxLength: 20,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.cr_model',
          translateLabel: true,
          formControlName: 'model',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.Number,
          label: this.formTranslationsReference + '.cr_capacity',
          translateLabel: true,
          formControlName: 'capacity',
          isRequired: true,
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 12,
          format: false,
        },
        {
          type: EditFormItemType.Number,
          label: this.formTranslationsReference + '.cr_tube_length',
          translateLabel: true,
          formControlName: 'tubeLength',
          isRequired: true,
          minFractionDigits: 1,
          maxFractionDigits: 2,
          maxLength: 12,
          format: false,
        },
        {
          type: EditFormItemType.Custom,
          label: '',
          translateLabel: true,
          formControlName: 'isInUse',
          hidden: true,
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
    this.form.controls.isInUse.valueChanges.pipe(takeUntilDestroyed()).subscribe((value) => {
      if (value) {
        this.form.controls.regNo.enable();
        this.form.controls.model.enable();
        this.form.controls.capacity.enable();
        this.form.controls.tubeLength.enable();
      } else {
        this.form.controls.regNo.disable();
        this.form.controls.model.disable();
        this.form.controls.capacity.disable();
        this.form.controls.tubeLength.disable();
      }
    });
    this.form.controls.tubeLength.addValidators([Validators.max(999999999999)]);
    this.form.controls.capacity.addValidators([Validators.max(999999999999)]);
  }

  protected doLoad(id?: string): void {
    this.adminService.getCar(id).subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }

  protected setData(data: NtisCar): void {
    this.data = data;
    EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isInUse', !!data.id);
    this.form.setValue({
      regNo: data.regNo,
      model: data.model,
      capacity: data.capacity,
      tubeLength: data.tubeLength,
      isInUse: data.isInUse,
    });
  }

  protected getData(): NtisCar {
    return { ...(this.data || ({} as NtisCar)), ...this.form.value };
  }

  protected doSave(value: NtisCar): Observable<NtisCar> {
    return this.adminService.setCar(value);
  }

  public override onSaveSuccess(data: NtisCar): void {
    super.onSaveSuccess(data);
    this.onCancel();
  }

  onCancel(): void {
    this.backToBrowseForm(
      `${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.NTIS_CARS_LIST}`
    );
  }
}
