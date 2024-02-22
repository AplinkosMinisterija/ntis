import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { InternalComponent } from '@itree-commons/src/lib/components/internal/internal.component';
import { PublicComponent } from '@itree-commons/src/lib/components/public/public.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprCanActivateGuard } from './guard/can-activate.guard';
import { SprRoleSelectGuard } from './guard/role-select.guard';
import { LoadFormActionsGuard } from './guard/load-form-actions.guard';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { SprOrgSelectGuard } from './guard/org-select.guard';
import { CheckFormActionsGuard } from './guard/check-form-actions.guard';
import { NtisRoutingConst } from './ntis-shared/constants/ntis-routing.const';
import { PageNotFoundGuard } from './guard/page-not-found.guard';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { AuthUtil } from '@itree/ngx-s2-commons';

const NTIS_PUBLIC_ROUTES: SprRoutes = [
  {
    path: NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
    loadChildren: () =>
      import('./wte-maintenance-and-operation/wte-maintenance-and-operation-public-routing.module').then(
        (m) => m.WteMaintenanceAndOperationPublicRoutingModule
      ),
  },
];

const NTIS_INTERNAL_ROUTES: SprRoutes = [
  {
    path: NtisRoutingConst.INSTITUTIONS,
    loadChildren: () =>
      import('./institutions-admin/institutions-admin-internal-routing.module').then(
        (m) => m.InstitutionsAdminInternalRoutingModule
      ),
  },
  {
    path: NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
    loadChildren: () =>
      import('./wte-maintenance-and-operation/wte-maintenance-and-operation-internal-routing.module').then(
        (m) => m.WteMaintenanceAndOperationInternalRoutingModule
      ),
  },
  {
    path: NtisRoutingConst.DATA,
    loadChildren: () =>
      import('./ints-data/ints-data-internal-routing.module').then((m) => m.IntsDataInternalRoutingModule),
  },
  {
    path: NtisRoutingConst.BUILDING_DATA,
    loadChildren: () =>
      import('././ntis-building-data-module/ntis-building-data-routing.module').then(
        (m) => m.NtisBuildingDataRoutingModule
      ),
  },
  {
    path: '',
    loadChildren: () =>
      import('././ntis-shared/ntis-shared-internal-routing.module').then((m) => m.NtisSharedInternalRoutingModule),
  },
];

const PUBLIC_ROUTES: SprRoutes = [
  {
    path: NtisRoutingConst.PUBLIC,
    children: NTIS_PUBLIC_ROUTES,
  },
  {
    path: RoutingConst.PAGENOTFOUND,
    loadComponent: () =>
      import('./public/pages/not-found-page/not-found-page.component').then((m) => m.NotFoundPageComponent),
    canActivate: [PageNotFoundGuard],
  },
  {
    path: '',
    loadChildren: () => import('./public/public.module').then((m) => m.PublicModule),
  },
];

const INTERNAL_ROUTES: SprRoutes = [
  ...NTIS_INTERNAL_ROUTES,
  {
    path: RoutingConst.ADMIN,
    loadChildren: () => import('./spark-admin-module/admin.module').then((m) => m.AdminModule),
  },
  {
    path: RoutingConst.TASKS,
    loadChildren: () => import('./spark-tasks-module/tasks.module').then((m) => m.TasksModule),
  },
  {
    path: '',
    loadChildren: () => import('./client/client.module').then((m) => m.ClientModule),
  },
  {
    path: RoutingConst.PAGENOTFOUND,
    loadComponent: () =>
      import('./public/pages/not-found-page/not-found-page.component').then((m) => m.NotFoundPageComponent),
  },
];

const routes: SprRoutes = [
  {
    path: RoutingConst.INTERNAL,
    component: InternalComponent,
    canActivate: [LoadFormActionsGuard],
    children: INTERNAL_ROUTES,
    canActivateChild: [SprOrgSelectGuard, SprRoleSelectGuard, SprCanActivateGuard, CheckFormActionsGuard],
  },
  {
    path: NtisRoutingConst.CONTRACT_RETURN,
    loadComponent: () =>
      import(
        './wte-maintenance-and-operation/contracts/pages/contract-sign-preview-return-page/contract-sign-preview-return-page.component'
      ).then((m) => m.ContractSignPreviewReturnPageComponent),
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: !AuthUtil.isLoggedIn() ? RoutingConst.HOME : RoutingConst.INTERNAL + '/' + NtisRoutingConst.DASHBOARD,
  },
  {
    path: '',
    component: PublicComponent,
    canActivate: [LoadFormActionsGuard],
    canActivateChild: [CheckFormActionsGuard],
    children: PUBLIC_ROUTES,
  },

  //{ path: '**', redirectTo: '/' + NtisRoutingConst.PAGENOTFOUND }
  { path: '**', redirectTo: '/' + NtisRoutingConst.MAP },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
