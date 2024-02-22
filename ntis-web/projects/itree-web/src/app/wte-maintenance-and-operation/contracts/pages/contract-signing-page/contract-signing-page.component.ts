import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { DialogModule } from 'primeng/dialog';
import { Subject } from 'rxjs';
import { NtisContractsService } from '../../services/ntis-contracts.service';
import { NtisContractEditModel } from '@itree-commons/src/lib/model/api/api';

@Component({
  selector: 'app-contract-signing-page',
  templateUrl: './contract-signing-page.component.html',
  styleUrls: ['./contract-signing-page.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    DialogModule,
  ],
})
export class ContractSigningPageComponent {
  destroy$: Subject<boolean> = new Subject<boolean>();
  iframeUrl: SafeResourceUrl;
  contractBeforeSign = {} as NtisContractEditModel;
  pingCount: number = 0;

  @HostListener('window:message', ['$event'])
  SampleFunction($event: MessageEvent): void {
    if ($event.origin !== window.location.origin) return;
    this.onIframeClick();
  }

  constructor(
    private commonFormServices: CommonFormServices,
    private sanitizer: DomSanitizer,
    private ntisContractsService: NtisContractsService
  ) {
    const navigation = this.commonFormServices.router.getCurrentNavigation();
    this.iframeUrl = navigation.extras.state
      ? this.sanitizer.bypassSecurityTrustResourceUrl(navigation.extras.state.url as string)
      : null;
    this.contractBeforeSign.cot_id = Number(navigation.extras.state?.cotId);
    this.contractBeforeSign.cot_state = navigation.extras.state?.cotState as string;
  }

  onIframeClick(): void {
    if (this.contractBeforeSign.cot_id && this.contractBeforeSign.cot_state) {
      this.ntisContractsService.loadContractInfo(this.contractBeforeSign.cot_id).subscribe((res) => {
        if (this.contractBeforeSign.cot_state !== res.cot_state) {
          this.ntisContractsService.handleContractSignNotifications(this.contractBeforeSign).subscribe();
          this.returnToContractPage();
        } else {
          if (this.pingCount < 20) {
            this.pingCount++;
            this.onIframeClick();
          } else {
            this.commonFormServices.translate
              .get('wteMaintenanceAndOperation.contracts.pages.contractEdit.signError')
              .subscribe((translation: string) => {
                this.commonFormServices.appMessages.showError('', translation);
              });
            this.returnToContractPage();
          }
        }
      });
    } else {
      this.returnToContractPage();
    }
  }

  returnToContractPage(): void {
    const returnUrlArr = this.commonFormServices.router.url.split('/');
    returnUrlArr.pop();
    const returnUrl = returnUrlArr.join('/');
    void this.commonFormServices.router.navigate([returnUrl]);
  }
}
