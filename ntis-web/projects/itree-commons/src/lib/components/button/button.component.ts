import { Component, Input } from '@angular/core';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
})
export class ButtonComponent {
  @Input() ariaLabel: string = null;
  @Input() className: string = '';
  @Input() disabled: boolean = false;
  @Input() tabindex: number = 0;
  @Input() hideTextOnMobile: boolean = false;
  @Input() size: 'regular' | 'large' = 'regular';
  @Input() name: string;
  @Input() type: 'submit' | 'reset' | 'button' = 'button';
  @Input() value: string | number;
  @Input() iconStyle: 'fab' | 'fas' | 'far' | 'brands' | 'solid' | 'regular' = 'solid';
  @Input() iconName: string;
  @Input() iconClassName: string = '';
  icons: Record<string, IconDefinition>;

  constructor(public faIconsService: FaIconsService) {}
}
