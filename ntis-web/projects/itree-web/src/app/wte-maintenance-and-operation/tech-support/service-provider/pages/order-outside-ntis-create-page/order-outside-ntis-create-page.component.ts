/* eslint-disable @typescript-eslint/no-unused-vars */
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { NTIS_ORDER_STATUS, SRV_STS_ACTIVE } from '@itree-web/src/app/ntis-shared/constants/classifiers.const';
import { ORDER_RECEIVED_OUTSIDE_NTIS_CREATE } from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { NtisOrderDetails, NtisServiceManagementItem } from '@itree-commons/src/lib/model/api/api';
import { CommonService } from '@itree-commons/src/lib/services/common.service';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { OrderStatus, ServiceItemType } from 'projects/itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { SelectOption } from 'projects/itree-web/src/app/ntis-shared/models/wtf-search';
import { NtisDateValidators } from 'projects/itree-web/src/app/ntis-shared/validators/date-validators';
import { takeUntil } from 'rxjs';
import { NtisTechSupportService } from '../../../shared/services/ntis-tech-support.service';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { CommonModule, Location } from '@angular/common';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { NtisSharedModule } from '@itree-web/src/app/ntis-shared/ntis-shared.module';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { EMAIL_PATTERN, PHONE_PATTERN } from '@itree-commons/src/constants/validation.constants';

