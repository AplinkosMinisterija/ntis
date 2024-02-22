import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_ORGANIZATION_ROLES_LIST } from '@itree-commons/src/constants/forms.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { OrganizationRoleRequest, SprOrganizationsDAO } from '@itree-commons/src/lib/model/api/api';
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
import { SprOrgAvailableRolesBrowseRow } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Component({
  selector: 'app-organizations-roles-page',
  templateUrl: './organizations-roles-page.component.html',
  styleUrls: ['./organizations-roles-page.component.scss'],
})
export class OrganizationsRolesPageComponent extends BaseBrowseForm<SprOrgAvailableRolesBrowseRow> implements OnInit {
  readonly formCode = SPR_ORGANIZATION_ROLES_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly translationsReference = 'pages.sprOrgAvailableRolesBrowse';
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  destroy$: Subject<boolean> = new Subject<boolean>();
  form: FormGroup;
  rowMenuItems: Record<string, MenuItem[]> = {};
  organizationRoles: SprOrgAvailableRolesBrowseRow[] = [];
  exportData: SprOrgAvailableRolesBrowseRow[] = [];
  assignedRolesList: OrganizationRoleRequest[] = [];
  checkedRolesArray: OrganizationRoleRequest[] = [];
  uncheckedRolesArray: OrganizationRoleRequest[] = [];
  selected_org: string;
  selected_org_id: string;
  oar_id: string;
  checked: boolean = false;
  actionDialog: boolean = false;
  minDate: Date;

