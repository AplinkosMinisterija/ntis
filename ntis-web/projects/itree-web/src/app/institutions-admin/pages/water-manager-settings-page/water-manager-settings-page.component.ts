import { Component, OnInit } from '@angular/core';
import { NtisServiceProviderSettingsInfo } from '@itree-commons/src/lib/model/api/api';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { LegalEntityDetailsComponent } from '../../components/legal-entity-details/legal-entity-details.component';
import { ContactInformationComponent } from '../../components/contact-information/contact-information.component';
import { ServiceProviderInformationComponent } from '../../components/service-provider-information/service-provider-information.component';
import { EmployeesInformationComponent } from '../../components/employees-information/employees-information.component';
import { WaterManagerWtfInformationComponent } from '../../components/water-manager-wtf-information/water-manager-wtf-information.component';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NtisOrgType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';

@Component({
  selector: 'app-water-manager-settings-page',
  templateUrl: './water-manager-settings-page.component.html',
  styleUrls: ['./water-manager-settings-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ContactInformationComponent,
    EmployeesInformationComponent,
    ItreeCommonsModule,
    LegalEntityDetailsComponent,
    ServiceProviderInformationComponent,
    WaterManagerWtfInformationComponent,
    NtisSharedModule,
  ],
})
export class WaterManagerSettingsPageComponent implements OnInit {
  readonly linkText = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SP_WM_SERVICE_REQUESTS_LIST}`;
  readonly ntisOrgType = NtisOrgType;
  public data: NtisServiceProviderSettingsInfo;
  isWaterManager: boolean = true;

  constructor(private institutionsAdminService: InstitutionsAdminService) {}

  ngOnInit(): void {
    this.institutionsAdminService.getWaterManagerProfileInfo().subscribe((result) => {
      this.data = result;
      this.isWaterManager =
        result.orgType === this.ntisOrgType.VANDEN || result.orgType === this.ntisOrgType.PASLAUG_VANDEN;
    });
  }
}
