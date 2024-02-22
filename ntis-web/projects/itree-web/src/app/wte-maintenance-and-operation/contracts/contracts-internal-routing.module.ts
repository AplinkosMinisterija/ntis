import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SprRoutes } from '@itree-commons/src/lib/types/routing';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';
import { ContractEditResolver } from './resolvers/contract-edit.resolver';
import {
  NTIS_CONTRACTS_LIST,
  NTIS_CONTRACT_EDIT,
  NTIS_CONTRACT_REQUEST,
  NTIS_CONTRACT_UPLOAD_PAGE,
  NTIS_SERVICE_ORDER_EDIT,
  NTIS_SERVICE_SEARCH,
} from '../../ntis-shared/constants/forms.const';
import { ContractUploadResolver } from './resolvers/contract-upload.resolver';

const routes: SprRoutes = [
  {
    path: '',
    data: {
      breadcrumb: 'wteMaintenanceAndOperation.contracts.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faUserTie' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: NtisRoutingConst.SERVICE_SEARCH,
        data: {
          loadWtfInfo: true,
          formCode: NTIS_SERVICE_SEARCH,
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/service-search-page/service-search-page.component').then(
                (m) => m.ServiceSearchPageComponent
              ),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
            data: {
              formCode: NTIS_SERVICE_ORDER_EDIT,
            },
            loadComponent: () =>
              import('../tech-support/client/pages/service-order-edit/service-order-edit.component').then(
                (m) => m.ServiceOrderEditComponent
              ),
          },
          {
            path: `${NtisRoutingConst.CONTRACT_REQUEST}/${RoutingConst.NEW}/:serviceProviderId`,
            data: {
              formCode: NTIS_CONTRACT_REQUEST,
            },
            loadComponent: () =>
              import('./pages/contract-request-page/contract-request-page.component').then(
                (m) => m.ContractRequestPageComponent
              ),
          },
        ],
      },
      {
        path: `${NtisRoutingConst.CONTRACT_REQUEST}/${RoutingConst.NEW}/:serviceProviderId`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.contractRequest.headerText',
          formCode: NTIS_CONTRACT_REQUEST,
        },
        loadComponent: () =>
          import('./pages/contract-request-page/contract-request-page.component').then(
            (m) => m.ContractRequestPageComponent
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
              import('./pages/contracts-list-page/contracts-list-page.component').then(
                (m) => m.ContractsListPageComponent
              ),
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
                  import('./pages/contract-edit-page/contract-edit-page.component').then(
                    (m) => m.ContractEditPageComponent
                  ),
                resolve: {
                  contractData: ContractEditResolver,
                },
              },
              {
                path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
                  formCode: NTIS_SERVICE_ORDER_EDIT,
                },
                loadComponent: () =>
                  import('../tech-support/client/pages/service-order-edit/service-order-edit.component').then(
                    (m) => m.ServiceOrderEditComponent
                  ),
              },
              {
                path: NtisRoutingConst.SIGN,
                loadComponent: () =>
                  import('./pages/contract-signing-page/contract-signing-page.component').then(
                    (m) => m.ContractSigningPageComponent
                  ),
                data: {
                  breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.contractSign.breadcrumb',
                },
              },
              {
                path: NtisRoutingConst.PREVIEW,
                loadComponent: () =>
                  import('./pages/contract-signing-page/contract-signing-page.component').then(
                    (m) => m.ContractSigningPageComponent
                  ),
                data: {
                  breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.contractSign.previewBreadcrumb',
                },
              },
            ],
          },
          {
            path: `${NtisRoutingConst.UPLOAD}/:type/:mode`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.contracts.pages.contractUpload.breadcrumb',
              formCode: NTIS_CONTRACT_UPLOAD_PAGE,
            },
            children: [
              {
                path: '',
                loadComponent: () =>
                  import('./pages/contract-upload-page/contract-upload-page.component').then(
                    (m) => m.ContractUploadPageComponent
                  ),
                resolve: {
                  contractData: ContractUploadResolver,
                },
              },
              {
                path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
                  formCode: NTIS_SERVICE_ORDER_EDIT,
                },
                loadComponent: () =>
                  import('../tech-support/client/pages/service-order-edit/service-order-edit.component').then(
                    (m) => m.ServiceOrderEditComponent
                  ),
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
  providers: [ContractEditResolver],
})
export class ContractsInternalRoutingModule {}
