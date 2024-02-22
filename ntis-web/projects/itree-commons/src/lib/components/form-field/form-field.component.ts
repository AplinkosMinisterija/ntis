import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MessageResolver } from '@itree/ngx-s2-commons';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.scss'],
})
export class FormFieldComponent implements OnChanges {
  @Input() labelText: string;
  @Input() labelFor: string;
  @Input() tooltipText: string;
  @Input() inputErrors: Record<string, unknown>;
  @Input() errorDefs: Record<string, string>;
  @Input() mandatory: boolean;
  @Input() isLabelHorizontal: boolean;
  @Input() errorDiv: boolean = true;
  @Input() isLabelCentered: boolean;
  @Input() hidden: boolean = false;
  @Input() infoText: string;

  errorMessage = '';

  constructor(protected translate: TranslateService, public faIconsService: FaIconsService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.inputErrors) {
      const errors = changes.inputErrors.currentValue as Record<string, unknown>;
      this.errorMessage = '';
      if (errors) {
        this.errorMessage = MessageResolver.resolveErrorToString(this.translate, errors, this.errorDefs);
      }
    }
  }
}
