import { DatePipe } from '@angular/common';
import { ClientLogService } from './../service/client-log.service';
import { ErrorHandler } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AppMessages } from '../message/app.messages';
import * as i0 from "@angular/core";
export declare class AppErrorHandler implements ErrorHandler {
    protected appMessages: AppMessages;
    protected translate: TranslateService;
    protected clientLogService: ClientLogService;
    protected datePipe: DatePipe;
    log: import("../log/logger.service").Logger;
    logFrequencyPerMinute: number;
    logTimeArray: number[];
    constructor(appMessages: AppMessages, translate: TranslateService, clientLogService: ClientLogService, datePipe: DatePipe);
    handleError(error: Error): void;
    protected showMessage(text: string, severity: string): void;
    private formatClientError;
    private checkLogFrequencyLimit;
    static ɵfac: i0.ɵɵFactoryDeclaration<AppErrorHandler, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<AppErrorHandler>;
}
export declare const appErrorHandler: {
    provide: typeof ErrorHandler;
    useClass: typeof AppErrorHandler;
};
