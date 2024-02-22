import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { AccessibilityService } from '../../services/accessibility.service';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-accessibility',
  templateUrl: './accessibility.component.html',
  styleUrls: ['./accessibility.component.scss'],
})
export class AccessibilityComponent implements OnInit {
  storage: Storage = localStorage;
  increaseTextSizeLevel: number = 0;
  isLinkBolded: boolean = false;
  isAddedCursor: boolean = false;
  isChangedContrast: boolean = false;
  isPanelVisible: boolean;

  constructor(
    private accessibilityService: AccessibilityService,
    private elementRef: ElementRef<Element>,
    public faIconsService: FaIconsService
  ) {}

  ngOnInit(): void {
    this.increaseTextSizeLevel = this.accessibilityService.getTextSizeLevel();
    this.isLinkBolded = this.accessibilityService.styles.includes(this.accessibilityService.cssClasses.boldLinks);
    this.isAddedCursor = this.accessibilityService.styles.includes(this.accessibilityService.cssClasses.cursor);
    this.isChangedContrast = this.accessibilityService.styles.includes(this.accessibilityService.cssClasses.contrast);
  }

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!this.elementRef.nativeElement.contains(targetElement)) {
      this.isPanelVisible = false;
    }
  }

  togglePanel(): void {
    this.isPanelVisible = !this.isPanelVisible;
  }

  onIncreaseTextSize(): void {
    this.increaseTextSizeLevel = this.increaseTextSizeLevel < 3 ? this.increaseTextSizeLevel + 1 : 0;
    this.accessibilityService.setTextSize(this.increaseTextSizeLevel);
  }

  onBoldLinks(): void {
    this.isLinkBolded ? this.accessibilityService.removeBoldedLinks() : this.accessibilityService.boldLinks();
    this.isLinkBolded = !this.isLinkBolded;
  }

  onAddCursor(): void {
    this.isAddedCursor
      ? this.accessibilityService.removeMouseCursorSizeIncreasement()
      : this.accessibilityService.increaseMouseCursorSize();
    this.isAddedCursor = !this.isAddedCursor;
  }

  onAddContrast(): void {
    this.isChangedContrast
      ? this.accessibilityService.removeChangedContrast()
      : this.accessibilityService.changeContrast();
    this.isChangedContrast = !this.isChangedContrast;
  }

  onRemoveAccessibility(): void {
    this.increaseTextSizeLevel = 0;
    this.isLinkBolded = false;
    this.isAddedCursor = false;
    this.isChangedContrast = false;
    this.accessibilityService.resetCssClasses();
  }
}
