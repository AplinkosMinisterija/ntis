import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras, Params } from '@angular/router';
import {
  SLD_DLV_PARTIAL,
  SLD_DLV_USED,
  SLD_DLV_WHOLE,
  SWG_TYP_DOMESTIC,
  SWG_TYP_INDUSTRIAL,
  SWG_TYP_SLUDGE,
} from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import {
  NtisOrderCarSelection,
  NtisSewageOriginFacility,
  NtisUsedSewageFacility,
  NtisWastewaterDeliveriesDAO,
  NtisWastewaterDeliveryEditModel,
  NtisWastewaterTreatmentOrgDAO,
  NtisWastewaterTreatmentOrgDAOGen,
  NtisWaterManagerSelectionModel,
} from '@itree-commons/src/lib/model/api/api';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { takeUntil } from 'rxjs';
import { NtisTechSupportService } from '../../../../shared/services/ntis-tech-support.service';
import { GroupedSelectOption } from '../../../models/browse-pages';
import { CommonModule } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { CalendarModule } from 'primeng/calendar';
import { UsedSewageFacilityComponent } from '../../../components/used-sewage-facility/used-sewage-facility.component';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';

@Component({
  selector: 'app-sewage-delivery-edit',
  templateUrl: './sewage-delivery-edit.component.html',
  styleUrls: ['./sewage-delivery-edit.component.scss'],
  standalone: true,
  imports: [
    CalendarModule,
    CommonModule,
    ItreeCommonsModule,
    NtisSharedModule,
    ReactiveFormsModule,
    UsedSewageFacilityComponent,
  ],
})
export class SewageDeliveryEditComponent extends BaseEditForm<NtisWastewaterDeliveriesDAO> implements OnInit {
  form = new FormGroup({
    wd_id: new FormControl<number>(null),
    wd_ord_id: new FormControl<number>(null),
    wd_delivery_date: new FormControl<Date>(null, Validators.compose([Validators.required])),
    wd_delivered_quantity: new FormControl<number>(
      null,
      Validators.compose([Validators.required, Validators.max(999999999)])
    ),
    wd_sewage_type: new FormControl<string>('', Validators.compose([Validators.required])),
    wd_wto_id: new FormControl<number>(null, Validators.compose([Validators.required])),
    wd_cr_id: new FormControl<number>(null, Validators.compose([Validators.required])),
    wd_description: new FormControl<string>('', Validators.maxLength(400)),
    wd_additional_information_sludge_delivery: new FormControl<string>(''),
    wd_used_sludge_quantity: new FormControl<number>(null),
  });
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.sewageDeliveryEdit';
  isSludgeUsed: boolean = false;
  isSludgePartial: boolean = false;
  swgIsSludge: boolean = false;
  maxDate: Date = new Date();

  originFacilitiesSelection: NtisWaterManagerSelectionModel[] = [];
  portableFacilitiesList: NtisWaterManagerSelectionModel[] = [];

  waterManagerFacilities: GroupedSelectOption[] = [];
  carList: NtisOrderCarSelection[] = [];

  originallySelectedFacilities: NtisWaterManagerSelectionModel[] = [];
  usedFacilitiesList: NtisUsedSewageFacility[] = [];
  deletedUsedFacilitiesList: string[] = [];
  originFacilitiesList: NtisSewageOriginFacility[] = [];
  deletedOriginFacilities: NtisSewageOriginFacility[] = [];
  wastewaterTreatmentOrg: NtisWastewaterTreatmentOrgDAO;

  ordId: number;
  paramActionType: string;
  newlyAddedWtf: string;
  wdIdParent: string;

  minDate: Date = new Date();

  readonly SLD_DLV_WHOLE = SLD_DLV_WHOLE;
  readonly SLD_DLV_PARTIAL = SLD_DLV_PARTIAL;
  readonly SLD_DLV_USED = SLD_DLV_USED;

  readonly SWG_TYP_DOMESTIC = SWG_TYP_DOMESTIC;
  readonly SWG_TYP_INDUSTRIAL = SWG_TYP_INDUSTRIAL;
  readonly SWG_TYP_SLUDGE = SWG_TYP_SLUDGE;

