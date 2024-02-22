import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class NtisInputValidators {
  public static readonly NO_WHITESPACE_PATTERN = '^\\S+$';
  public static readonly COORDINATES_PATTERN = '^(\\d{6}(.\\d{1,6})?),\\s(\\d{7}(.\\d{1,6})?)$';

  public static validateNoWhiteSpace(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const errors: ValidationErrors = {};
      if (!new RegExp(NtisInputValidators.NO_WHITESPACE_PATTERN).test(control.value as string)) {
        errors['noWhitespace'] = true;
      }
      return !control.value || Object.keys(errors).length === 0 ? null : errors;
    };
  }

  public static validateCoordinates(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const errors: ValidationErrors = {};
      if (!new RegExp(NtisInputValidators.COORDINATES_PATTERN).test(control.value as string)) {
        errors['invalidCoordinates'] = true;
      }
      return !control.value || Object.keys(errors).length === 0 ? null : errors;
    };
  }
}
