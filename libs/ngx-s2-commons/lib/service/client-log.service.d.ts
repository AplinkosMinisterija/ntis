import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientSideError } from '../model/common.api';
import * as i0 from "@angular/core";
export declare abstract class ClientLogService {
    private http;
    constructor(http: HttpClient);
    logClientError(clientSideError: ClientSideError): Observable<void>;
    abstract getLogServiceApiUrl(): string;
    static ɵfac: i0.ɵɵFactoryDeclaration<ClientLogService, never>;
    static ɵprov: i0.ɵɵInjectableDeclaration<ClientLogService>;
}
