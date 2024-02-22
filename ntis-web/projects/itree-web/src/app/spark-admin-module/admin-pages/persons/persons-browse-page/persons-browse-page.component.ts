import { ChangeDetectorRef, Component, HostListener, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {
  DATA_TYPE_DATE,
  DATA_TYPE_NUMBER,
  DATA_TYPE_STRING,
} from '@itree-commons/src/constants/classificators.constants';
import { SPR_FORMS_LIST, SPR_PERSON_LIST } from '@itree-commons/src/constants/forms.constants';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { fadeInFields } from '@itree-commons/src/lib/animations/animations';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { BrowseFormSearchResult } from '@itree-commons/src/lib/model/api/api';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
  Spr_paging_ot,
} from '@itree/ngx-s2-commons';

import { Table } from 'primeng/table';
import { Observable, tap } from 'rxjs';
import { AdminService } from '../../../admin-services/admin.service';
import { SprPersonBrowseRow } from '../../../models/browse-pages';
import { MenuItem } from 'primeng/api';
import { getActionIcons } from '@itree-commons/src/lib/utils/get-action-icons';
import { MAX_ROWS_FOR_EXPORT } from '@itree-commons/src/constants/db.const';

@Component({
  selector: 'app-persons-browse-page',
  templateUrl: './persons-browse-page.component.html',
  styleUrls: ['./persons-browse-page.component.scss'],
  animations: [fadeInFields],
})
export class PersonsBrowsePageComponent extends BaseBrowseForm<SprPersonBrowseRow> implements OnInit {
  readonly formCode = SPR_PERSON_LIST;
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  readonly dataTypeString = DATA_TYPE_STRING;
  readonly dataTypeDate = DATA_TYPE_DATE;
  readonly dataTypeNumber = DATA_TYPE_NUMBER;
  personEditLink = RoutingConst.EDIT;
  cols: TableColumn[];
  maximumDate = new Date();
  visibleColumns: TableColumn[];
  pagingParams: Spr_paging_ot;
  exportData: SprPersonBrowseRow[] = [];
  public datax: SprPersonBrowseRow[];
  rowMenuItems: Record<string, MenuItem[]> = {};
  paramsIsEmpty: boolean = true;
  id: string = '';
  userCanCreate: boolean;
  userCanRead: boolean;
  userCanUpdate: boolean;
  userCanDelete: boolean;
  characterLimit: number = 0;
  showTable: boolean = true;

