import { Component, HostBinding, Input, OnChanges, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { S2UiPopupMessage, S2UiPopupMessagesService } from '../../services/s2-ui-popup-messages.service';
import { IconComponent } from '../icon/icon.component';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { S2UiTranslationsService } from '../../services/s2-ui-translations.service';

@Component({
  selector: 's2-popup-messages',
  standalone: true,
  imports: [CommonModule, IconComponent],
  templateUrl: './popup-messages.component.html',
  styleUrls: ['./popup-messages.component.scss'],
  encapsulation: ViewEncapsulation.None,
  host: { class: 's2-popup-messages' },
  animations: [
    trigger('showHide', [
      state(
        'visible',
        style({
          transform: 'translate(0, 0)',
          opacity: 1,
        })
      ),
      transition(':enter', [
        style({
          opacity: 0,
          transform: '{{transformValue}}',
        }),
        animate('0.2s ease'),
      ]),
      transition(':leave', [animate('0.2s ease', style({ opacity: 0, transform: '{{transformValue}}' }))]),
    ]),
  ],
})
export class PopupMessagesComponent implements OnChanges {
  @HostBinding('class') private hostPositionClass: string = 's2-popup-messages--position-top-center';
  @Input() key: string | number;
  @Input() position:
    | 'top-center'
    | 'top-left'
    | 'top-right'
    | 'bottom-center'
    | 'bottom-left'
    | 'bottom-right'
    | 'center' = 'top-center';

  @Input() fadeDirection: 'top' | 'left' | 'right' | 'bottom' = 'top';
  readonly defaultFadeDirectionTransition = 'translateY(100%)';
  fadeDirectionTransitions: Record<typeof this.fadeDirection, string> = {
    top: 'translateY(-100%)',
    left: 'translateX(-100%)',
    right: 'translateX(100%)',
    bottom: 'translateY(100%)',
  };

  icons: Record<S2UiPopupMessage['severity'], string> = {
    error: 'error',
    warning: 'warning',
    success: 'check_circle',
    info: 'info',
  };

  constructor(public service: S2UiPopupMessagesService, public translationsService: S2UiTranslationsService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.position) {
      const currentValue = changes.position.currentValue as typeof this.position;
      if (currentValue) {
        this.hostPositionClass = 's2-popup-messages--position-' + currentValue;
      } else {
        this.hostPositionClass = '';
      }
    }
  }
}
