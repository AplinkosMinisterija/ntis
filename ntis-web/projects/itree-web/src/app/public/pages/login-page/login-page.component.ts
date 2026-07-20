import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { LoginData } from '../../models/login-data';
import { AuthType } from '../../enums/auth.enums';
import { Subject, takeUntil } from 'rxjs';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnDestroy {
  destroy$: Subject<boolean> = new Subject<boolean>();
  token: string = '';
  showRegMessage: boolean = false;

  show2fa = false;
  twoFactorEmailMasked = '';
  twoFactorCode = '';
  resendCooldown = 0;
  private pendingToken = '';
  private pendingUsername = '';
  private pendingUserType = '';
  private pendingReturnUrl = '';
  private resendTimer?: ReturnType<typeof setInterval>;

  constructor(
    private authService: AuthService,
    private activatedRoute: ActivatedRoute,
    public faIconsService: FaIconsService,
    private router: Router
  ) {
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((queryParams) => {
      this.showRegMessage = queryParams.showRegMessage === true;
    });
    if (AuthUtil.isLoggedIn()) {
      void this.router.navigate([RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
    }
  }

  public onLogin(loginData: LoginData): void {
    if (loginData.isLoginWithPass) {
      this.pendingUsername = loginData.username;
      this.pendingUserType = loginData.userType;
      this.pendingReturnUrl = this.authService.getReturnUrl();
      this.authService
        .loginWithPassword(loginData.username, loginData.password, this.pendingReturnUrl, this.authExtData())
        .subscribe((response) => {
          const s = response.session as {
            twoFactorRequired?: boolean;
            twoFactorEmailMasked?: string;
            twoFactorToken?: string;
          };
          if (s?.twoFactorRequired) {
            this.twoFactorEmailMasked = s.twoFactorEmailMasked || '';
            this.pendingToken = s.twoFactorToken || '';
            this.twoFactorCode = '';
            this.show2fa = true;
            this.startResendCooldown();
          }
        });
    } else {
      // personal code login
    }
  }

  private authExtData(): Record<string, string> {
    return { USER_TYPE: this.pendingUserType, AUTH_TYPE: AuthType.USER_PASSWORD_AUTH };
  }

  public submit2fa(): void {
    if (!this.twoFactorCode) {
      return;
    }
    this.authService
      .verify2fa(this.pendingUsername, this.pendingToken, this.twoFactorCode, this.authExtData(), this.pendingReturnUrl)
      .subscribe(() => {
        this.show2fa = false;
        this.clearResendTimer();
      });
  }

  public resend2fa(): void {
    if (this.resendCooldown > 0) {
      return;
    }
    this.authService.resend2fa(this.pendingUsername, this.pendingToken, this.authExtData()).subscribe(() => {
      this.twoFactorCode = '';
      this.startResendCooldown();
    });
  }

  public cancel2fa(): void {
    this.show2fa = false;
    this.clearResendTimer();
  }

  private startResendCooldown(): void {
    this.clearResendTimer();
    this.resendCooldown = 30;
    this.resendTimer = setInterval(() => {
      this.resendCooldown -= 1;
      if (this.resendCooldown <= 0) {
        this.clearResendTimer();
      }
    }, 1000);
  }

  private clearResendTimer(): void {
    if (this.resendTimer) {
      clearInterval(this.resendTimer);
      this.resendTimer = undefined;
    }
  }

  public ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
    this.clearResendTimer();
  }
}
