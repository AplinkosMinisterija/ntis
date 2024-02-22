import { ActionsEnum } from '../enums/table-row-actions.enums';

export type MenuActionIcons = {
  [key in string]: string;
};

export function getActionIcons(actionCode: ActionsEnum): string {
  const menuIcons: MenuActionIcons = {
    [ActionsEnum.ACTIONS_DELETE]: 'pi pi-fw pi-trash',
    [ActionsEnum.ACTIONS_UPDATE]: 'pi pi-fw  pi-pencil',
    [ActionsEnum.ACTIONS_READ]: 'pi pi-fw  pi-eye',
    [ActionsEnum.ACTIONS_CREATE]: 'pi pi-fw  pi-pencil',
    [ActionsEnum.ACTIONS_COPY]: 'pi pi-fw  pi-clone',
    [ActionsEnum.SET_FORM_DISABLED_ACTIONS]: 'pi pi-fw  pi-pencil',
    [ActionsEnum.SET_CODES_AND_TRANSLATIONS]: 'pi pi-fw  pi-bars',
    [ActionsEnum.ASSIGN_ROLE]: 'pi pi-fw  pi-user-plus',
    [ActionsEnum.ASSIGN_USERS]: 'pi pi-fw  pi-users',
    [ActionsEnum.ACTION_VIEW_INTS_INFO]: 'pi pi-fw pi-eye',
    [ActionsEnum.ACTION_RENEW_INTS_INFO]: 'pi pi-fw  pi-pencil',
    [ActionsEnum.ACTION_MANAGE_INTS_MANAGERS]: 'pi pi-fw pi-check-circle',
    [ActionsEnum.ACTION_REMOVE_FROM_ACCOUNT]: 'pi pi-fw pi-times-circle',
    [ActionsEnum.GET_LINK]: 'pi pi-fw pi-link',
  };

  return menuIcons[actionCode] || null;
}
