export const getUniqueName = (name: string, usedNames: string[]): string => {
  if (usedNames.some((usedName) => usedName === name)) {
    const suffixNumber = usedNames.reduce((result, usedName) => {
      if (usedName !== name && usedName?.startsWith(name)) {
        const usedNameEnd = usedName.slice(name.length);
        if (/^ \(\d+\)$/.test(usedNameEnd)) {
          const usedNameSuffixNumber = parseInt(/\d+/.exec(usedNameEnd)[0]);
          if (usedNameSuffixNumber >= result) {
            result = usedNameSuffixNumber + 1;
          }
        }
      }
      return result;
    }, 1);
    return getUniqueName(`${name} (${suffixNumber})`, usedNames);
  }
  return name;
};
