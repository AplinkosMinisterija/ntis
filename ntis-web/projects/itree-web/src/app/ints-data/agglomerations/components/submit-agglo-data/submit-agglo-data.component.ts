import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { CalendarModule } from 'primeng/calendar';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { TranslateService } from '@ngx-translate/core';
import { AppMessages } from '@itree/ngx-s2-commons';
import { AgglomerationsService } from '../../services/agglomerations.service';

@Component({
  selector: 'app-submit-agglo-data',
  standalone: true,
  imports: [CommonModule, DialogModule, ReactiveFormsModule, ItreeCommonsModule, CalendarModule, FontAwesomeModule],
  templateUrl: './submit-agglo-data.component.html',
  styleUrls: ['./submit-agglo-data.component.scss'],
})
export class SubmitAggloDataComponent implements OnChanges {
  readonly translationsRef = 'intsData.agglomerations.components.submitAggloData';

  @Input() show = false;
  @Input() aggId: number;
  @Output() showChange = new EventEmitter<boolean>();
  @Output() submitted = new EventEmitter<void>();

  formGroup = new FormGroup({
    approvalDate: new FormControl<Date>(null, Validators.required),
    approvalDocNo: new FormControl<string>('', Validators.required),
    dataDocuments: new FormControl<SprFile[]>([], Validators.required),
  });

  constructor(
    public faIconsService: FaIconsService,
    private fileUploadService: FileUploadService,
    private translateService: TranslateService,
    private appMessages: AppMessages,
    private agglomerationsService: AgglomerationsService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.show) {
      if (!changes.show.previousValue && changes.show.currentValue) {
        this.formGroup.reset({ approvalDate: null, approvalDocNo: '', dataDocuments: [] });
      }
    }
    if (changes.aggId) {
      if (changes.aggId.currentValue) {
        if (this.formGroup.controls.approvalDate.hasValidator(Validators.required)) {
          this.formGroup.controls.approvalDate.clearValidators();
        }
        if (this.formGroup.controls.approvalDocNo.hasValidator(Validators.required)) {
          this.formGroup.controls.approvalDocNo.clearValidators();
        }
      } else {
        if (!this.formGroup.controls.approvalDate.hasValidator(Validators.required)) {
          this.formGroup.controls.approvalDate.addValidators(Validators.required);
        }
        if (!this.formGroup.controls.approvalDocNo.hasValidator(Validators.required)) {
          this.formGroup.controls.approvalDocNo.addValidators(Validators.required);
        }
      }
    }
  }

  handleHide(): void {
    this.show = false;
    this.showChange.emit(this.show);
  }

  onFileUpload(files: SprFile[]): void {
    this.formGroup.controls.dataDocuments.setValue(files);
  }

  onFileDelete(file: SprFile): void {
    if (file.fil_key) {
      this.fileUploadService.deleteFile(file).subscribe();
    }
    this.formGroup.controls.dataDocuments.setValue([]);
  }

  handleSubmit(): void {
    this.formGroup.markAllAsTouched();
    if (this.formGroup.valid) {
      if (this.aggId) {
        this.agglomerationsService
          .submitNewData({
            aggId: this.aggId,
            approvalDate: null,
            approvalDocNo: null,
            dataDocument: this.formGroup.controls.dataDocuments.value[0],
          })
          .subscribe(() => {
            this.handleSubmitSuccess();
          });
      } else {
        this.agglomerationsService
          .submitAgglomeration({
            approvalDate: this.formGroup.controls.approvalDate.value,
            approvalDocNo: this.formGroup.controls.approvalDocNo.value,
            dataDocument: this.formGroup.controls.dataDocuments.value[0],
          })
          .subscribe((value) => {
            this.handleSubmitSuccess();
            this.agglomerationsService.sendSubmittedAggloNotification(value.agg_id).subscribe();
          });
      }
    } else {
      this.translateService
        .get('common.message.correctValidationErrors')
        .subscribe((translation: string) => this.appMessages.showError('', translation, 'VALIDATION'));
    }
  }

  handleSubmitSuccess(): void {
    this.handleHide();
    this.translateService.get('common.message.saveSuccess').subscribe((translation: string) => {
      this.appMessages.showSuccess('', translation);
    });
    this.submitted.emit();
  }
}
