import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ForeignKeyParams, NtisAddrSearchRequest, NtisAddrSearchResult } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';
import { QuickSearchAddrList, SelectOption } from '../../models/wtf-search';
import { NtisInputValidators } from '../../validators/input-validators';

enum SearchBy {
  address = 'address',
  coordinates = 'coordinates',
  ntr = 'ntr',
}

@Component({
  selector: 'ntis-wtf-search',
  templateUrl: './wtf-search.component.html',
  styleUrls: ['./wtf-search.component.scss'],
})
export class WtfSearchComponent implements OnInit {
  readonly translationsReference = 'ntisShared.components.wtfSearch';
  readonly RoutingConst = RoutingConst;

  @Input() quickSearchAddrList: QuickSearchAddrList;
  @Input() searchByNTR: boolean = false;
  @Input() detailedSearchAddrList: NtisAddrSearchResult[] = [];
  @Input() municipalities: SelectOption[] = [];
  @Input() cities: SelectOption[] = [];
  @Input() streets: SelectOption[] = [];
  @Output() municipalitySelectEvent = new EventEmitter<number>();
  @Output() citySelectEvent = new EventEmitter<number>();
  @Output() selectedLocationEvent = new EventEmitter<NtisAddrSearchResult>();
  @Output() findLocationsEvent = new EventEmitter<ForeignKeyParams>();
  @Output() detailedSearchEvent = new EventEmitter<NtisAddrSearchRequest>();

  destroy$: Subject<boolean> = new Subject();
  searchBy = SearchBy;
  isDetailedSearch: boolean = false;
  selectedAddr: NtisAddrSearchResult;

  quickSearchForm = new FormGroup({
    quick_search_addr_list: new FormControl<NtisAddrSearchResult>(null, Validators.required),
  });

  detailedSearchForm = new FormGroup({
    municipality: new FormControl<number>(null),
    city: new FormControl<number>({ value: null, disabled: true }),
    street: new FormControl<number>({ value: null, disabled: true }),
    houseNo: new FormControl<string>({ value: null, disabled: true }),
    flatNo: new FormControl<string>({ value: null, disabled: true }),
    coordinates: new FormControl<string>({ value: '', disabled: true }),
    ntrNumber: new FormControl<string>({ value: '', disabled: true }),
    radioSearchBy: new FormControl<string>(this.searchBy.address),
  });

  constructor(public faIconsService: FaIconsService, private commonFormServices: CommonFormServices) {}

