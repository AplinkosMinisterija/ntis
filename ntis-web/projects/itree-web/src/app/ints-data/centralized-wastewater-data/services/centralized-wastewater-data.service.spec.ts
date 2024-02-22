import { TestBed } from '@angular/core/testing';

import { CentralizedWastewaterDataService } from './centralized-wastewater-data.service';

describe('CentralizedWastewaterDataService', () => {
  let service: CentralizedWastewaterDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentralizedWastewaterDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
