import { Component, HostListener, OnInit } from '@angular/core';
import { NTIS_PRIORITY_WF_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  PriorityFacilitiesList,
  TableColumn,
  WaterManagerBrowseRow,
  WaterManagerFacility,
} from '../../models/browse-pages';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CalendarModule } from 'primeng/calendar';
import { ActivatedRoute } from '@angular/router';
import { PriorityFacilitiesOrgComponent } from '../../components/priority-facilities-org/priority-facilities-org.component';
import { DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';

export interface CheckedList {
  org_id: number;
  wto_id: number;
}

@Component({
  selector: 'app-priority-facilities-list',
  templateUrl: './priority-facilities-list.component.html',
  styleUrls: ['./priority-facilities-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
    FormsModule,
    FontAwesomeModule,
    CalendarModule,
    CommonModule,
    PriorityFacilitiesOrgComponent,
  ],
})
export class PriorityFacilitiesListComponent extends BaseBrowseForm<WaterManagerBrowseRow> implements OnInit {
  readonly translationsReference = 'institutionsAdmin.pages.priorityFacilitiesList';
  readonly formCode = NTIS_PRIORITY_WF_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  exportData: WaterManagerBrowseRow[] = [];
  isSelected: boolean;
  facilitiesList: WaterManagerFacility[] = [];
  displayModal: boolean = false;
  checkDataList: CheckedList[] = [];
  checkboxState: { [key: string]: boolean } = {};
  allowManageList = false;

  cols: TableColumn[] = [
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_phone', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_email', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_website', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  expansionColumns: TableColumn[] = [
    { field: 'wto_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'wto_org_id', export: true, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'wto_address', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_name', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  //TODO pataisyti eksportuojamus duomenis

  constructor(
    protected override commonFormServices: CommonFormServices,
    public faIconsService: FaIconsService,
    private adminService: InstitutionsAdminService,
    private authService: AuthService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.allowManageList = this.authService.isFormActionEnabled(
      NTIS_PRIORITY_WF_LIST,
      ActionsEnum.MANAGE_WF_PRIORITY_LIST
    );
  }

  ngOnInit(): void {
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('wto_name', new FormControl(''));
    this.searchForm.addControl('wto_selected', new FormControl(''));
    this.searchForm.addControl('wto_address', new FormControl(''));
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
    this.adminService
      .getWaterManagersList(pagingParams, params, extendedParams)
      .subscribe((result: BrowseFormSearchResult<WaterManagerBrowseRow>) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
        this.exportData = result.data;
        this.data.forEach((value: WaterManagerBrowseRow) => {
          if (value.facilities_info) {
            value.facilities_info = JSON.parse(value.facilities_info as string) as WaterManagerFacility[];
            this.facilitiesList = value.facilities_info;
            this.facilitiesList.forEach((org) => {
              this.checkboxState[org.wto_id] = false;
              if (org.wto_selected === DB_BOOLEAN_TRUE) {
                this.checkboxState[org.wto_id] = true;
                this.checkDataList.push({
                  wto_id: org.wto_id,
                  org_id: value.org_id,
                });
              }
            });
          }
        });
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getWaterManagersList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<WaterManagerBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  onCheckboxChange($event: Event, data: WaterManagerFacility, orgId: number): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;
    if (data.isSelected) {
      this.checkDataList.push({
        wto_id: data.wto_id,
        org_id: orgId,
      });
    } else {
      const index = this.checkDataList.findIndex((item) => item.wto_id === data.wto_id);
      this.checkDataList.splice(index, 1);
    }
  }

  onCancel(): void {
    void this.commonFormServices.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
  }

  protected doSave(): void {
    const priorityList: PriorityFacilitiesList[] = Object.entries(
      this.checkDataList.reduce((accumulator: { [key: number]: number[] }, item) => {
        if (accumulator[item.org_id]) {
          accumulator[item.org_id].push(item.wto_id);
        } else {
          accumulator[item.org_id] = [item.wto_id];
        }
        return accumulator;
      }, {})
    ).map(([org_id, wto_id]) => ({ org_id: Number(org_id), wto_id }));
    this.adminService.savePriorityFacilitiesList(priorityList).subscribe(() => {
      this.clearCheckboxArrays();
      this.isSelected = false;
      this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
        this.onCancel();
      });
    });
  }

  clearCheckboxArrays(): void {
    this.checkDataList = [];
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }
}
