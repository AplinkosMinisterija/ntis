import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonFormServices } from '@itree/ngx-s2-commons';

@Component({
  selector: 'app-my-fav-water-managers',
  templateUrl: './my-fav-water-managers.component.html',
  styleUrls: ['./my-fav-water-managers.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, RouterModule],
})
export class MyFavWaterManagersComponent {
  readonly translationsReference = 'institutionsAdmin.components.myFavWaterManagers';
  @Input() favWaterManagers: string[] = [];

  constructor(private commonFormServices: CommonFormServices) {}

  navigateToFavWaterManagers(): void {
    void this.commonFormServices.router.navigate([
      this.commonFormServices.router.url,
      NtisRoutingConst.NTIS_PRIORITY_WF_LIST,
    ]);
  }
}
