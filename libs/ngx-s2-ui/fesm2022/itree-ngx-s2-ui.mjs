import { trigger, transition, style, animate, state } from '@angular/animations';
import * as i0 from '@angular/core';
import { Pipe, Injectable, Component, ViewEncapsulation, Directive, Input, EventEmitter, Output, ContentChildren, HostBinding } from '@angular/core';
import * as i2 from '@angular/common';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import * as i3 from '@angular/forms';
import { NG_VALUE_ACCESSOR, FormsModule } from '@angular/forms';
import { HttpEventType } from '@angular/common/http';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Subject, takeUntil } from 'rxjs';

const expandFromTop = (name = 'expandFromTop') => {
    return trigger(name, [
        transition(':enter', [
            style({
                opacity: 0,
                transform: 'scaleY(0)',
                'transform-origin': 'top',
                height: 0,
            }),
            animate('0.15s linear', style({
                opacity: 1,
                transform: 'scaleY(1)',
                height: '*',
            })),
        ]),
        transition(':leave', [
            style({
                opacity: 1,
                transform: 'scaleY(1)',
                'transform-origin': 'top',
                height: '*',
            }),
            animate('0.15s linear', style({ opacity: 0, transform: 'scaleY(0)', height: 0 })),
        ]),
    ]);
};

class BytesToTextPipe {
    static defaultPrecision = 2;
    static defaultUnits = ['B', 'KB', 'MB', 'GB', 'TB', 'PB'];
    transform(bytes, precision = BytesToTextPipe.defaultPrecision, units = BytesToTextPipe.defaultUnits) {
        if (typeof precision !== 'number' || isNaN(precision) || !isFinite(precision) || precision < 0) {
            precision = BytesToTextPipe.defaultPrecision;
        }
        if (!units || units.length < 2) {
            units = BytesToTextPipe.defaultUnits;
        }
        const power = Math.min(Math.round(Math.log(bytes) / Math.log(1024)), units.length - 1);
        // const unitIndex = units[power];
        let unitIndex = power;
        let size = bytes / Math.pow(1024, power); // size in new units
        if (size < 0.5 && unitIndex > 0) {
            size = size * 1024;
            unitIndex = unitIndex - 1;
        }
        const roundMultiplier = precision > 0 ? Math.pow(10, precision) : 1;
        const roundedSize = Math.round(size * roundMultiplier) / roundMultiplier; // keep up to 2 decimals
        return `${roundedSize} ${units[unitIndex]}`;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BytesToTextPipe, deps: [], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: BytesToTextPipe, isStandalone: true, name: "bytesToText" });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BytesToTextPipe, decorators: [{
            type: Pipe,
            args: [{
                    name: 'bytesToText',
                    standalone: true,
                }]
        }] });

const getMimeTypeClass = (mimeType) => {
    return mimeType.substring(0, mimeType.indexOf('/'));
};

class FileTypeToIconPipe {
    static icons = {
        'application/geo+json': 'map',
        'application/json': 'data_object',
        'application/pdf': 'picture_as_pdf',
        'application/zip': 'folder_zip',
        'audio/*': 'music_note',
        'font/*': 'font_download',
        'image/*': 'imagesmode',
        'text/*': 'description',
        'text/css': 'css',
        'text/html': 'html',
        'text/javascript': 'javascript',
        'video/*': 'videocam',
    };
    transform(value, defaultIcon = 'draft') {
        return FileTypeToIconPipe.icons[value] || this.getWildcardType(value) || defaultIcon;
    }
    getWildcardType(value) {
        const wildcardTypeKey = Object.keys(FileTypeToIconPipe.icons)
            .filter((mimeType) => mimeType.endsWith('/*'))
            .find((mimeType) => getMimeTypeClass(mimeType) === getMimeTypeClass(value));
        return wildcardTypeKey && FileTypeToIconPipe.icons[wildcardTypeKey];
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, deps: [], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, isStandalone: true, name: "fileTypeToIcon" });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }, {
            type: Pipe,
            args: [{
                    name: 'fileTypeToIcon',
                    standalone: true,
                }]
        }] });

const getFileExtension = (fileName) => {
    return fileName.includes('.') ? `.${fileName.split('.').pop()}` : undefined;
};

const getUniqueName = (name, usedNames) => {
    if (usedNames.some((usedName) => usedName === name)) {
        const suffixNumber = usedNames.reduce((result, usedName) => {
            if (usedName !== name && usedName?.startsWith(name)) {
                const usedNameEnd = usedName.slice(name.length);
                if (/^ \(\d+\)$/.test(usedNameEnd)) {
                    const usedNameSuffixNumber = parseInt(/\d+/.exec(usedNameEnd)[0]);
                    if (usedNameSuffixNumber >= result) {
                        result = usedNameSuffixNumber + 1;
                    }
                }
            }
            return result;
        }, 1);
        return getUniqueName(`${name} (${suffixNumber})`, usedNames);
    }
    return name;
};

const getUniqueFileName = (name, usedNames) => {
    const nameExtension = getFileExtension(name);
    const nameWithoutExtension = name.slice(0, nameExtension && -nameExtension.length);
    const filteredUsedNamesWithoutExtension = usedNames
        .filter((usedName) => nameExtension ? usedName.toLowerCase().endsWith(nameExtension.toLowerCase()) : !getFileExtension(usedName))
        .map((usedName) => usedName.slice(0, nameExtension && -nameExtension.length));
    return `${getUniqueName(nameWithoutExtension, filteredUsedNamesWithoutExtension)}${nameExtension || ''}`;
};

class IconComponent {
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: IconComponent, deps: [], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: IconComponent, isStandalone: true, selector: "s2-icon", host: { classAttribute: "material-symbols-outlined s2-icon" }, ngImport: i0, template: "<ng-content></ng-content>\r\n", styles: ["@import\"https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined\";.s2-icon{display:inline-block;font-size:inherit;font-weight:inherit}\n"], dependencies: [{ kind: "ngmodule", type: CommonModule }], encapsulation: i0.ViewEncapsulation.None });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: IconComponent, decorators: [{
            type: Component,
            args: [{ selector: 's2-icon', standalone: true, imports: [CommonModule], encapsulation: ViewEncapsulation.None, host: { class: 'material-symbols-outlined s2-icon' }, template: "<ng-content></ng-content>\r\n", styles: ["@import\"https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined\";.s2-icon{display:inline-block;font-size:inherit;font-weight:inherit}\n"] }]
        }] });

class ReplacePipe {
    transform(value, pattern, replacement) {
        return value.replace(pattern, replacement);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ReplacePipe, deps: [], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: ReplacePipe, isStandalone: true, name: "replace" });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: ReplacePipe, decorators: [{
            type: Pipe,
            args: [{
                    name: 'replace',
                    standalone: true,
                }]
        }] });

class S2UiSettingsService {
    settings = {
        dateFormat: 'yyyy-MM-dd',
        dateTimeFormat: 'yyyy-MM-dd HH:mm:ss',
    };
    setSettings(settings) {
        this.settings = { ...this.settings, ...settings };
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiSettingsService, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiSettingsService, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiSettingsService, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }] });

class S2DatePipe {
    datePipe;
    settingsService;
    constructor(datePipe, settingsService) {
        this.datePipe = datePipe;
        this.settingsService = settingsService;
    }
    transform(value, showTime = false, ...options) {
        return this.datePipe.transform(value, this.settingsService.settings[showTime ? 'dateTimeFormat' : 'dateFormat'], ...options);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2DatePipe, deps: [{ token: i2.DatePipe }, { token: S2UiSettingsService }], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: S2DatePipe, isStandalone: true, name: "s2Date" });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2DatePipe, decorators: [{
            type: Pipe,
            args: [{
                    name: 's2Date',
                    standalone: true,
                }]
        }], ctorParameters: function () { return [{ type: i2.DatePipe }, { type: S2UiSettingsService }]; } });

class TemplateDirective {
    template;
    s2Template = '';
    s2TemplateExtras;
    constructor(template) {
        this.template = template;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: TemplateDirective, deps: [{ token: i0.TemplateRef }], target: i0.ɵɵFactoryTarget.Directive });
    static ɵdir = i0.ɵɵngDeclareDirective({ minVersion: "14.0.0", version: "16.0.2", type: TemplateDirective, isStandalone: true, selector: "[s2Template]", inputs: { s2Template: "s2Template", s2TemplateExtras: "s2TemplateExtras" }, ngImport: i0 });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: TemplateDirective, decorators: [{
            type: Directive,
            args: [{
                    selector: '[s2Template]',
                    standalone: true,
                }]
        }], ctorParameters: function () { return [{ type: i0.TemplateRef }]; }, propDecorators: { s2Template: [{
                type: Input
            }], s2TemplateExtras: [{
                type: Input
            }] } });

class S2UiTranslationsService {
    translations = {
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
    setTranslations(translations) {
        this.translations = { ...this.translations, ...translations };
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiTranslationsService, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiTranslationsService, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiTranslationsService, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }] });

