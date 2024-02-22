import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SPR_PROFILE } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import {
  NTIS_CONTRACTS_LIST,
  NTIS_CONTRACT_EDIT,
  NTIS_INSPECTOR_DISPOSAL_ORDERS_LIST,
  NTIS_INSPECTOR_RESEARCH_ORDERS_LIST,
  NTIS_INTS_OWNER_DASHBOARD,
  NTIS_RESEARCH_ORDER_PAGE,
  NTIS_SERVICE_ORDER_EDIT,
  NTIS_SERVICE_ORDER_PAGE,
  NTIS_WASTEWATER_FACILITY_EDIT,
  NTIS_WF_MANAGERS_EDIT,
  NTIS_WF_MANAGERS_LIST,
  SPR_FAQ_EDIT,
  SPR_FAQ_LIST,
  SPR_FAQ_THEMES_LIST,
  WASTEWATER_FACILITY_VIEW,
} from '../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { ChangePasswordPageComponent } from './pages/change-password-page/change-password-page.component';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { OrgSelectPageComponent } from './pages/org-select-page/org-select-page.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { RoleSelectPageComponent } from './pages/role-select-page/role-select-page.component';
import { WastewaterFacilityEditResolver } from '../ntis-building-data-module/resolvers/wastewater-facility-edit.resolver';
import { WastewaterFacilityViewResolver } from '../ntis-building-data-module/resolvers/wastewater-facility-view.resolver';
import { ContractEditResolver } from '../wte-maintenance-and-operation/contracts/resolvers/contract-edit.resolver';
import { FaqThemesListComponent } from '../ntis-shared/pages/faq/faq-themes-list/faq-themes-list.component';
import { FaqListComponent } from '../ntis-shared/pages/faq/faq-list/faq-list.component';
import { FaqEditComponent } from '../ntis-shared/pages/faq/faq-edit/faq-edit.component';

