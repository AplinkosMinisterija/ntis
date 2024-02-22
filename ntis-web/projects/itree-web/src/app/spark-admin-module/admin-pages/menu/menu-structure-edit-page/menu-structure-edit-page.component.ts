import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { NtisMstAdditionalText, SprMenuStructuresDAO } from '@itree-commons/src/lib/model/api/api';
import { S2DatePipe } from '@itree-commons/src/lib/pipes/common.date.pipe';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { AdminService } from '../../../admin-services/admin.service';
import { IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { SprDateValidators } from '@itree-commons/src/lib/validators/date-validators';
import { EditFormComponent } from '@itree-commons/src/lib/components/edit-form/edit-form.component';
import { takeUntil } from 'rxjs';
import { AuthService } from '@itree-commons/src/lib/services/auth.service';

interface FormListItem {
  id: number;
  label: string;
}

interface IconListItem {
  icon: IconDefinition;
  name: string;
}

@Component({
  selector: 'app-menu-structure-edit-page',
  templateUrl: './menu-structure-edit-page.component.html',
  styleUrls: ['./menu-structure-edit-page.component.scss'],
})
export class MenuStructureEditPageComponent extends BaseEditForm<SprMenuStructuresDAO> implements OnInit {
  iconList: IconListItem[];
  formList: FormListItem[];
  additionalText: NtisMstAdditionalText;

  form = new FormGroup({
    mst_title: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
    mst_site: new FormControl('', Validators.compose([Validators.maxLength(50), Validators.required])),
    mst_lang: new FormControl('', Validators.required),
    mst_code: new FormControl('', Validators.required),
    icon: new FormControl(null),
    mst_uri: new FormControl('', Validators.compose([Validators.maxLength(200), Validators.required])),
    mst_is_public: new FormControl<string>(undefined, Validators.compose([Validators.required])),
    mst_frm_id: new FormControl(null),
    isCategory: new FormControl(false),
    mst_date_from: new FormControl<Date>(undefined, Validators.required),
    mst_date_to: new FormControl<Date>(undefined),
    mst_tooltip: new FormControl<string>(null),
  });

  readonly formTranslationsReference = 'pages.sprMenuStructure';
  readonly formEditTranslationsReference = 'pages.sprMenuStructureEdit';
  editFormValues: EditFormValues = [
    {
      legend: this.formEditTranslationsReference + '.menuStructureDetails',
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.mst_title',
          translateLabel: true,
          formControlName: 'mst_title',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.mst_tooltip',
          translateLabel: true,
          formControlName: 'mst_tooltip',
          isRequired: false,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.mst_site',
          translateLabel: true,
          formControlName: 'mst_site',
          optionType: 'single',
          classifierCode: 'SPR_SITE_TYPE',
          isRequired: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.mst_lang',
          translateLabel: true,
          formControlName: 'mst_lang',
          optionType: 'single',
          classifierCode: 'SPR_LANGUAGE',
          isRequired: true,
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.mst_code',
          translateLabel: true,
          formControlName: 'mst_code',
          isRequired: true,
        },
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.mst_icon',
          translateLabel: true,
          formControlName: 'icon',
        },
        {
          type: EditFormItemType.Select,
          label: this.formTranslationsReference + '.mst_is_public',
          translateLabel: true,
          formControlName: 'mst_is_public',
          optionType: 'single',
          classifierCode: 'SPR_IS_PUBLIC_TYPE',
          isRequired: true,
        },
        {
          type: EditFormItemType.Select,
          label: this.formEditTranslationsReference + '.menuForm',
          translateLabel: true,
          formControlName: 'mst_frm_id',
          options: undefined,
          optionValue: 'id',
          optionLabel: 'label',
          optionType: 'single',
          filter: true,
          startFilteringLength: 3,
        },
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.category',
          translateLabel: true,
          formControlName: 'isCategory',
        },
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.mst_uri',
          translateLabel: true,
          formControlName: 'mst_uri',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.mst_date_from',
          translateLabel: true,
          formControlName: 'mst_date_from',
          isRequired: true,
        },
        {
          type: EditFormItemType.Date,
          label: this.formTranslationsReference + '.mst_date_to',
          translateLabel: true,
          formControlName: 'mst_date_to',
        },
      ],
    },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    public faIconsService: FaIconsService,
    private adminService: AdminService,
    private authService: AuthService,
    protected datePipe: S2DatePipe,
    protected override activatedRoute?: ActivatedRoute
  ) {
    super(commonFormServices, activatedRoute);

    this.iconList = Object.keys(this.faIconsService.solid).map((iconName) => ({
      icon: this.faIconsService.solid[iconName],
      name: iconName,
    }));
  }

  override ngOnInit(): void {
    this.loadformData();
    this.form.controls['mst_date_from'].addValidators([
      SprDateValidators.validateDateFrom(this.form.controls['mst_date_to']),
    ]);
    this.form.controls['mst_date_to'].addValidators([
      SprDateValidators.validateDateTo(this.form.controls['mst_date_from']),
    ]);
    this.form.controls.mst_date_from.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.mst_date_to.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.mst_date_to.valueChanges.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.form.controls.mst_date_from.updateValueAndValidity({ onlySelf: true, emitEvent: false });
    });
    this.form.controls.isCategory.valueChanges.pipe(takeUntil(this.destroy$)).subscribe((newValue) => {
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'mst_uri', !newValue);
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'mst_uri', 'isRequired', !newValue);
      if (!newValue) {
        this.form.controls.mst_uri.addValidators(Validators.required);
      } else {
        this.form.controls.mst_uri.clearValidators();
      }
      this.form.controls.mst_uri.updateValueAndValidity();
      EditFormComponent.toggleItemVisibilityInValues(this.editFormValues, 'mst_frm_id', !newValue);
    });
    super.ngOnInit();
  }

  protected doLoad(id?: string, actionType?: string): void {
    this.adminService.getMenuStructureRecord(id, actionType).subscribe((paramRec) => {
      this.adminService.getMstAdditionalText(id).subscribe((result) => {
        this.additionalText = result?.data[0];
        this.onLoadSuccess(paramRec);
      });
    });
  }

  private loadformData(): void {
    this.adminService.getMenuForms().subscribe((result) => {
      this.formList = result.data.map((sprForm) => ({
        id: parseInt(sprForm.frm_id),
        label: sprForm.frm_code,
      }));
      EditFormComponent.setItemPropertyInValues(this.editFormValues, 'mst_frm_id', 'options', this.formList);
    });
  }

  protected setData(data: SprMenuStructuresDAO): void {
    this.data = data;
    this.form.controls.mst_title.setValue(data.mst_title);
    this.form.controls.mst_tooltip.setValue(this.additionalText?.mst_tooltip);
    this.form.controls.mst_site.setValue(data.mst_site);
    this.form.controls.mst_lang.setValue(data.mst_lang);
    this.form.controls.mst_code.setValue(data.mst_code);
    this.form.controls.mst_uri.setValue(data.mst_uri);
    this.form.controls.mst_is_public.setValue(data.mst_is_public);
    this.form.controls.mst_frm_id.setValue(data.mst_frm_id);
    this.form.controls.isCategory.setValue(this.form.controls.mst_uri.value === null);
    this.form.controls.icon.setValue(
      data.mst_icon && this.iconList.find((iconListItem) => iconListItem.name === data.mst_icon)
    );
    this.form.controls.mst_date_from.setValue(new Date(data.mst_date_from));
    this.form.controls.mst_date_to.setValue(data.mst_date_to && new Date(data.mst_date_to));
  }

  protected getData(): SprMenuStructuresDAO {
    const result: SprMenuStructuresDAO = this.data || ({} as SprMenuStructuresDAO);
    result.mst_date_from = this.form.controls.mst_date_from.value;
    result.mst_date_to = this.form.controls.mst_date_to.value;
    result.mst_frm_id = this.form.controls.isCategory.value
      ? null
      : (this.form.controls.mst_frm_id.value as SprMenuStructuresDAO['mst_frm_id']);
    result.mst_icon = this.form.controls.icon.value && (this.form.controls.icon.value as IconListItem).name;
    result.mst_uri = this.form.controls.isCategory.value ? null : this.form.controls.mst_uri.value;
    result.mst_is_public = this.form.controls.mst_is_public.value;
    result.mst_lang = this.form.controls.mst_lang.value;
    result.mst_code = this.form.controls.mst_code.value;
    result.mst_site = this.form.controls.mst_site.value;
    result.mst_title = this.form.controls.mst_title.value;
    return result;
  }

  protected doSave(value: SprMenuStructuresDAO): void {
    this.adminService.setMenuStructureRecord(value).subscribe((res) => {
      this.additionalText.mst_id = value.mst_id?.toString();
      this.additionalText.mst_tooltip = this.form.controls.mst_tooltip.value as string;
      this.adminService.setMstAdditionalText(this.additionalText).subscribe(() => {
        this.onSaveSuccess(res);
        this.onCancel();
      });
    });
  }

  onCancel(): void {
    void this.commonFormServices.router
      .navigateByUrl(`${RoutingConst.INTERNAL}/${RoutingConst.ADMIN}/${RoutingConst.SPARK_MENU_STRUCTURE_BROWSE}`)
      .then(() => {
        this.authService.loadAndGetInternalMenu().subscribe();
      });
  }
}
