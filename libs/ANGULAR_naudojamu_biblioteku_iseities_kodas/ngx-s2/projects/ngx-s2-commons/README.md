# ngx-s2-commons

## Changelog

### 17.2.0

#### models

- **BREAKING CHANGE:** Removed `Spr_id_values_ot`, `Spr_key_values_ot`, `Spr_paging_ot` classes..

- Created a `SprPaging` class.

#### utils.ts

- **BREAKING CHANGE:** Removed `toKeyValues`, `fromKeyValues`, `convertKeyValues`, `keyValuesToMap`, `toRadioBtnIdVal`, `fromRadioBtnIdVal`, `toRadioBtnKeyVal`, `fromRadioBtnKeyVal` functions.

- **BREAKING CHANGE:** Edited `appendPagingParams` function to use `SprPaging` class.

#### auth.util.ts

- **POTENTIAL BREAKING CHANGE:** Edited `setTermsAccepted` and `isTermsAccepted` to not use `Spr_key_values_ot` model.

#### base.browse.form

- **BREAKING CHANGE:** Edited `getPagingParams` function to use `SprPaging` class.

- **BREAKING CHANGE:** Removed `instanceOfIdValuesOt`, `instanceOfKeyValuesOt` functions.

- **BREAKING CHANGE:** Removed usage of `instanceOfIdValuesOt`, `instanceOfKeyValuesOt` functions in `formatSearchParamValue` function.

#### common-api.ts

- **BREAKING CHANGE:** Removed `ChangePasswordRequest`, `CheckUserNameExistRequest`, `CreateNewUserRequest`, `CreatePasswordRequest`, `Key_values_ot`, `LoginRequest`, `NewPasswordRequest`, `RenewPasswordRequest`, `ServerErrorForClientSide`, `Spr_paging_ot` interfaces.

#### public-api.ts

- **BREAKING CHANGE:** Removed `Spr_id_values_ot`, `Spr_key_values_ot`, `Spr_paging_ot` from exports.

- Added `SprPaging` to exports.

### 17.1.0

#### base.edit.form

- Moved route param changes handling to a new function `handleIdChanges()`.

- **POTENTIAL BREAKING CHANGE:** Corrected data loading logic - data is loaded when `id` is provided and `forceDoLoad()` returns `true`.

#### base.browse.form

- **POTENTIAL BREAKING CHANGE:** Changed search params type from `Map<string, unknown>` to `Map<string, string>` to match backend model `SelectRequestParams` `params` property.

- Converted `private` properties to `protected`.

### 17.0.0

Upgraded Angular version to 17.

### 16.1.3

#### extended-search.ts

- Added new condition `Period` to ExtendedSearchCondition enum

### 16.1.2

#### extended-search.ts

- Two constants from enum ExtendedSearchUpperLower added.  
  CaseInsensitiveLatin, RegularLatin constants add ability to filter ignoring non-Latin characters.

### 16.1.1

#### s2.interceptor.ts

- Added catch for errors 503, 504

#### server-unavailable-error.ts

- Error class ServerUnavailableError added

- Error message added "common.error.serverUnavailableError": lt.json - "Atsiprašome, serveris laikinai nepasiekiamas. {{currentDateString}}", en.json - "Sorry, the server is temporarily unavailable. {{currentDateString}}",

#### app.error.handler.ts

- Added ServerUnavailableError error handling

### 16.1.0

#### package.json

- Upgraded required primeng version from `^16.0.0-rc.2` to `^16.0.0`

#### ContentTypeInterceptor

- **BREAKING CHANGE:** `Content-Type` header is set only in POST requests if `Content-Type` header is not provided and body is empty.

### 16.0.1

- Paging reset added after new search

- Deactivate guard added

### 16.0.0

Upgraded Angular version to 16.

#### base.browse.form

- Added static versions of `getSortOrder` and `getPagingParams` functions.

#### base.edit.form

- **BREAKING CHANGE:** `form` property marked as `abstract`.

- **BREAKING CHANGE:** exported constant `EDIT_FORM_DATA_KEY` moved to class as static property.

- **BREAKING CHANGE:** removed `FormBuilder` from constructor.

- **BREAKING CHANGE:** removed `buildForm` abstract method.

**_NOTE: Old version of `BaseEditForm` class has been cloned and available as `DeprecatedBaseEditForm`_**

#### app.messages

- **BREAKING CHANGE:** `showMessage` method set as `private`, PrimeNG `Message` interface was replaced with locally created `AppMessage` interface.

- **BREAKING CHANGE:** PrimeNG `MessageService` is no longer used. A desired UI sulution for messages can be chosen in a web application by subscribing to newly added `add$` and `clear$` Subjects.

#### ngx-s2-commons.module

