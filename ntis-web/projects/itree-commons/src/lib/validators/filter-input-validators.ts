import { AbstractControl, FormControl, ValidationErrors } from '@angular/forms';
import { ExtendedSearchCondition } from '@itree/ngx-s2-commons';
import { AdvancedSearchParameterStatement } from '../model/api/api';

export class SprFilterInputValidators {
  public static required(control: AbstractControl): ValidationErrors {
    const paramName = ((control as FormControl).value as AdvancedSearchParameterStatement)?.paramName;
    const paramValue = ((control as FormControl).value as AdvancedSearchParameterStatement)?.paramValue;
    return paramName &&
      paramValue &&
      (paramValue.value ||
        (paramValue.values?.length && paramValue.values.every((value) => value)) ||
        paramValue.condition === ExtendedSearchCondition.Empty ||
        paramValue.condition === ExtendedSearchCondition.NotEmpty)
      ? null
      : { required: true };
  }
}
