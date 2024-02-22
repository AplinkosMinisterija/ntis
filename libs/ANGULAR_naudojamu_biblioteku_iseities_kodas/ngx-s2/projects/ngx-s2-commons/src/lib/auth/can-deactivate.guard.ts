import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthEvent } from './auth.event';

export interface CanComponentDeactivate {
  canDeactivate: () => Observable<boolean> | Promise<boolean> | boolean;
}

export abstract class CanDeactivateGuard {
  canDeactivate(
    component: CanComponentDeactivate,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ): any {
    const skip = AuthEvent.isUnauthError || (nextState && nextState.url.includes(this.getLoginPath()));
    AuthEvent.isUnauthError = false;
    return skip || component.canDeactivate();
  }

  public abstract getLoginPath(): string;
}
