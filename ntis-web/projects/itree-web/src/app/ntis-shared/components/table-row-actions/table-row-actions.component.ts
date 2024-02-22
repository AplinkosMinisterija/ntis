import { Component, Input } from '@angular/core';
import { NtisTableRowActionsItem } from '../../models/table-row-actions';

@Component({
  selector: 'ntis-table-row-actions',
  templateUrl: './table-row-actions.component.html',
  styleUrls: ['./table-row-actions.component.scss'],
})
export class TableRowActionsComponent {
  @Input() actions: NtisTableRowActionsItem[];
}
