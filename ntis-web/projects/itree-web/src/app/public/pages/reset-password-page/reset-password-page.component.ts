import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';
import { ResetPasswordFormMode } from '../../enums/password-reset-page.enums';
import { SprPasswordValidator } from '@itree-commons/src/lib/validators/password-validator';
import { ValueMatchValidator } from '@itree-commons/src/lib/validators/value-match-validator';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

export enum ResetPasswordHeaders {
  NEW_USER_RESET_PASSWROD_HEADER = 'pages.resetPassword.newUserHeaderText',
  EXISTING_USER_RESET_PASSWROD_HEADER = 'pages.resetPassword.headerText',
}
@Component({
  selector: 'app-reset-password-page',
  templateUrl: './reset-password-page.component.html',
  styleUrls: ['./reset-password-page.component.scss'],
})
export class ResetPasswordPageComponent implements OnInit, OnDestroy {
  readonly formTranslationsReference = 'pages.resetPassword';
  readonly destroy$ = new Subject<void>();
  form = new FormGroup({
    newPassword: new FormControl(''),
    repeatedPassword: new FormControl(''),
  });
  mode: ResetPasswordFormMode;
  formHeader: string;
  private passwordParams: {
    minLength: number;
    minCapital: number;
    minDigits: number;
  };

  editFormValues: EditFormValues = [
    {
      items: [
        {
          type: EditFormItemType.Password,
          label: this.formTranslationsReference + '.newPassword',
          translateLabel: true,
          formControlName: 'newPassword',
          isRequired: true,
          maxLength: 100,
          errorDefs: {
            minCapital: 'common.error.passwordValidator',
            minDigits: 'common.error.passwordValidator',
            minLength: 'common.error.passwordValidator',
          },
        },
        {
          type: EditFormItemType.Password,
          label: this.formTranslationsReference + '.repeatedPassword',
          translateLabel: true,
          formControlName: 'repeatedPassword',
          isRequired: true,
          maxLength: 100,
          errorDefs: {
            minCapital: 'common.error.passwordValidator',
            minDigits: 'common.error.passwordValidator',
            minLength: 'common.error.passwordValidator',
          },
        },
      ],
    },
  ];

  token: string;
  constructor(
    private authService: AuthService,
    private router: Router,
    protected commonFormServices: CommonFormServices,
    protected activatedRoute?: ActivatedRoute
  ) {
    this.form.controls.newPassword.valueChanges.pipe(takeUntilDestroyed()).subscribe(() => {
      this.form.controls.repeatedPassword.updateValueAndValidity({ emitEvent: false });
    });
  }

  ngOnInit(): void {
    this.token = this.activatedRoute.snapshot.params.token as string;
    this.mode = this.activatedRoute.snapshot.data.mode as ResetPasswordFormMode;
    this.activatedRoute.data.subscribe((formparams) => {
      this.mode = formparams.mode as ResetPasswordFormMode;
      this.formHeader =
        this.mode === ResetPasswordFormMode.NEW_USER_MODE
          ? ResetPasswordHeaders.NEW_USER_RESET_PASSWROD_HEADER
          : ResetPasswordHeaders.EXISTING_USER_RESET_PASSWROD_HEADER;
    });
    if (!this.token) {
      void this.router.navigate([RoutingConst.LOGIN]);
    }
    this.updateValidators();
    this.loadPasswordParams();
    this.commonFormServices.translate.onLangChange.pipe(takeUntil(this.destroy$)).subscribe(() => {
      if (this.passwordParams) {
        this.updatePasswordRequirementsText();
      } else {
        this.loadPasswordParams();
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  doSave(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      if (this.mode === ResetPasswordFormMode.RESET_PASSWORD_MODE) {
        this.authService.resetPasswordByToken(this.form.controls.newPassword.value, this.token).subscribe(() => {
          void this.router.navigate([RoutingConst.LOGIN]);
        });
      } else {
        this.authService.setPasswordForNewUser(this.form.controls.newPassword.value, this.token).subscribe(() => {
          void this.router.navigate([RoutingConst.LOGIN]);
        });
      }
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((text: string) => this.commonFormServices.appMessages.showError('', text, 'VALIDATION'));
    }
  }

  loadPasswordParams(): void {
    this.authService.getUserPasswordParams(this.token, this.mode).subscribe((result) => {
      this.passwordParams = {
        minLength: result['psw_min_length'] ? parseInt(result['psw_min_length'], 10) : 0,
        minCapital: result['psw_min_length'] ? parseInt(result['psw_min_capital'], 10) : 0,
        minDigits: result['psw_min_digits'] ? parseInt(result['psw_min_digits'], 10) : 0,
      };
      this.updatePasswordRequirementsText();
      this.updateValidators();
    });
  }

  updatePasswordRequirementsText(): void {
    if (this.passwordParams) {
      this.authService
        .getPasswordRequirementsText(
          this.passwordParams.minLength,
          this.passwordParams.minDigits,
          this.passwordParams.minCapital
        )
        .subscribe((result) => {
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'newPassword', 'tooltipText', result);
        });
    }
  }

  updateValidators(): void {
    this.form.controls.newPassword.setValidators([Validators.maxLength(100), Validators.required]);
    this.form.controls.repeatedPassword.setValidators([
      Validators.maxLength(100),
      Validators.required,
      ValueMatchValidator(this.form.controls.newPassword),
    ]);
    if (this.passwordParams) {
      this.form.controls.newPassword.addValidators(
        SprPasswordValidator(
          this.passwordParams.minLength,
          this.passwordParams.minCapital,
          this.passwordParams.minDigits
        )
      );
    }
  }
}
