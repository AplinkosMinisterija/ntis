import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { NTIS_SERVICE_SEARCH } from '../../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AuthUtil } from '@itree/ngx-s2-commons';

const routes: SprRoutes = [
  {
    path: '',
    children: [
      {
        path: NtisRoutingConst.SERVICE_SEARCH,
        pathMatch: 'full',
        redirectTo: AuthUtil.isLoggedIn()
          ? `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.SERVICE_SEARCH}`
          : `/${NtisRoutingConst.PUBLIC}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.SERVICE_SEARCH}`,
      },
      {
        path: NtisRoutingConst.SERVICE_SEARCH,
        data: {
          formCode: NTIS_SERVICE_SEARCH,
        },
        loadComponent: () =>
          import('./pages/service-search-page/service-search-page.component').then((m) => m.ServiceSearchPageComponent),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContractsPublicRoutingModule {}
