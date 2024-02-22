import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {
  CommonFormServices,
  DeprecatedBaseEditForm,
  ExtendedSearchCondition,
  ExtendedSearchUpperLower,
} from '@itree/ngx-s2-commons';
import { ConfirmationService } from 'primeng/api';
import { AdminService } from 'projects/itree-web/src/app/spark-admin-module/admin-services/admin.service';
import {
  AdvancedSearchParameterStatement,
  PredefinedFilterRestDataModel,
  SprFormDataFiltersDAO,
} from '../../model/api/api';
import { FaIconsService } from '../../services/fa-icons.service';

@Component({
  selector: 'spr-filter-manager',
  templateUrl: './filter-manager.component.html',
  styleUrls: ['./filter-manager.component.scss'],
})
export class FilterManagerComponent extends DeprecatedBaseEditForm<PredefinedFilterRestDataModel> implements OnInit {
  @Input() formCode: string | null = null;
  @Input() formGroup: FormGroup;
  @Input() predefinedFiltersName: string | null;
  @Output() reloadFilterList: EventEmitter<unknown> = new EventEmitter();

  showModal: boolean = false;
  extendedParams: AdvancedSearchParameterStatement[] = [];
  filterTemplates: SprFormDataFiltersDAO[] = [];

  constructor(
    protected override formBuilder: FormBuilder,
    protected override commonFormServices: CommonFormServices,
    private adminService: AdminService,
    public faIconsService: FaIconsService,
    private confirmationService: ConfirmationService
  ) {
    super(formBuilder, commonFormServices);
  }

  protected buildForm(formBuilder: FormBuilder): void {
    this.form = formBuilder.group({
      formCode: new FormControl({ value: null, disabled: false }),
      predefinedCondition: new FormControl('', Validators.compose([Validators.maxLength(100)])),
      filterName: new FormControl(
        this.predefinedFiltersName,
        Validators.compose([Validators.required, Validators.maxLength(100)])
      ),
      filterDescription: new FormControl(null, Validators.compose([Validators.maxLength(200)])),
      globalFilter: new FormControl({ value: null, disabled: false }),
      extendedParams: new FormControl(''),
    });
  }

  protected doSave(value: PredefinedFilterRestDataModel): void {
    if (value.extendedParams.length > 0) {
      this.adminService.setFormPredefinedData(value).subscribe(() => {
        this.reloadFilterList.emit();
        this.showModal = false;
        this.commonFormServices.translate.get('common.message.deleteSuccess').subscribe((translate: string) => {
          this.commonFormServices.appMessages.showSuccess('', translate);
        });
        this.form.reset();
      });
    } else {
      this.commonFormServices.translate
        .get('components.filterTemplates.filterDataIsEmpty')
        .subscribe((translate: string) => {
          this.commonFormServices.appMessages.showWarning('', translate);
        });
    }
  }

  protected doLoad(): void {
    throw new Error('Method not implemented.');
  }

  protected setData(): void {
    throw new Error('Method not implemented.');
  }

  protected getData(): PredefinedFilterRestDataModel {
    const result: PredefinedFilterRestDataModel = this.data != null ? this.data : ({} as PredefinedFilterRestDataModel);
    result.formCode = this.formCode;
    result.filterName = this.form.controls.filterName.value as typeof result.filterName;
    result.extendedParams = this.extendedParams;
    result.predefinedCondition = this.form.controls.predefinedCondition.value as typeof result.predefinedCondition;
    result.filterDescription = this.form.controls.filterDescription.value as typeof result.filterDescription;
    result.globalFilter = this.form.controls.globalFilter.value as typeof result.globalFilter;
    this.data = result;
    return result;
  }

  onCancel(): void {
    this.showModal = false;
    this.form.reset();
  }

  openModal(): void {
    this.showModal = true;
    this.form.controls.filterName.setValue(this.predefinedFiltersName);
    this.form.controls.filterName.updateValueAndValidity();
    this.setAdvancedParams();
    this.getFormPredefinedData();
  }

  getFormPredefinedData(): void {
    this.adminService.getFormPredefinedData(this.formCode).subscribe((response) => {
      this.filterTemplates = response.data;
    });
  }

  onDelete(id: number): void {
    this.confirmationService.confirm({
      message: this.commonFormServices.translate.instant('components.filterTemplates.deleteFilterConfirm') as string,
      accept: () => {
        this.adminService.deleteFormPredefinedData(id).subscribe(() => {
          this.getFormPredefinedData();
          this.reloadFilterList.emit();
        });
      },
    });
  }

  setAdvancedParams(): void {
    this.extendedParams = [];
    Object.entries(this.formGroup.value as Record<string, unknown>)
      .filter(([, value]) => {
        return value;
      })
      .forEach(([name, value]) => {
        if (value instanceof Object && 'paramName' in value) {
          this.extendedParams.push(value as AdvancedSearchParameterStatement);
        } else {
          this.extendedParams.push({
            paramName: name,
            paramValue: {
              condition: ExtendedSearchCondition.Equals,
              value: value as string,
              upperLower: ExtendedSearchUpperLower.Regular,
            },
          } as AdvancedSearchParameterStatement);
        }
      });
  }
}
