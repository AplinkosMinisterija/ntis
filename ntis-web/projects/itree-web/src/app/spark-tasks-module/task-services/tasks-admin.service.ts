import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Spr_paging_ot } from '@itree/ngx-s2-commons';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { BrowseFormSearchResult, SprTaskEditModel, SprUsersDAO } from '@itree-commons/src/lib/model/api/api';
import { CommonResult } from '@itree-commons/src/lib/model/api';
import { Observable } from 'rxjs';
import { SprTasksBrowseRow } from '../models/browse-pages';
import { SprTaskCard } from '../models/task-card';

@Injectable({
  providedIn: 'root',
})
export class TasksAdminService {
  constructor(private http: HttpClient) {}
  // --- Tasks start
  findTasks(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, unknown>
  ): Observable<BrowseFormSearchResult<SprTasksBrowseRow>> {
    const url = REST_API_BASE_URL + '/spark-tasks/find-tasks';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray };
    return this.http.post<BrowseFormSearchResult<SprTasksBrowseRow>>(url, myPostBody);
  }

  getTaskPersonList(): Observable<CommonResult<SprUsersDAO>> {
    const url = REST_API_BASE_URL + '/spark-tasks/get-spark-person-list';
    return this.http.post<CommonResult<SprUsersDAO>>(url, null);
  }

  setTaskRecord(formData: SprTaskEditModel): Observable<SprTaskEditModel> {
    return this.http.post<SprTaskEditModel>(REST_API_BASE_URL + '/spark-tasks/set-spark-task', formData);
  }

  findTaskCards(
    pagingParams: Spr_paging_ot,
    searchParams: Map<string, string | boolean>
  ): Observable<BrowseFormSearchResult<SprTaskCard>> {
    const url = REST_API_BASE_URL + '/spark-tasks/find-task-cards';
    const paramArray = Array.from(searchParams.entries());
    const myPostBody = { pagingParams, params: paramArray };
    return this.http.post<BrowseFormSearchResult<SprTaskCard>>(url, myPostBody);
  }

  getTaskRecord(id: string, actionType?: string): Observable<SprTaskEditModel> {
    return this.http.post<SprTaskEditModel>(REST_API_BASE_URL + '/spark-tasks/get-spark-task', { id, actionType });
  }

  delTasksRecord(id: string): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/spark-tasks/del-spark-task', { id });
  }

  // --- Tasks end;
}
