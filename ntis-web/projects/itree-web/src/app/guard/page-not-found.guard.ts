import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PageNotFoundGuard implements CanActivate {
  constructor(private router: Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (state.url === `/${RoutingConst.PAGENOTFOUND}` && AuthUtil.isLoggedIn()) {
      return this.router.createUrlTree(['/', RoutingConst.INTERNAL, RoutingConst.PAGENOTFOUND]);
    }
    return true;
  }
}
