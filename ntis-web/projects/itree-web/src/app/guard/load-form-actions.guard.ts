import { Injectable } from '@angular/core';
import { CanActivate, UrlTree } from '@angular/router';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoadFormActionsGuard implements CanActivate {
  constructor(private authService: AuthService) {}

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return new Promise((resolve) => {
      if (
        !this.authService.loadedFormActions?.roleName ||
        (this.authService.loadedFormActions.roleName === AuthService.PUBLIC_ROLE_CODE && AuthUtil.isLoggedIn()) ||
        (this.authService.loadedFormActions.roleName !== AuthService.PUBLIC_ROLE_CODE && !AuthUtil.isLoggedIn())
      ) {
        this.authService.loadAndGetFormActions().subscribe({
          next: () => {
            resolve(true);
          },
          error: () => {
            resolve(true);
          },
        });
      } else {
        resolve(true);
      }
    });
  }
}
