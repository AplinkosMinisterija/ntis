import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { MONTAVIMAS, NTIS_SRV_ITEM_TYPE, VALYMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { DialogModule } from 'primeng/dialog';
import { InstitutionsAdminService } from '../../services/institutions-admin.service';

@Component({
  selector: 'app-facilities-installer-information',
  templateUrl: './facilities-installer-information.component.html',
  styleUrls: ['./facilities-installer-information.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, DialogModule],
})
export class FacilitiesInstallerInformationComponent implements OnInit {
  readonly translationsReference = 'institutionsAdmin.components.facilitiesInstaller';
  readonly MONTAVIMAS = MONTAVIMAS;
  @Input() registeredFrom: string;
  @Input() deregisteredFrom: string;
  @Input() deregisteredReason: string;
  @Output() statusChanged = new EventEmitter<void>();
  services: {
    code: string;
    name: string;
    registered: boolean;
  }[] = [];
  dialogVisible: boolean = false;

  constructor(
    private commonService: CommonService,
    private instAdminService: InstitutionsAdminService,
    private commonFormServices: CommonFormServices
  ) {}

  ngOnInit(): void {
    this.commonService.getClsf(NTIS_SRV_ITEM_TYPE).subscribe((result) => {
      this.services = result
        .filter((clsf) => clsf.key === MONTAVIMAS)
        .map((item) => ({
          code: item.key,
          name: item.display,
          registered: this.registeredFrom !== null || false,
        }));
    });
  }

  deregisterInstallation(): void {
    this.instAdminService.deregisterInstallation().subscribe(() => {
      this.dialogVisible = false;
      this.commonFormServices.translate
        .get(this.translationsReference + '.deregisteredSuccessfully')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
          this.statusChanged.emit();
        });
    });
  }
}
