import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { ConfirmationService } from 'primeng/api';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AdminService } from '../../admin-services/admin.service';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { SPR_USR_SES_OPN_LST } from '@itree-commons/src/constants/forms.constants';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SprOpenSessionsBrowseRow } from '../../models/browse-pages';

@Component({
  selector: 'app-user-session-open-browse-page',
  templateUrl: './user-session-open-browse-page.component.html',
  styleUrls: ['./user-session-open-browse-page.component.scss'],
})
export class UserSessionOpenBrowsePageComponent extends BaseBrowseForm<SprOpenSessionsBrowseRow> implements OnInit {
  readonly formCode = SPR_USR_SES_OPN_LST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  selectedRows: SprOpenSessionsBrowseRow[];
  maximumDate = new Date();
  rows = false;
  cols: TableColumn[] = [
    { field: 'ses_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ses_username', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_person_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_person_surname', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ses_login_time', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'ses_last_action_time', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((col) => col.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private confirmationService: ConfirmationService,
    private datePipe: S2DatePipe
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
  }

  ngOnInit(): void {
    this.searchForm.addControl('ses_username', new FormControl(''));
    this.searchForm.addControl('ses_id', new FormControl(''));
    this.searchForm.addControl('person_name', new FormControl(''));
    this.searchForm.addControl('person_surname', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('login_time', new FormControl(''));
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params.set('p_login_time', this.datePipe.transform(params.get('p_login_time') as string));
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
      .getSessionsOpen(pagingParams, this.fixDate(params), extendedParams)
      .subscribe((tableData: BrowseFormSearchResult<SprOpenSessionsBrowseRow>) => {
        this.data = tableData.data;
        this.totalRecords = tableData.paging.cnt;
      });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  killSessions(): void {
    if (this.selectedRows != null && this.selectedRows.length > 0) {
      const ses_keys = this.selectedRows.map((row) => row.ses_key);
      this.commonFormServices.translate
        .get('pages.sprOpenSessionsBrowse.dialogKillConfirm')
        .subscribe((translation: string) => {
          this.confirmationService.confirm({
            message: translation,
            accept: () => {
              this.adminService.killSessions(ses_keys).subscribe(() => {
                this.reload();
                this.commonFormServices.translate
                  .get('pages.sprOpenSessionsBrowse.messageSuccessKill')
                  .subscribe((translation: string) => {
                    this.commonFormServices.appMessages.showSuccess('', translation);
                  });
              });
            },
          });
        });
    } else {
      this.commonFormServices.translate
        .get('pages.sprOpenSessionsBrowse.messageWarnNoSesSelect')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showWarning('', translation);
        });
    }
  }

  killAllSessions(): void {
    this.commonFormServices.translate
      .get('pages.sprOpenSessionsBrowse.dialogKillAllConfirm')
      .subscribe((translation: string) => {
        this.confirmationService.confirm({
          message: translation,
          accept: () => {
            this.adminService.killAllSessions().subscribe(() => {
              this.reload();
              this.commonFormServices.translate
                .get('pages.sprOpenSessionsBrowse.messageSuccessKill')
                .subscribe((translation: string) => {
                  this.commonFormServices.appMessages.showSuccess('', translation);
                });
            });
          },
        });
      });
  }
}
