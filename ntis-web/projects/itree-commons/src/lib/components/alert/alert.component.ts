import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { FaIconsService } from '../../services/fa-icons.service';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

export interface AlertTextInfo {
  text: number;
  link: string;
}

@Component({
  selector: 'spr-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
})
export class AlertComponent {
  readonly formTranslationsReference = 'components.alert';
  @Input() texts: AlertTextInfo[] = [];
  @Input() showDialog: boolean = true;
  @Input() showUsersRolesMessage: boolean = false;
  @Input() showWaterManagersWtfsMessage: boolean = false;
  @Output() navigateToViewForm: EventEmitter<void> = new EventEmitter<void>();

  orgUsersLink: string = `/${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_USERS_BROWSE}`;
  facilitiesLink: string = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_WATER_MANAGER_SETTINGS}/${NtisRoutingConst.INST_WATER_MANAGER_FACILITIES}`;

  @HostListener('document:keydown.Escape', ['$event'])
  onKeydownHandler(): void {
    this.showDialog = false;
  }

  constructor(public faIconsService: FaIconsService) {}

  onShowMore(): void {
    this.showDialog = false;
    this.navigateToViewForm.emit();
  }

  identify(index: number, item: AlertTextInfo): number {
    return item.text;
  }

  countUnconfirmedMessages(): number {
    return (
      this.texts.length +
      (this.showUsersRolesMessage === true ? 1 : 0) +
      (this.showWaterManagersWtfsMessage === true ? 1 : 0)
    );
  }
}