  constructor(
    protected override commonFormServices: CommonFormServices,
    private router: Router,
    private datePipe: S2DatePipe,
    private authService: AuthService,
    private adminService: AdminService,
    public faIconsService: FaIconsService
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_CREATE);
    this.userCanRead = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_READ);
    this.userCanUpdate = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_UPDATE);
    this.userCanDelete = this.authService.isFormActionEnabled(SPR_FORMS_LIST, ActionsEnum.ACTIONS_DELETE);
  }

  ngOnInit(): void {
    this.updateCharacterLimit();

    this.searchForm.addControl('per_id', new FormControl(''));
    this.searchForm.addControl('per_name', new FormControl(''));
    this.searchForm.addControl('per_surname', new FormControl(''));
    this.searchForm.addControl('per_date_of_birth', new FormControl(''));
    this.searchForm.addControl('per_email', new FormControl(''));
    this.searchForm.addControl('rec_create_timestamp', new FormControl(''));
    this.searchForm.addControl('file', new FormControl(''));

    this.setCols();
    this.visibleColumns = this.cols.filter((res) => res.visible);
  }

  loadTableData(
    pagingParams: Spr_paging_ot,
    params: Map<string, unknown>,
    extendedParams: ExtendedSearchParam[]
  ): Observable<BrowseFormSearchResult<SprPersonBrowseRow>> {
    return this.adminService.getPersonList(pagingParams, this.fixDate(params), extendedParams);
  }

  protected load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    /*
    This line of code appears to be setting a boolean value to this.paramsIsEmpty. The value of this.paramsIsEmpty 
    will be true if params is truthy (i.e., not null or undefined) and the length of extendedParams is equal to 0. If 
    either of these conditions is not met, this.paramsIsEmpty will be false.
    */
    this.paramsIsEmpty = params && extendedParams.length === 0;

    this.loadTableData(this.getPagingParams(first, pageSize, sortField, order, null), params, extendedParams)
      .pipe(
        tap((result: BrowseFormSearchResult<SprPersonBrowseRow>) => {
          this.rowMenuItems = {};
          result.data.forEach((row: SprPersonBrowseRow) => {
            this.rowMenuItems[row.per_id] = this.getActions(row);
          });
        })
      )
      .subscribe((response: BrowseFormSearchResult<SprPersonBrowseRow>) => {
        this.data = response.data;
        this.exportData = response.data;
        this.totalRecords = response.paging.cnt;
      });
  }

  loadAllRecords(allRecords: boolean, dataTable: Table): void {
    if (allRecords) {
      this.loadTableData(
        this.getPagingParams(0, MAX_ROWS_FOR_EXPORT, dataTable.sortField, dataTable.sortOrder, null),
        this.fixDate(this.getSearchParamMap(dataTable)),
        this.getExtendedSearchParams(dataTable)
      ).subscribe((response: BrowseFormSearchResult<SprPersonBrowseRow>) => {
        this.exportData = response.data;
      });
    } else {
      this.exportData = this.data;
    }
  }

  fixDate(params: Map<string, unknown>): Map<string, unknown> {
    return params
      .set('p_per_date_of_birth', this.datePipe.transform(params.get('p_per_date_of_birth') as string))
      .set('p_rec_create_timestamp', this.datePipe.transform(params.get('p_rec_create_timestamp') as string));
  }

  protected deleteRecord(id: string): void {
    this.adminService.delPersonRecord(id).subscribe(() => {
      this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translate: string) => {
        this.commonFormServices.appMessages.showSuccess('', translate);
      });
      this.reload();
    });
  }

  async navigateToEdit(id: string): Promise<void> {
    await this.router.navigate([this.router.url, RoutingConst.EDIT, id], {
      queryParams: { actionType: 'edit' },
    });
  }

  async navigateToCopy(id: string): Promise<void> {
    await this.router.navigate([this.router.url, RoutingConst.EDIT, id], {
      queryParams: { actionType: 'copy' },
    });
  }

  getActions(application: SprPersonBrowseRow): MenuItem[] {
    const menu: MenuItem[] = [];
    const per_id: string = application.per_id.toString();
    const actions: ActionsEnum[] = [...application.availableActions];
    actions.forEach((action) => {
      let isMenuItem: boolean = false;
      if (action === ActionsEnum.ACTIONS_UPDATE) {
        isMenuItem = true;
      } else if (action === ActionsEnum.ACTIONS_DELETE) {
        isMenuItem = true;
      } else if (action === ActionsEnum.ACTIONS_READ) {
        if (!this.userCanUpdate) {
          isMenuItem = true;
        }
      } else if (action === ActionsEnum.ACTIONS_COPY) {
        isMenuItem = true;
      }
      if (isMenuItem) {
        menu.push({
          label: this.commonFormServices.translate.instant('common.action.' + action.toLowerCase()) as string,
          id: per_id,
          icon: getActionIcons(action),
          command: () => {
            if (action === ActionsEnum.ACTIONS_UPDATE || action === ActionsEnum.ACTIONS_READ) {
              void this.navigateToEdit(per_id);
            } else if (action === ActionsEnum.ACTIONS_DELETE) {
              this.deleteRecordWithConfirmation(per_id);
            } else if (action === ActionsEnum.ACTIONS_COPY) {
              void this.navigateToCopy(per_id);
            }
          },
        });
      }
    });
    return menu;
  }

  updateCharacterLimit(): boolean {
    const oldCharacterLimit = this.characterLimit;
    const screenWidth = window.innerWidth;

    switch (true) {
      case screenWidth >= 1300:
        this.characterLimit = 70;
        break;
      case screenWidth >= 1180:
        this.characterLimit = 35;
        break;
      case screenWidth >= 1140:
        this.characterLimit = 30;
        break;
      case screenWidth >= 1000:
        this.characterLimit = 15;
        break;
      case screenWidth >= 750:
        this.characterLimit = 68;
        break;
      case screenWidth >= 650:
        this.characterLimit = 35;
        break;
      case screenWidth >= 560:
        this.characterLimit = 25;
        break;
    }

    this.setCols();
    return this.characterLimit !== oldCharacterLimit;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.updateCharacterLimit();
    if (this.updateCharacterLimit()) {
      this.showTable = false;
      setTimeout(() => {
        this.showTable = true;
      });
    }
  }

  setCols(): void {
    this.cols = [
      { field: 'per_id', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'per_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'per_surname', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'per_date_of_birth', export: true, visible: true, type: DATA_TYPE_DATE },
      { field: 'per_email', export: true, visible: true, type: DATA_TYPE_STRING, textLength: this.characterLimit },
      { field: 'rec_create_timestamp', export: true, visible: true, type: DATA_TYPE_DATE },
    ];
  }
}
