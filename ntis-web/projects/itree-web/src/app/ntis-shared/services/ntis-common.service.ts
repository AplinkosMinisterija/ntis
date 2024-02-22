import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import {
  BrowseFormSearchResult,
  ForeignKeyParams,
  IdKeyValuePair,
  NtisAddrSearchRequest,
  NtisAddrSearchResult,
  NtisAdrResidencesDAO,
  NtisAdrStreetsDAO,
  NtisCheckWtfSelectionRequest,
  NtisCheckWtfSelectionResponse,
  NtisNotificationViewModel,
  NtisReviewCreationModel,
  NtisReviewsDAO,
  NtisWtfInfo,
  Spr_paging_ot,
} from '@itree-commons/src/lib/model/api/api';
import { Observable } from 'rxjs';
import { NtisNotificationsBrowseRow } from '../models/browse-page';
import { SelectOption } from '../../wte-maintenance-and-operation/tech-support/models/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class NtisCommonService {
  readonly restUrl = `${REST_API_BASE_URL}/ntis-common`;

  constructor(private http: HttpClient) {}

  getReviewInfo(id: string): Observable<NtisReviewsDAO> {
    const url = `${this.restUrl}/get-review-info`;
    return this.http.post<NtisReviewsDAO>(url, id);
  }

  // ----------- ntis review start --------------

  getNtisReview(id: string): Observable<NtisReviewCreationModel> {
    return this.http.post<NtisReviewCreationModel>(`${this.restUrl}/get-review`, id);
  }

  saveNtisReview(role: NtisReviewsDAO): Observable<NtisReviewsDAO> {
    return this.http.post<NtisReviewsDAO>(`${this.restUrl}/save-review`, role);
  }

  // ----------- ntis review end --------------

  requestEmailConfirmation(email: string): Observable<void> {
    return this.http.post<void>(`${this.restUrl}/request-confirm-email`, email);
  }

  checkWtfSelection(params: NtisCheckWtfSelectionRequest): Observable<NtisCheckWtfSelectionResponse> {
    return this.http.post<NtisCheckWtfSelectionResponse>(`${this.restUrl}/check-wtf-selection`, params);
  }

  selectWtf(wtfId: number): Observable<void> {
    return this.http.post<void>(`${this.restUrl}/select-wtf`, wtfId.toString());
  }

  getSelectedWtf(): Observable<string> {
    return this.http.post<string>(`${this.restUrl}/get-selected-wtf`, null);
  }

  //------- NTIS notifications START--------
  getNotificationList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisNotificationsBrowseRow>> {
    const url = this.restUrl + '/get-notifications-list';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisNotificationsBrowseRow>>(url, myPostBody);
  }

  markNotification(ntfId: number, action: string): Observable<void> {
    const url = this.restUrl + '/mark-notification';
    return this.http.post<void>(url, { id: ntfId, actionType: action });
  }

  loadNotification(ntfId: number): Observable<NtisNotificationViewModel> {
    const url = this.restUrl + '/load-notification';
    return this.http.post<NtisNotificationViewModel>(url, ntfId.toString());
  }
  //------- NTIS notifications END--------
  findFacilities(value: ForeignKeyParams): Observable<CommonResult<NtisAddrSearchRequest>> {
    return this.http.post<CommonResult<NtisAddrSearchRequest>>(`${this.restUrl}/find-facilities`, value);
  }

  getWtfInfo(value: number): Observable<NtisWtfInfo> {
    const url = `${this.restUrl}/get-faci-info`;
    return this.http.post<NtisWtfInfo>(url, value.toString());
  }

  onDetailedFaciSearch(template: NtisAddrSearchRequest): Observable<NtisAddrSearchResult[]> {
    const url = `${this.restUrl}/search-facility`;
    return this.http.post<NtisAddrSearchResult[]>(url, template);
  }

  getResidencesList(value: number): Observable<NtisAdrResidencesDAO[]> {
    const url = `${this.restUrl}/get-residences`;
    return this.http.post<NtisAdrResidencesDAO[]>(url, value.toString());
  }

  getStreetsList(value: number): Observable<NtisAdrStreetsDAO[]> {
    const url = `${this.restUrl}/get-streets`;
    return this.http.post<NtisAdrStreetsDAO[]>(url, value.toString());
  }

  getWtfList(): Observable<CommonResult<SelectOption>> {
    const url = `${this.restUrl}/get-wtf-list`;
    return this.http.get<CommonResult<SelectOption>>(url);
  }

  getSenList(): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-sen-list`;
    return this.http.get<CommonResult<IdKeyValuePair>>(url);
  }

  getSenMuniList(mnId: string): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-sen-muni`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, mnId.toString());
  }

  getResMuniList(mnId: string): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-res-muni`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, mnId.toString());
  }

  getResSenList(snId: string): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-res-sen`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, snId.toString());
  }

  getStreetResList(reId: string): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-streets-res`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, reId.toString());
  }

  getOrganizations(): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-organizations`;
    return this.http.get<CommonResult<IdKeyValuePair>>(url);
  }

  getSelectedWtfInfo(wtfId: string): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.restUrl}/get-selected-wtf-info`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, wtfId);
  }
}
