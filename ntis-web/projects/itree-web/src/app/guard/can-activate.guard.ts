import { AuthGuard, AuthUtil } from '@itree/ngx-s2-commons';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Injectable()
export class SprCanActivateGuard extends AuthGuard {
  constructor(private router: Router) {
    super();
  }

  protected override checkStatus(
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (!AuthUtil.isTermsAccepted() && state.url !== `/${RoutingConst.TERMS_AND_CONDITIONS}`) {
      return this.router.createUrlTree(['/', RoutingConst.TERMS_AND_CONDITIONS]);
    }
    if (
      AuthUtil.getSessionInfo()?.usrPasswordChangeToken &&
      state.url !== `/${RoutingConst.INTERNAL}/${RoutingConst.SPARK_CHANGE_PASSWORD}`
    ) {
      return this.router.createUrlTree(['/', RoutingConst.INTERNAL, RoutingConst.SPARK_CHANGE_PASSWORD]);
    }
    return true;
  }
}
