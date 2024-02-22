import { AbstractControl, FormArray, ValidationErrors } from '@angular/forms';

export class SprFormArrayValidators {
  /**
   * @description
   * Validator that requires at least one FormControl's value in a FormArray to be truthy.
   *
   * @usageNotes
   *
   * ### Validate that at least one FormControl value in a FormArray is truthy
   *
   * ```typescript
   * const group = new FormGroup([], SprFormArrayValidators.AtLeastOneTruthy);
   *
   * ```
   *
   * @returns An error map that contains the `atLeastOneTruthy` property
   * set to `true` if the validation check fails, otherwise `null`.
   */
  public static atLeastOneTruthy(control: AbstractControl): ValidationErrors {
    return (control as FormArray).controls.some((control) => control.value) ? null : { atLeastOneTruthy: true };
  }
}
