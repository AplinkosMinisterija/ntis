import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { AdminService } from '../admin-services/admin.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { EMPTY, Observable, catchError } from 'rxjs';
import { Injectable } from '@angular/core';
import { RefTranslationsObj } from '@itree-commons/src/lib/model/api/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { UrlSegmentsConst } from '@itree-commons/src/constants/urlSegments.const';
import { SprErrorHandler } from '@itree-commons/src/lib/errors/error-handler';

@Injectable({
  providedIn: 'root',
})
export class TranslationsResolver implements Resolve<RefTranslationsObj[]> {
  constructor(
    private adminService: AdminService,
    private router: Router,
    protected commonFormServices: CommonFormServices,
    private sprErrorHandler: SprErrorHandler
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<RefTranslationsObj[]> {
    return this.adminService.getTranslationsList(route.paramMap.get(UrlSegmentsConst.TABLE_NAME)).pipe(
      catchError((error) => {
        void this.router.navigate([RoutingConst.INTERNAL, RoutingConst.ADMIN, route.parent.url[0].path]);
        this.sprErrorHandler.handleError(error as Error);
        return EMPTY;
      })
    );
  }
}
