import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NewsEditModel, SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-public-news-view',
  templateUrl: './public-news-view.component.html',
  styleUrls: ['./public-news-view.component.scss'],
})
export class PublicNewsViewComponent {
  protected data: NewsEditModel;
  constructor(
    protected route: ActivatedRoute,
    private fileUploadService: FileUploadService,
    private location: Location
  ) {
    this.data = this.route.snapshot.data['newsViewData'] as NewsEditModel;
  }

  protected downloadFile(file: SprFile): void {
    this.fileUploadService.downloadFile(file, '/ntis-news/public/get-file/');
  }

  onReturn(): void {
    this.location.back();
  }
}
