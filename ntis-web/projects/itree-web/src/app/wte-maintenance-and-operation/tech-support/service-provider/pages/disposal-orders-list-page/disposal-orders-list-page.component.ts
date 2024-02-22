import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NTIS_DISPOSAL_ORDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, map, takeUntil } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { OrdersListBrowseRow } from '../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { OrderStatus, SewageDeliveryStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisOrderCompletedWorksDAO } from '@itree-commons/src/lib/model/api/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActivatedRoute } from '@angular/router';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

interface BrowseSearchData {
  url: string;
  paramsObj: Record<string, unknown>;
  extendedParams: ExtendedSearchParam[];
  searchFormValues: Record<string, unknown>;
  first: number;
  pageSize: number;
  sortField: string;
  order: number;
}

interface carOption {
  option: string;
  value: number;
}

@Component({
  selector: 'app-disposal-orders-list-page',
  templateUrl: './disposal-orders-list-page.component.html',
  styleUrls: ['./disposal-orders-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    TableModule,
    NtisSharedModule,
    FormsModule,
    DialogModule,
    ReactiveFormsModule,
    CalendarModule,
  ],
})
export class DisposalOrdersListPageComponent extends BaseBrowseForm<OrdersListBrowseRow> implements OnInit {
  protected override deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }
  readonly formCode = NTIS_DISPOSAL_ORDERS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference =
    'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.disposalOrdersList';
  destroy$: Subject<boolean> = new Subject<boolean>();
  exportData: OrdersListBrowseRow[] = [];
  userCanCreate: boolean;
  actionDialog: boolean;
  carsOptions: carOption[] = [];
  today: Date = new Date();
  minDate: Date;
  ord_id: number;
  ordStatus: string;

  dialogForm = new FormGroup({
    ord_id: new FormControl({ disabled: true, hidden: true }),
    ocw_completed_works_description: new FormControl<string>('', Validators.maxLength(400)),
    ocw_completed_date: new FormControl<Date>(null, Validators.required),
    ocw_discharged_sludge_amount: new FormControl<number>(
      null,
      Validators.compose([Validators.required, Validators.max(999999999)])
    ),
    ocw_car_used: new FormControl<number>(null, Validators.required),
  });
  cols: TableColumn[] = [
    { field: 'ord_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ord_created', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_method', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_customer', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_status_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_removed_sewage_date', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ocw_discharged_sludge_amount', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'sewage_delivery_status', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'address', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private techSuppService: NtisTechSupportService,
    protected override commonFormServices: CommonFormServices
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      if (params.ordState == OrderStatus.confirmed || params.ordState == OrderStatus.submitted) {
        this.ordStatus = params.ordState as string;
      }
    });
  }

  ngOnInit(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        this.loadSearchDataIntoSearchForm();
      }
    }
    this.searchForm.addControl('ord_id', new FormControl(''));
    this.searchForm.addControl('ord_created', new FormControl(''));
    this.searchForm.addControl('ord_method', new FormControl(''));
    this.searchForm.addControl('ord_customer', new FormControl(''));
    this.searchForm.addControl('ord_state', new FormControl(this.ordStatus));
    this.searchForm.addControl('ord_removed_sewage_date', new FormControl(''));
    this.searchForm.addControl('ocw_discharged_sludge_amount', new FormControl(''));
    this.searchForm.addControl('wd_state', new FormControl(''));
    this.searchForm.addControl('address', new FormControl(''));
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
    this.techSuppService
      .getDisposalOrdersList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getActions(row);
          });
          return result;
        })
      )
      .subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
        this.exportData = result.data;
      });
    this.techSuppService.getOrgCarsList().subscribe((result) => {
      result.forEach((car) => {
        this.carsOptions.push({
          option: `${car.cr_model} (${car.cr_reg_no})`,
          value: car.cr_id,
        });
      });
    });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.techSuppService
        .getDisposalOrdersList(
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

  enterOrderCompletionData(row: OrdersListBrowseRow): void {
    this.dialogForm.controls.ocw_completed_date.setValue(new Date());
    this.actionDialog = true;
    this.ord_id = row.ord_id;
    this.minDate = new Date(row.ord_created);
  }

  hideDialog(): void {
    this.actionDialog = false;
    this.dialogForm.reset();
  }

  getActions(row: OrdersListBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: (): void => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.SERVICE_ORDER_PAGE,
              row.ord_id,
            ]);
          },
        });
      }
      if (action === ActionsEnum.ACTIONS_UPDATE && row.ord_state === OrderStatus.confirmed) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faCheckCircle' },
          iconTheme: 'default',
          actionName: 'finishOrder',
          action: (): void => {
            this.enterOrderCompletionData(row);
          },
        });
      }
    });
    this.addAdditionalAction(row, itemActions);
    return itemActions;
  }

  addAdditionalAction(row: OrdersListBrowseRow, menu: NtisTableRowActionsItem[]): void {
    if (row.ord_state === OrderStatus.finished && (row.wd_state === SewageDeliveryStatus.rejected || !row.wd_state)) {
      menu.push({
        icon: { iconStyle: 'solid', iconName: 'faTruckArrowRight' },
        actionName: row.wd_state === SewageDeliveryStatus.rejected ? 'createNewBasedOnOld' : 'createDelivery',
        iconTheme: 'default',
        action: (): void => {
          if (row.wd_state === SewageDeliveryStatus.rejected) {
            void this.commonFormServices.router.navigate(
              [this.commonFormServices.router.url, NtisRoutingConst.SEWAGE_DELIVERIES, RoutingConst.EDIT, row.wd_id],
              { queryParams: { actionType: ActionsEnum.ACTIONS_COPY } }
            );
          }
          if (!row.sewage_delivery_status) {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.SEWAGE_DELIVERIES,
              RoutingConst.NEW,
              row.ord_id,
            ]);
          }
        },
      });
    }
  }

  checkFields(): boolean {
    for (const field in this.dialogForm.controls) {
      if (this.dialogForm.get(field).dirty === true) {
        return true;
      }
    }
    return false;
  }

  onCancel(): void {
    if (!this.checkFields()) {
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

  onCompleteOrder(): void {
    this.dialogForm.markAllAsTouched();
    if (this.dialogForm.valid) {
      const order: NtisOrderCompletedWorksDAO = {} as NtisOrderCompletedWorksDAO;
      order.ocw_ord_id = this.ord_id;
      order.ocw_completed_works_description = this.dialogForm.controls.ocw_completed_works_description.value;
      order.ocw_completed_date = this.dialogForm.controls.ocw_completed_date.value;
      order.ocw_cr_id = this.dialogForm.controls.ocw_car_used.value;
      order.ocw_discharged_sludge_amount = this.dialogForm.controls.ocw_discharged_sludge_amount.value;

      this.techSuppService.setDisposalOrderCompletionData(order).subscribe(() => {
        this.hideDialog();
        this.reload();
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant(this.formTranslationsReference + '.orderCompleted', {
            ord_id: order.ocw_ord_id,
          }) as string
        );
        this.techSuppService.sendOrderEditNotifications(this.ord_id).subscribe();
      });
    }
  }

  toAddNewOrder(): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      NtisRoutingConst.ORDER_OUTSIDE_NTIS_CREATE,
    ]);
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.actionDialog = undefined;
  }

  navigateToOrdersImport(): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, NtisRoutingConst.ORDERS_IMPORT]);
  }

  protected override saveSearchDataToSession(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const browseSearchData: BrowseSearchData = {
      url: this.commonFormServices.router.url,
      paramsObj: Object.fromEntries(params),
      extendedParams,
      searchFormValues: Object.keys(this.searchForm.controls)
        .filter((controlName) => this.searchForm.controls[controlName]?.value)
        .reduce(
          (valuesObj, controlName) => ({
            ...valuesObj,
            [controlName]: this.searchForm.controls[controlName].value as unknown,
          }),
          {} as Record<string, unknown>
        ),
      first,
      pageSize,
      sortField,
      order,
    };
    sessionStorage.setItem(this.formCode, JSON.stringify(browseSearchData));
  }

  protected override getSearchDataFromSession(): BrowseSearchData | null {
    const searchData = JSON.parse(sessionStorage.getItem(this.formCode)) as BrowseSearchData;
    if (searchData) {
      if (searchData?.url === this.commonFormServices.router.url) {
        return searchData;
      }
    }
    return null;
  }

  protected override removeSearchDataFromSession(): void {
    sessionStorage.removeItem(this.formCode);
  }
}
