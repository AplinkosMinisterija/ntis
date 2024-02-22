import { ActionMenuItem } from './table-action-dropdown';

export interface ContextMenu {
  mouseEvent: MouseEvent;
  contextTarget: HTMLElement;
  item: ActionMenuItem[];
}
