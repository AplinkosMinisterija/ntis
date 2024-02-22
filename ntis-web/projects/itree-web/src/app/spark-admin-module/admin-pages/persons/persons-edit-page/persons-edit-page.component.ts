import { ChangeDetectorRef, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { fadeInFields } from '@itree-commons/src/lib/animations/animations';
import { SprPersonsDAO } from '@itree-commons/src/lib/model/api/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AdminService } from '../../../admin-services/admin.service';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';

@Component({
  selector: 'app-persons-edit-page',
  templateUrl: './persons-edit-page.component.html',
  styleUrls: ['./persons-edit-page.component.scss'],
  animations: [fadeInFields],
})
export class PersonsEditPageComponent extends BaseEditForm<SprPersonsDAO> {
  form = new FormGroup({
    // General person data
    per_name: new FormControl('', Validators.compose([Validators.maxLength(20), Validators.required])),
    per_surname: new FormControl('', Validators.compose([Validators.maxLength(20), Validators.required])),
    per_date_of_birth: new FormControl<Date>(null, Validators.compose([Validators.maxLength(10), Validators.required])),

    // Contact person data
    per_lrt_resident: new FormControl(
      DB_BOOLEAN_TRUE,
      Validators.compose([Validators.maxLength(1), Validators.required])
    ),
    per_code: new FormControl('', Validators.maxLength(11)),
    per_address_city: new FormControl<string>(null, Validators.maxLength(20)),
    per_address_street: new FormControl<string>(null, Validators.maxLength(20)),
    per_address_house_number: new FormControl<string>(null, Validators.maxLength(10)),
    per_address_flat_number: new FormControl<string>(null, Validators.maxLength(10)),
    per_email: new FormControl<string>(null, Validators.pattern(new RegExp(EMAIL_PATTERN))),
    per_phone_number: new FormControl<string>(
      null, 
      Validators.compose([Validators.maxLength(12), Validators.pattern(new RegExp(PHONE_PATTERN))])
    ),

    // Document person data
    per_code_exists: new FormControl(
      DB_BOOLEAN_FALSE),
    per_document_number: new FormControl<string>(null),
    per_document_type: new FormControl<string>(
      null
    ),
    per_document_valid_until: new FormControl(
      null,
      ),
  });
  isLithuanian: string;
  maximumDate = new Date();
  autoComplete: 'off' | 'on' = 'off';

  readonly formTranslationsReference = 'pages.sprPersonsBrowse';
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;

  editFormValues: EditFormValues = [
    {
      legend: 'pages.sprPersonsBrowse.general_person_data',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'name_wrapper',
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.per_date_of_birth',
          translateLabel: true,
          formControlName: 'per_date_of_birth',
          isRequired: true,
        },
      ],
    },
    {
      legend: 'pages.sprPersonsBrowse.contact_person_data',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.resident',
          translateLabel: true,
          formControlName: 'per_lrt_resident',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.personal_code',
          translateLabel: true,
          formControlName: 'per_code',
          isRequired: false,
          maxLength: 11,
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'address_wrapper',
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'housing_wrapper',
        },
        {
          type: EditFormItemType.CustomEmpty,
          label: null,
          formControlName: null,
          selector: 'contact_wrapper',
        },
      ],
    },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    protected override activatedRoute: ActivatedRoute,
    changeDetectorRef: ChangeDetectorRef
  ) {
    super(commonFormServices, activatedRoute);

    this.form.controls.per_lrt_resident.valueChanges.pipe(takeUntilDestroyed()).subscribe((res) => {
      const showControl: boolean = res === DB_BOOLEAN_TRUE;
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'per_code', showControl);
    });

    this.form.controls.per_code_exists.valueChanges.pipe(takeUntilDestroyed()).subscribe((value) => {
      const showDocument = value === DB_BOOLEAN_TRUE;
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'document_wrapper', showDocument);
      if (showDocument) {
        this.form.controls.per_document_valid_until.addValidators(
          Validators.compose([Validators.maxLength(10), Validators.required])
        );
        this.form.controls.per_document_type.addValidators(
          Validators.compose([Validators.maxLength(50), Validators.required])
        );
      } else {
        this.form.controls.per_document_valid_until.clearValidators();
        this.form.controls.per_document_type.clearValidators();
      }
      this.form.controls.per_document_valid_until.updateValueAndValidity({ emitEvent: false });
      this.form.controls.per_document_type.updateValueAndValidity({ emitEvent: false });
      changeDetectorRef.detectChanges();
    });
  }

  protected setData(data: SprPersonsDAO): void {
    this.data = data;

    // General person data
    this.form.controls.per_name.setValue(data.per_name);
    this.form.controls.per_surname.setValue(data.per_surname);
    this.form.controls.per_date_of_birth.setValue(
      data.per_date_of_birth !== null ? new Date(data.per_date_of_birth) : null
    );

    // Contact person data
    this.form.controls.per_lrt_resident.setValue(data.per_lrt_resident);
    this.form.controls.per_code.setValue(data.per_code);
    this.form.controls.per_address_city.setValue(data.per_address_city);
    this.form.controls.per_address_street.setValue(data.per_address_street);
    this.form.controls.per_address_flat_number.setValue(data.per_address_flat_number);
    this.form.controls.per_address_house_number.setValue(data.per_address_house_number);
    this.form.controls.per_email.setValue(data.per_email);
    this.form.controls.per_phone_number.setValue(data.per_phone_number);

    // Document person data
    // Should be per_document_code_exists
    this.form.controls.per_code_exists.setValue(data.per_code_exists);
    this.form.controls.per_document_number.setValue(data.per_document_number);
    this.form.controls.per_document_type.setValue(data.per_document_type);
    this.form.controls.per_document_valid_until.setValue(
    data.per_document_valid_until !== null ? new Date(data.per_document_valid_until) : null
    );

    this.isLithuanian = this.form.controls.per_lrt_resident.value;
  }

  protected getData(): SprPersonsDAO {
    const result: SprPersonsDAO = this.data ? this.data : ({} as SprPersonsDAO);
    // General person data
    result.per_name = this.form.controls.per_name.value;
    result.per_surname = this.form.controls.per_surname.value;
    result.per_date_of_birth = this.form.controls.per_date_of_birth.value;
    result.per_code_exists = this.form.controls.per_code_exists.value;
    result.per_lrt_resident = this.form.controls.per_lrt_resident.value;

    // Contact person data
    result.per_address_city = this.form.controls.per_address_city.value;
    result.per_address_street = this.form.controls.per_address_street.value;
    result.per_address_flat_number = this.form.controls.per_address_flat_number.value;
    result.per_address_house_number = this.form.controls.per_address_house_number.value;
    result.per_email = this.form.controls.per_email.value;
    result.per_phone_number = this.form.controls.per_phone_number.value;

    // Document person data
    result.per_code = this.form.controls.per_code.value;
    result.per_document_number = this.form.controls.per_document_number.value;
    result.per_document_type = this.form.controls.per_document_type.value;
    result.per_document_valid_until = this.form.controls.per_document_valid_until
      .value as SprPersonsDAO['per_document_valid_until'];

    this.isLithuanian = this.form.controls.per_lrt_resident.value;
    this.data = result;
    return result;
  }

  showHideFields(): void {
    this.isLithuanian = this.form.controls.per_lrt_resident.value;
  }

  protected doSave(value: SprPersonsDAO): void {
    this.adminService.setPersonRecord(value).subscribe((results) => {
      this.onSaveSuccess(results);
      this.onCancel();
    });
  }

  protected doLoad(): void {
    this.onLoadSuccess(this.activatedRoute.snapshot.data['personData'] as SprPersonsDAO);
  }

  onFormSubmit($event: Event): void {
    this.form.controls.per_code_exists.setValue(DB_BOOLEAN_FALSE);
    this.form.controls.per_document_number.setValue(null);
    this.form.controls.per_document_type.setValue(null);
    this.form.controls.per_document_valid_until.setValue(null);

    if(this.form.controls.per_code.value === "") {
      this.form.controls.per_code.setValue(null);
    }
    this.onSubmit($event);
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_PERSONS_BROWSE}`);
  }
}
