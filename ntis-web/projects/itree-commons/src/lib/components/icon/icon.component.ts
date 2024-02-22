import { Component, Input } from '@angular/core';

type IconSizeInPx =
  | '8'
  | '10'
  | '12'
  | '14'
  | '16'
  | '18'
  | '20'
  | '24'
  | '30'
  | '36'
  | '48'
  | '56'
  | '60'
  | '72'
  | '96'
  | '128';

@Component({
  selector: 'spr-icon',
  templateUrl: './icon.component.html',
  styleUrls: ['./icon.component.scss'],
})
export class IconComponent {
  @Input() name: string;
  @Input() size: IconSizeInPx = '20';
}