class FileUploadComponent {
    translationsService;
    destroyRef;
    static S2_TEMPLATE_HEADER = 'header';
    static S2_TEMPLATE_HEADER_CONTENT = 'header-content';
    static S2_TEMPLATE_FILE_INFO = 'file-info';
    S2_TEMPLATE_HEADER = FileUploadComponent.S2_TEMPLATE_HEADER;
    S2_TEMPLATE_HEADER_CONTENT = FileUploadComponent.S2_TEMPLATE_HEADER_CONTENT;
    S2_TEMPLATE_FILE_INFO = FileUploadComponent.S2_TEMPLATE_FILE_INFO;
    accept;
    autoUploadMethod;
    browseButtonText;
    errorDuration = 5000;
    inputId;
    maxFiles = 5;
    maxFileSize = 10485760;
    showDate = true;
    showDragDropText = 'responsive';
    showHeaderIcon = 'responsive';
    clickDownload = new EventEmitter();
    clickRemove = new EventEmitter();
    autoUploadSuccess = new EventEmitter();
    autoUploadError = new EventEmitter();
    templatesQueryList;
    templates = {};
    files = [];
    error;
    errorClearTimeout;
    disabled = false;
    onChange;
    onTouched;
    constructor(translationsService, destroyRef) {
        this.translationsService = translationsService;
        this.destroyRef = destroyRef;
    }
    ngAfterContentInit() {
        this.updateTemplates();
        this.templatesQueryList.changes.pipe(takeUntilDestroyed(this.destroyRef)).subscribe(() => {
            this.updateTemplates();
        });
    }
    writeValue(files) {
        if (Array.isArray(files)) {
            this.files = files;
        }
        else {
            this.files = [];
        }
    }
    registerOnChange(fn) {
        this.onChange = fn;
    }
    registerOnTouched(fn) {
        this.onTouched = fn;
    }
    setDisabledState(isDisabled) {
        this.disabled = isDisabled;
    }
    loadFiles(files) {
        if (files.length) {
            if (files.length > this.maxFiles - this.files.length) {
                this.showError('maxFiles');
            }
            else if (typeof this.maxFileSize === 'number' && files.some((file) => file.size > this.maxFileSize)) {
                this.showError('maxFileSize');
            }
            else if (files.some((file) => !this.isFileTypeAccepted(file))) {
                this.showError('accept');
            }
            else {
                this.showError(undefined);
                const s2Files = files.map((file) => ({
                    name: getUniqueFileName(file.name, this.files.map((file) => file.name)),
                    type: file.type,
                    size: file.size,
                    uploaded: false,
                    date: new Date(),
                    file,
                }));
                this.files = [...this.files, ...s2Files];
                this.autoUploadFiles(s2Files);
                this.onChange?.(this.files);
            }
        }
    }
    autoUploadFiles(files) {
        this.autoUploadMethod?.(files)
            .pipe(catchError((error) => {
            files?.forEach((file) => {
                file.uploadProgress = undefined;
            });
            this.onChange?.(this.files);
            this.autoUploadError.emit({ files, error: error });
            throw error;
        }))
            .subscribe({
            next: (event) => {
                if (event.type === HttpEventType.UploadProgress) {
                    const progress = Math.round((event.loaded * 100) / event.total);
                    files?.forEach((file) => {
                        file.uploadProgress = progress;
                    });
                }
                else if (event.type === HttpEventType.Response) {
                    if (event.status >= 200 && event.status < 300) {
                        files?.forEach((file) => {
                            file.uploadProgress = undefined;
                            file.uploaded = true;
                            const receivedFile = event.body?.find((responseFile) => responseFile?.name === file.name);
                            if (receivedFile) {
                                Object.assign(file, receivedFile);
                            }
                        });
                        this.onChange?.(this.files);
                        this.autoUploadSuccess.emit(files);
                    }
                    else {
                        throw new Error(`HttpResponse status ${event.status}`);
                    }
                }
            },
        });
    }
    handleInputChange(event) {
        event.preventDefault();
        if (!this.disabled) {
            this.onTouched?.();
            this.loadFiles(Array.from(event.target.files));
        }
    }
    handleDropFile(event) {
        event.preventDefault();
        if (!this.disabled) {
            this.onTouched?.();
            this.loadFiles(Array.from(event.dataTransfer.files));
        }
    }
    handleDownloadFile(file) {
        this.clickDownload.emit(file);
    }
    handleRemoveFile(index) {
        this.clickRemove.emit(this.files[index]);
        this.files.splice(index, 1);
        this.onChange?.(this.files);
    }
    showError(error) {
        clearTimeout(this.errorClearTimeout);
        this.error = error;
        if (error) {
            this.errorClearTimeout = setTimeout(() => {
                this.error = undefined;
            }, this.errorDuration);
        }
    }
    isFileTypeAccepted(file) {
        if (!this.accept || this.accept === '*') {
            return true;
        }
        const acceptArray = this.accept
            .split(',')
            .map((type) => type.trim())
            .filter((type) => type);
        return acceptArray.some((acceptableType) => {
            if (acceptableType.indexOf('*') !== -1) {
                return getMimeTypeClass(file.type) === getMimeTypeClass(acceptableType);
            }
            else {
                return (file.type === acceptableType || getFileExtension(file.name).toLowerCase() === acceptableType.toLowerCase());
            }
        });
    }
    updateTemplates() {
        Object.keys(this.templates).forEach((templateKey) => {
            if (!this.templatesQueryList.some((template) => template.s2Template === templateKey)) {
                delete this.templates[templateKey];
            }
        });
        this.templatesQueryList.forEach((template) => {
            this.templates[template.s2Template] = template.template;
        });
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileUploadComponent, deps: [{ token: S2UiTranslationsService }, { token: i0.DestroyRef }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: FileUploadComponent, isStandalone: true, selector: "s2-file-upload", inputs: { accept: "accept", autoUploadMethod: "autoUploadMethod", browseButtonText: "browseButtonText", errorDuration: "errorDuration", inputId: "inputId", maxFiles: "maxFiles", maxFileSize: "maxFileSize", showDate: "showDate", showDragDropText: "showDragDropText", showHeaderIcon: "showHeaderIcon" }, outputs: { clickDownload: "clickDownload", clickRemove: "clickRemove", autoUploadSuccess: "autoUploadSuccess", autoUploadError: "autoUploadError" }, host: { classAttribute: "s2-file-upload" }, providers: [
            {
                provide: NG_VALUE_ACCESSOR,
                multi: true,
                useExisting: FileUploadComponent,
            },
        ], queries: [{ propertyName: "templatesQueryList", predicate: TemplateDirective }], ngImport: i0, template: "<input\r\n  class=\"s2-file-upload__input\"\r\n  type=\"file\"\r\n  [id]=\"inputId\"\r\n  (change)=\"handleInputChange($event)\"\r\n  [multiple]=\"maxFiles > 1 && files.length < (maxFiles-1)\"\r\n  [accept]=\"accept\"\r\n  [disabled]=\"disabled\"\r\n  #inputFile\r\n/>\r\n\r\n<ng-template *ngIf=\"templates[S2_TEMPLATE_HEADER]\" [ngTemplateOutlet]=\"templates[S2_TEMPLATE_HEADER]\" />\r\n\r\n<div\r\n  *ngIf=\"!templates[S2_TEMPLATE_HEADER]\"\r\n  class=\"s2-file-upload-header\"\r\n  [ngClass]=\"{'s2-file-upload-header--disabled': disabled || files.length >= maxFiles}\"\r\n  (dragover)=\"$event.preventDefault(); $event.stopPropagation()\"\r\n  (dragleave)=\"$event.preventDefault(); $event.stopPropagation()\"\r\n  (drop)=\"handleDropFile($event)\"\r\n>\r\n  <ng-template\r\n    *ngIf=\"templates[S2_TEMPLATE_HEADER_CONTENT]\"\r\n    [ngTemplateOutlet]=\"templates[S2_TEMPLATE_HEADER_CONTENT]\"\r\n  />\r\n\r\n  <ng-container *ngIf=\"!templates[S2_TEMPLATE_HEADER_CONTENT]\">\r\n    <s2-icon\r\n      *ngIf=\"showHeaderIcon\"\r\n      class=\"s2-file-upload-header__icon\"\r\n      [ngClass]=\"{'s2-file-upload-header__icon--responsive': showHeaderIcon === 'responsive'}\"\r\n      >file_upload</s2-icon\r\n    >\r\n    <div class=\"s2-file-upload-header__content-wrapper\">\r\n      <ng-container *ngIf=\"!error\">\r\n        <ng-container *ngIf=\"showDragDropText\">\r\n          <span\r\n            class=\"s2-file-upload-header__text\"\r\n            [ngClass]=\"{'s2-file-upload-header__text--responsive': showDragDropText === 'responsive'}\"\r\n            >{{ translationsService.translations.action.dragFilesHere }}</span\r\n          >\r\n          <span\r\n            class=\"s2-file-upload-header__text\"\r\n            [ngClass]=\"{'s2-file-upload-header__text--responsive': showDragDropText === 'responsive'}\"\r\n            >{{ translationsService.translations.general.or | lowercase }}</span\r\n          >\r\n        </ng-container>\r\n        <button\r\n          type=\"button\"\r\n          class=\"s2-file-upload-header__choose\"\r\n          [disabled]=\"disabled || files.length >= maxFiles\"\r\n          (click)=\"inputFile.click()\"\r\n        >\r\n          {{ browseButtonText === undefined ? translationsService.translations.action.browse : browseButtonText }}\r\n        </button>\r\n      </ng-container>\r\n      <span *ngIf=\"error\" class=\"s2-file-upload-header__error\">\r\n        <ng-container\r\n          *ngIf=\"error === 'accept'\"\r\n          >{{ translationsService.translations.errorMsg.invalidFileFormat | replace:'{0}':accept }}</ng-container\r\n        >\r\n        <ng-container\r\n          *ngIf=\"error === 'maxFiles'\"\r\n          >{{ translationsService.translations.errorMsg.maxFilesExceed | replace:'{0}':maxFiles+'' }}</ng-container\r\n        >\r\n        <ng-container\r\n          *ngIf=\"error === 'maxFileSize'\"\r\n          >{{ translationsService.translations.errorMsg.maxFileSizeExceed | replace:'{0}':(maxFileSize | bytesToText)\r\n          }}</ng-container\r\n        >\r\n      </span>\r\n    </div>\r\n  </ng-container>\r\n</div>\r\n\r\n<ul class=\"s2-uploaded-files\">\r\n  <li *ngFor=\"let file of files; index as i\" class=\"s2-uploaded-file\" @expandFromTop>\r\n    <div class=\"s2-uploaded-file__icon-wrapper\">\r\n      <s2-icon\r\n        class=\"s2-uploaded-file__icon\"\r\n        [ngClass]=\"{'s2-uploaded-file__icon--uploaded': file.uploaded}\"\r\n        >{{ file.icon || (file.type | fileTypeToIcon) }}</s2-icon\r\n      >\r\n    </div>\r\n    <ng-container *ngTemplateOutlet=\"templates[S2_TEMPLATE_FILE_INFO]; context: {$implicit: file, index: i}\" />\r\n    <div *ngIf=\"!templates[S2_TEMPLATE_FILE_INFO]\" class=\"s2-uploaded-file__info\">\r\n      <span *ngIf=\"!file.uploaded\" class=\"s2-uploaded-file__info-name\" [title]=\"file.name\">{{ file.name }}</span>\r\n      <button\r\n        *ngIf=\"file.uploaded\"\r\n        type=\"button\"\r\n        class=\"s2-uploaded-file__info-name\"\r\n        [ngClass]=\"{'s2-uploaded-file__info-name--downloadable': true}\"\r\n        [title]=\"file.name\"\r\n        (click)=\"handleDownloadFile(file)\"\r\n      >\r\n        {{ file.name }}\r\n      </button>\r\n      <div\r\n        *ngIf=\"!file.uploaded && (file.uploadProgress || file.uploadProgress === 0)\"\r\n        class=\"s2-uploaded-file__info-progress\"\r\n      >\r\n        <div class=\"s2-uploaded-file__info-progress-background\">\r\n          <div\r\n            class=\"s2-uploaded-file__info-progress-line\"\r\n            [ngStyle]=\"{'width.%': file.uploadProgress}\"\r\n            role=\"progressbar\"\r\n            aria-valuemin=\"0\"\r\n            aria-valuemax=\"100\"\r\n            [attr.aria-valuenow]=\"file.uploadProgress\"\r\n            [attr.aria-label]=\"file.name\"\r\n          ></div>\r\n        </div>\r\n      </div>\r\n      <span\r\n        *ngIf=\"file.uploaded || (!file.uploadProgress && file.uploadProgress !== 0)\"\r\n        class=\"s2-uploaded-file__info-details\"\r\n      >\r\n        <span *ngIf=\"showDate && file.date\" class=\"s2-uploaded-file__info-details-date\">\r\n          {{ file.date | s2Date }}\r\n        </span>\r\n        <span\r\n          class=\"s2-uploaded-file__info-details-size\"\r\n          [ngClass]=\"{'s2-uploaded-file__info-details-size--uploaded': file.uploaded}\"\r\n        >\r\n          {{ file.size | bytesToText }} <s2-icon *ngIf=\"file.uploaded\">check</s2-icon>\r\n        </span>\r\n      </span>\r\n    </div>\r\n    <button\r\n      type=\"button\"\r\n      class=\"s2-uploaded-file__remove\"\r\n      (click)=\"handleRemoveFile(i)\"\r\n      [disabled]=\"!file.uploaded && (file.uploadProgress || file.uploadProgress === 0)\"\r\n    >\r\n      <s2-icon class=\"s2-uploaded-file__remove-icon\">{{ file.uploaded ? 'delete' : 'close' }}</s2-icon>\r\n    </button>\r\n  </li>\r\n</ul>\r\n", styles: [".s2-file-upload{display:block;border-radius:.375rem;border-width:2px;--tw-border-opacity: 1;border-color:hsl(var(--s2-color-neutral-200) / var(--tw-border-opacity));padding:.5rem;container-type:inline-size}.s2-file-upload__input{display:none}.s2-file-upload .s2-file-upload-header{display:flex;flex-direction:column;align-items:center;justify-content:center;row-gap:.25rem;border-radius:.375rem;border-width:1px;border-style:dashed;--tw-border-opacity: 1;border-color:hsl(var(--s2-color-neutral-300) / var(--tw-border-opacity));padding-top:.5rem;padding-bottom:.5rem}.s2-file-upload .s2-file-upload-header__icon{font-size:var(--s2-font-size-4xl);line-height:var(--s2-font-size-4xl-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header__icon--responsive{display:none}@container (min-width: 24rem){.s2-file-upload .s2-file-upload-header__icon--responsive{display:inline-block}}.s2-file-upload .s2-file-upload-header__content-wrapper{display:flex;flex-direction:column;align-items:center;justify-content:center;column-gap:.375rem;row-gap:.125rem;padding-top:.125rem;padding-bottom:.125rem}@container (min-width: 24rem){.s2-file-upload .s2-file-upload-header__content-wrapper{flex-direction:row}}.s2-file-upload .s2-file-upload-header__text{display:inline}.s2-file-upload .s2-file-upload-header__text--responsive{display:none}@container (min-width: 24rem){.s2-file-upload .s2-file-upload-header__text--responsive{display:inline}}.s2-file-upload .s2-file-upload-header__choose{font-weight:500;--tw-text-opacity: 1;color:hsl(var(--s2-color-primary-500) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header__choose:hover{text-decoration-line:underline}.s2-file-upload .s2-file-upload-header__choose:disabled{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header__choose:disabled:hover{text-decoration-line:none}.s2-file-upload .s2-file-upload-header__error{text-align:center;--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-700) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header--disabled{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-neutral-050) / var(--tw-bg-opacity));--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header--disabled .s2-file-upload__icon{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-300) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-files{display:flex;flex-direction:column;gap:.25rem}.s2-file-upload .s2-uploaded-file{display:flex;align-items:center;gap:.5rem;overflow:hidden;border-bottom-width:1px;--tw-border-opacity: 1;border-color:hsl(var(--s2-color-neutral-200) / var(--tw-border-opacity));padding:.25rem}.s2-file-upload .s2-uploaded-file:first-child{margin-top:.5rem}.s2-file-upload .s2-uploaded-file:last-child{border-bottom-width:0px}.s2-file-upload .s2-uploaded-file__icon-wrapper{display:flex;height:2rem;width:2rem;flex-shrink:0;align-items:center;justify-content:center;border-radius:.375rem;--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-neutral-100) / var(--tw-bg-opacity))}@container (min-width: 24rem){.s2-file-upload .s2-uploaded-file__icon-wrapper{height:2.5rem;width:2.5rem}}.s2-file-upload .s2-uploaded-file__icon{flex-shrink:0;font-size:var(--s2-font-size-2xl);line-height:1;--tw-text-opacity: 1;color:hsl(var(--s2-color-text-500) / var(--tw-text-opacity))}@container (min-width: 24rem){.s2-file-upload .s2-uploaded-file__icon{font-size:var(--s2-font-size-3xl);line-height:1}}.s2-file-upload .s2-uploaded-file__icon--uploaded{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-600) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info{display:flex;flex-grow:1;flex-direction:column;overflow:hidden}.s2-file-upload .s2-uploaded-file__info-name{display:inline-block;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;text-align:left;--tw-text-opacity: 1;color:hsl(var(--s2-color-text-700) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-name--downloadable{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-900) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-name--downloadable:hover{text-decoration-line:underline}.s2-file-upload .s2-uploaded-file__info-progress{display:flex;width:100%;max-width:24rem;padding-top:.375rem;padding-bottom:.375rem}.s2-file-upload .s2-uploaded-file__info-progress-background{display:flex;height:.5rem;flex-grow:1;overflow:hidden;border-radius:9999px;--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-neutral-100) / var(--tw-bg-opacity))}.s2-file-upload .s2-uploaded-file__info-progress-line{height:100%;--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-primary-500) / var(--tw-bg-opacity));--tw-shadow: 0 4px 6px -1px rgb(0 0 0 / .1), 0 2px 4px -2px rgb(0 0 0 / .1);--tw-shadow-colored: 0 4px 6px -1px var(--tw-shadow-color), 0 2px 4px -2px var(--tw-shadow-color);box-shadow:var(--tw-ring-offset-shadow, 0 0 #0000),var(--tw-ring-shadow, 0 0 #0000),var(--tw-shadow);transition-property:all;transition-timing-function:cubic-bezier(.4,0,.2,1);transition-duration:.15s}.s2-file-upload .s2-uploaded-file__info-details{display:flex;flex-wrap:wrap;column-gap:.5rem}.s2-file-upload .s2-uploaded-file__info-details-date{font-size:var(--s2-font-size-sm);line-height:var(--s2-font-size-sm-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-details-size{font-size:var(--s2-font-size-sm);line-height:var(--s2-font-size-sm-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-500) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-details-size--uploaded{--tw-text-opacity: 1;color:hsl(var(--s2-color-success-500) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove{margin-left:auto;flex-shrink:0;padding:.25rem;--tw-text-opacity: 1;color:hsl(var(--s2-color-text-700) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove:hover{--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-700) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove:disabled{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-300) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove-icon{font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height)}\n"], dependencies: [{ kind: "ngmodule", type: CommonModule }, { kind: "directive", type: i2.NgClass, selector: "[ngClass]", inputs: ["class", "ngClass"] }, { kind: "directive", type: i2.NgForOf, selector: "[ngFor][ngForOf]", inputs: ["ngForOf", "ngForTrackBy", "ngForTemplate"] }, { kind: "directive", type: i2.NgIf, selector: "[ngIf]", inputs: ["ngIf", "ngIfThen", "ngIfElse"] }, { kind: "directive", type: i2.NgTemplateOutlet, selector: "[ngTemplateOutlet]", inputs: ["ngTemplateOutletContext", "ngTemplateOutlet", "ngTemplateOutletInjector"] }, { kind: "directive", type: i2.NgStyle, selector: "[ngStyle]", inputs: ["ngStyle"] }, { kind: "pipe", type: i2.LowerCasePipe, name: "lowercase" }, { kind: "component", type: IconComponent, selector: "s2-icon" }, { kind: "pipe", type: BytesToTextPipe, name: "bytesToText" }, { kind: "pipe", type: ReplacePipe, name: "replace" }, { kind: "pipe", type: S2DatePipe, name: "s2Date" }, { kind: "pipe", type: FileTypeToIconPipe, name: "fileTypeToIcon" }], animations: [expandFromTop()], encapsulation: i0.ViewEncapsulation.None });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileUploadComponent, decorators: [{
            type: Component,
            args: [{ selector: 's2-file-upload', standalone: true, imports: [CommonModule, IconComponent, BytesToTextPipe, ReplacePipe, S2DatePipe, FileTypeToIconPipe], encapsulation: ViewEncapsulation.None, host: { class: 's2-file-upload' }, providers: [
                        {
                            provide: NG_VALUE_ACCESSOR,
                            multi: true,
                            useExisting: FileUploadComponent,
                        },
                    ], animations: [expandFromTop()], template: "<input\r\n  class=\"s2-file-upload__input\"\r\n  type=\"file\"\r\n  [id]=\"inputId\"\r\n  (change)=\"handleInputChange($event)\"\r\n  [multiple]=\"maxFiles > 1 && files.length < (maxFiles-1)\"\r\n  [accept]=\"accept\"\r\n  [disabled]=\"disabled\"\r\n  #inputFile\r\n/>\r\n\r\n<ng-template *ngIf=\"templates[S2_TEMPLATE_HEADER]\" [ngTemplateOutlet]=\"templates[S2_TEMPLATE_HEADER]\" />\r\n\r\n<div\r\n  *ngIf=\"!templates[S2_TEMPLATE_HEADER]\"\r\n  class=\"s2-file-upload-header\"\r\n  [ngClass]=\"{'s2-file-upload-header--disabled': disabled || files.length >= maxFiles}\"\r\n  (dragover)=\"$event.preventDefault(); $event.stopPropagation()\"\r\n  (dragleave)=\"$event.preventDefault(); $event.stopPropagation()\"\r\n  (drop)=\"handleDropFile($event)\"\r\n>\r\n  <ng-template\r\n    *ngIf=\"templates[S2_TEMPLATE_HEADER_CONTENT]\"\r\n    [ngTemplateOutlet]=\"templates[S2_TEMPLATE_HEADER_CONTENT]\"\r\n  />\r\n\r\n  <ng-container *ngIf=\"!templates[S2_TEMPLATE_HEADER_CONTENT]\">\r\n    <s2-icon\r\n      *ngIf=\"showHeaderIcon\"\r\n      class=\"s2-file-upload-header__icon\"\r\n      [ngClass]=\"{'s2-file-upload-header__icon--responsive': showHeaderIcon === 'responsive'}\"\r\n      >file_upload</s2-icon\r\n    >\r\n    <div class=\"s2-file-upload-header__content-wrapper\">\r\n      <ng-container *ngIf=\"!error\">\r\n        <ng-container *ngIf=\"showDragDropText\">\r\n          <span\r\n            class=\"s2-file-upload-header__text\"\r\n            [ngClass]=\"{'s2-file-upload-header__text--responsive': showDragDropText === 'responsive'}\"\r\n            >{{ translationsService.translations.action.dragFilesHere }}</span\r\n          >\r\n          <span\r\n            class=\"s2-file-upload-header__text\"\r\n            [ngClass]=\"{'s2-file-upload-header__text--responsive': showDragDropText === 'responsive'}\"\r\n            >{{ translationsService.translations.general.or | lowercase }}</span\r\n          >\r\n        </ng-container>\r\n        <button\r\n          type=\"button\"\r\n          class=\"s2-file-upload-header__choose\"\r\n          [disabled]=\"disabled || files.length >= maxFiles\"\r\n          (click)=\"inputFile.click()\"\r\n        >\r\n          {{ browseButtonText === undefined ? translationsService.translations.action.browse : browseButtonText }}\r\n        </button>\r\n      </ng-container>\r\n      <span *ngIf=\"error\" class=\"s2-file-upload-header__error\">\r\n        <ng-container\r\n          *ngIf=\"error === 'accept'\"\r\n          >{{ translationsService.translations.errorMsg.invalidFileFormat | replace:'{0}':accept }}</ng-container\r\n        >\r\n        <ng-container\r\n          *ngIf=\"error === 'maxFiles'\"\r\n          >{{ translationsService.translations.errorMsg.maxFilesExceed | replace:'{0}':maxFiles+'' }}</ng-container\r\n        >\r\n        <ng-container\r\n          *ngIf=\"error === 'maxFileSize'\"\r\n          >{{ translationsService.translations.errorMsg.maxFileSizeExceed | replace:'{0}':(maxFileSize | bytesToText)\r\n          }}</ng-container\r\n        >\r\n      </span>\r\n    </div>\r\n  </ng-container>\r\n</div>\r\n\r\n<ul class=\"s2-uploaded-files\">\r\n  <li *ngFor=\"let file of files; index as i\" class=\"s2-uploaded-file\" @expandFromTop>\r\n    <div class=\"s2-uploaded-file__icon-wrapper\">\r\n      <s2-icon\r\n        class=\"s2-uploaded-file__icon\"\r\n        [ngClass]=\"{'s2-uploaded-file__icon--uploaded': file.uploaded}\"\r\n        >{{ file.icon || (file.type | fileTypeToIcon) }}</s2-icon\r\n      >\r\n    </div>\r\n    <ng-container *ngTemplateOutlet=\"templates[S2_TEMPLATE_FILE_INFO]; context: {$implicit: file, index: i}\" />\r\n    <div *ngIf=\"!templates[S2_TEMPLATE_FILE_INFO]\" class=\"s2-uploaded-file__info\">\r\n      <span *ngIf=\"!file.uploaded\" class=\"s2-uploaded-file__info-name\" [title]=\"file.name\">{{ file.name }}</span>\r\n      <button\r\n        *ngIf=\"file.uploaded\"\r\n        type=\"button\"\r\n        class=\"s2-uploaded-file__info-name\"\r\n        [ngClass]=\"{'s2-uploaded-file__info-name--downloadable': true}\"\r\n        [title]=\"file.name\"\r\n        (click)=\"handleDownloadFile(file)\"\r\n      >\r\n        {{ file.name }}\r\n      </button>\r\n      <div\r\n        *ngIf=\"!file.uploaded && (file.uploadProgress || file.uploadProgress === 0)\"\r\n        class=\"s2-uploaded-file__info-progress\"\r\n      >\r\n        <div class=\"s2-uploaded-file__info-progress-background\">\r\n          <div\r\n            class=\"s2-uploaded-file__info-progress-line\"\r\n            [ngStyle]=\"{'width.%': file.uploadProgress}\"\r\n            role=\"progressbar\"\r\n            aria-valuemin=\"0\"\r\n            aria-valuemax=\"100\"\r\n            [attr.aria-valuenow]=\"file.uploadProgress\"\r\n            [attr.aria-label]=\"file.name\"\r\n          ></div>\r\n        </div>\r\n      </div>\r\n      <span\r\n        *ngIf=\"file.uploaded || (!file.uploadProgress && file.uploadProgress !== 0)\"\r\n        class=\"s2-uploaded-file__info-details\"\r\n      >\r\n        <span *ngIf=\"showDate && file.date\" class=\"s2-uploaded-file__info-details-date\">\r\n          {{ file.date | s2Date }}\r\n        </span>\r\n        <span\r\n          class=\"s2-uploaded-file__info-details-size\"\r\n          [ngClass]=\"{'s2-uploaded-file__info-details-size--uploaded': file.uploaded}\"\r\n        >\r\n          {{ file.size | bytesToText }} <s2-icon *ngIf=\"file.uploaded\">check</s2-icon>\r\n        </span>\r\n      </span>\r\n    </div>\r\n    <button\r\n      type=\"button\"\r\n      class=\"s2-uploaded-file__remove\"\r\n      (click)=\"handleRemoveFile(i)\"\r\n      [disabled]=\"!file.uploaded && (file.uploadProgress || file.uploadProgress === 0)\"\r\n    >\r\n      <s2-icon class=\"s2-uploaded-file__remove-icon\">{{ file.uploaded ? 'delete' : 'close' }}</s2-icon>\r\n    </button>\r\n  </li>\r\n</ul>\r\n", styles: [".s2-file-upload{display:block;border-radius:.375rem;border-width:2px;--tw-border-opacity: 1;border-color:hsl(var(--s2-color-neutral-200) / var(--tw-border-opacity));padding:.5rem;container-type:inline-size}.s2-file-upload__input{display:none}.s2-file-upload .s2-file-upload-header{display:flex;flex-direction:column;align-items:center;justify-content:center;row-gap:.25rem;border-radius:.375rem;border-width:1px;border-style:dashed;--tw-border-opacity: 1;border-color:hsl(var(--s2-color-neutral-300) / var(--tw-border-opacity));padding-top:.5rem;padding-bottom:.5rem}.s2-file-upload .s2-file-upload-header__icon{font-size:var(--s2-font-size-4xl);line-height:var(--s2-font-size-4xl-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header__icon--responsive{display:none}@container (min-width: 24rem){.s2-file-upload .s2-file-upload-header__icon--responsive{display:inline-block}}.s2-file-upload .s2-file-upload-header__content-wrapper{display:flex;flex-direction:column;align-items:center;justify-content:center;column-gap:.375rem;row-gap:.125rem;padding-top:.125rem;padding-bottom:.125rem}@container (min-width: 24rem){.s2-file-upload .s2-file-upload-header__content-wrapper{flex-direction:row}}.s2-file-upload .s2-file-upload-header__text{display:inline}.s2-file-upload .s2-file-upload-header__text--responsive{display:none}@container (min-width: 24rem){.s2-file-upload .s2-file-upload-header__text--responsive{display:inline}}.s2-file-upload .s2-file-upload-header__choose{font-weight:500;--tw-text-opacity: 1;color:hsl(var(--s2-color-primary-500) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header__choose:hover{text-decoration-line:underline}.s2-file-upload .s2-file-upload-header__choose:disabled{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header__choose:disabled:hover{text-decoration-line:none}.s2-file-upload .s2-file-upload-header__error{text-align:center;--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-700) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header--disabled{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-neutral-050) / var(--tw-bg-opacity));--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-file-upload-header--disabled .s2-file-upload__icon{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-300) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-files{display:flex;flex-direction:column;gap:.25rem}.s2-file-upload .s2-uploaded-file{display:flex;align-items:center;gap:.5rem;overflow:hidden;border-bottom-width:1px;--tw-border-opacity: 1;border-color:hsl(var(--s2-color-neutral-200) / var(--tw-border-opacity));padding:.25rem}.s2-file-upload .s2-uploaded-file:first-child{margin-top:.5rem}.s2-file-upload .s2-uploaded-file:last-child{border-bottom-width:0px}.s2-file-upload .s2-uploaded-file__icon-wrapper{display:flex;height:2rem;width:2rem;flex-shrink:0;align-items:center;justify-content:center;border-radius:.375rem;--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-neutral-100) / var(--tw-bg-opacity))}@container (min-width: 24rem){.s2-file-upload .s2-uploaded-file__icon-wrapper{height:2.5rem;width:2.5rem}}.s2-file-upload .s2-uploaded-file__icon{flex-shrink:0;font-size:var(--s2-font-size-2xl);line-height:1;--tw-text-opacity: 1;color:hsl(var(--s2-color-text-500) / var(--tw-text-opacity))}@container (min-width: 24rem){.s2-file-upload .s2-uploaded-file__icon{font-size:var(--s2-font-size-3xl);line-height:1}}.s2-file-upload .s2-uploaded-file__icon--uploaded{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-600) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info{display:flex;flex-grow:1;flex-direction:column;overflow:hidden}.s2-file-upload .s2-uploaded-file__info-name{display:inline-block;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;text-align:left;--tw-text-opacity: 1;color:hsl(var(--s2-color-text-700) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-name--downloadable{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-900) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-name--downloadable:hover{text-decoration-line:underline}.s2-file-upload .s2-uploaded-file__info-progress{display:flex;width:100%;max-width:24rem;padding-top:.375rem;padding-bottom:.375rem}.s2-file-upload .s2-uploaded-file__info-progress-background{display:flex;height:.5rem;flex-grow:1;overflow:hidden;border-radius:9999px;--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-neutral-100) / var(--tw-bg-opacity))}.s2-file-upload .s2-uploaded-file__info-progress-line{height:100%;--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-primary-500) / var(--tw-bg-opacity));--tw-shadow: 0 4px 6px -1px rgb(0 0 0 / .1), 0 2px 4px -2px rgb(0 0 0 / .1);--tw-shadow-colored: 0 4px 6px -1px var(--tw-shadow-color), 0 2px 4px -2px var(--tw-shadow-color);box-shadow:var(--tw-ring-offset-shadow, 0 0 #0000),var(--tw-ring-shadow, 0 0 #0000),var(--tw-shadow);transition-property:all;transition-timing-function:cubic-bezier(.4,0,.2,1);transition-duration:.15s}.s2-file-upload .s2-uploaded-file__info-details{display:flex;flex-wrap:wrap;column-gap:.5rem}.s2-file-upload .s2-uploaded-file__info-details-date{font-size:var(--s2-font-size-sm);line-height:var(--s2-font-size-sm-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-400) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-details-size{font-size:var(--s2-font-size-sm);line-height:var(--s2-font-size-sm-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-500) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__info-details-size--uploaded{--tw-text-opacity: 1;color:hsl(var(--s2-color-success-500) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove{margin-left:auto;flex-shrink:0;padding:.25rem;--tw-text-opacity: 1;color:hsl(var(--s2-color-text-700) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove:hover{--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-700) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove:disabled{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-300) / var(--tw-text-opacity))}.s2-file-upload .s2-uploaded-file__remove-icon{font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height)}\n"] }]
        }], ctorParameters: function () { return [{ type: S2UiTranslationsService }, { type: i0.DestroyRef }]; }, propDecorators: { accept: [{
                type: Input
            }], autoUploadMethod: [{
                type: Input
            }], browseButtonText: [{
                type: Input
            }], errorDuration: [{
                type: Input
            }], inputId: [{
                type: Input
            }], maxFiles: [{
                type: Input
            }], maxFileSize: [{
                type: Input
            }], showDate: [{
                type: Input
            }], showDragDropText: [{
                type: Input
            }], showHeaderIcon: [{
                type: Input
            }], clickDownload: [{
                type: Output
            }], clickRemove: [{
                type: Output
            }], autoUploadSuccess: [{
                type: Output
            }], autoUploadError: [{
                type: Output
            }], templatesQueryList: [{
                type: ContentChildren,
                args: [TemplateDirective]
            }] } });

