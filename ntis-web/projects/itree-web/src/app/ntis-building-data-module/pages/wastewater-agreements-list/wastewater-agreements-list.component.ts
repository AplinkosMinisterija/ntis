import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { TableModule } from 'primeng/table';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { FilterInputType } from '@itree-commons/src/lib/enums/input-type.enums';
import { MenuItem } from 'primeng/api';
import { Subject } from 'rxjs';
import { NtisWtfAgreements, TableColumn } from '../../models/browse-page';
import { BuildingDataService } from '../../services/building-data.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CalendarModule } from 'primeng/calendar';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { Router, RouterModule } from '@angular/router';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { TooltipModule } from 'primeng/tooltip';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-wastewater-agreements-list',
  templateUrl: './wastewater-agreements-list.component.html',
  styleUrls: ['./wastewater-agreements-list.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
    FormsModule,
    CalendarModule,
    FontAwesomeModule,
    RouterModule,
    TooltipModule,
  ],
})
export class WastewaterAgreementsListComponent extends BaseBrowseForm<NtisWtfAgreements> implements OnInit, OnDestroy {
  readonly formTranslationsReference = 'ntisBuildingData.pages.wfAgrrementsList';
  readonly NTIS_BUILDING = NtisRoutingConst.NTIS_BUILDING;
  cols: TableColumn[];
  visibleColumns: TableColumn[];
  disabledForm = new FormGroup({});
  menuActions: Record<string, MenuItem[]> = {};
  exportData: NtisWtfAgreements[] = [];
  destroy$: Subject<boolean> = new Subject<boolean>();
  displayRecEdit: boolean = false;
  typeFilter: Map<string, string> = new Map([
    ['BUILDING', 'NTR objektas'],
    ['WASTEWATER', 'Individuali nuotekų tvarkymo sistema'],
  ]);
  constructor(
    protected override commonFormServices: CommonFormServices,
    private buildingDataService: BuildingDataService,
    private router: Router,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.cols = [
      {
        field: 'fua_id',
        export: true,
        visible: true,
        filterable: true,
        filterType: FilterInputType.number,
      },
      { field: 'fua_created', export: true, visible: true, filterable: true, filterType: FilterInputType.date },
      {
        field: 'person',
        export: true,
        visible: true,
        filterable: true,
        filterType: FilterInputType.text,
      },
      {
        field: 'fua_state_text',
        export: true,
        visible: true,
        filterable: true,
        filterType: FilterInputType.text,
        filterDomain: 'NTIS_WF_AGRREMENT_STATE',
      },
      { field: 'full_address_text', export: true, visible: true, filterable: true, filterType: FilterInputType.text },
      {
        field: 'fua_type_text',
        export: true,
        visible: true,
        filterable: true,
        filterType: FilterInputType.text,
        filterDomain: 'NTIS_BUILDING_TYPE',
      },
    ];
    this.cols.forEach((col) => {
      if (col.filterable) {
        this.searchForm.addControl(col.field, new FormControl(''));
      }
    });
    this.visibleColumns = this.cols.filter((res) => res.visible);
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.buildingDataService.getWtfAgrrementsList(pagingParams, params, extendedParams).subscribe((tableData) => {
      tableData.data.forEach((row) => {
        this.menuActions[row.fua_id] = this.getActions(row);
      });
      this.data = tableData.data;
      this.totalRecords = tableData.paging.cnt;
      this.exportData = tableData.data;
    });
  }

  loadAllRecordsForExport(allRecords: boolean): void {
    if (allRecords) {
      this.buildingDataService
        .getWtfAgrrementsList(
          this.getPagingParams(
            0,
            MAX_ROWS_FOR_EXPORT,
            this.dataTableComponent.sortField,
            this.dataTableComponent.sortOrder,
            null
          ),
          this.getSearchParamMap(this.dataTableComponent),
          this.getExtendedSearchParams(this.dataTableComponent)
        )
        .subscribe((result) => {
          this.exportData = result.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  getActions(row: NtisWtfAgreements): MenuItem[] {
    return row.availableActions.map((action) => {
      const menuItem: MenuItem = {
        label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
        id: row.fua_id?.toString(),
        icon: getActionIcons(action),
        command: (): void => {
          if (action === ActionsEnum.ACTIONS_UPDATE) {
            this.displayRecEdit = true;
          }
        },
      };
      return menuItem;
    });
  }

  protected deleteRecord(recordId: string): void {
    console.log('Do not need  to implement' + recordId);
  }
}
