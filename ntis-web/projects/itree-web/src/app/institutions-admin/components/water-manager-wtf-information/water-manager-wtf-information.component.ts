import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-water-manager-wtf-information',
  templateUrl: './water-manager-wtf-information.component.html',
  styleUrls: ['./water-manager-wtf-information.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, RouterModule],
})
export class WaterManagerWtfInformationComponent {
  readonly translationsReference = 'institutionsAdmin.components.waterManagerWtfInformation';
  @Input() wtfCount = 0;
  @Input() orgId: string;

  readonly toFacilities = NtisRoutingConst.INST_WATER_MANAGER_FACILITIES;
}
