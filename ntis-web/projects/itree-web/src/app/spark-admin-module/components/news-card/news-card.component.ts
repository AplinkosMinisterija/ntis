import { Component, Input } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NewsBrowseRow } from '@itree-web/src/app/spark-admin-module/models/browse-pages';
import { MenuItem } from 'primeng/api';
import { SPR_NEWS_LIST } from '../../../ntis-shared/constants/forms.const';
import { DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-news-card',
  templateUrl: './news-card.component.html',
  styleUrls: ['./news-card.component.scss'],
})
export class NewsCardComponent {
  readonly translationsRef = 'pages.newsBrowse';
  readonly DB_BOOLEAN_TRUE = DB_BOOLEAN_TRUE;

  readonly routingConst = RoutingConst;
  routerUrl: string;
  @Input() rowIndex: number = 0;
  @Input() isNewsGroup: boolean = false;
  @Input() isBlank: boolean = false;
  @Input() rowData: NewsBrowseRow = {} as NewsBrowseRow;
  @Input() menuItems: Record<string, MenuItem[]> = {};
  @Input() showVisibility: boolean = true;
  showDropdown: boolean = false;
  selectedTab: number = null;
  destroy$ = new Subject<void>();
  baseUrl: string = `/${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.NEWS_LIST}/${'regular'}`;

  constructor(
    private router: Router,
    private authService: AuthService,
    public faIconService: FaIconsService,
    private activatedRoute: ActivatedRoute
  ) {
    this.showDropdown = this.authService.isFormActionEnabled(SPR_NEWS_LIST, ActionsEnum.ACTIONS_CREATE);
    this.routerUrl = this.router.url;
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.selectedTab = Number(params['selectedTab']);
    });
  }

  navigateToView(): void {
    void this.router.navigate([this.baseUrl, this.routingConst.VIEW, this.rowData.nw_id], {
      queryParams: { selectedTab: this.selectedTab },
    });
  }
}
