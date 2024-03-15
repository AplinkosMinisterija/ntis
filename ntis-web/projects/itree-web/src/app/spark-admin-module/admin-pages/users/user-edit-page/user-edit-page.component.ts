import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommonFormServices, DeprecatedBaseEditForm, getLang } from '@itree/ngx-s2-commons';
import { ForeignKeyParams, SprPersonsDAO, SprUsersDAO, SprUsersNtisDAO } from '@itree-commons/src/lib/model/api/api';
import { AdminService } from '../../../admin-services/admin.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { Observable, takeUntil } from 'rxjs';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { UserType } from '@itree-web/src/app/public/enums/auth.enums';
import { SPR_USERS_EDIT } from '@itree-commons/src/constants/forms.constants';
import { PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';

const NO_LIMIT_ON_ORG_LEVEL = 'NO_LIMIT_ON_ORG_LEVEL';

@Component({
  selector: 'app-user-edit-page',
  templateUrl: './user-edit-page.component.html',
  styleUrls: ['./user-edit-page.component.scss'],
})
export class UserEditPageComponent extends DeprecatedBaseEditForm<SprUsersNtisDAO> implements OnDestroy {
  readonly formTranslationsReference = 'pages.sprUserEdit';
  originalEmail: string;
  emailChanged: boolean = false;
  emailWasConfirmed: boolean = false;
  noLimitOnOrLevelAction: boolean;

  personList: SprPersonsDAO[] = [];
  isEmailConfirmed: boolean = false;
  editFormValues: EditFormValues;
  isReadOnly: boolean = false;

  isNew: boolean = false;

  constructor(
    public faIconsService: FaIconsService,
    protected override formBuilder: FormBuilder,
    private authService: AuthService,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    protected override activatedRoute: ActivatedRoute,
    private appDataServ: AppDataService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.isNew = params['id'] === 'new' ? true : false;
      this.isReadOnly = params['action'] === ActionsEnum.ACTIONS_READ ? true : false;
    });
    if (this.isNew === false) {
      this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((params) => {
        this.isNew = params['actionType'] === ActionsEnum.ACTIONS_COPY ? true : false;
      });
    }
    this.noLimitOnOrLevelAction = this.authService.isFormActionEnabled(SPR_USERS_EDIT, NO_LIMIT_ON_ORG_LEVEL);
    this.editFormValues = [
      {
        legend: 'pages.sprUserRolesEdit.generalData',
        translateLegend: true,
        items: [
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.id',
            translateLabel: true,
            formControlName: 'usr_id',
            hidden: true,
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.personName',
            translateLabel: true,
            formControlName: 'usr_person_name',
            isRequired: true,
            maxLength: 100,
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.personSurname',
            translateLabel: true,
            formControlName: 'usr_person_surname',
            isRequired: true,
            maxLength: 100,
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.username',
            translateLabel: true,
            formControlName: 'usr_username',
            isRequired: true,
            hidden: false,
            maxLength: 100,
            errorDefs: {
              SPR_USR_UK: this.formTranslationsReference + '.error',
            },
            onFocusOut: (): void => {
              this.checkConstraints(
                this.adminService.checkUserRecordConstraints.bind(this.adminService, this.getData()),
                ['usr_username']
              );
            },
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.email',
            translateLabel: true,
            formControlName: 'usr_email',
            isRequired: true,
          },
          {
            type: EditFormItemType.Custom,
            label: '',
            formControlName: 'confirmEmail',
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.per_code',
            translateLabel: true,
            formControlName: 'per_code',
            maxLength: 11,
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.phone',
            translateLabel: true,
            formControlName: 'phone',
            isRequired: false,
            maxLength: 12,
            errorDefs: { pattern: 'common.error.phone' },
          },
          {
            type: EditFormItemType.Select,
            classifierCode: 'SPR_LANGUAGE',
            label: this.formTranslationsReference + '.language',
            translateLabel: true,
            formControlName: 'usr_language',
            optionType: 'single',
            autoComplete: false,
          },
        ],
      },
      {
        legend: 'pages.sprUserRolesEdit.generalOptions',
        translateLegend: true,
        items: [
          {
            type: EditFormItemType.Select,
            label: this.formTranslationsReference + '.usr_type',
            translateLabel: true,
            classifierCode: 'SPR_USER_TYPE',
            formControlName: 'usr_type',
            optionType: 'single',
            isRequired: true,
            autoComplete: false,
            readonly: true,
            showClear: false,
          },
          {
            type: EditFormItemType.Date,
            label: this.formTranslationsReference + '.usr_date_from',
            translateLabel: true,
            formControlName: 'usr_date_from',
            isRequired: true,
          },
          {
            type: EditFormItemType.Date,
            label: this.formTranslationsReference + '.usr_date_to',
            translateLabel: true,
            formControlName: 'usr_date_to',
            minDate: new Date(0),
          },
          {
            type: EditFormItemType.Select,
            classifierCode: 'CUST_ORGANIZATIONS',
            customClassifier: true,
            optionLabel: 'display',
            optionValue: 'id',
            label: this.formTranslationsReference + '.usr_org_id',
            translateLabel: true,
            formControlName: 'usr_org_id',
            optionType: 'single',
            autoComplete: false,
            filter: true,
            startFilteringLength: 3,
            hidden: this.isNew,
          },
          {
            type: EditFormItemType.Custom,
            label: this.formTranslationsReference + '.usr_disable',
            translateLabel: true,
            formControlName: 'usr_disable',
            hidden: !this.noLimitOnOrLevelAction,
          },
        ],
      },
    ];
  }

  protected buildForm(): void {
    this.form = this.formBuilder.group({
      usr_id: new FormControl({ value: null, disabled: true }),
      usr_person_name: new FormControl('', [Validators.maxLength(50), Validators.required]),
      usr_per_id: new FormControl(''),
      usr_person_surname: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
      usr_username: new FormControl('', [Validators.maxLength(50), Validators.required]),
      usr_email: new FormControl('', Validators.compose([Validators.email, Validators.required])),
      usr_date_from: new FormControl(new Date(), Validators.required),
      usr_date_to: new FormControl(''),
      usr_type: new FormControl({ value: UserType.ORGANIZATION_USER, disabled: true }),
      usr_org_id: new FormControl(''),
      usr_disable: new FormControl(''),
      usr_language: new FormControl('lt'),
      confirmEmail: new FormControl(''),
      usr_per_list: new FormControl(''),
      per_code: new FormControl(null),
      phone: new FormControl(
        null,
        Validators.compose([Validators.maxLength(12), Validators.pattern(new RegExp(PHONE_PATTERN))])
      ),
    });
    if (!this.appDataServ.multilanguageExists()) {
      this.form.controls.usr_language.setValue(getLang());
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_language', 'hidden', true);
    }
    this.form.controls.usr_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_date_to', 'minDate', newValue);
    });
    if (!this.authService.isFormActionEnabled('SPR_USERS_EDIT', NO_LIMIT_ON_ORG_LEVEL)) {
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_org_id', 'hidden', true);
    }
    this.form.controls.usr_email.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((changedEmail: string) => {
      if (this.originalEmail) {
        this.emailChanged = changedEmail !== this.originalEmail;
        if (this.emailWasConfirmed && changedEmail === this.originalEmail) {
          this.isEmailConfirmed = true;
        } else {
          this.isEmailConfirmed = false;
        }
      }
    });

    this.form.controls.usr_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((userType: string) => {
      if (userType === 'PRIVATE') {
        EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'usr_org_id', false);
      }
    });
  }

  override ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  protected doLoad(id?: string, actionType?: string): Observable<SprUsersNtisDAO> {
    this.isNew = false;
    return this.adminService.getNtisUserRecord(id, actionType);
  }

  protected setData(data: SprUsersNtisDAO): void {
    this.data = data;
    EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_org_id', 'classifierFilterParams', {
      usr_id: data.usr_id,
    });
    this.isEmailConfirmed = this.data.usr_email_confirmed === 'Y' ? true : false;
    EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_date_to', 'minDate', this.data.usr_date_from);
    this.form.controls.usr_id.setValue(data.usr_id);
    this.form.controls.usr_username.setValue(data.usr_username);
    this.form.controls.usr_per_id.setValue(data.usr_per_id);
    this.form.controls.usr_email.setValue(data.usr_email);
    this.form.controls.usr_person_name.setValue(data.usr_person_name);
    this.form.controls.usr_person_surname.setValue(data.usr_person_surname);
    this.form.controls.usr_date_from.setValue(data ? new Date(data.usr_date_from) : data.usr_date_from);
    this.form.controls.usr_date_to.setValue(data.usr_date_to !== null ? new Date(data.usr_date_to) : null);
    this.form.controls.usr_type.setValue(data.usr_type);
    this.form.controls.usr_org_id.setValue(data.usr_org_id);
    this.form.controls.usr_disable.setValue(this.data.usr_lock_date != null);
    this.form.controls.usr_language.setValue(data.usr_language || getLang());
    this.form.controls.per_code.setValue(data.personCode);
    if (data.personCodeConfirmed === 'Y') {
      this.form.controls.per_code.disable();
    }
    this.form.controls.phone.setValue(data.usr_phone_number);
    this.originalEmail = data.usr_email;
    this.emailWasConfirmed = this.data.usr_email_confirmed === DB_BOOLEAN_TRUE ? true : false;
    if (this.isReadOnly) {
      this.form.disable();
    }
    if (data.isViispUser === DB_BOOLEAN_TRUE) {
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'usr_username', 'hidden', true);
    }
  }

  protected getData(): SprUsersNtisDAO {
    const result: SprUsersNtisDAO = this.data != null ? this.data : ({} as SprUsersNtisDAO);
    result.usr_id = this.form.controls.usr_id.value as SprUsersDAO['usr_id'];
    result.usr_per_id = this.form.controls.usr_per_id.value as SprUsersDAO['usr_per_id'];
    result.usr_username = this.form.controls.usr_username.value as SprUsersDAO['usr_username'];
    result.usr_email = this.form.controls.usr_email.value as SprUsersDAO['usr_email'];
    result.usr_person_name = this.form.controls.usr_person_name.value as SprUsersDAO['usr_person_name'];
    result.usr_person_surname = this.form.controls.usr_person_surname.value as SprUsersDAO['usr_person_surname'];
    result.usr_date_from = this.form.controls.usr_date_from.value as SprUsersDAO['usr_date_from'];
    result.usr_date_to = this.form.controls.usr_date_to.value as SprUsersDAO['usr_date_to'];
    result.usr_type = this.form.controls.usr_type.value as SprUsersDAO['usr_type'];
    result.usr_language = this.form.controls.usr_language.value as SprUsersDAO['usr_language'];
    result.usr_org_id =
      this.form.controls.value !== null ? (this.form.controls.usr_org_id.value as SprUsersDAO['usr_org_id']) : null;
    result.usr_lock_date = this.form.controls.usr_disable.value ? new Date() : null;
    result.personCode = this.form.controls.per_code.value as SprUsersNtisDAO['personCode'];
    result.usr_phone_number = this.form.controls.phone.value as SprUsersNtisDAO['usr_phone_number'];
    this.data = result;
    return result;
  }

  protected doSave(value: SprUsersNtisDAO): void {
    if (!value.usr_language) {
      value.usr_language = getLang();
    }
    this.adminService.setUserRecord(value).subscribe((user) => {
      if (!value.usr_id) {
        this.adminService.informNewUser(user.usr_id.toString()).subscribe(() => {
          this.commonFormServices.translate.get('pages.sprUserEdit.emailSent').subscribe((translation: string) => {
            this.commonFormServices.appMessages.showSuccess('', translation);
          });
        });
      } else if (value.usr_id && this.emailChanged && !this.isEmailConfirmed) {
        this.onConfirmEmail();
      }
      this.onSaveSuccess(user);
    });
  }

  public override onSaveSuccess(data: SprUsersNtisDAO): void {
    super.onSaveSuccess(data);
    this.adminService.orgUserRolesAssignedSubject.next();
    this.onCancel();
  }

  findPersons(value: ForeignKeyParams): void {
    this.adminService.findPersons(value).subscribe((res) => {
      this.personList = res.data;
    });
  }

  onCancel(): void {
    this.backToBrowseForm(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_USERS_BROWSE}`);
  }

  onConfirmEmail(): void {
    if (this.form.controls.usr_email.valid) {
      const usr_email = this.form.controls.usr_email.value as string;
      const usr_id = this.form.controls.usr_id.value as string;
      this.authService.requestEmailConfirmation(usr_id, usr_email).subscribe(() => {
        this.commonFormServices.translate.get('pages.sprUserEdit.emailSent').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
      });
    } else {
      this.commonFormServices.translate.get('pages.sprUserEdit.emailInvalid').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    }
  }
}
