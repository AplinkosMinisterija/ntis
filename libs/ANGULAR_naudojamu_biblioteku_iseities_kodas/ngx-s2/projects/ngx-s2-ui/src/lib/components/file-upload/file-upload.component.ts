import { BytesToTextPipe } from '../../pipes/bytes-to-text.pipe';
import { CommonModule } from '@angular/common';
import {
  AfterContentInit,
  Component,
  ContentChildren,
  DestroyRef,
  EventEmitter,
  Input,
  Output,
  QueryList,
  TemplateRef,
  ViewEncapsulation,
} from '@angular/core';
import { catchError } from 'rxjs/operators';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { expandFromTop } from '../../animations/expand-from-top';
import { FileTypeToIconPipe } from '../../pipes/file-type-to-icon.pipe';
import { getFileExtension } from '../../utils/get-file-extension';
import { getMimeTypeClass } from '../../utils/get-mime-type-class';
import { getUniqueFileName } from '../../utils/get-unique-file-name';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { IconComponent } from '../icon/icon.component';
import { Observable } from 'rxjs/internal/Observable';
import { ReplacePipe } from '../../pipes/replace.pipe';
import { S2DatePipe } from '../../pipes/date.pipe';
import { S2UiTranslationsService } from '../../services/s2-ui-translations.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { TemplateDirective } from '../../directives/template.directive';

export interface S2File<T = unknown> {
  name: string;
  type: string;
  date?: Date;
  size: number;
  uploaded?: boolean;
  uploadProgress?: number;
  key?: string | number;
  icon?: string;
  extras?: T;
  file?: File;
}

export interface S2FileUploadError {
  files: S2File[];
  error: Error;
}

@Component({
  selector: 's2-file-upload',
  standalone: true,
  imports: [CommonModule, IconComponent, BytesToTextPipe, ReplacePipe, S2DatePipe, FileTypeToIconPipe],
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
  encapsulation: ViewEncapsulation.None,
  // eslint-disable-next-line @angular-eslint/no-host-metadata-property
  host: { class: 's2-file-upload' },
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: FileUploadComponent,
    },
  ],
  animations: [expandFromTop()],
})
export class FileUploadComponent implements ControlValueAccessor, AfterContentInit {
  static readonly S2_TEMPLATE_HEADER = 'header';
  static readonly S2_TEMPLATE_HEADER_CONTENT = 'header-content';
  static readonly S2_TEMPLATE_FILE_INFO = 'file-info';
  readonly S2_TEMPLATE_HEADER = FileUploadComponent.S2_TEMPLATE_HEADER;
  readonly S2_TEMPLATE_HEADER_CONTENT = FileUploadComponent.S2_TEMPLATE_HEADER_CONTENT;
  readonly S2_TEMPLATE_FILE_INFO = FileUploadComponent.S2_TEMPLATE_FILE_INFO;

  @Input() accept: string;
  @Input() autoUploadMethod: (files: S2File[]) => Observable<HttpEvent<S2File[]>>;
  @Input() browseButtonText: string;
  @Input() errorDuration = 5000;
  @Input() inputId: string;
  @Input() maxFiles = 5;
  @Input() maxFileSize: number = 10485760;
  @Input() showDate = true;
  @Input() showDragDropText: boolean | 'responsive' = 'responsive';
  @Input() showHeaderIcon: boolean | 'responsive' = 'responsive';
  @Output() clickDownload = new EventEmitter<S2File>();
  @Output() clickRemove = new EventEmitter<S2File>();
  @Output() autoUploadSuccess = new EventEmitter<S2File[]>();
  @Output() autoUploadError = new EventEmitter<S2FileUploadError>();
  @ContentChildren(TemplateDirective)
  templatesQueryList: QueryList<TemplateDirective>;
  templates: Record<string, TemplateRef<unknown>> = {};

  files: S2File[] = [];
  error: 'maxFiles' | 'maxFileSize' | 'accept';
  errorClearTimeout: NodeJS.Timeout;

  disabled = false;
  onChange: (value: S2File[]) => void;
  onTouched: () => void;

  constructor(public translationsService: S2UiTranslationsService, private destroyRef: DestroyRef) {}

