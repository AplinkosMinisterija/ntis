/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
/* eslint-disable @typescript-eslint/restrict-plus-operands */
/* eslint-disable @typescript-eslint/no-unsafe-call */
import { Component, ElementRef, EventEmitter, NgZone, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SwitchSelectedIndex } from '@itree-commons/src/lib/components/switch/switch.component';
import { ViispMockComponent } from '@itree-commons/src/lib/components/viisp-mock/viisp-mock.component';
import {
  ViispAuthData,
  ViispAuthRequest,
  ViispAuthResult,
  ViispMockAuthRequest,
} from '@itree-commons/src/lib/model/api/api';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices, DeprecatedBaseEditForm, getLang } from '@itree/ngx-s2-commons';
import { takeUntil } from 'rxjs';
import { AuthType, UserType } from '../../enums/auth.enums';
import { LoginData } from '../../models/login-data';
import { HeaderNav } from '@itree-commons/src/lib/components/header-nav/header-nav.component';
import { HeaderNavService } from '@itree-commons/src/lib/services/header-nav.service';
import { LOGIN_TYPE } from '@itree-commons/src/constants/localStorage.constants';

declare let EidWidget: {
  new (config: any): any; // Replace 'any' with the actual type if known
  init(): void;
  // Add other properties and methods as needed
};

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent extends DeprecatedBaseEditForm<void> implements OnInit, OnDestroy {
  @Output() login = new EventEmitter<LoginData>();
  @ViewChild(ViispMockComponent) viispMockComponent: ViispMockComponent;

  passwordFG: FormGroup;
  mobileSignatureFG: FormGroup;
  isLoginWithPassword: boolean = true;
  forgotPasswordLink = RoutingConst.FORGOT_PASSWORD;
  registerLink = RoutingConst.REGISTER;
  homeLink = RoutingConst.HOME;
  showSmartIdLogin: boolean = false;
  smartIdEnabled: string;
  readonly routingConst = RoutingConst;
  localStorageLoginType = localStorage.getItem(LOGIN_TYPE);
  protected loginType = RoutingConst.SEWERAGE;

  linksData: HeaderNav[] = [
    {
      linkTitle: '',
      linkType: `${RoutingConst.SEWERAGE}`,
      linkIcon: 'faUser',
    },
    {
      linkTitle: '',
      linkType: `${RoutingConst.SERVICE}`,
      linkIcon: 'faTruckField',
    },
    {
      linkTitle: '',
      linkType: `${RoutingConst.REGULATORY}`,
      linkIcon: 'faBuildingColumns',
    },
  ];

  private selectedUserType: string = UserType.PRIVATE_USER;

  viispAuthMockEnabled = false;
  readonly switchOptions = [
    {
      label: 'pages.login.loginPrivate',
      userType: UserType.PRIVATE_USER,
    },
    {
      label: 'pages.login.loginOrganization',
      userType: UserType.ORGANIZATION_USER,
    },
  ];
  switchSelectedIndex: SwitchSelectedIndex = 0;

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    protected override activatedRoute: ActivatedRoute,
    private authService: AuthService,
    public faIconsService: FaIconsService,
    private appDataService: AppDataService,
    private headerNavService: HeaderNavService
  ) {
    super(formBuilder, commonFormServices);
    this.viispAuthMockEnabled = this.appDataService.getAppData().springProfilesActive.includes('test');
    this.activatedRoute.queryParamMap.subscribe((params: ParamMap) => {
      const ticket = params.get('ticket');
      if (ticket) {
        this.authService.createSessionViisp(ticket).subscribe();
      }
    });
    this.smartIdEnabled = appDataService.getPropertyValue(AppDataService.SMART_ID);
  }

  override ngOnInit(): void {
    this.authService.loadedMenu$.subscribe(() => {
      this.linksData.forEach((element) => {
        this.commonFormServices.translate.get(`pages.login.${element.linkType}`).subscribe((translation: string) => {
          element.linkTitle = translation;
        });
      });
    });

    super.ngOnInit();

    this.getSelectedUserType(this.loginType);

    this.headerNavService.headerNav$.pipe(takeUntil(this.destroy$)).subscribe((data) => {
      if (data) {
        this.loginType = this.localStorageLoginType ? this.localStorageLoginType : data.linkType;
        this.getSelectedUserType(this.loginType);
      }
    });

    this.linksData.forEach((element) => {
      this.commonFormServices.translate.get(`pages.login.${element.linkType}`).subscribe((translation: string) => {
        element.linkTitle = translation;
      });
    });
  }

  setLinkType(item: HeaderNav): void {
    this.loginType = item.linkType;
    localStorage.setItem(LOGIN_TYPE, this.loginType);
    this.getSelectedUserType(item.linkType);
    this.headerNavService.sendMenuLink(item);
  }

  protected buildForm(): void {
    this.passwordFG = this.formBuilder.group({
      username: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
      phoneNumber: new FormControl(),
      personalCode: new FormControl(),
      name: new FormControl(null),
      surname: new FormControl(null),
      linkType: new FormControl(this.loginType),
    });
    this.mobileSignatureFG = this.formBuilder.group({
      username: new FormControl(),
      password: new FormControl(),
      phoneNumber: new FormControl(null, Validators.required),
      personalCode: new FormControl(null, Validators.required),
      name: new FormControl(),
      surname: new FormControl(),
      linkType: new FormControl(this.loginType),
    });
    this.form = this.passwordFG;
  }

  private getSelectedUserType(loginType?: string): void {
    this.selectedUserType = loginType === RoutingConst.SEWERAGE ? UserType.PRIVATE_USER : UserType.ORGANIZATION_USER;
  }

  onLoginMethodChange(isLoginWithPassword: boolean): void {
    this.isLoginWithPassword = isLoginWithPassword;
    if (!isLoginWithPassword) {
      this.form = this.mobileSignatureFG;
      this.form.reset();
      this.commonFormServices.appMessages.clearMessages();
    } else {
      this.form = this.passwordFG;
      this.form.reset();
      this.commonFormServices.appMessages.clearMessages();
    }
  }

  protected doSave(): void {
    this.login.emit({
      isLoginWithPass: this.isLoginWithPassword,
      userType: this.selectedUserType,
      username: this.form.controls.username.value as string,
      password: this.form.controls.password.value as string,
      name: this.form.controls.name.value as string,
      surname: this.form.controls.surname.value as string,
      phoneNumber: this.form.controls.phoneNumber.value as string,
      personalCode: this.form.controls.personalCode.value as string,
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  protected doLoad(): void {}
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  protected setData(): void {}
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  protected getData(): void {}

  initViispLogin(): void {
    const viispAuthRequest = this.createViispAuthRequest();
    viispAuthRequest.parameters['RETURN_URL'] = this.authService.getReturnUrl();
    viispAuthRequest.parameters['AUTH_TYPE'] = AuthType.VIISP_AUTH;
    viispAuthRequest.parameters['USER_TYPE'] = this.selectedUserType;
    this.authService.viispLogin(viispAuthRequest).subscribe((result: ViispAuthResult) => {
      window.location.href = result.viispUrlWithTicket;
    });
  }

  initViispLoginTest(): void {
    const viispAuthRequest = this.createViispAuthRequest();
    viispAuthRequest.parameters['RETURN_URL'] = this.authService.getReturnUrl();
    viispAuthRequest.parameters['AUTH_TYPE'] = AuthType.VIISP_AUTH;
    viispAuthRequest.parameters['USER_TYPE'] = this.selectedUserType;
    this.authService.viispLoginTest(viispAuthRequest).subscribe((result: ViispAuthResult) => {
      window.location.href = result.viispUrlWithTicket;
    });
  }

  initViispLoginMock(viispAuthData: ViispAuthData): void {
    const viispAuthRequest = { parameters: {} } as ViispMockAuthRequest;
    viispAuthRequest.parameters['RETURN_URL'] = this.authService.getReturnUrl();
    viispAuthRequest.parameters['AUTH_TYPE'] = AuthType.VIISP_AUTH;
    viispAuthRequest.parameters['USER_TYPE'] = this.selectedUserType;
    viispAuthRequest.parameters['LANGUAGE'] = getLang();
    viispAuthData.origin = window.location.origin;
    viispAuthRequest.mockAuthData = viispAuthData;
    this.authService.viispLoginMock(viispAuthRequest).subscribe((result: ViispAuthResult) => {
      window.location.href = result.viispUrlWithTicket;
    });
  }

  showViispLoginMock(): void {
    this.viispMockComponent.show();
  }

  createViispAuthRequest(): ViispAuthRequest {
    return { parameters: {} };
  }

  createSmartIdRequest(): void {
    this.authService.isenseSmartId().subscribe((res) => {
      this.showSmartIdLogin = true;
      const widgetConfig = {
        sessionToken: res.token,
        host: res.host,
        signatureProviders: ['SMARTID'],
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment, @typescript-eslint/no-unsafe-member-access
        element: document.getElementById('smart-id'),
        // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
        successCallback: (sessionToken: string) => {
          this.authService.completeSmartAuth(widgetConfig.sessionToken).subscribe(() => {
            this.showSmartIdLogin = false;
            widget.destroy();
          });
        },
        sessionExpireCallback: (sessionToken: unknown) => {
          this.showSmartIdLogin = false;
          widget.destroy();
        },
      };
      const widget = new EidWidget(widgetConfig);
      widget.init();
    });
  }
}
