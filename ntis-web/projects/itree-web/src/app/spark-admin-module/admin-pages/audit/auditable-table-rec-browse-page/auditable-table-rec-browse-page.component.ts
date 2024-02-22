import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { SPR_AUDITABLE_TABLES } from '@itree-commons/src/constants/forms.constants';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { Table } from 'primeng/table';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-auditable-table-rec-browse-page',
  templateUrl: './auditable-table-rec-browse-page.component.html',
  styleUrls: ['./auditable-table-rec-browse-page.component.scss'],
})
export class AuditableTableRecBrowsePageComponent extends BaseBrowseForm<Record<string, string>> implements OnInit {
  readonly formCode = SPR_AUDITABLE_TABLES;
  aut_id: string;
  pageTranslations = 'pages.sprAuditableTableRec';
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: Record<string, string>[] = [];
  cols: TableColumn[] = [];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private activatedRoute: ActivatedRoute,
    private adminService: AdminService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('rec_aud_timestamp', new FormControl(''));
    this.searchForm.addControl('rec_aud_user_id', new FormControl(''));
    if (this.activatedRoute) {
      this.activatedRoute.params.subscribe((par) => {
        this.aut_id = par.id as string;
      });
    }
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
      .getAuditableTableRecList(this.aut_id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          if (result.data.length > 0) {
            this.visibleColumns = Object.keys(result.data[0])
              .filter((colNames: string) => colNames !== 'availableActions' && colNames !== 'skip')
              .map((res) => {
                return { field: res, export: true, visible: true, type: DATA_TYPE_STRING, textLength: 40 };
              });
            this.cols = this.visibleColumns;
          }
        })
      )
      .subscribe((result) => {
        this.data = result.data;
        this.exportData = this.data;
        this.totalRecords = result.paging.cnt;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getAuditableTableRecList(
          this.aut_id,
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}
}
