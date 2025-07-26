import {
  Component,
  DestroyRef,
  ElementRef,
  HostListener,
  Injector,
  Input,
  OnDestroy,
  OnInit,
  SkipSelf,
  ViewChild,
  inject,
} from '@angular/core';
import { ControlContainer, ControlValueAccessor, FormGroup, NG_VALUE_ACCESSOR, NgControl } from '@angular/forms';
import { CommonFormServices, ExtendedSearchCondition, ExtendedSearchUpperLower } from '@itree/ngx-s2-commons';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { fadeInFields } from '../../animations/animations';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FilterInputType } from '../../enums/input-type.enums';
import { Subscription, of } from 'rxjs';
import { AdvancedSearchParameterStatement } from '../../model/api/api';
import { SelectComponent, SelectTypesEnum } from '../select/select.component';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

export interface FilterConditions {
  title: string;
  condition: string;
}

interface CalendarItem {
  date: Date;
  day: number;
  isTargetMonth: boolean;
  isCurrentDay?: boolean;
  inRange?: boolean;
}

interface YearsRange {
  yearStart: number;
  yearEnd: number;
}

interface MonthListItem {
  value: number;
  selected: boolean;
}

interface DateRange {
  dateFrom: string;
  dateTo: string;
}

@Component({
  selector: 'spr-filter-input',
  templateUrl: './filter-input.component.html',
  styleUrls: ['./filter-input.component.scss'],
  animations: [fadeInFields],
  viewProviders: [
    {
      provide: ControlContainer,
      useFactory: (container: ControlContainer): ControlContainer => container,
      deps: [[new SkipSelf(), ControlContainer]],
    },
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: FilterInputComponent,
      multi: true,
    },
  ],
})
export class FilterInputComponent implements OnInit, OnDestroy, ControlValueAccessor {
  readonly inputTypeEnum = FilterInputType;
  readonly selectTypesEnum = SelectTypesEnum;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;

  /*
   * @ViewChild
   * inputElement - nativeElement are obtained from the main input
   * inputDateFromElement - get nativeElement from "Date from" input
   * inputDateToElement - get nativeElement from "Date to" input
   * inputHoursElement - get nativeElement from hours input
   * inputMinutesElement - get nativeElement from minutes input
   */
  @ViewChild('inputElement') inputElement: ElementRef<HTMLInputElement>;
  @ViewChild('inputDateFromElement') inputDateFromElement: ElementRef<HTMLInputElement>;
  @ViewChild('inputDateToElement') inputDateToElement: ElementRef<HTMLInputElement>;
  @ViewChild('selectElement') selectElement: ElementRef<SelectComponent>;
  @ViewChild('inputHoursElement') inputHoursElement: ElementRef<HTMLInputElement>;
  @ViewChild('inputMinutesElement') inputMinutesElement: ElementRef<HTMLInputElement>;

  /*
   * @Input
   * inputType - input field type, number, text, date, datetime
   * maxLength - maximum number of characters possible
   * minLength - minimal number of characters possible
   * placeholder - field name display
   * id - input id
   * textTransform - passed to the backend, whether all lowercase or all uppercase or normal - regular,
   * showFilterConditions - if false, defaultFilterCondition should be defined
   */
  @Input() inputType: 'text' | 'number' | 'date' | 'datetime' | 'select' = FilterInputType.text;
  @Input() maxLength: number = 300;
  @Input() minLength: number = 0;
  @Input() placeholder: string = null;
  @Input() inputId: string;
  @Input() disabled: boolean = false;
  @Input() textTransform: ExtendedSearchUpperLower = ExtendedSearchUpperLower.CaseInsensitiveLatin;
  @Input() maxDate: Date = new Date('3000-12-31');
  @Input() minDate: Date = new Date('0001-01-01');
  @Input() showFilterConditions: boolean = true;
  @Input() defaultFilterCondition: ExtendedSearchCondition;
  @Input() useInForm: boolean = false;
  @Input() inputConditionList: string[] = [];
  @Input() manualDate: boolean = true;

  /*
   * @Variables
   * showContent - show or hide the list of conditions
   * inputConditionList - the array is populated depending on whether the input type is text or number
   * form - Designed to achieve parental shape controls
   * selectedConditionValue - the value of the selected condition
   * inputConditionList - list of conditions
   * pickYears - after selecting the year changes to true and shows the months
   * clearButton - field clear button
   */

  public form: FormGroup;
  public subscription: Subscription;

  showContent: boolean = false;
  selectedConditionTitle: string = null;
  selectedConditionValue: string = null;
  pickYears: boolean = false;
  clearButton: boolean = false;
  paramObj: AdvancedSearchParameterStatement;

