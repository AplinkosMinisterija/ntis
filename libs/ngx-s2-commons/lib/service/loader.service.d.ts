import { BehaviorSubject } from 'rxjs';
import { AppMessages } from '../message/app.messages';
import * as i0 from "@angular/core";
export interface LoaderParams {
    show: boolean;
    dimmed?: boolean;
    message?: string;
}
export declare class LoaderService {
    private appMessages;
    private showCount;
    private timeOutInterval;
    showLoader: BehaviorSubject<LoaderParams>;
    constructor(appMessages: AppMessages);
    show(message?: string, dimmed?: boolean): void;
    hide(): void;
    showWithOptions({ message, dimmed, timeout, timeoutMessage, }: {
        message?: string;
        dimmed?: boolean;
        timeout?: number;
        timeoutMessage?: string;
    }): void;
    protected onTimeout(timeoutMessage?: string): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<LoaderService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<LoaderService>;
}
