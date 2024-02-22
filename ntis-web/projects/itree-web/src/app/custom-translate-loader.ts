import { TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

export class CustomTranslateLoader implements TranslateLoader {
  constructor(private http: HttpClient, private prefix: string = 'assets/i18n/', private suffix: string = '.txt') {}

  public getTranslation(lang: string): Observable<Record<string, string>> {
    const url = `${this.prefix}${lang}${this.suffix}`;
    return this.http.get(url, { responseType: 'text' }).pipe(
      map((data) => {
        const translations: Record<string, string> = {};
        const lines = data.split('\n');
        lines.forEach((line) => {
          const [key, value] = line.split('=');
          if (key && value) {
            translations[key.trim()] = value.trim();
          }
        });
        return translations;
      }),
      catchError(() => {
        console.error(`Couldn't load translations from ${url}`);
        return of({});
      })
    );
  }
}
