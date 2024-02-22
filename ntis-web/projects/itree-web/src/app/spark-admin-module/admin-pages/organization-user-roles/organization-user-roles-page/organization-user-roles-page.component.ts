import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_ROLE_ORGANIZATIONS_LIST } from '@itree-commons/src/constants/forms.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, OrgUserRoleRequest } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { OrgUserRolesDetails } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

export interface OrgUserRecordStatus {
  added: boolean;
  orgUserRec: OrgUserRoleRequest;
}
@Component({
  selector: 'app-organization-user-roles-page',
  templateUrl: './organization-user-roles-page.component.html',
  styleUrls: ['./organization-user-roles-page.component.scss'],
})
export class OrganizationUserRolesPageComponent extends BaseBrowseForm<OrgUserRolesDetails> implements OnInit {
  readonly formCode = SPR_ROLE_ORGANIZATIONS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;

  translation: string = 'pages.sprOrgUserRoles';
  selected_user: string;
  selected_user_id: number;
  selected_org: string;
  form: FormGroup;
  actionDialog: boolean;
  orgUsrRoles: OrgUserRolesDetails[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: OrgUserRolesDetails[] = [];
  userCanCreate: boolean;
  userCanDelete: boolean;
  userCanUpdate: boolean;

  rololes: OrgUserRoleRequest[] = [];
  rolesCahnges = new Map<number, OrgUserRecordStatus>();

  // Column array
  cols: TableColumn[] = [
    { field: 'rol_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_description', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'our_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'our_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private datePipe: S2DatePipe
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.userCanDelete = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_DELETE);
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
    this.form = new FormGroup({
      rol_id: new FormControl({ disabled: true }),
      ou_id: new FormControl({ disabled: true }),
      our_id: new FormControl({ disabled: true }),
      our_date_from: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10)])
      ),
      our_date_to: new FormControl('', Validators.compose([Validators.minLength(10), Validators.maxLength(10)])),
    });
  }

  ngOnInit(): void {
    this.selected_user_id = this.activatedRoute.snapshot.queryParams.id as number;
    this.selected_user = this.activatedRoute.snapshot.queryParams.user as string;
    this.selected_org = this.activatedRoute.snapshot.queryParams.org as string;
    this.searchForm.addControl('rol_id', new FormControl(''));
    this.searchForm.addControl('rol_code', new FormControl(''));
    this.searchForm.addControl('rol_name', new FormControl(''));
    this.searchForm.addControl('rol_description', new FormControl(''));
    this.searchForm.addControl('rol_type', new FormControl(''));
    this.searchForm.addControl('our_date_to', new FormControl(''));
    this.searchForm.addControl('our_date_from', new FormControl(''));
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
    if (this.activatedRoute) {
      this.getOrgUserRoles(this.activatedRoute.snapshot.params.id as number, pagingParams, params, extendedParams);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    /*This doesn't have this action */
  }

  getOrgUserRoles(
    id: number,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getNtisOrgUserRolesRecord(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.rol_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<OrgUserRolesDetails>) => {
        this.orgUsrRoles = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getNtisOrgUserRolesRecord(
          this.selected_user_id,
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<OrgUserRolesDetails>) => {
          this.exportData = response.data;
        });
    }
  }

  saveRoles(roles: OrgUserRoleRequest[]): void {
    this.adminService.setOrgUserRolesData(roles).subscribe((rolesResult) => {
      rolesResult.forEach((resRec) => {
        this.orgUsrRoles.forEach((dataRec) => {
          if (dataRec.rol_id === resRec.rol_id) {
            dataRec.our_id = resRec.our_id;
            dataRec.our_date_from = this.datePipe.transform(resRec.our_date_from);
            dataRec.our_date_to = this.datePipe.transform(resRec.our_date_to);
            this.actionDialog = false;
          }
        });
      });
      this.adminService.orgUserRolesAssignedSubject.next();
    });
  }

  saveRecWithConfirmation(): void {
    if (this.rolesCahnges.entries.length > 0) {
      // this.checkDataList = [];
      this.commonFormServices.translate
        .get('pages.sprUserRolesEdit.noDialogChangesMade')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showInfo('', translation);
        });
      return;
    }
    this.commonFormServices.confirmationService.confirm({
      message: this.commonFormServices.translate.instant('common.message.saveConfirmationMsg') as string,
      accept: () => {
        this.save();
      },
    });
  }

  save(): void {
    const createRoles: OrgUserRoleRequest[] = [];
    const deleteRoles: OrgUserRoleRequest[] = [];
    this.rolesCahnges.forEach((value: OrgUserRecordStatus, key: number) =>
      value.added ? createRoles.push(value.orgUserRec) : deleteRoles.push(value.orgUserRec)
    );
    this.saveRoles(createRoles);
    this.adminService.deltOrgUserRolesData(deleteRoles).subscribe();
  }

  onCheckboxChange($event: Event, data: OrgUserRolesDetails): void {
    const eventTarget = $event.target as HTMLInputElement;
    // added: boolean;
    // orgUserRec: OrgUserRoleRequest;
    const rec: OrgUserRecordStatus = {
      added: eventTarget.checked,
      orgUserRec: {
        user_id: data.user_id,
        rol_id: eventTarget.value as unknown as number,
        our_id: data.our_id,
        our_date_from: data.our_date_from ? new Date(data.our_date_from) : null,
        our_date_to: data.our_date_to ? new Date(data.our_date_to) : null,
        ou_id: data.ou_id,
        selected: undefined,
        update: undefined,
      } as OrgUserRoleRequest,
    };

    this.rolesCahnges.set(eventTarget.value as unknown as number, rec);
    //  const rec: OrgUserRoleRequest = {
    //   user_id: data.user_id,
    //   rol_id: eventTarget.value as unknown as number,
    //   our_id: data.our_id,
    //   our_date_from: data.our_date_from ? new Date(data.our_date_from) : null,
    //   our_date_to: data.our_date_to ? new Date(data.our_date_to) : null,
    //   ou_id: data.ou_id,
    //   selected: undefined,
    //   update: undefined,
    // };

    // if (eventTarget.checked && !data.our_id) {
    //   // removeRoles: OrgUserRoleRequest[] = [];
    //   this.rolesCahnges.set(eventTarget.value as unknown as number, {true, rec} as  OrgUserRecordStatus);
    //   //this.saveRoles(roles);
    // } else if (!eventTarget.checked && data.our_id) {
    //   this.rolesCahnges.set(eventTarget.value as unknown as number, false,rec);

    //   this.adminService.deltOrgUserRolesData(roles).subscribe(() => {
    //     data.our_id = null;
    //     data.our_date_from = null;
    //     data.our_date_to = null;
    //   });
    // }
  }

  saveDate(): void {
    const roles: OrgUserRoleRequest[] = [];
    roles.push({
      user_id: this.selected_user_id,
      rol_id: this.form.controls.rol_id.value as number,
      our_id: this.form.controls.our_id.value as number,
      our_date_from: this.form.controls.our_date_from.value as Date,
      our_date_to: this.form.controls.our_date_to.value as Date,
      ou_id: this.form.controls.ou_id.value as number,
      selected: undefined,
      update: undefined,
    });
    this.saveRoles(roles);
  }

  editUserRoleDates(row: OrgUserRolesDetails): void {
    this.form.reset();
    this.actionDialog = true;
    this.form.controls.ou_id.setValue(row.ou_id);
    this.form.controls.our_id.setValue(row.our_id);
    this.form.controls.rol_id.setValue(row.rol_id);
    this.form.controls.our_date_from.setValue(row?.our_date_from ? new Date(row.our_date_from) : null);
    this.form.controls.our_date_to.setValue(row?.our_date_to ? new Date(row.our_date_to) : null);
  }

  onCancel(): void {
    if (!this.form.touched) {
      this.actionDialog = false;
    } else {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.actionDialog = false;
        },
      });
    }
  }

  getActions(row: OrgUserRolesDetails): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions
      .filter((action) => action == ActionsEnum.ACTIONS_UPDATE)
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          icon: getActionIcons(action as ActionsEnum),
          command: (): void => {
            this.editUserRoleDates(row);
          },
        });
      });
    return menu;
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_USERS_BROWSE],
      navigationExtras
    );
  }
}
