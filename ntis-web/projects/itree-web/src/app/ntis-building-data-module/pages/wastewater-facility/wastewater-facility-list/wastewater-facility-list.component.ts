/**
 * The `wastewater-facility-list` component is used to view the user's registered wastewater treatment facilities.
 * The wastewater treatment facilities can be viewed by the Service Assurance Company and the Water Management Company.
 *
 * @actions
 * - Edit - change the data of the wastewater treatment facility
 * - View - view the data of the wastewater treatment facility
 * - Confirm - confirm that the facility exists, when a new facility is registered it has a status of registered
 * - Remove from account - the facility is removed from the account, but the record remains in the database
 *
 *
 *
 */

import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { BuildingDataService } from '../../../services/building-data.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { NTIS_WASTEWATER_FACILITY_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  NtisWastewaterTreatmentFaciBrowseRow,
  NtisWastewaterTreatmentFaciExportRow,
  TableColumn,
  WtfServedObject,
} from '../../../models/browse-page';
import { Observable, Subject, tap } from 'rxjs';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { StatusData } from 'projects/itree-web/src/app/ntis-shared/models/record-status';
import { Table, TableModule } from 'primeng/table';
import { UNCONFIRMED } from '../../../constants/wastewater-facility.const';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';
import { NtisBuildingDataRoutingModule } from '../../../ntis-building-data-routing.module';
import { CLOSED, CONFIRMED } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { TooltipModule } from 'primeng/tooltip';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

export enum ThisFormActionsEnum {
  ACTION_VIEW_INTS_INFO = 'VIEW_INTS_INFO',
  ACTION_INTS_APPROVED = 'INTS_APPROVED',
  ACTION_REMOVE_FROM_ACCOUNT = 'REMOVE_FROM_ACCOUNT',
}

