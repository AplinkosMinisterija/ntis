import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_ORD_IMPORT_VIEW_PAGE } from '../../constants/forms.const';
import { OrdFilStatus, ServiceItemType } from '../../enums/classifiers.enums';
import { ActivatedRoute, Params } from '@angular/router';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NtisOrdImportFile, SprFile } from '@itree-commons/src/lib/model/api/api';
import { OrderImportService } from '../../services/order-import.service';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { OrdImportFileLinesListComponent } from '../../components/ord-import-file-lines-list/ord-import-file-lines-list.component';
import { OrdImportErrorLinesListComponent } from '../../components/ord-import-error-lines-list/ord-import-error-lines-list.component';
import { OrdImportRemovalLinesListComponent } from '../../components/ord-import-removal-lines-list/ord-import-removal-lines-list.component';
import { OrdImportResearchLinesListComponent } from '../../components/ord-import-research-lines-list/ord-import-research-lines-list.component';

@Component({
  selector: 'ntis-ord-import-view-page',
  templateUrl: './ord-import-view-page.component.html',
  styleUrls: ['./ord-import-view-page.component.scss'],
  standalone: true,
  imports: [
    ItreeCommonsModule,
    CommonModule,
    DialogModule,
    TableModule,
    OrdImportFileLinesListComponent,
    OrdImportErrorLinesListComponent,
    OrdImportRemovalLinesListComponent,
    OrdImportResearchLinesListComponent,
  ],
})
export class OrdImportViewPageComponent implements OnInit {
  readonly translationsReference = 'ntisShared.pages.ordersImportView';
  readonly formCode = NTIS_ORD_IMPORT_VIEW_PAGE;
  readonly OrdFilStatus = OrdFilStatus;
  readonly ServiceItemType = ServiceItemType;
  isServiceProvider: boolean;
  orfId: string;
  orgId: string;
  returnUrl: string;
  uploadedFile: SprFile;
  uploadedFileDetails: NtisOrdImportFile;
  importDateStr: string;
  confirmedDateStr: string;
  totalErrors: string;
  totalRecords: string;
  showErrors: boolean = false;
  showData: boolean = false;
  confirmationDialog: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private orderImportService: OrderImportService,
    private datePipe: S2DatePipe,
    private commonFormServices: CommonFormServices,
    private fileUploadService: FileUploadService,
    private location: Location
  ) {
    this.isServiceProvider = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.PASL_ADMIN_ACTIONS);
    this.activatedRoute.params.pipe(takeUntilDestroyed()).subscribe((params: Params) => {
      this.orfId = params.id as string;
      this.orgId = params.orgId as string;
    });
    this.activatedRoute.queryParams.pipe(takeUntilDestroyed()).subscribe((params: Params) => {
      this.returnUrl = (params?.['returnUrl'] as string) ?? this.returnUrl;
    });
  }

  ngOnInit(): void {
    this.orderImportService.getImportFile(this.orfId, this.orgId).subscribe((result) => {
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
      this.importDateStr = this.datePipe.transform(result.orf_import_date);
      this.confirmedDateStr = this.datePipe.transform(result.orf_status_date);
    });
  }

  download(): void {
    this.fileUploadService.downloadFile(this.uploadedFile);
  }

  backToBrowseForm(): void {
    if (this.returnUrl) {
      void this.commonFormServices.router.navigateByUrl(this.returnUrl);
    } else {
      this.location.back();
    }
  }

  deleteFile(): void {
    this.orderImportService.deleteFile(this.uploadedFile).subscribe(() => {
      this.commonFormServices.appMessages.showSuccess(
        '',
        this.commonFormServices.translate.instant('common.message.deleteSuccess') as string
      );
      this.backToBrowseForm();
    });
  }

  updateFileState(): void {
    this.confirmationDialog = false;
    if (!this.orgId) {
      this.orderImportService.updateFileStateOrg(this.orfId).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
        );
        this.backToBrowseForm();
      });
    } else {
      this.orderImportService.updateFileState(this.orfId).subscribe(() => {
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.fileConfirmationSuccess') as string
        );
        this.backToBrowseForm();
      });
    }
  }
}
