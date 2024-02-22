import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateChild, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthEvent } from './auth.event';
import { AuthUtil } from './auth.util';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.checkSession(state) && this.checkStatus(state);
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.canActivate(childRoute, state);
  }

  protected checkSession(state: RouterStateSnapshot): boolean {
    if (AuthUtil.isLoggedIn()) {
      return true;
    }
    AuthEvent.userLoggedIn.next({
      userLoggedIn: false,
      session: null,
      type: AuthEvent.AUTH_EVENT_NO_AUTH,
      returnUrl: state.url,
    });
    return false;
  }

  protected checkStatus(
    state: RouterStateSnapshot
  ): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return true;
  }
}
