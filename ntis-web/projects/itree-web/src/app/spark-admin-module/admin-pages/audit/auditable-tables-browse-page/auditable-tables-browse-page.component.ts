import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_AUDITABLE_TABLES } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { InputSwitchOnChangeEvent } from 'primeng/inputswitch';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprAuditableTablesBrowseRow } from '../../../models/browse-pages';
import { Table } from 'primeng/table/table';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-auditable-tables-browse-page',
  templateUrl: './auditable-tables-browse-page.component.html',
  styleUrls: ['./auditable-tables-browse-page.component.scss'],
})
export class AuditableTablesBrowsePageComponent extends BaseBrowseForm<SprAuditableTablesBrowseRow> implements OnInit {
  readonly formCode = SPR_AUDITABLE_TABLES;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  editFormLink = RoutingConst.EDIT;
  pageTranslations = 'pages.sprAuditableTables';
  rowMenuItems: Record<string, MenuItem[]> = {};
  rowToggleAvailable: Record<string, boolean> = {};
  exportData: SprAuditableTablesBrowseRow[] = [];

  cols: TableColumn[] = [
    { field: 'aut_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'aut_table_schema', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'aut_table_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'aut_trigger_enabled', export: true, visible: true, type: DATA_TYPE_STRING },
  ];

  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(protected override commonFormServices: CommonFormServices, private adminService: AdminService) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('aut_table_name', new FormControl(''));
    this.searchForm.addControl('aut_table_schema', new FormControl(''));
    this.reload();
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
      .getAuditableTablesList(pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          this.rowToggleAvailable = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.aut_id] = this.getActions(row);
            this.rowToggleAvailable[row.aut_id] = row.availableActions.includes(ActionsEnum.ACTIONS_CHANGE_TABLE_AUDIT);
          });
        })
      )
      .subscribe((result) => {
        this.data = result.data;
        this.exportData = result.data;
        this.totalRecords = result.paging.cnt;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getAuditableTablesList(
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

  protected deleteRecord(recId: string): void {
    this.adminService.delOrganizationRecord(recId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }

  navigateToEdit(recId: string): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, this.editFormLink, recId]);
  }

  navigateToAudRecords(recId: string): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      RoutingConst.SPARK_AUDITABLE_TABLE_REC_BROWSE,
      recId,
    ]);
  }

  getActions(row: SprAuditableTablesBrowseRow): MenuItem[] {
    return row.availableActions
      .filter((action) => ActionsEnum.ACTIONS_READ === action)
      .map((action) => {
        const menuItem: MenuItem = {
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          id: row.aut_id,
          icon: getActionIcons(action),
        };
        switch (action) {
          case ActionsEnum.ACTIONS_READ:
            menuItem.command = (): void => {
              this.navigateToAudRecords(row.aut_id);
            };
            break;
        }
        return menuItem;
      });
  }

  onInputSwitch(event: InputSwitchOnChangeEvent, autId: string): void {
    this.adminService.setAuditStatus(event.checked as unknown as string, parseInt(autId)).subscribe();
  }
}