  constructor(
    private techSupport: NtisTechSupportService,
    protected override commonFormServices: CommonFormServices,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(commonFormServices, activatedRoute);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((params: Params) => {
      this.ordId = (params.ordId as number) || null;
      if (this.ordId != null) {
        this.techSupport.getNewSewageOriginFacility(this.ordId.toString()).subscribe((response) => {
          if (response.ord_id != null) {
            this.minDate = new Date(response.ord_removed_sewage_date);
            this.originFacilitiesList.push(response);
            this.form.controls.wd_ord_id.setValue(this.ordId);
            this.form.controls.wd_cr_id.setValue(response.ocw_cr_id);
          }
        });
      }
    });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      this.paramActionType = param['actionType']?.split('?')[0];
      this.newlyAddedWtf = param['actionType']?.split('id=')[1] ?? param['id'];
    });

    this.techSupport.getOriginFacilitiesOrdersList().subscribe((response) => {
      this.originFacilitiesSelection = response;

      this.originFacilitiesSelection = this.originFacilitiesSelection.filter((facility) => {
        return facility.ord_id?.toString() !== this.ordId?.toString();
      });
    });

    this.techSupport.getPortableFacilities().subscribe((response) => {
      this.portableFacilitiesList = response;
    });

    this.techSupport.getCarsList().subscribe((response) => {
      this.carList = response;
    });
    this.techSupport.getAvailableWaterManagerFacilities().subscribe((response) => {
      this.waterManagerFacilities = response.data;
    });
    this.form.controls.wd_sewage_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((response: string) => {
      this.form.controls.wd_additional_information_sludge_delivery.clearValidators();
      this.form.controls.wd_additional_information_sludge_delivery.markAsUntouched();
      this.form.controls.wd_additional_information_sludge_delivery.setValue(null);
      if (response == this.SWG_TYP_SLUDGE) {
        this.swgIsSludge = true;
        this.form.controls.wd_additional_information_sludge_delivery.addValidators([Validators.required]);
      } else {
        this.swgIsSludge = false;
        this.isSludgeUsed = false;
        this.isSludgePartial = false;
        this.form.controls.wd_used_sludge_quantity.clearValidators();
        this.form.controls.wd_used_sludge_quantity.setValue(null);
      }
    });
    this.form.controls.wd_additional_information_sludge_delivery.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe((response: string) => {
        if (response == this.SLD_DLV_USED) {
          this.form.controls.wd_delivered_quantity.clearValidators();
          this.form.controls.wd_delivered_quantity.setValue(null);
          this.form.controls.wd_cr_id.clearValidators();
          this.form.controls.wd_cr_id.setValue(null);
          this.form.controls.wd_wto_id.clearValidators();
          this.form.controls.wd_wto_id.setValue(null);
          this.form.controls.wd_used_sludge_quantity.addValidators([
            Validators.required,
            Validators.min(0.01),
            Validators.max(999999999999),
          ]);
          this.form.controls.wd_used_sludge_quantity.setValue(null);
          this.isSludgeUsed = true;
          this.isSludgePartial = true;
        } else if (response == this.SLD_DLV_PARTIAL) {
          this.isSludgePartial = true;
          this.isSludgeUsed = false;
          this.form.controls.wd_wto_id.addValidators([Validators.required]);
          this.form.controls.wd_cr_id.addValidators([Validators.required]);
          this.form.controls.wd_used_sludge_quantity.addValidators([
            Validators.required,
            Validators.min(0.01),
            Validators.max(999999999999),
          ]);
          this.form.controls.wd_used_sludge_quantity.setValue(null);
        } else {
          this.isSludgeUsed = false;
          this.isSludgePartial = false;
          this.form.controls.wd_delivered_quantity.addValidators([Validators.required, Validators.min(0.01)]);
          this.form.controls.wd_cr_id.addValidators([Validators.required]);
          this.form.controls.wd_wto_id.addValidators([Validators.required]);
          this.form.controls.wd_used_sludge_quantity.clearValidators();
          this.form.controls.wd_used_sludge_quantity.setValue(null);
        }
      });
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.wdIdParent = this.activatedRoute.snapshot.params['id'] as string;
    if (this.newlyAddedWtf) {
      this.form.controls.wd_sewage_type.setValue(SWG_TYP_SLUDGE);
      this.swgIsSludge = true;
      this.isSludgePartial = true;
      this.form.controls.wd_additional_information_sludge_delivery.addValidators([Validators.required]);
    }
  }

  protected doSave(value: NtisWastewaterDeliveriesDAO): void {
    if (this.paramActionType === ActionsEnum.ACTIONS_COPY) {
      value.wd_id = null;
      value.wd_state = null;
      value.wd_wd_id = +this.wdIdParent;
    }
    this.form.markAsTouched();
    if (
      value.wd_sewage_type != this.SWG_TYP_SLUDGE ||
      (value.wd_sewage_type === this.SWG_TYP_SLUDGE &&
        value.wd_additional_information_sludge_delivery === this.SLD_DLV_WHOLE)
    ) {
      if (this.usedFacilitiesList.length > 0) {
        this.usedFacilitiesList.forEach((facil) => {
          this.deletedUsedFacilitiesList.push(facil?.us_id);
        });
      }
      this.usedFacilitiesList = [];
    }
    if (
      this.usedFacilitiesList.length < 1 &&
      (this.form.controls.wd_additional_information_sludge_delivery.value == this.SLD_DLV_USED ||
        this.form.controls.wd_additional_information_sludge_delivery.value == this.SLD_DLV_PARTIAL)
    ) {
      this.commonFormServices.translate.get('common.error.zeroFacilities').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else if (this.originFacilitiesList.length < 1) {
      this.commonFormServices.translate.get('common.error.zeroOrders').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else {
      const facility: NtisWastewaterDeliveryEditModel = {
        deletedOriginFacilities: this.deletedOriginFacilities,
        deletedUsedFacilities: this.deletedUsedFacilitiesList,
        originFacilities: this.originFacilitiesList,
        usedSewageFacilities: this.usedFacilitiesList,
        deliveryInfo: value,
        wastewaterTreatmentOrg: undefined,
      };
      this.techSupport.saveDeliveryRecord(facility).subscribe((res) => {
        this.onSaveSuccess(res.deliveryInfo);
        this.onCancel();
        if (
          (res.deliveryInfo.wd_wto_id && res.wastewaterTreatmentOrg?.wto_auto_accept !== 'Y') ||
          (res.wastewaterTreatmentOrg?.wto_auto_accept === 'Y' &&
            res.wastewaterTreatmentOrg?.wto_no_notifications !== 'Y')
        ) {
          this.techSupport.sendSewageDeliveryNotifications(res.deliveryInfo.wd_id).subscribe();
        }

        if (res.wastewaterTreatmentOrg?.wto_auto_accept === 'Y') {
          this.techSupport.sendNotificationForAutoAccept(res.deliveryInfo.wd_id).subscribe();
        }
      });
    }
  }

  protected doLoad(id: string, actionType?: string): void {
    if (this.paramActionType !== null) {
      actionType = this.paramActionType;
    }
    this.techSupport.getSewageDeliveryRecord(id, actionType).subscribe((res) => {
      if (res.deliveryInfo.wd_ord_id != null) {
        this.ordId = res.deliveryInfo.wd_ord_id;
      }
      if (res.originFacilities) {
        for (const originFacility of res.originFacilities) {
          if (originFacility.ord_removed_sewage_date < this.minDate)
            this.minDate = new Date(originFacility.ord_removed_sewage_date);
        }
        this.originFacilitiesList = res.originFacilities;
        this.originFacilitiesList.forEach((facility) => {
          const originallySelected: NtisWaterManagerSelectionModel = {
            id: Number(facility.ord_id),
            name: facility.name,
            date: facility.ord_removed_sewage_date,
            wtf_id: facility.wtf_id,
            ord_id: Number(facility.ord_id),
          };
          this.originallySelectedFacilities.push(originallySelected);
          if (this.originFacilitiesSelection.find((facil) => facil.ord_id === originallySelected.ord_id)) {
            this.originFacilitiesSelection = this.originFacilitiesSelection.filter(
              (facil) => facil.id !== originallySelected.id
            );
          }
          if (
            this.portableFacilitiesList.find(
              (facil) => facil.wtf_id === originallySelected.wtf_id && originallySelected.ord_id === null
            )
          ) {
            this.portableFacilitiesList = this.portableFacilitiesList.filter(
              (facil) => facil.wtf_id !== originallySelected.wtf_id
            );
          }
        });
        this.usedFacilitiesList = res.usedSewageFacilities;
      }
      this.onLoadSuccess(res.deliveryInfo);
    });
  }

  protected setData(data: NtisWastewaterDeliveriesDAO): void {
    this.data = data;
    this.form.controls.wd_id.setValue(data.wd_id);
    this.form.controls.wd_ord_id.setValue(data.wd_ord_id);
    this.form.controls.wd_delivery_date.setValue(
      data.wd_delivery_date !== null ? new Date(data.wd_delivery_date) : null
    );
    this.form.controls.wd_delivered_quantity.setValue(data.wd_delivered_quantity);
    this.form.controls.wd_sewage_type.setValue(data.wd_sewage_type);
    this.form.controls.wd_wto_id.setValue(data.wd_wto_id);
    this.form.controls.wd_cr_id.setValue(data.wd_cr_id);
    this.form.controls.wd_description.setValue(data.wd_description);
    this.form.controls.wd_additional_information_sludge_delivery.setValue(
      data.wd_additional_information_sludge_delivery
    );
    this.form.controls.wd_used_sludge_quantity.setValue(data.wd_used_sludge_quantity);
    if (this.newlyAddedWtf) {
      this.form.controls.wd_sewage_type.setValue(SWG_TYP_SLUDGE);
      this.swgIsSludge = true;
      this.isSludgePartial = true;
      this.form.controls.wd_additional_information_sludge_delivery.addValidators([Validators.required]);
    }
  }

  protected getData(): NtisWastewaterDeliveriesDAO {
    const result: NtisWastewaterDeliveriesDAO = this.data ? this.data : ({} as NtisWastewaterDeliveriesDAO);
    result.wd_id = this.form.controls.wd_id.value;
    result.wd_ord_id = this.form.controls.wd_ord_id.value;
    result.wd_delivery_date = this.form.controls.wd_delivery_date.value;
    result.wd_delivered_quantity = this.form.controls.wd_delivered_quantity.value;
    result.wd_sewage_type = this.form.controls.wd_sewage_type.value;
    result.wd_wto_id = this.form.controls.wd_wto_id.value;
    result.wd_cr_id = this.form.controls.wd_cr_id.value;
    result.wd_description = this.form.controls.wd_description.value;
    result.wd_additional_information_sludge_delivery =
      this.form.controls.wd_additional_information_sludge_delivery.value;
    result.wd_used_sludge_quantity = this.form.controls.wd_used_sludge_quantity.value;
    this.data = result;
    return result;
  }

  onCancel(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    void this.commonFormServices.router.navigate(
      [
        RoutingConst.INTERNAL,
        NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
        NtisRoutingConst.TECH_SUPPORT,
        NtisRoutingConst.SEWAGE_DELIVERIES,
      ],
      navigationExtras
    );
  }

  getUsedFacilitiesList(facilitiesList: NtisUsedSewageFacility[]): void {
    this.usedFacilitiesList = facilitiesList;
  }

  getDeletedUsedFacilitiesList(list: string[]): void {
    this.deletedUsedFacilitiesList = list;
  }

  getOriginFacilitiesList(facilitiesList: NtisSewageOriginFacility[]): void {
    this.originFacilitiesList = facilitiesList;
    this.minDate = new Date();
    for (const originFacility of facilitiesList) {
      if (originFacility.ord_removed_sewage_date < this.minDate) {
        this.minDate = new Date(originFacility.ord_removed_sewage_date);
      }
    }
  }

  getDeletedOriginFacilitiesList(list: NtisSewageOriginFacility[]): void {
    this.deletedOriginFacilities = list;
    const deletedFacility = this.deletedOriginFacilities[this.deletedOriginFacilities.length - 1];
    if (deletedFacility.ord_id !== null) {
      this.originFacilitiesList = this.originFacilitiesList.filter(
        (facility) => facility.ord_id !== deletedFacility.ord_id
      );
      this.techSupport.getOriginFacilitiesOrdersList().subscribe((response) => {
        this.originFacilitiesSelection = response;
        this.originallySelectedFacilities.forEach((ogFacility) => {
          if (
            !this.originFacilitiesList.find((facility) => Number(facility.ord_id) === ogFacility.ord_id) &&
            !this.originFacilitiesSelection.find((selectionFac) => selectionFac.ord_id === ogFacility.ord_id)
          ) {
            this.originFacilitiesSelection.push(ogFacility);
          }
        });
        this.originFacilitiesList.forEach((originFacility) => {
          if (this.originFacilitiesSelection.find((facility) => facility.ord_id === Number(originFacility.ord_id))) {
            this.originFacilitiesSelection = this.originFacilitiesSelection.filter(
              (faci) => faci.ord_id !== Number(originFacility.ord_id)
            );
          }
        });
      });
    } else if (deletedFacility.wtf_id !== null && deletedFacility.ord_id === null) {
      this.originFacilitiesList = this.originFacilitiesList.filter((facility) => {
        return facility?.ord_id !== null || (facility.wtf_id !== deletedFacility.wtf_id && facility?.ord_id === null);
      });

      this.techSupport.getPortableFacilities().subscribe((response) => {
        this.portableFacilitiesList = response;
        this.originallySelectedFacilities.forEach((ogFacility) => {
          if (
            !this.originFacilitiesList.find((facility) => facility.wtf_id === ogFacility.wtf_id && facility.ord_id) &&
            !this.portableFacilitiesList.find((selectionFac) => selectionFac.wtf_id === ogFacility.wtf_id)
          ) {
            this.portableFacilitiesList.push(ogFacility);
          }
        });
        this.originFacilitiesList.forEach((originFacility) => {
          if (
            this.portableFacilitiesList.find((facility) => {
              return facility.wtf_id === originFacility.wtf_id;
            })
          ) {
            this.portableFacilitiesList = this.portableFacilitiesList.filter((faci) => {
              return (
                faci.wtf_id !== originFacility.wtf_id ||
                (originFacility.ord_id !== null && faci.wtf_id === originFacility.wtf_id)
              );
            });
          }
        });
      });
    }

    this.minDate = new Date();
    for (const originFacility of this.originFacilitiesList) {
      if (originFacility.ord_removed_sewage_date < this.minDate) {
        this.minDate = new Date(originFacility.ord_removed_sewage_date);
      }
    }
  }

  addNewOriginFacility(id: string): void {
    this.techSupport.getNewSewageOriginFacility(id).subscribe((response) => {
      this.originFacilitiesSelection = this.originFacilitiesSelection.filter(
        (facility) => facility.ord_id !== parseInt(id)
      );
      if (!this.form.controls.wd_cr_id.value) {
        this.form.controls.wd_cr_id.setValue(response.ocw_cr_id);
      }
      if (response.ord_removed_sewage_date < this.minDate) {
        this.minDate = new Date(response.ord_removed_sewage_date);
      }
      if (!this.originallySelectedFacilities.find((facility) => facility.ord_id === Number(response.ord_id))) {
        response.df_id = null;
      }
      this.originFacilitiesList.push(response);
    });
  }

  addNewOriginWtfFacility(id: string): void {
    this.techSupport.getPortableWfForSewageDelivery(id).subscribe((response) => {
      this.portableFacilitiesList = this.portableFacilitiesList.filter((facility) => facility.wtf_id !== parseInt(id));
      this.originFacilitiesList.push(response);
    });
  }

  addNewUsedFacility(id: string): void {
    this.techSupport.getNewDeliveryFacility(id).subscribe((response) => {
      if (!this.usedFacilitiesList.find((facility) => facility.wtf_id === id)) {
        this.usedFacilitiesList.push(response);
      }
    });
  }
}
