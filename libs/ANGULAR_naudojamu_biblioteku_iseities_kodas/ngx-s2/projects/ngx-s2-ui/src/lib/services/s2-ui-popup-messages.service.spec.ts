import { TestBed } from '@angular/core/testing';

import { S2UiPopupMessagesService } from './s2-ui-popup-messages.service';

describe('S2UiPopupMessageService', () => {
  let service: S2UiPopupMessagesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(S2UiPopupMessagesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
