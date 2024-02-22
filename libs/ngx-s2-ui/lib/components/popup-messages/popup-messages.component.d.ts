import { OnChanges, SimpleChanges } from '@angular/core';
import { S2UiPopupMessage, S2UiPopupMessagesService } from '../../services/s2-ui-popup-messages.service';
import { S2UiTranslationsService } from '../../services/s2-ui-translations.service';
import * as i0 from "@angular/core";
export declare class PopupMessagesComponent implements OnChanges {
    service: S2UiPopupMessagesService;
    translationsService: S2UiTranslationsService;
    private hostPositionClass;
    key: string | number;
    position: 'top-center' | 'top-left' | 'top-right' | 'bottom-center' | 'bottom-left' | 'bottom-right' | 'center';
    fadeDirection: 'top' | 'left' | 'right' | 'bottom';
    readonly defaultFadeDirectionTransition = "translateY(100%)";
    fadeDirectionTransitions: Record<typeof this.fadeDirection, string>;
    icons: Record<S2UiPopupMessage['severity'], string>;
    constructor(service: S2UiPopupMessagesService, translationsService: S2UiTranslationsService);
    ngOnChanges(changes: SimpleChanges): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<PopupMessagesComponent, never>;
    static ɵcmp: i0.ɵɵComponentDeclaration<PopupMessagesComponent, "s2-popup-messages", never, { "key": { "alias": "key"; "required": false; }; "position": { "alias": "position"; "required": false; }; "fadeDirection": { "alias": "fadeDirection"; "required": false; }; }, {}, never, never, true, never>;
}
