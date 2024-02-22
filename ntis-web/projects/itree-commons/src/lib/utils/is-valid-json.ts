export const isValidJSON = (value: string): boolean => {
  try {
    JSON.parse(value);
  } catch (e) {
    return false;
  }
  return true;
};
