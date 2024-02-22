import { CommonModule, Location } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';
import { TableModule } from 'primeng/table';
import { NtisBuildingDataRoutingModule } from '../../ntis-building-data-routing.module';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { IdKeyValuePair, SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { TypeSewageFacility } from '../../enums/type-sewage-facility';
import { NtisWtfAgreements, WtfServedObject } from '../../models/browse-page';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { BuildingDataService } from '../../services/building-data.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NTIS_AGREEMENTS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { ServedObjectsView } from '@itree-web/src/app/ntis-shared/models/served-objects-view';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-wastewater-obj-agreements-confirmation',
  templateUrl: './wastewater-obj-agreements-confirmation.component.html',
  styleUrls: ['./wastewater-obj-agreements-confirmation.component.scss'],
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
    DialogModule,
  ],
})
export class WastewaterObjAgreementsConfirmationComponent implements OnInit {
  readonly treatmentFacilityReview = 'ntisBuildingData.pages.sewageTreatmentFacilityReview';
  readonly wfAgrrementsList = 'ntisBuildingData.pages.wfAgrrementsList';
  readonly formTranslationsReference = 'ntisBuildingData.pages.sewageTreatmentFacilityReview';
  agreementData: NtisWtfAgreements;
  agreementDataResponse: NtisWtfAgreements;
  data: ServedObjectsView;

  readonly typeSewageFacilityEnum = TypeSewageFacility;
  servedObjects: WtfServedObject[] = [];
  newServedObjects: WtfServedObject[] = [];
  diffServedObjects: WtfServedObject[] = [];
  files: SprFile;
  showConfirmButton: boolean;
  showUpdateButton: boolean;
  showRejectButton: boolean;
  showCancelButton: boolean;
  showFullInfo: boolean;
  form: FormGroup = new FormGroup({
    cancelReason: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(1000)])),
  });
  actionDialog: boolean;

  // ntisBuildingAgreementStatus = NtisBuildingAgreementStatus;
  // ntrOwnersData: NtrOwners[] = [];
  // showOwnersList: boolean = false;
  // file: SprFile;

  showObjects: boolean;
  constructor(
    protected activatedRoute: ActivatedRoute,
    private fileUploadService: FileUploadService,
    private commonFormServices: CommonFormServices,
    private location: Location,
    private router: Router,
    private authService: AuthService,
    private buildingDataService: BuildingDataService,
    public faIconsService: FaIconsService
  ) {
    this.agreementDataResponse = (
      this.activatedRoute.snapshot.data['wastewaterAgreementConfirmationResolver'] as CommonResult<NtisWtfAgreements>
    )?.data[0];
    this.data = (
      this.activatedRoute.snapshot.data['wastewaterFacilityBuildingData'] as CommonResult<ServedObjectsView>
    ).data[0];
    this.showConfirmButton = this.authService.isFormActionEnabled(NTIS_AGREEMENTS_LIST, ActionsEnum.ACTIONS_CONFIRM);
    this.showCancelButton = this.authService.isFormActionEnabled(NTIS_AGREEMENTS_LIST, ActionsEnum.ACTIONS_CANCEL);
    this.showUpdateButton = this.authService.isFormActionEnabled(NTIS_AGREEMENTS_LIST, ActionsEnum.ACTIONS_UPDATE);
    this.showRejectButton = this.authService.isFormActionEnabled(NTIS_AGREEMENTS_LIST, ActionsEnum.ACTIONS_REJECT);
  }

  ngOnInit(): void {
    if (this.agreementDataResponse?.file_ot) {
      this.files = JSON.parse(this.agreementDataResponse?.file_ot) as SprFile;
    }

    // this.data = (
    //   JSON.parse(this.agreementDataResponse.fua_wtf_old_info_json) as CommonResult<NtisWtfAgreements>
    // ).data[0];
    // if (this.agreementDataResponse?.fua_state !== 'SUBMITTED') {
    //   this.agreementData = (
    //     JSON.parse(this.agreementDataResponse.fua_wtf_new_info_json) as CommonResult<NtisWtfAgreements>
    //   ).data[0];
    // } else {
    //   this.agreementData = this.agreementDataResponse;
    // }
  }

  downloadFile(file: SprFile): void {
    if (file) {
      this.buildingDataService.downloadFile(this.agreementDataResponse.fua_id.toString(), file);
    }
  }

  goBack(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.router.navigate(
      [`/${RoutingConst.INTERNAL}/${NtisRoutingConst.BUILDING_DATA}/${NtisRoutingConst.WASTEWATER_AGRREMENTS}`],
      navigationExtras
    );
  }

  confirm(): void {
    this.buildingDataService.confirmWtfAgrrementObj(this.agreementDataResponse.fua_id.toString()).subscribe(() => {
      this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
        this.goBack();
      });
    });
  }

  cancel(): void {
    const result = {} as IdKeyValuePair;
    result.id = this.agreementDataResponse.fua_id.toString();
    result.value = this.form.controls.cancelReason.value as string;
    if (this.showCancelButton) {
      this.buildingDataService.cancelWtfObjAgrrement(result).subscribe(() => {
        this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.goBack();
        });
      });
    } else {
      this.buildingDataService.rejectWtfOBjAgrrement(result).subscribe(() => {
        this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.goBack();
        });
      });
    }
  }

  navigateToEdit(): void {
    void this.router.navigate([
      `/${RoutingConst.INTERNAL}/${NtisRoutingConst.BUILDING_DATA}/${NtisRoutingConst.WASTEWATER_AGRREMENTS}/${NtisRoutingConst.NTR_BUILDING}/${RoutingConst.EDIT}/`,
      this.agreementDataResponse.fua_so_id.toString(),
    ]);
  }

  onCancel(): void {
    this.actionDialog = true;
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.actionDialog = false;
  }
}
