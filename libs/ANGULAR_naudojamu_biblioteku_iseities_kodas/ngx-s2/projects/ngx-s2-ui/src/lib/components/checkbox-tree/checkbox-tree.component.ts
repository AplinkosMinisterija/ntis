import {
  AfterContentInit,
  Component,
  ContentChildren,
  Input,
  OnChanges,
  OnDestroy,
  QueryList,
  SimpleChanges,
  TemplateRef,
  ViewEncapsulation,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { IconComponent } from '../icon/icon.component';
import { TemplateDirective } from '../../directives/template.directive';
import { S2UiTranslationsService } from '../../services/s2-ui-translations.service';

export type CheckboxTreeItemChecked = boolean | 'semi';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export interface CheckboxTreeItem<T = any> {
  checked?: CheckboxTreeItemChecked;
  children?: CheckboxTreeItem<T>[];
  className?: string;
  disabled?: boolean;
  extras?: T;
  hidden?: boolean;
  key?: string;
  label: string;
  open?: boolean;
  parent?: CheckboxTreeItem<T>[];
}

@Component({
  selector: 's2-checkbox-tree',
  standalone: true,
  imports: [CommonModule, FormsModule, IconComponent],
  templateUrl: './checkbox-tree.component.html',
  styleUrls: ['./checkbox-tree.component.scss'],
  encapsulation: ViewEncapsulation.None,
  host: { class: 's2-checkbox-tree' },
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: CheckboxTreeComponent,
    },
  ],
})
export class CheckboxTreeComponent implements OnChanges, AfterContentInit, OnDestroy, ControlValueAccessor {
  static readonly S2_TEMPLATE_ITEM_LABEL = 'item-label';

  static getValues<T = never>(
    items: CheckboxTreeItem<T>[],
    valueProperty: keyof CheckboxTreeItem<T>,
    state: 'checked' | 'uncheked' | 'any' = 'checked'
  ): CheckboxTreeItem<T>[] | unknown[] {
    return items.reduce((accumulator, item) => {
      if (item.children?.length) {
        accumulator.push(...CheckboxTreeComponent.getValues(item.children, valueProperty, state));
      } else if (
        state === 'any' ||
        (state === 'checked' && item.checked === true) ||
        (state === 'uncheked' && !item.checked)
      ) {
        accumulator.push(valueProperty ? item[valueProperty] : item);
      }
      return accumulator;
    }, [] as unknown[]);
  }

  readonly destroy$ = new Subject<void>();

  @ContentChildren(TemplateDirective)
  templatesQueryList: QueryList<TemplateDirective>;
  itemLabelTemplate: TemplateRef<unknown>;

  @Input() items: CheckboxTreeItem[];
  @Input() showSelectAll: boolean = true;
  @Input() selectAllText: string;
  @Input() inputsIdPrefix = '';
  @Input() valueProperty: keyof CheckboxTreeItem;

  selectAllChecked: CheckboxTreeItemChecked = false;

  disabled = false;
  touched = false;
  onChange: (value: unknown[] | CheckboxTreeItem[]) => void;
  onTouched: () => void;

  constructor(public translationsService: S2UiTranslationsService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.items.currentValue) {
      this.refreshCheckedValues();
    }
  }

  ngAfterContentInit(): void {
    this.updateTemplates();
    this.templatesQueryList.changes.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.updateTemplates();
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  writeValue(newValue: unknown[]): void {
    if (this.onChange) {
      const setValue = (items: CheckboxTreeItem[], value: unknown[]): void => {
        items.forEach((item) => {
          if (item.children?.length) {
            setValue(item.children, value);
          } else {
            item.checked = value?.length
              ? this.valueProperty
                ? value.some((valueItem) => valueItem === item[this.valueProperty])
                : value.includes(item)
              : false;
          }
        });
      };
      setValue(this.items, newValue);
      this.refreshCheckedValues();
    }
  }

  registerOnChange(fn: (value: unknown[] | CheckboxTreeItem[]) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  markAsTouched(): void {
    if (!this.touched) {
      this.onTouched?.();
      this.touched = true;
    }
  }

  handleSelectAll(event: Event): void {
    this.handleCheckboxChange({ children: this.items, checked: this.selectAllChecked } as CheckboxTreeItem, event);
  }

  handleCheckboxChange(item: CheckboxTreeItem, event: Event): void {
    if (item.children?.length && event) {
      const eventTarget = event.target as HTMLInputElement;
      this.setChildrenCheckedValue(item, item.checked === 'semi' ? true : eventTarget.checked);
    }
    this.refreshCheckedValues();
    this.markAsTouched();
    this.onChange?.(this.getValues());
  }

  private getValues(
    items = this.items,
    valueProperty = this.valueProperty,
    state: 'checked' | 'uncheked' | 'any' = 'checked'
  ): CheckboxTreeItem[] | unknown[] {
    return CheckboxTreeComponent.getValues(items, valueProperty, state);
  }

  private setChildrenCheckedValue(item: CheckboxTreeItem, value: boolean): void {
    item.children?.forEach((child) => {
      child.checked = value;
      if (child.children?.length) {
        this.setChildrenCheckedValue(child, value);
      }
    });
  }

  private isItemChecked(item: CheckboxTreeItem): CheckboxTreeItemChecked {
    if (item.children?.length) {
      const selectedCount = item.children.reduce((accumulator, child) => {
        if (child.children?.length) {
          const isChildChildrenSelected = this.isItemChecked(child);
          child.checked = isChildChildrenSelected;
          if (isChildChildrenSelected === true) {
            return accumulator + 1;
          } else if (isChildChildrenSelected === 'semi') {
            return accumulator + 0.5;
          }
        } else if (child.checked === true) {
          return accumulator + 1;
        }
        return accumulator;
      }, 0);
      if (selectedCount === item.children.length && item.children.length > 0) {
        return true;
      } else if (selectedCount > 0) {
        return 'semi';
      }
      return false;
    } else {
      return item.checked === true;
    }
  }

  private refreshCheckedValues(): void {
    if (this.showSelectAll) {
      this.selectAllChecked = this.isItemChecked({ children: this.items } as CheckboxTreeItem);
    } else {
      this.items.forEach((item) => {
        item.checked = this.isItemChecked(item);
      });
    }
  }

  private updateTemplates(): void {
    this.itemLabelTemplate = this.templatesQueryList.find(
      (template) => template.s2Template === CheckboxTreeComponent.S2_TEMPLATE_ITEM_LABEL
    )?.template;
  }
}
