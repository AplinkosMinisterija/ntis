import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientSideError } from '../model/common.api';

@Injectable()
export abstract class ClientLogService {
    constructor(private http: HttpClient) { }

    logClientError(clientSideError: ClientSideError): Observable<void> {
        return this.http.post<void>(this.getLogServiceApiUrl(), clientSideError);
    }

    abstract getLogServiceApiUrl(): string;
}
