import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationExtras, Params, Router } from '@angular/router';
import {
  ACTIONS_DELETE,
  ACTIONS_UPDATE,
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { CwFilStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisTableRowActionsItem } from 'projects/itree-web/src/app/ntis-shared/models/table-row-actions';
import { CwDataListBrowseRow } from '../../models/browse-pages';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NtisSharedModule } from 'projects/itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';
import { NTIS_CW_DATA_LIST, NTIS_CW_FOR_ORG_DATA_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { CwFileErrorsListComponent } from '../../components/cw-file-errors-list/cw-file-errors-list.component';
import { CwFileLinesListComponent } from '../../components/cw-file-lines-list/cw-file-lines-list.component';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { Subject, map, takeUntil } from 'rxjs';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

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
  selector: 'app-centralized-wastewater-data-list',
  standalone: true,
  templateUrl: './centralized-wastewater-data-list.component.html',
  styleUrls: ['./centralized-wastewater-data-list.component.scss'],
  imports: [
    CommonModule,
    CwFileErrorsListComponent,
    CwFileLinesListComponent,
    DialogModule,
    FontAwesomeModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    TableModule,
    TooltipModule,
  ],
})
export class CentralizedWastewaterDataListComponent extends BaseBrowseForm<CwDataListBrowseRow> implements OnInit {
  readonly translationsReference = 'intsData.centralizedData.pages.centralizedWastewaterDataList';
  readonly errorsDetected = '.errorsDetected';
  readonly noErrorsDetected = '.noErrorsDetected';
  readonly formCode = NTIS_CW_DATA_LIST;
  readonly CwFilStatus = CwFilStatus;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  destroy$: Subject<boolean> = new Subject<boolean>();
  uploadedFiles: SprFile[] = [];
  uploadedFile: SprFile;
  disabledUpload: boolean = false;
  confirmationDialog: boolean = false;
  filName: string;
  fileUploaded: boolean = false;
  cwfId: string;
  orgName: string;
  orgId: string;
  files: SprFile[] = [];
  reglamentasUrl: string;
  userCanCreate: boolean = false;
  usr_person_name: string;
  usr_person_surname: string;
  usr_email: string;
  usr_phone_number: string;
  usrInfoDialog: boolean = false;
  sortClause: string;
  sortOrder: number;

  cols: TableColumn[] = [
    { field: 'cwf_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'fil_name', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwf_import_date', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'cwf_status', export: false, visible: false, type: DATA_TYPE_STRING },
    { field: 'total_errors', export: false, visible: false, type: DATA_TYPE_NUMBER },
    { field: 'cwf_fil_id', export: false, visible: false, type: DATA_TYPE_NUMBER },
  ];
  visibleColumns = this.cols.filter((res) => res.visible);

