import {
  AfterContentChecked,
  AfterViewChecked,
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Injector,
  Input,
  OnChanges,
  OnDestroy,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl } from '@angular/forms';
import {
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, debounceTime, fromEvent, map, takeUntil } from 'rxjs';
import { listboxShowUp } from '../../animations/animations';
import { AdvancedSearchParameterStatement, ForeignKeyParams } from '../../model/api/api';
import { CommonService } from '../../services/common.service';
import { FaIconsService } from '../../services/fa-icons.service';
import { SelectOptionType } from '../../types/select';
import { getElementParents } from '../../utils/get-element-parents';

/**
  @Enum SelectTypesEnum
  - single / select only one record,
  - multiple / select multiple options by checking the checkbox
  - groupedSingle / data is displayed according to groups, only one choice is possible
  - groupedMultiple / data is displayed according to groups, several options can be selected 
    by checking the checkbox 
  - autocomplete-single / type is for getting data from the database as a single selection 
  - autocomplete-multiple / autocomplete-single / type is intended for receiving data from a 
    database with the possibility of choosing several variants*/

export enum SelectTypesEnum {
  autoCompleteSingle = 'autocomplete-single',
  autoCompleteMultiple = 'autocomplete-multiple',
  single = 'single',
  multiple = 'multiple',
  groupedSingle = 'grouped-single',
  groupedMultiple = 'grouped-multiple',
}

/** @interface For assigning type to unknown array */
export interface itemType {
  [key: string]: object[];
}

export interface optionList {
  value: string;
  label: string;
}

export interface optionGroup {
  label: string;
  optionList: optionList[];
}

export interface SearchData {
  value: string;
  rowLimit: number;
}

