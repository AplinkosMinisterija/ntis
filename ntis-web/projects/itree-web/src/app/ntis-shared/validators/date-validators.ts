import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class NtisDateValidators {
  public static dateGreaterOrEqualThan(dateFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const dateLess = dateFormControl.value as Date;
      return !control.value || !dateLess || dateLess <= control.value
        ? null
        : { dateGreaterOrEqual: { valid: control.value as Date } };
    };
  }
}
