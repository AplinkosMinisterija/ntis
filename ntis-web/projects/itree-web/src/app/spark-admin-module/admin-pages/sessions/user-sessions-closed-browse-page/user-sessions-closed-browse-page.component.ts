import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AdminService } from '../../../admin-services/admin.service';
import { Table } from 'primeng/table';
import { SPR_USR_SES_CLSD_LST } from '@itree-commons/src/constants/forms.constants';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SprClosedSessionsBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-user-sessions-closed-browse-page',
  templateUrl: './user-sessions-closed-browse-page.component.html',
  styleUrls: ['./user-sessions-closed-browse-page.component.scss'],
})
export class UserSessionsClosedBrowsePageComponent
  extends BaseBrowseForm<SprClosedSessionsBrowseRow>
  implements OnInit
{
  readonly formCode = SPR_USR_SES_CLSD_LST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  seachParameter: string;
  type: string;
  isFilterShown = true;
  cols: TableColumn[];
  visibleColumns: TableColumn[];
  maximumDate = new Date();
  rows = false;
  exportData: SprClosedSessionsBrowseRow[] = [];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private datePipe: S2DatePipe
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('user_name', new FormControl(''));
    this.searchForm.addControl('ses_id', new FormControl(''));
    this.searchForm.addControl('person_name', new FormControl(''));
    this.searchForm.addControl('person_surname', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('login_time', new FormControl(''));
    this.searchForm.addControl('logout_time', new FormControl(''));
    this.searchForm.addControl('logout_cause', new FormControl(''));
    this.cols = [
      { field: 'sec_id', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'sec_username', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_person_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'usr_person_surname', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'sec_login_time', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'sec_logout_time', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'sec_last_action_time', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'sec_logout_cause', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    ];
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
    this.adminService.getSessionClosedList(pagingParams, this.fixDate(params), extendedParams).subscribe((result) => {
      this.data = result.data;
      this.totalRecords = result.paging.cnt;
      this.exportData = result.data;
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getSessionClosedList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.fixDate(this.getSearchParamMap(dataTable)),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params.set('p_login_time', this.datePipe.transform(params.get('p_login_time') as string));
  }
}
