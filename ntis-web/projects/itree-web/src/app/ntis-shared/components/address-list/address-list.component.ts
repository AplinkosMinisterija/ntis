import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { AddressSearchResponse } from '@itree-commons/src/lib/model/api/api';

export interface AddressList {
  addressId: number;
  fullAddress: string;
}
@Component({
  selector: 'ntis-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.scss'],
})
export class AddressListComponent implements OnChanges {
  @Input() multipleRecords: boolean = true;
  @Input() showRemove: boolean = true;
  @Input() addressList: AddressSearchResponse[];
  @Input() address: AddressSearchResponse;
  @Output() removeItem = new EventEmitter<AddressSearchResponse>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.address && changes.address.currentValue) {
      this.address = changes.address.currentValue as AddressSearchResponse;
    }
    if (changes.addressList && changes.addressList.currentValue) {
      this.addressList = changes.addressList.currentValue as AddressSearchResponse[];
    }
  }

  remove(row: AddressSearchResponse): void {
    this.removeItem.emit(row);
  }
}
