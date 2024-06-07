import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { Subject, map, takeUntil } from 'rxjs';
import { ResearchService } from '../../services/research.service';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { Table, TableModule } from 'primeng/table';
import { RouterModule } from '@angular/router';
import { NTIS_RESEARCH_NORMS_MANAGEMENT } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { DialogModule } from 'primeng/dialog';
import { ResearchNormsBrowseRow } from '../../models/browse-pages';
import { CalendarModule } from 'primeng/calendar';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TooltipModule } from 'primeng/tooltip';
import { NtisResearchNormEditModel } from '@itree-commons/src/lib/model/api/api';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

interface BrowseSearchData {
  url: string;
  paramsObj: Record<string, unknown>;
  extendedParams: ExtendedSearchParam[];
  searchFormValues: Record<string, unknown>;
  first: number;
  pageSize: number;
  sortField: string;
  order: number;
}

@Component({
  selector: 'app-research-norms-management',
  templateUrl: './research-norms-management.component.html',
  styleUrls: ['./research-norms-management.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    DialogModule,
    FontAwesomeModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    RouterModule,
    TableModule,
    TooltipModule,
  ],
})
export class ResearchNormsManagementComponent extends BaseBrowseForm<ResearchNormsBrowseRow> implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.research.pages.ntisResearchNormsManagement';
  readonly formCode = NTIS_RESEARCH_NORMS_MANAGEMENT;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly RoutingConst = RoutingConst;
  exportData: ResearchNormsBrowseRow[] = [];
  editDialog: boolean = false;
  minDateTo: Date;
  minDateFrom: Date;
  destroy$: Subject<boolean> = new Subject();
  sortClause: string;
  sortOrder: number;

  cols: TableColumn[] = [
    { field: 'rn_research_type', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rn_research_norm', export: true, visible: true, type: DATA_TYPE_NUMBER },
    { field: 'rn_facility_installation_date', export: true, visible: true, type: DATA_TYPE_STRING },
    { field: 'rn_date_from', export: true, visible: true, type: DATA_TYPE_DATE },
  ];

  form = new FormGroup({
    rn_id: new FormControl<number>({ value: null, disabled: true }),
    rn_research_type: new FormControl<string>('', Validators.required),
    research_type_clsf: new FormControl<string>(''),
    rn_research_norm: new FormControl<number>(null, [Validators.required, Validators.max(999999999999)]),
    rn_facility_installation_date: new FormControl<string>({ value: '', disabled: true }),
    installation_date_clsf: new FormControl<string>(''),
    rn_date_from: new FormControl<Date>(null, Validators.required),
    rn_date_to: new FormControl<Date>(null),
  });

  constructor(
    protected override commonFormServices: CommonFormServices,
    private researchService: ResearchService,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.form.controls['rn_date_to'].addValidators([
      SprDateValidators.validateDateTo(this.form.controls['rn_date_from']),
    ]);
    this.form.controls.rn_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value) => {
      this.form.controls.rn_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
      this.minDateTo = value;
    });
  }

  ngOnInit(): void {
    if (this.keepFilters) {
      const searchData = this.getSearchDataFromSession();
      if (searchData) {
        if (typeof searchData.pageSize === 'number') {
          this.showRows = searchData.pageSize;
        }
        this.sortClause = searchData.sortField ?? null;
        this.sortOrder = searchData.order ?? null;
        this.loadSearchDataIntoSearchForm();
      }
    }
    this.searchForm.addControl('rn_research_type', new FormControl(''));
    this.searchForm.addControl('rn_research_norm', new FormControl(''));
    this.searchForm.addControl('rn_facility_installation_date', new FormControl(''));
    this.searchForm.addControl('rn_date_from', new FormControl(''));
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(
      first,
      pageSize,
      this.sortClause ? this.sortClause : sortField,
      this.sortOrder ? this.sortOrder : order,
      null
    );
    this.researchService
      .getResearchNormsList(pagingParams, params, extendedParams)
      .pipe(
        map((result) => {
          result.data.forEach((row) => {
            row.actions = this.getActions(row);
          });
          return result;
        })
      )
      .subscribe((result) => {
        this.data = result.data;
        this.totalRecords = result.paging.cnt;
        this.exportData = result.data;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.researchService
        .getResearchNormsList(
          this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
          this.getSearchParamMap(dataTable),
          this.getExtendedSearchParams(dataTable)
        )
        .subscribe((response) => {
          this.exportData = response.data;
        });
    } else {
      this.exportData = this.data;
    }
  }

  getActions(row: ResearchNormsBrowseRow): NtisTableRowActionsItem[] {
    const itemActions: NtisTableRowActionsItem[] = [];
    const actions: string[] = [...row.availableActions];
    actions.forEach((action) => {
      if (action === ActionsEnum.ACTIONS_UPDATE) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faPencil' },
          iconTheme: 'default',
          actionName: 'update',
          action: (): void => {
            this.editNorm(row);
          },
        });
      } else if (action === ActionsEnum.ACTIONS_READ) {
        itemActions.push({
          icon: { iconStyle: 'solid', iconName: 'faClockRotateLeft' },
          iconTheme: 'default',
          actionName: 'read',
          action: (): void => {
            void this.commonFormServices.router.navigate([
              this.commonFormServices.router.url,
              NtisRoutingConst.RESEARCH_NORMS_HISTORY,
            ]);
          },
        });
      }
    });
    return itemActions;
  }

  editNorm(row: ResearchNormsBrowseRow): void {
    this.form.reset();
    this.form.controls.rn_id.setValue(row.rn_id);
    this.form.controls.rn_research_type.setValue(row.rn_research_type);
    this.form.controls.research_type_clsf.setValue(row.research_type_clsf);
    this.form.controls.rn_research_norm.setValue(row.rn_research_norm);
    this.form.controls.rn_date_from.setValue(row.rn_date_from ? new Date(row.rn_date_from) : null);
    this.form.controls.rn_facility_installation_date.setValue(
      row.rn_facility_installation_date ? row.rn_facility_installation_date : null
    );
    this.form.controls.installation_date_clsf.setValue(row.installation_clsf ? row.installation_clsf : null);
    this.form.controls.rn_date_to.setValue(row.rn_date_to ? new Date(row.rn_date_to) : null);
    this.minDateTo = this.form.controls.rn_date_from.value;
    this.minDateFrom = new Date(row.rn_date_from);
    this.editDialog = true;
  }

  toSaveNorm(): void {
    if (this.form.valid) {
      const normForUpdate: NtisResearchNormEditModel = {
        rn_id: this.form.controls.rn_id.value,
        rn_research_type: this.form.controls.research_type_clsf.value,
        rn_research_norm: this.form.controls.rn_research_norm.value,
        rn_date_from: this.form.controls.rn_date_from.value,
        rn_date_to: this.form.controls.rn_date_to.value,
        rn_facility_installation_date: this.form.controls.installation_date_clsf.value,
      };
      this.researchService.setResearchNorm(normForUpdate).subscribe(() => {
        this.editDialog = false;
        this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
        this.reload();
      });
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  protected deleteRecord(recordId: string): void {
    throw new Error('Method not implemented.');
  }

  protected override saveSearchDataToSession(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const browseSearchData: BrowseSearchData = {
      url: this.commonFormServices.router.url,
      paramsObj: Object.fromEntries(params),
      extendedParams,
      searchFormValues: Object.keys(this.searchForm.controls)
        .filter((controlName) => this.searchForm.controls[controlName]?.value)
        .reduce(
          (valuesObj, controlName) => ({
            ...valuesObj,
            [controlName]: this.searchForm.controls[controlName].value as unknown,
          }),
          {} as Record<string, unknown>
        ),
      first,
      pageSize,
      sortField,
      order,
    };
    sessionStorage.setItem(this.formCode, JSON.stringify(browseSearchData));
  }

  protected override getSearchDataFromSession(): BrowseSearchData | null {
    const searchData = JSON.parse(sessionStorage.getItem(this.formCode)) as BrowseSearchData;
    if (searchData) {
      if (searchData?.url === this.commonFormServices.router.url) {
        return searchData;
      }
    }
    return null;
  }

  protected override removeSearchDataFromSession(): void {
    sessionStorage.removeItem(this.formCode);
  }
}
