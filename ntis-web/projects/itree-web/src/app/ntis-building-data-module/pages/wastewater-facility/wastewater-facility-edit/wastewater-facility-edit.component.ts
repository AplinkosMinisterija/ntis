import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, Location } from '@angular/common';
import { Geometry, LineString } from 'ol/geom';
import {
  AddressSearchResponse,
  ForeignKeyParams,
  NtisFacilityModel,
  NtisServedObjectsDAO,
  NtisWastewaterTreatmentFaci,
  SprFile,
  SprListIdKeyValue,
} from '@itree-commons/src/lib/model/api/api';

import { ActivatedRoute, NavigationExtras, Params, Router } from '@angular/router';
import { AN_INTEGER, LKS_94_COORDINATES_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { BuildingDataService } from '../../../services/building-data.service';
import { CalendarModule } from 'primeng/calendar';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { ConfirmationService } from 'primeng/api';
import { Coordinate } from 'ol/coordinate';
import { DialogModule } from 'primeng/dialog';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { getSearchMapMenu } from '@itree-web/src/app/standalone/map/utils/getDefaultMapMenus';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MapComponent } from 'projects/itree-web/src/app/standalone/map/components/map/map.component';
import { MapMenu } from '@itree-web/src/app/standalone/map/types/map-menu';
import { NTIS_MUNICIPALITIES, NTIS_WTF_TYPE } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NtisBuildingDataRoutingModule } from '../../../ntis-building-data-routing.module';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { OTHER, RELEASE_INTO_THE_SOIL, RELEASE_RESERVOIR } from '../../../constants/wastewater-facility.const';
import { ProjectionLikeCode } from 'projects/itree-web/src/app/standalone/map/enums/projection-like-code';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { SearchByCoordinatesEvent } from '@itree-web/src/app/standalone/map/types/search-by-coordinates-event';
import { SelectOption } from 'projects/itree-web/src/app/ntis-shared/models/wtf-search';
import { TableModule } from 'primeng/table';
import { takeUntil } from 'rxjs';
import { TypeSewageFacility } from '../../../enums/type-sewage-facility';
import * as proj from 'ol/proj';
import Feature from 'ol/Feature';
import Icon from 'ol/style/Icon';
import MapBrowserEvent from 'ol/MapBrowserEvent';
import Point from 'ol/geom/Point';
import proj4 from 'proj4';
import Style from 'ol/style/Style';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { NtisWtfType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { TooltipModule } from 'primeng/tooltip';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NTIS_WASTEWATER_FACILITY_EDIT } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

enum InputNames {
  facilityAddress = 'facilityAddress',
  servedObject = 'servedObject',
  dischargeWastewatert = 'dischargeWastewatert',
}

enum IconNames {
  pinRed = 'pin_red',
  pinBlue = 'pin_blue',
  pinGreen = 'pin_green',
}

interface MapResizeParams {
  height: number;
  width: number;
  marginBottom: number;
  elementReference: string;
  lengthUnit: 'px' | 'rem';
}

