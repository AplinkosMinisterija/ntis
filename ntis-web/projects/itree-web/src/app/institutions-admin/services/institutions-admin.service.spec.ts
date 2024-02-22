import { TestBed } from '@angular/core/testing';

import { InstitutionsAdminService } from './institutions-admin.service';

describe('InstitutionsAdminService', () => {
  let service: InstitutionsAdminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InstitutionsAdminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
