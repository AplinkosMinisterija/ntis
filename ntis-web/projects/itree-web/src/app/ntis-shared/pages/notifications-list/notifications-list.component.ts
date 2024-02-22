import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { optionList } from '@itree-commons/src/lib/components/select/select.component';
import { FilterInputType } from '@itree-commons/src/lib/enums/input-type.enums';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { tap } from 'rxjs';
import { NTIS_NOTIFICATIONS_LIST } from '../../constants/forms.const';
import { NtisNotificationsBrowseRow } from '../../models/browse-page';
import { NtisTableRowActionsItem } from '../../models/table-row-actions';
import { NtisSharedModule } from '../../ntis-shared.module';
import { NtisCommonService } from '../../services/ntis-common.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

enum MarkAction {
  read = 'read',
  important = 'important',
  not_important = 'not_important',
  delete = 'delete',
}

@Component({
  selector: 'ntis-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrls: ['./notifications-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FontAwesomeModule,
    FormsModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
  ],
})
export class NotificationsListComponent extends BaseBrowseForm<NtisNotificationsBrowseRow> implements OnInit {
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'ntisShared.pages.notificationsList';
  readonly formCode = NTIS_NOTIFICATIONS_LIST;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  readonly MarkAction = MarkAction;
  isImportantOptions: optionList[] = [];
  isReadOptions: optionList[] = [];
  exportData: NtisNotificationsBrowseRow[] = [];
  cols: TableColumn[] = [
    { field: 'is_important', export: false, visible: false, filterable: true },
    { field: 'ntf_creation_date', export: true, visible: true, filterable: true, filterType: FilterInputType.date },
    {
      field: 'ntf_title',
      export: true,
      visible: true,
      filterable: true,
      filterType: FilterInputType.text,
    },
    {
      field: 'ntf_message',
      export: true,
      visible: true,
      filterable: true,
      filterType: FilterInputType.text,
      textLength: 60,
    },
  ];
  visibleCols = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private datePipe: S2DatePipe,
    private commonService: NtisCommonService,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('ntf_title', new FormControl(''));
    this.searchForm.addControl('ntf_message', new FormControl(''));
    this.searchForm.addControl('not_read', new FormControl(''));
    this.searchForm.addControl('is_important', new FormControl(''));
    this.searchForm.addControl('ntf_creation_date', new FormControl(''));
    this.getFiltersOptions();
  }

  getFiltersOptions(): void {
    this.isImportantOptions = [
      { label: this.getLabelTranslation('.important'), value: DB_BOOLEAN_TRUE },
      { label: this.getLabelTranslation('.not_important'), value: DB_BOOLEAN_FALSE },
    ];
    this.isReadOptions = [
      { label: this.getLabelTranslation('.read'), value: DB_BOOLEAN_FALSE },
      { label: this.getLabelTranslation('.not_read'), value: DB_BOOLEAN_TRUE },
    ];
  }

  getLabelTranslation(label: string): string {
    let translatedLabel: string = '';
    this.commonFormServices.translate.get(this.translationsReference + label).subscribe((res: string) => {
      translatedLabel = res;
    });
    return translatedLabel;
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.commonService
        .getNotificationList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<NtisNotificationsBrowseRow>) => {
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
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.commonService
      .getNotificationList(pagingParams, params, extendedParams)
      .pipe(
        tap((result: BrowseFormSearchResult<NtisNotificationsBrowseRow>) => {
          result.data.forEach((row: NtisNotificationsBrowseRow) => {
            row.ntf_creation_date = this.datePipe.transform(row.ntf_creation_date, true);
            row.actions = this.getActions(row);
          });
        })
      )
      .subscribe((response: BrowseFormSearchResult<NtisNotificationsBrowseRow>) => {
        this.data = response.data;
        this.exportData = response.data;
        this.totalRecords = response.paging.cnt;
      });
  }

  protected deleteRecord(recordId: string): void {
    this.commonService.markNotification(Number(recordId), MarkAction.delete).subscribe(() => {
      this.reload();
    });
  }

  getActions(row: NtisNotificationsBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: (): void => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              RoutingConst.VIEW,
              row.ntf_id,
            ]);
            this.markNotification(row, MarkAction.read);
          },
        });
      }
      if (action === ActionsEnum.ACTIONS_DELETE) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faTrash' },
          actionName: 'delete',
          iconTheme: 'default',
          action: (): void => {
            this.deleteRecordWithConfirmation(row.ntf_id.toString());
          },
        });
      }
    });
    return itemActions;
  }

  markNotification(row: NtisNotificationsBrowseRow, action: string): void {
    if (!(action === MarkAction.read && row.not_read === DB_BOOLEAN_FALSE)) {
      this.commonService.markNotification(row.ntf_id, action).subscribe(() => {
        this.reload();
      });
    }
  }
}
