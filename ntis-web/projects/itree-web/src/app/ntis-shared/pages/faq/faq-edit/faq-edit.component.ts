import { ActivatedRoute } from '@angular/router';
import { BaseEditForm, CommonFormServices } from '@itree/ngx-s2-commons';
import { Component } from '@angular/core';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';
import { FaqModel } from '@itree-commons/src/lib/model/api/api';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, Location } from '@angular/common';
import { S2File } from '@itree/ngx-s2-ui';
import { NtisFaqService } from '../../../services/ntis-faq.service';
import { s2FileToSprFile } from '@itree-commons/src/lib/utils/s2-file-to-spr-file';
import { sprFileToS2File } from '@itree-commons/src/lib/utils/spr-file-to-s2-file';
import { ItreeCommonsModule } from '@itree-commons/src/public-api';
import { EditorModule } from 'primeng/editor';

@Component({
  selector: 'ntis-faq-edit',
  templateUrl: './faq-edit.component.html',
  styleUrls: ['./faq-edit.component.scss'],
  standalone: true,
  imports: [CommonModule, EditorModule, ItreeCommonsModule, ReactiveFormsModule],
})
export class FaqEditComponent extends BaseEditForm<FaqModel> {
  readonly formTranslationsReference = 'pages.sprFaq';
  readonly MAIN = 'MAIN';
  questionGroup: string;
  questionId: string;

  form = new FormGroup({
    fac_code: new FormControl(''),
    fac_question: new FormControl(''),
    fac_answer: new FormControl(''),
    uploadedFiles: new FormControl<S2File[]>([]),
  });

  editFormValues: EditFormValues = [
    {
      items: [
        {
          type: EditFormItemType.Text,
          label: this.formTranslationsReference + '.fac_code',
          translateLabel: true,
          formControlName: 'fac_code',
          isRequired: true,
          maxLength: 50,
        },
        {
          type: EditFormItemType.TextArea,
          label: this.formTranslationsReference + '.fac_question',
          translateLabel: true,
          formControlName: 'fac_question',
          isRequired: true,
          rows: 2,
        },
        {
          type: EditFormItemType.Custom,
          label: this.formTranslationsReference + '.fac_answer',
          translateLabel: true,
          formControlName: 'fac_answer',
          isRequired: true,
        },
        {
          type: EditFormItemType.Files,
          label: undefined,
          translateLabel: true,
          formControlName: 'uploadedFiles',
          maxFiles: 20,
        },
      ],
    },
  ];

  constructor(
    protected override commonFormServices: CommonFormServices,
    private ntisFaqService: NtisFaqService,
    private location: Location,
    private route: ActivatedRoute
  ) {
    super(commonFormServices);

    this.route.params.subscribe((params) => {
      this.questionGroup = params['code'] as string;
      this.questionId = params['id'] as string;
      if (this.questionId !== 'new') {
        this.doLoad(this.questionId);
      }
    });
  }

  protected doSave(value: FaqModel): void {
    this.ntisFaqService.setQuestionAnswer(value).subscribe(() => {
      this.location.back();
    });
  }

  protected doLoad(id?: string): void {
    this.ntisFaqService.getQuestionAnswer(id).subscribe((res) => {
      this.onLoadSuccess(res);
    });
  }

  protected setData(data: FaqModel): void {
    this.data = data;
    this.form.controls.fac_code.setValue(data?.facCode);
    this.form.controls.fac_question.setValue(data?.facQuestion);
    this.form.controls.fac_answer.setValue(data?.facAnswer);
    this.form.controls.uploadedFiles.setValue(data.attachment?.map((file) => sprFileToS2File(file)) || []);
    if (this.data.facCode) {
      this.form.controls.fac_code.disable();
    } else {
      this.form.controls.fac_code.enable();
    }
  }

  protected getData(): FaqModel {
    const result: FaqModel = this.data || ({} as FaqModel);
    result.facGroup = this.questionGroup;
    result.facType = this.MAIN;
    result.facCode = this.form.controls.fac_code.value;
    result.facQuestion = this.form.controls.fac_question.value;
    result.facAnswer = this.form.controls.fac_answer.value;
    result.attachment = this.form.controls.uploadedFiles.value?.map((file) => s2FileToSprFile(file));
    this.data = result;
    return result;
  }

  onCancel(): void {
    this.location.back();
  }
}
