/**
 *
 * @note Po naujienų puslapių ir susijusių elementų perkėlimo iš "Spark"
 * prototipo, pakeitimai buvo atlikti pagal NTIS projektą.
 * @date 2023-05-24
 *
 */
import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  CommonFormServices,
  ExtendedSearchCondition,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NewsService } from '../../../admin-services/news.service';
import { MenuItem } from 'primeng/api';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { NewsBrowseRow } from '../../../models/browse-pages';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Subject, takeUntil } from 'rxjs';
import { fadeInFields } from '@itree-commons/src/lib/animations/animations';
import { SPR_NEWS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { NewsDbFields } from '@itree-commons/src/constants/db-fields.const';
import { optionList } from '@itree-commons/src/lib/components/select/select.component';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-news-browse',
  templateUrl: './news-browse.component.html',
  styleUrls: ['./news-browse.component.scss'],
  animations: [fadeInFields],
})
export class NewsBrowseComponent implements OnInit, OnDestroy {
  readonly translationsRef = 'pages.newsBrowse';
  readonly defaultCondition = ExtendedSearchCondition;
  pageType: string = null;
  selectedTab: number = null;
  baseUrl: string = `/${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.NEWS_LIST}/${'regular'}`;
  readonly routingConst = RoutingConst;
  protected rowMenuItems: Record<string, MenuItem[]> = {};
  protected showAddButton: boolean = false;
  totalRecords: number = 0;
  extendedParams: ExtendedSearchParam[] = [];
  searchForm: FormGroup;
  data: NewsBrowseRow[] = [];
  hiddenData: NewsBrowseRow[] = [];
  hideLoadMore: boolean = false;
  destroy$ = new Subject<void>();
  isPublicOptions: optionList[] = [];
  loadNewsTemplates: boolean = false;
  loadPageTemplates: boolean = false;
  tabPanels: string[] = ['.news', '.newsTemplates', '.pageTemplates'];

  recordPerLoad: number = 5;
  pageLoaded: boolean = false;

  constructor(
    private newsService: NewsService,
    protected commonFormServices: CommonFormServices,
    protected route: ActivatedRoute,
    public router: Router,
    private authService: AuthService,
    protected formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.data = [];
    this.pageType = this.route.snapshot.params['type'] as string;
    this.showAddButton = this.authService.isFormActionEnabled(SPR_NEWS_LIST, ActionsEnum.ACTIONS_CREATE);
    this.route.queryParams.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.selectedTab = !isNaN(Number(params['selectedTab'])) ? Number(params['selectedTab']) : 0;
    });

    this.buildForm();

    this.commonFormServices.translate.get(this.translationsRef + '.public').subscribe((res: string) => {
      this.isPublicOptions.push({ label: res, value: DB_BOOLEAN_TRUE });
      this.commonFormServices.translate.get(this.translationsRef + '.private').subscribe((res: string) => {
        this.isPublicOptions.push({ label: res, value: DB_BOOLEAN_FALSE });
      });
    });

    this.handleTabChange(this.selectedTab);
  }

  buildForm(): void {
    this.searchForm = this.formBuilder.group({
      nw_title: new FormControl(null),
      nw_summary: new FormControl(null),
      nw_publication_date: new FormControl(undefined),
      nw_type: new FormControl(null),
      is_public: new FormControl(null),
    });
  }
  loadData(isSearch: boolean): void {
    this.addFormValues();

    if (isSearch) {
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
      paramName: NewsDbFields.IS_TEMPLATE,
      paramValue: {
        condition: ExtendedSearchCondition.Equals,
        value: this.loadNewsTemplates ? DB_BOOLEAN_TRUE : DB_BOOLEAN_FALSE,
        upperLower: ExtendedSearchUpperLower.CaseInsensitiveLatin,
      },
    });

    if (this.loadPageTemplates) {
      this.extendedParams.push({
        paramName: NewsDbFields.PAGE_TEMPLATE,
        paramValue: {
          condition: ExtendedSearchCondition.Equals,
          value: DB_BOOLEAN_TRUE,
          upperLower: ExtendedSearchUpperLower.CaseInsensitiveLatin,
        },
      });
    }

    this.newsService.getList(this.extendedParams, pagingParams, false).subscribe((results) => {
      this.rowMenuItems = {};

      this.hiddenData = results.data;

      if (this.hiddenData.length <= this.recordPerLoad) {
        this.hideLoadMore = true;
      } else {
        results.data.pop();
      }
      this.data = [...this.data, ...results.data];

      this.totalRecords = results.paging.cnt;

      this.data.forEach((row) => {
        this.rowMenuItems[row.nw_id] = this.getActions(row);
        if (this.loadNewsTemplates) {
          this.addAdditionalAction(row, this.rowMenuItems[row.nw_id]);
        }
      });
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

  getActions(row: NewsBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions
      .filter(
        (action) =>
          action !== ActionsEnum.ACTIONS_READ ||
          !row.availableActions.some((searchedAction) => searchedAction === ActionsEnum.ACTIONS_UPDATE)
      )
      .filter((action) => !(action === ActionsEnum.ACTIONS_DELETE && this.loadPageTemplates))
      .forEach((action) => {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          icon: getActionIcons(action as ActionsEnum),
          command: (): void => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_CREATE) {
              this.navigateToEdit(`${row.nw_id}`);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecord(`${row.nw_id}`);
            }
          },
        });
      });
    return menu;
  }

  navigateToEdit(recordId: string, copy?: boolean): void {
    if (copy) {
      void this.router.navigate([this.baseUrl, RoutingConst.EDIT, recordId], {
        queryParams: { actionType: ActionsEnum.ACTIONS_COPY, selectedTab: this.selectedTab },
      });
    } else {
      void this.router.navigate([this.baseUrl, RoutingConst.EDIT, recordId], {
        queryParams: { selectedTab: this.selectedTab },
      });
    }
  }

  protected deleteRecord(recordId: string): void {
    this.newsService.delete(recordId).subscribe(() => {
      this.loadData(true);
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  addAdditionalAction(application: NewsBrowseRow, menu: MenuItem[]): void {
    menu.push({
      label: this.commonFormServices.translate.instant('pages.newsBrowse.createFromTemplate') as string,
      id: application.nw_id.toString(),
      icon: 'pi pi-fw pi-copy',
      command: (): void => {
        this.navigateToEdit(application.nw_id.toString(), true);
      },
    });
  }

  handleTabChange(e: number): void {
    this.selectedTab = e;
    this.loadNewsTemplates = this.tabPanels[e] === '.newsTemplates';
    this.loadPageTemplates = this.tabPanels[e] === '.pageTemplates';
    this.searchForm.reset();
    this.loadData(true);
    void this.router.navigate([this.baseUrl], { queryParams: { selectedTab: e } });
  }

  navigateToAddNew(): void {
    void this.router.navigate([this.baseUrl, this.routingConst.NEW], {
      queryParams: { selectedTab: this.selectedTab },
    });
  }
}
