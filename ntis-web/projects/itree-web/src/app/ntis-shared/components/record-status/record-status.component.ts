import { Component, Input } from '@angular/core';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { StatusData } from '../../models/record-status';

@Component({
  selector: 'ntis-record-status',
  templateUrl: './record-status.component.html',
  styleUrls: ['./record-status.component.scss'],
})
export class RecordStatusComponent {
  @Input() statusValue: string;
  @Input() colField: string;
  @Input() field: string;
  @Input() text: string;
  @Input() statusData: StatusData[];
  @Input() truncateText: boolean = false;
  @Input() statusType: 'icon' | 'text' = 'icon';

  constructor(public faIconsService: FaIconsService) {}

  setClass(value: string): string {
    return value;
  }
}
