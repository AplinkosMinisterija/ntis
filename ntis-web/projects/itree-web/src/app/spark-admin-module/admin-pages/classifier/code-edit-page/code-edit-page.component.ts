import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { CodeDictionaryModel, SprRefCodesDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { takeUntil } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';

@Component({
  selector: 'app-code-edit-page',
  templateUrl: './code-edit-page.component.html',
  styleUrls: ['./code-edit-page.component.scss'],
})
export class CodeEditPageComponent extends DeprecatedBaseEditForm<SprRefCodesDAO> implements OnInit {
  rfd_id: string;
  rfc_domain: string;
  rfc_parent: CodeDictionaryModel;
  additionalFormValues: EditFormValues = [];
  showAdditionalDetails: boolean = false;

  readonly formEditTranslationsReference = 'pages.sprCodesTranslationsEdit';
  editFormValues: EditFormValues = [
    {
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfc_id',
          translateLabel: true,
          formControlName: 'rfc_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfc_rfd_id',
          translateLabel: true,
          formControlName: 'rfc_rfd_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfc_domain',
          translateLabel: true,
          formControlName: 'rfc_domain',
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfc_code',
          translateLabel: true,
          formControlName: 'rfc_code',
          isRequired: true,
          maxLength: 20,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfc_meaning',
          translateLabel: true,
          formControlName: 'rfc_meaning',
          isRequired: true,
          maxLength: 50,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfc_description',
          translateLabel: true,
          formControlName: 'rfc_description',
          isRequired: true,
          maxLength: 100,
        },
        {
          type: EditFormItemType.Date,
          label: this.formEditTranslationsReference + '.rfc_date_from',
          translateLabel: true,
          formControlName: 'rfc_date_from',
          isRequired: true,
          hidden: false,
        },
        {
          type: EditFormItemType.Date,
          label: this.formEditTranslationsReference + '.rfc_date_to',
          translateLabel: true,
          formControlName: 'rfc_date_to',
          hidden: false,
        },
        {
          type: EditFormItemType.Number,
          label: this.formEditTranslationsReference + '.rfc_order',
          translateLabel: true,
          formControlName: 'rfc_order',
          isRequired: true,
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override activatedRoute: ActivatedRoute,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.getAdditionalInfo(this.rfd_id);
  }

  protected buildForm(fb: FormBuilder): void {
    this.activatedRoute.parent.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.rfd_id = typeof params.id === 'string' ? params.id : null;
      this.rfc_domain = typeof params.tableName === 'string' ? params.tableName : null;
    });

    this.form = fb.group({
      rfc_id: new FormControl({ value: null, disabled: true }),
      rfc_rfd_id: new FormControl({ value: this.rfd_id, disabled: true }),
      rfc_domain: new FormControl({ value: this.rfc_domain, disabled: true }),
      rfc_date_from: new FormControl(
        '',
        Validators.compose([Validators.minLength(10), Validators.maxLength(10), Validators.required])
      ),
      rfc_date_to: new FormControl('', Validators.compose([Validators.minLength(10), Validators.maxLength(10)])),
      rfc_code: new FormControl('', Validators.compose([Validators.maxLength(20), Validators.required])),
      rfc_meaning: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      rfc_description: new FormControl('', Validators.compose([Validators.maxLength(100), Validators.required])),
      rfc_order: new FormControl(null, Validators.compose([Validators.required])),
      rfc_ref_code_1: new FormControl(''),
      rfc_ref_code_2: new FormControl(''),
      rfc_ref_code_3: new FormControl(''),
      rfc_ref_code_4: new FormControl(''),
      rfc_ref_code_5: new FormControl(''),
      n01: new FormControl(''),
      n02: new FormControl(''),
      n03: new FormControl(''),
      n04: new FormControl(''),
      n05: new FormControl(''),
      c01: new FormControl('', Validators.maxLength(200)),
      c02: new FormControl('', Validators.maxLength(200)),
      c03: new FormControl('', Validators.maxLength(200)),
      c04: new FormControl('', Validators.maxLength(200)),
      c05: new FormControl('', Validators.maxLength(200)),
      d01: new FormControl(''),
      d02: new FormControl(''),
      d03: new FormControl(''),
      d04: new FormControl(''),
      d05: new FormControl(''),
    });
    this.form.controls['rfc_date_from'].addValidators([
      SprDateValidators.validateDateFrom(this.form.controls['rfc_date_to']),
    ]);
    this.form.controls['rfc_date_to'].addValidators([
      SprDateValidators.validateDateTo(this.form.controls['rfc_date_from']),
    ]);
    this.form.controls.rfc_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.rfc_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.rfc_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.rfc_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  protected setData(data: SprRefCodesDAO): void {
    this.data = data;
    this.form.controls.rfc_id.setValue(data.rfc_id);
    this.form.controls.rfc_rfd_id.setValue(this.rfd_id);
    this.form.controls.rfc_domain.setValue(this.rfc_domain);
    this.form.controls.rfc_date_from.setValue(data ? new Date(data.rfc_date_from) : data.rfc_date_from);
    this.form.controls.rfc_date_to.setValue(data.rfc_date_to !== null ? new Date(data.rfc_date_to) : null);
    this.form.controls.rfc_code.setValue(data.rfc_code);
    this.form.controls.rfc_meaning.setValue(data.rfc_meaning);
    this.form.controls.rfc_description.setValue(data.rfc_description);
    this.form.controls.rfc_order.setValue(data.rfc_order);
    this.form.controls.rfc_ref_code_1.setValue(data.rfc_ref_code_1);
    this.form.controls.rfc_ref_code_2.setValue(data.rfc_ref_code_2);
    this.form.controls.rfc_ref_code_3.setValue(data.rfc_ref_code_3);
    this.form.controls.rfc_ref_code_4.setValue(data.rfc_ref_code_4);
    this.form.controls.rfc_ref_code_5.setValue(data.rfc_ref_code_5);
    this.form.controls.n01.setValue(data.n01);
    this.form.controls.n02.setValue(data.n02);
    this.form.controls.n03.setValue(data.n03);
    this.form.controls.n04.setValue(data.n04);
    this.form.controls.n05.setValue(data.n05);
    this.form.controls.c01.setValue(data.c01);
    this.form.controls.c02.setValue(data.c02);
    this.form.controls.c03.setValue(data.c03);
    this.form.controls.c04.setValue(data.c04);
    this.form.controls.c05.setValue(data.c05);
    this.form.controls.d01.setValue(data.d01 !== null ? new Date(data.d01) : null);
    this.form.controls.d02.setValue(data.d02 !== null ? new Date(data.d02) : null);
    this.form.controls.d03.setValue(data.d03 !== null ? new Date(data.d03) : null);
    this.form.controls.d04.setValue(data.d04 !== null ? new Date(data.d04) : null);
    this.form.controls.d05.setValue(data.d05 !== null ? new Date(data.d05) : null);
  }

  protected getData(): SprRefCodesDAO {
    const result: SprRefCodesDAO = this.data != null ? this.data : ({} as SprRefCodesDAO);
    result.rfc_id = this.form.controls.rfc_id.value as SprRefCodesDAO['rfc_id'];
    result.rfc_rfd_id = this.form.controls.rfc_rfd_id.value as SprRefCodesDAO['rfc_rfd_id'];
    result.rfc_domain = this.form.controls.rfc_domain.value as SprRefCodesDAO['rfc_domain'];
    result.rfc_date_from = this.form.controls.rfc_date_from.value as SprRefCodesDAO['rfc_date_from'];
    result.rfc_date_to = this.form.controls.rfc_date_to.value as SprRefCodesDAO['rfc_date_to'];
    result.rfc_code = this.form.controls.rfc_code.value as SprRefCodesDAO['rfc_code'];
    result.rfc_meaning = this.form.controls.rfc_meaning.value as SprRefCodesDAO['rfc_meaning'];
    result.rfc_description = this.form.controls.rfc_description.value as SprRefCodesDAO['rfc_description'];
    result.rfc_order = this.form.controls.rfc_order.value as SprRefCodesDAO['rfc_order'];
    result.rfc_ref_code_1 = this.form.controls.rfc_ref_code_1.value as SprRefCodesDAO['rfc_ref_code_1'];
    result.rfc_ref_code_2 = this.form.controls.rfc_ref_code_2.value as SprRefCodesDAO['rfc_ref_code_2'];
    result.rfc_ref_code_3 = this.form.controls.rfc_ref_code_3.value as SprRefCodesDAO['rfc_ref_code_3'];
    result.rfc_ref_code_4 = this.form.controls.rfc_ref_code_4.value as SprRefCodesDAO['rfc_ref_code_4'];
    result.rfc_ref_code_5 = this.form.controls.rfc_ref_code_5.value as SprRefCodesDAO['rfc_ref_code_5'];
    result.n01 = this.form.controls.n01.value as SprRefCodesDAO['n01'];
    result.n02 = this.form.controls.n02.value as SprRefCodesDAO['n02'];
    result.n03 = this.form.controls.n03.value as SprRefCodesDAO['n03'];
    result.n04 = this.form.controls.n04.value as SprRefCodesDAO['n04'];
    result.n05 = this.form.controls.n05.value as SprRefCodesDAO['n05'];
    result.c01 = this.form.controls.c01.value as SprRefCodesDAO['c01'];
    result.c02 = this.form.controls.c02.value as SprRefCodesDAO['c02'];
    result.c03 = this.form.controls.c03.value as SprRefCodesDAO['c03'];
    result.c04 = this.form.controls.c04.value as SprRefCodesDAO['c04'];
    result.c05 = this.form.controls.c05.value as SprRefCodesDAO['c05'];
    result.d01 = this.form.controls.d01.value as SprRefCodesDAO['d01'];
    result.d02 = this.form.controls.d02.value as SprRefCodesDAO['d02'];
    result.d03 = this.form.controls.d03.value as SprRefCodesDAO['d03'];
    result.d04 = this.form.controls.d04.value as SprRefCodesDAO['d04'];
    result.d05 = this.form.controls.d05.value as SprRefCodesDAO['d05'];
    this.data = result;
    return result;
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getRefCodeRecord(id, actionType).subscribe((rec) => {
      this.onLoadSuccess(rec);
    });
  }

  protected doSave(value: SprRefCodesDAO): void {
    this.adminService.setRefCodeRecord(value).subscribe((res) => {
      this.onSaveSuccess(res);
      this.onCancel();
    });
  }

  onCancel(): void {
    this.backToBrowseForm(
      `${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_REF_DICTIONARIES_BROWSE}/${RoutingConst.SPARK_REF_CODES_BROWSE}/${this.rfd_id}/${this.rfc_domain}`
    );
  }

  private getAdditionalInfo(id: string): void {
    this.adminService.getCodeDictionary(id).subscribe((result) => {
      this.rfc_parent = result;

      this.showAdditionalDetails = Object.values(this.rfc_parent).some((value) => value);

      this.additionalFormValues = [
        {
          items: [
            {
              type: EditFormItemType.Date,
              label: this.rfc_parent?.rfd_d1_colname,
              formControlName: 'd01',
              hidden: !this.rfc_parent?.rfd_d1_colname,
            },
            {
              type: EditFormItemType.Date,
              label: this.rfc_parent?.rfd_d2_colname,
              formControlName: 'd02',
              hidden: !this.rfc_parent?.rfd_d2_colname,
            },
            {
              type: EditFormItemType.Date,
              label: this.rfc_parent?.rfd_d3_colname,
              formControlName: 'd03',
              hidden: !this.rfc_parent?.rfd_d3_colname,
            },
            {
              type: EditFormItemType.Date,
              label: this.rfc_parent?.rfd_d4_colname,
              formControlName: 'd04',
              hidden: !this.rfc_parent?.rfd_d4_colname,
            },
            {
              type: EditFormItemType.Date,
              label: this.rfc_parent?.rfd_d5_colname,
              formControlName: 'd05',
              hidden: !this.rfc_parent?.rfd_d5_colname,
            },
            {
              type: EditFormItemType.Text,
              label: this.rfc_parent?.rfd_c1_colname,
              formControlName: 'c01',
              maxLength: 200,
              hidden: !this.rfc_parent?.rfd_c1_colname,
            },
            {
              type: EditFormItemType.Text,
              label: this.rfc_parent?.rfd_c2_colname,
              formControlName: 'c02',
              maxLength: 200,
              hidden: !this.rfc_parent?.rfd_c2_colname,
            },
            {
              type: EditFormItemType.Text,
              label: this.rfc_parent?.rfd_c3_colname,
              formControlName: 'c03',
              maxLength: 200,
              hidden: !this.rfc_parent?.rfd_c3_colname,
            },
            {
              type: EditFormItemType.Text,
              label: this.rfc_parent?.rfd_c4_colname,
              formControlName: 'c04',
              maxLength: 200,
              hidden: !this.rfc_parent?.rfd_c4_colname,
            },
            {
              type: EditFormItemType.Text,
              label: this.rfc_parent?.rfd_c5_colname,
              formControlName: 'c05',
              maxLength: 200,
              hidden: !this.rfc_parent?.rfd_c5_colname,
            },
            {
              type: EditFormItemType.Number,
              label: this.rfc_parent?.rfd_n1_colname,
              formControlName: 'n01',
              hidden: !this.rfc_parent?.rfd_n1_colname,
            },
            {
              type: EditFormItemType.Number,
              label: this.rfc_parent?.rfd_n2_colname,
              formControlName: 'n02',
              hidden: !this.rfc_parent?.rfd_n2_colname,
            },
            {
              type: EditFormItemType.Number,
              label: this.rfc_parent?.rfd_n3_colname,
              formControlName: 'n03',
              hidden: !this.rfc_parent?.rfd_n3_colname,
            },
            {
              type: EditFormItemType.Number,
              label: this.rfc_parent?.rfd_n4_colname,
              formControlName: 'n04',
              hidden: !this.rfc_parent?.rfd_n4_colname,
            },
            {
              type: EditFormItemType.Number,
              label: this.rfc_parent?.rfd_n5_colname,
              formControlName: 'n05',
              hidden: !this.rfc_parent?.rfd_n5_colname,
            },
            {
              type: EditFormItemType.Select,
              label: this.rfc_parent?.rfd_ref_domain_1,
              formControlName: 'rfc_ref_code_1',
              optionType: 'single',
              filter: true,
              startFilteringLength: 3,
              classifierCode: this.rfc_parent?.rfd_ref_domain_1_clsf,
              hidden: !this.rfc_parent?.rfd_ref_domain_1,
            },
            {
              type: EditFormItemType.Select,
              label: this.rfc_parent?.rfd_ref_domain_2,
              formControlName: 'rfc_ref_code_2',
              optionType: 'single',
              filter: true,
              startFilteringLength: 3,
              classifierCode: this.rfc_parent?.rfd_ref_domain_2_clsf,
              hidden: !this.rfc_parent?.rfd_ref_domain_2,
            },
            {
              type: EditFormItemType.Select,
              label: this.rfc_parent?.rfd_ref_domain_3,
              formControlName: 'rfc_ref_code_3',
              optionType: 'single',
              filter: true,
              startFilteringLength: 3,
              classifierCode: this.rfc_parent?.rfd_ref_domain_3_clsf,
              hidden: !this.rfc_parent?.rfd_ref_domain_3,
            },
            {
              type: EditFormItemType.Select,
              label: this.rfc_parent?.rfd_ref_domain_4,
              formControlName: 'rfc_ref_code_4',
              optionType: 'single',
              filter: true,
              startFilteringLength: 3,
              classifierCode: this.rfc_parent?.rfd_ref_domain_4_clsf,
              hidden: !this.rfc_parent?.rfd_ref_domain_4,
            },
            {
              type: EditFormItemType.Select,
              label: this.rfc_parent?.rfd_ref_domain_5,
              formControlName: 'rfc_ref_code_5',
              optionType: 'single',
              filter: true,
              startFilteringLength: 3,
              classifierCode: this.rfc_parent?.rfd_ref_domain_5_clsf,
              hidden: !this.rfc_parent?.rfd_ref_domain_5,
            },
          ],
        },
      ];
    });
  }
}
