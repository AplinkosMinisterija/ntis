import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { NtisReviewCreationModel, NtisReviewsDAO } from '@itree-commons/src/lib/model/api/api';
import { FaIconsService } from '@itree-commons/src/lib/services/fa-icons.service';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { Observable } from 'rxjs';
import { NtisCommonService } from '../../services/ntis-common.service';
import { ActivatedRoute, NavigationExtras, Params } from '@angular/router';
import { NtisRoutingConst } from '../../constants/ntis-routing.const';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'ntis-review-create-page',
  templateUrl: './review-create-page.component.html',
  styleUrls: ['./review-create-page.component.scss'],
  standalone: true,
  imports: [CommonModule, ItreeCommonsModule, FontAwesomeModule],
})
export class ReviewCreatePageComponent extends BaseEditForm<NtisReviewCreationModel> {
  readonly formTranslationsReference = 'ntisShared.pages.reviewCreate';
  revId: number;
  showSaveButton: boolean = false;
  returnUrl: string;
  override form = new FormGroup({
    score: new FormControl<number>(null, Validators.required),
    comment: new FormControl<string>(null),
  });

  editFormValues: EditFormValues = [
    {
      legend: null,
      translateLegend: true,
      items: [
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.score',
          translateLabel: true,
          formControlName: 'score',
          hidden: false,
          isRequired: true,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.comment',
          translateLabel: true,
          formControlName: 'comment',
          isRequired: false,
        },
      ],
    },
  ];

  constructor(
    protected commonService: CommonFormServices,
    public override activatedRoute: ActivatedRoute,
    public faIconsService: FaIconsService,
    private ntisCommonService: NtisCommonService
  ) {
    super(commonService);
    this.activatedRoute.queryParams.pipe(takeUntilDestroyed()).subscribe((params: Params) => {
      this.returnUrl = (params?.['returnUrl'] as string) ?? this.returnUrl;
    });
  }

  protected override doSave(value: NtisReviewCreationModel): void {
    this.ntisCommonService.saveNtisReview(value?.reviewInfo).subscribe(() => {
      this.commonFormServices.translate
        .get(this.formTranslationsReference + '.reviewLeft')
        .subscribe((translation: string) => {
          this.commonFormServices.appMessages.showSuccess('', translation);
        });
      this.onCancel();
    });
  }

  protected override doLoad(id: string): void | Observable<NtisReviewCreationModel> {
    this.ntisCommonService.getNtisReview(id).subscribe((res) => {
      if (res) {
        this.showSaveButton = true;
      }
      this.onLoadSuccess(res);
    });
  }

  protected override setData(data: NtisReviewCreationModel): void {
    this.data = data;
    this.form.controls.score.setValue(data?.reviewInfo?.rev_score);
    this.form.controls.comment.setValue(data?.reviewInfo?.rev_comment);
  }

  protected override getData(): NtisReviewCreationModel {
    const result = this.data != null ? this.data : ({} as NtisReviewCreationModel);
    result.reviewInfo.rev_score = this.form.controls.score.value;
    result.reviewInfo.rev_comment = this.form.controls.comment.value;
    this.data = result;
    return result;
  }

  setScore(value: number): void {
    this.form.controls.score.setValue(value);
    this.form.controls.score.updateValueAndValidity();
  }

  onCancel(): void {
    if (this.returnUrl) {
      const navigationExtras: NavigationExtras = { state: { keepFilters: true } };
      void this.commonFormServices.router.navigateByUrl(this.returnUrl, navigationExtras);
    } else {
      void this.commonFormServices.router.navigate([RoutingConst.INTERNAL, NtisRoutingConst.NTIS_INTS_OWNER_DASHBOARD]);
    }
  }
}
