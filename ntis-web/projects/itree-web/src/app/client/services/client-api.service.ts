import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { NtisINTSDashboardModel, SprApiKeysDAO, SprFile, SprProfile } from '@itree-commons/src/lib/model/api/api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientApiService {
  constructor(private http: HttpClient) {}

  // --- Profile start
  getProfileSettings(): Observable<SprProfile> {
    const url = REST_API_BASE_URL + '/spark/get-spark-profile-settings';
    return this.http.post<SprProfile>(url, null);
  }

  updateProfileSettings(params: SprProfile): Observable<null> {
    const url = REST_API_BASE_URL + '/spark/update-spark-profile-settings';
    return this.http.post<null>(url, params);
  }

  getProfileReport(): Observable<SprFile> {
    const url = REST_API_BASE_URL + '/spark/get-profile-report';
    return this.http.post<SprFile>(url, '{}');
  }

  generateApiKey(): Observable<SprApiKeysDAO> {
    const url = REST_API_BASE_URL + '/spark/generate-api-key';
    return this.http.post<SprApiKeysDAO>(url, null);
  }
  regenerateApiKey(id: number): Observable<SprApiKeysDAO> {
    const url = REST_API_BASE_URL + '/spark/regenerate-api-key';
    return this.http.post<SprApiKeysDAO>(url, id);
  }
  // --- Profile end;

  // --- INTS Owner dashboard start
  getINTSDashboardInfo(): Observable<NtisINTSDashboardModel> {
    const url = REST_API_BASE_URL + '/ntis-tech-supp/get-ints-dashboard';
    return this.http.post<NtisINTSDashboardModel>(url, null);
  }
  removeFacility(id: string): Observable<null> {
    const url = REST_API_BASE_URL + '/ntis-tech-supp/remove-facility';
    return this.http.post<null>(url, id);
  }

  updateSelectedWtf(selectedWtf: number): Observable<null> {
    const url = REST_API_BASE_URL + '/ntis-tech-supp/update-selected-wtf';
    return this.http.post<null>(url, selectedWtf.toString());
  }
  // --- INTS Owner dashboard end
}
