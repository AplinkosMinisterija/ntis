import { Component, Input } from '@angular/core';

@Component({
  selector: 'spr-table-no-rec-found',
  templateUrl: './table-no-rec-found.component.html',
  styleUrls: ['./table-no-rec-found.component.scss'],
})
export class TableNoRecFoundComponent {
  @Input() messageOnLoad: boolean = false;
}
