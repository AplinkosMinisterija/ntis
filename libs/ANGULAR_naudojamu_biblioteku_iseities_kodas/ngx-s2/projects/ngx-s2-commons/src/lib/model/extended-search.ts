import { ExtendedSearchCondition, ExtendedSearchUpperLower } from '../enums/extended-search';

export interface ExtendedSearchParamValue {
  condition: ExtendedSearchCondition;
  value?: string;
  values?: string[];
  upperLower: ExtendedSearchUpperLower;
}

export interface ExtendedSearchParam {
  paramName: string;
  paramValue: ExtendedSearchParamValue;
}
