import { LoginResult, WebSessionInfo } from '../model/common.api';

export const MULTI_TAB_KEY = 'multitab';
export const SESSION_KEY = 'session';
export const TERMS_KEY = 'terms';

export const SES_VALUE_TRUE = 'T';
export const SES_VALUE_FALSE = 'F';

export class AuthUtil {
  private static copyFromSessionStorageToLocalStorage(keys: string[]): void {
    keys.forEach((key) => {
      const value = sessionStorage.getItem(key);
      if (value !== null) {
        localStorage.setItem(key, value);
      }
    });
  }

  public static setMultiTabSession(multiTabSession: boolean): void {
    if (multiTabSession) {
      if (localStorage.getItem(MULTI_TAB_KEY) !== SES_VALUE_TRUE) {
        localStorage.setItem(MULTI_TAB_KEY, SES_VALUE_TRUE);
        this.copyFromSessionStorageToLocalStorage([SESSION_KEY, TERMS_KEY]);
      }
    } else {
      localStorage.setItem(MULTI_TAB_KEY, SES_VALUE_FALSE);
      localStorage.removeItem(SESSION_KEY);
      localStorage.removeItem(TERMS_KEY);
    }
  }

  public static isMultiTabSession(): boolean {
    return localStorage.getItem(MULTI_TAB_KEY) === SES_VALUE_TRUE;
  }

  public static clearSessionStorage(): void {
    if (AuthUtil.isMultiTabSession()) {
      localStorage.removeItem(SESSION_KEY);
      localStorage.removeItem(TERMS_KEY);
    }
    sessionStorage.clear();
  }

  public static getJWTFromSession(): string {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.token : null;
  }

  public static getSessionInfo(): WebSessionInfo {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session : null;
  }

  public static updateSessionInfo(session: WebSessionInfo): void {
    const loginResult = AuthUtil.getLoginResult();
    loginResult.session = session;
    if (AuthUtil.isMultiTabSession()) {
      localStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
    } else {
      sessionStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
    }
  }

  public static getLoginResult(): LoginResult<WebSessionInfo> {
    const sessionData = AuthUtil.isMultiTabSession()
      ? localStorage.getItem(SESSION_KEY)
      : sessionStorage.getItem(SESSION_KEY);
    return sessionData ? (JSON.parse(sessionData) as LoginResult<WebSessionInfo>) : null;
  }

  public static setLoginResult(loginResult: LoginResult<WebSessionInfo>): void {
    if (AuthUtil.isMultiTabSession()) {
      localStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
    } else {
      sessionStorage.setItem(SESSION_KEY, JSON.stringify(loginResult));
    }
  }

  public static isLoggedIn(): boolean {
    return this.getLoginResult() !== null;
  }

  public static isTermsAccepted(): boolean {
    return (
      (AuthUtil.isMultiTabSession() ? localStorage.getItem(TERMS_KEY) : sessionStorage.getItem(TERMS_KEY)) ===
      SES_VALUE_TRUE
    );
  }

  public static setTermsAccepted(accept: boolean): void {
    const value = accept ? SES_VALUE_TRUE : SES_VALUE_FALSE;
    if (AuthUtil.isMultiTabSession()) {
      localStorage.setItem(TERMS_KEY, value);
    } else {
      sessionStorage.setItem(TERMS_KEY, value);
    }
  }

  public static getRoleCode(): string {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session.roleCode : null;
  }

  public static getRoleId(): number {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session.roleId : null;
  }

  public static getRoleName(): string {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session.roleName : null;
  }

  public static getOrgId(): number {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session.orgId : null;
  }

  public static getOrgName(): string {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session.orgName : null;
  }

  public static getLanguage(): string {
    const loginResult = AuthUtil.getLoginResult();
    return loginResult ? loginResult.session.language : null;
  }
}
