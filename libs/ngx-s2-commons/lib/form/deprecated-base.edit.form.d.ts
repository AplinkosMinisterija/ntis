import { OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { CanComponentDeactivate } from '../auth/can-deactivate.guard';
import { S2ViolatedConstraint } from '../model/common.api';
import { CommonFormServices } from './common.form.services';
import * as i0 from "@angular/core";
export declare const EDIT_FORM_DATA_KEY = "editFormValues";
interface EditFormData<T> {
    url: string;
    actionType: string;
    data: T;
}
/**
 * @deprecated since 16.0.0
 */
export declare abstract class DeprecatedBaseEditForm<T> implements OnInit, OnDestroy, CanComponentDeactivate {
    protected formBuilder: FormBuilder;
    protected commonFormServices: CommonFormServices;
    protected activatedRoute?: ActivatedRoute;
    static readonly EDIT_FORM_DATA_KEY = "editFormValues";
    readonly destroy$: Subject<void>;
    static formSavedSuccessSubject: Subject<boolean>;
    log: import("../log/logger.service").Logger;
    formDataLoaded: boolean;
    initialData: T;
    data: T;
    form: FormGroup;
    protected id: string;
    protected actionType: string;
    protected dirty: boolean;
    protected saved: boolean;
    newRecordMode: boolean;
    protected abstract buildForm(formBuilder: FormBuilder): void;
    protected abstract doSave(value: T): void | Observable<T>;
    protected abstract doLoad(id: string, actionType?: string): void | Observable<T>;
    protected abstract setData(data: T): void;
    protected abstract getData(): T;
    constructor(formBuilder: FormBuilder, commonFormServices: CommonFormServices, activatedRoute?: ActivatedRoute);
    ngOnInit(): void;
    ngOnDestroy(): void;
    onSubmit(event: Event): void;
    canDeactivate(): Observable<boolean> | Promise<boolean> | boolean;
    onChange(): void;
    doCancel(): void;
    downloadFile(data: Blob, fileName: string): void;
    onSaveSuccess(data: T): void;
    protected getSuccessMessage(): string;
    protected setFormControlDisabled(controlName: string, disabled: boolean): void;
    protected setFormState(data: T): void;
    protected onLoadSuccess(data: T): void;
    protected markFormFieldsPristine(): void;
    protected get isWarningEnabled(): boolean;
    protected get isEventEmitEnabled(): boolean;
    protected get isRestoreUnsavedDataEnabled(): boolean;
    protected copyObject(data: T): T;
    protected stringifyJsonEmptyAsNulls(data: T): string | null;
    protected hasDataChanged(): boolean;
    protected forceDoLoad(): boolean;
    backToBrowseForm(formUrl: string | string[], startAtRoot?: boolean): void;
    protected saveFormDataToLS(): void;
    protected getFormDataFromLS(): EditFormData<T>;
    protected removeFormDataFromLS(): void;
    protected loadFormDataFromLSWithConfirmation(doLoadIfRejected?: boolean, dialogText?: string, translate?: boolean): void;
    protected executeDoSave(data: T): void;
    protected executeDoLoad(id?: string, actionType?: string): void;
    protected checkConstraints(apiFunction: () => Observable<S2ViolatedConstraint[]>, fields?: string[], fieldControls?: Record<string, AbstractControl | string>): void;
    protected handleConstraintsResult(result: S2ViolatedConstraint[], fields?: string[], fieldControls?: Record<string, AbstractControl | string>): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<DeprecatedBaseEditForm<any>, never>;
    static ɵcmp: i0.ɵɵComponentDeclaration<DeprecatedBaseEditForm<any>, "ng-component", never, {}, {}, never, never, false, never>;
}
export {};
