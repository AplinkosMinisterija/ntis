import { Component, Input } from '@angular/core';

@Component({
  selector: 'spr-content-box',
  templateUrl: './content-box.component.html',
  styleUrls: ['./content-box.component.scss'],
})
export class ContentBoxComponent {
  @Input() showBreadcrumbs = true;
  @Input() headerText: string;
  @Input() translateHeader = true;
  @Input() textSize:
    | 'text-xs'
    | 'text-sm'
    | 'text-base'
    | 'text-lg'
    | 'text-xl'
    | 'text-2xl'
    | 'text-3xl'
    | 'text-4xl'
    | 'text-5xl'
    | 'text-6xl'
    | 'text-7xl'
    | 'text-8xl'
    | 'text-9xl' = 'text-xl';

  @Input() textAlignment: 'text-left' | 'text-center' | 'text-right' | 'text-justify' | 'text-start' | 'text-end' =
    'text-center';

  @Input() transformingText: 'normal-case' | 'uppercase' | 'lowercase' | 'capitalize' = 'normal-case';
}
