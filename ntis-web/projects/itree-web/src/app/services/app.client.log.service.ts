import { Injectable } from '@angular/core';
import { ClientLogService } from '@itree/ngx-s2-commons';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';

@Injectable({
  providedIn: 'root',
})
export class AppClientLogService extends ClientLogService {
  getLogServiceApiUrl(): string {
    return REST_API_BASE_URL + '/client-log/log-error';
  }
}
