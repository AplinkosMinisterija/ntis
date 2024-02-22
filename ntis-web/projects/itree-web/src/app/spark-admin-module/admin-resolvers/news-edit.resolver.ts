import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisNewsEditModel } from '@itree-commons/src/lib/model/api/api';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable, map } from 'rxjs';
import { NewsService } from '../admin-services/news.service';

@Injectable({
  providedIn: 'root',
})
export class NewsEditResolver {
  constructor(private newsService: NewsService, protected commonFormServices: CommonFormServices) {}

  resolve(route: ActivatedRouteSnapshot): Observable<NtisNewsEditModel> {
    const recordId = route.paramMap.get(RoutingConst.ID);
    return this.newsService.get(recordId).pipe(
      map((data: NtisNewsEditModel) => {
        if (!data) {
          const selectedTab = route.queryParamMap.get('selectedTab');
          void this.commonFormServices.router.navigate(
            [RoutingConst.INTERNAL, RoutingConst.ADMIN, RoutingConst.NEWS_LIST, RoutingConst.REGULAR],
            {
              queryParams: { selectedTab: selectedTab },
            }
          );
        }
        return data;
      })
    );
  }
}
