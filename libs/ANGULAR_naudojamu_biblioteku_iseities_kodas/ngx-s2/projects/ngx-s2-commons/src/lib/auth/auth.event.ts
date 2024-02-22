import { Subject } from 'rxjs';
import { WebSessionInfo } from '../model/common.api';

export class AuthEvent {
  public static readonly AUTH_EVENT_LOGIN = 'LOGIN';
  public static readonly AUTH_EVENT_LOGOUT = 'LOGOUT';
  public static readonly AUTH_EVENT_NO_AUTH = 'NO_AUTH';
  public static readonly AUTH_EVENT_401 = '401';
  public static readonly AUTH_EVENT_OTHER_USER = 'OTHER_USER';
  public static readonly AUTH_EVENT_UPDATE = 'UPDATE';

  public static isUnauthError = false;

  public static userLoggedIn: Subject<AuthEvent> = new Subject();

  constructor(
    public userLoggedIn: boolean,
    public session: WebSessionInfo,
    public type: string,
    public regStatus?: string,
    public returnUrl?: string
  ) {}
}
