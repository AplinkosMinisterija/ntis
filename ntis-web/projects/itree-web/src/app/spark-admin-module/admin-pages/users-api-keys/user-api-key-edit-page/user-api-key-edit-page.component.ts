import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { ApiKey, ForeignKeyParams, SprListIdKeyValue } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { AdminService } from '../../../admin-services/admin.service';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { SPR_API_KEY_EDIT } from '@itree-commons/src/constants/forms.constants';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { tap } from 'rxjs';

@Component({
  selector: 'app-user-api-key-edit-page',
  templateUrl: './user-api-key-edit-page.component.html',
  styleUrls: ['./user-api-key-edit-page.component.css'],
})
export class UserApiKeyEditPageComponent extends BaseEditForm<ApiKey> {
  form = new FormGroup({
    isOrgType: new FormControl<string>(
      DB_BOOLEAN_TRUE,
      Validators.compose([Validators.maxLength(1), Validators.required])
    ),
    usrId: new FormControl<number>(null),
    orgId: new FormControl<number>(null),
    ouId: new FormControl<number>(null),
    apiDateFrom: new FormControl<Date>(null, Validators.compose([Validators.required])),
    apiDateTo: new FormControl<Date>(null),
  });
  readonly backUrl = `${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_API_KEYS_LIST}`;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  limitationsOnOrgId = true;
  readonly formTranslationsReference = 'pages.sprApiKeys';
  orgSearchResults: SprListIdKeyValue[] = [] as SprListIdKeyValue[];
  orgUsersSearchResults: SprListIdKeyValue[] = [] as SprListIdKeyValue[];
  usersSearchResults: SprListIdKeyValue[] = [] as SprListIdKeyValue[];
  editFormValues: EditFormValues = [
    {
      legend: '',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.apiTypeFor',
          translateLabel: true,
          formControlName: 'isOrgType',
          isRequired: false,
          hidden: true,
        },
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.org_name',
          translateLabel: true,
          formControlName: 'orgId',
          isRequired: true,
          hidden: false,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.org_usr_username',
          translateLabel: true,
          formControlName: 'ouId',
          isRequired: true,
          hidden: false,
          options: this.orgUsersSearchResults,
          autoComplete: true,
          optionValue: 'id',
          optionLabel: 'display',
          filter: true,
          startFilteringLength: 3,
          optionType: 'single',
        },

        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.usr_username',
          translateLabel: true,
          formControlName: 'usrId',
          isRequired: false,
          hidden: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.api_date_from',
          translateLabel: true,
          formControlName: 'apiDateFrom',
          isRequired: true,
        },

        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.api_date_to',
          translateLabel: true,
          formControlName: 'apiDateTo',
          isRequired: false,
        },
      ],
    },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private authService: AuthService,
    protected override activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices, activatedRoute);
    this.limitationsOnOrgId = !this.authService.isFormActionEnabled(
      SPR_API_KEY_EDIT,
      ActionsEnum.NO_LIMIT_ON_ORG_LEVEL
    );
    this.data = this.activatedRoute.snapshot.data[RoutingConst.DATA] as typeof this.data;
    if (!this.data?.api_id) {
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'usrId', false);
      this.form.controls.usrId.clearValidators();
      this.form.controls.usrId.updateValueAndValidity({ emitEvent: false });
      if (this.limitationsOnOrgId) {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isOrgType', false);
        this.changeInputType(DB_BOOLEAN_FALSE);
      } else {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isOrgType', true);
        this.form.controls.isOrgType.addValidators(Validators.compose([Validators.required]));
        this.form.controls.isOrgType.updateValueAndValidity({ emitEvent: true });
        this.changeInputType(DB_BOOLEAN_TRUE);
        this.form.controls.isOrgType.valueChanges.pipe(takeUntilDestroyed()).subscribe((res) => {
          this.changeInputType(res);
        });

        this.form.controls.orgId.valueChanges.pipe(takeUntilDestroyed()).subscribe((res) => {
          if (res) {
            this.orgUserSearch(res);
          }
        });
      }
    } else {
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'isOrgType', false);
      if (this.data.api_usr_id) {
        this.changeInputType(DB_BOOLEAN_FALSE);
      }
    }
    if (this.data?.api_ou_id || this.data.orgInfo?.length > 0) {
      this.orgSearchResults = this.data.orgInfo;
      this.form.controls.orgId.setValue(this.data.api_org_id);
      this.form.controls.orgId.disable();
      this.form.controls.orgId.updateValueAndValidity({ emitEvent: false });
      this.orgUserSearch(this.data.api_org_id, this.data.api_ou_id);
    }

    if (this.data?.api_usr_id) {
      this.usersSearchResults = this.data.userInfo;
      this.form.controls.usrId.setValue(this.data.api_usr_id);
      this.form.controls.usrId.disable();
      this.form.controls.usrId.updateValueAndValidity({ emitEvent: false });
    }

    this.form.controls.apiDateFrom.addValidators([SprDateValidators.validateDateFrom(this.form.controls.apiDateTo)]);
    this.form.controls.apiDateTo.addValidators([SprDateValidators.validateDateTo(this.form.controls.apiDateFrom)]);
    this.form.controls.apiDateFrom.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.apiDateTo.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.apiDateTo.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.apiDateFrom.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  changeInputType(type: string): void {
    if (type === DB_BOOLEAN_TRUE) {
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgId', true);
      this.form.controls.orgId.addValidators(Validators.compose([Validators.required]));
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'orgId', 'isRequired', true);
      this.form.controls.orgId.updateValueAndValidity({ emitEvent: true });

      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'ouId', true);
      this.form.controls.ouId.addValidators(Validators.compose([Validators.required]));
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'ouId', 'isRequired', true);
      this.form.controls.ouId.updateValueAndValidity({ emitEvent: true });

      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'usrId', false);
      this.form.controls.usrId.setValue(null);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usrId', 'isRequired', false);
      this.form.controls.usrId.clearValidators();
      this.form.controls.usrId.updateValueAndValidity({ emitEvent: false });
    } else {
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'orgId', false);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'orgId', 'isRequired', false);
      this.form.controls.orgId.setValue(null);
      this.form.controls.orgId.clearValidators();
      this.form.controls.orgId.updateValueAndValidity({ emitEvent: false });

      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'ouId', false);
      this.form.controls.ouId.setValue(null);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'ouId', 'isRequired', false);
      this.form.controls.ouId.clearValidators();
      this.form.controls.ouId.updateValueAndValidity({ emitEvent: false });

      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'usrId', true);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usrId', 'isRequired', true);
      this.form.controls.usrId.addValidators(Validators.compose([Validators.required]));
      this.form.controls.usrId.updateValueAndValidity({ emitEvent: true });
    }
  }

  protected setData(data: typeof this.data): void {
    if (data?.api_id) {
      this.data = data;
      this.form.controls.orgId.setValue(data.api_org_id);
      this.form.controls.apiDateFrom.setValue(data.api_date_from ? new Date(data.api_date_from) : null);
      this.form.controls.apiDateTo.setValue(data.api_date_to ? new Date(data.api_date_to) : null);
    }
  }

  protected getData(): typeof this.data {
    const result = this.data || ({} as ApiKey);
    result.api_usr_id = this.form.controls.usrId.value;
    result.api_org_id = this.form.controls.orgId.value;
    result.api_ou_id = this.form.controls.ouId.value;
    result.api_date_from = this.form.controls.apiDateFrom.value;
    result.api_date_to = this.form.controls.apiDateTo.value;
    this.data = result;
    return result;
  }

  protected doSave(value: typeof this.data): void {
    if (value.api_org_id !== null && value.api_ou_id !== null && value.api_id === null && value.api_usr_id === null) {
      this.adminService
        .getApiOrgUserUsrId(value)
        .pipe(
          tap((result) => {
            value.api_usr_id = result;
            this.adminService.setApiKeyRecord(value).subscribe((results) => {
              this.onSaveSuccess(results);
              this.onCancel();
            });
          })
        )
        .subscribe();
    } else {
      this.adminService.setApiKeyRecord(value).subscribe((results) => {
        this.onSaveSuccess(results);
        this.onCancel();
      });
    }
  }

  protected doLoad(): void {
    this.onLoadSuccess(this.activatedRoute.snapshot.data[RoutingConst.DATA] as typeof this.data);
  }

  onCancel(): void {
    this.backToBrowseForm(this.backUrl);
  }

  orgSearch(params: ForeignKeyParams): void {
    this.adminService.getOrganizationList(params).subscribe((response) => {
      this.orgSearchResults = response;
    });
  }

  userSearch(value: ForeignKeyParams): void {
    this.adminService.getApiUsersList(value).subscribe((response) => {
      this.usersSearchResults = response;
    });
  }

  orgUserSearch(orgId: number, ouId?: number): void {
    this.adminService.getOrganizationUsersList(orgId.toString()).subscribe((response) => {
      this.orgUsersSearchResults = response;
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'ouId', 'options', response);

      if (ouId) {
        this.form.controls.ouId.setValue(ouId);
        this.form.controls.ouId.disable();
        this.form.controls.ouId.updateValueAndValidity({ emitEvent: true });
      }
    });
  }
}
