import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthEvent, ExtendedSearchParam, Spr_paging_ot, getLang } from '@itree/ngx-s2-commons';
import { REST_API_BASE_URL, REST_COMMON_CONTROLER } from '@itree-commons/src/constants/rest.constants';
import { Observable, of, tap } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import {
  BrowseFormSearchResult,
  FormPredefinedData,
  Request4FormsDefinedData,
  SprListIdKeyValue,
  SprNotificationsDAO,
} from '../model/api/api';
import { CommonResult } from '../model/api';
import { SprNotificationsBrowseRow } from '../model/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class CommonService {
  readonly commonRestUrl = `${REST_API_BASE_URL}/common`;

  constructor(private http: HttpClient) {
    AuthEvent.userLoggedIn.subscribe((result) => {
      if (result.userLoggedIn) {
        this.clearClassifersCache();
      }
    });
  }

  protected classifiersCache: Record<string, Map<string, SprListIdKeyValue[]>> = {};

  clearClassifersCache(): void {
    this.classifiersCache = {};
  }

  getClsf(
    code: string,
    useCache = true,
    customClassifier?: boolean,
    parameters?: Record<string, string>
  ): Observable<SprListIdKeyValue[]> {
    const lang = getLang();
    if (!this.classifiersCache[lang]) {
      this.classifiersCache[lang] = new Map<string, SprListIdKeyValue[]>();
    }

    if (useCache && this.classifiersCache[lang].get(code)) {
      return of(this.classifiersCache[lang].get(code));
    } else if (customClassifier) {
      return this.http
        .post<SprListIdKeyValue[]>(`${REST_API_BASE_URL + REST_COMMON_CONTROLER}`, { code, lang, parameters })
        .pipe(
          map((res) => {
            return res;
          })
        );
    } else {
      return this.http.get<SprListIdKeyValue[]>(`${REST_API_BASE_URL}/common/ref-codes/${lang}/${code}`).pipe(
        tap((res) => {
          if (useCache) {
            if (!this.classifiersCache[lang]) {
              this.classifiersCache[lang] = new Map<string, SprListIdKeyValue[]>();
            }
            this.classifiersCache[lang].set(code, res);
          }
          return res;
        })
      );
    }
  }

  getFormPredefinedData(formCode: string, formId?: string, language?: string): Observable<FormPredefinedData> {
    const url = `${this.commonRestUrl}/get-form-predefined-data`;
    const body: Request4FormsDefinedData = { formCode, formId: formId || null, language: language || null };
    return this.http.post<FormPredefinedData>(url, body);

    // const body:
  }

  // Notifications - start
  getNotification(): Observable<CommonResult<SprNotificationsDAO>> {
    return this.http.get<CommonResult<SprNotificationsDAO>>(this.commonRestUrl + '/get-user-notifications');
  }

  markAsRead(id: number): Observable<CommonResult<SprNotificationsDAO>> {
    return this.http.post<CommonResult<SprNotificationsDAO>>(this.commonRestUrl + '/mark-user-notification-as-read', {
      id,
    });
  }

  getNotificationList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprNotificationsBrowseRow>> {
    const url = this.commonRestUrl + '/get-user-notifications-list';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprNotificationsBrowseRow>>(url, myPostBody);
  }

  deleteNotification(id: string): Observable<null> {
    return this.http.post<null>(this.commonRestUrl + '/del-user-notification', { id });
  }
  // Notifications - ends
}
