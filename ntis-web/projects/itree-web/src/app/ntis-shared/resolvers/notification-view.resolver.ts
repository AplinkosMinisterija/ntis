import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { EMPTY, Observable, catchError } from 'rxjs';
import { NtisCommonService } from '../services/ntis-common.service';
import { UrlSegmentsConst } from '@itree-commons/src/constants/urlSegments.const';
import { NtisNotificationViewModel } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '../constants/ntis-routing.const';
import { SprErrorHandler } from '@itree-commons/src/lib/errors/error-handler';

@Injectable({
  providedIn: 'root',
})
export class NotificationViewResolver {
  constructor(
    private ntisCommonService: NtisCommonService,
    protected commonFormServices: CommonFormServices,
    private sprErrorHandler: SprErrorHandler
  ) {}
  resolve(route: ActivatedRouteSnapshot): Observable<NtisNotificationViewModel> {
    return this.ntisCommonService.loadNotification(route.paramMap.get(UrlSegmentsConst.ID) as unknown as number).pipe(
      catchError((error) => {
        void this.commonFormServices.router.navigate([RoutingConst.INTERNAL, NtisRoutingConst.NOTIFICATIONS_LIST]);
        this.sprErrorHandler.handleError(error as Error);
        return EMPTY;
      })
    );
  }
}
