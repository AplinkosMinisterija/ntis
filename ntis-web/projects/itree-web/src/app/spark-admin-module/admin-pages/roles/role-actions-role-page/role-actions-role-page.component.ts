import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Spr_paging_ot } from '@itree/ngx-s2-commons/lib/model/spr_paging_ot';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AdminService } from '../../../admin-services/admin.service';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { Subject, takeUntil, tap } from 'rxjs';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { SPR_ROLE_ACTIONS_ROLE_LIST } from '@itree-commons/src/constants/forms.constants';
import { Table } from 'primeng/table';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { RoleActionsRoleDetails } from '../../../models/browse-pages';

interface RoleDetails {
  roa_rol_id: string;
  rol_id: string;
  roa_id: string;
  roa_assigned_rol_id: string;
  rol_code: string;
  rol_type: string;
  rol_name: string;
  rol_description: string;
  roa_date_from: string;
  roa_date_to: string;
  isSelected: boolean;
  availableActions: string[];
}

interface RolRequest {
  roa_assigned_rol_id: string;
  roa_id: string;
  roa_rol_id: string;
  rol_id: string;
  isSelected: boolean;
}

@Component({
  selector: 'app-role-actions-role-page',
  templateUrl: './role-actions-role-page.component.html',
  styleUrls: ['./role-actions-role-page.component.scss'],
})
export class RoleActionsRoleBrowsePageComponent
  extends BaseBrowseForm<RoleActionsRoleDetails>
  implements OnInit, OnDestroy
{
  readonly formCode = SPR_ROLE_ACTIONS_ROLE_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly ExtendedSearchCondition = ExtendedSearchCondition;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  rowMenuItems: Record<string, MenuItem[]> = {};
  destroy$: Subject<boolean> = new Subject();
  minDate: Date;
  today: Date = new Date();
  checked: boolean = false;
  rol_id: string;
  roa_id: string;
  roa_date_to: Date;
  roa_date_from: Date;
  selected_role: string;
  selected_role_id: string;
  form: FormGroup;
  actionDialog: boolean;
  dialogHeader: string;
  roleAction: RoleActionsRoleDetails[];
  wasInteractedWith: unknown[] = [];
  checkDataList: RolRequest[] = [];
  joinRoles: RoleDetails[];
  exportData: RoleActionsRoleDetails[] = [];
  // PrimeNgTabMenu
  tabMenu: MenuItem[];
  activeItem: MenuItem;
  // user granted actions
  userCanCreate: boolean;
  userCanUpdate: boolean;
  userCanDelete: boolean;
  // data arrays created on checkbox change
  selectedRowsArray: RolRequest[] = [];
  unselectedRowsArray: RolRequest[] = [];
  allChangedRowsArray: RolRequest[] = [];
  // Column array
  cols: TableColumn[] = [
    { field: 'rol_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rol_description', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'roa_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'roa_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    public router: Router,
    private activatedRoute: ActivatedRoute,
    private datePipe: S2DatePipe,
    private adminService: AdminService,
    private confirmationService: ConfirmationService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
    this.userCanDelete = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_DELETE);
    this.form = new FormGroup({
      rol_id: new FormControl({ value: null, disabled: true }),
      rol_name: new FormControl({ value: null, disabled: true }),
      rol_type: new FormControl({ value: null, disabled: true }),
      rol_code: new FormControl({ value: null, disabled: true }),
      rol_description: new FormControl({ value: null, disabled: true }),
      roa_date_from: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10)])
      ),
      roa_date_to: new FormControl('', Validators.compose([Validators.minLength(10), Validators.maxLength(10)])),
    });
    this.form.controls['roa_date_from'].addValidators([
      SprDateValidators.validateDateFrom(this.form.controls['roa_date_to']),
    ]);
    this.form.controls['roa_date_to'].addValidators([
      SprDateValidators.validateDateTo(this.form.controls['roa_date_from']),
    ]);
    this.form.controls.roa_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.roa_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.roa_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.roa_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.userCanUpdate
      ? this.commonFormServices.translate.get('common.action.edit').subscribe((translation: string) => {
          this.dialogHeader = translation;
        })
      : this.commonFormServices.translate.get('common.action.view').subscribe((translation: string) => {
          this.dialogHeader = translation;
        });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  redirectToHome(): void {
    void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_ROLES_BROWSE]);
  }

  ngOnInit(): void {
    this.searchForm.addControl('rol_id', new FormControl(''));
    this.searchForm.addControl('rol_name', new FormControl(''));
    this.searchForm.addControl('rol_code', new FormControl(''));
    this.searchForm.addControl('rol_type', new FormControl(''));
    this.searchForm.addControl('rol_description', new FormControl(''));
    this.searchForm.addControl('roa_date_from', new FormControl(''));
    this.searchForm.addControl('roa_date_to', new FormControl(''));
    this.minDate = new Date();
    this.tabMenu = [
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleActions.Forms_primeng') as string,
        icon: 'pi pi-fw pi-table',
        command: (): void => {
          void this.router.navigate([
            '/',
            RoutingConst.INTERNAL,
            RoutingConst.ADMIN,
            RoutingConst.SPARK_ROLES_BROWSE,
            RoutingConst.SPARK_ROLE_ACTIONS,
            this.activatedRoute.snapshot.paramMap.get('id'),
          ]);
        },
      },
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleActions.Roles_primeng') as string,
        icon: 'pi pi-fw pi-user',
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
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((par) => {
      this.getRoles(par.id as string, pagingParams, this.fixDate(params), extendedParams);
      this.getRoleInfo(par.id as string);
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  getRoles(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getRoleActionsRoleList(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.rol_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<RoleActionsRoleDetails>) => {
        const roleAction = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.selected_role_id = id;
        this.exportData = tableData.data;
        this.joinRoles = [];
        for (const rol of roleAction) {
          if (this.allChangedRowsArray.length > 0) {
            for (const row of this.allChangedRowsArray) {
              if (rol.rol_id === row.rol_id) {
                rol.belongs = row.isSelected ? 'Y' : '';
              }
            }
          }
          this.joinRoles.push({
            roa_id: rol.roa_id,
            roa_rol_id: id,
            rol_id: rol.rol_id,
            availableActions: rol.availableActions,
            roa_assigned_rol_id: rol.roa_assigned_rol_id,
            isSelected: rol.belongs === DB_BOOLEAN_TRUE,
            rol_code: rol.rol_code,
            rol_type: rol.rol_type,
            rol_name: rol.rol_name,
            rol_description: rol.rol_description,
            roa_date_from: rol.roa_date_from,
            roa_date_to: rol.roa_date_to,
          });
        }
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getRoleActionsRoleList(
          this.selected_role_id,
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.fixDate(this.getSearchParamMap(dataTable)),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<RoleActionsRoleDetails>) => {
          this.exportData = response.data;
        });
    }
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.joinRoles.length; i++) {
      if (this.joinRoles[i].rol_id === id) {
        index = i;
        break;
      }
    }
    return index;
  }

  onPageChange(): void {
    this.checked = false;
  }

  onCheckboxesChange(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    const isChecked = eventTarget.checked;
    this.checked = isChecked;
    this.checkDataList = [];

    for (const data of this.joinRoles) {
      data.isSelected = isChecked;
      this.checkDataList.push({
        roa_id: data.roa_id,
        roa_rol_id: data.roa_rol_id,
        roa_assigned_rol_id: data.rol_id,
        rol_id: data.rol_id,
        isSelected: data.isSelected,
      });
      this.allChangedRowsArray.push({
        roa_id: data.roa_id,
        roa_rol_id: data.roa_rol_id,
        roa_assigned_rol_id: data.rol_id,
        rol_id: data.rol_id,
        isSelected: data.isSelected,
      });
    }
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: RolRequest): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;

    if (this.selectedRowsArray.length > 0 && !data.isSelected) {
      this.selectedRowsArray = this.selectedRowsArray.filter((row) => row.rol_id !== data.rol_id);
    }
    if (this.unselectedRowsArray.length > 0 && data.isSelected) {
      this.unselectedRowsArray = this.unselectedRowsArray.filter((row) => row.rol_id !== data.rol_id);
    }
    if (this.checkDataList.length > 0) {
      this.checkDataList = this.checkDataList.filter((row) => row.rol_id !== data.rol_id);
    }

    this.checkDataList.push({
      roa_id: data.roa_id,
      roa_rol_id: data.roa_rol_id,
      roa_assigned_rol_id: data.rol_id,
      rol_id: data.rol_id,
      isSelected: data.isSelected,
    });
    this.allChangedRowsArray.push({
      roa_id: data.roa_id,
      roa_rol_id: data.roa_rol_id,
      roa_assigned_rol_id: data.rol_id,
      rol_id: data.rol_id,
      isSelected: data.isSelected,
    });

    // filter checkDataList to selected and unselected rows
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_roa_date_from', this.datePipe.transform(params.get('p_roa_date_from') as string))
      .set('p_roa_date_to', this.datePipe.transform(params.get('p_roa_date_to') as string));
  }

  // -------- role text below --------
  getRoleInfo(id: string): void {
    this.adminService.getRoleRecord(id).subscribe((role) => {
      const roleInfo = role;
      this.selected_role = roleInfo.rol_name;
      this.rol_id = `${roleInfo.rol_id}`;
    });
  }

  saveRecWithConfirmation(): void {
    // show info if user didn't make any changes
    if (this.selectedRowsArray.length === 0 && this.unselectedRowsArray.length === 0) {
      this.checkDataList = [];
      this.commonFormServices.translate
        .get('pages.sprRoleActions.noFormsChangesMade')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showInfo('', translation);
        });
      return;
    }
    this.confirmationService.confirm({
      message: this.commonFormServices.translate.instant('common.message.saveConfirmationMsg') as string,
      accept: () => {
        this.save();
      },
    });
  }

  save(): void {
    if (this.isCreateOrDeleteGranted()) {
      const roles = [];
      for (const role of this.checkDataList) {
        roles.push({
          roa_id: role.roa_id,
          roa_rol_id: role.roa_rol_id,
          rol_id: role.rol_id,
          roa_assigned_rol_id: role.roa_assigned_rol_id,
          update: role.isSelected,
        });
      }
      this.adminService.setRolesData(roles).subscribe(() => {
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
        .get('pages.sprRoleActionsRole.userIsNotGrantedToCreate')
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
        .get('pages.sprRoleActionsRole.userIsNotGrantedToDelete')
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

  //CheckActions==
  editRoleActions(row: RoleActionsRoleDetails): void {
    this.form.reset();
    this.actionDialog = true;
    this.roa_id = row.roa_id;
    this.form.controls.rol_id.setValue(row.rol_id);
    this.form.controls.rol_name.setValue(row.rol_name);
    this.form.controls.rol_code.setValue(row.rol_code);
    this.form.controls.rol_type.setValue(row.rol_type);
    this.form.controls.rol_description.setValue(row.rol_description);
    this.form.controls.roa_date_from.setValue(row?.roa_date_from ? new Date(row.roa_date_from) : null);
    this.form.controls.roa_date_to.setValue(row?.roa_date_to ? new Date(row.roa_date_to) : null);
  }

  hideDialog(): void {
    this.actionDialog = false;
    this.wasInteractedWith = [];
    this.minDate = this.today;
  }

  checkFieldsDate(): boolean {
    for (const field in this.form.controls) {
      if (field === 'roa_date_to' || field === 'roa_date_from') {
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
      this.confirmationService.confirm({
        message: this.commonFormServices.translate.instant('common.message.discardChanges') as string,
        accept: () => {
          this.hideDialog();
          this.reload();
        },
      });
    }
  }

  getActions(application: RoleActionsRoleDetails): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...application.availableActions];
    actions
      .filter((action) =>
        this.userCanUpdate ? action === ActionsEnum.ACTIONS_UPDATE : action === ActionsEnum.ACTIONS_READ
      )
      .forEach((element) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + element.toLowerCase()) as string,
          icon: getActionIcons(element as ActionsEnum),
          command: (): void => {
            this.editRoleActions(application);
          },
        });
      });
    return menu;
  }

  updateDateRestriction(): void {
    if (this.form.controls.roa_date_from.value === null) {
      this.minDate = this.today;
    } else {
      this.minDate = this.form.controls.roa_date_from.value as Date;
    }
  }

  saveDate(roa_id: string): void {
    if (this.form.controls.roa_date_from.valid) {
      if (roa_id === '') {
        const role = [
          {
            roa_id: roa_id,
            roa_rol_id: this.rol_id,
            roa_assigned_rol_id: this.form.controls.rol_id.value as string,
            rol_id: this.form.controls.rol_id.value as string,
            update: true,
          },
        ];
        this.adminService.setRolesData(role).subscribe(() => {
          this.adminService
            .getRoleActionsRole(this.form.controls.rol_id.value as string, this.rol_id)
            .pipe(
              tap((result) => {
                this.roa_id = result.data[0].roa_id;
              })
            )
            .subscribe(() => {
              this.adminService
                .setRolesActionDate(
                  this.roa_id,
                  this.form.controls.roa_date_to.value as Date,
                  this.form.controls.roa_date_from.value as Date
                )
                .subscribe(() => {
                  this.commonFormServices.appMessages.showSuccess(
                    '',
                    this.commonFormServices.translate.instant('common.message.saveSuccess') as string
                  );
                  this.reload();
                  this.actionDialog = false;
                });
            });
        });
      } else {
        this.adminService
          .setRolesActionDate(
            roa_id,
            this.form.controls.roa_date_to.value as Date,
            this.form.controls.roa_date_from.value as Date
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
      this.form.controls.roa_date_from.markAsDirty();
    }
  }
}
