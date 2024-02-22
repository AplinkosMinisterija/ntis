import { Injectable } from '@angular/core';

export interface S2UiTranslations {
  action: {
    browse: string;
    close: string;
    dragFilesHere: string;
    selectAll: string;
  };
  general: {
    or: string;
  };
  errorMsg: {
    invalidFileFormat: string;
    maxFilesExceed: string;
    maxFileSizeExceed: string;
  };
}

@Injectable({
  providedIn: 'root',
})
export class S2UiTranslationsService {
  translations: S2UiTranslations = {
    action: {
      browse: 'Browse',
      close: 'Close',
      dragFilesHere: 'Drag files here',
      selectAll: 'Select all',
    },
    general: {
      or: 'Or',
    },
    errorMsg: {
      invalidFileFormat: 'Invalid file format! Allowed file types: {0}',
      maxFilesExceed: 'Total number of files exceeded ({0})',
      maxFileSizeExceed: 'Maximum file size exceeded ({0})',
    },
  };

  setTranslations(translations: S2UiTranslations): void {
    this.translations = { ...this.translations, ...translations };
  }
}