  /*
    More = '>'
    Less = '<'
    Equals = '='
    NotEqual = '!='
    StartsWith = '%-'
    EndsWith = '-%'
    Contains = '%-%'
    Empty = 'null'
    NotEmpty = 'notnull'
  */

  numberConditions: string[] = [
    ExtendedSearchCondition.More,
    ExtendedSearchCondition.Less,
    ExtendedSearchCondition.Equals,
    ExtendedSearchCondition.NotEqual,
    ExtendedSearchCondition.Empty,
    ExtendedSearchCondition.NotEmpty,
  ] as ExtendedSearchCondition[];

  textConditions: string[] = [
    ExtendedSearchCondition.Equals,
    ExtendedSearchCondition.StartsWith,
    ExtendedSearchCondition.EndsWith,
    ExtendedSearchCondition.Contains,
    ExtendedSearchCondition.Empty,
    ExtendedSearchCondition.NotEmpty,
  ] as ExtendedSearchCondition[];
  currentDate: Date = new Date();

  dateConditions: string[] = [
    ExtendedSearchCondition.More,
    ExtendedSearchCondition.Less,
    ExtendedSearchCondition.Equals,
    ExtendedSearchCondition.NotEqual,
    ExtendedSearchCondition.Empty,
    ExtendedSearchCondition.NotEmpty,
    ExtendedSearchCondition.Period,
  ] as ExtendedSearchCondition[];

  /*
   *@Calendar variables
   */
  calendarYear: number;
  calendarMonth: number;
  calendarItems: CalendarItem[] = [];
  selectedItem: number = -1;
  weekDays = Array(7);
  selectedDate: Date;
  minutes: string = '0';
  hours: string = '0';
  maxRange: number = 1439;
  minRange: number = 0;
  currentRange: number = 720;
  monthList: MonthListItem[] = [];
  yearsList: number[] = [];
  yearsToMonth: boolean = false;
  currentYear = Number(new Date().getFullYear());
  currentYearLabel: string = null;
  inputFocused: boolean = false;
  inputValue: string = null;
  inputValues: string = null;
  activeInput: 'dateFrom' | 'dateTo' = undefined;
  isDateRangeOpen: boolean = false;
  groupedYears: YearsRange[] = [
    {
      yearStart: 1800,
      yearEnd: 1809,
    },
  ];
  dateRange: DateRange = {
    dateFrom: null,
    dateTo: null,
  };

  destroyRef = inject(DestroyRef);

  private onChange: (value: AdvancedSearchParameterStatement) => void;
  private onTouched: () => void;

  constructor(
    public faIconsService: FaIconsService,
    private controlContainer: ControlContainer,
    protected commonFormServices: CommonFormServices,
    private datePipe: S2DatePipe,
    private elementRef: ElementRef<Element>,
    private injector: Injector
  ) {
    this.subscription = new Subscription();
    of(true).pipe(takeUntilDestroyed()).subscribe();
  }

  writeValue(value: AdvancedSearchParameterStatement): void {
    this.paramObj = value;
    if (value) {
      this.inputValue = value.paramValue.value;
      // this.inputValues = value.paramValue.values
      this.setSelectedCondition(value.paramValue.condition || this.defaultFilterCondition);
      this.textTransform = value.paramValue.upperLower as ExtendedSearchUpperLower;
      if (this.selectedConditionValue === ExtendedSearchCondition.Period && value.paramValue.values) {
        this.dateRange.dateFrom = value.paramValue.values[0];
        this.dateRange.dateTo = value.paramValue.values[1];
        this.inputValues = `${this.dateRange.dateFrom} - ${this.dateRange.dateTo}`;
        this.recalculateCalendarItemsRange();
      }
    } else {
      this.inputValue = null;
      this.setSelectedCondition(this.defaultFilterCondition);
      this.textTransform = null;
    }
  }