const routes: SprRoutes = [
  {
    path: NtisRoutingConst.MAP,
    data: { breadcrumb: 'ntisShared.components.map.breadcrumb' },
    children: [
      {
        path: '',
        loadComponent: () => import('../ntis-shared/components/map/map.component').then((m) => m.NtisMapComponent),
      },
      {
        path: `${NtisRoutingConst.INSPECTOR_RESEARCH_ORDERS_LIST}/${RoutingConst.PARAM_ID}`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.research.pages.ntisOwnerResearchOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_INSPECTOR_RESEARCH_ORDERS_LIST },
            loadComponent: () =>
              import(
                '../wte-maintenance-and-operation/research/pages/inspector-research-orders-list/inspector-research-orders-list.component'
              ).then((m) => m.InspectorResearchOrdersListComponent),
          },
          {
            path: `${NtisRoutingConst.RESEARCH_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.research.pages.researchOrderPage.headerText',
              formCode: NTIS_RESEARCH_ORDER_PAGE,
            },
            loadComponent: () =>
              import(
                '../wte-maintenance-and-operation/research/pages/research-order-page/research-order-page.component'
              ).then((m) => m.ResearchOrderPageComponent),
          },
        ],
      },
      {
        path: `${NtisRoutingConst.INSPECTOR_DISPOSAL_ORDERS_LIST}/${RoutingConst.PARAM_ID}`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.ownerDisposalOrdersList.headerTextIns',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_INSPECTOR_DISPOSAL_ORDERS_LIST },
            loadComponent: () =>
              import(
                '../wte-maintenance-and-operation/tech-support/shared/pages/inspector-disposal-orders-list/inspector-disposal-orders-list.component'
              ).then((m) => m.InspectorDisposalOrdersListComponent),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import(
                '../wte-maintenance-and-operation/tech-support/client/pages/service-order-page/service-order-page.component'
              ).then((m) => m.ServiceOrderPageComponent),
          },
        ],
      },
    ],
  },
  {
    path: '',
    data: {
      breadcrumb: 'pages.dashboard.headerText',
      breadcrumbUrl: `/${RoutingConst.INTERNAL}${RoutingConst.DASHBOARD}`,
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faHome' },
    },
    children: [
      { path: RoutingConst.ORG_SELECT, component: OrgSelectPageComponent },
      { path: RoutingConst.ROLE_SELECT, component: RoleSelectPageComponent },
      { path: RoutingConst.DASHBOARD, component: DashboardPageComponent },
      {
        path: RoutingConst.SPARK_CHANGE_PASSWORD,
        component: ChangePasswordPageComponent,
        data: { breadcrumb: 'pages.changePassword.headerText' },
      },
      {
        path: RoutingConst.SPARK_PROFILE_SETTINGS,
        component: ProfilePageComponent,
        data: { breadcrumb: 'pages.sprProfile.headerText', formCode: SPR_PROFILE },
      },
    ],
  },
  {
    path: '',
    data: {
      breadcrumb: 'ntisBuildingData.breadcrumb',
      breadcrumbUrl: null,
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faBuilding' },
    },
    children: [
      {
        path: NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD,
        data: { breadcrumb: 'pages.intsOwnerDashboard.breadcrumb' },
        children: [
          {
            path: '',
            data: { formCode: NTIS_INTS_OWNER_DASHBOARD },
            loadComponent: () =>
              import('./pages/ntis-ints-dashboard-page/ntis-ints-dashboard-page.component').then(
                (m) => m.NtisIntsDashboardPageComponent
              ),
          },
          {
            path: NtisRoutingConst.CONTRACTS_LIST,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.contractsList.headerTextCustomer',
              formCode: NTIS_CONTRACTS_LIST,
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import(
                    '../wte-maintenance-and-operation/contracts/pages/contracts-list-page/contracts-list-page.component'
                  ).then((m) => m.ContractsListPageComponent),
              },
              {
                path: `${RoutingConst.EDIT}/:type/:id`,
                data: {
                  breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.contractEdit.breadcrumb',
                  formCode: NTIS_CONTRACT_EDIT,
                },
                children: [
                  {
                    path: '',
                    loadComponent: () =>
                      import(
                        '../wte-maintenance-and-operation/contracts/pages/contract-edit-page/contract-edit-page.component'
                      ).then((m) => m.ContractEditPageComponent),
                    resolve: {
                      contractData: ContractEditResolver,
                    },
                  },
                ],
              },
            ],
          },
          {
            path: `${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.EDIT}/${RoutingConst.NEW}`,
            loadComponent: () =>
              import(
                '../ntis-building-data-module/pages/wastewater-facility/wastewater-facility-edit/wastewater-facility-edit.component'
              ).then((m) => m.WastewaterFacilityEditComponent),
            data: {
              formCode: NTIS_WASTEWATER_FACILITY_EDIT,
              breadcrumb: 'pages.intsOwnerDashboard.breadcrumbForNewFacility',
            },
            resolve: {
              wastewaterFacilityData: WastewaterFacilityEditResolver,
            },
          },
          {
            path: `${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.EDIT_WITH_ID}`,
            loadComponent: () =>
              import(
                '../ntis-building-data-module/pages/wastewater-facility/wastewater-facility-edit/wastewater-facility-edit.component'
              ).then((m) => m.WastewaterFacilityEditComponent),
            data: {
              formCode: NTIS_WASTEWATER_FACILITY_EDIT,
              breadcrumb: 'pages.intsOwnerDashboard.breadcrumbForFaciltyEdit',
            },
            resolve: {
              wastewaterFacilityData: WastewaterFacilityEditResolver,
            },
          },
          {
            path: `${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.VIEW_WITH_ID}`,
            data: {
              formCode: WASTEWATER_FACILITY_VIEW,
              breadcrumb: 'ntisBuildingData.pages.sewageTreatmentFacilityReview.headerText',
            },
            resolve: {
              wastewaterFacilityViewData: WastewaterFacilityViewResolver,
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import(
                    '../ntis-building-data-module/pages/wastewater-facility/wastewater-facility-view/wastewater-facility-view'
                  ).then((m) => m.WastewaterFacilityViewComponent),
              },
              {
                path: `${RoutingConst.EDIT_WITH_ID}`,
                loadComponent: () =>
                  import(
                    '../ntis-building-data-module/pages/wastewater-facility/wastewater-facility-edit/wastewater-facility-edit.component'
                  ).then((m) => m.WastewaterFacilityEditComponent),
                data: {
                  formCode: NTIS_WASTEWATER_FACILITY_EDIT,
                  breadcrumb: 'pages.intsOwnerDashboard.breadcrumbForFaciltyEdit',
                },
                resolve: {
                  wastewaterFacilityData: WastewaterFacilityEditResolver,
                },
              },
            ],
          },
          {
            path: `${NtisRoutingConst.WF_MANAGERS_LIST}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'ntisBuildingData.pages.wfManagersList.headerText',
            },
            children: [
              {
                path: '',
                data: {
                  formCode: NTIS_WF_MANAGERS_LIST,
                },
                loadComponent: () =>
                  import(
                    '../ntis-building-data-module/pages/wastewater-facility-managers/wf-managers-list/wf-managers-list.component'
                  ).then((m) => m.WfManagersListComponent),
              },
              {
                path: RoutingConst.EDIT_WITH_ID,
                data: {
                  breadcrumb: 'ntisBuildingData.pages.wfManagersEdit.headerText',
                  formCode: NTIS_WF_MANAGERS_EDIT,
                },
                loadComponent: () =>
                  import(
                    '../ntis-building-data-module/pages/wastewater-facility-managers/wf-managers-edit/wf-managers-edit.component'
                  ).then((m) => m.WfManagersEditComponent),
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
              import(
                '../wte-maintenance-and-operation/research/pages/research-order-page/research-order-page.component'
              ).then((m) => m.ResearchOrderPageComponent),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.breadcrumb',
              formCode: NTIS_SERVICE_ORDER_EDIT,
            },
            loadComponent: () =>
              import(
                '../wte-maintenance-and-operation/tech-support/client/pages/service-order-edit/service-order-edit.component'
              ).then((m) => m.ServiceOrderEditComponent),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import(
                '../wte-maintenance-and-operation/tech-support/client/pages/service-order-page/service-order-page.component'
              ).then((m) => m.ServiceOrderPageComponent),
          },
        ],
      },
      {
        path: RoutingConst.SPARK_FAQ,
        data: { breadcrumb: 'pages.sprFaq.chooseTheme', formCode: SPR_FAQ_THEMES_LIST },
        children: [
          { path: '', component: FaqThemesListComponent },
          {
            path: RoutingConst.PARAM_ID,
            component: FaqListComponent,
            data: {
              breadcrumb: 'pages.sprFaq.headerText',
              formCode: SPR_FAQ_LIST,
            },
          },
          {
            path: `${RoutingConst.EDIT_WITH_ID}/${RoutingConst.PARAM_CODE}`,
            component: FaqEditComponent,
            data: {
              breadcrumb: 'pages.sprFaq.headerText',
              formCode: SPR_FAQ_EDIT,
            },
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
export class ClientRoutingModule {}
