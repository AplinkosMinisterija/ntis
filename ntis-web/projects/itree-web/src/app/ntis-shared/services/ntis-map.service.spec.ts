import { TestBed } from '@angular/core/testing';

import { NtisMapService } from './ntis-map.service';

describe('NtisMapService', () => {
  let service: NtisMapService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NtisMapService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
