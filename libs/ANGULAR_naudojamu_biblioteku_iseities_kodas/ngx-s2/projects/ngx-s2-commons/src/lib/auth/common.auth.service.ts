import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthEvent } from './auth.event';
import { HttpClient } from '@angular/common/http';
import { AuthUtil } from './auth.util';
import { LoginResult, WebSessionInfo } from '../model/common.api';

@Injectable({
  providedIn: 'root',
})
export class CommonAuthService {
  constructor(protected http: HttpClient) {}

  protected processLogin(
    response: LoginResult<WebSessionInfo>,
    returnUrl: string,
    checkForPasswordChangeToken: boolean = true
  ): void {
    if (response && response.session) {
      AuthUtil.setLoginResult(response);
      if (response.token && (!checkForPasswordChangeToken || !response.session.usrPasswordChangeToken)) {
        AuthUtil.setTermsAccepted(response.session?.usrTermsAccepted === 'Y');
        this.getRegistrationStatus().subscribe((regStatus) => {
          AuthEvent.userLoggedIn.next({
            userLoggedIn: true,
            session: response.session,
            type: AuthEvent.AUTH_EVENT_LOGIN,
            regStatus,
            returnUrl,
          });
        });
      }
    }
  }

  protected getRegistrationStatus(): Observable<string> {
    return of('ok');
  }

  protected processLogout(): void {
    AuthUtil.clearSessionStorage();
    AuthEvent.userLoggedIn.next({ userLoggedIn: false, session: null, type: AuthEvent.AUTH_EVENT_LOGOUT });
  }
}
