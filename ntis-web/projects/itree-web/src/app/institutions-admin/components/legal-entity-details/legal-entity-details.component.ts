import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-legal-entity-details',
  templateUrl: './legal-entity-details.component.html',
  styleUrls: ['./legal-entity-details.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class LegalEntityDetailsComponent {
  @Input() name: string;
  @Input() code: string;
  @Input() address: string;
  @Input() manager: string;

  readonly translationsReference = 'institutionsAdmin.components.legalEntityDetails';
}