@Component({
  selector: 'app-wastewater-facility-edit',
  templateUrl: './wastewater-facility-edit.component.html',
  styleUrls: ['./wastewater-facility-edit.component.scss'],
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
    MapComponent,
    DialogModule,
    TooltipModule,
  ],
})
export class WastewaterFacilityEditComponent
  extends DeprecatedBaseEditForm<NtisWastewaterTreatmentFaci>
  implements AfterViewInit, OnDestroy
{
  readonly ntisBuildingDataReference = 'ntisBuildingData.pages.wastewaterFacilityEdit';
  readonly commonActionReference = 'common.action';
  readonly RELEASE_INTO_THE_SOIL = RELEASE_INTO_THE_SOIL;
  readonly RELEASE_RESERVOIR = RELEASE_RESERVOIR;
  readonly OTHER = OTHER;
  readonly typeSewageFacilityEnum = TypeSewageFacility;
  readonly inputNamesEnum = InputNames;
  readonly iconNamesEnum = IconNames;
  readonly LKS_94_COORDINATES_PATTERN = LKS_94_COORDINATES_PATTERN;
  readonly today: Date = new Date();
  readonly minDate = new Date(1900, 0, 1);
  readonly ntisWtfType = NtisWtfType;

  resizeObserver: ResizeObserver;
  mapResizeParams: MapResizeParams = {
    height: 350,
    width: 1100,
    marginBottom: 50,
    elementReference: '#content-layout',
    lengthUnit: 'px',
  };
  facilitySearchResults: AddressSearchResponse[] = [];
  servedObjectSearchResults: AddressSearchResponse[] = [];
  showDetailedSearchForm: boolean = false;
  selectedFacilityAddress: AddressSearchResponse;
  selectedServedObjectAddress: AddressSearchResponse[] = [];
  selectedDischargeAddress: AddressSearchResponse;
  mapHeadertext: string = '';
  dischargeAsFacility: boolean = true;
  facilityAsServedObject: boolean = false;
  dischargeCoordinates: string = null;
  facilityModels: NtisFacilityModel[] = [];
  municipalities: SelectOption[] = [];
  uploadedFiles: SprFile[] = [];
  vectorSourceObj = new VectorSource();
  showAddressDialog: boolean = false;
  addressList: AddressSearchResponse[] = [];
  contentWidth: number;
  vectorLayer = new VectorLayer({
    source: this.vectorSourceObj,
  });
  mapMenuItems: MapMenu[] = [getSearchMapMenu()];
  focusMap: boolean = false;
  facilityDetailedSearchForm: boolean = false;
  dischargeDetailedSearchForm: boolean = false;
  focusedMapName: InputNames = null;
  installationDate: Date;
  showServObjAddressBlock: boolean = false;
  showNtrMessage: boolean = false;
  showMessageNtrNotFound: boolean = false;
  showConfirmSameAddressMessage: boolean = false;
  returnUrl: string = null;
  returnTo: string = null;
  textFromOtherPage: boolean = false;
  showFacilityExistsDialog: boolean = false;
  existingFaciData: NtisWastewaterTreatmentFaci;
  wtfTypeSelection: SprListIdKeyValue[] = [];
  showFacilityWithIdentificationExists: boolean = false;
  existingWtfId: number;
  isIntsOwner: boolean = false;
  correctAddressConfirmed: boolean = false;

  @ViewChild(MapComponent, { static: false }) mapComponent: MapComponent;
  @ViewChild('mapReference') mapReference: ElementRef<HTMLDivElement>;

  constructor(
    protected override formBuilder: FormBuilder,
    private commonService: CommonService,
    protected override commonFormServices: CommonFormServices,
    private buildingDataService: BuildingDataService,
    private location: Location,
    private datePipe: S2DatePipe,
    private authService: AuthService,
    protected override activatedRoute?: ActivatedRoute,
    private confirmationService?: ConfirmationService,
    private router?: Router,
    private cdr?: ChangeDetectorRef
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
    this.isIntsOwner = this.authService.isFormActionEnabled(
      NTIS_WASTEWATER_FACILITY_EDIT,
      ActionsEnum.INTS_OWNER_ACTIONS
    );
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      wtf_id: new FormControl(null),
      wtf_type: new FormControl(null, Validators.required),
      wtf_technical_passport_id: new FormControl({ value: null, disabled: true }),
      wtf_manufacturer: new FormControl({ value: null, disabled: false }),
      wtf_model: new FormControl({ value: null, disabled: false }),
      wtf_distance: new FormControl({ value: null, disabled: false }, [
        Validators.pattern(AN_INTEGER),
        Validators.max(1000),
      ]),
      wtf_installation_date: new FormControl({ value: null, disabled: false }),
      wtf_checkout_date: new FormControl({ value: null, disabled: false }),
      wtf_capacity: new FormControl({ value: null, disabled: false }),
      wtf_manufacturer_description: new FormControl({ value: null, disabled: false }, Validators.maxLength(80)),
      wtf_identification_number: new FormControl({ value: null }, Validators.maxLength(80)),
      wtf_ad_id: new FormControl({ value: null, disabled: false }),
      wtf_discharge_type: new FormControl(RELEASE_INTO_THE_SOIL),
      wtf_facility_latitude: new FormControl(null, Validators.required),
      wtf_facility_longitude: new FormControl(null, Validators.required),
      wtf_discharge_latitude: new FormControl(null),
      wtf_discharge_longitude: new FormControl(null),
      so_address: new FormControl(null, Validators.required),
      facilitySearch: new FormControl(null),
      servedObjectSearch: new FormControl(null),
      dwCoordinates: new FormControl(null),
      fam_pop_equivalent: new FormControl({ value: null, disabled: true }),
      fam_chds: new FormControl({ value: null, disabled: true }),
      fam_bds: new FormControl({ value: null, disabled: true }),
      fam_float_material: new FormControl({ value: null, disabled: true }),
      fam_phosphor: new FormControl({ value: null, disabled: true }),
      fam_nitrogen: new FormControl({ value: null, disabled: true }),
    });
    this.form.controls.wtf_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.form.controls.so_address.clearValidators();
      this.form.controls.so_address.updateValueAndValidity();
      this.form.controls.wtf_technical_passport_id.setValue(null);
      this.form.controls.wtf_manufacturer_description.setValue(null);
      this.form.controls.wtf_manufacturer.setValue(null);
      this.form.controls.wtf_model.setValue(null);
      this.form.controls.wtf_identification_number.setValue(null);
      this.form.controls.wtf_identification_number.clearValidators();
      this.form.controls.wtf_identification_number.updateValueAndValidity();
      if (!this.correctAddressConfirmed) {
        this.form.controls.so_address.addValidators(Validators.required);
        this.form.controls.so_address.updateValueAndValidity();
      }
      if (value === NtisWtfType.RESERVOIR || value === NtisWtfType.PORTABLE_RESERVOIR) {
        this.form.controls.wtf_discharge_latitude.clearValidators();
        this.form.controls.wtf_discharge_longitude.clearValidators();
        this.form.controls.dwCoordinates.clearValidators();
        this.form.controls.dwCoordinates.setValue(null);
        this.form.controls.wtf_discharge_latitude.setValue(null);
        this.form.controls.wtf_discharge_longitude.setValue(null);
        this.form.controls.wtf_discharge_type.setValue(null);
        this.form.controls.dwCoordinates.updateValueAndValidity();
        this.form.controls.wtf_discharge_latitude.updateValueAndValidity();
        this.form.controls.wtf_discharge_longitude.updateValueAndValidity();
        this.form.controls.wtf_discharge_type.updateValueAndValidity();
        this.dischargeAsFacility = false;
        if (value === NtisWtfType.PORTABLE_RESERVOIR) {
          this.form.controls.wtf_manufacturer.setValue(null);
          this.form.controls.so_address.setValue(null);
          this.form.controls.so_address.clearValidators();
          this.form.controls.so_address.updateValueAndValidity();
          this.form.controls.wtf_identification_number.addValidators(Validators.required);
          this.form.controls.wtf_identification_number.addValidators(Validators.maxLength(80));
          this.form.controls.wtf_identification_number.updateValueAndValidity();
        }
      } else {
        this.dischargeAsFacility = true;
        this.setDischargeAddress(this.selectedFacilityAddress);
        if (!this.form.controls.wtf_discharge_type.value) {
          this.form.controls.wtf_discharge_type.setValue(RELEASE_INTO_THE_SOIL);
          this.form.controls.wtf_discharge_type.updateValueAndValidity();
        }
        this.form.controls.wtf_discharge_latitude.addValidators(Validators.required);
        this.form.controls.wtf_discharge_longitude.addValidators(Validators.required);
        this.form.controls.wtf_discharge_latitude.updateValueAndValidity();
        this.form.controls.wtf_discharge_longitude.updateValueAndValidity();
      }
    });
    this.form.controls.wtf_ad_id.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      if (
        value &&
        this.correctAddressConfirmed &&
        this.form.controls.facilitySearch.value !== null &&
        this.data?.servedObjects === null
      ) {
        this.correctAddressConfirmed = false;
      }
      this.form.controls.so_address.setValue(null);
      this.form.controls.so_address.updateValueAndValidity();
      this.facilityAsServedObject = false;
      this.selectedServedObjectAddress = [];
    });
    this.form.controls.wtf_model.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      const model = this.facilityModels?.find((model) => model.rfc_code === value);
      this.uploadedFiles = [];
      if (this.facilityModels.length > 0) {
        this.form.controls.wtf_technical_passport_id.setValue(null);
        this.form.controls.fam_bds.setValue(null);
        this.form.controls.fam_chds.setValue(null);
        this.form.controls.fam_float_material.setValue(null);
        this.form.controls.fam_nitrogen.setValue(null);
        this.form.controls.fam_phosphor.setValue(null);
        this.form.controls.fam_pop_equivalent.setValue(null);
      }
      if (model) {
        this.buildingDataService.loadAdditionalDetails(model.rfc_id).subscribe((modelDetails) => {
          if (modelDetails) {
            this.form.controls.wtf_technical_passport_id.setValue(modelDetails.fam_tech_pass);
            if (modelDetails.passport) {
              this.uploadedFiles.push(modelDetails.passport);
            }
            this.form.controls.fam_bds.setValue(modelDetails.fam_bds);
            this.form.controls.fam_chds.setValue(modelDetails.fam_chds);
            this.form.controls.fam_float_material.setValue(modelDetails.fam_float_material);
            this.form.controls.fam_nitrogen.setValue(modelDetails.fam_nitrogen);
            this.form.controls.fam_phosphor.setValue(modelDetails.fam_phosphor);
            this.form.controls.fam_pop_equivalent.setValue(modelDetails.fam_pop_equivalent);
          }
        });
      }
    });
    this.getDataOnLoad();
    this.subscribeFormControls();

    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      if (param['returnUrl']) {
        this.returnUrl = param['returnUrl'];
      }
      if (param['address'] && param['address']?.length > 4) {
        const searchData = {
          filterValue: param['address'],
          filterValueModified4Search: null,
          recordCount: 100,
        } as ForeignKeyParams;
        this.searchFacilityAddress(searchData, false);
      }
      if (param['type']) {
        this.form.controls.wtf_type.setValue(param['type']);
      }
      if (param['distance']) {
        this.form.controls.wtf_distance.setValue(Number(param['distance']));
      }
      if (param['returnTo']) {
        this.returnTo = param['returnTo'];
      }
    });
  }
  protected doSave(value: NtisWastewaterTreatmentFaci): void {
    if (value.wtf_type === this.ntisWtfType.PORTABLE_RESERVOIR) {
      this.buildingDataService
        .checkIfIdentificationNumberExists(value?.wtf_identification_number)
        .subscribe((wtfId) => {
          if (wtfId && value.wtf_id != wtfId) {
            this.showFacilityWithIdentificationExists = true;
            this.existingWtfId = wtfId;
          } else {
            if (value.wtf_ad_id && !this.data?.wtf_id) {
              this.buildingDataService
                .verifyAddress(value.wtf_ad_id, this.form.controls.wtf_type.value as string)
                .subscribe((response) => {
                  if (response) {
                    this.existingFaciData = value;
                    this.showFacilityExistsDialog = true;
                  } else {
                    this.checkDistance(value);
                  }
                });
            } else {
              this.checkDistance(value);
            }
          }
        });
    } else {
      if (value.wtf_ad_id && !this.data?.wtf_id) {
        this.buildingDataService
          .verifyAddress(value.wtf_ad_id, this.form.controls.wtf_type.value as string)
          .subscribe((response) => {
            if (response) {
              this.existingFaciData = value;
              this.showFacilityExistsDialog = true;
            } else {
              this.checkDistance(value);
            }
          });
      } else {
        this.checkDistance(value);
      }
    }
  }

  protected doLoad(): void {
    this.onLoadSuccess(this.activatedRoute.snapshot.data['wastewaterFacilityData'] as NtisWastewaterTreatmentFaci);
    if (
      this.data?.wtf_id !== null &&
      (this.data?.servedObjectsAddr === null || this.data?.servedObjectsAddr?.length === 0)
    ) {
      this.correctAddressConfirmed = true;
    }
  }

  protected setData(data: NtisWastewaterTreatmentFaci): void {
    this.data = data;
    this.form.controls.wtf_id.setValue(data.wtf_id);
    this.form.controls.wtf_type.setValue(data.wtf_type);
    this.form.controls.wtf_technical_passport_id.setValue(data.wtf_technical_passport_id);
    this.form.controls.wtf_manufacturer.setValue(data.wtf_manufacturer);
    this.form.controls.wtf_model.setValue(data.wtf_model);
    this.form.controls.wtf_distance.setValue(data.wtf_distance);
    this.form.controls.wtf_installation_date.setValue(this.datePipe.transform(data.wtf_installation_date));
    this.form.controls.wtf_checkout_date.setValue(this.datePipe.transform(data.wtf_checkout_date));
    this.form.controls.wtf_capacity.setValue(data.wtf_capacity);
    this.form.controls.wtf_manufacturer_description.setValue(data.wtf_manufacturer_description);
    this.form.controls.wtf_ad_id.setValue(data.wtf_ad_id);
    this.form.controls.wtf_facility_latitude.setValue(data.wtf_facility_latitude);
    this.form.controls.wtf_facility_longitude.setValue(data.wtf_facility_latitude);
    this.form.controls.wtf_discharge_latitude.setValue(data.wtf_discharge_latitude);
    this.form.controls.wtf_discharge_longitude.setValue(data.wtf_discharge_longitude);
    this.form.controls.wtf_discharge_type.setValue(data.wtf_discharge_type);
    this.form.controls.so_address.setValue(data.servedObjects);
    this.form.controls.fam_bds.setValue(data.fam_bds);
    this.form.controls.fam_chds.setValue(data.fam_chds);
    this.form.controls.fam_float_material.setValue(data.fam_float_material);
    this.form.controls.fam_nitrogen.setValue(data.fam_nitrogen);
    this.form.controls.fam_phosphor.setValue(data.fam_phosphor);
    this.form.controls.fam_pop_equivalent.setValue(data.fam_pop_equivalent);
    this.installationDate = new Date(data.wtf_installation_date);
    this.form.controls.wtf_identification_number.setValue(data.wtf_identification_number);
    if (
      this.form.controls.wtf_id.value &&
      (this.form.controls.wtf_type.value as string) !== this.ntisWtfType.PORTABLE_RESERVOIR
    ) {
      this.wtfTypeSelection = this.wtfTypeSelection.filter((value) => {
        return value.key !== this.ntisWtfType.PORTABLE_RESERVOIR;
      });
    }
    if (this.data.attachments !== null) {
      this.uploadedFiles = this.data.attachments;
    }
  }

  protected getData(): NtisWastewaterTreatmentFaci {
    const result: NtisWastewaterTreatmentFaci = this.data != null ? this.data : ({} as NtisWastewaterTreatmentFaci);
    result.wtf_id = this.form.controls.wtf_id.value as NtisWastewaterTreatmentFaci['wtf_id'];
    result.wtf_type = this.form.controls.wtf_type.value as NtisWastewaterTreatmentFaci['wtf_type'];
    result.wtf_technical_passport_id = this.form.controls.wtf_technical_passport_id
      .value as NtisWastewaterTreatmentFaci['wtf_technical_passport_id'];
    result.wtf_manufacturer = this.form.controls.wtf_manufacturer
      .value as NtisWastewaterTreatmentFaci['wtf_manufacturer'];
    result.wtf_model = this.form.controls.wtf_model.value as NtisWastewaterTreatmentFaci['wtf_model'];
    result.wtf_distance = this.form.controls.wtf_distance.value as NtisWastewaterTreatmentFaci['wtf_distance'];
    result.wtf_installation_date = this.form.controls.wtf_installation_date
      .value as NtisWastewaterTreatmentFaci['wtf_installation_date'];
    result.wtf_checkout_date = this.form.controls.wtf_checkout_date
      .value as NtisWastewaterTreatmentFaci['wtf_checkout_date'];
    result.wtf_capacity = this.form.controls.wtf_capacity.value as NtisWastewaterTreatmentFaci['wtf_capacity'];
    result.wtf_manufacturer_description = this.form.controls.wtf_manufacturer_description
      .value as NtisWastewaterTreatmentFaci['wtf_manufacturer_description'];
    result.wtf_ad_id = this.form.controls.wtf_ad_id.value as NtisWastewaterTreatmentFaci['wtf_ad_id'];
    result.wtf_discharge_latitude = this.form.controls.wtf_discharge_latitude
      .value as NtisWastewaterTreatmentFaci['wtf_discharge_latitude'];
    result.wtf_discharge_longitude = this.form.controls.wtf_discharge_longitude
      .value as NtisWastewaterTreatmentFaci['wtf_discharge_longitude'];
    result.wtf_discharge_type = this.form.controls.wtf_discharge_type
      .value as NtisWastewaterTreatmentFaci['wtf_discharge_type'];
    result.wtf_facility_latitude = this.form.controls.wtf_facility_latitude
      .value as NtisWastewaterTreatmentFaci['wtf_facility_latitude'];
    result.wtf_facility_longitude = this.form.controls.wtf_facility_longitude
      .value as NtisWastewaterTreatmentFaci['wtf_facility_longitude'];
    result.wtf_identification_number = this.form.controls.wtf_identification_number
      .value as NtisWastewaterTreatmentFaci['wtf_identification_number'];
    result.servedObjects = this.form.controls.so_address.value as NtisServedObjectsDAO[];
    result.attachments = this.uploadedFiles;
    return result;
  }

  ngAfterViewInit(): void {
    this.mapComponent.map.addLayer(this.vectorLayer);
    this.resizeMap();
    this.initMapPoints();
    if (this.facilitySearchResults.length === 0 && this.addressList.length === 0) {
      this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
        if (param['x'] && param['y']) {
          setTimeout(() => {
            this.setFacilityAddress({
              latitude: Number(param['x']),
              longitude: Number(param['y']),
            } as AddressSearchResponse);
          }, 0);
        }
      });
    }
  }

  initMapPoints(): void {
    if (this.data?.facilityLocationAddr) {
      this.setFacilityAddress(this.data.facilityLocationAddr);
      this.selectedFacilityAddress = this.data.facilityLocationAddr;
    }
    if (this.data?.dischargeWastewaterAddr) {
      this.setDischargeAddress(this.data.dischargeWastewaterAddr);
      this.selectedDischargeAddress = this.data.dischargeWastewaterAddr;
    }
    if (this.data?.servedObjectsAddr?.length > 0) {
      for (const row of this.data.servedObjectsAddr) {
        this.pushServedObjectAddress(row);
      }
    }
    this.facilityAsServedObject = this.data?.servedObjectsAddr?.some((item) =>
      this.addressMatchChecking(item, this.data.facilityLocationAddr)
    );
    this.dischargeAsFacility = this.addressMatchChecking(
      this.data?.facilityLocationAddr,
      this.data?.dischargeWastewaterAddr
    );
    this.cdr.detectChanges();
  }

  /**
   * addressMatchChecking checks if two address objects contain the same address_id, latitude, and longitude values.
   * @param firstObject: the first address object to compare
   * @param secondObject: the second address object to compare
   * @return true if the two objects have the same values for address_id, latitude, and longitude, false otherwise
   */
  addressMatchChecking(firstObject: AddressSearchResponse, secondObject: AddressSearchResponse): boolean {
    return (
      firstObject?.address_id === secondObject?.address_id &&
      firstObject?.latitude === secondObject?.latitude &&
      firstObject?.longitude === firstObject?.longitude
    );
  }

  /**
   * checkDistance performs distance checks on data to ensure that the facility and discharge
   * wastewater locations are within 1000 meters of each other,
   * and that the facility location is within 1000 meters of all served objects (if they exist).
   * If any of these conditions are not met, the user is prompted to confirm before the data is saved.
   * @param data: an object of type NtisWastewaterTreatmentFaci containing information about a facility and
   * its associated locations
   */
  checkDistance(data: NtisWastewaterTreatmentFaci): void {
    const dischargeLine =
      data.wtf_discharge_latitude && data.wtf_facility_latitude
        ? this.getLineLength([
            [data.wtf_facility_latitude, data.wtf_facility_longitude],
            [data.wtf_discharge_latitude, data.wtf_discharge_longitude],
          ])
        : 0;

    const overDistance = data.servedObjects?.some((item) => {
      return (
        this.getLineLength([
          [data.wtf_facility_latitude, data.wtf_facility_longitude],
          [item.so_coordinate_latitude, item.so_coordinate_longitude],
        ]) > 1000
      );
    });

    if (dischargeLine > 1000 || overDistance) {
      this.commonFormServices.translate
        .get(this.ntisBuildingDataReference + '.facilityAndDischargeDistance')
        .subscribe((translation: string) => {
          this.confirmationService.confirm({
            message: translation,
            accept: () => {
              this.save(data);
            },
          });
        });
    } else {
      this.save(data);
    }
  }

  /**
   * getLineLength calculates the length of a line in meters between two coordinates.
   * @param coordinates: an array of two coordinate pairs (each containing latitude and longitude values)
   * @return the length of the line in meters
   */
  private getLineLength(coordinates: Coordinate[]): number {
    return new LineString(coordinates).getLength();
  }

  /**
   * save stores the provided data in the database via a service call.
   * @param value: an object of type NtisWastewaterTreatmentFaci containing information
   * about a facility and its associated locations
   */
  save(value: NtisWastewaterTreatmentFaci): void {
    this.buildingDataService.setFacility(value).subscribe((response) => {
      this.onSaveSuccess(response);
      this.locationBack(response.wtf_id);
    });
  }

  /**
   * setCoordinates updates the facility, discharge, or served object location coordinates based on user input on the map.
   * @param value: an object containing information about the event that occurred on the map (e.g. a click or hover)
   */
  setCoordinates(lks94Coords: Coordinate): void {
    const coordinates = {
      address_id: null,
      latitude: lks94Coords[0],
      longitude: lks94Coords[1],
    } as AddressSearchResponse;

    this.facilitySearchResults = null;

    this.commonFormServices.confirmationService.confirm({
      message: this.commonFormServices.translate.instant(this.ntisBuildingDataReference + '.confirmLocation', {
        latitude: coordinates.latitude,
        longitude: coordinates.longitude,
      }) as string,
      accept: () => {
        switch (this.focusedMapName) {
          case InputNames.servedObject:
            this.buildingDataService.findAddressByCoordinates(lks94Coords).subscribe((response) => {
              if (this.selectedServedObjectAddress.length > 0) {
                this.addressList = this.removeSelectedfromSearchResult(this.selectedServedObjectAddress, response);
              } else {
                this.addressList = response;
              }
              this.showAddressDialog = true;
            });
            break;
          case InputNames.facilityAddress:
            this.setFacilityAddress(coordinates);
            this.setDischargeAddress(coordinates);
            break;
          case InputNames.dischargeWastewatert:
            this.setDischargeAddress(coordinates);
            break;
        }
      },
    });
  }

  /**
   * calls setCoordinates function when user clicks on map and map is focused
   * @param value object of type MapBrowserEvent<UIEvent>, which is emitted by map component mapSingleClick emitter
   */
  handleMapSingleClick(value: MapBrowserEvent<UIEvent>): void {
    if (this.focusMap) {
      this.setCoordinates(this.convertCoordinates(value.coordinate, ProjectionLikeCode.WebMercator));
    }
  }

  /**
   * calls setCoordinates function when user executes a search by coordinates and map is focused
   * @param value object of type SearchByCoordinatesEvent, which is emitted by map component searchByCoordinates emitter
   */
  handleSearchByCoordinates(event: SearchByCoordinatesEvent): void {
    if (this.focusMap) {
      this.setCoordinates(this.convertCoordinates(event.coordinates, event.projectionLikeCode));
    }
  }

  /**
   * convertCoordinates converts a set of coordinates from provided projection to LKS94 projection.
   * @param coordinates: an array containing the latitude and longitude coordinates in web mercator projection
   * @param projectionLikeCode: ProjectionLikeCode of provided coordinates
   * @return an array containing the converted latitude and longitude coordinates in LKS94 projection
   */
  convertCoordinates(coordinates: Coordinate, projectionLikeCode: ProjectionLikeCode): Coordinate {
    const lks94Coords = proj4(projectionLikeCode, ProjectionLikeCode.LKS94, coordinates);
    return [Math.floor(lks94Coords[0]), Math.floor(lks94Coords[1])];
  }

  /**
   *
   *
   * selectFromMap sets the focus to the map and updates the focused map name and map header text based on the provided input.
   * @param value: a value indicating which map (facility, discharge, or served object) is being selected from
   */
  selectFromMap(value: InputNames): void {
    if (value === this.focusedMapName) {
      this.focusedMapName = null;
      this.focusMap = false;
      this.mapHeadertext = null;
    } else {
      this.focusedMapName = value;
      this.focusMap = true;

      switch (value) {
        case InputNames.facilityAddress:
          this.mapHeadertext = this.ntisBuildingDataReference + '.selectFacility';
          break;
        case InputNames.servedObject:
          this.mapHeadertext = this.ntisBuildingDataReference + '.selectServedObject';
          break;
        case InputNames.dischargeWastewatert:
          this.mapHeadertext = this.ntisBuildingDataReference + '.selectDischarge';
          break;
      }

      if (this.contentWidth < this.mapResizeParams.width) {
        const topAnchor = document.getElementById('top-position');
        topAnchor.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }

  /**
   * setDischargeCoordinates updates the discharge location coordinates based on user input in a text field.
   * The text field should contain a comma-separated pair of latitude and longitude values.
   */
  setDischargeCoordinates(): void {
    const dischargeCoordinates = {
      address_id: null,
      latitude: Number(this.dischargeCoordinates.replace(/\s+/g, '').split(',')[0]),
      longitude: Number(this.dischargeCoordinates.replace(/\s+/g, '').split(',')[1]),
    } as AddressSearchResponse;

    this.setDischargeAddress(dischargeCoordinates);
  }

  /**
   * subscribeFormControls sets up value change subscriptions for form controls, calling the appropriate functions when a value changes.
   * The controls being subscribed to are: facilitySearch, servedObjectSearch, and wtf_manufacturer.
   */
  subscribeFormControls(): void {
    this.form.controls.facilitySearch.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((value: AddressSearchResponse) => {
        if (value && value instanceof Object) this.setFacilityAddress(value);
      });

    this.form.controls.servedObjectSearch.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((value: AddressSearchResponse) => {
        if (value) this.pushServedObjectAddress(value);
      });

    this.form.controls.wtf_manufacturer.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((value: AddressSearchResponse) => {
        this.form.controls.wtf_model.setValue(null);
        if (value) this.getFacilityModels(value);
      });
  }

  /**
   * getFacilityModels retrieves a list of available facility models from the backend based on the provided manufacturer.
   * @param value: a string containing the name of the manufacturer
   */
  getFacilityModels(value: unknown): void {
    this.buildingDataService.getFacilityModels(value as string).subscribe((response) => {
      this.facilityModels = response;
    });
  }

  /**
   * pushServedObjectAddress adds or removes a served object location from the list of selected served object addresses,
   * and updates the form control for served object locations. The location is added to the map as a new layer.
   * @param value: an object containing the address_id, latitude, and longitude of the served object location
   */
  pushServedObjectAddress(value: AddressSearchResponse): void {
    if (this.selectedServedObjectAddress.includes(value)) {
      this.selectedServedObjectAddress = this.selectedServedObjectAddress.filter(
        (row) => !this.addressMatchChecking(row, value)
      );
      this.removeServedObjectAddress(value);
      return;
    }
    this.showServObjAddressBlock = false;

    if (!this.selectedServedObjectAddress.includes(value)) {
      this.selectedServedObjectAddress.push(value);
      this.servedObjectSearchResults = this.removeSelectedfromSearchResult(
        this.selectedServedObjectAddress,
        this.servedObjectSearchResults
      );
    }

    const servedObjects: NtisServedObjectsDAO[] = this.selectedServedObjectAddress.map(
      (row: AddressSearchResponse) =>
        ({
          so_bn_id: row.ntr_building_id ? row.ntr_building_id : null,
          so_ad_id: row.address_id ? row.address_id : null,
          so_coordinate_latitude: row.latitude,
          so_coordinate_longitude: row.longitude,
        } as NtisServedObjectsDAO)
    );
    this.form.controls.so_address.setValue(servedObjects);

    if (!this.addressMatchChecking(this.selectedFacilityAddress, value)) {
      this.createMapLayer(value, this.inputNamesEnum.servedObject);
    }
  }

  /**
   * removeServedObjectAddress removes an address from the list of selected served object addresses and from the map layer.
   * @param value: the address object to remove from the list and map layer
   */
  removeServedObjectAddress(value: AddressSearchResponse): void {
    if (value) {
      this.selectedServedObjectAddress = this.selectedServedObjectAddress.filter(
        (row) => row.ntr_building_id != value.ntr_building_id
      );

      const servedObjects: NtisServedObjectsDAO[] = this.selectedServedObjectAddress.map(
        (row: AddressSearchResponse) =>
          ({
            so_ad_id: row.address_id ? row.address_id : null,
            so_coordinate_latitude: row.latitude,
            so_coordinate_longitude: row.longitude,
            so_bn_id: row.ntr_building_id,
          } as NtisServedObjectsDAO)
      );
      this.form.controls.so_address.setValue(servedObjects);

      this.vectorSourceObj.removeFeature(
        this.vectorSourceObj.getFeatureById(
          `${InputNames.servedObject}-${value.latitude}${value.longitude}${value.address_id}`
        ) as Feature<Geometry>
      );
    }
  }

  /**
   * Removes the discharge address from the form and the map.
   *
   * @param {AddressSearchResponse} value - The selected discharge address.
   * @returns {void}
   */
  removeDischargeAddress(value: AddressSearchResponse): void {
    if (value) {
      this.selectedDischargeAddress = null;
      this.dischargeAsFacility = true;
      if (this.selectedFacilityAddress) {
        this.setDischargeAddress(this.selectedFacilityAddress);
      } else {
        this.form.controls.wtf_discharge_latitude.setValue(null);
        this.form.controls.wtf_discharge_longitude.setValue(null);
      }
      this.vectorSourceObj.removeFeature(
        this.vectorSourceObj.getFeatureById(InputNames.dischargeWastewatert) as Feature<Geometry>
      );
    }
  }

  /**
   * Sets the discharge address and updates the form and map.
   *
   * @param {AddressSearchResponse} value - The selected discharge address.
   * @returns {void}
   */
  setDischargeAddress(value: AddressSearchResponse): void {
    this.selectedDischargeAddress = value;
    this.dischargeCoordinates = value ? `${value.latitude}, ${value.longitude}` : null;
    this.form.controls.wtf_discharge_latitude.setValue(value?.latitude);
    this.form.controls.wtf_discharge_longitude.setValue(value?.longitude);
    if (!this.addressMatchChecking(this.selectedFacilityAddress, value)) {
      this.createMapLayer(value, this.inputNamesEnum.dischargeWastewatert);
    }
  }

  /**
   * Pushes the facility address to the list of served object addresses,
   * or removes it from the list if it already exists.
   *
   * @returns {void}
   */
  pushFacilityAddress(): void {
    if (this.facilityAsServedObject) {
      this.buildingDataService.checkNtrObjects(this.selectedFacilityAddress.address_id).subscribe((response) => {
        if (response?.length === 1) {
          this.pushServedObjectAddress(response[0]);
        } else if (response?.length === 0 || response[0]?.ntr_building_id === null) {
          this.showMessageNtrNotFound = true;
          this.showConfirmSameAddressMessage = true;
          this.facilityAsServedObject = false;
          this.showAddressDialog = true;
          this.showNtrMessage = false;
          this.addressList = [];
        } else if (response?.length > 1) {
          this.showAddressDialog = true;
          this.addressList = response;
          this.showNtrMessage = true;
          this.showMessageNtrNotFound = false;
          this.showConfirmSameAddressMessage = false;
        }
      });
    } else {
      this.selectedServedObjectAddress = this.selectedServedObjectAddress.filter(
        (row) => !this.addressMatchChecking(row, this.selectedFacilityAddress)
      );
      this.removeServedObjectAddress(this.selectedFacilityAddress);
    }
  }

  /**
   * Sets the facility address, updates the form and map, and sets the
   * discharge address to the same value as the facility address.
   *
   * @param {AddressSearchResponse} value - The selected facility address.
   * @returns {void}
   */
  setFacilityAddress(value: AddressSearchResponse): void {
    this.textFromOtherPage = false;
    this.selectedFacilityAddress = value;
    this.dischargeAsFacility = true;
    this.form.controls.wtf_ad_id.setValue(value.address_id);
    this.form.controls.address;
    this.form.controls.wtf_facility_latitude.setValue(value.latitude);
    this.form.controls.wtf_facility_longitude.setValue(value.longitude);
    this.setDischargeAddress(value);
    this.createMapLayer(value, this.inputNamesEnum.facilityAddress);
  }

  /**
   * Removes the discharge address from the map and sets the discharge address
   * to the same value as the facility address, if the 'dischargeAsFacility'
   * property is true.
   *
   * @returns {void}
   */
  replaceDischargeAddress(): void {
    this.vectorSourceObj.removeFeature(
      this.vectorSourceObj.getFeatureById(this.inputNamesEnum.dischargeWastewatert) as Feature<Geometry>
    );
    if (this.selectedFacilityAddress && this.dischargeAsFacility) {
      this.form.controls.dwCoordinates.setValue(null);
      this.form.controls.dwCoordinates.clearValidators();
      this.form.controls.dwCoordinates.updateValueAndValidity();
      this.setDischargeAddress(this.selectedFacilityAddress);
    } else {
      this.selectedDischargeAddress = null;
      this.form.controls.wtf_discharge_latitude.setValue(null);
      this.form.controls.wtf_discharge_longitude.setValue(null);
      this.form.controls.wtf_discharge_latitude.updateValueAndValidity();
      this.form.controls.wtf_discharge_longitude.updateValueAndValidity();
      this.form.controls.dwCoordinates.setValue(null);
      this.form.controls.dwCoordinates.addValidators(Validators.required);
      this.form.controls.dwCoordinates.updateValueAndValidity();
    }
  }

  /**
   * Removes the facility address from the form, map, and list of served object addresses.
   *
   * @param {AddressSearchResponse} value - The selected facility address.
   * @returns {void}
   */
  removeFacilityAddress(value: AddressSearchResponse): void {
    if (value) {
      this.selectedFacilityAddress = null;
      this.dischargeAsFacility = false;
      this.form.controls.wtf_ad_id.setValue(null);
      this.form.controls.wtf_facility_latitude.setValue(null);
      this.form.controls.wtf_facility_longitude.setValue(null);
      this.removeDischargeAddress(value);

      this.vectorSourceObj.removeFeature(
        this.vectorSourceObj.getFeatureById(InputNames.facilityAddress) as Feature<Geometry>
      );
      if (this.selectedServedObjectAddress?.some((item) => this.addressMatchChecking(item, value))) {
        this.selectedServedObjectAddress = this.selectedServedObjectAddress.filter(
          (row) => !this.addressMatchChecking(row, value)
        );
        this.vectorSourceObj.removeFeature(
          this.vectorSourceObj.getFeatureById(
            `${InputNames.servedObject}-${value.longitude}${value.latitude}`
          ) as Feature<Geometry>
        );
      }
      this.facilityAsServedObject = false;
    }
  }

  /**
   * Searches for served object addresses using the 'buildingDataService'.
   *
   * @param {ForeignKeyParams} value - The search parameters.
   * @returns {void}
   */
  searchServedObjectAddress(value: ForeignKeyParams): void {
    this.buildingDataService.findNtrAddress(value).subscribe((response: AddressSearchResponse[]) => {
      if (this.selectedServedObjectAddress.length > 0) {
        this.servedObjectSearchResults = this.removeSelectedfromSearchResult(
          this.selectedServedObjectAddress,
          response
        );
      } else {
        this.servedObjectSearchResults = response;
      }
    });
  }

  /**
   * Searches for facility addresses using the 'buildingDataService'.
   *
   * @param {ForeignKeyParams} value - The search parameters.
   * @returns {void}
   */
  searchFacilityAddress(value: ForeignKeyParams, fromInput: boolean = true): void {
    this.buildingDataService.findAddress(value).subscribe((response: AddressSearchResponse[]) => {
      if (fromInput) {
        this.facilitySearchResults = response;
      } else {
        this.addressList = response;
        if (response.length > 0) {
          this.showAddressDialog = true;
          this.textFromOtherPage = true;
        }
      }
    });
  }

  /**
   * Populates the 'municipalities' property with data from the 'commonService'.
   *
   * @returns {void}
   */
  getDataOnLoad(): void {
    this.commonService.getClsf(NTIS_MUNICIPALITIES).subscribe((result) => {
      this.municipalities = result.map((item) => ({
        option: item.display,
        value: item.key,
      }));
    });

    this.commonService.getClsf(NTIS_WTF_TYPE).subscribe((options) => {
      if (
        this.form.controls.wtf_id.value &&
        (this.form.controls.wtf_type.value as string) !== this.ntisWtfType.PORTABLE_RESERVOIR
      ) {
        this.wtfTypeSelection = options.filter((value) => {
          return value.key !== this.ntisWtfType.PORTABLE_RESERVOIR;
        });
      } else {
        this.wtfTypeSelection = options;
      }
    });
  }

  /**
   * @method resizeMap
   * This function is intended to change the size of the map based on the size of its parent element. The
   * function uses a "ResizeObserver" object to monitor changes in element size and selects the value of the map's
   * height based on the element's width. The "ResizeObserver"
   * starts monitoring changes in element size using the specified element reference.
   *
   */
  resizeMap(): void {
    this.resizeObserver = new ResizeObserver((entries) => {
      for (const entry of entries) {
        const { contentRect } = entry;
        const { width, height, lengthUnit } = this.mapResizeParams;
        const { offsetTop } = entry.target.parentElement;
        const mapElement = this.mapReference.nativeElement;
        this.contentWidth = contentRect.width;
        if (contentRect.width > width) {
          mapElement.style.height = `${
            contentRect.height - (offsetTop + this.mapResizeParams.marginBottom)
          }${lengthUnit}`;
        } else {
          mapElement.style.height = `${height}${lengthUnit}`;
        }
      }
    });
    this.resizeObserver.observe(document.querySelector(this.mapResizeParams.elementReference));
  }

  /**
   * Creates a map layer for the specified address.
   *
   * @param {AddressSearchResponse} value - The selected address.
   * @param {InputNames} inputName - The input name corresponding to the address.
   * @returns {void}
   */
  createMapLayer(value: AddressSearchResponse, inputName: InputNames): void {
    if (value.latitude && value.longitude) {
      const coordinates: Coordinate = [value.latitude, value.longitude];

      const point = new Point(proj.transform(coordinates, ProjectionLikeCode.LKS94, ProjectionLikeCode.WebMercator));
      this.mapComponent.setCenterCoordinates(coordinates, 18, ProjectionLikeCode.LKS94, true);

      const featureObj = new Feature({
        geometry: point,
        name: inputName,
        coordinates: coordinates,
      });

      const iconStyle = new Style({
        image: new Icon({
          anchor: [0.5, 1],
          src: `./assets/img/${this.getPinColor(inputName)}.png`,
        }),
      });

      featureObj.setStyle(iconStyle);

      switch (inputName) {
        case InputNames.facilityAddress:
          this.vectorSourceObj.removeFeature(this.vectorSourceObj.getFeatureById(inputName) as Feature<Geometry>);
          this.vectorSourceObj.addFeature(featureObj);
          featureObj.setId(inputName);
          break;
        case InputNames.dischargeWastewatert:
          this.vectorSourceObj.removeFeature(this.vectorSourceObj.getFeatureById(inputName) as Feature<Geometry>);
          this.vectorSourceObj.addFeature(featureObj);
          featureObj.setId(inputName);
          break;
        case InputNames.servedObject:
          this.vectorSourceObj.addFeature(featureObj);
          featureObj.setId(`${inputName}-${value.latitude}${value.longitude}${value.address_id}`);
          break;
      }
    } else {
      this.commonFormServices.translate
        .get(this.ntisBuildingDataReference + '.cantGetCoordinates')
        .subscribe((translation: string) => {
          console.error(translation);
        });
    }
  }

  /**
   * Returns the name of the icon to use for the specified address.
   *
   * @param {AddressSearchResponse} value - The selected address.
   * @param {InputNames} place - The input name corresponding to the address.
   * @returns {IconNames} The name of the icon.
   */
  getPinColor(place: InputNames): IconNames {
    switch (place) {
      case this.inputNamesEnum.facilityAddress:
        return this.iconNamesEnum.pinRed;
      case this.inputNamesEnum.servedObject:
        return this.iconNamesEnum.pinBlue;
      case this.inputNamesEnum.dischargeWastewatert:
        return this.iconNamesEnum.pinGreen;
    }
  }
  override ngOnDestroy(): void {
    this.resizeObserver.unobserve(document.querySelector(this.mapResizeParams.elementReference));
  }

  locationBack(wtfId?: number): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    if (this.returnUrl && wtfId && !this.returnTo) {
      void this.router.navigateByUrl(`${this.returnUrl}?id=${wtfId}`, navigationExtras);
    } else {
      if (this.returnTo && wtfId) {
        void this.router.navigateByUrl(this.returnUrl, navigationExtras).then(() => {
          window.location.reload();
        });
      } else if (this.isIntsOwner) {
        void this.router.navigateByUrl(
          this.router.url.split('/' + NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY)[0],
          navigationExtras
        );
      } else {
        void this.router.navigateByUrl(this.router.url.split('/' + RoutingConst.EDIT)[0], navigationExtras);
      }
    }
  }

  removeSelectedfromSearchResult(
    selected: AddressSearchResponse[],
    searchResult: AddressSearchResponse[]
  ): AddressSearchResponse[] {
    return searchResult.filter((resAddr) => {
      return selected.every((selectedAddr) => {
        return resAddr.address_id !== selectedAddr.address_id;
      });
    });
  }

  assignExistingFacility(value: NtisWastewaterTreatmentFaci): void {
    this.buildingDataService.assignExistingFacility(value).subscribe((wtfId) => {
      this.locationBack(wtfId);
    });
  }

  navigateToOtherFacility(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((param: Params) => {
      if (param['id']) {
        const url = this.commonFormServices.router.url.replace(param['id'] as string, this.existingWtfId?.toString());
        window.open(url, '_blank');
      } else if (this.commonFormServices.router.url.includes(RoutingConst.NEW)) {
        const url = this.commonFormServices.router.url.replace(RoutingConst.NEW, this.existingWtfId?.toString());
        window.open(url, '_blank');
      }
    });
  }

  onCheckboxChange($event: Event): void {
    const eventTarget = $event.target as HTMLInputElement;
    this.correctAddressConfirmed = eventTarget.checked;
    if (this.correctAddressConfirmed) {
      this.showAddressDialog = false;
      this.form.controls.so_address.setValue(null);
      this.form.controls.so_address.clearValidators();
      this.form.controls.so_address.updateValueAndValidity();
      if (this.data?.servedObjectsAddr) {
        this.data.servedObjectsAddr = [];
      }
    }
  }
}
