import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationExtras, Params, Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { NtisWastewaterDataImportFile, SprFile } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { CwFilStatus } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';
import { CwFileErrorsListComponent } from '../../components/cw-file-errors-list/cw-file-errors-list.component';
import { CwFileLinesListComponent } from '../../components/cw-file-lines-list/cw-file-lines-list.component';
import { Subject, takeUntil } from 'rxjs';
@Component({
  selector: 'app-centralized-wastewater-data-view-page',
  standalone: true,
  templateUrl: './centralized-wastewater-data-view-page.component.html',
  styleUrls: ['./centralized-wastewater-data-view-page.component.scss'],
  imports: [
    CommonModule,
    CwFileErrorsListComponent,
    CwFileLinesListComponent,
    DialogModule,
    ItreeCommonsModule,
    TableModule,
  ],
})
export class CentralizedWastewaterDataViewPageComponent implements OnInit {
  readonly translationsReference = 'intsData.centralizedData.pages.centralizedWastewaterDataViewPage';
  readonly formCode = NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE;
  readonly cw_fil_pending = CwFilStatus.cw_fil_pending;
  readonly cw_fil_final = CwFilStatus.cw_fil_final;
  readonly cw_fil_err_proccessing = CwFilStatus.cw_fil_err_proccessing;
  readonly cw_fil_pending_err = CwFilStatus.cw_fil_pending_err;
  destroy$: Subject<boolean> = new Subject<boolean>();
  returnUrl: string;
  uploadedFileDetails: NtisWastewaterDataImportFile;
  uploadedFile: SprFile;
  cwfId: string;
  orgId: string;
  confirmationDialog: boolean;
  totalErrors: string;
  totalRecords: string;
  showErrors: boolean = false;
  showData: boolean = false;
  importDateStr: string;
  confirmedDateStr: string;
  isWastewaterAdmin: boolean;

  errorCols: TableColumn[] = [
    { field: 'cwfde_column_nr', export: false, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'cwfde_column_value', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfde_msg_text', export: false, visible: true, type: DATA_TYPE_STRING },
  ];
  dataLineCols: TableColumn[] = [
    { field: 'cwfd_pastato_kodas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_pastato_adr_kodas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_adresas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_nuot_salinimo_budas', export: false, visible: true, type: DATA_TYPE_STRING },
    { field: 'cwfd_prijungimo_data', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'cwfd_atjungimo_data', export: false, visible: true, type: DATA_TYPE_DATE },
    { field: 'cwfd_statinio_vald_kodas', export: false, visible: true, type: DATA_TYPE_STRING },
  ];

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private centralizedService: CentralizedWastewaterDataService,
    private commonFormServices: CommonFormServices,
    private datePipe: S2DatePipe,
    private fileUploadService: FileUploadService,
    private router: Router,
    private location: Location
  ) {
    this.isWastewaterAdmin = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.VAND_ADMIN_ACTIONS);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.cwfId = params.id as string;
      this.orgId = params.orgId as string;
    });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.returnUrl = (params?.['returnUrl'] as string) ?? this.returnUrl;
    });
  }

  ngOnInit(): void {
    this.centralizedService.getImportFile(this.cwfId, this.orgId).subscribe((result) => {
      this.uploadedFileDetails = result;
      this.totalErrors = result.total_errors ? result.total_errors.toString() : '0';
      this.totalRecords = result.total_records ? result.total_records.toString() : '0';
      this.uploadedFile = {
        fil_content_type: this.uploadedFileDetails.fil_content_type,
        fil_key: this.uploadedFileDetails.fil_key,
        fil_name: this.uploadedFileDetails.fil_name,
        fil_size: this.uploadedFileDetails.fil_size,
        fil_status: this.uploadedFileDetails.fil_status,
        fil_status_date: this.uploadedFileDetails.fil_status_date,
      };
      this.importDateStr = this.datePipe.transform(result.cwf_import_date);
      this.confirmedDateStr = this.datePipe.transform(result.cwf_status_date);
    });
  }

  updateFileState(): void {
    this.confirmationDialog = false;
    this.centralizedService.updateFileState(this.cwfId).subscribe(() => {
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
      );
      this.backToBrowseForm();
    });
  }

  deleteFile(): void {
    this.centralizedService.deleteFile(this.uploadedFile).subscribe(() => {
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
      );
      this.backToBrowseForm();
    });
  }

  download(): void {
    this.fileUploadService.downloadFile(this.uploadedFile);
  }

  backToBrowseForm(): void {
    if (this.returnUrl) {
      const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
      void this.router.navigateByUrl(this.returnUrl, navigationExtras);
    } else {
      this.location.back();
    }
  }
}
