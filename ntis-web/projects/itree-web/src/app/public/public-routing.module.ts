import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ConfirmEmailPageComponent } from './pages/confirm-email-page/confirm-email-page.component';
import { ForgotPasswordPageComponent } from './pages/forgot-password-page/forgot-password-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { ResetPasswordFormMode } from './enums/password-reset-page.enums';
import { ResetPasswordPageComponent } from './pages/reset-password-page/reset-password-page.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { TermsAndConditionsPageComponent } from './pages/terms-and-conditions-page/terms-and-conditions-page.component';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { PublicNewsListComponent } from './pages/public-news-list/public-news-list.component';
import { PublicNewsViewComponent } from './pages/public-news-view/public-news-view.component';
import { NewsViewResolver } from '../spark-admin-module/admin-resolvers/news-view.resolver';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { SPR_FAQ_LIST, SPR_FAQ_THEMES_LIST } from '../ntis-shared/constants/forms.const';
import { FaqThemesListComponent } from '../ntis-shared/pages/faq/faq-themes-list/faq-themes-list.component';
import { FaqListComponent } from '../ntis-shared/pages/faq/faq-list/faq-list.component';

const routes: SprRoutes = [
  {
    path: '',
    children: [
      {
        path: RoutingConst.HOME,
        pathMatch: 'full',
        redirectTo: AuthUtil.isLoggedIn()
          ? `/${RoutingConst.INTERNAL}/${RoutingConst.DASHBOARD}`
          : `/${RoutingConst.HOME}`,
      },
      {
        path: RoutingConst.HOME,
        component: HomePageComponent,
      },
      {
        path: RoutingConst.NEWS_LIST,
        children: [
          {
            path: '',
            component: PublicNewsListComponent,
            children: [
              {
                path: RoutingConst.PARAM_TYPE,
                component: PublicNewsListComponent,
              },
            ],
          },
          {
            path: RoutingConst.VIEW_WITH_ID,
            component: PublicNewsViewComponent,
            resolve: {
              newsViewData: NewsViewResolver,
            },
          },
        ],
      },
      {
        path: RoutingConst.LOGIN,
        data: { breadcrumb: 'pages.login.login' },
        children: [
          { path: '', component: LoginPageComponent },
          {
            path: RoutingConst.FORGOT_PASSWORD,
            component: ForgotPasswordPageComponent,
            data: { breadcrumb: 'pages.forgotPassword.headerText' },
          },
          {
            path: RoutingConst.HOME,
            component: HomePageComponent,
            data: { breadcrumb: 'pages.home.headerText', breadcrumbIcon: { iconStyle: 'fas', iconName: 'faHome' } },
          },
        ],
      },
      {
        path: RoutingConst.RESET_PASSWORD + '/:token',
        component: ResetPasswordPageComponent,
        data: { mode: ResetPasswordFormMode.RESET_PASSWORD_MODE },
      },
      {
        path: RoutingConst.NEW_USER_PASSWORD + '/:token',
        component: ResetPasswordPageComponent,
        data: { mode: ResetPasswordFormMode.NEW_USER_MODE },
      },
      {
        path: RoutingConst.CONFIRM_EMAIL + '/:token',
        component: ConfirmEmailPageComponent,
      },
      {
        path: RoutingConst.TERMS_AND_CONDITIONS,
        component: TermsAndConditionsPageComponent,
      },
      {
        path: NtisRoutingConst.MAP,
        pathMatch: 'full',
        redirectTo: AuthUtil.isLoggedIn()
          ? `/${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}`
          : `/${NtisRoutingConst.MAP}`,
      },
      {
        path: NtisRoutingConst.MAP,
        pathMatch: 'full',
        loadComponent: () => import('../ntis-shared/components/map/map.component').then((m) => m.NtisMapComponent),
      },
      {
        path: RoutingConst.SPARK_FAQ,
        data: { breadcrumb: 'pages.sprFaq.chooseTheme', formCode: SPR_FAQ_THEMES_LIST },
        children: [
          { path: '', component: FaqThemesListComponent },
          {
            path: `${RoutingConst.PARAM_ID}`,
            component: FaqListComponent,
            data: {
              breadcrumb: 'pages.sprFaq.headerText',
              formCode: SPR_FAQ_LIST,
            },
          },
          {
            path: `${RoutingConst.PARAM_ID}/:fac_id`,
            component: FaqListComponent,
            data: {
              breadcrumb: 'pages.sprFaq.headerText',
              formCode: SPR_FAQ_LIST,
            },
          },
        ],
      },

      {
        path: RoutingConst.PAGENOTFOUND,
        component: NotFoundPageComponent,
      },

      {
        path: '**',
        redirectTo: AuthUtil.isLoggedIn()
          ? `/${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}`
          : `/${NtisRoutingConst.MAP}`,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PublicRoutingModule {}
