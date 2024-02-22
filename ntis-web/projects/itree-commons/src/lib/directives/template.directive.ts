import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[sprTemplate]',
})
export class TemplateDirective<T = unknown> {
  @Input() sprTemplate = '';
  @Input() sprTemplateExtras: T;
  constructor(public template: TemplateRef<unknown>) {}
}
