import { Component, OnInit } from '@angular/core';
import { NtisServiceProviderSettingsInfo } from '@itree-commons/src/lib/model/api/api';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { LegalEntityDetailsComponent } from '../../components/legal-entity-details/legal-entity-details.component';
import { ContactInformationComponent } from '../../components/contact-information/contact-information.component';
import { ServiceProviderInformationComponent } from '../../components/service-provider-information/service-provider-information.component';
import { EmployeesInformationComponent } from '../../components/employees-information/employees-information.component';
import { VehiclesInformationComponent } from '../../components/vehicles-information/vehicles-information.component';
import { MyFavWaterManagersComponent } from '../../components/my-fav-water-managers/my-fav-water-managers.component';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { NtisOrgType, ServiceItemType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import {
  NTIS_SERVICE_MANAGEMENT,
  NTIS_SERVICE_PROVIDER_SETTINGS,
} from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-service-provider-settings-page',
  templateUrl: './service-provider-settings-page.component.html',
  styleUrls: ['./service-provider-settings-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ContactInformationComponent,
    EmployeesInformationComponent,
    ItreeCommonsModule,
    LegalEntityDetailsComponent,
    MyFavWaterManagersComponent,
    NtisSharedModule,
    ServiceProviderInformationComponent,
    VehiclesInformationComponent,
  ],
})
export class ServiceProviderSettingsPageComponent implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.serviceProviderSettings';
  readonly linkText = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SP_WM_SERVICE_REQUESTS_LIST}`;
  readonly ntisOrgType = NtisOrgType;
  readonly ServiceItemType = ServiceItemType;
  public data: NtisServiceProviderSettingsInfo;
  showNoCarMessage: boolean = false;
  showSewageRemovalBlocks: boolean = false;
  activeServices: string[] = [];
  userCanUpdate: boolean = true;
  isServiceProvider: boolean = true;
  showSrvProviderInfoBlock: boolean = false;

  constructor(private institutionsAdminService: InstitutionsAdminService, private authService: AuthService) {
    this.userCanUpdate = this.authService.isFormActionEnabled(
      NTIS_SERVICE_PROVIDER_SETTINGS,
      ActionsEnum.ACTIONS_UPDATE
    );
    this.showSrvProviderInfoBlock = this.authService.isFormActionEnabled(
      NTIS_SERVICE_MANAGEMENT,
      ActionsEnum.ACTIONS_READ
    );
  }

  ngOnInit(): void {
    this.institutionsAdminService.getServiceProviderProfileInfo().subscribe((result) => {
      this.data = result;
      this.isServiceProvider =
        result.orgType === this.ntisOrgType.PASLAUG || result.orgType === this.ntisOrgType.PASLAUG_VANDEN;
      this.showSewageRemovalBlocks = !!this.data.services?.filter(
        (srv) => srv.service_type === ServiceItemType.sewageRemoval
      )[0];
      this.showNoCarMessage = this.data.vehiclesCount === 0 && this.showSewageRemovalBlocks;
      this.data.services
        ?.filter((srv) => srv.is_active === DB_BOOLEAN_TRUE)
        .forEach((srv) => {
          this.activeServices.push(srv.service_type);
        });
    });
  }
}
