import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthUtil, CommonFormServices } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { Subject, takeUntil } from 'rxjs';
import { SprPasswordValidator } from '@itree-commons/src/lib/validators/password-validator';
import { ValueMatchValidator } from '@itree-commons/src/lib/validators/value-match-validator';

@Component({
  selector: 'app-change-password-page',
  templateUrl: './change-password-page.component.html',
  styleUrls: ['./change-password-page.component.scss'],
})
export class ChangePasswordPageComponent implements OnInit, OnDestroy {
  readonly destroy$ = new Subject<void>();
  private passwordParams: {
    minLength: number;
    minCapital: number;
    minDigits: number;
  };

  form = new FormGroup({
    oldPassword: new FormControl(null, Validators.compose([Validators.required])),
    newPassword: new FormControl(null),
    repeatPassword: new FormControl(null),
  });
  passwordExpired = !!AuthUtil.getSessionInfo()?.usrPasswordChangeToken;
  passwordRequirementsText: string;

  constructor(
    protected commonFormServices: CommonFormServices,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
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

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      if (this.form.controls.newPassword.value === this.form.controls.repeatPassword.value) {
        this.authService
          .resetPassword(this.form.controls.oldPassword.value as string, this.form.controls.newPassword.value as string)
          .subscribe(() => {
            this.commonFormServices.translate.get('common.message.passwordChanged').subscribe((translation: string) => {
              this.commonFormServices.appMessages.showSuccess('', translation);
              void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
            });
          });
      } else {
        this.commonFormServices.translate.get('pages.register.error.differentPass').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showWarning('', translation);
        });
      }
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) =>
          this.commonFormServices.appMessages.showError('', translation, 'VALIDATION')
        );
    }
  }

  loadPasswordParams(): void {
    this.authService.getUserPasswordParams().subscribe((result) => {
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
          this.passwordRequirementsText = result;
        });
    }
  }

  updateValidators(): void {
    this.form.controls.newPassword.setValidators([Validators.maxLength(100), Validators.required]);
    this.form.controls.repeatPassword.setValidators([
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
