import { Pipe, PipeTransform } from '@angular/core';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { ClsfSprLanguage } from '../enums/classifiers.enums';

export enum TimeIntervalsEnum {
  day = 'day',
  hour = 'hour',
  minute = 'minute',
  month = 'month',
  second = 'second',
  week = 'week',
  year = 'year',
}

@Pipe({
  name: 'dateAgo',
  pure: true,
})
export class DateAgoPipe implements PipeTransform {
  readonly clsfSprLanguage = ClsfSprLanguage;
  readonly timeIntervalEnum = TimeIntervalsEnum;
  constructor(protected commonFormServices: CommonFormServices) {}

  transform(value: unknown): string {
    if (value) {
      const seconds = Math.floor((+new Date() - +new Date(value as string)) / 1000);

      if (seconds < 29) {
        let justNowTranslation: string;
        this.commonFormServices.translate.get('common.date.dateAgo.justNow').subscribe((translation: string) => {
          justNowTranslation = translation;
        });
        return justNowTranslation;
      }

      const intervals: { [key: string]: number } = {
        day: 86400,
        hour: 3600,
        minute: 60,
        month: 2592000,
        second: 1,
        week: 604800,
        year: 31536000,
      };

      let counter: number;
      let translation: string;

      for (const i in intervals) {
        counter = Math.floor(seconds / intervals[i]);
        let plural: string = '';
        if (this.commonFormServices.translate.currentLang === this.clsfSprLanguage.En) {
          if (counter === 1) {
            plural = '';
          } else {
            plural = 's';
          }
        }
        if (counter > 0) {
          this.commonFormServices.translate.get('common.date.dateAgo.before').subscribe((translationText: string) => {
            translation = `${translationText} ${counter}`;
          });
          this.commonFormServices.translate
            .get('common.date.dateAgo.' + this.getKey(counter, i))
            .subscribe((translationText: string) => {
              translation = `${translation} ${translationText}`;
            });
          return translation;
        }
      }
    }
    return value as string;
  }

  getKey(counter: number, key?: string): string {
    const timeArrays = [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 30, 40, 50];
    const timeArraysWithOne = [1, 21, 31, 41, 51];
    switch (key) {
      case this.timeIntervalEnum.second:
        if (timeArrays.includes(counter)) {
          return 'secondsOne';
        } else if (timeArraysWithOne.includes(counter)) {
          return 'secondsTwo';
        } else {
          return 'secondsThree';
        }
      case this.timeIntervalEnum.minute:
        if (timeArraysWithOne.includes(counter)) {
          return 'minutesOne';
        } else if (timeArrays.includes(counter)) {
          return 'minutesTwo';
        } else {
          return 'minutesThree';
        }
      case this.timeIntervalEnum.hour:
        if (timeArraysWithOne.includes(counter)) {
          return 'hoursOne';
        } else if (timeArrays.includes(counter)) {
          return 'hoursThree';
        } else {
          return 'hoursTwo';
        }
      case this.timeIntervalEnum.day:
        if (timeArraysWithOne.includes(counter)) {
          return 'hoursOne';
        } else if (timeArrays.includes(counter)) {
          return 'daysThree';
        } else {
          return 'daysTwo';
        }
      case this.timeIntervalEnum.week:
        if (timeArraysWithOne.includes(counter)) {
          return 'weekOne';
        } else {
          return 'weekTwo';
        }
      case this.timeIntervalEnum.month:
        if (timeArraysWithOne.includes(counter)) {
          return 'monthOne';
        } else if (timeArrays.includes(counter)) {
          return 'monthThree';
        } else {
          return 'monthTwo';
        }
      case this.timeIntervalEnum.year:
        if (timeArrays.includes(counter)) {
          return 'yearTwo';
        } else {
          return 'yearOne';
        }
    }
    return '';
  }
}
