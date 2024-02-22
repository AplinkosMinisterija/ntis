import { inject } from '@angular/core/testing';
import { TemplateDirective } from './template.directive';
import { TemplateRef } from '@angular/core';

describe('TemplateDirective', () => {
  it('should create an instance', inject([TemplateRef], (templateRef: TemplateRef<unknown>) => {
    const directive = new TemplateDirective(templateRef);
    expect(directive).toBeTruthy();
  }));
});
