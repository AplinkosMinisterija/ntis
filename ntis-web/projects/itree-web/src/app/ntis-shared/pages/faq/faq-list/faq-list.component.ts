import { Component } from '@angular/core';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { tap } from 'rxjs';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { IdKeyValuePair, SprFile } from '@itree-commons/src/lib/model/api/api';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { DomSanitizer } from '@angular/platform-browser';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaqBrowseRow } from '../../../models/browse-page';
import { SPR_FAQ_LIST } from '../../../constants/forms.const';
import { NtisFaqService } from '../../../services/ntis-faq.service';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { ReactiveFormsModule } from '@angular/forms';
import { NtisSharedModule } from '../../../ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';

@Component({
  selector: 'ntis-faq-list',
  templateUrl: './faq-list.component.html',
  styleUrls: ['./faq-list.component.scss'],
  standalone: true,
  imports: [CommonModule, NtisSharedModule, ItreeCommonsModule, TableModule, CalendarModule, ReactiveFormsModule],
})
export class FaqListComponent extends BaseBrowseForm<FaqBrowseRow> {
  readonly formTranslationsReference = 'pages.sprFaq';
  readonly formCode = SPR_FAQ_LIST;
  questionGroup: string;
  groupName: string;
  menuActions: Record<string, MenuItem[]> = {};
  selectedId: number;
  displayEditDialog: boolean;
  questionGroupTitle: string = 'GR1';
  userCanCreate: boolean;
  fac_id: string;
  expandedFacId: { [key: string]: boolean } = {};
  link: string;

  constructor(
    private ntisFaqService: NtisFaqService,
    protected override commonFormServices: CommonFormServices,
    private route: ActivatedRoute,
    protected sanitizer: DomSanitizer,
    public router: Router,
    private fileUploadService: FileUploadService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ACTIONS_CREATE);
    this.route.params.subscribe((params) => {
      this.fac_id = params['fac_id'] as string;
      if (this.fac_id) {
        this.expandedFacId = { [this.fac_id]: true };
      }
    });
  }

  removeTailwindStyles(text: string): string {
    const pattern = /((tw-|hover:)[^\s]+)/g;
    const replacedText = text.replace(pattern, '');
    return replacedText;
  }
  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    this.route.params.subscribe((params) => {
      this.questionGroup = params['id'] as string;
    });
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.ntisFaqService
      .getQuestionsAndAnswers(this.questionGroup, pagingParams, params, extendedParams)
      .pipe(
        tap((res) => {
          this.menuActions = {};
          res.data.forEach((row) => {
            this.menuActions[row.fac_id] = this.getActions(row);
            row.link = `${window.location.href}/${row.fac_id}`;
          });
        }),
        tap()
      )
      .subscribe((row) => {
        this.data = row.data;
        this.totalRecords = row.paging.cnt;
      });
    this.ntisFaqService.getQuestionGroupName(this.questionGroup).subscribe((response: IdKeyValuePair) => {
      this.groupName = response.value;
    });
  }

  downloadFile(file: SprFile): void {
    this.ntisFaqService.downloadFile(file);
  }

  getActions(row: FaqBrowseRow): MenuItem[] {
    const actions: ActionsEnum[] = [...row.availableActions];
    return actions.map((action) => {
      const menuItem: MenuItem = {
        label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
        id: row.fac_id.toString(),
        icon: getActionIcons(action),
      };
      switch (action) {
        case ActionsEnum.ACTIONS_DELETE:
          menuItem.command = (): void => {
            this.deleteRecordWithConfirmation(row.fac_id.toString());
          };
          break;
      }
      switch (action) {
        case ActionsEnum.ACTIONS_UPDATE:
          menuItem.command = (): void => {
            void this.router.navigate([
              RoutingConst.INTERNAL,
              RoutingConst.SPARK_FAQ,
              RoutingConst.EDIT,
              row.fac_id,
              this.questionGroup,
            ]);
          };
          break;
      }
      switch (action) {
        case ActionsEnum.GET_LINK:
          menuItem.command = (): void => {
            void navigator.clipboard.writeText(row.link);
          };
          break;
      }
      return menuItem;
    });
  }

  createQuestion(): void {
    void this.router.navigate([
      RoutingConst.INTERNAL,
      RoutingConst.SPARK_FAQ,
      RoutingConst.EDIT,
      'new',
      this.questionGroup,
    ]);
  }

  protected deleteRecord(recordId: string): void {
    this.ntisFaqService.delQuestionAnswer(recordId).subscribe(() => {
      this.reload();
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showSuccess('', translation);
      });
    });
  }
}
