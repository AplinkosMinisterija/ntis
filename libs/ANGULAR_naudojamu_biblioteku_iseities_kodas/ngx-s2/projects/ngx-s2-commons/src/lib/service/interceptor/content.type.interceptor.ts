import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class ContentTypeInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (
      request.method === 'POST' &&
      !request.headers.get('Content-Type') &&
      (request.body === undefined || request.body === null)
    ) {
      const clonedRequest = request.clone({
        headers: request.headers.set('Content-Type', 'application/json'),
      });
      return next.handle(clonedRequest);
    }
    return next.handle(request);
  }
}

export const contentTypeInterceptor = {
  provide: HTTP_INTERCEPTORS,
  useClass: ContentTypeInterceptor,
  multi: true,
};
