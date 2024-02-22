import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthUtil, CommonFormServices } from '@itree/ngx-s2-commons';
import { ConfirmationService } from 'primeng/api';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NewsService } from '@itree-web/src/app/spark-admin-module/admin-services/news.service';
import { NtisPageTemplateType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisNewsEditModel } from '@itree-commons/src/lib/model/api/api';

@Component({
  selector: 'app-terms-and-conditions-page',
  templateUrl: './terms-and-conditions-page.component.html',
  styleUrls: ['./terms-and-conditions-page.component.scss'],
})
export class TermsAndConditionsPageComponent implements OnInit {
  data: NtisNewsEditModel;

  constructor(
    private authService: AuthService,
    protected commonFormServices: CommonFormServices,
    private confirmationService: ConfirmationService,
    private router: Router,
    private newsService: NewsService
  ) {}

  ngOnInit(): void {
    this.newsService.viewPageTemplate(NtisPageTemplateType.TERMS_AND_CONDITIONS).subscribe((res) => {
      this.data = res;
    });
  }

  accept(): void {
    this.authService.acceptUserTerms('Y').subscribe(() => {
      const sessionInfo = AuthUtil.getSessionInfo();
      sessionInfo.usrTermsAccepted = 'Y';
      AuthUtil.updateSessionInfo(sessionInfo);
      AuthUtil.setTermsAccepted(true);
      this.authService.checkIfPersonHasEmail().subscribe((value) => {
        if (!value) {
          void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.SPARK_PROFILE_SETTINGS]);
        } else {
          void this.router.navigate(['/', RoutingConst.INTERNAL, RoutingConst.DASHBOARD]);
        }
      });
    });
  }

  logout(): void {
    this.authService.logout().subscribe(() => {
      window.location.replace('/' + RoutingConst.LOGIN);
    });
  }

  cancel(): void {
    this.commonFormServices.translate.get('pages.termsAndConditions.cancelText').subscribe((translation: string) => {
      this.confirmationService.confirm({
        message: translation,
        accept: () => {
          this.logout();
        },
      });
    });
  }
}
