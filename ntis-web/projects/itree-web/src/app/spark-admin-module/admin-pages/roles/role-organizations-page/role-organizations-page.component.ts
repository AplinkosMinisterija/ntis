import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_ROLE_ORGANIZATIONS_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { OrganizationRoleRequest } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { Subject, takeUntil, tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprRoleOrganizationsBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-role-organizations-page',
  templateUrl: './role-organizations-page.component.html',
  styleUrls: ['./role-organizations-page.component.scss'],
})
export class RoleOrganizationsPageComponent extends BaseBrowseForm<SprRoleOrganizationsBrowseRow> implements OnInit {
  readonly formCode = SPR_ROLE_ORGANIZATIONS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'pages.sprRoleOrganizations';
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  destroy$: Subject<boolean> = new Subject<boolean>();
  form: FormGroup;
  rowMenuItems: Record<string, MenuItem[]> = {};
  actionDialog: boolean = false;
  checked: boolean = false;
  assignedOrgsList: SprRoleOrganizationsBrowseRow[] = [];
  checkedOrgsArray: SprRoleOrganizationsBrowseRow[] = [];
  uncheckedOrgsArray: SprRoleOrganizationsBrowseRow[] = [];
  roleOrganizations: SprRoleOrganizationsBrowseRow[] = [];
  exportData: SprRoleOrganizationsBrowseRow[] = [];
  oar_id: string;
  selected_role: string;
  selected_role_id: string;
  minDate: Date = new Date();
  tabMenu: MenuItem[];
  activeItem: MenuItem;

  cols: TableColumn[] = [
    { field: 'org_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_email', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_phone', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'oar_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'oar_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute,
    public router: Router
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.form = new FormGroup({
      org_id: new FormControl({ value: null, disabled: true }),
      org_name: new FormControl({ value: null, disabled: true }),
      org_code: new FormControl({ value: null, disabled: true }),
      org_email: new FormControl({ value: null, disabled: true }),
      org_phone: new FormControl({ value: null, disabled: true }),
      oar_date_from: new FormControl(''),
      oar_date_to: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('org_id', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('org_code', new FormControl(''));
    this.searchForm.addControl('org_email', new FormControl(''));
    this.searchForm.addControl('org_phone', new FormControl(''));
    this.searchForm.addControl('oar_date_to', new FormControl(''));
    this.searchForm.addControl('oar_date_from', new FormControl(''));
    this.tabMenu = [
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleUsersBrowse.usersTab') as string,
        icon: 'pi pi-fw pi-users',
        command: (): void => {
          void this.router.navigate([
            '/',
            RoutingConst.INTERNAL,
            RoutingConst.ADMIN,
            RoutingConst.SPARK_ROLES_BROWSE,
            RoutingConst.SPARK_ROLE_USERS,
            this.activatedRoute.snapshot.paramMap.get('id'),
          ]);
        },
      },
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleUsersBrowse.organizationsTab') as string,
        icon: 'pi pi-fw pi-building',
        command: (): void => {
          this.router.url;
        },
      },
    ];
    if (!this.activeItem && this.tabMenu && this.tabMenu.length) {
      this.activeItem = this.tabMenu[1];
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
    if (this.activatedRoute) {
      this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((par) => {
        this.getSelectedRole(par.id as string);
        this.getRoleOrganizations(par.id as string, pagingParams, params, extendedParams);
        this.checked = false;
      });
    }
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getRoleOrganizationsList(
          this.selected_role_id,
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response) => {
          this.exportData = response.data;
        });
    }
  }

  getRoleOrganizations(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getRoleOrganizationsList(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.org_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData) => {
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        this.roleOrganizations = tableData.data;
      });
  }

  getSelectedRole(id: string, actionType?: string): void {
    this.adminService.getRoleRecord(id, actionType).subscribe((role) => {
      this.selected_role = role.rol_name;
      this.selected_role_id = `${role.rol_id}`;
    });
  }

