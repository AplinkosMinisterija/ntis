import { TestBed } from '@angular/core/testing';

import { CheckFormActionsGuard } from './check-form-actions.guard';

describe('CheckFormActionsGuard', () => {
  let guard: CheckFormActionsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CheckFormActionsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
