import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { LoginResult, WebSessionInfo } from '../model/common.api';
import * as i0 from "@angular/core";
export declare class CommonAuthService {
    protected http: HttpClient;
    constructor(http: HttpClient);
    protected processLogin(response: LoginResult<WebSessionInfo>, returnUrl: string, checkForPasswordChangeToken?: boolean): void;
    protected getRegistrationStatus(): Observable<string>;
    protected processLogout(): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<CommonAuthService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<CommonAuthService>;
}
