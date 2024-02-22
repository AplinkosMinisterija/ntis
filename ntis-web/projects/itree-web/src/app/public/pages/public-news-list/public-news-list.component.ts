import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NewsService } from '@itree-web/src/app/spark-admin-module/admin-services/news.service';
import { NewsBrowseRow } from '@itree-web/src/app/spark-admin-module/models/browse-pages';
import {
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
  getLang,
} from '@itree/ngx-s2-commons';
import { Subject, takeUntil } from 'rxjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { NewsDbFields } from '@itree-commons/src/constants/db-fields.const';

@Component({
  selector: 'app-public-news-list',
  templateUrl: './public-news-list.component.html',
  styleUrls: ['./public-news-list.component.scss'],
})
export class PublicNewsListComponent implements OnInit, OnDestroy {
  readonly routingConst = RoutingConst;
  totalRecords: number = 0;
  extendedParams: ExtendedSearchParam[] = [];
  data: NewsBrowseRow[] = [];
  hiddenData: NewsBrowseRow[] = [];
  hideLoadMore: boolean = false;
  allCategories: boolean = false;
  routerUrl: string = '';
  readonly translationsRef = 'pages.newsBrowse';
  pageType: string = '';
  destroy$ = new Subject<void>();

  searchForm: FormGroup;

  recordPerLoad: number = 10;

  pageLoaded: boolean = false;

  constructor(
    private newsService: NewsService,
    protected commonFormServices: CommonFormServices,
    protected route: ActivatedRoute,
    public router: Router,
    protected formBuilder: FormBuilder
  ) {
    this.detectUrlChanges();
  }

  ngOnInit(): void {
    this.data = [];
    this.pageType = this.router.url.split('/').pop();
    this.routerUrl = this.router.url;
    if (!this.pageLoaded) {
      this.loadData(true);
      this.pageLoaded = true;
    }
    this.buildForm();
  }

  detectUrlChanges(): void {
    this.router.events.pipe(takeUntil(this.destroy$)).subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.pageType = event.url.split('/').pop();
        this.routerUrl = event.url;

        if (this.pageLoaded) {
          this.loadData(true);
        }
      }
    });
  }

  buildForm(): void {
    this.searchForm = this.formBuilder.group({
      nw_title: new FormControl(null),
      nw_summary: new FormControl(null),
      nw_publication_date_from: new FormControl(undefined),
      nw_publication_date_to: new FormControl(undefined),
      nw_type: new FormControl(null),
      nw_all_categories: new FormControl(this.allCategories),
    });
  }

  loadData(cleanArray: boolean): void {
    this.addFormValues();
    if (cleanArray) {
      this.data = [];
    }
    const skipRows = this.data?.length;

    const pagingParams: Spr_paging_ot = {
      cnt: this.totalRecords,
      order_clause: null,
      page_size: this.recordPerLoad + 1,
      skip_rows: skipRows,
      sum_values: null,
    };

    this.extendedParams.push({
      paramName: NewsDbFields.NW_TYPE,
      paramValue: {
        condition: ExtendedSearchCondition.Equals,
        value: this.pageType,
        upperLower: ExtendedSearchUpperLower.Regular,
      },
    });
    this.extendedParams.push({
      paramName: 'p_lang',
      paramValue: {
        condition: ExtendedSearchCondition.Equals,
        value: getLang(),
        upperLower: ExtendedSearchUpperLower.Regular,
      },
    });
    this.newsService.getList(this.extendedParams, pagingParams, true).subscribe((results) => {
      this.hiddenData = results.data;

      if (this.hiddenData.length <= this.recordPerLoad) {
        this.hideLoadMore = true;
      } else {
        results.data.pop();
      }

      this.data = [...this.data, ...results.data];

      this.totalRecords = results.paging.cnt;
    });
  }

  addFormValues(): void {
    this.extendedParams = [];
    if (this.searchForm?.value) {
      Object.keys(this.searchForm?.value as FormGroup).forEach((control: string) => {
        if (this.searchForm.get(control).value) {
          this.extendedParams.push(this.searchForm.get(control).value as ExtendedSearchParam);
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }
}
