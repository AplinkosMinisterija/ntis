import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { ExtendedSearchParam, Spr_paging_ot } from '@itree/ngx-s2-commons';
import { Observable, map } from 'rxjs';
import {
  ResearchNormsBrowseRow,
  ResearchNormsHistoryBrowseRow,
  SpResearchOrdersListBrowseRow,
} from '../models/browse-pages';
import {
  BrowseFormSearchResult,
  NtisOrdersDAO,
  NtisResearchNormEditModel,
  NtisResearchOrderModel,
  NtisWtfInfo,
  ResearchCriteriaResultsModel,
} from '@itree-commons/src/lib/model/api/api';
import { ResearchesListBrowseRow } from '../models/browse-pages';
import { OwnerResearchOrdersListBrowseRow } from '../models/browse-pages';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';

@Injectable({
  providedIn: 'root',
})
export class ResearchService {
  readonly restUrl = `${REST_API_BASE_URL}/ntis-research`;
  constructor(private http: HttpClient) {}

  // sp research orders list -- start
  getSpResearchOrdersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SpResearchOrdersListBrowseRow>> {
    const url = `${this.restUrl}/get-sp-research-orders`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SpResearchOrdersListBrowseRow>>(url, myPostBody);
  }
  // sp research orders list -- end

  // research norms management -- start
  getResearchNormsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ResearchNormsBrowseRow>> {
    const url = `${this.restUrl}/get-research-norms`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ResearchNormsBrowseRow>>(url, myPostBody);
  }

  setResearchNorm(researchNorm: NtisResearchNormEditModel): Observable<null> {
    const url = `${this.restUrl}/set-research-norm`;
    return this.http.post<null>(url, researchNorm);
  }
  //   research norms management -- end

  // research providers list start
  getLaboratoriesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ResearchesListBrowseRow>> {
    const url = `${this.restUrl}/get-research-providers-list`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ResearchesListBrowseRow>>(url, myPostBody);
  }

  getSelectedWtfInfo(): Observable<NtisWtfInfo> {
    const url = `${this.restUrl}/get-selected-wtf`;
    return this.http.get<NtisWtfInfo>(url);
  }
  // research providers list endy

  // research outside ntis create start
  checkIfHasResearch(): Observable<boolean> {
    const url = `${this.restUrl}/check-if-has-research`;
    return this.http.post<boolean>(url, null);
  }

  checkResearchId(): Observable<number> {
    const url = `${this.restUrl}/check-research-id`;
    return this.http.post<number>(url, null);
  }

  getCurrentNorms(id: number): Observable<ResearchCriteriaResultsModel[]> {
    const url = `${this.restUrl}/get-current-norms`;
    return this.http.post<ResearchCriteriaResultsModel[]>(url, id.toString());
  }

  saveNewOrder(order: NtisResearchOrderModel): Observable<NtisOrdersDAO> {
    return this.http.post<NtisOrdersDAO>(`${this.restUrl}/save-new-order`, order);
  }

  sendOrderNotifications(orderId: number): Observable<void> {
    const url = `${this.restUrl}/send-order-notifications`;
    return this.http.post<void>(url, orderId.toString());
  }

  sendOutsideOrderNotifications(orderId: number): Observable<void> {
    const url = `${this.restUrl}/send-outside-order-notifications`;
    return this.http.post<void>(url, orderId.toString());
  }
  // research outside ntis create end

  //research norms history start
  getResearchNormsHistory(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ResearchNormsHistoryBrowseRow>> {
    const url = `${this.restUrl}/get-research-norms-history`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ResearchNormsHistoryBrowseRow>>(url, myPostBody);
  }
  // research norms history end

  // owner research orders list start
  getOwnerResearchOrdersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OwnerResearchOrdersListBrowseRow>> {
    const url = `${this.restUrl}/get-owner-research-orders`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OwnerResearchOrdersListBrowseRow>>(url, myPostBody);
  }
  // owner research orders list end

  // research order page start
  getResearchOrderDetails(id: string): Observable<NtisResearchOrderModel> {
    const url = `${this.restUrl}/get-order-info`;
    return this.http.post<NtisResearchOrderModel>(url, id).pipe(
      map((result) => {
        result.selectedCriteria.forEach((criteria) => {
          criteria.isSelected = criteria.belongs === DB_BOOLEAN_TRUE ? true : false;
        });
        return result;
      })
    );
  }

  getResearchOrderDetailsByToken(token: string): Observable<number> {
    const url = `${this.restUrl}/get-order-info-by-token`;
    return this.http.post<number>(url, token);
  }

  updateOrder(order: NtisResearchOrderModel): Observable<null> {
    const url = `${this.restUrl}/update-order`;
    return this.http.post<null>(url, order);
  }
  // research order page end

  // inspector research orders list start
  getInspectorResearchOrdersList(
    wtfId: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OwnerResearchOrdersListBrowseRow>> {
    const url = `${this.restUrl}/get-inspector-research-orders`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['wtfId', wtfId]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OwnerResearchOrdersListBrowseRow>>(url, myPostBody);
  }
  // inspector research orders list end
}
