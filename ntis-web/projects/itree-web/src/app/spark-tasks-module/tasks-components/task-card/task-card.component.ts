import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { numberToShortStringFormatter, stripHTMLTagsFromString } from 'projects/itree-web/src/utils/formatters';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { SprTaskCard } from '../../models/task-card';

interface SprTaskCardView extends SprTaskCard {
  assigneesView: AssigneeView[];
  progressView: number;
  hour?: string;
  minute?: string;
}

interface AssigneeView {
  textShow: string;
  textHover: string;
}

@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrls: ['./task-card.component.scss'],
})
export class TaskCardComponent implements OnInit {
  @Input() taskData: SprTaskCard;
  @Input() taskDataView: SprTaskCardView;
  @Input() timeline: boolean = false;

  constructor(
    public faIconsService: FaIconsService,
    private translateService: TranslateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.taskDataView = this.taskData as SprTaskCardView;

    // adapt time for rendering:
    if (this.taskDataView.tas_date_to) {
      const dateTo = new Date(this.taskDataView.tas_date_to);
      const _hour = dateTo.getHours();
      const _minute = dateTo.getMinutes();
      this.taskDataView.hour = `${_hour > 9 ? '' : '0'}${_hour}`;
      this.taskDataView.minute = `${_minute > 9 ? '' : '0'}${_minute}`;
    }

    // format and shorten the description:
    if (this.taskDataView?.tas_description) {
      this.taskDataView.tas_description = stripHTMLTagsFromString(this.taskDataView.tas_description);
      const descriptionMaxLength = this.timeline ? 40 : 100;
      if (this.taskDataView.tas_description.length > descriptionMaxLength) {
        this.taskDataView.tas_description = this.taskDataView.tas_description.slice(0, descriptionMaxLength) + '...';
      }
    }

    // adapt progress data for rendering:
    this.taskDataView.progressView = Math.round(this.taskDataView.progress * 100);

    // adapt assignees data for rendering:
    this.taskDataView.assigneesView = this.taskDataView.assignees.slice(0, 4).map((assignee) => ({
      textShow: assignee.name[0] + assignee.surname[0],
      textHover: `${assignee.name} ${assignee.surname}`,
    }));

    if (this.taskDataView.assignees.length > 4) {
      this.translateService.get('pages.sprTasksDashboard.users').subscribe((translatedText: string) => {
        this.taskDataView.assigneesView.push({
          textShow: `+${numberToShortStringFormatter(
            this.taskDataView.assignees.length - this.taskDataView.assigneesView.length
          )}`,
          textHover: `+${
            this.taskDataView.assignees.length - this.taskDataView.assigneesView.length
          } ${translatedText}`,
        });
      });
    }
  }

  navigateToEdit(): void {
    void this.router.navigate([
      '/',
      RoutingConst.INTERNAL,
      RoutingConst.TASKS,
      RoutingConst.SPARK_TASKS_BROWSE,
      RoutingConst.EDIT,
      this.taskDataView.tas_id,
    ]);
  }
}
