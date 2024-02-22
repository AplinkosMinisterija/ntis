export const getMimeTypeClass = (mimeType: string): string => {
  return mimeType.substring(0, mimeType.indexOf('/'));
};
