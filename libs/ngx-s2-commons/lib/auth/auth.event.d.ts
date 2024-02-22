import { Subject } from 'rxjs';
import { WebSessionInfo } from '../model/common.api';
export declare class AuthEvent {
    userLoggedIn: boolean;
    session: WebSessionInfo;
    type: string;
    regStatus?: string;
    returnUrl?: string;
    static readonly AUTH_EVENT_LOGIN = "LOGIN";
    static readonly AUTH_EVENT_LOGOUT = "LOGOUT";
    static readonly AUTH_EVENT_NO_AUTH = "NO_AUTH";
    static readonly AUTH_EVENT_401 = "401";
    static readonly AUTH_EVENT_OTHER_USER = "OTHER_USER";
    static readonly AUTH_EVENT_UPDATE = "UPDATE";
    static isUnauthError: boolean;
    static userLoggedIn: Subject<AuthEvent>;
    constructor(userLoggedIn: boolean, session: WebSessionInfo, type: string, regStatus?: string, returnUrl?: string);
}
