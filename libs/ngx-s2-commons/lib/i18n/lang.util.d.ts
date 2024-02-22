import { TranslateService } from '@ngx-translate/core';
export declare const DEFAULT_LANGUAGE = "lt";
export declare const LOCAL_STORAGE_LANG_KEY = "lang";
/**
 * Gets language from localstorage
 */
export declare function getLang(): string;
/**
 * Sets language, updates localstorage ant ngx-translate language
 */
export declare function setLang(translate: TranslateService, lang: string): void;
/**
 * Used to init translations on app startup
 */
export declare function initLang(translate: TranslateService): void;