@Component({
  selector: 'app-wastewater-facility-list',
  templateUrl: './wastewater-facility-list.component.html',
  styleUrls: ['./wastewater-facility-list.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    NtisBuildingDataRoutingModule,
    NtisSharedModule,
    ReactiveFormsModule,
    TableModule,
    TooltipModule,
  ],
})
export class WastewaterFacilityListComponent
  extends BaseBrowseForm<NtisWastewaterTreatmentFaciBrowseRow>
  implements OnInit, OnDestroy
{
  readonly formTranslationsReference = 'ntisBuildingData.pages.wastewaterFacilityList';
  readonly unconfirmed = UNCONFIRMED;
  readonly formCode = NTIS_WASTEWATER_FACILITY_LIST;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;

  readonly destroy$ = new Subject<void>();
  wastewaterFacilityEditLink = RoutingConst.EDIT;
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: NtisWastewaterTreatmentFaciExportRow[] = [];
  userCanCreate: boolean;

  paramsIsEmpty: boolean = false;

  constructor(
    protected override commonFormServices: CommonFormServices,
    private buildingDataService: BuildingDataService,
    public faIconsService: FaIconsService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.searchForm.addControl('wtf_address', new FormControl(''));
    this.searchForm.addControl('wtf_state', new FormControl(''));
    this.searchForm.addControl('wtf_type', new FormControl(''));
    this.searchForm.addControl('wtf_latitude', new FormControl(''));
    this.searchForm.addControl('wtf_longitude', new FormControl(''));
    this.searchForm.addControl('bn_aob_code', new FormControl(''));
    this.searchForm.addControl('bn_obj_inv_code', new FormControl(''));
  }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }
  cols: TableColumn[] = [
    { field: 'wtf_id', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'wtf_address', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wtf_fl_latitude', export: true, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_fl_longitude', export: true, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_fua_state', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_id', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_served_objects', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_state', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_state_meaning', export: true, visible: false, type: DATA_TYPE_STRING },
    { field: 'wtf_title', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'coordinates', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'served_objects', export: true, visible: false, type: DATA_TYPE_STRING },
  ];

  recordStatus: StatusData[] = [
    { status: 'CLOSED', type: 'risk', iconName: 'highlight_off' },
    { status: 'CONFIRMED', type: 'success', iconName: 'check_circle' },
    { status: 'REGISTERED', type: 'neutral', iconName: 'help_outline' },
  ];

  visibleColumns = this.cols.filter((res) => res.visible);
  exportCols = this.cols.filter(
    (res) =>
      res.visible || res.field === 'served_objects' || res.field === 'wtf_id' || res.field === 'wtf_state_meaning'
  );

  loadTableData(
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>> {
    return this.buildingDataService.getWastewaterTreatmentFacilityList(pagingParams, params, extendedParams);
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.paramsIsEmpty = params && extendedParams.length === 0;

    this.loadTableData(this.getPagingParams(first, pageSize, sortField, order, null), params, extendedParams)
      .pipe(
        tap((result: BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>) => {
          this.rowMenuItems = {};
          result.data.forEach((row: NtisWastewaterTreatmentFaciBrowseRow) => {
            this.rowMenuItems[row.wtf_id] = this.getActions(row);
          });
        })
      )
      .subscribe((response: BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>) => {
        this.data = response.data;
        this.exportData = response.data as NtisWastewaterTreatmentFaciExportRow[];
        this.modifyExportRow(this.exportData);
        this.totalRecords = response.paging.cnt;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.loadTableData(
        this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
        this.getSearchParamMap(dataTable),
        this.getExtendedSearchParams(dataTable)
      ).subscribe((response: BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>) => {
        this.exportData = response.data as NtisWastewaterTreatmentFaciExportRow[];
        this.modifyExportRow(this.exportData);
      });
    } else {
      this.exportData = this.data as NtisWastewaterTreatmentFaciExportRow[];
      this.modifyExportRow(this.exportData);
    }
  }

  modifyExportRow(exportData: NtisWastewaterTreatmentFaciExportRow[]): void {
    exportData.forEach((row: NtisWastewaterTreatmentFaciExportRow) => {
      row.coordinates = row.wtf_fl_latitude + ', ' + row.wtf_fl_longitude;
      if (row.wtf_served_objects) {
        const servedObjectsJson: WtfServedObject[] = JSON.parse(row.wtf_served_objects) as WtfServedObject[];
        row.served_objects = servedObjectsJson.map((obj) => obj.so_address).join('; ');
      }
    });
  }

  protected deleteRecord(id: string): void {
    this.buildingDataService.removeFacility(id).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  navigateToAgreementView(id: string, fuaId: number): void {
    void this.commonFormServices.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.BUILDING_DATA,
      NtisRoutingConst.WASTEWATER_AGRREMENTS,
      id,
      fuaId,
    ]);
  }

  navigateToViewNtisInformation(id: number): void {
    if (id) {
      void this.commonFormServices.router.navigate([
        RoutingConst.INTERNAL,
        NtisRoutingConst.BUILDING_DATA,
        NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        RoutingConst.VIEW,
        id,
      ]);
    }
  }

  navigateToEdit(recId: string): void {
    void this.commonFormServices.router.navigate(
      [this.commonFormServices.router.url, this.wastewaterFacilityEditLink, recId],
      {
        queryParams: { actionType: 'edit' },
      }
    );
  }
  /**
   *
   * Approves the specified wastewater treatment facility.
   * After approval, the table will be reloaded and a success message will be displayed.
   *
   * @param id - the ID of the wastewater treatment facility to be approved
   */
  approveFacility(id: string): void {
    this.buildingDataService.approveFacility(id).subscribe(() => {
      this.reload();
      this.commonFormServices.translate
        .get(`${this.formTranslationsReference}.facilityIsConfirmed`)
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
    });
  }

  getActions(application: NtisWastewaterTreatmentFaciBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    type AllActionsEnum = ActionsEnum | ThisFormActionsEnum;
    application.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !application.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action: AllActionsEnum) => {
        if (
          action === ThisFormActionsEnum.ACTION_VIEW_INTS_INFO ||
          (action === ThisFormActionsEnum.ACTION_INTS_APPROVED &&
            application.wtf_state !== CONFIRMED &&
            application.wtf_state !== CLOSED) ||
          action === ThisFormActionsEnum.ACTION_REMOVE_FROM_ACCOUNT
        ) {
          menu.push({
            label: this.commonFormServices.translate.instant(this.getThisFormMenuCodes(action)) as string,
            id: application.wtf_id.toString(),
            icon: this.getThisFormActionsIcon(action),
            command: (): void => {
              if (action == ThisFormActionsEnum.ACTION_VIEW_INTS_INFO) {
                this.navigateToViewNtisInformation(application.wtf_id);
              } else if (action == ThisFormActionsEnum.ACTION_INTS_APPROVED) {
                this.approveFacility(application.wtf_id.toString());
              } else if (action == ThisFormActionsEnum.ACTION_REMOVE_FROM_ACCOUNT) {
                this.deleteRecordWithConfirmation(application.wtf_id.toString());
              }
            },
          });
        } else if (
          action !== ThisFormActionsEnum.ACTION_INTS_APPROVED &&
          action === ActionsEnum.ACTIONS_UPDATE &&
          application.wtf_state !== CLOSED &&
          !application.wtf_fua_id
        ) {
          menu.push({
            label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
            id: application.wtf_id.toString(),
            icon: getActionIcons(action),
            command: (): void => {
              if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
                this.navigateToEdit(application.wtf_id.toString());
              }
            },
          });
        }
      });
    return menu;
  }

  getThisFormMenuCodes(actionCode: ThisFormActionsEnum): string {
    const menuIcons = {
      [ThisFormActionsEnum.ACTION_VIEW_INTS_INFO]: `${
        this.formTranslationsReference
      }.${actionCode.toLocaleLowerCase()}`,
      [ThisFormActionsEnum.ACTION_INTS_APPROVED]: `${this.formTranslationsReference}.${actionCode.toLocaleLowerCase()}`,
      [ThisFormActionsEnum.ACTION_REMOVE_FROM_ACCOUNT]: `${
        this.formTranslationsReference
      }.${actionCode.toLocaleLowerCase()}`,
    };
    return menuIcons[actionCode];
  }

  getThisFormActionsIcon(actionCode: ThisFormActionsEnum): string {
    const menuIcons = {
      [ThisFormActionsEnum.ACTION_VIEW_INTS_INFO]: 'pi pi-fw pi-eye',
      [ThisFormActionsEnum.ACTION_INTS_APPROVED]: 'pi pi-fw pi-check-circle',
      [ThisFormActionsEnum.ACTION_REMOVE_FROM_ACCOUNT]: 'pi pi-fw pi-times-circle',
    };
    return menuIcons[actionCode];
  }
}
