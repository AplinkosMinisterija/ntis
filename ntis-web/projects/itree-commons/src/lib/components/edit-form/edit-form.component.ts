import {
  AfterContentChecked,
  AfterContentInit,
  ChangeDetectorRef,
  Component,
  ContentChildren,
  EventEmitter,
  Input,
  Output,
  QueryList,
  TemplateRef,
} from '@angular/core';
import { FormGroup } from '@angular/forms';
import { TemplateDirective } from '../../directives/template.directive';
import { EditFormItemType } from '../../enums/edit-form.enums';
import { EditFormItemsGroup, EditFormValues } from '../../types/edit-form';
import { HttpEvent } from '@angular/common/http';
import { S2File } from '@itree/ngx-s2-ui';
import { Observable } from 'rxjs';
import { AppDataService } from '../../services/app-data.service';
import { FileUploadService } from '../../services/file-upload.service';
import { s2FileToSprFile } from '../../utils/s2-file-to-spr-file';

@Component({
  selector: 'spr-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.scss'],
})
export class EditFormComponent implements AfterContentInit, AfterContentChecked {
  readonly EditFormItemType = EditFormItemType;

  @Input() formGroup: FormGroup | undefined;
  @Input() values: EditFormValues = [];
  @Input() showSaveButton = true;
  @Input() submitButtonText = 'common.action.save';
  @Input() translateSubmitButtonText = true;
  @Input() cancelButtonText = 'common.action.back';
  @Input() translateCancelButtonText = true;
  @Input() showCancelButton = true;
  @Input() autoComplete: 'off' | 'on' = 'on';
  @Output() cancel = new EventEmitter();
  @ContentChildren(TemplateDirective) templatesQueryList: QueryList<TemplateDirective>;
  templates: Record<string, TemplateRef<unknown>> = {};
  boundS2AutoUploadMethod: (files: S2File[]) => Observable<HttpEvent<S2File[]>>;
  defaultMaxFileSize: number;

  constructor(
    private appDataService: AppDataService,
    private fileUploadService: FileUploadService,
    private cdr: ChangeDetectorRef
  ) {
    this.boundS2AutoUploadMethod = this.fileUploadService.s2AutoUploadMethod.bind(this.fileUploadService);
    this.defaultMaxFileSize = parseInt(
      this.appDataService.getPropertyValue(AppDataService.UPLOAD_FILE_SIZE) || '10485760'
    );
  }
  ngAfterContentChecked(): void {
    this.cdr.detectChanges();
  }

  ngAfterContentInit(): void {
    this.updateTemplates();
    this.templatesQueryList.changes.subscribe(() => {
      this.updateTemplates();
    });
  }

  public static toggleItemVisibilityInValues(
    editFormValues: EditFormValues,
    formControlName: string,
    show?: boolean
  ): void {
    editFormValues.forEach((group) => {
      group.items.forEach((item) => {
        if (
          (item.type === EditFormItemType.CustomEmpty && item.selector === formControlName) ||
          item.formControlName === formControlName
        ) {
          item.hidden = typeof show === 'boolean' ? !show : !item.hidden;
        }
      });
    });
  }

  public static setItemPropertyInValues(
    editFormValues: EditFormValues,
    formControlName: string,
    propertyName: string,
    propertyValue: unknown
  ): void {
    editFormValues.forEach((group) => {
      const item = group.items.find(
        (item) =>
          (item.type === EditFormItemType.CustomEmpty && item.selector === formControlName) ||
          item.formControlName === formControlName
      ) as unknown as Record<string, unknown>;
      if (item) {
        item[propertyName] = propertyValue;
      }
    });
  }

  public static toggleGroupVisibilityInValues(
    editFormValues: EditFormValues,
    groupKey: EditFormItemsGroup['key'],
    formGroup: FormGroup,
    clearFormControlsOnHide = true,
    show?: boolean
  ): void {
    const targetGroup = editFormValues.find((group) => group.key && group.key === groupKey);
    if (targetGroup) {
      targetGroup.hidden = typeof show === 'boolean' ? !show : !targetGroup.hidden;
      if (clearFormControlsOnHide) {
        targetGroup.items.forEach((item) => {
          const control = formGroup.controls[item.formControlName];
          if (control) {
            control.reset();
          }
        });
      }
    }
  }

  toggleItemVisibility(formControlName: string, show?: boolean): void {
    EditFormComponent.toggleItemVisibilityInValues(this.values, formControlName, show);
  }

  setItemProperty(formControlName: string, propertyName: string, propertyValue: unknown): void {
    EditFormComponent.setItemPropertyInValues(this.values, formControlName, propertyName, propertyValue);
  }

  toggleGroupVisibility(groupKey: EditFormItemsGroup['key'], clearFormControlsOnHide = true, show?: boolean): void {
    EditFormComponent.toggleGroupVisibilityInValues(
      this.values,
      groupKey,
      this.formGroup,
      clearFormControlsOnHide,
      show
    );
  }

  updateTemplates(): void {
    Object.keys(this.templates).forEach((templateKey) => {
      if (!this.templatesQueryList.some((template) => template.sprTemplate === templateKey)) {
        delete this.templates[templateKey];
      }
    });
    this.templatesQueryList.forEach((template) => {
      this.templates[template.sprTemplate] = template.template;
    });
  }

  defaultHandleClickRemoveFile(file: S2File): void {
    if (file.uploaded) {
      this.fileUploadService.deleteFile(s2FileToSprFile(file)).subscribe();
    }
  }

  defaultHandleClickDownloadFile(file: S2File): void {
    this.fileUploadService.downloadFile(s2FileToSprFile(file));
  }
}
