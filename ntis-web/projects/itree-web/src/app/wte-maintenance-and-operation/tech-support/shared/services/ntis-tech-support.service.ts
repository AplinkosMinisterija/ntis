import { ExtendedSearchParam, Spr_paging_ot } from '@itree/ngx-s2-commons';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { Observable, map } from 'rxjs';
import {
  SludgeTreatmentDeliveriesBrowseRow,
  WmDashboardRow,
  WmGraphBar,
} from '../../water-manager/models/browse-pages';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import {
  BrowseFormSearchResult,
  NtisAddrSearchRequest,
  NtisAddrSearchResult,
  NtisAdrResidencesDAO,
  NtisAdrStreetsDAO,
  NtisCarsDAO,
  NtisOrderCarSelection,
  NtisOrderCompletedWorksDAO,
  NtisOrderCompletedWorksRequest,
  NtisOrderDetails,
  NtisOrdersRequest,
  NtisServiceManagementItem,
  NtisServiceOrderRequest,
  NtisSewageOriginFacility,
  NtisSludgeDeliveryDetails,
  NtisUsedSewageFacility,
  NtisWastewaterDeliveriesDAO,
  NtisWastewaterDeliveryEditModel,
  NtisWaterManagerSelectionModel,
  RecordIdentifier,
} from '@itree-commons/src/lib/model/api/api';
import { OwnerDisposalOrdersBrowseRow, OwnerTechOrdersBrowseRow, SelectOption } from '../../models/browse-pages';
import {
  GroupedSelectOption,
  OrdersListBrowseRow,
  SelectOptionForGroup,
  ServiceProviderDashboardBrowse,
  ServiceProviderFinishedGraphBar,
  ServiceProviderSludgeGraphBar,
  SewageDeliveriesBrowseRow,
} from '../../service-provider/models/browse-pages';

@Injectable({
  providedIn: 'root',
})
export class NtisTechSupportService {
  readonly techSupRestUrl = `${REST_API_BASE_URL}/ntis-tech-supp`;

  constructor(private http: HttpClient) {}

  updateSelectedWtf(selectedWtf: number): Observable<null> {
    const url = this.techSupRestUrl + '/update-selected-wtf';
    return this.http.post<null>(url, selectedWtf.toString());
  }

  // Service provider dashboard (start)
  getSpDasboard(): Observable<CommonResult<ServiceProviderDashboardBrowse>> {
    const url = `${this.techSupRestUrl}/get-sp-dashboard`;
    return this.http.post<CommonResult<ServiceProviderDashboardBrowse>>(url, null);
  }

  getSpFinishedOrders(period?: string): Observable<CommonResult<ServiceProviderFinishedGraphBar>> {
    const url = `${this.techSupRestUrl}/get-sp-finished-ord`;
    return this.http.post<CommonResult<ServiceProviderFinishedGraphBar>>(url, period);
  }

  getSpDischargedSludge(period?: string): Observable<CommonResult<ServiceProviderSludgeGraphBar>> {
    const url = `${this.techSupRestUrl}/get-sp-sludge`;
    return this.http.post<CommonResult<ServiceProviderSludgeGraphBar>>(url, period);
  }
  // Service provider dashboard (end)

  // Water manager dashboard (start)
  getWmDashboard(id: string): Observable<CommonResult<WmDashboardRow>> {
    const url = `${this.techSupRestUrl}/get-wm-dashboard`;
    return this.http.post<CommonResult<WmDashboardRow>>(url, id);
  }

  getWmConfirmedDeliveries(period: string, wtoId: string): Observable<CommonResult<WmGraphBar>> {
    const url = `${this.techSupRestUrl}/get-wm-confirmed-del`;
    const paramArray: [string, string][] = [
      ['period', period],
      ['wtoId', wtoId],
    ];
    const myPostBody = { params: paramArray };
    return this.http.post<CommonResult<WmGraphBar>>(url, myPostBody);
  }

