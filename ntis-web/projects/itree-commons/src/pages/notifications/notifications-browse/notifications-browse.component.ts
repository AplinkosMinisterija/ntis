import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { SPR_NOTIFICATIONS } from '@itree-commons/src/constants/forms.constants';
import { NotificationTypes } from '@itree-commons/src/lib/enums/notification-types.enums';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { SprNotificationsBrowseRow, TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { Observable, tap } from 'rxjs';

@Component({
  selector: 'spr-notifications-browse',
  templateUrl: './notifications-browse.component.html',
  styleUrls: ['./notifications-browse.component.scss'],
})
export class NotificationsBrowseComponent extends BaseBrowseForm<SprNotificationsBrowseRow> implements OnInit {
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly formCode = SPR_NOTIFICATIONS;
  readonly notificationTypes = NotificationTypes;
  readonly translationsReference = 'pages.sprNotifications';

  cols: TableColumn[];
  exportData: SprNotificationsBrowseRow[] = [];
  rowActions: ActionsEnum[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  visibleColumns: TableColumn[];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private datePipe: S2DatePipe,
    private commonService: CommonService,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('ntf_id', new FormControl(''));
    this.searchForm.addControl('ntf_title', new FormControl(''));
    this.searchForm.addControl('ntf_message', new FormControl(''));
    this.searchForm.addControl('ntf_type', new FormControl(''));
    this.searchForm.addControl('not_read', new FormControl(''));
    this.searchForm.addControl('ntf_creation_date', new FormControl(''));

    this.cols = [
      { field: 'ntf_title', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'ntf_message', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 210 },
      { field: 'ntf_type', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'ntf_creation_date', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'ntf_id', export: false, visible: false, type: DATA_TYPE_STRING },
    ];
    this.visibleColumns = this.cols.filter((res) => res.visible);
  }

  loadTableData(
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprNotificationsBrowseRow>> {
    return this.commonService.getNotificationList(pagingParams, this.fixDate(params), extendedParams);
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.loadTableData(
        this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
        this.fixDate(this.getSearchParamMap(dataTable)),
        this.getExtendedSearchParams(dataTable)
      ).subscribe((response: BrowseFormSearchResult<SprNotificationsBrowseRow>) => {
        this.exportData = response.data;
      });
    } else {
      this.exportData = this.data;
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
    this.loadTableData(this.getPagingParams(first, pageSize, sortField, order, null), params, extendedParams)
      .pipe(
        tap((result: BrowseFormSearchResult<SprNotificationsBrowseRow>) => {
          this.rowActions = [];
          result.data.forEach((row: SprNotificationsBrowseRow) => {
            this.rowActions = this.getActions(row);
          });
        })
      )
      .subscribe((response: BrowseFormSearchResult<SprNotificationsBrowseRow>) => {
        this.data = response.data;
        this.exportData = response.data;
        this.totalRecords = response.paging.cnt;
      });
  }
  protected deleteRecord(recordId: string): void {
    this.commonService.deleteNotification(recordId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((txt: string) => {
        this.commonFormServices.appMessages.showSuccess('', txt);
      });
    });
  }

  getActions(application: SprNotificationsBrowseRow): ActionsEnum[] {
    return [...application.availableActions];
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params.set('p_ntf_creation_date', this.datePipe.transform(params.get('p_ntf_creation_date') as string));
  }

  deleteMessage(id: number): void {
    if (this.rowActions.includes(ActionsEnum.ACTIONS_DELETE)) {
      this.deleteRecordWithConfirmation(id.toString());
    }
  }

  markAsRead(row: SprNotificationsBrowseRow): void {
    if (row.not_read === DB_BOOLEAN_TRUE) {
      this.commonService.markAsRead(row.ntf_id).subscribe(() => {
        this.reload();
      });
    }
  }
}
