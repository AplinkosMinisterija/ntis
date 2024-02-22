import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthEvent } from '../../auth/auth.event';
import { S2Error } from '../../error/s2error';
import { ServerError } from '../../error/servererror';
import { UnauthorizedError } from '../../error/unauthorized';
import { LoggerFactory } from '../../log/logger.factory';
import { S2Message } from '../../model/common.api';
import { LoaderService } from '../loader.service';
import { ServerUnavailableError } from '../../error/server-unavailable-error';

@Injectable()
export class S2Interceptor implements HttpInterceptor {
  log = LoggerFactory.getLogger();

  constructor(private loaderService: LoaderService) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!req.headers.has('x-s2-noloader')) {
      this.loaderService.show(req.url);
    }
    return next.handle(req).pipe(
      tap({
        next: (event) => {
          if (event instanceof HttpResponse) {
            if (!req.headers.has('x-s2-noloader')) {
              this.loaderService.hide();
            }

            const s2Status = event.headers.get('X-S2-status');
            this.log.debug(`X-S2-status=${s2Status}`);
            if (s2Status === 'ERR') {
              const messages = event.body as S2Message[];
              this.log.debug(messages);
              throw new S2Error(messages);
            }
          }
        },
        error: (err) => {
          if (!req.headers.has('x-s2-noloader')) {
            this.loaderService.hide();
          }
          this.log.error('HTTP error', err);
          if (err instanceof HttpErrorResponse) {
            this.log.debug(`${err.status} ' - system error:'}`);
            switch (err.status) {
              case 403:
              case 401:
                AuthEvent.isUnauthError = true;
                throw new UnauthorizedError();
              case 500:
                throw new ServerError(err.headers.get('X-S2-Err-UUID'));
              case 503:
              case 504:
                throw new ServerUnavailableError();
            }
          }
        },
      })
    );
  }
}

export const s2Provider = {
  provide: HTTP_INTERCEPTORS,
  useClass: S2Interceptor,
  multi: true,
};
