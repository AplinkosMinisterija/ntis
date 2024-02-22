import { HttpEvent, HttpResponse } from '@angular/common/http';
import { FileLoader } from '@ckeditor/ckeditor5-upload';
import { SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';

export class MyUploadAdapter {
  constructor(private loader: FileLoader, private fileUploadService: FileUploadService) {}

  upload(): Promise<any> {
    return this.loader.file
      .then((file) => {
        return this.fileUploadService.uploadMultiple([file]).toPromise();
      })
      .then((event: HttpEvent<SprFile[]>) => {
        if (event instanceof HttpResponse) {
          const responseData = event.body;
          if (responseData && responseData.length > 0) {
            const fileDao: SprFile = {
              fil_content_type: responseData[0].fil_content_type,
              fil_key: responseData[0].fil_key,
              fil_name: responseData[0].fil_name,
              fil_size: responseData[0].fil_size,
              fil_status: responseData[0].fil_status,
              fil_status_date: responseData[0].fil_status_date,
            };
            return this.convertBlobToBase64(fileDao);
          }
        }
        throw new Error('Invalid upload response');
      })
      .then((base64Url) => {
        return { default: base64Url };
      });
  }

  convertBlobToBase64(fileDao: SprFile): Promise<string> {
    return new Promise((resolve, reject) => {
      this.fileUploadService.viewFile(fileDao).subscribe(
        (blobUrl: string) => {
          fetch(blobUrl)
            .then((res) => res.blob())
            .then((blob) => {
              const reader = new FileReader();
              reader.readAsDataURL(blob);
              reader.onloadend = (): void => {
                const base64data = reader.result as string;
                resolve(base64data);
              };
              reader.onerror = (error): void => {
                reject(error);
              };
            })
            .catch((error) => {
              reject(error);
            });
        },
        (err) => {
          reject(err);
        }
      );
    });
  }
}
