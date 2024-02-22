import { TestBed } from '@angular/core/testing';

import { NtisTechSupportService } from './ntis-tech-support.service';

describe('NtisTechSupportService', () => {
  let service: NtisTechSupportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NtisTechSupportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
