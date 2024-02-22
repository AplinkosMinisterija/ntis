import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { ClsfSprJobDefPeriod, ClsfSprJobDefType } from '@itree-commons/src/lib/enums/classifiers.enums';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { SprJobDefinitionsDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { SprNumberValidators } from '@itree-commons/src/lib/validators/number-validators';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { map, takeUntil } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';

@Component({
  selector: 'app-job-edit-page',
  templateUrl: './job-edit-page.component.html',
  styleUrls: ['./job-edit-page.component.scss'],
})
export class JobEditPageComponent extends DeprecatedBaseEditForm<SprJobDefinitionsDAO> implements OnInit {
  readonly formTranslationsReference = 'pages.sprJobEdit';
  editFormValues: EditFormValues = [
    {
      legend: this.formTranslationsReference + '.groupGeneral',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.system',
          translateLabel: true,
          formControlName: 'system',
          classifierCode: 'SPR_JOB_SYSTEMS',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.id',
          translateLabel: true,
          formControlName: 'id',
          hidden: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.type',
          translateLabel: true,
          formControlName: 'type',
          classifierCode: 'SPR_JOB_DEF_TYPE',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.status',
          translateLabel: true,
          formControlName: 'status',
          classifierCode: 'SPR_JOB_DEF_STATUS',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.code',
          translateLabel: true,
          formControlName: 'code',
          isRequired: true,
          maxLength: 50,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.name',
          translateLabel: true,
          formControlName: 'name',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.description',
          translateLabel: true,
          formControlName: 'description',
          rows: 3,
          maxLength: 600,
        },
      ],
    },
    {
      legend: this.formTranslationsReference + '.groupSetup',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.period',
          translateLabel: true,
          formControlName: 'period',
          classifierCode: 'SPR_JOB_DEF_PERIOD',
          optionType: 'single',
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Number,
          label: this.formTranslationsReference + '.periodInMinutes',
          translateLabel: true,
          formControlName: 'periodInMinutes',
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.nextActionTime',
          translateLabel: true,
          formControlName: 'nextActionTime',
          showTime: true,
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.path',
          translateLabel: true,
          formControlName: 'path',
          maxLength: 500,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.template',
          formControlName: 'templateId',
          translateLabel: true,
          options: undefined,
          optionLabel: 'name',
          optionValue: 'id',
          optionType: 'single',
          filter: true,
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.defaultExecutor',
          translateLabel: true,
          formControlName: 'defaultExecutor',
          maxLength: 500,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.executionParameter',
          translateLabel: true,
          formControlName: 'executionParameter',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.executionUnit',
          translateLabel: true,
          formControlName: 'executionUnit',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.actionType',
          translateLabel: true,
          formControlName: 'actionType',
          maxLength: 500,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.defaultOutputType',
          translateLabel: true,
          formControlName: 'defaultOutputType',
          maxLength: 500,
        },
      ],
    },
    {
      legend: this.formTranslationsReference + '.groupValidity',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.dateFrom',
          translateLabel: true,
          formControlName: 'dateFrom',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.dateTo',
          translateLabel: true,
          formControlName: 'dateTo',
        },
        {
          type: EditFormItemType.Number,
          label: this.formTranslationsReference + '.daysInRequest',
          translateLabel: true,
          formControlName: 'daysInRequest',
          max: 999999999999,
        },
        {
          type: EditFormItemType.Number,
          label: this.formTranslationsReference + '.daysInHistory',
          translateLabel: true,
          formControlName: 'daysInHistory',
          max: 999999999999,
        },
      ],
    },
  ];

  constructor(
    formBuilder: FormBuilder,
    activatedRoute: ActivatedRoute,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.adminService.getJobAvailableTemplates().subscribe((result) => {
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'templateId', 'options', result);
    });
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      system: formBuilder.control(null, Validators.required),
      id: formBuilder.control({ value: null, disabled: true }),
      type: formBuilder.control(null, Validators.required),
      status: formBuilder.control(null, Validators.required),
      code: formBuilder.control(null, [Validators.required, Validators.maxLength(50)]),
      name: formBuilder.control(null, [Validators.required, Validators.maxLength(200)]),
      description: formBuilder.control(null, [Validators.maxLength(600)]),
      period: formBuilder.control(null),
      path: formBuilder.control(null, [Validators.maxLength(500)]),
      templateId: formBuilder.control(null),
      defaultExecutor: formBuilder.control(null, [Validators.maxLength(500)]),
      executionParameter: formBuilder.control(null),
      executionUnit: formBuilder.control(null),
      actionType: formBuilder.control(null, [Validators.maxLength(1000)]),
      defaultOutputType: formBuilder.control(null, [Validators.maxLength(500)]),
      dateFrom: formBuilder.control(null, Validators.required),
      dateTo: formBuilder.control(null),
      daysInRequest: formBuilder.control(null),
      daysInHistory: formBuilder.control(null),
      nextActionTime: formBuilder.control(null),
      periodInMinutes: formBuilder.control(null),
    });

    this.form.controls.type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue === ClsfSprJobDefType.Scheduler) {
        this.schedulerJod(true);
        this.form.controls.period.updateValueAndValidity({ emitEvent: false });
        this.form.controls.nextActionTime.addValidators(Validators.required);
        this.form.controls.nextActionTime.updateValueAndValidity({ emitEvent: false });
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'nextActionTime', true);
      } else {
        this.schedulerJod(false);
        this.form.controls.period.setValue(null, { emitEvent: false });
        this.form.controls.period.clearValidators();
        this.form.controls.period.updateValueAndValidity({ emitEvent: false });
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'period', false);
        this.form.controls.nextActionTime.setValue(null, { emitEvent: false });
        this.form.controls.nextActionTime.clearValidators();
        this.form.controls.nextActionTime.updateValueAndValidity({ emitEvent: false });
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'nextActionTime', false);
        this.form.controls.periodInMinutes.setValue(null, { emitEvent: false });
        this.form.controls.periodInMinutes.clearValidators();
        this.form.controls.periodInMinutes.updateValueAndValidity({ emitEvent: false });
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'periodInMinutes', false);
      }
    });

    this.form.controls.dateFrom.addValidators([SprDateValidators.validateDateFrom(this.form.controls.dateTo)]);
    this.form.controls.dateTo.addValidators([SprDateValidators.validateDateTo(this.form.controls.dateFrom)]);
    this.form.controls.dateFrom.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.dateTo.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.dateTo.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.dateFrom.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  protected schedulerJod(isSchedulerJod: boolean):void {
    if (isSchedulerJod) {
      this.form.controls.periodInMinutes.addValidators([
        Validators.required,
        SprNumberValidators.validateNumber(true, false),
      ]);
      this.form.controls.periodInMinutes.updateValueAndValidity({ emitEvent: false });
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'periodInMinutes', true);
      this.form.controls.period.setValue(ClsfSprJobDefPeriod.Min);
    } else {
      this.form.controls.periodInMinutes.setValue(null, { emitEvent: false });
      this.form.controls.periodInMinutes.clearValidators();
      this.form.controls.periodInMinutes.updateValueAndValidity({ emitEvent: false });
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'periodInMinutes', false);
      this.form.controls.period.setValue(null);
    }
  }

  protected doSave(value: SprJobDefinitionsDAO): void {
    this.adminService.setJob(value).subscribe((result) => {
      this.onSaveSuccess(result);
      this.onCancel();
    });
  }

  protected doLoad(id: string, actionType?: string): void {
    this.adminService
      .getJob(id)
      .pipe(
        map((result) => {
          if (actionType === ActionsEnum.ACTIONS_COPY) {
            result.jde_id = null;
            result.jde_code = `${result.jde_code}_${ActionsEnum.ACTIONS_COPY}`;
            result.jde_name = `${result.jde_name} ${ActionsEnum.ACTIONS_COPY}`;
          }
          return result;
        })
      )
      .subscribe((result) => {
        this.onLoadSuccess(result);
      });
  }

  protected setData(data: SprJobDefinitionsDAO): void {
    this.data = data;
    if (data) {
      this.form.controls.system.setValue(data.jde_system);
      this.form.controls.id.setValue(data.jde_id);
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'id', !!data.jde_id);
      this.form.controls.type.setValue(data.jde_type);
      this.form.controls.status.setValue(data.jde_status);
      this.form.controls.code.setValue(data.jde_code);
      this.form.controls.name.setValue(data.jde_name);
      this.form.controls.description.setValue(data.jde_description);
      this.form.controls.period.setValue(data.jde_period);
      this.form.controls.path.setValue(data.jde_path);
      this.form.controls.templateId.setValue(data.jde_tml_id);
      this.form.controls.defaultExecutor.setValue(data.jde_default_executer);
      this.form.controls.executionParameter.setValue(data.jde_execution_parameter);
      this.form.controls.executionUnit.setValue(data.jde_execution_unit);
      this.form.controls.actionType.setValue(data.jde_action_type);
      this.form.controls.defaultOutputType.setValue(data.jde_default_output_type);
      this.form.controls.dateFrom.setValue(data.jde_date_from && new Date(data.jde_date_from));
      this.form.controls.dateTo.setValue(data.jde_date_to && new Date(data.jde_date_to));
      this.form.controls.daysInRequest.setValue(data.jde_days_in_request);
      this.form.controls.daysInHistory.setValue(data.jde_days_in_history);
      this.form.controls.nextActionTime.setValue(data.jde_next_action_time && new Date(data.jde_next_action_time));
      this.form.controls.periodInMinutes.setValue(data.jde_period_in_minutes);
    }
  }

  protected getData(): SprJobDefinitionsDAO {
    const result: SprJobDefinitionsDAO = this.data != null ? this.data : ({} as SprJobDefinitionsDAO);
    result.jde_system = this.form.controls.system.value as SprJobDefinitionsDAO['jde_system'];
    result.jde_type = this.form.controls.type.value as SprJobDefinitionsDAO['jde_type'];
    result.jde_status = this.form.controls.status.value as SprJobDefinitionsDAO['jde_status'];
    result.jde_code = this.form.controls.code.value as SprJobDefinitionsDAO['jde_code'];
    result.jde_name = this.form.controls.name.value as SprJobDefinitionsDAO['jde_name'];
    result.jde_description = this.form.controls.description.value as SprJobDefinitionsDAO['jde_description'];
    result.jde_period = this.form.controls.period.value as SprJobDefinitionsDAO['jde_period'];
    result.jde_path = this.form.controls.path.value as SprJobDefinitionsDAO['jde_path'];
    result.jde_tml_id = this.form.controls.templateId.value as SprJobDefinitionsDAO['jde_tml_id'];
    result.jde_default_executer = this.form.controls.defaultExecutor
      .value as SprJobDefinitionsDAO['jde_default_executer'];
    result.jde_execution_parameter = this.form.controls.executionParameter
      .value as SprJobDefinitionsDAO['jde_execution_parameter'];
    result.jde_execution_unit = this.form.controls.executionUnit.value as SprJobDefinitionsDAO['jde_execution_unit'];
    result.jde_action_type = this.form.controls.actionType.value as SprJobDefinitionsDAO['jde_action_type'];
    result.jde_default_output_type = this.form.controls.defaultOutputType
      .value as SprJobDefinitionsDAO['jde_default_output_type'];
    result.jde_date_from = this.form.controls.dateFrom.value as SprJobDefinitionsDAO['jde_date_from'];
    result.jde_date_to = this.form.controls.dateTo.value as SprJobDefinitionsDAO['jde_date_to'];
    result.jde_days_in_request = this.form.controls.daysInRequest.value as SprJobDefinitionsDAO['jde_days_in_request'];
    result.jde_days_in_history = this.form.controls.daysInHistory.value as SprJobDefinitionsDAO['jde_days_in_history'];
    result.jde_next_action_time = this.form.controls.nextActionTime
      .value as SprJobDefinitionsDAO['jde_next_action_time'];
    result.jde_period_in_minutes = this.form.controls.periodInMinutes
      .value as SprJobDefinitionsDAO['jde_period_in_minutes'];
    this.data = result;
    return result;
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_JOBS_BROWSE}`);
  }
}
