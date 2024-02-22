import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { Observable, Observer, Subject, auditTime, takeUntil } from 'rxjs';
import { CanComponentDeactivate } from '../auth/can-deactivate.guard';
import { S2Error } from '../error/s2error';
import { LoggerFactory } from '../log/logger.factory';
import { S2Message, S2ViolatedConstraint } from '../model/common.api';
import { MessageResolver } from '../ui/field/validation/message.resolver';
import { CommonFormServices } from './common.form.services';

export const EDIT_FORM_DATA_KEY = 'editFormValues';

interface EditFormData<T> {
  url: string;
  actionType: string;
  data: T;
}

/**
 * @deprecated since 16.0.0
 */
@Component({ template: '<p></p>' })
// eslint-disable-next-line @angular-eslint/component-class-suffix
export abstract class DeprecatedBaseEditForm<T> implements OnInit, OnDestroy, CanComponentDeactivate {
  static readonly EDIT_FORM_DATA_KEY = 'editFormValues';
  public readonly destroy$: Subject<void> = new Subject();
  public static formSavedSuccessSubject: Subject<boolean> = new Subject();
  log = LoggerFactory.getLogger();
  formDataLoaded = false;
  initialData: T;
  data: T;
  form: FormGroup;
  protected id: string;
  protected actionType: string;
  protected dirty = false;
  protected saved = false;
  newRecordMode = true;

  protected abstract buildForm(formBuilder: FormBuilder): void;
  protected abstract doSave(value: T): void | Observable<T>;
  protected abstract doLoad(id: string, actionType?: string): void | Observable<T>;
  protected abstract setData(data: T): void;
  protected abstract getData(): T;