class S2UiPopupMessagesService {
    messages = [];
    add(message) {
        this.messages.push(message);
        if (message.duration === undefined || message.duration > 0) {
            setTimeout(() => {
                this.remove(message);
            }, message.duration || 5000);
        }
    }
    addAll(messages) {
        messages.forEach((message) => {
            this.add(message);
        });
    }
    remove(message) {
        if (message && this.messages.includes(message)) {
            this.messages.splice(this.messages.indexOf(message), 1);
            return true;
        }
        return false;
    }
    removeAt(index) {
        if (this.messages[index]) {
            this.messages.splice(index, 1);
            return true;
        }
        return false;
    }
    removeByKey(key) {
        const message = this.messages.find((message) => message.key === key);
        if (message) {
            this.messages.splice(this.messages.indexOf(message), 1);
            return true;
        }
        return false;
    }
    clear() {
        this.messages.splice(0, this.messages.length);
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiPopupMessagesService, deps: [], target: i0.ɵɵFactoryTarget.Injectable });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiPopupMessagesService, providedIn: 'root' });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: S2UiPopupMessagesService, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }] });

class PopupMessagesComponent {
    service;
    translationsService;
    hostPositionClass = 's2-popup-messages--position-top-center';
    key;
    position = 'top-center';
    fadeDirection = 'top';
    defaultFadeDirectionTransition = 'translateY(100%)';
    fadeDirectionTransitions = {
        top: 'translateY(-100%)',
        left: 'translateX(-100%)',
        right: 'translateX(100%)',
        bottom: 'translateY(100%)',
    };
    icons = {
        error: 'error',
        warning: 'warning',
        success: 'check_circle',
        info: 'info',
    };
    constructor(service, translationsService) {
        this.service = service;
        this.translationsService = translationsService;
    }
    ngOnChanges(changes) {
        if (changes.position) {
            const currentValue = changes.position.currentValue;
            if (currentValue) {
                this.hostPositionClass = 's2-popup-messages--position-' + currentValue;
            }
            else {
                this.hostPositionClass = '';
            }
        }
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: PopupMessagesComponent, deps: [{ token: S2UiPopupMessagesService }, { token: S2UiTranslationsService }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: PopupMessagesComponent, isStandalone: true, selector: "s2-popup-messages", inputs: { key: "key", position: "position", fadeDirection: "fadeDirection" }, host: { properties: { "class": "this.hostPositionClass" }, classAttribute: "s2-popup-messages" }, usesOnChanges: true, ngImport: i0, template: "<ng-container *ngFor=\"let message of service.messages; index as i\">\r\n  <div\r\n    [@showHide]=\"{ value: 'visible', params: {transformValue: fadeDirectionTransitions[fadeDirection] || defaultFadeDirectionTransition} }\"\r\n    *ngIf=\"(!key && !message.containerKey) || key === message.containerKey\"\r\n    class=\"s2-popup-message\"\r\n    [ngClass]=\"{'s2-popup-message--closable': message.closable === undefined || message.closable}\"\r\n    role=\"alertdialog\"\r\n    [attr.aria-labelledby]=\"'s2-popup-message-heading-' + i\"\r\n    [attr.aria-describedby]=\"'s2-popup-message-text-' + i\"\r\n  >\r\n    <div\r\n      class=\"s2-popup-message__border\"\r\n      [ngClass]=\"message.severity ? 's2-popup-message__border--' + message.severity : ''\"\r\n    ></div>\r\n    <s2-icon\r\n      *ngIf=\"message.icon || message.severity\"\r\n      class=\"s2-popup-message__icon\"\r\n      [ngClass]=\"message.severity ? 's2-popup-message__icon--' + message.severity : ''\"\r\n      >{{ message.icon || icons[message.severity] }}</s2-icon\r\n    >\r\n    <div class=\"s2-popup-message__texts\">\r\n      <div\r\n        *ngIf=\"message.heading\"\r\n        class=\"s2-popup-message__heading\"\r\n        [ngClass]=\"message.severity ? 's2-popup-message__heading--' + message.severity : ''\"\r\n        [id]=\"'s2-popup-message-heading-' + i\"\r\n      >\r\n        {{ message.heading }}\r\n      </div>\r\n      <div *ngIf=\"message.text\" class=\"s2-popup-message__text\" [id]=\"'s2-popup-message-text-' + i\">\r\n        {{ message.text }}\r\n      </div>\r\n    </div>\r\n    <button\r\n      *ngIf=\"message.closable === undefined || message.closable\"\r\n      type=\"button\"\r\n      class=\"s2-popup-message__close\"\r\n      (click)=\"service.remove(message)\"\r\n      [attr.aria-label]=\"translationsService.translations.action.close\"\r\n    >\r\n      <s2-icon class=\"s2-popup-message__close-icon\">close</s2-icon>\r\n    </button>\r\n  </div>\r\n</ng-container>\r\n", styles: [".s2-popup-messages{pointer-events:none;position:fixed;z-index:50;display:flex;flex-direction:column;align-items:stretch;gap:.5rem;padding:.5rem;width:min(95vw,30rem)}.s2-popup-messages--position-top-center{top:0;left:50%;--tw-translate-x: -50%;transform:translate(var(--tw-translate-x),var(--tw-translate-y)) rotate(var(--tw-rotate)) skew(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y))}.s2-popup-messages--position-top-left{top:0;left:0}.s2-popup-messages--position-top-right{top:0;right:0}.s2-popup-messages--position-bottom-center{bottom:0;left:50%;--tw-translate-x: -50%;transform:translate(var(--tw-translate-x),var(--tw-translate-y)) rotate(var(--tw-rotate)) skew(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y))}.s2-popup-messages--position-bottom-left{bottom:0;left:0}.s2-popup-messages--position-bottom-right{bottom:0;right:0}.s2-popup-messages--position-center{top:50%;left:50%;--tw-translate-y: -50%;--tw-translate-x: -50%;transform:translate(var(--tw-translate-x),var(--tw-translate-y)) rotate(var(--tw-rotate)) skew(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y))}.s2-popup-message{pointer-events:auto;position:relative;display:flex;align-items:center;overflow:hidden;border-radius:.25rem;background-color:hsl(var(--s2-color-neutral-050) / .95);padding:.25rem;--tw-shadow: 0 4px 6px -1px rgb(0 0 0 / .1), 0 2px 4px -2px rgb(0 0 0 / .1);--tw-shadow-colored: 0 4px 6px -1px var(--tw-shadow-color), 0 2px 4px -2px var(--tw-shadow-color);box-shadow:var(--tw-ring-offset-shadow, 0 0 #0000),var(--tw-ring-shadow, 0 0 #0000),var(--tw-shadow)}.s2-popup-message--closable{padding-right:1.25rem}.s2-popup-message__border{position:absolute;top:0;left:0;bottom:0;width:.25rem}.s2-popup-message__border--error{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-risk-600) / var(--tw-bg-opacity))}.s2-popup-message__border--info{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-info-600) / var(--tw-bg-opacity))}.s2-popup-message__border--success{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-success-600) / var(--tw-bg-opacity))}.s2-popup-message__border--warning{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-warning-600) / var(--tw-bg-opacity))}.s2-popup-message .s2-popup-message__icon{flex-shrink:0;padding-left:1rem;padding-right:.75rem;font-size:var(--s2-font-size-3xl);line-height:var(--s2-font-size-3xl-line-height)}.s2-popup-message .s2-popup-message__icon--error{--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-500) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__icon--info{--tw-text-opacity: 1;color:hsl(var(--s2-color-info-500) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__icon--success{--tw-text-opacity: 1;color:hsl(var(--s2-color-success-500) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__icon--warning{--tw-text-opacity: 1;color:hsl(var(--s2-color-warning-500) / var(--tw-text-opacity))}.s2-popup-message__texts{padding:.25rem}.s2-popup-message__heading{padding-bottom:.125rem;font-size:var(--s2-font-size-md);line-height:var(--s2-font-size-md-line-height);font-weight:500}.s2-popup-message__heading--error{--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-900) / var(--tw-text-opacity))}.s2-popup-message__heading--info{--tw-text-opacity: 1;color:hsl(var(--s2-color-info-900) / var(--tw-text-opacity))}.s2-popup-message__heading--success{--tw-text-opacity: 1;color:hsl(var(--s2-color-success-900) / var(--tw-text-opacity))}.s2-popup-message__heading--warning{--tw-text-opacity: 1;color:hsl(var(--s2-color-warning-900) / var(--tw-text-opacity))}.s2-popup-message__close{position:absolute;top:.25rem;right:.25rem;font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height)}.s2-popup-message .s2-popup-message__close-icon{font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-700) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__close-icon:hover{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-900) / var(--tw-text-opacity))}\n"], dependencies: [{ kind: "ngmodule", type: CommonModule }, { kind: "directive", type: i2.NgClass, selector: "[ngClass]", inputs: ["class", "ngClass"] }, { kind: "directive", type: i2.NgForOf, selector: "[ngFor][ngForOf]", inputs: ["ngForOf", "ngForTrackBy", "ngForTemplate"] }, { kind: "directive", type: i2.NgIf, selector: "[ngIf]", inputs: ["ngIf", "ngIfThen", "ngIfElse"] }, { kind: "component", type: IconComponent, selector: "s2-icon" }], animations: [
            trigger('showHide', [
                state('visible', style({
                    transform: 'translate(0, 0)',
                    opacity: 1,
                })),
                transition(':enter', [
                    style({
                        opacity: 0,
                        transform: '{{transformValue}}',
                    }),
                    animate('0.2s ease'),
                ]),
                transition(':leave', [animate('0.2s ease', style({ opacity: 0, transform: '{{transformValue}}' }))]),
            ]),
        ], encapsulation: i0.ViewEncapsulation.None });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: PopupMessagesComponent, decorators: [{
            type: Component,
            args: [{ selector: 's2-popup-messages', standalone: true, imports: [CommonModule, IconComponent], encapsulation: ViewEncapsulation.None, host: { class: 's2-popup-messages' }, animations: [
                        trigger('showHide', [
                            state('visible', style({
                                transform: 'translate(0, 0)',
                                opacity: 1,
                            })),
                            transition(':enter', [
                                style({
                                    opacity: 0,
                                    transform: '{{transformValue}}',
                                }),
                                animate('0.2s ease'),
                            ]),
                            transition(':leave', [animate('0.2s ease', style({ opacity: 0, transform: '{{transformValue}}' }))]),
                        ]),
                    ], template: "<ng-container *ngFor=\"let message of service.messages; index as i\">\r\n  <div\r\n    [@showHide]=\"{ value: 'visible', params: {transformValue: fadeDirectionTransitions[fadeDirection] || defaultFadeDirectionTransition} }\"\r\n    *ngIf=\"(!key && !message.containerKey) || key === message.containerKey\"\r\n    class=\"s2-popup-message\"\r\n    [ngClass]=\"{'s2-popup-message--closable': message.closable === undefined || message.closable}\"\r\n    role=\"alertdialog\"\r\n    [attr.aria-labelledby]=\"'s2-popup-message-heading-' + i\"\r\n    [attr.aria-describedby]=\"'s2-popup-message-text-' + i\"\r\n  >\r\n    <div\r\n      class=\"s2-popup-message__border\"\r\n      [ngClass]=\"message.severity ? 's2-popup-message__border--' + message.severity : ''\"\r\n    ></div>\r\n    <s2-icon\r\n      *ngIf=\"message.icon || message.severity\"\r\n      class=\"s2-popup-message__icon\"\r\n      [ngClass]=\"message.severity ? 's2-popup-message__icon--' + message.severity : ''\"\r\n      >{{ message.icon || icons[message.severity] }}</s2-icon\r\n    >\r\n    <div class=\"s2-popup-message__texts\">\r\n      <div\r\n        *ngIf=\"message.heading\"\r\n        class=\"s2-popup-message__heading\"\r\n        [ngClass]=\"message.severity ? 's2-popup-message__heading--' + message.severity : ''\"\r\n        [id]=\"'s2-popup-message-heading-' + i\"\r\n      >\r\n        {{ message.heading }}\r\n      </div>\r\n      <div *ngIf=\"message.text\" class=\"s2-popup-message__text\" [id]=\"'s2-popup-message-text-' + i\">\r\n        {{ message.text }}\r\n      </div>\r\n    </div>\r\n    <button\r\n      *ngIf=\"message.closable === undefined || message.closable\"\r\n      type=\"button\"\r\n      class=\"s2-popup-message__close\"\r\n      (click)=\"service.remove(message)\"\r\n      [attr.aria-label]=\"translationsService.translations.action.close\"\r\n    >\r\n      <s2-icon class=\"s2-popup-message__close-icon\">close</s2-icon>\r\n    </button>\r\n  </div>\r\n</ng-container>\r\n", styles: [".s2-popup-messages{pointer-events:none;position:fixed;z-index:50;display:flex;flex-direction:column;align-items:stretch;gap:.5rem;padding:.5rem;width:min(95vw,30rem)}.s2-popup-messages--position-top-center{top:0;left:50%;--tw-translate-x: -50%;transform:translate(var(--tw-translate-x),var(--tw-translate-y)) rotate(var(--tw-rotate)) skew(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y))}.s2-popup-messages--position-top-left{top:0;left:0}.s2-popup-messages--position-top-right{top:0;right:0}.s2-popup-messages--position-bottom-center{bottom:0;left:50%;--tw-translate-x: -50%;transform:translate(var(--tw-translate-x),var(--tw-translate-y)) rotate(var(--tw-rotate)) skew(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y))}.s2-popup-messages--position-bottom-left{bottom:0;left:0}.s2-popup-messages--position-bottom-right{bottom:0;right:0}.s2-popup-messages--position-center{top:50%;left:50%;--tw-translate-y: -50%;--tw-translate-x: -50%;transform:translate(var(--tw-translate-x),var(--tw-translate-y)) rotate(var(--tw-rotate)) skew(var(--tw-skew-x)) skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x)) scaleY(var(--tw-scale-y))}.s2-popup-message{pointer-events:auto;position:relative;display:flex;align-items:center;overflow:hidden;border-radius:.25rem;background-color:hsl(var(--s2-color-neutral-050) / .95);padding:.25rem;--tw-shadow: 0 4px 6px -1px rgb(0 0 0 / .1), 0 2px 4px -2px rgb(0 0 0 / .1);--tw-shadow-colored: 0 4px 6px -1px var(--tw-shadow-color), 0 2px 4px -2px var(--tw-shadow-color);box-shadow:var(--tw-ring-offset-shadow, 0 0 #0000),var(--tw-ring-shadow, 0 0 #0000),var(--tw-shadow)}.s2-popup-message--closable{padding-right:1.25rem}.s2-popup-message__border{position:absolute;top:0;left:0;bottom:0;width:.25rem}.s2-popup-message__border--error{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-risk-600) / var(--tw-bg-opacity))}.s2-popup-message__border--info{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-info-600) / var(--tw-bg-opacity))}.s2-popup-message__border--success{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-success-600) / var(--tw-bg-opacity))}.s2-popup-message__border--warning{--tw-bg-opacity: 1;background-color:hsl(var(--s2-color-warning-600) / var(--tw-bg-opacity))}.s2-popup-message .s2-popup-message__icon{flex-shrink:0;padding-left:1rem;padding-right:.75rem;font-size:var(--s2-font-size-3xl);line-height:var(--s2-font-size-3xl-line-height)}.s2-popup-message .s2-popup-message__icon--error{--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-500) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__icon--info{--tw-text-opacity: 1;color:hsl(var(--s2-color-info-500) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__icon--success{--tw-text-opacity: 1;color:hsl(var(--s2-color-success-500) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__icon--warning{--tw-text-opacity: 1;color:hsl(var(--s2-color-warning-500) / var(--tw-text-opacity))}.s2-popup-message__texts{padding:.25rem}.s2-popup-message__heading{padding-bottom:.125rem;font-size:var(--s2-font-size-md);line-height:var(--s2-font-size-md-line-height);font-weight:500}.s2-popup-message__heading--error{--tw-text-opacity: 1;color:hsl(var(--s2-color-risk-900) / var(--tw-text-opacity))}.s2-popup-message__heading--info{--tw-text-opacity: 1;color:hsl(var(--s2-color-info-900) / var(--tw-text-opacity))}.s2-popup-message__heading--success{--tw-text-opacity: 1;color:hsl(var(--s2-color-success-900) / var(--tw-text-opacity))}.s2-popup-message__heading--warning{--tw-text-opacity: 1;color:hsl(var(--s2-color-warning-900) / var(--tw-text-opacity))}.s2-popup-message__close{position:absolute;top:.25rem;right:.25rem;font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height)}.s2-popup-message .s2-popup-message__close-icon{font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height);--tw-text-opacity: 1;color:hsl(var(--s2-color-text-700) / var(--tw-text-opacity))}.s2-popup-message .s2-popup-message__close-icon:hover{--tw-text-opacity: 1;color:hsl(var(--s2-color-text-900) / var(--tw-text-opacity))}\n"] }]
        }], ctorParameters: function () { return [{ type: S2UiPopupMessagesService }, { type: S2UiTranslationsService }]; }, propDecorators: { hostPositionClass: [{
                type: HostBinding,
                args: ['class']
            }], key: [{
                type: Input
            }], position: [{
                type: Input
            }], fadeDirection: [{
                type: Input
            }] } });

