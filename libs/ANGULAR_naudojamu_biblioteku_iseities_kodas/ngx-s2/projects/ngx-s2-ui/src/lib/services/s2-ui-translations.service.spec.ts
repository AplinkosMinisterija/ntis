import { TestBed } from '@angular/core/testing';

import { S2UiTranslationsService } from './s2-ui-translations.service';

describe('S2UiTranslationsService', () => {
  let service: S2UiTranslationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(S2UiTranslationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
