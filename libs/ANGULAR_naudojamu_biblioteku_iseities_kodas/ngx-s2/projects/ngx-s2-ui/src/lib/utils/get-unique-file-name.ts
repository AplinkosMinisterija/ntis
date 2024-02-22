import { getFileExtension } from './get-file-extension';
import { getUniqueName } from './get-unique-name';

export const getUniqueFileName = (name: string, usedNames: string[]): string => {
  const nameExtension = getFileExtension(name);
  const nameWithoutExtension = name.slice(0, nameExtension && -nameExtension.length);
  const filteredUsedNamesWithoutExtension = usedNames
    .filter((usedName) =>
      nameExtension ? usedName.toLowerCase().endsWith(nameExtension.toLowerCase()) : !getFileExtension(usedName)
    )
    .map((usedName) => usedName.slice(0, nameExtension && -nameExtension.length));
  return `${getUniqueName(nameWithoutExtension, filteredUsedNamesWithoutExtension)}${nameExtension || ''}`;
};