class CheckboxTreeComponent {
    translationsService;
    static S2_TEMPLATE_ITEM_LABEL = 'item-label';
    static getValues(items, valueProperty, state = 'checked') {
        return items.reduce((accumulator, item) => {
            if (item.children?.length) {
                accumulator.push(...CheckboxTreeComponent.getValues(item.children, valueProperty, state));
            }
            else if (state === 'any' ||
                (state === 'checked' && item.checked === true) ||
                (state === 'uncheked' && !item.checked)) {
                accumulator.push(valueProperty ? item[valueProperty] : item);
            }
            return accumulator;
        }, []);
    }
    destroy$ = new Subject();
    templatesQueryList;
    itemLabelTemplate;
    items;
    showSelectAll = true;
    selectAllText;
    inputsIdPrefix = '';
    valueProperty;
    selectAllChecked = false;
    disabled = false;
    touched = false;
    onChange;
    onTouched;
    constructor(translationsService) {
        this.translationsService = translationsService;
    }
    ngOnChanges(changes) {
        if (changes.items.currentValue) {
            this.refreshCheckedValues();
        }
    }
    ngAfterContentInit() {
        this.updateTemplates();
        this.templatesQueryList.changes.pipe(takeUntil(this.destroy$)).subscribe(() => {
            this.updateTemplates();
        });
    }
    ngOnDestroy() {
        this.destroy$.next();
        this.destroy$.unsubscribe();
    }
    writeValue(newValue) {
        if (this.onChange) {
            const setValue = (items, value) => {
                items.forEach((item) => {
                    if (item.children?.length) {
                        setValue(item.children, value);
                    }
                    else {
                        item.checked = value?.length
                            ? this.valueProperty
                                ? value.some((valueItem) => valueItem === item[this.valueProperty])
                                : value.includes(item)
                            : false;
                    }
                });
            };
            setValue(this.items, newValue);
            this.refreshCheckedValues();
        }
    }
    registerOnChange(fn) {
        this.onChange = fn;
    }
    registerOnTouched(fn) {
        this.onTouched = fn;
    }
    setDisabledState(isDisabled) {
        this.disabled = isDisabled;
    }
    markAsTouched() {
        if (!this.touched) {
            this.onTouched?.();
            this.touched = true;
        }
    }
    handleSelectAll(event) {
        this.handleCheckboxChange({ children: this.items, checked: this.selectAllChecked }, event);
    }
    handleCheckboxChange(item, event) {
        if (item.children?.length && event) {
            const eventTarget = event.target;
            this.setChildrenCheckedValue(item, item.checked === 'semi' ? true : eventTarget.checked);
        }
        this.refreshCheckedValues();
        this.markAsTouched();
        this.onChange?.(this.getValues());
    }
    getValues(items = this.items, valueProperty = this.valueProperty, state = 'checked') {
        return CheckboxTreeComponent.getValues(items, valueProperty, state);
    }
    setChildrenCheckedValue(item, value) {
        item.children?.forEach((child) => {
            child.checked = value;
            if (child.children?.length) {
                this.setChildrenCheckedValue(child, value);
            }
        });
    }
    isItemChecked(item) {
        if (item.children?.length) {
            const selectedCount = item.children.reduce((accumulator, child) => {
                if (child.children?.length) {
                    const isChildChildrenSelected = this.isItemChecked(child);
                    child.checked = isChildChildrenSelected;
                    if (isChildChildrenSelected === true) {
                        return accumulator + 1;
                    }
                    else if (isChildChildrenSelected === 'semi') {
                        return accumulator + 0.5;
                    }
                }
                else if (child.checked === true) {
                    return accumulator + 1;
                }
                return accumulator;
            }, 0);
            if (selectedCount === item.children.length && item.children.length > 0) {
                return true;
            }
            else if (selectedCount > 0) {
                return 'semi';
            }
            return false;
        }
        else {
            return item.checked === true;
        }
    }
    refreshCheckedValues() {
        if (this.showSelectAll) {
            this.selectAllChecked = this.isItemChecked({ children: this.items });
        }
        else {
            this.items.forEach((item) => {
                item.checked = this.isItemChecked(item);
            });
        }
    }
    updateTemplates() {
        this.itemLabelTemplate = this.templatesQueryList.find((template) => template.s2Template === CheckboxTreeComponent.S2_TEMPLATE_ITEM_LABEL)?.template;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CheckboxTreeComponent, deps: [{ token: S2UiTranslationsService }], target: i0.ɵɵFactoryTarget.Component });
    static ɵcmp = i0.ɵɵngDeclareComponent({ minVersion: "14.0.0", version: "16.0.2", type: CheckboxTreeComponent, isStandalone: true, selector: "s2-checkbox-tree", inputs: { items: "items", showSelectAll: "showSelectAll", selectAllText: "selectAllText", inputsIdPrefix: "inputsIdPrefix", valueProperty: "valueProperty" }, host: { classAttribute: "s2-checkbox-tree" }, providers: [
            {
                provide: NG_VALUE_ACCESSOR,
                multi: true,
                useExisting: CheckboxTreeComponent,
            },
        ], queries: [{ propertyName: "templatesQueryList", predicate: TemplateDirective }], usesOnChanges: true, ngImport: i0, template: "<div *ngIf=\"showSelectAll\" class=\"s2-ui-inline-input-label s2-checkbox-tree-item s2-checkbox-tree-item--select-all\">\r\n  <input\r\n    type=\"checkbox\"\r\n    id=\"select-all\"\r\n    [checked]=\"selectAllChecked\"\r\n    class=\"s2-ui-input-checkbox\"\r\n    [ngClass]=\"{'s2-ui-input-checkbox--semi-selected': selectAllChecked === 'semi'}\"\r\n    (change)=\"handleSelectAll($event)\"\r\n  />\r\n  <label class=\"s2-checkbox-tree-item__label\" for=\"select-all\">\r\n    {{ selectAllText || translationsService.translations.action.selectAll }}\r\n  </label>\r\n</div>\r\n\r\n<ul class=\"s2-checkbox-tree-items\">\r\n  <ng-container *ngTemplateOutlet=\"loop; context: {items: items, idPrefix: inputsIdPrefix}\"></ng-container>\r\n</ul>\r\n\r\n<ng-template #loop let-items=\"items\" let-idPrefix=\"idPrefix\">\r\n  <ng-container *ngFor=\"let item of items; index as i\">\r\n    <li *ngIf=\"!item.hidden\">\r\n      <ng-container\r\n        *ngTemplateOutlet=\"item.children?.length ? groupView : checkboxView; context: {item: item, idPrefix: ''+idPrefix+i}\"\r\n      ></ng-container>\r\n    </li>\r\n  </ng-container>\r\n</ng-template>\r\n\r\n<ng-template #groupView let-group=\"item\" let-idPrefix=\"idPrefix\">\r\n  <div class=\"s2-ui-inline-input-label\">\r\n    <div class=\"s2-checkbox-tree-item__button-placeholder\">\r\n      <button type=\"button\" class=\"s2-checkbox-tree-item__chevron-button\" (click)=\"group.open = !group.open\">\r\n        <s2-icon>{{ group.open ? 'expand_more' : 'chevron_right'}} </s2-icon>\r\n      </button>\r\n    </div>\r\n    <input\r\n      type=\"checkbox\"\r\n      [id]=\"idPrefix + '-' + (group.key !== undefined ? group.key : group.label)\"\r\n      class=\"s2-ui-input-checkbox\"\r\n      [checked]=\"group.checked\"\r\n      [disabled]=\"disabled || group.disabled\"\r\n      [ngClass]=\"{'s2-ui-input-checkbox--semi-selected': group.checked === 'semi'}\"\r\n      (change)=\"handleCheckboxChange(group, $event)\"\r\n    />\r\n    <label\r\n      class=\"s2-checkbox-tree-item__label\"\r\n      [for]=\"idPrefix + '-' + (group.key !== undefined ? group.key : group.label)\"\r\n    >\r\n      <ng-container *ngIf=\"itemLabelTemplate\">\r\n        <ng-container *ngTemplateOutlet=\"itemLabelTemplate; context: {$implicit: group}\"></ng-container>\r\n      </ng-container>\r\n      <ng-container *ngIf=\"!itemLabelTemplate\">\r\n        {{ group.label }}\r\n      </ng-container>\r\n    </label>\r\n  </div>\r\n  <ul *ngIf=\"group.open\">\r\n    <ng-container *ngTemplateOutlet=\"loop; context: {items: group.children, idPrefix: idPrefix}\"></ng-container>\r\n  </ul>\r\n</ng-template>\r\n\r\n<ng-template #checkboxView let-checkbox=\"item\" let-idPrefix=\"idPrefix\">\r\n  <div class=\"s2-ui-inline-input-label\">\r\n    <div class=\"s2-checkbox-tree-item__button-placeholder\"></div>\r\n    <input\r\n      type=\"checkbox\"\r\n      [id]=\"idPrefix + '-' + (checkbox.key !== undefined ? checkbox.key : checkbox.label)\"\r\n      class=\"s2-ui-input-checkbox\"\r\n      [disabled]=\"disabled || checkbox.disabled\"\r\n      [(ngModel)]=\"checkbox.checked\"\r\n      (ngModelChange)=\"handleCheckboxChange(checkbox, null)\"\r\n    />\r\n    <label\r\n      class=\"s2-checkbox-tree-item__label\"\r\n      [for]=\"idPrefix + '-' + (checkbox.key !== undefined ? checkbox.key : checkbox.label)\"\r\n    >\r\n      <ng-container *ngIf=\"itemLabelTemplate\">\r\n        <ng-container *ngTemplateOutlet=\"itemLabelTemplate; context: {$implicit: checkbox}\"></ng-container>\r\n      </ng-container>\r\n      <ng-container *ngIf=\"!itemLabelTemplate\">\r\n        {{ checkbox.label }}\r\n      </ng-container>\r\n    </label>\r\n  </div>\r\n</ng-template>\r\n", styles: [".s2-checkbox-tree{display:block}.s2-checkbox-tree-items li{margin-top:.125rem;margin-bottom:.125rem}.s2-checkbox-tree-items li li{margin-left:1.5rem}.s2-checkbox-tree-item__button-placeholder{min-width:1.25rem}.s2-checkbox-tree-item__chevron-button{display:flex;align-items:center;justify-content:center;font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height)}.s2-checkbox-tree-item__label{padding-top:1px;font-size:var(--s2-font-size-sm);line-height:var(--s2-font-size-sm-line-height)}.s2-checkbox-tree-item--select-all{padding-bottom:.125rem}\n"], dependencies: [{ kind: "ngmodule", type: CommonModule }, { kind: "directive", type: i2.NgClass, selector: "[ngClass]", inputs: ["class", "ngClass"] }, { kind: "directive", type: i2.NgForOf, selector: "[ngFor][ngForOf]", inputs: ["ngForOf", "ngForTrackBy", "ngForTemplate"] }, { kind: "directive", type: i2.NgIf, selector: "[ngIf]", inputs: ["ngIf", "ngIfThen", "ngIfElse"] }, { kind: "directive", type: i2.NgTemplateOutlet, selector: "[ngTemplateOutlet]", inputs: ["ngTemplateOutletContext", "ngTemplateOutlet", "ngTemplateOutletInjector"] }, { kind: "ngmodule", type: FormsModule }, { kind: "directive", type: i3.CheckboxControlValueAccessor, selector: "input[type=checkbox][formControlName],input[type=checkbox][formControl],input[type=checkbox][ngModel]" }, { kind: "directive", type: i3.NgControlStatus, selector: "[formControlName],[ngModel],[formControl]" }, { kind: "directive", type: i3.NgModel, selector: "[ngModel]:not([formControlName]):not([formControl])", inputs: ["name", "disabled", "ngModel", "ngModelOptions"], outputs: ["ngModelChange"], exportAs: ["ngModel"] }, { kind: "component", type: IconComponent, selector: "s2-icon" }], encapsulation: i0.ViewEncapsulation.None });
}
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: CheckboxTreeComponent, decorators: [{
            type: Component,
            args: [{ selector: 's2-checkbox-tree', standalone: true, imports: [CommonModule, FormsModule, IconComponent], encapsulation: ViewEncapsulation.None, host: { class: 's2-checkbox-tree' }, providers: [
                        {
                            provide: NG_VALUE_ACCESSOR,
                            multi: true,
                            useExisting: CheckboxTreeComponent,
                        },
                    ], template: "<div *ngIf=\"showSelectAll\" class=\"s2-ui-inline-input-label s2-checkbox-tree-item s2-checkbox-tree-item--select-all\">\r\n  <input\r\n    type=\"checkbox\"\r\n    id=\"select-all\"\r\n    [checked]=\"selectAllChecked\"\r\n    class=\"s2-ui-input-checkbox\"\r\n    [ngClass]=\"{'s2-ui-input-checkbox--semi-selected': selectAllChecked === 'semi'}\"\r\n    (change)=\"handleSelectAll($event)\"\r\n  />\r\n  <label class=\"s2-checkbox-tree-item__label\" for=\"select-all\">\r\n    {{ selectAllText || translationsService.translations.action.selectAll }}\r\n  </label>\r\n</div>\r\n\r\n<ul class=\"s2-checkbox-tree-items\">\r\n  <ng-container *ngTemplateOutlet=\"loop; context: {items: items, idPrefix: inputsIdPrefix}\"></ng-container>\r\n</ul>\r\n\r\n<ng-template #loop let-items=\"items\" let-idPrefix=\"idPrefix\">\r\n  <ng-container *ngFor=\"let item of items; index as i\">\r\n    <li *ngIf=\"!item.hidden\">\r\n      <ng-container\r\n        *ngTemplateOutlet=\"item.children?.length ? groupView : checkboxView; context: {item: item, idPrefix: ''+idPrefix+i}\"\r\n      ></ng-container>\r\n    </li>\r\n  </ng-container>\r\n</ng-template>\r\n\r\n<ng-template #groupView let-group=\"item\" let-idPrefix=\"idPrefix\">\r\n  <div class=\"s2-ui-inline-input-label\">\r\n    <div class=\"s2-checkbox-tree-item__button-placeholder\">\r\n      <button type=\"button\" class=\"s2-checkbox-tree-item__chevron-button\" (click)=\"group.open = !group.open\">\r\n        <s2-icon>{{ group.open ? 'expand_more' : 'chevron_right'}} </s2-icon>\r\n      </button>\r\n    </div>\r\n    <input\r\n      type=\"checkbox\"\r\n      [id]=\"idPrefix + '-' + (group.key !== undefined ? group.key : group.label)\"\r\n      class=\"s2-ui-input-checkbox\"\r\n      [checked]=\"group.checked\"\r\n      [disabled]=\"disabled || group.disabled\"\r\n      [ngClass]=\"{'s2-ui-input-checkbox--semi-selected': group.checked === 'semi'}\"\r\n      (change)=\"handleCheckboxChange(group, $event)\"\r\n    />\r\n    <label\r\n      class=\"s2-checkbox-tree-item__label\"\r\n      [for]=\"idPrefix + '-' + (group.key !== undefined ? group.key : group.label)\"\r\n    >\r\n      <ng-container *ngIf=\"itemLabelTemplate\">\r\n        <ng-container *ngTemplateOutlet=\"itemLabelTemplate; context: {$implicit: group}\"></ng-container>\r\n      </ng-container>\r\n      <ng-container *ngIf=\"!itemLabelTemplate\">\r\n        {{ group.label }}\r\n      </ng-container>\r\n    </label>\r\n  </div>\r\n  <ul *ngIf=\"group.open\">\r\n    <ng-container *ngTemplateOutlet=\"loop; context: {items: group.children, idPrefix: idPrefix}\"></ng-container>\r\n  </ul>\r\n</ng-template>\r\n\r\n<ng-template #checkboxView let-checkbox=\"item\" let-idPrefix=\"idPrefix\">\r\n  <div class=\"s2-ui-inline-input-label\">\r\n    <div class=\"s2-checkbox-tree-item__button-placeholder\"></div>\r\n    <input\r\n      type=\"checkbox\"\r\n      [id]=\"idPrefix + '-' + (checkbox.key !== undefined ? checkbox.key : checkbox.label)\"\r\n      class=\"s2-ui-input-checkbox\"\r\n      [disabled]=\"disabled || checkbox.disabled\"\r\n      [(ngModel)]=\"checkbox.checked\"\r\n      (ngModelChange)=\"handleCheckboxChange(checkbox, null)\"\r\n    />\r\n    <label\r\n      class=\"s2-checkbox-tree-item__label\"\r\n      [for]=\"idPrefix + '-' + (checkbox.key !== undefined ? checkbox.key : checkbox.label)\"\r\n    >\r\n      <ng-container *ngIf=\"itemLabelTemplate\">\r\n        <ng-container *ngTemplateOutlet=\"itemLabelTemplate; context: {$implicit: checkbox}\"></ng-container>\r\n      </ng-container>\r\n      <ng-container *ngIf=\"!itemLabelTemplate\">\r\n        {{ checkbox.label }}\r\n      </ng-container>\r\n    </label>\r\n  </div>\r\n</ng-template>\r\n", styles: [".s2-checkbox-tree{display:block}.s2-checkbox-tree-items li{margin-top:.125rem;margin-bottom:.125rem}.s2-checkbox-tree-items li li{margin-left:1.5rem}.s2-checkbox-tree-item__button-placeholder{min-width:1.25rem}.s2-checkbox-tree-item__chevron-button{display:flex;align-items:center;justify-content:center;font-size:var(--s2-font-size-xl);line-height:var(--s2-font-size-xl-line-height)}.s2-checkbox-tree-item__label{padding-top:1px;font-size:var(--s2-font-size-sm);line-height:var(--s2-font-size-sm-line-height)}.s2-checkbox-tree-item--select-all{padding-bottom:.125rem}\n"] }]
        }], ctorParameters: function () { return [{ type: S2UiTranslationsService }]; }, propDecorators: { templatesQueryList: [{
                type: ContentChildren,
                args: [TemplateDirective]
            }], items: [{
                type: Input
            }], showSelectAll: [{
                type: Input
            }], selectAllText: [{
                type: Input
            }], inputsIdPrefix: [{
                type: Input
            }], valueProperty: [{
                type: Input
            }] } });

/*
 * Public API Surface of ngx-s2-ui
 */

/**
 * Generated bundle index. Do not edit.
 */

export { BytesToTextPipe, CheckboxTreeComponent, FileTypeToIconPipe, FileUploadComponent, IconComponent, PopupMessagesComponent, ReplacePipe, S2DatePipe, S2UiPopupMessagesService, S2UiSettingsService, S2UiTranslationsService, TemplateDirective, expandFromTop, getFileExtension, getMimeTypeClass, getUniqueFileName, getUniqueName };
//# sourceMappingURL=itree-ngx-s2-ui.mjs.map
