import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { NtisFacilityManagerEditModel } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { Observable, takeUntil } from 'rxjs';
import { BuildingDataService } from '../../../services/building-data.service';

@Component({
  selector: 'app-wf-managers-edit',
  templateUrl: './wf-managers-edit.component.html',
  styleUrls: ['./wf-managers-edit.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class WfManagersEditComponent extends DeprecatedBaseEditForm<NtisFacilityManagerEditModel> {
  readonly translationsReference = 'ntisBuildingData.pages.wfManagersEdit';
  wtfId: string;
  selectedFacility: string;

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.managerInfo',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_code',
          translateLabel: true,
          formControlName: 'per_code',
          isRequired: true,
          maxLength: 11,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_name',
          translateLabel: true,
          formControlName: 'per_name',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.per_surname',
          translateLabel: true,
          formControlName: 'per_surname',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.fo_date_from',
          translateLabel: true,
          formControlName: 'fo_date_from',
          isRequired: true,
          minDate: new Date(),
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.fo_date_to',
          translateLabel: true,
          formControlName: 'fo_date_to',
          isRequired: false,
          minDate: new Date(),
        },
      ],
    },
  ];

  constructor(
    private buildingDataService: BuildingDataService,
    private router: Router,
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.wtfId = (params.id as string) || null;
      this.buildingDataService.getSelectedWfFacility(this.wtfId).subscribe((result) => {
        this.selectedFacility = result?.wtf_address;
      });
    });
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      per_code: new FormControl<string>(
        null,
        Validators.compose([
          Validators.required,
          Validators.maxLength(11),
          Validators.pattern(new RegExp('^[1-9][0-9]{10}$')),
        ])
      ),
      per_name: new FormControl<string>(null, Validators.compose([Validators.required])),
      per_surname: new FormControl<string>(null, Validators.compose([Validators.required])),
      fo_date_from: new FormControl<Date>(null, Validators.compose([Validators.required])),
      fo_date_to: new FormControl<Date>(null),
    });
  }

  protected doSave(value: NtisFacilityManagerEditModel): void {
    this.buildingDataService.saveNewFacilityManager(value).subscribe(() => {
      this.commonFormServices.translate
        .get('common.message.savedManagerSuccessfully')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
      this.onCancel();
    });
  }

  protected getData(): NtisFacilityManagerEditModel {
    const result: NtisFacilityManagerEditModel = this.data != null ? this.data : ({} as NtisFacilityManagerEditModel);
    result.per_code = this.form.controls.per_code.value as NtisFacilityManagerEditModel['per_code'];
    result.per_name = this.form.controls.per_name.value as NtisFacilityManagerEditModel['per_name'];
    result.fo_wtf_id = parseInt(this.wtfId);
    result.per_surname = this.form.controls.per_surname.value as NtisFacilityManagerEditModel['per_surname'];
    result.fo_date_from = this.form.controls.fo_date_from.value as NtisFacilityManagerEditModel['fo_date_from'];
    result.fo_date_to = this.form.controls.fo_date_to.value as NtisFacilityManagerEditModel['fo_date_to'];
    this.data = result;
    return result;
  }

  onCancel(): void {
    void this.commonFormServices.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  protected doLoad(id: string, actionType?: string): void | Observable<NtisFacilityManagerEditModel> {}

  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  protected setData(data: NtisFacilityManagerEditModel): void {}
}