  registerOnChange(fn: (value: AdvancedSearchParameterStatement) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  ngOnInit(): void {
    this.setConditions();
    this.loadCalendarData();
    this.hours = this.addZero(Math.floor(this.currentRange / 60));
    this.minutes = this.addZero(this.currentRange % 60);

    for (let i = 1; i <= 12; i++) {
      this.monthList.push({ value: i, selected: i === new Date().getMonth() + 1 });
    }

    this.subscription.add(
      this.controlContainer.valueChanges
        .pipe(takeUntilDestroyed(this.destroyRef))
        .subscribe((control: ControlContainer) => {
          const isEmpty = !Object.values(control).some(
            (value) => value !== null && value !== '' && value !== undefined
          );
          if (isEmpty) this.clearInputField();
        })
    );
    if (this.inputType === FilterInputType.text && !this.defaultFilterCondition) {
      this.selectedConditionValue = ExtendedSearchCondition.Contains;
    }
    if (this.defaultFilterCondition) {
      this.onClickSetConditionValue(this.defaultFilterCondition);
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  // /* when the input box is opened, it then closes before it is opened */
  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (
      !this.elementRef.nativeElement?.contains(targetElement) &&
      !targetElement.classList.contains('prevent-to-close')
    ) {
      this.showContent = false;
      if (this.dateRange.dateFrom && this.dateRange.dateTo) {
        this.inputValues = `${this.dateRange.dateFrom} - ${this.dateRange.dateTo} `;
      } else {
        this.inputValues = null;
        this.dateRange.dateFrom = null;
        this.dateRange.dateTo = null;
      }
    }
  }

  recalculateCalendarItemsRange(): void {
    const dateFrom = this.dateRange.dateFrom && new Date(this.dateRange.dateFrom);
    dateFrom?.setHours(0);
    const dateTo = this.dateRange.dateTo && new Date(this.dateRange.dateTo);
    dateTo?.setHours(0);
    this.calendarItems.forEach((item) => {
      item.inRange = dateFrom && dateTo && item.date >= dateFrom && item.date <= dateTo;
    });
  }

  /*  An object is created from the selected condition and input
  and assigned according to the formControl name */
  createFormObject(): void {
    if (this.selectedConditionValue === '[]' && this.dateRange.dateFrom && this.dateRange.dateTo) {
      this.recalculateCalendarItemsRange();
    }

    if (this.paramObj) {
      this.paramObj.paramValue.value =
        this.selectedConditionValue !== ExtendedSearchCondition.Period ? this.inputValue : null;
      this.paramObj.paramValue.values =
        this.selectedConditionValue === ExtendedSearchCondition.Period
          ? [this.dateRange.dateFrom, this.dateRange.dateTo]
          : null;

      this.paramObj.paramValue.upperLower =
        this.textTransform !== null ? this.textTransform : ExtendedSearchUpperLower.CaseInsensitiveLatin;

      if (this.inputType === FilterInputType.text) {
        this.paramObj.paramValue.condition =
          this.selectedConditionValue !== null ? this.selectedConditionValue : ExtendedSearchCondition.Contains;
      } else {
        this.paramObj.paramValue.condition =
          this.selectedConditionValue !== null ? this.selectedConditionValue : ExtendedSearchCondition.Equals;
      }

      if (this.inputValue?.length > 0) {
        this.clearButton = true;
      }
      this.onChange?.(this.paramObj);
    } else {
      if (this.inputValue) {
        this.clearButton = true;
        if (
          this.inputType === FilterInputType.date ||
          this.inputType === FilterInputType.datetime ||
          this.inputType === FilterInputType.number
        ) {
          this.selectedConditionValue = this.selectedConditionValue
            ? this.selectedConditionValue
            : ExtendedSearchCondition.Equals;
        }
      }
      if (this.inputValue || (this.dateRange.dateFrom && this.dateRange.dateTo)) {
        this.paramObj = {
          paramName: this.injector?.get(NgControl)?.name?.toString(),
          paramValue: {
            condition:
              this.selectedConditionValue !== null ? this.selectedConditionValue : ExtendedSearchCondition.Contains,
            value: this.selectedConditionValue !== ExtendedSearchCondition.Period ? this.inputValue : null,
            values:
              this.selectedConditionValue === ExtendedSearchCondition.Period
                ? [this.dateRange.dateFrom, this.dateRange.dateTo]
                : [],
            upperLower:
              this.textTransform !== null ? this.textTransform : ExtendedSearchUpperLower.CaseInsensitiveLatin,
          },
        };
        this.onChange?.(this.paramObj);
      }
    }
  }

  clearInvalid(dateValue: string): void {
    if (!this.inputValue || (!this.dateRange.dateFrom && !this.dateRange.dateTo)) {
      return null;
    }
    if (
      new Date(this.dateRange.dateFrom) > new Date(this.dateRange.dateTo) &&
      this.inputType === this.inputTypeEnum.datetime
    ) {
      this.inputValues = null;
      this.dateRange.dateFrom = null;
      this.dateRange.dateTo = null;
    }

    const dataTimeArray = dateValue?.split(' ');
    if (dataTimeArray?.length === 2) {
      const REGEX = /^\d{2}:\d{2}:\d{2}$/;
      if (dataTimeArray && !REGEX.test(dataTimeArray[1])) {
        this.inputValue = null;
        this.inputValues = null;
        this.dateRange.dateFrom = null;
        this.dateRange.dateTo = null;
      } else if (this.dateRange.dateFrom > this.dateRange.dateTo) {
        this.inputValues = null;
        this.dateRange.dateFrom = null;
        this.dateRange.dateTo = null;
      }
      this.createFormObject();
    } else {
      const REGEX = /^\d{4}-\d{2}-\d{2}$/;
      if (dataTimeArray && !REGEX.test(dataTimeArray[0])) {
        this.inputValue = null;
        this.inputValues = null;
        this.dateRange.dateFrom = null;
        this.dateRange.dateTo = null;
      } else if (this.dateRange.dateFrom > this.dateRange.dateTo) {
        this.inputValues = null;
        this.dateRange.dateFrom = null;
        this.dateRange.dateTo = null;
      }
      this.createFormObject();
    }
  }

  validateDateInput(event: Event): void {
    let value = (event.target as HTMLInputElement).value;
    const previousValue = (event.target as HTMLInputElement).getAttribute('previousValue') || '';
    const currentCursorPosition = (event.target as HTMLInputElement).selectionStart;
    const years = value.slice(0, 4);
    const months = value.slice(5, 7);
    const days = value.slice(8, 10);

    const hours = value.slice(11, 13);
    const minutes = value.slice(14, 16);
    const seconds = value.slice(17, 19);

    // if editing
    if (currentCursorPosition < value.length) {
      switch (currentCursorPosition) {
        case 1:
        case 2:
        case 3:
        case 4:
          value = value.length < 4 ? value : '';
          break;
        case 6:
          value =
            value.length === 7
              ? previousValue.length > value.length
                ? value.slice(0, 6)
                : value.slice(0, 8) + '-'
              : years + '-';
          break;
        case 5:
        case 7:
          value = value.length > 6 ? years + '-' : '';
          break;
        case 8:
          value = previousValue.length > value.length && value.length > 8 ? value.slice(0, 7) + '-' : years + '-';
          break;
        case 9:
          value =
            value.length === 10
              ? previousValue.length > value.length
                ? value.slice(0, 9)
                : value.slice(0, 10) + ' '
              : value.slice(0, 8);
          break;
        case 10:
          value = value.slice(0, 7) + '-';
          break;
        case 11:
          value = value.length < 13 ? value.slice(0, 7) + '-' : value.slice(0, 10) + ' ';
          break;
        case 12:
        case 13:
          value =
            value.length === 13
              ? previousValue.length > value.length
                ? value.slice(0, 12)
                : value.slice(0, 14) + ':'
              : value.slice(0, 11);
          break;
        case 14:
          value = previousValue.length >= value.length ? value.slice(0, 14) : value.slice(0, 10) + ' ';
          break;
        case 15:
          value =
            value.length === 16
              ? previousValue.length > value.length
                ? value.slice(0, 15)
                : value.slice(0, 16) + ':'
              : value.slice(0, 14);
          break;
        case 16:
          value = value.slice(0, 14);
          break;
        case 17:
          value = previousValue.length > value.length ? value.slice(0, 17) : value.slice(0, 14);
          break;
      }
      //if not editing
    } else {
      switch (value.length) {
        case 4:
          value = previousValue.length > value.length ? value.slice(0, 3) : value + '-';
          break;
        case 7:
          value = previousValue.length > value.length ? value.slice(0, 6) : value + '-';
          break;
        case 10:
          value =
            previousValue.length > value.length
              ? value.slice(0, 9)
              : this.inputType === this.inputTypeEnum.date
              ? value
              : value + ' ';
          break;
        case 13:
          value = previousValue.length > value.length ? value.slice(0, 12) : value + ':';
          break;
        case 16:
          value = previousValue.length > value.length ? value.slice(0, 15) : value + ':';
          break;
      }
    }

    // validation
    const currentDate = new Date();
    if (value.length === 5 && (Number(years) < 1 || Number(years) > 3000)) {
      value = currentDate.getFullYear().toString().padStart(4, '0') + '-';
    }

    if (value.length === 8 && (Number(months) < 1 || Number(months) > 12)) {
      value = years + '-' + (currentDate.getMonth() + 1).toString().padStart(2, '0') + '-';
    }

    if (value.length === 11 && (Number(days) < 1 || Number(days) > 31)) {
      value = value.slice(0, 8) + currentDate.getDate().toString().padStart(2, '0') + ' ';
    } else if (
      this.inputType === this.inputTypeEnum.date &&
      value.length === 10 &&
      (Number(days) < 1 || Number(days) > 31)
    ) {
      value = value.slice(0, 8) + currentDate.getDate().toString().padStart(2, '0');
    }

    if (value.length === 14 && Number(hours) > 23) {
      value = value.slice(0, 11) + '00:';
    }

    if (value.length === 17 && Number(minutes) > 59) {
      value = value.slice(0, 14) + '00:';
    }

    if (value.length === 19 && Number(seconds) > 59) {
      value = value.slice(0, 17) + '00';
    }
    if (this.activeInput === 'dateFrom') {
      this.dateRange.dateFrom = value;
    } else if (this.activeInput === 'dateTo') {
      this.dateRange.dateTo = value;
    }
    this.inputValue = value;
    (event.target as HTMLInputElement).setAttribute('previousValue', value);
    this.syncCalendarWithInputDate(value);
  }

  onKeypressFilterDate(event: KeyboardEvent): boolean {
    const allowedKeys: string[] = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    const allowedDays: string[] = ['0', '1', '2', '3'];
    const allowedMonths: string[] = ['0', '1'];

    const target = event.target as HTMLInputElement;
    const value = target.value;

    const currentCursorPosition = target.selectionStart;
    if (allowedKeys.includes(event.key)) {
      if (currentCursorPosition === 5 && !allowedMonths.includes(event.key)) {
        return false;
      }
      if (currentCursorPosition === 6 && value?.[5] === '1' && !allowedDays.slice(0, 3).includes(event.key)) {
        return false;
      }
      if (
        currentCursorPosition === 8 &&
        (!allowedDays.includes(event.key) || (value?.length > 8 && !allowedDays.slice(0, 3).includes(event.key)))
      ) {
        return false;
      }
      if (currentCursorPosition === 9 && value?.[8] === '3' && !allowedMonths.includes(event.key)) {
        return false;
      }
      if (currentCursorPosition === 11 && !allowedDays.slice(0, 3).includes(event.key)) {
        return false;
      }
      if (currentCursorPosition === 12 && value?.[11] === '2' && !allowedDays.includes(event.key)) {
        return false;
      }
      if (
        (currentCursorPosition === 14 || currentCursorPosition === 17) &&
        !allowedKeys.slice(0, 6).includes(event.key)
      ) {
        return false;
      }
      return true;
    }
    return false;
  }

  /*  Checks the input type and adds conditions accordingly */
  setConditions(): void {
    if (this.inputConditionList.length === 0) {
      switch (this.inputType) {
        case this.inputTypeEnum.number:
          this.inputConditionList = this.numberConditions;
          break;
        case this.inputTypeEnum.text:
          this.inputConditionList = this.textConditions;
          break;
        case this.inputTypeEnum.date:
        case this.inputTypeEnum.datetime:
          this.inputConditionList = this.dateConditions;
          break;
      }
    }
  }

  /*  Filters the entered characters only returns numbers */
  onKeypressFilterChar(event: KeyboardEvent): boolean {
    const allowedKeys: string[] = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Enter'];
    if (allowedKeys.includes(event.key)) {
      return true;
    }
    return false;
  }

  setSelectedCondition(condition: string): void {
    this.selectedConditionValue = condition || null;
    if (condition === '=') {
      condition = 'equals';
    } else if (condition === '!=') {
      condition = 'notEquals';
    }
    if (condition) {
      this.commonFormServices.translate.get('common.conditions.' + condition).subscribe((translation: string) => {
        this.selectedConditionTitle = translation;
      });
    } else {
      this.selectedConditionTitle = null;
    }
  }

  onClickSetConditionValue(value: string): void {
    this.setSelectedCondition(value);

    if (value === ExtendedSearchCondition.Period) {
      this.isDateRangeOpen = !this.isDateRangeOpen;
    } else {
      this.isDateRangeOpen = false;
      this.dateRange.dateFrom = null;
      this.dateRange.dateTo = null;
      this.inputValues = null;
    }

    if (value === ExtendedSearchCondition.Empty || value === ExtendedSearchCondition.NotEmpty) {
      this.inputValue = null;
      this.inputValues = null;
      this.clearButton = true;
      if (this.inputElement) {
        this.inputElement.nativeElement.disabled = true;
      }
    } else {
      this.clearButton = true;
      if (this.inputElement) {
        this.inputElement.nativeElement.disabled = false;
        this.inputElement.nativeElement.focus();
      }
    }

    if (this.inputType !== this.inputTypeEnum.datetime && this.inputType !== this.inputTypeEnum.date) {
      this.showContent = false;
    }
    this.createFormObject();
  }

  /* Shows the last month */
  onClickOlderMonth(): void {
    if (this.calendarYear && (this.calendarMonth || this.calendarMonth === 0)) {
      this.loadCalendarData(
        new Date(this.calendarYear, this.calendarMonth - 1, 1).getFullYear(),
        new Date(this.calendarYear, this.calendarMonth - 1, 1).getMonth()
      );
    }
  }

  /* Shows next month */
  onClickNewerMonth(): void {
    if (this.calendarYear && (this.calendarMonth || this.calendarMonth === 0)) {
      this.loadCalendarData(
        new Date(this.calendarYear, this.calendarMonth + 1, 1).getFullYear(),
        new Date(this.calendarYear, this.calendarMonth + 1, 1).getMonth()
      );
    }
  }

  /* Generates days by year and month */
  loadCalendarData(year?: number, month?: number): void {
    this.calendarItems = [];
    this.selectedItem = -1;

    if (!year || (!month && month !== 0)) {
      year = this.currentDate.getFullYear();
      month = this.currentDate.getMonth();
    }
    this.calendarYear = year;
    this.calendarMonth = month;
    const lastDayDate = new Date(year, month + 1, 0);
    const thisMonthLastDay = lastDayDate.getDate();
    for (let thisMonthDay = 1; thisMonthDay <= thisMonthLastDay; thisMonthDay++) {
      const dayDate = new Date(year, month, thisMonthDay);
      const dayWeekDay = dayDate.getDay() === 0 ? 6 : dayDate.getDay() - 1;
      if (thisMonthDay === 1 && dayWeekDay > 0) {
        const prevMonthLastDay = new Date(year, month, 0).getDate();
        for (let prevMonthDay = prevMonthLastDay - (dayWeekDay - 1); prevMonthDay <= prevMonthLastDay; prevMonthDay++) {
          const prevMonthDayDate = new Date(year, month - 1, prevMonthDay);
          this.calendarItems.push({ date: prevMonthDayDate, day: prevMonthDayDate.getDate(), isTargetMonth: false });
        }
      }
      this.calendarItems.push({
        date: dayDate,
        day: dayDate.getDate(),
        isTargetMonth: true,
        isCurrentDay:
          year === this.currentDate.getFullYear() &&
          month === this.currentDate.getMonth() &&
          thisMonthDay === this.currentDate.getDate(),
      });

      if (thisMonthDay === thisMonthLastDay) {
        const daysToAdd = 42 - this.calendarItems.length;
        for (let nextMonthDay = 1; nextMonthDay <= daysToAdd; nextMonthDay++) {
          const nextMonthDayDate = new Date(year, month + 1, thisMonthDay);
          this.calendarItems.push({ date: nextMonthDayDate, day: nextMonthDay, isTargetMonth: false });
        }
      }
    }
  }

  /* The date is indexed and assigned to the object */
  onClickSelectDay(dayIndex: number): void {
    const calendarItem = this.calendarItems[dayIndex];
    if (this.selectedConditionValue !== ExtendedSearchCondition.Period) {
      this.selectedItem = this.selectedItem === dayIndex ? -1 : dayIndex;
      if (this.selectedItem > -1) {
        this.selectedDate = this.calendarItems[this.selectedItem].date;
        if (this.inputValue?.length === 19) {
          this.hours = this.inputValue.slice(11, 13);
          this.minutes = this.inputValue.slice(14, 16);
        }
        this.inputValue = this.createDate(this.selectedDate, this.hours, this.minutes);
        this.showContent = false;
      }
    } else {
      const isLess = new Date(calendarItem.date) <= new Date(this.dateRange.dateFrom);
      const isMore = new Date(calendarItem.date) >= new Date(this.dateRange.dateTo);
      this.selectedItem = this.selectedItem === dayIndex ? -1 : dayIndex;
      this.selectedDate = this.calendarItems[this.selectedItem]?.date;
      if (this.inputType === this.inputTypeEnum.date) {
        if (this.activeInput === 'dateFrom' && this.dateRange.dateTo && !isMore) {
          this.dateRange.dateFrom = this.datePipe.transform(calendarItem.date);
          this.activeInput = undefined;
        } else if (this.activeInput === 'dateTo' && this.dateRange.dateFrom && !isLess) {
          this.dateRange.dateTo = this.datePipe.transform(calendarItem.date);
          this.activeInput = undefined;
        } else if (this.dateRange.dateFrom === null && this.dateRange.dateTo === null) {
          this.dateRange.dateFrom = this.datePipe.transform(calendarItem.date);
        } else if (this.dateRange.dateFrom !== null && this.dateRange.dateTo === null && !isLess) {
          this.dateRange.dateTo = this.datePipe.transform(calendarItem.date);
          this.showContent = false;
        } else if ((this.dateRange.dateFrom !== null && this.dateRange.dateTo !== null) || isLess) {
          this.dateRange.dateTo = null;
          this.dateRange.dateFrom = this.datePipe.transform(calendarItem.date);
        }
      } else {
        this.selectedDate = calendarItem.date;
        if (this.activeInput === 'dateFrom' && this.dateRange.dateTo && !isMore) {
          this.dateRange.dateFrom = this.createDate(this.selectedDate, this.hours, this.minutes);
          this.activeInput = undefined;
        } else if (this.activeInput === 'dateTo' && this.dateRange.dateFrom && !isLess) {
          this.dateRange.dateTo = this.createDate(this.selectedDate, this.hours, this.minutes);
          this.activeInput = undefined;
        } else if (this.dateRange.dateFrom === null && this.dateRange.dateTo === null) {
          this.dateRange.dateFrom = this.createDate(this.selectedDate, this.hours, this.minutes);
        } else if (this.dateRange.dateFrom !== null && this.dateRange.dateTo === null && !isLess) {
          this.dateRange.dateTo = this.createDate(this.selectedDate, this.hours, this.minutes);
          this.showContent = false;
        } else if ((this.dateRange.dateFrom !== null && this.dateRange.dateTo !== null) || isLess) {
          this.dateRange.dateTo = null;
          this.dateRange.dateFrom = this.createDate(this.selectedDate, this.hours, this.minutes);
        }
      }
    }
    this.createFormObject();
  }

  /* Creates the correct format for the date and time from the segment, hours and minutes of the date */
  createDate(date: Date, hours: string, minutes: string): string {
    if (date === undefined || null) {
      date = new Date();
    }
    if (this.inputType === this.inputTypeEnum.datetime) {
      const selectedDate =
        this.selectedItem < 0 && this.inputValue ? this.inputValue.slice(0, 10) : this.datePipe.transform(date);
      return this.datePipe.transform(new Date(`${selectedDate} ${hours}:${minutes}`), true);
    }
    if (this.inputType === this.inputTypeEnum.date) {
      return this.datePipe.transform(date);
    }
    return '';
  }

  onChangeHours(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    if (Number(eventTarget.value) > 23 || Number(eventTarget.value) < 0) {
      this.inputValue = this.createDate(this.selectedDate, this.addZero(0), this.minutes);
      this.hours = this.addZero(0);
      this.inputHoursElement.nativeElement.value = this.addZero(0);
    } else {
      this.inputHoursElement.nativeElement.value = this.addZero(Number(eventTarget.value));
      this.hours = this.addZero(Number(eventTarget.value));
      this.inputValue = this.createDate(this.selectedDate, this.addZero(Number(eventTarget.value)), this.minutes);
    }
    this.createFormObject();
    if (this.selectedConditionValue === ExtendedSearchCondition.Period) {
      if (this.dateRange.dateFrom.length && this.activeInput === 'dateFrom') {
        this.dateRange.dateFrom = this.createDate(
          new Date(this.dateRange.dateFrom),
          this.addZero(Number(eventTarget.value)),
          this.minutes
        );
      } else if (this.dateRange.dateTo.length && this.activeInput === 'dateTo') {
        this.dateRange.dateTo = this.createDate(
          new Date(this.dateRange.dateTo),
          this.addZero(Number(eventTarget.value)),
          this.minutes
        );
        this.createFormObject();
      }
    }
  }

  onChangeMinutes(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    if (Number(eventTarget.value) > 59 || Number(eventTarget.value) < 0) {
      this.inputValue = this.createDate(this.selectedDate, this.hours, this.addZero(0));
      this.minutes = this.addZero(0);
      this.inputMinutesElement.nativeElement.value = this.addZero(0);
    } else {
      this.inputMinutesElement.nativeElement.value = this.addZero(Number(eventTarget.value));
      this.minutes = this.addZero(Number(eventTarget.value));

      this.inputValue = this.createDate(this.selectedDate, this.hours, this.addZero(Number(eventTarget.value)));
    }
    this.createFormObject();
  }

  /* Generates a date group from a minimum date to a maximum and places it in an array every ten years */
  createDateGroups(value: string): void {
    this.cleanUpCalendarArray();
    let start = 1800;
    switch (value) {
      case null:
        this.currentYear = Number(new Date().getFullYear());
        break;
      case 'next':
        this.currentYear += 10;
        break;
      case 'prev':
        this.currentYear -= 10;
        break;
    }

    while (start <= 2200) {
      start = start + 10;
      this.groupedYears.push({ yearStart: start, yearEnd: start + 9 });
    }

    this.groupedYears.forEach((item) => {
      if (Number(this.currentYear) >= Number(item.yearStart) && Number(this.currentYear) <= Number(item.yearEnd)) {
        this.currentYearLabel = `${item.yearStart} - ${item.yearEnd}`;
        for (let i = 0; i < 10; i++) {
          this.yearsList.push(item.yearStart + i);
        }
      }
    });
  }

  onClickShowYearsList(): void {
    this.pickYears = !this.pickYears;
    this.cleanUpCalendarArray();
    this.createDateGroups(null);
  }

  onClickOlderDate(): void {
    if (this.yearsToMonth === false) {
      if (
        Number(this.currentYear + 10) <=
        this.currentYear + (this.groupedYears.slice(-1)[0].yearEnd - this.currentYear)
      ) {
        this.createDateGroups('prev');
      }
    }
  }

  onClickNewerDate(): void {
    if (this.yearsToMonth === false) {
      if (Number(this.currentYear) >= this.groupedYears.shift().yearStart) {
        this.createDateGroups('next');
      }
    }
  }

  onClickSelectYear(value: number): void {
    this.yearsToMonth = true;
    this.currentYearLabel = `${value}`;
  }

  onClickSelectMonth(value: number): void {
    this.loadCalendarData(
      new Date(Number(this.currentYearLabel), value - 1, 1).getFullYear(),
      new Date(Number(this.currentYearLabel), value - 1, 1).getMonth()
    );
    this.restoringContentValues();
    this.cleanUpCalendarArray();
    this.inputValue =
      this.inputType === this.inputTypeEnum.date
        ? this.datePipe.transform(new Date())
        : this.datePipe.transform(new Date(), true).slice(0, 19).replace('T', ' ');
    this.createFormObject();
    this.showContent = false;
  }

  onClickBackToday(): void {
    this.loadCalendarData(this.currentDate.getFullYear(), this.currentDate.getMonth());
    this.cleanUpCalendarArray();
    const today =
      this.inputType === this.inputTypeEnum.date
        ? this.datePipe.transform(new Date()).slice(0, 10)
        : this.datePipe.transform(new Date(), true).slice(0, 19).replace('T', ' ');
    if (this.selectedConditionValue !== '[]') {
      this.restoringContentValues();
      this.inputValue = today;
      this.createFormObject();
      this.showContent = false;
    } else if (this.activeInput === 'dateFrom' || this.dateRange.dateFrom === null) {
      this.dateRange.dateFrom = today;
      this.createFormObject();
    } else if (this.activeInput === 'dateTo' || this.dateRange.dateFrom !== null) {
      this.dateRange.dateTo = today;
      this.createFormObject();
    } else {
      this.inputValue = today;
      this.createFormObject();
      this.showContent = false;
    }
    if (this.dateRange.dateFrom !== null && this.dateRange.dateTo !== null) {
      this.createFormObject();
      this.showContent = false;
    }
  }

  onClickClearCalendar(): void {
    this.loadCalendarData(this.currentDate.getFullYear(), this.currentDate.getMonth());
    this.resetCalendarValues();
  }

  clearInputField(): void {
    this.resetCalendarValues();
    this.setSelectedCondition(this.defaultFilterCondition);
    this.showContent = false;
    this.inputElement.nativeElement.disabled = false;
  }

  resetCalendarValues(): void {
    this.setSelectedCondition(this.defaultFilterCondition);
    this.restoringContentValues();
    this.inputValue = null;
    this.inputValues = null;
    this.dateRange.dateFrom = null;
    this.dateRange.dateTo = null;
    this.hours = '12';
    this.minutes = '00';
    this.clearButton = false;
    this.textTransform = null;
    this.createFormObject();
  }

  /* If the hour value is less than 10, then zero is added at the beginning */
  addZero(value: number): string {
    if (value.toString().length < 2) {
      return `0${value.toString()}`;
    } else {
      return value.toString();
    }
  }

  /* The contents are closed */
  restoringContentValues(): void {
    this.pickYears = false;
    this.yearsToMonth = false;
    if (this.selectedConditionValue !== ExtendedSearchCondition.Period) {
      this.isDateRangeOpen = false;
    }
  }

  /* Clears date and month arrays */
  cleanUpCalendarArray(): void {
    this.groupedYears = [];
    this.yearsList = [];
    this.currentYearLabel = null;
  }

  setLastClickedInput(inputName: 'dateFrom' | 'dateTo'): void {
    this.activeInput = inputName;
  }

  syncCalendarWithInputDate(dateValue: string): void {
    if (!dateValue) return;

    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    let dateToSync = dateValue;

    if (this.inputType === this.inputTypeEnum.datetime && dateValue.includes(' ')) {
      dateToSync = dateValue.split(' ')[0];
    }

    if (dateRegex.test(dateToSync)) {
      const enteredDate = new Date(dateToSync);

      if (!isNaN(enteredDate.getTime())) {
        this.loadCalendarData(enteredDate.getFullYear(), enteredDate.getMonth());

        const targetDate = enteredDate.getTime();
        this.selectedItem = this.calendarItems.findIndex(
          (item) => item.isTargetMonth && item.date.getTime() === targetDate
        );

        if (this.selectedItem >= 0) {
          this.selectedDate = enteredDate;
        }
      }
    }
  }
}
