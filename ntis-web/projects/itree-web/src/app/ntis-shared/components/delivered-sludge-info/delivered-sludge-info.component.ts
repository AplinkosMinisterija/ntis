import { Component, Input } from '@angular/core';

@Component({
  selector: 'ntis-delivered-sludge-info',
  templateUrl: './delivered-sludge-info.component.html',
  styleUrls: ['./delivered-sludge-info.component.scss'],
})
export class DeliveredSludgeInfoComponent {
  readonly translationsReference = 'ntisShared.components.deliveredSludgeInfo';
  @Input() carRegNumber: string;
  @Input() deliveredQuantity: number;
  @Input() deliveryDate: string;
  @Input() email: string;
  @Input() notes: string;
  @Input() orgCode: string;
  @Input() phone: string;
  @Input() serviceProvider: string;
  @Input() sewageType: string;
  @Input() treatmentFacility: string;
  @Input() usedSludgeQuantity: number;
  @Input() waterManager: string;
}
