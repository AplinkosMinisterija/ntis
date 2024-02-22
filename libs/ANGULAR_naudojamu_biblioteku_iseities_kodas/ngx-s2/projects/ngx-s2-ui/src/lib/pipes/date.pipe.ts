import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';
import { S2UiSettingsService } from '../services/s2-ui-settings.service';

@Pipe({
  name: 's2Date',
  standalone: true,
})
export class S2DatePipe implements PipeTransform {
  constructor(private datePipe: DatePipe, private settingsService: S2UiSettingsService) {}

  transform(value: Date | number | string, showTime = false, ...options: string[]): string {
    return this.datePipe.transform(
      value,
      this.settingsService.settings[showTime ? 'dateTimeFormat' : 'dateFormat'],
      ...options
    );
  }
}
