import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { DialogModule } from 'primeng/dialog';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';

@Component({
  selector: 'ntis-no-registered-faci-dialog',
  templateUrl: './no-registered-faci-dialog.component.html',
  styleUrls: ['./no-registered-faci-dialog.component.scss'],
  standalone: true,
  imports: [ItreeCommonsModule, DialogModule, CommonModule],
})
export class NoRegisteredFaciDialogComponent {
  readonly translationsReference = 'ntisShared.components.noRegisteredFaciDialog';

  @Input() showDialog: boolean = false;
  @Output() closeDialog = new EventEmitter<void>();

  constructor(private router: Router) {}

  toCreateNewFacility(): void {
    void this.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.BUILDING_DATA,
        NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        RoutingConst.EDIT,
        RoutingConst.NEW,
      ],
      {
        queryParams: {
          returnUrl: this.router.url,
        },
      }
    );
  }
}
