import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import {
  ForeignKeyParams,
  NtisAddrSearchRequest,
  NtisAddrSearchResult,
  NtisWtfInfo,
} from '@itree-commons/src/lib/model/api/api';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { NTIS_MUNICIPALITIES } from '../../constants/classifiers.const';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';
import { QuickSearchAddrList, SelectOption } from '../../models/wtf-search';
import { NtisCommonService } from '../../services/ntis-common.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { NtisInputValidators } from '../../validators/input-validators';

interface QueryParams {
  address: string;
  returnUrl: string;
  x?: string;
  y?: string;
}

@Component({
  selector: 'ntis-existing-facility-search',
  templateUrl: './existing-facility-search.component.html',
  styleUrls: ['./existing-facility-search.component.scss'],
})
export class ExistingFacilitySearchComponent implements OnInit {
  @Output() selectedWtf: EventEmitter<number> = new EventEmitter<number>();
  @Input() showAddedFacilities: boolean = true;
  @Input() showWithHeader: boolean = true;
  @Input() showSearchBlock: boolean = true;
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.orderOutsideNtis';

  facilityData: NtisWtfInfo;
  detailedSearchFaciList: NtisAddrSearchResult[] = [];
  municipalities: SelectOption[] = [];
  cities: SelectOption[] = [];
  streets: SelectOption[] = [];
  toRegisterNewFaci: string = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.BUILDING_DATA}/${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.EDIT}/${RoutingConst.NEW}`;
  today: Date = new Date();
  newlyAddedWtf: string;
  destroy$: Subject<boolean> = new Subject();
  queryParams = {} as QueryParams;
  quickSearchFaciList: QuickSearchAddrList = {
    list: [],
    labelTemplate: '&address& (&wtf_id&)',
  };

  constructor(
    protected commonFormService: CommonFormServices,
    private ntisCommonService: NtisCommonService,
    private activatedRoute: ActivatedRoute,
    private commonService: CommonService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      const addressObj = {
        ad_id: null,
        city: null,
        wtf_id: Number(param['id']),
        housing: null,
        latitude: null,
        longitude: null,
        municipality: null,
        street: null,
      } as NtisAddrSearchResult;
      this.setSelectedFaci(addressObj);
      if (param['id']) {
        this.selectedWtf.emit(Number(param['id']));
      } else {
        this.newlyAddedWtf = param['actionType']?.split('id=')[1] ?? null;
        this.selectedWtf.emit(Number(this.newlyAddedWtf));
      }
    });
    this.getSelectOptions();
    this.queryParams.returnUrl =
      this.commonFormService.router.url.split('?id').length > 1
        ? this.commonFormService.router.url.split('?id')[0]
        : this.commonFormService.router.url.split('%')[0];
  }

  getSelectOptions(): void {
    this.commonService.getClsf(NTIS_MUNICIPALITIES).subscribe((result) => {
      this.municipalities = result
        .map((item) => ({
          option: item.display,
          value: item.key,
        }))
        .sort((a, b) => (a.option > b.option ? 1 : -1));
    });
  }

  onDetailedFaciSearch($event: NtisAddrSearchRequest): void {
    this.ntisCommonService.onDetailedFaciSearch($event).subscribe((res) => {
      if (res.length > 0) {
        this.detailedSearchFaciList = res;
      } else {
        this.commonFormService.translate.get('common.primeng.emptyMessage').subscribe((translation: string) => {
          this.commonFormService.appMessages.showError('', translation);
        });
      }
    });
  }

  findFaciLocations(value: ForeignKeyParams): void {
    const pattern: RegExp = /[a-zA-Z]/g;
    if (pattern.test(value.filterValue)) {
      this.queryParams.address = value.filterValue;
    } else if (new RegExp(NtisInputValidators.COORDINATES_PATTERN).test(value.filterValue)) {
      const coordinates = value.filterValue.split(',');
      this.queryParams.x = coordinates[0].trim();
      this.queryParams.y = coordinates[1].trim();
    }
    this.ntisCommonService.findFacilities(value).subscribe((res) => {
      this.quickSearchFaciList.list = res.data;
    });
  }

  filterCities(municipalityCode: number): void {
    this.ntisCommonService.getResidencesList(municipalityCode).subscribe((result) => {
      this.cities = result.map((city) => ({
        option: city.re_name,
        value: city.re_recidence_code,
        id: city.re_municipality_code,
      }));
    });
  }

  filterStreets(residenceCode: number): void {
    this.ntisCommonService.getStreetsList(residenceCode).subscribe((result) => {
      this.streets = result.map((street) => ({
        option: street.str_name,
        value: street.str_street_code,
        id: street.str_residence_code,
      }));
    });
  }

  setSelectedFaci(selectedFaci: NtisAddrSearchResult): void {
    selectedFaci?.wtf_id
      ? this.ntisCommonService.getWtfInfo(selectedFaci.wtf_id).subscribe((res) => {
          this.facilityData = res;
          this.selectedWtf.emit(selectedFaci.wtf_id);
        })
      : (this.facilityData = null);
    this.selectedWtf.emit(undefined);
  }
}
