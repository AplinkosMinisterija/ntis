import { WebSessionInfo } from './model/common.api';
import { TranslateService } from '@ngx-translate/core';
import { AppMessages } from './message/app.messages';
import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, ActivationEnd, NavigationEnd, Router } from '@angular/router';
import { AuthEvent } from './auth/auth.event';
import { AuthUtil } from './auth/auth.util';
import { initLang, setLang } from './i18n/lang.util';

@Component({ template: '' })
export abstract class BaseAppComponent<T extends WebSessionInfo> implements OnInit {
  currentUser: T;
  paramsLang: string;

  constructor(
    protected router: Router,
    protected appMessages: AppMessages,
    protected translate: TranslateService,
    protected route: ActivatedRoute,
    protected ngZone: NgZone
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof ActivationEnd) {
        this.paramsLang = event.snapshot.queryParams['lang'] as string;
        if (this.paramsLang) {
          setLang(this.translate, this.paramsLang);
        }
      }
      initLang(this.translate);
    });

    AuthEvent.userLoggedIn.subscribe((res: AuthEvent) => {
      if (res.type === AuthEvent.AUTH_EVENT_UPDATE) {
        this.onUserInfoUpdate(res.session as T);
      } else {
        if (res.userLoggedIn) {
          this.setUser();
          this.onLogin();
          this.handleRegStatus(res);
        } else if (res.type !== AuthEvent.AUTH_EVENT_LOGOUT) {
          this.clearUser();
          this.onLogout();
          this.ngZone.run(() => {
            void this.router
              .navigate([this.getLoginUrl()], { queryParams: { returnUrl: this.getReturnUrl(res) } })
              .then(() => {
                if (res.type === AuthEvent.AUTH_EVENT_401) {
                  this.showNoAuthWarning();
                }
              });
          });
        }
        if (res.type === AuthEvent.AUTH_EVENT_LOGOUT) {
          this.clearUser();
          this.onLogout();
        }
      }
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  protected onLogin(): void {}

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  protected onLogout(): void {}

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected handleRegStatus(regStatus: AuthEvent): void {}

  protected onUserInfoUpdate(session: T): void {
    AuthUtil.updateSessionInfo(session);
    this.currentUser = session;
  }

  protected abstract getLoginUrl(): string;

  ngOnInit(): void {
    this.setUser();
    this.router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        this.appMessages.clearMessagesExceptSuccess();
      }
    });
  }

  protected clearUser(): void {
    this.currentUser = null;
    AuthUtil.clearSessionStorage();
  }

  protected setUser(): void {
    this.currentUser = AuthUtil.getSessionInfo() as T;
    if (this.currentUser != null && this.currentUser.language != null) {
      setLang(this.translate, this.currentUser.language.toLowerCase());
    }
  }

  private showNoAuthWarning(): void {
    this.translate.get('common.message.noSession').subscribe((translation: string) => {
      this.appMessages.showWarning('', translation, AuthEvent.AUTH_EVENT_NO_AUTH);
    });
  }

  getReturnUrl(authEvent: AuthEvent): string {
    return authEvent.returnUrl || '';
  }

  isLoggedIn(): boolean {
    return AuthUtil.isLoggedIn();
  }
}
