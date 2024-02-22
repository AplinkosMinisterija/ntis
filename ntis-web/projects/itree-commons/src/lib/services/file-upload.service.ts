import { HttpClient, HttpEvent, HttpEventType, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { S2File } from '@itree/ngx-s2-ui';
import { Observable, map } from 'rxjs';
import { sprFileToS2File } from '../utils/spr-file-to-s2-file';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  constructor(private http: HttpClient) {}

  upload(file: File): Observable<SprFile> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<SprFile>(REST_API_BASE_URL + '/common/upload-file', formData);
  }

  uploadMultiple(files: File[]): Observable<HttpEvent<SprFile[]>> {
    const formData: FormData = new FormData();
    files.forEach((file) => {
      formData.append(file.name, file);
    });
    return this.http.request(
      new HttpRequest('POST', REST_API_BASE_URL + '/common/upload', formData, {
        reportProgress: true,
        responseType: 'json',
      })
    );
  }

  uploadWithProgress(file: File): Observable<HttpEvent<SprFile>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    const req = new HttpRequest('POST', REST_API_BASE_URL + '/common/upload-file', formData, {
      reportProgress: true,
      responseType: 'json',
    });
    return this.http.request(req);
  }

  deleteFile(fileToDelete: SprFile): Observable<null> {
    return this.http.post<null>(REST_API_BASE_URL + '/common/delete-file', fileToDelete);
  }

  downloadFile(fileDao: SprFile, restEdpoint?: string): void {
    this.http
      .get<Blob>(REST_API_BASE_URL + (restEdpoint || '/common/get-file/') + fileDao.fil_key, {
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

  viewFile(fileDao: SprFile): Observable<string> {
    return this.http
      .get<Blob>(REST_API_BASE_URL + '/common/get-file/' + fileDao.fil_key, {
        observe: 'response',
        responseType: 'blob' as 'json',
      })
      .pipe(
        map((file: HttpResponse<Blob>) => {
          const dataType = fileDao.fil_content_type;
          return window.URL.createObjectURL(new Blob([file.body], { type: dataType }));
        })
      );
  }

  s2AutoUploadMethod(files: S2File[]): Observable<HttpEvent<S2File[]>> {
    return this.uploadMultiple(files.map((file) => file.file)).pipe(
      map<HttpEvent<SprFile[]>, HttpEvent<S2File[]>>((event) => {
        if (event.type === HttpEventType.Response && Array.isArray(event.body)) {
          return new HttpResponse({
            headers: event.headers,
            status: event.status,
            url: event.url,
            body: event.body?.map?.((sprFile) => sprFileToS2File(sprFile)),
          });
        }
        return event as HttpEvent<S2File[]>;
      })
    );
  }
}
