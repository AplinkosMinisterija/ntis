import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import {
  BrowseFormSearchResult,
  IdKeyValuePair,
  NtisAdrMappingsDAO,
  NtisCwFilesDAO,
  NtisWastewaterDataImportFile,
  RecordIdentifier,
  SprFile,
  Spr_paging_ot,
} from '@itree-commons/src/lib/model/api/api';
import { ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import {
  CentralizedWastewaterDataReportBrowseRow,
  CwAddressMappingsBrowseRow,
  CwDataListBrowseRow,
  WastewaterFileDataListRow,
  WastewaterFileErrorListRow,
} from '../models/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class CentralizedWastewaterDataService {
  readonly wwDataUrl = `${REST_API_BASE_URL}/ntis-centralized-data`;

  constructor(private http: HttpClient) {}

  // CENTRALIZED WASTEWATER DATA LIST -- START
  // ntis-admin related methods (start)
  getCentralizedWastewaterData(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<CwDataListBrowseRow>> {
    const url = `${this.wwDataUrl}/get-centralized-ww-data`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orgId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<CwDataListBrowseRow>>(url, postBody);
  }

  getOrgName(id: string): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.wwDataUrl}/get-org-name`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, id);
  }

  setAdrMapping(adrMapping: NtisAdrMappingsDAO): Observable<NtisAdrMappingsDAO> {
    const url = `${this.wwDataUrl}/set-adr-mapping`;
    return this.http.post<NtisAdrMappingsDAO>(url, adrMapping);
  }

  setFileForErrProcessing(cwfId: string): Observable<null> {
    const url = `${this.wwDataUrl}/set-file-for-processing`;
    return this.http.post<null>(url, cwfId);
  }

  revalidateErrors(cwfId: string, orgId: string): Observable<null> {
    const url = `${this.wwDataUrl}/revalidate-errors`;
    const paramArray: [string, string][] = [
      ['cwfId', cwfId],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }

  setAdrMappingOrg(adrMapping: NtisAdrMappingsDAO): Observable<NtisAdrMappingsDAO> {
    const url = `${this.wwDataUrl}/set-adr-mapping-org`;
    return this.http.post<NtisAdrMappingsDAO>(url, adrMapping);
  }

  setFileForErrProcessingOrg(cwfId: string): Observable<null> {
    const url = `${this.wwDataUrl}/set-file-for-processing-org`;
    return this.http.post<null>(url, cwfId);
  }

  revalidateErrorsOrg(cwfId: string): Observable<null> {
    const url = `${this.wwDataUrl}/revalidate-errors-org`;
    return this.http.post<null>(url, cwfId);
  }

  deleteAdrMapping(id: string): Observable<null> {
    const url = `${this.wwDataUrl}/del-adr-mapping`;
    return this.http.post<null>(url, id);
  }

  deleteAdrMappingOrg(id: string): Observable<null> {
    const url = `${this.wwDataUrl}/del-adr-mapping-org`;
    return this.http.post<null>(url, id);
  }

  getCwAddressMappings(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[],
    id?: string
  ): Observable<BrowseFormSearchResult<CwAddressMappingsBrowseRow>> {
    let url = '';
    if (id) {
      url = `${this.wwDataUrl}/get-centralized-adr-map`;
    } else {
      url = `${this.wwDataUrl}/get-centralized-adr-map-adm`;
    }
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['orgId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<CwAddressMappingsBrowseRow>>(url, postBody);
  }

  saveNewWastewaterDataFile(file: string, orgId?: string): Observable<NtisCwFilesDAO> {
    const paramArray: [string, string][] = [
      ['file', file],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    const url = `${this.wwDataUrl}/set-centralized-ww-data-file`;
    return this.http.post<NtisCwFilesDAO>(url, postBody);
  }

  validateFileErrs(cwfId: number, orgId: string): Observable<null> {
    const url = `${this.wwDataUrl}/validate-errs`;
    const paramArray: [string, string][] = [
      ['cwfId', cwfId.toString()],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }

  getNewlyImportedFile(cwfId: string): Observable<NtisWastewaterDataImportFile> {
    const url = `${this.wwDataUrl}/get-newly-imported-file`;
    return this.http.post<NtisWastewaterDataImportFile>(url, cwfId);
  }

  deletePendingFile(file: SprFile): Observable<null> {
    const url = `${this.wwDataUrl}/del-pending-file`;
    return this.http.post<null>(url, file);
  }

  updateCwFileState(id: string): Observable<null> {
    const url = `${this.wwDataUrl}/update-ww-file-state`;
    return this.http.post<null>(url, id);
  }

  getErrors(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WastewaterFileErrorListRow>> {
    const url = `${this.wwDataUrl}/get-errors`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['cwfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WastewaterFileErrorListRow>>(url, postBody);
  }

  getLines(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WastewaterFileDataListRow>> {
    const url = `${this.wwDataUrl}/get-data-lines`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['cwfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WastewaterFileDataListRow>>(url, postBody);
  }

  getExemplaryFiles(): Observable<SprFile[]> {
    const url = `${this.wwDataUrl}/get-exemplary-files`;
    return this.http.post<SprFile[]>(url, null);
  }

  getExemplaryFilesOrg(): Observable<SprFile[]> {
    const url = `${this.wwDataUrl}/get-exemplary-files-org`;
    return this.http.post<SprFile[]>(url, null);
  }

  getPrpFile(): Observable<IdKeyValuePair> {
    const url = `${this.wwDataUrl}/get-prp-doc`;
    return this.http.post<IdKeyValuePair>(url, null);
  }

  getPrpFileOrg(): Observable<IdKeyValuePair> {
    const url = `${this.wwDataUrl}/get-prp-doc-org`;
    return this.http.post<IdKeyValuePair>(url, null);
  }
  // ntis-admin related methods (end)
  // specific organization related methods (start)

  getCentralizedWastewaterDataOrg(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<CwDataListBrowseRow>> {
    const url = `${this.wwDataUrl}/get-centralized-org-ww-data`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<CwDataListBrowseRow>>(url, postBody);
  }

  getOrgNameForOrg(): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.wwDataUrl}/get-org-name-for-org`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, null);
  }

  getCwAdrMappingsOrg(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<CwAddressMappingsBrowseRow>> {
    const url = `${this.wwDataUrl}/get-centralized-adr-map-org`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<CwAddressMappingsBrowseRow>>(url, postBody);
  }

  saveNewWastewaterDataFileOrg(file: string): Observable<NtisCwFilesDAO> {
    const url = `${this.wwDataUrl}/set-centralized-org-ww-data-file`;
    const paramArray: [string, string][] = [['file', file]];
    const postBody = { params: paramArray };
    return this.http.post<NtisCwFilesDAO>(url, postBody);
  }

  validateFileErrsOrg(cwfId: number): Observable<null> {
    const url = `${this.wwDataUrl}/validate-errs-org`;
    const paramArray: [string, string][] = [['cwfId', cwfId.toString()]];
    const postBody = { params: paramArray };
    return this.http.post<null>(url, postBody);
  }

  getNewlyImportedFileOrg(cwfId: string): Observable<NtisWastewaterDataImportFile> {
    const url = `${this.wwDataUrl}/get-newly-imported-file-org`;
    return this.http.post<NtisWastewaterDataImportFile>(url, cwfId);
  }

  deletePendingFileOrg(file: SprFile): Observable<null> {
    const url = `${this.wwDataUrl}/del-pending-file-org`;
    return this.http.post<null>(url, file);
  }

  updateCwFileStateOrg(id: string): Observable<null> {
    const url = `${this.wwDataUrl}/update-ww-file-state-org`;
    return this.http.post<null>(url, id);
  }

  getErrorsOrg(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WastewaterFileErrorListRow>> {
    const url = `${this.wwDataUrl}/get-errors-org`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['cwfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WastewaterFileErrorListRow>>(url, postBody);
  }

  getLinesOrg(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WastewaterFileDataListRow>> {
    const url = `${this.wwDataUrl}/get-data-lines-org`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['cwfId', id]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WastewaterFileDataListRow>>(url, postBody);
  }

  getCwAdrMapping(id: string, actionType?: string): Observable<NtisAdrMappingsDAO> {
    const url = `${this.wwDataUrl}/get-cw-adr-mapping`;
    const params: RecordIdentifier = { id: id, actionType: actionType };
    return this.http.post<NtisAdrMappingsDAO>(url, params);
  }
  getCwAdrMappingOrg(id: string, actionType?: string): Observable<NtisAdrMappingsDAO> {
    const url = `${this.wwDataUrl}/get-cw-adr-mapping-org`;
    const params: RecordIdentifier = { id: id, actionType: actionType };
    return this.http.post<NtisAdrMappingsDAO>(url, params);
  }
  // specific organization related methods (end)
  // CENTRALIZED WASTEWATER DATA LIST -- END

  // WASTEWATER DATA VIEW PAGE  -- START
  getImportFile(id: string, orgId: string): Observable<NtisWastewaterDataImportFile> {
    const paramArray: [string, string][] = [
      ['cwfId', id],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    const url = `${this.wwDataUrl}/get-wastewater-file-rec`;
    return this.http.post<NtisWastewaterDataImportFile>(url, postBody);
  }

  updateFileState(id: string): Observable<null> {
    const url = `${this.wwDataUrl}/update-ww-data-file-state`;
    return this.http.post<null>(url, id);
  }

  deleteFile(file: SprFile): Observable<null> {
    const url = `${this.wwDataUrl}/del-file`;
    return this.http.post<null>(url, file);
  }
  // WASTEWATER DATA VIEW PAGE  -- END

  // CENTRALIZED WASTEWATER DATA REPORT -- START
  getWastewaterDataReport(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<CentralizedWastewaterDataReportBrowseRow>> {
    const url = `${this.wwDataUrl}/get-centralized-data-report`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<CentralizedWastewaterDataReportBrowseRow>>(url, postBody);
  }
  getWaterManagers(): Observable<CommonResult<IdKeyValuePair>> {
    const url = `${this.wwDataUrl}/get-water-managers-list`;
    return this.http.post<CommonResult<IdKeyValuePair>>(url, null);
  }
  // CENTRALIZED WASTEWATER DATA REPORT -- END
}
