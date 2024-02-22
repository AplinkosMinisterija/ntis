import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { cacheManagerPageResolver } from './cache-manager-page.resolver';
import { CacheInfo } from '@itree-commons/src/lib/model/api/api';

describe('cacheManagerPageResolver', () => {
  const executeResolver: ResolveFn<CacheInfo[]> = (...resolverParameters) =>
    TestBed.runInInjectionContext(() => cacheManagerPageResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
