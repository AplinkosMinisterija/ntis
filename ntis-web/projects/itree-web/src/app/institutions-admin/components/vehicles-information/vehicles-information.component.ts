import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonFormServices } from '@itree/ngx-s2-commons';

@Component({
  selector: 'app-vehicles-information',
  templateUrl: './vehicles-information.component.html',
  styleUrls: ['./vehicles-information.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, RouterModule],
})
export class VehiclesInformationComponent {
  readonly translationsReference = 'institutionsAdmin.components.vehiclesInformation';
  @Input() vehiclesCount = 0;
  constructor(private commonFormServices: CommonFormServices) {}

  navigateToCarsList(): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, NtisRoutingConst.NTIS_CARS_LIST]);
  }
}
