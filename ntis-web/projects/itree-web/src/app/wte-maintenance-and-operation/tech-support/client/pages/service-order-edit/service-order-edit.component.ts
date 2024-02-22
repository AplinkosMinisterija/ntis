import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { TYRIMAI } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import {
  NtisCheckWtfSelectionRequest,
  NtisCheckWtfSelectionResponse,
  NtisOrdersRequest,
  NtisServiceOrderRequest,
} from '@itree-commons/src/lib/model/api/api';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { SharedModule } from '../../../shared/shared.module';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { ResearchOptionsCheckboxListComponent } from '@itree-web/src/app/wte-maintenance-and-operation/research/components/research-options-checkbox-list/research-options-checkbox-list.component';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { CommonFormServices } from '@itree/ngx-s2-commons';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { Subject, takeUntil } from 'rxjs';
import { DialogModule } from 'primeng/dialog';
import { SelectOrAddWtfDialogComponent } from '@itree-web/src/app/ntis-shared/components/select-or-add-wtf-dialog/select-or-add-wtf-dialog.component';

@Component({
  selector: 'app-service-order-edit',
  templateUrl: './service-order-edit.component.html',
  styleUrls: ['./service-order-edit.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ItreeCommonsModule,
    SharedModule,
    NtisSharedModule,
    ResearchOptionsCheckboxListComponent,
    DialogModule,
    SelectOrAddWtfDialogComponent,
  ],
})
export class ServiceOrderEditComponent implements OnInit {
  readonly TYRIMAI = TYRIMAI;
  readonly formTranslationsReference = 'institutionsAdmin.pages.serviceOrderEdit';
  destroy$: Subject<boolean> = new Subject<boolean>();
  selectedResearchTypes: string[] = [];
  data: NtisServiceOrderRequest;
  wtfSelectionData: NtisCheckWtfSelectionResponse;
  isWtfSelected: boolean = undefined;
  params = {} as NtisCheckWtfSelectionRequest;
  orderRequest = {} as NtisOrdersRequest;
  wtfSearchAddress: string;
  addrDialog: boolean = false;

  constructor(
    private techSuppService: NtisTechSupportService,
    private activatedRoute: ActivatedRoute,
    protected clsfService: CommonService,
    private ntisCommonService: NtisCommonService,
    private commonFormServices: CommonFormServices
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.orderRequest.ord_srv_id = params.id as number;
    });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((queryParams) => {
      this.orderRequest.actionType = queryParams?.['actionType'] as string;
      this.orderRequest.ord_wtf_id = queryParams?.['id'] as number;
      this.wtfSearchAddress = queryParams.address as string;
      this.isWtfSelected = true;
      this.orderRequest.ord_cs_id = queryParams?.['csId'] as number;

      if (queryParams.adId || (queryParams.x && queryParams.y)) {
        this.params.adId = queryParams.adId ? Number(queryParams.adId) : null;
        this.params.lksX = queryParams.x ? Number(queryParams.x) : null;
        this.params.lksY = queryParams.y ? Number(queryParams.y) : null;
        this.params.wtfType = queryParams.type as string;
        this.params.wtfDistance = Number(queryParams.distance);

        this.ntisCommonService.checkWtfSelection(this.params).subscribe((res) => {
          this.wtfSelectionData = res;
          this.isWtfSelected = this.wtfSelectionData.selected;
          if (this.wtfSelectionData.wtfs?.length > 1) {
            this.addrDialog = true;
          } else {
            this.orderRequest.ord_wtf_id =
              this.wtfSelectionData.wtfs?.length > 0 ? this.wtfSelectionData.wtfs[0].wtfId : null;
            this.loadOrder();
          }
        });
      } else {
        this.loadOrder();
      }
    });
  }

  loadOrder(): void {
    if (this.isWtfSelected || this.orderRequest.ord_wtf_id) {
      this.techSuppService.getServiceOrderInfo(this.orderRequest).subscribe((result) => {
        this.data = result;
        this.selectedResearchTypes = this.data?.selectedResearchTypes;
      });
    } else {
      this.toAddNewFacility();
    }
  }

  setFacility(wtfId: number): void {
    this.orderRequest.ord_wtf_id = wtfId;
    this.addrDialog = false;
    this.loadOrder();
  }

  toAddNewFacility(): void {
    void this.commonFormServices.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD,
        NtisRoutingConst.BUILDING_DATA_WASTEWATER_FACILITY,
        RoutingConst.EDIT,
        RoutingConst.NEW,
      ],
      {
        queryParams: {
          returnUrl: `/${RoutingConst.INTERNAL}/${NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION}/${NtisRoutingConst.TECH_SUPPORT}/${NtisRoutingConst.SERVICE_ORDER_EDIT}/${this.orderRequest.ord_srv_id}`,
          adId: this.params.adId,
          x: !this.params.adId ? this.params.lksX : undefined,
          y: !this.params.adId ? this.params.lksY : undefined,
          type: this.params.wtfType,
          address: this.wtfSearchAddress,
          distance: this.params.wtfDistance,
        },
      }
    );
  }
}
