import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { Subject, takeUntil } from 'rxjs';
import { AppDataService } from '../../services/app-data.service';
import { InstitutionsAdminService } from '@itree-web/src/app/institutions-admin/services/institutions-admin.service';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { AlertTextInfo } from '../alert/alert.component';
import { AdminService } from '@itree-web/src/app/spark-admin-module/admin-services/admin.service';
import { MenuStructure } from '../../model/browse-pages';
import { getLang } from '@itree/ngx-s2-commons';

@Component({
  selector: 'spr-internal',
  templateUrl: './internal.component.html',
  styleUrls: ['./internal.component.scss'],
})
export class InternalComponent implements OnInit, OnDestroy {
  destroy$: Subject<boolean> = new Subject();
  menuItems: MenuStructure[] = [];
  multilanguageExists: boolean = true;
  logoUrl: string = AuthService.DEFAULT_RETURN_URL;
  unconfirmedServices: AlertTextInfo[] = [];
  showUsersRolesMessage: boolean = false;
  showWaterManagersWtfsMessage: boolean = false;
  showAlert = true;
  footerLine1: string = '';
  footerLine2: string = '';

  constructor(
    public faIconsService: FaIconsService,
    private authService: AuthService,
    private appDataService: AppDataService,
    private institutionsAdminService: InstitutionsAdminService,
    private router: Router,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.multilanguageExists = this.appDataService.multilanguageExists();
    this.authService.loadMenu();
    this.authService.loadedMenu$.pipe(takeUntil(this.destroy$)).subscribe((menu) => {
      this.menuItems = menu || [];
    });
    this.authService.loadedFormActions$.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.logoUrl = this.authService.getDefaultRoleUrl();
    });

    this.getServiceStatuses();
    this.institutionsAdminService.serviceStatusSubject.subscribe(() => this.getServiceStatuses());
    this.getOrgUserRolesChecked();
    this.adminService.orgUserRolesAssignedSubject.subscribe(() => this.getOrgUserRolesChecked());
    this.institutionsAdminService.waterManagersWtfsSubject.subscribe(() => this.getWaterManagersWtfs());

    if (getLang() === 'lt') {
      this.footerLine1 = this.appDataService.getPropertyValue('FOOTER_VAR_LT_1');
      this.footerLine2 = this.appDataService.getPropertyValue('FOOTER_VAR_LT_2');
    } else {
      this.footerLine1 = this.appDataService.getPropertyValue('FOOTER_VAR_EN_1');
      this.footerLine2 = this.appDataService.getPropertyValue('FOOTER_VAR_EN_2');
    }
  }

  getServiceStatuses(): void {
    this.institutionsAdminService.getServicesInfo().subscribe((result) => {
      this.unconfirmedServices = result
        .filter((item) => (item.sriId || item.srId) && item.isConfirmed !== null && !item.isConfirmed)
        .map((fItem) => {
          return {
            text: fItem.serviceName,
            link: `${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.INST_SERVICE_MANAGEMENT}`,
          } as unknown as AlertTextInfo;
        });
      this.showAlert = this.unconfirmedServices?.length > 0;
    });
  }

  getOrgUserRolesChecked(): void {
    this.adminService.checkIfOrgUserRolesAssigned().subscribe((result) => {
      this.showUsersRolesMessage = !result;
      if (!this.showAlert) {
        this.showAlert = this.showUsersRolesMessage;
      }
    });
  }

  getWaterManagersWtfs(): void {
    this.institutionsAdminService.checkIfWaterManagerWtfRegistered().subscribe((result) => {
      this.showWaterManagersWtfsMessage = !result;
      this.showAlert = this.showWaterManagersWtfsMessage;
    });
  }

  navigateToViewForm(): void {
    void this.router.navigate([
      `${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.INST_SERVICE_MANAGEMENT}`,
    ]);
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }
}