  onCheckboxesChange(isChecked: boolean): void {
    if (isChecked === false && this.assignedOrgsList.length != 0) {
      this.assignedOrgsList = [];
    }
    for (const data of this.roleOrganizations) {
      data.isSelected = isChecked;
      this.assignedOrgsList.push(data);
    }
    this.checkedOrgsArray = this.assignedOrgsList.filter((row) => row.isSelected);
    this.uncheckedOrgsArray = this.assignedOrgsList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: SprRoleOrganizationsBrowseRow): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;
    const org: SprRoleOrganizationsBrowseRow = { ...data };
    // checking if organization was not previously added to checked organizations array
    if (this.checkedOrgsArray.indexOf(org) === -1 && eventTarget.checked) {
      this.checkedOrgsArray.push(org);
      // checking if organization was not previously added to unchecked array and removing it from (un)checked arrays if it was
      if (this.uncheckedOrgsArray.length != 0) {
        for (let i = 0; i < this.uncheckedOrgsArray.length; i++) {
          if (this.uncheckedOrgsArray[i].org_id === org.org_id) {
            this.uncheckedOrgsArray = this.uncheckedOrgsArray.filter((row) => row.org_id != org.org_id);
            this.checkedOrgsArray = this.checkedOrgsArray.filter((row) => row.org_id != org.org_id);
          }
        }
      }
    }
    // checking if organization was not previously added to unchecked organizations array
    if (this.uncheckedOrgsArray.indexOf(org) === -1 && !eventTarget.checked) {
      this.uncheckedOrgsArray.push(org);
      // checking if organization was not previously added to checked array and removing it from (un)checked arrays if it was
      if (this.checkedOrgsArray.length != 0) {
        for (let i = 0; i < this.checkedOrgsArray.length; i++) {
          if (this.checkedOrgsArray[i].org_id === org.org_id) {
            this.uncheckedOrgsArray = this.uncheckedOrgsArray.filter((row) => row.org_id != org.org_id);
            this.checkedOrgsArray = this.checkedOrgsArray.filter((row) => row.org_id != org.org_id);
          }
        }
      }
    }
    this.assignedOrgsList = this.assignedOrgsList.filter((row) => row.org_id !== org.org_id);
    this.assignedOrgsList.push(org);
  }

  saveRecWithConfirmation(): void {
    if (this.checkedOrgsArray.length === 0 && this.uncheckedOrgsArray.length === 0) {
      this.assignedOrgsList = [];
      this.commonFormServices.translate
        .get('pages.sprRoleOrganizations.noDialogChangesMade')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showInfo('', translation);
        });
    } else {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.saveConfirmationMsg') as string,
        accept: () => {
          this.save();
        },
      });
    }
  }

  save(): void {
    const orgs = this.rowsToOrganizationRoleRequest(this.assignedOrgsList);
    this.adminService.setOrganizationRolesData(orgs).subscribe(() => {
      this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
      this.reload();
      this.clearArrays();
    });
    this.checked = false;
  }

  clearArrays(): void {
    this.assignedOrgsList = [];
    this.checkedOrgsArray = [];
    this.uncheckedOrgsArray = [];
  }

  getActions(row: SprRoleOrganizationsBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          icon: getActionIcons(action as ActionsEnum),
          command: (): void => {
            this.editRoleOrganization(row);
          },
        });
      });
    return menu;
  }
  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  editRoleOrganization(row: SprRoleOrganizationsBrowseRow): void {
    this.form.reset();
    this.actionDialog = true;
    this.oar_id = row.oar_id;
    this.form.controls.org_id.setValue(row.org_id);
    this.form.controls.org_name.setValue(row.org_name);
    this.form.controls.org_code.setValue(row.org_code);
    this.form.controls.org_email.setValue(row.org_email);
    this.form.controls.org_phone.setValue(row.org_phone);
    this.form.controls.oar_date_from.setValue(row?.oar_date_from ? new Date(row.oar_date_from) : null);
    this.form.controls.oar_date_to.setValue(row?.oar_date_to ? new Date(row.oar_date_to) : null);
  }

  updateDateRestriction(): void {
    if (this.form.controls.oar_date_from.value === null) {
      this.minDate = new Date();
    } else {
      this.minDate = this.form.controls.oar_date_from.value as Date;
    }
  }

  saveDateWithConfirmation(): void {
    // checking if there were no changes made to both date to and date from
    if (!this.form.controls.oar_date_from.dirty && !this.form.controls.oar_date_to.dirty) {
      this.commonFormServices.translate
        .get('pages.sprRoleOrganizations.noDialogChangesMade')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showInfo('', translation);
        });
      // checking if either of the dates was provided
    } else if (this.form.controls.oar_date_from.value === null && this.form.controls.oar_date_to.value === null) {
      this.commonFormServices.translate.get('common.error.noDate').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
      // checking if value of date_from is not larger than date_to (if provided)
    } else if (
      this.form.controls.oar_date_from.value != null &&
      this.form.controls.oar_date_to.value != null &&
      this.form.controls.oar_date_to.value < this.form.controls.oar_date_from.value
    ) {
      this.commonFormServices.translate.get('common.error.dateFrom').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
      // checking if org was not previously assigned and if either of the date fields is filled out
    } else if (
      !this.oar_id &&
      (this.form.controls.oar_date_from.value != null || this.form.controls.oar_date_to.value != null)
    ) {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant(
          'pages.sprOrgAvailableRolesBrowse.assignRoleThroughDate'
        ) as string,
        accept: () => {
          this.saveDate();
          this.hideDialog();
        },
      });
    } else {
      this.saveDate();
    }
  }

  saveDate(): void {
    const orgForUpdate: OrganizationRoleRequest = {
      oar_id: this.oar_id,
      org_id: this.form.controls.org_id.value as string,
      rol_id: this.selected_role_id,
      isSelected: true,
      oar_date_from: this.form.controls.oar_date_from.value as Date,
      oar_date_to: this.form.controls.oar_date_to.value as Date,
    };
    if (!this.oar_id) {
      this.adminService.setOrganizationRolesData([orgForUpdate]).subscribe(() => {
        this.reload();
        this.hideDialog();
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
      });
    } else {
      this.adminService.setOrganizationRolesDate(orgForUpdate).subscribe(() => {
        this.reload();
        this.hideDialog();
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
      });
    }
  }

  onCancel(): void {
    if (this.form.controls.oar_date_from.dirty || this.form.controls.oar_date_to.dirty) {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.hideDialog();
        },
      });
    } else {
      this.hideDialog();
    }
  }

  hideDialog(): void {
    this.actionDialog = false;
    this.clearArrays();
    this.reload();
  }

  redirectToRolesBrowse(): void {
    void this.commonFormServices.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.ADMIN,
      RoutingConst.SPARK_ROLES_BROWSE,
    ]);
  }

  rowsToOrganizationRoleRequest(rows: SprRoleOrganizationsBrowseRow[]): OrganizationRoleRequest[] {
    return rows.map(this.rowToOrganizationRoleRequest.bind(this));
  }

  rowToOrganizationRoleRequest(row: SprRoleOrganizationsBrowseRow): OrganizationRoleRequest {
    return {
      isSelected: row.isSelected,
      oar_date_from: row.oar_date_from,
      oar_date_to: row.oar_date_to,
      oar_id: row.oar_id,
      org_id: row.org_id,
      rol_id: this.selected_role_id,
    };
  }
}
