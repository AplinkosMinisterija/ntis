import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { BrowseFormSearchResult, FaqModel, IdKeyValuePair, SprFile } from '@itree-commons/src/lib/model/api/api';
import { AuthUtil, ExtendedSearchParam, Spr_paging_ot } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { FaqBrowseRow, FaqThemesBrowseRow } from '../models/browse-page';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';

@Injectable({
  providedIn: 'root',
})
export class NtisFaqService {
  readonly internalUrl = `${REST_API_BASE_URL}/ntis-faq`;
  readonly publicUrl = `${REST_API_BASE_URL}/ntis-faq-pub`;

  constructor(private http: HttpClient, private fileUploadService: FileUploadService) {}

  get url(): string {
    return AuthUtil.isLoggedIn() ? this.internalUrl : this.publicUrl;
  }

  getFaqThemes(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<FaqThemesBrowseRow>> {
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<FaqThemesBrowseRow>>(this.url + '/get-faq-themes-list', myPostBody);
  }

  getQuestionsAndAnswers(
    code: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<FaqBrowseRow>> {
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['fac_group', code]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<FaqBrowseRow>>(this.url + '/get-faq-list', myPostBody);
  }

  getQuestionGroupName(code: string): Observable<IdKeyValuePair> {
    return this.http.post<IdKeyValuePair>(this.url + '/get-faq-name', code);
  }

  setQuestionAnswer(faqRec: FaqModel): Observable<FaqModel> {
    return this.http.post<FaqModel>(this.url + '/set-question-answer', faqRec);
  }

  getQuestionAnswer(id: string): Observable<FaqModel> {
    return this.http.post<FaqModel>(this.url + '/get-question-answer', { id });
  }

  delQuestionAnswer(id: string): Observable<null> {
    return this.http.post<null>(this.url + '/del-question-answer', { id });
  }

  downloadFile(file: SprFile): void {
    this.fileUploadService.downloadFile(file, AuthUtil.isLoggedIn() ? '' : '/ntis-faq-pub' + '/get-file/');
  }
}
