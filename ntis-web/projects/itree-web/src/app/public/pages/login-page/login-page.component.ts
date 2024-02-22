import { Component } from '@angular/core';
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
export class LoginPageComponent {
  destroy$: Subject<boolean> = new Subject<boolean>();
  token: string = '';
  showRegMessage: boolean = false;

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
      const authExtData: Record<string, string> = {
        USER_TYPE: loginData.userType,
        AUTH_TYPE: AuthType.USER_PASSWORD_AUTH,
      };
      this.authService
        .loginWithPassword(loginData.username, loginData.password, this.authService.getReturnUrl(), authExtData)
        .subscribe();
    } else {
      // personal code login
    }
  }
}
