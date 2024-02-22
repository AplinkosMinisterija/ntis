import * as i0 from "@angular/core";
export interface S2UiSettings {
    dateFormat: string;
    dateTimeFormat: string;
}
export declare class S2UiSettingsService {
    settings: S2UiSettings;
    setSettings(settings: S2UiSettings): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<S2UiSettingsService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<S2UiSettingsService>;
}
