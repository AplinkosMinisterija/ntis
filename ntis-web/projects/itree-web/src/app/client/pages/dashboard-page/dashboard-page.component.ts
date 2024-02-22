import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NTIS_INTS_OWNER_DASHBOARD } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { NewsService } from '@itree-web/src/app/spark-admin-module/admin-services/news.service';
import { NtisNewsEditModel } from '@itree-commons/src/lib/model/api/api';
import { NtisPageTemplateType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss'],
})
export class DashboardPageComponent implements OnInit {
  data: NtisNewsEditModel;

  constructor(authService: AuthService, router: Router, private newsService: NewsService) {
    if (authService.isFormActionEnabled(NTIS_INTS_OWNER_DASHBOARD, ActionsEnum.INTS_OWNER_ACTIONS)) {
      void router.navigateByUrl('/' + RoutingConst.INTERNAL + '/' + NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD);
    }
  }
  ngOnInit(): void {
    this.newsService.viewPageTemplate(NtisPageTemplateType.INT_DASHBOARD).subscribe((res) => {
      this.data = res;
    });
  }
}
