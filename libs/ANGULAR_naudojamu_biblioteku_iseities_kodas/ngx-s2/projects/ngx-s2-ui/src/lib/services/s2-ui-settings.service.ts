import { Injectable } from '@angular/core';

export interface S2UiSettings {
  dateFormat: string;
  dateTimeFormat: string;
}

@Injectable({
  providedIn: 'root',
})
export class S2UiSettingsService {
  settings: S2UiSettings = {
    dateFormat: 'yyyy-MM-dd',
    dateTimeFormat: 'yyyy-MM-dd HH:mm:ss',
  };

  setSettings(settings: S2UiSettings): void {
    this.settings = { ...this.settings, ...settings };
  }
}
