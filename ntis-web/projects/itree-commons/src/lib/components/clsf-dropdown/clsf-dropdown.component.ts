import { Component, EventEmitter, Input, OnInit, Output, SkipSelf } from '@angular/core';
import { ControlContainer } from '@angular/forms';
import { SprListIdKeyValue } from '../../model/api/api';
import { CommonService } from '../../services/common.service';

@Component({
  selector: 'spr-clsf-dropdown',
  templateUrl: './clsf-dropdown.component.html',
  styleUrls: ['./clsf-dropdown.component.scss'],
  viewProviders: [
    {
      provide: ControlContainer,
      useFactory: (container: ControlContainer): ControlContainer => container,
      deps: [[new SkipSelf(), ControlContainer]],
    },
  ],
})
export class ClsfDropdownComponent implements OnInit {
  @Input() clsfCode: string;
  @Input() controlName: string;
  @Input() placeholder: string;
  @Input() emitSprIdValues = false;
  @Input() emitIdValue = false;
  @Input() readonly = false;
  @Input() showClear = true;
  @Input() filter = false;
  @Input() inputId: string;
  @Input() defaultCode: string;
  @Output() dropdownChange = new EventEmitter<unknown>();

  options: SprListIdKeyValue[];

  constructor(protected clsfService: CommonService) {}

  ngOnInit(): void {
    this.clsfService.getClsf(this.clsfCode).subscribe((options) => {
      this.options = options;
    });
  }

  onDropdownChange(event: { originalEvent: Event; value: unknown }): void {
    const result = this.emitSprIdValues ? this.options.find((clsf) => clsf.key === event.value) : event.value;
    this.dropdownChange.emit(result);
  }

  onIdDropdownChange(event: { originalEvent: Event; value: unknown }): void {
    const result = this.emitSprIdValues ? this.options.find((clsf) => clsf.id === event.value) : event.value;
    this.dropdownChange.emit(result);
  }
}
