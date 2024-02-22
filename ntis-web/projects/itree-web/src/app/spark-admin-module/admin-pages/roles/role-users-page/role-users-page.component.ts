import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_FORMS_LIST, SPR_ROLE_USERS_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, UserRoleRequest } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
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
import { RoleUsersDetails, UserRequest } from '../../../models/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-role-users-page',
  templateUrl: './role-users-page.component.html',
  styleUrls: ['./role-users-page.component.scss'],
})
export class RoleUsersPageComponent extends BaseBrowseForm<RoleUsersDetails> implements OnInit, OnDestroy {
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'pages.sprRoleUsersBrowse';
  readonly formCode = SPR_ROLE_USERS_LIST;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  destroy$: Subject<boolean> = new Subject();
  selected_role: string;
  selected_role_id: string;
  checked: boolean = false;
  form: FormGroup;
  actionDialog: boolean;
  dialogHeader: string;
  uro_id: string;
  minDate: Date;
  today: Date = new Date();
  joinRoleUsr: RoleUsersDetails[] = [];
  checkDataList: UserRequest[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: RoleUsersDetails[] = [];
  // primeNgTabMenu
  tabMenu: MenuItem[];
  activeItem: MenuItem;
  // routings
  sparkRoleOrganizations = RoutingConst.SPARK_ROLE_ORGANIZATIONS;
  // data arrays created on checkbox change
  selectedRowsArray: UserRequest[] = [];
  unselectedRowsArray: UserRequest[] = [];
  allChangedRowsArray: UserRequest[] = [];
  // user granted actions
  userCanCreate: boolean;
  userCanDelete: boolean;
  userCanUpdate: boolean;
  // Column array
  cols: TableColumn[] = [
    { field: 'usr_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_username', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_person_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_person_surname', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
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
      usr_id: new FormControl({ value: null, disabled: true }),
      usr_username: new FormControl({ value: null, disabled: true }),
      usr_type: new FormControl({ value: null, disabled: true }),
      usr_person_name: new FormControl({ value: null, disabled: true }),
      usr_person_surname: new FormControl({ value: null, disabled: true }),
      org_name: new FormControl({ value: null, disabled: true }),
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
    this.userCanUpdate
      ? this.commonFormServices.translate.get('common.action.edit').subscribe((translation: string) => {
          this.dialogHeader = translation;
        })
      : this.commonFormServices.translate.get('common.action.view').subscribe((translation: string) => {
          this.dialogHeader = translation;
        });
  }

  ngOnInit(): void {
    this.searchForm.addControl('usr_id', new FormControl(''));
    this.searchForm.addControl('usr_username', new FormControl(''));
    this.searchForm.addControl('usr_type', new FormControl(''));
    this.searchForm.addControl('usr_person_name', new FormControl(''));
    this.searchForm.addControl('usr_person_surname', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('uro_date_to', new FormControl(''));
    this.searchForm.addControl('uro_date_from', new FormControl(''));
    this.tabMenu = [
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleUsersBrowse.usersTab') as string,
        icon: 'pi pi-fw pi-users',
        command: (): void => {
          this.commonFormServices.router.url;
          return null;
        },
      },
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleUsersBrowse.organizationsTab') as string,
        icon: 'pi pi-fw pi-building',
        command: (): void => {
          void this.commonFormServices.router.navigate([
            'int/admin/spr-roles-browse/',
            this.sparkRoleOrganizations,
            this.activatedRoute.snapshot.paramMap.get('id'),
          ]);
          return null;
        },
      },
    ];
    if (!this.activeItem && this.tabMenu && this.tabMenu.length) {
      this.activeItem = this.tabMenu[0];
    }
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
        this.getRoleUsers(par.id as string, pagingParams, this.fixDate(params), extendedParams);
        this.getSelectedRole(par.id as string);
        this.findIndexById(par.id as string);
        this.checked = false;
      });
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  getRoleUsers(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getRoleUsersRecord(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.usr_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<RoleUsersDetails>) => {
        this.joinRoleUsr = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getRoleUsersRecord(
          this.selected_role_id.toString(),
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<RoleUsersDetails>) => {
          this.exportData = response.data;
        });
    }
  }

  redirectToRolesBrowse(): void {
    void this.commonFormServices.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.ADMIN,
      RoutingConst.SPARK_ROLES_BROWSE,
    ]);
  }

  onCheckboxesChange(isChecked: boolean): void {
    this.checkDataList = [];

    for (const data of this.joinRoleUsr) {
      data.isSelected = isChecked;
      this.checkDataList.push({
        isSelected: data.isSelected,
        uro_date_from: data.uro_date_from,
        uro_date_to: data.uro_date_to,
        user_id: parseInt(data.usr_id),
        rol_id: this.selected_role_id,
        uro_id: data.uro_id,
      });
      this.allChangedRowsArray.push({
        isSelected: data.isSelected,
        uro_date_from: data.uro_date_from,
        uro_date_to: data.uro_date_to,
        user_id: parseInt(data.usr_id),
        rol_id: this.selected_role_id,
        uro_id: data.uro_id,
      });
    }
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: RoleUsersDetails): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;

    if (this.selectedRowsArray.length > 0 && !data.isSelected) {
      this.selectedRowsArray = this.selectedRowsArray.filter((row) => row.user_id.toString() !== data.usr_id);
    }
    if (this.unselectedRowsArray.length > 0 && data.isSelected) {
      this.unselectedRowsArray = this.unselectedRowsArray.filter((row) => row.user_id.toString() !== data.usr_id);
    }
    if (this.checkDataList.length > 0) {
      this.checkDataList = this.checkDataList.filter((row) => row.user_id.toString() !== data.usr_id);
    }

    this.checkDataList.push({
      isSelected: data.isSelected,
      uro_date_from: data.uro_date_from,
      uro_date_to: data.uro_date_to,
      user_id: parseInt(data.usr_id),
      rol_id: this.selected_role_id,
      uro_id: data.uro_id,
    });
    this.allChangedRowsArray.push({
      isSelected: data.isSelected,
      uro_date_from: data.uro_date_from,
      uro_date_to: data.uro_date_to,
      user_id: parseInt(data.usr_id),
      rol_id: this.selected_role_id,
      uro_id: data.uro_id,
    });

    // filter checkDataList to selected and unselected rows
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  getSelectedRole(id: string, actionType?: string): void {
    this.adminService.getRoleRecord(id, actionType).subscribe((role) => {
      this.selected_role = role.rol_name;
      this.selected_role_id = `${role.rol_id}`;
    });
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_uro_date_from', this.datePipe.transform(params.get('p_uro_date_from') as string))
      .set('p_uro_date_to', this.datePipe.transform(params.get('p_uro_date_to') as string));
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.joinRoleUsr.length; i++) {
      if (this.joinRoleUsr[i].usr_id === id) {
        index = i;
        break;
      }
    }
    return index;
  }

  saveRecWithConfirmation(): void {
    // show info if user didn't make any changes
    if (this.selectedRowsArray.length === 0 && this.unselectedRowsArray.length === 0) {
      this.checkDataList = [];
      this.commonFormServices.translate
        .get('pages.sprRoleUsersBrowse.noChangesMade')
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
    if (this.isCreateOrDeleteGranted()) {
      const users: UserRoleRequest[] = [];
      for (const user of this.checkDataList) {
        users.push({
          user_id: user.user_id,
          uro_id: user.uro_id,
          rol_id: user.rol_id,
          uro_date_from: user.uro_date_from,
          uro_date_to: user.uro_date_to,
          update: user.isSelected,
        });
      }
      this.adminService.setUserRolesData(users).subscribe(() => {
        this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
        this.clearCheckboxArrays();
        this.checked = false;
        this.reload();
      });
    }
  }

  isCreateOrDeleteGranted(): boolean {
    // throw error if user selected rows and is not granted to
    if (!this.userCanCreate && this.selectedRowsArray.length > 0) {
      this.clearCheckboxArrays();
      this.reload();
      this.commonFormServices.translate
        .get('pages.sprRoleUsersBrowse.userIsNotGrantedToCreate')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
      return false;
    }
    // throw error if user unselected rows and is not granted to
    if (!this.userCanDelete && this.unselectedRowsArray.length > 0) {
      this.clearCheckboxArrays();
      this.reload();
      this.commonFormServices.translate
        .get('pages.sprRoleUsersBrowse.userIsNotGrantedToDelete')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
      return false;
    }
    return true;
  }

  clearCheckboxArrays(): void {
    this.checkDataList = [];
    this.unselectedRowsArray = [];
    this.selectedRowsArray = [];
    this.allChangedRowsArray = [];
  }

  saveDate(uro_id: string): void {
    if (this.form.controls.uro_date_from.valid) {
      if (uro_id === '') {
        const user: UserRoleRequest[] = [
          {
            uro_id: uro_id,
            user_id: parseInt(this.form.controls.usr_id.value as string),
            rol_id: this.selected_role_id,
            uro_date_from: this.form.controls.uro_date_from.value as Date,
            uro_date_to: this.form.controls.uro_date_to.value as Date,
            update: true,
          },
        ];
        this.adminService.setUserRolesData(user).subscribe(() => {
          this.reload();
          this.hideDialog();
          this.commonFormServices.appMessages.showSuccess(
            '',
            this.commonFormServices.translate.instant('common.message.saveSuccess') as string
          );
        });
      } else {
        this.adminService
          .setUserRolesDate(
            uro_id,
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
    } else {
      this.form.controls.uro_date_from.markAsDirty();
    }
  }

  editRoleUsersDates(row: RoleUsersDetails): void {
    this.form.reset();
    this.actionDialog = true;
    this.uro_id = row.uro_id;
    this.form.controls.usr_id.setValue(row.usr_id);
    this.form.controls.usr_type.setValue(row.usr_type);
    this.form.controls.usr_username.setValue(row.usr_username);
    this.form.controls.usr_person_name.setValue(row.usr_person_name);
    this.form.controls.usr_person_surname.setValue(row.usr_person_surname);
    this.form.controls.org_name.setValue(row.org_name);
    this.form.controls.uro_date_from.setValue(row?.uro_date_from ? new Date(row.uro_date_from) : null);
    this.form.controls.uro_date_to.setValue(row?.uro_date_to ? new Date(row.uro_date_to) : null);
  }

  hideDialog(): void {
    this.actionDialog = false;
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
    if (!this.checkFieldsDate()) {
      this.hideDialog();
    } else {
      this.commonFormServices.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.hideDialog();
          this.reload();
        },
      });
    }
  }

  getActions(row: RoleUsersDetails): MenuItem[] {
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
            this.editRoleUsersDates(row);
          },
        });
      });
    return menu;
  }

  updateDateRestriction(): void {
    if (this.form.controls.uro_date_from.value === null) {
      this.minDate = this.today;
    } else {
      this.minDate = this.form.controls.uro_date_from.value as Date;
    }
  }
}
