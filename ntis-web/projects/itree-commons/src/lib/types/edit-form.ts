import { HttpEvent } from '@angular/common/http';
import { S2File, S2FileUploadError } from '@itree/ngx-s2-ui';
import { Observable } from 'rxjs';
import { EditFormItemType } from '../enums/edit-form.enums';
import { SelectOptionType } from './select';

export interface EditFormItemBase {
  type: EditFormItemType;
  label: string;
  formControlName: string;
  errorDefs?: Record<string, string>;
  translateLabel?: boolean;
  isRequired?: boolean;
  hidden?: boolean;
  tooltipText?: string;
  infoText?: string;
  onFocusOut?: (event: FocusEvent | Event) => void;
}

export interface CustomEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Custom;
}

export interface CustomEmptyEditFormItem extends EditFormItemBase {
  type: EditFormItemType.CustomEmpty;
  selector: string;
}

export interface DateEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Date;
  minDate?: Date;
  maxDate?: Date;
  defaultDateTo?: Date;
  showTime?: boolean;
  showIcon?: boolean;
  timeOnly?: boolean;
  showButtonBar?: boolean;
}

export interface SelectEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Select;
  options?: unknown[];
  optionLabel?: string;
  optionValue?: string;
  optionType?: SelectOptionType;
  filter?: boolean;
  groupLabel?: string;
  groupListKey?: string;
  startFilteringLength?: number;
  delaySearch?: number;
  classifierCode?: string;
  customClassifier?: boolean;
  classifierFilterParams?: Record<string, string>;
  readonly?: boolean;
  labelTemplate?: string;
  groupLabelTemplate?: string;
  autoComplete?: boolean;
  showClear?: boolean;
}

export interface NumberEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Number;
  min?: number;
  max?: number;
  useGrouping?: boolean;
  format?: boolean;
  maxLength?: number;
  minFractionDigits?: number;
  maxFractionDigits?: number;
}

export interface TextEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Text;
  maxLength?: number;
}

export interface TextAreaEditFormItem extends EditFormItemBase {
  type: EditFormItemType.TextArea;
  maxLength?: number;
  rows?: number;
}

export interface PasswordEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Password;
  maxLength?: number;
}

export interface FilesEditFormItem extends EditFormItemBase {
  type: EditFormItemType.Files;
  accept?: string;
  autoUploadMethod?: (files: S2File[]) => Observable<HttpEvent<S2File[]>>;
  browseButtonText?: string;
  errorDuration?: number;
  maxFiles?: number;
  maxFileSize?: number;
  showDate?: boolean;
  showDragDropText?: boolean;
  showHeaderIcon?: boolean;
  onClickDownload?: (file: S2File) => void;
  onClickRemove?: (file: S2File) => void;
  onAutoUploadSuccess?: (files: S2File[]) => void;
  onAutoUploadError?: (error: S2FileUploadError) => void;
}

export type EditFormItem =
  | CustomEditFormItem
  | CustomEmptyEditFormItem
  | DateEditFormItem
  | NumberEditFormItem
  | TextEditFormItem
  | TextAreaEditFormItem
  | PasswordEditFormItem
  | SelectEditFormItem
  | FilesEditFormItem;

export interface EditFormItemsGroup {
  legend?: string;
  translateLegend?: boolean;
  key?: string | number;
  hidden?: boolean;
  items: EditFormItem[];
}

export type EditFormValues = EditFormItemsGroup[];
