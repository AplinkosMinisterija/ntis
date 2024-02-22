import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { ConfirmationService, MenuItem } from 'primeng/api';
import {
  DATA_TYPE_BOOLEAN,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { BrowseFormSearchResult, RoleRequest } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AdminService } from '../../../admin-services/admin.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { Subject, takeUntil, tap } from 'rxjs';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { SPR_ROLE_ACTIONS_LIST } from '@itree-commons/src/constants/forms.constants';
import { Table } from 'primeng/table';
import { RoleFormActions, RoleFormActionsSelected, SprRoleActions } from '../../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

interface RoleActionsDisabledForm {
  roa_id: string;
  frm_id: number;
}

interface RoleActionsEnabledForm extends RoleActionsDisabledForm {
  disabled_actions: string[];
}

export interface RoleActionsMultiAnswer {
  rol_id: string;
  enabled_forms: RoleActionsEnabledForm[];
  disabled_forms: RoleActionsDisabledForm[];
}

export interface RoleActionsSetFormRow extends SprRoleActions {
  disabled_actions: string[];
  enabled_actions: string[];
}

export interface RoleActionsSingleAnswer {
  rol_id: string;
  form: RoleActionsSetFormRow;
  availableActionsCnt: number;
}

@Component({
  selector: 'app-role-actions-page',
  templateUrl: './role-actions-page.component.html',
  styleUrls: ['./role-actions-page.component.scss'],
})
export class RoleActionsPageComponent extends BaseBrowseForm<SprRoleActions> implements OnInit, OnDestroy {
  readonly translationsReference = 'pages.sprRoleActions';
  readonly formCode = SPR_ROLE_ACTIONS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  destroy$: Subject<boolean> = new Subject();
  minDate: Date = new Date();
  rol_id: string;
  roa_id: string;
  frm_id: number;
  selected_role: string;
  actionDialog: boolean = false;
  parentActionSelector: boolean = false;
  parentFormSelector: boolean = false;
  form: FormGroup;
  exportData: SprRoleActions[] = [];
  defaultFormOptions: SprRoleActions[] = [];
  selectedForm: SprRoleActions;
  tempSelectedForm: SprRoleActions;
  // table rows checkboxes data arrays
  checkedFormsArray: SprRoleActions[] = [];
  uncheckedFormsArray: SprRoleActions[] = [];
  assignedFormsFiltered: SprRoleActions[] = [];
  revokedFormsFiltered: SprRoleActions[] = [];
  tempSelectionArray: SprRoleActions[] = [];
  // dialog checkboxes data arrays
  roleFormActionsData: RoleFormActions[] = [];
  availableActionsData: RoleFormActionsSelected[] = [];
  checkRevokedActions: RoleFormActionsSelected[] = [];
  wasInteractedWith: RoleFormActionsSelected[] = [];
  // primeNgTabMenu
  tabMenu: MenuItem[];
  activeItem: MenuItem;
  // routings
  sparkRoleActionsRole = RoutingConst.SPARK_ROLE_ACTIONS_ROLE;
  // user granted actions
  rowMenuItems: Record<string, MenuItem[]> = {};
  userCanCreate: boolean;
  userCanRead: boolean;
  userCanUpdate: boolean;
  userCanDelete: boolean;
  userCanSetFormActions: boolean;
  // columns
  cols: TableColumn[] = [
    { field: 'belongs', export: false, visible: false, type: DATA_TYPE_BOOLEAN },
    { field: 'frm_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'frm_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'frm_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'roa_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'roa_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
  ];
  visibleColumns: TableColumn[] = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    public router: Router,
    private adminService: AdminService,
    private confirmationService: ConfirmationService,
    private activatedRoute: ActivatedRoute,
    private datePipe: S2DatePipe,
    public faIconsService: FaIconsService,
    private translateService: TranslateService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.userCanRead = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_READ);
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
    this.userCanDelete = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_DELETE);
    this.userCanSetFormActions = this.authService.isFormActionEnabled(
      this.formCode,
      ActionsEnum.SET_FORM_DISABLED_ACTIONS
    );
    this.useExtendedSearchParams = true;
    this.form = new FormGroup({
      frm_id: new FormControl({ disabled: true }),
      frm_name: new FormControl({ disabled: true }),
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
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.searchForm.addControl('frm_id', new FormControl(''));
    this.searchForm.addControl('frm_code', new FormControl(''));
    this.searchForm.addControl('frm_name', new FormControl(''));
    this.searchForm.addControl('roa_date_from', new FormControl(''));
    this.searchForm.addControl('roa_date_to', new FormControl(''));
    this.tabMenu = [
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleActions.Forms_primeng') as string,
        icon: 'pi pi-fw pi-table',
        command: (): void => {
          this.router.url;
          return null;
        },
      },
      {
        label: this.commonFormServices.translate.instant('pages.sprRoleActions.Roles_primeng') as string,
        icon: 'pi pi-fw pi-user',
        command: (): void => {
          void this.router.navigate([
            '/',
            RoutingConst.INTERNAL,
            RoutingConst.ADMIN,
            RoutingConst.SPARK_ROLES_BROWSE,
            this.sparkRoleActionsRole,
            this.activatedRoute.snapshot.paramMap.get('id'),
          ]);
          return null;
        },
      },
    ];
    if (!this.activeItem && this.tabMenu && this.tabMenu.length) {
      this.activeItem = this.tabMenu[0];
    }
    if (this.activatedRoute) {
      this.activatedRoute.params.subscribe((params: Params) => {
        this.rol_id = typeof params.id === 'string' ? params.id : null;
        this.getRoleInfo(this.rol_id);
      });
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
    this.adminService
      .getRoleForms(pagingParams, this.fixDate(params), this.rol_id, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.frm_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprRoleActions>) => {
        this.data = tableData.data;
        this.exportData = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        for (const item of this.data) {
          item.select = item.belongs === DB_BOOLEAN_TRUE;
          if (this.tempSelectionArray.length > 0) {
            for (const temp of this.tempSelectionArray) {
              if (item.frm_id === temp.frm_id) {
                item.select = temp.select;
                item.belongs = item.select ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE;
              }
            }
          }
        }
      });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getRoleForms(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.fixDate(this.getSearchParamMap(dataTable)),
          this.rol_id,
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprRoleActions>) => {
          this.exportData = response.data;
        });
    }
  }

  redirectToRolesList(): void {
    void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_ROLES_BROWSE]);
  }

  // -------- FORMS/TABLE METHODS --------
  // Forms checkbox event handling
  onPageChange(): void {
    this.parentFormSelector = false;
  }

  onChangeForms(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    const frm_id = Number(eventTarget.value);
    const isChecked = eventTarget.checked;
    this.data.forEach((d) => {
      if (d.frm_id === frm_id) {
        const valBefore: boolean = d.select;
        d.select = isChecked;
        const valAfter: boolean = d.select;

        if (valBefore === false && valAfter === true) {
          if (d.roa_id === '' || d.roa_id === null) {
            if (this.checkedFormsArray.indexOf(d) === -1) {
              this.checkedFormsArray.push(d);
              this.tempSelectionArray.push(d);
            }
          }

          // IF FORM ORIGINALLY ASSIGNED, BUT WAS UNCHECKED BY USER AND THEN CHECKED AGAIN,
          // REMOVE IT FROM uncheckedFormsArray
          if (d.roa_id !== '' && d.roa_id !== null) {
            this.uncheckedFormsArray = this.uncheckedFormsArray.filter((item) => item.frm_id !== d.frm_id);
            this.tempSelectionArray = this.tempSelectionArray.filter((item) => item.frm_id !== d.frm_id);
          }
        }

        if (valBefore === true && valAfter === false) {
          //  ONLY PUSH ITEMS THAT HAVE BEEN ASSIGNED BEFORE AND HAVE ROA_ID
          if (d.roa_id !== '' && d.roa_id !== null) {
            if (this.uncheckedFormsArray.indexOf(d) === -1) {
              this.uncheckedFormsArray.push(d);
              this.tempSelectionArray.push(d);
            }
          }

          if (d.roa_id === '' || d.roa_id === null) {
            this.checkedFormsArray = this.checkedFormsArray.filter((item) => item.frm_id !== d.frm_id);
            this.tempSelectionArray = this.tempSelectionArray.filter((item) => item.frm_id !== d.frm_id);
          }
        }
        return d;
      }

      if (frm_id === -1) {
        const valBefore: boolean = d.select;
        d.select = this.parentFormSelector;
        const valAfter: boolean = d.select;

        if (valBefore === false && valAfter === true) {
          if (d.roa_id === '' || d.roa_id === null) {
            if (this.checkedFormsArray.indexOf(d) === -1) {
              this.checkedFormsArray.push(d);
              this.tempSelectionArray.push(d);
            }
          }
          if (d.roa_id !== '' && d.roa_id !== null) {
            this.uncheckedFormsArray = this.uncheckedFormsArray.filter((item) => item.frm_id !== d.frm_id);
            this.tempSelectionArray = this.tempSelectionArray.filter((item) => item.frm_id !== d.frm_id);
          }
        }

        if (valBefore === true && valAfter === false) {
          if (d.roa_id !== '' && d.roa_id !== null) {
            if (this.uncheckedFormsArray.indexOf(d) === -1) {
              this.uncheckedFormsArray.push(d);
              this.tempSelectionArray.push(d);
            }
            if (this.checkedFormsArray.indexOf(d) !== -1) {
              this.checkedFormsArray = this.checkedFormsArray.filter((item) => item.frm_id !== d.frm_id);
            }
          }
          if (d.roa_id === '' || d.roa_id === null) {
            this.checkedFormsArray = this.checkedFormsArray.filter((item) => item.frm_id !== d.frm_id);
            this.tempSelectionArray = this.tempSelectionArray.filter((item) => item.frm_id !== d.frm_id);
          }
        }
        return d;
      }
      return d;
    });
    this.assignedFormsFiltered = this.checkedFormsArray.filter((item) => !(item.select === false));
    this.revokedFormsFiltered = this.uncheckedFormsArray.filter((item) => !(item.select === true));
  }

  getMultiRowsAnswerObject(): RoleActionsMultiAnswer {
    const revokedFormsObjectsArr: RoleActionsDisabledForm[] = [];
    for (let i = 0; i < this.revokedFormsFiltered.length; i++) {
      revokedFormsObjectsArr.push({
        roa_id: this.revokedFormsFiltered[i].roa_id,
        frm_id: this.revokedFormsFiltered[i].frm_id,
      });
    }
    const assignedFormsArr: RoleActionsEnabledForm[] = [];
    for (let i = 0; i < this.assignedFormsFiltered.length; i++) {
      assignedFormsArr.push({
        roa_id:
          this.assignedFormsFiltered[i].roa_id !== ''
            ? this.assignedFormsFiltered[i].roa_id
            : (this.assignedFormsFiltered[i].roa_id = null),
        frm_id: this.assignedFormsFiltered[i].frm_id,
        disabled_actions: [],
      });
    }
    const multiAnswerObj: RoleActionsMultiAnswer = {
      rol_id: this.rol_id,
      enabled_forms: [...assignedFormsArr],
      disabled_forms: [...revokedFormsObjectsArr],
    };
    return multiAnswerObj;
  }

  onSaveSelectedForms(): void {
    const assignedFormsCnt: number = this.assignedFormsFiltered.length;
    const revokedFormsCnt: number = this.revokedFormsFiltered.length;

    if (
      assignedFormsCnt === 0 &&
      revokedFormsCnt === 0 &&
      this.selectedForm?.roa_id === this.tempSelectedForm?.roa_id
    ) {
      this.translateService.get('pages.sprRoleActions.noFormsChangesMade').subscribe((txt: string) => {
        this.commonFormServices.appMessages.showInfo('', txt);
      });
    } else if (
      assignedFormsCnt > 0 ||
      revokedFormsCnt > 0 ||
      this.selectedForm?.roa_id !== this.tempSelectedForm?.roa_id
    ) {
      if (this.userCanCreate && this.userCanDelete) {
        this.doSaveSelectedForms();
      } else if (this.userCanCreate && !this.userCanDelete) {
        if (assignedFormsCnt > 0 && revokedFormsCnt === 0) {
          this.doSaveSelectedForms();
        } else if (revokedFormsCnt !== 0) {
          this.clearAllArrays();
          this.reload();
          this.translateService.get('pages.sprRoleActions.userIsNotGrantedToDelete').subscribe((txt: string) => {
            this.commonFormServices.appMessages.showError('', txt);
          });
        }
      } else if (!this.userCanCreate && this.userCanDelete) {
        if (revokedFormsCnt > 0 && assignedFormsCnt === 0) {
          this.doSaveSelectedForms();
        } else if (assignedFormsCnt !== 0) {
          this.clearAllArrays();
          this.reload();
          this.translateService.get('pages.sprRoleActions.userIsNotGrantedToCreate').subscribe((txt: string) => {
            this.commonFormServices.appMessages.showError('', txt);
          });
        }
      }
    }
  }

  doSaveSelectedForms(): void {
    this.confirmationService.confirm({
      message: this.commonFormServices.translate.instant('common.message.saveConfirmationMsg') as string,
      accept: () => {
        this.adminService.setRoleFormsRecord(this.getMultiRowsAnswerObject()).subscribe(() => {
          if (this.tempSelectedForm?.roa_id !== this.selectedForm?.roa_id) {
            const defaultForm = {
              update: null,
              roa_assigned_rol_id: null,
              roa_id: this.tempSelectedForm?.roa_id,
              roa_rol_id: this.rol_id,
            } as RoleRequest;
            this.adminService.setDefaultRoleForm(defaultForm).subscribe(() => {
              this.onSaveSuccess();
            });
          } else {
            this.onSaveSuccess();
          }
        });
      },
    });
  }

  onSaveSuccess(): void {
    this.translateService.get('common.message.saveSuccess').subscribe((txt: string) => {
      this.commonFormServices.appMessages.showSuccess('', txt);
    });
    this.clearAllArrays();
    this.reload();
  }

  clearAllArrays(): void {
    this.parentFormSelector = false;
    this.checkedFormsArray = [];
    this.uncheckedFormsArray = [];
    this.assignedFormsFiltered = [];
    this.revokedFormsFiltered = [];
    this.tempSelectionArray = [];
  }

  // -------- ACTIONS/DIALOG METHODS --------
  // All possible actions data of a specific form
  getFormActionsData(id: number): void {
    this.adminService.getFormActions(id.toString()).subscribe((tableData) => {
      this.availableActionsData = tableData.data;
      if (this.roleFormActionsData) {
        for (const formAction of this.availableActionsData) {
          for (const userAction of this.roleFormActionsData) {
            if (userAction.fra_id === formAction.fra_id) {
              // CHECK IF ACTION IS DISABLED
              formAction['select'] = userAction.rda_fra_id === null || userAction.rda_fra_id === '';
            }
          }
        }
      }
    });
  }

  // Granted form actions data
  getRoleFormActionsData(roa_id: string, frm_id: number): void {
    this.adminService.getRoleActionsRecord(roa_id, frm_id.toString()).subscribe((tableData) => {
      this.roleFormActionsData = tableData.data;
      this.getFormActionsData(frm_id);
    });
  }

  // Edit role actions in dialog window
  editRoleFormActions(row: SprRoleActions): void {
    this.form.reset();
    this.actionDialog = true;
    this.frm_id = row.frm_id;
    this.form.controls.frm_id.setValue(row.frm_id);
    this.form.controls.frm_name.setValue(row.frm_name);
    this.form.controls.roa_date_from.setValue(row?.roa_date_from ? new Date(row.roa_date_from) : null);
    this.form.controls.roa_date_to.setValue(row?.roa_date_to ? new Date(row.roa_date_to) : null);
    if (row.roa_id !== '') {
      this.getRoleFormActionsData(row.roa_id, row.frm_id);
      this.roa_id = row.roa_id;
    }
    if (row.roa_id === '') {
      this.getFormActionsData(row.frm_id);
      this.roleFormActionsData = null;
      this.roa_id = null;
    }
  }

  protected getData(id: number): SprRoleActions {
    for (const item of this.data) {
      if (item.frm_id === id) {
        item.roa_date_from = this.form.controls.roa_date_from.value as Date;
        item.roa_date_to = this.form.controls.roa_date_to.value as Date;
        return item;
      }
    }
    return null;
  }

  // Dialog checkbox event handling
  onChangeActions(event: Event): void {
    const eventTarget = event.target as HTMLInputElement;
    const fra_id = eventTarget.value;
    const isChecked = eventTarget.checked;
    this.availableActionsData.map((d) => {
      if (d.fra_id.toString() === fra_id) {
        const valBefore = d.select;
        d.select = isChecked;
        const valAfter = d.select;
        if (valBefore !== valAfter) {
          if (this.wasInteractedWith.indexOf(d) === -1) {
            this.wasInteractedWith.push(d);
          }
        }
        return d;
      }
      if (fra_id === '-1') {
        const valBefore = d.select;
        d.select = this.parentActionSelector;
        const valAfter = d.select;

        if (valBefore !== valAfter) {
          if (this.wasInteractedWith.indexOf(d) === -1) {
            this.wasInteractedWith.push(d);
          }
        }
        return d;
      }
      return d;
    });
    this.checkRevokedActions = this.availableActionsData.filter((item) => !item.select);
  }

  getSingleFormAnswerObject(): RoleActionsSingleAnswer {
    const arrOfDisabledIds: string[] = [];
    const arrOfEnabledIds: string[] = [];
    if (this.availableActionsData.length > 0) {
      // IF FORM ALREADY ASSIGNED
      if (this.roa_id !== null || this.roa_id !== '') {
        for (let i = 0; i < this.availableActionsData.length; i++) {
          if (this.availableActionsData[i].select === true) {
            if (arrOfEnabledIds.indexOf(this.availableActionsData[i].fra_id) === -1) {
              arrOfEnabledIds.push(this.availableActionsData[i].fra_id);
            }
          } else if (this.availableActionsData[i].select === false) {
            if (arrOfDisabledIds.indexOf(this.availableActionsData[i].fra_id) === -1) {
              arrOfDisabledIds.push(this.availableActionsData[i].fra_id);
            }
          }
        }
        if (this.checkRevokedActions.length === this.availableActionsData.length) {
          for (const item of this.data) {
            if (item.roa_id === this.roa_id) {
              // IF FORM ASSIGNED AND ALL ACTIONS WERE DISABLED, UNCHECK CHECKBOX
              item.select = false;
            }
          }
        }
      }
      // IF FORM UNSASSIGNED YET
      if (this.roa_id === '' || this.roa_id === null) {
        if (this.wasInteractedWith.length > 0) {
          for (let i = 0; i < this.availableActionsData.length; i++) {
            if (this.availableActionsData[i].select === true) {
              if (arrOfEnabledIds.indexOf(this.availableActionsData[i].fra_id) === -1) {
                arrOfEnabledIds.push(this.availableActionsData[i].fra_id);
              }
            } else if (this.availableActionsData[i].select === false || !this.availableActionsData[i].select) {
              if (arrOfDisabledIds.indexOf(this.availableActionsData[i].fra_id) === -1) {
                arrOfDisabledIds.push(this.availableActionsData[i].fra_id);
              }
            }
          }
        }
      }
    }
    const singleAnswerObj: RoleActionsSingleAnswer = {
      rol_id: this.rol_id,
      form: {
        ...this.getData(this.frm_id),
        disabled_actions: [...arrOfDisabledIds],
        enabled_actions: [...arrOfEnabledIds],
      },
      availableActionsCnt: this.availableActionsData.length,
    };
    return singleAnswerObj;
  }

  hideDialog(): void {
    this.actionDialog = false;
    this.parentActionSelector = false;
    this.checkRevokedActions = [];
    this.wasInteractedWith = [];
  }

  checkFields(): boolean {
    for (const field in this.form.controls) {
      if (field === 'roa_date_from' || field === 'roa_date_to') {
        if (this.form.get(field).dirty === true) {
          return true;
        }
      }
    }
    return false;
  }

  actionChangesWereMade(): boolean {
    let differences: boolean;
    if (this.wasInteractedWith.length > 0) {
      // IF FORM UNASSIGNED
      if (!this.roleFormActionsData) {
        differences = this.availableActionsData.length !== this.checkRevokedActions.length;
      }
      // IF FORM ASSIGNED
      if (this.roleFormActionsData) {
        const originalDisabledActionValues = this.roleFormActionsData
          .filter((item) => item.rda_fra_id)
          .map(function (item) {
            return item.fra_id;
          });
        const afterInteractionDisabledValues = this.checkRevokedActions
          .filter((item) => item.fra_id)
          .map(function (item) {
            return item.fra_id;
          });
        // CHECK FOR DIFFERENCES BETWEEN ORIGINAL AND CHECKED VALUES
        const diff = originalDisabledActionValues
          .filter((action) => !afterInteractionDisabledValues.includes(action))
          .concat(afterInteractionDisabledValues.filter((action) => !originalDisabledActionValues.includes(action)));
        differences = diff.length > 0;
      }
    }
    return differences;
  }

  onSaveFormActions(): void {
    const noInteraction: boolean = this.wasInteractedWith.length === 0;
    if (this.userCanSetFormActions || this.userCanUpdate) {
      if (!this.form.invalid) {
        if (!this.checkFields() && noInteraction) {
          this.translateService.get('pages.sprRoleActions.noDialogChangesMade').subscribe((txt: string) => {
            this.commonFormServices.appMessages.showInfo('', txt);
          });
        } else if (this.roa_id === null || this.roa_id === '') {
          if (noInteraction || this.checkRevokedActions.length === this.availableActionsData.length) {
            this.translateService.get('pages.sprRoleActions.noActionsSelected').subscribe((txt: string) => {
              this.commonFormServices.appMessages.showWarning('', txt);
            });
          } else if (
            this.checkRevokedActions.length !== this.availableActionsData.length &&
            this.wasInteractedWith.length > 0 &&
            this.form.controls.roa_date_from.value !== null
          ) {
            this.confirmationService.confirm({
              message: this.commonFormServices.translate.instant(
                'pages.sprRoleActions.assignFormThroughActions'
              ) as string,
              accept: () => {
                this.doSaveFormActions();
              },
            });
          }
        } else if (this.roa_id !== null && this.roa_id !== '') {
          if (this.checkRevokedActions.length === this.availableActionsData.length) {
            this.confirmationService.confirm({
              message: this.commonFormServices.translate.instant('pages.sprRoleActions.allActionsRemoved') as string,
              accept: () => {
                this.doSaveFormActions();
              },
            });
          } else if (
            (this.checkFields() || this.wasInteractedWith.length > 0) &&
            this.form.controls.roa_date_from.value !== null
          ) {
            this.doSaveFormActions();
          }
        }
      } else {
        this.form.controls.roa_date_from.markAsDirty();
      }
    }
  }

  doSaveFormActions(): void {
    this.commonFormServices.appMessages.clearMessages();
    this.adminService.setRoleFormActionsRecord(this.getSingleFormAnswerObject()).subscribe(() => {
      this.reload();
      this.hideDialog();
      this.translateService.get('common.message.saveSuccess').subscribe((txt: string) => {
        this.commonFormServices.appMessages.showSuccess('', txt);
      });
    });
  }

  onCancel(): void {
    let emptyField: boolean = true;
    const form = this.form.controls;
    for (const input in form) {
      if (input !== 'frm_id' && input !== 'frm_name') {
        if (
          (!this.checkFields() && !this.actionChangesWereMade()) ||
          ((form[input].value === null || form[input].value === '') && !this.actionChangesWereMade())
        ) {
          continue;
        } else {
          emptyField = !emptyField;
          break;
        }
      }
    }
    !emptyField
      ? this.confirmationService.confirm({
          message: this.commonFormServices.translate.instant('common.message.cancelConfirmation') as string,
          accept: () => {
            this.commonFormServices.appMessages.clearMessages();
            this.hideDialog();
            this.reload();
          },
        })
      : this.hideDialog();
  }

  // -------- GENERAL METHODS --------
  getRoleInfo(id: string): void {
    this.adminService.getRoleRecord(id).subscribe((role) => {
      const roleInfo = role;
      this.selected_role = roleInfo.rol_name;

      this.adminService.getRoleActions(this.rol_id).subscribe((res) => {
        this.defaultFormOptions = res.data;
        this.selectedForm = this.defaultFormOptions.filter((item) => item.roa_default_menu_item === DB_BOOLEAN_TRUE)[0];
        this.tempSelectedForm = this.selectedForm;
      });
    });
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_roa_date_from', this.datePipe.transform(params.get('p_roa_date_from') as string))
      .set('p_roa_date_to', this.datePipe.transform(params.get('p_roa_date_to') as string));
  }

  getActions(application: SprRoleActions): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...application.availableActions];
    actions
      .filter(
        (action) =>
          action === ActionsEnum.SET_FORM_DISABLED_ACTIONS ||
          (action === ActionsEnum.ACTIONS_READ && !this.userCanSetFormActions)
      )
      .forEach((element) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + element.toLowerCase()) as string,
          icon: getActionIcons(element as ActionsEnum),
          command: (): void => {
            this.editRoleFormActions(application);
          },
        });
      });
    return menu;
  }
}
