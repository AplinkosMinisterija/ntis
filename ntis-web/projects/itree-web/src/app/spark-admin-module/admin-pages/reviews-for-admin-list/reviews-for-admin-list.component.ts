import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { AdminReviewsBrowseRow } from '../../models/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { NTIS_REVIEWS_FOR_ADMIN_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { AdminService } from '../../admin-services/admin.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { map } from 'rxjs';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { Table, TableModule } from 'primeng/table';
import { SprListIdKeyValue } from '@itree-commons/src/lib/model/api/api';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  DATA_TYPE_BOOLEAN,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';

@Component({
  selector: 'app-reviews-for-admin-list',
  templateUrl: './reviews-for-admin-list.component.html',
  styleUrls: ['./reviews-for-admin-list.component.scss'],
  standalone: true,
  imports: [
    ItreeCommonsModule,
    CommonModule,
    TableModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    TooltipModule,
    DialogModule,
    NtisSharedModule,
  ],
})
export class ReviewsForAdminListComponent extends BaseBrowseForm<AdminReviewsBrowseRow> implements OnInit {
  readonly translationsReference = 'pages.reviews';
  readonly reviewsTranslationsReference = 'ntisShared.pages.reviewCreate';
  readonly formCode = NTIS_REVIEWS_FOR_ADMIN_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  exportData: AdminReviewsBrowseRow[] = [];
  orgTypeSelection: SprListIdKeyValue[] = [];
  readMessagesSelection: SprListIdKeyValue[] = [];
  readonly institution = 'INST';
  readonly institutionLt = 'INST_LT';
  showReviewDialog = false;
  revScore: number;
  revComment: string;

  cols: TableColumn[] = [
    { field: 'rev_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'rev_admin_read', export: false, visible: false, type: DATA_TYPE_BOOLEAN },
    { field: 'rev_score', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'rev_comment', export: true, visible: true, type: DATA_TYPE_STRING, textLength: 80 },
    { field: 'rev_completed_date', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_type', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private clsfService: CommonService,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.clsfService.getClsf('NTIS_ORG_TYPE').subscribe((options) => {
      this.orgTypeSelection = options.filter((type) => {
        return type.key !== this.institution && type.key !== this.institutionLt;
      });
    });

    this.commonFormServices.translate.get(this.translationsReference + '.read').subscribe((translation: string) => {
      this.readMessagesSelection.push({ display: translation, key: DB_BOOLEAN_TRUE, id: null });
    });
    this.commonFormServices.translate.get(this.translationsReference + '.notRead').subscribe((translation: string) => {
      this.readMessagesSelection.push({ display: translation, key: DB_BOOLEAN_FALSE, id: null });
    });

    this.searchForm.addControl('rev_score', new FormControl(''));
    this.searchForm.addControl('rev_comment', new FormControl(''));
    this.searchForm.addControl('rev_completed_date', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('org_type', new FormControl(''));
    this.searchForm.addControl('rev_admin_read', new FormControl(''));
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
      .getAdminReviewsList(pagingParams, params, extendedParams)
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
        this.exportData = result.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getAdminReviewsList(
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

  markReviewAsRead(id: number): void {
    this.adminService.markReviewAsRead(id?.toString()).subscribe(() => {
      this.data.forEach((review) => {
        if (review.rev_id === id) {
          review.rev_admin_read = DB_BOOLEAN_TRUE;
        }
      });
    });
  }

  getNtisActions(row: AdminReviewsBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: () => {
            this.openDialog(row);
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  openDialog(row: AdminReviewsBrowseRow): void {
    this.revComment = row.rev_comment;
    this.revScore = row.rev_score;
    this.showReviewDialog = true;
    if (row.rev_admin_read === DB_BOOLEAN_FALSE) {
      this.markReviewAsRead(row.rev_id);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  protected override deleteRecord(recordId: string): void {}
}
