import { ActivatedRoute, Router } from '@angular/router';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { AuthUtil, CommonFormServices, getLang } from '@itree/ngx-s2-commons';
import { CheckWtfSelectionComponent } from '@itree-web/src/app/ntis-shared/components/check-wtf-selection/check-wtf-selection.component';
import { CheckWtfSelectionService } from '@itree-web/src/app/ntis-shared/services/check-wtf-selection.service';
import { CommonModule } from '@angular/common';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Coordinate } from 'ol/coordinate';
import { DialogModule } from 'primeng/dialog';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { getSearchMapMenu } from '@itree-web/src/app/standalone/map/utils/getDefaultMapMenus';
import { InputDigitsOnlyDirective } from '@itree-web/src/app/ntis-shared/directives/input-digits-only.directive';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MapBrowserEvent } from 'ol';
import { MapComponent } from '@itree-web/src/app/standalone/map/components/map/map.component';
import { MapLocationSearchResult } from '@itree-web/src/app/standalone/map/types/map-location-search-result';
import {
  MONTAVIMAS,
  NTIS_SRV_ITEM_TYPE,
  NTIS_WTF_TYPE,
  VALYMAS,
} from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NtisAddressSuggestion, NtisServiceSearchResult } from '@itree-commons/src/lib/model/api/api';
import { NtisContractsService } from '../../services/ntis-contracts.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { ProjectionLikeCode } from '@itree-web/src/app/standalone/map/enums/projection-like-code';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SearchByCoordinatesEvent } from '@itree-web/src/app/standalone/map/types/search-by-coordinates-event';
import { ServiceSearchResultListComponent } from '../../components/service-search-result-list/service-search-result-list.component';
import { SprFormArrayValidators } from '@itree-commons/src/lib/validators/form-array-validators';
import { SprListIdKeyValue } from '@itree-commons/src/lib/model/api/api';
import { Subject, takeUntil } from 'rxjs';
import * as proj from 'ol/proj';
import { QUERY_PARAMS, SERVICE_ID } from '@itree-commons/src/constants/localStorage.constants';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-service-search-page',
  standalone: true,
  templateUrl: './service-search-page.component.html',
  styleUrls: ['./service-search-page.component.scss'],
  imports: [
    CommonModule,
    ItreeCommonsModule,
    ReactiveFormsModule,
    ServiceSearchResultListComponent,
    InputDigitsOnlyDirective,
    AutoCompleteModule,
    DialogModule,
    MapComponent,
    CheckWtfSelectionComponent,
    FontAwesomeModule,
    TooltipModule,
  ],
})
export class ServiceSearchPageComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.pages.serviceSearch';
  readonly NTIS_WTF_TYPE = NTIS_WTF_TYPE;
  destroy$: Subject<boolean> = new Subject<boolean>();

  orderingOptions: SprListIdKeyValue[] = [];
  orderByPrice: boolean = null;
  orderByRating: boolean = null;

  @ViewChild(MapComponent) private mapComponent: MapComponent;

  showMap = false;
  mapMenuItems = [getSearchMapMenu()];

  orderClause = new FormGroup({
    order: new FormControl<string[]>(null),
  });

  addressSuggestions: NtisAddressSuggestion[] = [];
  facilityTypes: SprListIdKeyValue[] = [];
  availableServices: SprListIdKeyValue[] = [];
  form = new FormGroup({
    address: new FormControl<NtisAddressSuggestion>(null, Validators.required),
    distanceToObject: new FormControl<string>(null),
    equipmentType: new FormControl<string>(null, Validators.required),
    carCapacity: new FormControl<string>(null),
    services: new FormArray<FormControl<boolean>>([], SprFormArrayValidators.atLeastOneTruthy),
  });
  searchResult: NtisServiceSearchResult[];

  constructor(
    private commonService: CommonService,
    private ntisContractsService: NtisContractsService,
    private checkWtfSelectionService: CheckWtfSelectionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public faIconsService: FaIconsService,
    private commonFormServices: CommonFormServices
  ) {}

  ngOnInit(): void {
    this.orderClause.controls.order.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      if (value) {
        this.getOrderClause();
      }
    });
    this.commonFormServices.translate
      .get(this.translationsReference + '.orderByPrice')
      .subscribe((translation: string) => {
        this.orderingOptions.push({ display: translation, key: '1', id: null });
      });
    this.commonFormServices.translate
      .get(this.translationsReference + '.orderByRating')
      .subscribe((translation: string) => {
        this.orderingOptions.push({ display: translation, key: '2', id: null });
      });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((queryParams) => {
      const selectedSrvType = queryParams?.['srvType'] as string;
      if (selectedSrvType && this.availableServices?.length && this.form.controls.services.length) {
        this.form.controls.services.setValue(
          this.form.controls.services.value.map(
            (_serviceValue, serviceIndex) =>
              this.availableServices[serviceIndex].key.toLowerCase() === selectedSrvType.toLowerCase()
          )
        );
      }
    });

    this.commonService.getClsf(NTIS_WTF_TYPE).subscribe((result) => {
      this.facilityTypes = result;
    });

    this.commonService.getClsf(NTIS_SRV_ITEM_TYPE).subscribe((result) => {
      const selectedSrvType = this.activatedRoute.snapshot.queryParams?.['srvType'] as string;
      this.availableServices = result.filter((clsf) => clsf.key !== VALYMAS);
      this.form.controls.services.clear();
      this.availableServices.forEach((resultItem) => {
        this.form.controls.services.push(
          new FormControl(selectedSrvType ? resultItem.key.toLowerCase() === selectedSrvType.toLowerCase() : false)
        );
      });
    });

    if (this.activatedRoute.snapshot.data?.['loadWtfInfo'] === true) {
      this.ntisContractsService.getSelectedWtfInfo().subscribe((result) => {
        if (result) {
          if (result.distanceToObject) {
            this.form.controls.distanceToObject.setValue(`${result.distanceToObject}`);
          }
          if (result.equipmentType) {
            this.form.controls.equipmentType.setValue(result.equipmentType);
          }
          if (result.lksX && result.lksY) {
            this.setAddress([result.lksX, result.lksY], result.address);
          }
        }
      });
    }
  }

  getSelectedServices(): string[] {
    return this.form.controls.services.controls
      .map((control, index) => (control.value === true ? this.availableServices[index].key : null))
      .filter((value) => value);
  }

  autocompleteAddress(event: { originalEvent: Event; query: string }): void {
    const text = typeof event.query === 'string' ? event.query.trim() : '';
    if (text.length >= 3) {
      this.ntisContractsService.getAddressSuggestions(text).subscribe((result) => {
        this.addressSuggestions = result;
      });
    } else {
      this.addressSuggestions = [];
    }
  }

  setAddress(lksCoordinates: Coordinate, addressName?: string, hideMap = true): void {
    const address: NtisAddressSuggestion = {
      address: addressName || `${lksCoordinates[0]}, ${lksCoordinates[1]}`,
      id: null,
      lksX: lksCoordinates[0],
      lksY: lksCoordinates[1],
    };
    this.addressSuggestions = [address];
    this.form.controls.address.setValue(address);
    this.toggleMap(!hideMap);
  }

  toggleMap(show?: boolean): void {
    this.showMap = show === undefined ? !this.showMap : show;
    if (this.showMap && this.mapComponent) {
      this.mapComponent.clearPins();
      const address = this.form.controls.address.value;
      if (address) {
        this.mapComponent.showPinInCoordinates([address.lksX, address.lksY], ProjectionLikeCode.LKS94);
        this.mapComponent.setCenterCoordinates([address.lksX, address.lksY], 17, ProjectionLikeCode.LKS94, false);
      }
    }
  }

  handleMapClick(event: MapBrowserEvent<UIEvent>): void {
    const lksCoordinates = proj
      .transform(event.coordinate, ProjectionLikeCode.WebMercator, ProjectionLikeCode.LKS94)
      .map((coordinate) => Math.round(coordinate));
    this.setAddress(lksCoordinates);
  }

  handleSearchByCoordinates(event: SearchByCoordinatesEvent): void {
    const coordinates = (
      event.projectionLikeCode === ProjectionLikeCode.LKS94
        ? event.coordinates
        : proj.transform(event.coordinates, event.projectionLikeCode, ProjectionLikeCode.LKS94)
    ).map((coordinate) => Math.round(coordinate));
    this.setAddress(coordinates);
  }

  handleClickSearchResult(result: MapLocationSearchResult): void {
    this.setAddress(
      proj
        .transform(result.coordinates, ProjectionLikeCode.WGS84, ProjectionLikeCode.LKS94)
        .map((coordinate) => Math.round(coordinate)),
      result.type === 'Adresas' ? result.name : undefined
    );
  }

  handleSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.ntisContractsService
        .serviceSearch(
          this.form.controls.address.value.lksX,
          this.form.controls.address.value.lksY,
          this.form.controls.distanceToObject.value != null ? Number(this.form.controls.distanceToObject.value) : 0,
          this.form.controls.equipmentType.value,
          this.form.controls.carCapacity.value != null ? Number(this.form.controls.carCapacity.value) : 0,
          this.getSelectedServices(),
          getLang(),
          this.getPriceClause(),
          this.getRatingClause()
        )
        .subscribe((result) => {
          this.searchResult = result;
          this.searchResult = this.searchResult.filter((res) => res.services.length > 0);
        });
    } else {
      this.scrollToFirstError();
    }
  }

  private scrollToFirstError(): void {
    const firstErrorIndex = Object.values(this.form.controls).findIndex((control) => control.errors);
    const formFields = document.querySelectorAll('spr-form-field');

    formFields[firstErrorIndex].scrollIntoView({
      behavior: 'smooth',
      block: 'center',
    });
  }

  getPriceClause(): string {
    let clause = null;
    if (this.orderByPrice !== null) {
      clause = this.orderByPrice ? '1' : '2';
    }
    return clause;
  }

  getRatingClause(): string {
    let clause = null;
    if (this.orderByRating !== null) {
      clause = this.orderByRating ? '1' : '2';
    }
    return clause;
  }

  getOrderClause(): void {
    this.orderByPrice = null;
    this.orderByRating = null;
    if (this.orderClause.controls.order.value.length > 0) {
      if (this.orderClause.controls.order.value[0] === '1') {
        this.orderByPrice = true;
      } else if (this.orderClause.controls.order.value[0] === '2') {
        this.orderByRating = true;
      }
      if (this.orderClause.controls.order.value.length === 2) {
        if (this.orderClause.controls.order.value[1] === '1') {
          this.orderByPrice = true;
        } else if (this.orderClause.controls.order.value[1] === '2') {
          this.orderByRating = true;
        }
      }
    }
    this.handleSubmit();
  }

  handleClickOrderService(serviceId: number): void {
    if (AuthUtil.isLoggedIn()) {
      this.checkWtfSelectionService.check(
        {
          adId: this.form.controls.address.value?.id,
          lksX: this.form.controls.address.value.lksX,
          lksY: this.form.controls.address.value.lksY,
          wtfType: this.form.controls.equipmentType.value,
          address: this.form.controls.address.value.address,
          wtfDistance:
            this.form.controls.distanceToObject.value != null ? Number(this.form.controls.distanceToObject.value) : 0,
        },
        `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.SERVICE_SEARCH}/${NtisRoutingConst.SERVICE_ORDER_EDIT}/${serviceId}`
      );
    } else {
      const queryParamsObj = {
        adId: this.form.controls.address.value?.id,
        x: !this.form.controls.address.value?.id ? this.form.controls.address.value.lksX : undefined,
        y: !this.form.controls.address.value?.id ? this.form.controls.address.value.lksY : undefined,
        type: this.form.controls.equipmentType.value,
        address: this.form.controls.address.value.address,
        distance:
          this.form.controls.distanceToObject.value != null ? Number(this.form.controls.distanceToObject.value) : 0,
      };
      localStorage.setItem(SERVICE_ID, serviceId.toString());
      localStorage.setItem(QUERY_PARAMS, JSON.stringify(queryParamsObj));
      void this.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.CONTRACTS,
          NtisRoutingConst.SERVICE_SEARCH,
          NtisRoutingConst.SERVICE_ORDER_EDIT,
          serviceId,
        ],
        {
          queryParams: queryParamsObj,
        }
      );
    }
  }

  handleClickRequestContract(orgId: number): void {
    if (AuthUtil.isLoggedIn()) {
      this.checkWtfSelectionService.check(
        {
          adId: this.form.controls.address.value?.id,
          lksX: this.form.controls.address.value.lksX,
          lksY: this.form.controls.address.value.lksY,
          wtfType: this.form.controls.equipmentType.value,
          address: this.form.controls.address.value.address,
          wtfDistance:
            this.form.controls.distanceToObject.value != null ? Number(this.form.controls.distanceToObject.value) : 0,
        },
        `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.SERVICE_SEARCH}/${NtisRoutingConst.CONTRACT_REQUEST}/${RoutingConst.NEW}/${orgId}`
      );
    } else {
      void this.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.CONTRACTS,
          NtisRoutingConst.SERVICE_SEARCH,
          NtisRoutingConst.CONTRACT_REQUEST,
          RoutingConst.NEW,
          orgId,
        ],
        {
          queryParams: {
            adId: this.form.controls.address.value?.id,
            x: !this.form.controls.address.value?.id ? this.form.controls.address.value.lksX : undefined,
            y: !this.form.controls.address.value?.id ? this.form.controls.address.value.lksY : undefined,
            type: this.form.controls.equipmentType.value,
            address: this.form.controls.address.value.address,
            distance:
              this.form.controls.distanceToObject.value != null ? Number(this.form.controls.distanceToObject.value) : 0,
          },
        }
      );
    }
  }

  clearOrderingValues(): void {
    this.orderClause.controls.order.setValue([]);
    this.getOrderClause();
  }
}
