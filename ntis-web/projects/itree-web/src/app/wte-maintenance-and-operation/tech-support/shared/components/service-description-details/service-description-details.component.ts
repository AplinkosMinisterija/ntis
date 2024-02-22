import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-service-description-details',
  templateUrl: './service-description-details.component.html',
  styleUrls: ['./service-description-details.component.scss'],
})
export class ServiceDescriptionDetailsComponent {
  @Input() name: string;
  @Input() org_name: string;
  @Input() email: string;
  @Input() phone: string;
  @Input() price_from: string;
  @Input() price_to: string;
  @Input() completion_time_in_days: string;
  @Input() description: string;
  @Input() hideAccordion: boolean = true;
  @Input() csId: number;

  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.client.components.serviceDescriptionDetails';
}
