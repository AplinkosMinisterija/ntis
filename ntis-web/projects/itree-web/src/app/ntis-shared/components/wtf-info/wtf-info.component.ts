import { Component, Input } from '@angular/core';
import { NtisWtfInfo } from '@itree-commons/src/lib/model/api/api';
import { TypeSewageFacility } from '@itree-web/src/app/ntis-building-data-module/enums/type-sewage-facility';

@Component({
  selector: 'ntis-wtf-info',
  templateUrl: './wtf-info.component.html',
  styleUrls: ['./wtf-info.component.scss'],
})
export class WtfInfoComponent {
  readonly translationsReference = 'ntisShared.components.wtfInfo';
  readonly ntisWtfType = TypeSewageFacility;
  @Input() data: NtisWtfInfo;
  @Input() displayAsCard: boolean = true;
  @Input() bgAndBorder: boolean = false;
  @Input() legend: string = this.translationsReference + '.legend';
}
