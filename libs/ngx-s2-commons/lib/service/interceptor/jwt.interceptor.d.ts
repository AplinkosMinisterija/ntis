import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as i0 from "@angular/core";
export declare class JwtInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>>;
    static ɵfac: i0.ɵɵFactoryDeclaration<JwtInterceptor, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<JwtInterceptor>;
}
export declare const jwtProvider: {
    provide: import("@angular/core").InjectionToken<HttpInterceptor[]>;
    useClass: typeof JwtInterceptor;
    multi: boolean;
};
