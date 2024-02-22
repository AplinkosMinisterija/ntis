import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';
import {
  NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST,
  NTIS_SLUDGE_TREATMENT_DELIVERY,
  NTIS_WM_DASHBOARD,
} from '../../ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

const routes: Routes = [
  {
    path: NtisRoutingConst.WATER_MANAGER,
    data: {
      breadcrumb: 'wteMaintenanceAndOperation.sludgeTreatmentMenu.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faRecycle' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: RoutingConst.DASHBOARD,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.waterManager.pages.dashboard.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_WM_DASHBOARD },
            loadComponent: () =>
              import('./water-manager/pages/water-manager-dashboard-page/water-manager-dashboard-page.component').then(
                (m) => m.WaterManagerDashboardPageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.SLUDGE_TREATMENT_DELIVERIES_LIST,
        data: {
          breadcrumb:
            'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDeliveriesList.breadcrumb',
          formCode: NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST,
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST },
            loadComponent: () =>
              import(
                './water-manager/pages/sludge-treatment-deliveries-list-page/sludge-treatment-deliveries-list-page.component'
              ).then((m) => m.SludgeTreatmentDeliveriesListPageComponent),
          },
          {
            path: `${NtisRoutingConst.SLUDGE_TREATMENT_DELIVERY}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb:
                'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDelivery.headerText',
              formCode: NTIS_SLUDGE_TREATMENT_DELIVERY,
            },
            loadComponent: () =>
              import(
                './water-manager/pages/sludge-treatment-delivery-page/sludge-treatment-delivery-page.component'
              ).then((m) => m.SludgeTreatmentDeliveryPageComponent),
          },
          {
            path: ':status',
            data: {
              breadcrumb:
                'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDeliveriesList.breadcrumb',
            },
            children: [
              {
                path: '',
                data: { formCode: NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST },
                loadComponent: () =>
                  import(
                    './water-manager/pages/sludge-treatment-deliveries-list-page/sludge-treatment-deliveries-list-page.component'
                  ).then((m) => m.SludgeTreatmentDeliveriesListPageComponent),
              },
              {
                path: `${NtisRoutingConst.SLUDGE_TREATMENT_DELIVERY}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb:
                    'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDelivery.headerText',
                  formCode: NTIS_SLUDGE_TREATMENT_DELIVERY,
                },
                loadComponent: () =>
                  import(
                    './water-manager/pages/sludge-treatment-delivery-page/sludge-treatment-delivery-page.component'
                  ).then((m) => m.SludgeTreatmentDeliveryPageComponent),
              },
            ],
          },
          {
            path: ':status' + '/:orgId',
            data: { formCode: NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST },
            children: [
              {
                path: '',
                data: { formCode: NTIS_SLUDGE_TREATMENT_DELIVERIES_LIST },
                loadComponent: () =>
                  import(
                    './water-manager/pages/sludge-treatment-deliveries-list-page/sludge-treatment-deliveries-list-page.component'
                  ).then((m) => m.SludgeTreatmentDeliveriesListPageComponent),
              },
              {
                path: `${NtisRoutingConst.SLUDGE_TREATMENT_DELIVERY}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb:
                    'wteMaintenanceAndOperation.techSupport.waterManager.pages.sludgeTreatmentDelivery.headerText',
                  formCode: NTIS_SLUDGE_TREATMENT_DELIVERY,
                },
                loadComponent: () =>
                  import(
                    './water-manager/pages/sludge-treatment-delivery-page/sludge-treatment-delivery-page.component'
                  ).then((m) => m.SludgeTreatmentDeliveryPageComponent),
              },
            ],
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
export class SludgeTreatmentInternalRoutingModule {}
