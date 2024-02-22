import { Injectable } from '@angular/core';
import { AuthUtil, CommonAuthService, CommonFormServices, getLang, setLang } from '@itree/ngx-s2-commons';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  EmailVerificationRequest,
  LoginResult,
  NtisIsenseAuthModel,
  RoleFormsActions,
  SprBackendWebSessionInfo,
  ViispAuthRequest,
  ViispAuthResult,
  WebSessionInfo,
} from '@itree-commons/src/lib/model/api/api';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';
import { filter, map, tap } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, ResolveEnd, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprAvailableOrganization, SprAvailableRole } from '../model/auth';
import { SprRouteData } from '../types/routing';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { MenuStructure } from '../model/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class AuthService extends CommonAuthService {
  static readonly MERGED_ROLE_ID = -1;
  static readonly PUBLIC_ROLE_CODE = 'PUBLIC';
  static readonly DEFAULT_RETURN_URL = `${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}`;
  /**
   * Setting to force navigation to default form url on role/organization change.
   *
   * Set to `false` to navigate only if new role doesn't have a right to see the current page.
   */
  static readonly FORCE_NAVIGATE_TO_DEFAULT_FORM_URL: boolean = true;
  readonly authRestUrl = `${REST_API_BASE_URL}/auth`;
  readonly ntisCommonRestUrl = `${REST_API_BASE_URL}/ntis-common`;
  private loadedMenuValue: MenuStructure[] = null;
  currentRouteFormCode: string;
  loadedMenu$ = new Subject<MenuStructure[]>();
  loadedMenuType: 'public' | 'internal';
  loadedFormActions$ = new BehaviorSubject<RoleFormsActions>(null);
  availableOrganizations$ = new BehaviorSubject<SprAvailableOrganization[]>([]);
  availableRoles$ = new BehaviorSubject<SprAvailableRole[]>([]);

  constructor(
    protected override http: HttpClient,
    protected router: Router,
    protected activatedRoute: ActivatedRoute,
    protected translateService: TranslateService,
    private commonFormServices: CommonFormServices
  ) {
    super(http);

    this.router.events.pipe(filter((event) => event instanceof ResolveEnd)).subscribe((event) => {
      let route = (event as ResolveEnd).state.root;
      let formCode: string;
      while (route) {
        if ((route.data as SprRouteData).formCode) {
          formCode = (route.data as SprRouteData).formCode;
        }
        if (route.firstChild) {
          route = route.firstChild;
        } else {
          this.currentRouteFormCode = formCode;
          route = undefined;
        }
      }
    });
  }

  get loadedMenu(): MenuStructure[] {
    return this.loadedMenuValue;
  }

  set loadedMenu(value: MenuStructure[]) {
    this.loadedMenuValue = value;
    this.loadedMenu$.next(value);
  }

  get loadedFormActions(): RoleFormsActions {
    return this.loadedFormActions$.value;
  }

  set loadedFormActions(value: RoleFormsActions) {
    this.loadedFormActions$.next(value);
  }

  get availableOrganizations(): SprAvailableOrganization[] {
    return this.availableOrganizations$.value;
  }

  set availableOrganizations(value: SprAvailableOrganization[]) {
    this.availableOrganizations$.next(value);
  }

  get availableRoles(): SprAvailableRole[] {
    return this.availableRoles$.value;
  }

  set availableRoles(value: SprAvailableRole[]) {
    this.availableRoles$.next(value);
  }

  override processLogin(response: LoginResult<WebSessionInfo>, returnUrl: string, returnToDefaultForm = false): void {
    super.processLogin(response, returnUrl, false);
    if (!this.availableOrganizations?.length) {
      this.loadUserOrganizations();
    }
    if (!this.availableRoles?.length) {
      this.loadUserRoles();
    }
    if (response.session.usrPasswordChangeToken) {
      void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.SPARK_CHANGE_PASSWORD]);
    } else {
      if (returnToDefaultForm && (!returnUrl || returnUrl === AuthService.DEFAULT_RETURN_URL)) {
        this.loadAndGetFormActions().subscribe((result) => {
          void this.router.navigateByUrl(result.defaultFormUrl || AuthService.DEFAULT_RETURN_URL);
        });
      } else if (returnUrl) {
        void this.router.navigateByUrl(returnUrl);
      }
    }
  }

  checkSession(): Promise<boolean> {
    return new Promise((resolve) => {
      if (AuthUtil.isLoggedIn()) {
        const url = `${this.authRestUrl}/check-session`;
        this.http.get(url, { responseType: 'text' }).subscribe({
          next: () => {
            resolve(true);
          },
          error: () => {
            AuthUtil.clearSessionStorage();
            resolve(false);
          },
        });
      } else {
        resolve(false);
      }
    });
  }

  getReturnUrl(defaultUrl: string = AuthService.DEFAULT_RETURN_URL): string {
    return typeof this.activatedRoute.snapshot.queryParams.returnUrl === 'string' &&
      this.activatedRoute.snapshot.queryParams.returnUrl.length
      ? this.activatedRoute.snapshot.queryParams.returnUrl
      : defaultUrl;
  }

  isFormAvailable(formCode: string): boolean {
    return !!(formCode && this.loadedFormActions?.roleForms?.[formCode]?.availableActions?.length);
  }

  isFormActionEnabled(formCode: string, action: string): boolean {
    return this.loadedFormActions?.roleForms?.[formCode]?.availableActions?.includes(action);
  }

  getDefaultRoleUrl(): string {
    const url =
      this.loadedFormActions?.defaultFormUrl ||
      `/${AuthUtil.isLoggedIn() ? AuthService.DEFAULT_RETURN_URL : RoutingConst.HOME}`;
    return `${url.startsWith('/') ? '' : '/'}${url}`;
  }

  loginWithPassword(
    username: string,
    password: string,
    returnUrl: string,
    authExtData: Record<string, string>
  ): Observable<LoginResult<WebSessionInfo>> {
    return this.http
      .post<LoginResult<WebSessionInfo>>(`${this.authRestUrl}/login`, {
        username: username,
        password: password,
        authExtData,
      })
      .pipe(
        tap((response) => {
          this.processLogin(response, returnUrl, true);
        })
      );
  }

  logout(): Observable<null> {
    return this.http.post<null>(`${this.authRestUrl}/logout`, null).pipe(
      tap(() => {
        this.availableOrganizations = [];
        this.availableRoles = [];
        this.processLogout();
      })
    );
  }

  loadAndGetUserOrganizations(): Observable<SprAvailableOrganization[]> {
    const url = `${this.authRestUrl}/organizations`;
    return this.http.post<SprAvailableOrganization[]>(url, null).pipe(
      tap((response) => {
        this.availableOrganizations = response;
      })
    );
  }

  loadUserOrganizations(): void {
    this.loadAndGetUserOrganizations().subscribe();
  }

  getUserOrganizations(): Observable<SprAvailableOrganization[]> {
    return this.availableOrganizations && this.availableOrganizations.length > 0
      ? of(this.availableOrganizations)
      : this.loadAndGetUserOrganizations();
  }

  setUserOrganization(orgId: string): Observable<LoginResult<SprBackendWebSessionInfo>> {
    this.availableRoles = [];
    return this.http
      .post<LoginResult<SprBackendWebSessionInfo>>(`${this.authRestUrl}/set-organization`, { id: orgId })
      .pipe(
        tap((response) => {
          const returnUrl: string = null;
          this.processLogin(response as LoginResult<WebSessionInfo>, returnUrl);
          this.loadAndGetFormActions().subscribe(() => {
            if (response.session.roleId) {
              this.reloadPageAfterRoleChange();
            }
          });
          if (!response.session.roleId) {
            this.loadedMenu = [];
            this.loadedMenuType = 'internal';
            void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.ROLE_SELECT]);
          } else {
            this.loadMenu();
          }
        })
      );
  }

  loadAndGetUserRoles(): Observable<SprAvailableRole[]> {
    const url = REST_API_BASE_URL + (AuthUtil.isLoggedIn() ? '/auth/roles' : '/public/roles');
    return this.http.post<SprAvailableRole[]>(url, null).pipe(
      tap((response) => {
        this.availableRoles = response;
      })
    );
  }

  loadUserRoles(): void {
    this.loadAndGetUserRoles().subscribe();
  }

  getUserRoles(): Observable<SprAvailableRole[]> {
    if (this.availableRoles.length) {
      return of(this.availableRoles);
    } else {
      return this.loadAndGetUserRoles();
    }
  }

  checkUserPasswordExists(): Observable<boolean> {
    return this.http.get<boolean>(`${this.authRestUrl}/check-user-have-password`);
  }

  setUserRole(roleId: string): Observable<LoginResult<WebSessionInfo>> {
    return this.http.post<LoginResult<WebSessionInfo>>(`${this.authRestUrl}/set-role/`, { id: roleId }).pipe(
      tap((response) => {
        const returnUrl: string = null;
        this.processLogin(response, returnUrl);
        this.loadMenu();
        this.loadAndGetFormActions().subscribe(() => {
          this.reloadPageAfterRoleChange();
        });
      })
    );
  }

  loadAndGetPublicMenu(): Observable<MenuStructure[]> {
    return this.http
      .post<MenuStructure[]>(REST_API_BASE_URL + '/public/get-public-menu', {
        id: getLang(),
      })
      .pipe(
        tap((result: MenuStructure[]) => {
          this.loadedMenuType = 'public';
          this.loadedMenu = result;
        })
      );
  }

  loadPublicMenu(): void {
    this.loadAndGetPublicMenu().subscribe();
  }

  loadAndGetInternalMenu(): Observable<MenuStructure[]> {
    return this.http.post<MenuStructure[]>(`${this.authRestUrl}/get-internal-menu`, null).pipe(
      tap((result: MenuStructure[]) => {
        this.loadedMenuType = 'internal';
        this.loadedMenu = result;
      })
    );
  }

  loadInternalMenu(): void {
    this.loadAndGetInternalMenu().subscribe();
  }

  loadAndGetMenu(): Observable<MenuStructure[]> {
    if (AuthUtil.isLoggedIn()) {
      return this.loadAndGetInternalMenu();
    } else {
      return this.loadAndGetPublicMenu();
    }
  }

  loadMenu(): void {
    this.loadAndGetMenu().subscribe();
  }

  changeLanguage(translateService: TranslateService, languageCode: string): void {
    if (AuthUtil.isLoggedIn()) {
      this.http.post<unknown>(`${this.authRestUrl}/change-language`, { id: languageCode }).subscribe(() => {
        setLang(translateService, languageCode);
        const sessionInfo = AuthUtil.getSessionInfo();
        sessionInfo.language = languageCode;
        AuthUtil.updateSessionInfo(sessionInfo);
        this.loadMenu();
        window.location.reload();
      });
    } else {
      setLang(translateService, languageCode);
      this.loadMenu();
      window.location.reload();
    }
  }

  loadFormActions(): void {
    this.loadAndGetFormActions().subscribe();
  }

  loadAndGetFormActions(): Observable<RoleFormsActions> {
    const url = REST_API_BASE_URL + (AuthUtil.isLoggedIn() ? '/auth/get-role-forms' : '/public/get-role-forms');
    return this.http.post<RoleFormsActions>(url, null).pipe(
      tap((result) => {
        this.loadedFormActions = result;
      })
    );
  }

  confirmEmail(token: string): Observable<void> {
    return this.http.post<void>(`${this.authRestUrl}/confirm-email`, token);
  }

  requestEmailConfirmation(userId: string, email: string): Observable<void> {
    const body: EmailVerificationRequest = { userId, email };
    return this.http.post<void>(`${this.authRestUrl}/request-confirm-email`, body);
  }

  requestNewPassword(userName: string): Observable<void> {
    return this.http.post<void>(`${this.authRestUrl}/request-new-password`, {
      email: userName,
      lang: getLang(),
    });
  }

  resetPassword(oldPass: string, newPass: string): Observable<void> {
    return this.http.post<void>(`${this.authRestUrl}/change-password`, {
      oldPassword: oldPass,
      newPassword: newPass,
    });
  }

  resetPasswordByToken(password: string, token: string): Observable<void> {
    return this.http.post<void>(`${this.authRestUrl}/reset-password`, {
      token,
      password,
    });
  }

  setPasswordForNewUser(password: string, token: string): Observable<void> {
    return this.http.post<void>(`${this.authRestUrl}/reset-new-user-password`, {
      token,
      password,
    });
  }

  getUserPasswordParams(token?: string, requestType?: string): Observable<Record<string, string>> {
    const url = AuthUtil.isLoggedIn() ? 'get-user-password-params' : 'get-password-params';
    return this.http.get<Record<string, string>>(
      `${this.authRestUrl}/${url}`,
      token && requestType && { params: { token, requestType } }
    );
  }

  getPasswordRequirementsText(minLength: number, minDigits?: number, minCapital?: number): Observable<string> {
    return minLength
      ? this.translateService.get('common.error.passwordPattern.minLength', { value: minLength }).pipe(
          map((translation: string) => {
            let text = translation;
            if (minCapital || minDigits) {
              text = text + (this.translateService.instant('common.error.passwordPattern.ofWhich') as string);
              if (minCapital) {
                text =
                  text +
                  (this.translateService.instant(
                    'common.error.passwordPattern.minCapital' + (minCapital > 1 ? 'Plural' : 'Singular'),
                    {
                      value: minCapital,
                    }
                  ) as string);
              }
              if (minDigits) {
                if (minCapital) {
                  text = text + ', ';
                }
                text =
                  text +
                  (this.translateService.instant(
                    'common.error.passwordPattern.minDigits' + (minDigits > 1 ? 'Plural' : 'Singular'),
                    {
                      value: minDigits,
                    }
                  ) as string);
              }
              text = text + '.';
            }
            return text;
          })
        )
      : undefined;
  }

  acceptUserTerms(answer: string): Observable<null> {
    return this.http.post<null>(`${this.authRestUrl}/accept-user-terms`, answer);
  }

  viispLoginMock(request: ViispAuthRequest): Observable<ViispAuthResult> {
    return this.http.post<ViispAuthResult>(`${this.authRestUrl}/viisp/init-mock-viisp-auth`, request);
  }

  viispLogin(request: ViispAuthRequest): Observable<ViispAuthResult> {
    return this.http.post<ViispAuthResult>(`${this.authRestUrl}/viisp/init-viisp-auth`, request);
  }

  viispLoginTest(request: ViispAuthRequest): Observable<ViispAuthResult> {
    return this.http.post<ViispAuthResult>(`${this.authRestUrl}/viisp/init-test-viisp-auth`, request);
  }

  isenseSmartId(): Observable<NtisIsenseAuthModel> {
    return this.http.post<NtisIsenseAuthModel>(`${REST_API_BASE_URL}/auth/isense/start-isense-auth`, {});
  }

  completeSmartAuth(token: string): Observable<LoginResult<SprBackendWebSessionInfo>> {
    return this.http
      .post<LoginResult<SprBackendWebSessionInfo>>(
        `${REST_API_BASE_URL}/auth/isense/complete-isense-auth?isenseSessionToken=${token}`,
        {}
      )
      .pipe(
        map((response: LoginResult<SprBackendWebSessionInfo>) => {
          if (response.session) {
            this.processLogin(response, response.session.defaultRoute, true);
          }
          return response;
        })
      );
  }

  createSessionViisp(ticket: string): Observable<LoginResult<SprBackendWebSessionInfo>> {
    return this.http
      .post<LoginResult<SprBackendWebSessionInfo>>(`${this.authRestUrl}/viisp/create-session-viisp`, {
        payload: ticket,
      })
      .pipe(
        map((response: LoginResult<SprBackendWebSessionInfo>) => {
          if (response.session) {
            if (response.session.roleType == 'ORG_FROM_PRIVATE') {
              this.commonFormServices.confirmationService.confirm({
                message: this.commonFormServices.translate.instant('common.message.viispIndividualLogin') as string,
                accept: () => {
                  this.processLogin(response, response.session.defaultRoute, true);
                },
                reject: () => {
                  this.processLogin(response, null, false);
                  this.http
                    .post<null>(`${this.ntisCommonRestUrl}/delete-user-and-org`, null)
                    .pipe(
                      map(() => {
                        this.logout().subscribe(() => {
                          void this.commonFormServices.router.navigateByUrl(RoutingConst.LOGIN);
                          setTimeout(() => {
                            this.commonFormServices.translate
                              .get('common.error.userNotFound')
                              .subscribe((translation: string) => {
                                this.commonFormServices.appMessages.showError('', translation);
                              });
                          }, 500);
                        });
                      })
                    )
                    .subscribe();
                },
              });
            } else {
              this.processLogin(response, response.session.defaultRoute, true);
            }
          }
          return response;
        })
      );
  }

  private reloadPageAfterRoleChange(): void {
    if (
      !AuthService.FORCE_NAVIGATE_TO_DEFAULT_FORM_URL &&
      (!this.currentRouteFormCode || this.isFormAvailable(this.currentRouteFormCode))
    ) {
      const currentUrl = this.router.url;
      void this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        void this.router.navigate([currentUrl]);
      });
    } else {
      if (this.getDefaultRoleUrl() === this.router.url) {
        void this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          void this.router.navigate([this.getDefaultRoleUrl()]);
        });
      } else {
        void this.router.navigate([this.getDefaultRoleUrl()]);
      }
    }
  }

  checkIfPersonHasEmail(): Observable<boolean> {
    return this.http.get<boolean>(`${this.ntisCommonRestUrl}/check-person-has-email`);
  }
}
