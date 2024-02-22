import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisNewsEditModel } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable, map } from 'rxjs';
import { NewsService } from '../admin-services/news.service';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class NewsViewResolver {
  constructor(
    private newsService: NewsService,
    private authService: AuthService,
    protected commonFormServices: CommonFormServices
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<NtisNewsEditModel> {
    const recordId = route.paramMap.get(RoutingConst.ID);
    const isPublic = this.authService.loadedFormActions.roleName === AuthService.PUBLIC_ROLE_CODE;

    return this.newsService.view(recordId, isPublic).pipe(
      map((data: NtisNewsEditModel) => {
        if (!data) {
          const selectedTab = route.queryParamMap.get('selectedTab');
          if (!isPublic) {
            void this.commonFormServices.router.navigate(
              [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.NEWS_LIST, RoutingConst.REGULAR],
              {
                queryParams: { selectedTab: selectedTab },
              }
            );
          } else {
            void this.commonFormServices.router.navigate([RoutingConst.NEWS_LIST, RoutingConst.REGULAR], {
              queryParams: { selectedTab: selectedTab },
            });
          }
        }
        return data;
      })
    );
  }
}
