import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SewageDeliveryStatus } from '../../enums/classifiers.enums';

@Component({
  selector: 'ntis-sewage-delivery-info',
  templateUrl: './sewage-delivery-info.component.html',
  styleUrls: ['./sewage-delivery-info.component.scss'],
})
export class SewageDeliveryInfoComponent {
  readonly translationsReference = 'ntisShared.components.sewageDeliveryInfo';
  readonly rejected = SewageDeliveryStatus.rejected;
  readonly confirmed = SewageDeliveryStatus.confirmed;
  @Input() deliveryNumber: string;
  @Input() state: string;
  @Input() state_clsf: string;
  @Input() delivRecCreated: string;
  @Input() showConfirmRejectButtons: boolean;
  @Input() rejectionReason: string;
  @Input() deliveredWastewaterDesc: string;
  @Input() acceptedSewageQuantity: string;
  @Output() confirmDelivery = new EventEmitter<void>();
  @Output() rejectDelivery = new EventEmitter<void>();

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  constructor() {}
}
