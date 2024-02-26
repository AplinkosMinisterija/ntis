import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  NtisCar,
  NtisFacilityModelEditModel,
  NtisInstitutionEditModel,
  NtisNewServiceRequest,
  NtisServiceDetails,
  NtisServiceManagementItem,
  NtisServiceProviderContacts,
  NtisServiceProviderRejectionModel,
  NtisServiceProviderSettingsInfo,
  NtisServiceReqDetails,
  NtisServiceRequestsDAO,
  NtisWastewaterTreatmentOrgDAO,
  NtisWaterManagerFacility,
  RecordIdentifier,
} from '@itree-commons/src/lib/model/api/api';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import {
  AdrAddressesBrowseRow,
  FacilityModelsBrowseRow,
  NtisCarsBrowseRow,
  NtisInstitutionsBrowseRow,
  PriorityFacilitiesList,
  ServiceProvidersBrowseRow,
  ServiceRequestItem,
  SrvReviewsBrowseRow,
  WaterManagerBrowseRow,
  WaterManagerFacilitiesBrowseRow,
} from '../models/browse-pages';
import { ExtendedSearchParam, S2ViolatedConstraint, Spr_paging_ot } from '@itree/ngx-s2-commons';
import { BehaviorSubject, Observable } from 'rxjs';
import { NtisServiceDescriptionEditModel, SprFile } from '@itree-commons/src/lib/model/api/api';
import { ServiceRequestsBrowseRow } from '../models/browse-pages';
import { CommonResult } from '@itree-commons/src/lib/model/api';

@Injectable({
  providedIn: 'root',
})
export class InstitutionsAdminService {
  readonly spRestUrl = `${REST_API_BASE_URL}/ntis-service-providers`;
  readonly wmRestUrl = `${REST_API_BASE_URL}/ntis-water-managers`;
  readonly saRestUrl = `${REST_API_BASE_URL}/ntis-system-admin`;
  readonly swRestUrl = `${REST_API_BASE_URL}/ntis-sp-wm-shared`;

  constructor(private http: HttpClient) {}

  serviceStatusSubject = new BehaviorSubject<void>(null);
  waterManagersWtfsSubject = new BehaviorSubject<void>(null);

  // Organization services (start)
  getServiceRecord(id: string): Observable<NtisServiceDescriptionEditModel> {
    return this.http.post<NtisServiceDescriptionEditModel>(this.spRestUrl + '/get-service', id);
  }

  setServiceRecord(service: NtisServiceDescriptionEditModel): Observable<NtisServiceDescriptionEditModel> {
    return this.http.post<NtisServiceDescriptionEditModel>(this.spRestUrl + '/set-service', service);
  }
  // Organization services (end)

  // Service providers profile (start)
  getServiceProviderProfileInfo(): Observable<NtisServiceProviderSettingsInfo> {
    const url = `${this.spRestUrl}/get-profile-info`;
    return this.http.post<NtisServiceProviderSettingsInfo>(url, null);
  }

  saveServiceProviderContacts(contacts: NtisServiceProviderContacts): Observable<NtisServiceProviderContacts> {
    const url = `${this.spRestUrl}/save-contacts`;
    return this.http.post<NtisServiceProviderContacts>(url, contacts);
  }

  deregisterInstallation(): Observable<void> {
    const url = `${this.spRestUrl}/deregister-installation`;
    return this.http.post<void>(url, null);
  }
  // Service providers profile (end)

  // Service management (start)
  getServicesInfo(): Observable<NtisServiceManagementItem[]> {
    const url = `${this.spRestUrl}/get-services-info`;
    return this.http.post<NtisServiceManagementItem[]>(url, null);
  }

  getServiceDetails(srvId: number): Observable<NtisServiceDetails> {
    const url = `${this.spRestUrl}/get-service-details`;
    return this.http.post<NtisServiceDetails>(url, { id: `${srvId}` });
  }

  confirmService(srvId: number): Observable<null> {
    const url = `${this.spRestUrl}/confirm-service`;
    return this.http.post<null>(url, { id: `${srvId}` });
  }

  deregisterService(sriId: number): Observable<null> {
    const url = `${this.spRestUrl}/deregister-service`;
    return this.http.post<null>(url, { id: `${sriId}` });
  }
  // Service management (end)

  // Water managers profile (start)
  getWaterManagerProfileInfo(): Observable<NtisServiceProviderSettingsInfo> {
    const url = `${this.wmRestUrl}/get-profile-info`;
    return this.http.post<NtisServiceProviderSettingsInfo>(url, null);
  }

