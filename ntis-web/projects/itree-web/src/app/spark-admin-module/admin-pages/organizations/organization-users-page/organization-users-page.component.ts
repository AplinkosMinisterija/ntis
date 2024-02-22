import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_ORG_USERS_LIST } from '@itree-commons/src/constants/forms.constants';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult, OrgUserRequest, SprOrganizationsDAO } from '@itree-commons/src/lib/model/api/api';
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
import { SprOrgUsersListRow } from '../../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-organization-users-page',
  templateUrl: './organization-users-page.component.html',
  styleUrls: ['./organization-users-page.component.scss'],
})
export class OrganizationUsersPageComponent extends BaseBrowseForm<SprOrgUsersListRow> implements OnInit, OnDestroy {
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'pages.sprOrgUsersList';
  readonly formCode = SPR_ORG_USERS_LIST;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  destroy$: Subject<boolean> = new Subject();
  selected_org: string;
  selected_org_id: string;
  checked: boolean = false;
  form: FormGroup;
  actionDialog: boolean;
  dialogHeader: string;
  ou_id: string;
  minDate: Date;
  today: Date = new Date();
  joinOrgUsr: SprOrgUsersListRow[] = [];
  checkDataList: SprOrgUsersListRow[] = [];
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprOrgUsersListRow[] = [];
  // data arrays created on checkbox change
  selectedRowsArray: SprOrgUsersListRow[] = [];
  unselectedRowsArray: SprOrgUsersListRow[] = [];
  allChangedRowsArray: SprOrgUsersListRow[] = [];
  // user granted actions
  userCanCreate: boolean;
  userCanDelete: boolean;
  userCanUpdate: boolean;
  // Column array
  cols: TableColumn[] = [
    { field: 'usr_id', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_username', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_email', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_person_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'usr_person_surname', export: true, visible: true, type: DATA_TYPE_STRING },
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
      usr_id: new FormControl({ value: null, disabled: true }),
      usr_username: new FormControl({ value: null, disabled: true }),
      usr_email: new FormControl({ value: null, disabled: true }),
      usr_person_name: new FormControl({ value: null, disabled: true }),
      usr_person_surname: new FormControl({ value: null, disabled: true }),
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
      this.updateDateRestriction();
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
    this.searchForm.addControl('usr_id', new FormControl(''));
    this.searchForm.addControl('usr_username', new FormControl(''));
    this.searchForm.addControl('usr_email', new FormControl(''));
    this.searchForm.addControl('usr_person_name', new FormControl(''));
    this.searchForm.addControl('usr_person_surname', new FormControl(''));
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
        this.getOrgUsers(par.id as string, pagingParams, this.fixDate(params), extendedParams);
        this.getSelectedOrg(par.id as string);
        this.findIndexById(par.id as string);
        this.checked = false;
      });
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  getOrgUsers(
    id: string,
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.adminService
      .getOrgUsersList(id, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row) => {
            this.rowMenuItems[row.usr_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprOrgUsersListRow>) => {
        this.joinOrgUsr = tableData.data;
        this.totalRecords = tableData.paging.cnt;
        this.exportData = tableData.data;
        if (this.allChangedRowsArray.length > 0) {
          this.joinOrgUsr.forEach((row) => {
            this.allChangedRowsArray.forEach((changedRow) => {
              if (row.usr_id === changedRow.usr_id) {
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
        .getOrgUsersList(
          this.selected_org_id.toString(),
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response: BrowseFormSearchResult<SprOrgUsersListRow>) => {
          this.exportData = response.data;
        });
    }
  }

  redirectToOrgsBrowse(): void {
    void this.commonFormServices.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.ADMIN,
      RoutingConst.SPARK_ORGANIZATIONS_BROWSE,
    ]);
  }

  onCheckboxesChange(isChecked: boolean): void {
    this.checkDataList = [];

    for (const data of this.joinOrgUsr) {
      data.isSelected = isChecked;
      this.checkDataList.push(data);
      this.allChangedRowsArray.push(data);
    }
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  onCheckboxChange($event: Event, data: SprOrgUsersListRow): void {
    const eventTarget = $event.target as HTMLInputElement;
    data.isSelected = eventTarget.checked;

    if (this.selectedRowsArray.length > 0 && !data.isSelected) {
      this.selectedRowsArray = this.selectedRowsArray.filter((row) => row.usr_id !== data.usr_id);
    }
    if (this.unselectedRowsArray.length > 0 && data.isSelected) {
      this.unselectedRowsArray = this.unselectedRowsArray.filter((row) => row.usr_id !== data.usr_id);
    }
    if (this.checkDataList.length > 0) {
      this.checkDataList = this.checkDataList.filter((row) => row.usr_id !== data.usr_id);
    }

    this.checkDataList.push(data);
    this.allChangedRowsArray.push(data);

    // filter checkDataList to selected and unselected rows
    this.selectedRowsArray = this.checkDataList.filter((row) => row.isSelected);
    this.unselectedRowsArray = this.checkDataList.filter((row) => !row.isSelected);
  }

  getSelectedOrg(id: string, actionType?: string): void {
    this.adminService.getOrganizationRecord(id, actionType).subscribe((tableData: SprOrganizationsDAO) => {
      const orgInfo = tableData;
      this.selected_org = orgInfo.org_name;
      this.selected_org_id = orgInfo.org_id.toString();
    });
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_ou_date_from', this.datePipe.transform(params.get('p_ou_date_from') as string))
      .set('p_ou_date_to', this.datePipe.transform(params.get('p_ou_date_to') as string));
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.joinOrgUsr.length; i++) {
      if (this.joinOrgUsr[i].usr_id === id) {
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
      this.checkDataList.map((row) => {
        row.update = row.isSelected;
        row.ou_org_id = this.selected_org_id;
      });
      const orgUserRequests = this.rowsToOrgUserRequest(this.checkDataList);
      this.adminService.setOrgUsersData(orgUserRequests).subscribe(() => {
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
      const user: OrgUserRequest = {
        ou_id: ou_id === 'null' ? null : ou_id,
        usr_id: this.form.controls.usr_id.value as string,
        ou_org_id: this.selected_org_id,
        ou_position: null,
        ou_date_from: this.form.controls.ou_date_from.value as Date,
        ou_date_to: this.form.controls.ou_date_to.value as Date,
        isSelected: true,
        org_id: null,
        ou_usr_id: null,
      };
      if (!user.ou_id) {
        this.adminService.setOrgUsersData([user]).subscribe(() => {
          this.reload();
          this.hideDialog();
          this.commonFormServices.appMessages.showSuccess(
            '',
            this.commonFormServices.translate.instant('common.message.saveSuccess') as string
          );
        });
      } else {
        this.adminService.setOrgUserDate(user).subscribe(() => {
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

  editOrgUsersDates(row: SprOrgUsersListRow): void {
    this.form.reset();
    this.actionDialog = true;
    this.ou_id = row.ou_id;
    this.form.controls.usr_id.setValue(row.usr_id);
    this.form.controls.usr_email.setValue(row.usr_email);
    this.form.controls.usr_username.setValue(row.usr_username);
    this.form.controls.usr_person_name.setValue(row.usr_person_name);
    this.form.controls.usr_person_surname.setValue(row.usr_person_surname);
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

  getActions(row: SprOrgUsersListRow): MenuItem[] {
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
            this.editOrgUsersDates(row);
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

  rowsToOrgUserRequest(rows: SprOrgUsersListRow[]): OrgUserRequest[] {
    return rows.map(this.rowToOrgUserRequest.bind(this));
  }

  rowToOrgUserRequest(row: SprOrgUsersListRow): OrgUserRequest {
    return {
      isSelected: row.isSelected,
      org_id: null,
      ou_date_from: row.ou_date_from,
      ou_date_to: row.ou_date_to,
      ou_id: row.ou_id === 'null' ? null : row.ou_id,
      ou_org_id: row.ou_org_id,
      ou_position: null,
      ou_usr_id: null,
      usr_id: row.usr_id,
    };
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.SPARK_ORGANIZATIONS_BROWSE],
      navigationExtras
    );
  }
}
