// angular modules:
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
// external modules:
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MissingTranslationHandler, TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { AppMissingTranslationHandler } from '@itree/ngx-s2-commons';
// PrimeNG modules:
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { EditorModule } from 'primeng/editor';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
// internal:
import { HttpLoaderFactory } from '../app.module';
import { TaskEditPageComponent } from './task-pages/task-edit-page/task-edit-page.component';
// pages:
import { TasksBrowsePageComponent } from './task-pages/tasks-browse-page/tasks-browse-page.component';
import { TasksDashboardPageComponent } from './task-pages/tasks-dashboard-page/tasks-dashboard-page.component';
// components:
import { SubTaskFormCardComponent } from './tasks-components/sub-task-form-card/sub-task-form-card.component';
import { TaskCardComponent } from './tasks-components/task-card/task-card.component';
import { TasksCalendarComponent } from './tasks-components/tasks-calendar/tasks-calendar.component';
import { taskRoutes } from './tasks-routing.module';

@NgModule({
  declarations: [
    // components:
    SubTaskFormCardComponent,
    TasksCalendarComponent,
    TaskCardComponent,
    // pages:
    TasksBrowsePageComponent,
    TaskEditPageComponent,
    TasksDashboardPageComponent,
  ],
  imports: [
    // angular modules:
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(taskRoutes),
    // PrimeNG modules:
    CalendarModule,
    DialogModule,
    EditorModule,
    ItreeCommonsModule,
    RadioButtonModule,
    TableModule,
    TooltipModule,
    // external modules:
    FontAwesomeModule,
    TranslateModule.forChild({
      missingTranslationHandler: { provide: MissingTranslationHandler, useClass: AppMissingTranslationHandler },
      useDefaultLang: true,
      defaultLanguage: 'lt',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
      extend: true,
    }),
  ],
})
export class TasksModule {}
