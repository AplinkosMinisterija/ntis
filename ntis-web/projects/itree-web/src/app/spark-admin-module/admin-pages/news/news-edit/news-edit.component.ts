/**
 *
 * @note Po naujienų puslapių ir susijusių elementų perkėlimo iš "Spark"
 * prototipo, pakeitimai buvo atlikti pagal NTIS projektą.
 * @date 2023-05-24
 *
 */
import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NtisNewsEditModel, SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { CommonFormServices, DeprecatedBaseEditForm, getLang } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { NewsService } from '../../../admin-services/news.service';
import { FILE_STATUS_CONFIRMED, FILE_STATUS_UPLOADED } from '@itree-commons/src/constants/files.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { ClsfSprLanguage } from '@itree-commons/src/lib/enums/classifiers.enums';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import DecoupledEditor from '@ckeditor/ckeditor5-build-decoupled-document';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MyUploadAdapter } from './my-upload-adapter';
import { FileLoader } from '@ckeditor/ckeditor5-upload';

@Component({
  selector: 'app-news-edit',
  templateUrl: './news-edit.component.html',
  styleUrls: ['./news-edit.component.scss'],
})
export class NewsEditComponent extends DeprecatedBaseEditForm<NtisNewsEditModel> {
  readonly translationsRef = 'pages.newsEdit';
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  protected uploadedFiles: SprFile[] = [];
  private newsType: string = null;
  protected minDate: Date = new Date();
  private returnUrl: string[];
  public Editor = DecoupledEditor;
  isPublic: boolean = false;
  isTemplate: boolean = false;
  isNewsFromTemplate: boolean;
  editorData: string = null;
  currentTab: number;

  readonly clsfSprLanguage = ClsfSprLanguage;

  protected multilanguageExists: boolean = true;

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private newsService: NewsService,
    protected override activatedRoute: ActivatedRoute,
    private router: Router,
    private fileUploadService: FileUploadService,
    private appDataService: AppDataService
  ) {
    super(formBuilder, commonFormServices, activatedRoute);

    this.activatedRoute.params.pipe(takeUntilDestroyed()).subscribe((params) => {
      const type = params[RoutingConst.TYPE] as string;
      this.newsType = type;
      this.returnUrl = [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.NEWS_LIST, this.newsType];
    });

    this.activatedRoute.queryParams.pipe(takeUntilDestroyed()).subscribe((params) => {
      this.currentTab = params['selectedTab'] as number;
      this.returnUrl = [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.NEWS_LIST, this.newsType];
      this.isNewsFromTemplate = params.actionType === ActionsEnum.ACTIONS_COPY;
    });
  }

  public onReady(editor: DecoupledEditor): void {
    const element = editor.ui.getEditableElement();
    const parent = element.parentElement;
    parent.insertBefore(editor.ui.view.toolbar.element, element);

    editor.plugins.get('FileRepository').createUploadAdapter = (loader: FileLoader): MyUploadAdapter => {
      return new MyUploadAdapter(loader, this.fileUploadService);
    };
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      nw_title: new FormControl(null, Validators.compose([Validators.maxLength(256), Validators.required])),
      nw_lang: new FormControl(this.clsfSprLanguage.Lt),
      nw_summary: new FormControl(null, Validators.compose([Validators.maxLength(800), Validators.required])),
      nw_text: new FormControl(null, Validators.compose([Validators.required])),
      is_template: new FormControl(null),
    });

    this.multilanguageExists = this.appDataService.multilanguageExists();
  }

  protected doSave(formData: NtisNewsEditModel): void | Observable<NtisNewsEditModel> {
    formData.nwLang = getLang();
    formData.isTemplate = 'N';
    this.newsService.save(formData).subscribe((response) => {
      this.navigateToTab();
      this.onSaveSuccess(response);
    });
  }

  protected doLoad(): void {
    this.onLoadSuccess(this.activatedRoute.snapshot.data['newsEditData'] as NtisNewsEditModel);
  }

  protected setData(data: NtisNewsEditModel): void {
    this.data = data;
    this.form.controls.nw_title.setValue(data.nwTitle);
    this.form.controls.nw_lang.setValue(data.nwLang);
    this.form.controls.nw_summary.setValue(data.nwSummary);
    this.form.controls.nw_text.setValue(data.nwText);
    this.uploadedFiles = data.attachment;
    this.isPublic = data.isPublic === DB_BOOLEAN_TRUE;
    this.form.controls.is_template.setValue(data.isTemplate);
  }

  protected getData(): NtisNewsEditModel {
    const result: NtisNewsEditModel = this.data ?? ({} as NtisNewsEditModel);
    result.nwTitle = this.form.controls.nw_title.value as NtisNewsEditModel['nwTitle'];
    result.nwLang = this.form.controls.nw_lang.value as NtisNewsEditModel['nwLang'];
    result.nwSummary = this.form.controls.nw_summary.value as NtisNewsEditModel['nwSummary'];
    result.nwText = this.form.controls.nw_text.value as NtisNewsEditModel['nwText'];
    result.nwPublished = new Date();
    result.nwDateFrom = new Date();
    result.nwType = this.newsType;
    result.attachment = this.uploadedFiles;
    result.isPublic = this.isPublic ? DB_BOOLEAN_TRUE : (DB_BOOLEAN_FALSE as NtisNewsEditModel['isPublic']);
    result.isTemplate = this.isNewsFromTemplate
      ? DB_BOOLEAN_FALSE
      : (this.form.controls.is_template.value as NtisNewsEditModel['isTemplate']);
    if (this.isNewsFromTemplate) {
      result.nwId = null;
    }
    return result;
  }
  isUsedForNewsCategory: string;
  favorite: string;

  protected onDeleteFile(fileToDelete: SprFile): void {
    if (fileToDelete.fil_status === FILE_STATUS_UPLOADED) {
      this.deleteTempFile(fileToDelete);
    } else if (fileToDelete.fil_status === FILE_STATUS_CONFIRMED) {
      this.uploadedFiles = [];
    }
  }

  private deleteTempFile(fileToDelete: SprFile): void {
    this.fileUploadService.deleteFile(fileToDelete).subscribe();
  }

  protected onCancel(): void {
    this.navigateToTab();
  }

  onSaveTemplate(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const formData = this.getData();
      formData.isTemplate = DB_BOOLEAN_TRUE;
      formData.saveAsNewTemplate = true;
      this.newsService.save(formData).subscribe((response) => {
        this.navigateToTab();
        this.onSaveSuccess(response);
      });
    }
  }

  navigateToTab(): void {
    const queryParams = { selectedTab: this.currentTab };
    void this.router.navigate(this.returnUrl, { queryParams });
  }
}
