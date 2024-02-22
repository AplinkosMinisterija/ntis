import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_REF_CLASSIFIER_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ClsfSprLanguage } from '@itree-commons/src/lib/enums/classifiers.enums';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';

import {
  BrowseFormSearchResult,
  RefTranslationsObj,
  SprRefDictionariesDAO,
} from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { Subject, takeUntil, tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { RefCodesClassifierObj, SprCodesTranslationsBrowseRow } from '../../../models/browse-pages';
import { AppDataService } from '@itree-commons/src/lib/services/app-data.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';

@Component({
  selector: 'app-codes-translations-page',
  templateUrl: './codes-translations-page.component.html',
  styleUrls: ['./codes-translations-page.component.scss'],
})
export class CodesTranslationsPageComponent extends BaseBrowseForm<SprCodesTranslationsBrowseRow> implements OnInit {
  readonly formCode = SPR_REF_CLASSIFIER_LIST;
  destroy$: Subject<boolean> = new Subject<boolean>();
  rowMenuItems: Record<string, MenuItem[]> = {};
  exportData: SprCodesTranslationsBrowseRow[] = [];
  codeEditLink = RoutingConst.EDIT;
  rfd_id: string;
  rfd_table_name: string;
  rfd_name: string;
  rfd_description: string;
  form: FormGroup;
  tempTranslations: RefTranslationsObj[] = [];
  codesTranslations: RefCodesClassifierObj[] = [];
  codesList: SprCodesTranslationsBrowseRow[] = [];
  translationsList: RefTranslationsObj[] = [];

  actionDialog: boolean = false;
  showTranslationsList: boolean = false;

  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  cols: TableColumn[];
  visibleColumns: TableColumn[];
  expansionColumns: TableColumn[];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    private confirmationService: ConfirmationService,
    private appDataService: AppDataService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.form = new FormGroup({
      rft_id: new FormControl(''),
      rft_lang: new FormControl(''),
      rft_display_code: new FormControl(''),
      rft_description: new FormControl(''),
      rft_rfc_id: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('rfc_id', new FormControl(''));
    this.searchForm.addControl('rfc_code', new FormControl(''));
    this.searchForm.addControl('rfc_domain', new FormControl(''));
    this.searchForm.addControl('rfc_meaning', new FormControl(''));
    this.searchForm.addControl('rfc_date_from', new FormControl(''));
    this.searchForm.addControl('rfc_date_to', new FormControl(''));
    this.searchForm.addControl('rfc_description', new FormControl(''));
    this.searchForm.addControl('rfc_order', new FormControl(''));
    this.searchForm.addControl('rft_id', new FormControl(''));
    this.searchForm.addControl('rft_lang', new FormControl(''));
    this.searchForm.addControl('rft_display_code', new FormControl(''));
    this.searchForm.addControl('rft_description', new FormControl(''));
    this.cols = [
      { field: 'rfc_id', export: false, visible: false, type: DATA_TYPE_STRING },
      { field: 'rfc_code', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'rfc_domain', export: false, visible: false, type: DATA_TYPE_STRING },
      { field: 'rfc_meaning', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'rfc_description', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'rfc_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'rfc_date_to', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'rfc_rfd_id', export: false, visible: false, type: DATA_TYPE_STRING },
      { field: 'rfc_order', export: true, visible: true, type: DATA_TYPE_NUMBER },
      { field: 'rft_lang', export: true, visible: false, type: DATA_TYPE_STRING },
      { field: 'rft_display_code', export: true, visible: false, type: DATA_TYPE_STRING },
      { field: 'rft_description', export: true, visible: false, type: DATA_TYPE_STRING },
    ];
    this.visibleColumns = this.cols.filter((res) => res.visible);
    this.expansionColumns = this.cols.filter((res) => res.export && !res.visible);

    this.translationsList = this.activatedRoute.snapshot.data['translations'] as RefTranslationsObj[];

    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.rfd_id = typeof params.id === 'string' ? params.id : null;
      this.rfd_table_name = typeof params.tableName === 'string' ? params.tableName : null;
      this.getCodeInfo(this.rfd_id);
    });

    this.showTranslationsList = this.appDataService.multilanguageExists();
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
    this.codesTranslations = [];
    this.getCodes(pagingParams, params, extendedParams);
  }

  getCodes(pagingParams: Spr_paging_ot, params: Map<string, unknown>, extendedParams: ExtendedSearchParam[]): void {
    this.adminService
      .getCodesList(this.rfd_table_name, pagingParams, params, extendedParams)
      .pipe(
        tap((result) => {
          this.rowMenuItems = {};
          result.data.forEach((row: SprCodesTranslationsBrowseRow) => {
            this.rowMenuItems[row.rfc_id] = this.getActions(row);
          });
        })
      )
      .subscribe((tableData: BrowseFormSearchResult<SprCodesTranslationsBrowseRow>) => {
        this.codesList = [];
        this.codesTranslations = [];
        for (const code of tableData.data) {
          this.codesList = this.codesList.filter((row) => row.rfc_id != code.rfc_id);
          this.codesList.push(code);
        }
        const data = this.codesList;
        for (let i = 0; i < data.length; i++) {
          if (i != 0) {
            this.addWithoutTranslation(i - 1, data);
          }
          this.tempTranslations = [];
          for (let j = 0; j < this.translationsList.length; j++) {
            if (
              (this.tempTranslations.length === 0 && this.translationsList[j].rft_rfc_id == data[i].rfc_id) ||
              (this.tempTranslations.length != 0 &&
                this.codesTranslations.length != 0 &&
                this.tempTranslations[this.tempTranslations.length - 1].rft_rfc_id !=
                  this.translationsList[j].rft_rfc_id &&
                data[i].rfc_id == this.translationsList[j].rft_rfc_id)
            ) {
              this.tempTranslations.push({
                rft_id: this.translationsList[j].rft_id,
                rft_lang: this.translationsList[j].rft_lang,
                rft_display_code: this.translationsList[j].rft_display_code,
                rft_description: this.translationsList[j].rft_description,
                rft_rfc_id: this.translationsList[j].rft_rfc_id,
              });
              this.codesTranslations.push({
                rfc_id: data[i].rfc_id,
                rfc_code: data[i].rfc_code,
                rfc_domain: data[i].rfc_domain,
                rfc_meaning: data[i].rfc_meaning,
                rfc_rfd_id: data[i].rfc_rfd_id,
                rfc_parent_domain: data[i].rfc_parent_domain,
                rfc_parent_domain_code: data[i].rfc_parent_domain_code,
                rfc_description: data[i].rfc_description,
                rfc_date_from: data[i].rfc_date_from,
                rfc_date_to: data[i].rfc_date_to,
                rfc_order: data[i].rfc_order,
                rft: this.tempTranslations,
                availableActions: data[i].availableActions,
              });
            }
            if (
              (this.tempTranslations.length != 0 &&
                this.codesTranslations.length === 0 &&
                this.translationsList[j].rft_rfc_id == data[i].rfc_id &&
                this.tempTranslations[this.tempTranslations.length - 1].rft_lang !=
                  this.translationsList[j].rft_lang) ||
              (this.tempTranslations.length != 0 &&
                this.codesTranslations.length != 0 &&
                this.tempTranslations[this.tempTranslations.length - 1].rft_lang != this.translationsList[j].rft_lang &&
                this.tempTranslations[this.tempTranslations.length - 1].rft_rfc_id ==
                  this.translationsList[j].rft_rfc_id &&
                data[i].rfc_id == this.translationsList[j].rft_rfc_id) ||
              (this.tempTranslations.length === 0 &&
                j != 0 &&
                this.translationsList[j - 1].rft_rfc_id == data[i].rfc_id)
            ) {
              this.tempTranslations.push({
                rft_id: this.translationsList[j].rft_id,
                rft_lang: this.translationsList[j].rft_lang,
                rft_display_code: this.translationsList[j].rft_display_code,
                rft_description: this.translationsList[j].rft_description,
                rft_rfc_id: this.translationsList[j].rft_rfc_id,
              });
            }
          }
          if (i >= 0) {
            this.addWithoutTranslation(i, data);
          }
        }
        this.exportData = this.codesTranslations;
        this.totalRecords = this.codesTranslations.length;
      });
  }

  getTranslations(p_table_name: string): void {
    this.adminService.getTranslationsList(p_table_name).subscribe((result) => {
      this.translationsList = result;
    });
  }

  addWithoutTranslation(i: number, codes: SprCodesTranslationsBrowseRow[]): void {
    if (this.tempTranslations.length === 1 && this.codesTranslations[i].rft.length === 1) {
      if (this.codesTranslations[i].rft[0].rft_lang == ClsfSprLanguage.En) {
        this.codesTranslations[i].rft.push({
          rft_id: null,
          rft_lang: ClsfSprLanguage.Lt,
          rft_display_code: null,
          rft_description: null,
          rft_rfc_id: codes[i].rfc_id,
        });
      } else {
        this.codesTranslations[i].rft.push({
          rft_id: null,
          rft_lang: ClsfSprLanguage.En,
          rft_display_code: null,
          rft_description: null,
          rft_rfc_id: codes[i].rfc_id,
        });
      }
    }
    if (this.tempTranslations.length === 0) {
      this.tempTranslations.push(
        {
          rft_id: null,
          rft_lang: ClsfSprLanguage.En,
          rft_display_code: null,
          rft_description: null,
          rft_rfc_id: codes[i].rfc_id,
        },
        {
          rft_id: null,
          rft_lang: ClsfSprLanguage.Lt,
          rft_display_code: null,
          rft_description: null,
          rft_rfc_id: codes[i].rfc_id,
        }
      );
      this.codesTranslations.push({
        rfc_id: codes[i].rfc_id,
        rfc_code: codes[i].rfc_code,
        rfc_domain: codes[i].rfc_domain,
        rfc_meaning: codes[i].rfc_meaning,
        rfc_rfd_id: codes[i].rfc_rfd_id,
        rfc_parent_domain: codes[i].rfc_parent_domain,
        rfc_parent_domain_code: codes[i].rfc_parent_domain_code,
        rfc_description: codes[i].rfc_description,
        rfc_order: codes[i].rfc_order,
        rfc_date_from: codes[i].rfc_date_from,
        rfc_date_to: codes[i].rfc_date_to,
        rft: this.tempTranslations,
        availableActions: codes[i].availableActions,
      });
    }
  }

  getCodeInfo(id: string): void {
    this.adminService.getDictionaryRecord(id).subscribe((tableData: SprRefDictionariesDAO) => {
      this.rfd_description = tableData.rfd_description;
      this.rfd_name = tableData.rfd_name;
    });
  }

  protected deleteRecord(id: string): void {
    this.adminService.delRefCode(id).subscribe(() => {
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translate: string) => {
        this.commonFormServices.appMessages.showSuccess('', translate);
      });
      this.codesTranslations = this.codesTranslations.filter((code) => code.rfc_id.toString() != id);
      this.translationsList = this.translationsList.filter((translation) => translation.rft_rfc_id.toString() != id);
      this.codesList = this.codesList.filter((code) => code.rfc_id.toString() != id);
    });
  }

  getActions(row: SprCodesTranslationsBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          icon: getActionIcons(action as ActionsEnum),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              void this.router.navigate([this.router.url, RoutingConst.EDIT, row.rfc_id]);
            }
            if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(row.rfc_id.toString());
            }
          },
        });
      });
    return menu;
  }

  editTranslations(row: SprCodesTranslationsBrowseRow): void {
    this.form.reset();
    this.actionDialog = true;
    this.form.controls.rft_id.setValue(row.rft_id);
    this.form.controls.rft_lang.setValue(row.rft_lang);
    this.form.controls.rft_display_code.setValue(row.rft_display_code);
    this.form.controls.rft_description.setValue(row.rft_description);
    this.form.controls.rft_rfc_id.setValue(row.rft_rfc_id);
  }

  onCancel(): void {
    this.actionDialog = false;
  }

  onSaveRft(): void {
    let translation;
    this.adminService
      .setTranslation({
        rft_id: this.form.controls.rft_id.value as number,
        rft_lang: this.form.controls.rft_lang.value as ClsfSprLanguage,
        rft_display_code: this.form.controls.rft_display_code.value as string,
        rft_description: this.form.controls.rft_description.value as string,
        rft_rfc_id: this.form.controls.rft_rfc_id.value as number,
      })
      .subscribe((response) => {
        this.actionDialog = false;
        translation = this.translationsList.find((translation) => translation.rft_id === response.rft_id);
        if (translation == null) {
          this.translationsList.push(response);
        } else {
          translation.rft_description = this.form.controls.rft_description.value as string;
          translation.rft_display_code = this.form.controls.rft_display_code.value as string;
        }
        this.reload();
        this.commonFormServices.appMessages.showSuccess(
          '',
          this.commonFormServices.translate.instant('common.message.saveSuccess') as string
        );
      });
  }

  onTranslationDelete(rft: RefTranslationsObj): void {
    if (rft.rft_id === null) {
      this.commonFormServices.translate.get('common.message.noTranslationEntered').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showInfo('', translation);
      });
    } else {
      this.commonFormServices.translate.get('common.message.deleteConfirmationMsg').subscribe((translation: string) => {
        this.confirmationService.confirm({
          message: translation,
          accept: () => {
            this.adminService.delTranslationRecord(rft.rft_id.toString()).subscribe(() => {
              this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
                this.commonFormServices.appMessages.showSuccess('', translation);
              });
              this.translationsList = this.translationsList.filter((translation) => translation.rft_id != rft.rft_id);
              this.reload();
            });
          },
        });
      });
    }
  }

  backToDictionaries(): void {
    void this.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.ADMIN,
      RoutingConst.SPARK_REF_DICTIONARIES_BROWSE,
    ]);
  }
}
