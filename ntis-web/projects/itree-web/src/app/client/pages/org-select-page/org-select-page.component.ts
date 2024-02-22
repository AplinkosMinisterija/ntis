import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';

@Component({
  selector: 'app-org-select-page',
  templateUrl: './org-select-page.component.html',
  styleUrls: ['./org-select-page.component.scss'],
})
export class OrgSelectPageComponent implements OnInit {
  readonly translationsReference = 'pages.orgSelect';
  selectedOrgId: string;

  constructor(public authService: AuthService, public faIconsService: FaIconsService, private router: Router) {}

  ngOnInit(): void {
    this.authService.getUserOrganizations().subscribe((availableOrganizations) => {
      if (availableOrganizations?.length === 1) {
        this.setUserOrganizationAndNavigate(availableOrganizations[0].org_id);
      }
    });
  }

  handleClickContinue(): void {
    if (this.selectedOrgId) {
      this.setUserOrganizationAndNavigate(this.selectedOrgId);
    }
  }

  private setUserOrganizationAndNavigate(orgId: string): void {
    this.authService.setUserOrganization(orgId).subscribe(() => {
      void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]).then(() => {
        window.location.reload();
      });
    });
  }
}
