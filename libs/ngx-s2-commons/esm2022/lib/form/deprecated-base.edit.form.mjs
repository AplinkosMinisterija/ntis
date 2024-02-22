import { Component } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
import { Observable, Subject, auditTime, takeUntil } from 'rxjs';
import { S2Error } from '../error/s2error';
import { LoggerFactory } from '../log/logger.factory';
import { MessageResolver } from '../ui/field/validation/message.resolver';
import * as i0 from "@angular/core";
import * as i1 from "@angular/forms";
import * as i2 from "./common.form.services";
import * as i3 from "@angular/router";
export const EDIT_FORM_DATA_KEY = 'editFormValues';
/**
 * @deprecated since 16.0.0
 */
class DeprecatedBaseEditForm {
    formBuilder;
    commonFormServices;
    activatedRoute;
    static EDIT_FORM_DATA_KEY = 'editFormValues';
    destroy$ = new Subject();
    static formSavedSuccessSubject = new Subject();
    log = LoggerFactory.getLogger();
    formDataLoaded = false;
    initialData;
    data;
    form;
    id;
    actionType;
    dirty = false;
    saved = false;
    newRecordMode = true;
    constructor(formBuilder, commonFormServices, activatedRoute) {
        this.formBuilder = formBuilder;
        this.commonFormServices = commonFormServices;
        this.activatedRoute = activatedRoute;
    }
    ngOnInit() {
        this.buildForm(this.formBuilder);
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
            DeprecatedBaseEditForm.formSavedSuccessSubject.next(true);
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
            localStorage.setItem(EDIT_FORM_DATA_KEY, JSON.stringify(formData));
        }
    }
    getFormDataFromLS() {
        const formData = JSON.parse(localStorage.getItem(EDIT_FORM_DATA_KEY));
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
        localStorage.removeItem(EDIT_FORM_DATA_KEY);
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
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: DeprecatedBaseEditForm, deps: [{ token: i1.FormBuilder }, { token: i2.CommonFormServices }, { token: i3.ActivatedRoute }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: DeprecatedBaseEditForm, selector: "ng-component", ngImport: i0, template: '<p></p>', isInline: true });
}
export { DeprecatedBaseEditForm };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: DeprecatedBaseEditForm, decorators: [{
            type: Component,
            args: [{ template: '<p></p>' }]
        }], ctorParameters: function () { return [{ type: i1.FormBuilder }, { type: i2.CommonFormServices }, { type: i3.ActivatedRoute }]; } });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGVwcmVjYXRlZC1iYXNlLmVkaXQuZm9ybS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi1jb21tb25zL3NyYy9saWIvZm9ybS9kZXByZWNhdGVkLWJhc2UuZWRpdC5mb3JtLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sRUFBRSxTQUFTLEVBQXFCLE1BQU0sZUFBZSxDQUFDO0FBQzdELE9BQU8sRUFBRSxlQUFlLEVBQWUsU0FBUyxFQUFFLE1BQU0sZ0JBQWdCLENBQUM7QUFFekUsT0FBTyxFQUFFLFVBQVUsRUFBWSxPQUFPLEVBQUUsU0FBUyxFQUFFLFNBQVMsRUFBRSxNQUFNLE1BQU0sQ0FBQztBQUUzRSxPQUFPLEVBQUUsT0FBTyxFQUFFLE1BQU0sa0JBQWtCLENBQUM7QUFDM0MsT0FBTyxFQUFFLGFBQWEsRUFBRSxNQUFNLHVCQUF1QixDQUFDO0FBRXRELE9BQU8sRUFBRSxlQUFlLEVBQUUsTUFBTSx5Q0FBeUMsQ0FBQzs7Ozs7QUFHMUUsTUFBTSxDQUFDLE1BQU0sa0JBQWtCLEdBQUcsZ0JBQWdCLENBQUM7QUFRbkQ7O0dBRUc7QUFDSCxNQUVzQixzQkFBc0I7SUFzQjlCO0lBQ0E7SUFDQTtJQXZCWixNQUFNLENBQVUsa0JBQWtCLEdBQUcsZ0JBQWdCLENBQUM7SUFDdEMsUUFBUSxHQUFrQixJQUFJLE9BQU8sRUFBRSxDQUFDO0lBQ2pELE1BQU0sQ0FBQyx1QkFBdUIsR0FBcUIsSUFBSSxPQUFPLEVBQUUsQ0FBQztJQUN4RSxHQUFHLEdBQUcsYUFBYSxDQUFDLFNBQVMsRUFBRSxDQUFDO0lBQ2hDLGNBQWMsR0FBRyxLQUFLLENBQUM7SUFDdkIsV0FBVyxDQUFJO0lBQ2YsSUFBSSxDQUFJO0lBQ1IsSUFBSSxDQUFZO0lBQ04sRUFBRSxDQUFTO0lBQ1gsVUFBVSxDQUFTO0lBQ25CLEtBQUssR0FBRyxLQUFLLENBQUM7SUFDZCxLQUFLLEdBQUcsS0FBSyxDQUFDO0lBQ3hC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O29CQUNoQyxJQUFJLE9BQU8sRUFBRTt3QkFDWCxNQUFNLE1BQU0sR0FBRyxPQUFPLENBQUMsTUFBTSxJQUFJLEVBQUUsQ0FBQzt3QkFDcEMsTUFBTSxDQUFDLFVBQVUsQ0FBQyxJQUFJLENBQUMsR0FBRyxJQUFJLENBQUM7d0JBQy9CLE9BQU8sQ0FBQyxTQUFTLENBQUMsTUFBTSxDQUFDLENBQUM7cUJBQzNCO2dCQUNILENBQUMsQ0FBQyxDQUFDO2FBQ047UUFDSCxDQUFDLENBQUMsQ0FBQztJQUNMLENBQUM7dUdBOVdtQixzQkFBc0I7MkZBQXRCLHNCQUFzQixvREFGckIsU0FBUzs7U0FFVixzQkFBc0I7MkZBQXRCLHNCQUFzQjtrQkFGM0MsU0FBUzttQkFBQyxFQUFFLFFBQVEsRUFBRSxTQUFTLEVBQUUiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBDb21wb25lbnQsIE9uRGVzdHJveSwgT25Jbml0IH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5pbXBvcnQgeyBBYnN0cmFjdENvbnRyb2wsIEZvcm1CdWlsZGVyLCBGb3JtR3JvdXAgfSBmcm9tICdAYW5ndWxhci9mb3Jtcyc7XG5pbXBvcnQgeyBBY3RpdmF0ZWRSb3V0ZSwgTmF2aWdhdGlvbkV4dHJhcyB9IGZyb20gJ0Bhbmd1bGFyL3JvdXRlcic7XG5pbXBvcnQgeyBPYnNlcnZhYmxlLCBPYnNlcnZlciwgU3ViamVjdCwgYXVkaXRUaW1lLCB0YWtlVW50aWwgfSBmcm9tICdyeGpzJztcbmltcG9ydCB7IENhbkNvbXBvbmVudERlYWN0aXZhdGUgfSBmcm9tICcuLi9hdXRoL2Nhbi1kZWFjdGl2YXRlLmd1YXJkJztcbmltcG9ydCB7IFMyRXJyb3IgfSBmcm9tICcuLi9lcnJvci9zMmVycm9yJztcbmltcG9ydCB7IExvZ2dlckZhY3RvcnkgfSBmcm9tICcuLi9sb2cvbG9nZ2VyLmZhY3RvcnknO1xuaW1wb3J0IHsgUzJNZXNzYWdlLCBTMlZpb2xhdGVkQ29uc3RyYWludCB9IGZyb20gJy4uL21vZGVsL2NvbW1vbi5hcGknO1xuaW1wb3J0IHsgTWVzc2FnZVJlc29sdmVyIH0gZnJvbSAnLi4vdWkvZmllbGQvdmFsaWRhdGlvbi9tZXNzYWdlLnJlc29sdmVyJztcbmltcG9ydCB7IENvbW1vbkZvcm1TZXJ2aWNlcyB9IGZyb20gJy4vY29tbW9uLmZvcm0uc2VydmljZXMnO1xuXG5leHBvcnQgY29uc3QgRURJVF9GT1JNX0RBVEFfS0VZID0gJ2VkaXRGb3JtVmFsdWVzJztcblxuaW50ZXJmYWNlIEVkaXRGb3JtRGF0YTxUPiB7XG4gIHVybDogc3RyaW5nO1xuICBhY3Rpb25UeXBlOiBzdHJpbmc7XG4gIGRhdGE6IFQ7XG59XG5cbi8qKlxuICogQGRlcHJlY2F0ZWQgc2luY2UgMTYuMC4wXG4gKi9cbkBDb21wb25lbnQoeyB0ZW1wbGF0ZTogJzxwPjwvcD4nIH0pXG4vLyBlc2xpbnQtZGlzYWJsZS1uZXh0LWxpbmUgQGFuZ3VsYXItZXNsaW50L2NvbXBvbmVudC1jbGFzcy1zdWZmaXhcbmV4cG9ydCBhYnN0cmFjdCBjbGFzcyBEZXByZWNhdGVkQmFzZUVkaXRGb3JtPFQ+IGltcGxlbWVudHMgT25Jbml0LCBPbkRlc3Ryb3ksIENhbkNvbXBvbmVudERlYWN0aXZhdGUge1xuICBzdGF0aWMgcmVhZG9ubHkgRURJVF9GT1JNX0RBVEFfS0VZID0gJ2VkaXRGb3JtVmFsdWVzJztcbiAgcHVibGljIHJlYWRvbmx5IGRlc3Ryb3kkOiBTdWJqZWN0PHZvaWQ+ID0gbmV3IFN1YmplY3QoKTtcbiAgcHVibGljIHN0YXRpYyBmb3JtU2F2ZWRTdWNjZXNzU3ViamVjdDogU3ViamVjdDxib29sZWFuPiA9IG5ldyBTdWJqZWN0KCk7XG4gIGxvZyA9IExvZ2dlckZhY3RvcnkuZ2V0TG9nZ2VyKCk7XG4gIGZvcm1EYXRhTG9hZGVkID0gZmFsc2U7XG4gIGluaXRpYWxEYXRhOiBUO1xuICBkYXRhOiBUO1xuICBmb3JtOiBGb3JtR3JvdXA7XG4gIHByb3RlY3RlZCBpZDogc3RyaW5nO1xuICBwcm90ZWN0ZWQgYWN0aW9uVHlwZTogc3RyaW5nO1xuICBwcm90ZWN0ZWQgZGlydHkgPSBmYWxzZTtcbiAgcHJvdGVjdGVkIHNhdmVkID0gZmFsc2U7XG4gIG5ld1JlY29yZE1vZGUgPSB0cnVlO1xuXG4gIHByb3RlY3RlZCBhYnN0cmFjdCBidWlsZEZvcm0oZm9ybUJ1aWxkZXI6IEZvcm1CdWlsZGVyKTogdm9pZDtcbiAgcHJvdGVjdGVkIGFic3RyYWN0IGRvU2F2ZSh2YWx1ZTogVCk6IHZvaWQgfCBPYnNlcnZhYmxlPFQ+O1xuICBwcm90ZWN0ZWQgYWJzdHJhY3QgZG9Mb2FkKGlkOiBzdHJpbmcsIGFjdGlvblR5cGU/OiBzdHJpbmcpOiB2b2lkIHwgT2JzZXJ2YWJsZTxUPjtcbiAgcHJvdGVjdGVkIGFic3RyYWN0IHNldERhdGEoZGF0YTogVCk6IHZvaWQ7XG4gIHByb3RlY3RlZCBhYnN0cmFjdCBnZXREYXRhKCk6IFQ7XG5cbiAgY29uc3RydWN0b3IoXG4gICAgcHJvdGVjdGVkIGZvcm1CdWlsZGVyOiBGb3JtQnVpbGRlcixcbiAgICBwcm90ZWN0ZWQgY29tbW9uRm9ybVNlcnZpY2VzOiBDb21tb25Gb3JtU2VydmljZXMsXG4gICAgcHJvdGVjdGVkIGFjdGl2YXRlZFJvdXRlPzogQWN0aXZhdGVkUm91dGVcbiAgKSB7fVxuXG4gIG5nT25Jbml0KCk6IHZvaWQge1xuICAgIHRoaXMuYnVpbGRGb3JtKHRoaXMuZm9ybUJ1aWxkZXIpO1xuICAgIGlmICh0aGlzLmlzUmVzdG9yZVVuc2F2ZWREYXRhRW5hYmxlZCkge1xuICAgICAgdGhpcy5mb3JtLnZhbHVlQ2hhbmdlcy5waXBlKHRha2VVbnRpbCh0aGlzLmRlc3Ryb3kkKSwgYXVkaXRUaW1lKDIwMCkpLnN1YnNjcmliZSgoKSA9PiB7XG4gICAgICAgIHRoaXMuc2F2ZUZvcm1EYXRhVG9MUygpO1xuICAgICAgfSk7XG4gICAgfVxuXG4gICAgaWYgKHRoaXMuYWN0aXZhdGVkUm91dGUpIHtcbiAgICAgIHRoaXMuYWN0aXZhdGVkUm91dGUucXVlcnlQYXJhbXMuc3Vic2NyaWJlKChxdWVyeVBhcmFtKSA9PiB7XG4gICAgICAgIGlmIChxdWVyeVBhcmFtLmFjdGlvblR5cGUgIT09IHVuZGVmaW5lZCkge1xuICAgICAgICAgIHRoaXMuYWN0aW9uVHlwZSA9IHF1ZXJ5UGFyYW0uYWN0aW9uVHlwZSBhcyBzdHJpbmc7XG4gICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgdGhpcy5hY3Rpb25UeXBlID0gbnVsbDtcbiAgICAgICAgfVxuICAgICAgfSk7XG4gICAgICB0aGlzLmFjdGl2YXRlZFJvdXRlLnBhcmFtcy5zdWJzY3JpYmUoKHBhcmFtcykgPT4ge1xuICAgICAgICB0aGlzLmlkID0gcGFyYW1zLmlkIGFzIHN0cmluZztcbiAgICAgICAgdGhpcy5mb3JtRGF0YUxvYWRlZCA9IGZhbHNlO1xuICAgICAgICBpZiAodGhpcy5pZCAmJiB0aGlzLmlkICE9PSAnbmV3Jykge1xuICAgICAgICAgIGlmICh0aGlzLmlzUmVzdG9yZVVuc2F2ZWREYXRhRW5hYmxlZCAmJiB0aGlzLmdldEZvcm1EYXRhRnJvbUxTKCkpIHtcbiAgICAgICAgICAgIHRoaXMubG9hZEZvcm1EYXRhRnJvbUxTV2l0aENvbmZpcm1hdGlvbih0cnVlKTtcbiAgICAgICAgICB9IGVsc2Uge1xuICAgICAgICAgICAgdGhpcy5leGVjdXRlRG9Mb2FkKHRoaXMuaWQsIHRoaXMuYWN0aW9uVHlwZSk7XG4gICAgICAgICAgfVxuICAgICAgICB9IGVsc2UgaWYgKHRoaXMuaWQgPT09ICduZXcnKSB7XG4gICAgICAgICAgdGhpcy5pbml0aWFsRGF0YSA9IHRoaXMuY29weU9iamVjdCh0aGlzLmdldERhdGEoKSk7XG4gICAgICAgICAgdGhpcy5zZXRGb3JtU3RhdGUobnVsbCk7XG4gICAgICAgICAgdGhpcy5sb2FkRm9ybURhdGFGcm9tTFNXaXRoQ29uZmlybWF0aW9uKCk7XG4gICAgICAgIH0gZWxzZSBpZiAodGhpcy5mb3JjZURvTG9hZCgpKSB7XG4gICAgICAgICAgdGhpcy5leGVjdXRlRG9Mb2FkKG51bGwsIHRoaXMuYWN0aW9uVHlwZSk7XG4gICAgICAgIH1cbiAgICAgIH0pO1xuICAgIH1cbiAgfVxuXG4gIG5nT25EZXN0cm95KCk6IHZvaWQge1xuICAgIHRoaXMuZGVzdHJveSQubmV4dCgpO1xuICAgIHRoaXMuZGVzdHJveSQudW5zdWJzY3JpYmUoKTtcbiAgICB0aGlzLnJlbW92ZUZvcm1EYXRhRnJvbUxTKCk7XG4gIH1cblxuICAvLyBlc2xpbnQtZGlzYWJsZS1uZXh0LWxpbmUgQHR5cGVzY3JpcHQtZXNsaW50L25vLXVudXNlZC12YXJzXG4gIG9uU3VibWl0KGV2ZW50OiBFdmVudCk6IHZvaWQge1xuICAgIHRoaXMuZm9ybS5tYXJrQWxsQXNUb3VjaGVkKCk7XG4gICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuYXBwTWVzc2FnZXMuY2xlYXJNZXNzYWdlcygpO1xuICAgIGlmICh0aGlzLmZvcm0udmFsaWQpIHtcbiAgICAgIHRoaXMuZXhlY3V0ZURvU2F2ZSh0aGlzLmdldERhdGEoKSk7XG4gICAgfSBlbHNlIHtcbiAgICAgIHRoaXMubG9nLmRlYnVnKHRoaXMuZm9ybS5lcnJvcnMpO1xuICAgICAgaWYgKHRoaXMuZm9ybS5lcnJvcnMpIHtcbiAgICAgICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuYXBwTWVzc2FnZXMuc2hvd0Vycm9yKFxuICAgICAgICAgICcnLFxuICAgICAgICAgIE1lc3NhZ2VSZXNvbHZlci5yZXNvbHZlRXJyb3JUb1N0cmluZyh0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy50cmFuc2xhdGUsIHRoaXMuZm9ybS5lcnJvcnMsIG51bGwsIHRydWUpXG4gICAgICAgICk7XG4gICAgICB9IGVsc2Uge1xuICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy50cmFuc2xhdGVcbiAgICAgICAgICAuZ2V0KCdjb21tb24ubWVzc2FnZS5jb3JyZWN0VmFsaWRhdGlvbkVycm9ycycpXG4gICAgICAgICAgLnN1YnNjcmliZSgodHJhbnNsYXRpb246IHN0cmluZykgPT4ge1xuICAgICAgICAgICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuYXBwTWVzc2FnZXMuc2hvd0Vycm9yKCcnLCB0cmFuc2xhdGlvbiwgJ1ZBTElEQVRJT04nKTtcbiAgICAgICAgICB9KTtcbiAgICAgIH1cbiAgICB9XG4gIH1cblxuICBjYW5EZWFjdGl2YXRlKCk6IE9ic2VydmFibGU8Ym9vbGVhbj4gfCBQcm9taXNlPGJvb2xlYW4+IHwgYm9vbGVhbiB7XG4gICAgaWYgKHRoaXMuaGFzRGF0YUNoYW5nZWQoKSkge1xuICAgICAgcmV0dXJuIG5ldyBPYnNlcnZhYmxlKChvYnNlcnZlcjogT2JzZXJ2ZXI8Ym9vbGVhbj4pID0+IHtcbiAgICAgICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuY29uZmlybWF0aW9uU2VydmljZS5jb25maXJtKHtcbiAgICAgICAgICBtZXNzYWdlOiB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy50cmFuc2xhdGUuaW5zdGFudCgnY29tbW9uLm1lc3NhZ2UuZGlzY2FyZENoYW5nZXMnKSBhcyBzdHJpbmcsXG4gICAgICAgICAgaWNvbjogJ2ZhIGZhLXF1ZXN0aW9uLWNpcmNsZScsXG4gICAgICAgICAgYWNjZXB0OiAoKSA9PiB7XG4gICAgICAgICAgICB0aGlzLnJlbW92ZUZvcm1EYXRhRnJvbUxTKCk7XG4gICAgICAgICAgICBvYnNlcnZlci5uZXh0KHRydWUpO1xuICAgICAgICAgICAgb2JzZXJ2ZXIuY29tcGxldGUoKTtcbiAgICAgICAgICB9LFxuICAgICAgICAgIHJlamVjdDogKCkgPT4ge1xuICAgICAgICAgICAgb2JzZXJ2ZXIubmV4dChmYWxzZSk7XG4gICAgICAgICAgICBvYnNlcnZlci5jb21wbGV0ZSgpO1xuICAgICAgICAgIH0sXG4gICAgICAgIH0pO1xuICAgICAgfSk7XG4gICAgfSBlbHNlIHtcbiAgICAgIHRoaXMucmVtb3ZlRm9ybURhdGFGcm9tTFMoKTtcbiAgICAgIHJldHVybiB0cnVlO1xuICAgIH1cbiAgfVxuXG4gIG9uQ2hhbmdlKCk6IHZvaWQge1xuICAgIHRoaXMuZGlydHkgPSB0cnVlO1xuICAgIHRoaXMuc2F2ZWQgPSBmYWxzZTtcbiAgfVxuXG4gIGRvQ2FuY2VsKCk6IHZvaWQge1xuICAgIGlmIChcbiAgICAgIHRoaXMuaXNXYXJuaW5nRW5hYmxlZCAmJlxuICAgICAgd2luZG93LmNvbmZpcm0odGhpcy5jb21tb25Gb3JtU2VydmljZXMudHJhbnNsYXRlLmluc3RhbnQoJ2NvbW1vbi5tZXNzYWdlLmRpc2NhcmRDaGFuZ2VzJykgYXMgc3RyaW5nKVxuICAgICkge1xuICAgICAgdGhpcy5leGVjdXRlRG9Mb2FkKHRoaXMuaWQpO1xuICAgIH1cbiAgfVxuXG4gIGRvd25sb2FkRmlsZShkYXRhOiBCbG9iLCBmaWxlTmFtZTogc3RyaW5nKTogdm9pZCB7XG4gICAgY29uc3QgbGluayA9IGRvY3VtZW50LmNyZWF0ZUVsZW1lbnQoJ2EnKTtcbiAgICBsaW5rLnN0eWxlLmRpc3BsYXkgPSAnbm9uZSc7XG4gICAgZG9jdW1lbnQuYm9keS5hcHBlbmRDaGlsZChsaW5rKTtcbiAgICBpZiAobGluay5kb3dubG9hZCAhPT0gdW5kZWZpbmVkKSB7XG4gICAgICBsaW5rLnNldEF0dHJpYnV0ZSgnaHJlZicsIFVSTC5jcmVhdGVPYmplY3RVUkwoZGF0YSkpO1xuICAgICAgbGluay5zZXRBdHRyaWJ1dGUoJ2Rvd25sb2FkJywgZmlsZU5hbWUpO1xuICAgICAgbGluay5jbGljaygpO1xuICAgIH1cbiAgICBkb2N1bWVudC5ib2R5LnJlbW92ZUNoaWxkKGxpbmspO1xuICB9XG5cbiAgcHVibGljIG9uU2F2ZVN1Y2Nlc3MoZGF0YTogVCk6IHZvaWQge1xuICAgIHRoaXMubG9nLmRlYnVnKCdvblNhdmVTdWNjZXNzJyk7XG4gICAgdGhpcy5jb21tb25Gb3JtU2VydmljZXMuYXBwTWVzc2FnZXMuc2hvd1N1Y2Nlc3MoJycsIHRoaXMuZ2V0U3VjY2Vzc01lc3NhZ2UoKSk7XG4gICAgdGhpcy5mb3JtLnJlc2V0KCk7XG4gICAgdGhpcy5yZW1vdmVGb3JtRGF0YUZyb21MUygpO1xuICAgIHRoaXMub25Mb2FkU3VjY2VzcyhkYXRhKTtcbiAgICB0aGlzLmRpcnR5ID0gZmFsc2U7XG4gICAgdGhpcy5zYXZlZCA9IHRydWU7XG4gICAgaWYgKHRoaXMuaXNFdmVudEVtaXRFbmFibGVkKSB7XG4gICAgICBEZXByZWNhdGVkQmFzZUVkaXRGb3JtLmZvcm1TYXZlZFN1Y2Nlc3NTdWJqZWN0Lm5leHQodHJ1ZSk7XG4gICAgfVxuICB9XG5cbiAgcHJvdGVjdGVkIGdldFN1Y2Nlc3NNZXNzYWdlKCk6IHN0cmluZyB7XG4gICAgcmV0dXJuIHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnRyYW5zbGF0ZS5pbnN0YW50KCdjb21tb24ubWVzc2FnZS5zYXZlU3VjY2VzcycpIGFzIHN0cmluZztcbiAgfVxuXG4gIHByb3RlY3RlZCBzZXRGb3JtQ29udHJvbERpc2FibGVkKGNvbnRyb2xOYW1lOiBzdHJpbmcsIGRpc2FibGVkOiBib29sZWFuKTogdm9pZCB7XG4gICAgY29uc3QgY29udHJvbCA9IHRoaXMuZm9ybS5jb250cm9sc1tjb250cm9sTmFtZV07XG4gICAgaWYgKGRpc2FibGVkID09PSB0cnVlKSB7XG4gICAgICBjb250cm9sLmRpc2FibGUoKTtcbiAgICB9IGVsc2Uge1xuICAgICAgY29udHJvbC5lbmFibGUoKTtcbiAgICB9XG4gIH1cblxuICAvLyBlc2xpbnQtZGlzYWJsZS1uZXh0LWxpbmUgQHR5cGVzY3JpcHQtZXNsaW50L25vLWVtcHR5LWZ1bmN0aW9uLCBAdHlwZXNjcmlwdC1lc2xpbnQvbm8tdW51c2VkLXZhcnNcbiAgcHJvdGVjdGVkIHNldEZvcm1TdGF0ZShkYXRhOiBUKTogdm9pZCB7fVxuXG4gIHByb3RlY3RlZCBvbkxvYWRTdWNjZXNzKGRhdGE6IFQpOiB2b2lkIHtcbiAgICB0aGlzLmxvZy5kZWJ1Zygnb25Mb2FkU3VjY2VzcycpO1xuICAgIHRoaXMubmV3UmVjb3JkTW9kZSA9IGZhbHNlO1xuICAgIHRoaXMuc2V0Rm9ybVN0YXRlKGRhdGEpO1xuICAgIHRoaXMuc2V0RGF0YShkYXRhKTtcbiAgICB0aGlzLmluaXRpYWxEYXRhID0gdGhpcy5jb3B5T2JqZWN0KHRoaXMuZ2V0RGF0YSgpKTtcbiAgICB0aGlzLmRpcnR5ID0gZmFsc2U7XG4gICAgdGhpcy5mb3JtRGF0YUxvYWRlZCA9IHRydWU7XG4gIH1cblxuICBwcm90ZWN0ZWQgbWFya0Zvcm1GaWVsZHNQcmlzdGluZSgpOiB2b2lkIHtcbiAgICBPYmplY3Qua2V5cyh0aGlzLmZvcm0uY29udHJvbHMpLmZvckVhY2goKGtleSkgPT4ge1xuICAgICAgdGhpcy5mb3JtLmNvbnRyb2xzW2tleV0ubWFya0FzUHJpc3RpbmUoKTtcbiAgICAgIGlmICh0aGlzLmZvcm0uY29udHJvbHNba2V5XSBpbnN0YW5jZW9mIEZvcm1Hcm91cCkge1xuICAgICAgICBPYmplY3Qua2V5cygodGhpcy5mb3JtLmNvbnRyb2xzW2tleV0gYXMgRm9ybUdyb3VwKS5jb250cm9scykuZm9yRWFjaCgoa2V5MikgPT4ge1xuICAgICAgICAgICh0aGlzLmZvcm0uY29udHJvbHNba2V5XSBhcyBGb3JtR3JvdXApLmNvbnRyb2xzW2tleTJdLm1hcmtBc1ByaXN0aW5lKCk7XG4gICAgICAgIH0pO1xuICAgICAgfVxuICAgIH0pO1xuICB9XG5cbiAgcHJvdGVjdGVkIGdldCBpc1dhcm5pbmdFbmFibGVkKCk6IGJvb2xlYW4ge1xuICAgIHJldHVybiB0cnVlO1xuICB9XG5cbiAgcHJvdGVjdGVkIGdldCBpc0V2ZW50RW1pdEVuYWJsZWQoKTogYm9vbGVhbiB7XG4gICAgcmV0dXJuIHRydWU7XG4gIH1cblxuICBwcm90ZWN0ZWQgZ2V0IGlzUmVzdG9yZVVuc2F2ZWREYXRhRW5hYmxlZCgpOiBib29sZWFuIHtcbiAgICByZXR1cm4gZmFsc2U7XG4gIH1cblxuICBwcm90ZWN0ZWQgY29weU9iamVjdChkYXRhOiBUKTogVCB7XG4gICAgcmV0dXJuIEpTT04ucGFyc2UodGhpcy5zdHJpbmdpZnlKc29uRW1wdHlBc051bGxzKGRhdGEpKSBhcyBUO1xuICB9XG5cbiAgcHJvdGVjdGVkIHN0cmluZ2lmeUpzb25FbXB0eUFzTnVsbHMoZGF0YTogVCk6IHN0cmluZyB8IG51bGwge1xuICAgIHJldHVybiBKU09OLnN0cmluZ2lmeShkYXRhLCAoa2V5LCB2YWx1ZTogc3RyaW5nKSA9PiB7XG4gICAgICByZXR1cm4gdmFsdWUgPT09ICcnID8gbnVsbCA6IHZhbHVlO1xuICAgIH0pO1xuICB9XG5cbiAgcHJvdGVjdGVkIGhhc0RhdGFDaGFuZ2VkKCk6IGJvb2xlYW4ge1xuICAgIGNvbnN0IGluaXRpYWxEYXRhID0gSlNPTi5zdHJpbmdpZnkodGhpcy5pbml0aWFsRGF0YSk7XG4gICAgY29uc3QgbmV3RGF0YSA9IHRoaXMuc3RyaW5naWZ5SnNvbkVtcHR5QXNOdWxscyh0aGlzLmdldERhdGEoKSk7XG4gICAgcmV0dXJuICF0aGlzLnNhdmVkICYmIGluaXRpYWxEYXRhICE9PSBuZXdEYXRhO1xuICB9XG5cbiAgcHJvdGVjdGVkIGZvcmNlRG9Mb2FkKCk6IGJvb2xlYW4ge1xuICAgIHJldHVybiBmYWxzZTtcbiAgfVxuXG4gIGJhY2tUb0Jyb3dzZUZvcm0oZm9ybVVybDogc3RyaW5nIHwgc3RyaW5nW10sIHN0YXJ0QXRSb290ID0gdHJ1ZSk6IHZvaWQge1xuICAgIGNvbnN0IG5hdmlnYXRpb25FeHRyYXM6IE5hdmlnYXRpb25FeHRyYXMgPSB7IHN0YXRlOiB7IGtlZXBGaWx0ZXJzOiB0cnVlIH0gfTtcbiAgICBpZiAoc3RhcnRBdFJvb3QpIHtcbiAgICAgIGlmICh0eXBlb2YgZm9ybVVybCA9PT0gJ3N0cmluZycpIHtcbiAgICAgICAgZm9ybVVybCA9IFtgLyR7Zm9ybVVybH1gXTtcbiAgICAgIH0gZWxzZSBpZiAoQXJyYXkuaXNBcnJheShmb3JtVXJsKSkge1xuICAgICAgICBmb3JtVXJsID0gWycvJywgLi4uZm9ybVVybF07XG4gICAgICB9XG4gICAgfVxuICAgIHZvaWQgdGhpcy5jb21tb25Gb3JtU2VydmljZXMucm91dGVyLm5hdmlnYXRlKHR5cGVvZiBmb3JtVXJsID09PSAnc3RyaW5nJyA/IFtmb3JtVXJsXSA6IGZvcm1VcmwsIG5hdmlnYXRpb25FeHRyYXMpO1xuICB9XG5cbiAgcHJvdGVjdGVkIHNhdmVGb3JtRGF0YVRvTFMoKTogdm9pZCB7XG4gICAgaWYgKHRoaXMuZm9ybURhdGFMb2FkZWQpIHtcbiAgICAgIGNvbnN0IGZvcm1EYXRhOiBFZGl0Rm9ybURhdGE8VD4gPSB7XG4gICAgICAgIHVybDogdGhpcy5jb21tb25Gb3JtU2VydmljZXMucm91dGVyLnVybCxcbiAgICAgICAgYWN0aW9uVHlwZTogdGhpcy5hY3Rpb25UeXBlLFxuICAgICAgICBkYXRhOiB0aGlzLmdldERhdGEoKSxcbiAgICAgIH07XG4gICAgICBsb2NhbFN0b3JhZ2Uuc2V0SXRlbShFRElUX0ZPUk1fREFUQV9LRVksIEpTT04uc3RyaW5naWZ5KGZvcm1EYXRhKSk7XG4gICAgfVxuICB9XG5cbiAgcHJvdGVjdGVkIGdldEZvcm1EYXRhRnJvbUxTKCk6IEVkaXRGb3JtRGF0YTxUPiB7XG4gICAgY29uc3QgZm9ybURhdGEgPSBKU09OLnBhcnNlKGxvY2FsU3RvcmFnZS5nZXRJdGVtKEVESVRfRk9STV9EQVRBX0tFWSkpIGFzIEVkaXRGb3JtRGF0YTxUPjtcbiAgICBpZiAoZm9ybURhdGEpIHtcbiAgICAgIGlmIChcbiAgICAgICAgZm9ybURhdGEudXJsID09PSB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5yb3V0ZXIudXJsICYmXG4gICAgICAgIGZvcm1EYXRhLmFjdGlvblR5cGUgPT09IHRoaXMuYWN0aW9uVHlwZSAmJlxuICAgICAgICBmb3JtRGF0YS5kYXRhXG4gICAgICApIHtcbiAgICAgICAgcmV0dXJuIGZvcm1EYXRhO1xuICAgICAgfVxuICAgICAgdGhpcy5yZW1vdmVGb3JtRGF0YUZyb21MUygpO1xuICAgIH1cbiAgICByZXR1cm4gbnVsbDtcbiAgfVxuXG4gIHByb3RlY3RlZCByZW1vdmVGb3JtRGF0YUZyb21MUygpOiB2b2lkIHtcbiAgICBsb2NhbFN0b3JhZ2UucmVtb3ZlSXRlbShFRElUX0ZPUk1fREFUQV9LRVkpO1xuICB9XG5cbiAgcHJvdGVjdGVkIGxvYWRGb3JtRGF0YUZyb21MU1dpdGhDb25maXJtYXRpb24oXG4gICAgZG9Mb2FkSWZSZWplY3RlZCA9IGZhbHNlLFxuICAgIGRpYWxvZ1RleHQgPSAnY29tbW9uLm1lc3NhZ2UucmVzdG9yZVVuc2F2ZWREYXRhJyxcbiAgICB0cmFuc2xhdGUgPSB0cnVlXG4gICk6IHZvaWQge1xuICAgIGlmICh0aGlzLmlzUmVzdG9yZVVuc2F2ZWREYXRhRW5hYmxlZCkge1xuICAgICAgY29uc3QgZm9ybURhdGEgPSB0aGlzLmdldEZvcm1EYXRhRnJvbUxTKCk7XG4gICAgICBpZiAoZm9ybURhdGE/LmRhdGEpIHtcbiAgICAgICAgaWYgKHRyYW5zbGF0ZSkge1xuICAgICAgICAgIHRoaXMuY29tbW9uRm9ybVNlcnZpY2VzLnRyYW5zbGF0ZS5nZXQoZGlhbG9nVGV4dCkuc3Vic2NyaWJlKCh0cmFuc2xhdGlvbjogc3RyaW5nKSA9PiB7XG4gICAgICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5jb25maXJtYXRpb25TZXJ2aWNlLmNvbmZpcm0oe1xuICAgICAgICAgICAgICBtZXNzYWdlOiB0cmFuc2xhdGlvbixcbiAgICAgICAgICAgICAgYWNjZXB0OiAoKSA9PiB7XG4gICAgICAgICAgICAgICAgdGhpcy5zZXREYXRhKGZvcm1EYXRhLmRhdGEpO1xuICAgICAgICAgICAgICAgIHRoaXMuZm9ybURhdGFMb2FkZWQgPSB0cnVlO1xuICAgICAgICAgICAgICB9LFxuICAgICAgICAgICAgICByZWplY3Q6ICgpID0+IHtcbiAgICAgICAgICAgICAgICB0aGlzLnJlbW92ZUZvcm1EYXRhRnJvbUxTKCk7XG4gICAgICAgICAgICAgICAgaWYgKGRvTG9hZElmUmVqZWN0ZWQpIHtcbiAgICAgICAgICAgICAgICAgIHRoaXMuZXhlY3V0ZURvTG9hZCh0aGlzLmlkLCB0aGlzLmFjdGlvblR5cGUpO1xuICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICB0aGlzLmZvcm1EYXRhTG9hZGVkID0gdHJ1ZTtcbiAgICAgICAgICAgICAgICB9XG4gICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICB9KTtcbiAgICAgICAgICB9KTtcbiAgICAgICAgfSBlbHNlIHtcbiAgICAgICAgICB0aGlzLmNvbW1vbkZvcm1TZXJ2aWNlcy5jb25maXJtYXRpb25TZXJ2aWNlLmNvbmZpcm0oe1xuICAgICAgICAgICAgbWVzc2FnZTogZGlhbG9nVGV4dCxcbiAgICAgICAgICAgIGFjY2VwdDogKCkgPT4ge1xuICAgICAgICAgICAgICB0aGlzLnNldERhdGEoZm9ybURhdGEuZGF0YSk7XG4gICAgICAgICAgICB9LFxuICAgICAgICAgICAgcmVqZWN0OiB0aGlzLnJlbW92ZUZvcm1EYXRhRnJvbUxTLmJpbmQodGhpcyksXG4gICAgICAgICAgfSk7XG4gICAgICAgIH1cbiAgICAgIH1cbiAgICB9XG4gIH1cblxuICBwcm90ZWN0ZWQgZXhlY3V0ZURvU2F2ZShkYXRhOiBUKTogdm9pZCB7XG4gICAgKHRoaXMuZG9TYXZlKGRhdGEpIGFzIE9ic2VydmFibGU8VD4pPy5zdWJzY3JpYmUoe1xuICAgICAgbmV4dDogKHJlc3VsdCkgPT4ge1xuICAgICAgICB0aGlzLm9uU2F2ZVN1Y2Nlc3MocmVzdWx0KTtcbiAgICAgIH0sXG4gICAgICBlcnJvcjogKGVycm9yKSA9PiB7XG4gICAgICAgIGxldCBjb25zdHJhaW50TWVzc2FnZXM6IFMyTWVzc2FnZVtdO1xuICAgICAgICBpZiAoZXJyb3IgaW5zdGFuY2VvZiBTMkVycm9yKSB7XG4gICAgICAgICAgY29uc3RyYWludE1lc3NhZ2VzID0gZXJyb3IubWVzc2FnZXM/LmZpbHRlcigobWVzc2FnZSkgPT4gbWVzc2FnZS5rZXkgPT09ICdkYi50YWJsZS51ay5jb25zdHJhaW50Jyk7XG4gICAgICAgICAgaWYgKGNvbnN0cmFpbnRNZXNzYWdlcz8ubGVuZ3RoKSB7XG4gICAgICAgICAgICBjb25zdCB2aW9sYXRlZENvbnN0cmFpbnRzOiBTMlZpb2xhdGVkQ29uc3RyYWludFtdID0gY29uc3RyYWludE1lc3NhZ2VzLm1hcCgobWVzc2FnZSkgPT4gKHtcbiAgICAgICAgICAgICAgbmFtZTogbWVzc2FnZS5pdGVtLFxuICAgICAgICAgICAgICBmaWVsZHM6IG1lc3NhZ2UucGFyYW1zLFxuICAgICAgICAgICAgfSkpO1xuICAgICAgICAgICAgdGhpcy5oYW5kbGVDb25zdHJhaW50c1Jlc3VsdCh2aW9sYXRlZENvbnN0cmFpbnRzKTtcbiAgICAgICAgICB9XG4gICAgICAgIH1cbiAgICAgICAgaWYgKCFjb25zdHJhaW50TWVzc2FnZXM/Lmxlbmd0aCB8fCAhKGVycm9yIGluc3RhbmNlb2YgUzJFcnJvcikpIHtcbiAgICAgICAgICB0aHJvdyBlcnJvcjtcbiAgICAgICAgfVxuICAgICAgfSxcbiAgICB9KTtcbiAgfVxuXG4gIHByb3RlY3RlZCBleGVjdXRlRG9Mb2FkKGlkPzogc3RyaW5nLCBhY3Rpb25UeXBlPzogc3RyaW5nKTogdm9pZCB7XG4gICAgKHRoaXMuZG9Mb2FkKGlkLCBhY3Rpb25UeXBlKSBhcyBPYnNlcnZhYmxlPFQ+KT8uc3Vic2NyaWJlKChyZXN1bHQpID0+IHtcbiAgICAgIHRoaXMub25Mb2FkU3VjY2VzcyhyZXN1bHQpO1xuICAgIH0pO1xuICB9XG5cbiAgcHJvdGVjdGVkIGNoZWNrQ29uc3RyYWludHMoXG4gICAgYXBpRnVuY3Rpb246ICgpID0+IE9ic2VydmFibGU8UzJWaW9sYXRlZENvbnN0cmFpbnRbXT4sXG4gICAgZmllbGRzPzogc3RyaW5nW10sXG4gICAgZmllbGRDb250cm9scz86IFJlY29yZDxzdHJpbmcsIEFic3RyYWN0Q29udHJvbCB8IHN0cmluZz5cbiAgKTogdm9pZCB7XG4gICAgYXBpRnVuY3Rpb24oKS5zdWJzY3JpYmUoKHJlc3BvbnNlKSA9PiB7XG4gICAgICB0aGlzLmhhbmRsZUNvbnN0cmFpbnRzUmVzdWx0KHJlc3BvbnNlLCBmaWVsZHMsIGZpZWxkQ29udHJvbHMpO1xuICAgIH0pO1xuICB9XG5cbiAgcHJvdGVjdGVkIGhhbmRsZUNvbnN0cmFpbnRzUmVzdWx0KFxuICAgIHJlc3VsdDogUzJWaW9sYXRlZENvbnN0cmFpbnRbXSxcbiAgICBmaWVsZHM/OiBzdHJpbmdbXSxcbiAgICBmaWVsZENvbnRyb2xzPzogUmVjb3JkPHN0cmluZywgQWJzdHJhY3RDb250cm9sIHwgc3RyaW5nPlxuICApOiB2b2lkIHtcbiAgICByZXN1bHQuZm9yRWFjaCgoY29uc3RyYWludCkgPT4ge1xuICAgICAgaWYgKGNvbnN0cmFpbnQuZmllbGRzPy5sZW5ndGgpIHtcbiAgICAgICAgY29uc3RyYWludC5maWVsZHNcbiAgICAgICAgICAuZmlsdGVyKChmaWVsZCkgPT4gIWZpZWxkcyB8fCBmaWVsZHMuaW5jbHVkZXMoZmllbGQpKVxuICAgICAgICAgIC5mb3JFYWNoKChmaWVsZCkgPT4ge1xuICAgICAgICAgICAgY29uc3QgY29udHJvbCA9XG4gICAgICAgICAgICAgIGZpZWxkQ29udHJvbHM/LltmaWVsZF0gJiYgZmllbGRDb250cm9scz8uW2ZpZWxkXSBpbnN0YW5jZW9mIEFic3RyYWN0Q29udHJvbFxuICAgICAgICAgICAgICAgID8gKGZpZWxkQ29udHJvbHM/LltmaWVsZF0gYXMgQWJzdHJhY3RDb250cm9sKVxuICAgICAgICAgICAgICAgIDogdGhpcy5mb3JtLmNvbnRyb2xzW2ZpZWxkXTtcbiAgICAgICAgICAgIGlmIChjb250cm9sKSB7XG4gICAgICAgICAgICAgIGNvbnN0IGVycm9ycyA9IGNvbnRyb2wuZXJyb3JzIHx8IHt9O1xuICAgICAgICAgICAgICBlcnJvcnNbY29uc3RyYWludC5uYW1lXSA9IHRydWU7XG4gICAgICAgICAgICAgIGNvbnRyb2wuc2V0RXJyb3JzKGVycm9ycyk7XG4gICAgICAgICAgICB9XG4gICAgICAgICAgfSk7XG4gICAgICB9XG4gICAgfSk7XG4gIH1cbn1cbiJdfQ==