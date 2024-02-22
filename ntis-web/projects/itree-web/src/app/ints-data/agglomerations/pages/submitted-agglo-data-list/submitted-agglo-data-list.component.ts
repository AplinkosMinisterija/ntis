import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { TableModule } from 'primeng/table';
import { BaseBrowseForm, CommonFormServices, ExtendedSearchParam } from '@itree/ngx-s2-commons';
import { SubmittedAggloDataListRow } from '../../models/browse-pages';
import { AgglomerationsService } from '../../services/agglomerations.service';
import { NtisSharedModule } from 'projects/itree-web/src/app/ntis-shared/ntis-shared.module';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { NTIS_SUBMITTED_AGGLO_DATA_LIST } from 'projects/itree-web/src/app/ntis-shared/constants/forms.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { SubmitAggloDataComponent } from '../../components/submit-agglo-data/submit-agglo-data.component';
import { AGLO_STATUS_REJECTED } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { Subject, takeUntil } from 'rxjs';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';

@Component({
  selector: 'app-submitted-agglo-data-list',
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    TableModule,
    NtisSharedModule,
    ReactiveFormsModule,
    SubmitAggloDataComponent,
  ],
  templateUrl: './submitted-agglo-data-list.component.html',
  styleUrls: ['./submitted-agglo-data-list.component.scss'],
})
export class SubmittedAggloDataListComponent
  extends BaseBrowseForm<SubmittedAggloDataListRow>
  implements OnInit, OnDestroy
{
  readonly translationsRef = 'intsData.agglomerations.pages.submittedAggloDataList';
  readonly AGLO_STATUS_REJECTED = AGLO_STATUS_REJECTED;
  readonly formCode = NTIS_SUBMITTED_AGGLO_DATA_LIST;
  readonly destroy$ = new Subject<void>();

  columns: (keyof SubmittedAggloDataListRow)[] = ['id', 'municipality', 'status_date', 'status'];
  canCreate = false;
  canReadAll = false;

  showSubmitDialog = false;

  constructor(
    protected override commonFormServices: CommonFormServices,
    private agglomerationsService: AgglomerationsService,
    authService: AuthService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    authService.loadedFormActions$.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.canCreate = authService.isFormActionEnabled(NTIS_SUBMITTED_AGGLO_DATA_LIST, ActionsEnum.ACTIONS_CREATE);
      this.canReadAll = authService.isFormActionEnabled(NTIS_SUBMITTED_AGGLO_DATA_LIST, ActionsEnum.READ_ALL);
      this.searchForm.reset();
      if (
        authService.isFormActionEnabled(NTIS_SUBMITTED_AGGLO_DATA_LIST, ActionsEnum.ACTIONS_READ) &&
        this.dataTableComponent
      ) {
        this.reload();
      }
    });
  }

  ngOnInit(): void {
    this.searchForm.addControl('municipality', new FormControl());
    this.searchForm.addControl('status_date', new FormControl());
    this.searchForm.addControl('status', new FormControl());
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.agglomerationsService.getSubmittedAggloDataList(pagingParams, extendedParams).subscribe((result) => {
      this.data = result.data.map((row) => ({
        ...row,
        actions: [
          {
            icon: { iconStyle: 'fas', iconName: 'faEye' },
            iconTheme: 'default',
            actionName: 'view',
            url: `${RoutingConst.VIEW}/${row.id}`,
          },
        ],
      }));
      this.totalRecords = result.paging.cnt;
    });
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function, @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {}

  handleClickUploadNew(): void {
    if (this.canCreate) {
      this.showSubmitDialog = true;
    }
  }
}
