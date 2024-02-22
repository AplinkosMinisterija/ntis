import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { SprRouteData } from '@itree-commons/src/lib/types/routing';
import { Observable } from 'rxjs';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { AuthUtil } from '@itree/ngx-s2-commons';

@Injectable({
  providedIn: 'root',
})
export class CheckFormActionsGuard {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const routeData = route.data as SprRouteData;
    if (routeData.formCode && !this.authService.isFormAvailable(routeData.formCode)) {
      if (!AuthUtil.isLoggedIn()) {
        return this.router.createUrlTree(['/', NtisRoutingConst.MAP]);
      } else {
        return this.router.createUrlTree(['/', RoutingConst.DASHBOARD]);
      }
    }
    return true;
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivate(childRoute);
  }
}
