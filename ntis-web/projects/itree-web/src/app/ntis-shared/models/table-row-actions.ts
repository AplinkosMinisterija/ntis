import { FaIconData } from '@itree-commons/src/lib/types/fa-icons';

export interface NtisTableRowActionsItem {
  icon: FaIconData;
  iconTheme: 'default' | 'risk' | 'success' | 'info';
  actionName?: string;
  action?: () => void;
  url?: string;
}
