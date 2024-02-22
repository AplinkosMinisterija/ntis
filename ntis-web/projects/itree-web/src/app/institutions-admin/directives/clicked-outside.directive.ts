import { Directive, ElementRef, EventEmitter, HostListener, Output } from '@angular/core';

@Directive({
  selector: '[appClickedOutside]',
  standalone: true,
})
export class ClickedOutsideDirective {
  @Output() clickedOutside = new EventEmitter();

  constructor(private elementRef: ElementRef) {}

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!(this.elementRef.nativeElement as HTMLElement).contains(targetElement)) {
      this.clickedOutside.emit();
    }
  }
}
