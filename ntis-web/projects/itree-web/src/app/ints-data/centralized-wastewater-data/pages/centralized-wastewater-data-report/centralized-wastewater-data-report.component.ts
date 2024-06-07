import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DATA_TYPE_DATE, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { IdKeyValuePair } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_CW_DATA_REPORT } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { NtisRoutingConst } from 'projects/itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CwFilStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { NtisSharedModule } from 'projects/itree-web/src/app/ntis-shared/ntis-shared.module';
import { map } from 'rxjs';
import { CentralizedWastewaterDataReportBrowseRow } from '../../models/browse-pages';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';

interface BrowseSearchData {
  url: string;
  paramsObj: Record<string, unknown>;
  extendedParams: ExtendedSearchParam[];
  searchFormValues: Record<string, unknown>;
  first: number;
  pageSize: number;
  sortField: string;
  order: number;
}

@Component({
  selector: 'app-centralized-wastewater-data-report',
  standalone: true,
  templateUrl: './centralized-wastewater-data-report.component.html',
  styleUrls: ['./centralized-wastewater-data-report.component.scss'],
  imports: [
    CommonModule,
    DialogModule,
    FontAwesomeModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    TableModule,
    TooltipModule,
  ],
})
export class CentralizedWastewaterDataReportComponent
  extends BaseBrowseForm<CentralizedWastewaterDataReportBrowseRow>
  implements OnInit
{
  readonly translationsReference = 'intsData.centralizedData.pages.centralizedWastewaterDataReport';
  readonly formCode = NTIS_CW_DATA_REPORT;
  readonly CwFilStatus = CwFilStatus;
  readonly NTIS_CW_FIL_STATUS = 'NTIS_CW_FIL_STATUS';
  readonly notSubmitted = 'NOT_SUBMITTED';
  readonly pendingErrs = 'CW_FIL_PENDING_ERR';
  orgInfoDialog: boolean = false;
  org_name: string;
  org_address: string;
  org_delegation_person: string;
  org_email: string;
  org_phone: string;
  org_website: string;
  waterManagers: IdKeyValuePair[] = [];
  cwfStateSelection: IdKeyValuePair[] = [];
  sortClause: string;
  sortOrder: number;
  all: string;
  cols: TableColumn[] = [
    { field: 'org_name', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'date_in_period', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'latest_date', export: false, visible: true, type: DATA_TYPE_DATE },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    protected commonService: CommonService,
    private centralizedService: CentralizedWastewaterDataService,
    private router: Router,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;

    this.commonService.getClsf(this.NTIS_CW_FIL_STATUS).subscribe((result) => {
      this.cwfStateSelection = result
        .map((item) => ({
          id: item.key,
          value: item.display,
        }))
        .filter((value) => value.id !== this.pendingErrs);

      this.commonFormServices.translate
        .get(this.translationsReference + '.notSubmitted')
        .subscribe((translation: string) => {
          this.cwfStateSelection.push({
            id: this.notSubmitted,
            value: translation,
          });
        });
    });
    this.centralizedService.getWaterManagers().subscribe((result) => {
      result.data.map((wm) => {
        this.waterManagers.push({
          value: wm.value,
          id: wm.id,
        });
      });
    });
  }

  ngOnInit(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        this.sortClause = searchData.sortField ?? null;
        this.sortOrder = searchData.order ?? null;
        this.loadSearchDataIntoSearchForm();
      }
    }
    this.searchForm.addControl('org_id', new FormControl(''));
    this.searchForm.addControl('cwf_status', new FormControl(''));
    this.searchForm.addControl('cwf_period_from', new FormControl(''));
    this.searchForm.addControl('cwf_period_to', new FormControl(''));
    this.searchForm.addControl('latest_date', new FormControl(''));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(
      first,
      pageSize,
      this.sortClause ? this.sortClause : sortField,
      this.sortOrder ? this.sortOrder : order,
      null
    );
    this.centralizedService
      .getWastewaterDataReport(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
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

  getNtisActions(row: CentralizedWastewaterDataReportBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const viewInfo: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faInfo' },
          actionName: 'viewOrgInfo',
          iconTheme: 'default',
          action: () => {
            this.openInfoDialog(row);
          },
        };
        rowActions.push(viewInfo);
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faFileCsv' },
          actionName: 'view',
          iconTheme: 'default',
          action: () => {
            void this.router.navigate([this.router.url, NtisRoutingConst.WASTEWATER_DATA_LIST, row.org_id]);
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  openInfoDialog(row: CentralizedWastewaterDataReportBrowseRow): void {
    this.org_name = row.org_name;
    this.org_address = row.org_address;
    this.org_delegation_person = row.org_delegation_person;
    this.org_email = row.org_email;
    this.org_phone = row.org_phone;
    this.org_website = row.org_website;
    this.orgInfoDialog = true;
  }

  navigateToAddressMappings(): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      NtisRoutingConst.CW_ADDRESS_MAPPINGS,
    ]);
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.orgInfoDialog = false;
  }

  protected override saveSearchDataToSession(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const browseSearchData: BrowseSearchData = {
      url: this.commonFormServices.router.url,
      paramsObj: Object.fromEntries(params),
      extendedParams,
      searchFormValues: Object.keys(this.searchForm.controls)
        .filter((controlName) => this.searchForm.controls[controlName]?.value)
        .reduce(
          (valuesObj, controlName) => ({
            ...valuesObj,
            [controlName]: this.searchForm.controls[controlName].value as unknown,
          }),
          {} as Record<string, unknown>
        ),
      first,
      pageSize,
      sortField,
      order,
    };
    sessionStorage.setItem(this.formCode, JSON.stringify(browseSearchData));
  }

  protected override getSearchDataFromSession(): BrowseSearchData | null {
    const searchData = JSON.parse(sessionStorage.getItem(this.formCode)) as BrowseSearchData;
    if (searchData) {
      if (searchData?.url === this.commonFormServices.router.url) {
        return searchData;
      }
    }
    return null;
  }

  protected override removeSearchDataFromSession(): void {
    sessionStorage.removeItem(this.formCode);
  }
}
