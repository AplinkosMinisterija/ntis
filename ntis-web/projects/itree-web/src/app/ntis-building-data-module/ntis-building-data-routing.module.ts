import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import {
  NTIS_AGREEMENTS_LIST,
  NTIS_WASTEWATER_FACILITY_EDIT,
  NTIS_WASTEWATER_FACILITY_LIST,
  WASTEWATER_FACILITY_VIEW,
} from '../ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '../ntis-shared/constants/ntis-routing.const';
import { WastewaterFacilityBuildingEditResolver } from './resolvers/wastewater-facility-building-edit.resolver';
import { WastewaterFacilityBuildingViewResolver } from './resolvers/wastewater-facility-building-view.resolver';
import { WastewaterFacilityEditResolver } from './resolvers/wastewater-facility-edit.resolver';
import { WastewaterFacilityViewResolver } from './resolvers/wastewater-facility-view.resolver';
import { WastewaterAgreementConfirmationResolver } from './pages/wastewater-agreements-confirmation/wastewater-agreement-confirmation.resolver';
import { WastewaterObjAgreementConfirmationResolver } from './pages/wastewater-obj-agreements-confirmation/wastewater-obj-agreement-confirmation.resolver';

const routes: Routes = [
  {
    path: '',
    data: {
      breadcrumb: 'ntisBuildingData.breadcrumb',
      breadcrumbIcon: { iconStyle: 'fas', iconName: 'faBuilding' },
      breadcrumbUrl: null,
    },
    children: [
      {
        path: NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        data: {
          breadcrumb: 'ntisBuildingData.pages.wastewaterFacilityList.headerText',
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/wastewater-facility/wastewater-facility-list/wastewater-facility-list.component').then(
                (m) => m.WastewaterFacilityListComponent
              ),
            data: {
              formCode: NTIS_WASTEWATER_FACILITY_LIST,
            },
          },
          {
            path: RoutingConst.VIEW_WITH_ID,
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
                  import('./pages/wastewater-facility/wastewater-facility-view/wastewater-facility-view').then(
                    (m) => m.WastewaterFacilityViewComponent
                  ),
              },
              {
                path: RoutingConst.EDIT_WITH_ID,
                loadComponent: () =>
                  import(
                    './pages/wastewater-facility/wastewater-facility-edit/wastewater-facility-edit.component'
                  ).then((m) => m.WastewaterFacilityEditComponent),
                resolve: {
                  wastewaterFacilityData: WastewaterFacilityEditResolver,
                },
              },
            ],
          },
          {
            path: RoutingConst.EDIT_WITH_ID,
            loadComponent: () =>
              import('./pages/wastewater-facility/wastewater-facility-edit/wastewater-facility-edit.component').then(
                (m) => m.WastewaterFacilityEditComponent
              ),
            data: { formCode: NTIS_WASTEWATER_FACILITY_EDIT },
            resolve: {
              wastewaterFacilityData: WastewaterFacilityEditResolver,
            },
          },
          {
            path: `${NtisRoutingConst.NTR_BUILDING}/${RoutingConst.VIEW_WITH_ID}`,
            data: {
              breadcrumb: 'ntisBuildingData.pages.ntrBuildingView.headerText',
              formCode: WASTEWATER_FACILITY_VIEW,
            },
            loadComponent: () =>
              import(
                './pages/wastewater-facility/wastewater-facility-building-view/wastewater-facility-building-view.component'
              ).then((m) => m.WastewaterFacilityBuildingViewComponent),
            resolve: {
              wastewaterFacilityBuildingData: WastewaterFacilityBuildingViewResolver,
            },
          },
          {
            path: `${NtisRoutingConst.NTR_BUILDING}/${RoutingConst.EDIT_WITH_ID}`,
            data: {
              breadcrumb: 'ntisBuildingData.pages.wastewaterFacilityBuildingEditComponent.headerText',
            },
            loadComponent: () =>
              import(
                './pages/wastewater-facility/wastewater-facility-building-edit/wastewater-facility-building-edit.component'
              ).then((m) => m.WastewaterFacilityBuildingEditComponent),
            resolve: {
              wastewaterFacilityBuildingEdit: WastewaterFacilityBuildingEditResolver,
            },
          },
        ],
      },
      {
        path: `${NtisRoutingConst.WASTEWATER_AGRREMENTS}`,
        data: {
          breadcrumb: 'ntisBuildingData.pages.wfAgrrementsList.breadcrumb',
        },
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/wastewater-agreements-list/wastewater-agreements-list.component').then(
                (m) => m.WastewaterAgreementsListComponent
              ),
            data: {
              formCode: NTIS_AGREEMENTS_LIST,
            },
          },
          {
            path: `${RoutingConst.PARAM_ID}/:fuaId`,
            loadComponent: () =>
              import('./pages/wastewater-agreements-confirmation/wastewater-agreements-confirmation.component').then(
                (m) => m.WastewaterAgreementsConfirmationComponent
              ),
            data: {
              formCode: NTIS_AGREEMENTS_LIST,
              breadcrumb: 'ntisBuildingData.pages.wfAgrrementsList.headerText',
            },
            resolve: {
              // wastewaterFacilityViewData: WastewaterFacilityViewResolver,
              wastewaterAgreementConfirmationResolver: WastewaterAgreementConfirmationResolver,
            },
          },
          {
            path: `${RoutingConst.PARAM_ID}/${NtisRoutingConst.NTIS_BUILDING}/:fuaId`,
            loadComponent: () =>
              import(
                './pages/wastewater-obj-agreements-confirmation/wastewater-obj-agreements-confirmation.component'
              ).then((m) => m.WastewaterObjAgreementsConfirmationComponent),
            data: {
              formCode: NTIS_AGREEMENTS_LIST,
              breadcrumb: 'ntisBuildingData.pages.wfAgrrementsList.headerText',
            },
            resolve: {
              // wastewaterFacilityViewData: WastewaterFacilityViewResolver,
              wastewaterAgreementConfirmationResolver: WastewaterObjAgreementConfirmationResolver,
              wastewaterFacilityBuildingData: WastewaterFacilityBuildingViewResolver,
            },
          },
          {
            path: `${NtisRoutingConst.NTR_BUILDING}/${RoutingConst.EDIT_WITH_ID}`,
            data: {
              breadcrumb: 'ntisBuildingData.pages.wastewaterFacilityBuildingEditComponent.headerText',
            },
            loadComponent: () =>
              import(
                './pages/wastewater-facility/wastewater-facility-building-edit/wastewater-facility-building-edit.component'
              ).then((m) => m.WastewaterFacilityBuildingEditComponent),
            resolve: {
              wastewaterFacilityBuildingEdit: WastewaterFacilityBuildingEditResolver,
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
export class NtisBuildingDataRoutingModule {}
