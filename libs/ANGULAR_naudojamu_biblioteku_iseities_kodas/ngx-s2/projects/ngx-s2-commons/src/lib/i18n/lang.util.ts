import { TranslateService } from '@ngx-translate/core';

export const DEFAULT_LANGUAGE = 'lt';
export const LOCAL_STORAGE_LANG_KEY = 'lang';

/**
 * Gets language from localstorage
 */
export function getLang(): string {
    return localStorage.getItem(LOCAL_STORAGE_LANG_KEY);
}

/**
 * Sets language, updates localstorage ant ngx-translate language
 */
export function setLang(translate: TranslateService, lang: string): void {
    localStorage.setItem(LOCAL_STORAGE_LANG_KEY, lang);
    translate.use(lang);
}

/**
 * Used to init translations on app startup
 */
export function initLang(translate: TranslateService): void {
    const userLang = localStorage.getItem(LOCAL_STORAGE_LANG_KEY);
    // ------------ Isjungta nes nekorektiskai veikia kalbos setinimas is url parametru --------------
    // translate.setDefaultLang(DEFAULT_LANGUAGE);
    if (userLang != null) {
        translate.use(userLang);
    } else {
        setLang(translate, DEFAULT_LANGUAGE);
    }
}
