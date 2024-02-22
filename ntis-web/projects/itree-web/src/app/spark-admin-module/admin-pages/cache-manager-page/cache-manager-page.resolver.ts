import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { AdminService } from '../../admin-services/admin.service';
import { CacheInfo } from '@itree-commons/src/lib/model/api/api';

export const cacheManagerPageResolver: ResolveFn<CacheInfo[]> = () => {
  return inject(AdminService).getCacheList();
};
