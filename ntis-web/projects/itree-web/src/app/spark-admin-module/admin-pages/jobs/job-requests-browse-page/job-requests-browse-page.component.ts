import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { SPR_JOB_REQUESTS } from '@itree-commons/src/constants/forms.constants';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table } from 'primeng/table';
import { AdminService } from '../../../admin-services/admin.service';
import { SprJobRequestsBrowseRow, SprJobRequestsJobItem } from '../../../models/browse-pages';

@Component({
  selector: 'app-job-requests-browse-page',
  templateUrl: './job-requests-browse-page.component.html',
  styleUrls: ['./job-requests-browse-page.component.scss'],
})
export class JobRequestsBrowsePageComponent extends BaseBrowseForm<SprJobRequestsBrowseRow> implements OnInit {
  readonly formCode = SPR_JOB_REQUESTS;
  readonly translationsReference = 'pages.sprJobRequestsBrowse';
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;

  exportData: SprJobRequestsBrowseRow[] = [];
  jobNameOptions: SprJobRequestsJobItem[] = [];
  isArchiveSearch: boolean = false;
  jobId: string;
  cols: TableColumn[] = [
    { field: 'jrq_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jde_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jrq_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'jrq_start_date', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'jrq_end_date', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'jrq_executer_name', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((col) => col.visible);

  executionCols: TableColumn[] = [
    { field: 'jre_time', export: true, visible: true },
    { field: 'jre_name', export: true, visible: true },
    { field: 'jre_event_info', export: true, visible: true },
    { field: 'jre_err', export: true, visible: true },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('jrq_id', new FormControl(null));
    this.searchForm.addControl('jde_name', new FormControl(null));
    this.searchForm.addControl('jrq_code', new FormControl(null));
    this.searchForm.addControl('jrq_start_date', new FormControl(null));
    this.searchForm.addControl('jrq_end_date', new FormControl(null));
    this.searchForm.addControl('jrq_executer_name', new FormControl(null));
    this.jobId = this.activatedRoute.snapshot.params.id as string;
    this.adminService.getJobRequestsNames().subscribe((result) => {
      this.jobNameOptions = result.data;
    });
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
    if (this.jobId) {
      extendedParams.push({
        paramName: 'jrq_jde_id',
        paramValue: {
          condition: ExtendedSearchCondition.Equals,
          value: this.jobId,
          upperLower: ExtendedSearchUpperLower.Regular,
        },
      });
    }
    params.set('isArchiveSearch', this.isArchiveSearch ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE);
    this.adminService.getJobRequestsList(pagingParams, params, extendedParams).subscribe((result) => {
      this.data = result.data;
      this.totalRecords = result.paging.cnt;
      this.exportData = result.data;
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      const params = this.getSearchParamMap(dataTable);
      const extendedSearchParams = this.getExtendedSearchParams(dataTable);

      if (this.jobId) {
        extendedSearchParams.push({
          paramName: 'jrq_jde_id',
          paramValue: {
            condition: ExtendedSearchCondition.Equals,
            value: this.jobId,
            upperLower: ExtendedSearchUpperLower.Regular,
          },
        });
      }

      params.set('isArchiveSearch', this.isArchiveSearch ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE);
      this.adminService
        .getJobRequestsList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          params,
          extendedSearchParams
        )
        .subscribe((response: BrowseFormSearchResult<SprJobRequestsBrowseRow>) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }
}
