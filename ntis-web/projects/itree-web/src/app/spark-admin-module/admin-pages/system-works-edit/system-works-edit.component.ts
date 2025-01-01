import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Event } from '@angular/router';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { ItreeCommonsModule } from '@itree-commons/src/lib/itree-commons.module';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { InputSwitchModule, InputSwitchOnChangeEvent } from 'primeng/inputswitch';
import { AdminService } from '../../admin-services/admin.service';
import { NtisSystemWorksEditModel } from '@itree-commons/src/lib/model/api/api';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { ACTIONS_CREATE } from '@itree-commons/src/constants/classificators.constants';
import { SYSTEM_WORKS_EDIT } from '@itree-web/src/app/ntis-shared/constants/forms.const';

@Component({
  selector: 'app-system-works-edit',
  templateUrl: './system-works-edit.component.html',
  styleUrls: ['./system-works-edit.component.scss'],
  standalone: true,
  imports: [ItreeCommonsModule, CommonModule, FormsModule, ReactiveFormsModule, InputSwitchModule],
})
export class SystemWorksEditComponent extends BaseEditForm<NtisSystemWorksEditModel> {
  readonly translationsReference = 'admin.pages.systemWorks';
  isMessageActive: boolean = false;
  canCreate: boolean = false;
  form = new FormGroup({
    nswId: new FormControl<number>(null),
    startDate: new FormControl<Date>(null, Validators.required),
    endDate: new FormControl<Date>(null, Validators.required),
    worksDateFrom: new FormControl<Date>(null, Validators.required),
    worksDateTo: new FormControl<Date>(null, Validators.required),
    additionalInformation: new FormControl<string>(null),
    isActive: new FormControl<string>(DB_BOOLEAN_FALSE),
  });

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.duration',
      translateLegend: true,
      inOneRow: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.dateFrom',
          formControlName: 'startDate',
          translateLabel: true,
          isRequired: true,
          showTime: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.dateTo',
          formControlName: 'endDate',
          translateLabel: true,
          isRequired: true,
          showTime: true,
        },
      ],
    },
    {
      legend: this.translationsReference + '.worksExecutionInformation',
      translateLegend: true,
      inOneRow: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.dateFrom',
          formControlName: 'worksDateFrom',
          translateLabel: true,
          isRequired: true,
          showTime: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.dateTo',
          formControlName: 'worksDateTo',
          translateLabel: true,
          isRequired: true,
          showTime: true,
        },
      ],
    },
    {
      legend: '',
      items: [
        {
          type: EditFormItemType.TextArea,
          label: this.translationsReference + '.additionalInformation',
          formControlName: 'additionalInformation',
          translateLabel: true,
          isRequired: false,
          rows: 6,
        },
        {
          type: EditFormItemType.Custom,
          label: this.translationsReference + '.isActive',
          formControlName: 'isActive',
          translateLabel: true,
        },
      ],
    },
  ];

  constructor(
    protected commonService: CommonFormServices,
    private adminService: AdminService,
    private authService: AuthService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(commonService, activatedRoute);
    this.validateDates();
    this.canCreate = this.authService.isFormActionEnabled(SYSTEM_WORKS_EDIT, ACTIONS_CREATE);
    this.disableForm();
    this.doLoad(null);
  }

  disableForm(): void {
    if (!this.canCreate) {
      this.form.disable();
    }
  }

  protected override doSave(value: NtisSystemWorksEditModel) {
    this.adminService.saveSystemWorksRecord(value).subscribe((result) => {
      this.onSaveSuccess(result);
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected override doLoad(id: string, actionType?: string) {
    this.adminService.getSystemWorksRecord().subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }
  protected override setData(data: NtisSystemWorksEditModel): void {
    this.data = data;
    this.form.controls.nswId.setValue(data.nswId);
    this.form.controls.startDate.setValue(data.startDate ? new Date(data.startDate) : undefined);
    this.form.controls.endDate.setValue(data.endDate ? new Date(data.endDate) : undefined);
    this.form.controls.worksDateFrom.setValue(data.worksDateFrom ? new Date(data.worksDateFrom) : undefined);
    this.form.controls.worksDateTo.setValue(data.worksDateTo ? new Date(data.worksDateTo) : undefined);
    this.form.controls.additionalInformation.setValue(data.additionalInformation);
    this.form.controls.isActive.setValue(data.isActive);
    this.isMessageActive = data.isActive === DB_BOOLEAN_TRUE;
  }

  protected override getData(): NtisSystemWorksEditModel {
    const result = this.data != null ? this.data : ({} as NtisSystemWorksEditModel);
    result.nswId = this.form.controls.nswId.value;
    result.startDate = this.form.controls.startDate.value;
    result.endDate = this.form.controls.endDate.value;
    result.worksDateFrom = this.form.controls.worksDateFrom.value;
    result.worksDateTo = this.form.controls.worksDateTo.value;
    result.additionalInformation = this.form.controls.additionalInformation.value;
    result.isActive = this.form.controls.isActive.value;
    this.data = result;
    return result;
  }

  validateDates(): void {
    this.form.controls.startDate.addValidators([SprDateValidators.validateDateFromEqual(this.form.controls.endDate)]);
    this.form.controls.endDate.addValidators([SprDateValidators.validateDateToEqual(this.form.controls.startDate)]);
    this.form.controls.startDate.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.endDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.endDate.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.startDate.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.worksDateFrom.addValidators([
      SprDateValidators.validateDateFromEqual(this.form.controls.worksDateTo),
    ]);
    this.form.controls.worksDateTo.addValidators([
      SprDateValidators.validateDateToEqual(this.form.controls.worksDateFrom),
    ]);
    this.form.controls.worksDateFrom.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.worksDateTo.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.worksDateTo.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.worksDateFrom.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  onInputSwitch(event: InputSwitchOnChangeEvent): void {
    this.isMessageActive = event.checked;
    if (this.form.controls.nswId.value) {
      this.adminService.updateSysWorksStatus(this.form.controls.nswId.value).subscribe(() => {
        this.form.controls.isActive.setValue(this.isMessageActive ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE);
      });
    } else {
      this.form.controls.isActive.setValue(this.isMessageActive ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE);
    }
  }
}
