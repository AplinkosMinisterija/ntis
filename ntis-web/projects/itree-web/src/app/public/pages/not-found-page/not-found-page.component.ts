import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-not-found-page',
  templateUrl: './not-found-page.component.html',
  styleUrls: ['./not-found-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class NotFoundPageComponent {
  isUserLoggedIn = false;

  constructor(private router: Router) {
    this.isUserLoggedIn = AuthUtil.isLoggedIn();
  }

  navigate(): void {
    void this.router.navigate(this.isUserLoggedIn ? ['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD] : ['/']);
  }
}
