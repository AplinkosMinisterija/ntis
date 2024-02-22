import { Component, OnInit } from '@angular/core';
import { CwAddressMappingsBrowseRow } from '../../models/browse-pages';
import {
  BaseBrowseForm,
  CommonFormServices,
  ExtendedSearchParam,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { ActivatedRoute, NavigationExtras, Params } from '@angular/router';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Subject, map, takeUntil } from 'rxjs';
import { TableColumn } from '@itree-commons/src/lib/model/browse-pages';
import { DATA_TYPE_STRING } from '@itree-commons/src/constants/classificators.constants';
import { CommonModule, Location } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { TableModule } from 'primeng/table';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import {
  NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
  NTIS_CW_DATA_LIST,
  NTIS_CW_FOR_ORG_DATA_LIST,
  NTIS_ORD_IMPORT_FOR_ORG_LIST,
  NTIS_ORD_IMPORT_LIST,
  NTIS_ORD_IMPORT_VIEW_PAGE,
} from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { IdKeyValuePair } from '@itree-commons/src/lib/model/api/api';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { NtisTableRowActionsItem } from '@itree-web/src/app/ntis-shared/models/table-row-actions';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { TooltipModule } from 'primeng/tooltip';
import { OrderImportService } from '@itree-web/src/app/ntis-shared/services/order-import.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';

