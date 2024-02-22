import { LoginResult, WebSessionInfo } from '../model/common.api';
export declare const MULTI_TAB_KEY = "multitab";
export declare const SESSION_KEY = "session";
export declare const TERMS_KEY = "terms";
export declare const SES_VALUE_TRUE = "T";
export declare const SES_VALUE_FALSE = "F";
export declare class AuthUtil {
    private static copyFromSessionStorageToLocalStorage;
    static setMultiTabSession(multiTabSession: boolean): void;
    static isMultiTabSession(): boolean;
    static clearSessionStorage(): void;
    static getJWTFromSession(): string;
    static getSessionInfo(): WebSessionInfo;
    static updateSessionInfo(session: WebSessionInfo): void;
    static getLoginResult(): LoginResult<WebSessionInfo>;
    static setLoginResult(loginResult: LoginResult<WebSessionInfo>): void;
    static isLoggedIn(): boolean;
    static isTermsAccepted(): boolean;
    static setTermsAccepted(accept: boolean): void;
    static getRoleCode(): string;
    static getRoleId(): number;
    static getRoleName(): string;
    static getOrgId(): number;
    static getOrgName(): string;
    static getLanguage(): string;
}
