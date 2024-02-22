import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';

@Component({
  selector: 'app-contract-sign-preview-return-page',
  templateUrl: './contract-sign-preview-return-page.component.html',
  styleUrls: ['./contract-sign-preview-return-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class ContractSignPreviewReturnPageComponent implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.pages.contractSign';
  divList = new Array(12) as Array<number>;

  ngOnInit(): void {
    this.postMessageToParent();
  }

  postMessageToParent(): void {
    window.parent.postMessage({
      func: 'onIframeClick',
    });
  }
}
