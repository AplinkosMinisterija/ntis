import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';
import { mockApi } from './mock.api';

@Injectable()
export class MockHttpInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const url: string = request.url;
    const method: string = request.method;
    return mockApi(url, method, request) || next.handle(request);
  }
}

export const mockApiProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: MockHttpInterceptor,
  multi: true,
};
