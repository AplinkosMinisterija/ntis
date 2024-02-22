import { NgZone } from '@angular/core';
import { Subject } from 'rxjs';
import * as i0 from "@angular/core";
export interface AppMessage {
    severity?: string;
    detail?: string;
    id?: unknown;
    sticky?: boolean;
    data?: unknown;
}
export declare class AppMessages {
    private ngZone;
    static readonly SEVERITY_ERROR = "error";
    static readonly SEVERITY_WARN = "warn";
    static readonly SEVERITY_SUCCESS = "success";
    static readonly SEVERITY_INFO = "info";
    readonly add$: Subject<AppMessage>;
    readonly clear$: Subject<void>;
    private messages;
    constructor(ngZone: NgZone);
    private showMessage;
    clearMessages(): void;
    clearMessagesExceptSuccess(): void;
    removeMessage(message: AppMessage): void;
    showError(summary: string, detail: string, id?: string): void;
    showWarning(summary: string, detail: string, id?: string): void;
    showSuccess(summary: string, detail: string, id?: string): void;
    showInfo(summary: string, detail: string, id?: string): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<AppMessages, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<AppMessages>;
}
