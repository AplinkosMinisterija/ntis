import { animate, style, transition, trigger } from '@angular/animations';

export const fadeInAnimation = trigger('fadeInAnimation', [
  transition(':enter', [style({ opacity: 0 }), animate('.7s ease-out', style({ opacity: 1 }))]),
]);

export const fadeAnimation = trigger('fadeAnimation', [
  transition(':enter', [style({ opacity: 0 }), animate('.7s ease-out', style({ opacity: 1 }))]),
  transition(':leave', [style({ opacity: 1 }), animate('.7s ease-out', style({ opacity: 0 }))]),
]);

export const fadeInFields = trigger('fadeInFields', [
  transition(':enter', [style({ opacity: 0 }), animate('.3s ease-out', style({ opacity: 1 }))]),
]);

export const loaderFadeAnimation = trigger('loaderFadeAnimation', [
  transition(':enter', [style({ opacity: 0 }), animate('.3s ease-out', style({ opacity: 1 }))]),
  transition(':leave', [style({ opacity: 1 }), animate('.3s ease-out', style({ opacity: 0 }))]),
]);

export const fadeOutLineLoader = trigger('fadeOutLineLoader', [
  transition(':leave', [style({ opacity: 1 }), animate('.3s ease-out', style({ opacity: 0 }))]),
]);

export const slideToLeft = trigger('slideToLeft', [
  transition(':enter', [style({ right: '-15rem' }), animate('0.3s ease-out', style({ right: '1.5rem' }))]),
  transition(':leave', [style({ right: '1.5rem' }), animate('0.3s ease-out', style({ right: '-15rem' }))]),
]);

export const listboxShowUp = trigger('listboxShowUp', [
  transition(':enter', [style({ opacity: 0 }), animate('.1s linear', style({ opacity: 1 }))]),
  transition(':leave', [style({ opacity: 1 }), animate('.1s linear', style({ opacity: 0 }))]),
]);

export const expandFromTop = trigger('expandFromTop', [
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
