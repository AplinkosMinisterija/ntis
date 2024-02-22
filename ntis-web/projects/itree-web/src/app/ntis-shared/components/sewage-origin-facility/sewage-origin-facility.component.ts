import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NtisSewageOriginFacility, NtisWaterManagerSelectionModel } from '@itree-commons/src/lib/model/api/api';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';

@Component({
  selector: 'ntis-sewage-origin-facility',
  templateUrl: './sewage-origin-facility.component.html',
  styleUrls: ['./sewage-origin-facility.component.scss'],
})
export class SewageOriginFacilityComponent {
  readonly translationsReference = 'ntisShared.components.sewageOriginFacility';
  readonly ORDER = 'ORDER';
  readonly FACILITY = 'FACILITY';
  sewageOriginDialog: boolean = false;
  chooseFacilities: boolean = false;
  removedFacilities: NtisSewageOriginFacility[] = [];

  @Input() editMode: boolean = false;
  @Input() ordId: string;
  @Input() isServiceProvider: boolean = false;
  @Input() originFacilities: NtisSewageOriginFacility[];
  @Input() selectionFacilitiesList: NtisWaterManagerSelectionModel[] = [];
  @Input() portableFacilitiesList: NtisWaterManagerSelectionModel[] = [];
  @Output() addedOrdFacility = new EventEmitter<string>();
  @Output() addedWtfFacility = new EventEmitter<string>();
  @Output() facilitiesList = new EventEmitter<NtisSewageOriginFacility[]>();
  @Output() deletedFacilities = new EventEmitter<NtisSewageOriginFacility[]>();

  formSelection = new FormGroup({
    ord_id: new FormControl<string>(null),
    wtf_id: new FormControl<string>(null),
  });

  constructor(private router: Router) {}

  deleteOriginFacility(facility: NtisSewageOriginFacility): void {
    if (
      (facility.type === this.ORDER &&
        !this.removedFacilities.find((facil) => {
          facility.ord_id?.toString() === facil.ord_id?.toString() && facil.type === this.ORDER;
        })) ||
      (facility.type === this.FACILITY &&
        !this.removedFacilities.find((facil) => {
          facility.wtf_id?.toString() === facil.wtf_id?.toString() &&
            facil.type === this.FACILITY &&
            facil.ord_id === null;
        }))
    ) {
      this.removedFacilities.push(facility);
      if (facility.type === this.ORDER) {
        this.originFacilities = this.originFacilities.filter((facil) => facil.ord_id !== facility.ord_id);
      } else if (facility.type === this.FACILITY) {
        this.originFacilities = this.originFacilities.filter((facil) => {
          return (
            (facil.wtf_id?.toString() !== facility.wtf_id?.toString() && facil.ord_id === null) || facil.ord_id !== null
          );
        });
      }
      this.deletedFacilities.emit(this.removedFacilities);
      this.facilitiesList.emit(this.originFacilities);
    }
  }

  getNewFacility(ordId: string, wtfId: string): void {
    if (ordId) {
      this.addedOrdFacility.emit(ordId);
      this.formSelection.controls.ord_id.setValue(null);
    }
    if (wtfId) {
      this.addedWtfFacility.emit(wtfId);
      this.formSelection.controls.wtf_id.setValue(null);
    }
    this.sewageOriginDialog = false;
  }

  navigateToOrderPage(id: string): void {
    if (this.isServiceProvider) {
      void this.router.navigate([this.router.url, NtisRoutingConst.SERVICE_ORDER_PAGE, id]);
    }
  }

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.sewageOriginDialog = false;
  }
}
