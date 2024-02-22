import { TestBed } from '@angular/core/testing';

import { LoadFormActionsGuard } from './load-form-actions.guard';

describe('FormActionsGuard', () => {
  let guard: LoadFormActionsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(LoadFormActionsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