  getWmAcceptedSludge(period: string, wtoId: string): Observable<CommonResult<WmGraphBar>> {
    const url = `${this.techSupRestUrl}/get-wm-sludge`;
    const paramArray: [string, string][] = [
      ['period', period],
      ['wtoId', wtoId],
    ];
    const myPostBody = { params: paramArray };
    return this.http.post<CommonResult<WmGraphBar>>(url, myPostBody);
  }

  getWmFacilities(): Observable<CommonResult<SelectOption>> {
    const url = `${this.techSupRestUrl}/get-wm-facilities`;
    return this.http.post<CommonResult<SelectOption>>(url, null);
  }
  // Water manager dashboard (end)

  // Sludge treatment deliveries (start)
  getSludgeTreatmentDeliveriesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SludgeTreatmentDeliveriesBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-sludge-deliveries`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SludgeTreatmentDeliveriesBrowseRow>>(url, postBody);
  }

  setSludgeTreatmentDeliveryData(template: NtisWastewaterDeliveriesDAO): Observable<NtisWastewaterDeliveriesDAO> {
    const url = `${this.techSupRestUrl}/set-delivery-data`;
    return this.http.post<NtisWastewaterDeliveriesDAO>(url, template);
  }

  getSludgeDeliveryInfo(id: number): Observable<NtisSludgeDeliveryDetails> {
    const url = `${this.techSupRestUrl}/get-sludge-delivery-info`;
    return this.http.post<NtisSludgeDeliveryDetails>(url, id.toString());
  }

  sendSludgeDeliveryNotifications(deliveryId: number): Observable<void> {
    const url = `${this.techSupRestUrl}/send-sludge-delivery-notifications`;
    return this.http.post<void>(url, deliveryId.toString());
  }
  // sewage delivery list start
  getSewageDeliveriesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SewageDeliveriesBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-sewage-deliveries`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SewageDeliveriesBrowseRow>>(url, postBody);
  }

  deleteSewageDelivery(id: string): Observable<null> {
    const url = `${this.techSupRestUrl}/del-sewage-delivery`;
    return this.http.post<null>(url, { id });
  }

  cancelSewageDelivery(id: string): Observable<null> {
    const url = `${this.techSupRestUrl}/cancel-sewage-delivery`;
    return this.http.post<null>(url, { id });
  }
  //sewage delivery list end

  //sewage delivery edit start
  getSewageDeliveryRecord(id: string, actionType?: string): Observable<NtisWastewaterDeliveryEditModel> {
    const url = `${this.techSupRestUrl}/get-sewage-delivery`;
    const body: RecordIdentifier = { id, actionType };
    return this.http.post<NtisWastewaterDeliveryEditModel>(url, body);
  }

  saveDeliveryRecord(delivery: NtisWastewaterDeliveryEditModel): Observable<NtisWastewaterDeliveryEditModel> {
    const url = `${this.techSupRestUrl}/save-delivery-record`;
    return this.http.post<NtisWastewaterDeliveryEditModel>(url, delivery);
  }

  sendSewageDeliveryNotifications(deliveryId: number): Observable<void> {
    const url = `${this.techSupRestUrl}/send-sewage-delivery-notifications`;
    return this.http.post<void>(url, deliveryId.toString());
  }

  sendNotificationForAutoAccept(deliveryId: number): Observable<void> {
    const url = `${this.techSupRestUrl}/send-auto-accept-delivery-notifications`;
    return this.http.post<void>(url, deliveryId.toString());
  }

  //sewage delivery edit -- origin facilities related

  getNewSewageOriginFacility(id: string): Observable<NtisSewageOriginFacility> {
    const url = `${this.techSupRestUrl}/get-new-sewage-origin-facility`;
    return this.http.post<NtisSewageOriginFacility>(url, id);
  }

  getPortableWfForSewageDelivery(id: string): Observable<NtisSewageOriginFacility> {
    const url = `${this.techSupRestUrl}/get-portable-facility-for-delivery`;
    return this.http.post<NtisSewageOriginFacility>(url, id);
  }

  getOriginFacilitiesOrdersList(): Observable<NtisWaterManagerSelectionModel[]> {
    const url = `${this.techSupRestUrl}/get-origin-facilities-orders-list`;
    return this.http.post<NtisWaterManagerSelectionModel[]>(url, null);
  }

  getPortableFacilities(): Observable<NtisWaterManagerSelectionModel[]> {
    const url = `${this.techSupRestUrl}/get-portable-facilities`;
    return this.http.post<NtisWaterManagerSelectionModel[]>(url, null);
  }

  //sewage delivery edit -- delivery facilities related

  getNewDeliveryFacility(id: string): Observable<NtisUsedSewageFacility> {
    const url = `${this.techSupRestUrl}/get-new-delivery-facility`;
    return this.http.post<NtisUsedSewageFacility>(url, id);
  }

  //sewage delivery edit -- select components related

  getAvailableWaterManagerFacilities(): Observable<CommonResult<GroupedSelectOption>> {
    const url = `${this.techSupRestUrl}/get-available-water-manager-facilities`;
    return this.http.post<CommonResult<GroupedSelectOption>>(url, null).pipe(
      map((result: CommonResult<GroupedSelectOption>) => {
        result.data.forEach((organization: GroupedSelectOption) => {
          if (organization.values) {
            organization.optionList = JSON.parse(organization.values) as SelectOptionForGroup[];
          } else {
            result.data = result.data.filter((org) => org !== organization);
          }
        });
        return result;
      })
    );
  }

  getCarsList(): Observable<NtisOrderCarSelection[]> {
    const url = `${this.techSupRestUrl}/get-available-org-cars`;
    return this.http.post<NtisOrderCarSelection[]>(url, null);
  }

  //sewage delivery edit end

  // sewage delivery view start
  getSewageDeliveryView(id: string): Observable<NtisSludgeDeliveryDetails> {
    const url = `${this.techSupRestUrl}/get-sewage-delivery-view`;
    return this.http.post<NtisSludgeDeliveryDetails>(url, id);
  }

  getDeliveryByToken(token: string): Observable<number> {
    const url = `${this.techSupRestUrl}/get-delivery-by-token`;
    return this.http.post<number>(url, token);
  }
  // sewage delivery view end

  // Disposal orders (start)
  getDisposalOrdersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrdersListBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-disposal-orders`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrdersListBrowseRow>>(url, postBody);
  }

  getOrgCarsList(): Observable<NtisCarsDAO[]> {
    const url = `${this.techSupRestUrl}/get-org-cars`;
    return this.http.post<NtisCarsDAO[]>(url, null);
  }

  setDisposalOrderCompletionData(template: NtisOrderCompletedWorksDAO): Observable<NtisOrderCompletedWorksDAO> {
    const url = `${this.techSupRestUrl}/set-disposal-order-completion-data`;
    return this.http.post<NtisOrderCompletedWorksDAO>(url, template);
  }
  // Tech orders (start)
  getTechOrdersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrdersListBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-tech-orders`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrdersListBrowseRow>>(url, postBody);
  }

  setTechOrderCompletionDate(template: NtisOrderCompletedWorksRequest): Observable<NtisOrderCompletedWorksRequest> {
    const url = `${this.techSupRestUrl}/set-tech-order-completion-date`;
    return this.http.post<NtisOrderCompletedWorksRequest>(url, template);
  }

  setTechOrderState(template: NtisOrdersRequest): Observable<NtisOrdersRequest> {
    const url = `${this.techSupRestUrl}/set-tech-order-state`;
    return this.http.post<NtisOrdersRequest>(url, template);
  }

  // Owner orders list (start)
  // tech orders
  getOwnerTechOrders(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OwnerTechOrdersBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-owner-tech-orders`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OwnerTechOrdersBrowseRow>>(url, postBody);
  }
  // disposal orders
  getOwnerDisposalOrders(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OwnerDisposalOrdersBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-owner-disposal-orders`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OwnerDisposalOrdersBrowseRow>>(url, postBody);
  }
  // Owner  orders list (end)

  // Inspector disposal orders list (start)
  getInspectorDisposalOrdersList(
    wtfId: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OwnerDisposalOrdersBrowseRow>> {
    const url = `${this.techSupRestUrl}/get-inspector-disposal-orders`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['wtfId', wtfId]);
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OwnerDisposalOrdersBrowseRow>>(url, postBody);
  }
  // Inspector disposal orders list (end)

  // Order outside NTIS (start)
  getOrgServices(): Observable<NtisServiceManagementItem[]> {
    const url = `${this.techSupRestUrl}/get-org-services`;
    return this.http.post<NtisServiceManagementItem[]>(url, null);
  }

  setOrderOutsideNtis(template: NtisOrderDetails): Observable<NtisOrderDetails> {
    const url = `${this.techSupRestUrl}/set-order-outside-ntis`;
    return this.http.post<NtisOrderDetails>(url, template);
  }

  onDetailedFaciSearch(template: NtisAddrSearchRequest): Observable<NtisAddrSearchResult[]> {
    const url = `${this.techSupRestUrl}/search-facility`;
    return this.http.post<NtisAddrSearchResult[]>(url, template);
  }

  getResidencesList(value: number): Observable<NtisAdrResidencesDAO[]> {
    const url = `${this.techSupRestUrl}/get-residences`;
    return this.http.post<NtisAdrResidencesDAO[]>(url, value.toString());
  }

  getStreetsList(value: number): Observable<NtisAdrStreetsDAO[]> {
    const url = `${this.techSupRestUrl}/get-streets`;
    return this.http.post<NtisAdrStreetsDAO[]>(url, value.toString());
  }

  sendOutsideOrderNotifications(orderId: number): Observable<void> {
    const url = `${this.techSupRestUrl}/send-outside-order-notifications`;
    return this.http.post<void>(url, orderId.toString());
  }
  // Order outside NTIS (end)

  // Service order (start)
  getServiceOrderInfo(record: NtisOrdersRequest): Observable<NtisServiceOrderRequest> {
    const url = `${this.techSupRestUrl}/get-service-order`;
    return this.http.post<NtisServiceOrderRequest>(url, record);
  }

  saveServiceOrdererDetails(orderDetails: NtisServiceOrderRequest): Observable<NtisServiceOrderRequest> {
    const url = `${this.techSupRestUrl}/save-service-order`;
    return this.http.post<NtisServiceOrderRequest>(url, orderDetails);
  }

  getServiceOrderDetails(id: string): Observable<NtisServiceOrderRequest> {
    return this.http.post<NtisServiceOrderRequest>(this.techSupRestUrl + '/get-order-details', id);
  }

  updateOrderDetails(order: NtisOrderDetails): Observable<NtisOrderDetails> {
    const url = `${this.techSupRestUrl}/update-order`;
    return this.http.post<NtisOrderDetails>(url, order);
  }

  sendOrderNotifications(orderId: number): Observable<void> {
    const url = `${this.techSupRestUrl}/send-order-notifications`;
    return this.http.post<void>(url, orderId.toString());
  }

  sendOrderEditNotifications(orderId: number, actionType?: string): Observable<void> {
    const url = `${this.techSupRestUrl}/send-order-edit-notifications`;
    return this.http.post<void>(url, { id: orderId, actionType: actionType });
  }

  getOrderByToken(token: string): Observable<number> {
    const url = `${this.techSupRestUrl}/get-order-by-token`;
    return this.http.post<number>(url, token);
  }
  // Service order (end)
}
