import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import {
  AddressSearchResponse,
  BrowseFormSearchResult,
  FacilityModelAdditionalDetails,
  ForeignKeyParams,
  IdKeyValuePair,
  NtisCheckWtfSelectionRequest,
  NtisFacilityManagerEditModel,
  NtisFacilityModel,
  NtisServedBuildingUpdateModel,
  NtisWastewaterTreatmentFaci,
  NtisWastewaterTreatmentFacility,
  NtrOwnerModel,
  SprFile,
  SprOrganizationsDAO,
  Spr_paging_ot,
} from '@itree-commons/src/lib/model/api/api';
import { ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { Coordinate } from 'ol/coordinate';
import { Observable } from 'rxjs';
import { AddressSearch } from '../../ntis-shared/components/address-search-form/address-search-form.component';
import { ServedObjectsView } from '../../ntis-shared/models/served-objects-view';
import {
  NtisWastewaterFacilityView,
  NtisWastewaterTreatmentFaciBrowseRow,
  NtisWtfAgreements,
  WfManagersListRow,
} from '../models/browse-page';

@Injectable({
  providedIn: 'root',
})
export class BuildingDataService {
  readonly spRestUrl = `${REST_API_BASE_URL}/ntis-building-data`;

  constructor(private http: HttpClient) {}

  checkIfIdentificationNumberExists(identifiactionNumber: string): Observable<number> {
    const url = `${this.spRestUrl}/check-identification-number`;
    return this.http.post<number>(url, identifiactionNumber);
  }

  /**
   * Sends a POST request to the server to save a wastewater facility record.
   * @param data a NtisWastewaterTreatmentFaci object containing the information to save
   * @return an Observable of type NtisWastewaterTreatmentFaci, representing the saved record
   */
  setFacility(data: NtisWastewaterTreatmentFaci): Observable<NtisWastewaterTreatmentFaci> {
    const url = `${this.spRestUrl}/set-facility`;
    return this.http.post<NtisWastewaterTreatmentFaci>(url, data);
  }
  /**
   */
  getFacility(id: string): Observable<NtisWastewaterTreatmentFaci> {
    const url = `${this.spRestUrl}/get-facility`;
    return this.http.post<NtisWastewaterTreatmentFaci>(url, { id });
  }
  /**
   * Sends a POST request to the server to retrieve a list of facility models based on the specified code.
   * @param code a string containing the code to use for the query
   * @return an Observable of type NtisFacilityModel[], representing the list of retrieved models
   */
  getFacilityModels(code: string): Observable<NtisFacilityModel[]> {
    const url = `${this.spRestUrl}/get-facility-models`;
    return this.http.post<NtisFacilityModel[]>(url, code);
  }
  /**
   * Sends a POST request to the server to retrieve a list of wastewater facilities based on the specified parameters.
   * @param pagingParams a Spr_paging_ot object containing the paging parameters for the request
   * @param searchParams a Map object containing the search parameters for the request
   * @param extendedParams an array of ExtendedSearchParam objects containing additional search parameters for the request
   * @return an Observable of type BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>, representing the list of retrieved facilities
   */
  getWastewaterTreatmentFacilityList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>> {
    const url = `${this.spRestUrl}/get-facility-list`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisWastewaterTreatmentFaciBrowseRow>>(url, myPostBody);
  }
  /**
   * Sends a POST request to the server to remove a wastewater facility from the list.
   * @param id a string containing the ID of the facility to remove
   * @return an Observable of type null
   */
  removeFacility(id: string): Observable<null> {
    const url = `${this.spRestUrl}/remove-facility`;
    return this.http.post<null>(url, { id });
  }
  /**
   *
   * Sends a POST request to the server to approve a wastewater facility.
   * @param id a string containing the ID of the facility to approve
   * @return an Observable of type null
   */
  approveFacility(id: string): Observable<null> {
    const url = `${this.spRestUrl}/approve-facility`;
    return this.http.post<null>(url, { id });
  }

  /**
   *
   * This function is used to retrieve treatment facility data from the server.
   * @param id: string - The ID of the treatment facility for which the data should be retrieved.
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  getTreatmentFacilityInfo(id: string): Observable<CommonResult<NtisWastewaterFacilityView>> {
    const url = `${this.spRestUrl}/get-treatment-facility-data`;
    return this.http.post<CommonResult<NtisWastewaterFacilityView>>(url, { id });
  }

  findAddress(value: ForeignKeyParams): Observable<AddressSearchResponse[]> {
    const url = `${this.spRestUrl}/find-address`;
    return this.http.post<AddressSearchResponse[]>(url, value);
  }

  loadAdditionalDetails(id: string): Observable<FacilityModelAdditionalDetails> {
    const url = `${this.spRestUrl}/get-facility-model-details`;
    return this.http.post<FacilityModelAdditionalDetails>(url, { id });
  }

  /**
   *
   *
   * @param id Street id.
   * @returns Streets with city id.
   */
  searchAddresses(data: AddressSearch): Observable<CommonResult<AddressSearchResponse>> {
    const url = `${this.spRestUrl}/search-address`;
    return this.http.post<CommonResult<AddressSearchResponse>>(url, data);
  }

  /**
   *
   *
   * @param id Address id.
   * @returns AddressSearchResponse.
   *
   */
  findAddressById(id: number): Observable<AddressSearch[]> {
    const url = `${this.spRestUrl}/find-address-by-id`;
    return this.http.post<AddressSearch[]>(url, { id });
  }
  /**
   *
   *
   * @param facilityAddressId Facility Address id.
   * @returns Returns a boolean value after checking whether the facility is already registered.
   */
  verifyAddress(facilityAddressId: number, facilityType: string): Observable<boolean> {
    const url = `${this.spRestUrl}/verify-address`;
    const params = {
      adId: facilityAddressId,
      wtfType: facilityType,
    } as NtisCheckWtfSelectionRequest;
    return this.http.post<boolean>(url, params);
  }

  findNtrAddress(value: ForeignKeyParams): Observable<AddressSearchResponse[]> {
    const url = `${this.spRestUrl}/find-ntr-address`;
    return this.http.post<AddressSearchResponse[]>(url, value);
  }

  findAddressByCoordinates(coordinates: Coordinate): Observable<AddressSearchResponse[]> {
    const url = `${this.spRestUrl}/find-address-by-coordinates`;
    return this.http.post<AddressSearchResponse[]>(url, coordinates);
  }

  checkNtrObjects(addressid: number): Observable<AddressSearchResponse[]> {
    const url = `${this.spRestUrl}/check-ntr-objects`;
    return this.http.post<AddressSearchResponse[]>(url, addressid.toString());
  }

  assignExistingFacility(value: NtisWastewaterTreatmentFaci): Observable<number> {
    const url = `${this.spRestUrl}/assign-existing-facility`;
    return this.http.post<number>(url, value);
  }

  getBuildingData(soId: string): Observable<CommonResult<ServedObjectsView>> {
    const url = `${this.spRestUrl}/get-ntr-objects`;
    return this.http.post<CommonResult<ServedObjectsView>>(url, soId);
  }

  loadNTROwnersList(bnRegNr: string): Observable<NtrOwnerModel[]> {
    const url = `${this.spRestUrl}/load-ntr-owners`;
    return this.http.post<NtrOwnerModel[]>(url, bnRegNr);
  }

  getBuildingAgreement(baId: string): Observable<NtisServedBuildingUpdateModel> {
    const url = `${this.spRestUrl}/get-building-agreement`;
    return this.http.post<NtisServedBuildingUpdateModel>(url, baId);
  }

  updateBuildingAgreement(formData: NtisServedBuildingUpdateModel): Observable<NtisServedBuildingUpdateModel> {
    const url = `${this.spRestUrl}/update-building-agreement`;
    return this.http.post<NtisServedBuildingUpdateModel>(url, formData);
  }

  // Wastewater facility managers edit start
  getSelectedWfFacility(id: string): Observable<NtisWastewaterTreatmentFacility> {
    const url = `${this.spRestUrl}/get-selected-manager-wtf`;
    return this.http.post<NtisWastewaterTreatmentFacility>(url, id);
  }

  saveNewFacilityManager(manager: NtisFacilityManagerEditModel): Observable<NtisFacilityManagerEditModel> {
    const url = `${this.spRestUrl}/save-new-manager`;
    return this.http.post<NtisFacilityManagerEditModel>(url, manager);
  }

  getWaterManagementCompanies(): Observable<SprOrganizationsDAO[]> {
    const url = `${this.spRestUrl}/get-water-management-companies`;
    return this.http.post<SprOrganizationsDAO[]>(url, null);
  }

  // Wastewater facility managers edit end

  // WF managers list start
  getWfManagers(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    wtfId: string,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WfManagersListRow>> {
    const url = `${this.spRestUrl}/get-wf-managers`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['wtfId', wtfId]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WfManagersListRow>>(url, myPostBody);
  }

  getWfManagerWtf(id: string): Observable<NtisWastewaterTreatmentFacility> {
    const url = `${this.spRestUrl}/get-wf-manager-wtf`;
    return this.http.post<NtisWastewaterTreatmentFacility>(url, id);
  }

  updateManagerDate(manager: NtisFacilityManagerEditModel): Observable<null> {
    const url = `${this.spRestUrl}/update-manager-date`;
    return this.http.post<null>(url, manager);
  }

  checkIsOwner(id: string): Observable<boolean> {
    const url = `${this.spRestUrl}/check-is-owner`;
    return this.http.post<boolean>(url, id);
  }
  // WF managers list end

  /**
   * Sends a POST request to the server to retrieve a list of wastewater facilities agreements on the specified parameters.
   * @param pagingParams a Spr_paging_ot object containing the paging parameters for the request
   * @param searchParams a Map object containing the search parameters for the request
   * @param extendedParams an array of ExtendedSearchParam objects containing additional search parameters for the request
   * @return an Observable of type BrowseFormSearchResult<NtisWtfAgreements>, representing the list of retrieved facilities
   */
  getWtfAgrrementsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisWtfAgreements>> {
    const url = `${this.spRestUrl}/get-wf-agreements-list`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisWtfAgreements>>(url, myPostBody);
  }

  /**
   *
   * This function is used to retrieve WF confirmation data from the server.
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  getWtfAgrrementsConfirmation(id: string): Observable<CommonResult<NtisWtfAgreements>> {
    const url = `${this.spRestUrl}/get-wf-agreement-confirmation-view`;
    return this.http.post<CommonResult<NtisWtfAgreements>>(url, { id });
  }

  /**
   *
   * This function is used to retrieve WF confirmation data from the server.
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  getWtfObjAgrrementsConfirmation(id: string): Observable<CommonResult<NtisWtfAgreements>> {
    const url = `${this.spRestUrl}/get-wf-obj-agreement-confirmation-view`;
    return this.http.post<CommonResult<NtisWtfAgreements>>(url, { id });
  }

  /**
   *
   * This function is used to confirm agreement
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  confirmWtfAgrrement(id: string): Observable<CommonResult<void>> {
    const url = `${this.spRestUrl}/confirm-wf-agreement`;
    return this.http.post<CommonResult<void>>(url, { id });
  }

  /**
   *
   * This function is used to confirm agreement
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  confirmWtfAgrrementObj(id: string): Observable<CommonResult<void>> {
    const url = `${this.spRestUrl}/confirm-wf-obj-agreement`;
    return this.http.post<CommonResult<void>>(url, { id });
  }

  /**
   *
   * This function is used to cancel agreement
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  cancelWtfAgrrement(info: IdKeyValuePair): Observable<CommonResult<void>> {
    const url = `${this.spRestUrl}/cancel-wf-agreement`;
    return this.http.post<CommonResult<void>>(url, info);
  }

  /**
   *
   * This function is used to cancel agreement
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  cancelWtfObjAgrrement(info: IdKeyValuePair): Observable<CommonResult<void>> {
    const url = `${this.spRestUrl}/cancel-wf-obj-agreement`;
    return this.http.post<CommonResult<void>>(url, info);
  }

  /**
   *
   * This function is used to reject agreement
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  rejectWtfAgrrement(info: IdKeyValuePair): Observable<CommonResult<void>> {
    const url = `${this.spRestUrl}/reject-wf-agreement`;
    return this.http.post<CommonResult<void>>(url, info);
  }

  /**
   *
   * This function is used to reject agreement
   * @param id: string - The ID of agreement
   * @returns Observable<CommonResult<NtisWastewaterFacilityView>> - An observable that emits the result of the server request. The result is a CommonResult object containing the requested treatment facility data.
   */
  rejectWtfOBjAgrrement(info: IdKeyValuePair): Observable<CommonResult<void>> {
    const url = `${this.spRestUrl}/reject-wf-obj-agreement`;
    return this.http.post<CommonResult<void>>(url, info);
  }

  downloadFile(id: string, fileDao: SprFile): void {
    this.http
      .get<Blob>(`${this.spRestUrl}/get-agreement-file/${id}`, {
        observe: 'response',
        responseType: 'blob' as 'json',
      })
      .subscribe((file) => {
        const dataType = fileDao.fil_content_type;
        const downloadLink = document.createElement('a');
        downloadLink.href = window.URL.createObjectURL(new Blob([file.body], { type: dataType }));
        downloadLink.setAttribute('download', fileDao.fil_name);
        downloadLink.click();
      });
  }

  getObjFuaByToken(token: string): Observable<CommonResult<NtisWtfAgreements>> {
    const url = `${this.spRestUrl}/get-obj-fua-by-token`;
    return this.http.post<CommonResult<NtisWtfAgreements>>(url, token);
  }

  getFuaByToken(token: string): Observable<CommonResult<NtisWtfAgreements>> {
    const url = `${this.spRestUrl}/get-fua-by-token`;
    return this.http.post<CommonResult<NtisWtfAgreements>>(url, token);
  }

  deleteFuaFile(fileToDelete: SprFile): Observable<null> {
    return this.http.post<null>(this.spRestUrl + '/del-fua-file', fileToDelete);
  }
}
