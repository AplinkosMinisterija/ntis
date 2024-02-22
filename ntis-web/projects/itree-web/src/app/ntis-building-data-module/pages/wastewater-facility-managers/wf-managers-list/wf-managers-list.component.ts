import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule } from '@angular/router';
import { DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, NtisFacilityManagerEditModel } from '@itree-commons/src/lib/model/api/api';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_WF_MANAGERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { StatusData } from '@itree-web/src/app/ntis-shared/models/record-status';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { Subject, map, takeUntil } from 'rxjs';
import { TableColumn, WfManagersListRow } from '../../../models/browse-page';
import { BuildingDataService } from '../../../services/building-data.service';

@Component({
  selector: 'app-wf-managers-list',
  templateUrl: './wf-managers-list.component.html',
  styleUrls: ['./wf-managers-list.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    DialogModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    RouterModule,
    TableModule,
  ],
})
export class WfManagersListComponent extends BaseBrowseForm<WfManagersListRow> implements OnInit {
  readonly formCode = NTIS_WF_MANAGERS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'ntisBuildingData.pages.wfManagersList';
  readonly activeStatus = 'ACTIVE';
  readonly RoutingConst = RoutingConst;
  destroy$: Subject<boolean> = new Subject<boolean>();
  wtfId: string;
  selectedWtf: string;
  isOwner: boolean;
  renewalRightsDialog: boolean = false;
  minDate: Date;

  cols: TableColumn[] = [
    { field: 'manager', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'full_date', export: false, visible: false, type: DATA_TYPE_STRING },
  ];

  recordStatus: StatusData[] = [
    { status: 'ACTIVE', type: 'success', iconName: 'check_circle' },
    { status: 'INACTIVE', type: 'risk', iconName: 'highlight_off' },
  ];

  constructor(
    private activatedRoute: ActivatedRoute,
    private buildingDataService: BuildingDataService,
    protected override commonFormServices: CommonFormServices
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.wtfId = (params.id as string) || null;
      this.buildingDataService.getWfManagerWtf(this.wtfId).subscribe((result) => {
        this.selectedWtf = result?.wtf_address;
      });
    });
    this.minDate = new Date();
  }

  form = new FormGroup({
    fo_id: new FormControl<number>(null),
    manager: new FormControl<string>(null),
    fo_date_from: new FormControl<Date>(null),
    fo_date_to: new FormControl<Date>(null),
  });

  ngOnInit(): void {
    this.searchForm.addControl('manager', new FormControl(''));
    this.searchForm.addControl('fo_date_from', new FormControl(''));
    this.searchForm.addControl('fo_date_to', new FormControl(''));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.buildingDataService.checkIsOwner(this.wtfId).subscribe((result) => {
      this.isOwner = result;
      if (this.isOwner) {
        const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
        this.buildingDataService
          .getWfManagers(pagingParams, params, this.wtfId, extendedParams)
          .pipe(
            map((result: BrowseFormSearchResult<WfManagersListRow>) => {
              result.data.forEach((row: WfManagersListRow) => {
                row.actions = this.getNtisActions(row);
              });
              return result;
            })
          )
          .subscribe((result) => {
            this.data = result.data;
            this.totalRecords = result.paging.cnt;
          });
      }
    });
  }

  getNtisActions(row: WfManagersListRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_UPDATE && row.status === this.activeStatus) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faPencil' },
          actionName: 'renewManagementRights',
          iconTheme: 'default',
          action: () => {
            this.setDialogValues(row);
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  setDialogValues(row: WfManagersListRow): void {
    this.form.controls.fo_id.setValue(row.fo_id);
    this.form.controls.manager.setValue(row.manager);
    this.form.controls.fo_date_from.setValue(row.date_from);
    this.form.controls.fo_date_to.setValue(row.date_to);
    this.renewalRightsDialog = true;
  }

  updateManagementRights(): void {
    const managerForUpdate: NtisFacilityManagerEditModel = {
      fo_id: this.form.controls.fo_id.value,
      fo_wtf_id: parseInt(this.wtfId),
      fo_date_to: this.form.controls.fo_date_to.value,
      fo_date_from: this.form.controls.fo_date_from.value,
      per_code: null,
      per_name: null,
      per_surname: null,
    };
    this.buildingDataService.updateManagerDate(managerForUpdate).subscribe(() => {
      this.renewalRightsDialog = false;
      this.commonFormServices.translate
        .get(this.translationsReference + '.managerDetailsUpdated')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
    });
    this.reload();
  }

  navigateToAddNew(): void {
    void this.commonFormServices.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      NtisRoutingConst.BUILDING_DATA,
      NtisRoutingConst.WF_MANAGERS_LIST,
      RoutingConst.EDIT,
      this.wtfId,
    ]);
  }
}
