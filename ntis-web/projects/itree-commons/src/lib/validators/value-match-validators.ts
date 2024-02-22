import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class SprValueMatchValidators {
  public static validateNumberLessThanEqual(numberToFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const numberTo = numberToFormControl.value as number;
      return !control.value || !numberTo || numberTo >= control.value ? null : { numberLessThanEqual: true };
    };
  }

  public static validateNumberMoreThanEqual(numberToFormControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const numberFrom = numberToFormControl.value as number;
      return !control.value || !numberFrom || numberFrom <= control.value ? null : { numberLessThanEqual: true };
    };
  }
}
