import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import DecoupledEditor from '@ckeditor/ckeditor5-build-decoupled-document';
import { EditFormItemType } from '@itree-commons/src/lib/enums/edit-form.enums';
import { EditFormValues } from '@itree-commons/src/lib/types/edit-form';

@Component({
  selector: 'app-template-text-edit',
  templateUrl: './template-text-edit.component.html',
  styleUrls: ['./template-text-edit.component.scss'],
})
export class TemplateTextEditComponent implements OnInit {
  public Editor = DecoupledEditor;
  editorData: string = null;
  @Input() formGroup: FormGroup;
  @Input() showRemoveButton = true;
  @Input() isTextEditor = true;
  @Input() hideCode = false;
  @Input() hideName = false;
  @Input() hideDescription = false;
  @Output() remove = new EventEmitter();

  editFormValues: EditFormValues;

  readonly formTranslationsReference = 'pages.sprTemplateEdit';

  ngOnInit(): void {
    this.editFormValues = [
      {
        items: [
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.code',
            translateLabel: true,
            formControlName: 'code',
            errorDefs: { uniqueCode: 'pages.sprTemplateEdit.error' },
            isRequired: true,
            maxLength: 32,
            hidden: this.hideCode,
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.name',
            translateLabel: true,
            formControlName: 'name',
            isRequired: true,
            maxLength: 200,
            hidden: this.hideName,
          },
          {
            type: EditFormItemType.Text,
            label: this.formTranslationsReference + '.description',
            translateLabel: true,
            formControlName: 'description',
            maxLength: 600,
            hidden: this.hideDescription,
          },
          {
            type: this.isTextEditor ? EditFormItemType.Custom : EditFormItemType.Text,
            label: this.formTranslationsReference + '.text',
            translateLabel: true,
            formControlName: 'text',
            isRequired: true,
          },
          {
            type: EditFormItemType.Date,
            label: this.formTranslationsReference + '.dateFrom',
            translateLabel: true,
            formControlName: 'dateFrom',
            isRequired: true,
          },
          {
            type: EditFormItemType.Date,
            label: this.formTranslationsReference + '.dateTo',
            translateLabel: true,
            formControlName: 'dateTo',
          },
        ],
      },
    ];
  }

  public onReady(editor: DecoupledEditor): void {
    const element = editor.ui.getEditableElement();
    const parent = element.parentElement;
    parent.insertBefore(editor.ui.view.toolbar.element, element);
  }
}
