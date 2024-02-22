export type CheckboxTreeItemChecked = boolean | 'semi';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export interface CheckboxTreeItem<T = any> {
  checked?: CheckboxTreeItemChecked;
  children?: CheckboxTreeItem<T>[];
  className?: string;
  disabled?: boolean;
  extras?: T;
  hidden?: boolean;
  key?: string;
  label: string;
  open?: boolean;
  parent?: CheckboxTreeItem<T>[];
  translateLabel?: boolean;
}
