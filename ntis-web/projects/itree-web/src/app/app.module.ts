import {
  AppMissingTranslationHandler,
  ClientLogService,
  CommonFormServices,
  contentTypeInterceptor,
  jwtProvider,
  s2Provider,
} from '@itree/ngx-s2-commons';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { AppClientLogService } from './services/app.client.log.service';
import { AppComponent } from './app.component';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { AppRoutingModule } from './app-routing.module';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmExitGuard } from './guard/confirm-exit.guard';
import { DatePipe } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClient } from '@angular/common/http';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MissingTranslationHandler, TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { SprCanActivateGuard } from './guard/can-activate.guard';
import { sprErrorHandlerProvider } from '@itree-commons/src/lib/errors/error-handler';
import { SprOrgSelectGuard } from './guard/org-select.guard';
import { SprRoleSelectGuard } from './guard/role-select.guard';
import { ToastModule } from 'primeng/toast';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { WebsocketService } from '@itree-commons/src/lib/services/websocket.service';
import { CustomTranslateLoader } from './custom-translate-loader';

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.txt');
}

export function appDataProviderFactory(provider: AppDataService): () => Promise<boolean> {
  return () => provider.load();
}

export function authServiceProviderFactory(provider: AuthService): () => Promise<boolean> {
  return () => provider.checkSession();
}

export function customTranslateLoaderFactory(http: HttpClient): TranslateLoader {
  return new CustomTranslateLoader(http, 'assets/i18n/', '.txt');
}

@NgModule({
  declarations: [AppComponent],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    ConfirmDialogModule,
    FontAwesomeModule,
    ItreeCommonsModule,
    ToastModule,
    TranslateModule.forRoot({
      missingTranslationHandler: { provide: MissingTranslationHandler, useClass: AppMissingTranslationHandler },
      useDefaultLang: true,
      defaultLanguage: 'lt',
      loader: {
        provide: TranslateLoader,
        useClass: CustomTranslateLoader,
        useFactory: customTranslateLoaderFactory,
        deps: [HttpClient],
      },
      isolate: false,
    }),
  ],
  providers: [
    WebsocketService,
    AppDataService,
    AuthService,
    CommonFormServices,
    ConfirmExitGuard,
    ConfirmationService,
    DatePipe,
    MessageService,
    S2DatePipe,
    SprCanActivateGuard,
    SprOrgSelectGuard,
    SprRoleSelectGuard,
    contentTypeInterceptor,
    jwtProvider,
    s2Provider,
    sprErrorHandlerProvider,
    {
      provide: ClientLogService,
      useClass: AppClientLogService,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appDataProviderFactory,
      deps: [AppDataService],
      multi: true,
    },
    {
      provide: APP_INITIALIZER,
      useFactory: authServiceProviderFactory,
      deps: [AuthService],
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
