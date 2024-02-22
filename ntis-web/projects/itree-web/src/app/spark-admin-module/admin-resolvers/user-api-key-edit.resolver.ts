import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ApiKey } from '@itree-commons/src/lib/model/api/api';
import { EMPTY, Observable, catchError, of } from 'rxjs';
import { AdminService } from '../admin-services/admin.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { UrlSegmentsConst } from '@itree-commons/src/constants/urlSegments.const';
import { SprErrorHandler } from '@itree-commons/src/lib/errors/error-handler';

@Injectable({
  providedIn: 'root',
})
export class UserApiKeyResolver {
  constructor(
    private adminService: AdminService,
    private router: Router,
    protected commonFormServices: CommonFormServices,
    private sprErrorHandler: SprErrorHandler
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ApiKey> {
    return this.adminService
      .getApiKeyRecord(
        route.paramMap.get(UrlSegmentsConst.ID) !== RoutingConst.NEW ? route.paramMap.get(UrlSegmentsConst.ID) : null
      )
      .pipe(
        catchError((error) => {
          void this.router.navigate([RoutingConst.INTERNAL, RoutingConst.ADMIN, route.parent.url[0].path]);
          this.sprErrorHandler.handleError(error as Error);
          return EMPTY;
        })
      );
  }
}
