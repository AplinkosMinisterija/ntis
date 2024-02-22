import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { FaIconsService } from '../../services/fa-icons.service';
import { DropdownItem } from '../../types/dropdown';

@Component({
  selector: 'spr-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss'],
})
export class DropdownComponent {
  @Input() className: string;
  @Input() compact: boolean = false;
  @Input() showClearButton: boolean = false;
  @Input() items: DropdownItem<unknown>[] = [];
  @Input() placeholderText: string = '';
  @Input() translatePlaceholderText: boolean = false;
  @Input() translateItemsText: boolean = false;
  @Output() clearSelection: EventEmitter<unknown> = new EventEmitter();
  @Output() selectItem: EventEmitter<DropdownItem<unknown>> = new EventEmitter<DropdownItem<unknown>>();
  isOpen: boolean = false;
  selectedIndex: number = -1;

  constructor(private elementRef: ElementRef, public faIconsService: FaIconsService) {}

  clearSelectedItem(): void {
    this.selectedIndex = -1;
    this.clearSelection.emit();
  }

  showDropdown(state: boolean): void {
    this.isOpen = state;
  }

  onSelectOption(index: number): void {
    if (this.isOpen) {
      this.selectedIndex = index;
      this.selectItem.emit(this.items[this.selectedIndex]);
      this.showDropdown(false);
    }
  }

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!(this.elementRef.nativeElement as HTMLElement).contains(targetElement)) {
      this.showDropdown(false);
    }
  }
}
