import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { EMAIL_PATTERN } from '@itree-commons/src/constants/validation.constants';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password-page.component.html',
  styleUrls: ['./forgot-password-page.component.scss'],
})
export class ForgotPasswordPageComponent {
  token: string;
  form = new FormGroup({
    userName: new FormControl(null, Validators.required),
  });

  constructor(
    private commonFormServices: CommonFormServices,
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      this.authService.requestNewPassword(this.form.controls.userName.value as string).subscribe(() => {
        this.commonFormServices.translate.get('pages.forgotPassword.emailSent').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          void this.router.navigate(['/', RoutingConst.HOME]);
        });
      });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) =>
          this.commonFormServices.appMessages.showError('', translation, 'VALIDATION')
        );
    }
  }
}
