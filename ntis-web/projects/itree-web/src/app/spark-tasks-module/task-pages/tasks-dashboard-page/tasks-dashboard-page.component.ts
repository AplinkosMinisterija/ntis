import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { debounceTime } from 'rxjs';
import { DropdownItem } from '@itree-commons/src/lib/types/dropdown';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { Spr_paging_ot } from '@itree/ngx-s2-commons';
import { SprTaskCard } from '../../models/task-card';
import { TasksAdminService } from '../../task-services/tasks-admin.service';

interface TaskFilter {
  text: string;
  value: TaskFilterValue;
}

type TaskFilterValue = '' | 'completed' | 'new' | 'previous' | 'todays' | 'upcoming';

@Component({
  selector: 'app-tasks-dashboard-page',
  templateUrl: './tasks-dashboard-page.component.html',
  styleUrls: ['./tasks-dashboard-page.component.scss'],
})
export class TasksDashboardPageComponent implements OnInit, AfterViewInit {
  private FORM_NAME = 'SPR_TASKS_CARDS';

  @ViewChild('loadMoreButton', { read: ElementRef })
  private loadMoreButton: ElementRef;

  taskFilters: TaskFilter[] = [
    { text: 'pages.sprTasksDashboard.cardsTabNew', value: 'new' },
    { text: 'pages.sprTasksDashboard.cardsTabTodays', value: 'todays' },
    { text: 'pages.sprTasksDashboard.cardsTabUpcoming', value: 'upcoming' },
    { text: 'pages.sprTasksDashboard.cardsTabPrevious', value: 'previous' },
    { text: 'pages.sprTasksDashboard.cardsTabNew', value: 'completed' },
  ];

  orderingDropdownItems: DropdownItem<string>[] = [
    { text: 'pages.sprTasksDashboard.orderingDropdownPriority', value: 'tas_priority' },
    { text: 'pages.sprTasksDashboard.orderingDropdownEndDate', value: 'tas_date_to' },
  ];

  createTaskAllowed = false;
  isLoadingCards = false;
  areAllCardsLoaded = false;
  filterParam: TaskFilterValue = 'new';
  orderParam = '1';
  sortParam: 'ASC' | 'DESC' = 'ASC';
  searchParamsMap = new Map<string, string | boolean>([
    ['filterByUser', true],
    ['filter', 'new'],
  ]);
  pagingParams = new Spr_paging_ot(null, 0, 4, `${this.orderParam} ${this.sortParam}`);
  taskCards: SprTaskCard[] = [];

  todayYear: number;
  todayMonth: number;
  todayDay: number;

  constructor(
    public faIconsService: FaIconsService,
    private tasksAdminService: TasksAdminService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const todayDate = new Date();
    this.todayYear = todayDate.getFullYear();
    this.todayMonth = todayDate.getMonth();
    this.todayDay = todayDate.getDate();
    this.createTaskAllowed = this.authService.isFormActionEnabled(this.FORM_NAME, 'CREATE');
    this.loadCards(true);
  }

  loadCards(reset: boolean = false): void {
    if (reset) {
      this.taskCards = [];
      this.areAllCardsLoaded = false;
    }
    if (!this.isLoadingCards && !this.areAllCardsLoaded) {
      this.isLoadingCards = true;

      this.tasksAdminService
        .findTaskCards(
          {
            ...this.pagingParams,
            skip_rows: this.taskCards.length,
            order_clause: `${this.orderParam} ${this.sortParam}`,
          },
          this.searchParamsMap
        )
        .pipe(debounceTime(1000))
        .subscribe((result: BrowseFormSearchResult<SprTaskCard>) => {
          this.taskCards = this.taskCards.concat(result.data);
          this.areAllCardsLoaded = result.paging.cnt === result.paging.page_size;
          this.isLoadingCards = false;
        });
    }
  }

  searchCards(text: string): void {
    if (text.length >= 3) {
      this.searchParamsMap.set('p_tas_name', text);
      this.loadCards(true);
    } else if (text.length === 0) {
      this.searchParamsMap.delete('p_tas_name');
      this.loadCards(true);
    }
  }

  onClickFilterButton(value: TaskFilterValue): void {
    if (this.searchParamsMap.get('filter') !== value) {
      this.searchParamsMap.set('filter', value);
      this.filterParam = value;
      this.loadCards(true);
    }
  }

  onClickSortButton(): void {
    this.sortParam = this.sortParam === 'ASC' ? 'DESC' : 'ASC';
    this.loadCards(true);
  }

  onCreateNewTask(): void {
    void this.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.TASKS,
      RoutingConst.SPARK_TASKS_BROWSE,
      RoutingConst.EDIT,
      RoutingConst.NEW,
    ]);
  }

  onSearch(event: Event): void {
    this.searchCards((event.target as HTMLInputElement).value);
  }

  onSelectOrderingDropdownItem(dropdownItem: DropdownItem<unknown>): void {
    if (this.orderParam !== dropdownItem.value) {
      this.orderParam = dropdownItem.value as string;
      this.loadCards(true);
    }
  }

  ngAfterViewInit(): void {
    const intersectionObserver = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting === true) {
          this.loadCards();
        }
      },
      {
        threshold: 0.5,
      }
    );
    if (this.loadMoreButton) {
      intersectionObserver.observe(this.loadMoreButton.nativeElement as HTMLButtonElement);
    }
  }
}
