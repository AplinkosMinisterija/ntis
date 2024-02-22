import { SelectTypesEnum } from '@itree-commons/src/lib/components/select/select.component';

export interface MapFilterField {
  name: string;
  translateName?: boolean;
  field: string;
  classifierCode?: string;
  options?: unknown[];
  optionLabel?: string;
  optionValue?: string;
  optionType?: SelectTypesEnum.single | SelectTypesEnum.multiple;
  formActionName?: string;
  filterInputType?: 'text' | 'date';
}
