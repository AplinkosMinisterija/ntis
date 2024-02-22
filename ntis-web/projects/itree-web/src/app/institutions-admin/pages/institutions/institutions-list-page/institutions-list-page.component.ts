import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NTIS_INSTITUTIONS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { map } from 'rxjs';
import { NtisInstitutionsBrowseRow, TableColumn } from '../../../models/browse-pages';
import { InstitutionsAdminService } from '../../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { DialogModule } from 'primeng/dialog';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { SprListIdKeyValue } from '@itree-commons/src/lib/model/api/api';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { NtisOrgType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { TooltipModule } from 'primeng/tooltip';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-institutions-list-page',
  templateUrl: './institutions-list-page.component.html',
  styleUrls: ['./institutions-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    DialogModule,
    FormsModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    RouterModule,
    TableModule,
    TooltipModule,
  ],
})
export class InstitutionsListPageComponent extends BaseBrowseForm<NtisInstitutionsBrowseRow> implements OnInit {
  readonly formTranslationsReference = 'institutionsAdmin.pages.institutionsList';
  readonly formCode = NTIS_INSTITUTIONS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly RoutingConst = RoutingConst;
  exportData: NtisInstitutionsBrowseRow[] = [];
  sendInvitationDialog: boolean = false;
  removeAdminDialog: boolean = false;
  institution: string;
  userRole: string;
  user: string;
  userEmail: string;
  userId: string;
  orgId: string;
  orgTypeSelection: SprListIdKeyValue[] = [];
  userCanCreate: boolean;

  cols: TableColumn[] = [
    { field: 'org_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'municipality', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService,
    private authService: AuthService,
    private clsfService: CommonService,
    private router: Router
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('org_code', new FormControl(''));
    this.searchForm.addControl('org_type', new FormControl(''));
    this.searchForm.addControl('municipality', new FormControl(''));

    this.commonFormServices.translate.get('common.generalUse.admin').subscribe((translation: string) => {
      this.userRole = translation;
    });
    this.clsfService.getClsf('NTIS_ORG_TYPE').subscribe((options) => {
      this.orgTypeSelection = options.filter((type) => {
        return type.key === NtisOrgType.INST || type.key === NtisOrgType.INST_LT;
      });
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
    this.adminService
      .getInstitutionsList(pagingParams, params, extendedParams)
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
        .getInstitutionsList(
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

  getNtisActions(row: NtisInstitutionsBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions
      .filter((action) => action !== ActionsEnum.ACTIONS_CREATE && action !== ActionsEnum.ACTIONS_READ)
      .forEach((action) => {
        if (action === ActionsEnum.ACTIONS_UPDATE) {
          const action: NtisTableRowActionsItem = {
            icon: { iconStyle: 'solid', iconName: 'faPencil' },
            iconTheme: 'default',
            actionName: 'update',
            action: () => {
              void this.router.navigate([this.router.url, RoutingConst.EDIT, row.org_id.toString()]);
            },
          };
          rowActions.push(action);
        } else if (action === ActionsEnum.ACTION_INVITE_PERSON && row.per_email) {
          const action: NtisTableRowActionsItem = {
            icon: { iconStyle: 'solid', iconName: 'faEnvelopeOpen' },
            iconTheme: 'default',
            actionName: 'sendInvitation',
            action: () => {
              this.sendInvitationDialog = true;
              this.setValues(row.org_id?.toString());
            },
          };
          rowActions.push(action);
        } else if (action === ActionsEnum.REMOVE_ADMIN && row.per_email && row.usr_id) {
          const action: NtisTableRowActionsItem = {
            icon: { iconStyle: 'solid', iconName: 'faUserXmark' },
            iconTheme: 'default',
            actionName: 'removeAdmin',
            action: () => {
              this.removeAdminDialog = true;
              this.setValues(row.org_id?.toString());
            },
          };
          rowActions.push(action);
        }
      });
    return rowActions;
  }

  cancel(): void {
    this.sendInvitationDialog = false;
    this.removeAdminDialog = false;
  }

  sendEmailRequest(): void {
    this.adminService.inviteNewPerson(this.userId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.requestSent')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
    });
    this.sendInvitationDialog = false;
  }

  setValues(id: string): void {
    this.adminService.getInstitutionAdmin(id).subscribe((institution) => {
      this.institution = institution.org_name;
      this.user = institution.per_name + ' ' + institution.per_surname;
      this.userEmail = institution.per_email;
      this.userId = institution.usr_id.toString();
      this.orgId = institution.org_id.toString();
    });
  }

  removeAdmin(): void {
    this.adminService.removeInstAdmin(this.userId, this.orgId).subscribe(() => {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.adminRemoved')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.reload();
          this.removeAdminDialog = false;
        });
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.sendInvitationDialog = false;
    this.removeAdminDialog = false;
  }
}
