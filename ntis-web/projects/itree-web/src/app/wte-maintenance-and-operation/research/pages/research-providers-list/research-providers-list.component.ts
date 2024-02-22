import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { DropdownItem } from '@itree-commons/src/lib/types/dropdown';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NTIS_RESEARCH_PROVIDERS_LIST } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { TableModule } from 'primeng/table';
import { ResearchProvidersListTableComponent } from '../../components/research-providers-list-table/research-providers-data.component';
import { ResearchesListBrowseRow } from '../../models/browse-pages';
import { ResearchService } from '../../services/research.service';

@Component({
  selector: 'app-research-providers-list',
  templateUrl: './research-providers-list.component.html',
  styleUrls: ['./research-providers-list.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    TableModule,
    ReactiveFormsModule,
    ResearchProvidersListTableComponent,
    FormsModule,
  ],
})
export class ResearchProvidersListComponent extends BaseBrowseForm<ResearchesListBrowseRow> {
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.researchProvidersList';
  address: string;
  capacity: string;
  order: number = 1;
  orderingDropdownItems: DropdownItem<number>[] = [];
  allowOrderService: boolean;

  constructor(
    protected override commonFormServices: CommonFormServices,
    private researchService: ResearchService,
    private authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.allowOrderService = this.authService.isFormActionEnabled(
      NTIS_RESEARCH_PROVIDERS_LIST,
      ActionsEnum.ORDER_LAB_SERVICE
    );
    this.commonFormServices.translate
      .get('wteMaintenanceAndOperation.research.pages.researchProvidersList')
      .subscribe((translations: Record<string, string>) => {
        this.orderingDropdownItems = [
          { text: translations['min_price'], value: -1 },
          { text: translations['max_price'], value: 1 },
        ];
      });
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, this.order, null);
    this.researchService.getLaboratoriesList(pagingParams, params, extendedParams).subscribe((response) => {
      this.data = response.data;
      this.totalRecords = response.paging.cnt;
    });
    this.researchService.getSelectedWtfInfo().subscribe((response) => {
      this.address = response.address;
      this.capacity = response.capacity;
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }
}
