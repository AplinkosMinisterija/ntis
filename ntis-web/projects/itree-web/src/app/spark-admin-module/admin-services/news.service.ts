/**
 *
 *
 * @note Po naujienų puslapių ir susijusių elementų perkėlimo iš "Spark"
 * prototipo, pakeitimai buvo atlikti pagal NTIS projektą.
 *
 * @note Metodui view() pridėtas roles tikrinimas. Jei rolė yra PUBLIC, atitinkamas URL pridedamas į užklausą .
 *  <security:http pattern="/rest/spark-news/public/view/*" security="none" />
 *
 * @note Metodui getList() pridėtas roles tikrinimas. Jei rolė yra PUBLIC, atitinkamas URL pridedamas į užklausą .
 *  <security:http pattern="/rest/spark-news/public/list*" security="none" />
 *
 * @date 2023-05-24
 *
 */

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExtendedSearchParam, Spr_paging_ot, getLang } from '@itree/ngx-s2-commons';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  BrowseFormSearchResult,
  NewsCommentModel,
  NtisNewsEditModel,
  SprNewsCommentsDAO,
} from '@itree-commons/src/lib/model/api/api';

import { Observable } from 'rxjs';
import { NewsBrowseRow } from '../models/browse-pages';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Injectable({
  providedIn: 'root',
})

/**
 *
 * @description This class provides methods for accessing and manipulating news data using HTTP requests.
 *
 */
export class NewsService {
  readonly SPARK_NEWS_REST_URL = REST_API_BASE_URL + '/spark-news';
  readonly NTIS_NEWS_REST_URL = REST_API_BASE_URL + '/ntis-news';
  readonly routingConst = RoutingConst;
  constructor(private http: HttpClient) {}

  /**
   * @description This method returns a list of news items that match the given paging and search parameters.
   * @param {Spr_paging_ot} pagingParams - The object that contains the page number, page size and sort order.
   * @param {Map<string, unknown>} searchParams - The map that contains the search criteria as key-value pairs.
   * @param {ExtendedSearchParam[]} extendedParams - The array that contains the additional search parameters.
   * @return {Observable<BrowseFormSearchResult<NewsBrowseRow>>} An observable that emits the result of the search, which includes the total count and the list of news items.
   */
  getList(
    extendedParams: ExtendedSearchParam[],
    pagingParams: Spr_paging_ot,
    isPublic: boolean
  ): Observable<BrowseFormSearchResult<NewsBrowseRow>> {
    const urlPart = isPublic
      ? `${this.NTIS_NEWS_REST_URL}/${this.routingConst.PUBLIC}/get-news-list`
      : `${this.NTIS_NEWS_REST_URL}/get-news-list`;
    const requestBody = { pagingParams, extendedParams };
    return this.http.post<BrowseFormSearchResult<NewsBrowseRow>>(`${urlPart}`, requestBody);
  }

  /**
   * @description This method saves a news item to the database and returns the updated data.
   * @param {NtisNewsEditModel} formData - The object that contains the news item data to be saved.
   * @return {Observable<NtisNewsEditModel>} An observable that emits the saved news item data.
   */
  save(formData: NtisNewsEditModel): Observable<NtisNewsEditModel> {
    return this.http.post<NtisNewsEditModel>(`${this.NTIS_NEWS_REST_URL}/save-nw`, formData);
  }

  /**
   * @description This method retrieves a news item from the database by its record id.
   * @param {string} id - The string that represents the unique identifier of the news item.
   * @return {Observable<NtisNewsEditModel>} An observable that emits the news item data.
   */
  get(id: string): Observable<NtisNewsEditModel> {
    return this.http.get<NtisNewsEditModel>(`${this.NTIS_NEWS_REST_URL}/get-nw/${id}`);
  }

  /**
   * @description This method deletes a news item from the database by its record id and returns the deleted data.
   * @param {string} id - The string that represents the unique identifier of the news item to be deleted.
   */
  delete(id: string): Observable<void> {
    return this.http.post<void>(`${this.SPARK_NEWS_REST_URL}/delete`, { id });
  }

  /**
   * @description This method retrieves a news item from the database by its record id and increments its view count.
   * @param {string} id - The string that represents the unique identifier of the news item to be viewed.
   * @return {Observable<NtisNewsEditModel>} An observable that emits the viewed news item data with the updated view count.
   */
  view(id: string, isPublic: boolean): Observable<NtisNewsEditModel> {
    const url = isPublic
      ? `${this.NTIS_NEWS_REST_URL}/${this.routingConst.PUBLIC}/view-nw/${id}`
      : `${this.NTIS_NEWS_REST_URL}/view-nw/${id}`;
    const paramArray: [string, string][] = [
      ['lang', getLang()],
      ['id', id],
    ];
    const myPostBody = { params: paramArray };
    return this.http.post<NtisNewsEditModel>(url, myPostBody);
  }

  /**
   * @description This method adds a comment to a news item and returns the updated list of comments for that news item.
   * @param {SprNewsCommentsDAO} formData - The object that contains the comment data to be added, such as the author, content and record id of the news item.
   * @return {Observable<NewsCommentModel[]>} An observable that emits the list of comments for the news item with the added comment.
   */
  comment(formData: SprNewsCommentsDAO): Observable<NewsCommentModel[]> {
    return this.http.post<NewsCommentModel[]>(`${this.SPARK_NEWS_REST_URL}/comment`, formData);
  }

  /**
   * @description This method deletes a comment from a news item and returns the updated list of comments for that news item.
   * @param {number} id - The identifier of the comment to be deleted.
   * @return {Observable<NewsCommentModel[]>} An observable that emits the list of comments for the news item with the deleted comment.
   */
  deleteComment(commentData: NewsCommentModel): Observable<NewsCommentModel[]> {
    return this.http.post<NewsCommentModel[]>(`${this.SPARK_NEWS_REST_URL}/comment/delete`, commentData);
  }

  viewPageTemplate(pageTemplateType: string): Observable<NtisNewsEditModel> {
    const paramArray: [string, string][] = [
      ['lang', getLang()],
      ['pageTemplateType', pageTemplateType],
    ];
    const myPostBody = { params: paramArray };
    return this.http.post<NtisNewsEditModel>(`${this.NTIS_NEWS_REST_URL}/get-page-template`, myPostBody);
  }
}
