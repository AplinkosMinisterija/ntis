import { Component, HostListener, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { TECH_ORDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, map, takeUntil } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { OrdersListBrowseRow } from '../../models/browse-pages';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { NtisOrderCompletedWorksRequest } from '@itree-commons/src/lib/model/api/api';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { OrderStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { ActivatedRoute } from '@angular/router';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { DialogModule } from 'primeng/dialog';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
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

@Component({
  selector: 'app-tech-orders-list-page',
  templateUrl: './tech-orders-list-page.component.html',
  styleUrls: ['./tech-orders-list-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
    NtisSharedModule,
    TableModule,
    CalendarModule,
  ],
})
export class TechOrdersListPageComponent extends BaseBrowseForm<OrdersListBrowseRow> implements OnInit {
  readonly formCode = TECH_ORDERS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.techOrdersList';
  destroy$: Subject<boolean> = new Subject<boolean>();
  exportData: OrdersListBrowseRow[] = [];
  actionDialog: boolean;
  form: FormGroup;
  today: Date = new Date();
  minDate: Date;
  ord_id: number;
  ordStatus: string;
  sortClause: string;
  sortOrder: number;

  cols: TableColumn[] = [
    { field: 'ord_id', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'ord_created', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_method', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_customer', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ord_status', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'ocw_completed_date', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'address', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    private activatedRoute: ActivatedRoute,
    private techSuppService: NtisTechSupportService,
    protected override commonFormServices: CommonFormServices
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;

    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      if (params.ordState == OrderStatus.confirmed || params.ordState == OrderStatus.submitted) {
        this.ordStatus = params.ordState as string;
      }
    });

    this.form = new FormGroup({
      ord_id: new FormControl({ disabled: true, hidden: true }),
      ocw_completed_works_description: new FormControl('', Validators.maxLength(400)),
      ocw_completed_date: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        this.sortClause = searchData.sortField ?? null;
        this.sortOrder = searchData.order ?? null;
        this.loadSearchDataIntoSearchForm();
      }
    }
    this.searchForm.addControl('ord_id', new FormControl(''));
    this.searchForm.addControl('ord_created', new FormControl(''));
    this.searchForm.addControl('ord_method', new FormControl(''));
    this.searchForm.addControl('ord_customer', new FormControl(''));
    this.searchForm.addControl('ord_state', new FormControl(this.ordStatus));
    this.searchForm.addControl('ocw_completed_date', new FormControl(''));
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
    const pagingParams = this.getPagingParams(
      first,
      pageSize,
      this.sortClause ? this.sortClause : sortField,
      this.sortOrder ? this.sortOrder : order,
      null
    );
    this.techSuppService
      .getTechOrdersList(pagingParams, params, extendedParams)
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
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.techSuppService
        .getTechOrdersList(
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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  protected deleteRecord(recordId: string): void {}

  enterOrderCompletionDate(row: OrdersListBrowseRow): void {
    this.form.controls.ocw_completed_date.setValue(new Date());
    this.actionDialog = true;
    this.ord_id = row.ord_id;
    this.minDate = new Date(row.ord_created);
  }

  hideDialog(): void {
    this.actionDialog = false;
  }

  getActions(row: OrdersListBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action == ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          iconTheme: 'default',
          actionName: 'view',
          action: (): void => {
            if (action === ActionsEnum.ACTIONS_READ) {
              void this.commonFormServices.router.navigate([
                this.commonFormServices.router.url,
                NtisRoutingConst.SERVICE_ORDER_PAGE,
                row.ord_id,
              ]);
            }
          },
        });
      }
      if (action == ActionsEnum.ACTIONS_UPDATE) {
        this.addAdditionalAction(row, itemActions);
      }
    });

    return itemActions;
  }

  addAdditionalAction(row: OrdersListBrowseRow, menu: NtisTableRowActionsItem[]): void {
    if (row.ord_state === OrderStatus.confirmed) {
      menu.push({
        icon: { iconStyle: 'solid', iconName: 'faCheckCircle' },
        actionName: 'finishOrder',
        iconTheme: 'default',
        action: (): void => {
          this.enterOrderCompletionDate(row);
        },
      });
    }
  }

  checkFieldsDate(): boolean {
    for (const field in this.form.controls) {
      if (field === 'ocw_completed_date') {
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

  saveDate(): void {
    if (this.form.controls.ocw_completed_date.valid) {
      const order: NtisOrderCompletedWorksRequest = {
        ocw_ord_id: this.ord_id,
        ocw_completed_date: this.form.controls.ocw_completed_date.value as Date,
        ocw_completed_works_description: this.form.controls.ocw_completed_works_description.value as string,
      };
      this.techSuppService.setTechOrderCompletionDate(order).subscribe(() => {
        this.techSuppService
          .setTechOrderState({ ord_id: this.ord_id, ord_state: OrderStatus.finished })
          .subscribe(() => {
            this.hideDialog();
            this.reload();
            this.commonFormServices.appMessages.showSuccess(
              '',
              this.commonFormServices.translate.instant('common.message.saveSuccess') as string
            );
            this.techSuppService.sendOrderEditNotifications(this.ord_id).subscribe();
          });
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
    this.actionDialog = false;
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
