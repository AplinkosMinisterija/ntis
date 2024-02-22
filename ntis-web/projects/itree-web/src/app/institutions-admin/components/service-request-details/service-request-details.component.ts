import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-service-request-details',
  templateUrl: './service-request-details.component.html',
  styleUrls: ['./service-request-details.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class ServiceRequestDetailsComponent {
  readonly translationsReference = 'institutionsAdmin.components.serviceRequestDetails';
  @Input() reqNumber: string;
  @Input() registrationDate: string;
  @Input() reqStatus: string;
  @Input() reqStatusDate: string;
  @Input() reqRemovalReason: string;
  @Input() showConfirmRejectButtons: boolean;
  @Output() cancelRequest = new EventEmitter<void>();
  @Output() confirmRequest = new EventEmitter<void>();
  @Output() rejectRequest: EventEmitter<void> = new EventEmitter<void>();

  onReject(): void {
    this.rejectRequest.emit();
  }

  onCancel(): void {
    this.cancelRequest.emit();
  }

  onConfirm(): void {
    this.confirmRequest.emit();
  }
}
