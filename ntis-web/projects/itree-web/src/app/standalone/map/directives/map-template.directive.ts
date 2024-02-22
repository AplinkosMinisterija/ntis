import { Directive, Input, TemplateRef } from '@angular/core';
import { MapLayerTemplateType } from '../types/map-layer-template-type';
import { MapTemplate } from '../types/map-template';

@Directive({
  selector: '[mapTemplate]',
  standalone: true,
})
export class MapTemplateDirective {
  @Input() mapTemplate: MapTemplate;
  @Input() mapLayerTemplateKey: string;
  @Input() mapLayerTemplateType: MapLayerTemplateType;

  constructor(public template: TemplateRef<unknown>) {}
}
