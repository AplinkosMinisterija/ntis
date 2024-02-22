import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { TableModule } from 'primeng/table';
import { NtisSharedModule } from '../../ntis-shared.module';
import { ActivatedRoute } from '@angular/router';
import { NtisNotificationViewModel } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices, ExtendedSearchUpperLower } from '@itree/ngx-s2-commons';
import { NTIS_NOTIFICATION_VIEW } from '../../constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { MessageSubject, NtisNtfRefType } from '../../enums/classifiers.enums';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'ntis-notification-view-page',
  templateUrl: './notification-view-page.component.html',
  styleUrls: ['./notification-view-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
  ],
})
export class NotificationViewPageComponent implements OnInit {
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'ntisShared.pages.notificationView';
  readonly formCode = NTIS_NOTIFICATION_VIEW;
  readonly NtisNtfRefType = NtisNtfRefType;
  data: NtisNotificationViewModel;
  dateString: string;
  showContactsBlock: boolean = true;
  showClientContacts: boolean = true;

  isServiceProvider: boolean;
  isWaterManager: boolean;
  isIntsOwner: boolean;
  isSystemAdmin: boolean;
  isOrgNew: boolean;

  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(
    public faIconsService: FaIconsService,
    private activatedRoute: ActivatedRoute,
    private datePipe: S2DatePipe,
    private commonFormServices: CommonFormServices,
    private authService: AuthService
  ) {
    this.isServiceProvider = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.SERVICE_PROVIDER_ACTIONS);
    this.isWaterManager = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.WATER_MANAGER_ACTIONS);
    this.isIntsOwner = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.INTS_OWNER_ACTIONS);
    this.isSystemAdmin = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.SYSTEM_ADMIN_ACTIONS);
    this.isOrgNew = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.NEW_ORG_ACTIONS);
  }

  ngOnInit(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.data = this.activatedRoute.snapshot.data['ntfData'] as NtisNotificationViewModel;
      this.dateString = this.datePipe.transform(this.data.date);
      this.handleContactsBlock();
    });
  }

  handleContactsBlock(): void {
    if (!this.data.refType) {
      this.showContactsBlock = false;
    } else if (this.isServiceProvider || this.isWaterManager || this.isOrgNew) {
      switch (this.data.refType) {
        case (NtisNtfRefType.ORDER, NtisNtfRefType.CONTRACT, NtisNtfRefType.RESEARCH): {
          this.showClientContacts = true;
          break;
        }
        case NtisNtfRefType.DELIVERY: {
          if (
            this.data.msgSubject === MessageSubject.MSG_SBJ_DELIVERY_SUBMITTED ||
            this.data.msgSubject === MessageSubject.MSG_SBJ_DELIVERY_CANCELLED
          ) {
            this.showClientContacts = true;
          } else {
            this.showClientContacts = false;
          }
          break;
        }
        case NtisNtfRefType.SYSTEM: {
          this.showClientContacts = false;
          break;
        }
        case NtisNtfRefType.SRV_REQ: {
          if (this.data.msgSubject !== MessageSubject.MSG_SBJ_APPLICATION_REJECTED) {
            this.showContactsBlock = false;
          }
          this.showClientContacts = false;
          break;
        }
        case NtisNtfRefType.FUA_AGREEMENT: {
          this.showClientContacts = false;
          this.data.contactInfo.orgName = this.data.contactInfo.orgPhone
            ? this.data.contactInfo.orgName + ', ' + this.data.contactInfo.orgPhone
            : this.data.contactInfo.orgName;
          break;
        }
      }
    } else if (this.isSystemAdmin) {
      this.showContactsBlock = false;
    } else if (this.isIntsOwner) {
      this.showClientContacts = false;
    }
  }

  onCancel(): void {
    void this.commonFormServices.router.navigate([RoutingConst.INTERNAL, NtisRoutingConst.NOTIFICATIONS_LIST]);
  }
}
