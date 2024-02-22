import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SPR_TASKS_CARDS, SPR_TASKS_LIST, SPR_TASK_EDIT } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { ConfirmExitGuard } from '../guard/confirm-exit.guard';
import { TaskEditPageComponent } from './task-pages/task-edit-page/task-edit-page.component';
import { TasksBrowsePageComponent } from './task-pages/tasks-browse-page/tasks-browse-page.component';
import { TasksDashboardPageComponent } from './task-pages/tasks-dashboard-page/tasks-dashboard-page.component';

export const taskRoutes: SprRoutes = [
  {
    path: '',
    data: {
      breadcrumb: 'pages.sprTasks.headerText',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faCalendarCheck' },
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: `${RoutingConst.SPARK_TASKS_DASHBOARD}`,
      },
      {
        path: RoutingConst.SPARK_TASKS_DASHBOARD,
        component: TasksDashboardPageComponent,
        data: { breadcrumb: 'pages.sprTasksDashboard.headerText', formCode: SPR_TASKS_CARDS },
      },
      {
        path: RoutingConst.SPARK_TASKS_BROWSE,
        data: { breadcrumb: 'pages.sprTasks.headerText' },
        children: [
          { path: '', component: TasksBrowsePageComponent, data: { formCode: SPR_TASKS_LIST } },
          {
            path: RoutingConst.EDIT_WITH_ID,
            component: TaskEditPageComponent,
            pathMatch: 'full',
            canDeactivate: [ConfirmExitGuard],
            data: { formCode: SPR_TASK_EDIT },
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(taskRoutes, {
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class TaskRoutingModule {}