  constructor(
    protected formBuilder: FormBuilder,
    protected commonFormServices: CommonFormServices,
    protected activatedRoute?: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.buildForm(this.formBuilder);
    if (this.isRestoreUnsavedDataEnabled) {
      this.form.valueChanges.pipe(takeUntil(this.destroy$), auditTime(200)).subscribe(() => {
        this.saveFormDataToLS();
      });
    }

    if (this.activatedRoute) {
      this.activatedRoute.queryParams.subscribe((queryParam) => {
        if (queryParam.actionType !== undefined) {
          this.actionType = queryParam.actionType as string;
        } else {
          this.actionType = null;
        }
      });
      this.activatedRoute.params.subscribe((params) => {
        this.id = params.id as string;
        this.formDataLoaded = false;
        if (this.id && this.id !== 'new') {
          if (this.isRestoreUnsavedDataEnabled && this.getFormDataFromLS()) {
            this.loadFormDataFromLSWithConfirmation(true);
          } else {
            this.executeDoLoad(this.id, this.actionType);
          }
        } else if (this.id === 'new') {
          this.initialData = this.copyObject(this.getData());
          this.setFormState(null);
          this.loadFormDataFromLSWithConfirmation();
        } else if (this.forceDoLoad()) {
          this.executeDoLoad(null, this.actionType);
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
    this.removeFormDataFromLS();
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  onSubmit(event: Event): void {
    this.form.markAllAsTouched();
    this.commonFormServices.appMessages.clearMessages();
    if (this.form.valid) {
      this.executeDoSave(this.getData());
    } else {
      this.log.debug(this.form.errors);
      if (this.form.errors) {
        this.commonFormServices.appMessages.showError(
          '',
          MessageResolver.resolveErrorToString(this.commonFormServices.translate, this.form.errors, null, true)
        );
      } else {
        this.commonFormServices.translate
          .get('common.message.correctValidationErrors')
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showError('', translation, 'VALIDATION');
          });
      }
    }
  }

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (this.hasDataChanged()) {
      return new Observable((observer: Observer<boolean>) => {
        this.commonFormServices.confirmationService.confirm({
          message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
          icon: 'fa fa-question-circle',
          accept: () => {
            this.removeFormDataFromLS();
            observer.next(true);
            observer.complete();
          },
          reject: () => {
            observer.next(false);
            observer.complete();
          },
        });
      });
    } else {
      this.removeFormDataFromLS();
      return true;
    }
  }

  onChange(): void {
    this.dirty = true;
    this.saved = false;
  }

  doCancel(): void {
    if (
      this.isWarningEnabled &&
      window.confirm(this.commonFormServices.translate.instant('common.message.discardChanges') as string)
    ) {
      this.executeDoLoad(this.id);
    }
  }

  downloadFile(data: Blob, fileName: string): void {
    const link = document.createElement('a');
    link.style.display = 'none';
    document.body.appendChild(link);
    if (link.download !== undefined) {
      link.setAttribute('href', URL.createObjectURL(data));
      link.setAttribute('download', fileName);
      link.click();
    }
    document.body.removeChild(link);
  }

  public onSaveSuccess(data: T): void {
    this.log.debug('onSaveSuccess');
    this.commonFormServices.appMessages.showSuccess('', this.getSuccessMessage());
    this.form.reset();
    this.removeFormDataFromLS();
    this.onLoadSuccess(data);
    this.dirty = false;
    this.saved = true;
    if (this.isEventEmitEnabled) {
      DeprecatedBaseEditForm.formSavedSuccessSubject.next(true);
    }
  }

  protected getSuccessMessage(): string {
    return this.commonFormServices.translate.instant('common.message.saveSuccess') as string;
  }

  protected setFormControlDisabled(controlName: string, disabled: boolean): void {
    const control = this.form.controls[controlName];
    if (disabled === true) {
      control.disable();
    } else {
      control.enable();
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected setFormState(data: T): void {}

  protected onLoadSuccess(data: T): void {
    this.log.debug('onLoadSuccess');
    this.newRecordMode = false;
    this.setFormState(data);
    this.setData(data);
    this.initialData = this.copyObject(this.getData());
    this.dirty = false;
    this.formDataLoaded = true;
  }

  protected markFormFieldsPristine(): void {
    Object.keys(this.form.controls).forEach((key) => {
      this.form.controls[key].markAsPristine();
      if (this.form.controls[key] instanceof FormGroup) {
        Object.keys((this.form.controls[key] as FormGroup).controls).forEach((key2) => {
          (this.form.controls[key] as FormGroup).controls[key2].markAsPristine();
        });
      }
    });
  }

  protected get isWarningEnabled(): boolean {
    return true;
  }

  protected get isEventEmitEnabled(): boolean {
    return true;
  }

  protected get isRestoreUnsavedDataEnabled(): boolean {
    return false;
  }

  protected copyObject(data: T): T {
    return JSON.parse(this.stringifyJsonEmptyAsNulls(data)) as T;
  }

  protected stringifyJsonEmptyAsNulls(data: T): string | null {
    return JSON.stringify(data, (key, value: string) => {
      return value === '' ? null : value;
    });
  }

  protected hasDataChanged(): boolean {
    const initialData = JSON.stringify(this.initialData);
    const newData = this.stringifyJsonEmptyAsNulls(this.getData());
    return !this.saved && initialData !== newData;
  }

  protected forceDoLoad(): boolean {
    return false;
  }

  backToBrowseForm(formUrl: string | string[], startAtRoot = true): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    if (startAtRoot) {
      if (typeof formUrl === 'string') {
        formUrl = [`/${formUrl}`];
      } else if (Array.isArray(formUrl)) {
        formUrl = ['/', ...formUrl];
      }
    }
    void this.commonFormServices.router.navigate(typeof formUrl === 'string' ? [formUrl] : formUrl, navigationExtras);
  }

  protected saveFormDataToLS(): void {
    if (this.formDataLoaded) {
      const formData: EditFormData<T> = {
        url: this.commonFormServices.router.url,
        actionType: this.actionType,
        data: this.getData(),
      };
      localStorage.setItem(EDIT_FORM_DATA_KEY, JSON.stringify(formData));
    }
  }

  protected getFormDataFromLS(): EditFormData<T> {
    const formData = JSON.parse(localStorage.getItem(EDIT_FORM_DATA_KEY)) as EditFormData<T>;
    if (formData) {
      if (
        formData.url === this.commonFormServices.router.url &&
        formData.actionType === this.actionType &&
        formData.data
      ) {
        return formData;
      }
      this.removeFormDataFromLS();
    }
    return null;
  }

  protected removeFormDataFromLS(): void {
    localStorage.removeItem(EDIT_FORM_DATA_KEY);
  }

  protected loadFormDataFromLSWithConfirmation(
    doLoadIfRejected = false,
    dialogText = 'common.message.restoreUnsavedData',
    translate = true
  ): void {
    if (this.isRestoreUnsavedDataEnabled) {
      const formData = this.getFormDataFromLS();
      if (formData?.data) {
        if (translate) {
          this.commonFormServices.translate.get(dialogText).subscribe((translation: string) => {
            this.commonFormServices.confirmationService.confirm({
              message: translation,
              accept: () => {
                this.setData(formData.data);
                this.formDataLoaded = true;
              },
              reject: () => {
                this.removeFormDataFromLS();
                if (doLoadIfRejected) {
                  this.executeDoLoad(this.id, this.actionType);
                } else {
                  this.formDataLoaded = true;
                }
              },
            });
          });
        } else {
          this.commonFormServices.confirmationService.confirm({
            message: dialogText,
            accept: () => {
              this.setData(formData.data);
            },
            reject: this.removeFormDataFromLS.bind(this),
          });
        }
      }
    }
  }

  protected executeDoSave(data: T): void {
    (this.doSave(data) as Observable<T>)?.subscribe({
      next: (result) => {
        this.onSaveSuccess(result);
      },
      error: (error) => {
        let constraintMessages: S2Message[];
        if (error instanceof S2Error) {
          constraintMessages = error.messages?.filter((message) => message.key === 'db.table.uk.constraint');
          if (constraintMessages?.length) {
            const violatedConstraints: S2ViolatedConstraint[] = constraintMessages.map((message) => ({
              name: message.item,
              fields: message.params,
            }));
            this.handleConstraintsResult(violatedConstraints);
          }
        }
        if (!constraintMessages?.length || !(error instanceof S2Error)) {
          throw error;
        }
      },
    });
  }

  protected executeDoLoad(id?: string, actionType?: string): void {
    (this.doLoad(id, actionType) as Observable<T>)?.subscribe((result) => {
      this.onLoadSuccess(result);
    });
  }

  protected checkConstraints(
    apiFunction: () => Observable<S2ViolatedConstraint[]>,
    fields?: string[],
    fieldControls?: Record<string, AbstractControl | string>
  ): void {
    apiFunction().subscribe((response) => {
      this.handleConstraintsResult(response, fields, fieldControls);
    });
  }

  protected handleConstraintsResult(
    result: S2ViolatedConstraint[],
    fields?: string[],
    fieldControls?: Record<string, AbstractControl | string>
  ): void {
    result.forEach((constraint) => {
      if (constraint.fields?.length) {
        constraint.fields
          .filter((field) => !fields || fields.includes(field))
          .forEach((field) => {
            const control =
              fieldControls?.[field] && fieldControls?.[field] instanceof AbstractControl
                ? (fieldControls?.[field] as AbstractControl)
                : this.form.controls[field];
            if (control) {
              const errors = control.errors || {};
              errors[constraint.name] = true;
              control.setErrors(errors);
            }
          });
      }
    });
  }
}
