import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NTIS_WATER_MANAGER_FACILITIES_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AddressSearchResponse, NtisWaterManagerFacility } from '@itree-commons/src/lib/model/api/api';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Table, TableModule } from 'primeng/table';
import { map } from 'rxjs';
import { NtisTableRowActionsItem } from '../../../ntis-shared/models/table-row-actions';
import { TableColumn, WaterManagerFacilitiesBrowseRow } from '../../models/browse-pages';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { TooltipModule } from 'primeng/tooltip';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

interface isUsedOption {
  option: string;
  value: string;
}

@Component({
  selector: 'app-water-manager-facilities-list',
  templateUrl: './water-manager-facilities-list.component.html',
  styleUrls: ['./water-manager-facilities-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    TableModule,
    NtisSharedModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    TooltipModule,
  ],
})
export class WaterManagerFacilitiesListComponent
  extends BaseBrowseForm<WaterManagerFacilitiesBrowseRow>
  implements OnInit
{
  readonly formCode = NTIS_WATER_MANAGER_FACILITIES_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'institutionsAdmin.pages.ntisWaterManagerFacilitiesList';
  readonly trueDB = DB_BOOLEAN_TRUE;
  readonly falseDB = DB_BOOLEAN_FALSE;
  exportData: WaterManagerFacilitiesBrowseRow[] = [];
  actions: NtisTableRowActionsItem[];
  editDialog: boolean = false;
  checked: boolean = false;
  usedOptions: isUsedOption[] = [];
  facilitySearch: boolean = false;
  searchActive: boolean = true;
  addressId: number = null;
  userCanCreate: boolean;
  noNotifications: boolean = false;

  cols: TableColumn[] = [
    { field: 'wto_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'wto_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_address', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_productivity', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'wto_domestic_sewage', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_industrial_sewage', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_sludge', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_is_it_used', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'wto_auto_accept', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: InstitutionsAdminService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
  }

  ngOnInit(): void {
    this.searchForm.addControl('wto_name', new FormControl(''));
    this.searchForm.addControl('wto_address', new FormControl(''));
    this.searchForm.addControl('wto_productivity', new FormControl(''));
    this.searchForm.addControl('wto_domestic_sewage', new FormControl(''));
    this.searchForm.addControl('wto_industrial_sewage', new FormControl(''));
    this.searchForm.addControl('wto_sludge', new FormControl(''));
    this.searchForm.addControl('wto_is_it_used', new FormControl(''));
    this.searchForm.addControl('wto_auto_accept', new FormControl(''));

    this.commonFormServices.translate.get('common.action.yes').subscribe((translation: string) => {
      this.usedOptions.push({ option: translation, value: DB_BOOLEAN_TRUE });
    });
    this.commonFormServices.translate.get('common.action.no').subscribe((translation: string) => {
      this.usedOptions.push({ option: translation, value: DB_BOOLEAN_FALSE });
    });
  }

  form = new FormGroup({
    wto_id: new FormControl<number>(null),
    wto_name: new FormControl<string>(null, [Validators.required, Validators.maxLength(50)]),
    wto_address: new FormControl<string>(null, [Validators.required, Validators.maxLength(200)]),
    address_id: new FormControl<number>(null),
    wto_productivity: new FormControl<number>(null, Validators.max(999999999999)),
    wto_domestic_sewage: new FormControl<string>(null, Validators.required),
    wto_industrial_sewage: new FormControl<string>(null, Validators.required),
    wto_sludge: new FormControl<string>(null, Validators.required),
    wto_is_it_used: new FormControl<string>(null, Validators.required),
    wto_auto_accept: new FormControl<string>(null),
    wto_no_notifications: new FormControl<string>(null),
  });

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
      .getManagerFacilitiesList(pagingParams, params, extendedParams)
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
        .getManagerFacilitiesList(
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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  getNtisActions(row: WaterManagerFacilitiesBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_CREATE &&
          (action !== ActionsEnum.ACTIONS_READ ||
            !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE))
      )
      .forEach((action) => {
        if (action === ActionsEnum.ACTIONS_UPDATE) {
          const action: NtisTableRowActionsItem = {
            icon: { iconStyle: 'solid', iconName: 'faPencil' },
            actionName: 'update',
            iconTheme: 'default',
            action: () => {
              this.editFacility(row);
            },
          };
          rowActions.push(action);
        } else if (action === ActionsEnum.ACTIONS_READ) {
          const action: NtisTableRowActionsItem = {
            icon: { iconStyle: 'solid', iconName: 'faEye' },
            actionName: 'view',
            iconTheme: 'default',
            action: () => {
              this.editFacility(row);
            },
          };
          rowActions.push(action);
        }
      });
    return rowActions;
  }
  editFacility(row: WaterManagerFacilitiesBrowseRow): void {
    this.form.reset();
    if (row.wto_auto_accept === this.trueDB) {
      if (row.wto_no_notifications === this.trueDB) {
        this.noNotifications = true;
        this.form.controls.wto_no_notifications.setValue(this.trueDB);
      } else {
        this.noNotifications = false;
        this.form.controls.wto_no_notifications.setValue(this.falseDB);
      }
    }
    if (row.wto_is_it_used == this.trueDB) {
      this.checked = true;
      this.form.controls.wto_is_it_used.setValue(this.trueDB);
    } else {
      this.checked = false;
      this.form.controls.wto_is_it_used.setValue(this.falseDB);
    }
    this.addressId = row.address_id;
    this.editDialog = true;
    this.form.controls.wto_id.setValue(row.wto_id);
    this.form.controls.address_id.setValue(row.address_id);
    this.form.controls.wto_name.setValue(row.wto_name);
    this.form.controls.wto_productivity.setValue(row.wto_productivity);
    this.form.controls.wto_address.setValue(row.wto_address);
    this.form.controls.wto_domestic_sewage.setValue(row.wto_domestic_sewage);
    this.form.controls.wto_industrial_sewage.setValue(row.wto_industrial_sewage);
    this.form.controls.wto_sludge.setValue(row.wto_sludge);
    this.form.controls.wto_auto_accept.setValue(row.wto_auto_accept);
    this.manageFormDisabled();
  }

  addNewFacility(): void {
    this.form.reset();
    this.checked = true;
    this.form.controls.wto_is_it_used.setValue(this.trueDB);
    this.manageFormDisabled();
    this.facilitySearch = true;
    this.editDialog = true;
  }

  setFacilityAddress(value: AddressSearchResponse): void {
    this.form.controls.address_id.setValue(value.address_id);
    this.form.controls.wto_address.setValue(value.full_address_text);
  }

  saveFacility(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const facilityForUpdate: NtisWaterManagerFacility = {
        wto_domestic_sewage: this.form.controls.wto_domestic_sewage.value,
        wto_id: this.form.controls.wto_id.value,
        wto_industrial_sewage: this.form.controls.wto_industrial_sewage.value,
        wto_is_it_used: this.form.controls.wto_is_it_used.value,
        wto_name: this.form.controls.wto_name.value,
        wto_productivity: this.form.controls.wto_productivity.value,
        wto_sludge: this.form.controls.wto_sludge.value,
        wto_address: this.form.controls.wto_address.value,
        address_id: this.form.controls.address_id.value,
        wto_auto_accept: this.form.controls.wto_auto_accept.value,
        wto_no_notifications:
          this.form.controls.wto_auto_accept.value === this.falseDB
            ? null
            : this.form.controls.wto_no_notifications.value
            ? this.form.controls.wto_no_notifications.value
            : this.falseDB,
      };
      this.adminService.saveManagerFacility(facilityForUpdate).subscribe(() => {
        this.reload();
        this.editDialog = false;
        this.form.reset();
        this.facilitySearch = false;
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
        this.adminService.waterManagersWtfsSubject.next();
        location.reload();
      });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  onCheckboxChange(): void {
    this.checked = !this.checked;
    if (this.checked) {
      this.form.controls.wto_is_it_used.setValue(this.trueDB);
    } else {
      this.form.controls.wto_is_it_used.setValue(this.falseDB);
    }
    this.manageFormDisabled();
  }

  onNotificationCheckboxChange(): void {
    this.noNotifications = !this.noNotifications;
    if (this.noNotifications) {
      this.form.controls.wto_no_notifications.setValue(this.trueDB);
    } else {
      this.form.controls.wto_no_notifications.setValue(this.falseDB);
    }
  }

  manageFormDisabled(): void {
    if (this.checked) {
      this.form.controls.wto_name.enable();
      this.form.controls.wto_productivity.enable();
      this.form.controls.wto_domestic_sewage.enable();
      this.form.controls.wto_industrial_sewage.enable();
      this.form.controls.wto_sludge.enable();
      this.form.controls.wto_address.enable();
      this.form.controls.wto_auto_accept.enable();
      this.searchActive = true;
    } else {
      this.searchActive = false;
      this.form.controls.wto_name.disable();
      this.form.controls.wto_productivity.disable();
      this.form.controls.wto_domestic_sewage.disable();
      this.form.controls.wto_industrial_sewage.disable();
      this.form.controls.wto_sludge.disable();
      this.form.controls.wto_address.disable();
      this.form.controls.wto_auto_accept.disable();
    }
  }

  convertShownValue(dbText: string): string {
    let shownValue: string;
    if (dbText === this.trueDB) {
      shownValue = this.commonFormServices.translate.instant('common.action.yes') as string;
    } else if (dbText === this.falseDB) {
      shownValue = this.commonFormServices.translate.instant('common.action.no') as string;
    }
    return shownValue;
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.editDialog = false;
  }

  onReturn(): void {
    void this.commonFormServices.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.INSTITUTIONS,
      NtisRoutingConst.INST_WATER_MANAGER_SETTINGS,
    ]);
  }
}
