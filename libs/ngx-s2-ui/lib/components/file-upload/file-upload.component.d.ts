/// <reference types="node" />
import { AfterContentInit, DestroyRef, EventEmitter, QueryList, TemplateRef } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';
import { HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { S2UiTranslationsService } from '../../services/s2-ui-translations.service';
import { TemplateDirective } from '../../directives/template.directive';
import * as i0 from "@angular/core";
export interface S2File<T = unknown> {
    name: string;
    type: string;
    date?: Date;
    size: number;
    uploaded?: boolean;
    uploadProgress?: number;
    key?: string | number;
    icon?: string;
    extras?: T;
    file?: File;
}
export interface S2FileUploadError {
    files: S2File[];
    error: Error;
}
export declare class FileUploadComponent implements ControlValueAccessor, AfterContentInit {
    translationsService: S2UiTranslationsService;
    private destroyRef;
    static readonly S2_TEMPLATE_HEADER = "header";
    static readonly S2_TEMPLATE_HEADER_CONTENT = "header-content";
    static readonly S2_TEMPLATE_FILE_INFO = "file-info";
    readonly S2_TEMPLATE_HEADER = "header";
    readonly S2_TEMPLATE_HEADER_CONTENT = "header-content";
    readonly S2_TEMPLATE_FILE_INFO = "file-info";
    accept: string;
    autoUploadMethod: (files: S2File[]) => Observable<HttpEvent<S2File[]>>;
    browseButtonText: string;
    errorDuration: number;
    inputId: string;
    maxFiles: number;
    maxFileSize: number;
    showDate: boolean;
    showDragDropText: boolean | 'responsive';
    showHeaderIcon: boolean | 'responsive';
    clickDownload: EventEmitter<S2File<unknown>>;
    clickRemove: EventEmitter<S2File<unknown>>;
    autoUploadSuccess: EventEmitter<S2File<unknown>[]>;
    autoUploadError: EventEmitter<S2FileUploadError>;
    templatesQueryList: QueryList<TemplateDirective>;
    templates: Record<string, TemplateRef<unknown>>;
    files: S2File[];
    error: 'maxFiles' | 'maxFileSize' | 'accept';
    errorClearTimeout: NodeJS.Timeout;
    disabled: boolean;
    onChange: (value: S2File[]) => void;
    onTouched: () => void;
    constructor(translationsService: S2UiTranslationsService, destroyRef: DestroyRef);
    ngAfterContentInit(): void;
    writeValue(files: S2File[]): void;
    registerOnChange(fn: (value: S2File[]) => void): void;
    registerOnTouched(fn: () => void): void;
    setDisabledState?(isDisabled: boolean): void;
    loadFiles(files: File[]): void;
    autoUploadFiles(files: S2File[]): void;
    handleInputChange(event: Event): void;
    handleDropFile(event: DragEvent): void;
    handleDownloadFile(file: S2File): void;
    handleRemoveFile(index: number): void;
    showError(error: typeof this.error): void;
    isFileTypeAccepted(file: File): boolean;
    updateTemplates(): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<FileUploadComponent, never>;
    static ɵcmp: i0.ɵɵComponentDeclaration<FileUploadComponent, "s2-file-upload", never, { "accept": { "alias": "accept"; "required": false; }; "autoUploadMethod": { "alias": "autoUploadMethod"; "required": false; }; "browseButtonText": { "alias": "browseButtonText"; "required": false; }; "errorDuration": { "alias": "errorDuration"; "required": false; }; "inputId": { "alias": "inputId"; "required": false; }; "maxFiles": { "alias": "maxFiles"; "required": false; }; "maxFileSize": { "alias": "maxFileSize"; "required": false; }; "showDate": { "alias": "showDate"; "required": false; }; "showDragDropText": { "alias": "showDragDropText"; "required": false; }; "showHeaderIcon": { "alias": "showHeaderIcon"; "required": false; }; }, { "clickDownload": "clickDownload"; "clickRemove": "clickRemove"; "autoUploadSuccess": "autoUploadSuccess"; "autoUploadError": "autoUploadError"; }, ["templatesQueryList"], never, true, never>;
}
