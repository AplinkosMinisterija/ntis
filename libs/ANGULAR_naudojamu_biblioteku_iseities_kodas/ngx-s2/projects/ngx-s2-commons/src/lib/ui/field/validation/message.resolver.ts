import { TranslateService } from '@ngx-translate/core';

export class MessageResolver {
  private static readonly ERROR_KEY_PREFIX = 'common.error';

  /**
   * Jeigu fielde per [errorDefs] nurodyti nestandartiniai validacijos pranešimai, tai pirmiausia naudojame juos:
   */
  private static getErrorCodePrefix(key: string, customErrors: Record<string, string>): string {
    return customErrors?.[key] || MessageResolver.ERROR_KEY_PREFIX;
  }

  static resolveError(
    translate: TranslateService,
    errors: Record<string, unknown>,
    customErrors: Record<string, string>,
    fullErrorCode = false
  ): string[] {
    const rez: string[] = [];
    // jeigu field turi validacijos klaidų:
    if (errors) {
      // ištraukiame iš validacijos rezultatų objekto klaidų kodus:

      const errorKeys = Object.keys(errors);
      errorKeys.forEach((key) => {
        let prefix = '';
        if (!fullErrorCode) {
          prefix = MessageResolver.getErrorCodePrefix(key, customErrors) + '.';
        }
        // tada atliekame vertimą per servisą:

        rez.push(translate.instant(prefix + key, errors[key]) as string);
      });
    }
    return rez;
  }

  static resolveErrorToString(
    translate: TranslateService,
    errors: Record<string, unknown>,
    customErrors: Record<string, string>,
    fullErrorCode = false
  ): string {
    let errorMessage = '';
    const messages = MessageResolver.resolveError(translate, errors, customErrors, fullErrorCode);
    messages.forEach((msg, idx) => {
      errorMessage += msg;
      if (idx < messages.length - 1) {
        errorMessage += '. ';
      }
    });
    return errorMessage;
  }
}
