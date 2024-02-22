import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { NTIS_SERVICE_REQUEST_CREATE } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  NtisNewServiceRequest,
  NtisServiceReqDetails,
  NtisServiceReqItemsDAO,
  SprFile,
} from '@itree-commons/src/lib/model/api/api';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import {
  ServiceItemStatus,
  ServiceItemType,
  ServiceRequestStatus,
  ServiceType,
} from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { ServiceRequestItem } from '../../../models/browse-pages';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { ServiceRequestContentComponent } from '../../../components/service-request-content/service-request-content.component';
import { ServiceRequestDetailsComponent } from '../../../components/service-request-details/service-request-details.component';
import { ServiceRequestPropsComponent } from '../../../components/service-request-props/service-request-props.component';
import { Subject, takeUntil } from 'rxjs';
import { EMAIL_PATTERN, PHONE_PATTERN, WEBSITE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';

@Component({
  selector: 'app-service-request-create-page',
  templateUrl: './service-request-create-page.component.html',
  styleUrls: ['./service-request-create-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    ReactiveFormsModule,
    NtisSharedModule,
    ServiceRequestContentComponent,
    ServiceRequestDetailsComponent,
    ServiceRequestPropsComponent,
  ],
})
export class ServiceRequestCreatePageComponent implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.serviceRequest';
  readonly formCode = NTIS_SERVICE_REQUEST_CREATE;
  destroy$: Subject<boolean> = new Subject<boolean>();
  data: NtisNewServiceRequest;
  isEditable: boolean = true;
  isServiceProvider: boolean;
  organization: string;
  applicant: string;
  requestFiles: SprFile[] = [];
  registeredServices: ServiceRequestItem[];
  selectedServices: string[] = [];
  serviceType: string;
  checkChildForm: Subject<boolean> = new Subject();
  termsLink: string;

  form = new FormGroup({
    sr_data_is_correct: new FormControl<boolean>(null, Validators.requiredTrue),
    sr_rules_accepted: new FormControl<boolean>(null, Validators.requiredTrue),
    selected_items: new FormControl<string[]>(null, Validators.required),
    sr_email: new FormControl<string>(
      null,
      Validators.compose([
        Validators.required,
        Validators.maxLength(100),
        Validators.pattern(new RegExp(EMAIL_PATTERN)),
      ])
    ),
    sr_phone: new FormControl<string>(
      null,
      Validators.compose([Validators.required, Validators.pattern(new RegExp(PHONE_PATTERN))])
    ),
    sr_homepage: new FormControl<string>(null, Validators.pattern(new RegExp(WEBSITE_PATTERN))),
  });

  constructor(
    private activatedRoute: ActivatedRoute,
    private institutionsAdminService: InstitutionsAdminService,
    private commonFormServices: CommonFormServices,
    private authService: AuthService,
    private appDataService: AppDataService
  ) {
    this.termsLink = this.appDataService.getPropertyValue('NTIS_TERMS_URL');
  }

  ngOnInit(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.serviceType = params.type as string;
      this.isServiceProvider = this.serviceType === ServiceType.serviceProvider.toLowerCase();
      if (
        this.serviceType !== ServiceType.serviceProvider.toLowerCase() &&
        this.serviceType !== ServiceType.waterManager.toLowerCase()
      ) {
        this.commonFormServices.translate.get('common.error.action_not_granted').subscribe((translate: string) => {
          this.commonFormServices.appMessages.showError('', translate);
          return this.onBackToList();
        });
      }
    });

    this.institutionsAdminService.getActiveServicesInfo().subscribe((result) => {
      this.registeredServices = result.data
        .filter((item) =>
          this.isServiceProvider ? item.code !== ServiceItemType.treatment : item.code === ServiceItemType.treatment
        )
        .map((item) => ({
          ...item,
          registered: false,
        }));
    });

    this.getNewServiceRequest();
  }

  getNewServiceRequest(): void {
    this.institutionsAdminService.getNewServiceReqInfo().subscribe((result) => {
      this.data = result;

      this.commonFormServices.translate
        .get(this.translationsReference + '.legalPersonCode')
        .subscribe((translation: string) => {
          this.organization = `${this.data.org_name} (${translation} ${this.data.org_code})`;
        });

      this.applicant = `${this.data.per_name} ${this.data.per_surname}`;
      this.data.org_email = this.data.org_email?.split(',')[0];

      this.setOrgDetails(result);
    });
  }

  onSubmit(): void {
    this.commonFormServices.appMessages.clearMessages();
    this.form.markAllAsTouched();
    if (
      this.form.controls.selected_items.value &&
      this.form.controls.selected_items.value.includes(ServiceItemType.tests) &&
      this.requestFiles.length === 0
    ) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.fileIsRequired')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
      return;
    }
    if (this.form.valid) {
      this.saveServiceRequest();
    } else if (!this.form.controls.selected_items.value) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.atLeastOneIsRequired')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else if (!this.form.controls.sr_rules_accepted.value || !this.form.controls.sr_data_is_correct.value) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.bothConfirmationsAreRequired')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else {
      this.checkChildForm.next(true);
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  saveServiceRequest(): void {
    const result: NtisServiceReqDetails = {} as NtisServiceReqDetails;

    result.sr_type = this.serviceType.toUpperCase();
    result.sr_resp_person_description = this.applicant;
    result.sr_email = this.form.controls.sr_email.value;
    result.sr_phone = this.form.controls.sr_phone.value;
    result.sr_homepage = this.form.controls.sr_homepage.value;
    result.sr_data_is_correct = DB_BOOLEAN_TRUE;
    result.sr_rules_accepted = DB_BOOLEAN_TRUE;
    result.sr_email_verified =
      this.data.org_email === this.form.controls.sr_email.value ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE;
    result.sr_status =
      this.form.controls.selected_items.value.includes(ServiceItemType.tests) ||
      this.form.controls.selected_items.value.includes(ServiceItemType.treatment)
        ? ServiceRequestStatus.submitted
        : ServiceRequestStatus.confirmed;
    result.attachments = this.requestFiles;
    result.reqItemsDAO = [];
    this.selectedServices.forEach((srv) => {
      const requestItem: NtisServiceReqItemsDAO = {} as NtisServiceReqItemsDAO;
      requestItem.sri_service_type = srv;
      requestItem.sri_status = ServiceItemStatus.notRegistered;
      result.reqItemsDAO.push(requestItem);
    });

    this.institutionsAdminService.setServiceRequest(result).subscribe((res) => {
      this.commonFormServices.translate
        .get(this.translationsReference + '.requestSubmitted')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.loadFormActionsAndMenu();
          void this.commonFormServices.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
          this.institutionsAdminService
            .sendNewServiceReqNotifications(res.sr_id)
            .subscribe(() => this.institutionsAdminService.serviceStatusSubject.next());
        });
    });
  }

  loadFormActionsAndMenu(): void {
    this.authService.loadFormActions();
    this.authService.loadMenu();
  }

  setServiceItems(requestItems: ServiceRequestItem[]): void {
    if (requestItems.length > 0) {
      this.selectedServices = requestItems.map((item) => item.code);
      this.form.controls.selected_items.setValue(this.selectedServices);
    } else {
      this.form.controls.selected_items.setValue(null);
    }
  }

  setServiceFiles(requestFiles: SprFile[]): void {
    this.requestFiles = requestFiles;
  }

  setOrgDetails(orgDetails: NtisNewServiceRequest): void {
    this.form.controls.sr_email.setValue(orgDetails.org_email);
    this.form.controls.sr_phone.setValue(orgDetails.org_phone);
    this.form.controls.sr_homepage.setValue(orgDetails.org_website);
  }

  onBackToList(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true }, relativeTo: this.activatedRoute.parent };
    void this.commonFormServices.router.navigate(['.'], navigationExtras);
  }
}
