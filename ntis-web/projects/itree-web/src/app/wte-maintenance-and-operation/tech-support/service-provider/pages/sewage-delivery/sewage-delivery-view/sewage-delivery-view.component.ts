import { Component, OnInit } from '@angular/core';
import {
  SWG_DLV_STS_REJECTED,
  SWG_DLV_STS_SUBMITTED,
} from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisSludgeDeliveryDetails } from '@itree-commons/src/lib/model/api/api';
import { NtisTechSupportService } from '../../../../shared/services/ntis-tech-support.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { UsedSewageFacilityComponent } from '../../../components/used-sewage-facility/used-sewage-facility.component';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NTIS_SEWAGE_DELIVERY_EDIT } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';

@Component({
  selector: 'app-sewage-delivery-view',
  templateUrl: './sewage-delivery-view.component.html',
  styleUrls: ['./sewage-delivery-view.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, NtisSharedModule, UsedSewageFacilityComponent],
})
export class SewageDeliveryViewComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryView';
  wdId: string;
  delivery: NtisSludgeDeliveryDetails;
  readonly SWG_DLV_STS_SUBMITTED = SWG_DLV_STS_SUBMITTED;
  readonly SWG_DLV_STS_REJECTED = SWG_DLV_STS_REJECTED;
  destroy$: Subject<boolean> = new Subject<boolean>();
  userCanUpdate: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private techSupportService: NtisTechSupportService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.userCanUpdate = this.authService.isFormActionEnabled(NTIS_SEWAGE_DELIVERY_EDIT, ActionsEnum.ACTIONS_UPDATE);
    if (this.activatedRoute.snapshot.queryParams.token) {
      this.techSupportService
        .getDeliveryByToken(this.activatedRoute.snapshot.queryParams.token as string)
        .subscribe((result) => {
          this.wdId = result.toString();
          this.getDeliveryDetails(this.wdId);
        });
    } else {
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        this.wdId = (params.id as string) || null;
        this.getDeliveryDetails(this.wdId);
      });
    }
  }

  getDeliveryDetails(id: string): void {
    this.techSupportService.getSewageDeliveryView(id).subscribe((result) => {
      this.delivery = result;
    });
  }

  navigateToCopy(): void {
    if (this.delivery.wd_state_clsf === SWG_DLV_STS_REJECTED) {
      void this.router.navigate(['./', RoutingConst.EDIT, this.wdId], {
        relativeTo: this.activatedRoute.parent,
        queryParams: { actionType: ActionsEnum.ACTIONS_COPY },
      });
    }
  }

  navigateToEdit(): void {
    if (this.delivery.wd_state_clsf === this.SWG_DLV_STS_SUBMITTED) {
      void this.router.navigate([
        '/',
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.SEWAGE_DELIVERIES,
        RoutingConst.EDIT,
        this.wdId,
      ]);
    }
  }

  onCancel(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true }, relativeTo: this.activatedRoute.parent };
    void this.router.navigate(['.'], navigationExtras);
  }
}
