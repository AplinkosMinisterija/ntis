import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: 'input[ntisDigitsOnly]',
  standalone: true,
})
export class InputDigitsOnlyDirective {
  constructor(private elementRef: ElementRef<HTMLInputElement>) {}

  @HostListener('input', ['$event']) onInputChange(event: InputEvent): void {
    const initalValue = this.elementRef.nativeElement.value;
    this.elementRef.nativeElement.value = initalValue.replace(/[^0-9]*/g, '');
    if (initalValue !== this.elementRef.nativeElement.value) {
      event.stopPropagation();
    }
  }
}
