import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { NtrOwnerModel } from '@itree-commons/src/lib/model/api/api';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import {
  NtisBuildingAgreementStatus,
  NtisTypeWastewaterTreatment,
} from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtrOwners, ServedObjectsView } from '@itree-web/src/app/ntis-shared/models/served-objects-view';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';
import { TableModule } from 'primeng/table';
import { NtisBuildingDataRoutingModule } from '../../../ntis-building-data-routing.module';
import { BuildingDataService } from '../../../services/building-data.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { WASTEWATER_FACILITY_VIEW } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-wastewater-facility-building-view',
  templateUrl: './wastewater-facility-building-view.component.html',
  styleUrls: ['./wastewater-facility-building-view.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    NtisBuildingDataRoutingModule,
    NtisSharedModule,
    ReactiveFormsModule,
    TableModule,
  ],
})
export class WastewaterFacilityBuildingViewComponent implements OnInit {
  readonly formTranslationsReference = 'ntisBuildingData.pages.ntrBuildingView';
  readonly ntrBuildingEdit = '/int/building-data/wastewater-facility/ntr-building/edit/';
  readonly formCode = WASTEWATER_FACILITY_VIEW;

  data: ServedObjectsView;
  ntisBuildingAgreementStatus = NtisBuildingAgreementStatus;
  ntisTypeWastewaterTreatment = NtisTypeWastewaterTreatment;
  ntrOwnersData: NtrOwners[] = [];
  showOwnersList: boolean = false;
  ownersList: NtrOwnerModel[] = [];
  showNoOwnersFound: boolean = false;
  isNTISAdmin: boolean;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private location: Location,
    private commonFormServices: CommonFormServices,
    private buildingDataService: BuildingDataService,
    private authService: AuthService
  ) {
    this.data = (
      this.activatedRoute.snapshot.data['wastewaterFacilityBuildingData'] as CommonResult<ServedObjectsView>
    ).data[0];
    this.isNTISAdmin = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.NTIS_ADMIN_ACTIONS);
  }

  ngOnInit(): void {
    if (this.data?.ntr_owner) {
      this.ntrOwnersData = JSON.parse(this.data.ntr_owner) as NtrOwners[];
    }
  }

  goBack(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigateByUrl(
      this.commonFormServices.router.url.split('/' + NtisRoutingConst.NTR_BUILDING)[0],
      navigationExtras
    );
  }

  loadOwnersList(): void {
    this.showNoOwnersFound = false;
    this.buildingDataService.loadNTROwnersList(this.data?.bn_reg_nr).subscribe((res) => {
      res.length > 0 ? (this.ownersList = res) : (this.showNoOwnersFound = true);
    });
  }
}
