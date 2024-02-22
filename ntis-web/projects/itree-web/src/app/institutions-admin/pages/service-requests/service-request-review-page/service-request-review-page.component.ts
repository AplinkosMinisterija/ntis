import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { NTIS_SRV_ITEM_TYPE } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { NTIS_SERVICE_REQUEST_REVIEW } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisServiceReqDetails, SprFile } from '@itree-commons/src/lib/model/api/api';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import {
  ServiceItemType,
  ServiceRequestStatus,
  ServiceType,
} from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { ServiceRequestItem } from '../../../models/browse-pages';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { ServiceRequestContentComponent } from '../../../components/service-request-content/service-request-content.component';
import { ServiceRequestPropsComponent } from '../../../components/service-request-props/service-request-props.component';
import { ServiceRequestDetailsComponent } from '../../../components/service-request-details/service-request-details.component';
import { DialogModule } from 'primeng/dialog';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-service-request-review-page',
  templateUrl: './service-request-review-page.component.html',
  styleUrls: ['./service-request-review-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    ServiceRequestContentComponent,
    ServiceRequestPropsComponent,
    ServiceRequestDetailsComponent,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
  ],
})
export class ServiceRequestReviewPageComponent implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.serviceRequest';
  readonly formCode = NTIS_SERVICE_REQUEST_REVIEW;
  destroy$: Subject<boolean> = new Subject<boolean>();
  data: NtisServiceReqDetails;
  dataForUpdate: NtisServiceReqDetails;
  isEditable: boolean = false;
  isServiceProvider: boolean;
  showConfirmRejectButtons: boolean;
  showDialog: boolean = false;
  dialogForm = new FormGroup({
    sr_removal_reason: new FormControl<string>('', Validators.maxLength(100)),
  });
  organization: string;
  statusDate: string;
  registrationDate: string;
  requestFiles: SprFile[];
  registeredServices: ServiceRequestItem[];
  isConfirmed: boolean = false;
  requestId: number;
  serviceType: string;
  userCanUpdate: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private institutionsAdminService: InstitutionsAdminService,
    private commonFormServices: CommonFormServices,
    private commonService: CommonService,
    private authService: AuthService,
    private datePipe: S2DatePipe
  ) {
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
  }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.queryParams.token) {
      this.institutionsAdminService
        .getSrvReqByToken(this.activatedRoute.snapshot.queryParams.token as string)
        .subscribe((result) => {
          this.requestId = result;
          this.getServiceRequest(this.requestId);
        });
    } else {
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        this.requestId = params.id as number;
        this.serviceType = params.type as string;
        this.isServiceProvider = this.serviceType === ServiceType.serviceProvider.toLowerCase();
        this.getServiceRequest(this.requestId);
      });
    }
  }

  getServiceRequest(id: number): void {
    this.institutionsAdminService.getServiceRequestInfo(id).subscribe((result) => {
      this.data = result;

      this.registrationDate = this.datePipe.transform(this.data.sr_registration_date);
      this.statusDate =
        this.data.sr_status === ServiceRequestStatus.submitted
          ? null
          : this.datePipe.transform(this.data.sr_status_date);

      this.commonFormServices.translate
        .get(this.translationsReference + '.legalPersonCode')
        .subscribe((translation: string) => {
          this.organization = `${this.data.org_name} (${translation} ${this.data.org_code})`;
        });

      this.showConfirmRejectButtons =
        this.data.sr_status === ServiceRequestStatus.submitted && this.userCanUpdate ? true : false;

      this.commonService.getClsf(NTIS_SRV_ITEM_TYPE).subscribe((result) => {
        this.registeredServices = result
          .filter((item) =>
            this.isServiceProvider ? item.key !== ServiceItemType.treatment : item.key === ServiceItemType.treatment
          )
          .map((item) => ({
            code: item.key,
            name: item.display,
            registered: this.data.registered_services?.includes(item.key),
          }));
      });
    });

    this.institutionsAdminService.getServiceRequestFiles(id).subscribe((result) => {
      this.requestFiles = result;
    });
  }

  onReject(): void {
    const reason = this.dialogForm.controls.sr_removal_reason.value
      ? this.dialogForm.controls.sr_removal_reason.value
      : null;
    this.onReqStatusChange(ServiceRequestStatus.rejected, reason);
  }

  onConfirm(): void {
    this.onReqStatusChange(ServiceRequestStatus.confirmed);
  }

  onCancel(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    this.userCanUpdate
      ? void this.commonFormServices.router.navigate(
          [RoutingConst.INTERNAL, NtisRoutingConst.INSTITUTIONS, NtisRoutingConst.SERVICE_REQUESTS_LIST],
          navigationExtras
        )
      : void this.commonFormServices.router.navigate(
          [RoutingConst.INTERNAL, NtisRoutingConst.INSTITUTIONS, NtisRoutingConst.SP_WM_SERVICE_REQUESTS_LIST],
          navigationExtras
        );
  }

  hideDialog(): void {
    this.showDialog = false;
    this.onCancel();
  }

  onReqStatusChange(status: string, reason?: string): void {
    this.dataForUpdate = this.data;
    this.dataForUpdate.sr_status = status;
    this.dataForUpdate.sr_removal_reason = reason;
    this.institutionsAdminService.setServiceRequestStatus(this.dataForUpdate).subscribe(() => {
      void this.commonFormServices.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.INSTITUTIONS,
        NtisRoutingConst.SERVICE_REQUESTS_LIST,
        status.toLowerCase(),
        this.requestId,
      ]);
      this.institutionsAdminService
        .sendServiceReqNotifications(this.requestId)
        .subscribe(() => this.institutionsAdminService.serviceStatusSubject.next());
    });
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.showDialog = false;
  }
}
