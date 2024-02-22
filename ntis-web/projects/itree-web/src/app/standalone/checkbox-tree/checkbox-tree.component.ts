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
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckboxTreeItem, CheckboxTreeItemChecked } from './checkbox-tree.types';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { TemplateDirective } from '@itree-commons/src/lib/directives/template.directive';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-checkbox-tree',
  standalone: true,
  imports: [CommonModule, FormsModule, ItreeCommonsModule, FontAwesomeModule],
  templateUrl: './checkbox-tree.component.html',
  styleUrls: ['./checkbox-tree.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: CheckboxTreeComponent,
    },
  ],
})
export class CheckboxTreeComponent implements OnChanges, AfterContentInit, OnDestroy, ControlValueAccessor {
  static readonly SPR_TEMPLATE_ITEM_LABEL = 'item-label';

  readonly destroy$ = new Subject<void>();

  @ContentChildren(TemplateDirective)
  templatesQueryList: QueryList<TemplateDirective>;
  itemLabelTemplate: TemplateRef<unknown>;

  @Input() items: CheckboxTreeItem[];
  @Input() translationsPrefix: string = '';
  @Input() showSelectAll: boolean = true;
  @Input() selectAllText: string = 'common.action.selectAll';
  @Input() translateSelectAllText: boolean = true;
  @Input() inputsIdPrefix = '';
  @Input() valueAttribute: keyof CheckboxTreeItem;

  selectAllChecked: CheckboxTreeItemChecked = false;

  disabled = false;
  touched = false;
  onChange: (value: unknown[] | CheckboxTreeItem[]) => void;
  onTouched: () => void;

  constructor(public faIconsService: FaIconsService) {}

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
              ? this.valueAttribute
                ? value.some((valueItem) => valueItem === item[this.valueAttribute])
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
      this.onTouched();
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
    this.onChange(this.getSelectedValues());
  }

  private getSelectedValues(items = this.items, checkedOnly = true): CheckboxTreeItem[] | unknown[] {
    return items.reduce((accumulator, item) => {
      if (item.children?.length) {
        accumulator.push(...this.getSelectedValues(item.children));
      } else if (!checkedOnly || (checkedOnly && item.checked === true)) {
        accumulator.push(this.valueAttribute ? item[this.valueAttribute] : item);
      }
      return accumulator;
    }, [] as unknown[]);
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
      (template) => template.sprTemplate === CheckboxTreeComponent.SPR_TEMPLATE_ITEM_LABEL
    )?.template;
  }
}
