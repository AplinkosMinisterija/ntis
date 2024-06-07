import { CommonModule } from '@angular/common';
import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { NtisOrdersImportBrowseRow } from '../../models/browse-page';
import { NTIS_ORD_IMPORT_FOR_ORG_LIST, NTIS_ORD_IMPORT_LIST } from '../../constants/forms.const';
import { ActivatedRoute, NavigationExtras, Params } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { OrderImportService } from '../../services/order-import.service';
import { map, mergeMap, of } from 'rxjs';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  ACTIONS_DELETE,
  ACTIONS_UPDATE,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TableModule } from 'primeng/table';
import { NtisSharedModule } from '../../ntis-shared.module';
import { TooltipModule } from 'primeng/tooltip';
import { OrgDetailsForOrderImport, SprFile, SprListIdKeyValue } from '@itree-commons/src/lib/model/api/api';
import { OrdFilStatus, ServiceItemType } from '../../enums/classifiers.enums';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { NTIS_SRV_ITEM_TYPE } from '../../constants/classifiers.const';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { NtisTableRowActionsItem } from '../../models/table-row-actions';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';
import { DialogModule } from 'primeng/dialog';

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
  selector: 'ntis-ord-import-list',
  templateUrl: './ord-import-list.component.html',
  styleUrls: ['./ord-import-list.component.scss'],
  standalone: true,
  imports: [
    ItreeCommonsModule,
    CommonModule,
    TooltipModule,
    ReactiveFormsModule,
    NtisSharedModule,
    TableModule,
    FontAwesomeModule,
    DialogModule,
  ],
})
export class OrdImportListComponent extends BaseBrowseForm<NtisOrdersImportBrowseRow> implements OnInit {
  readonly translationsReference = 'ntisShared.pages.ordersImport';
  readonly translationsReferenceForCar =
    'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.orderOutsideNtis';

