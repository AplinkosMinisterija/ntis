import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ServiceRequestItem } from '../../models/browse-pages';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-service-request-content',
  templateUrl: './service-request-content.component.html',
  styleUrls: ['./service-request-content.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class ServiceRequestContentComponent {
  readonly translationsReference = 'institutionsAdmin.components.serviceRequestContent';
  selectedServices: string[] = [];
  @Input() isServiceProvider: boolean;
  @Input() isEditable: boolean;
  @Input() applicant: string;
  @Input() organization: string;
  @Input() registeredServices: ServiceRequestItem[] = [];
  @Input() requestFiles: SprFile[] = [];
  @Output() setItemsEvent = new EventEmitter<ServiceRequestItem[]>();
  @Output() setFilesEvent = new EventEmitter<SprFile[]>();

  uploadedFiles: SprFile[] = [];

  constructor(private fileUploadService: FileUploadService) {}

  onCheckboxChange(service: ServiceRequestItem): void {
    this.registeredServices.map((item) => (item.code === service.code ? (item.registered = !item.registered) : null));
    this.setItemsEvent.emit(this.registeredServices.filter((item) => item.registered));
  }

  onFileUpload(): void {
    this.setFilesEvent.emit(this.uploadedFiles);
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_name) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
    this.onFileUpload();
  }

  download(file: SprFile): void {
    this.fileUploadService.downloadFile(file);
  }
}
