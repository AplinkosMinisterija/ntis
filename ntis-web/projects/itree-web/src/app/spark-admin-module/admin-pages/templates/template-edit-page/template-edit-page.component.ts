import { Component } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { SprTemplate, SprTemplateTextsDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { CommonFormServices, DeprecatedBaseEditForm, getLang } from '@itree/ngx-s2-commons';
import { map, pairwise, startWith, takeUntil } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { ClsfSprTmplType } from '@itree-commons/src/lib/enums/classifiers.enums';
import { TEMPLATE_TEXT_CODE_BODY, TEMPLATE_TEXT_CODE_SUBJECT } from '@itree-commons/src/constants/templates.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { ScrollService } from '@itree-commons/src/lib/services/scroll.service';

@Component({
  selector: 'app-template-edit-page',
  templateUrl: './template-edit-page.component.html',
  styleUrls: ['./template-edit-page.component.scss'],
})
export class TemplateEditPageComponent extends DeprecatedBaseEditForm<SprTemplate> {
  readonly ClsfSprTmplType = ClsfSprTmplType;
  readonly formTranslationsReference = 'pages.sprTemplateEdit';
  headerTextStr: string = '';
  indexForLoading: number;
  editFormValues: EditFormValues = [
    {
      legend: this.formTranslationsReference + '.groupGeneral',
      translateLegend: true,
      items: [
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
          classifierCode: 'SPR_TMPL_TYPE',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.status',
          translateLabel: true,
          formControlName: 'status',
          classifierCode: 'SPR_TMPL_STATUS',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.code',
          translateLabel: true,
          formControlName: 'tml_code',
          isRequired: true,
          maxLength: 240,
          errorDefs: {
            TML_CODE_UK: this.formTranslationsReference + '.error',
          },
          onFocusOut: (): void => {
            this.checkConstraints(this.adminService.checkTemplateConstraints.bind(this.adminService, this.getData()), [
              'tml_code',
            ]);
          },
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
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.logicImpl',
          translateLabel: true,
          formControlName: 'logicImpl',
          maxLength: 200,
          hidden: true,
        },
      ],
    },
    {
      legend: this.formTranslationsReference + '.groupOther',
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
      ],
    },
    {
      legend: this.formTranslationsReference + '.groupTexts',
      translateLegend: true,
      key: 'textsBlock',
      hidden: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.selectLanguage',
          translateLabel: true,
          formControlName: 'language',
          classifierCode: 'SPR_LANGUAGE',
          optionType: 'single',
          showClear: false,
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'texts',
        },
      ],
    },
  ];
  initialLanguageValue: string = null;

  constructor(
    formBuilder: FormBuilder,
    activatedRoute: ActivatedRoute,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private appDataServ: AppDataService,
    private scrollService: ScrollService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
    this.activatedRoute.snapshot['params'];
    this.headerTextStr = !isNaN(this.activatedRoute.snapshot.params['id'] as number)
      ? 'pages.sprTemplateEdit.headerTextEdit'
      : 'pages.sprTemplateEdit.headerTextCreate';
    this.scrollService.innerScroll.pipe(takeUntil(this.destroy$)).subscribe((res) => {
      this.indexForLoading = res;
    });
  }

  override ngOnDestroy(): void {
    this.scrollService.innerScroll.next(1);
    this.destroy$.next();
    this.destroy$.complete();
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      id: formBuilder.control({ value: null, disabled: true }),
      type: formBuilder.control(null, Validators.required),
      status: formBuilder.control(null, Validators.required),
      tml_code: formBuilder.control(null, [Validators.required, Validators.maxLength(240)]),
      name: formBuilder.control(null, [Validators.required, Validators.maxLength(200)]),
      description: formBuilder.control(null, Validators.maxLength(200)),
      logicImpl: formBuilder.control('', Validators.maxLength(200)),
      dateFrom: formBuilder.control(null, Validators.required),
      dateTo: formBuilder.control(null),
      language: formBuilder.control(null /*, Validators.required*/),
      texts: formBuilder.array([]),
    });
    if (!this.appDataServ.multilanguageExists()) {
      this.form.controls.language.setValue(getLang());
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'language', 'hidden', true);
    }

    this.form.controls.dateFrom.addValidators([SprDateValidators.validateDateFrom(this.form.controls.dateTo)]);
    this.form.controls.dateTo.addValidators([SprDateValidators.validateDateTo(this.form.controls.dateFrom)]);
    this.form.controls.dateFrom.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.dateTo.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.dateTo.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.dateFrom.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });

    this.form.controls.type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.type.value
        ? EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'textsBlock', this.form, false, true)
        : EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'textsBlock', this.form, false, false);

      this.removeNonEmailTexts();
      this.removeEmailTexts();
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        if (params.id === RoutingConst.NEW) {
          this.createEmailTexts();
        }
      });
      this.toggleEmailTexts();
    });

    this.form.controls.language.valueChanges
      .pipe(takeUntil(this.destroy$), startWith(null as string), pairwise())
      .subscribe(([previousValue]: [string, string]) => {
        previousValue = previousValue || this.initialLanguageValue;
        if (previousValue) {
          const previousLanguageGroups = this.textsFormArrayGroups.filter(
            (textGroup) => textGroup.controls.language.value === previousValue
          );
          const errorsExist = previousLanguageGroups.some((textGroup) =>
            Object.keys(textGroup.controls).some((controlName) => textGroup.controls[controlName].errors)
          );
          if (errorsExist) {
            if (
              this.form.controls.type.value === ClsfSprTmplType.Email &&
              !previousLanguageGroups.some((textGroup) =>
                Object.keys(textGroup.controls)
                  .filter((controlName) => controlName !== 'language' && controlName !== 'code')
                  .some((controlName) => textGroup.controls[controlName].value)
              )
            ) {
              this.removeTextsForLanguage(previousValue);
            } else {
              this.form.controls.language.setValue(previousValue, { emitEvent: false });
              this.textsFormArray.markAllAsTouched();
              this.commonFormServices.translate
                .get('common.message.correctValidationErrors')
                .subscribe((translation: string) => {
                  this.commonFormServices.appMessages.showError('', translation);
                });
            }
          } else {
            this.textsFormArray.markAsPristine();
          }
        }
        this.createEmailTexts();
        this.toggleEmailTexts();
      });
  }

  get textsFormArray(): FormArray {
    return this.form.controls.texts as FormArray;
  }

  get textsFormArrayGroups(): FormGroup[] {
    return this.textsFormArray.controls as FormGroup[];
  }

  protected doSave(value: SprTemplate): void {
    this.adminService.setTemplate(value).subscribe((result) => {
      this.onSaveSuccess(result);
      this.onCancel();
    });
  }

  protected doLoad(id: string, actionType?: string): void {
    this.adminService
      .getTemplate(id)
      .pipe(
        map((result) => {
          if (actionType === ActionsEnum.ACTIONS_COPY) {
            result.tml_id = null;
            result.tml_code = `${result.tml_code}_${ActionsEnum.ACTIONS_COPY}`;
            result.tml_name = `${result.tml_name} ${ActionsEnum.ACTIONS_COPY}`;
            result.texts = result.texts.map((text) => ({ ...text, tmt_id: null }));
          }
          return result;
        })
      )
      .subscribe((result) => {
        this.onLoadSuccess(result);
      });
  }

  protected setData(data: SprTemplate): void {
    this.data = data;
    if (data) {
      this.form.controls.id.setValue(data.tml_id);
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'id', !!data.tml_id);
      this.form.controls.type.setValue(data.tml_type);
      this.form.controls.tml_code.setValue(data.tml_code);
      this.form.controls.status.setValue(data.tml_status);
      this.form.controls.name.setValue(data.tml_name);
      this.form.controls.description.setValue(data.tml_description);
      this.form.controls.logicImpl.setValue(data.tml_logic_impl);
      this.form.controls.dateFrom.setValue(data.tml_date_from && new Date(data.tml_date_from));
      this.form.controls.dateTo.setValue(data.tml_date_to && new Date(data.tml_date_to));
      this.data.texts
        .sort((a, b) => (a.tmt_code < b.tmt_code ? 1 : -1))
        .forEach((text) => {
          this.addText(text);
        });
      this.form.controls.language.setValue(getLang(), { emitEvent: false });
      this.toggleEmailTexts();
    }
  }

  protected getData(): SprTemplate {
    const result: SprTemplate = this.data != null ? this.data : ({} as SprTemplate);
    result.tml_type = this.form.controls.type.value as SprTemplate['tml_type'];
    result.tml_code = this.form.controls.tml_code.value as SprTemplate['tml_code'];
    result.tml_name = this.form.controls.name.value as SprTemplate['tml_name'];
    result.tml_status = this.form.controls.status.value as SprTemplate['tml_status'];
    result.tml_description = this.form.controls.description.value as SprTemplate['tml_description'];
    result.tml_logic_impl = this.form.controls.logicImpl.value as SprTemplate['tml_logic_impl'];
    result.tml_date_from = this.form.controls.dateFrom.value as SprTemplate['tml_date_from'];
    result.tml_date_to = this.form.controls.dateTo.value as SprTemplate['tml_date_to'];
    result.texts = this.textsFormArrayGroups.map(
      (textGroup) =>
        ({
          tmt_id: textGroup.controls.id.value as SprTemplateTextsDAO['tmt_id'],
          tmt_code: textGroup.controls.code.value as SprTemplateTextsDAO['tmt_code'],
          tmt_name: textGroup.controls.name.value as SprTemplateTextsDAO['tmt_name'],
          tmt_description: textGroup.controls.description.value as SprTemplateTextsDAO['tmt_description'],
          tmt_lang: textGroup.controls.language.value as SprTemplateTextsDAO['tmt_lang'],
          tmt_text: textGroup.controls.text.value as SprTemplateTextsDAO['tmt_text'],
          tmt_date_from: textGroup.controls.dateFrom.value as SprTemplateTextsDAO['tmt_date_from'],
          tmt_date_to: textGroup.controls.dateTo.value as SprTemplateTextsDAO['tmt_date_to'],
        } as SprTemplateTextsDAO)
    );
    this.data = result;
    return result;
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_TEMPLATES_BROWSE}`);
  }

  private validateTextCode(textGroups: FormGroup[]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const textCodes = textGroups
        .filter(
          (textGroup) =>
            textGroup.controls.code.value &&
            textGroup.controls.language.value === this.form.controls.language.value &&
            textGroup.controls.code !== control
        )
        .map((textGroup) => textGroup.controls.code.value as string);
      return textCodes.includes(control.value as string) ? { uniqueCode: true } : null;
    };
  }

  addText(text?: SprTemplateTextsDAO): void {
    const textGroup = new FormGroup({
      id: new FormControl(text?.tmt_id || null),
      code: new FormControl(text?.tmt_code || null, [
        Validators.required,
        Validators.maxLength(32),
        this.validateTextCode(this.textsFormArrayGroups),
      ]),
      name: new FormControl(text?.tmt_name || null, [Validators.required, Validators.maxLength(200)]),
      description: new FormControl(text?.tmt_description || null, Validators.maxLength(600)),
      language: new FormControl(text?.tmt_lang || this.form.controls.language.value, Validators.required),
      text: new FormControl(text?.tmt_text || null, Validators.required),
      dateFrom: new FormControl(text?.tmt_date_from ? new Date(text.tmt_date_from) : null, Validators.required),
      dateTo: new FormControl(text?.tmt_date_to ? new Date(text.tmt_date_to) : null),
    });

    textGroup.controls.dateFrom.addValidators([SprDateValidators.validateDateFrom(textGroup.controls.dateTo)]);
    textGroup.controls.dateTo.addValidators([SprDateValidators.validateDateTo(textGroup.controls.dateFrom)]);
    textGroup.controls.dateFrom.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      textGroup.controls.dateTo.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    textGroup.controls.dateTo.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      textGroup.controls.dateFrom.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });

    textGroup.controls.code.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      const codesToValidate = this.textsFormArrayGroups
        .filter(
          (filteredTextGroup) =>
            filteredTextGroup !== textGroup &&
            filteredTextGroup.controls.language.value === this.form.controls.language.value
        )
        .map((filteredTextGroup) => filteredTextGroup.controls.code);
      codesToValidate.forEach((code) => code.updateValueAndValidity({ emitEvent: false }));
    });
    this.textsFormArray.push(textGroup);
  }

  removeText(index: number): void {
    this.textsFormArray.removeAt(index);
  }

  removeNonEmailTexts(): void {
    if (this.form.controls.type.value === ClsfSprTmplType.Email) {
      const nonEmailTextGroups = this.textsFormArrayGroups.filter(
        (textGroup) =>
          textGroup.controls.code.value !== TEMPLATE_TEXT_CODE_SUBJECT &&
          textGroup.controls.code.value !== TEMPLATE_TEXT_CODE_BODY
      );
      nonEmailTextGroups.forEach((textGroup) => {
        this.textsFormArray.removeAt(this.textsFormArrayGroups.indexOf(textGroup));
      });
    }
  }

  removeEmailTexts(): void {
    if (this.form.controls.type.value === ClsfSprTmplType.Jasper) {
      const emailTextGroups = this.textsFormArrayGroups.filter(
        (textGroup) =>
          textGroup.controls.code.value === TEMPLATE_TEXT_CODE_SUBJECT ||
          textGroup.controls.code.value === TEMPLATE_TEXT_CODE_BODY
      );
      emailTextGroups.forEach((textGroup) => {
        this.textsFormArray.removeAt(this.textsFormArrayGroups.indexOf(textGroup));
      });
    }
  }

  createEmailTexts(): void {
    if (this.form.controls.type.value === ClsfSprTmplType.Email && this.form.controls.language.value) {
      const currentLanguageGroups = this.textsFormArrayGroups.filter(
        (textGroup) => textGroup.controls.language.value === this.form.controls.language.value
      );
      if (!currentLanguageGroups.some((textGroup) => textGroup.controls.code.value === TEMPLATE_TEXT_CODE_SUBJECT)) {
        this.addText({
          tmt_code: TEMPLATE_TEXT_CODE_SUBJECT,
          tmt_name: 'EMAIL_SUBJECT',
          tmt_description: 'Email subject',
          tmt_date_from: new Date(),
        } as SprTemplateTextsDAO);
      }
      if (!currentLanguageGroups.some((textGroup) => textGroup.controls.code.value === TEMPLATE_TEXT_CODE_BODY)) {
        this.addText({
          tmt_code: TEMPLATE_TEXT_CODE_BODY,
          tmt_name: 'EMAIL_BODY',
          tmt_description: 'Email body',
          tmt_date_from: new Date(),
        } as SprTemplateTextsDAO);
      }
    }
  }

  removeTextsForLanguage(language: string): void {
    this.textsFormArrayGroups
      .filter((textGroup) => textGroup.controls.language.value === language)
      .forEach((textGroup) => {
        this.textsFormArray.removeAt(this.textsFormArrayGroups.indexOf(textGroup));
      });
  }

  toggleEmailTexts(): void {
    const disable = this.form.controls.type.value === ClsfSprTmplType.Email;
    const emailTextGroups = this.textsFormArrayGroups.filter(
      (textGroup) =>
        textGroup.controls.code.value === TEMPLATE_TEXT_CODE_SUBJECT ||
        textGroup.controls.code.value === TEMPLATE_TEXT_CODE_BODY
    );
    emailTextGroups.forEach((textGroup) => {
      disable ? textGroup.controls.code.disable() : textGroup.controls.code.enable();
    });
  }
}
