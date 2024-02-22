import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { SprFile, SprPropertiesModel } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AdminService } from '../../../admin-services/admin.service';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import {
  DATA_TYPE_BOOLEAN,
  DATA_TYPE_DATE,
  DATA_TYPE_FILE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { Observable, takeUntil } from 'rxjs';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { FILE_STATUS_CONFIRMED, FILE_STATUS_UPLOADED } from '@itree-commons/src/constants/files.const';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';

@Component({
  selector: 'app-properties-edit-page',
  templateUrl: './properties-edit-page.component.html',
  styleUrls: ['./properties-edit-page.component.scss'],
})
export class PropertiesEditPageComponent extends DeprecatedBaseEditForm<SprPropertiesModel> implements OnInit {
  readonly formTranslationsReference = 'pages.sprProperties';
  readonly DATA_TYPE_BOOLEAN = DATA_TYPE_BOOLEAN;
  readonly DATA_TYPE_DATE = DATA_TYPE_DATE;
  readonly DATA_TYPE_NUMBER = DATA_TYPE_NUMBER;
  readonly DATA_TYPE_STRING = DATA_TYPE_STRING;
  readonly DATA_TYPE_FILE = DATA_TYPE_FILE;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  uploadedFiles: SprFile[] = [];
  acceptedFileFormat: string;

  editFormValues: EditFormValues = [
    {
      // legend: 'pages.sprFormsBrowse.generalH1',
      // translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.prp_id',
          translateLabel: true,
          formControlName: 'prp_id',
          isRequired: true,
          hidden: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.prp_name',
          translateLabel: true,
          formControlName: 'prp_name',
          isRequired: true,
          maxLength: 200,
          errorDefs: {
            SPR_PROPERTIES_PRP_GUID_PRP_NAME_PRP_INSTALL_INSTANCE_KEY: this.formTranslationsReference + '.error',
          },
          onFocusOut: (): void => {
            this.form.controls.prp_install_instance.markAsTouched();
            this.form.controls.prp_install_instance.updateValueAndValidity();
            this.checkConstraints(this.adminService.checkParamConstraints.bind(this.adminService, this.getData()));
          },
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.prp_description',
          translateLabel: true,
          formControlName: 'prp_description',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.prp_type',
          translateLabel: true,
          formControlName: 'prp_type',
          classifierCode: 'SPR_PROP_TYPE',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.prp_value',
          translateLabel: true,
          formControlName: 'prp_value',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.prp_guid',
          translateLabel: true,
          formControlName: 'prp_guid',
          maxLength: 50,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.prp_install_instance',
          translateLabel: true,
          formControlName: 'prp_install_instance',
          maxLength: 50,
          errorDefs: {
            SPR_PROPERTIES_PRP_GUID_PRP_NAME_PRP_INSTALL_INSTANCE_KEY: this.formTranslationsReference + '.error',
          },
          onFocusOut: (): void => {
            this.form.controls.prp_name.markAsTouched();
            this.form.controls.prp_name.updateValueAndValidity();
            this.checkConstraints(this.adminService.checkParamConstraints.bind(this.adminService, this.getData()));
          },
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    protected datePipe: S2DatePipe,
    private appDataService: AppDataService,
    private fileUploadService: FileUploadService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
    this.acceptedFileFormat = this.appDataService.getPropertyValue('UPLOAD_FILE_EXTENTIONS');
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      prp_id: new FormControl({ value: null, disabled: true }),
      prp_name: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
      prp_description: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
      prp_value: new FormControl('', Validators.maxLength(128)),
      prp_type: new FormControl(DATA_TYPE_STRING, [Validators.required]),
      prp_guid: new FormControl({ value: '', disabled: true }, Validators.maxLength(50)),
      prp_install_instance: new FormControl('', Validators.maxLength(50)),
    });

    this.form.controls.prp_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      this.form.controls.prp_value.setValue('');
      this.setFormControlDisabled('prp_guid', newValue === DATA_TYPE_STRING);
    });
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getParameterRecord(id, actionType).subscribe((rec) => {
      this.onLoadSuccess(rec);
    });
  }

  protected setData(data: SprPropertiesModel): void {
    this.data = data;
    this.form.controls.prp_id.setValue(data.prp_id);
    EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'prp_id', !!data.prp_id);
    this.form.controls.prp_name.setValue(data.prp_name);
    this.form.controls.prp_description.setValue(data.prp_description);
    this.form.controls.prp_type.setValue(data.prp_type);
    this.form.controls.prp_guid.setValue(data.prp_guid);
    this.form.controls.prp_install_instance.setValue(data.prp_install_instance);
    this.form.controls.prp_value.setValue(data.prp_type === DATA_TYPE_DATE ? new Date(data.prp_value) : data.prp_value);
    if (data.attachment) {
      this.uploadedFiles[0] = data.attachment;
    }
    if (this.form.controls.prp_name.value === 'N4200_PAVYZDINE_VI_BYLA') {
      this.acceptedFileFormat = '.csv, .xls';
    }
  }

  protected getData(): SprPropertiesModel {
    const result: SprPropertiesModel = this.data != null ? this.data : ({} as SprPropertiesModel);
    result.prp_id = this.form.controls.prp_id.value as SprPropertiesModel['prp_id'];
    result.prp_name = this.form.controls.prp_name.value as SprPropertiesModel['prp_name'];
    result.prp_description = this.form.controls.prp_description.value as SprPropertiesModel['prp_description'];
    result.prp_type = this.form.controls.prp_type.value as SprPropertiesModel['prp_type'];
    result.prp_guid = this.form.controls.prp_guid.value as SprPropertiesModel['prp_guid'];
    result.prp_install_instance = this.form.controls.prp_install_instance
      .value as SprPropertiesModel['prp_install_instance'];
    result.prp_value =
      result.prp_type === DATA_TYPE_DATE
        ? this.datePipe.transform(this.form.controls.prp_value.value as SprPropertiesModel['prp_value'])
        : result.prp_type === DATA_TYPE_FILE
        ? this.uploadedFiles[0]?.fil_name
        : (this.form.controls.prp_value.value as SprPropertiesModel['prp_value']);
    if (this.uploadedFiles.length === 0) {
      result.prp_fil_id = null;
    }
    result.attachment = this.uploadedFiles[0];
    this.data = result;
    return result;
  }

  protected doSave(value: SprPropertiesModel): Observable<SprPropertiesModel> {
    return this.adminService.setParameterRecord(value);
  }

  public override onSaveSuccess(data: SprPropertiesModel): void {
    super.onSaveSuccess(data);
    this.onCancel();
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_PROPERTIES_BROWSE}`);
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_status === FILE_STATUS_UPLOADED) {
      this.deleteTempFile(fileToDelete);
    } else if (fileToDelete.fil_status === FILE_STATUS_CONFIRMED) {
      this.uploadedFiles = [];
    }
  }

  deleteTempFile(fileToDelete: SprFile): void {
    this.fileUploadService.deleteFile(fileToDelete).subscribe();
  }
}