  constructor(
    protected override commonFormServices: CommonFormServices,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private centralizedService: CentralizedWastewaterDataService,
    private fileUploadService: FileUploadService,
    private router: Router,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    if (this.userCanCreate == undefined) {
      this.userCanCreate = this.authService.isFormActionEnabled(NTIS_CW_FOR_ORG_DATA_LIST, ActionsEnum.ACTIONS_CREATE);
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
    this.searchForm.addControl('cwf_status', new FormControl(''));
    this.searchForm.addControl('fil_name', new FormControl(''));
    this.searchForm.addControl('cwf_import_date', new FormControl(''));
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
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((arParam: Params) => {
      this.orgId = arParam.orgId ? (arParam.orgId as string) : null;
      if (this.orgId) {
        this.centralizedService.getExemplaryFiles().subscribe((result) => {
          this.files = result;
        });
        this.centralizedService.getPrpFile().subscribe((result) => {
          this.reglamentasUrl = result?.value;
        });
        this.centralizedService
          .getCentralizedWastewaterData(this.orgId, pagingParams, params, extendedParams)
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
            this.orgName = result.data.length !== 0 ? result.data[0].org_name : null;
            if (!this.orgName) {
              this.centralizedService.getOrgName(this.orgId).subscribe((result) => {
                this.orgName = result?.data[0].value;
              });
            }
          });
      } else {
        this.centralizedService.getExemplaryFilesOrg().subscribe((result) => {
          this.files = result;
        });
        this.centralizedService.getPrpFileOrg().subscribe((result) => {
          this.reglamentasUrl = result?.value;
        });
        this.centralizedService
          .getCentralizedWastewaterDataOrg(pagingParams, params, extendedParams)
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
            this.orgName = result.data.length !== 0 ? result.data[0].org_name : null;
            if (!this.orgName) {
              this.centralizedService.getOrgNameForOrg().subscribe((result) => {
                this.orgName = result?.data[0].value;
              });
            }
          });
      }
    });
  }

  onFileUpload(): void {
    this.disabledUpload = true;
    if (this.uploadedFiles.length !== 0) {
      if (this.orgId === null) {
        this.centralizedService
          .saveNewWastewaterDataFileOrg(this.uploadedFiles[0].fil_key)
          .pipe(
            map((result) => {
              this.centralizedService.validateFileErrsOrg(result?.cwf_id).subscribe();
            })
          )
          .subscribe(() => {
            this.reload();
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
          });
      } else {
        this.centralizedService
          .saveNewWastewaterDataFile(this.uploadedFiles[0].fil_key, this.orgId)
          .pipe(
            map((result) => {
              this.centralizedService.validateFileErrs(result?.cwf_id, this.orgId).subscribe();
            })
          )
          .subscribe(() => {
            this.reload();
            this.commonFormServices.appMessages.showInfo(
              '',
              this.commonFormServices.translate.instant('common.message.fileUploadedErrProcessing') as string
            );
          });
      }
    }
  }

  download(row?: CwDataListBrowseRow): void {
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
    } else {
      file = this.uploadedFile;
    }
    this.fileUploadService.downloadFile(file);
  }

  deleteFile(row?: CwDataListBrowseRow): void {
    let file: SprFile;

    if (row !== null && row !== undefined) {
      file = {
        fil_content_type: row.fil_content_type,
        fil_key: row.fil_key,
        fil_name: row.fil_name,
        fil_size: row.fil_size,
        fil_status: row.fil_status,
        fil_status_date: row.fil_status_date,
      };
    } else {
      file = this.uploadedFile;
    }
    if (this.orgId === null) {
      this.centralizedService.deletePendingFileOrg(file).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
        );
        this.disabledUpload = false;
        this.uploadedFiles = [];
        this.reload();
      });
    } else {
      this.centralizedService.deletePendingFile(file).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
        );
        this.disabledUpload = false;
        this.uploadedFiles = [];
        this.reload();
      });
    }
  }

  navigateToRead(id: string): void {
    void this.router.navigate([this.router.url, RoutingConst.VIEW, id], {
      queryParams: { returnUrl: this.router.url },
    });
  }

  confirmFinalDetails(row: CwDataListBrowseRow): void {
    this.confirmationDialog = true;
    this.filName = row.fil_name;
    this.cwfId = row.cwf_id.toString();
  }

  updateFileState(): void {
    this.confirmationDialog = false;
    if (this.orgId === null) {
      this.centralizedService.updateCwFileStateOrg(this.cwfId).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
        );
        this.disabledUpload = false;
        this.reload();
      });
    } else {
      this.centralizedService.updateCwFileState(this.cwfId).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
        );
        this.disabledUpload = false;
        this.reload();
      });
    }
  }

  getActions(row: CwDataListBrowseRow): NtisTableRowActionsItem[] {
    const searchData = this.getSearchDataFromSession();
    const rowActions: NtisTableRowActionsItem[] = [];
    if (
      row.cwf_status === this.CwFilStatus.cw_fil_pending ||
      row.cwf_status === this.CwFilStatus.cw_fil_pending_err ||
      row.cwf_status === this.CwFilStatus.cw_fil_err_proccessing
    ) {
      this.disabledUpload = true;
    }
    row.availableActions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_READ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faEye' },
          actionName: 'view',
          iconTheme: 'default',
          action: (): void => {
            this.navigateToRead(row.cwf_id.toString());
          },
        };
        const viewInfo: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faUser' },
          actionName: 'viewUsrInfo',
          iconTheme: 'default',
          action: () => {
            this.openInfoDialog(row);
          },
        };
        rowActions.push(viewInfo);
        rowActions.push(action);
      } else if (
        row.cwf_status === this.CwFilStatus.cw_fil_pending &&
        (row.total_errors === 0 || !row.total_errors) &&
        action === ACTIONS_UPDATE
      ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faCircleCheck' },
          actionName: 'confirm',
          iconTheme: 'success',
          action: (): void => {
            this.confirmFinalDetails(row);
          },
        };
        rowActions.push(action);
      } else if (
        (row.cwf_status === this.CwFilStatus.cw_fil_pending ||
          row.cwf_status === this.CwFilStatus.cw_fil_pending_err) &&
        action === ACTIONS_DELETE
      ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faXmark' },
          actionName: 'deleteCwFile',
          iconTheme: 'risk',
          action: (): void => {
            this.deleteFile(row);
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  back(): void {
    this.uploadedFiles = [];
    this.uploadedFile = null;
    this.fileUploaded = false;
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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected override deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  onReturn(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    if (this.orgId) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.DATA,
          NtisRoutingConst.CENTRALIZED_WASTEWATER,
          NtisRoutingConst.WASTEWATER_DATA_REPORT,
        ],
        navigationExtras
      );
    }
  }

  openInfoDialog(row: CwDataListBrowseRow): void {
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
