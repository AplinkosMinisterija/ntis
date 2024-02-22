import { AuthGuard, AuthUtil } from '@itree/ngx-s2-commons';
import { Observable, map } from 'rxjs';
import { Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Injectable } from '@angular/core';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';

@Injectable()
export class SprOrgSelectGuard extends AuthGuard {
  constructor(private router: Router, private authService: AuthService) {
    super();
  }

  protected override checkStatus(
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    if (state.url === `/${RoutingConst.INTERNAL}/${RoutingConst.ORG_SELECT}`) {
      if (typeof AuthUtil.getOrgId() === 'number') {
        return this.router.createUrlTree(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
      }
      return true;
    }
    if (typeof AuthUtil.getOrgId() !== 'number') {
      return this.authService
        .getUserOrganizations()
        .pipe(
          map((orgs) =>
            orgs?.length ? this.router.createUrlTree(['/', RoutingConst.INTERNAL, RoutingConst.ORG_SELECT]) : true
          )
        );
    }
    return true;
  }
}
