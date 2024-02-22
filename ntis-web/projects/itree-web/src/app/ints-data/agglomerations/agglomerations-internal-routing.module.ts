import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { NTIS_AGGLO_INFO, NTIS_SUBMITTED_AGGLO_DATA_LIST } from '../../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';

const routes: SprRoutes = [
  {
    path: NtisRoutingConst.SUBMITTED_AGGLO_DATA_LIST,
    data: {
      breadcrumb: 'intsData.agglomerations.pages.submittedAggloDataList.breadcrumb',
    },
    children: [
      {
        path: '',
        pathMatch: 'full',
        data: { formCode: NTIS_SUBMITTED_AGGLO_DATA_LIST },
        loadComponent: () =>
          import('./pages/submitted-agglo-data-list/submitted-agglo-data-list.component').then(
            (m) => m.SubmittedAggloDataListComponent
          ),
      },
      {
        path: RoutingConst.VIEW_WITH_ID,
        data: { breadcrumb: 'intsData.agglomerations.pages.aggloInfo.headerText', formCode: NTIS_AGGLO_INFO },
        children: [
          {
            path: '',
            pathMatch: 'full',
            loadComponent: () =>
              import('./pages/agglo-info-page/agglo-info-page.component').then((m) => m.AggloInfoPageComponent),
          },
          {
            path: ':avId',
            data: {
              breadcrumb: 'intsData.agglomerations.pages.aggloVersionInfo.headerText',
            },
            loadComponent: () =>
              import('./pages/agglo-info-version-page/agglo-info-version-page.component').then(
                (m) => m.AggloInfoVersionPageComponent
              ),
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AgglomerationsInternalRoutingModule {}
