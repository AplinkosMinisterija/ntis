import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DATA_TYPE_NUMBER, DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { NTIS_CONTRACTS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  AuthUtil,
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, map, takeUntil } from 'rxjs';
import { Table, TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import {
  ForeignKeyParams,
  NtisContractRequestService,
  SprFile,
  SprOrganizationsDAO,
} from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ContractState } from '../../enums/contract-state.enums';
import { NtisContractsService } from '../../services/ntis-contracts.service';
import { ContractsListBrowseRow } from '../../models/browse-pages';
import { StatusData } from 'projects/itree-web/src/app/ntis-shared/models/record-status';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from 'projects/itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from 'projects/itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { DB_BOOLEAN_TRUE, MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { TooltipModule } from 'primeng/tooltip';

interface SelectOption {
  option: string;
  value: number;
  type?: 'org' | 'per';
}

@Component({
  selector: 'app-contracts-list-page',
  standalone: true,
  templateUrl: './contracts-list-page.component.html',
  styleUrls: ['./contracts-list-page.component.scss'],
  imports: [
    CommonModule,
    ItreeCommonsModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    NtisSharedModule,
    DialogModule,
    TooltipModule,
  ],
})
export class ContractsListPageComponent extends BaseBrowseForm<ContractsListBrowseRow> implements OnInit, OnDestroy {
  readonly formCode = NTIS_CONTRACTS_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly formTranslationsReference = 'wteMaintenanceAndOperation.contracts.pages.contractsList';
  destroy$: Subject<boolean> = new Subject();
  exportData: ContractsListBrowseRow[] = [];
  isServiceProvider: boolean;
  isCustomer: boolean;
  pageHeader: string;
  actionDialog: boolean;
  dialogHeader: string;
  dialogForm = new FormGroup({
    cot_service_provider: new FormControl<string>('', Validators.required),
    cot_wtf_id: new FormControl<number>(null),
  });
  wtfFilter = new FormGroup({
    wtf_id: new FormControl<string>(null),
  });
  uploadedFiles: SprFile[] = [];
  wtfOptions: SelectOption[] = [];
  serviceProvidersList: SprOrganizationsDAO[] = [];
  userCanUploadContract: boolean;
  spHasServices: boolean;
  cols: TableColumn[];
  visibleColumns: TableColumn[];

  recordStatus: StatusData[] = [
    { status: ContractState.SUBMITTED, type: 'neutral' },
    { status: ContractState.CANCELLED, type: 'risk' },
    { status: ContractState.REJECTED, type: 'risk' },
    { status: ContractState.CONFIRMED, type: 'success' },
    { status: ContractState.EXPIRED, type: 'risk' },
    { status: ContractState.SIGNED_BY_BOTH, type: 'success' },
    { status: ContractState.SIGNED_BY_SRV_PROVIDER, type: 'neutral' },
    { status: ContractState.SIGNED_BY_CUSTOMER, type: 'neutral' },
    { status: ContractState.VALID, type: 'success' },
    { status: ContractState.TERMINATED, type: 'risk' },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: NtisContractsService,
    private authService: AuthService,
    private ntisCommonService: NtisCommonService,
    private fileUploadService: FileUploadService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;

    this.isServiceProvider = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.SERVICE_PROVIDER_ACTIONS);
    this.isCustomer = !this.isServiceProvider;
    this.userCanUploadContract = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.UPLOAD_CONTRACT);

    this.cols = [
      { field: 'cot_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
      { field: 'cot_code', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'cot_created', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'cot_services', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'cot_service_provider', export: this.isCustomer, visible: this.isCustomer, type: DATA_TYPE_STRING },
      {
        field: 'cot_customer',
        export: this.isServiceProvider,
        visible: this.isServiceProvider,
        type: DATA_TYPE_STRING,
      },
      { field: 'cot_state_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'cot_method', export: true, visible: true, type: DATA_TYPE_STRING },
    ];
    this.visibleColumns = this.cols.filter((res) => res.visible);

    if (this.isServiceProvider) {
      this.adminService.checkServices().subscribe((result) => {
        this.spHasServices = result;
      });
      this.dialogForm.controls.cot_service_provider.setValue(AuthUtil.getOrgName());
      this.dialogForm.controls.cot_service_provider.disable();
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.dialogHeaderServiceProvider')
        .subscribe((translation: string) => {
          this.dialogHeader = translation;
        });
      this.pageHeader = this.formTranslationsReference + '.headerTextServiceProvider';
    } else if (this.isCustomer) {
      this.dialogForm.controls.cot_wtf_id.setValidators(Validators.required);
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.dialogHeaderCustomer')
        .subscribe((translation: string) => {
          this.dialogHeader = translation;
        });
      this.pageHeader = this.formTranslationsReference + '.headerTextCustomer';
    }
    this.wtfFilter.controls.wtf_id.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.reload();
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.searchForm.addControl('cot_code', new FormControl(''));
    this.searchForm.addControl('cot_created', new FormControl(''));
    this.searchForm.addControl('cot_services', new FormControl(''));
    this.searchForm.addControl('cot_service_provider', new FormControl(''));
    this.searchForm.addControl('cot_customer', new FormControl(''));
    this.searchForm.addControl('cot_state', new FormControl(''));
    this.searchForm.addControl('cot_method', new FormControl(''));

    this.ntisCommonService.getWtfList().subscribe((result) => {
      this.wtfOptions = [];
      result.data.map((wtf) => {
        this.wtfOptions.push({
          value: wtf.value,
          option: wtf.option,
        });
      });
    });
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
    if (this.wtfFilter.controls.wtf_id.value) {
      extendedParams.push({
        paramName: 'cot_wtf_id',
        paramValue: {
          condition: ExtendedSearchCondition.Equals,
          value: this.wtfFilter.controls.wtf_id.value,
          upperLower: ExtendedSearchUpperLower.Regular,
        },
      });
    }
    this.adminService
      .getContractsList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getActions(row);
            if (row.cot_services_json) {
              const services: NtisContractRequestService[] = JSON.parse(
                row.cot_services_json
              ) as NtisContractRequestService[];
              const serviceNameArr: string[] = [];
              services.forEach((srv) => {
                serviceNameArr.push(srv.name);
              });
              row.cot_services = serviceNameArr.join(', ');
            }
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
        .getContractsList(
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

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  showDialog(): void {
    this.actionDialog = true;
  }

  hideDialog(): void {
    this.actionDialog = false;
  }

  onFileUpload(): void {
    if (this.uploadedFiles.length <= 0) {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.noFileUploaded')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else if (this.uploadedFiles[0].fil_size === 0) {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.fileIsEmpty')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_name) {
      this.fileUploadService.deleteFile(fileToDelete).subscribe();
    }
  }

  getActions(row: ContractsListBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: (): void => {
            if (row.cot_method_code === DB_BOOLEAN_TRUE) {
              void this.commonFormServices.router.navigate([
                this.commonFormServices.router.url,
                RoutingConst.EDIT,
                this.isServiceProvider ? NtisRoutingConst.PASLAUG : NtisRoutingConst.CLIENT,
                row.cot_id,
              ]);
            } else {
              void this.commonFormServices.router.navigate(
                [
                  this.commonFormServices.router.url,
                  NtisRoutingConst.UPLOAD,
                  this.isServiceProvider ? NtisRoutingConst.PASLAUG : NtisRoutingConst.CLIENT,
                  RoutingConst.VIEW,
                ],
                {
                  queryParams: {
                    cot: row.cot_id,
                  },
                }
              );
            }
          },
        });
      }
    });
    return itemActions;
  }

  onCancel(): void {
    this.hideDialog();
  }

  findServiceProviders(value: ForeignKeyParams): void {
    this.adminService.findServiceProviders(value).subscribe((res) => {
      this.serviceProvidersList = res.data;
      if (res.data.length <= 0) {
        this.commonFormServices.translate
          .get(this.formTranslationsReference + '.serviceProviderNotFound')
          .subscribe((translation: string) => {
            this.commonFormServices.appMessages.showError('', translation);
          });
      }
    });
  }

  onNext(): void {
    this.dialogForm.markAllAsTouched();
    if (this.dialogForm.valid && this.uploadedFiles.length > 0 && this.uploadedFiles[0].fil_size > 0) {
      void this.commonFormServices.router.navigate(
        [
          this.commonFormServices.router.url,
          NtisRoutingConst.UPLOAD,
          this.isServiceProvider ? NtisRoutingConst.PASLAUG : NtisRoutingConst.CLIENT,
          RoutingConst.EDIT,
        ],
        {
          queryParams: {
            sp: this.isServiceProvider
              ? AuthUtil.getOrgId().toString()
              : this.dialogForm.controls.cot_service_provider.value,
            wtfId: this.dialogForm.controls.cot_wtf_id.value as unknown as string,
            filKey: this.uploadedFiles[0].fil_key,
          },
        }
      );
    } else if (this.uploadedFiles.length <= 0) {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.noFileUploaded')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else if (this.uploadedFiles[0].fil_size === 0) {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.fileIsEmpty')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    } else {
      this.commonFormServices.translate
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showError('', translation);
        });
    }
  }

  navigateToServiceSearch(): void {
    void this.commonFormServices.router.navigate([
      RoutingConst.INTERNAL,
      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
      NtisRoutingConst.CONTRACTS,
      NtisRoutingConst.SERVICE_SEARCH,
    ]);
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.actionDialog = false;
  }
}
