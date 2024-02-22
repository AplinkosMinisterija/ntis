import { Component, Input } from '@angular/core';

@Component({
  selector: 'ntis-service-provider-info',
  templateUrl: './service-provider-info.component.html',
  styleUrls: ['./service-provider-info.component.scss'],
})
export class ServiceProviderInfoComponent {
  readonly translationsReference = 'ntisShared.components.serviceProviderInfo';
  @Input() name: string;
  @Input() email: string;
  @Input() phone: string;
}
