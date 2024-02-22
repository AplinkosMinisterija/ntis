import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';
import { TableModule } from 'primeng/table';
import { TypeSewageFacility } from '../../../enums/type-sewage-facility';
import { NtisWastewaterFacilityView, WtfServedObject } from '../../../models/browse-page';
import { NtisBuildingDataRoutingModule } from '../../../ntis-building-data-routing.module';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-wastewater-facility-view',
  templateUrl: './wastewater-facility-view.html',
  styleUrls: ['./wastewater-facility-view.scss'],
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
export class WastewaterFacilityViewComponent implements OnInit {
  readonly treatmentFacilityReview = 'ntisBuildingData.pages.sewageTreatmentFacilityReview';
  data: NtisWastewaterFacilityView;
  readonly typeSewageFacilityEnum = TypeSewageFacility;
  servedObjects: WtfServedObject[] = [];
  files: SprFile[] = [];
  constructor(
    protected activatedRoute: ActivatedRoute,
    private fileUploadService: FileUploadService,
    private location: Location,
    private router: Router
  ) {
    this.data = (
      this.activatedRoute.snapshot.data['wastewaterFacilityViewData'] as CommonResult<NtisWastewaterFacilityView>
    ).data[0];
  }

  ngOnInit(): void {
    if (this.data?.wtf_served_objects_json) {
      this.servedObjects = JSON.parse(this.data.wtf_served_objects_json) as WtfServedObject[];
    }
    if (this.data?.wtf_files_json) {
      this.files = JSON.parse(this.data.wtf_files_json) as SprFile[];
    }
  }

  downloadFile(file: SprFile): void {
    if (file) {
      this.fileUploadService.downloadFile(file);
    }
  }

  goBack(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    let currentUrl = this.router.url;
    if (currentUrl.includes(NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD)) {
      currentUrl = currentUrl.substring(
        0,
        currentUrl.indexOf(`/${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}`)
      );
    } else if (currentUrl.includes(NtisRoutingConst.BUILDING_DATA) && currentUrl.includes(RoutingConst.VIEW)) {
      currentUrl = currentUrl.substring(0, currentUrl.indexOf(`/${RoutingConst.VIEW}`));
    }
    void this.router.navigateByUrl(currentUrl, navigationExtras);
  }

  goEdit(): void {
    const currentUrl: string = this.router.url;
    void this.router.navigate([currentUrl, RoutingConst.EDIT, this.data.wtf_id], {
      queryParams: {
        returnUrl: currentUrl,
        returnTo: 'view',
      },
    });
  }

  openServedObject(row: WtfServedObject): void {
    if (row.fo_so_id) {
      void this.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.BUILDING_DATA,
        NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        NtisRoutingConst.NTR_BUILDING,
        RoutingConst.VIEW,
        row.so_id,
      ]);
    }
  }
}
