import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'spr-info-line',
  templateUrl: './info-line.component.html',
  styleUrls: ['./info-line.component.scss'],
})
export class InfoLineComponent implements OnChanges {
  listMode = false;

  @Input() name: string;
  @Input() translateName = false;
  @Input() value: string | string[];
  @Input() redText: boolean;
  @Input() greenText: boolean;
  @Input() sqMeters: boolean = false;
  @Input() textDisplay: 'inline' | 'editor' | 'box' = 'inline';

  editorValue: string;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.value) {
      this.listMode = typeof changes.value.currentValue !== 'string';
      this.editorValue = typeof changes.value.currentValue === 'string' ? changes.value.currentValue : undefined;
    }
  }
}