  ngAfterContentInit(): void {
    this.updateTemplates();
    this.templatesQueryList.changes.pipe(takeUntilDestroyed(this.destroyRef)).subscribe(() => {
      this.updateTemplates();
    });
  }

  writeValue(files: S2File[]): void {
    if (Array.isArray(files)) {
      this.files = files;
    } else {
      this.files = [];
    }
  }

  registerOnChange(fn: (value: S2File[]) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  loadFiles(files: File[]): void {
    if (files.length) {
      if (files.length > this.maxFiles - this.files.length) {
        this.showError('maxFiles');
      } else if (typeof this.maxFileSize === 'number' && files.some((file) => file.size > this.maxFileSize)) {
        this.showError('maxFileSize');
      } else if (files.some((file) => !this.isFileTypeAccepted(file))) {
        this.showError('accept');
      } else {
        this.showError(undefined);
        const s2Files = files.map((file) => ({
          name: getUniqueFileName(
            file.name,
            this.files.map((file) => file.name)
          ),
          type: file.type,
          size: file.size,
          uploaded: false,
          date: new Date(),
          file,
        }));
        this.files = [...this.files, ...s2Files];
        this.autoUploadFiles(s2Files);
        this.onChange?.(this.files);
      }
    }
  }

  autoUploadFiles(files: S2File[]): void {
    this.autoUploadMethod?.(files)
      .pipe(
        catchError((error) => {
          files?.forEach((file) => {
            file.uploadProgress = undefined;
          });
          this.onChange?.(this.files);
          this.autoUploadError.emit({ files, error: error as Error });
          throw error;
        })
      )
      .subscribe({
        next: (event) => {
          if (event.type === HttpEventType.UploadProgress) {
            const progress = Math.round((event.loaded * 100) / event.total);
            files?.forEach((file) => {
              file.uploadProgress = progress;
            });
          } else if (event.type === HttpEventType.Response) {
            if (event.status >= 200 && event.status < 300) {
              files?.forEach((file) => {
                file.uploadProgress = undefined;
                file.uploaded = true;
                const receivedFile = event.body?.find((responseFile) => responseFile?.name === file.name);
                if (receivedFile) {
                  Object.assign(file, receivedFile);
                }
              });
              this.onChange?.(this.files);
              this.autoUploadSuccess.emit(files);
            } else {
              throw new Error(`HttpResponse status ${event.status}`);
            }
          }
        },
      });
  }

  handleInputChange(event: Event): void {
    event.preventDefault();
    if (!this.disabled) {
      this.onTouched?.();
      this.loadFiles(Array.from((event.target as HTMLInputElement).files));
    }
  }

  handleDropFile(event: DragEvent): void {
    event.preventDefault();
    if (!this.disabled) {
      this.onTouched?.();
      this.loadFiles(Array.from(event.dataTransfer.files));
    }
  }

  handleDownloadFile(file: S2File): void {
    this.clickDownload.emit(file);
  }

  handleRemoveFile(index: number): void {
    this.clickRemove.emit(this.files[index]);
    this.files.splice(index, 1);
    this.onChange?.(this.files);
  }

  showError(error: typeof this.error): void {
    clearTimeout(this.errorClearTimeout);
    this.error = error;
    if (error) {
      this.errorClearTimeout = setTimeout(() => {
        this.error = undefined;
      }, this.errorDuration);
    }
  }

  isFileTypeAccepted(file: File): boolean {
    if (!this.accept || this.accept === '*') {
      return true;
    }
    const acceptArray = this.accept
      .split(',')
      .map((type) => type.trim())
      .filter((type) => type);
    return acceptArray.some((acceptableType) => {
      if (acceptableType.indexOf('*') !== -1) {
        return getMimeTypeClass(file.type) === getMimeTypeClass(acceptableType);
      } else {
        return (
          file.type === acceptableType || getFileExtension(file.name).toLowerCase() === acceptableType.toLowerCase()
        );
      }
    });
  }

  updateTemplates(): void {
    Object.keys(this.templates).forEach((templateKey) => {
      if (!this.templatesQueryList.some((template) => template.s2Template === templateKey)) {
        delete this.templates[templateKey];
      }
    });
    this.templatesQueryList.forEach((template) => {
      this.templates[template.s2Template] = template.template;
    });
  }
}
