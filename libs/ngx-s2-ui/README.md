# ngx-s2-ui

## Changelog

### 16.0.3

#### file-upload.component

- Improved error handling for `autoUploadMethod`

### 16.0.2

#### NEW animation: expandFromTop

#### NEW component: file-upload.component

#### NEW pipe: date.pipe

#### NEW service: s2-ui-settings.service

#### s2-ui-translations.service

- Added `action.browse`, `action.dragFilesHere`, `general.or`, `errorMsg.invalidFileFormat`, `errorMsg.maxFilesExceed`, `errorMsg.maxFileSizeExceed` properties to `S2UiTranslations` interface

#### NEW utility functions: getFileExtension, getMimeTypeClass, getUniqueName, getUniqueFileName

### 16.0.1

#### package.json

- Added `@tailwindcss/container-queries` and `@tailwindcss/forms` to dev and peer dependencies

#### NEW component: checkbox-tree.component

#### NEW directive: template.directive

#### s2-ui-translations.service

- Added `action.selectAll` property to `S2UiTranslations` interface

#### NEW styles file: s2-ui.scss

- Added `s2-ui-inline-input-label` class for styling a wrapper for input element and a label

#### NEW styles file: \_inputs.scss (included in s2-ui.scss)

- Added `s2-ui-input-checkbox` and `s2-ui-input-radio` classes for styling inputs.

### 16.0.0

Upgraded Angular version to 16.

#### popup-messages.component

- Fix close icon not visible

### 15.0.0

Created the library
