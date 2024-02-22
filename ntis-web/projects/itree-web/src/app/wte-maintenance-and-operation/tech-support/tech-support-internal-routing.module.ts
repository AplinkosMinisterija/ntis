import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import {
  NTIS_DISPOSAL_ORDERS_LIST,
  NTIS_ORD_IMPORT_FOR_ORG_LIST,
  NTIS_ORD_IMPORT_VIEW_PAGE,
  NTIS_OWNER_DISPOSAL_ORDERS_LIST,
  NTIS_OWNER_TECH_ORDERS_LIST,
  NTIS_SERVICE_ORDER_EDIT,
  NTIS_SERVICE_ORDER_PAGE,
  NTIS_SERVICE_SEARCH,
  NTIS_SEWAGE_DELIVERIES_LIST,
  NTIS_SEWAGE_DELIVERY_EDIT,
  NTIS_SEWAGE_DELIVERY_VIEW,
  NTIS_SP_DASHBOARD,
  ORDER_RECEIVED_OUTSIDE_NTIS_CREATE,
  TECH_ORDERS_LIST,
} from '../../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../../ntis-shared/constants/ntis-routing.const';

const routes: Routes = [
  {
    path: '',
    data: {
      breadcrumb: 'wteMaintenanceAndOperation.techSupport.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faBriefcase' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
        data: {
          breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
          formCode: NTIS_SERVICE_ORDER_EDIT,
        },
        loadComponent: () =>
          import('./client/pages/service-order-edit/service-order-edit.component').then(
            (m) => m.ServiceOrderEditComponent
          ),
      },
      {
        path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
          formCode: NTIS_SERVICE_ORDER_PAGE,
        },
        loadComponent: () =>
          import('./client/pages/service-order-page/service-order-page.component').then(
            (m) => m.ServiceOrderPageComponent
          ),
      },
      {
        path: NtisRoutingConst.OWNER_DISPOSAL_ORDERS_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.ownerDisposalOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_OWNER_DISPOSAL_ORDERS_LIST },
            loadComponent: () =>
              import('./client/pages/owner-disposal-orders-list/owner-disposal-orders-list.component').then(
                (m) => m.OwnerDisposalOrdersListComponent
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
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./client/pages/service-order-page/service-order-page.component').then(
                (m) => m.ServiceOrderPageComponent
              ),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
              formCode: NTIS_SERVICE_ORDER_EDIT,
            },
            loadComponent: () =>
              import('./client/pages/service-order-edit/service-order-edit.component').then(
                (m) => m.ServiceOrderEditComponent
              ),
          },
        ],
      },
      {
        path: `${NtisRoutingConst.OWNER_DISPOSAL_ORDERS_LIST}/${RoutingConst.PARAM_ID}`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.ownerDisposalOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_OWNER_DISPOSAL_ORDERS_LIST },
            loadComponent: () =>
              import('./client/pages/owner-disposal-orders-list/owner-disposal-orders-list.component').then(
                (m) => m.OwnerDisposalOrdersListComponent
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
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./client/pages/service-order-page/service-order-page.component').then(
                (m) => m.ServiceOrderPageComponent
              ),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
              formCode: NTIS_SERVICE_ORDER_EDIT,
            },
            loadComponent: () =>
              import('./client/pages/service-order-edit/service-order-edit.component').then(
                (m) => m.ServiceOrderEditComponent
              ),
          },
        ],
      },

      {
        path: NtisRoutingConst.OWNER_TECH_ORDERS_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.ownerTechOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_OWNER_TECH_ORDERS_LIST },
            loadComponent: () =>
              import('./client/pages/owner-tech-orders-list/owner-tech-orders-list.component').then(
                (m) => m.OwnerTechOrdersListComponent
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
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./client/pages/service-order-page/service-order-page.component').then(
                (m) => m.ServiceOrderPageComponent
              ),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
              formCode: NTIS_SERVICE_ORDER_EDIT,
            },
            loadComponent: () =>
              import('./client/pages/service-order-edit/service-order-edit.component').then(
                (m) => m.ServiceOrderEditComponent
              ),
          },
        ],
      },
      {
        path: `${NtisRoutingConst.OWNER_TECH_ORDERS_LIST}/${RoutingConst.PARAM_ID}`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.ownerTechOrdersList.headerText',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_OWNER_TECH_ORDERS_LIST },
            loadComponent: () =>
              import('./client/pages/owner-tech-orders-list/owner-tech-orders-list.component').then(
                (m) => m.OwnerTechOrdersListComponent
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
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./client/pages/service-order-page/service-order-page.component').then(
                (m) => m.ServiceOrderPageComponent
              ),
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_EDIT}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'institutionsAdmin.pages.serviceOrderEdit.headerText',
              formCode: NTIS_SERVICE_ORDER_EDIT,
            },
            loadComponent: () =>
              import('./client/pages/service-order-edit/service-order-edit.component').then(
                (m) => m.ServiceOrderEditComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.SEWAGE_DELIVERIES,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveriesList.breadcrumb',
          formCode: NTIS_SEWAGE_DELIVERIES_LIST,
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import(
                './service-provider/pages/sewage-delivery/sewage-deliveries-list/sewage-deliveries-list.component'
              ).then((m) => m.SewageDeliveriesListComponent),
          },
          {
            path: RoutingConst.VIEW_WITH_ID,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryView.headerText',
              formCode: NTIS_SEWAGE_DELIVERY_VIEW,
            },
            loadComponent: () =>
              import(
                './service-provider/pages/sewage-delivery/sewage-delivery-view/sewage-delivery-view.component'
              ).then((m) => m.SewageDeliveryViewComponent),
          },
          {
            path: `${RoutingConst.EDIT}/${RoutingConst.NEW}`,
            data: {
              breadcrumb:
                'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryEdit.headerTextNew',
              formCode: NTIS_SEWAGE_DELIVERY_EDIT,
            },
            loadComponent: () =>
              import(
                './service-provider/pages/sewage-delivery/sewage-delivery-edit/sewage-delivery-edit.component'
              ).then((m) => m.SewageDeliveryEditComponent),
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            data: {
              breadcrumb:
                'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryEdit.headerTextEdit',
            },
            children: [
              {
                path: '',
                data: { formCode: NTIS_SEWAGE_DELIVERY_EDIT },
                loadComponent: () =>
                  import(
                    './service-provider/pages/sewage-delivery/sewage-delivery-edit/sewage-delivery-edit.component'
                  ).then((m) => m.SewageDeliveryEditComponent),
              },
              {
                path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
                  formCode: NTIS_SERVICE_ORDER_PAGE,
                },
                loadComponent: () =>
                  import('./client/pages/service-order-page/service-order-page.component').then(
                    (m) => m.ServiceOrderPageComponent
                  ),
              },
            ],
          },
        ],
      },
      {
        path: NtisRoutingConst.TECH_ORDERS_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.techOrdersList.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: TECH_ORDERS_LIST },
            loadComponent: () =>
              import('./service-provider/pages/tech-orders-list-page/tech-orders-list-page.component').then(
                (m) => m.TechOrdersListPageComponent
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
            path: NtisRoutingConst.ORDER_OUTSIDE_NTIS_CREATE,
            data: {
              formCode: ORDER_RECEIVED_OUTSIDE_NTIS_CREATE,
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.orderOutsideNtis.breadcrumb',
            },
            loadComponent: () =>
              import(
                './service-provider/pages/order-outside-ntis-create-page/order-outside-ntis-create-page.component'
              ).then((m) => m.OrderOutsideNtisCreatePageComponent),
          },
          {
            path: ':ordState',
            children: [
              {
                path: '',
                data: { formCode: TECH_ORDERS_LIST },
                loadComponent: () =>
                  import('./service-provider/pages/tech-orders-list-page/tech-orders-list-page.component').then(
                    (m) => m.TechOrdersListPageComponent
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
                path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                data: {
                  breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
                  formCode: NTIS_SERVICE_ORDER_PAGE,
                },
                loadComponent: () =>
                  import('./client/pages/service-order-page/service-order-page.component').then(
                    (m) => m.ServiceOrderPageComponent
                  ),
              },
            ],
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
              formCode: NTIS_SERVICE_ORDER_PAGE,
            },
            loadComponent: () =>
              import('./client/pages/service-order-page/service-order-page.component').then(
                (m) => m.ServiceOrderPageComponent
              ),
          },
        ],
      },
      {
        path: NtisRoutingConst.DISPOSAL_ORDERS_LIST,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.disposalOrdersList.breadcrumb',
        },
        children: [
          {
            path: '',

            children: [
              {
                path: '',
                data: { formCode: NTIS_DISPOSAL_ORDERS_LIST },
                loadComponent: () =>
                  import('./service-provider/pages/disposal-orders-list-page/disposal-orders-list-page.component').then(
                    (m) => m.DisposalOrdersListPageComponent
                  ),
              },
              {
                path: NtisRoutingConst.ORDER_OUTSIDE_NTIS_CREATE,
                data: {
                  formCode: ORDER_RECEIVED_OUTSIDE_NTIS_CREATE,
                  breadcrumb:
                    'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.orderOutsideNtis.breadcrumb',
                },
                loadComponent: () =>
                  import(
                    './service-provider/pages/order-outside-ntis-create-page/order-outside-ntis-create-page.component'
                  ).then((m) => m.OrderOutsideNtisCreatePageComponent),
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
                path: ':ordState',
                children: [
                  {
                    path: '',
                    data: { formCode: NTIS_DISPOSAL_ORDERS_LIST },
                    loadComponent: () =>
                      import(
                        './service-provider/pages/disposal-orders-list-page/disposal-orders-list-page.component'
                      ).then((m) => m.DisposalOrdersListPageComponent),
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
                              import(
                                '../../ntis-shared/pages/ord-import-view-page/ord-import-view-page.component'
                              ).then((m) => m.OrdImportViewPageComponent),
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
                    path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                    data: {
                      breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
                      formCode: NTIS_SERVICE_ORDER_PAGE,
                    },
                    loadComponent: () =>
                      import('./client/pages/service-order-page/service-order-page.component').then(
                        (m) => m.ServiceOrderPageComponent
                      ),
                  },
                ],
              },
              {
                path: `${NtisRoutingConst.SEWAGE_DELIVERIES}/${RoutingConst.EDIT_WITH_ID}`,
                data: {
                  breadcrumb:
                    'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryEdit.headerTextNew',
                },
                children: [
                  {
                    path: '',
                    data: { formCode: NTIS_SEWAGE_DELIVERY_EDIT },
                    loadComponent: () =>
                      import(
                        './service-provider/pages/sewage-delivery/sewage-delivery-edit/sewage-delivery-edit.component'
                      ).then((m) => m.SewageDeliveryEditComponent),
                  },
                  {
                    path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                    data: {
                      breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
                      formCode: NTIS_SERVICE_ORDER_PAGE,
                    },
                    loadComponent: () =>
                      import('./client/pages/service-order-page/service-order-page.component').then(
                        (m) => m.ServiceOrderPageComponent
                      ),
                  },
                ],
              },
              {
                path: `${NtisRoutingConst.SEWAGE_DELIVERIES}/${RoutingConst.NEW}/:ordId`,
                data: {
                  breadcrumb:
                    'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryEdit.headerTextNew',
                },
                children: [
                  {
                    path: '',
                    data: { formCode: NTIS_SEWAGE_DELIVERY_EDIT },
                    loadComponent: () =>
                      import(
                        './service-provider/pages/sewage-delivery/sewage-delivery-edit/sewage-delivery-edit.component'
                      ).then((m) => m.SewageDeliveryEditComponent),
                  },
                  {
                    path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                    data: {
                      breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
                      formCode: NTIS_SERVICE_ORDER_PAGE,
                    },
                    loadComponent: () =>
                      import('./client/pages/service-order-page/service-order-page.component').then(
                        (m) => m.ServiceOrderPageComponent
                      ),
                  },
                ],
              },
            ],
          },
          {
            path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
            data: {
              breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
            },
            children: [
              {
                path: '',
                data: {
                  formCode: NTIS_SERVICE_ORDER_PAGE,
                },
                loadComponent: () =>
                  import('./client/pages/service-order-page/service-order-page.component').then(
                    (m) => m.ServiceOrderPageComponent
                  ),
              },
              {
                path: `${NtisRoutingConst.SEWAGE_DELIVERIES}/${RoutingConst.NEW}/:ordId`,
                data: {
                  breadcrumb:
                    'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryEdit.headerTextNew',
                },
                children: [
                  {
                    path: '',
                    data: { formCode: NTIS_SEWAGE_DELIVERY_EDIT },
                    loadComponent: () =>
                      import(
                        './service-provider/pages/sewage-delivery/sewage-delivery-edit/sewage-delivery-edit.component'
                      ).then((m) => m.SewageDeliveryEditComponent),
                  },
                  {
                    path: `${NtisRoutingConst.SERVICE_ORDER_PAGE}/${RoutingConst.PARAM_ID}`,
                    data: {
                      breadcrumb: 'wteMaintenanceAndOperation.techSupport.client.pages.serviceOrderPage.headerText',
                      formCode: NTIS_SERVICE_ORDER_PAGE,
                    },
                    loadComponent: () =>
                      import('./client/pages/service-order-page/service-order-page.component').then(
                        (m) => m.ServiceOrderPageComponent
                      ),
                  },
                ],
              },
            ],
          },
        ],
      },
      {
        path: `${NtisRoutingConst.SERVICE_PROVIDER}/${RoutingConst.DASHBOARD}`,
        data: {
          breadcrumb: 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.dashboard.breadcrumb',
        },
        children: [
          {
            path: '',
            data: { formCode: NTIS_SP_DASHBOARD },
            loadComponent: () =>
              import(
                './service-provider/pages/service-provider-dashboard-page/service-provider-dashboard-page.component'
              ).then((m) => m.ServiceProviderDashboardPageComponent),
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
export class TechSupportInternalRoutingModule {}
