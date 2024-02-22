import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { NtisAdrMappingsDAO } from '@itree-commons/src/lib/model/api/api';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { CommonFormServices, DeprecatedBaseEditForm } from '@itree/ngx-s2-commons';
import { Observable, map, mergeMap, takeUntil } from 'rxjs';
import { CentralizedWastewaterDataService } from '../../services/centralized-wastewater-data.service';
import { ActivatedRoute, NavigationExtras } from '@angular/router';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { NtisCommonService } from '@itree-web/src/app/ntis-shared/services/ntis-common.service';
import { NtisAddressMapType } from '@itree-web/src/app/ntis-shared/enums/classifiers.enums';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';
import { ActionsEnum } from '@itree-commons/src/lib/enums/table-row-actions.enums';
import {
  NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
  NTIS_ORD_IMPORT_VIEW_PAGE,
} from '@itree-web/src/app/ntis-shared/constants/forms.const';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { NtisRoutingConst } from '@itree-web/src/app/ntis-shared/constants/ntis-routing.const';
import { OrderImportService } from '@itree-web/src/app/ntis-shared/services/order-import.service';

@Component({
  selector: 'app-cw-address-mappings-edit',
  templateUrl: './cw-address-mappings-edit.component.html',
  styleUrls: ['./cw-address-mappings-edit.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule],
})
export class CwAddressMappingsEditComponent extends DeprecatedBaseEditForm<NtisAdrMappingsDAO> {
  readonly translationsReference = 'intsData.centralizedData.pages.cwAddressMappingsEdit';
  orgId: string;
  cwfId: string;
  orfId: string;
  isWastewaterAdmin: boolean;
  isPaslAdmin: boolean;
  subscribed: boolean = false;
  returnUrl: string;
  errorCode: string;
  value: string;
  readonly municipalityNameOrf = 'ERR-COLMN-006';
  readonly senNameOrf = 'ERR-COLMN-008';
  readonly reNameOrf = 'ERR-COLMN-010';
  readonly strNameOrf = 'ERR-COLMN-012';
  readonly municipalityNameCwf = 'ERR-COLMN-005';
  readonly senNameCwf = 'ERR-COLMN-007';
  readonly reNameCwf = 'ERR-COLMN-009';
  readonly strNameCwf = 'ERR-COLMN-011';

  editFormValues: EditFormValues = [
    {
      legend: this.translationsReference + '.headerText',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.am_id',
          translateLabel: true,
          formControlName: 'am_id',
          hidden: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.am_address_type',
          translateLabel: true,
          formControlName: 'am_address_type',
          classifierCode: 'NTIS_ADDRESS_MAP_TYPE',
          optionType: 'single',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.translationsReference + '.am_provided_address_name',
          translateLabel: true,
          formControlName: 'am_provided_address_name',
          isRequired: true,
          maxLength: 200,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.am_municipality_code',
          translateLabel: true,
          formControlName: 'am_municipality_code',
          classifierCode: 'NTIS_MUNICIPALITIES',
          optionType: 'single',
          isRequired: true,
          filter: true,
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.am_sen_id',
          translateLabel: true,
          formControlName: 'am_sen_id',
          options: undefined,
          optionLabel: 'value',
          optionValue: 'id',
          optionType: 'single',
          isRequired: false,
          hidden: false,
          filter: true,
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.am_re_id',
          translateLabel: true,
          formControlName: 'am_re_id',
          options: undefined,
          optionLabel: 'value',
          optionValue: 'id',
          optionType: 'single',
          isRequired: false,
          hidden: false,
          filter: true,
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.am_str_id',
          translateLabel: true,
          formControlName: 'am_str_id',
          options: undefined,
          optionLabel: 'value',
          optionValue: 'id',
          optionType: 'single',
          isRequired: false,
          hidden: false,
          filter: true,
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Select,
          label: this.translationsReference + '.am_org_id',
          translateLabel: true,
          formControlName: 'am_org_id',
          options: undefined,
          optionLabel: 'value',
          optionValue: 'id',
          optionType: 'single',
          isRequired: false,
          hidden: true,
        },
      ],
    },
  ];

  constructor(
    protected override formBuilder: FormBuilder,
    protected commonService: CommonFormServices,
    private centralizedService: CentralizedWastewaterDataService,
    private ordersImportService: OrderImportService,
    private ntisCommonService: NtisCommonService,
    private authService: AuthService,
    private location: Location,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(formBuilder, commonService, activatedRoute);
    this.isWastewaterAdmin = this.authService.isFormActionEnabled(
      NTIS_CENTRALIZED_WASTEWATER_DATA_VIEW_PAGE,
      ActionsEnum.VAND_ADMIN_ACTIONS
    );
    this.isPaslAdmin = this.authService.isFormActionEnabled(NTIS_ORD_IMPORT_VIEW_PAGE, ActionsEnum.PASL_ADMIN_ACTIONS);
    this.activatedRoute.params.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      this.orgId = param['orgId'] ?? null;
    });
    this.activatedRoute.queryParams.pipe(takeUntil(this.destroy$)).subscribe((param: Record<string, string>) => {
      this.errorCode = param['errorCode'] ?? null;
      this.value = param['value'] ?? null;
      this.cwfId = param['cwfId'] ?? null;
      this.orfId = param['orfId'] ?? null;
      this.returnUrl = param['returnUrl'] ?? null;
    });
  }

  protected override buildForm(fb: FormBuilder): void {
    this.form = fb.group({
      am_id: new FormControl(null),
      am_address_type: new FormControl('', Validators.required),
      am_provided_address_name: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.maxLength(200)])
      ),
      am_municipality_code: new FormControl({ value: null, disabled: true }),
      am_sen_id: new FormControl({ value: null, disabled: true }),
      am_str_id: new FormControl({ value: null, disabled: true }),
      am_org_id: new FormControl(null),
      am_re_id: new FormControl({ value: null, disabled: true }),
    });
    this.setInitialOptionValues();
    if (!this.isWastewaterAdmin && !this.isPaslAdmin) {
      this.ntisCommonService.getOrganizations().subscribe((result) => {
        if (this.orgId) {
          this.form.controls.am_org_id.setValue(this.orgId);
        } else {
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_org_id', 'options', result.data);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_org_id', 'hidden', false);
        }
      });
    }
    if (!this.subscribed) {
      this.subscribeFormControls();
    }
    this.form.controls.am_address_type.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: string) => {
      this.form.controls.am_municipality_code.setValue(null);
      if (value) {
        this.form.controls.am_municipality_code.enable();
        if (value === NtisAddressMapType.SAVIVALD) {
          this.form.controls.am_municipality_code.addValidators(Validators.required);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_municipality_code', 'isRequired', true);
          this.form.controls.am_sen_id.clearValidators();
          this.form.controls.am_sen_id.disable();
          this.form.controls.am_re_id.clearValidators();
          this.form.controls.am_re_id.disable();
          this.form.controls.am_str_id.clearValidators();
          this.form.controls.am_str_id.disable();
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_sen_id', 'isRequired', false);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_re_id', 'isRequired', false);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'isRequired', false);
        } else if (value === NtisAddressMapType.SENIUN) {
          this.form.controls.am_sen_id.enable();
          this.form.controls.am_sen_id.addValidators(Validators.required);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_sen_id', 'isRequired', true);
          this.form.controls.am_re_id.clearValidators();
          this.form.controls.am_re_id.disable();
          this.form.controls.am_str_id.clearValidators();
          this.form.controls.am_str_id.disable();
          this.form.controls.am_municipality_code.addValidators(Validators.required);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_re_id', 'isRequired', false);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'isRequired', false);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_municipality_code', 'isRequired', true);
        } else if (value === NtisAddressMapType.VIETOV) {
          this.form.controls.am_re_id.enable();
          this.form.controls.am_sen_id.enable();
          this.form.controls.am_re_id.addValidators(Validators.required);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_re_id', 'isRequired', true);
          this.form.controls.am_str_id.clearValidators();
          this.form.controls.am_sen_id.clearValidators();
          this.form.controls.am_str_id.disable();
          this.form.controls.am_municipality_code.addValidators(Validators.required);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_sen_id', 'isRequired', false);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'isRequired', false);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_municipality_code', 'isRequired', true);
        } else if (value === NtisAddressMapType.GATVE) {
          this.form.controls.am_str_id.enable();
          this.form.controls.am_re_id.enable();
          this.form.controls.am_sen_id.enable();
          this.form.controls.am_municipality_code.addValidators(Validators.required);
          this.form.controls.am_str_id.addValidators(Validators.required);
          this.form.controls.am_re_id.addValidators(Validators.required);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_re_id', 'isRequired', true);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_municipality_code', 'isRequired', true);
          EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'isRequired', true);
        }
      } else {
        this.form.controls.am_municipality_code.disable();
        this.form.controls.am_re_id.disable();
        this.form.controls.am_sen_id.disable();
        this.form.controls.am_str_id.disable();
      }
      this.form.controls.am_municipality_code.updateValueAndValidity();
      this.form.controls.am_re_id.updateValueAndValidity();
      this.form.controls.am_sen_id.updateValueAndValidity();
      this.form.controls.am_str_id.updateValueAndValidity();
    });
  }

  protected override doSave(value: NtisAdrMappingsDAO): void | Observable<NtisAdrMappingsDAO> {
    this.clearEmptyValues(value);
    if (
      ((this.form.controls.am_re_id?.value as string) === 'EMPTY' &&
        this.form.controls.am_address_type?.value === NtisAddressMapType.VIETOV) ||
      ((this.form.controls.am_str_id?.value as string) === 'EMPTY' &&
        this.form.controls.am_address_type?.value === NtisAddressMapType.GATVE) ||
      ((this.form.controls.am_sen_id?.value as string) === 'EMPTY' &&
        this.form.controls.am_address_type?.value === NtisAddressMapType.SENIUN)
    ) {
      this.commonFormServices.translate.get('common.error.incorrectAdrMap').subscribe((translation: string) => {
        this.commonFormServices.appMessages.showError('', translation);
      });
    } else {
      if (this.orgId || (!this.isWastewaterAdmin && !this.isPaslAdmin)) {
        if (this.cwfId) {
          this.centralizedService.setAdrMapping(value).subscribe(() => {
            this.centralizedService
              .setFileForErrProcessing(this.cwfId)
              .pipe(
                map(() => {
                  this.centralizedService.revalidateErrors(this.cwfId, this.orgId).subscribe();
                })
              )
              .subscribe(() => {
                this.commonFormServices.appMessages.showSuccess(
                  '',
                  this.commonFormServices.translate.instant('common.message.fileReprocessing') as string
                );
                void this.commonFormServices.router.navigate([
                  RoutingConst.INTERNAL,
                  NtisRoutingConst.DATA,
                  NtisRoutingConst.CENTRALIZED_WASTEWATER,
                  NtisRoutingConst.WASTEWATER_DATA_REPORT,
                  NtisRoutingConst.WASTEWATER_DATA_LIST,
                  this.orgId,
                ]);
              });
          });
        } else if (this.orfId) {
          this.ordersImportService
            .setAdrMapping(value)
            .pipe(
              mergeMap(() => this.ordersImportService.setFileForErrProcessing(this.orfId)),
              map(() => {
                this.commonFormServices.appMessages.showSuccess(
                  '',
                  this.commonFormServices.translate.instant('common.message.fileReprocessing') as string
                );
                void this.commonFormServices.router.navigate([
                  RoutingConst.INTERNAL,
                  NtisRoutingConst.INSTITUTIONS,
                  NtisRoutingConst.SERVICE_PROVIDERS,
                  NtisRoutingConst.ORDERS_IMPORT,
                  this.orgId,
                ]);
              }),
              mergeMap(() => this.ordersImportService.revalidateErrors(this.orfId, this.orgId))
            )
            .subscribe(() => {
              this.commonFormServices.confirmationService.confirm({
                message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
                accept: () => {
                  void this.commonFormServices.router.navigateByUrl(this.returnUrl);
                },
                reject: () => {
                  this.commonFormServices.appMessages.showSuccess(
                    '',
                    this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
                  );
                },
              });
            });
        } else {
          this.centralizedService.setAdrMapping(value).subscribe(() => {
            this.onCancel();
          });
        }
      } else if (this.isWastewaterAdmin && !this.orfId) {
        this.centralizedService.setAdrMappingOrg(value).subscribe(() => {
          if (this.cwfId) {
            this.centralizedService
              .setFileForErrProcessingOrg(this.cwfId)
              .pipe(
                map(() => {
                  this.centralizedService.revalidateErrorsOrg(this.cwfId).subscribe();
                })
              )
              .subscribe(() => {
                this.commonFormServices.appMessages.showSuccess(
                  '',
                  this.commonFormServices.translate.instant('common.message.fileReprocessing') as string
                );
                void this.commonFormServices.router.navigate([
                  RoutingConst.INTERNAL,
                  NtisRoutingConst.DATA,
                  NtisRoutingConst.CENTRALIZED_WASTEWATER,
                  NtisRoutingConst.WASTEWATER_DATA_LIST,
                ]);
              });
          } else {
            this.onCancel();
          }
        });
      } else if (this.isPaslAdmin && !this.cwfId) {
        if (!this.orfId) {
          this.ordersImportService.setAdrMappingOrg(value).subscribe(() => this.location.back());
        } else {
          this.ordersImportService
            .setAdrMappingOrg(value)
            .pipe(
              mergeMap(() => this.ordersImportService.setFileForErrProcessingOrg(this.orfId)),
              map(() => {
                this.commonFormServices.appMessages.showSuccess(
                  '',
                  this.commonFormServices.translate.instant('common.message.fileReprocessing') as string
                );
                void this.commonFormServices.router.navigate([
                  RoutingConst.INTERNAL,
                  NtisRoutingConst.WTE_MAINTENANCE_AND_OPERATION,
                  NtisRoutingConst.TECH_SUPPORT,
                  NtisRoutingConst.DISPOSAL_ORDERS_LIST,
                  NtisRoutingConst.ORDERS_IMPORT,
                ]);
              }),
              mergeMap(() => this.ordersImportService.revalidateErrorsOrg(this.orfId))
            )
            .subscribe(() => {
              this.commonFormServices.confirmationService.confirm({
                message: this.commonFormServices.translate.instant('common.message.ordersImportProcessed') as string,
                accept: () => {
                  void this.commonFormServices.router.navigateByUrl(this.returnUrl);
                },
                reject: () => {
                  this.commonFormServices.appMessages.showSuccess(
                    '',
                    this.commonFormServices.translate.instant('common.message.ordersImportProcessedSc') as string
                  );
                },
              });
            });
        }
      }
    }
  }

  protected override doLoad(id: string, actionType?: string): void {
    if (this.orgId || (!this.isWastewaterAdmin && !this.isPaslAdmin)) {
      this.centralizedService.getCwAdrMapping(id, actionType).subscribe((res) => {
        this.onLoadSuccess(res);
      });
    } else {
      if (this.isWastewaterAdmin && !this.orfId) {
        this.centralizedService.getCwAdrMappingOrg(id, actionType).subscribe((res) => {
          this.onLoadSuccess(res);
        });
      } else if (this.isPaslAdmin && !this.cwfId) {
        this.ordersImportService.getAdrMappingOrg(id, actionType).subscribe((res) => {
          this.onLoadSuccess(res);
        });
      }
    }
  }

  protected override setData(data: NtisAdrMappingsDAO): void {
    this.data = data;
    this.form.controls.am_id.setValue(data.am_id);
    this.form.controls.am_address_type.setValue(data.am_address_type);
    this.form.controls.am_provided_address_name.setValue(data.am_provided_addres_name);
    this.form.controls.am_municipality_code.setValue(data.am_municipality_code);
    this.form.controls.am_sen_id.setValue(data.am_sen_id);
    this.form.controls.am_str_id.setValue(data.am_str_id);
    this.form.controls.am_re_id.setValue(data.am_re_id);
    this.form.controls.am_org_id.setValue(data.am_org_id);
    this.handleFromErrors(this.errorCode);
  }

  protected override getData(): NtisAdrMappingsDAO {
    const result: NtisAdrMappingsDAO = this.data != null ? this.data : ({} as NtisAdrMappingsDAO);
    result.am_id = this.form.controls.am_id.value as NtisAdrMappingsDAO['am_id'];
    result.am_address_type = this.form.controls.am_address_type.value as NtisAdrMappingsDAO['am_address_type'];
    result.am_provided_addres_name = this.form.controls.am_provided_address_name
      .value as NtisAdrMappingsDAO['am_provided_addres_name'];
    result.am_municipality_code = this.form.controls.am_municipality_code
      .value as NtisAdrMappingsDAO['am_municipality_code'];
    result.am_sen_id = this.form.controls.am_sen_id.value as NtisAdrMappingsDAO['am_sen_id'];
    result.am_str_id = this.form.controls.am_str_id.value as NtisAdrMappingsDAO['am_str_id'];
    result.am_re_id = this.form.controls.am_re_id.value as NtisAdrMappingsDAO['am_re_id'];
    result.am_org_id = this.form.controls.am_org_id.value as NtisAdrMappingsDAO['am_org_id'];
    this.data = result;
    return result;
  }

  setInitialOptionValues(): void {
    EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_sen_id', 'options', [
      { value: '(pasirinkite savivaldybę)', id: 'EMPTY' },
    ]);
    EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'options', [
      { value: '(pasirinkite vietovę)', id: 'EMPTY' },
    ]);
    EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_re_id', 'options', [
      { value: '(pasirinkite savivaldybę)', id: 'EMPTY' },
    ]);
  }

  clearEmptyValues(value: NtisAdrMappingsDAO): void {
    if ((this.form.controls.am_re_id?.value as string) === 'EMPTY') {
      value.am_re_id = null;
    }
    if ((this.form.controls.am_str_id?.value as string) === 'EMPTY') {
      value.am_str_id = null;
    }
    if ((this.form.controls.am_sen_id?.value as string) === 'EMPTY') {
      value.am_sen_id = null;
    }
  }

  subscribeFormControls(): void {
    this.form.controls.am_municipality_code.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: string) => {
      this.form.controls.am_re_id.setValue(null);
      this.form.controls.am_sen_id.setValue(null);
      this.form.controls.am_str_id.setValue(null);
      if (value) {
        EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'options', [
          { value: '(pasirinkite vietovę)', id: 'EMPTY' },
        ]);
        this.ntisCommonService.getSenMuniList(value).subscribe((data) => {
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'am_sen_id',
            'options',
            data.data?.length > 0 ? data.data : [{ value: '(seniūnijų nerasta)', id: 'EMPTY' }]
          );
        });
        this.ntisCommonService.getResMuniList(value).subscribe((data) => {
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'am_re_id',
            'options',
            data.data?.length > 0 ? data.data : [{ value: '(vietovių nerasta)', id: 'EMPTY' }]
          );
        });
      }
    });

    this.form.controls.am_sen_id.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: string) => {
      this.form.controls.am_re_id.setValue(null);
      this.form.controls.am_str_id.setValue(null);
      if (value && value !== 'EMPTY') {
        EditFormComponent.setItemPropertyInValues(this.editFormValues, 'am_str_id', 'options', [
          { value: '(pasirinkite vietovę)', id: 'EMPTY' },
        ]);
        this.ntisCommonService.getResSenList(value).subscribe((data) => {
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'am_re_id',
            'options',
            data.data?.length > 0 ? data.data : [{ value: '(vietovių nerasta)', id: 'EMPTY' }]
          );
        });
      } else if ((this.form.controls.am_municipality_code?.value as string) !== null) {
        this.ntisCommonService
          .getResMuniList(this.form.controls.am_municipality_code.value as string)
          .subscribe((data) => {
            EditFormComponent.setItemPropertyInValues(
              this.editFormValues,
              'am_re_id',
              'options',
              data.data?.length > 0 ? data.data : [{ value: '(vietovių nerasta)', id: 'EMPTY' }]
            );
          });
      }
    });

    this.form.controls.am_re_id.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((value: string) => {
      if ((this.form.controls.am_re_id?.value as number) === this.data?.am_re_id) {
        this.form.controls.am_str_id.setValue(this.data.am_str_id);
      } else {
        this.form.controls.am_str_id.setValue(null);
      }
      if (value && value !== 'EMPTY') {
        this.ntisCommonService.getStreetResList(value).subscribe((data) => {
          EditFormComponent.setItemPropertyInValues(
            this.editFormValues,
            'am_str_id',
            'options',
            data.data?.length > 0 ? data.data : [{ value: '(gatvių nerasta)', id: 'EMPTY' }]
          );
        });
      }
    });
    this.subscribed = true;
  }

  handleFromErrors(errorCode: string): void {
    if (this.value) {
      this.form.controls.am_provided_address_name.setValue(this.value);
    }
    switch (errorCode) {
      case this.municipalityNameCwf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.SAVIVALD);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.municipalityNameOrf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.SAVIVALD);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.senNameCwf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.SENIUN);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.senNameOrf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.SENIUN);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.reNameCwf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.VIETOV);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.reNameOrf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.VIETOV);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.strNameCwf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.GATVE);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
      case this.strNameOrf:
        this.form.controls.am_address_type.setValue(NtisAddressMapType.GATVE);
        this.form.controls.am_address_type.updateValueAndValidity();
        break;
    }
  }

  onCancel(): void {
    const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
    if (this.returnUrl) {
      void this.commonFormServices.router.navigateByUrl(this.returnUrl, navigationExtras);
    } else {
      void this.commonFormServices.router.navigateByUrl(
        this.commonFormServices.router.url.split('/edit')[0],
        navigationExtras
      );
    }
  }
}
