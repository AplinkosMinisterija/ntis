import { DatePipe } from '@angular/common';
import { PipeTransform } from '@angular/core';
import { S2UiSettingsService } from '../services/s2-ui-settings.service';
import * as i0 from "@angular/core";
export declare class S2DatePipe implements PipeTransform {
    private datePipe;
    private settingsService;
    constructor(datePipe: DatePipe, settingsService: S2UiSettingsService);
    transform(value: Date | number | string, showTime?: boolean, ...options: string[]): string;
    static ɵfac: i0.ɵɵFactoryDeclaration<S2DatePipe, never>;
    static ɵpipe: i0.ɵɵPipeDeclaration<S2DatePipe, "s2Date", true>;
}
