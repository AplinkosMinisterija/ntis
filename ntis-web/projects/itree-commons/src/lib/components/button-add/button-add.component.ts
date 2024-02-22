import { Component, Input } from '@angular/core';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-button-add',
  templateUrl: './button-add.component.html',
  styleUrls: ['./button-add.component.scss'],
})
export class ButtonAddComponent {
  @Input() disabled: boolean = false;
  @Input() textTranslateRef: string = 'common.action.enterNew';

  constructor(public faIconsService: FaIconsService) {}
}
