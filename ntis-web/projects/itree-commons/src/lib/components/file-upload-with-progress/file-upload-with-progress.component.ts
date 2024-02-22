import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { FileProgressEvent, FileUpload } from 'primeng/fileupload';
import { SprFile } from '../../model/api/api';
import { FaIconsService } from '../../services/fa-icons.service';
import { FileUploadService } from '../../services/file-upload.service';
import { AppDataService } from '../../services/app-data.service';

export interface UploadInformation {
  isLoading: boolean;
  progress: number;
}

@Component({
  selector: 'spr-file-upload-with-progress',
  templateUrl: './file-upload-with-progress.component.html',
  styleUrls: ['./file-upload-with-progress.component.scss'],
})
export class FileUploadWithProgressComponent {
  @ViewChild(FileUpload) fileUploader: FileUpload;

  @Input() fileLimit = 10;
  @Input() multiple: boolean = true;
  @Input() disabled: boolean = false;
  @Input() allowedFileSize: number;
  @Input() acceptedFormat = 'image/*,application/pdf, ';
  @Input() chooseIcon = 'pi pi-paperclip';
  @Output() fileUpload = new EventEmitter();
  @Output() fileToDelete = new EventEmitter<SprFile>();
  @Input() uploadedFiles: SprFile[] = [];
  @Input() isFileUploadWithProgress = false;
  @Input() label: string;
  @Input() showSuccess: boolean = true;
  uploadInformation: UploadInformation[] = [];
  filesToUpload: File[];

  constructor(
    public faIconsService: FaIconsService,
    protected commonFormServices: CommonFormServices,
    private fileUploadService: FileUploadService,
    appDataService: AppDataService
  ) {
    this.allowedFileSize = parseInt(appDataService.getPropertyValue(AppDataService.UPLOAD_FILE_SIZE) || '50000000');
  }

  addEmptyFiles(uploadedFiles: SprFile[]): void {
    uploadedFiles.forEach((uploadedFile) => {
      const file = new File([''], uploadedFile.fil_name, {
        type: uploadedFile.fil_content_type,
        lastModified: 4518181,
      });
      this.uploadInformation.push({ isLoading: false, progress: 0 });
      this.fileUploader.files.push(file);
    });
  }

  onUpload(event: { files: File[] }): void {
    this.uploadFile(event.files);
  }

  download(file: SprFile): void {
    this.fileUploadService.downloadFile(file);
  }

  uploadFile(files: File[]): void {
    this.filesToUpload = files;
    // uploadinam tik tuos, kurie dar nepakrauti į backend.
    if (this.uploadedFiles !== null && this.uploadedFiles.length > 0) {
      this.filesToUpload = files.filter((f) => {
        return this.uploadedFiles.every((uploaded) => {
          return f.name !== uploaded.fil_name;
        });
      });
    }
    if (this.filesToUpload.length > 0) {
      this.fileUploader.uploading = true;
      this.fileUploadService.uploadMultiple(this.filesToUpload).subscribe(
        (event) => {
          if (event.type === HttpEventType.UploadProgress) {
            const progress = Math.round((event.loaded * 100) / event.total);
            this.fileUploader.onProgress.emit({ originalEvent: event, progress });
          } else if (event instanceof HttpResponse) {
            if (event.status >= 200 && event.status < 300) {
              this.uploadedFiles.push(...event.body);
              this.fileUpload.emit(this.uploadedFiles);
              if (this.showSuccess) {
                this.commonFormServices.translate
                  .get('common.fileUpload.fileAdded')
                  .subscribe((translation: string) => {
                    this.commonFormServices.appMessages.showSuccess('', translation);
                  });
              }

              this.fileUploader.uploading = false;
              this.fileUploader.onUpload.emit({ originalEvent: event, files: this.filesToUpload });
            } else {
              this.fileUploader.uploading = false;
              this.fileUploader.onError.emit({ files: this.filesToUpload });
            }
          }
        },
        (error) => {
          this.fileUploader.uploading = false;
          this.fileUploader.onError.emit({ files: this.filesToUpload, error: error as ErrorEvent });
          throw error;
        }
      );
    } else {
      this.onUploadFinished();
    }
  }

  onUploadFinished(): void {
    this.fileUploader.files = [];
  }

  onUploadError(): void {
    this.fileUploader.files = [];
  }

  isFileAlreadyUploaded(file: File): boolean {
    let isFileUploaded = false;
    this.uploadedFiles.forEach((uploadedFile: SprFile) => {
      if (file.name === uploadedFile.fil_name) {
        isFileUploaded = true;
      }
    });
    return isFileUploaded;
  }

  onDelete(file: SprFile, fileUploader: FileUpload): void {
    this.deleteLoadingStatusFromArray(this.uploadedFiles.indexOf(file));
    const deletedFile = this.deleteSprFileFromArray(this.uploadedFiles, file.fil_name);
    this.deleteFileFromArray(fileUploader.files, file.fil_name);
    if (deletedFile !== null) {
      this.fileToDelete.emit(deletedFile);
    }
  }

  deleteSprFileFromArray(filesArray: SprFile[], fileName: string): SprFile {
    const fileIndex = filesArray.findIndex((file) => file.fil_name === fileName);
    const result = fileIndex !== -1 ? filesArray[fileIndex] : null;
    if (result) {
      filesArray.splice(fileIndex, 1);
    }
    return result;
  }

  deleteFileFromArray(filesArray: File[], fileName: string): File {
    const fileIndex = filesArray.findIndex((file) => file.name === fileName);
    const result = fileIndex !== -1 ? filesArray[fileIndex] : null;
    if (result) {
      filesArray.splice(fileIndex, 1);
    }
    return result;
  }

  deleteLoadingStatusFromArray(indexOfFile: number): void {
    if (indexOfFile !== -1) {
      this.uploadInformation.splice(indexOfFile, 1);
    }
  }

  progressReport(event: FileProgressEvent): void {
    this.fileUploader.progress = event.progress;
  }
}
