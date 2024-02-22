import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExtendedSearchParam, S2ViolatedConstraint, Spr_paging_ot } from '@itree/ngx-s2-commons';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  ApiKey,
  BrowseFormSearchResult,
  CacheInfo,
  CodeDictionaryModel,
  ForeignKeyParams,
  MenuStructureRequest,
  NtisMstAdditionalText,
  OrgUserRequest,
  OrgUserRoleRequest,
  OrganizationRoleRequest,
  PredefinedFilterRestDataModel,
  RecordIdentifier,
  RefTranslationsObj,
  RoleRequest,
  RoleRolesDate,
  RoleRolesRecord,
  SprAuditableTablesDAO,
  SprFormDataFiltersDAO,
  SprFormsDAO,
  SprJobAvailableTemplate,
  SprJobDefinitionsDAO,
  SprListIdKeyValue,
  SprMenuStructuresDAO,
  SprOrganizationsDAOGen,
  SprOrganizationsNtisDAO,
  SprPersonsDAO,
  SprPropertiesModel,
  SprRefCodesDAO,
  SprRefDictionariesDAO,
  SprRolesDAO,
  SprTemplate,
  SprUsersDAO,
  SprUsersNtisDAO,
  SwitchStatus,
  UserRoleDate,
  UserRoleRequest,
} from '@itree-commons/src/lib/model/api/api';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { BehaviorSubject, Observable, map } from 'rxjs';
import {
  RoleActionsMultiAnswer,
  RoleActionsSingleAnswer,
} from '../admin-pages/roles/role-actions-page/role-actions-page.component';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import {
  AdminReviewsBrowseRow,
  OrgUserRolesDetails,
  PropertiesBrowse,
  RoleActionsRoleDetails,
  RoleFormActions,
  RoleFormActionsSelected,
  RoleUsersDetails,
  SprApiKeyBrowseRow,
  SprAuditableTablesBrowseRow,
  SprClosedSessionsBrowseRow,
  SprCodesTranslationsBrowseRow,
  SprDictionariesBrowseRow,
  SprFormsBrowseRow,
  SprJobRequestsBrowseRow,
  SprJobRequestsJobItem,
  SprJobsBrowseRow,
  SprMenuStructureBrowseRow,
  SprOpenSessionsBrowseRow,
  SprOrgAvailableRolesBrowseRow,
  SprOrgUsersListRow,
  SprOrganizationsBrowseRow,
  SprPersonBrowseRow,
  SprRoleActions,
  SprRoleOrganizationsBrowseRow,
  SprRolesBrowseRow,
  SprTemplatesBrowseRow,
  SprUserApiKeyBrowseRow,
  SprUserOrgsListRow,
  SprUsersBrowseRow,
  UserRolesDetails,
} from '../models/browse-pages';
import {
  MenuStructureForm,
  SprDictionary,
  SprMenuStructureTreeItem,
  SprOrganizationResult,
} from '../models/edit-pages';
import { BrowseFormSearchPostBody } from '@itree-commons/src/lib/model/browse-pages';
import { SprUsersWithoutRoles } from '../admin-pages/users/users-browse-page/users-browse-page.component';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  readonly SPARK_REST_URL = REST_API_BASE_URL + '/spark';

  constructor(private http: HttpClient) {}

  orgUserRolesAssignedSubject = new BehaviorSubject<void>(null);

  // --- Roles start
  getRoleList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    lang: string,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprRolesBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-roles';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_lang', lang]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprRolesBrowseRow>>(url, myPostBody);
  }

  getRoleRecord(id: string, actionType?: string): Observable<SprRolesDAO> {
    const body: RecordIdentifier = { id, actionType };
    return this.http.post<SprRolesDAO>(REST_API_BASE_URL + '/spark/get-spark-role', body);
  }

  setRoleRecord(role: SprRolesDAO): Observable<SprRolesDAO> {
    return this.http.post<SprRolesDAO>(REST_API_BASE_URL + '/spark/set-spark-role', role);
  }

  delRoleRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-role', { id });
  }

  getRoleOrganizationsList(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprRoleOrganizationsBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-role-organizations';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_rol_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprRoleOrganizationsBrowseRow>>(url, myPostBody).pipe(
      map((result) => {
        result.data.forEach((roleOrg) => {
          roleOrg.isSelected = roleOrg.belongs === DB_BOOLEAN_TRUE;
        });
        return result;
      })
    );
  }
  // --- Roles end

  // --- Role actions start
  getRoleForms(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    p_rol_id: string,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprRoleActions>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-role-actions';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_rol_id', p_rol_id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprRoleActions>>(url, myPostBody);
  }

  getRoleActionsRecord(roa_id: string, frm_id: string): Observable<CommonResult<RoleFormActions>> {
    return this.http.post<CommonResult<RoleFormActions>>(REST_API_BASE_URL + '/spark/get-spark-role-action', {
      roa_id,
      frm_id,
    });
  }

  getRoleActions(roleId: string): Observable<CommonResult<SprRoleActions>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-role-actions';
    return this.http.post<CommonResult<SprRoleActions>>(url, roleId);
  }

  getFormActions(id: string): Observable<CommonResult<RoleFormActionsSelected>> {
    return this.http.post<CommonResult<RoleFormActionsSelected>>(REST_API_BASE_URL + '/spark/find-spark-form-actions', {
      id,
    });
  }

  setRoleFormActionsRecord(answerObj: RoleActionsSingleAnswer): Observable<RoleActionsSingleAnswer> {
    return this.http.post<RoleActionsSingleAnswer>(REST_API_BASE_URL + '/spark/set-spark-role-action', answerObj);
  }

  setRoleFormsRecord(answerObj: RoleActionsMultiAnswer): Observable<RoleActionsMultiAnswer> {
    return this.http.post<RoleActionsMultiAnswer>(REST_API_BASE_URL + '/spark/set-spark-role-actions', answerObj);
  }

  setDefaultRoleForm(answerObj: RoleRequest): Observable<null> {
    return this.http.post<null>(this.SPARK_REST_URL + '/set-default-role-form', answerObj);
  }
  // --- Role actions end

  // --- Role users start
  getRoleUsersRecord(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<RoleUsersDetails>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-role-users';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_rol_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<RoleUsersDetails>>(url, myPostBody).pipe(
      map((result) => {
        result.data.forEach((user) => {
          user.isSelected = user.belongs === DB_BOOLEAN_TRUE;
        });
        return result;
      })
    );
  }
  // --- Role users end

  //user-roles START
  getUserRolesRecord(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<UserRolesDetails>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-usr-roles';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_usr_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<UserRolesDetails>>(url, myPostBody);
  }

  setUserRolesDate(uro_id: string, dateTo: Date, dateFrom: Date): Observable<UserRoleDate> {
    const userRoleDate: UserRoleDate = {} as UserRoleDate;
    userRoleDate.uro_id = uro_id;
    userRoleDate.uro_date_to = dateTo;
    userRoleDate.uro_date_from = dateFrom;
    return this.http.post<UserRoleDate>(REST_API_BASE_URL + '/spark/set-spark-user-roles-date', userRoleDate);
  }

  setUserRolesData(userRoleRequests: UserRoleRequest[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/set-spark-user-roles', userRoleRequests);
  }

  // user-roles END

  // org-user-roles START
  setOrgUserRolesDate(uro_id: string, dateTo: Date, dateFrom: Date): Observable<UserRoleDate> {
    const userRoleDate: UserRoleDate = {} as UserRoleDate;
    userRoleDate.uro_id = uro_id;
    userRoleDate.uro_date_to = dateTo;
    userRoleDate.uro_date_from = dateFrom;
    return this.http.post<UserRoleDate>(REST_API_BASE_URL + '/spark/set-spark-org-user-roles-date', userRoleDate);
  }

  setOrgUserRolesData(userRolesData: OrgUserRoleRequest[]): Observable<OrgUserRoleRequest[]> {
    return this.http.post<OrgUserRoleRequest[]>(REST_API_BASE_URL + '/spark/set-spark-org-user-roles', userRolesData);
  }

  deltOrgUserRolesData(userRolesData: OrgUserRoleRequest[]): Observable<OrgUserRoleRequest> {
    return this.http.post<OrgUserRoleRequest>(REST_API_BASE_URL + '/spark/del-spark-org-user-roles', userRolesData);
  }

  getOrgUserRolesRecord(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrgUserRolesDetails>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-org-usr-roles';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_ou_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrgUserRolesDetails>>(url, myPostBody);
  }

  getNtisOrgUserRolesRecord(
    id: number,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<OrgUserRolesDetails>> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/get-ntis-org-user-roles';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_ou_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<OrgUserRolesDetails>>(url, myPostBody);
  }
  // org-user-roles END

  // --- Users start
  getUsersList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    lang: string,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprUsersBrowseRow>> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/get-ntis-users';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_lang', lang]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprUsersBrowseRow>>(url, myPostBody);
  }

  checkIfOrgUserRolesAssigned(): Observable<boolean> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/check-if-roles-assigned';
    return this.http.post<boolean>(url, null);
  }

  getUsersWithoutRoles(): Observable<CommonResult<SprUsersWithoutRoles>> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/get-users-without-roles';
    return this.http.post<CommonResult<SprUsersWithoutRoles>>(url, null);
  }

  delNtisOrgUserRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/ntis-system-admin/del-ntis-org-user', { id });
  }

  getNtisUserRecord(id: string, actionType?: string): Observable<SprUsersNtisDAO> {
    return this.http.post<SprUsersNtisDAO>(REST_API_BASE_URL + '/ntis-system-admin/get-ntis-user', { id, actionType });
  }

  setUserRecord(rolRec: SprUsersNtisDAO): Observable<SprUsersNtisDAO> {
    return this.http.post<SprUsersNtisDAO>(REST_API_BASE_URL + '/ntis-system-admin/set-ntis-user', rolRec);
  }

  delUsersRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-user', { id });
  }

  getUserRecord(id: string, actionType?: string): Observable<SprUsersNtisDAO> {
    return this.http.post<SprUsersNtisDAO>(REST_API_BASE_URL + '/spark/get-spark-user', { id, actionType });
  }

  checkUserRecordConstraints(data: SprUsersDAO): Observable<S2ViolatedConstraint[]> {
    return this.http.post<S2ViolatedConstraint[]>(REST_API_BASE_URL + '/spark/check-spark-user-constraints', data);
  }

  getUserOrgs(): Observable<BrowseFormSearchResult<SprOrganizationsDAOGen>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-user-orgs';
    return this.http.post<BrowseFormSearchResult<SprOrganizationsDAOGen>>(url, null);
  }

  changeUserDisabledStatus(id: string): Observable<SprUsersBrowseRow> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/change-spark-user-status', { id });
  }

  informNewUser(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/inform-spark-new-user', { id });
  }

  informUserPasswordReset(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/inform-spark-user-password-reset', { id });
  }

  getUserOrgsList(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprUserOrgsListRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-user-orgs';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_usr_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprUserOrgsListRow>>(url, myPostBody).pipe(
      map((result) => {
        result.data.forEach((userOrg) => {
          userOrg.isSelected = userOrg.belongs === DB_BOOLEAN_TRUE;
        });
        return result;
      })
    );
  }

  setUserOrgData(userOrgData: OrgUserRequest[]): Observable<OrgUserRequest> {
    return this.http.post<OrgUserRequest>(REST_API_BASE_URL + '/spark/set-spark-user-orgs', userOrgData);
  }

  setUserOrgDate(userOrgData: OrgUserRequest): Observable<OrgUserRequest> {
    return this.http.post<OrgUserRequest>(REST_API_BASE_URL + '/spark/set-spark-user-org-date', userOrgData);
  }
  // --- Users end

  // --- Persons start
  getPersonList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprPersonBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-persons';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprPersonBrowseRow>>(url, myPostBody);
  }

  getPersonRecord(id: string, actionType: string): Observable<SprPersonsDAO> {
    return this.http.post<SprPersonsDAO>(REST_API_BASE_URL + '/spark/get-spark-person', { id, actionType });
  }

  findPersons(value: ForeignKeyParams): Observable<CommonResult<SprPersonsDAO>> {
    return this.http.post<CommonResult<SprPersonsDAO>>(`${this.SPARK_REST_URL}/find-persons`, value);
  }

  setPersonRecord(formData: SprPersonsDAO): Observable<SprPersonsDAO> {
    return this.http.post<SprPersonsDAO>(REST_API_BASE_URL + '/spark/set-spark-person', formData);
  }

  delPersonRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-person', { id });
  }

  // --- Persons end

  // --- Menu structure start
  getMenuStructureList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    lang: string,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprMenuStructureBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-menu-structures';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_lang', lang]);
    const myPostBody = { pagingParams: pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprMenuStructureBrowseRow>>(url, myPostBody);
  }

  getMenuStructureRecord(id: string, actionType?: string): Observable<SprMenuStructuresDAO> {
    return this.http.post<SprMenuStructuresDAO>(REST_API_BASE_URL + '/spark/get-spark-menu-structure', {
      id,
      actionType,
    });
  }

  getMstAdditionalText(id: string): Observable<CommonResult<NtisMstAdditionalText>> {
    return this.http.post<CommonResult<NtisMstAdditionalText>>(
      REST_API_BASE_URL + '/ntis-system-admin/get-additional-mst-item',
      id
    );
  }

  setMenuStructureRecord(menuStructureRec: SprMenuStructuresDAO): Observable<SprMenuStructuresDAO> {
    return this.http.post<SprMenuStructuresDAO>(
      REST_API_BASE_URL + '/spark/set-spark-menu-structure',
      menuStructureRec
    );
  }

  setMstAdditionalText(menuStructureRec: NtisMstAdditionalText): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/ntis-system-admin/set-additional-mst-item', menuStructureRec);
  }

  delMenuStructureRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-menu-structure', { id });
  }

  getMenuForms(): Observable<BrowseFormSearchResult<MenuStructureForm>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-menu-forms';
    return this.http.post<BrowseFormSearchResult<MenuStructureForm>>(url, null);
  }

  // Menu structure tree:
  setMenuTreeData(menuStructureRequests: MenuStructureRequest[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/update-spark-menu-structure-tree', menuStructureRequests);
  }

  setUnassignedPages(ids: number[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/update-spark-unassigned-page', ids);
  }

  getMenuTreeStructureList(
    lang: string,
    site: string,
    isPublic: string
  ): Observable<BrowseFormSearchResult<SprMenuStructureTreeItem>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-menu-structures-tree';
    const paramList = new Map<string, string>();
    paramList.set('p_lang', lang);
    paramList.set('p_site', site);
    paramList.set('p_is_public', isPublic);
    const paramArray = Array.from(paramList.entries());
    const myPostBody: BrowseFormSearchPostBody = { pagingParams: null, params: paramArray };
    return this.http.post<BrowseFormSearchResult<SprMenuStructureTreeItem>>(url, myPostBody);
  }

  getUnassignedMenuTreeStructureList(
    lang: string,
    site: string,
    isPublic: string
  ): Observable<BrowseFormSearchResult<SprMenuStructureTreeItem>> {
    const url = REST_API_BASE_URL + '/spark/find-spark-not-assigned-menu-pages';
    const paramList = new Map<string, string>();
    paramList.set('p_lang', lang);
    paramList.set('p_site', site);
    paramList.set('p_is_public', isPublic);
    const paramArray = Array.from(paramList.entries());
    const myPostBody: BrowseFormSearchPostBody = { pagingParams: null, params: paramArray };
    return this.http.post<BrowseFormSearchResult<SprMenuStructureTreeItem>>(url, myPostBody);
  }

  // --- Open Sessions start
  getSessionsOpen(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprOpenSessionsBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-open-sessions';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprOpenSessionsBrowseRow>>(url, myPostBody);
  }

  killSessions(sesList: string[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/kill-sessions', sesList);
  }

  killAllSessions(): Observable<null> {
    return this.http.get<null>(REST_API_BASE_URL + '/spark/kill-all-sessions');
  }

  // organizations:
  getOrganizationsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprOrganizationsBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-orgs';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprOrganizationsBrowseRow>>(url, myPostBody);
  }

  getOrganizationRecord(id: string, actionType?: string): Observable<SprOrganizationsNtisDAO> {
    return this.http.post<SprOrganizationsNtisDAO>(REST_API_BASE_URL + '/spark/get-spark-org', { id, actionType });
  }

  delOrganizationRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-org', { id });
  }

  setOrganizationRecord(orgRec: SprOrganizationsNtisDAO): Observable<SprOrganizationsNtisDAO> {
    return this.http.post<SprOrganizationsNtisDAO>(REST_API_BASE_URL + '/ntis-admin/set-spark-org', orgRec);
  }

  checkOrganizationConstraints(data: SprOrganizationsNtisDAO): Observable<S2ViolatedConstraint[]> {
    return this.http.post<S2ViolatedConstraint[]>(REST_API_BASE_URL + '/spark/check-organization-constraints', data);
  }

  getParentOrganizations(id: string, name: string): Observable<CommonResult<SprOrganizationResult>> {
    const postBody = { orgId: id, orgName: name };
    return this.http.post<CommonResult<SprOrganizationResult>>(
      REST_API_BASE_URL + '/spark/get-parent-organization',
      postBody
    );
  }

  getOrganizationRolesList(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprOrgAvailableRolesBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-org-roles';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_org_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprOrgAvailableRolesBrowseRow>>(url, myPostBody).pipe(
      map((result) => {
        result.data.forEach((orgRole) => {
          orgRole.isSelected = orgRole.belongs === DB_BOOLEAN_TRUE;
        });
        return result;
      })
    );
  }

  setOrganizationRolesData(orgRolesData: OrganizationRoleRequest[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/set-spark-org-roles', orgRolesData);
  }

  setOrganizationRolesDate(organizationForUpdate: OrganizationRoleRequest): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/set-spark-org-roles-date', organizationForUpdate);
  }

  getOrgUsersList(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprOrgUsersListRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-org-users';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_org_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprOrgUsersListRow>>(url, myPostBody).pipe(
      map((result) => {
        result.data.forEach((orgUser) => {
          orgUser.isSelected = orgUser.belongs === DB_BOOLEAN_TRUE;
        });
        return result;
      })
    );
  }

  setOrgUsersData(orgUserData: OrgUserRequest[]): Observable<OrgUserRequest> {
    return this.http.post<OrgUserRequest>(REST_API_BASE_URL + '/spark/set-spark-org-users', orgUserData);
  }

  setOrgUserDate(orgUserData: OrgUserRequest): Observable<OrgUserRequest> {
    return this.http.post<OrgUserRequest>(REST_API_BASE_URL + '/spark/set-spark-org-user-date', orgUserData);
  }

  // organizations end

  // --- Forms start
  getFormsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprFormsBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-forms';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprFormsBrowseRow>>(url, myPostBody);
  }

  delFormRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-form', { id });
  }

  getFormRecord(id: string, actionType?: string): Observable<SprFormsDAO> {
    return this.http.post<SprFormsDAO>(REST_API_BASE_URL + '/spark/get-spark-form', { id, actionType });
  }

  setFormRecord(rolRec: SprFormsDAO): Observable<SprFormsDAO> {
    return this.http.post<SprFormsDAO>(REST_API_BASE_URL + '/spark/set-spark-form', rolRec);
  }
  // --- Forms end

  // ------------------------------------------------------------------------------------------------------
  // Rest methods for spark cache management (start)
  // ------------------------------------------------------------------------------------------------------
  getCacheList(): Observable<CacheInfo[]> {
    return this.http.post<CacheInfo[]>(REST_API_BASE_URL + '/spark/get-spark-cache-list', null);
  }

  clearCache(id: string): Observable<CacheInfo[]> {
    return this.http.post<CacheInfo[]>(REST_API_BASE_URL + '/spark/clear-spark-cache', { id });
  }

  clearAllCaches(): Observable<CacheInfo[]> {
    return this.http.post<CacheInfo[]>(REST_API_BASE_URL + '/spark/clear-all-spark-caches', null);
  }

  // ------------------------------------------------------------------------------------------------------
  // Rest methods for spark cache management (end)
  // ------------------------------------------------------------------------------------------------------

  getParameterRecord(id: string, actionType?: string): Observable<SprPropertiesModel> {
    return this.http.post<SprPropertiesModel>(REST_API_BASE_URL + '/spark/get-spark-param', { id, actionType });
  }

  delParameterRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-param', { id });
  }

  setParameterRecord(prpRec: SprPropertiesModel): Observable<SprPropertiesModel> {
    return this.http.post<SprPropertiesModel>(REST_API_BASE_URL + '/spark/set-spark-param', prpRec);
  }

  checkParamConstraints(data: SprPropertiesModel): Observable<S2ViolatedConstraint[]> {
    return this.http.post<S2ViolatedConstraint[]>(REST_API_BASE_URL + '/spark/check-param-constraints', data);
  }

  getParameterList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<PropertiesBrowse>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-params';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<PropertiesBrowse>>(url, myPostBody);
  }

  getDictionaryList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprDictionariesBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-dictionaries';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprDictionariesBrowseRow>>(url, myPostBody);
  }

  getDictionariesList(): Observable<BrowseFormSearchResult<SprDictionary>> {
    const url = REST_API_BASE_URL + '/spark/get-dictionaries-list';
    return this.http.post<BrowseFormSearchResult<SprDictionary>>(url, null);
  }

  getDictionaryRecord(id: string, actionType?: string): Observable<SprRefDictionariesDAO> {
    return this.http.post<SprRefDictionariesDAO>(REST_API_BASE_URL + '/spark/get-spark-dictionary', { id, actionType });
  }

  delDictionaryRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-spark-dictionary', { id });
  }

  setDictionaryRecord(formData: SprRefDictionariesDAO): Observable<SprRefDictionariesDAO> {
    return this.http.post<SprRefDictionariesDAO>(REST_API_BASE_URL + '/spark/set-spark-dictionary', formData);
  }

  getRefCodeRecord(id: string, actionType?: string): Observable<SprRefCodesDAO> {
    return this.http.post<SprRefCodesDAO>(REST_API_BASE_URL + '/spark/get-ref-code', { id, actionType });
  }

  getCodeDictionary(id: string, actionType?: string): Observable<CodeDictionaryModel> {
    return this.http.post<CodeDictionaryModel>(REST_API_BASE_URL + '/spark/get-code-dictionary', { id, actionType });
  }

  setRefCodeRecord(formData: SprRefCodesDAO): Observable<SprRefCodesDAO> {
    return this.http.post<SprRefCodesDAO>(REST_API_BASE_URL + '/spark/set-ref-code', formData);
  }

  //klasifikatorių kodų importavimui
  setRefCodeRecords(formData: SprRefCodesDAO[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/set-ref-codes', formData);
  }

  delRefCode(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-ref-code', { id });
  }

  delTranslationRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/del-rft', { id });
  }

  setTranslation(record: RefTranslationsObj): Observable<RefTranslationsObj> {
    return this.http.post<RefTranslationsObj>(REST_API_BASE_URL + '/spark/update-rft', record);
  }

  getTranslationsList(p_table_name: string): Observable<RefTranslationsObj[]> {
    const url = REST_API_BASE_URL + '/spark/get-spark-translations';
    return this.http.post<RefTranslationsObj[]>(url, p_table_name);
  }

  getCodesList(
    p_table_name: string,
    pagingParams?: Spr_paging_ot,
    searchParams?: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprCodesTranslationsBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-codes';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_table_name', p_table_name]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprCodesTranslationsBrowseRow>>(url, myPostBody);
  }

  getCodes(p_table_name: string): Observable<SprCodesTranslationsBrowseRow[]> {
    const url = REST_API_BASE_URL + '/spark/get-codes';
    return this.http.post<SprCodesTranslationsBrowseRow[]>(url, p_table_name);
  }

  // --- Closed Sessions start
  getSessionClosedList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprClosedSessionsBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark/find-closed-sessions';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprClosedSessionsBrowseRow>>(url, myPostBody);
  }

  // --- Closed sessions end
  // --- Role actions Role start
  getRoleActionsRoleList(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<RoleActionsRoleDetails>> {
    const url = REST_API_BASE_URL + '/spark/get-spark-role-actions-role';
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_role_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<RoleActionsRoleDetails>>(url, myPostBody);
  }

  getRoleActionsRole(assigned_role_id: string, selected_role_id: string): Observable<CommonResult<RoleRequest>> {
    const url = `${REST_API_BASE_URL}/spark/get-spark-roles-role`;
    const body: RoleRolesRecord = {
      roa_assigned_rol_id: assigned_role_id,
      roa_rol_id: selected_role_id,
    };
    return this.http.post<CommonResult<RoleRequest>>(url, body);
  }

  setRolesData(roleRequest: RoleRequest[]): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark/set-spark-role-actions-role', roleRequest);
  }

  setRolesActionDate(roa_id: string, dateTo: Date, dateFrom: Date): Observable<null> {
    const body: RoleRolesDate = {
      roa_id,
      roa_date_from: dateFrom,
      roa_date_to: dateTo,
    };
    return this.http.post<null>(REST_API_BASE_URL + '/spark/set-spark-role-actions-role-date', body);
  }

  // Jobs:
  getJobsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprJobsBrowseRow>> {
    const url = `${this.SPARK_REST_URL}/get-jobs-list`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprJobsBrowseRow>>(url, postBody);
  }

  getJobRequestsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprJobRequestsBrowseRow>> {
    const url = `${this.SPARK_REST_URL}/get-job-requests-list`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprJobRequestsBrowseRow>>(url, postBody);
  }

  getJobRequestsNames(): Observable<CommonResult<SprJobRequestsJobItem>> {
    const url = `${this.SPARK_REST_URL}/get-job-requests-names-list`;
    return this.http.get<CommonResult<SprJobRequestsJobItem>>(url);
  }

  deleteJob(id: string): Observable<null> {
    const url = `${this.SPARK_REST_URL}/del-job`;
    return this.http.post<null>(url, { id });
  }

  getJob(id: string): Observable<SprJobDefinitionsDAO> {
    const url = `${this.SPARK_REST_URL}/get-job`;
    return this.http.post<SprJobDefinitionsDAO>(url, { id });
  }

  setJob(template: SprJobDefinitionsDAO): Observable<SprJobDefinitionsDAO> {
    const url = `${this.SPARK_REST_URL}/set-job`;
    return this.http.post<SprJobDefinitionsDAO>(url, template);
  }

  getJobAvailableTemplates(): Observable<SprJobAvailableTemplate> {
    const url = `${this.SPARK_REST_URL}/get-job-available-templates`;
    return this.http.post<SprJobAvailableTemplate>(url, null);
  }

  // Templates:
  getTemplatesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprTemplatesBrowseRow>> {
    const url = `${this.SPARK_REST_URL}/get-templates-list`;
    const paramArray = Array.from(searchParams.entries());
    const postBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprTemplatesBrowseRow>>(url, postBody);
  }

  deleteTemplate(id: string): Observable<null> {
    const url = `${this.SPARK_REST_URL}/del-template`;
    return this.http.post<null>(url, { id });
  }

  getTemplate(id: string): Observable<SprTemplate> {
    const url = `${this.SPARK_REST_URL}/get-template`;
    return this.http.post<SprTemplate>(url, { id });
  }

  setTemplate(template: SprTemplate): Observable<SprTemplate> {
    const url = `${this.SPARK_REST_URL}/set-template`;
    return this.http.post<SprTemplate>(url, template);
  }

  checkTemplateConstraints(data: SprTemplate): Observable<S2ViolatedConstraint[]> {
    return this.http.post<S2ViolatedConstraint[]>(REST_API_BASE_URL + '/spark/check-template-constraints', data);
  }

  // Audit:
  getAuditableTablesList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprAuditableTablesBrowseRow>> {
    const url = `${this.SPARK_REST_URL}/get-auditable-tables-list`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprAuditableTablesBrowseRow>>(url, myPostBody);
  }

  getAuditableTableRecList(
    id: string,
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<Record<string, string>>> {
    const url = `${this.SPARK_REST_URL}/get-auditable-table-rec-list`;
    const paramArray = Array.from(searchParams.entries());
    paramArray.push(['p_aut_id', id]);
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<Record<string, string>>>(url, myPostBody);
  }

  setAuditStatus(status: string, id: number): Observable<null> {
    const myPostBody: SwitchStatus = { id, status };
    return this.http.post<null>(this.SPARK_REST_URL + '/set-table-audit-status', myPostBody);
  }

  getAuditRecord(id: string): Observable<SprAuditableTablesDAO> {
    return this.http.post<SprAuditableTablesDAO>(REST_API_BASE_URL + '/spark/get-spark-auditable', { id });
  }

  setAuditRecord(rolRec: SprAuditableTablesDAO): Observable<SprAuditableTablesDAO> {
    return this.http.post<SprAuditableTablesDAO>(REST_API_BASE_URL + '/spark/set-spark-auditable', rolRec);
  }

  setFormPredefinedData(data: PredefinedFilterRestDataModel): Observable<PredefinedFilterRestDataModel> {
    const url = `${this.SPARK_REST_URL}/set-form-predefined-filter`;
    return this.http.post<PredefinedFilterRestDataModel>(url, data);
  }

  deleteFormPredefinedData(id: number): Observable<null> {
    const url = `${this.SPARK_REST_URL}/del-form-predefined-filter`;
    return this.http.post<null>(url, { id });
  }

  getFormPredefinedData(formCode: string): Observable<CommonResult<SprFormDataFiltersDAO>> {
    const url = `${this.SPARK_REST_URL}/get-form-predefined-filters`;
    return this.http.post<CommonResult<SprFormDataFiltersDAO>>(url, { formCode });
  }

  /**
   * Retrieves a list of API keys based on provided paging, search, and extended parameters.
   *
   * @param pagingParams       Paging parameters for controlling the list's pagination.
   * @param searchParams       Search parameters as a map for filtering the list.
   * @param extendedParams     Extended search parameters for additional filtering.
   * @returns                 An Observable containing the list of API keys.
   */
  getApiKeyRecList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprApiKeyBrowseRow>> {
    const url = `${this.SPARK_REST_URL}/get-api-list`;
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprApiKeyBrowseRow>>(url, myPostBody);
  }

  getNtisApiKeyRecList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprApiKeyBrowseRow>> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/get-ntis-api-list';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<SprApiKeyBrowseRow>>(url, myPostBody);
  }

  /**
   * Retrieves a specific API key record by its ID and optional action type.
   *
   * @param id          The ID of the API key to retrieve.
   * @returns           An Observable containing the API key record.
   */
  getApiKeyRecord(id: string): Observable<ApiKey> {
    return this.http.post<ApiKey>(`${REST_API_BASE_URL}/spark/get-api-key`, { id });
  }
  /**
   * Deletes an API key record by its ID.
   *
   * @param id  The ID of the API key record to delete.
   * @returns   An Observable with no data (null) upon successful deletion.
   */
  deleteApiKeyRecord(id: string): Observable<null> {
    const url = `${this.SPARK_REST_URL}/del-api-key`;
    return this.http.post<null>(url, { id });
  }

  /**
   * Sets or updates an API key record using the provided template.
   *
   * @param template  The API key template to use for setting or updating the record.
   * @returns         An Observable containing the updated or newly created API key record.
   */
  setApiKeyRecord(template: ApiKey): Observable<ApiKey> {
    const url = `${this.SPARK_REST_URL}/set-api-key`;
    return this.http.post<ApiKey>(url, template);
  }

  getApiOrgUserUsrId(template: ApiKey): Observable<number> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/get-api-usr-id';
    return this.http.post<number>(url, template);
  }
  /**
   * Changes the status of an API key record by its ID.
   *
   * @param id  The ID of the API key record whose status should be changed.
   * @returns   An Observable containing the updated API key record.
   */
  changeApiKeyStatus(id: number): Observable<SprUserApiKeyBrowseRow> {
    return this.http.post<null>(`${this.SPARK_REST_URL}/change-api-key-status`, { id });
  }

  /**
   *
   * This function is used to retrieve organization list.
   * @param value: ForeignKeyParams - Filtering parameters
   * @returns Observable<CommonResult<SprListIdKeyValue>> - An observable that emits the result of the server request.
   */
  getOrganizationList(value: ForeignKeyParams): Observable<SprListIdKeyValue[]> {
    const url = `${this.SPARK_REST_URL}/get-org-list`;
    return this.http.post<SprListIdKeyValue[]>(url, value);
  }

  /**
   *
   * This function is used to retrieve organization users list.
   * @param id: number - Organization ID
   * @returns Observable<CommonResult<SprListIdKeyValue>> - An observable that emits the result of the server request.
   */
  getOrganizationUsersList(id: string): Observable<SprListIdKeyValue[]> {
    const url = `${this.SPARK_REST_URL}/get-org-users-list`;
    return this.http.post<SprListIdKeyValue[]>(url, { id });
  }

  /**
   *
   * This function is used to retrieve organization list.
   * @param value: ForeignKeyParams - Filtering parameters
   * @returns Observable<CommonResult<SprListIdKeyValue>> - An observable that emits the result of the server request.
   */
  getApiUsersList(value: ForeignKeyParams): Observable<SprListIdKeyValue[]> {
    const url = `${this.SPARK_REST_URL}/get-api-users-list`;
    return this.http.post<SprListIdKeyValue[]>(url, value);
  }

  getAdminReviewsList(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<AdminReviewsBrowseRow>> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/get-reviews-for-admin';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray, extendedParams };
    return this.http.post<BrowseFormSearchResult<AdminReviewsBrowseRow>>(url, myPostBody);
  }

  markReviewAsRead(id: string): Observable<null> {
    const url = REST_API_BASE_URL + '/ntis-system-admin/mark-review-as-read-admin';
    return this.http.post<null>(url, id);
  }
}
