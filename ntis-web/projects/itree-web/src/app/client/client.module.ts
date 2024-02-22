import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientRoutingModule } from './client-routing.module';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { ChangePasswordPageComponent } from './pages/change-password-page/change-password-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { RoleSelectPageComponent } from './pages/role-select-page/role-select-page.component';
import { OrgSelectPageComponent } from './pages/org-select-page/org-select-page.component';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { NtisSharedModule } from '../ntis-shared/ntis-shared.module';
import { MapComponent } from '../standalone/map/components/map/map.component';

@NgModule({
  declarations: [
    ChangePasswordPageComponent,
    DashboardPageComponent,
    OrgSelectPageComponent,
    ProfilePageComponent,
    RoleSelectPageComponent,
  ],
  imports: [
    ClientRoutingModule,
    CommonModule,
    DialogModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    MapComponent,
    NtisSharedModule,
    ReactiveFormsModule,
    TableModule,
  ],
})
export class ClientModule {}
