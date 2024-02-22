import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';

type MessageBoxType = 'info' | 'error' | 'success';

@Component({
  selector: 'ntis-message-box',
  templateUrl: './message-box.component.html',
  styleUrls: ['./message-box.component.scss'],
})
export class MessageBoxComponent implements OnChanges {
  @Input() type: MessageBoxType = 'error';
  @Input() text = '';
  @Input() link: string;
  @Input() linkText: string;
  icon: IconDefinition;

  constructor(public faIconsService: FaIconsService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.type) {
      const iconCurrentValue = changes.type.currentValue as MessageBoxType;
      switch (iconCurrentValue) {
        case 'error':
          this.icon = this.faIconsService.fas.faTriangleExclamation;
          break;
        case 'success':
          this.icon = this.faIconsService.fas.faCircleCheck;
          break;
        default:
          this.icon = this.faIconsService.fas.faCircleInfo;
          break;
      }
    }
  }
}
