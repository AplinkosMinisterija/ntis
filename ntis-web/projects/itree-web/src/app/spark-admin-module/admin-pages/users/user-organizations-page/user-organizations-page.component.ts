import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_USER_ORGS_LIST } from '@itree-commons/src/constants/forms.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, OrgUserRequest, SprUsersDAO } from '@itree-commons/src/lib/model/api/api';
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
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { SprUserOrgsListRow } from '../../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-user-organizations-page',
  templateUrl: './user-organizations-page.component.html',
  styleUrls: ['./user-organizations-page.component.scss'],
})
export class UserOrganizationsPageComponent extends BaseBrowseForm<SprUserOrgsListRow> implements OnInit, OnDestroy {
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'pages.sprUserOrgsList';
  readonly formCode = SPR_USER_ORGS_LIST;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  destroy$: Subject<boolean> = new Subject();
  selected_user: string;
  selected_user_id: string;
  checked: boolean = false;
  form: FormGroup;
  actionDialog: boolean;
  dialogHeader: string;
  ou_id: string;
  minDate: Date;
  today: Date = new Date();
  joinUsrOrg: SprUserOrgsListRow[] = [];
  checkDataList: SprUserOrgsListRow[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprUserOrgsListRow[] = [];
  // data arrays created on checkbox change
  selectedRowsArray: SprUserOrgsListRow[] = [];
  unselectedRowsArray: SprUserOrgsListRow[] = [];
  allChangedRowsArray: SprUserOrgsListRow[] = [];
  // user granted actions
  userCanCreate: boolean;
  userCanDelete: boolean;
  userCanUpdate: boolean;
  // Column array
  cols: TableColumn[] = [
    { field: 'org_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_code', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_email', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'org_phone', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ou_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'ou_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
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
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.userCanDelete = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_DELETE);
    this.userCanUpdate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_UPDATE);
    this.form = new FormGroup({
      org_id: new FormControl({ value: null, disabled: true }),
      org_name: new FormControl({ value: null, disabled: true }),
      org_code: new FormControl({ value: null, disabled: true }),
      org_email: new FormControl({ value: null, disabled: true }),
      org_phone: new FormControl({ value: null, disabled: true }),
      ou_date_from: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(10), Validators.maxLength(10)])
      ),
      ou_date_to: new FormControl('', Validators.compose([Validators.minLength(10), Validators.maxLength(10)])),
    });
    this.form.controls.ou_date_from.addValidators([SprDateValidators.validateDateFrom(this.form.controls.ou_date_to)]);
    this.form.controls.ou_date_to.addValidators([SprDateValidators.validateDateTo(this.form.controls.ou_date_from)]);

    this.form.controls.ou_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.ou_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.ou_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.ou_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
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
    this.searchForm.addControl('org_id', new FormControl(''));
    this.searchForm.addControl('org_name', new FormControl(''));
    this.searchForm.addControl('org_code', new FormControl(''));
    this.searchForm.addControl('org_email', new FormControl(''));
    this.searchForm.addControl('org_phone', new FormControl(''));
    this.searchForm.addControl('ou_date_to', new FormControl(''));
    this.searchForm.addControl('ou_date_from', new FormControl(''));
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
        this.getUserOrgs(par.id as string, pagingParams, this.fixDate(params), extendedParams);
        this.getSelectedUser(par.id as string);
        this.findIndexById(par.id as string);
        this.checked = false;
      });
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  getUserOrgs(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getUserOrgsList(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.org_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprUserOrgsListRow>) => {
        this.joinUsrOrg = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        if (this.allChangedRowsArray.length > 0) {
          this.joinUsrOrg.map((row) => {
            this.allChangedRowsArray.forEach((changedRow) => {
              if (row.org_id === changedRow.org_id) {
                row.isSelected = changedRow.isSelected;
              }
            });
          });
        }
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.adminService
        .getUserOrgsList(
          this.selected_user_id.toString(),
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprUserOrgsListRow>) => {
          this.exportData = response.data;
        });
    }
  }

  redirectToUsersBrowse(): void {
    void this.commonFormServices.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.ADMIN,
      RoutingConst.SPARK_USERS_BROWSE,
    ]);
  }

  onCheckboxesChange(isChecked: boolean): void {
    this.checkDataList = [];

    for (const data of this.joinUsrOrg) {
      data.isSelected = isChecked;
      this.checkDataList.push(data);
      this.allChangedRowsArray.push(data);
    }
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: SprUserOrgsListRow): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;

    if (this.selectedRowsArray.length > 0 && !data.isSelected) {
      this.selectedRowsArray = this.selectedRowsArray.filter((row) => row.org_id !== data.org_id);
    }
    if (this.unselectedRowsArray.length > 0 && data.isSelected) {
      this.unselectedRowsArray = this.unselectedRowsArray.filter((row) => row.org_id !== data.org_id);
    }
    if (this.checkDataList.length > 0) {
      this.checkDataList = this.checkDataList.filter((row) => row.org_id !== data.org_id);
    }

    this.checkDataList.push(data);
    this.allChangedRowsArray.push(data);

    // filter checkDataList to selected and unselected rows
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  getSelectedUser(id: string, actionType?: string): void {
    this.adminService.getUserRecord(id, actionType).subscribe((tableData: SprUsersDAO) => {
      const userInfo = tableData;
      this.selected_user = userInfo.usr_username;
      this.selected_user_id = userInfo.usr_id.toString();
    });
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_ou_date_from', this.datePipe.transform(params.get('p_ou_date_from') as string))
      .set('p_ou_date_to', this.datePipe.transform(params.get('p_ou_date_to') as string));
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.joinUsrOrg.length; i++) {
      if (this.joinUsrOrg[i].org_id === id) {
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
        .get(this.formTranslationsReference + '.noChangesMade')
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
      this.checkDataList.forEach((row) => {
        row.update = row.isSelected;
        row.ou_usr_id = this.selected_user_id;
      });
      const orgUserRequest = this.rowsToOrgUserRequest(this.checkDataList);
      this.adminService.setUserOrgData(orgUserRequest).subscribe(() => {
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
        .get(this.formTranslationsReference + '.userIsNotGrantedToCreate')
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
        .get(this.formTranslationsReference + '.userIsNotGrantedToDelete')
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

  saveDate(ou_id: string): void {
    if (this.form.controls.ou_date_from.valid) {
      const org: OrgUserRequest = {
        ou_id: ou_id === 'null' ? null : ou_id,
        org_id: this.form.controls.org_id.value as string,
        ou_usr_id: this.selected_user_id,
        ou_position: null,
        ou_date_from: this.form.controls.ou_date_from.value as Date,
        ou_date_to: this.form.controls.ou_date_to.value as Date,
        isSelected: true,
        ou_org_id: null,
        usr_id: null,
      };
      if (!org.ou_id) {
        this.adminService.setUserOrgData([org]).subscribe(() => {
          this.reload();
          this.hideDialog();
          this.commonFormServices.appMessages.showSuccess(
            '',
            this.commonFormServices.translate.instant('common.message.saveSuccess') as string
          );
        });
      } else {
        this.adminService.setUserOrgDate(org).subscribe(() => {
          this.commonFormServices.appMessages.showSuccess(
            '',
            this.commonFormServices.translate.instant('common.message.saveSuccess') as string
          );
          this.reload();
          this.actionDialog = false;
        });
      }
    } else {
      this.form.controls.ou_date_from.markAsDirty();
    }
  }

  editUserOrgDates(row: SprUserOrgsListRow): void {
    this.form.reset();
    this.actionDialog = true;
    this.ou_id = row.ou_id;
    this.form.controls.org_id.setValue(row.org_id);
    this.form.controls.org_name.setValue(row.org_name);
    this.form.controls.org_code.setValue(row.org_code);
    this.form.controls.org_email.setValue(row.org_email);
    this.form.controls.org_phone.setValue(row.org_phone);
    this.form.controls.ou_date_from.setValue(row?.ou_date_from ? new Date(row.ou_date_from) : null);
    this.form.controls.ou_date_to.setValue(row?.ou_date_to ? new Date(row.ou_date_to) : null);
  }

  hideDialog(): void {
    this.actionDialog = false;
  }

  checkFieldsDate(): boolean {
    for (const field in this.form.controls) {
      if (field === 'ou_date_to' || field === 'ou_date_from') {
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

  getActions(row: SprUserOrgsListRow): MenuItem[] {
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
            this.editUserOrgDates(row);
          },
        });
      });
    return menu;
  }

  updateDateRestriction(): void {
    if (this.form.controls.ou_date_from.value === null) {
      this.minDate = this.today;
    } else {
      this.minDate = this.form.controls.ou_date_from.value as Date;
    }
  }

  rowsToOrgUserRequest(rows: SprUserOrgsListRow[]): OrgUserRequest[] {
    return rows.map(this.rowToOrgUserRequest.bind(this));
  }

  rowToOrgUserRequest(row: SprUserOrgsListRow): OrgUserRequest {
    return {
      isSelected: row.isSelected,
      org_id: row.org_id,
      ou_date_from: row.ou_date_from,
      ou_date_to: row.ou_date_to,
      ou_id: row.ou_id === 'null' ? null : row.ou_id,
      ou_org_id: null,
      ou_position: null,
      ou_usr_id: row.ou_usr_id,
      usr_id: this.selected_user_id,
    };
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_USERS_BROWSE],
      navigationExtras
    );
  }
}
