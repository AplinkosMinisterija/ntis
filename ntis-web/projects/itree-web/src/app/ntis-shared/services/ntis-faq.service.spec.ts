import { TestBed } from '@angular/core/testing';

import { NtisFaqService } from './ntis-faq.service';

describe('NtisFaqService', () => {
  let service: NtisFaqService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NtisFaqService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
