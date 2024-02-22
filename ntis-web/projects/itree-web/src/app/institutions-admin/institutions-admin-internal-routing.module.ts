import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import {
  NTIS_CARS_LIST,
  NTIS_CAR_EDIT,
  NTIS_FACILITY_MODELS_LIST,
  NTIS_FACILITY_MODEL_EDIT,
  NTIS_INSTITUTIONS_EDIT,
  NTIS_INSTITUTIONS_LIST,
  NTIS_ORD_IMPORT_LIST,
  NTIS_ORD_IMPORT_VIEW_PAGE,
  NTIS_PRIORITY_WF_LIST,
  NTIS_SERVICE_DESCRIPTION_EDIT,
  NTIS_SERVICE_MANAGEMENT,
  NTIS_SERVICE_PROVIDERS_LIST,
  NTIS_SERVICE_PROVIDER_SETTINGS,
  NTIS_SERVICE_REQUESTS_LIST,
  NTIS_SERVICE_REQUEST_CREATE,
  NTIS_SERVICE_REQUEST_REVIEW,
  NTIS_SP_WM_SERVICE_REQUESTS_LIST,
  NTIS_WATER_MANAGER_FACILITIES_LIST,
  NTIS_WATER_MANAGER_SETTINGS,
} from '../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';

const routes: Routes = [
  {
    path: '',
    data: {
      breadcrumb: 'institutionsAdmin.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faCogs' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: NtisRoutingConst.SP_WM_SERVICE_REQUESTS_LIST,
        data: {
          breadcrumb: 'institutionsAdmin.pages.serviceRequestsList.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_SP_WM_SERVICE_REQUESTS_LIST },
            loadComponent: () =>
              import(
                './pages/service-requests/sp-wm-service-requests-list-page/sp-wm-service-requests-list-page.component'
              ).then((m) => m.SpWmServiceRequestsListPageComponent),
          },
          {
            path: NtisRoutingConst.SERVICE_REQUEST_REVIEW + '/:type' + '/:id',
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceRequest.headerText',
              formCode: NTIS_SERVICE_REQUEST_REVIEW,
            },
            loadComponent: () =>
              import('./pages/service-requests/service-request-review-page/service-request-review-page.component').then(
                (m) => m.ServiceRequestReviewPageComponent
              ),
          },
          {
            path: NtisRoutingConst.SERVICE_REQUEST_CREATE + '/:type',
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceRequest.headerText',
              formCode: NTIS_SERVICE_REQUEST_CREATE,
            },
            loadComponent: () =>
              import('./pages/service-requests/service-request-create-page/service-request-create-page.component').then(
                (m) => m.ServiceRequestCreatePageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.SERVICE_REQUESTS_LIST,
        data: {
          breadcrumb: 'institutionsAdmin.pages.serviceRequestsList.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_SERVICE_REQUESTS_LIST },
            loadComponent: () =>
              import('./pages/service-requests/service-requests-list-page/service-requests-list-page.component').then(
                (m) => m.ServiceRequestsListPageComponent
              ),
          },
          {
            path: ':status/:id',
            data: { formCode: NTIS_SERVICE_REQUESTS_LIST },
            loadComponent: () =>
              import('./pages/service-requests/service-requests-list-page/service-requests-list-page.component').then(
                (m) => m.ServiceRequestsListPageComponent
              ),
          },
          {
            path: NtisRoutingConst.SERVICE_REQUEST_REVIEW + '/:type' + '/:id',
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceRequest.headerText',
              formCode: NTIS_SERVICE_REQUEST_REVIEW,
            },
            loadComponent: () =>
              import('./pages/service-requests/service-request-review-page/service-request-review-page.component').then(
                (m) => m.ServiceRequestReviewPageComponent
              ),
          },
          {
            path: NtisRoutingConst.SERVICE_REQUEST_CREATE + '/:type',
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceRequest.headerText',
              formCode: NTIS_SERVICE_REQUEST_CREATE,
            },
            loadComponent: () =>
              import('./pages/service-requests/service-request-create-page/service-request-create-page.component').then(
                (m) => m.ServiceRequestCreatePageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.INST_WATER_MANAGER_SETTINGS,
        data: {
          breadcrumb: 'institutionsAdmin.pages.waterManagerSettings.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formcode: NTIS_WATER_MANAGER_SETTINGS },
            loadComponent: () =>
              import('./pages/water-manager-settings-page/water-manager-settings-page.component').then(
                (m) => m.WaterManagerSettingsPageComponent
              ),
          },
          {
            path: NtisRoutingConst.INST_WATER_MANAGER_FACILITIES,
            data: {
              breadcrumb: 'institutionsAdmin.pages.ntisWaterManagerFacilitiesList.headerText',
              formCode: NTIS_WATER_MANAGER_FACILITIES_LIST,
            },
            loadComponent: () =>
              import('./pages/water-manager-facilities-list/water-manager-facilities-list.component').then(
                (m) => m.WaterManagerFacilitiesListComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS,
        data: {
          breadcrumb: 'institutionsAdmin.pages.serviceProviderSettings.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_SERVICE_PROVIDER_SETTINGS },
            loadComponent: () =>
              import('./pages/service-provider-settings-page/service-provider-settings-page.component').then(
                (m) => m.ServiceProviderSettingsPageComponent
              ),
          },
          {
            path: NtisRoutingConst.INST_SERVICE_MANAGEMENT,
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceManagement.headerText',
            },
            children: [
              {
                path: '',
                data: { formCode: NTIS_SERVICE_MANAGEMENT },
                loadComponent: () =>
                  import('./pages/service-management-page/service-management-page.component').then(
                    (m) => m.ServiceManagementPageComponent
                  ),
              },
              {
                path: NtisRoutingConst.INST_SERVICE_DESCRIPTION + '/:id',
                data: {
                  breadcrumb: 'institutionsAdmin.pages.institutionsServiceDescription.headerText',
                  formCode: NTIS_SERVICE_DESCRIPTION_EDIT,
                },
                loadComponent: () =>
                  import('./pages/service-description-page/service-description-page.component').then(
                    (m) => m.ServiceDescriptionPageComponent
                  ),
              },
              {
                path: NtisRoutingConst.SERVICE_REQUEST_CREATE + '/:type',
                data: {
                  breadcrumb: 'institutionsAdmin.pages.serviceRequest.headerText',
                  formCode: NTIS_SERVICE_REQUEST_CREATE,
                },
                loadComponent: () =>
                  import(
                    './pages/service-requests/service-request-create-page/service-request-create-page.component'
                  ).then((m) => m.ServiceRequestCreatePageComponent),
              },
            ],
          },
          {
            path: NtisRoutingConst.NTIS_CARS_LIST,
            data: {
              breadcrumb: 'institutionsAdmin.pages.ntisCarsBrowse.headerText',
            },
            children: [
              {
                path: '',
                data: { formCode: NTIS_CARS_LIST },
                loadComponent: () =>
                  import('./pages/cars/cars-browse-page/cars-browse-page.component').then(
                    (m) => m.CarsBrowsePageComponent
                  ),
              },
              {
                path: RoutingConst.EDIT_WITH_ID,
                data: {
                  breadcrumb: 'institutionsAdmin.pages.ntisCarEdit.headerText',
                  formCode: NTIS_CAR_EDIT,
                },
                loadComponent: () =>
                  import('./pages/cars/car-edit-page/car-edit-page.component').then((m) => m.CarEditPageComponent),
              },
            ],
          },
          {
            path: NtisRoutingConst.NTIS_PRIORITY_WF_LIST,
            data: {
              breadcrumb: 'institutionsAdmin.pages.priorityFacilitiesList.headerText',
              formCode: NTIS_PRIORITY_WF_LIST,
            },
            loadComponent: () =>
              import('./pages/priority-facilities-list/priority-facilities-list.component').then(
                (m) => m.PriorityFacilitiesListComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.INST_INSTITUTIONS_LIST,
        data: {
          breadcrumb: 'institutionsAdmin.pages.institutionsList.headerText',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_INSTITUTIONS_LIST },
            loadComponent: () =>
              import('./pages/institutions/institutions-list-page/institutions-list-page.component').then(
                (m) => m.InstitutionsListPageComponent
              ),
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            data: { formCode: NTIS_INSTITUTIONS_EDIT },
            loadComponent: () =>
              import('./pages/institutions/institutions-edit-page/institutions-edit-page.component').then(
                (m) => m.InstitutionsEditPageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.SERVICE_PROVIDERS,
        data: {
          breadcrumb: 'institutionsAdmin.pages.serviceProvidersList.breadcrumb',
          formCode: NTIS_SERVICE_PROVIDERS_LIST,
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/service-providers-list/service-providers-list.component').then(
                (m) => m.ServiceProvidersListComponent
              ),
          },
          {
            path: NtisRoutingConst.ORDERS_IMPORT + '/:orgId',
            data: {
              breadcrumb: 'ntisShared.pages.ordersImport.headerText',
              formCode: NTIS_ORD_IMPORT_LIST,
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import('../ntis-shared/pages/ord-import-list/ord-import-list.component').then(
                    (m) => m.OrdImportListComponent
                  ),
              },
              {
                path: `${NtisRoutingConst.CW_ADDRESS_MAPPINGS}/:orgId`,
                data: {
                  formCode: NTIS_ORD_IMPORT_LIST,
                  breadcrumb: 'intsData.centralizedData.pages.cwAddressMappings.headerText',
                },
                children: [
                  {
                    path: '',
                    loadComponent: () =>
                      import(
                        '../ints-data/centralized-wastewater-data/pages/cw-address-mappings/cw-address-mappings.component'
                      ).then((m) => m.CwAddressMappingsComponent),
                  },
                  {
                    path: RoutingConst.EDIT_WITH_ID,
                    data: {
                      breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextEdit',
                    },
                    loadComponent: () =>
                      import(
                        '../ints-data/centralized-wastewater-data//pages/cw-address-mappings-edit/cw-address-mappings-edit.component'
                      ).then((m) => m.CwAddressMappingsEditComponent),
                  },
                  {
                    path: RoutingConst.EDIT,
                    data: {
                      breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
                    },
                    loadComponent: () =>
                      import(
                        '../ints-data/centralized-wastewater-data/pages/cw-address-mappings-edit/cw-address-mappings-edit.component'
                      ).then((m) => m.CwAddressMappingsEditComponent),
                  },
                ],
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
                      import('../ntis-shared/pages/ord-import-view-page/ord-import-view-page.component').then(
                        (m) => m.OrdImportViewPageComponent
                      ),
                  },
                  {
                    path: `${NtisRoutingConst.CW_ADDRESS_MAPPINGS}/${RoutingConst.EDIT}/:orgId`,
                    data: {
                      breadcrumb: 'intsData.centralizedData.pages.cwAddressMappingsEdit.headerTextNew',
                    },
                    loadComponent: () =>
                      import(
                        '../ints-data/centralized-wastewater-data/pages/cw-address-mappings-edit/cw-address-mappings-edit.component'
                      ).then((m) => m.CwAddressMappingsEditComponent),
                  },
                ],
              },
            ],
          },
        ],
      },
      {
        path: NtisRoutingConst.NTIS_FACILITY_MODELS,
        data: {
          breadcrumb: 'institutionsAdmin.pages.facilityModelsList.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_FACILITY_MODELS_LIST },
            loadComponent: () =>
              import('./pages/facility-models-list-page/facility-models-list-page.component').then(
                (m) => m.FacilityModelsListPageComponent
              ),
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            data: {
              breadcrumb: 'institutionsAdmin.pages.facilityModelEdit.headerText',
              formCode: NTIS_FACILITY_MODEL_EDIT,
            },
            loadComponent: () =>
              import('./pages/facility-model-edit-page/facility-model-edit-page.component').then(
                (m) => m.FacilityModelEditPageComponent
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
export class InstitutionsAdminInternalRoutingModule {}
