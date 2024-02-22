import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { Observable, takeUntil } from 'rxjs';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { BuildingDataService } from '../../../ntis-building-data-module/services/building-data.service';
import { AddressSearchResponse, NtisAdrResidencesDAO, NtisAdrStreetsDAO } from '@itree-commons/src/lib/model/api/api';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { NtisInputValidators } from '../../validators/input-validators';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { SelectOption } from '../../models/wtf-search';
import { NTIS_MUNICIPALITIES } from '../../constants/classifiers.const';
import { NtisCommonService } from '../../services/ntis-common.service';

export enum SearchMethod {
  address = 'address',
  coordinates = 'coordinates',
  immovablePropertyNo = 'immovable-property-no',
}

export interface AddressSearch {
  municipality: string;
  city: string;
  street: string;
  houseNr: string;
  flatNr: string;
  longitude: string;
  latitude: string;
  immovablePropertyNo: string;
}

@Component({
  selector: 'ntis-address-search-form',
  templateUrl: './address-search-form.component.html',
  styleUrls: ['./address-search-form.component.scss'],
})
export class AddressSearchFormComponent extends DeprecatedBaseEditForm<AddressSearch> implements OnInit, OnChanges {
  readonly addressFormReference = 'ntisShared.components.addressForm';
  readonly commonActionReference = 'common.action';
  readonly commonErrorReference = 'common.error';
  readonly searchMethod = SearchMethod;
  readonly LKS_94_COORDINATES_PATTERN = '^(\\d{6}(.\\d{1,6})?),\\s(\\d{7}(.\\d{1,6})?)$';

  searchMethodValue: SearchMethod = this.searchMethod.address;

  municipalities: SelectOption[] = [];
  citiesList: NtisAdrResidencesDAO[] = [];
  streetsList: NtisAdrStreetsDAO[] = [];
  addressList: AddressSearchResponse[] = [];
  showResultDialog: boolean = false;

  showStreetRequired: boolean = true;

