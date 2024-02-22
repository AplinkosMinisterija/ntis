import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const SprPasswordValidator = (minLength: number, minCapital: number, minDigits: number): ValidatorFn => {
  return (control: AbstractControl): ValidationErrors | null => {
    const stringValue = typeof control.value === 'string' ? control.value : '';
    const errors: ValidationErrors = {};
    if (typeof minLength === 'number' && minLength > 0 && stringValue.length < minLength) {
      errors['minLength'] = { minLength };
    }
    if (
      typeof minCapital === 'number' &&
      minCapital > 0 &&
      (stringValue.replace(/[^A-ZĄČĘĖĮŠŲŪŽ]/gm, '') || '').length < minCapital
    ) {
      errors['minCapital'] = { minCapital };
    }
    if (
      typeof minDigits === 'number' &&
      minDigits > 0 &&
      (stringValue.replace(/[^\d]/gm, '') || '').length < minDigits
    ) {
      errors['minDigits'] = { minDigits };
    }
    return !control.value || Object.keys(errors).length === 0 ? null : errors;
  };
};
