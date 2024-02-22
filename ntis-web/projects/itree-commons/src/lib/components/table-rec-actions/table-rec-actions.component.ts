import { Component, Input } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { TieredMenu } from 'primeng/tieredmenu';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-table-rec-actions',
  templateUrl: './table-rec-actions.component.html',
  styleUrls: ['./table-rec-actions.component.scss'],
})
export class TableRecActionsComponent {
  constructor(public faIconsService: FaIconsService) {}

  @Input() actionsList: MenuItem[] = [];

  toggleMenu(menu: TieredMenu, event: Event): void {
    menu.toggle(event);
  }
}
