import { AnimationTriggerMetadata, animate, style, transition, trigger } from '@angular/animations';

export const expandFromTop = (name = 'expandFromTop'): AnimationTriggerMetadata => {
  return trigger(name, [
    transition(':enter', [
      style({
        opacity: 0,
        transform: 'scaleY(0)',
        'transform-origin': 'top',
        height: 0,
      }),
      animate(
        '0.15s linear',
        style({
          opacity: 1,
          transform: 'scaleY(1)',
          height: '*',
        })
      ),
    ]),
    transition(':leave', [
      style({
        opacity: 1,
        transform: 'scaleY(1)',
        'transform-origin': 'top',
        height: '*',
      }),
      animate('0.15s linear', style({ opacity: 0, transform: 'scaleY(0)', height: 0 })),
    ]),
  ]);
};
