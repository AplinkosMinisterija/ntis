import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { SprProfile } from '@itree-commons/src/lib/model/api/api';
import { ClientApiService } from '../../../client/services/client-api.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { QUERY_PARAMS, SERVICE_ID } from '@itree-commons/src/constants/localStorage.constants';
import { Router } from '@angular/router';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

interface QueryParameters {
  adId?: number;
  x?: number;
  y?: number;
  type?: string;
  address?: string;
  distance?: number;
}

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss'],
})
export class ProfilePageComponent extends DeprecatedBaseEditForm<SprProfile> implements OnInit {
  isEmailConfirmed: boolean = true;
  confirmedEmail: string = '';
  showConfirmButton: boolean = false;

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private clientApiService: ClientApiService,
    public faIconsService: FaIconsService,
    private ntisCommonService: NtisCommonService,
    private router: Router
  ) {
    super(formBuilder, commonFormServices);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.doLoad();
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      per_code: new FormControl({ value: null, disabled: true }),
      per_name: new FormControl({ value: null, disabled: true }),
      per_surname: new FormControl({ value: null, disabled: true }),
      per_phone_number: new FormControl(
        null,
        Validators.compose([Validators.maxLength(12), Validators.pattern(new RegExp(PHONE_PATTERN))])
      ),
      emailNotifications: new FormControl(false),
      per_email: new FormControl(
        null,
        Validators.compose([Validators.required, Validators.pattern(new RegExp(EMAIL_PATTERN))])
      ),
      api_key: new FormControl({ value: null, disabled: true }),
      api_id: new FormControl({ value: null, disabled: true }),
    });
  }

  protected doSave(value: SprProfile): void {
    if (this.confirmedEmail !== value.per_email) {
      value.per_email_confirmed = false;
      this.onConfirmEmail();
    }

    this.clientApiService.updateProfileSettings(value).subscribe(() => {
      this.commonFormServices.appMessages.showSuccess('', this.getSuccessMessage());
    });
  }

  protected doLoad(): void {
    this.clientApiService.getProfileSettings().subscribe((result) => {
      this.isEmailConfirmed = result.per_email_confirmed;
      if (this.isEmailConfirmed) {
        this.confirmedEmail = result.per_email;
      } else {
        if (result.per_email) {
          this.showConfirmButton = true;
        }
      }
      this.onLoadSuccess(result);
    });
  }

  protected setData(data: SprProfile): void {
    this.data = data;
    this.form.controls.per_code.setValue(data.per_code);
    this.form.controls.per_name.setValue(data.per_name);
    this.form.controls.per_surname.setValue(data.per_surname);
    this.form.controls.per_phone_number.setValue(data.per_phone_number);
    this.form.controls.per_email.setValue(data.per_email);
    this.form.controls.emailNotifications.setValue(!!data.emailNotifications);
    if (!data.per_email) {
      this.form.controls.emailNotifications.setValue(true);
    }
    this.form.controls.api_key.setValue(data.api_key);
    this.form.controls.api_id.setValue(data.api_id);
  }

  protected getData(): SprProfile {
    const result: SprProfile = this.data != null ? this.data : ({} as SprProfile);
    result.per_phone_number = this.form.controls.per_phone_number.value as SprProfile['per_phone_number'];
    result.per_email = this.form.controls.per_email.value as SprProfile['per_email'];
    result.api_key = this.form.controls.api_key.value as SprProfile['api_key'];
    result.api_id = this.form.controls.api_id.value as SprProfile['api_id'];
    result.emailNotifications = this.form.controls.emailNotifications.value as SprProfile['emailNotifications'];
    this.data = result;

    return result;
  }

  onConfirmEmail(): void {
    if (this.form.controls.per_email.valid) {
      const per_email = this.form.controls.per_email.value as string;
      const profile: SprProfile = { ...this.data, per_email: per_email };
      this.clientApiService.updateProfileSettings(profile).subscribe(() => {
        this.ntisCommonService.requestEmailConfirmation(per_email).subscribe(() => {
          this.commonFormServices.translate.get('pages.sprUserEdit.emailSent').subscribe((translation: string) => {
            this.confirmedEmail = '';
            this.isEmailConfirmed = false;
            this.commonFormServices.appMessages.showSuccess('', translation);
            //Jeigu paslauga buvo pradėta užsakyti dar neautentifikuotam vartotojui
            this.continueOrderService();
          });
        });
      });
    } else {
      this.commonFormServices.translate.get('pages.sprUserEdit.emailInvalid').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    }
  }

  generateApiKey(): void {
    if (this.form.controls.api_key.value != null) {
      this.clientApiService.regenerateApiKey(this.form.controls.api_id.value as number).subscribe(() => {
        this.doLoad();
      });
    } else {
      this.clientApiService.generateApiKey().subscribe(() => {
        this.doLoad();
      });
    }
  }

  continueOrderService(): void {
    const serviceId: string = localStorage.getItem(SERVICE_ID);
    const queryParamsStr: string = localStorage.getItem(QUERY_PARAMS);

    if (serviceId && queryParamsStr) {
      const queryParamsObj = JSON.parse(queryParamsStr) as QueryParameters;
      void this.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.CONTRACTS,
          NtisRoutingConst.SERVICE_SEARCH,
          NtisRoutingConst.SERVICE_ORDER_EDIT,
          serviceId,
        ],
        {
          queryParams: queryParamsObj,
        }
      );
      localStorage.removeItem(SERVICE_ID);
      localStorage.removeItem(QUERY_PARAMS);
    }
  }
}
