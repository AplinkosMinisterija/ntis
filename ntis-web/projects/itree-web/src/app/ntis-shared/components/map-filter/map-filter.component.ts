import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import {
  ControlValueAccessor,
  FormControl,
  FormGroup,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AdvancedSearchParameterStatement } from '@itree-commons/src/lib/model/api/api';
import { SprFilterInputValidators } from '@itree-commons/src/lib/validators/filter-input-validators';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { ExtendedSearchCondition, ExtendedSearchUpperLower } from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';
import { MapFilterField } from './map-filter.types';

@Component({
  selector: 'ntis-map-filter',
  templateUrl: './map-filter.component.html',
  styleUrls: ['./map-filter.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, ReactiveFormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: MapFilterComponent,
    },
  ],
})
export class MapFilterComponent implements ControlValueAccessor, OnInit, OnDestroy, OnChanges {
  readonly translationsRef = 'ntisShared.components.mapFilter';
  readonly destroy$ = new Subject<void>();
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;

  readonly textFilterConditions: ExtendedSearchCondition[] = [
    ExtendedSearchCondition.Equals,
    ExtendedSearchCondition.NotEqual,
    ExtendedSearchCondition.Contains,
    ExtendedSearchCondition.Empty,
    ExtendedSearchCondition.NotEmpty,
  ];

  readonly dateFilterConditions: ExtendedSearchCondition[] = [ExtendedSearchCondition.Period];

  @Input() fields: MapFilterField[] = [];
  @Input() index: number;

  @Output() remove = new EventEmitter<void>();

  filterValue: AdvancedSearchParameterStatement = {
    paramName: undefined,
    paramValue: undefined,
  };

  form = new FormGroup({
    field: new FormControl<MapFilterField>(undefined, [Validators.required]),
    filter: new FormControl<AdvancedSearchParameterStatement>(undefined, SprFilterInputValidators.required),
    selectFilter: new FormControl<unknown>(undefined, Validators.required),
  });

  @Input() touched = false;

  onChange: (value: AdvancedSearchParameterStatement) => void;
  onTouched: () => void;

  writeValue(newValue: AdvancedSearchParameterStatement): void {
    if (newValue) {
      this.filterValue = newValue;
      this.form.controls.field.setValue(this.fields?.find((field) => field.field === newValue.paramName));
      this.form.controls.filter.setValue(newValue || undefined, { emitEvent: false });
      this.form.controls.selectFilter.setValue(
        newValue?.paramValue?.value || newValue?.paramValue?.values || undefined,
        { emitEvent: false }
      );
    } else {
      this.filterValue.paramName = undefined;
      this.filterValue.paramValue = undefined;
    }
  }

  registerOnChange(fn: (value: AdvancedSearchParameterStatement) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  markAsTouched(): void {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
      this.form.markAllAsTouched();
    }
  }

  ngOnInit(): void {
    this.form.controls.field.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue?.options || newValue?.classifierCode) {
        this.form.controls.selectFilter.setValidators(Validators.required);
        this.form.controls.selectFilter.updateValueAndValidity({ emitEvent: false });
        this.form.controls.filter.clearValidators();
        this.form.controls.filter.updateValueAndValidity({ emitEvent: false });
      } else {
        this.form.controls.filter.setValidators(SprFilterInputValidators.required);
        this.form.controls.filter.updateValueAndValidity({ emitEvent: false });
        this.form.controls.selectFilter.clearValidators();
        this.form.controls.selectFilter.updateValueAndValidity({ emitEvent: false });
      }
      this.filterValue.paramName = newValue?.field;
      this.onChange?.(this.filterValue);
    });

    this.form.controls.filter.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (!this.form.controls.field.value?.options && !this.form.controls.field.value?.classifierCode) {
        this.filterValue.paramValue = newValue?.paramValue;
        this.onChange?.(this.filterValue);
      }
    });

    this.form.controls.selectFilter.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (!this.form.controls.field.value?.options || !this.form.controls.field.value?.classifierCode) {
        this.filterValue.paramValue = {
          condition: ExtendedSearchCondition.Equals,
          upperLower: ExtendedSearchUpperLower.CaseInsensitiveLatin,
          value: typeof newValue === 'string' ? newValue : undefined,
          values:
            Array.isArray(newValue) && newValue.length && typeof newValue[0] === 'string'
              ? (newValue as string[])
              : undefined,
        };
        this.onChange?.(this.filterValue);
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.touched) {
      if (changes.touched.currentValue === true) {
        this.form.markAllAsTouched();
      } else {
        this.form.markAsUntouched();
      }
    }
  }
}
