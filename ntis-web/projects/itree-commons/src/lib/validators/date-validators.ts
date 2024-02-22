import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class SprDateValidators {
  public static validateDateFrom(dateToFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const dateTo = dateToFormControl.value as Date;
      return !control.value || !dateTo || dateTo > control.value
        ? null
        : { dateFrom: { valid: control.value as Date } };
    };
  }

  public static validateDateFromEqual(dateToFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const dateTo = dateToFormControl.value as Date;
      return !control.value || !dateTo || dateTo >= control.value
        ? null
        : { dateFrom: { valid: control.value as Date } };
    };
  }

  public static validateDateTo(dateFromFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const dateFrom = dateFromFormControl.value as Date;
      return !control.value || !dateFrom || dateFrom < control.value
        ? null
        : { dateTo: { valid: control.value as Date } };
    };
  }

  public static validateDateToEqual(dateFromFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const dateFrom = dateFromFormControl.value as Date;
      return !control.value || !dateFrom || dateFrom <= control.value
        ? null
        : { dateTo: { valid: control.value as Date } };
    };
  }
}
