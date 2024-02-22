import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { SPR_FORMS_LIST, SPR_USER_ROLES_LIST } from '@itree-commons/src/constants/forms.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, UserRoleRequest } from '@itree-commons/src/lib/model/api/api';
import { SprUsersDAO } from '@itree-commons/src/lib/model/api/api.d';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { LazyLoadEvent, MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { Subject, takeUntil, tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { UserRequest, UserRolesDetails } from '../../../models/browse-pages';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

interface UserRolDetails {
  uro_date_from: Date;
  uro_date_to: Date;
  uro_id: string;
  rol_code: string;
  rol_type: string;
  rol_name: string;
  rol_description: string;
  rol_id: string;
  availableActions: string[];
  isSelected: boolean;
  user_id: string;
}

@Component({
  selector: 'app-user-roles-page',
  templateUrl: './user-roles-page.component.html',
  styleUrls: ['./user-roles-page.component.scss'],
})
export class UserRolesPageComponent extends BaseBrowseForm<UserRolesDetails> implements OnInit, OnDestroy {
  readonly formCode = SPR_USER_ROLES_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;
  destroy$: Subject<boolean> = new Subject();
  wasInteractedWith: unknown[] = [];
  selected_user: string;
  selected_user_id: number;
  checked: boolean = false;
  form: FormGroup;
  actionDialog: boolean;
  uro_id: string;
  minDate: Date;
  joinUsrRoles: UserRolDetails[] = [];
  checkDataList: UserRequest[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: UserRolesDetails[] = [];

  checkedRolesArray: UserRequest[] = [];
  uncheckedRolesArray: UserRequest[] = [];

  userCanCreate: boolean;
  userCanDelete: boolean;
  userCanUpdate: boolean;
  // Column array
  cols: TableColumn[] = [
    { field: 'rol_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_description', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'uro_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'uro_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private datePipe: S2DatePipe,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_CREATE);
    this.userCanDelete = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_DELETE);
    this.userCanUpdate = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_UPDATE);
    this.form = new FormGroup({
      rol_id: new FormControl({ value: null, disabled: true }),
      rol_code: new FormControl({ value: null, disabled: true }),
      rol_name: new FormControl({ value: null, disabled: true }),
      rol_type: new FormControl({ value: null, disabled: true }),
      rol_description: new FormControl({ disabled: true }),
      uro_date_from: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10)])
      ),
      uro_date_to: new FormControl('', Validators.compose([Validators.minLength(10), Validators.maxLength(10)])),
    });
    this.form.controls.uro_date_from.addValidators([
      SprDateValidators.validateDateFrom(this.form.controls.uro_date_to),
    ]);
    this.form.controls.uro_date_to.addValidators([SprDateValidators.validateDateTo(this.form.controls.uro_date_from)]);

    this.form.controls.uro_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.uro_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.uro_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.uro_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('rol_id', new FormControl(''));
    this.searchForm.addControl('rol_code', new FormControl(''));
    this.searchForm.addControl('rol_name', new FormControl(''));
    this.searchForm.addControl('rol_description', new FormControl(''));
    this.searchForm.addControl('rol_type', new FormControl(''));
    this.searchForm.addControl('uro_date_to', new FormControl(''));
    this.searchForm.addControl('uro_date_from', new FormControl(''));
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
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
        this.getUserRoles(par.id as string, pagingParams, this.fixDate(params), extendedParams);
        this.getSelectedUser(par.id as string);
        this.findIndexById(par.id as string);
        this.checked = false;
      });
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  getUserRoles(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getUserRolesRecord(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.rol_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<UserRolesDetails>) => {
        const userRoles = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        this.joinUsrRoles = [];
        for (const rol of userRoles) {
          this.joinUsrRoles.push({
            uro_id: rol.uro_id,
            availableActions: rol.availableActions,
            user_id: id,
            isSelected: rol.belongs === DB_BOOLEAN_TRUE,
            rol_id: rol.rol_id,
            rol_code: rol.rol_code,
            rol_type: rol.rol_type,
            rol_name: rol.rol_name,
            rol_description: rol.rol_description,
            uro_date_from: rol.uro_date_from,
            uro_date_to: rol.uro_date_to,
          });
        }
        if (this.checkDataList.length > 0 && !this.checked) {
          for (const data of this.checkDataList) {
            const userRole = this.joinUsrRoles.filter((dt) => dt.rol_id === data.rol_id.toString())[0];
            if (userRole !== undefined) {
              userRole.isSelected = data.isSelected;
            }
          }
        }
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getUserRolesRecord(
          this.selected_user_id.toString(),
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<UserRolesDetails>) => {
          this.exportData = response.data;
        });
    }
  }

  onCheckboxesChange(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    const isChecked = eventTarget.checked;
    this.checked = isChecked;
    if (this.checked === false && this.checkDataList.length != 0) {
      this.checkDataList = [];
    }

    for (const data of this.joinUsrRoles) {
      data.isSelected = isChecked;
      this.checkDataList.push({
        uro_id: data.uro_id,
        user_id: parseInt(data.user_id),
        rol_id: data.rol_id,
        isSelected: data.isSelected,
        uro_date_from: data.uro_date_from,
        uro_date_to: data.uro_date_to,
      });
    }
    this.checkedRolesArray = this.checkDataList.filter((row) => row.isSelected);
    this.uncheckedRolesArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: UserRequest): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;
    const role: UserRequest = {
      user_id: data.user_id,
      rol_id: eventTarget.value,
      uro_id: data.uro_id,
      uro_date_from: data.uro_date_from,
      uro_date_to: data.uro_date_to,
    };
    if (this.checkedRolesArray.indexOf(role) === -1 && eventTarget.checked) {
      this.checkedRolesArray.push(role);
      if (this.uncheckedRolesArray.length != 0) {
        for (let i = 0; i < this.uncheckedRolesArray.length; i++) {
          if (this.uncheckedRolesArray[i].rol_id === role.rol_id) {
            this.uncheckedRolesArray = this.uncheckedRolesArray.filter((row) => row.rol_id != role.rol_id);
            this.checkedRolesArray = this.checkedRolesArray.filter((row) => row.rol_id != role.rol_id);
          }
        }
      }
    }
    if (this.uncheckedRolesArray.indexOf(role) === -1 && !eventTarget.checked) {
      this.uncheckedRolesArray.push(role);
      if (this.checkedRolesArray.length != 0) {
        for (let i = 0; i < this.checkedRolesArray.length; i++) {
          if (this.checkedRolesArray[i].rol_id === role.rol_id) {
            this.uncheckedRolesArray = this.uncheckedRolesArray.filter((row) => row.rol_id != role.rol_id);
            this.checkedRolesArray = this.checkedRolesArray.filter((row) => row.rol_id != role.rol_id);
          }
        }
      }
    }
    this.checkDataList = this.checkDataList.filter((row) => row.rol_id !== data.rol_id);
    this.checkDataList.push({
      uro_id: data.uro_id,
      user_id: data.user_id,
      rol_id: data.rol_id,
      isSelected: data.isSelected,
      uro_date_from: data.uro_date_from,
      uro_date_to: data.uro_date_to,
    });
  }
  getSelectedUser(id: string, actionType?: string): void {
    this.adminService.getUserRecord(id, actionType).subscribe((tableData: SprUsersDAO) => {
      const userInfo = tableData;
      this.selected_user = userInfo.usr_username;
      this.selected_user_id = userInfo.usr_id;
    });
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_uro_date_from', this.datePipe.transform(params.get('p_uro_date_from') as string))
      .set('p_uro_date_to', this.datePipe.transform(params.get('p_uro_date_to') as string));
  }

  showAllRecords(value: boolean, dataTable: LazyLoadEvent): void {
    if (value === true) {
      this.load(dataTable.first, null, dataTable.sortField, dataTable.sortOrder, this.getSearchParamMap(dataTable));
    } else {
      this.load(
        dataTable.first,
        dataTable.rows,
        dataTable.sortField,
        dataTable.sortOrder,
        this.getSearchParamMap(dataTable)
      );
    }
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.joinUsrRoles.length; i++) {
      if (this.joinUsrRoles[i].rol_id === id) {
        index = i;
        break;
      }
    }
    return index;
  }

  saveRecWithConfirmation(): void {
    if (this.checkedRolesArray.length === 0 && this.uncheckedRolesArray.length === 0) {
      this.checkDataList = [];
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
    if (
      (!this.userCanCreate && this.checkedRolesArray.length > 0) ||
      (!this.userCanDelete && this.uncheckedRolesArray.length > 0)
    ) {
      this.clearArrays();
      this.reload();
      this.commonFormServices.translate.get('common.error.action_not_granted').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
      return;
    }
    const roles: UserRoleRequest[] = [];
    for (const role of this.checkDataList) {
      roles.push({
        uro_id: role.uro_id,
        user_id: role.user_id,
        rol_id: role.rol_id,
        update: role.isSelected,
        uro_date_from: role.uro_date_from,
        uro_date_to: role.uro_date_to,
      });
    }
    this.adminService.setUserRolesData(roles).subscribe(() => {
      this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
      this.reload();
      this.clearArrays();
    });
    this.checked = false;
  }

  saveDateWithConfirmation(): void {
    if (!this.form.controls.uro_date_from.dirty && !this.form.controls.uro_date_to.dirty) {
      this.commonFormServices.translate
        .get('pages.sprUserRolesEdit.noDialogChangesMade')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showInfo('', translation);
        });
    } else if (this.form.controls.uro_date_from.value === null && this.form.controls.uro_date_to.value === null) {
      this.commonFormServices.translate.get('common.error.noDate').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else if (
      this.form.controls.uro_date_from.value != null &&
      this.form.controls.uro_date_to.value != null &&
      this.form.controls.uro_date_to.value < this.form.controls.uro_date_from.value
    ) {
      this.commonFormServices.translate.get('common.error.dateFrom').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else if (
      (this.uro_id === null || this.uro_id === '') &&
      (this.form.controls.uro_date_from.value != null || this.form.controls.uro_date_to.value != null)
    ) {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('pages.sprUserRolesEdit.assignRoleThroughDate') as string,
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
    if (this.uro_id === null || this.uro_id === '') {
      this.adminService
        .setUserRolesData([
          {
            uro_id: this.uro_id,
            user_id: this.selected_user_id,
            rol_id: this.form.controls.rol_id.value as string,
            update: true,
            uro_date_from: this.form.controls.uro_date_from.value as Date,
            uro_date_to: this.form.controls.uro_date_to.value as Date,
          },
        ])
        .subscribe(() => {
          this.reload();
          this.hideDialog();
          this.commonFormServices.appMessages.showSuccess(
            '',
            this.commonFormServices.translate.instant('common.message.saveSuccess') as string
          );
        });
      return;
    }
    this.adminService
      .setUserRolesDate(
        this.uro_id,
        this.form.controls.uro_date_to.value as Date,
        this.form.controls.uro_date_from.value as Date
      )
      .subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
        this.reload();
        this.actionDialog = false;
      });
  }

  editUserRoleDates(row: UserRolesDetails): void {
    this.form.reset();
    this.actionDialog = true;
    this.uro_id = row.uro_id;
    this.form.controls.rol_id.setValue(row.rol_id);
    this.form.controls.rol_code.setValue(row.rol_code);
    this.form.controls.rol_name.setValue(row.rol_name);
    this.form.controls.rol_type.setValue(row.rol_type);
    this.form.controls.rol_description.setValue(row.rol_description);
    this.form.controls.uro_date_from.setValue(row?.uro_date_from ? new Date(row.uro_date_from) : null);
    this.form.controls.uro_date_to.setValue(row?.uro_date_to ? new Date(row.uro_date_to) : null);
  }

  hideDialog(): void {
    this.actionDialog = false;
    this.wasInteractedWith = [];
  }

  checkFieldsDate(): boolean {
    for (const field in this.form.controls) {
      if (field === 'uro_date_to' || field === 'uro_date_from') {
        if (this.form.get(field).dirty === true) {
          return true;
        }
      }
    }
    return false;
  }

  onCancel(): void {
    if (!this.checkFieldsDate() && this.wasInteractedWith.length === 0) {
      this.hideDialog();
    } else if (this.checkFieldsDate() || this.wasInteractedWith.length > 0) {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.hideDialog();
          this.reload();
        },
      });
    }
  }

  getActions(row: UserRolesDetails): MenuItem[] {
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
            this.editUserRoleDates(row);
          },
        });
      });
    return menu;
  }

  updateDateRestriction(): void {
    if (this.form.controls.uro_date_from.value === null) {
      this.minDate = new Date();
    } else {
      this.minDate = this.form.controls.uro_date_from.value as Date;
    }
  }

  clearArrays(): void {
    this.checkDataList = [];
    this.checkedRolesArray = [];
    this.uncheckedRolesArray = [];
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_USERS_BROWSE],
      navigationExtras
    );
  }
}
