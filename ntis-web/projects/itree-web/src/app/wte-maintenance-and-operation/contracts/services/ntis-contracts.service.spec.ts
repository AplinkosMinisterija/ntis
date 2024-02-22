import { TestBed } from '@angular/core/testing';

import { NtisContractsService } from './ntis-contracts.service';

describe('NtisContractsService', () => {
  let service: NtisContractsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NtisContractsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
