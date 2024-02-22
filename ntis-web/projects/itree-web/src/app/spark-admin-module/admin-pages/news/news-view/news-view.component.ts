import { Component, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActivatedRoute, Router } from '@angular/router';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisNewsEditModel, SprFile } from '@itree-commons/src/lib/model/api/api';
import { FileUploadService } from '@itree-commons/src/lib/services/file-upload.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-news-view',
  templateUrl: './news-view.component.html',
  styleUrls: ['./news-view.component.scss'],
})
export class NewsViewComponent implements OnInit {
  readonly translationsRef = 'pages.newsView';
  protected data: NtisNewsEditModel;
  private returnUrl: string[];
  protected recordId: string = null;
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;
  destroy$: Subject<boolean> = new Subject<boolean>();
  currentTab: number;

  constructor(
    protected route: ActivatedRoute,
    private fileUploadService: FileUploadService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      const type = params[RoutingConst.TYPE] as string;
      this.recordId = params[RoutingConst.ID] as string;
      this.returnUrl = [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.NEWS_LIST, type];
      this.currentTab = Number(type.split('=')[1]);
    });
    this.activatedRoute.queryParams.pipe(takeUntilDestroyed()).subscribe((params) => {
      this.currentTab = params['selectedTab'] as number;
    });
  }

  ngOnInit(): void {
    this.data = this.route.snapshot.data['newsViewData'] as NtisNewsEditModel;
  }

  protected downloadFile(file: SprFile): void {
    this.fileUploadService.downloadFile(file);
  }

  onReturn(): void {
    const queryParams = { selectedTab: this.currentTab };
    void this.router.navigate(this.returnUrl, { queryParams });
  }
}
