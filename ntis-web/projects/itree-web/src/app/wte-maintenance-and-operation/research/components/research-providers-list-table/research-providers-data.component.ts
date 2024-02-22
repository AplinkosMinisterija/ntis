import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { TableModule } from 'primeng/table';
import { ResearchesListBrowseRow } from '../../models/browse-pages';

@Component({
  selector: 'app-research-providers-data',
  templateUrl: './research-providers-data.component.html',
  styleUrls: ['./research-providers-data.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, NtisSharedModule, TableModule, ReactiveFormsModule],
})
export class ResearchProvidersListTableComponent {
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.researchProvidersList';
  readonly RoutingConst = RoutingConst;
  showText: boolean = false;
  @Input() researchProvider: ResearchesListBrowseRow;
  @Input() allowOrderService: boolean;

  constructor(private router: Router) {}

  toggleDescription(): void {
    this.showText = !this.showText;
  }

  navigateToOrderPage(): void {
    //TODO naviguoti į Formą Tyrimo užsakymas ir perduoti formai serviso id -> this.items.srv_id
    return;
  }
}
