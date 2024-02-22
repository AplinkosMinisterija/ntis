import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MONTAVIMAS, NTIS_SRV_ITEM_TYPE, VALYMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { RouterModule } from '@angular/router';
import { CommonFormServices } from '@itree/ngx-s2-commons';

@Component({
  selector: 'app-service-provider-information',
  templateUrl: './service-provider-information.component.html',
  styleUrls: ['./service-provider-information.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, RouterModule],
})
export class ServiceProviderInformationComponent implements OnInit, OnChanges {
  readonly translationsReference = 'institutionsAdmin.components.serviceProviderInformation';
  readonly RoutingConst = RoutingConst;
  readonly NtisRoutingConst = NtisRoutingConst;
  readonly MONTAVIMAS = MONTAVIMAS;
  @Input() registeredFrom: string;
  @Input() deregisteredFrom: string;
  @Input() registeredServices: string[] = [];
  @Input() deregisteredReason: string;
  @Input() isWaterManager = false;
  services: {
    code: string;
    name: string;
    registered: boolean;
  }[] = [];

  constructor(private commonService: CommonService, private commonFormServices: CommonFormServices) {}

  ngOnInit(): void {
    this.commonService.getClsf(NTIS_SRV_ITEM_TYPE).subscribe((result) => {
      this.services = result
        .filter((clsf) => clsf.key !== VALYMAS)
        .map((item) => ({
          code: item.key,
          name: item.display,
          registered: this.registeredServices?.indexOf(item.key) !== -1 || false,
        }));
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const currVal = (changes.registeredServices?.currentValue as string[]) || [];
    this.services.forEach((service) => {
      service.registered = currVal.indexOf(service.code) !== -1;
    });
  }

  navigateToServiceManagement(): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      NtisRoutingConst.INST_SERVICE_MANAGEMENT,
    ]);
  }
}
