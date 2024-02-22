import { Component } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
import { Observable, Subject, auditTime, takeUntil } from 'rxjs';
import { S2Error } from '../error/s2error';
import { LoggerFactory } from '../log/logger.factory';
import { MessageResolver } from '../ui/field/validation/message.resolver';
import * as i0 from "@angular/core";
import * as i1 from "./common.form.services";
import * as i2 from "@angular/router";
class BaseEditForm {
    commonFormServices;
    activatedRoute;
    static LS_KEY_EDIT_FORM_DATA = 'editFormValues';
    destroy$ = new Subject();
    static formSavedSuccessSubject = new Subject();
    log = LoggerFactory.getLogger();
    formDataLoaded = false;
    initialData;
    data;
    id;
    actionType;
    dirty = false;
    saved = false;
    newRecordMode = true;
    constructor(commonFormServices, activatedRoute) {
        this.commonFormServices = commonFormServices;
        this.activatedRoute = activatedRoute;
    }
    ngOnInit() {
        if (this.isRestoreUnsavedDataEnabled) {
            this.form.valueChanges.pipe(takeUntil(this.destroy$), auditTime(200)).subscribe(() => {
                this.saveFormDataToLS();
            });
        }
        if (this.activatedRoute) {
            this.activatedRoute.queryParams.subscribe((queryParam) => {
                if (queryParam.actionType !== undefined) {
                    this.actionType = queryParam.actionType;
                }
                else {
                    this.actionType = null;
                }
            });
            this.activatedRoute.params.subscribe((params) => {
                this.id = params.id;
                this.formDataLoaded = false;
                if (this.id && this.id !== 'new') {
                    if (this.isRestoreUnsavedDataEnabled && this.getFormDataFromLS()) {
                        this.loadFormDataFromLSWithConfirmation(true);
                    }
                    else {
                        this.executeDoLoad(this.id, this.actionType);
                    }
                }
                else if (this.id === 'new') {
                    this.initialData = this.copyObject(this.getData());
                    this.setFormState(null);
                    this.loadFormDataFromLSWithConfirmation();
                }
                else if (this.forceDoLoad()) {
                    this.executeDoLoad(null, this.actionType);
                }
            });
        }
    }
    ngOnDestroy() {
        this.destroy$.next();
        this.destroy$.unsubscribe();
        this.removeFormDataFromLS();
    }
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    onSubmit(event) {
        this.form.markAllAsTouched();
        this.commonFormServices.appMessages.clearMessages();
        if (this.form.valid) {
            this.executeDoSave(this.getData());
        }
        else {
            this.log.debug(this.form.errors);
            if (this.form.errors) {
                this.commonFormServices.appMessages.showError('', MessageResolver.resolveErrorToString(this.commonFormServices.translate, this.form.errors, null, true));
            }
            else {
                this.commonFormServices.translate
                    .get('common.message.correctValidationErrors')
                    .subscribe((translation) => {
                    this.commonFormServices.appMessages.showError('', translation, 'VALIDATION');
                });
            }
        }
    }
    canDeactivate() {
        if (this.hasDataChanged()) {
            return new Observable((observer) => {
                this.commonFormServices.confirmationService.confirm({
                    message: this.commonFormServices.translate.instant('common.message.discardChanges'),
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
        }
        else {
            this.removeFormDataFromLS();
            return true;
        }
    }
    onChange() {
        this.dirty = true;
        this.saved = false;
    }
    doCancel() {
        if (this.isWarningEnabled &&
            window.confirm(this.commonFormServices.translate.instant('common.message.discardChanges'))) {
            this.executeDoLoad(this.id);
        }
    }
    downloadFile(data, fileName) {
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
    onSaveSuccess(data) {
        this.log.debug('onSaveSuccess');
        this.commonFormServices.appMessages.showSuccess('', this.getSuccessMessage());
        this.form.reset();
        this.removeFormDataFromLS();
        this.onLoadSuccess(data);
        this.dirty = false;
        this.saved = true;
        if (this.isEventEmitEnabled) {
            BaseEditForm.formSavedSuccessSubject.next(true);
        }
    }
    getSuccessMessage() {
        return this.commonFormServices.translate.instant('common.message.saveSuccess');
    }
    setFormControlDisabled(controlName, disabled) {
        const control = this.form.controls[controlName];
        if (disabled === true) {
            control.disable();
        }
        else {
            control.enable();
        }
    }
    // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
    setFormState(data) { }
    onLoadSuccess(data) {
        this.log.debug('onLoadSuccess');
        this.newRecordMode = false;
        this.setFormState(data);
        this.setData(data);
        this.initialData = this.copyObject(this.getData());
        this.dirty = false;
        this.formDataLoaded = true;
    }
    markFormFieldsPristine() {
        Object.keys(this.form.controls).forEach((key) => {
            this.form.controls[key].markAsPristine();
            if (this.form.controls[key] instanceof FormGroup) {
                Object.keys(this.form.controls[key].controls).forEach((key2) => {
                    this.form.controls[key].controls[key2].markAsPristine();
                });
            }
        });
    }
    get isWarningEnabled() {
        return true;
    }
    get isEventEmitEnabled() {
        return true;
    }
    get isRestoreUnsavedDataEnabled() {
        return false;
    }
    copyObject(data) {
        return JSON.parse(this.stringifyJsonEmptyAsNulls(data));
    }
    stringifyJsonEmptyAsNulls(data) {
        return JSON.stringify(data, (key, value) => {
            return value === '' ? null : value;
        });
    }
    hasDataChanged() {
        const initialData = JSON.stringify(this.initialData);
        const newData = this.stringifyJsonEmptyAsNulls(this.getData());
        return !this.saved && initialData !== newData;
    }
    forceDoLoad() {
        return false;
    }
    backToBrowseForm(formUrl, startAtRoot = true) {
        const navigationExtras = { state: { keepFilters: true } };
        if (startAtRoot) {
            if (typeof formUrl === 'string') {
                formUrl = [`/${formUrl}`];
            }
            else if (Array.isArray(formUrl)) {
                formUrl = ['/', ...formUrl];
            }
        }
        void this.commonFormServices.router.navigate(typeof formUrl === 'string' ? [formUrl] : formUrl, navigationExtras);
    }
    saveFormDataToLS() {
        if (this.formDataLoaded) {
            const formData = {
                url: this.commonFormServices.router.url,
                actionType: this.actionType,
                data: this.getData(),
            };
            localStorage.setItem(BaseEditForm.LS_KEY_EDIT_FORM_DATA, JSON.stringify(formData));
        }
    }
    getFormDataFromLS() {
        const formData = JSON.parse(localStorage.getItem(BaseEditForm.LS_KEY_EDIT_FORM_DATA));
        if (formData) {
            if (formData.url === this.commonFormServices.router.url &&
                formData.actionType === this.actionType &&
                formData.data) {
                return formData;
            }
            this.removeFormDataFromLS();
        }
        return null;
    }
    removeFormDataFromLS() {
        localStorage.removeItem(BaseEditForm.LS_KEY_EDIT_FORM_DATA);
    }
    loadFormDataFromLSWithConfirmation(doLoadIfRejected = false, dialogText = 'common.message.restoreUnsavedData', translate = true) {
        if (this.isRestoreUnsavedDataEnabled) {
            const formData = this.getFormDataFromLS();
            if (formData?.data) {
                if (translate) {
                    this.commonFormServices.translate.get(dialogText).subscribe((translation) => {
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
                                }
                                else {
                                    this.formDataLoaded = true;
                                }
                            },
                        });
                    });
                }
                else {
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
    executeDoSave(data) {
        this.doSave(data)?.subscribe({
            next: (result) => {
                this.onSaveSuccess(result);
            },
            error: (error) => {
                let constraintMessages;
                if (error instanceof S2Error) {
                    constraintMessages = error.messages?.filter((message) => message.key === 'db.table.uk.constraint');
                    if (constraintMessages?.length) {
                        const violatedConstraints = constraintMessages.map((message) => ({
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
    executeDoLoad(id, actionType) {
        this.doLoad(id, actionType)?.subscribe((result) => {
            this.onLoadSuccess(result);
        });
    }
    checkConstraints(apiFunction, fields, fieldControls) {
        apiFunction().subscribe((response) => {
            this.handleConstraintsResult(response, fields, fieldControls);
        });
    }
    handleConstraintsResult(result, fields, fieldControls) {
        result.forEach((constraint) => {
            if (constraint.fields?.length) {
                constraint.fields
                    .filter((field) => !fields || fields.includes(field))
                    .forEach((field) => {
                    const control = fieldControls?.[field] && fieldControls?.[field] instanceof AbstractControl
                        ? fieldControls?.[field]
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
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseEditForm, deps: [{ token: i1.CommonFormServices }, { token: i2.ActivatedRoute }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: BaseEditForm, selector: "ng-component", ngImport: i0, template: '<p></p>', isInline: true });
}
export { BaseEditForm };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BaseEditForm, decorators: [{
            type: Component,
            args: [{ template: '<p></p>' }]
        }], ctorParameters: function () { return [{ type: i1.CommonFormServices }, { type: i2.ActivatedRoute }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYmFzZS5lZGl0LmZvcm0uanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi8uLi8uLi8uLi9wcm9qZWN0cy9uZ3gtczItY29tbW9ucy9zcmMvbGliL2Zvcm0vYmFzZS5lZGl0LmZvcm0udHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFNBQVMsRUFBcUIsTUFBTSxlQUFlLENBQUM7QUFDN0QsT0FBTyxFQUFFLGVBQWUsRUFBRSxTQUFTLEVBQUUsTUFBTSxnQkFBZ0IsQ0FBQztBQUU1RCxPQUFPLEVBQUUsVUFBVSxFQUFZLE9BQU8sRUFBRSxTQUFTLEVBQUUsU0FBUyxFQUFFLE1BQU0sTUFBTSxDQUFDO0FBRTNFLE9BQU8sRUFBRSxPQUFPLEVBQUUsTUFBTSxrQkFBa0IsQ0FBQztBQUMzQyxPQUFPLEVBQUUsYUFBYSxFQUFFLE1BQU0sdUJBQXVCLENBQUM7QUFFdEQsT0FBTyxFQUFFLGVBQWUsRUFBRSxNQUFNLHlDQUF5QyxDQUFDOzs7O0FBUzFFLE1BRXNCLFlBQVk7SUFvQlY7SUFBa0Q7SUFuQnhFLE1BQU0sQ0FBVSxxQkFBcUIsR0FBRyxnQkFBZ0IsQ0FBQztJQUN6QyxRQUFRLEdBQWtCLElBQUksT0FBTyxFQUFFLENBQUM7SUFDakQsTUFBTSxDQUFDLHVCQUF1QixHQUFxQixJQUFJLE9BQU8sRUFBRSxDQUFDO0lBQ3hFLEdBQUcsR0FBRyxhQUFhLENBQUMsU0FBUyxFQUFFLENBQUM7SUFDaEMsY0FBYyxHQUFHLEtBQUssQ0FBQztJQUN2QixXQUFXLENBQUk7SUFDZixJQUFJLENBQUk7SUFFRSxFQUFFLENBQVM7SUFDWCxVQUFVLENBQVM7SUFDbkIsS0FBSyxHQUFHLEtBQUssQ0FBQztJQUNkLEtBQUssR0FBRyxLQUFLLENBQUM7SUFDeEIsYUFBYSxHQUFHLElBQUksQ0FBQztJQU9yQixZQUFzQixrQkFBc0MsRUFBWSxjQUErQjtRQUFqRix1QkFBa0IsR0FBbEIsa0JBQWtCLENBQW9CO1FBQVksbUJBQWMsR0FBZCxj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Q0FBQztxQkFDM0I7Z0JBQ0gsQ0FBQyxDQUFDLENBQUM7YUFDTjtRQUNILENBQUMsQ0FBQyxDQUFDO0lBQ0wsQ0FBQzt1R0F4V21CLFlBQVk7MkZBQVosWUFBWSxvREFGWCxTQUFTOztTQUVWLFlBQVk7MkZBQVosWUFBWTtrQkFGakMsU0FBUzttQkFBQyxFQUFFLFFBQVEsRUFBRSxTQUFTLEVBQUUiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBDb21wb25lbnQsIE9uRGVzdHJveSwgT25Jbml0IH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5pbXBvcnQgeyBBYnN0cmFjdENvbnRyb2wsIEZvcm1Hcm91cCB9IGZyb20gJ0Bhbmd1bGFyL2Zvcm1zJztcbmltcG9ydCB7IEFjdGl2YXRlZFJvdXRlLCBOYXZpZ2F0aW9uRXh0cmFzIH0gZnJvbSAnQGFuZ3VsYXIvcm91dGVyJztcbmltcG9ydCB7IE9ic2VydmFibGUsIE9ic2VydmVyLCBTdWJqZWN0LCBhdWRpdFRpbWUsIHRha2VVbnRpbCB9IGZyb20gJ3J4anMnO1xuaW1wb3J0IHsgQ2FuQ29tcG9uZW50RGVhY3RpdmF0ZSB9IGZyb20gJy4uL2F1dGgvY2FuLWRlYWN0aXZhdGUuZ3VhcmQnO1xuaW1wb3J0IHsgUzJFcnJvciB9IGZyb20gJy4uL2Vycm9yL3MyZXJyb3InO1xuaW1wb3J0IHsgTG9nZ2VyRmFjdG9yeSB9IGZyb20gJy4uL2xvZy9sb2dnZXIuZmFjdG9yeSc7XG5pbXBvcnQgeyBTMk1lc3NhZ2UsIFMyVmlvbGF0ZWRDb25zdHJhaW50IH0gZnJvbSAnLi4vbW9kZWwvY29tbW9uLmFwaSc7XG5pbXBvcnQgeyBNZXNzYWdlUmVzb2x2ZXIgfSBmcm9tICcuLi91aS9maWVsZC92YWxpZGF0aW9uL21lc3NhZ2UucmVzb2x2ZXInO1xuaW1wb3J0IHsgQ29tbW9uRm9ybVNlcnZpY2VzIH0gZnJvbSAnLi9jb21tb24uZm9ybS5zZXJ2aWNlcyc7XG5cbmludGVyZmFjZSBFZGl0Rm9ybURhdGE8VD4ge1xuICB1cmw6IHN0cmluZztcbiAgYWN0aW9uVHlwZTogc3RyaW5nO1xuICBkYXRhOiBUO1xufVxuXG5AQ29tcG9uZW50KHsgdGVtcGxhdGU6ICc8cD48L3A+JyB9KVxuLy8gZXNsaW50LWRpc2FibGUtbmV4dC1saW5lIEBhbmd1bGFyLWVzbGludC9jb21wb25lbnQtY2xhc3Mtc3VmZml4XG5leHBvcnQgYWJzdHJhY3QgY2xhc3MgQmFzZUVkaXRGb3JtPFQ+IGltcGxlbWVudHMgT25Jbml0LCBPbkRlc3Ryb3ksIENhbkNvbXBvbmVudERlYWN0aXZhdGUge1xuICBzdGF0aWMgcmVhZG9ubHkgTFNfS0VZX0VESVRfRk9STV9EQVRBID0gJ2VkaXRGb3JtVmFsdWVzJztcbiAgcHVibGljIHJlYWRvbmx5IGRlc3Ryb3kkOiBTdWJqZWN0PHZvaWQ+ID0gbmV3IFN1YmplY3QoKTtcbiAgcHVibGljIHN0YXRpYyBmb3JtU2F2ZWRTdWNjZXNzU3ViamVjdDogU3ViamVjdDxib29sZWFuPiA9IG5ldyBTdWJqZWN0KCk7XG4gIGxvZyA9IExvZ2dlckZhY3RvcnkuZ2V0TG9nZ2VyKCk7XG4gIGZvcm1EYXRhTG9hZGVkID0gZmFsc2U7XG4gIGluaXRpYWxEYXRhOiBUO1xuICBkYXRhOiBUO1xuICBhYnN0cmFjdCBmb3JtOiBGb3JtR3JvdXA7XG4gIHByb3RlY3RlZCBpZDogc3RyaW5nO1xuICBwcm90ZWN0ZWQgYWN0aW9uVHlwZTogc3RyaW5nO1xuICBwcm90ZWN0ZWQgZGlydHkgPSBmYWxzZTtcbiAgcHJvdGVjdGVkIHNhdmVkID0gZmFsc2U7XG4gIG5ld1JlY29yZE1vZGUgPSB0cnVlO1xuXG4gIHByb3RlY3RlZCBhYnN0cmFjdCBkb1NhdmUodmFsdWU6IFQpOiB2b2lkIHwgT2JzZXJ2YWJsZTxUPjtcbiAgcHJvdGVjdGVkIGFic3RyYWN0IGRvTG9hZChpZDogc3RyaW5nLCBhY3Rpb25UeXBlPzogc3RyaW5nKTogdm9pZCB8IE9ic2VydmFibGU8VD47XG4gIHByb3RlY3RlZCBhYnN0cmFjdCBzZXREYXRhKGRhdGE6IFQpOiB2b2lkO1xuICBwcm90ZWN0ZWQgYWJzdHJhY3QgZ2V0RGF0YSgpOiBUO1xuXG4gIGNvbnN0cnVjdG9yKHByb3RlY3RlZCBjb21tb25Gb3JtU2VydmljZXM6IENvbW1vbkZvcm1TZXJ2aWNlcywgcHJvdGVjdGVkIGFjdGl2YXRlZFJvdXRlPzogQWN0aXZhdGVkUm91dGUpIHt9XG5cbiAgbmdPbkluaXQoKTogdm9pZCB7XG4gICAgaWYgKHRoaXMuaXNSZXN0b3JlVW5zYXZlZERhdGFFbmFibGVkKSB7XG4gICAgICB0aGlzLmZvcm0udmFsdWVDaGFuZ2VzLnBpcGUodGFrZVVudGlsKHRoaXMuZGVzdHJveSQpLCBhdWRpdFRpbWUoMjAwKSkuc3Vic2NyaWJlKCgpID0+IHtcbiAgICAgICAgdGhpcy5zYXZlRm9ybURhdGFUb0xTKCk7XG4gICAgICB9KTtcbiAgICB9XG5cbiAgICBpZiAodGhpcy5hY3RpdmF0ZWRSb3V0ZSkge1xuICAgICAgdGhpcy5hY3RpdmF0ZWRSb3V0ZS5xdWVyeVBhcmFtcy5zdWJzY3JpYmUoKHF1ZXJ5UGFyYW0pID0+IHtcbiAgICAgICAgaWYgKHF1ZXJ5UGFyYW0uYWN0aW9uVHlwZSAhPT0gdW5kZWZpbmVkKSB7XG4gICAgICAgICAgdGhpcy5hY3Rpb25UeXBlID0gcXVlcnlQYXJhbS5hY3Rpb25UeXBlIGFzIHN0cmluZztcbiAgICAgICAgfSBlbHNlIHtcbiAgICAgICAgICB0aGlzLmFjdGlvblR5cGUgPSBudWxsO1xuICAgICAgICB9XG4gICAgICB9KTtcbiAgICAgIHRoaXMuYWN0aXZhdGVkUm91dGUucGFyYW1zLnN1YnNjcmliZSgocGFyYW1zKSA9PiB7XG4gICAgICAgIHRoaXMuaWQgPSBwYXJhbXMuaWQgYXMgc3RyaW5nO1xuICAgICAgICB0aGlzLmZvcm1EYXRhTG9hZGVkID0gZmFsc2U7XG4gICAgICAgIGlmICh0aGlzLmlkICYmIHRoaXMuaWQgIT09ICduZXcnKSB7XG4gICAgICAgICAgaWYgKHRoaXMuaXNSZXN0b3JlVW5zYXZlZERhdGFFbmFibGVkICYmIHRoaXMuZ2V0Rm9ybURhdGFGcm9tTFMoKSkge1xuICAgICAgICAgICAgdGhpcy5sb2FkRm9ybURhdGFGcm9tTFNXaXRoQ29uZmlybWF0aW9uKHRydWUpO1xuICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICB0aGlzLmV4ZWN1dGVEb0xvYWQodGhpcy5pZCwgdGhpcy5hY3Rpb25UeXBlKTtcbiAgICAgICAgICB9XG4gICAgICAgIH0gZWxzZSBpZiAodGhpcy5pZCA9PT0gJ25ldycpIHtcbiAgICAgICAgICB0aGlzLmluaXRpYWxEYXRhID0gdGhpcy5jb3B5T2JqZWN0KHRoaXMuZ2V0RGF0YSgpKTtcbiAgICAgICAgICB0aGlzLnNldEZvcm1TdGF0ZShudWxsKTtcbiAgICAgICAgICB0aGlzLmxvYWRGb3JtRGF0YUZyb21MU1dpdGhDb25maXJtYXRpb24oKTtcbiAgICAgICAgfSBlbHNlIGlmICh0aGlzLmZvcmNlRG9Mb2FkKCkpIHtcbiAgICAgICAgICB0aGlzLmV4ZWN1dGVEb0xvYWQobnVsbCwgdGhpcy5hY3Rpb25UeXBlKTtcbiAgICAgICAgfVxuICAgICAgfSk7XG4gICAgfVxuICB9XG5cbiAgbmdPbkRlc3Ryb3koKTogdm9pZCB7XG4gICAgdGhpcy5kZXN0cm95JC5uZXh0KCk7XG4gICAgdGhpcy5kZXN0cm95JC51bnN1YnNjcmliZSgpO1xuICAgIHRoaXMucmVtb3ZlRm9ybURhdGFGcm9tTFMoKTtcbiAgfVxuXG4gIC8vIGVzbGludC1kaXNhYmxlLW5leHQtbGluZSBAdHlwZXNjcmlwdC1lc2xpbnQvbm8tdW51c2VkLXZhcnNcbiAgb25TdWJtaXQoZXZlbnQ6IEV2ZW50KTogdm9pZCB7XG4gICAgdGhpcy5mb3JtLm1hcmtBbGxBc1RvdWNoZWQoKTtcbiAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5hcHBNZXNzYWdlcy5jbGVhck1lc3NhZ2VzKCk7XG4gICAgaWYgKHRoaXMuZm9ybS52YWxpZCkge1xuICAgICAgdGhpcy5leGVjdXRlRG9TYXZlKHRoaXMuZ2V0RGF0YSgpKTtcbiAgICB9IGVsc2Uge1xuICAgICAgdGhpcy5sb2cuZGVidWcodGhpcy5mb3JtLmVycm9ycyk7XG4gICAgICBpZiAodGhpcy5mb3JtLmVycm9ycykge1xuICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5hcHBNZXNzYWdlcy5zaG93RXJyb3IoXG4gICAgICAgICAgJycsXG4gICAgICAgICAgTWVzc2FnZVJlc29sdmVyLnJlc29sdmVFcnJvclRvU3RyaW5nKHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnRyYW5zbGF0ZSwgdGhpcy5mb3JtLmVycm9ycywgbnVsbCwgdHJ1ZSlcbiAgICAgICAgKTtcbiAgICAgIH0gZWxzZSB7XG4gICAgICAgIHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnRyYW5zbGF0ZVxuICAgICAgICAgIC5nZXQoJ2NvbW1vbi5tZXNzYWdlLmNvcnJlY3RWYWxpZGF0aW9uRXJyb3JzJylcbiAgICAgICAgICAuc3Vic2NyaWJlKCh0cmFuc2xhdGlvbjogc3RyaW5nKSA9PiB7XG4gICAgICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5hcHBNZXNzYWdlcy5zaG93RXJyb3IoJycsIHRyYW5zbGF0aW9uLCAnVkFMSURBVElPTicpO1xuICAgICAgICAgIH0pO1xuICAgICAgfVxuICAgIH1cbiAgfVxuXG4gIGNhbkRlYWN0aXZhdGUoKTogT2JzZXJ2YWJsZTxib29sZWFuPiB8IFByb21pc2U8Ym9vbGVhbj4gfCBib29sZWFuIHtcbiAgICBpZiAodGhpcy5oYXNEYXRhQ2hhbmdlZCgpKSB7XG4gICAgICByZXR1cm4gbmV3IE9ic2VydmFibGUoKG9ic2VydmVyOiBPYnNlcnZlcjxib29sZWFuPikgPT4ge1xuICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5jb25maXJtYXRpb25TZXJ2aWNlLmNvbmZpcm0oe1xuICAgICAgICAgIG1lc3NhZ2U6IHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnRyYW5zbGF0ZS5pbnN0YW50KCdjb21tb24ubWVzc2FnZS5kaXNjYXJkQ2hhbmdlcycpIGFzIHN0cmluZyxcbiAgICAgICAgICBpY29uOiAnZmEgZmEtcXVlc3Rpb24tY2lyY2xlJyxcbiAgICAgICAgICBhY2NlcHQ6ICgpID0+IHtcbiAgICAgICAgICAgIHRoaXMucmVtb3ZlRm9ybURhdGFGcm9tTFMoKTtcbiAgICAgICAgICAgIG9ic2VydmVyLm5leHQodHJ1ZSk7XG4gICAgICAgICAgICBvYnNlcnZlci5jb21wbGV0ZSgpO1xuICAgICAgICAgIH0sXG4gICAgICAgICAgcmVqZWN0OiAoKSA9PiB7XG4gICAgICAgICAgICBvYnNlcnZlci5uZXh0KGZhbHNlKTtcbiAgICAgICAgICAgIG9ic2VydmVyLmNvbXBsZXRlKCk7XG4gICAgICAgICAgfSxcbiAgICAgICAgfSk7XG4gICAgICB9KTtcbiAgICB9IGVsc2Uge1xuICAgICAgdGhpcy5yZW1vdmVGb3JtRGF0YUZyb21MUygpO1xuICAgICAgcmV0dXJuIHRydWU7XG4gICAgfVxuICB9XG5cbiAgb25DaGFuZ2UoKTogdm9pZCB7XG4gICAgdGhpcy5kaXJ0eSA9IHRydWU7XG4gICAgdGhpcy5zYXZlZCA9IGZhbHNlO1xuICB9XG5cbiAgZG9DYW5jZWwoKTogdm9pZCB7XG4gICAgaWYgKFxuICAgICAgdGhpcy5pc1dhcm5pbmdFbmFibGVkICYmXG4gICAgICB3aW5kb3cuY29uZmlybSh0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy50cmFuc2xhdGUuaW5zdGFudCgnY29tbW9uLm1lc3NhZ2UuZGlzY2FyZENoYW5nZXMnKSBhcyBzdHJpbmcpXG4gICAgKSB7XG4gICAgICB0aGlzLmV4ZWN1dGVEb0xvYWQodGhpcy5pZCk7XG4gICAgfVxuICB9XG5cbiAgZG93bmxvYWRGaWxlKGRhdGE6IEJsb2IsIGZpbGVOYW1lOiBzdHJpbmcpOiB2b2lkIHtcbiAgICBjb25zdCBsaW5rID0gZG9jdW1lbnQuY3JlYXRlRWxlbWVudCgnYScpO1xuICAgIGxpbmsuc3R5bGUuZGlzcGxheSA9ICdub25lJztcbiAgICBkb2N1bWVudC5ib2R5LmFwcGVuZENoaWxkKGxpbmspO1xuICAgIGlmIChsaW5rLmRvd25sb2FkICE9PSB1bmRlZmluZWQpIHtcbiAgICAgIGxpbmsuc2V0QXR0cmlidXRlKCdocmVmJywgVVJMLmNyZWF0ZU9iamVjdFVSTChkYXRhKSk7XG4gICAgICBsaW5rLnNldEF0dHJpYnV0ZSgnZG93bmxvYWQnLCBmaWxlTmFtZSk7XG4gICAgICBsaW5rLmNsaWNrKCk7XG4gICAgfVxuICAgIGRvY3VtZW50LmJvZHkucmVtb3ZlQ2hpbGQobGluayk7XG4gIH1cblxuICBwdWJsaWMgb25TYXZlU3VjY2VzcyhkYXRhOiBUKTogdm9pZCB7XG4gICAgdGhpcy5sb2cuZGVidWcoJ29uU2F2ZVN1Y2Nlc3MnKTtcbiAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5hcHBNZXNzYWdlcy5zaG93U3VjY2VzcygnJywgdGhpcy5nZXRTdWNjZXNzTWVzc2FnZSgpKTtcbiAgICB0aGlzLmZvcm0ucmVzZXQoKTtcbiAgICB0aGlzLnJlbW92ZUZvcm1EYXRhRnJvbUxTKCk7XG4gICAgdGhpcy5vbkxvYWRTdWNjZXNzKGRhdGEpO1xuICAgIHRoaXMuZGlydHkgPSBmYWxzZTtcbiAgICB0aGlzLnNhdmVkID0gdHJ1ZTtcbiAgICBpZiAodGhpcy5pc0V2ZW50RW1pdEVuYWJsZWQpIHtcbiAgICAgIEJhc2VFZGl0Rm9ybS5mb3JtU2F2ZWRTdWNjZXNzU3ViamVjdC5uZXh0KHRydWUpO1xuICAgIH1cbiAgfVxuXG4gIHByb3RlY3RlZCBnZXRTdWNjZXNzTWVzc2FnZSgpOiBzdHJpbmcge1xuICAgIHJldHVybiB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy50cmFuc2xhdGUuaW5zdGFudCgnY29tbW9uLm1lc3NhZ2Uuc2F2ZVN1Y2Nlc3MnKSBhcyBzdHJpbmc7XG4gIH1cblxuICBwcm90ZWN0ZWQgc2V0Rm9ybUNvbnRyb2xEaXNhYmxlZChjb250cm9sTmFtZTogc3RyaW5nLCBkaXNhYmxlZDogYm9vbGVhbik6IHZvaWQge1xuICAgIGNvbnN0IGNvbnRyb2wgPSB0aGlzLmZvcm0uY29udHJvbHNbY29udHJvbE5hbWVdO1xuICAgIGlmIChkaXNhYmxlZCA9PT0gdHJ1ZSkge1xuICAgICAgY29udHJvbC5kaXNhYmxlKCk7XG4gICAgfSBlbHNlIHtcbiAgICAgIGNvbnRyb2wuZW5hYmxlKCk7XG4gICAgfVxuICB9XG5cbiAgLy8gZXNsaW50LWRpc2FibGUtbmV4dC1saW5lIEB0eXBlc2NyaXB0LWVzbGludC9uby1lbXB0eS1mdW5jdGlvbiwgQHR5cGVzY3JpcHQtZXNsaW50L25vLXVudXNlZC12YXJzXG4gIHByb3RlY3RlZCBzZXRGb3JtU3RhdGUoZGF0YTogVCk6IHZvaWQge31cblxuICBwcm90ZWN0ZWQgb25Mb2FkU3VjY2VzcyhkYXRhOiBUKTogdm9pZCB7XG4gICAgdGhpcy5sb2cuZGVidWcoJ29uTG9hZFN1Y2Nlc3MnKTtcbiAgICB0aGlzLm5ld1JlY29yZE1vZGUgPSBmYWxzZTtcbiAgICB0aGlzLnNldEZvcm1TdGF0ZShkYXRhKTtcbiAgICB0aGlzLnNldERhdGEoZGF0YSk7XG4gICAgdGhpcy5pbml0aWFsRGF0YSA9IHRoaXMuY29weU9iamVjdCh0aGlzLmdldERhdGEoKSk7XG4gICAgdGhpcy5kaXJ0eSA9IGZhbHNlO1xuICAgIHRoaXMuZm9ybURhdGFMb2FkZWQgPSB0cnVlO1xuICB9XG5cbiAgcHJvdGVjdGVkIG1hcmtGb3JtRmllbGRzUHJpc3RpbmUoKTogdm9pZCB7XG4gICAgT2JqZWN0LmtleXModGhpcy5mb3JtLmNvbnRyb2xzKS5mb3JFYWNoKChrZXkpID0+IHtcbiAgICAgIHRoaXMuZm9ybS5jb250cm9sc1trZXldLm1hcmtBc1ByaXN0aW5lKCk7XG4gICAgICBpZiAodGhpcy5mb3JtLmNvbnRyb2xzW2tleV0gaW5zdGFuY2VvZiBGb3JtR3JvdXApIHtcbiAgICAgICAgT2JqZWN0LmtleXMoKHRoaXMuZm9ybS5jb250cm9sc1trZXldIGFzIEZvcm1Hcm91cCkuY29udHJvbHMpLmZvckVhY2goKGtleTIpID0+IHtcbiAgICAgICAgICAodGhpcy5mb3JtLmNvbnRyb2xzW2tleV0gYXMgRm9ybUdyb3VwKS5jb250cm9sc1trZXkyXS5tYXJrQXNQcmlzdGluZSgpO1xuICAgICAgICB9KTtcbiAgICAgIH1cbiAgICB9KTtcbiAgfVxuXG4gIHByb3RlY3RlZCBnZXQgaXNXYXJuaW5nRW5hYmxlZCgpOiBib29sZWFuIHtcbiAgICByZXR1cm4gdHJ1ZTtcbiAgfVxuXG4gIHByb3RlY3RlZCBnZXQgaXNFdmVudEVtaXRFbmFibGVkKCk6IGJvb2xlYW4ge1xuICAgIHJldHVybiB0cnVlO1xuICB9XG5cbiAgcHJvdGVjdGVkIGdldCBpc1Jlc3RvcmVVbnNhdmVkRGF0YUVuYWJsZWQoKTogYm9vbGVhbiB7XG4gICAgcmV0dXJuIGZhbHNlO1xuICB9XG5cbiAgcHJvdGVjdGVkIGNvcHlPYmplY3QoZGF0YTogVCk6IFQge1xuICAgIHJldHVybiBKU09OLnBhcnNlKHRoaXMuc3RyaW5naWZ5SnNvbkVtcHR5QXNOdWxscyhkYXRhKSkgYXMgVDtcbiAgfVxuXG4gIHByb3RlY3RlZCBzdHJpbmdpZnlKc29uRW1wdHlBc051bGxzKGRhdGE6IFQpOiBzdHJpbmcgfCBudWxsIHtcbiAgICByZXR1cm4gSlNPTi5zdHJpbmdpZnkoZGF0YSwgKGtleSwgdmFsdWU6IHN0cmluZykgPT4ge1xuICAgICAgcmV0dXJuIHZhbHVlID09PSAnJyA/IG51bGwgOiB2YWx1ZTtcbiAgICB9KTtcbiAgfVxuXG4gIHByb3RlY3RlZCBoYXNEYXRhQ2hhbmdlZCgpOiBib29sZWFuIHtcbiAgICBjb25zdCBpbml0aWFsRGF0YSA9IEpTT04uc3RyaW5naWZ5KHRoaXMuaW5pdGlhbERhdGEpO1xuICAgIGNvbnN0IG5ld0RhdGEgPSB0aGlzLnN0cmluZ2lmeUpzb25FbXB0eUFzTnVsbHModGhpcy5nZXREYXRhKCkpO1xuICAgIHJldHVybiAhdGhpcy5zYXZlZCAmJiBpbml0aWFsRGF0YSAhPT0gbmV3RGF0YTtcbiAgfVxuXG4gIHByb3RlY3RlZCBmb3JjZURvTG9hZCgpOiBib29sZWFuIHtcbiAgICByZXR1cm4gZmFsc2U7XG4gIH1cblxuICBiYWNrVG9Ccm93c2VGb3JtKGZvcm1Vcmw6IHN0cmluZyB8IHN0cmluZ1tdLCBzdGFydEF0Um9vdCA9IHRydWUpOiB2b2lkIHtcbiAgICBjb25zdCBuYXZpZ2F0aW9uRXh0cmFzOiBOYXZpZ2F0aW9uRXh0cmFzID0geyBzdGF0ZTogeyBrZWVwRmlsdGVyczogdHJ1ZSB9IH07XG4gICAgaWYgKHN0YXJ0QXRSb290KSB7XG4gICAgICBpZiAodHlwZW9mIGZvcm1VcmwgPT09ICdzdHJpbmcnKSB7XG4gICAgICAgIGZvcm1VcmwgPSBbYC8ke2Zvcm1Vcmx9YF07XG4gICAgICB9IGVsc2UgaWYgKEFycmF5LmlzQXJyYXkoZm9ybVVybCkpIHtcbiAgICAgICAgZm9ybVVybCA9IFsnLycsIC4uLmZvcm1VcmxdO1xuICAgICAgfVxuICAgIH1cbiAgICB2b2lkIHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnJvdXRlci5uYXZpZ2F0ZSh0eXBlb2YgZm9ybVVybCA9PT0gJ3N0cmluZycgPyBbZm9ybVVybF0gOiBmb3JtVXJsLCBuYXZpZ2F0aW9uRXh0cmFzKTtcbiAgfVxuXG4gIHByb3RlY3RlZCBzYXZlRm9ybURhdGFUb0xTKCk6IHZvaWQge1xuICAgIGlmICh0aGlzLmZvcm1EYXRhTG9hZGVkKSB7XG4gICAgICBjb25zdCBmb3JtRGF0YTogRWRpdEZvcm1EYXRhPFQ+ID0ge1xuICAgICAgICB1cmw6IHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnJvdXRlci51cmwsXG4gICAgICAgIGFjdGlvblR5cGU6IHRoaXMuYWN0aW9uVHlwZSxcbiAgICAgICAgZGF0YTogdGhpcy5nZXREYXRhKCksXG4gICAgICB9O1xuICAgICAgbG9jYWxTdG9yYWdlLnNldEl0ZW0oQmFzZUVkaXRGb3JtLkxTX0tFWV9FRElUX0ZPUk1fREFUQSwgSlNPTi5zdHJpbmdpZnkoZm9ybURhdGEpKTtcbiAgICB9XG4gIH1cblxuICBwcm90ZWN0ZWQgZ2V0Rm9ybURhdGFGcm9tTFMoKTogRWRpdEZvcm1EYXRhPFQ+IHtcbiAgICBjb25zdCBmb3JtRGF0YSA9IEpTT04ucGFyc2UobG9jYWxTdG9yYWdlLmdldEl0ZW0oQmFzZUVkaXRGb3JtLkxTX0tFWV9FRElUX0ZPUk1fREFUQSkpIGFzIEVkaXRGb3JtRGF0YTxUPjtcbiAgICBpZiAoZm9ybURhdGEpIHtcbiAgICAgIGlmIChcbiAgICAgICAgZm9ybURhdGEudXJsID09PSB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5yb3V0ZXIudXJsICYmXG4gICAgICAgIGZvcm1EYXRhLmFjdGlvblR5cGUgPT09IHRoaXMuYWN0aW9uVHlwZSAmJlxuICAgICAgICBmb3JtRGF0YS5kYXRhXG4gICAgICApIHtcbiAgICAgICAgcmV0dXJuIGZvcm1EYXRhO1xuICAgICAgfVxuICAgICAgdGhpcy5yZW1vdmVGb3JtRGF0YUZyb21MUygpO1xuICAgIH1cbiAgICByZXR1cm4gbnVsbDtcbiAgfVxuXG4gIHByb3RlY3RlZCByZW1vdmVGb3JtRGF0YUZyb21MUygpOiB2b2lkIHtcbiAgICBsb2NhbFN0b3JhZ2UucmVtb3ZlSXRlbShCYXNlRWRpdEZvcm0uTFNfS0VZX0VESVRfRk9STV9EQVRBKTtcbiAgfVxuXG4gIHByb3RlY3RlZCBsb2FkRm9ybURhdGFGcm9tTFNXaXRoQ29uZmlybWF0aW9uKFxuICAgIGRvTG9hZElmUmVqZWN0ZWQgPSBmYWxzZSxcbiAgICBkaWFsb2dUZXh0ID0gJ2NvbW1vbi5tZXNzYWdlLnJlc3RvcmVVbnNhdmVkRGF0YScsXG4gICAgdHJhbnNsYXRlID0gdHJ1ZVxuICApOiB2b2lkIHtcbiAgICBpZiAodGhpcy5pc1Jlc3RvcmVVbnNhdmVkRGF0YUVuYWJsZWQpIHtcbiAgICAgIGNvbnN0IGZvcm1EYXRhID0gdGhpcy5nZXRGb3JtRGF0YUZyb21MUygpO1xuICAgICAgaWYgKGZvcm1EYXRhPy5kYXRhKSB7XG4gICAgICAgIGlmICh0cmFuc2xhdGUpIHtcbiAgICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy50cmFuc2xhdGUuZ2V0KGRpYWxvZ1RleHQpLnN1YnNjcmliZSgodHJhbnNsYXRpb246IHN0cmluZykgPT4ge1xuICAgICAgICAgICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuY29uZmlybWF0aW9uU2VydmljZS5jb25maXJtKHtcbiAgICAgICAgICAgICAgbWVzc2FnZTogdHJhbnNsYXRpb24sXG4gICAgICAgICAgICAgIGFjY2VwdDogKCkgPT4ge1xuICAgICAgICAgICAgICAgIHRoaXMuc2V0RGF0YShmb3JtRGF0YS5kYXRhKTtcbiAgICAgICAgICAgICAgICB0aGlzLmZvcm1EYXRhTG9hZGVkID0gdHJ1ZTtcbiAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgcmVqZWN0OiAoKSA9PiB7XG4gICAgICAgICAgICAgICAgdGhpcy5yZW1vdmVGb3JtRGF0YUZyb21MUygpO1xuICAgICAgICAgICAgICAgIGlmIChkb0xvYWRJZlJlamVjdGVkKSB7XG4gICAgICAgICAgICAgICAgICB0aGlzLmV4ZWN1dGVEb0xvYWQodGhpcy5pZCwgdGhpcy5hY3Rpb25UeXBlKTtcbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgICAgICAgdGhpcy5mb3JtRGF0YUxvYWRlZCA9IHRydWU7XG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgICB9LFxuICAgICAgICAgICAgfSk7XG4gICAgICAgICAgfSk7XG4gICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuY29uZmlybWF0aW9uU2VydmljZS5jb25maXJtKHtcbiAgICAgICAgICAgIG1lc3NhZ2U6IGRpYWxvZ1RleHQsXG4gICAgICAgICAgICBhY2NlcHQ6ICgpID0+IHtcbiAgICAgICAgICAgICAgdGhpcy5zZXREYXRhKGZvcm1EYXRhLmRhdGEpO1xuICAgICAgICAgICAgfSxcbiAgICAgICAgICAgIHJlamVjdDogdGhpcy5yZW1vdmVGb3JtRGF0YUZyb21MUy5iaW5kKHRoaXMpLFxuICAgICAgICAgIH0pO1xuICAgICAgICB9XG4gICAgICB9XG4gICAgfVxuICB9XG5cbiAgcHJvdGVjdGVkIGV4ZWN1dGVEb1NhdmUoZGF0YTogVCk6IHZvaWQge1xuICAgICh0aGlzLmRvU2F2ZShkYXRhKSBhcyBPYnNlcnZhYmxlPFQ+KT8uc3Vic2NyaWJlKHtcbiAgICAgIG5leHQ6IChyZXN1bHQpID0+IHtcbiAgICAgICAgdGhpcy5vblNhdmVTdWNjZXNzKHJlc3VsdCk7XG4gICAgICB9LFxuICAgICAgZXJyb3I6IChlcnJvcikgPT4ge1xuICAgICAgICBsZXQgY29uc3RyYWludE1lc3NhZ2VzOiBTMk1lc3NhZ2VbXTtcbiAgICAgICAgaWYgKGVycm9yIGluc3RhbmNlb2YgUzJFcnJvcikge1xuICAgICAgICAgIGNvbnN0cmFpbnRNZXNzYWdlcyA9IGVycm9yLm1lc3NhZ2VzPy5maWx0ZXIoKG1lc3NhZ2UpID0+IG1lc3NhZ2Uua2V5ID09PSAnZGIudGFibGUudWsuY29uc3RyYWludCcpO1xuICAgICAgICAgIGlmIChjb25zdHJhaW50TWVzc2FnZXM/Lmxlbmd0aCkge1xuICAgICAgICAgICAgY29uc3QgdmlvbGF0ZWRDb25zdHJhaW50czogUzJWaW9sYXRlZENvbnN0cmFpbnRbXSA9IGNvbnN0cmFpbnRNZXNzYWdlcy5tYXAoKG1lc3NhZ2UpID0+ICh7XG4gICAgICAgICAgICAgIG5hbWU6IG1lc3NhZ2UuaXRlbSxcbiAgICAgICAgICAgICAgZmllbGRzOiBtZXNzYWdlLnBhcmFtcyxcbiAgICAgICAgICAgIH0pKTtcbiAgICAgICAgICAgIHRoaXMuaGFuZGxlQ29uc3RyYWludHNSZXN1bHQodmlvbGF0ZWRDb25zdHJhaW50cyk7XG4gICAgICAgICAgfVxuICAgICAgICB9XG4gICAgICAgIGlmICghY29uc3RyYWludE1lc3NhZ2VzPy5sZW5ndGggfHwgIShlcnJvciBpbnN0YW5jZW9mIFMyRXJyb3IpKSB7XG4gICAgICAgICAgdGhyb3cgZXJyb3I7XG4gICAgICAgIH1cbiAgICAgIH0sXG4gICAgfSk7XG4gIH1cblxuICBwcm90ZWN0ZWQgZXhlY3V0ZURvTG9hZChpZD86IHN0cmluZywgYWN0aW9uVHlwZT86IHN0cmluZyk6IHZvaWQge1xuICAgICh0aGlzLmRvTG9hZChpZCwgYWN0aW9uVHlwZSkgYXMgT2JzZXJ2YWJsZTxUPik/LnN1YnNjcmliZSgocmVzdWx0KSA9PiB7XG4gICAgICB0aGlzLm9uTG9hZFN1Y2Nlc3MocmVzdWx0KTtcbiAgICB9KTtcbiAgfVxuXG4gIHByb3RlY3RlZCBjaGVja0NvbnN0cmFpbnRzKFxuICAgIGFwaUZ1bmN0aW9uOiAoKSA9PiBPYnNlcnZhYmxlPFMyVmlvbGF0ZWRDb25zdHJhaW50W10+LFxuICAgIGZpZWxkcz86IHN0cmluZ1tdLFxuICAgIGZpZWxkQ29udHJvbHM/OiBSZWNvcmQ8c3RyaW5nLCBBYnN0cmFjdENvbnRyb2wgfCBzdHJpbmc+XG4gICk6IHZvaWQge1xuICAgIGFwaUZ1bmN0aW9uKCkuc3Vic2NyaWJlKChyZXNwb25zZSkgPT4ge1xuICAgICAgdGhpcy5oYW5kbGVDb25zdHJhaW50c1Jlc3VsdChyZXNwb25zZSwgZmllbGRzLCBmaWVsZENvbnRyb2xzKTtcbiAgICB9KTtcbiAgfVxuXG4gIHByb3RlY3RlZCBoYW5kbGVDb25zdHJhaW50c1Jlc3VsdChcbiAgICByZXN1bHQ6IFMyVmlvbGF0ZWRDb25zdHJhaW50W10sXG4gICAgZmllbGRzPzogc3RyaW5nW10sXG4gICAgZmllbGRDb250cm9scz86IFJlY29yZDxzdHJpbmcsIEFic3RyYWN0Q29udHJvbCB8IHN0cmluZz5cbiAgKTogdm9pZCB7XG4gICAgcmVzdWx0LmZvckVhY2goKGNvbnN0cmFpbnQpID0+IHtcbiAgICAgIGlmIChjb25zdHJhaW50LmZpZWxkcz8ubGVuZ3RoKSB7XG4gICAgICAgIGNvbnN0cmFpbnQuZmllbGRzXG4gICAgICAgICAgLmZpbHRlcigoZmllbGQpID0+ICFmaWVsZHMgfHwgZmllbGRzLmluY2x1ZGVzKGZpZWxkKSlcbiAgICAgICAgICAuZm9yRWFjaCgoZmllbGQpID0+IHtcbiAgICAgICAgICAgIGNvbnN0IGNvbnRyb2wgPVxuICAgICAgICAgICAgICBmaWVsZENvbnRyb2xzPy5bZmllbGRdICYmIGZpZWxkQ29udHJvbHM/LltmaWVsZF0gaW5zdGFuY2VvZiBBYnN0cmFjdENvbnRyb2xcbiAgICAgICAgICAgICAgICA/IChmaWVsZENvbnRyb2xzPy5bZmllbGRdIGFzIEFic3RyYWN0Q29udHJvbClcbiAgICAgICAgICAgICAgICA6IHRoaXMuZm9ybS5jb250cm9sc1tmaWVsZF07XG4gICAgICAgICAgICBpZiAoY29udHJvbCkge1xuICAgICAgICAgICAgICBjb25zdCBlcnJvcnMgPSBjb250cm9sLmVycm9ycyB8fCB7fTtcbiAgICAgICAgICAgICAgZXJyb3JzW2NvbnN0cmFpbnQubmFtZV0gPSB0cnVlO1xuICAgICAgICAgICAgICBjb250cm9sLnNldEVycm9ycyhlcnJvcnMpO1xuICAgICAgICAgICAgfVxuICAgICAgICAgIH0pO1xuICAgICAgfVxuICAgIH0pO1xuICB9XG59XG4iXX0=