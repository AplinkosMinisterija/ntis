import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as i0 from "@angular/core";
export declare class ContentTypeInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>>;
    static ɵfac: i0.ɵɵFactoryDeclaration<ContentTypeInterceptor, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<ContentTypeInterceptor>;
}
export declare const contentTypeInterceptor: {
    provide: import("@angular/core").InjectionToken<HttpInterceptor[]>;
    useClass: typeof ContentTypeInterceptor;
    multi: boolean;
};
