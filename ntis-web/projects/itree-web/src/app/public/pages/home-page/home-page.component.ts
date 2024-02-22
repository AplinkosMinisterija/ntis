import { AuthUtil } from '@itree/ngx-s2-commons';
import { Component, OnInit } from '@angular/core';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisNewsEditModel } from '@itree-commons/src/lib/model/api/api';
import { NewsService } from '@itree-web/src/app/spark-admin-module/admin-services/news.service';
import { NtisPageTemplateType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent implements OnInit {
  readonly RoutingConst = RoutingConst;
  isUserLoggedIn = AuthUtil.isLoggedIn();
  data: NtisNewsEditModel;

  constructor(private newsService: NewsService) {}
  ngOnInit(): void {
    this.newsService.viewPageTemplate(NtisPageTemplateType.HOME).subscribe((res) => {
      this.data = res;
    });
  }
}
