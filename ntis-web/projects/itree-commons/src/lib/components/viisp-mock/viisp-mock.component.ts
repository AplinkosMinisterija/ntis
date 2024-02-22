import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ViispAuthData } from '../../model/api/api';

@Component({
  selector: 'spr-viisp-mock',
  templateUrl: './viisp-mock.component.html',
  styleUrls: ['./viisp-mock.component.scss'],
})
export class ViispMockComponent {
  @Output() initViispMock = new EventEmitter<ViispAuthData>();
  @Input() viispMockDialogVisible = false;
  @Output() viispMockDialogVisibleChange = new EventEmitter<boolean>();

  viispMockForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    personCode: new FormControl('', Validators.required),
    companyName: new FormControl('', null),
    companyCode: new FormControl('', null),
  });

  show(): void {
    this.viispMockForm.reset();
    this.viispMockDialogVisible = true;
    this.viispMockDialogVisibleChange.emit(this.viispMockDialogVisible);
  }

  initViispLoginMock(): void {
    this.viispMockForm.markAllAsTouched();
    if (this.viispMockForm.valid) {
      const viispData: Partial<ViispAuthData> = {};
      // viispData.customData = this.authService.getReturnUrl();
      viispData.personCode = this.viispMockForm.controls.personCode.value?.trim();
      viispData.firstName = this.viispMockForm.controls.firstName.value?.trim();
      viispData.lastName = this.viispMockForm.controls.lastName.value?.trim();
      viispData.companyName = this.viispMockForm.controls.companyName.value?.trim();
      viispData.companyCode = this.viispMockForm.controls.companyCode.value?.trim();
      this.initViispMock.emit(viispData as ViispAuthData);
    }
  }
}