  saveWaterManagerContacts(contacts: NtisServiceProviderContacts): Observable<NtisServiceProviderContacts> {
    const url = `${this.wmRestUrl}/save-contacts`;
    return this.http.post<NtisServiceProviderContacts>(url, contacts);
  }

  checkIfWaterManagerWtfRegistered(): Observable<boolean> {
    const url = `${this.spRestUrl}/check-if-facilities-registered`;
    return this.http.post<boolean>(url, null);
  }
  // Water managers profile (end)

  // Cars (start)
  getCarsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisCarsBrowseRow>> {
    const url = `${this.spRestUrl}/get-cars`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisCarsBrowseRow>>(url, postBody);
  }

  deleteCar(id: string): Observable<null> {
    const url = `${this.spRestUrl}/del-car`;
    return this.http.post<null>(url, { id });
  }

  getCar(id: string): Observable<NtisCar> {
    const url = `${this.spRestUrl}/get-car`;
    const body: RecordIdentifier = { id };
    return this.http.post<NtisCar>(url, body);
  }

  setCar(car: NtisCar): Observable<NtisCar> {
    const url = `${this.spRestUrl}/set-car`;
    return this.http.post<NtisCar>(url, car);
  }
  // Cars (end)

  // Service Requests (start)
  getServiceRequestsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ServiceRequestsBrowseRow>> {
    const url = `${this.saRestUrl}/get-service-requests`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ServiceRequestsBrowseRow>>(url, postBody);
  }

  getServiceRequestInfo(id: number): Observable<NtisServiceReqDetails> {
    const url = `${this.saRestUrl}/get-service-request-info`;
    return this.http.post<NtisServiceReqDetails>(url, id.toString());
  }

  getServiceRequestFiles(id: number): Observable<SprFile[]> {
    const url = `${this.saRestUrl}/get-service-request-files`;
    return this.http.post<SprFile[]>(url, `${id}`);
  }

  setServiceRequestStatus(template: NtisServiceReqDetails): Observable<NtisServiceReqDetails> {
    const url = `${this.saRestUrl}/set-service-request-status`;
    return this.http.post<NtisServiceReqDetails>(url, template);
  }

  getNewServiceReqInfo(): Observable<NtisNewServiceRequest> {
    const url = `${this.saRestUrl}/get-new-service-request-info`;
    return this.http.post<NtisNewServiceRequest>(url, null);
  }

  setServiceRequest(template: NtisServiceReqDetails): Observable<NtisServiceRequestsDAO> {
    const url = `${this.saRestUrl}/set-service-request`;
    return this.http.post<NtisServiceRequestsDAO>(url, template);
  }

  sendNewServiceReqNotifications(srvReqId: number): Observable<void> {
    const url = `${this.saRestUrl}/send-new-service-req-notifications`;
    return this.http.post<void>(url, srvReqId.toString());
  }

  sendServiceReqNotifications(srvReqId: number): Observable<void> {
    const url = `${this.saRestUrl}/send-service-req-notifications`;
    return this.http.post<void>(url, srvReqId.toString());
  }

  getSrvReqByToken(token: string): Observable<number> {
    const url = `${this.saRestUrl}/get-service-req-by-token`;
    return this.http.post<number>(url, token);
  }

  getActiveServicesInfo(): Observable<CommonResult<ServiceRequestItem>> {
    const url = `${this.saRestUrl}/get-active-services-info`;
    return this.http.post<CommonResult<ServiceRequestItem>>(url, null);
  }
  // Service Requests (end)
  // Institutions (start)
  getInstitutionsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<NtisInstitutionsBrowseRow>> {
    const url = `${this.saRestUrl}/get-institutions`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<NtisInstitutionsBrowseRow>>(url, postBody);
  }

  getInstitutionAdmin(id: string, actionType?: string): Observable<NtisInstitutionEditModel> {
    const url = `${this.saRestUrl}/get-inst-admin`;
    return this.http.post<NtisInstitutionEditModel>(url, { id, actionType });
  }

  saveNewInstitution(institution: NtisInstitutionEditModel): Observable<NtisInstitutionEditModel> {
    const url = `${this.saRestUrl}/set-institution`;
    return this.http.post<NtisInstitutionEditModel>(url, institution);
  }

