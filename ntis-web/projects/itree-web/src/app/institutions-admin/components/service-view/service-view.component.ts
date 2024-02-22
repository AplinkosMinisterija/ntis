import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NtisServiceDetails } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { TYRIMAI } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';

@Component({
  selector: 'app-service-view',
  templateUrl: './service-view.component.html',
  styleUrls: ['./service-view.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class ServiceViewComponent {
  readonly translationsReference = 'institutionsAdmin.components.serviceView';
  readonly TYRIMAI = TYRIMAI;
  @Input() data: NtisServiceDetails;

  constructor(public fileUploadService: FileUploadService) {}
}
