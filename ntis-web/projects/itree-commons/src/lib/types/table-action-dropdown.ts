import { IconDefinition } from '@fortawesome/fontawesome-svg-core';

export interface ActionMenuItem {
  label: string;
  id: string;
  icon: IconDefinition;
  action: string;
}