@Component({
  selector: 'spr-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss'],
  animations: [listboxShowUp],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: SelectComponent,
      multi: true,
    },
  ],
})
export class SelectComponent
  implements OnChanges, ControlValueAccessor, OnDestroy, AfterViewInit, AfterContentChecked, AfterViewChecked
{
  readonly selectTypesEnum = SelectTypesEnum;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;

  /** @ViewChild For receiving data from the list filtering input */
  @ViewChild('searchInput') searchInput: ElementRef<HTMLInputElement>;

  /** @ViewChild Get the height of the dropdown, so that I can 
  identify the position and move it up if necessary */
  @ViewChild('combobox') comboboxElement: ElementRef<HTMLElement>;
  @ViewChild('listboxElement') listboxElement: ElementRef<HTMLElement>;
  @ViewChild('inputElement') inputElement: ElementRef<HTMLInputElement>;

  /** @Input Is intended to specify the dropdown type, single, multiple, 
  grouped-single, grouped-multiple */
  @Input() optionType: SelectOptionType = this.selectTypesEnum.single;

  /** @Input Selects the value from the object, if no value is selected 
  the entire object is returned after the selection */
  @Input() optionValue: string = null;

  /** @Input Possibility to create group text template using e.g. &org_department& */
  @Input() groupLabelTemplate: string = null;

  /** @Input Possibility to create selection text template using e.g. &org_dep_name& */
  @Input() labelTemplate: string = null;

  /** @Input Simple object key selection to render text */
  @Input() optionLabel: string = null;

  /** @Input Simple object key selection to render group text */
  @Input() groupLabel: string = null;

  /** @Input Select a key to display the group records contained in it */
  @Input() groupListKey: string;

  /** @Input Data is passed to the select component */
  @Input() optionData: Array<unknown> = [];

  /** @Input Input placeholder */
  @Input() placeholder: string = '';

  /** @Input Specify to start filtering from the entered characters */
  @Input() startFilteringLength: number = 3;

  /** @Input A code is entered if the component is used as a classifier */
  @Input() classifierCode: string = null;

  @Input() customClassifier: boolean = false;
  @Input() classifierFilterParams?: Record<string, string>;

  /** @Input All options can be read only */
  @Input() readonly: boolean = false;

  /** @Input Show filter to list */
  @Input() showFilter: boolean = false;

  /** @Input If the component is used for search, it creates additional parameters for advanced filter */
  @Input() advancedFilterParams: boolean = false;

  /** @Input After entering the text, a query is sent to the database and matching records are returned */
  @Input() autoComplete: boolean = false;

  /** @Input After entering the text, a query is sent to the database and matching records are returned */
  @Input() rowLimit: number = 100;

  /** @Input Designed to bind label and input */
  @Input() inputId: string = null;

  @Input() showClearIcon: boolean = true;
  @Input() delaySearch: number = 1000;

  @Input() showSearchIcon: boolean = false;

  @Input() theme: 'risk' | 'success' | 'warning' | 'info' = undefined;

  /** @Output Emit string value to parent component to search data */
  @Output() searchData = new EventEmitter<ForeignKeyParams>();

  @Output() valuesCleared = new EventEmitter<void>();

  /** @Subject Designed to destroy search input subscribe*/
  destroy$: Subject<boolean> = new Subject();
  selectedOption: optionList[] = [];
  clickedOnListBox: boolean = false;
  showListbox: boolean = false;
  filteredOptions: optionList[] = [];
  optionList: optionList[] = [];
  groupedOptionList: optionGroup[] = [];
  filteredGroupedOptionList: optionGroup[] = [];
  showClear: boolean = false;
  formData: unknown = null;
  disabled: boolean = false;
  isVisible: boolean = false;
  showSpinner: boolean = false;
  inputSearchValue: string = null;
  inputFilterValue: string = null;
  filterThisArray: boolean = false;
  inputTempValue: string = null;
  foundRecords: boolean = true;
  chosenOption: optionList = null;

  private resizeObserver: ResizeObserver;
  private scrollableParents: HTMLElement[] = [];

  onChange: (value: unknown) => void;
  onTouched: () => void;

  constructor(
    public faIconsService: FaIconsService,
    protected commonFormServices: CommonFormServices,
    protected clsfService: CommonService,
    private elementRef: ElementRef,
    private injector: Injector,
    private cdr: ChangeDetectorRef
  ) {}

  ngAfterViewInit(): void {
    this.filterList(this.inputElement?.nativeElement);

    this.comboboxElement?.nativeElement.style.setProperty('--select-width', '0');
    this.resizeObserver = new ResizeObserver((entries) => {
      for (const entry of entries) {
        if (entry.target === this.inputElement.nativeElement) {
          this.comboboxElement?.nativeElement.style.setProperty('--select-width', `${entry.target.scrollWidth}px`);
        }
      }
    });
    this.resizeObserver.observe(this.inputElement.nativeElement);
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
    this.resizeObserver?.disconnect();
    this.toggleShowListBox(false);
  }

  /** @ngAfterContentChecked is used to detect when the dropdown is visible and focus the filter input */
  ngAfterContentChecked(): void {
    this.cdr.detectChanges();

    if (this.showFilter) {
      if (!this.isVisible && this.searchInput?.nativeElement.offsetParent) {
        this.inputElement.nativeElement.blur();
        this.searchInput.nativeElement.focus();
        this.filterList(this.searchInput?.nativeElement);
        this.isVisible = true;
      } else if (this.isVisible && !this.searchInput?.nativeElement.offsetParent) {
        this.inputElement.nativeElement.focus();
        this.isVisible = false;
      }
    }
  }

  ngAfterViewChecked(): void {
    this.adjustDropdownPosition();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.optionData || changes.classifierCode || changes.classifierFilterParams) {
      this.showSpinner = false;
      this.adjustDropdownPosition();
      if (
        this.optionType === this.selectTypesEnum.autoCompleteSingle ||
        this.optionType === this.selectTypesEnum.autoCompleteMultiple
      ) {
        if (this.optionType === this.selectTypesEnum.autoCompleteSingle) {
          this.toggleShowListBox(this.optionData?.length > 0);
        }

        this.optionData = changes.optionData.currentValue as Array<unknown>;

        if (this.optionData?.length < this.rowLimit && this.optionData?.length !== 0) {
          this.filterThisArray = true;
          this.inputTempValue = this.inputElement?.nativeElement.value;
        } else {
          this.filterThisArray = false;
        }

        this.setSingleOptions(changes.optionData.currentValue as Array<unknown>);
      } else {
        this.setOptionData();
      }
    }
  }

  private toggleShowListBox(value?: boolean): void {
    if (typeof value !== 'boolean') {
      value = !this.showListbox;
    }
    const valueChanged = this.showListbox !== value;
    this.showListbox = value;
    if (valueChanged) {
      if (value && this.inputElement) {
        this.scrollableParents = getElementParents(this.inputElement.nativeElement).filter((element) => {
          if (element.nodeType === Node.DOCUMENT_NODE) {
            return false;
          }
          const overflowRegex = /(auto|scroll)/;
          const elementStyle = window.getComputedStyle(element, null);
          return (
            overflowRegex.test(elementStyle.getPropertyValue('overflow')) ||
            overflowRegex.test(elementStyle.getPropertyValue('overflowX')) ||
            overflowRegex.test(elementStyle.getPropertyValue('overflowY'))
          );
        });
        this.scrollableParents.forEach((scrollableParent) => {
          scrollableParent.addEventListener('scroll', this.toggleShowListBox.bind(this, false));
        });
        window.addEventListener('resize', this.adjustDropdownPosition.bind(this));
      } else {
        this.scrollableParents?.forEach((scrollableParent) => {
          scrollableParent?.removeEventListener('scroll', this.toggleShowListBox.bind(this, false));
        });
        window.removeEventListener('resize', this.adjustDropdownPosition.bind(this));
      }
    }
  }

  showDropdown(): void {
    if (this.optionType !== this.selectTypesEnum.autoCompleteSingle) {
      this.toggleShowListBox();
    }
    this.onTouched?.();
  }

  showClearButton(): void {
    this.showClear = (!this.readonly && !this.disabled) || this.inputSearchValue.length > 0;
  }

  /** Method from NG_VALUE_ACCESSOR to retrieve data from the form */
  writeValue(obj: unknown): void {
    const searchParam = obj as ExtendedSearchParam;

    if (searchParam?.paramValue?.value) {
      this.formData = searchParam?.paramValue?.value;
    } else if (searchParam?.paramValue?.values) {
      if (searchParam?.paramValue?.values?.length === 1) {
        this.formData = searchParam?.paramValue?.values[0];
      } else {
        const valueArray: unknown[] = [];
        searchParam?.paramValue?.values.forEach((value) => {
          valueArray.push(value);
          const val = this.optionList.find((item: optionList) => item.value === value);
        });
        this.formData = valueArray;
      }
    } else {
      this.formData = obj;
    }
    if (!this.classifierCode || this.optionList?.length) {
      this.loadFormData();
    }
    this.listenToReset();
    this.showClear = !!this.formData;
  }

  /** Method from NG_VALUE_ACCESSOR to listen for change */
  registerOnChange(fn: never): void {
    this.onChange = fn;
  }

  /** Method from NG_VALUE_ACCESSOR to check if the input has been touched */
  registerOnTouched(fn: never): void {
    this.onTouched = fn;
  }
  /** Method from NG_VALUE_ACCESSOR, make disabled input */
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  /** Loads data from a form by groups if the data matches a list, e.g. (multiple, grouped-multiple) 
  org_id from the form 125, 545, 64 this dropdown selections will be marked 
  if only one matching entry is selected, it will be displayed as a placeholder */
  loadGroupedFormData(data: optionGroup[]): void {
    if (Array.isArray(this.formData)) {
      (this.formData as Array<unknown>).forEach((value: unknown) => {
        if (typeof value === 'object') {
          data.forEach((element) => {
            if (
              element.optionList.find((item: optionList) => this.getValuesFromObject(value, 'label') === item.label) !==
              undefined
            ) {
              this.pushSelectedValues(
                element.optionList.find((item: optionList) => this.getValuesFromObject(value, 'label') === item.label)
              );
            }
          });
        } else {
          data.forEach((element) => {
            if (element.optionList.find((item: optionList) => value === item.value) !== undefined) {
              this.pushSelectedValues(element.optionList.find((item: optionList) => value === item.value));
            }
          });
        }
      });
    } else {
      this.setPlaceholder();
      this.setGroupedPlaceholder();
    }
  }
  /** Loads data from a form if the data matches a list, e.g. (multiple, single-multiple) 
  org_id from the form 125, 545, 64 this dropdown selections will be marked, 
  if only one matching entry is selected, it will be displayed as a placeholder */
  loadFormData(): void {
    if (Array.isArray(this.formData)) {
      (this.formData as Array<unknown>).forEach((value) => {
        if (typeof value === 'object') {
          this.pushSelectedValues(
            this.optionList.find((item) => this.getValuesFromObject(value, 'label') === item.label)
          );
        } else {
          this.pushSelectedValues(this.optionList.find((item) => value === item.value));
        }
      });
    } else {
      this.setPlaceholder();
      this.setGroupedPlaceholder();
    }
  }

  /** If the form was reset, then all data will be reset */
  listenToReset(): void {
    if (!this.formData) {
      this.selectedOption = [];
      this.showClear = false;
      this.filteredOptions = this.optionList;
      this.filteredGroupedOptionList = this.groupedOptionList;
      this.toggleShowListBox(false);
      if (this.inputElement) {
        this.inputElement.nativeElement.value = '';
      }
    }
  }

  /** After clicking on the clear button, all data will be cleared and the input will be made as untouched */
  clearValues(): void {
    if (!this.disabled) {
      this.selectedOption = [];
      this.showClear = false;
      this.filteredOptions = this.optionList;
      this.filteredGroupedOptionList = this.groupedOptionList;
      this.toggleShowListBox(false);
      this.inputSearchValue = null;
      this.inputFilterValue = null;
      this.inputElement.nativeElement.value = '';
      this.onChange?.(null);
      this.valuesCleared.emit();
    }
  }

  /** It is used to check the received data from the form and put check marks */
  checkOption(value: string): boolean {
    for (let i = 0; i < this.selectedOption.length; i++) {
      if (value === this.selectedOption[i].value) {
        return true;
      }
    }
    return false;
  }

  /** Checks whether it is a classifier or data from optionData, and accordingly checks whether it is a grouped type */
  setOptionData(): void {
    if (this.optionType === this.selectTypesEnum.multiple || this.optionType === this.selectTypesEnum.single) {
      if (this.classifierCode) {
        this.clsfService
          .getClsf(this.classifierCode, !this.customClassifier, this.customClassifier, this.classifierFilterParams)
          .subscribe((options) => {
            setTimeout(() => {
              if (options) {
                this.setSingleOptions(options);
                this.loadFormData();
              }
            }, 200);
          });
        this.optionValue = this.optionValue ? this.optionValue : 'key';
        this.optionLabel = this.optionLabel ? this.optionLabel : 'display';
      } else {
        if (this.optionData.length < 1) {
          this.foundRecords = false;
        } else {
          this.foundRecords = true;
        }
        this.setSingleOptions(this.optionData);
      }
    } else if (
      this.optionType === this.selectTypesEnum.groupedMultiple ||
      this.optionType === this.selectTypesEnum.groupedSingle
    ) {
      if (this.optionData.length < 1) {
        this.foundRecords = false;
      } else {
        this.foundRecords = true;
      }
      this.setGroupedOptions();
      this.setGroupedPlaceholder();
    }
  }

  setGroupedPlaceholder(): void {
    this.groupedOptionList.forEach((element) => {
      const row: optionList = element['optionList'].find((item: optionList) => item.value === this.formData);
      if (row !== undefined) {
        if (row.label.length > 0) {
          this.inputSearchValue = row.label;
        }
      }
    });
  }

  setPlaceholder(): void {
    let row = this.optionList.find((item: optionList) => item.value === this.formData);
    if (row) {
      this.inputSearchValue = row.label;
      this.chosenOption = row;
    } else if (typeof this.formData === 'object' && this.formData && 'paramValue' in this.formData) {
      const advanceParam = this.formData as AdvancedSearchParameterStatement;
      if (advanceParam.paramValue?.values?.length > 0) {
        this.inputSearchValue = null;
        advanceParam.paramValue.values.forEach((element) => {
          row = this.optionList.find((item: optionList) => item.value === element);
          this.pushSelectedValues(row);
        });
      } else {
        row = this.optionList.find((item: optionList) => item.value === advanceParam?.paramValue.value);
        this.pushSelectedValues(row);
      }
    }
  }

  /** According to groupListKey, the received groups and the array of that group are 
  transformed into the value, label object array according to the provided optionValue,
  optionLabel, if optionValue is not specified, then the entire object is assigned */
  setGroupedOptions(): void {
    if (this.optionData?.length !== 0 && Object.keys(this.optionData[0]).includes(this.groupListKey)) {
      this.optionData.forEach((items: unknown) => {
        this.groupedOptionList.push({
          label: !this.groupLabelTemplate
            ? this.getValuesFromObject(items, this.groupLabel)
            : this.setLabelTemplate(items, this.groupLabelTemplate),
          optionList: this.setGroupData(items),
        });
      });
      this.filteredGroupedOptionList = this.groupedOptionList;
      this.loadGroupedFormData(this.groupedOptionList);
    }
  }
  /** The method is called from setGroupedOptions to create the group data */
  setGroupData(items: unknown): optionList[] {
    const data: optionList[] = [];
    for (let i = 0; i < (items as itemType)[this.groupListKey].length; i++) {
      data.push({
        value: Object.keys((items as itemType)[this.groupListKey][i]).includes(this.optionValue)
          ? this.getValuesFromObject((items as itemType)[this.groupListKey][i], this.optionValue)
          : ((items as itemType)[this.groupListKey][i] as unknown as string),
        label: !this.labelTemplate
          ? this.getValuesFromObject((items as itemType)[this.groupListKey][i], this.optionLabel)
          : this.setLabelTemplate((items as itemType)[this.groupListKey][i], this.labelTemplate),
      });
    }
    return data;
  }

  @HostListener('click')
  clickInside(): void {
    this.clickedOnListBox = true;
  }

  @HostListener('document:click')
  clickOutside(): void {
    if (!this.clickedOnListBox) {
      this.toggleShowListBox(false);
    }
    this.clickedOnListBox = false;
  }

  /** Recreates data from optionData or classifier according to the provided object keys */
  setSingleOptions(data?: Array<unknown>): void {
    this.optionList = [];
    data?.forEach((items: unknown) => {
      this.optionList.push({
        value: Object.keys(items as object).includes(this.optionValue)
          ? this.getValuesFromObject(items, this.optionValue)
          : (items as string),
        label: !this.labelTemplate
          ? this.getValuesFromObject(items, this.optionLabel)
          : this.setLabelTemplate(items, this.labelTemplate),
      });
    });

    this.setPlaceholder();
    this.filteredOptions = this.optionList;
  }

  /** According to the given object keys e.g. &org_name& will be found and replaced with the appropriate data */
  setLabelTemplate(item: unknown, templalte: string): string {
    let labelTemplate: string = templalte;
    Object.keys(item).forEach((key) => {
      const objectKey = `&${key}&`;
      if (labelTemplate?.search(objectKey) > -1) {
        const searchRegExp = new RegExp(objectKey, 'g');
        labelTemplate = labelTemplate.replace(searchRegExp, this.getValuesFromObject(item, key));
      }
    });
    return labelTemplate;
  }

  /** Receiving data from an object that has an unknown data type */
  getValuesFromObject(items: unknown, key?: string): string {
    let value: string = null;
    for (let i = 0; i < Object.entries(items).length; i++) {
      if (Object.entries(items)[i][0] === key) {
        value = Object.entries(items)[i][1] as string;
      }
    }
    return value;
  }

  /** The method is designed to filter data from the given optionData or classifier */
  filterList(input: HTMLInputElement): void {
    fromEvent(input, 'input')
      .pipe(
        map((event: Event) => (event.target as HTMLInputElement).value),
        debounceTime(this.delaySearch),
        takeUntil(this.destroy$)
      )
      .subscribe((inputValue) => {
        if (this.optionType === this.selectTypesEnum.multiple || this.optionType === this.selectTypesEnum.single) {
          if (inputValue.length >= this.startFilteringLength) {
            this.filteredOptions = this.filter(this.optionList, inputValue);
          } else {
            this.filteredOptions = this.optionList;
          }
        } else if (
          this.optionType === this.selectTypesEnum.groupedMultiple ||
          this.optionType === this.selectTypesEnum.groupedSingle
        ) {
          if (inputValue.length >= this.startFilteringLength) {
            const filteredData: optionGroup[] = [];
            let filteredDataList: optionList[] = [];

            for (let i = 0; i < this.groupedOptionList.length; i++) {
              filteredDataList = this.filter(this.groupedOptionList[i]['optionList'], inputValue);

              if (filteredDataList.length > 0) {
                filteredData.push({
                  label: this.groupedOptionList[i].label,
                  optionList: filteredDataList,
                });
              }
            }
            this.filteredGroupedOptionList = filteredData;
          } else {
            this.filteredGroupedOptionList = this.groupedOptionList;
          }
        } else if (
          this.optionType === this.selectTypesEnum.autoCompleteMultiple ||
          this.optionType === this.selectTypesEnum.autoCompleteSingle
        ) {
          if (inputValue.length >= this.startFilteringLength) {
            this.filteredOptions = [];

            if (!this.filterThisArray) {
              this.searchDataEmit(inputValue);
            } else {
              if (!inputValue.includes(this.inputTempValue) && this.inputTempValue !== inputValue) {
                this.searchDataEmit(inputValue);
              } else {
                this.filteredOptions = this.filter(this.optionList, inputValue);
                this.showListbox = true;
              }
            }
          }
        }
      });
  }

  /* Emit data to parent component */
  searchDataEmit(inputValue: string): void {
    this.showSpinner = true;
    this.searchData.emit({
      filterValue: inputValue,
      filterValueModified4Search: null,
      recordCount: this.rowLimit,
    });
  }

  /** Search from an array */
  filter(data: optionList[], value: string): optionList[] {
    return data.filter((element: optionList) => {
      return this.replaceLithuanianLetters(element.label.toLowerCase()).search(
        this.replaceLithuanianLetters(value.toLowerCase())
      ) > -1
        ? element
        : null;
    });
  }

  replaceLithuanianLetters(inputString: string): string {
    const letterMap: { [key: string]: string } = {
      ą: 'a',
      č: 'c',
      ę: 'e',
      ė: 'e',
      į: 'i',
      š: 's',
      ų: 'u',
      ū: 'u',
      ž: 'z',
    };

    return inputString.replace(/[ąčęėįšųūž]/g, (match) => letterMap[match]);
  }

  clearSearchInput(): void {
    this.searchInput.nativeElement.value = '';
    this.filteredOptions = this.optionList;
    this.filteredGroupedOptionList = this.groupedOptionList;
  }

  /** After selecting an entry from the list, the data is added to the form */
  optionSelected(row: optionList): void {
    this.chosenOption = row;
    this.inputElement.nativeElement.value = row.label;
    if (!this.readonly) {
      this.inputSearchValue = row.label;
      this.toggleShowListBox(false);

      if (this.advancedFilterParams) {
        const extendedSearchParam: ExtendedSearchParam = {
          paramName: this.injector?.get(NgControl)?.name as string,
          paramValue: {
            condition: ExtendedSearchCondition.Contains,
            value: row.value,
            upperLower: ExtendedSearchUpperLower.Regular,
          },
        };
        this.onChange?.(extendedSearchParam);
      } else {
        this.onChange?.(row.value);
      }
      this.showClear = true;
    }
  }

  /** If multiple or grouped-multiple is used, when the record is selected, it is added to the 
  array and adds the data to the form */
  pushSelectedValues(value: optionList): void {
    const pushedTitles: string[] = [];
    const selectedMultipleValues: string[] = [];

    const index = this.selectedOption.findIndex((element: optionList) => {
      return element?.value === value?.value;
    });

    index === -1 ? this.selectedOption.push(value) : this.selectedOption.splice(index, 1);

    this.selectedOption.forEach((element) => {
      pushedTitles.push(element?.label);
      selectedMultipleValues.push(element?.value);
    });

    this.inputSearchValue = pushedTitles.join(', ');

    if (this.advancedFilterParams) {
      const extendedSearchParam: ExtendedSearchParam = {
        paramName: this.injector?.get(NgControl)?.name as string,
        paramValue: {
          condition: ExtendedSearchCondition.Contains,
          values: selectedMultipleValues,
          upperLower: ExtendedSearchUpperLower.Regular,
        },
      };
      this.formData = extendedSearchParam;
      this.onChange?.(extendedSearchParam);
    } else {
      this.formData = selectedMultipleValues;
      this.onChange?.(selectedMultipleValues);
    }
    this.showClear = !!this.formData;
  }

  adjustDropdownPosition(): void {
    if (this.listboxElement?.nativeElement) {
      const element = this.listboxElement.nativeElement;
      let startLeft = 0,
        startTop = 0;
      const container = getElementParents(element)
        .filter((parentElement) => parentElement.nodeType === Node.ELEMENT_NODE)
        .find((parent) => {
          const containerType = getComputedStyle(parent).getPropertyValue('container-type');
          return containerType === 'size' || containerType === 'inline-size';
        });
      if (container) {
        const containerRect = container.getBoundingClientRect();
        startLeft = -containerRect.left;
        startTop = -containerRect.top;
      }
      const elementHeight = element.offsetHeight;
      const parentRect = this.inputElement.nativeElement.getBoundingClientRect();
      const windowScrollTop = window.scrollY - (document.documentElement.clientTop || 0);
      if (parentRect.bottom + elementHeight > window.innerHeight) {
        element.style.top = `${Math.max(
          startTop + parentRect.top + windowScrollTop - elementHeight,
          windowScrollTop
        )}px`;
      } else {
        element.style.top = `${startTop + parentRect.bottom + windowScrollTop}px`;
      }
      element.style.left = `${startLeft + parentRect.left}px`;
    }
  }
}
