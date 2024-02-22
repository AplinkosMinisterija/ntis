import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';
import { AppDataService } from '../services/app-data.service';

@Pipe({
  name: 's2Date',
})
export class S2DatePipe implements PipeTransform {
  constructor(private datePipe: DatePipe, private appDataService: AppDataService) {}

  transform(value: Date | number | string, showTime = false, ...options: string[]): string {
    return this.datePipe.transform(
      value,
      this.appDataService.getPropertyValue(showTime ? 'DATE_FORMAT_DAY_TIME_JAVA' : 'DATE_FORMAT_DAY_JAVA'),
      ...options
    );
  }
  transformDateHoursMinutes(value: Date | number | string, ...options: string[]): string {
    return this.datePipe.transform(value, 'yyyy-MM-dd HH:mm', ...options);
  }
}