  ngOnInit(): void {
    this.quickSearchForm.controls.quick_search_addr_list.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((newValue) => {
        this.detailedSearchAddrList = [];
        if (newValue) {
          this.selectedLocationEvent.emit(newValue);
        }
      });

    this.detailedSearchForm.controls.radioSearchBy.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      this.detailedSearchForm.clearValidators();
      if (newValue === this.searchBy.address) {
        this.detailedSearchForm.controls.municipality.enable();
        this.detailedSearchForm.controls.municipality.addValidators(Validators.required);
        this.detailedSearchForm.controls.coordinates.reset();
        this.detailedSearchForm.controls.coordinates.disable();
      } else if (newValue === this.searchBy.coordinates) {
        this.detailedSearchForm.controls.municipality.reset();
        this.detailedSearchForm.controls.municipality.disable();
        this.detailedSearchForm.controls.coordinates.enable();
        this.detailedSearchForm.controls.coordinates.addValidators(
          Validators.compose([Validators.required, NtisInputValidators.validateCoordinates()])
        );
      } else if (newValue === this.searchBy.ntr) {
        this.detailedSearchForm.controls.municipality.reset();
        this.detailedSearchForm.controls.municipality.disable();
        this.detailedSearchForm.controls.coordinates.reset();
        this.detailedSearchForm.controls.coordinates.disable();
        this.detailedSearchForm.controls.ntrNumber.enable();
        this.detailedSearchForm.controls.ntrNumber.addValidators(Validators.required);
      }
    });

    this.detailedSearchForm.controls.municipality.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue) {
        this.detailedSearchForm.controls.city.enable();
        this.detailedSearchForm.controls.city.addValidators(Validators.required);
        this.municipalitySelectEvent.emit(newValue);
      } else {
        this.detailedSearchForm.controls.city.reset();
        this.detailedSearchForm.controls.city.disable();
      }
    });
    this.detailedSearchForm.controls.city.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue) {
        this.detailedSearchForm.controls.street.enable();
        this.detailedSearchForm.controls.street.addValidators(Validators.required);
        this.citySelectEvent.emit(newValue);
      } else {
        this.detailedSearchForm.controls.street.reset();
        this.detailedSearchForm.controls.street.disable();
      }
    });
    this.detailedSearchForm.controls.street.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue !== null) {
        this.detailedSearchForm.controls.houseNo.enable();
        this.detailedSearchForm.controls.flatNo.enable();
      } else {
        this.detailedSearchForm.controls.houseNo.reset();
        this.detailedSearchForm.controls.houseNo.disable();
        this.detailedSearchForm.controls.flatNo.reset();
        this.detailedSearchForm.controls.flatNo.disable();
      }
    });
  }

  onDetailedSearch(): void {
    this.isDetailedSearch = !this.isDetailedSearch;
    this.quickSearchForm.reset();
    if (!this.isDetailedSearch) {
      this.detailedSearchForm.controls.radioSearchBy.setValue('');
    } else {
      this.detailedSearchForm.controls.radioSearchBy.setValue(this.searchBy.address);
    }
  }

  onSubmit(): void {
    Object.keys(this.detailedSearchForm.controls).forEach((field) => {
      const control = this.detailedSearchForm.get(field);
      control.updateValueAndValidity();
    });
    this.detailedSearchForm.markAllAsTouched();
    if (this.detailedSearchForm.valid) {
      this.detailedSearchAddrList = [];
      const detailedSearchParams: NtisAddrSearchRequest = {} as NtisAddrSearchRequest;
      detailedSearchParams.municipalityCode = this.detailedSearchForm.controls.municipality.value;
      detailedSearchParams.residenceCode = this.detailedSearchForm.controls.city.value;
      if (this.detailedSearchForm.controls.street.value !== 0) {
        detailedSearchParams.streetCode = this.detailedSearchForm.controls.street.value;
      }
      detailedSearchParams.houseNo = this.detailedSearchForm.controls.houseNo.value;
      detailedSearchParams.flatNo = this.detailedSearchForm.controls.flatNo.value;
      detailedSearchParams.latitude = this.detailedSearchForm.controls.coordinates.value?.split(
        ', '
      )[0] as unknown as NtisAddrSearchRequest['latitude'];
      detailedSearchParams.longitude = this.detailedSearchForm.controls.coordinates.value?.split(
        ', '
      )[1] as unknown as NtisAddrSearchRequest['longitude'];
      detailedSearchParams.uniqueNo = this.detailedSearchForm.controls.ntrNumber.value;

      this.detailedSearchEvent.emit(detailedSearchParams);
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  onSelectAddr(address: NtisAddrSearchResult): void {
    this.selectedAddr = address;
    this.selectedLocationEvent.emit(address);
  }

  onQuickSearch(value: ForeignKeyParams): void {
    const pattern: RegExp = /[a-zA-Z]/g;
    if (pattern.test(value.filterValue)) {
      this.setInvalidCoordinateError(false);
      this.quickSearchForm.controls.quick_search_addr_list.updateValueAndValidity();
      this.findLocationsEvent.emit(value);
    } else {
      if (!new RegExp(NtisInputValidators.COORDINATES_PATTERN).test(value.filterValue)) {
        this.setInvalidCoordinateError(true);
      } else {
        this.setInvalidCoordinateError(false);
        this.quickSearchForm.controls.quick_search_addr_list.updateValueAndValidity();
        this.findLocationsEvent.emit(value);
      }
    }
  }

  setInvalidCoordinateError(value: boolean): void {
    this.quickSearchForm.controls.quick_search_addr_list.setErrors({ invalidCoordinates: value });
  }
}
