import { Component, ElementRef, EventEmitter, HostListener, Input, Output, SkipSelf } from '@angular/core';
import { ControlContainer } from '@angular/forms';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-input-password',
  templateUrl: './input-password.component.html',
  styleUrls: ['./input-password.component.scss'],
  viewProviders: [
    {
      provide: ControlContainer,
      useFactory: (container: ControlContainer): ControlContainer => container,
      deps: [[new SkipSelf(), ControlContainer]],
    },
  ],
})
export class InputPasswordComponent {
  @Input() placeholder: string = '';
  @Input() controlName = 'password';
  @Input() maxLength: number;
  @Output() focusOut = new EventEmitter<FocusEvent>();
  inputFocused: boolean;
  revealPassword: boolean;

  constructor(public faIconsService: FaIconsService, private elementRef: ElementRef<Element>) {}

  togglePasswordVisibility(): void {
    this.revealPassword = !this.revealPassword;
    this.inputFocused = true;
  }

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!this.elementRef.nativeElement.contains(targetElement)) {
      this.inputFocused = false;
    }
  }

  handleFocusOut(event: FocusEvent): void {
    this.inputFocused = false;
    this.focusOut.emit(event);
  }
}
