import { Component, Input } from '@angular/core';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-button-action',
  templateUrl: './button-action.component.html',
  styleUrls: ['./button-action.component.scss'],
})
export class ButtonActionComponent {
  @Input() disabled: boolean = false;
  @Input() name: string;
  @Input() type: 'submit' | 'reset' | 'button' = 'button';
  @Input() value: string | number;
  @Input() iconName: string;
  @Input() iconOnly: boolean;
  @Input() text: string;

  constructor(public faIconsService: FaIconsService) {}
}
