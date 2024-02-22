import { SelectOptionType } from '@itree-commons/src/lib/types/select';

export interface SelectOption {
  option: string;
  value: string | number;
  id?: number;
}

export interface QuickSearchAddrList {
  autoComplete?: boolean;
  labelTemplate: string;
  list: Array<unknown>;
  optionType?: SelectOptionType;
  optionValue?: string;
  showFilter?: boolean;
  startFilteringLength?: number;
}