@Component({
  selector: 'app-cw-address-mappings',
  templateUrl: './cw-address-mappings.component.html',
  styleUrls: ['./cw-address-mappings.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, ReactiveFormsModule, TableModule, NtisSharedModule, TooltipModule],
})
export class CwAddressMappingsComponent extends BaseBrowseForm<CwAddressMappingsBrowseRow> implements OnInit {
  readonly translationsReference = 'intsData.centralizedData.pages.cwAddressMappings';
  readonly ExtendedSearchUpperLower = ExtendedSearchUpperLower;
  destroy$: Subject<boolean> = new Subject<boolean>();
  orgId: string;
  municipalities: IdKeyValuePair[] = [];
  senList: IdKeyValuePair[] = [];
  orgsList: IdKeyValuePair[] = [];

  userCanCreate: boolean;
  userOrgCanCreate: boolean;
  userIsVandAdmin: boolean;
  userIsPaslAdmin: boolean;

  cols: TableColumn[];
  visibleColumns: TableColumn[];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private activatedRoute: ActivatedRoute,
    private centralizedService: CentralizedWastewaterDataService,
    private orderImportService: OrderImportService,
    private authService: AuthService,
    private ntisCommonService: NtisCommonService,
    private location: Location
  ) {
    super(commonFormServices);
    this.useExtendedSearchParams = true;
    this.userCanCreate = this.authService.isFormActionEnabled(NTIS_CW_DATA_LIST, ActionsEnum.ACTIONS_CREATE)
      ? this.authService.isFormActionEnabled(NTIS_CW_DATA_LIST, ActionsEnum.ACTIONS_CREATE)
      : this.authService.isFormActionEnabled(NTIS_ORD_IMPORT_LIST, ActionsEnum.ACTIONS_CREATE);

    this.userOrgCanCreate = this.authService.isFormActionEnabled(NTIS_CW_FOR_ORG_DATA_LIST, ActionsEnum.ACTIONS_CREATE)
      ? this.authService.isFormActionEnabled(NTIS_CW_FOR_ORG_DATA_LIST, ActionsEnum.ACTIONS_CREATE)
      : this.authService.isFormActionEnabled(NTIS_ORD_IMPORT_FOR_ORG_LIST, ActionsEnum.ACTIONS_CREATE);
    this.userIsVandAdmin = this.authService.isFormActionEnabled(
      NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
      ActionsEnum.VAND_ADMIN_ACTIONS
    );
    this.userIsPaslAdmin = this.authService.isFormActionEnabled(
      NTIS_ORD_IMPORT_VIEW_PAGE,
      ActionsEnum.PASL_ADMIN_ACTIONS
    );
    this.cols = [
      { field: 'am_address_type', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'am_provided_addres_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'am_municipality_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'sen_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 're_name', export: true, visible: true, type: DATA_TYPE_STRING },
      { field: 'str_name', export: true, visible: true, type: DATA_TYPE_STRING },
      {
        field: 'org_name',
        export: !this.userIsVandAdmin && !this.userIsPaslAdmin,
        visible: !this.userIsVandAdmin && !this.userIsPaslAdmin,
        type: DATA_TYPE_STRING,
      },
    ];
    this.visibleColumns = this.cols.filter((res) => res.visible);
  }

  ngOnInit(): void {
    this.searchForm.addControl('addressTypeCode', new FormControl(''));
    this.searchForm.addControl('providedAddressName', new FormControl(''));
    this.searchForm.addControl('municipalityCode', new FormControl(''));
    this.searchForm.addControl('senName', new FormControl(''));
    if (!this.userIsVandAdmin && this.orgId === null) {
      this.searchForm.addControl('orgName', new FormControl(''));
      this.ntisCommonService.getOrganizations().subscribe((result) => {
        this.orgsList = result.data;
      });
    }
    this.ntisCommonService.getSenList().subscribe((result) => {
      this.senList = result.data;
    });
  }

  protected override load(
    first: number,
    pageSize: number,
    sortField: string,
    order: number,
    params: Map<string, unknown>,
    extendedParams?: ExtendedSearchParam[]
  ): void {
    const pagingParams = this.getPagingParams(first, pageSize, sortField, order, null);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((arParam: Params) => {
      this.orgId = arParam.orgId ? (arParam.orgId as string) : null;
      if (this.orgId || (!this.userIsVandAdmin && !this.userIsPaslAdmin)) {
        this.centralizedService
          .getCwAddressMappings(pagingParams, params, extendedParams, this.orgId)
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
          });
      } else {
        if (this.userIsVandAdmin) {
          this.centralizedService
            .getCwAdrMappingsOrg(pagingParams, params, extendedParams)
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
            });
        } else if (this.userIsPaslAdmin) {
          this.orderImportService
            .getAdrMappingsOrg(pagingParams, params, extendedParams)
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
            });
        }
      }
    });
  }

  getActions(row: CwAddressMappingsBrowseRow): NtisTableRowActionsItem[] {
    const rowActions: NtisTableRowActionsItem[] = [];
    row.availableActions.forEach((action) => {
      if (
        action === ActionsEnum.ACTIONS_UPDATE &&
        ((this.userIsVandAdmin && row.am_org_id !== null) || !this.userIsVandAdmin || !this.userIsPaslAdmin)
      ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faPencil' },
          actionName: 'edit',
          iconTheme: 'default',
          action: () => {
            this.navigateToEdit(row.am_id.toString());
          },
        };
        rowActions.push(action);
      } else if (
        action === ActionsEnum.ACTIONS_DELETE &&
        ((this.userIsVandAdmin && row.am_org_id !== null) || !this.userIsVandAdmin || !this.userIsPaslAdmin)
      ) {
        const action: NtisTableRowActionsItem = {
          icon: { iconStyle: 'solid', iconName: 'faTrashCan' },
          iconTheme: 'default',
          actionName: 'remove',
          action: () => {
            this.deleteRecordWithConfirmation(row.am_id.toString());
          },
        };
        rowActions.push(action);
      }
    });
    return rowActions;
  }

  protected override deleteRecord(recordId: string): void {
    if (this.orgId || (!this.userIsVandAdmin && !this.userIsPaslAdmin)) {
      this.centralizedService.deleteAdrMapping(recordId).subscribe(() => {
        this.reload();
        this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((txt: string) => {
          this.commonFormServices.appMessages.showSuccess('', txt);
        });
      });
    } else if (this.userIsVandAdmin) {
      this.centralizedService.deleteAdrMappingOrg(recordId).subscribe(() => {
        this.reload();
        this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((txt: string) => {
          this.commonFormServices.appMessages.showSuccess('', txt);
        });
      });
    } else if (this.userIsPaslAdmin) {
      this.orderImportService.deleteAdrMappingOrg(recordId).subscribe(() => {
        this.reload();
        this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((txt: string) => {
          this.commonFormServices.appMessages.showSuccess('', txt);
        });
      });
    }
  }

  navigateToEdit(id: string): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, RoutingConst.EDIT, id]);
  }

  navigateToAddNew(): void {
    void this.commonFormServices.router.navigate([this.commonFormServices.router.url, RoutingConst.EDIT], {
      queryParams: { actionType: RoutingConst.NEW },
    });
  }

  onReturn(): void {
    const currentUrl = this.commonFormServices.router.url;
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    if (currentUrl.includes(NtisRoutingConst.WASTEWATER_DATA_REPORT)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.DATA,
          NtisRoutingConst.CENTRALIZED_WASTEWATER,
          NtisRoutingConst.WASTEWATER_DATA_REPORT,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.SERVICE_PROVIDERS)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.INSTITUTIONS,
          NtisRoutingConst.SERVICE_PROVIDERS,
          NtisRoutingConst.ORDERS_IMPORT,
          this.orgId,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.SP_RESEARCH_LIST)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.RESEARCH,
          NtisRoutingConst.SP_RESEARCH_LIST,
          NtisRoutingConst.ORDERS_IMPORT,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.DISPOSAL_ORDERS_LIST)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.TECH_SUPPORT,
          NtisRoutingConst.DISPOSAL_ORDERS_LIST,
          NtisRoutingConst.ORDERS_IMPORT,
        ],
        navigationExtras
      );
    } else if (currentUrl.includes(NtisRoutingConst.TECH_ORDERS_LIST)) {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
          NtisRoutingConst.TECH_SUPPORT,
          NtisRoutingConst.TECH_ORDERS_LIST,
          NtisRoutingConst.ORDERS_IMPORT,
        ],
        navigationExtras
      );
    } else {
      void this.commonFormServices.router.navigate(
        [
          RoutingConst.INTERNAL,
          NtisRoutingConst.DATA,
          NtisRoutingConst.CENTRALIZED_WASTEWATER,
          NtisRoutingConst.WASTEWATER_DATA_LIST,
        ],
        navigationExtras
      );
    }
  }
}
