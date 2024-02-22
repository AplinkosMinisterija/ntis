import { ActionsEnum } from '../enums/table-row-actions.enums';
import { FaIconData } from '../types/fa-icons';

export function getActionIcons(actionCode: ActionsEnum): FaIconData {
  const menuIcons: Record<string, FaIconData> = {
    [ActionsEnum.ACTIONS_DELETE]: { iconName: 'faTrash', iconStyle: 'fas' },
    [ActionsEnum.ACTIONS_UPDATE]: { iconName: 'faPencil', iconStyle: 'fas' },
    [ActionsEnum.ACTIONS_READ]: { iconName: 'faEye', iconStyle: 'fas' },
    [ActionsEnum.ACTIONS_CREATE]: { iconName: 'faPencil', iconStyle: 'fas' },
    [ActionsEnum.ACTIONS_COPY]: { iconName: 'faCopy', iconStyle: 'fas' },
    [ActionsEnum.SET_FORM_DISABLED_ACTIONS]: { iconName: 'faPencil', iconStyle: 'fas' },
    [ActionsEnum.SET_CODES_AND_TRANSLATIONS]: { iconName: 'faBars', iconStyle: 'fas' },
  };

  return menuIcons[actionCode] || null;
}
