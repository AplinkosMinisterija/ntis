import { Component, EventEmitter, Input, Output } from '@angular/core';

export type SwitchSelectedIndex = 0 | 1;

@Component({
  selector: 'spr-switch',
  templateUrl: './switch.component.html',
  styleUrls: ['./switch.component.scss'],
})
export class SwitchComponent {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  @Input() options: any[];
  @Input() optionLabel: string;
  @Input() translateOptions: boolean = true;
  @Input() selectedIndex: SwitchSelectedIndex = 0;
  @Output() selectedIndexChange = new EventEmitter<SwitchSelectedIndex>();

  switch(index: number): void {
    this.selectedIndex = index as SwitchSelectedIndex;
    this.selectedIndexChange.emit(this.selectedIndex);
  }
}
