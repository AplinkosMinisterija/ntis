import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ForgotPasswordPageComponent } from './pages/forgot-password-page/forgot-password-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { LoginComponent } from './components/login/login.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { NgModule } from '@angular/core';
import { PublicRoutingModule } from './public-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ResetPasswordPageComponent } from './pages/reset-password-page/reset-password-page.component';
import { TermsAndConditionsPageComponent } from './pages/terms-and-conditions-page/terms-and-conditions-page.component';
import { ConfirmEmailPageComponent } from './pages/confirm-email-page/confirm-email-page.component';
import { NtisSharedModule } from '../ntis-shared/ntis-shared.module';
import { PublicNewsListComponent } from './pages/public-news-list/public-news-list.component';
import { PublicNewsViewComponent } from './pages/public-news-view/public-news-view.component';
import { DialogModule } from 'primeng/dialog';

@NgModule({
  declarations: [
    ConfirmEmailPageComponent,
    ForgotPasswordPageComponent,
    HomePageComponent,
    LoginComponent,
    LoginPageComponent,
    ResetPasswordPageComponent,
    TermsAndConditionsPageComponent,
    PublicNewsListComponent,
    PublicNewsViewComponent,
  ],
  imports: [
    CommonModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    ItreeCommonsModule,
    PublicRoutingModule,
    NtisSharedModule,
    DialogModule,
  ],
})
export class PublicModule {}
