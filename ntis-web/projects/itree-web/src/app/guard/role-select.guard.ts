import { AuthGuard, AuthUtil } from '@itree/ngx-s2-commons';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Injectable()
export class SprRoleSelectGuard extends AuthGuard {
  constructor(private router: Router) {
    super();
  }

  protected override checkStatus(
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (state.url === `/${RoutingConst.INTERNAL}/${RoutingConst.ORG_SELECT}`) {
      return true;
    } else if (state.url === `/${RoutingConst.INTERNAL}/${RoutingConst.ROLE_SELECT}`) {
      if (typeof AuthUtil.getRoleId() === 'number') {
        return this.router.createUrlTree(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
      }
      return true;
    }
    if (typeof AuthUtil.getRoleId() !== 'number') {
      return this.router.createUrlTree(['/', RoutingConst.INTERNAL, RoutingConst.ROLE_SELECT]);
    }
    return true;
  }
}
