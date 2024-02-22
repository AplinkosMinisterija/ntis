import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[s2Template]',
  standalone: true,
})
export class TemplateDirective<T = unknown> {
  @Input() s2Template = '';
  @Input() s2TemplateExtras: T;
  constructor(public template: TemplateRef<unknown>) {}
}
