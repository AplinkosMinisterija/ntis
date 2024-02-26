import { Component, HostListener, OnDestroy, OnInit, ViewContainerRef } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';
import { SprAvailableOrganization, SprAvailableRole } from '../../model/auth';
import { AuthService } from '../../services/auth.service';
import { FaIconsService } from '../../services/fa-icons.service';
import { SPR_CHANGE_PASSWORD, SPR_PROFILE } from '@itree-commons/src/constants/forms.constants';

type ExpandableMenuItem = 'roles' | 'organizations';

@Component({
  selector: 'spr-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.scss'],
})
export class UserMenuComponent implements OnInit, OnDestroy {
  readonly translationsReference = 'components.userMenu';
  destroy$ = new Subject<void>();
  userName: string = AuthUtil.getSessionInfo().userName;
  isMenuOpen: boolean = false;
  openListItem: ExpandableMenuItem | null;
  // Profile settings & password change paths
  passwordChangingLink: string = `/${RoutingConst.INTERNAL}/${RoutingConst.SPARK_CHANGE_PASSWORD}`;
  profileSettingsLink: string = `/${RoutingConst.INTERNAL}/${RoutingConst.SPARK_PROFILE_SETTINGS}`;

  selectedRoleText: string;
  singleRoleMode = false;
  selectedOrgText: string;
  passwordChangingEnabled: boolean;
  passwordExist: boolean;
  profileSettingsEnabled: boolean;

  constructor(
    public faIconsService: FaIconsService,
    public authService: AuthService,
    private router: Router,
    private readonly viewContainerRef: ViewContainerRef
  ) {}

  ngOnInit(): void {
    const firstName = AuthUtil.getSessionInfo()?.personName;
    const lastName = AuthUtil.getSessionInfo()?.personLastName;
    if (firstName && lastName) {
      this.userName = `${firstName} ${lastName}`;
    }

    this.authService.getUserRoles().subscribe();
    this.authService.availableRoles$.pipe(takeUntil(this.destroy$)).subscribe((roles) => {
      this.processRoleChanges(roles);
    });

    this.authService.getUserOrganizations().subscribe();
    this.authService.availableOrganizations$.pipe(takeUntil(this.destroy$)).subscribe((orgs) => {
      this.processOrgChanges(orgs);
    });

    this.profileSettingsEnabled = this.authService.isFormAvailable(SPR_PROFILE);
    this.passwordChangingEnabled = this.authService.isFormAvailable(SPR_CHANGE_PASSWORD);
    if (this.passwordChangingEnabled) {
      this.authService.checkUserPasswordExists().subscribe((res) => (this.passwordExist = res));
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  processRoleChanges(roles: SprAvailableRole[]): void {
    const sessionRoleId = AuthUtil.getRoleId();
    this.selectedRoleText = roles.find((role) => role.rol_id === `${sessionRoleId}`)?.rol_name || null;
    if (roles.length === 1 || roles?.[0]?.rol_id === '-1') {
      this.singleRoleMode = true;
    }
  }

  processOrgChanges(orgs: SprAvailableOrganization[]): void {
    const sessionOrgId = AuthUtil.getOrgId();
    this.selectedOrgText = orgs.find((org) => org.org_id === `${sessionOrgId}`)?.org_name || null;
  }

  toggleUserMenu(state?: boolean): void {
    if (!this.openListItem) {
      this.openListItem = 'organizations';
    }
    if (typeof state !== 'boolean') {
      state = !this.isMenuOpen;
    }
    if (!state) {
      this.openListItem = null;
    }
    this.isMenuOpen = state;
  }

  toggleListItem(item: ExpandableMenuItem): void {
    this.openListItem = this.openListItem === item ? null : item;
  }

  @HostListener('document:click', ['$event.target'])
  onPageClick(targetElement: HTMLElement): void {
    if (!(this.viewContainerRef.element.nativeElement as HTMLElement).contains(targetElement)) {
      this.toggleUserMenu(false);
    }
  }

  onRoleSelect(role: SprAvailableRole): void {
    this.toggleUserMenu(false);
    this.authService.setUserRole(role.rol_id).subscribe((result) => {
      this.selectedRoleText = result.session.roleName;
      if (this.router.url.startsWith(`/${RoutingConst.INTERNAL}/${RoutingConst.ROLE_SELECT}`)) {
        void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
      }
    });
  }

  handleOrganizationSelect(organization: SprAvailableOrganization): void {
    this.toggleUserMenu(false);
    const previousOrgId = AuthUtil.getOrgId();
    this.authService.setUserOrganization(organization.org_id).subscribe((result) => {
      if (previousOrgId !== result.session.orgId) {
        this.selectedOrgText = result.session.orgName;
      }
    });
  }

  navigateToLink(link: string): void {
    void this.router.navigate([link]);
    this.toggleUserMenu();
  }

  onLogout(): void {
    window.localStorage.setItem('logout-event', Math.random().toString());
    this.authService.logout().subscribe(() => {
      window.location.replace('/' + RoutingConst.LOGIN);
    });
  }
}
