export const getFileExtension = (fileName: string): string => {
  return fileName.includes('.') ? `.${fileName.split('.').pop()}` : undefined;
};
