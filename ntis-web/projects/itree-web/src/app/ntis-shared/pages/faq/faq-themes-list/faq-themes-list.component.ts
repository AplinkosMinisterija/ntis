import { Component } from '@angular/core';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { FaqThemesBrowseRow } from '../../../models/browse-page';
import { NtisFaqService } from '../../../services/ntis-faq.service';

@Component({
  selector: 'ntis-faq-themes-list',
  templateUrl: './faq-themes-list.component.html',
  styleUrls: ['./faq-themes-list.component.scss'],
})
export class FaqThemesListComponent extends BaseBrowseForm<FaqThemesBrowseRow> {
  readonly formTranslationsReference = 'pages.sprFaq';

  constructor(private ntisFaqService: NtisFaqService, protected override commonFormServices: CommonFormServices) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
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

    this.ntisFaqService.getFaqThemes(pagingParams, params, extendedParams).subscribe((row) => {
      this.data = row.data;
      this.totalRecords = row.paging.cnt;
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }
}