@Component({
  selector: 'app-order-outside-ntis-create-page',
  templateUrl: './order-outside-ntis-create-page.component.html',
  styleUrls: ['./order-outside-ntis-create-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FormsModule, ReactiveFormsModule, NtisSharedModule],
})
export class OrderOutsideNtisCreatePageComponent extends DeprecatedBaseEditForm<NtisOrderDetails> implements OnInit {
  readonly translationsReference = 'wteMaintenanceAndOperation.techSupport.serviceProvider.pages.orderOutsideNtis';
  readonly formCode = ORDER_RECEIVED_OUTSIDE_NTIS_CREATE;
  readonly SRV_STS_ACTIVE = SRV_STS_ACTIVE;
  readonly ServiceItemType = ServiceItemType;
  readonly linkForMessage = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.INST_SERVICE_MANAGEMENT}`;
  readonly linkForNoCarsMessage = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.INSTITUTIONS}/${NtisRoutingConst.INST_SERVICE_PROVIDER_SETTINGS}/${NtisRoutingConst.NTIS_CARS_LIST}`;
  carsOptions: SelectOption[] = undefined;
  serviceTypeOptions: SelectOption[] = [];
  services: NtisServiceManagementItem[] = [];
  orderStatusOptions: SelectOption[] = [];
  today: Date = new Date();
  selectedWtf: number;
  // user actions
  isTechSpecialist: boolean;
  isCarSpecialist: boolean;
  isAdmin: boolean;

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.serviceLegend',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.ord_state',
          translateLabel: true,
          formControlName: 'ord_state',
          options: undefined,
          optionLabel: 'option',
          optionValue: 'value',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Custom,
          label: this.translationsReference + '.srv_type',
          translateLabel: true,
          formControlName: 'srv_type',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.ord_created',
          translateLabel: true,
          formControlName: 'ord_created',
          maxDate: this.today,
          isRequired: true,
        },
      ],
    },
    {
      legend: this.translationsReference + '.contactInfo',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.ord_email',
          translateLabel: true,
          formControlName: 'ord_email',
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.ord_phone_number',
          translateLabel: true,
          formControlName: 'ord_phone_number',
          errorDefs: { pattern: 'common.error.phone' },
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.ord_additional_description',
          translateLabel: true,
          formControlName: 'ord_additional_description',
        },
      ],
    },
    {
      legend: this.translationsReference + '.sewageLegend',
      translateLegend: true,
      key: 'sewageBlock',
      hidden: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.ord_removed_sewage_date',
          translateLabel: true,
          formControlName: 'ocw_completed_date',
          isRequired: true,
          maxDate: this.today,
          minDate: undefined,
          errorDefs: {
            dateGreaterOrEqual: this.translationsReference + '.sewageRemovalDateError',
          },
        },
        {
          type: EditFormItemType.Custom,
          label: this.translationsReference + '.ocw_discharged_sludge_amount',
          translateLabel: true,
          formControlName: 'ocw_discharged_sludge_amount',
          isRequired: true,
        },
        {
          type: EditFormItemType.Custom,
          label: this.translationsReference + '.ocw_car',
          translateLabel: true,
          formControlName: 'ocw_cr_id',
          isRequired: true,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.translationsReference + '.ocw_works_description',
          translateLabel: true,
          formControlName: 'ocw_completed_works_description',
        },
      ],
    },
    {
      legend: this.translationsReference + '.techLegend',
      translateLegend: true,
      key: 'techBlock',
      hidden: true,
      items: [
        {
          type: EditFormItemType.Date,
          label: this.translationsReference + '.ocw_completed_date',
          translateLabel: true,
          formControlName: 'ocw_completed_date',
          isRequired: true,
          maxDate: this.today,
          minDate: undefined,
          errorDefs: {
            dateGreaterOrEqual: this.translationsReference + '.completedDateError',
          },
        },
        {
          type: EditFormItemType.TextArea,
          label: this.translationsReference + '.ocw_works_description',
          translateLabel: true,
          formControlName: 'ocw_completed_works_description',
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected commonFormService: CommonFormServices,
    private commonService: CommonService,
    private techSuppService: NtisTechSupportService,
    private authService: AuthService,
    private location: Location,
    public faIconsService: FaIconsService,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonFormService, activatedRoute);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.form.controls.ocw_completed_date.setValue(this.today);
    this.isTechSpecialist = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.TECH_SPECIALIST_ACTIONS);
    this.isCarSpecialist = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.CAR_SPECIALIST_ACTIONS);
    this.isAdmin = this.authService.isFormActionEnabled(this.formCode, ActionsEnum.ADMIN_ACTIONS);
    this.getSelectOptions();
  }

  getSelectOptions(): void {
    this.techSuppService.getOrgServices().subscribe((result) => {
      this.services = result.filter(
        (item) => item.serviceType === ServiceItemType.maintenance || item.serviceType === ServiceItemType.sewageRemoval
      );
      if (this.isCarSpecialist || (this.isCarSpecialist && this.isTechSpecialist)) {
        this.techSuppService.getOrgCarsList().subscribe((result) => {
          this.carsOptions = result.map((car) => ({
            option: `${car.cr_model} (${car.cr_reg_no})`,
            value: car.cr_id,
          }));
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'ocw_cr_id', 'options', this.carsOptions);
          this.serviceTypeOptions = this.services
            .filter((item) =>
              item.serviceType === ServiceItemType.sewageRemoval
                ? item.isConfirmed && this.carsOptions?.length > 0
                : this.isCarSpecialist && this.isTechSpecialist
                ? item.isConfirmed
                : this.isTechSpecialist
            )
            .map((service) => ({
              option: service.serviceName,
              value: service.serviceType,
              id: service.srvId,
            }));
          if (this.serviceTypeOptions.length === 1) {
            this.form.controls.srv_type.setValue(this.serviceTypeOptions[0].value);
          }
        });
      } else {
        this.serviceTypeOptions = this.services
          .filter((item) => item.isConfirmed && item.serviceType === ServiceItemType.maintenance)
          .map((service) => ({
            option: service.serviceName,
            value: service.serviceType,
            id: service.srvId,
          }));
      }
    });
    this.commonService.getClsf(NTIS_ORDER_STATUS).subscribe((result) => {
      this.orderStatusOptions = result
        .filter((item) => item.key === OrderStatus.finished || item.key === OrderStatus.confirmed)
        .map((item) => ({
          option: item.display,
          value: item.key,
        }));
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'ord_state', 'options', this.orderStatusOptions);
    });
  }

  protected buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      ord_state: new FormControl('', Validators.required),
      srv_type: new FormControl('', Validators.required),
      ord_created: new FormControl('', Validators.required),
      ocw_completed_date: new FormControl(''),
      ocw_discharged_sludge_amount: new FormControl(''),
      ocw_cr_id: new FormControl(''),
      ocw_completed_works_description: new FormControl(''),
      ord_wtf_id: new FormControl(null, Validators.required),
      ord_email: new FormControl(
        null,
        Validators.compose([Validators.pattern(new RegExp(EMAIL_PATTERN)), Validators.maxLength(100)])
      ),
      ord_phone_number: new FormControl(
        null,
        Validators.compose([Validators.pattern(new RegExp(PHONE_PATTERN)), Validators.maxLength(12)])
      ),
      ord_additional_description: new FormControl(null),
    });

    this.form.controls['ocw_completed_date'].addValidators([
      NtisDateValidators.dateGreaterOrEqualThan(this.form.controls['ord_created']),
    ]);

    this.form.controls.ord_state.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (newValue === OrderStatus.finished) {
        this.form.controls.ocw_completed_date.addValidators(Validators.required);
        if (this.form.controls.srv_type.value === ServiceItemType.maintenance) {
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'techBlock', this.form, true, true);
          this.form.controls.ocw_completed_date.setValue(this.today);
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'ocw_completed_works_description',
            'label',
            this.translationsReference + '.ocw_works_description'
          );
        } else if (this.form.controls.srv_type.value === ServiceItemType.sewageRemoval) {
          this.form.controls.ocw_discharged_sludge_amount.addValidators(
            Validators.compose([Validators.required, Validators.min(0.01)])
          );
          this.form.controls.ocw_cr_id.addValidators(Validators.required);
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'sewageBlock', this.form, true, true);
          this.form.controls.ocw_completed_date.setValue(this.today);
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'ocw_works_description',
            'label',
            this.translationsReference + '.sludgeDescription'
          );
        }
      } else {
        this.form.controls.ocw_completed_date.setValue(null);
        this.form.controls.ocw_completed_date.clearValidators();
        this.form.controls.ocw_discharged_sludge_amount.clearValidators();
        this.form.controls.ocw_cr_id.clearValidators();
        EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'sewageBlock', this.form, true, false);
        EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'techBlock', this.form, true, false);
      }
    });

    this.form.controls.srv_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      if (this.form.controls.ord_state.value === OrderStatus.finished) {
        this.form.controls.ocw_completed_date.addValidators(Validators.required);
        if (newValue === ServiceItemType.maintenance) {
          this.form.controls.ocw_discharged_sludge_amount.clearValidators();
          this.form.controls.ocw_cr_id.clearValidators();
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'techBlock', this.form, true, true);
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'sewageBlock', this.form, true, false);
          this.form.controls.ocw_completed_date.setValue(this.today);
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'ocw_completed_works_description',
            'label',
            this.translationsReference + '.ocw_works_description'
          );
        } else if (newValue === ServiceItemType.sewageRemoval) {
          this.form.controls.ocw_discharged_sludge_amount.addValidators(
            Validators.compose([Validators.required, Validators.min(0.01)])
          );
          this.form.controls.ocw_cr_id.addValidators(Validators.required);
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'sewageBlock', this.form, true, true);
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'techBlock', this.form, true, false);
          this.form.controls.ocw_completed_date.setValue(this.today);
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'ocw_completed_works_description',
            'label',
            this.translationsReference + '.sludgeDescription'
          );
        } else if (!newValue) {
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'sewageBlock', this.form, true, false);
          EditFormComponent.toggleGroupVisibilityInValues(this.editFormValues, 'techBlock', this.form, true, false);
        }
      } else {
        this.form.controls.ocw_completed_date.clearValidators();
        this.form.controls.ocw_discharged_sludge_amount.clearValidators();
        this.form.controls.ocw_cr_id.clearValidators();
      }
    });

    this.form.controls.ord_created.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue: Date) => {
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'ocw_completed_date', 'minDate', newValue);
      this.form.controls.ocw_completed_date.updateValueAndValidity();
    });
  }

  protected doLoad(id: string, actionType?: string): void {
    throw new Error('Method not implemented.');
  }
  protected setData(data: NtisOrderDetails): void {
    throw new Error('Method not implemented.');
  }

  protected doSave(value: NtisOrderDetails): void {
    if (!value.ord_wtf_id) {
      this.commonFormServices.translate.get('common.error.facilityRequired').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else {
      this.techSuppService.setOrderOutsideNtis(value).subscribe((res) => {
        this.commonFormServices.translate.get('common.message.saveSuccess').subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
        this.onCancel(this.form.controls.srv_type.value as string);
        this.techSuppService.sendOutsideOrderNotifications(res.ord_id).subscribe();
      });
    }
  }

  protected getData(): NtisOrderDetails {
    const result: NtisOrderDetails = this.data != null ? this.data : ({} as NtisOrderDetails);
    result.ord_wtf_id = this.form.controls.ord_wtf_id.value as NtisOrderDetails['ord_wtf_id'];
    result.ord_state = this.form.controls.ord_state.value as NtisOrderDetails['ord_state'];
    result.ord_created = this.form.controls.ord_created.value as NtisOrderDetails['ord_created'];
    result.ord_srv_id = this.serviceTypeOptions.filter(
      (service) => service.value === this.form.controls.srv_type.value
    )[0].id;
    result.ocw_completed_date = this.form.controls.ocw_completed_date.value as NtisOrderDetails['ocw_completed_date'];
    result.ocw_discharged_sludge_amount = this.form.controls.ocw_discharged_sludge_amount
      .value as NtisOrderDetails['ocw_discharged_sludge_amount'];
    result.ocw_cr_id = this.form.controls.ocw_cr_id.value as NtisOrderDetails['ocw_cr_id'];
    result.ocw_completed_works_description = this.form.controls.ocw_completed_works_description
      .value as NtisOrderDetails['ocw_completed_works_description'];
    result.ord_additional_description = this.form.controls.ord_additional_description
      .value as NtisOrderDetails['ocw_completed_works_description'];
    result.ord_email = this.form.controls.ord_email.value as NtisOrderDetails['ord_email'];
    result.ord_phone_number = this.form.controls.ord_phone_number.value as NtisOrderDetails['ord_phone_number'];
    this.data = result;
    return result;
  }

  onCancel(serviceType?: string): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    if (serviceType) {
      if (serviceType === ServiceItemType.maintenance) {
        void this.commonFormServices.router.navigate(
          [
            RoutingConst.INTERNAL,
            NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
            NtisRoutingConst.TECH_SUPPORT,
            NtisRoutingConst.TECH_ORDERS_LIST,
          ],
          navigationExtras
        );
      } else if (serviceType === ServiceItemType.sewageRemoval) {
        void this.commonFormServices.router.navigate(
          [
            RoutingConst.INTERNAL,
            NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
            NtisRoutingConst.TECH_SUPPORT,
            NtisRoutingConst.DISPOSAL_ORDERS_LIST,
          ],
          navigationExtras
        );
      }
    } else {
      void this.commonFormServices.router.navigateByUrl(
        this.commonFormServices.router.url.split(NtisRoutingConst.ORDER_OUTSIDE_NTIS_CREATE)[0],
        navigationExtras
      );
    }
  }

  updateWtfSelection(wtfId: number): void {
    this.form.controls.ord_wtf_id.setValue(wtfId);
  }
}
