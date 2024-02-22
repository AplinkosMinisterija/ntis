import { ConfirmationService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { AppMessages } from '../message/app.messages';
import { TranslateService } from '@ngx-translate/core';
import * as i0 from "@angular/core";
export declare class CommonFormServices {
    route: ActivatedRoute;
    router: Router;
    appMessages: AppMessages;
    translate: TranslateService;
    confirmationService: ConfirmationService;
    calendarLt: {
        closeText: string;
        prevText: string;
        nextText: string;
        currentText: string;
        today: string;
        clear: string;
        monthNames: string[];
        monthNamesShort: string[];
        dayNames: string[];
        dayNamesShort: string[];
        dayNamesMin: string[];
        weekHeader: string;
        dateFormat: string;
        firstDay: number;
        isRTL: boolean;
        showMonthAfterYear: boolean;
        yearSuffix: string;
    };
    constructor(route: ActivatedRoute, router: Router, appMessages: AppMessages, translate: TranslateService, confirmationService: ConfirmationService);
    static ɵfac: i0.ɵɵFactoryDeclaration<CommonFormServices, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<CommonFormServices>;
}
