import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import {
  ACTIONS_COPY,
  ACTIONS_CREATE,
  ACTIONS_READ,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { NTIS_INTS_OWNER_DASHBOARD } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '../../../ntis-shared/constants/ntis-routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import {
  NtisINTSDashboardContract,
  NtisINTSDashboardModel,
  NtisINTSDashboardWastewater,
} from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import Point from 'ol/geom/Point';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { MenuItem } from 'primeng/api';
import { tap } from 'rxjs';
import { StatusData } from '../../../ntis-shared/models/record-status';
import { MapComponent } from '../../../standalone/map/components/map/map.component';
import { ProjectionLikeCode } from '../../../standalone/map/enums/projection-like-code';
import * as proj from 'ol/proj';
import { ClientApiService } from '../../services/client-api.service';
import { Feature } from 'ol';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import {
  NtisFaciUpdateReq,
  NtisFacilityOwnerType,
  ServiceItemType,
} from '../../../ntis-shared/enums/classifiers.enums';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { NoRegisteredFaciDialogComponent } from '@itree-web/src/app/ntis-shared/components/no-registered-faci-dialog/no-registered-faci-dialog.component';
import { CLOSED } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { TooltipModule } from 'primeng/tooltip';
import { getSearchMapMenu } from '@itree-web/src/app/standalone/map/utils/getDefaultMapMenus';
import { MapMenu } from '@itree-web/src/app/standalone/map/types/map-menu';
import Geometry from 'ol/geom/Geometry';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-ntis-ints-dashboard-page',
  templateUrl: './ntis-ints-dashboard-page.component.html',
  styleUrls: ['./ntis-ints-dashboard-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    DialogModule,
    FormsModule,
    ItreeCommonsModule,
    MapComponent,
    NtisSharedModule,
    RouterModule,
    TableModule,
    NoRegisteredFaciDialogComponent,
    TooltipModule,
    FontAwesomeModule,
  ],
})
export class NtisIntsDashboardPageComponent implements OnInit, AfterViewInit {
  // routings
  readonly toAddNewFacility = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.EDIT}/${RoutingConst.NEW}`;
  readonly toContractsList = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.CONTRACTS}/${NtisRoutingConst.CONTRACTS_LIST}`;
  readonly toDisposalOrdersList = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.OWNER_DISPOSAL_ORDERS_LIST}`;
  readonly toEditFacility = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.EDIT}`;
  readonly toLabOrdersList = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.RESEARCH}/${NtisRoutingConst.OWNER_RESEARCH_LIST}`;
  readonly toManageIntsManager = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.WF_MANAGERS_LIST}`;
  readonly toOrderNewFromPrevious = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.SERVICE_ORDER_EDIT}`;
  readonly toTechOrdersList = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.OWNER_TECH_ORDERS_LIST}`;
  readonly toViewContract = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.CONTRACTS_LIST}/${RoutingConst.EDIT}/${NtisRoutingConst.CLIENT}`;
  readonly toViewIntsInfo = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY}/${RoutingConst.VIEW}`;
  readonly toViewOrder = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.SERVICE_ORDER_PAGE}`;
  readonly toViewLabOrder = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD}/${NtisRoutingConst.RESEARCH_ORDER_PAGE}`;
  readonly toRenewalRequest = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.BUILDING_DATA}/${NtisRoutingConst.WASTEWATER_AGRREMENTS}`;
  // constants
  readonly translationsReference = 'pages.intsOwnerDashboard';
  readonly reviewsTranslationsReference = 'ntisShared.pages.reviewCreate';
  readonly formCode = NTIS_INTS_OWNER_DASHBOARD;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly confirmed = NtisFaciUpdateReq.CONFIRMED;
  readonly newOrder = ACTIONS_CREATE;
  readonly viewOrder = ACTIONS_READ;
  readonly VEZIMAS = ServiceItemType.sewageRemoval;
  readonly PRIEZIURA = ServiceItemType.maintenance;
  readonly TYRIMAI = ServiceItemType.tests;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly OWNER = NtisFacilityOwnerType.OWNER;
  readonly SELF_ASSIGNED = NtisFacilityOwnerType.SELF_ASSIGNED;
  // available actions
  userCanCreate = false;

  // reviews related
  showReviewDialog = false;
  revComment: string;
  revScore: number;

  //map related
  @ViewChild(MapComponent, { static: false }) mapComponent: MapComponent;
  @ViewChild('mapReference') mapReference: ElementRef<HTMLDivElement>;
  showMap: boolean = false;
  contentWidth: number;
  mapMenuItems: MapMenu[] = [getSearchMapMenu()];
  vectorLayer = new VectorLayer({
    source: new VectorSource(),
  });

  errorDialog: boolean = false;
  selectedWtf: number;
  selectedWtfAddress: string;
  wastewaterRowMenuItems: Record<string, MenuItem[]> = {};
  contractServices: Map<number, NtisINTSDashboardContract[]> = new Map();
  availableServices: NtisINTSDashboardContract[];
  dashboardInfo: NtisINTSDashboardModel;
  paginator: boolean = false;
  chooseSrvTypeDialog: boolean = false;
  chooseSrvType: string;
  wasteWaterCols: TableColumn[] = [
    { field: 'wtf_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'wtf_address', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'wtf_type', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'wtf_state', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'wtf_fua_state', export: false, visible: false, type: DATA_TYPE_STRING },
  ];
  visibleWastewater = this.wasteWaterCols.filter((res) => res.visible);
  serviceOrderCols: TableColumn[] = [
    { field: 'ord_id', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'wtf_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'ord_date', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'srv_provider', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_state', export: false, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleServiceOrder = this.serviceOrderCols.filter((res) => res.visible);
  contractCols: TableColumn[] = [
    { field: 'cot_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'cot_wtf_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'cot_srv_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'cot_srv_provider', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cot_created', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'cot_state', export: false, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleContract = this.contractCols.filter((res) => res.visible);
  labCols: TableColumn[] = [
    { field: 'lab_name', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_created', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'wtf_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'compliance_norms', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_state', export: false, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleLabs = this.labCols.filter((res) => res.visible);
  recordStatus: StatusData[] = [
    { status: 'CLOSED', type: 'risk', iconName: 'highlight_off' },
    { status: 'CONFIRMED', type: 'success', iconName: 'check_circle' },
    { status: 'REGISTERED', type: 'neutral', iconName: 'help_outline' },
  ];

  constructor(
    protected commonFormServices: CommonFormServices,
    private clientApiService: ClientApiService,
    public faIconsService: FaIconsService,
    private datePipe: S2DatePipe,
    private router: Router,
    private authService: AuthService
  ) {
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.loadDashboardInfo();
  }

  ngAfterViewInit(): void {
    this.mapComponent?.map.addLayer(this.vectorLayer);
  }

  loadDashboardInfo(): void {
    this.clientApiService
      .getINTSDashboardInfo()
      .pipe(
        tap((result) => {
          this.wastewaterRowMenuItems = {};
          this.selectedWtfAddress = undefined;
          this.selectedWtf = undefined;
          result.facilities.forEach((row) => {
            this.wastewaterRowMenuItems[row.wtf_id] = this.getWastewaterActions(result, row.wtf_id);
            if (row.fo_selected === DB_BOOLEAN_TRUE) {
              this.selectedWtf = row.wtf_id;
              this.selectedWtfAddress = row.wtf_address;
            }
          });
        })
      )
      .subscribe((result) => {
        if (result.facilities.length > 10) {
          this.paginator = true;
        }
        this.dashboardInfo = result;
      });
  }
  parseDate(date: Date): string {
    return this.datePipe.transform(date);
  }
  getWastewaterActions(row: NtisINTSDashboardModel, id: number): MenuItem[] {
    const menu: MenuItem[] = [];
    row.availableActions.forEach((action) => {
      const rowFacility = row.facilities.find((facility) => facility.wtf_id === id);
      if (
        action === ActionsEnum.ACTION_VIEW_INTS_INFO ||
        action === ActionsEnum.ACTION_MANAGE_INTS_MANAGERS ||
        action === ActionsEnum.ACTION_RENEW_INTS_INFO ||
        action === ActionsEnum.ACTION_REMOVE_FROM_ACCOUNT
      ) {
        if (
          (action === ActionsEnum.ACTION_VIEW_INTS_INFO &&
            (rowFacility.fo_owner_type !== this.SELF_ASSIGNED || rowFacility.ful_id)) ||
          (action === ActionsEnum.ACTION_RENEW_INTS_INFO &&
            rowFacility.wtf_state !== CLOSED &&
            rowFacility.wtf_fua_state !== NtisFaciUpdateReq.SUBMITTED &&
            (rowFacility.fo_owner_type !== this.SELF_ASSIGNED || rowFacility.ful_id)) ||
          (action === ActionsEnum.ACTION_MANAGE_INTS_MANAGERS &&
            rowFacility.wtf_state !== CLOSED &&
            (rowFacility.fo_owner_type === this.OWNER || rowFacility.ful_id)) ||
          (action === ActionsEnum.ACTION_REMOVE_FROM_ACCOUNT &&
            rowFacility.wtf_fua_state !== NtisFaciUpdateReq.SUBMITTED)
        ) {
          if (
            rowFacility.ful_id ||
            action !== ActionsEnum.ACTION_RENEW_INTS_INFO ||
            (action == ActionsEnum.ACTION_RENEW_INTS_INFO && rowFacility.fua_id == null)
          ) {
            menu.push({
              label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
              id: id.toString(),
              icon: getActionIcons(action),
              command: (): void => {
                if (action === ActionsEnum.ACTION_VIEW_INTS_INFO) {
                  void this.router.navigate([this.toViewIntsInfo, id.toString()]);
                } else if (action === ActionsEnum.ACTION_RENEW_INTS_INFO) {
                  void this.router.navigate([this.toEditFacility, id.toString()]);
                } else if (action === ActionsEnum.ACTION_MANAGE_INTS_MANAGERS) {
                  void this.router.navigate([this.toManageIntsManager, id.toString()]);
                } else if (action === ActionsEnum.ACTION_REMOVE_FROM_ACCOUNT) {
                  this.removeFacilityWithConfirmation(rowFacility.wtf_id.toString());
                }
              },
            });
          }
        }
      }
    });
    return menu;
  }
  protected removeFacility(id: string): void {
    this.clientApiService.removeFacility(id).subscribe(() => {
      this.loadDashboardInfo();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  removeFacilityWithConfirmation(id: string): void {
    this.commonFormServices.translate
      .get('common.message.removeFacilityConfirmation')
      .subscribe((translation: string) => {
        this.commonFormServices.confirmationService.confirm({
          message: translation,
          accept: () => {
            this.removeFacility(id);
          },
        });
      });
  }

  updateSelectedWtf(): void {
    this.clientApiService.updateSelectedWtf(this.selectedWtf).subscribe(() => {
      this.loadDashboardInfo();
    });
  }

  navigateToServiceSearch(srvType?: string): void {
    if (this.dashboardInfo.facilities.length === 0) {
      this.errorDialog = true;
    } else {
      if (srvType !== null) {
        void this.router.navigate(
          [
            RoutingConst.INTERNAL,
            NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
            NtisRoutingConst.CONTRACTS,
            NtisRoutingConst.SERVICE_SEARCH,
          ],
          {
            queryParams: { srvType: srvType },
          }
        );
      } else {
        void this.router.navigate([
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.CONTRACTS,
          NtisRoutingConst.SERVICE_SEARCH,
        ]);
      }
    }
  }

  navigateToOrderFromPrevious(id: string): void {
    void this.router.navigate([this.toOrderNewFromPrevious, id], {
      queryParams: { actionType: ACTIONS_COPY },
    });
  }

  showOnMap(row: NtisINTSDashboardWastewater): void {
    const coordinates: number[] = [row.latitude, row.longitude];
    const point = new Point(proj.transform(coordinates, ProjectionLikeCode.LKS94, ProjectionLikeCode.WebMercator));
    this.mapComponent.setCenterCoordinates(coordinates, 17, ProjectionLikeCode.LKS94, this.showMap);
    const featureObj = new Feature({
      geometry: point,
      name: 'facilityAddress',
      coordinates: coordinates,
    });
    const iconStyle = new Style({
      image: new Icon({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: `../../../../../assets/img/pin_red.png`,
      }),
    });
    featureObj.setStyle(iconStyle);
    this.vectorLayer
      .getSource()
      .removeFeature(this.vectorLayer.getSource().getFeatureById('facilityAddress') as Feature<Geometry>);
    this.vectorLayer.getSource().addFeature(featureObj);
    featureObj.setId('facilityAddress');
    this.showMap = true;
  }

  viewReview(revScore: number, revComment: string): void {
    this.revScore = revScore;
    this.revComment = revComment;
    this.showReviewDialog = true;
  }

  navigateToCreateReview(revId: number): void {
    void this.commonFormServices.router.navigate([RoutingConst.INTERNAL, NtisRoutingConst.REVIEW_CREATE, revId], {
      queryParams: { returnUrl: this.commonFormServices.router.url },
    });
  }
}
