import { TranslateService } from '@ngx-translate/core';
export declare class MessageResolver {
    private static readonly ERROR_KEY_PREFIX;
    /**
     * Jeigu fielde per [errorDefs] nurodyti nestandartiniai validacijos pranešimai, tai pirmiausia naudojame juos:
     */
    private static getErrorCodePrefix;
    static resolveError(translate: TranslateService, errors: Record<string, unknown>, customErrors: Record<string, string>, fullErrorCode?: boolean): string[];
    static resolveErrorToString(translate: TranslateService, errors: Record<string, unknown>, customErrors: Record<string, string>, fullErrorCode?: boolean): string;
}
