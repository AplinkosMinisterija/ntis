import * as i0 from "@angular/core";
export interface S2UiTranslations {
    action: {
        browse: string;
        close: string;
        dragFilesHere: string;
        selectAll: string;
    };
    general: {
        or: string;
    };
    errorMsg: {
        invalidFileFormat: string;
        maxFilesExceed: string;
        maxFileSizeExceed: string;
    };
}
export declare class S2UiTranslationsService {
    translations: S2UiTranslations;
    setTranslations(translations: S2UiTranslations): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<S2UiTranslationsService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<S2UiTranslationsService>;
}
