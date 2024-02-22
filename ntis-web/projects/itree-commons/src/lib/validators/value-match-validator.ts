import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const ValueMatchValidator = (otherControl: AbstractControl): ValidatorFn => {
  return (control: AbstractControl): ValidationErrors | null => {
    return control.value === otherControl.value ? null : { valueMatch: undefined };
  };
};
