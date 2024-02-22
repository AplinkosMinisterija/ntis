import { TestBed } from '@angular/core/testing';

import { S2UiSettingsService } from './s2-ui-settings.service';

describe('S2UiSettingsService', () => {
  let service: S2UiSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(S2UiSettingsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
