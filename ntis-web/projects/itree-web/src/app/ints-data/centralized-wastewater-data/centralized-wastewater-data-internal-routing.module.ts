import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import {
  NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
  NTIS_CW_DATA_LIST,
  NTIS_CW_DATA_REPORT,
  NTIS_CW_FOR_ORG_DATA_LIST,
} from '../../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';

const routes: SprRoutes = [
  {
    path: NtisRoutingConst.WASTEWATER_DATA_REPORT,
    data: {
      breadcrumb: 'intsData.centralizedData.pages.centralizedWastewaterDataReport.breadcrumb',
    },
    children: [
      {
        path: '',
        data: { formCode: NTIS_CW_DATA_REPORT },
        loadComponent: () =>
          import('./pages/centralized-wastewater-data-report/centralized-wastewater-data-report.component').then(
            (m) => m.CentralizedWastewaterDataReportComponent
          ),
      },
      {
        path: NtisRoutingConst.CW_ADDRESS_MAPPINGS,
        data: {
          formCode: NTIS_CW_DATA_REPORT,
          breadcrumb: 'intsData.centralizedData.pages.cwAddressMappings.headerText',
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/cw-address-mappings/cw-address-mappings.component').then(
                (m) => m.CwAddressMappingsComponent
              ),
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            data: {
              breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextEdit',
            },
            loadComponent: () =>
              import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                (m) => m.CwAddressMappingsEditComponent
              ),
          },
          {
            path: RoutingConst.EDIT,
            data: {
              breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
            },
            loadComponent: () =>
              import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                (m) => m.CwAddressMappingsEditComponent
              ),
          },
        ],
      },
      {
        path: `${NtisRoutingConst.WASTEWATER_DATA_LIST}/:orgId`,
        data: {
          breadcrumb: 'intsData.centralizedData.pages.centralizedWastewaterDataList.headerText',
        },
        children: [
          {
            path: '',
            data: {
              formCode: NTIS_CW_DATA_LIST,
            },
            loadComponent: () =>
              import('./pages/centralized-wastewater-data-list/centralized-wastewater-data-list.component').then(
                (m) => m.CentralizedWastewaterDataListComponent
              ),
          },
          {
            path: `${NtisRoutingConst.CW_ADDRESS_MAPPINGS}/:orgId`,
            data: {
              formCode: NTIS_CW_DATA_LIST,
              breadcrumb: 'intsData.centralizedData.pages.cwAddressMappings.headerText',
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import('./pages/cw-address-mappings/cw-address-mappings.component').then(
                    (m) => m.CwAddressMappingsComponent
                  ),
              },
              {
                path: RoutingConst.EDIT_WITH_ID,
                data: {
                  breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextEdit',
                },
                loadComponent: () =>
                  import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                    (m) => m.CwAddressMappingsEditComponent
                  ),
              },
              {
                path: RoutingConst.EDIT,
                data: {
                  breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
                },
                loadComponent: () =>
                  import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                    (m) => m.CwAddressMappingsEditComponent
                  ),
              },
            ],
          },
          {
            path: RoutingConst.VIEW_WITH_ID,
            data: {
              breadcrumb: 'intsData.centralizedData.pages.centralizedWastewaterDataViewPage.headerText',
              formCode: NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import(
                    './pages/centralized-wastewater-data-view-page/centralized-wastewater-data-view-page.component'
                  ).then((m) => m.CentralizedWastewaterDataViewPageComponent),
              },
              {
                path: `${NtisRoutingConst.CW_ADDRESS_MAPPINGS}/${RoutingConst.EDIT}/:orgId`,
                data: {
                  breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
                },
                loadComponent: () =>
                  import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                    (m) => m.CwAddressMappingsEditComponent
                  ),
              },
            ],
          },
        ],
      },
    ],
  },
  {
    path: NtisRoutingConst.WASTEWATER_DATA_LIST,
    data: {
      breadcrumb: 'intsData.centralizedData.pages.centralizedWastewaterDataList.headerText',
    },
    children: [
      {
        path: '',
        data: {
          formCode: NTIS_CW_FOR_ORG_DATA_LIST,
        },
        loadComponent: () =>
          import('./pages/centralized-wastewater-data-list/centralized-wastewater-data-list.component').then(
            (m) => m.CentralizedWastewaterDataListComponent
          ),
      },
      {
        path: NtisRoutingConst.CW_ADDRESS_MAPPINGS,
        data: {
          formCode: NTIS_CW_FOR_ORG_DATA_LIST,
          breadcrumb: 'intsData.centralizedData.pages.cwAddressMappings.headerText',
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/cw-address-mappings/cw-address-mappings.component').then(
                (m) => m.CwAddressMappingsComponent
              ),
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            data: {
              breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextEdit',
            },
            loadComponent: () =>
              import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                (m) => m.CwAddressMappingsEditComponent
              ),
          },
          {
            path: RoutingConst.EDIT,
            data: {
              breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
            },
            loadComponent: () =>
              import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                (m) => m.CwAddressMappingsEditComponent
              ),
          },
        ],
      },
      {
        path: RoutingConst.VIEW_WITH_ID,
        data: {
          breadcrumb: 'intsData.centralizedData.pages.centralizedWastewaterDataViewPage.headerText',
          formCode: NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import(
                './pages/centralized-wastewater-data-view-page/centralized-wastewater-data-view-page.component'
              ).then((m) => m.CentralizedWastewaterDataViewPageComponent),
          },
          {
            path: `${NtisRoutingConst.CW_ADDRESS_MAPPINGS}/${RoutingConst.EDIT}`,
            data: {
              breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
            },
            loadComponent: () =>
              import('./pages/cw-address-mappings-edit/cw-address-mappings-edit.component').then(
                (m) => m.CwAddressMappingsEditComponent
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
export class CentralizedWastewaterDataInternalRoutingModule {}
