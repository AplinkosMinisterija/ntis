import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '../../constants/rest.constants';
import { AppData, IdKeyValuePair } from '../model/api/api';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';

@Injectable()
export class AppDataService {
  public static readonly MULTI_TAB_SESSION_ENABLED = 'MULTI_TAB_SESSION_ENABLED';
  public static readonly UPLOAD_FILE_SIZE = 'UPLOAD_FILE_SIZE';
  public static readonly SMART_ID = 'iSense-identification-ENABLED';

  private appData: AppData;

  constructor(private http: HttpClient) {}

  load(): Promise<boolean> {
    return new Promise((resolve) => {
      this.http.get<AppData>(REST_API_BASE_URL + '/common/appData').subscribe({
        next: (res) => {
          this.appData = res;
          const isMultiTabEnabled = this.appData.properties.some(
            (valuePair) =>
              valuePair.id === AppDataService.MULTI_TAB_SESSION_ENABLED && valuePair.value === DB_BOOLEAN_TRUE
          );
          AuthUtil.setMultiTabSession(isMultiTabEnabled);
          resolve(true);
        },
        error: () => {
          resolve(false);
          throw new Error('Unable to load settings');
        },
      });
    });
  }

  public getAppData(): AppData {
    return this.appData;
  }

  public getPropertyValue(propertyName: string): string {
    return this.appData.properties.find((idKeyValuePair) => idKeyValuePair.id === propertyName)?.value;
  }

  public getLanguages(): IdKeyValuePair[] {
    return this.appData.languages;
  }

  public multilanguageExists(): boolean {
    return this.appData.languages.length > 1;
  }
}
