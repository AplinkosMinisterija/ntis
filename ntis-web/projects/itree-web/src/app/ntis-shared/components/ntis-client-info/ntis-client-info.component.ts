import { Component, Input } from '@angular/core';

@Component({
  selector: 'ntis-client-info',
  templateUrl: './ntis-client-info.component.html',
  styleUrls: ['./ntis-client-info.component.scss'],
})
export class NtisClientInfoComponent {
  readonly translationsReference = 'ntisShared.components.clientInfo';
  @Input() name: string;
  @Input() email: string;
  @Input() phone: string;
}