  @Input() searchNtr: boolean = false;
  @Output() addressSelect: EventEmitter<AddressSearchResponse> = new EventEmitter<AddressSearchResponse>();
  @Input() addressId: number = null;

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private buildingDataService: BuildingDataService,
    private commonService: CommonService,
    private ntisCommonService: NtisCommonService
  ) {
    super(formBuilder, commonFormServices);
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes.addressId?.currentValue) {
      this.loadAddress(this.addressId);
    }
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      municipality: new FormControl({ value: null, disabled: false }),
      city: new FormControl({ value: null, disabled: true }),
      street: new FormControl({ value: null, disabled: true }),
      houseNr: new FormControl({ value: null, disabled: true }),
      flatNr: new FormControl({ value: null, disabled: true }),
      coordinates: new FormControl(
        { value: null, disabled: false },
        Validators.compose([Validators.required, NtisInputValidators.validateCoordinates()])
      ),
      immovablePropertyNo: new FormControl({ value: null, disabled: false }),
    });

    this.getSelectOptions();
    this.subscribeFormControls();
    this.changeValidators();
  }

  protected doSave(value: AddressSearch): void | Observable<AddressSearch> {
    if (value) {
      this.searchAddresses(value);
    }
  }

  protected doLoad(): void | Observable<AddressSearch> {
    console.log;
  }
  protected setData(data: AddressSearch): void {
    this.data = data;
    this.form.controls.municipality.setValue(data.municipality || null);
    this.form.controls.city.setValue(data.city || null);
    this.form.controls.street.setValue(data.street || null);
    this.form.controls.houseNr.setValue(data.houseNr || null);
    this.form.controls.flatNr.setValue(data.flatNr || null);
    this.form.controls.longitude.setValue(data.longitude || null);
    this.form.controls.latitude.setValue(data.latitude || null);
    this.form.controls.immovablePropertyNo.setValue(data.immovablePropertyNo || null);
  }
  protected getData(): AddressSearch {
    const coordonates = this.form.controls.coordinates.value as string;

    const result: AddressSearch = this.data != null ? this.data : ({} as AddressSearch);
    result.municipality = this.form.controls.municipality.value as AddressSearch['municipality'];
    result.city = this.form.controls.city.value as AddressSearch['city'];
    result.street = this.form.controls.street.value as AddressSearch['street'];
    result.houseNr = this.form.controls.houseNr.value as AddressSearch['houseNr'];
    result.flatNr = this.form.controls.flatNr.value as AddressSearch['flatNr'];
    result.latitude = coordonates?.split(',')[0].replace(/\s+/g, '');
    result.longitude = coordonates?.split(',')[1].replace(/\s+/g, '');
    result.immovablePropertyNo = this.form.controls.immovablePropertyNo.value as AddressSearch['immovablePropertyNo'];

    return result;
  }
  /**
   *
   *
   *
   * @method subscribeFormControls Called from the buildForm
   * method, all inputs subscribe are placed in the function.
   *
   */

  changeFormGroup(value: SearchMethod): void {
    this.searchMethodValue = value;
    this.clearFormFields();
    this.changeValidators();
  }

  subscribeFormControls(): void {
    this.form.controls.municipality.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: number) => {
      this.form.controls.city.reset();
      this.form.controls.street.reset();
      this.form.controls.houseNr.reset();
      this.form.controls.flatNr.reset();
      if (value) {
        this.form.controls.city.enable();
        this.getCitiesList(value);
      } else {
        this.form.controls.city.disable();
        this.clearFormFields();
      }
    });
    this.form.controls.city.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: number) => {
      this.form.controls.street.reset();
      this.form.controls.houseNr.reset();
      this.form.controls.flatNr.reset();
      if (value) {
        this.form.controls.street.enable();
        this.getStreetsList(value);
      } else {
        this.form.controls.street.disable();
      }
    });
    this.form.controls.street.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: string) => {
      this.form.controls.houseNr.reset();
      this.form.controls.flatNr.reset();
      if (value) {
        this.form.controls.houseNr.enable();
        this.form.controls.flatNr.enable();
      } else {
        this.form.controls.houseNr.disable();
        this.form.controls.flatNr.disable();
      }
    });
  }
  /**
   *
   */
  changeValidators(): void {
    if (this.searchMethodValue === SearchMethod.address) {
      this.form.controls.city.setValidators(Validators.required);
      this.form.controls.municipality.setValidators(Validators.required);
      this.form.controls.street.setValidators(Validators.required);
      this.form.controls.coordinates.clearValidators();
      this.form.controls.immovablePropertyNo.clearValidators();
      this.form.updateValueAndValidity();
      this.form.reset();
    } else if (this.searchMethodValue === SearchMethod.coordinates) {
      this.form.controls.city.clearValidators();
      this.form.controls.municipality.clearValidators();
      this.form.controls.street.clearValidators();
      this.form.controls.coordinates.setValidators(Validators.required);
      this.form.updateValueAndValidity();
      this.form.reset();
    } else if (this.searchMethodValue === SearchMethod.immovablePropertyNo) {
      this.form.controls.city.clearValidators();
      this.form.controls.municipality.clearValidators();
      this.form.controls.street.clearValidators();
      this.form.controls.coordinates.clearValidators();
      this.form.controls.immovablePropertyNo.setValidators(Validators.required);
      this.form.updateValueAndValidity();
      this.form.reset();
    }
  }
  /**
   *
   * @param value
   */
  setAddress(value: AddressSearchResponse): void {
    if (value) {
      this.addressSelect.emit(value);
    }
  }
  /**
   *
   * @param data
   */
  searchAddresses(data: AddressSearch): void {
    data.street === '0' ? (data.street = null) : null;
    this.buildingDataService.searchAddresses(data).subscribe((result: CommonResult<AddressSearchResponse>) => {
      if (result.data) {
        this.showResultDialog = true;
        if (this.searchNtr) {
          this.addressList = result.data.filter((addr) => addr.ntr_building_id);
        } else {
          this.addressList = result.data;
        }
      }
    });
  }
  /**
   *
   * @param data
   */
  loadAddress(adressId: number): void {
    this.buildingDataService.findAddressById(adressId).subscribe((result: AddressSearch[]) => {
      if (result) {
        this.form.controls.municipality.setValue(`${result[0].municipality}`);
        this.form.controls.city.setValue(result[0].city);
        this.form.controls.street.setValue(result[0].street);
        this.form.controls.houseNr.setValue(result[0].houseNr);
        this.form.controls.flatNr.setValue(result[0].flatNr);
      }
    });
  }
  /**
   *
   *
   *
   * @method getStreetsList
   * dsad
   * @param value
   * dasdas
   *
   */
  getStreetsList(residenceCode: number): void {
    this.ntisCommonService.getStreetsList(residenceCode).subscribe((result) => {
      this.streetsList = result;
      if (result.length > 0) {
        this.showStreetRequired = true;
        this.form.controls.street.setValidators(Validators.required);
      } else {
        this.showStreetRequired = false;
        this.form.controls.street.clearValidators();
        this.form.controls.street.updateValueAndValidity();
      }
    });
  }

  /**
   *
   *
   *
   * @method getCitiesList
   * dsad
   * @param value
   * dasdas
   *
   */
  getCitiesList(municipalityCode: number): void {
    this.ntisCommonService.getResidencesList(municipalityCode).subscribe((result) => {
      this.citiesList = result;
    });
  }

  /**
   *
   *
   *
   * @method clearFormFields If the input value of the municipality
   * changes, all other address fields are cleared.
   *
   */
  clearFormFields(): void {
    this.form.controls.city.reset();
    this.form.controls.street.reset();
    this.form.controls.houseNr.reset();
    this.form.controls.flatNr.reset();
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
}
