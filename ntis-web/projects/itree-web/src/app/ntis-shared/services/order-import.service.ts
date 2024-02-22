import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  BrowseFormSearchResult,
  NtisAdrMappingsDAO,
  NtisOrdImportFile,
  OrgDetailsForOrderImport,
  RecordIdentifier,
  SprFile,
  Spr_paging_ot,
} from '@itree-commons/src/lib/model/api/api';
import { ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import {
  NtisOrdersImportBrowseRow,
  OrderImportDisposalLinesListRow,
  OrderImportErrorDataListRow,
  OrderImportFileDataListRow,
  OrderImportResearchLinesListRow,
} from '../models/browse-page';
import { CwAddressMappingsBrowseRow } from '../../ints-data/centralized-wastewater-data/models/browse-pages';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { FacilityForErrors } from '../models/facility-for-errors';

@Injectable({
  providedIn: 'root',
})
export class OrderImportService {
  readonly restUrl = `${REST_API_BASE_URL}/ntis-orders-import`;

  constructor(private http: HttpClient) {}

  // ORDERS IMPORT DATA LIST  -- START
  getOrderImports(
    orgId: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisOrdersImportBrowseRow>> {
    const url = `${this.restUrl}/get-orders-imports`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orgId', orgId]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisOrdersImportBrowseRow>>(url, postBody);
  }

  getOrderImportsForOrg(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisOrdersImportBrowseRow>> {
    const url = `${this.restUrl}/get-orders-imports-org`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisOrdersImportBrowseRow>>(url, postBody);
  }

  getOrgDetails(id: string): Observable<OrgDetailsForOrderImport[]> {
    const url = `${this.restUrl}/get-org-details`;
    return this.http.post<OrgDetailsForOrderImport[]>(url, id);
  }

  getOrgDetailsOrg(): Observable<OrgDetailsForOrderImport[]> {
    const url = `${this.restUrl}/get-org-details-for-org`;
    return this.http.post<OrgDetailsForOrderImport[]>(url, null);
  }

  uploadNewFile(fileKey: string, srvId: string, orgId: string): Observable<number> {
    const url = `${this.restUrl}/set-new-file`;
    const paramArray: [string, string][] = [
      ['file', fileKey],
      ['srvId', srvId],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<number>(url, postBody);
  }

  uploadNewFileOrg(fileKey: string, srvId: string): Observable<number> {
    const url = `${this.restUrl}/set-new-file-org`;
    const paramArray: [string, string][] = [
      ['file', fileKey],
      ['srvId', srvId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<number>(url, postBody);
  }

  deletePendingFile(id: string): Observable<null> {
    const url = `${this.restUrl}/del-pending-file`;
    return this.http.post<null>(url, id);
  }

  deletePendingFileOrg(id: string): Observable<null> {
    const url = `${this.restUrl}/del-pending-file-org`;
    return this.http.post<null>(url, id);
  }

  updateFileState(id: string): Observable<null> {
    const url = `${this.restUrl}/update-file-state`;
    return this.http.post<null>(url, id);
  }

  updateFileStateOrg(id: string): Observable<null> {
    const url = `${this.restUrl}/update-file-state-org`;
    return this.http.post<null>(url, id);
  }

  validateErrors(orfId: number, orgId: string): Observable<null> {
    const url = `${this.restUrl}/validate-errors`;
    const paramArray: [string, string][] = [
      ['orfId', orfId.toString()],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }

  validateErrorsOrg(orfId: number): Observable<null> {
    const url = `${this.restUrl}/validate-errors-org`;
    const paramArray: [string, string][] = [['orfId', orfId.toString()]];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }

  // ORDERS IMPORT DATA LIST  -- END

  // ORDER IMPORT VIEW PAGE  -- START
  getImportFile(id: string, orgId: string): Observable<NtisOrdImportFile> {
    const paramArray: [string, string][] = [
      ['orfId', id],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    const url = `${this.restUrl}/get-file-rec`;
    return this.http.post<NtisOrdImportFile>(url, postBody);
  }

  getLines(
    orfId: number,
    orgId: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportFileDataListRow>> {
    const url = `${this.restUrl}/get-data-lines`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', orfId]);
    paramArray.push(['orgId', orgId]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportFileDataListRow>>(url, postBody);
  }

  getResearchLines(
    orfId: number,
    orgId: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportResearchLinesListRow>> {
    const url = `${this.restUrl}/get-data-lines`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', orfId]);
    paramArray.push(['orgId', orgId]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportResearchLinesListRow>>(url, postBody);
  }

  getDisposalLines(
    orfId: number,
    orgId: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportDisposalLinesListRow>> {
    const url = `${this.restUrl}/get-data-lines`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', orfId]);
    paramArray.push(['orgId', orgId]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportDisposalLinesListRow>>(url, postBody);
  }

  getLinesOrg(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportFileDataListRow>> {
    const url = `${this.restUrl}/get-data-lines-org`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportFileDataListRow>>(url, postBody);
  }

  getResearchLinesOrg(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportResearchLinesListRow>> {
    const url = `${this.restUrl}/get-data-lines-org`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportResearchLinesListRow>>(url, postBody);
  }

  getDisposalLinesOrg(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportDisposalLinesListRow>> {
    const url = `${this.restUrl}/get-data-lines-org`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportDisposalLinesListRow>>(url, postBody);
  }

  getErrors(
    orfId: number,
    orgId: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportErrorDataListRow>> {
    const url = `${this.restUrl}/get-error-lines`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', orfId]);
    paramArray.push(['orgId', orgId]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportErrorDataListRow>>(url, postBody);
  }

  getErrorsOrg(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrderImportErrorDataListRow>> {
    const url = `${this.restUrl}/get-error-lines-org`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrderImportErrorDataListRow>>(url, postBody);
  }

  deleteFile(file: SprFile): Observable<null> {
    const url = `${this.restUrl}/del-file`;
    return this.http.post<null>(url, file);
  }

  getAvailableWtfs(orfdeId: string): Observable<CommonResult<FacilityForErrors>> {
    const url = `${this.restUrl}/get-available-wtfs`;
    return this.http.post<CommonResult<FacilityForErrors>>(url, orfdeId);
  }

  updateWithSelectedWtf(wtfId: string, orfdeId: string): Observable<null> {
    const url = `${this.restUrl}/update-selected-wtf`;
    const paramArray: [string, string][] = [
      ['wtfId', wtfId],
      ['orfdeId', orfdeId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }
  // ORDER IMPORT VIEW PAGE  -- END

  // ADDRESS MAPPING RELATED
  getAdrMappingsOrg(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<CwAddressMappingsBrowseRow>> {
    const url = `${this.restUrl}/get-adr-map-org`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<CwAddressMappingsBrowseRow>>(url, postBody);
  }

  getAdrMappingOrg(id: string, actionType?: string): Observable<NtisAdrMappingsDAO> {
    const url = `${this.restUrl}/get-adr-mapping-org`;
    const params: RecordIdentifier = { id: id, actionType: actionType };
    return this.http.post<NtisAdrMappingsDAO>(url, params);
  }

  setAdrMappingOrg(adrMapping: NtisAdrMappingsDAO): Observable<NtisAdrMappingsDAO> {
    const url = `${this.restUrl}/set-adr-mapping-org`;
    return this.http.post<NtisAdrMappingsDAO>(url, adrMapping);
  }

  setAdrMapping(adrMapping: NtisAdrMappingsDAO): Observable<NtisAdrMappingsDAO> {
    const url = `${this.restUrl}/set-adr-mapping`;
    return this.http.post<NtisAdrMappingsDAO>(url, adrMapping);
  }

  deleteAdrMappingOrg(id: string): Observable<null> {
    const url = `${this.restUrl}/del-adr-mapping-org`;
    return this.http.post<null>(url, id);
  }

  setFileForErrProcessing(orfId: string): Observable<null> {
    const url = `${this.restUrl}/set-file-for-processing`;
    return this.http.post<null>(url, orfId);
  }

  setFileForErrProcessingOrg(orfId: string): Observable<null> {
    const url = `${this.restUrl}/set-file-for-processing-org`;
    return this.http.post<null>(url, orfId);
  }

  revalidateErrors(orfId: string, orgId: string): Observable<null> {
    const url = `${this.restUrl}/revalidate-errors`;
    const paramArray: [string, string][] = [
      ['orfId', orfId],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }

  revalidateErrorsOrg(orfId: string): Observable<null> {
    const url = `${this.restUrl}/revalidate-errors-org`;
    return this.http.post<null>(url, orfId);
  }
}
