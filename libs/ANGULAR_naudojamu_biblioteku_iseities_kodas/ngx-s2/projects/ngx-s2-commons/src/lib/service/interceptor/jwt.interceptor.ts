import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { AuthUtil } from '../../auth/auth.util';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // console.log('JwtInterceptor');
    const clonedRequest = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + AuthUtil.getJWTFromSession()),
    });
    return next.handle(clonedRequest);
  }
}

export const jwtProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: JwtInterceptor,
  multi: true,
};
