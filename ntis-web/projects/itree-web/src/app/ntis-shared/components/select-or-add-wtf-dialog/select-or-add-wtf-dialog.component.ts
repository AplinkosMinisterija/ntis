import { CommonModule } from '@angular/common';
import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { NtisCheckWtfSelectionResponse } from '@itree-commons/src/lib/model/api/api';
import { DialogModule } from 'primeng/dialog';
import { NtisSharedModule } from '../../ntis-shared.module';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'ntis-select-or-add-wtf-dialog',
  templateUrl: './select-or-add-wtf-dialog.component.html',
  styleUrls: ['./select-or-add-wtf-dialog.component.scss'],
  standalone: true,
  imports: [CommonModule, NtisSharedModule, ItreeCommonsModule, DialogModule],
})
export class SelectOrAddWtfDialogComponent {
  readonly translationsReference = 'ntisShared.components.selectOrAddWtfDialog';
  @Input() showDialog: boolean = false;
  @Input() wtfSelectionData: NtisCheckWtfSelectionResponse;
  @Output() selectFacility: EventEmitter<number> = new EventEmitter<number>();
  @Output() addNewFacility: EventEmitter<void> = new EventEmitter<void>();

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.showDialog = false;
  }
}
