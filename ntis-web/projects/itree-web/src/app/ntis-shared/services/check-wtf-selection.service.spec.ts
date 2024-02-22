import { TestBed } from '@angular/core/testing';

import { CheckWtfSelectionService } from './check-wtf-selection.service';

describe('CheckWtfSelectionService', () => {
  let service: CheckWtfSelectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckWtfSelectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
