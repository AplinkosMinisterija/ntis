import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, TrackByFunction } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DB_BOOLEAN_FALSE, DB_BOOLEAN_TRUE } from '@itree-commons/src/constants/db.const';
import { expandFromTop } from '@itree-commons/src/lib/animations/animations';
import { NtisServiceSearchResult, NtisServiceSearchResultService } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { VEZIMAS } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { ServiceItemType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { NtisRoutingConst } from 'projects/itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-service-search-result-list',
  standalone: true,
  templateUrl: './service-search-result-list.component.html',
  styleUrls: ['./service-search-result-list.component.scss'],
  imports: [CommonModule, ItreeCommonsModule, RouterModule, FontAwesomeModule],
  animations: [expandFromTop],
})
export class ServiceSearchResultListComponent implements OnChanges {
  readonly translationsReference = 'wteMaintenanceAndOperation.contracts.components.serviceSearchResultList';
  readonly NtisRoutingConst = NtisRoutingConst;
  readonly DB_BOOLEAN_FALSE = DB_BOOLEAN_FALSE;
  readonly ServiceItemType = ServiceItemType;

  @Input() results: NtisServiceSearchResult[];
  @Output() clickOrderService = new EventEmitter<number>();
  @Output() clickRequestContract = new EventEmitter<number>();
  contractsAvailable: Record<string, boolean> = {};
  openDetails: Record<string, number | null> = {};
  otherServicesAvailable: boolean = false;
  providerHasCar: boolean = false;

  constructor(public faIconsService: FaIconsService) {}

  resultsTrackBy: TrackByFunction<NtisServiceSearchResult> = (_index, result) => result.orgId;
  servicesTrackBy: TrackByFunction<NtisServiceSearchResultService> = (_index, result) => result.id;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.results) {
      const resultsCurrentVal = changes.results.currentValue as NtisServiceSearchResult[];
      if (resultsCurrentVal) {
        resultsCurrentVal.forEach((result) => {
          this.contractsAvailable[result.orgId] = result.services.some((service) => service.contractAvailable);
          result.services.forEach((service) => {
            this.otherServicesAvailable = service.type !== VEZIMAS ? true : this.otherServicesAvailable;
          });
          this.providerHasCar = result.hasCar === DB_BOOLEAN_TRUE ? true : this.providerHasCar;
        });
      } else {
        this.contractsAvailable = {};
        this.openDetails = {};
      }
    }
  }

  handleToggleDetails(orgId: number, serviceId: number): void {
    this.openDetails[orgId] = this.openDetails[orgId] === serviceId ? null : serviceId;
    if (this.openDetails[orgId] === serviceId) {
      requestAnimationFrame(() => {
        const detailsElement = document.getElementById(`service-details-${orgId}-${serviceId}`);
        detailsElement?.focus();
      });
    }
  }
}