  readonly linkForNoCarsMessage = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.NTIS_CARS_LIST}`;

  readonly formCode = NTIS_ORD_IMPORT_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly ServiceItemType = ServiceItemType;
  readonly OrdFilStatus = OrdFilStatus;
  destroyRef = inject(DestroyRef);
  availableServices: SprListIdKeyValue[] = [];
  orgId: string;
  orgName: string;
  userCanCreate: boolean;
  uploadedResearch: SprFile[] = [];
  uploadedTech: SprFile[] = [];
  uploadedDisposal: SprFile[] = [];
  orgDetails: OrgDetailsForOrderImport[];
  researchAvailable: boolean = false;
  techAvailable: boolean = false;
  disposalAvailable: boolean = false;
  orfId: number;
  showNoCarMessage: boolean = true;
  usr_phone_number: string;
  usr_person_surname: string;
  usr_person_name: string;
  usr_email: string;
  usrInfoDialog: boolean = false;
  sortClause: string;
  sortOrder: number;

  cols: TableColumn[] = [
    { field: 'orf_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'fil_name', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'srv_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'orf_import_date', export: true, visible: true, type: DATA_TYPE_DATE },
    { field: 'orf_status', export: true, visible: true, type: DATA_TYPE_STRING },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private activatedRoute: ActivatedRoute,
    private orderImportService: OrderImportService,
    private authService: AuthService,
    public faIconsService: FaIconsService,
    private commonService: CommonService,
    private fileUploadService: FileUploadService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    of(true).pipe(takeUntilDestroyed()).subscribe();
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    if (!this.userCanCreate) {
      this.userCanCreate = this.authService.isFormActionEnabled(
        NTIS_ORD_IMPORT_FOR_ORG_LIST,
        ActionsEnum.ACTIONS_CREATE
      );
    }
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
    this.commonService.getClsf(NTIS_SRV_ITEM_TYPE).subscribe((result) => {
      this.availableServices = result.filter(
        (clsf) => clsf.key !== this.ServiceItemType.treatment && clsf.key !== this.ServiceItemType.installation
      );
    });

    this.searchForm.addControl('orf_import_date', new FormControl(''));
    this.searchForm.addControl('orf_status', new FormControl(''));
    this.searchForm.addControl('srv_type', new FormControl(''));
    this.searchForm.addControl('fil_name', new FormControl(''));
  }

  protected override load(
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

    this.activatedRoute.params.pipe(takeUntilDestroyed(this.destroyRef)).subscribe((param: Params) => {
      this.orgId = param.orgId ? (param.orgId as string) : null;
      if (this.orgId) {
        this.orderImportService.getOrgDetails(this.orgId).subscribe((org) => {
          this.orgName = org[0]?.orgName;
          this.orgDetails = org;
          this.checkServiceAvailability(org);
        });
        this.orderImportService
          .getOrderImports(this.orgId, pagingParams, params, extendedParams)
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
          });
      } else {
        this.orderImportService.getOrgDetailsOrg().subscribe((org) => {
          this.orgName = org[0]?.orgName;
          this.orgDetails = org;
          this.checkServiceAvailability(org);
        });
        this.orderImportService
          .getOrderImportsForOrg(pagingParams, params, extendedParams)
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
          });
      }
    });
  }

  protected override deleteRecord(recordId: string): void {
    if (this.orgId === null) {
      this.orderImportService.deletePendingFileOrg(recordId).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
        );
        this.reload();
      });
    } else {
      this.orderImportService.deletePendingFile(recordId).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
        );
        this.reload();
      });
    }
  }

  onResearchFileUpload(): void {
    const filKey = this.uploadedResearch[this.uploadedResearch.length - 1].fil_key;
    const srvId = this.orgDetails.find((srv) => {
      return srv.srvTypeClsf === this.ServiceItemType.tests;
    }).srvId;

    if (!this.orgId) {
      this.orderImportService
        .uploadNewFileOrg(filKey, srvId.toString())
        .pipe(
          map((response) => {
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
            this.orfId = response;
            this.reload();
          }),
          mergeMap(() => this.orderImportService.validateErrorsOrg(this.orfId))
        )
        .subscribe(() => {
          this.commonFormServices.confirmationService.confirm({
            message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
            accept: () => {
              void this.commonFormServices.router
                .navigateByUrl(
                  `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.RESEARCH}/${NtisRoutingConst.SP_RESEARCH_LIST}`,
                  { skipLocationChange: true }
                )
                .then(() => {
                  void this.commonFormServices.router.navigate(
                    [
                      RoutingConst.INTERNAL,
                      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
                      NtisRoutingConst.RESEARCH,
                      NtisRoutingConst.SP_RESEARCH_LIST,
                      NtisRoutingConst.ORDERS_IMPORT,
                      RoutingConst.VIEW,
                      this.orfId,
                    ],
                    {
                      queryParams: {
                        returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.RESEARCH}/${NtisRoutingConst.SP_RESEARCH_LIST}/${NtisRoutingConst.ORDERS_IMPORT}`,
                      },
                    }
                  );
                });
            },
            reject: () => {
              this.commonFormServices.appMessages.showSuccess(
                '',
                this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
              );
              this.reload();
            },
          });
        });
    } else {
      this.orderImportService
        .uploadNewFile(filKey, srvId.toString(), this.orgId)
        .pipe(
          map((response) => {
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
            this.orfId = response;
            this.reload();
          }),
          mergeMap(() => this.orderImportService.validateErrors(this.orfId, this.orgId))
        )
        .subscribe(() => {
          this.commonFormServices.confirmationService.confirm({
            message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
            accept: () => {
              void this.commonFormServices.router
                .navigateByUrl(
                  `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SERVICE_PROVIDERS}/${NtisRoutingConst.ORDERS_IMPORT}/${this.orgId}`,
                  { skipLocationChange: true }
                )
                .then(() => {
                  void this.commonFormServices.router.navigate(
                    [
                      RoutingConst.INTERNAL,
                      NtisRoutingConst.INSTITUTIONS,
                      NtisRoutingConst.SERVICE_PROVIDERS,
                      NtisRoutingConst.ORDERS_IMPORT,
                      this.orgId,
                      RoutingConst.VIEW,
                      this.orfId,
                    ],
                    {
                      queryParams: {
                        returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SERVICE_PROVIDERS}/${NtisRoutingConst.ORDERS_IMPORT}/${this.orgId}`,
                      },
                    }
                  );
                });
            },
            reject: () => {
              this.commonFormServices.appMessages.showSuccess(
                '',
                this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
              );
              this.reload();
            },
          });
        });
    }
  }

  onTechFileUpload(): void {
    const filKey = this.uploadedTech[this.uploadedTech.length - 1].fil_key;
    const srvId = this.orgDetails.find((srv) => {
      return srv.srvTypeClsf === this.ServiceItemType.maintenance;
    }).srvId;

    if (!this.orgId) {
      this.orderImportService
        .uploadNewFileOrg(filKey, srvId.toString())
        .pipe(
          map((response) => {
            this.orfId = response;
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
            this.reload();
          }),
          mergeMap(() => this.orderImportService.validateErrorsOrg(this.orfId))
        )
        .subscribe(() => {
          this.commonFormServices.confirmationService.confirm({
            message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
            accept: () => {
              void this.commonFormServices.router
                .navigateByUrl(
                  `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.TECH_ORDERS_LIST}/${NtisRoutingConst.ORDERS_IMPORT}`,
                  { skipLocationChange: true }
                )
                .then(() => {
                  void this.commonFormServices.router.navigate(
                    [
                      RoutingConst.INTERNAL,
                      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
                      NtisRoutingConst.TECH_SUPPORT,
                      NtisRoutingConst.TECH_ORDERS_LIST,
                      NtisRoutingConst.ORDERS_IMPORT,
                      RoutingConst.VIEW,
                      this.orfId,
                    ],
                    {
                      queryParams: {
                        returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.TECH_ORDERS_LIST}/${NtisRoutingConst.ORDERS_IMPORT}`,
                      },
                    }
                  );
                });
            },
            reject: () => {
              this.commonFormServices.appMessages.showSuccess(
                '',
                this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
              );
              this.reload();
            },
          });
        });
    } else {
      this.orderImportService
        .uploadNewFile(filKey, srvId.toString(), this.orgId)
        .pipe(
          map((response) => {
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
            this.orfId = response;
            this.reload();
          }),
          mergeMap(() => this.orderImportService.validateErrors(this.orfId, this.orgId))
        )
        .subscribe(() => {
          this.commonFormServices.confirmationService.confirm({
            message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
            accept: () => {
              void this.commonFormServices.router
                .navigateByUrl(
                  `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SERVICE_PROVIDERS}/${NtisRoutingConst.ORDERS_IMPORT}/${this.orgId}`,
                  { skipLocationChange: true }
                )
                .then(() => {
                  void this.commonFormServices.router.navigate(
                    [
                      RoutingConst.INTERNAL,
                      NtisRoutingConst.INSTITUTIONS,
                      NtisRoutingConst.SERVICE_PROVIDERS,
                      NtisRoutingConst.ORDERS_IMPORT,
                      this.orgId,
                      RoutingConst.VIEW,
                      this.orfId,
                    ],
                    {
                      queryParams: {
                        returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SERVICE_PROVIDERS}/${NtisRoutingConst.ORDERS_IMPORT}/${this.orgId}`,
                      },
                    }
                  );
                });
            },
            reject: () => {
              this.commonFormServices.appMessages.showSuccess(
                '',
                this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
              );
              this.reload();
            },
          });
        });
    }
  }

  onDisposalFileUpload(): void {
    const filKey = this.uploadedDisposal[this.uploadedDisposal.length - 1].fil_key;
    const srvId = this.orgDetails.find((srv) => {
      return srv.srvTypeClsf === this.ServiceItemType.sewageRemoval;
    }).srvId;

    if (!this.orgId) {
      this.orderImportService
        .uploadNewFileOrg(filKey, srvId.toString())
        .pipe(
          map((response) => {
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
            this.orfId = response;
            this.reload();
          }),
          mergeMap(() => this.orderImportService.validateErrorsOrg(this.orfId))
        )
        .subscribe(() => {
          this.commonFormServices.confirmationService.confirm({
            message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
            accept: () => {
              void this.commonFormServices.router
                .navigateByUrl(
                  `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.DISPOSAL_ORDERS_LIST}/${NtisRoutingConst.ORDERS_IMPORT}`,
                  { skipLocationChange: true }
                )
                .then(() => {
                  void this.commonFormServices.router.navigate(
                    [
                      RoutingConst.INTERNAL,
                      NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
                      NtisRoutingConst.TECH_SUPPORT,
                      NtisRoutingConst.DISPOSAL_ORDERS_LIST,
                      NtisRoutingConst.ORDERS_IMPORT,
                      RoutingConst.VIEW,
                      this.orfId,
                    ],
                    {
                      queryParams: {
                        returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.DISPOSAL_ORDERS_LIST}/${NtisRoutingConst.ORDERS_IMPORT}`,
                      },
                    }
                  );
                });
            },
            reject: () => {
              this.commonFormServices.appMessages.showSuccess(
                '',
                this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
              );
              this.reload();
            },
          });
        });
    } else {
      this.orderImportService
        .uploadNewFile(filKey, srvId.toString(), this.orgId)
        .pipe(
          map((response) => {
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
            this.orfId = response;
            this.reload();
          }),
          mergeMap(() => this.orderImportService.validateErrors(this.orfId, this.orgId))
        )
        .subscribe(() => {
          this.commonFormServices.confirmationService.confirm({
            message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
            accept: () => {
              void this.commonFormServices.router
                .navigateByUrl(
                  `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SERVICE_PROVIDERS}/${NtisRoutingConst.ORDERS_IMPORT}/${this.orgId}`,
                  { skipLocationChange: true }
                )
                .then(() => {
                  void this.commonFormServices.router.navigate(
                    [
                      RoutingConst.INTERNAL,
                      NtisRoutingConst.INSTITUTIONS,
                      NtisRoutingConst.SERVICE_PROVIDERS,
                      NtisRoutingConst.ORDERS_IMPORT,
                      this.orgId,
                      RoutingConst.VIEW,
                      this.orfId,
                    ],
                    {
                      queryParams: {
                        returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.SERVICE_PROVIDERS}/${NtisRoutingConst.ORDERS_IMPORT}/${this.orgId}`,
                      },
                    }
                  );
                });
            },
            reject: () => {
              this.commonFormServices.appMessages.showSuccess(
                '',
                this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
              );
              this.reload();
            },
          });
        });
    }
  }

  checkServiceAvailability(orgDetails: OrgDetailsForOrderImport[]): void {
    orgDetails.forEach((orgService) => {
      if (orgService.car_exists) {
        this.showNoCarMessage = false;
      }
      if (orgService.srvTypeClsf === this.ServiceItemType.maintenance) {
        this.techAvailable = true;
      } else if (orgService.srvTypeClsf === this.ServiceItemType.tests) {
        this.researchAvailable = true;
      } else if (orgService.srvTypeClsf === this.ServiceItemType.sewageRemoval) {
        this.disposalAvailable = true;
      }
    });
  }

  download(row: NtisOrdersImportBrowseRow): void {
    let file: SprFile;
    if (row) {
      file = {
        fil_content_type: row.fil_content_type,
        fil_key: row.fil_key,
        fil_name: row.fil_name,
        fil_size: row.fil_size,
        fil_status: row.fil_status,
        fil_status_date: row.fil_status_date,
      };
    }
    this.fileUploadService.downloadFile(file);
  }

  getActions(row: NtisOrdersImportBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const viewInfo: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faUser' },
          actionName: 'viewUsrInfo',
          iconTheme: 'default',
          action: () => {
            this.openInfoDialog(row);
          },
        };
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: (): void => {
            this.navigateToRead(row.orf_id.toString());
          },
        };
        rowActions.push(viewInfo);
        rowActions.push(action);
      } else if (
        row.orf_status_clsf === this.OrdFilStatus.ord_fil_pending &&
        (row.total_errors === 0 || !row.total_errors) &&
        action === ACTIONS_UPDATE
      ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faCircleCheck' },
          actionName: 'createOrders',
          iconTheme: 'success',
          action: (): void => {
            this.confirmFileAsFinal(row.orf_id.toString());
          },
        };
        rowActions.push(action);
      } else if (
        row.orf_status_clsf !== this.OrdFilStatus.ord_fil_final &&
        row.orf_status_clsf !== this.OrdFilStatus.ord_fil_err_proccessing &&
        action === ACTIONS_DELETE
      ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faXmark' },
          actionName: 'delete',
          iconTheme: 'risk',
          action: (): void => {
            this.deleteRecordWithConfirmation(row.orf_id.toString());
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  navigateToRead(id: string): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, RoutingConst.VIEW, id], {
      queryParams: { returnUrl: this.commonFormServices.router.url },
    });
  }

  confirmFileAsFinal(id: string): void {
    if (this.orgId) {
      this.orderImportService.updateFileState(id).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
        );
        this.reload();
      });
    } else {
      this.orderImportService.updateFileStateOrg(id).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
        );
        this.reload();
      });
    }
  }

  navigateToAddressMappings(): void {
    if (this.orgId) {
      void this.commonFormServices.router.navigate([
        this.commonFormServices.router.url,
        NtisRoutingConst.CW_ADDRESS_MAPPINGS,
        this.orgId,
      ]);
    } else {
      void this.commonFormServices.router.navigate([
        this.commonFormServices.router.url,
        NtisRoutingConst.CW_ADDRESS_MAPPINGS,
      ]);
    }
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    const currentUrl = this.commonFormServices.router.url;
    if (currentUrl.includes(NtisRoutingConst.DISPOSAL_ORDERS_LIST)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.TECH_SUPPORT,
          NtisRoutingConst.DISPOSAL_ORDERS_LIST,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.TECH_ORDERS_LIST)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.TECH_SUPPORT,
          NtisRoutingConst.TECH_ORDERS_LIST,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.SP_RESEARCH_LIST)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.RESEARCH,
          NtisRoutingConst.SP_RESEARCH_LIST,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.SERVICE_PROVIDERS)) {
      void this.commonFormServices.router.navigate(
        [RoutingConst.INTERNAL, NtisRoutingConst.INSTITUTIONS, NtisRoutingConst.SERVICE_PROVIDERS],
        navigationExtras
      );
    }
  }
  openInfoDialog(row: NtisOrdersImportBrowseRow): void {
    this.usr_phone_number = row.usr_phone_number;
    this.usr_person_surname = row.usr_person_surname;
    this.usr_person_name = row.usr_person_name;
    this.usr_email = row.usr_email;
    this.usrInfoDialog = true;
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
