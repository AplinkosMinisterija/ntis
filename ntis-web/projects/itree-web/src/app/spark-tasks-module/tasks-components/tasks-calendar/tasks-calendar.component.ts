import { Component, OnInit } from '@angular/core';
import { Spr_paging_ot } from '@itree/ngx-s2-commons';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { TasksAdminService } from '../../task-services/tasks-admin.service';
import { SprTaskCard } from '../../models/task-card';

interface CalendarItem {
  date: Date;
  day: number;
  isTargetMonth: boolean;
  isCurrentDay?: boolean;
}

@Component({
  selector: 'app-tasks-calendar',
  templateUrl: './tasks-calendar.component.html',
  styleUrls: ['./tasks-calendar.component.scss'],
})
export class TasksCalendarComponent implements OnInit {
  calendarYear: number;
  calendarMonth: number;
  calendarItems: CalendarItem[] = [];
  selectedItem: number = -1;
  taskCards: SprTaskCard[] = [];
  weekDays = Array(7);

  constructor(
    private adminService: TasksAdminService,
    public faIconsService: FaIconsService,
    private datePipe: S2DatePipe
  ) {}

  ngOnInit(): void {
    this.loadCalendarData();
  }

  convertWeekDayToMonSunWeekFormat(day: number): number {
    return day === 0 ? 6 : day - 1;
  }

  loadPrevMonth(): void {
    if (this.calendarYear && (this.calendarMonth || this.calendarMonth === 0)) {
      const nextMonthDate = new Date(this.calendarYear, this.calendarMonth - 1, 1);
      this.loadCalendarData(nextMonthDate.getFullYear(), nextMonthDate.getMonth());
    }
  }
  loadNextMonth(): void {
    if (this.calendarYear && (this.calendarMonth || this.calendarMonth === 0)) {
      const nextMonthDate = new Date(this.calendarYear, this.calendarMonth + 1, 1);
      this.loadCalendarData(nextMonthDate.getFullYear(), nextMonthDate.getMonth());
    }
  }

  loadCalendarData(year?: number, month?: number): void {
    this.calendarItems = [];
    this.selectedItem = -1;
    const currentDate = new Date();
    if (!year || (!month && month !== 0)) {
      year = currentDate.getFullYear();
      month = currentDate.getMonth();
    }
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth();
    const currentDay = currentDate.getDate();
    this.calendarYear = year;
    this.calendarMonth = month;
    const lastDayDate = new Date(year, month + 1, 0);
    const thisMonthLastDay = lastDayDate.getDate();
    for (let thisMonthDay = 1; thisMonthDay <= thisMonthLastDay; thisMonthDay++) {
      const dayDate = new Date(year, month, thisMonthDay);
      const dayWeekDay = this.convertWeekDayToMonSunWeekFormat(dayDate.getDay());
      // add days to beginning of calendar if first day of the month isn't monday:
      if (thisMonthDay === 1 && dayWeekDay > 0) {
        const prevMonthLastDay = new Date(year, month, 0).getDate();
        for (let prevMonthDay = prevMonthLastDay - (dayWeekDay - 1); prevMonthDay <= prevMonthLastDay; prevMonthDay++) {
          const prevMonthDayDate = new Date(year, month - 1, prevMonthDay);
          this.calendarItems.push({ date: prevMonthDayDate, day: prevMonthDayDate.getDate(), isTargetMonth: false });
        }
      }
      this.calendarItems.push({
        date: dayDate,
        day: dayDate.getDate(),
        isTargetMonth: true,
        isCurrentDay: year === currentYear && month === currentMonth && thisMonthDay === currentDay,
      });
      // add days to end of the calendar to make it have 42 days:
      if (thisMonthDay === thisMonthLastDay) {
        const daysToAdd = 42 - this.calendarItems.length;
        for (let nextMonthDay = 1; nextMonthDay <= daysToAdd; nextMonthDay++) {
          const nextMonthDayDate = new Date(year, month + 1, thisMonthDay);
          this.calendarItems.push({ date: nextMonthDayDate, day: nextMonthDay, isTargetMonth: false });
        }
      }
    }
  }

  loadCards(): void {
    const selectedDate = this.calendarItems[this.selectedItem].date;
    const pagingParams = new Spr_paging_ot(null, 0, null, 'tas_date_to ASC');
    const searchParamsMap = new Map<string, string | boolean>([
      ['filterByUser', true],
      ['filterByDateTo', this.datePipe.transform(selectedDate)],
    ]);

    this.adminService
      .findTaskCards({ ...pagingParams, skip_rows: this.taskCards.length }, searchParamsMap)
      .subscribe((result: BrowseFormSearchResult<SprTaskCard>) => {
        this.taskCards = result.data;
      });
  }

  onSelectDay(dayIndex: number): void {
    this.selectedItem = this.selectedItem === dayIndex ? -1 : dayIndex;
    if (this.selectedItem > -1) {
      this.loadCards();
    }
  }
}
