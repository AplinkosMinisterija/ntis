import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-role-select-page',
  templateUrl: './role-select-page.component.html',
  styleUrls: ['./role-select-page.component.scss'],
})
export class RoleSelectPageComponent implements OnInit, OnDestroy {
  selectedRoleId: string;
  availableRolesSubscription: Subscription;

  constructor(public authService: AuthService, public faIconsService: FaIconsService, private router: Router) {}

  ngOnInit(): void {
    this.availableRolesSubscription = this.authService.availableRoles$.subscribe((roles) => {
      if (roles?.length === 1 && roles[0].rol_id === `${AuthService.MERGED_ROLE_ID}`) {
        const loginResult = AuthUtil.getLoginResult();
        loginResult.session.roleId = AuthService.MERGED_ROLE_ID;
        AuthUtil.setLoginResult(loginResult);
        this.authService.loadInternalMenu();
        void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
      }
    });
  }

  ngOnDestroy(): void {
    this.availableRolesSubscription.unsubscribe();
  }

  handleClickContinue(): void {
    if (this.selectedRoleId) {
      this.authService.setUserRole(this.selectedRoleId).subscribe(() => {
        void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
      });
    }
  }
}
