import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class SprNumberValidators {
  public static NUMBER_PATTERN = '^-?\\d*\\.?\\d+$';
  public static NUMBER_POSITIVE_PATTERN = '^\\d*\\.?\\d+$';
  public static INTEGER_PATTERN = '^-?\\d+$';
  public static INTEGER_POSITIVE_PATTERN = '^\\d+$';

  public static validateNumber(integer = false, positiveOnly = false): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const errors: ValidationErrors = {};
      if (!new RegExp(SprNumberValidators.NUMBER_PATTERN).test(control.value as string)) {
        errors['number'] = true;
      } else {
        if (!integer && positiveOnly) {
          if (!new RegExp(SprNumberValidators.NUMBER_POSITIVE_PATTERN).test(control.value as string)) {
            errors['positiveNumber'] = true;
          }
        } else if (integer) {
          if (positiveOnly && !new RegExp(SprNumberValidators.INTEGER_POSITIVE_PATTERN).test(control.value as string)) {
            errors['positiveInteger'] = true;
          } else if (!positiveOnly && !new RegExp(SprNumberValidators.INTEGER_PATTERN).test(control.value as string)) {
            errors['integer'] = true;
          }
        }
      }
      return !control.value || Object.keys(errors).length === 0 ? null : errors;
    };
  }
}
