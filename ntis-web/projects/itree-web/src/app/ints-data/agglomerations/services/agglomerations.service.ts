import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  BrowseFormSearchResult,
  NtisAggloRejectRequest,
  NtisAgglomerationVersionsDAO,
  NtisAgglomerationsDAO,
  NtisRejectedAggloVersion,
  NtisSubmitAggloData,
  NtisSubmittedAggloData,
  RecordIdentifier,
} from '@itree-commons/src/lib/model/api/api';
import { ExtendedSearchParam, Spr_paging_ot } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { SubmittedAggloDataListRow } from '../models/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class AgglomerationsService {
  readonly restUrl = `${REST_API_BASE_URL}/ntis-agglo`;

  constructor(private http: HttpClient) {}

  getSubmittedAggloDataList(
    pagingParams: Spr_paging_ot,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SubmittedAggloDataListRow>> {
    return this.http.post<BrowseFormSearchResult<SubmittedAggloDataListRow>>(
      `${this.restUrl}/get-submitted-agglo-data-list`,
      { pagingParams, extendedParams }
    );
  }

  submitAgglomeration(data: NtisSubmitAggloData): Observable<NtisAgglomerationsDAO> {
    return this.http.post<NtisAgglomerationsDAO>(`${this.restUrl}/submit-agglomeration`, data);
  }

  sendSubmittedAggloNotification(aggId: number): Observable<void> {
    const url = `${this.restUrl}/send-submitted-notifications`;
    return this.http.post<void>(url, aggId.toString());
  }

  sendAggloNotifications(aggId: number): Observable<void> {
    const url = `${this.restUrl}/send-agglo-notifications`;
    return this.http.post<void>(url, aggId.toString());
  }

  loadUploadedAggloByTokenToken(token: string): Observable<NtisSubmittedAggloData> {
    const url = `${this.restUrl}/get-agglo-by-token`;
    return this.http.post<NtisSubmittedAggloData>(url, token);
  }

  submitNewData(data: NtisSubmitAggloData): Observable<NtisAgglomerationsDAO> {
    return this.http.post<NtisAgglomerationsDAO>(`${this.restUrl}/submit-new-data`, data);
  }

  getAggloData(id: string): Observable<NtisSubmittedAggloData> {
    const body: RecordIdentifier = { id };
    return this.http.post<NtisSubmittedAggloData>(`${this.restUrl}/get-agglo-data`, body);
  }

  approveAgglo(id: number): Observable<NtisSubmittedAggloData> {
    const body: RecordIdentifier = { id: `${id}` };
    return this.http.post<NtisSubmittedAggloData>(`${this.restUrl}/approve-agglo`, body);
  }

  rejectAgglo(request: NtisAggloRejectRequest): Observable<NtisSubmittedAggloData> {
    return this.http.post<NtisSubmittedAggloData>(`${this.restUrl}/reject-agglo`, request);
  }

  getRejectionDetails(avId: string): Observable<NtisRejectedAggloVersion> {
    const body: RecordIdentifier = { id: avId };
    return this.http.post<NtisRejectedAggloVersion>(`${this.restUrl}/get-rejection-details`, body);
  }
}
