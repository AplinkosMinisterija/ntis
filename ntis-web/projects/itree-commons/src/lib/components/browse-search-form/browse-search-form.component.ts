import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { FaIconsService } from '../../services/fa-icons.service';
import { ExtendedSearchCondition, ExtendedSearchParam, ExtendedSearchUpperLower } from '@itree/ngx-s2-commons';
import {
  PREDEFINED_FILTER_CONTROL_NAME,
  QUICK_SEARCH_CONTROL_NAME,
  SPR_FORMS_DATA_FILTER,
} from '@itree-commons/src/constants/forms.constants';
import { CommonService } from '../../services/common.service';
import { AdvancedSearchParameterStatement, PredefinedFilter, PredefinedFilterRestDataModel } from '../../model/api/api';
import { AuthService } from '../../services/auth.service';
import { ActionsEnum } from '../../enums/table-row-actions.enums';

@Component({
  selector: 'spr-browse-search-form',
  templateUrl: './browse-search-form.component.html',
  styleUrls: ['./browse-search-form.component.scss'],
})
export class BrowseSearchFormComponent implements OnInit {
  detailedSearchOpen: boolean = false;

  quickSearchValue: string = '';
  predefinedFilterValue: string | null = null;
  predefinedFilters: PredefinedFilter[] = [];
  selectedFilters: string[] = [];
  selectedPredefinedFiltersName: string;

  @Input() formGroup: FormGroup;
  @Input() quickSearchControlName: string = QUICK_SEARCH_CONTROL_NAME;
  @Input() quickSearchEnabled: boolean = true;
  @Input() quickSearchCondition: ExtendedSearchCondition = ExtendedSearchCondition.Contains;
  @Input() quickSearchUpperLower: ExtendedSearchUpperLower = ExtendedSearchUpperLower.CaseInsensitiveLatin;
  @Input() predefinedFilterControlName = PREDEFINED_FILTER_CONTROL_NAME;
  @Input() formCode: string | null = null;
  @Input() placeholder: string;
  @Input() detailedSearchEnabled: boolean = true;
  showSaveFilter: boolean = false;

  @Output() search = new EventEmitter();
  constructor(
    public faIconsService: FaIconsService,
    private commonService: CommonService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.showSaveFilter = this.authService.isFormActionEnabled(
      SPR_FORMS_DATA_FILTER,
      ActionsEnum.GLOBAL_FILTER_MANAGER
    );

    if (this.formGroup) {
      if (!this.formGroup.controls[this.quickSearchControlName]) {
        this.formGroup.addControl(this.quickSearchControlName, new FormControl());
      }

      if (this.formCode && !this.formGroup.controls[this.predefinedFilterControlName]) {
        this.formGroup.addControl(this.predefinedFilterControlName, new FormControl());
        this.loadFormPredefinedData();
        this.formGroup.controls[this.predefinedFilterControlName].valueChanges.subscribe((res) =>
          this.setFilters(res as AdvancedSearchParameterStatement)
        );
      }

      this.formGroup.controls[this.quickSearchControlName].valueChanges.subscribe((value) => {
        const searchParamValue = value as ExtendedSearchParam;
        if (!this.quickSearchValue && searchParamValue?.paramValue?.value) {
          this.quickSearchValue = searchParamValue.paramValue.value;
        }
      });

      this.formGroup.statusChanges.subscribe(() => this.countSelectedFilters());
    }
  }

  loadFormPredefinedData(): void {
    this.commonService.getFormPredefinedData(this.formCode).subscribe((result) => {
      this.predefinedFilters = result.predefinedFilters;
    });
  }

  onSearch(): void {
    this.countSelectedFilters();
    this.search.emit();
  }

  onClickReset(): void {
    this.formGroup.reset();
    this.selectedFilters = [];
    this.selectedPredefinedFiltersName = null;
    this.search.emit();
  }

  setFilters(filter: AdvancedSearchParameterStatement): void {
    const listRec = this.predefinedFilters.find((rec) => rec.id === filter?.paramValue?.value);
    if (listRec?.filterContent) {
      const advenceFilters = JSON.parse(listRec.filterContent) as PredefinedFilterRestDataModel;
      this.selectedPredefinedFiltersName = listRec.filterName;
      Object.keys(this.formGroup.controls)
        .filter((cn) => this.predefinedFilterControlName !== cn)
        .forEach((controlName) => {
          const controlVal = advenceFilters?.extendedParams.find((param) => param.paramName === controlName);
          const setControl = (controlName: string, obj: AdvancedSearchParameterStatement): void => {
            this.formGroup.controls[controlName].setValue(null);
            this.formGroup.controls[controlName].setValue(obj);
            this.formGroup.controls[controlName].updateValueAndValidity({ onlySelf: true, emitEvent: false });
          };
          setControl(controlName, controlVal);
        });
      this.search.emit();
    }
  }

  handleQuickSearchChange(value: string): void {
    if (this.formGroup?.controls?.[this.quickSearchControlName]) {
      const extendedSearchParam: ExtendedSearchParam = {
        paramName: this.quickSearchControlName,
        paramValue: {
          condition: this.quickSearchCondition,
          value: value,
          upperLower: this.quickSearchUpperLower,
        },
      };
      this.formGroup.controls[this.quickSearchControlName].setValue(extendedSearchParam);
    }
  }

  toggleDetailedSearch(): void {
    this.countSelectedFilters();
    this.detailedSearchOpen = !this.detailedSearchOpen;
    if (this.detailedSearchOpen) {
      this.quickSearchValue = null;
      this.formGroup.controls[this.quickSearchControlName].setValue(null);
    }
  }

  countSelectedFilters(): void {
    Object.keys(this.formGroup.controls).forEach((key) => {
      if (key !== this.predefinedFilterControlName && key !== this.quickSearchControlName) {
        if (typeof this.formGroup.controls[key].value === 'string') {
          if (this.formGroup.controls[key].value && !this.selectedFilters.includes(key)) {
            this.selectedFilters.push(key);
          } else if (!this.formGroup.controls[key].value && this.selectedFilters.includes(key)) {
            this.selectedFilters = this.selectedFilters.filter((obj) => obj !== key);
          }
        } else {
          const param = this.formGroup.controls[key].value as ExtendedSearchParam;
          if ((param?.paramValue?.value || param?.paramValue['values']) && !this.selectedFilters.includes(key)) {
            this.selectedFilters.push(key);
          } else if (!param?.paramValue['values'] && !param?.paramValue.value && this.selectedFilters.includes(key)) {
            this.selectedFilters = this.selectedFilters.filter((obj) => obj !== key);
          }
        }
      }
    });
  }
}
