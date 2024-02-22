import { DatePipe } from '@angular/common';
import { S2DatePipe } from './date.pipe';
import { TestBed } from '@angular/core/testing';
import { S2UiSettingsService } from '../services/s2-ui-settings.service';

describe('S2DatePipe', () => {
  let datePipe: DatePipe;
  let settingsService: S2UiSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    datePipe = TestBed.inject(DatePipe);
    settingsService = TestBed.inject(S2UiSettingsService);
  });

  it('create an instance', () => {
    const pipe = new S2DatePipe(datePipe, settingsService);
    expect(pipe).toBeTruthy();
  });
});