- Removed `OverlayPanelModule` from imports.

#### loader.service

- **BREAKING CHANGE:** `showLoader` property type `Subject` changed to `BehaviorSubject`

- **BREAKING CHANGE:** removed `public` modifier from `showCount` property.

- `dimmed` and `message` properties marked as optional in `LoaderParams` interface.

### message.resolver

- removed unecessary code.

### s2.interceptor

- Calling `loaderService.hide();` only if request header `x-s2-noloader` doesn't exist.

- Injecting `LoaderService` in constructor instead of `Injector.get` method.

### 15.0.1

#### base.browse.form

- Added `rowsPerPageOptions` property with default value to use with PrimeNG Table component. See https://github.com/iTree-Lietuva/spark-web/pull/120

### 15.0.0

Upgraded Angular version to 15.

### 14.4.1

- Changed base.browse.form search parameter handling. See https://jira.itreegroup.eu/browse/SAP-290.

### 14.4.0

#### common.api

- Added `S2ViolatedConstraint` interface.

- Added `S2Message` interface.

### public-api

- **POTENTIAL BREAKING CHANGE:** Removed `SparkUserMessage` export.

- Added `S2Message` and `S2ViolatedConstraint` exports.

#### s2error

- **POTENTIAL BREAKING CHANGE:** Corrected `messages` property type from `SparkUserMessage[]` to `S2Message[]`.

#### base.edit.form

- **POTENTIAL BREAKING CHANGE:** Replaced `public destroy$: Subject<boolean>` property with `public readonly destroy$: Subject<void>`.

- Changed return types of `doSave` and `doLoad` functions from `void` to `void | Observable<T>`.

- Added `checkConstraints(apiFunction: () => Observable<S2ViolatedConstraint[]>, fields?: string[], fieldControls?: Record<string, AbstractControl | string>): void` and `handleConstraintsResult(result: S2ViolatedConstraint[], fields?: string[], fieldControls?: Record<string, AbstractControl | string>): void` functions for handling violated constraints.

- Added `protected executeDoSave(data: T): void` and `protected executeDoLoad(id?: string, actionType?: string): void` functions which execute `doSave` and `doLoad` functions and handle the result if available.

### 14.3.0 (s2-rest-api 1.3.0 required)

#### base.edit.form

- **POTENTIAL BREAKING CHANGE:** Set `isRestoreUnsavedDataEnabled` getter default value to `false`.

#### common.api

- **BREAKING CHANGE:** Renamed `role` to `roleCode` in WebSessionInfo interface (s2-rest-api 1.3.0).

- Added `orgId` and `orgName` properties to WebSessionInfo interface (s2-rest-api 1.3.0).

#### auth.util

- **BREAKING CHANGE:** Renamed `getRole` function to `getRoleCode`.

- Added `getOrgId`, `getOrgName`, `getRoleName` methods.

### 14.2.0

#### base.edit.form

- **BREAKING CHANGE:** Renamed `commonService` variable to `commonFormServices`.

- **BREAKING CHANGE:** Replaced `enableRestoreUnsavedData` variable with `isRestoreUnsavedDataEnabled` getter.

- **BREAKING CHANGE:** `isWarningEnabled` and `isEventEmitEnabled` turned into getters.

- Replaced private `isSaveDataToLSAllowed` variable with public `formDataLoaded`.

- Added `actionType` to saved form data for restoring, so restoring is possible only if saved actionType matches.

- When unsaved data is found in localStorage, function `doLoad` is executed only if user rejects restoring.

### 14.1.2

#### base.edit.form

- Fixed bug related to unsaved data restoring.

### 14.1.1

#### content.type.interceptor

- fixed wrong result for FormData in `isFile` function.

### 14.1.0

#### base.edit.form

- **POTENTIAL BREAKING CHANGE:** `destroy$` Subject and `ngOnDestroy` hook have been added.

- Implemented unsaved form restoring. Use `this.enableRestoreUnsavedData = false;` in constructor to disable it (default is `true`). `common.message.restoreUnsavedData` translation should be added to the project.

### 14.0.1

#### common.auth.service.ts

- optional parameter `checkForPasswordChangeToken: boolean = true` has been added to the `processLogin` method.

### 14.0.0

Upgraded Angular version to 14.

#### base.edit.form

- `protected dateFormat = 'yyyy-MM-dd';` has been removed

- `protected commonService: CommonFormServices` has been moved to constructor

#### base.browse.form

- `deleteRecordWithConfirmation` function has been added:

```typescript
protected deleteRecordWithConfirmation(
  recordId: string,
  message?: string,
  translateMessage?: boolean
): void {
  ...
}
```

- `deleteRecord` abstract function has been added:

```typescript
protected abstract deleteRecord(recordId: string): void;
```