  cols: TableColumn[] = [
    { field: 'rol_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_description', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'oar_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'oar_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);
  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private activatedRoute: ActivatedRoute
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.form = new FormGroup({
      rol_id: new FormControl({ value: null, disabled: true }),
      rol_code: new FormControl({ value: null, disabled: true }),
      rol_name: new FormControl({ value: null, disabled: true }),
      rol_type: new FormControl({ value: null, disabled: true }),
      rol_description: new FormControl({ value: null, disabled: true }),
      oar_date_from: new FormControl(''),
      oar_date_to: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('rol_id', new FormControl(''));
    this.searchForm.addControl('rol_code', new FormControl(''));
    this.searchForm.addControl('rol_name', new FormControl(''));
    this.searchForm.addControl('rol_description', new FormControl(''));
    this.searchForm.addControl('rol_type', new FormControl(''));
    this.searchForm.addControl('oar_date_to', new FormControl(''));
    this.searchForm.addControl('oar_date_from', new FormControl(''));
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
        this.getOrganizationRoles(par.id as string, pagingParams, params, extendedParams);
        this.getSelectedOrg(par.id as string);
        this.checked = false;
      });
    }
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getOrganizationRolesList(
          this.selected_org_id.toString(),
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response) => {
          this.exportData = response.data;
        });
    }
  }

  getOrganizationRoles(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getOrganizationRolesList(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.rol_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData) => {
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        this.organizationRoles = tableData.data;
      });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  getSelectedOrg(id: string, actionType?: string): void {
    this.adminService.getOrganizationRecord(id, actionType).subscribe((tableData: SprOrganizationsDAO) => {
      const orgInfo = tableData;
      this.selected_org = orgInfo.org_name;
      this.selected_org_id = orgInfo.org_id.toString();
    });
  }

  onCheckboxesChange(isChecked: boolean): void {
    if (isChecked === false && this.assignedRolesList.length != 0) {
      this.assignedRolesList = [];
    }
    for (const data of this.organizationRoles) {
      data.isSelected = isChecked;
      this.assignedRolesList.push({
        org_id: this.selected_org_id,
        rol_id: data.rol_id,
        oar_id: data.oar_id,
        isSelected: data.isSelected,
        oar_date_from: data.oar_date_from,
        oar_date_to: data.oar_date_to,
      });
    }
    this.checkedRolesArray = this.assignedRolesList.filter((row) => row.isSelected);
    this.uncheckedRolesArray = this.assignedRolesList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: OrganizationRoleRequest): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;
    const role: OrganizationRoleRequest = {
      org_id: data.org_id,
      rol_id: eventTarget.value,
      oar_id: data.oar_id,
      isSelected: data.isSelected,
      oar_date_from: data.oar_date_from,
      oar_date_to: data.oar_date_to,
    };
    // checking if role was not previously added to checked roles array
    if (this.checkedRolesArray.indexOf(role) === -1 && eventTarget.checked) {
      this.checkedRolesArray.push(role);
      // checking if role was not previously added to unchecked array and removing it from (un)checked arrays if it was
      if (this.uncheckedRolesArray.length != 0) {
        for (let i = 0; i < this.uncheckedRolesArray.length; i++) {
          if (this.uncheckedRolesArray[i].rol_id === role.rol_id) {
            this.uncheckedRolesArray = this.uncheckedRolesArray.filter((row) => row.rol_id != role.rol_id);
            this.checkedRolesArray = this.checkedRolesArray.filter((row) => row.rol_id != role.rol_id);
          }
        }
      }
    }
    // checking if role was not previously added to unchecked roles array
    if (this.uncheckedRolesArray.indexOf(role) === -1 && !eventTarget.checked) {
      this.uncheckedRolesArray.push(role);
      // checking if role was not previously added to checked array and removing it from (un)checked arrays if it was
      if (this.checkedRolesArray.length != 0) {
        for (let i = 0; i < this.checkedRolesArray.length; i++) {
          if (this.checkedRolesArray[i].rol_id === role.rol_id) {
            this.uncheckedRolesArray = this.uncheckedRolesArray.filter((row) => row.rol_id != role.rol_id);
            this.checkedRolesArray = this.checkedRolesArray.filter((row) => row.rol_id != role.rol_id);
          }
        }
      }
    }
    this.assignedRolesList = this.assignedRolesList.filter((row) => row.rol_id !== data.rol_id);
    this.assignedRolesList.push({
      org_id: data.org_id,
      rol_id: data.rol_id,
      oar_id: data.oar_id,
      isSelected: data.isSelected,
      oar_date_from: data.oar_date_from,
      oar_date_to: data.oar_date_to,
    });
  }

  saveRecWithConfirmation(): void {
    if (this.checkedRolesArray.length === 0 && this.uncheckedRolesArray.length === 0) {
      this.assignedRolesList = [];
      this.commonFormServices.translate
        .get(this.translationsReference + '.noDialogChangesMade')
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
    const roles: OrganizationRoleRequest[] = [];
    for (const role of this.assignedRolesList) {
      roles.push({
        org_id: this.selected_org_id,
        rol_id: role.rol_id,
        oar_id: role.oar_id,
        isSelected: role.isSelected,
        oar_date_from: role.oar_date_from,
        oar_date_to: role.oar_date_to,
      });
    }
    this.adminService.setOrganizationRolesData(roles).subscribe(() => {
      this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
      this.reload();
      this.clearArrays();
    });
    this.checked = false;
  }

  clearArrays(): void {
    this.assignedRolesList = [];
    this.checkedRolesArray = [];
    this.uncheckedRolesArray = [];
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

  editOrganizationRole(row: SprOrgAvailableRolesBrowseRow): void {
    this.form.reset();
    this.actionDialog = true;
    this.oar_id = row.oar_id;
    this.form.controls.rol_id.setValue(row.rol_id);
    this.form.controls.rol_code.setValue(row.rol_code);
    this.form.controls.rol_name.setValue(row.rol_name);
    this.form.controls.rol_type.setValue(row.rol_type);
    this.form.controls.rol_description.setValue(row.rol_description);
    this.form.controls.oar_date_from.setValue(row?.oar_date_from ? new Date(row.oar_date_from) : null);
    this.form.controls.oar_date_to.setValue(row?.oar_date_to ? new Date(row.oar_date_to) : null);
  }

  saveDateWithConfirmation(): void {
    // checking if date form fields have been edited
    if (!this.form.controls.oar_date_from.dirty && !this.form.controls.oar_date_to.dirty) {
      this.commonFormServices.translate
        .get(this.translationsReference + '.noDialogChangesMade')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showInfo('', translation);
        });
      // checking if either of the dates were provided
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
      // checking if role was not previously assigned and if either of the date fields is filled out
    } else if (
      (this.oar_id === null || this.oar_id === '') &&
      (this.form.controls.oar_date_from.value != null || this.form.controls.oar_date_to.value != null)
    ) {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant(
          this.translationsReference + '.assignRoleThroughDate'
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
    const roleForUpdate: OrganizationRoleRequest = {
      oar_id: this.oar_id,
      org_id: this.selected_org_id,
      rol_id: this.form.controls.rol_id.value as string,
      isSelected: true,
      oar_date_from: this.form.controls.oar_date_from.value as Date,
      oar_date_to: this.form.controls.oar_date_to.value as Date,
    };

    if (!this.oar_id) {
      this.adminService.setOrganizationRolesData([roleForUpdate]).subscribe(() => {
        this.reload();
        this.hideDialog();
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
      });
    } else {
      this.adminService.setOrganizationRolesDate(roleForUpdate).subscribe(() => {
        this.reload();
        this.hideDialog();
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
      });
    }
  }

  hideDialog(): void {
    this.actionDialog = false;
    this.clearArrays();
    this.reload();
  }

  updateDateRestriction(): void {
    if (this.form.controls.oar_date_from.value === null) {
      this.minDate = new Date();
    } else {
      this.minDate = this.form.controls.oar_date_from.value as Date;
    }
  }
  getActions(row: SprOrgAvailableRolesBrowseRow): MenuItem[] {
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
            this.editOrganizationRole(row);
          },
        });
      });
    return menu;
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_ORGANIZATIONS_BROWSE],
      navigationExtras
    );
  }
}
