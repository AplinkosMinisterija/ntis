import { TestBed } from '@angular/core/testing';

import { NtisCommonService } from './ntis-common.service';

describe('NtisCommonService', () => {
  let service: NtisCommonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NtisCommonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