  inviteNewPerson(id: string): Observable<null> {
    const url = `${this.saRestUrl}/invite-institution-person`;
    return this.http.post<null>(url, id);
  }

  removeInstAdmin(usrId: string, orgId: string): Observable<null> {
    const paramArray: [string, string][] = [
      ['usrId', usrId],
      ['orgId', orgId],
    ];
    const postBody = { params: paramArray };
    const url = `${this.saRestUrl}/remove-inst-admin`;
    return this.http.post<null>(url, postBody);
  }

  checkUserRecordConstraints(data: NtisInstitutionEditModel): Observable<S2ViolatedConstraint[]> {
    const url = `${this.saRestUrl}/check-usr-constraint`;
    return this.http.post<S2ViolatedConstraint[]>(url, data);
  }
  // Institutions (end)

  // Service providers list (start)
  getServiceProvidersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ServiceProvidersBrowseRow>> {
    const url = `${this.saRestUrl}/get-service-providers`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ServiceProvidersBrowseRow>>(url, postBody);
  }
  setServiceProviderDisabled(provider: NtisServiceProviderRejectionModel): Observable<null> {
    const url = `${this.saRestUrl}/set-org-disabled`;
    return this.http.post<null>(url, provider);
  }

  sendSpListNotifications(orgId: number): Observable<void> {
    const url = `${this.saRestUrl}/send-sp-list-notifications`;
    return this.http.post<void>(url, orgId.toString());
  }
  // Service providers list (end)
  // Water manager facilities list (start)
  getManagerFacilitiesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WaterManagerFacilitiesBrowseRow>> {
    const url = `${this.spRestUrl}/get-water-facilities`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WaterManagerFacilitiesBrowseRow>>(url, postBody);
  }

  saveManagerFacility(facility: NtisWaterManagerFacility): Observable<NtisWastewaterTreatmentOrgDAO> {
    const url = `${this.spRestUrl}/update-water-facility`;
    return this.http.post<NtisWastewaterTreatmentOrgDAO>(url, facility);
  }
  // Water manager facilities list (end)
  // SP/WM Service Requests (start)
  getSpWmServiceRequestsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<ServiceRequestsBrowseRow>> {
    const url = `${this.swRestUrl}/get-service-requests`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<ServiceRequestsBrowseRow>>(url, postBody);
  }
  // SP/WM Service Requests (end)
  // Priority facilities list (start)
  getWaterManagersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<WaterManagerBrowseRow>> {
    const url = `${this.spRestUrl}/get-water-managers-list`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<WaterManagerBrowseRow>>(url, postBody);
  }

  savePriorityFacilitiesList(facility: PriorityFacilitiesList[]): Observable<PriorityFacilitiesList[]> {
    const url = `${this.spRestUrl}/set-priority-facilities-list`;
    return this.http.post<PriorityFacilitiesList[]>(url, facility);
  }
  // Priority facilities list (end)
  // Facility models list (start)
  getFacilityModelsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<FacilityModelsBrowseRow>> {
    const url = `${this.saRestUrl}/get-facility-models`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<FacilityModelsBrowseRow>>(url, postBody);
  }
  delFacilityModelRecord(id: string): Observable<null> {
    const url = `${this.saRestUrl}/del-facility-model`;
    return this.http.post<null>(url, { id });
  }
  getFacilityModel(id: string): Observable<NtisFacilityModelEditModel> {
    const url = `${this.saRestUrl}/get-facility-model`;
    const body: RecordIdentifier = { id };
    return this.http.post<NtisFacilityModelEditModel>(url, body);
  }
  setFacilityModel(model: NtisFacilityModelEditModel): Observable<NtisFacilityModelEditModel> {
    const url = `${this.saRestUrl}/set-facility-model`;
    return this.http.post<NtisFacilityModelEditModel>(url, model);
  }
  // Facility models list (end)

  getSrvReviewsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SrvReviewsBrowseRow>> {
    const url = `${this.spRestUrl}/get-srv-reviews-list`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SrvReviewsBrowseRow>>(url, myPostBody);
  }

  markReviewAsRead(id: string): Observable<null> {
    const url = `${this.spRestUrl}/mark-review-as-read-srv`;
    return this.http.post<null>(url, id);
  }

  // Adr addresses list (start)
  getAdrAddressesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<AdrAddressesBrowseRow>> {
    const url = `${this.saRestUrl}/get-adr-address-list`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<AdrAddressesBrowseRow>>(url, postBody);
  }
}
