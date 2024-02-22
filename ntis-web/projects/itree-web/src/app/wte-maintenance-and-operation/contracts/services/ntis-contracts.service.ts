import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  BrowseFormSearchResult,
  ForeignKeyParams,
  NtisAddressSuggestion,
  NtisContractEditModel,
  NtisContractRequestComment,
  NtisContractSignReq,
  NtisContractUploadRequest,
  NtisNewContractRequest,
  NtisNewContractRequestInfo,
  NtisServiceSearchRequest,
  NtisServiceSearchResult,
  NtisSubmitContractRequestInfo,
  SprOrganizationsDAO,
  Spr_paging_ot,
} from '@itree-commons/src/lib/model/api/api';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { ContractsListBrowseRow } from '../models/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class NtisContractsService {
  readonly contractsRestUrl = `${REST_API_BASE_URL}/ntis-contracts`;

  constructor(private http: HttpClient) {}

  // Service search (start)
  getSelectedWtfInfo(): Observable<NtisServiceSearchRequest> {
    return this.http.get<NtisServiceSearchRequest>(`${this.contractsRestUrl}/get-selected-wtf`);
  }

  getAddressSuggestions(text: string): Observable<NtisAddressSuggestion[]> {
    return this.http.get<NtisAddressSuggestion[]>(`${this.contractsRestUrl}/search-address/${text}`);
  }

  serviceSearch(
    lksX: number,
    lksY: number,
    distanceToObject: number,
    equipmentType: string,
    carCapacity: number,
    services: string[],
    lang: string,
    priceClause?: string,
    ratingClause?: string
  ): Observable<NtisServiceSearchResult[]> {
    const url = `${this.contractsRestUrl}/service-search`;
    const body: NtisServiceSearchRequest = {
      address: null,
      lksX,
      lksY,
      carCapacity,
      distanceToObject,
      equipmentType,
      services,
      lang,
      priceClause,
      ratingClause,
    };
    return this.http.post<NtisServiceSearchResult[]>(url, body);
  }
  // Service search (end)

  // Contract requests (start)
  loadNewContractRequest(record: NtisNewContractRequest): Observable<NtisNewContractRequestInfo> {
    const url = `${this.contractsRestUrl}/load-new-contract-request`;
    return this.http.post<NtisNewContractRequestInfo>(url, record);
  }

  submitContractRequest(submitContractRequestInfo: NtisSubmitContractRequestInfo): Observable<null> {
    const url = `${this.contractsRestUrl}/submit-contract-request`;
    return this.http.post<null>(url, submitContractRequestInfo);
  }
  // Contract requests (end)

  // Contracts list (start)
  checkServices(): Observable<boolean> {
    const url = `${this.contractsRestUrl}/check-sp-services`;
    return this.http.post<boolean>(url, null);
  }
  getContractsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ContractsListBrowseRow>> {
    const url = `${this.contractsRestUrl}/get-contracts`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ContractsListBrowseRow>>(url, postBody);
  }

  findServiceProviders(value: ForeignKeyParams): Observable<CommonResult<SprOrganizationsDAO>> {
    return this.http.post<CommonResult<SprOrganizationsDAO>>(`${this.contractsRestUrl}/find-service-providers`, value);
  }
  // Contracts list (end)

  // Contract edit (start)
  loadContractInfo(contractId: number): Observable<NtisContractEditModel> {
    const url = `${this.contractsRestUrl}/load-contract`;
    return this.http.post<NtisContractEditModel>(url, contractId.toString());
  }

  getContractByToken(token: string): Observable<NtisContractEditModel> {
    const url = `${this.contractsRestUrl}/get-contract-by-token`;
    return this.http.post<NtisContractEditModel>(url, token);
  }

  updateContract(record: NtisContractEditModel): Observable<null> {
    const url = `${this.contractsRestUrl}/update-contract`;
    return this.http.post<null>(url, record);
  }

  loadContractComments(contractId: number): Observable<NtisContractRequestComment[]> {
    const url = `${this.contractsRestUrl}/load-contract-comments`;
    return this.http.post<NtisContractRequestComment[]>(url, contractId.toString());
  }

  saveContractComment(record: NtisContractRequestComment): Observable<null> {
    const url = `${this.contractsRestUrl}/save-contract-comment`;
    return this.http.post<null>(url, record);
  }

  deleteContractComment(comment: NtisContractRequestComment): Observable<null> {
    const url = `${this.contractsRestUrl}/delete-contract-comment`;
    return this.http.post<null>(url, comment);
  }

  viewAndSignContract(cotId: number): Observable<NtisContractSignReq> {
    const url = `${this.contractsRestUrl}/sign-contract`;
    return this.http.post<NtisContractSignReq>(url, cotId.toString());
  }

  previewContract(cotId: number): Observable<NtisContractSignReq> {
    const url = `${this.contractsRestUrl}/preview-contract`;
    return this.http.post<NtisContractSignReq>(url, cotId.toString());
  }

  handleContractSignNotifications(contractBeforeSign: NtisContractEditModel): Observable<void> {
    const url = `${this.contractsRestUrl}/contract-sign-notifications`;
    return this.http.post<void>(url, contractBeforeSign);
  }
  // Contract edit (end)
  // Contract upload (start)
  loadTempContract(request: NtisContractUploadRequest): Observable<NtisContractEditModel> {
    const url = `${this.contractsRestUrl}/load-temp-contract`;
    return this.http.post<NtisContractEditModel>(url, request);
  }

  saveContract(record: NtisContractEditModel): Observable<NtisContractEditModel> {
    const url = `${this.contractsRestUrl}/save-contract`;
    return this.http.post<NtisContractEditModel>(url, record);
  }

  loadUploadedContract(cotId: number): Observable<NtisContractEditModel> {
    const url = `${this.contractsRestUrl}/load-uploaded-contract`;
    return this.http.post<NtisContractEditModel>(url, cotId.toString());
  }

  deleteContract(cotId: number): Observable<null> {
    const url = `${this.contractsRestUrl}/delete-contract`;
    return this.http.post<null>(url, cotId.toString());
  }

  sendNotifications(cotId: number): Observable<void> {
    const url = `${this.contractsRestUrl}/send-notifications`;
    return this.http.post<void>(url, cotId.toString());
  }

  loadUploadedContractByToken(token: string): Observable<NtisContractEditModel> {
    const url = `${this.contractsRestUrl}/load-uploaded-contract-by-token`;
    return this.http.post<NtisContractEditModel>(url, token);
  }
  // Contract upload (end)
}
