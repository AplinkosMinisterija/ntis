import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const SprPasswordValidator = (
  minLength: number,
  minCapital: number,
  minDigits: number,
  minSpecial: number,
  specialSymbols: string
): ValidatorFn => {
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
    if (
      typeof minSpecial === 'number' &&
      minSpecial > 0 &&
      new Set([...stringValue].filter((char) => specialSymbols.includes(char))).size < minSpecial
    ) {
      errors['minSpecial'] = { minSpecial };
    }
    return !control.value || Object.keys(errors).length === 0 ? null : errors;
  };
};
