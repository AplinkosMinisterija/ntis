import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'spr-form-field-group',
  templateUrl: './form-field-group.component.html',
  styleUrls: ['./form-field-group.component.scss'],
})
export class FormFieldGroupComponent {
  @Input() text: string = '';
  @Input() translateText: boolean = true;
  @Input() small: boolean = false;
  @Input() className: string = '';
  @Input() showEdit = false;
  @Input() contentBg: boolean = false;
  @Output() clickEdit = new EventEmitter<void>();
}
