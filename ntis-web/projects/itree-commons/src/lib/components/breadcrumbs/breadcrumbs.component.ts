import { Component } from '@angular/core';
import { BreadcrumbsService } from '../../services/breadcrumbs.service';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.scss'],
})
export class BreadcrumbsComponent {
  constructor(public breadcrumbsService: BreadcrumbsService, public faIconsService: FaIconsService) {}
}
