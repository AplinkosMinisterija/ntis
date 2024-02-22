import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdvancedSearchParameterStatement } from '@itree-commons/src/lib/model/api/api';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MapFilterComponent } from '../map-filter/map-filter.component';
import { MapFilterField } from '../map-filter/map-filter.types';
import { NTIS_MAP } from '../../constants/forms.const';
import { SprFilterInputValidators } from '@itree-commons/src/lib/validators/filter-input-validators';

@Component({
  selector: 'ntis-map-filters-form',
  templateUrl: './map-filters-form.component.html',
  styleUrls: ['./map-filters-form.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FormsModule, ReactiveFormsModule, MapFilterComponent],
})
export class MapFiltersFormComponent implements OnInit, OnChanges {
  readonly translationsRef = 'ntisShared.components.mapFiltersForm';

  @Input() savedFilters: AdvancedSearchParameterStatement[];
  @Input() fields: MapFilterField[] = [];
  @Input() maxFilters: number;
  @Output() save = new EventEmitter<AdvancedSearchParameterStatement[]>();

  visibleFields: MapFilterField[] = [];

  formArray = new FormArray<FormControl<AdvancedSearchParameterStatement>>([]);

  constructor(private commonFormServices: CommonFormServices, private authService: AuthService) {}

  ngOnInit(): void {
    if (this.savedFilters) {
      this.savedFilters.forEach((savedFilter) => {
        this.addFilter(savedFilter);
      });
    } else {
      this.addFilter();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.fields) {
      this.visibleFields =
        (changes.fields.currentValue as typeof this.fields)
          ?.map((field) => ({ ...field, name: this.commonFormServices.translate.instant(field.name) as string }))
          ?.filter(
            (field) => !field.formActionName || this.authService.isFormActionEnabled(NTIS_MAP, field.formActionName)
          ) || [];
    }
  }

  addFilter(value: AdvancedSearchParameterStatement = null): void {
    if (!this.maxFilters || this.formArray.length < this.maxFilters) {
      this.formArray.push(
        new FormControl<AdvancedSearchParameterStatement>(value, [SprFilterInputValidators.required])
      );
    }
  }

  removeFilter(index: number): void {
    this.formArray.removeAt(index);
  }

  handleSubmit(): void {
    this.formArray.markAllAsTouched();
    if (!this.formArray.valid) {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else {
      this.save.emit(this.formArray.value);
    }
  }
}
