import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NtisUsedSewageFacility } from '@itree-commons/src/lib/model/api/api';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { TypeSewageFacility } from '@itree-web/src/app/ntis-building-data-module/enums/type-sewage-facility';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-used-sewage-facility',
  templateUrl: './used-sewage-facility.component.html',
  styleUrls: ['./used-sewage-facility.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, ReactiveFormsModule, DialogModule, NtisSharedModule],
})
export class UsedSewageFacilityComponent {
  sewageUsedDialog: boolean = false;
  removedFacilities: string[] = [];
  readonly ntisWtfType = TypeSewageFacility;

  readonly translationsReference =
    'wteMaintenanceAndOperation.techSupport.serviceProvider.components.usedSewageFacility';

  form = new FormGroup({
    wtf_id: new FormControl<number>(null),
  });

  @Input() editMode: boolean = false;
  @Input() usedFacilities: NtisUsedSewageFacility[];
  @Output() addedFacility = new EventEmitter<string>();
  @Output() facilitiesList = new EventEmitter<NtisUsedSewageFacility[]>();
  @Output() deletedFacilities = new EventEmitter<string[]>();

  deleteDeliveryFacility(wtfId: string, usId: string): void {
    this.removedFacilities.push(usId);
    this.usedFacilities = this.usedFacilities.filter((facility) => facility.wtf_id !== wtfId);
    this.deletedFacilities.emit(this.removedFacilities);
    this.facilitiesList.emit(this.usedFacilities);
  }

  getNewDeliveryFacility(id: number): void {
    this.addedFacility.emit(id?.toString());
    this.sewageUsedDialog = false;
  }

  updateWtfSelection(wtfId: number): void {
    this.form.controls.wtf_id.setValue(wtfId);
    if (wtfId) {
      this.addedFacility.emit(wtfId?.toString());
    }
  }
}
