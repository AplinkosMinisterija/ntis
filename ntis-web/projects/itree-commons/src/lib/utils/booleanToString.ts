export const booleanToString = (value: boolean, trueValue = 'true', falseValue = 'false'): string => {
  if (typeof value === 'boolean') {
    return value ? trueValue : falseValue;
  }
  return value;
};
