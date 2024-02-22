export const getElementParents = (element: HTMLElement, parents: HTMLElement[] = []): HTMLElement[] => {
  const parent = element?.parentNode as HTMLElement;
  return !parent ? parents : getElementParents(parent, [...parents, parent]);
};
