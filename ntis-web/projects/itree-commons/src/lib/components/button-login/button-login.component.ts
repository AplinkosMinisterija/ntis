import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthEvent, AuthUtil } from '@itree/ngx-s2-commons';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Subscription } from 'rxjs';

@Component({
  selector: 'spr-button-login',
  templateUrl: './button-login.component.html',
  styleUrls: ['./button-login.component.scss'],
})
export class ButtonLoginComponent implements OnInit, OnDestroy {
  readonly RoutingConst = RoutingConst;
  private subscription: Subscription;
  isUserLoggedIn: boolean = AuthUtil.isLoggedIn();

  constructor(private authService: AuthService, private router: Router, public faIconsService: FaIconsService) {}

  ngOnInit(): void {
    this.subscription = AuthEvent.userLoggedIn.subscribe((res) => {
      this.isUserLoggedIn = res.userLoggedIn;
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  authenticate(): void {
    void this.router.navigate([RoutingConst.LOGIN]);
  }
}
