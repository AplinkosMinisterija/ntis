import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoaderService } from '../loader.service';
import * as i0 from "@angular/core";
export declare class S2Interceptor implements HttpInterceptor {
    private loaderService;
    log: import("../../log/logger.service").Logger;
    constructor(loaderService: LoaderService);
    intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>>;
    static ɵfac: i0.ɵɵFactoryDeclaration<S2Interceptor, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<S2Interceptor>;
}
export declare const s2Provider: {
    provide: import("@angular/core").InjectionToken<HttpInterceptor[]>;
    useClass: typeof S2Interceptor;
    multi: boolean;
};
