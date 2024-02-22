import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';

const routes: SprRoutes = [
  {
    path: NtisRoutingConst.AGGLOMERATIONS,
    data: {
      breadcrumb: 'intsData.agglomerations.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faCogs' },
      breadcrumbUrl: null,
    },
    loadChildren: () =>
      import('./agglomerations/agglomerations-internal-routing.module').then(
        (m) => m.AgglomerationsInternalRoutingModule
      ),
  },
  {
    path: NtisRoutingConst.CENTRALIZED_WASTEWATER,
    data: {
      breadcrumb: 'intsData.centralizedData.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faBuildingCircleArrowRight' },
      breadcrumbUrl: null,
    },
    loadChildren: () =>
      import('./centralized-wastewater-data/centralized-wastewater-data-internal-routing.module').then(
        (m) => m.CentralizedWastewaterDataInternalRoutingModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class IntsDataInternalRoutingModule {}
