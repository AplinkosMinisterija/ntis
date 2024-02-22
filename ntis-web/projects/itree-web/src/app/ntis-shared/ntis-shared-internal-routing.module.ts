import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { NTIS_NOTIFICATIONS_LIST, NTIS_NOTIFICATION_VIEW, NTIS_REVIEW_CREATE } from './constants/forms.const';
import { NotificationsListComponent } from './pages/notifications-list/notifications-list.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NotificationViewPageComponent } from './pages/notification-view-page/notification-view-page.component';
import { NotificationViewResolver } from './resolvers/notification-view.resolver';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: NtisRoutingConst.NOTIFICATIONS_LIST,
        data: {
          breadcrumb: 'ntisShared.pages.notificationsList.breadcrumb',
          formCode: NTIS_NOTIFICATIONS_LIST,
        },
        children: [
          { path: '', component: NotificationsListComponent },
          {
            path: RoutingConst.VIEW_WITH_ID,
            data: {
              breadcrumb: 'ntisShared.pages.notificationView.headerText',
              formCode: NTIS_NOTIFICATION_VIEW,
            },
            runGuardsAndResolvers: 'always',
            component: NotificationViewPageComponent,
            resolve: {
              ntfData: NotificationViewResolver,
            },
          },
        ],
      },
      {
        path: `${NtisRoutingConst.REVIEW_CREATE}/${':id'}`,
        data: {
          breadcrumb: 'ntisShared.pages.reviewCreate.breadcrumb',
          formCode: NTIS_REVIEW_CREATE,
        },
        loadComponent: () =>
          import('./pages/review-create-page/review-create-page.component').then((m) => m.ReviewCreatePageComponent),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NtisSharedInternalRoutingModule {}
