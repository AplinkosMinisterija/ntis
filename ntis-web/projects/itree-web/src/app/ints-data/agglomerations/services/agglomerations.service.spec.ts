import { TestBed } from '@angular/core/testing';

import { AgglomerationsService } from './agglomerations.service';

describe('AgglomerationsService', () => {
  let service: AgglomerationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgglomerationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
