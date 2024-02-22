import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthEvent, BaseEditForm } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
@Injectable()
export class ConfirmExitGuard {
  isUserLoggedIn: boolean;

  canDeactivate(
    component: BaseEditForm<unknown>,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    const skip = AuthEvent.isUnauthError || (nextState && nextState.url.includes(RoutingConst.LOGIN));
    AuthEvent.isUnauthError = false;
    return skip || component.canDeactivate();
  }
}
