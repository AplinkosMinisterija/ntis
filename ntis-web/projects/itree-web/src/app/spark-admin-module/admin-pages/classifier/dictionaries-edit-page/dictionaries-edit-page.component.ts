import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { SprRefDictionariesDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { AdminService } from '../../../admin-services/admin.service';

interface DictionaryListItem {
  id: string;
  label: string;
}

@Component({
  selector: 'app-dictionaries-edit-page',
  templateUrl: './dictionaries-edit-page.component.html',
  styleUrls: ['./dictionaries-edit-page.component.scss'],
})
export class DictionariesEditPageComponent extends DeprecatedBaseEditForm<SprRefDictionariesDAO> implements OnInit {
  dictionaryList: DictionaryListItem[] = [];

  readonly formEditTranslationsReference = 'pages.sprDictionariesEdit';

  // Additional columns begin
  editDateCols: EditFormValues = [
    {
      legend: this.formEditTranslationsReference + '.dCols',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col1',
          translateLabel: true,
          formControlName: 'rfd_d1_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col2',
          translateLabel: true,
          formControlName: 'rfd_d2_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col3',
          translateLabel: true,
          formControlName: 'rfd_d3_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col4',
          translateLabel: true,
          formControlName: 'rfd_d4_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col5',
          translateLabel: true,
          formControlName: 'rfd_d5_colname',
          maxLength: 200,
        },
      ],
    },
  ];

  editStringCols: EditFormValues = [
    {
      legend: this.formEditTranslationsReference + '.cCols',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col1',
          translateLabel: true,
          formControlName: 'rfd_c1_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col2',
          translateLabel: true,
          formControlName: 'rfd_c2_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col3',
          translateLabel: true,
          formControlName: 'rfd_c3_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col4',
          translateLabel: true,
          formControlName: 'rfd_c4_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col5',
          translateLabel: true,
          formControlName: 'rfd_c5_colname',
          maxLength: 200,
        },
      ],
    },
  ];

  editNumericCols: EditFormValues = [
    {
      legend: this.formEditTranslationsReference + '.nCols',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col1',
          translateLabel: true,
          formControlName: 'rfd_n1_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col2',
          translateLabel: true,
          formControlName: 'rfd_n2_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col3',
          translateLabel: true,
          formControlName: 'rfd_n3_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col4',
          translateLabel: true,
          formControlName: 'rfd_n4_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.col5',
          translateLabel: true,
          formControlName: 'rfd_n5_colname',
          maxLength: 200,
        },
      ],
    },
  ];

  editRefCols: EditFormValues = [
    {
      legend: this.formEditTranslationsReference + '.refCols',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.col1',
          translateLabel: true,
          formControlName: 'rfd_ref_domain_1',
          options: undefined,
          optionValue: 'id',
          optionLabel: 'label',
          filter: true,
          optionType: 'single',
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.col2',
          translateLabel: true,
          formControlName: 'rfd_ref_domain_2',
          options: undefined,
          optionValue: 'id',
          optionLabel: 'label',
          filter: true,
          optionType: 'single',
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.col3',
          translateLabel: true,
          formControlName: 'rfd_ref_domain_3',
          options: undefined,
          optionValue: 'id',
          optionLabel: 'label',
          filter: true,
          optionType: 'single',
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.col4',
          translateLabel: true,
          formControlName: 'rfd_ref_domain_4',
          options: undefined,
          optionValue: 'id',
          optionLabel: 'label',
          filter: true,
          optionType: 'single',
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.col5',
          translateLabel: true,
          formControlName: 'rfd_ref_domain_5',
          options: undefined,
          optionValue: 'id',
          optionLabel: 'label',
          filter: true,
          optionType: 'single',
          startFilteringLength: 3,
        },
      ],
    },
  ];
  // Additional columns end and classifier details begin
  editClassifierDetails: EditFormValues = [
    {
      legend: this.formEditTranslationsReference + '.classifierDetails',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.rfd_subsystem',
          translateLabel: true,
          formControlName: 'rfd_subsystem',
          optionType: 'single',
          classifierCode: 'SPR_CLSF_SUBSYSTEM',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfd_table_name',
          translateLabel: true,
          formControlName: 'rfd_table_name',
          isRequired: true,
          maxLength: 20,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfd_name',
          translateLabel: true,
          formControlName: 'rfd_name',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfd_description',
          translateLabel: true,
          formControlName: 'rfd_description',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.rfd_code_type',
          translateLabel: true,
          formControlName: 'rfd_code_type',
          optionType: 'single',
          classifierCode: 'SPR_CLSF_CODE_TYPE',
          isRequired: true,
        },
        {
          type: EditFormItemType.Number,
          label: this.formEditTranslationsReference + '.rfd_code_length',
          translateLabel: true,
          formControlName: 'rfd_code_length',
          max: 999999999999,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfd_code_colname',
          translateLabel: true,
          formControlName: 'rfd_code_colname',
          maxLength: 200,
        },
        {
          type: EditFormItemType.Text,
          label: this.formEditTranslationsReference + '.rfd_desc_colname',
          translateLabel: true,
          formControlName: 'rfd_desc_colname',
          maxLength: 200,
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

  protected buildForm(fb: FormBuilder): void {
    this.loadDictionaries();
    this.form = fb.group({
      rfd_subsystem: new FormControl('', Validators.compose([Validators.required])),
      rfd_table_name: new FormControl('', Validators.compose([Validators.maxLength(20), Validators.required])),
      rfd_name: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
      rfd_description: new FormControl('', Validators.compose([Validators.maxLength(200)])),
      rfd_code_type: new FormControl(''),
      rfd_code_length: new FormControl(null, Validators.max(999999999999)),
      rfd_code_colname: new FormControl('', Validators.maxLength(200)),
      rfd_desc_colname: new FormControl('', Validators.maxLength(200)),
      rfd_ref_domain_1: new FormControl(''),
      rfd_ref_domain_2: new FormControl(''),
      rfd_ref_domain_3: new FormControl(''),
      rfd_ref_domain_4: new FormControl(''),
      rfd_ref_domain_5: new FormControl(''),
      rfd_n1_colname: new FormControl('', Validators.maxLength(200)),
      rfd_n2_colname: new FormControl('', Validators.maxLength(200)),
      rfd_n3_colname: new FormControl('', Validators.maxLength(200)),
      rfd_n4_colname: new FormControl('', Validators.maxLength(200)),
      rfd_n5_colname: new FormControl('', Validators.maxLength(200)),
      rfd_c1_colname: new FormControl('', Validators.maxLength(200)),
      rfd_c2_colname: new FormControl('', Validators.maxLength(200)),
      rfd_c3_colname: new FormControl('', Validators.maxLength(200)),
      rfd_c4_colname: new FormControl('', Validators.maxLength(200)),
      rfd_c5_colname: new FormControl('', Validators.maxLength(200)),
      rfd_d1_colname: new FormControl('', Validators.maxLength(200)),
      rfd_d2_colname: new FormControl('', Validators.maxLength(200)),
      rfd_d3_colname: new FormControl('', Validators.maxLength(200)),
      rfd_d4_colname: new FormControl('', Validators.maxLength(200)),
      rfd_d5_colname: new FormControl('', Validators.maxLength(200)),
    });
  }

  protected setData(data: SprRefDictionariesDAO): void {
    this.data = data;
    this.form.controls.rfd_subsystem.setValue(data.rfd_subsystem);
    this.form.controls.rfd_table_name.setValue(data.rfd_table_name);
    this.form.controls.rfd_name.setValue(data.rfd_name);
    this.form.controls.rfd_description.setValue(data.rfd_description);
    this.form.controls.rfd_code_type.setValue(data.rfd_code_type);
    this.form.controls.rfd_code_length.setValue(data.rfd_code_length);
    this.form.controls.rfd_code_colname.setValue(data.rfd_code_colname);
    this.form.controls.rfd_desc_colname.setValue(data.rfd_desc_colname);
    this.form.controls.rfd_ref_domain_1.setValue(data.rfd_ref_domain_1);
    this.form.controls.rfd_ref_domain_2.setValue(data.rfd_ref_domain_2);
    this.form.controls.rfd_ref_domain_3.setValue(data.rfd_ref_domain_3);
    this.form.controls.rfd_ref_domain_4.setValue(data.rfd_ref_domain_4);
    this.form.controls.rfd_ref_domain_5.setValue(data.rfd_ref_domain_5);
    this.form.controls.rfd_c1_colname.setValue(data.rfd_c1_colname);
    this.form.controls.rfd_c2_colname.setValue(data.rfd_c2_colname);
    this.form.controls.rfd_c3_colname.setValue(data.rfd_c3_colname);
    this.form.controls.rfd_c4_colname.setValue(data.rfd_c4_colname);
    this.form.controls.rfd_c5_colname.setValue(data.rfd_c5_colname);
    this.form.controls.rfd_d1_colname.setValue(data.rfd_d1_colname);
    this.form.controls.rfd_d2_colname.setValue(data.rfd_d2_colname);
    this.form.controls.rfd_d3_colname.setValue(data.rfd_d3_colname);
    this.form.controls.rfd_d4_colname.setValue(data.rfd_d4_colname);
    this.form.controls.rfd_d5_colname.setValue(data.rfd_d5_colname);
    this.form.controls.rfd_n1_colname.setValue(data.rfd_n1_colname);
    this.form.controls.rfd_n2_colname.setValue(data.rfd_n2_colname);
    this.form.controls.rfd_n3_colname.setValue(data.rfd_n3_colname);
    this.form.controls.rfd_n4_colname.setValue(data.rfd_n4_colname);
    this.form.controls.rfd_n5_colname.setValue(data.rfd_n5_colname);
  }

  protected getData(): SprRefDictionariesDAO {
    const result: SprRefDictionariesDAO = this.data != null ? this.data : ({} as SprRefDictionariesDAO);
    result.rfd_subsystem = this.form.controls.rfd_subsystem.value as SprRefDictionariesDAO['rfd_subsystem'];
    result.rfd_table_name = this.form.controls.rfd_table_name.value as SprRefDictionariesDAO['rfd_table_name'];
    result.rfd_name = this.form.controls.rfd_name.value as SprRefDictionariesDAO['rfd_name'];
    result.rfd_description = this.form.controls.rfd_description.value as SprRefDictionariesDAO['rfd_description'];
    result.rfd_code_type = this.form.controls.rfd_code_type.value as SprRefDictionariesDAO['rfd_code_type'];
    result.rfd_code_length = this.form.controls.rfd_code_length.value as SprRefDictionariesDAO['rfd_code_length'];
    result.rfd_code_colname = this.form.controls.rfd_code_colname.value as SprRefDictionariesDAO['rfd_code_colname'];
    result.rfd_desc_colname = this.form.controls.rfd_desc_colname.value as SprRefDictionariesDAO['rfd_desc_colname'];
    result.rfd_ref_domain_1 = this.form.controls.rfd_ref_domain_1.value as SprRefDictionariesDAO['rfd_ref_domain_1'];
    result.rfd_ref_domain_2 = this.form.controls.rfd_ref_domain_2.value as SprRefDictionariesDAO['rfd_ref_domain_2'];
    result.rfd_ref_domain_3 = this.form.controls.rfd_ref_domain_3.value as SprRefDictionariesDAO['rfd_ref_domain_3'];
    result.rfd_ref_domain_4 = this.form.controls.rfd_ref_domain_4.value as SprRefDictionariesDAO['rfd_ref_domain_4'];
    result.rfd_ref_domain_5 = this.form.controls.rfd_ref_domain_5.value as SprRefDictionariesDAO['rfd_ref_domain_5'];
    result.rfd_c1_colname = this.form.controls.rfd_c1_colname.value as SprRefDictionariesDAO['rfd_c1_colname'];
    result.rfd_c2_colname = this.form.controls.rfd_c2_colname.value as SprRefDictionariesDAO['rfd_c2_colname'];
    result.rfd_c3_colname = this.form.controls.rfd_c3_colname.value as SprRefDictionariesDAO['rfd_c3_colname'];
    result.rfd_c4_colname = this.form.controls.rfd_c4_colname.value as SprRefDictionariesDAO['rfd_c4_colname'];
    result.rfd_c5_colname = this.form.controls.rfd_c5_colname.value as SprRefDictionariesDAO['rfd_c5_colname'];
    result.rfd_d1_colname = this.form.controls.rfd_d1_colname.value as SprRefDictionariesDAO['rfd_d1_colname'];
    result.rfd_d2_colname = this.form.controls.rfd_d2_colname.value as SprRefDictionariesDAO['rfd_d2_colname'];
    result.rfd_d3_colname = this.form.controls.rfd_d3_colname.value as SprRefDictionariesDAO['rfd_d3_colname'];
    result.rfd_d4_colname = this.form.controls.rfd_d4_colname.value as SprRefDictionariesDAO['rfd_d4_colname'];
    result.rfd_d5_colname = this.form.controls.rfd_d5_colname.value as SprRefDictionariesDAO['rfd_d5_colname'];
    result.rfd_n1_colname = this.form.controls.rfd_n1_colname.value as SprRefDictionariesDAO['rfd_n1_colname'];
    result.rfd_n2_colname = this.form.controls.rfd_n2_colname.value as SprRefDictionariesDAO['rfd_n2_colname'];
    result.rfd_n3_colname = this.form.controls.rfd_n3_colname.value as SprRefDictionariesDAO['rfd_n3_colname'];
    result.rfd_n4_colname = this.form.controls.rfd_n4_colname.value as SprRefDictionariesDAO['rfd_n4_colname'];
    result.rfd_n5_colname = this.form.controls.rfd_n5_colname.value as SprRefDictionariesDAO['rfd_n5_colname'];
    this.data = result;
    return result;
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getDictionaryRecord(id, actionType).subscribe((rec) => {
      this.onLoadSuccess(rec);
    });
  }

  protected doSave(value: SprRefDictionariesDAO): void {
    this.adminService.setDictionaryRecord(value).subscribe((res) => {
      this.onSaveSuccess(res);
      this.onCancel();
    });
  }

  onCancel(): void {
    this.backToBrowseForm(
      `${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_REF_DICTIONARIES_BROWSE}`
    );
  }

  private loadDictionaries(): void {
    this.adminService.getDictionariesList().subscribe((result) => {
      this.dictionaryList = result.data.map((sprDictionary) => ({
        id: sprDictionary.rfd_table_name,
        label: sprDictionary.rfd_name,
      }));
      this.editRefCols.forEach((group) => {
        group.items.forEach((item) => {
          EditFormComponent.setItemPropertyInValues(
            this.editRefCols,
            item.formControlName,
            'options',
            this.dictionaryList
          );
        });
      });
    });
  }
}
