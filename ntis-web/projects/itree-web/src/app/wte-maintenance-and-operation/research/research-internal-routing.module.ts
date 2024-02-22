import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import {
  NTIS_ORD_IMPORT_FOR_ORG_LIST,
  NTIS_ORD_IMPORT_VIEW_PAGE,
  NTIS_OWNER_RESEARCH_ORDERS_LIST,
  NTIS_RESEARCH_NORMS_HISTORY,
  NTIS_RESEARCH_NORMS_MANAGEMENT,
  NTIS_RESEARCH_ORDER_PAGE,
  NTIS_RESEARCH_PROVIDERS_LIST,
  NTIS_SERVICE_SEARCH,
  NTIS_SP_RESEARCH_ORDERS_LIST,
  RESEARCH_OUTSIDE_NTIS_CREATE,
} from '../../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';

const routes: SprRoutes = [
  {
    path: '',
    data: {
      breadcrumb: 'wteMaintenanceAndOperation.research.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faHandHoldingDroplet' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: NtisRoutingConst.RESEARCH_NORMS_MANAGEMENT,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.research.pages.ntisResearchNormsManagement.headerText',
        },
        children: [
          {
            path: '',
            data: {
              formCode: NTIS_RESEARCH_NORMS_MANAGEMENT,
            },
            loadComponent: () =>
              import('./pages/research-norms-management/research-norms-management.component').then(
                (m) => m.ResearchNormsManagementComponent
              ),
          },
          {
            path: NtisRoutingConst.RESEARCH_NORMS_HISTORY,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.research.pages.ntisResearchNormsHistory.headerText',
              formCode: NTIS_RESEARCH_NORMS_HISTORY,
            },
            loadComponent: () =>
              import('./pages/research-norms-history/research-norms-history.component').then(
                (m) => m.ResearchNormsHistoryComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.SP_RESEARCH_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.research.pages.ntisSpResearchOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: {
              formCode: NTIS_SP_RESEARCH_ORDERS_LIST,
            },
            loadComponent: () =>
              import('./pages/sp-research-orders-list/sp-research-orders-list.component').then(
                (m) => m.SpResearchOrdersListComponent
              ),
          },
          {
            path: NtisRoutingConst.ORDERS_IMPORT,
            data: {
              formCode: NTIS_ORD_IMPORT_FOR_ORG_LIST,
              breadcrumb: 'ntisShared.pages.ordersImport.headerText',
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import('../../ntis-shared/pages/ord-import-list/ord-import-list.component').then(
                    (m) => m.OrdImportListComponent
                  ),
              },
              {
                path: RoutingConst.VIEW_WITH_ID,
                data: {
                  formCode: NTIS_ORD_IMPORT_VIEW_PAGE,
                  breadcrumb: 'ntisShared.pages.ordersImportView.headerText',
                },
                children: [
                  {
                    path: '',
                    loadComponent: () =>
                      import('../../ntis-shared/pages/ord-import-view-page/ord-import-view-page.component').then(
                        (m) => m.OrdImportViewPageComponent
                      ),
                  },
                  {
                    path: `${NtisRoutingConst.CW_ADDRESS_MAPPINGS}/${RoutingConst.EDIT}`,
                    data: {
                      breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
                    },
                    loadComponent: () =>
                      import(
                        '../../ints-data/centralized-wastewater-data/pages/cw-address-mappings-edit/cw-address-mappings-edit.component'
                      ).then((m) => m.CwAddressMappingsEditComponent),
                  },
                ],
              },
              {
                path: NtisRoutingConst.CW_ADDRESS_MAPPINGS,
                data: {
                  formCode: NTIS_ORD_IMPORT_FOR_ORG_LIST,
                  breadcrumb: 'intsData.centralizedData.pages.cwAddressMappings.headerText',
                },
                children: [
                  {
                    path: '',
                    loadComponent: () =>
                      import(
                        '../../ints-data/centralized-wastewater-data/pages/cw-address-mappings/cw-address-mappings.component'
                      ).then((m) => m.CwAddressMappingsComponent),
                  },
                  {
                    path: RoutingConst.EDIT_WITH_ID,
                    data: {
                      breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextEdit',
                    },
                    loadComponent: () =>
                      import(
                        '../../ints-data/centralized-wastewater-data/pages/cw-address-mappings-edit/cw-address-mappings-edit.component'
                      ).then((m) => m.CwAddressMappingsEditComponent),
                  },
                  {
                    path: RoutingConst.EDIT,
                    data: {
                      breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
                    },
                    loadComponent: () =>
                      import(
                        '../../ints-data/centralized-wastewater-data/pages/cw-address-mappings-edit/cw-address-mappings-edit.component'
                      ).then((m) => m.CwAddressMappingsEditComponent),
                  },
                ],
              },
            ],
          },
          {
            path: NtisRoutingConst.RESEARCH_OUTSIDE_NTIS,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.research.pages.researchOutsideNtis.breadcrumb',
              formCode: RESEARCH_OUTSIDE_NTIS_CREATE,
            },
            loadComponent: () =>
              import('./pages/research-outside-ntis-create/research-outside-ntis-create.component').then(
                (m) => m.ResearchOutsideNtisCreateComponent
              ),
          },
          {
            path: ':ordState',
            children: [
              {
                path: '',
                data: {
                  formCode: NTIS_SP_RESEARCH_ORDERS_LIST,
                },
                loadComponent: () =>
                  import('./pages/sp-research-orders-list/sp-research-orders-list.component').then(
                    (m) => m.SpResearchOrdersListComponent
                  ),
              },
              {
                path: `${NtisRoutingConst.RESEARCH_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb: 'wteMaintenanceAndOperation.research.pages.researchOrderPage.headerText',
                  formCode: NTIS_RESEARCH_ORDER_PAGE,
                },
                loadComponent: () =>
                  import('./pages/research-order-page/research-order-page.component').then(
                    (m) => m.ResearchOrderPageComponent
                  ),
              },
            ],
          },
          {
            path: `${NtisRoutingConst.RESEARCH_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.research.pages.researchOrderPage.headerText',
              formCode: NTIS_RESEARCH_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./pages/research-order-page/research-order-page.component').then(
                (m) => m.ResearchOrderPageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.OWNER_RESEARCH_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.research.pages.ntisOwnerResearchOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: {
              formCode: NTIS_OWNER_RESEARCH_ORDERS_LIST,
            },

            loadComponent: () =>
              import('./pages/owner-research-orders-list/owner-research-orders-list.component').then(
                (m) => m.OwnerResearchOrdersListComponent
              ),
          },
          {
            path: NtisRoutingConst.SERVICE_SEARCH,
            data: {
              loadWtfInfo: true,
              breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.serviceSearch.headerText',
              formCode: NTIS_SERVICE_SEARCH,
            },
            loadComponent: () =>
              import('../contracts/pages/service-search-page/service-search-page.component').then(
                (m) => m.ServiceSearchPageComponent
              ),
          },
          {
            path: `${NtisRoutingConst.RESEARCH_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.research.pages.researchOrderPage.headerText',
              formCode: NTIS_RESEARCH_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./pages/research-order-page/research-order-page.component').then(
                (m) => m.ResearchOrderPageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.PROVIDERS_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.research.pages.researchProvidersList.headerText',
          formCode: NTIS_RESEARCH_PROVIDERS_LIST,
        },
        loadComponent: () =>
          import('./pages/research-providers-list/research-providers-list.component').then(
            (m) => m.ResearchProvidersListComponent
          ),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ResearchInternalRoutingModule {}
