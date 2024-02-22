import { Component, OnDestroy, OnInit } from '@angular/core';
import { AppDataService } from '../../services/app-data.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { HeaderNavService } from '../../services/header-nav.service';
import { Router } from '@angular/router';
import { Subject, Subscription, takeUntil } from 'rxjs';

export interface HeaderNav {
  linkTitle: string;
  linkType: string;
  linkIcon: string;
}

@Component({
  selector: 'spr-header-nav',
  templateUrl: './header-nav.component.html',
  styleUrls: ['./header-nav.component.scss'],
})
export class HeaderNavComponent implements OnInit, OnDestroy {
  destroy$ = new Subject<void>();
  public links: HeaderNav[] = [];
  linkType: string = RoutingConst.SEWERAGE;
  private subscription: Subscription;
  defaultNewsCategory: string = '';

  constructor(
    private appDataService: AppDataService,
    private commonFormServices: CommonFormServices,
    private headerNavService: HeaderNavService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.defaultNewsCategory = this.appDataService.getPropertyValue('DEFAULT_NEWS_CATEGORY');

    this.links = [
      {
        linkTitle: '',
        linkType: RoutingConst.SEWERAGE,
        linkIcon: 'person',
      },
      {
        linkTitle: '',
        linkType: RoutingConst.SERVICE,
        linkIcon: 'local_shipping',
      },
      {
        linkTitle: '',
        linkType: RoutingConst.REGULATORY,
        linkIcon: 'account_balance',
      },
      {
        linkTitle: '',
        linkType: RoutingConst.NEWS_LIST,
        linkIcon: 'newspaper',
      },
    ];

    this.links.forEach((element) => {
      this.commonFormServices.translate.get(`pages.login.${element.linkType}`).subscribe((translation: string) => {
        element.linkTitle = translation;
      });
    });

    this.headerNavService.sendHeaderNav(this.links[0]);

    this.headerNavService.menuLink$.pipe(takeUntil(this.destroy$)).subscribe((data) => {
      if (data) {
        this.linkType = data.linkType;
      }
    });
  }

  sendData(item: HeaderNav): void {
    if (RoutingConst.NEWS_LIST === item.linkType && this.defaultNewsCategory) {
      void this.router.navigate([RoutingConst.NEWS_LIST, this.defaultNewsCategory]);
    } else {
      void this.router.navigate([RoutingConst.LOGIN]);
      this.headerNavService.sendHeaderNav(item);
    }
    this.linkType = item.linkType;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }
}
